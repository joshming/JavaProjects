public class Node {

    private int name;
    private boolean mark;

    public Node(int n){
        name = n;
        mark = false;
    }

    public void setMark(boolean newMark) {
        mark = newMark;
    }

    public boolean getMark(){
        return mark;
    }

    public int getName(){
        return name;
    }

    public boolean equal(Node otherNode){
        return this.name == otherNode.name;
    }

}
