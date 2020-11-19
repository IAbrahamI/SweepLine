package model;

public class Parabole {

    double a;
    double u;
    double v;
    double bPointX;
    double bPointY;
    double incrementedValue;

    //------------------------------------------------------------------------
    public Parabole(double a, double u, double v, double incrementedValue, double bPointX, double bPointY) {
        this.a = a;
        this.u = u;
        this.v = v;
        this.incrementedValue = incrementedValue;
        this.bPointX = bPointX;
        this.bPointY = bPointY;
    }
    //------------------------------------------------------------------------
    public double getA() {
        return a;
    }
    public void setA(double a) {
        this.a = a;
    }
    public double getU() {
        return u;
    }
    public void setU(double u) {
        this.u = u;
    }
    public double getV() {
        return v;
    }
    public void setV(double v) {
        this.v = v;
    }
    public double getIncrementedValue() {
        return incrementedValue;
    }
    public void setIncrementedValue(double incrementedValue) {
        this.incrementedValue = incrementedValue;
    }
    public double getbPointX() {
        return bPointX;
    }
    public void setbPointX(double bPointX) {
        this.bPointX = bPointX;
    }
    public double getbPointY() {
        return bPointY;
    }

    public void setbPointY(double bPointY) {
        this.bPointY = bPointY;
    }
    //------------------------------------------------------------------------
    public double getXValueFromParabola(double incrementedValue){
        return incrementedValue;
    }
    public double getYValueFromParabola(double incrementedValue){
        return this.a*((incrementedValue-this.u)*(incrementedValue-this.u))+this.v;
    }
    //------------------------------------------------------------------------




}
