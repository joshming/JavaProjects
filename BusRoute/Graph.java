import java.util.*;

public class Graph implements GraphADT{

    private Node[] vertices;
    private Edge[][] edges;

    public Graph(int n){
        // set up the graph as a adjacency matrix
        vertices = new Node[n];
        for (int i = 0; i < n; i++){
            vertices[i] = new Node(i);
        }
        edges = new Edge[n][n];
    }

    public void addEdge(Node nodeu, Node nodev, String busLine) throws GraphException{
    	if (!((nodeu.getName() >= vertices.length) || (nodev.getName() >= vertices.length))) {
    	    // if this is true, then we have nodes for the nodes that were passed as parameters
            if ((edges[nodeu.getName()][nodev.getName()] == null)  &&
                    (edges[nodev.getName()][nodev.getName()] == null)){
                // if this edge doesn't exist, put edges (u, v) and (v, u) in their areas in the adjacency matrix
                edges[nodeu.getName()][nodev.getName()] = new Edge(nodeu, nodev, busLine);
                edges[nodev.getName()][nodeu.getName()] = new Edge(nodev, nodeu, busLine);
            }
            else{
                throw new GraphException("GraphException: edges already exist.");
            }
        }
        else{
            throw new GraphException("GraphException: these nodes do not exist.");
        }

    }

    public Node getNode(int name) throws GraphException{
        if (name < vertices.length){
            return vertices[name];
        }
        throw new GraphException("GraphException: There is no node with this name in this graph.");
    }

    public Iterator<Edge> incidentEdges(Node u) throws GraphException{
        if (u.getName() >= vertices.length){
            throw new GraphException("GraphException: This node does not exist.");
        }
        Stack<Edge> stackEdges = new Stack<>();
        // create the stack, implementing the iterator interface
        for (int i = 0; i < vertices.length; i++){
            if (edges[u.getName()][i] != null){
                stackEdges.add(edges[u.getName()][i]);
            }
        }
        if (!stackEdges.isEmpty()){
            // once we have gotten all of the edges, return the iterator
            return stackEdges.iterator();
        }
        // if there were no edges, return null
        return null;
    }

    public Edge getEdge(Node u, Node v) throws GraphException{
        // return the edge with the given endpoints
        if ((u.getName() >= vertices.length) || (v.getName() >= vertices.length)){
            throw new GraphException("GraphException: One or both of these nodes do not exist.");
        }
        else if (edges[u.getName()][v.getName()] == null){
            throw new GraphException("GraphException: there is no edge.");
        }
        else{
            return edges[u.getName()][v.getName()];
        }
    }

    public boolean areAdjacent(Node u, Node v) throws GraphException{
        // if they are adjacent, there should be an edge between them at their indices in the matrix
    	if ((u.getName() >= vertices.length) || (v.getName() >= vertices.length)){
            throw new GraphException("GraphException: One or both of these nodes do not exist.");
        }
        else return edges[u.getName()][v.getName()] != null;
    }
}
