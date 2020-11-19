package model;

public class Arc {

    private double xMinValue;
    private double xMaxValue;
    private double aValue;
    private double uValue;
    private double vValue;
    //-----------------------------------------------------------------------------
     public Arc(double xMinValue, double xMaxValue, double aValue, double uValue, double vValue) {
            this.xMinValue = xMinValue;
            this.xMaxValue = xMaxValue;
            this.aValue = aValue;
            this.uValue = uValue;
            this.vValue = vValue;
        }
    //-----------------------------------------------------------------------------
    public double getxMinValue() {
        return xMinValue;
    }
    public void setxMinValue(double xMinValue) {
        this.xMinValue = xMinValue;
    }
    public double getxMaxValue() {
        return xMaxValue;
    }
    public void setxMaxValue(double xMaxValue) {
        this.xMaxValue = xMaxValue;
    }
    public double getaValue() {
        return aValue;
    }
    public void setaValue(double aValue) {
        this.aValue = aValue;
    }
    public double getuValue() {
        return uValue;
    }
    public void setuValue(double uValue) {
        this.uValue = uValue;
    }
    public double getvValue() {
        return vValue;
    }
    public void setvValue(double vValue) {
        this.vValue = vValue;
    }
    //-----------------------------------------------------------------------------

}
