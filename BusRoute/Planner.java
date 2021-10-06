import java.io.*;
import java.util.*;

public class Planner {

    private Graph busMap;
    private String busLines;
    private Node start, dest;
    private final Vector<Node> route = new Vector<Node>();

    public Planner(String inputFile) throws MapException{
        try{
            BufferedReader roadInfo = new BufferedReader(new FileReader(inputFile));
            int i = 0, row = 0, width = 0, height = 0, nodePos = 0, prevPos = 0;
            // i keeps track of the line in the file we are on
            // row keeps track of which roads we are processing; nodePos keeps track of which node we are on
            // prevPos is the previous first node we processed before going to the next line in the file
            // width and height are the dimensions of the graph
            String currInfo = null;
			try {
				currInfo = roadInfo.readLine(); // give currInfo the lines from the file
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
            while(currInfo != null){
                if (i > 0){ // When i = 0, then we are on a line that doesn't need to be processed
                    if (i == 1){
                        // i = 1 means we are on the line that gives us the width and the next gives us the height
                        try {
                            width = Integer.parseInt(roadInfo.readLine());
							height = Integer.parseInt(roadInfo.readLine());
						} catch (IOException e) {
							System.out.println(e.getMessage());
						}
                        busMap = new Graph(width * height);
                        i++;
                    }
                    else if (i == 3){
                            // when i = 3 we are on the line that tells us which bus lines there are
							busLines = currInfo;
                    }
                    else {
                        try {
                            boolean vertRoad = false;
                            if (row % 2 == 1){
                                vertRoad = true;
                            }
                            for (int j = 0; j< currInfo.length(); j++){
                                // go through all of the characters in the line
                                char currChar = currInfo.charAt(j);
                                if (currChar == 'S'){
                                    // if the character is an S, this is the starting point
                                	start = busMap.getNode(nodePos);
                                }
                                else if (currChar == 'D'){
                                    // if the character is a D, this is the destination
                                    dest = busMap.getNode(nodePos);
                                }
                                else if (Character.isLetter(currChar)){
                                    // if it is a letter, call the function to create and add an edge
                                    createRoad(row, nodePos + 1, nodePos, width, String.valueOf(currChar));
                                    // the next edge will now start at the next node so increment the node position
                                    nodePos++;
                                }
                                else if ((currChar != '+') && (currChar != '*')){
                                    // if it is not either of these characters, it is a road with no line, so don't
                                    // process the edge
                                    nodePos++;
                                }
                                else if ((currChar == '*') && (j % 2 != row % 2)){
                                    // Encountering an area where a road is thought to be made messes up counting
                                    // Ensures we skip the area with no road proper;y
                                    nodePos++;
                                }

                            }
                            nodePos++;
                            if (vertRoad){
                                // After processing the vertical roads, we must process the horizontal roads
                                // for the same intersections, so go back to the previous position before this line
                                nodePos = prevPos;
                            }else{
                                // keep track of the previous first node position from each line
                                prevPos = nodePos;
                            }
                            row++;
                        } catch (GraphException e){
                            System.out.println(e.getMessage());
                        }
                    }
                    try {
                    	currInfo = roadInfo.readLine();
                    }
                    catch (IOException e) {
                    	System.out.println(e.getMessage());
                    }

                }
                i++;
            }
            try {
				roadInfo.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
        } catch(FileNotFoundException e){
            throw new MapException(e.getMessage());
        }
    }

    public Graph getGraph() throws MapException{
        if (busMap == null){
            throw new MapException("MapException: the graph could not be initialized");
        }
        return busMap;
    }

    public Iterator<Node> planTrip(){
    	route.add(start);
    	start.setMark(true);
    	for (int i = 0; i < busLines.length(); i++) {
    	    // For each bus line, check if we have a valid path
            // if none are found then it is obvious we don't have a path to the destination.
    		if (Character.isLetter(busLines.charAt(i))) {
    			if (possiblePath(start, dest, busLines.charAt(i))) {
    				return route.iterator();
    			}
    		}
    	}
    	if (busLines.length() == 0){
    	    possiblePath(start, dest, ' ');
    	    return route.iterator();
        }
    	return null;

    }

    private boolean possiblePath(Node s, Node d, char line){

    	s.setMark(true);
    	if (!route.contains(s)) {
            route.add(s);
            // if the node is not in the vector, add it as it might be part of the path
        }
    	if (s.equal(d)) {
    	    // we have found the path in this case
    		return true;
    	}
    	else {
    		try {
				Iterator<Edge> edges = busMap.incidentEdges(s);
				// get all the incident edges to see if there is a possible path down one of them
				while (edges.hasNext()) {
	    			Edge currEdge = edges.next();
	    			if (((currEdge.getBusLine().charAt(0) == line) ||
                            !busLines.contains(currEdge.getBusLine()))) {
	    			    // you can only go down this road if it is using the correct bus line or it is not part of the
                        // lines
	    			    if (!currEdge.secondEndpoint().getMark()){
                            if (possiblePath(currEdge.secondEndpoint(), d, line)) {
                                return true;
                            }
                        }

	    			}
	    		}
				s.setMark(false); // remove the mark so we can possibly use the path again
				route.remove(s);
				return false;
			} catch (GraphException e) {
				System.out.println(e.getMessage());
			}
    		return false;
    	}
    }

    private void createRoad(int row, int vPos, int uPos, int width, String busLine) throws GraphException{
        Node u;
        Node v;
        if (row % 2 == 0) {
            u = busMap.getNode(uPos);
            v = busMap.getNode(vPos);
        }
        else {
            u = busMap.getNode(uPos - width );
            v = busMap.getNode(vPos-1);
        }
        busMap.addEdge(u, v, busLine);
    }
}
