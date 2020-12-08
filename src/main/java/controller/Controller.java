package controller;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
    private int valueForLine = 0;
    private double xFinal = 0;
    private double yFinal = 0;
    private double x = 0;
    private double y = 0;
    private ArrayList<Dot> dots = new ArrayList<Dot>();
    private ArrayList<Arc> arcs = new ArrayList<Arc>();
    private ArrayList<Dot> voronoiDots = new ArrayList<Dot>();
    private ArrayList<Parabola> parabolas = new ArrayList<Parabola>();
    //---------------------------------------------------------------------
    @FXML
    void startAction(ActionEvent event) {
        Calculation c = new Calculation();
        this.drawVoronoi(c);
    }
    //---------------------------------------------------------------------
    public void drawVoronoi(Calculation c) {
        gc = canvas.getGraphicsContext2D();
        createDots(c);
        final AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long nanotime) {

                valueForLine++;
                refreshCanvas();
                drawDots();
                drawSweepLine();
                scannForSweepLineDotCollision();
                drawLines();
                //drawArcs();
            }
        };
        timer.start();
    }
    //---------------------------------------------------------------------
    public void refreshCanvas() {
        gc.setFill(Color.WHITE);
        gc.fillRect(minX, minY, maxX, maxY);
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
            gc.strokeOval(d.getX(), d.getY(), 6, 6);
        }
    }
    //---------------------------------------------------------------------
    public void drawArcs(){
        for(Arc a: arcs){
            for (double i=a.getxMinValue();i<=a.getxMaxValue();i++){
                double y = a.getaValue()*((i-a.getuValue())*(i-a.getuValue()))+a.getvValue();
                double x = i;
                gc.setStroke(Color.GREEN);
                gc.strokeOval(x,y,1,1);
            }
        }
    }
    //---------------------------------------------------------------------
    public void drawLines(){
        for(Dot d: voronoiDots){
            gc.setStroke(Color.BLACK);
            gc.strokeOval(d.getX(),d.getY(),2,2);
        }
    }
    //---------------------------------------------------------------------
    // Storage and Creation Methods
    //---------------------------------------------------------------------
    public void addDot(int x, int y, double minX, double maxX) {
        dots.add(new Dot(x, y, minX, maxX));
    }
    public void addParabola(double a, double u, double v){
        parabolas.add(new Parabola(a,u,v));
    }
    public void addArc(double xMin, double xMax, double a, double u, double v){
        arcs.add(new Arc(xMin,xMax,a,u,v));
    }
    public void addVoronoiDot(double x, double y){
        voronoiDots.add(new Dot(x,y));
    }
    //---------------------------------------------------------------------
    public void sortDots(){
        Comparator<Dot> dotComparator = Comparator.comparing(Dot::getY).thenComparing(Dot::getX);
        Collections.sort(dots,dotComparator);
    }
    //---------------------------------------------------------------------
    public double getMaxYValue(){
        double maxValue = 0;
        for(int i=0;i< voronoiDots.size();i++){
            if(voronoiDots.get(i).getY()>maxValue){
                maxValue = voronoiDots.get(i).getY();
            }
        }
        return maxValue;
    }
    //---------------------------------------------------------------------
    public void createDots(Calculation c) {
        if (!points.getText().matches("[0-9]*")) {
        } else {
            int amountOfDots = Integer.parseInt(points.getText());
            for (int i = 0; i < amountOfDots; i++) {
                int xValue = c.createRandomXDot(maxX);
                int yValue = c.createRandomYDot(maxY);
                this.addDot(xValue, yValue,minX,maxX);
            }
        }
        sortDots();
    }
    //---------------------------------------------------------------------
    public void storeParabola(double a, double u, double v){
        this.parabolas.get(parabolas.size()-1).setA(a);
        this.parabolas.get(parabolas.size()-1).setU(u);
        this.parabolas.get(parabolas.size()-1).setV(v);
    }
    public void storeArc(double a, double u, double v){
        this.arcs.get(arcs.size()-1).setaValue(a);
        this.arcs.get(arcs.size()-1).setuValue(u);
        this.arcs.get(arcs.size()-1).setvValue(v);
    }
    //---------------------------------------------------------------------
    // Implementations
    //---------------------------------------------------------------------
    public void scannForSweepLineDotCollision(){
        for (Dot d: dots){
            addParabola(0,0,0);
            if(d.getY()==valueForLine){
                addArc(d.getxMin(),d.getxMax(),0,0,0);
            }
            calculateParabola(d);
            if(parabolas.size()>1){
                compareMultipleParabolas(d);
            }
        }
        System.out.println(voronoiDots.size());
    }
    //---------------------------------------------------------------------
    public void calculateParabola(Dot dot){
        for(double i=minX;i<maxX;i++){
            double a = 1 / (2 * (dot.getY() - valueForLine));
            double u = dot.getX();
            double v = 0.5 * (dot.getY() + valueForLine);

            double pY = a*((i-u)*(i-u))+v;
            double pX = i;
            if(pY<=valueForLine){
                gc.setStroke(Color.BLUE);
                gc.strokeOval(pX,pY,1,1);
            }
            storeParabola(a,u,v);
        }
    }
    //---------------------------------------------------------------------
    public void compareMultipleParabolas(Dot dot1){
        ArrayList<Dot> list = new ArrayList<Dot>();
        for (Dot dot2: dots){
            if(dot2.getY()<valueForLine){
                double e = dot1.getX()*(dot2.getY()-valueForLine) - dot2.getX()*(dot1.getY()-valueForLine);
                double a = Math.sqrt((dot1.getY()-valueForLine)*(dot2.getY()-valueForLine));
                double d = Math.sqrt((dot1.getX()-dot2.getX())*(dot1.getX()-dot2.getX()) + (dot1.getY()-dot2.getY())*(dot1.getY()-dot2.getY()));
                double b = dot2.getY()-dot1.getY();

                x = (e+(a*d))/b;
                y = parabolas.get(parabolas.size()-1).getA()*((x-dot1.getX())*(x-dot1.getX()))+0.5*(dot1.getY()+valueForLine);

                calculateDots(dot1, x, y);
                // Prove if there are two X Values and add them to the list
            }
            if (x > dot1.getX()) {
                xFinal = x;
            } else if(x == dot1.getX()) {
                xFinal = x;
            } else if(x < dot1.getX()) {
                xFinal = dot1.getX();
            }

            if (y > dot1.getY()) {
                yFinal = y;
            } else if(y == dot1.getY()) {
                yFinal = y;
            } else if(y < dot1.getY()) {
                yFinal = dot1.getY();
            }

            list.add(new Dot(xFinal, yFinal));
        }
        //Prove wich Y Value ist max and store it

        list.clear();
    }
    //---------------------------------------------------------------------
    public void calculateDots(Dot dot, double x, double y){
        if(valueForLine>y && y>minY && y<=maxY && x>minX && x<=maxX){
            addVoronoiDot(x,y);
            gc.setStroke(Color.GREEN);
            gc.strokeOval(x,y,6,6);
        }
    }
    //---------------------------------------------------------------------
}