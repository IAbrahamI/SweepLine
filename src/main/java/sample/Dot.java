package sample;

public class Dot {

    double x;
    double y;
    double xMin;
    double xMax;
    //-----------------------------------------------------------
    public Dot(double x, double y) {
        this.x = x;
        this.y = y;
        this.xMin = xMin;
        this.xMax = xMax;
    }
    public Dot(double x, double y, double xMin,double xMax) {
        this.x = x;
        this.y = y;
        this.xMin = xMin;
        this.xMax = xMax;
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
    public double getxMin() {
        return xMin;
    }
    public void setxMin(double xMin) {
        this.xMin = xMin;
    }
    public double getxMax() {
        return xMax;
    }
    public void setxMax(double xMax) {
        this.xMax = xMax;
    }
    //-----------------------------------------------------------
}
