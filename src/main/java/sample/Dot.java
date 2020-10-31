package sample;

public class Dot {

    double x;
    double y;
    double xParaboleValue;
    //-----------------------------------------------------------
    public Dot(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Dot(double x, double y, double xParaboleValue){
        this.x = x;
        this.y = y;
        this.xParaboleValue = xParaboleValue;
    }
    //-----------------------------------------------------------
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
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
