public class Icon implements IconADT{
	private int id, width, height;
	private String type; 
	private Position offset;
	private BinarySearchTree bst;
	
	public Icon(int id, int width, int height, String type, Position pos) {
		this.id = id; 
		this.width = width;
		this.height = height;
		this.type = type; 
		offset = pos;
		bst = new BinarySearchTree();
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public String getType(){
	    return type;
    }

    public int getId(){
	    return id;
    }

    public Position getOffset(){
	    return offset;
    }

    public void setOffset(Position value){
	    offset = value;
    }

    public void addPixel(Pixel pix) throws DuplicatedKeyException{
		// if this pixel is not already in the tree, we can add it
        if (!findPixel(pix)){
            bst.put(bst.getRoot(), pix);
        }
        else{
            throw new DuplicatedKeyException("DuplicatedKeyException: Key is already in the dictionary.");
        }
    }

    public boolean intersects(Icon otherIcon){
		boolean possibleIntersect = false;
		int thisXCoord = this.getOffset().xCoord();
		int thisYCoord = this.getOffset().yCoord();
		int otherXCoord = otherIcon.getOffset().xCoord();
		int otherYCoord = otherIcon.getOffset().yCoord();

		// Intersection of this icon's left half with other's right right
		if ((thisXCoord >= otherXCoord) && (thisXCoord <= otherXCoord + otherIcon.getWidth())){
			if ((thisYCoord >= otherYCoord) && (thisYCoord <= otherYCoord + otherIcon.getHeight())) {
				possibleIntersect = true;
			}
			else if ((thisYCoord + this.height >= otherYCoord) && (thisYCoord + this.height <= otherYCoord +
					otherIcon.getHeight())){
				possibleIntersect = true;
			}
			else if ((thisYCoord <= otherYCoord) && (thisYCoord + height >= otherYCoord + otherIcon.getHeight())){
				possibleIntersect = true;
			}
		}
		// intersection of this icon's right half with other's left half
		else if ((thisXCoord + this.width >= otherXCoord) && (thisXCoord + this.width <= otherXCoord +
				otherIcon.getWidth())){
			if ((thisYCoord >= otherYCoord) && (thisYCoord <= otherYCoord + otherIcon.getHeight())){
				possibleIntersect = true;
			}
			else if ((thisYCoord + this.height >= otherYCoord) && (thisYCoord + this.height <= otherYCoord +
					otherIcon.getHeight())){
				possibleIntersect = true;
			}
			else if ((thisYCoord <= otherYCoord) && (thisYCoord + this.height >= otherYCoord + otherIcon.getHeight())){
				possibleIntersect = true;
			}
		}
		// this icon is larger in at least the x direction of other icon
		else if ((thisXCoord <= otherXCoord) && (thisXCoord + this.width >= otherXCoord + otherIcon.getHeight())){
			if ((thisYCoord >= otherYCoord) && (thisYCoord <= otherYCoord + otherIcon.getHeight())){
				possibleIntersect = true;
			}
			else if ((thisYCoord + this.height >= otherYCoord) && (thisYCoord + this.height <= otherYCoord +
					otherIcon.getHeight())){
				possibleIntersect = true;
			}
			else if ((thisYCoord <= otherYCoord) && (thisYCoord + this.height >= otherYCoord + otherIcon.getHeight())){
				possibleIntersect = true;
			}
		}
		// this icon is smaller in the x direction, but intersects due to the length of the rectangle
		else if ((thisXCoord <= otherXCoord) && (thisXCoord + this.width <= otherXCoord + otherIcon.getWidth())){
			if ((thisYCoord >= otherYCoord) && (thisYCoord <= otherYCoord + otherIcon.getHeight())){
				possibleIntersect = true;
			}
			else if ((thisYCoord + this.height >= otherYCoord) && (thisYCoord + this.height <= otherYCoord +
					otherIcon.getHeight())){
				possibleIntersect = true;
			}
			else if ((thisYCoord <= otherYCoord) && (thisYCoord + this.height >= otherYCoord + otherIcon.getHeight())){
				possibleIntersect = true;
			}
		}
		// If possibleIntersects is true, then the rectangles of both Icon's touch/intersect each other
		if (possibleIntersect){
			try {
				// get the "first"/smallest Pixel of this Icon
				Pixel currPixel = this.bst.smallest(bst.getRoot());
				// while this Icon's Pixel is never null
				while (currPixel != null){
					Position currPos = currPixel.getPosition();
					// setup a test position such that the:
					// x-coordinate is the current Pixel's x-coordinate + this Icon's xoffset - other Icon's xoffset
					// y-coordinate is the current Pixel's y-coordinate + this Icon's yoffset - other Icon's yoffset
					Position test = new Position(currPos.xCoord() + thisXCoord - otherXCoord, currPos.yCoord()
							+ thisYCoord - otherYCoord);
					// if the other icon doesn't have this position in the tree then we can test other pixels
					if (otherIcon.bst.get(otherIcon.bst.getRoot(), test) == null){
						currPixel = this.bst.successor(this.bst.getRoot(), currPixel.getPosition());
					}
					// if it does have this pixel, then there is an intersection and we must return true
					else return true;
				}
				return false;
			}catch (EmptyTreeException e){
				System.out.println(e.getMessage());
			}
		}
		// Since possibleIntersect is false, there isn't a way for these icons to be intersecting
		return false;
	}

    private boolean findPixel(Pixel pix){
	    // returns true iff pixel is in the BST
	    Pixel possNode = bst.get(bst.getRoot(), pix.getPosition());
		return possNode != null;
    }
}
