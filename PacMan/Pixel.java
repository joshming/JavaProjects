public class Pixel {
    private Position coordinate;
    private int colour;

    public Pixel(Position p, int color){
        coordinate = p;
        colour = color;
    }

    public Position getPosition(){
        return coordinate;
    }

    public int getColor(){
        return colour;
    }

}
