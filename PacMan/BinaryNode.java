public class BinaryNode {
    private Pixel data;
    private BinaryNode leftChild, rightChild, parentNode;

    public BinaryNode(Pixel value, BinaryNode left, BinaryNode right, BinaryNode parent){
        data = value;
        leftChild = left;
        rightChild = right;
        parentNode = parent;
    }
    public BinaryNode(){
        data = null;
        leftChild = null;
        rightChild = null;
        parentNode = null;
    }

    public BinaryNode getParent(){
        return parentNode;
    }
    public void setParent(BinaryNode parent){
        parentNode = parent;
    }

    public void setLeft(BinaryNode p){
        leftChild = p;
    }

    public void setRight(BinaryNode p){
        rightChild = p;
    }

    public void setData(Pixel value){
        data = value;
    }

    public boolean isLeaf(){
        return ((leftChild == null) && (rightChild == null));
    }

    public Pixel getData(){
        return data;
    }

    public BinaryNode getLeft(){
        return leftChild;
    }

    public BinaryNode getRight(){
        return rightChild;
    }
}
