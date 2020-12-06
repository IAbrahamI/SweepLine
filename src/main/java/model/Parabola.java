package model;

public class Parabola {

    double a;
    double u;
    double v;

    //------------------------------------------------------------------------
    public Parabola(double a, double u, double v) {
        this.a = a;
        this.u = u;
        this.v = v;
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
    //------------------------------------------------------------------------
}
