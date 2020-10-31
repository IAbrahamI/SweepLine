package sample;

public class Parabole {

    double a;
    double u;
    double v;
    double minusXPointValue;
    double plusXPointValue;
    double incrementedValue;

    //------------------------------------------------------------------------
    public Parabole(double a, double u, double v, double minusXPointValue, double plusXPointValue, double incrementedValue) {
        this.a = a;
        this.u = u;
        this.v = v;
        this.minusXPointValue = minusXPointValue;
        this.plusXPointValue = plusXPointValue;
        this.incrementedValue = incrementedValue;
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
    public double getMinusXPointValue() {
        return minusXPointValue;
    }
    public void setMinusXPointValue(double minusXPointValue) {
        this.minusXPointValue = minusXPointValue;
    }
    public double getPlusXPointValue() {
        return plusXPointValue;
    }
    public void setPlusXPointValue(double plusXPointValue) {
        this.plusXPointValue = plusXPointValue;
    }
    public double getIncrementedValue() {
        return incrementedValue;
    }
    public void setIncrementedValue(double incrementedValue) {
        this.incrementedValue = incrementedValue;
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
