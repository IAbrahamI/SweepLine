package sample;

public class Dot {

    int x;
    int y;
    double xParaboleValue;
    //-----------------------------------------------------------
    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Dot(int x, int y, double xParaboleValue){
        this.x = x;
        this.y = y;
        this.xParaboleValue = xParaboleValue;
    }
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
    public double getxParaboleValue() {
        return xParaboleValue;
    }
    public void setxParaboleValue(double xParaboleValue) {
        this.xParaboleValue = xParaboleValue;
    }
    //-----------------------------------------------------------
}
