package sample;

public class Dot {

<<<<<<< Updated upstream
    int x;
    int y;
    double xParaboleValue;
=======
    double x;
    double y;
>>>>>>> Stashed changes
    //-----------------------------------------------------------
    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }
<<<<<<< Updated upstream
    public Dot(int x, int y, double xParaboleValue){
        this.x = x;
        this.y = y;
        this.xParaboleValue = xParaboleValue;
    }
=======
>>>>>>> Stashed changes
    //-----------------------------------------------------------
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    //-----------------------------------------------------------
}
