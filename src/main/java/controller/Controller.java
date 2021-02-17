package controller;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.Arc;
import model.Dot;
import model.Parabola;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Controller{
    @FXML
    private Button button;
    @FXML
    private Canvas canvas;
    @FXML
    private TextField points;
    private GraphicsContext gc;
    private int minX = 0;
    private int maxX = 450;
    private int minY = 0;
    private int maxY = 500;
    // y-value for sweepline
    private int valueForLine = 0;
    private boolean isStopped = false;
    private ArrayList<Dot> dots = new ArrayList<Dot>();
    private ArrayList<Arc> arcs = new ArrayList<Arc>();
    private ArrayList<Dot> voronoiDots = new ArrayList<Dot>();
    private ArrayList<Parabola> parabolas = new ArrayList<Parabola>();
    //---------------------------------------------------------------------
    @FXML
    void startAction(ActionEvent event) {
        if (!points.getText().matches("([2-9]|1[0-9]|20)")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler bei der Eingabe");
            alert.setContentText("Bitte gib eine Zahl zwischen 2 - 20 ein");
            alert.showAndWait();
            points.clear();
        } else {
            Calculation c = new Calculation();
            this.drawVoronoi(c);
        }
    }
    //---------------------------------------------------------------------
    @FXML
    void endAction(ActionEvent event) {
        gc = canvas.getGraphicsContext2D();
        gc.fillRect(0,0,maxX,maxY);
        this.voronoiDots.clear();
        this.parabolas.clear();
        this.arcs.clear();
        this.dots.clear();
        this.valueForLine=0;
        this.isStopped=true;
    }
    //---------------------------------------------------------------------
    // Hauptprogramm
    // changed by trommsdorff
    //---------------------------------------------------------------------

    public void drawVoronoi(Calculation c) {
        gc = canvas.getGraphicsContext2D();
        createDots(c);
        final AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long nanotimes) {
                valueForLine++;
                refreshCanvas();
                drawDots();
                drawSweepLine();
                new_arc_event(); // neu by trommsdorff
                loss_arc_event(); // neu by trommsdorff
                sortArcs(); //Unklar ob notwendig bei dieser Implementierung ?
                update_arcs(); // neu by trommsdorff
                drawarcs();
                drawLines();
            }
        };
        timer.start();
        if(this.isStopped==true){
            timer.stop();
        }
    }
    //---------------------------------------------------------------------
    // Unterprogramme
    //---------------------------------------------------------------------

    // entfernt einen arc mit "länge" kleiner 1
    public void loss_arc_event(){
        Arc to_remove=new Arc(0,0,0,0,0);
        for (Arc ar:arcs) {
            double x_min=ar.getxMinValue();
            double x_max=ar.getxMaxValue();
            //der Abstand zwischen xMin und xMax wird sehr klein. Arc wird gelöscht
            if (x_max-x_min < 1) {
                to_remove=ar;
            }
        }
        arcs.remove(to_remove);
    }
    // fügt einen neuen Arc ein. Ein Arc wird "gesplittet": es entstehen drei neue Arcs
    public void new_arc_event(){
        int currentAmountOfArcs=arcs.size();
        Arc changearc=new Arc(0,0,0,0,0);
        Dot dot_left=new Dot(0,0);
        Dot dot_right=new Dot(0,0);
        // Check for a new Event
        for (Dot d: dots){
            //Neuer Punkt bei der sweepline? Check ob der dot_y Wert plus/minus 1 von der sweepline entfernt ist
            if (d.getY()<valueForLine+1 && d.getY()>valueForLine-1){
                Parabola par_new = Parabola.calculateParabola(d,valueForLine);
                parabolas.add(par_new); // kann wahrscheinlich gelöscht werden
                //der neue Punkt erzeugt einen neuen Arc. Die grenzen zunächst minX und maxX
                Arc arc_new = new Arc(minX,maxX,par_new.getA(),par_new.getU(),par_new.getV());

                //Sonderfall, wenn noch kein Arc vorhanden ist
                if (currentAmountOfArcs<1){
                    // erster Arc
                    arcs.add(arc_new);
                } else {
                    double xtest=arc_new.getuValue();  //x Koord vom neuen Punkt
                    //suche den arc den es zu bearbeiten gilt. Das ist der Arc in welchem xtest liegt
                    for (Arc ar:arcs){
                        if (ar.getxMinValue()<xtest && ar.getxMaxValue()>xtest){
                            //dieser Arc muss verändert werden
                            changearc=ar;
                            dot_left=Arc.intersect_arcs_left(changearc,arc_new);
                            dot_right=Arc.intersect_arcs_right(changearc,arc_new);
                        }
                    }
                    //die dreien neuen Arcs werden jetzt festgelegt
                    double x_1=changearc.getxMinValue();
                    double x_2=dot_left.getX();
                    double x_3=dot_right.getX();
                    //if (x_2 > x_3){ x_3=x_2;x_2=dot_right.getX();} //wenn es nicht stimmen sollte
                    double x_4=changearc.getxMaxValue();
                    arcs.add(new Arc(x_1,x_2,changearc.getaValue(),changearc.getuValue(),changearc.getvValue()));
                    arcs.add(new Arc(x_2,x_3,arc_new.getaValue(),arc_new.getuValue(),arc_new.getvValue()));
                    arcs.add(new Arc(x_3,x_4,changearc.getaValue(),changearc.getuValue(),changearc.getvValue()));

                    //Add Voronoi Dots for line

                    arcs.remove(changearc);  // ist das nötig oder nicht glaube schon
                }
            }
        }
    }
    // die sweep line geht nach unten. a und v verändern sich. u bleibt. dafür neue Formeln von Trommsdorff
    public void update_arcs(){
        int k=arcs.size();
        for (Arc ar:arcs) {
            double a_old=ar.getaValue();
            if (a_old != 0) {
                ar.setaValue(1/(1/(a_old)-1));
            }
            ar.setvValue(ar.getvValue()+0.5);
        }
        //Schnittpunkte müssen auch angepasst werden
        if (k>1){
            for (int j=0;j<k-1;j++){
                Arc eins=arcs.get(j);
                Arc zwei=arcs.get(j+1);
                Dot dot_left=Arc.intersect_arcs_left(eins,zwei);
                //Dot dot_right=Arc.intersect_arcs_right(eins,zwei);  //nicht nötig?
                double x_new=dot_left.getX();
                //double y_new=dot_left.getX();
                arcs.get(j).setxMaxValue(x_new);
                arcs.get(j+1).setxMinValue(x_new);
            }
        }
    }


