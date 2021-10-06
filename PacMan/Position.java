public class Position {
    private int xPos;
    private int yPos;
    public Position(int x, int y){
        xPos = x;
        yPos = y;
    }
    public int xCoord() {
        return xPos;
    }

    public int yCoord(){
        return yPos;
    }
    public int compareTo(Position p){
        // this position is < p
        if (((this.xPos < p.xCoord()) && this.yPos == p.yCoord()) || (this.yPos < p.yCoord())){
            return -1;
        }
        // this position = p
        else if ((this.xPos == p.xCoord()) && (this.yPos == p.yCoord())){
            return 0;
        }
        // this position > p
        else {
            return 1;
        }
    }
}
