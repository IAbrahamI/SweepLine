package sample;

public class Parabole {

    double startXpoint;
    double startYpoint;
    double endXpoint;
    double endYpoint;
    double curveXpoint;
    double curveYpoint;

    //------------------------------------------------------------------------
    public Parabole(double startXpoint, double startYpoint, double endXpoint, double endYpoint, double curveXpoint, double curveYpoint) {
        this.startXpoint = startXpoint;
        this.startYpoint = startYpoint;
        this.endXpoint = endXpoint;
        this.endYpoint = endYpoint;
        this.curveXpoint = curveXpoint;
        this.curveYpoint = curveYpoint;
    }
    //------------------------------------------------------------------------
    public double getStartXpoint() {
        return startXpoint;
    }
    public void setStartXpoint(double startXpoint) {
        this.startXpoint = startXpoint;
    }
    public double getStartYpoint() {
        return startYpoint;
    }
    public void setStartYpoint(double startYpoint) {
        this.startYpoint = startYpoint;
    }
    public double getEndXpoint() {
        return endXpoint;
    }
    public void setEndXpoint(double endXpoint) {
        this.endXpoint = endXpoint;
    }
    public double getEndYpoint() {
        return endYpoint;
    }
    public void setEndYpoint(double endYpoint) {
        this.endYpoint = endYpoint;
    }
    public double getCurveXpoint() {
        return curveXpoint;
    }
    public void setCurveXpoint(double curveXpoint) {
        this.curveXpoint = curveXpoint;
    }
    public double getCurveYpoint() {
        return curveYpoint;
    }
    public void setCurveYpoint(double curveYpoint) {
        this.curveYpoint = curveYpoint;
    }
    //------------------------------------------------------------------------
}
