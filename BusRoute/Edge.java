public class Edge {

    private Node endP1;
    private Node endP2;
    private String busLine;

    public Edge(Node u, Node v, String busLine){
        endP1 = u;
        endP2 = v;
        this.busLine = busLine;
    }

    public Node firstEndpoint(){
        return endP1;
    }

    public Node secondEndpoint(){
        return endP2;
    }

    public String getBusLine(){
        return busLine;
    }
}
