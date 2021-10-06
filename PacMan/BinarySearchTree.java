public class BinarySearchTree implements BinarySearchTreeADT{
    private BinaryNode root;

    public BinarySearchTree(){
        root = new BinaryNode();
    }

    public Pixel get(BinaryNode r, Position key){
        if (r.isLeaf()) {
            return r.getData();
        }
        else {
            // if the node / root's key is equal to the key we want to find, then return the node's / root's data
            if ((r.getData().getPosition().compareTo(key))==0) {
                return r.getData();
            }
            // if key < r.key() go down the left path
            else if (r.getData().getPosition().compareTo(key) == 1) {
                return get(r.getLeft(), key);
            }
            // else go down the right subtree
            else {
                return get(r.getRight(), key);
            }
        }
    }
    private BinaryNode getNode(BinaryNode r, Position key){
        // performs the same algorithm as get, however, it returns the BinaryNode instead of the Pixel.
        if (r.isLeaf()) {
            return r;
        }
        else {
            if ((r.getData().getPosition().compareTo(key))==0) {
                return r;
            }
            // if key < r.key() go down the left path
            else if (r.getData().getPosition().compareTo(key) == -1) {
                return getNode(r.getRight(), key);
            }
            // else go down the right subtree
            else {
                return getNode(r.getLeft(), key);
            }
        }
    }

    public void put(BinaryNode r, Pixel data) throws DuplicatedKeyException{
        // puts a new BinaryNode into the tree with data <data>
        BinaryNode posNode = getNode(r, data.getPosition()); // returns the node for where to place the new node
        if (!posNode.isLeaf()){
            throw new DuplicatedKeyException("DuplicatedKeyException: key is already in the dictionary");
        }
        // As long as posNode is not a leaf, we create a left and right child that will now be the children of posNode
        // posNode will have the Pixel we wanted to put into the tree
        else{
            BinaryNode newLChild = new BinaryNode();
            BinaryNode newRChild = new BinaryNode();
            newLChild.setParent(posNode);
            newRChild.setParent(posNode);
            posNode.setData(data);
            posNode.setLeft(newLChild);
            posNode.setRight(newRChild);
        }
    }

    public void remove(BinaryNode r, Position key) throws InexistentKeyException{
        BinaryNode posNode = getNode(r, key); // get the possible BinaryNode posNode
        if (posNode.isLeaf()){
            throw new InexistentKeyException("InexistentKeyException: this key does not exist in the dictionary");
        }
        else {
            if (posNode.getLeft().isLeaf()) {
                BinaryNode tempChild = posNode.getRight();
                BinaryNode parent = posNode.getParent();
                if (parent == null) { // if posNode's parent is null, that means that posNode is the root
                    // when posNode is a root the new root of the tree is now the right child of posNode
                    // the right child of posNode now has no parent
                    root = tempChild;
                    tempChild.setParent(null);
                }
                // Make sure that posNode is the left child of the node and posNode's left child is a leaf
                // then we have to make the right child of posNode, the left child of posNode's former parent
                else if (equalNodes(posNode, parent.getLeft())) {
                    parent.setLeft(tempChild);
                    tempChild.setParent(parent);
                }
                // else posNode must be the right child of the parent and we must make the right child of posNode the right
                // child of posNode's former parent
                else {
                    parent.setRight(tempChild);
                    tempChild.setParent(parent);
                }
            }
            // repeat the process/cases for when the right is a leaf
            else if (posNode.getRight().isLeaf()) {
                BinaryNode tempChild = posNode.getLeft();
                BinaryNode parent = posNode.getParent();
                if (parent == null) {
                    root = tempChild;
                    tempChild.setParent(null);
                } else if (equalNodes(posNode, parent.getLeft())) {
                		parent.setLeft(tempChild);
                		tempChild.setParent(parent);
                	}
                	else {
                		parent.setRight(tempChild);
                		tempChild.setParent(parent);
                	}
                }
            else{
                // If neither are a leaf, now we must find the smallest node larger than posNode to replace it to
                // maintain the binary search tree structure
                try {
                    Pixel smallData = smallest(posNode.getRight());
                    BinaryNode smallNode = getNode(posNode.getRight(), smallData.getPosition());
                    posNode.setData(smallData);
                    remove(smallNode, smallData.getPosition());
                }catch(EmptyTreeException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public Pixel smallest(BinaryNode r) throws EmptyTreeException{
        if (r.getData() == null) {
            throw new EmptyTreeException("EmptyTreeException: the tree is empty.");
        }
        else if (r.isLeaf()){
            return r.getData();
        }
        // when r is neither a leaf nor doesn't have data, continue down the left subtree
        else{
            BinaryNode nextNode = r;
            while (!nextNode.getLeft().isLeaf()){
                nextNode = nextNode.getLeft();
            }
            // return the data once you get to leaf
            return nextNode.getData();
        }
    }

    public Pixel successor(BinaryNode r, Position key){
        if (r.isLeaf()){
            return null;
        }
        else {
            BinaryNode nextNode = getNode(r, key);
            if ((!nextNode.isLeaf()) && (!nextNode.getRight().isLeaf())){
                try {
                    // if the retrieved BinaryNode <nextNode> and its right child  is not a leaf, get the smallest of
                    // its right subtree
                    return smallest(nextNode.getRight());
                }catch(EmptyTreeException e){
                    System.out.println(e.getMessage());
                }
            }
            else{
                // When nextNode is either a leaf or it's right child is a leaf, continue to get nextNode's parent
                // whilst nextNode remains the right child of the parent
                BinaryNode parent = nextNode.getParent();
                while ((!equalNodes(r, nextNode)) && (equalNodes(parent.getRight(), nextNode))){
                    nextNode = parent;
                    parent = nextNode.getParent();
                }
                // Check if nextNode is the root, then there is no successor
                if (equalNodes(nextNode, r)){
                    return null;
                }
                // since it is not the root, this must be the successor so return the data stored in parent
                else{
                    return parent.getData();
                }
            }
        }
        return null;
    }

    public Pixel predecessor(BinaryNode r, Position key){
        if (r.isLeaf()){
            return null;
        }
        else {
            //  when r is not a leaf get the desired node we want to find the predecessor for
            BinaryNode nextNode = getNode(r, key);
            if ((!nextNode.isLeaf()) && (!nextNode.getLeft().isLeaf())) {
                try {
                    // as long as nextNode and its right child is a leaf, get the largest node form its left subtree
                    return largest(nextNode.getLeft());
                } catch (EmptyTreeException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                // if that condition is false, then get this parent
                BinaryNode parent = nextNode.getParent();
                // if nextNode is the left child of its parent, then loop until we get to a node that isn't the left
                // child anymore or until we get to the root
                if (nextNode.getData().getPosition().compareTo(parent.getData().getPosition())==-1) {
                	while ((!equalNodes(nextNode, r)) && (equalNodes(parent.getLeft(), nextNode))) {
                        nextNode = parent;
                        parent = nextNode.getParent();
                    }
                	// if we are at the root, there is no predecessor
                    if (equalNodes(nextNode, r)) {
                       return null;
                    } else {
                        return parent.getData();
                    }
                }
                // nextNode was the right child of its parent so we just return the parent's data
                return nextNode.getParent().getData();
            }
        }
        return null;
    }

    public Pixel largest(BinaryNode r) throws EmptyTreeException{
        if (r.getData() == null){
            throw new EmptyTreeException("EmptyTreeException: The tree with your root is empty.");
        }
        else if (r.isLeaf()){
            return r.getData();
        }
        else{
            // as long as the right child is not a leaf, keep going down the right subtree
            BinaryNode nextNode = r;
            while (!nextNode.getRight().isLeaf()){
                nextNode = nextNode.getRight();
            }
            return nextNode.getData();
        }
    }

    public BinaryNode getRoot(){
        return root;
    }

    private boolean equalNodes(BinaryNode r1, BinaryNode r2){
        // Returns true iff r1 = r2
    	if ((!r1.isLeaf()) && (!r2.isLeaf())){
    		 return r1.getData().getPosition().compareTo(r2.getData().getPosition()) == 0;
    	}
    	return (r1 == r2) && (r1.getData() == r2.getData());
    }
}