//---------------------------------------------------------------------
    public void refreshCanvas() {
        gc.setFill(Color.WHITE);
        gc.fillRect(minX, minY, maxX, maxY);
    }
    //---------------------------------------------------------------------
    public void drawarcs(){
        gc.setStroke(Color.RED);
        for (Arc ar:arcs){
            double a = ar.getaValue();
            double u = ar.getuValue();
            double v = ar.getvValue();
            double x_min=ar.getxMinValue();
            double x_max=ar.getxMaxValue();

            for(double i=x_min;i<=x_max;i++){
                gc.strokeOval(i,a*Math.pow((i-u),2)+v,1,1);
            }
        }
    }
    //---------------------------------------------------------------------
    public void drawSweepLine() {
        gc.setStroke(Color.RED);
        gc.strokeLine(minX, valueForLine, maxX, valueForLine);
    }
    //---------------------------------------------------------------------
    public void drawDots() {
        for (Dot d : dots) {
            gc.setStroke(Color.BLUE);
            gc.strokeOval(d.getX(), d.getY(), 3, 3);
        }
    }
    //---------------------------------------------------------------------
    public void drawLines(){
        for(Dot d: voronoiDots){
            gc.setStroke(Color.BLACK);
            gc.strokeOval(d.getX(),d.getY(),6,6);
        }
    }
    //---------------------------------------------------------------------
    // Storage and Creation Methods
    //---------------------------------------------------------------------

    // new tf Sort arcs-----------------------------------------------------
    public void sortArcs(){
        Comparator<Arc> arcComparator = Comparator.comparing(Arc::getxMinValue);
        Collections.sort(arcs,arcComparator);
    }
    public void addDot(int x, int y, double minX, double maxX) {
        dots.add(new Dot(x, y, minX, maxX));
    }
    public void addParabola(double a, double u, double v){
        parabolas.add(new Parabola(a,u,v));
    }
    public void addVoronoiDot(double x, double y){
        voronoiDots.add(new Dot(x,y));
    }
    //**_New
    public void addArc(double minX, double maxX,double a, double u, double v){
        arcs.add(new Arc(minX,maxX,a,u,v));
    }
    //---------------------------------------------------------------------
    public void sortDots(){
        Comparator<Dot> dotComparator = Comparator.comparing(Dot::getY); //tf_new xy vertauscht
        Collections.sort(dots,dotComparator);
    }

    //---------------------------------------------------------------------
    public double getMaxYValue(){
        double maxValue = 0;
        for(int i=0;i< dots.size();i++){
            if(dots.get(i).getY()>maxValue){
                maxValue = dots.get(i).getY();
            }
        }
        return maxValue;
    }
    //---------------------------------------------------------------------
    public void createDots(Calculation c) {
        int amountOfDots = Integer.parseInt(points.getText());
        for (int i = 0; i < amountOfDots; i++) {
            int xValue = c.createRandomXDot(maxX);
            int yValue = c.createRandomYDot(maxY);
            this.addDot(xValue, yValue,minX,maxX);
        }
        sortDots();
    }
    //---------------------------------------------------------------------
    public void storeParabola(double a, double u, double v){
        this.parabolas.get(parabolas.size()-1).setA(a);
        this.parabolas.get(parabolas.size()-1).setU(u);
        this.parabolas.get(parabolas.size()-1).setV(v);
    }
}


