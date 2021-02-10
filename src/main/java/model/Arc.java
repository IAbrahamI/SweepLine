package model;

public class   Arc implements Comparable<Arc> {

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
    // trommsdorff neu bestimmt die Schnittpunkte von Parabeln aus a1,u1,v1 und a2,u2,v2 Formeln mit wolfram alph gefuneden siehe bild
    public static Dot intersect_arcs_left(Arc arc1,Arc arc2){
        double a1=arc1.getaValue();
        double u1=arc1.getuValue();
        double v1=arc1.getvValue();
        double a2=arc2.getaValue();
        double u2=arc2.getuValue();
        double v2=arc2.getvValue();
        double delta_a=a1-a2;
        double delta_u=u1-u2;
        double delta_v=v1-v2;
        //evtl Fehler abfangen keine neg Wurzel
        double root= Math.sqrt(a1*a2*delta_u*delta_u-delta_v*delta_a);

        double x_left=((-1)*root+a1*u1-a2*u2)/delta_a;
        double y_left=a1*(x_left-u1)*(x_left-u1)+v1;
        return new Dot(x_left,y_left);
    }
    public static Dot intersect_arcs_right(Arc arc1,Arc arc2){
        double a1=arc1.getaValue();
        double u1=arc1.getuValue();
        double v1=arc1.getvValue();
        double a2=arc2.getaValue();
        double u2=arc2.getuValue();
        double v2=arc2.getvValue();
        double delta_a=a1-a2;
        double delta_u=u1-u2;
        double delta_v=v1-v2;
        //evtl Fehler abfangen keine neg Wurzel
        double root = Math.sqrt(a1*a2*delta_u*delta_u-delta_v*delta_a);

        double x_right=(root+a1*u1-a2*u2)/delta_a;
        double y_right=a1*(x_right-u1)*(x_right-u1)+v1;
        return new Dot(x_right,y_right);
    }


    public int compareTo(Arc arc) {
        return (int) (this.xMinValue-arc.getxMinValue());
    }
}


