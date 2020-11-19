package controller;

import model.Arc;
import model.Dot;
import model.Parabole;

import java.util.ArrayList;
import java.util.Random;

public class Calculation {

    private Random rd = new Random();
    private ArrayList<Arc> arcs = new ArrayList<Arc>();
    private ArrayList<Dot> activatedDots = new ArrayList<Dot>();
    private ArrayList<Dot> voronoiDots = new ArrayList<Dot>();
    private ArrayList<Dot> dots = new ArrayList<Dot>();
    private ArrayList<Parabole> paraboles = new ArrayList<Parabole>();

    //--------------------------------------------------------------------------------------
    public ArrayList<Arc> getArcs() {
        return arcs;
    }
    public void setArcs(ArrayList<Arc> arcs) {
        this.arcs = arcs;
    }
    public ArrayList<Dot> getActivatedDots() {
        return activatedDots;
    }
    public void setActivatedDots(ArrayList<Dot> activatedDots) {
        this.activatedDots = activatedDots;
    }
    public ArrayList<Dot> getVoronoiDots() {
        return voronoiDots;
    }
    public void setVoronoiDots(ArrayList<Dot> voronoiDots) {
        this.voronoiDots = voronoiDots;
    }
    public ArrayList<Dot> getDots() {
        return dots;
    }
    public void setDots(ArrayList<Dot> dots) {
        this.dots = dots;
    }
    public ArrayList<Parabole> getParaboles() {
        return paraboles;
    }
    public void setParaboles(ArrayList<Parabole> paraboles) {
        this.paraboles = paraboles;
    }
    //--------------------------------------------------------------------------------------
    public void addDot(double x, double y) {
        dots.add(new Dot(x, y));
    }
    public void addCollisionDot(double x, double y) {
        dots.add(new Dot(x, y));
    }
    public void addActivatedDot(double x, double y, double xMin, double xMax) {
        activatedDots.add(new Dot(x, y, xMin, xMax));
    }
    public void addDotForLine(double x, double y) {
        voronoiDots.add(new Dot(x, y));
    }
    public void addParabola(double a, double u, double v, double incrementedValue, double bPointX, double bPointY) {
        paraboles.add(new Parabole(a, u, v, incrementedValue, bPointX, bPointY));
    }
    public void addArc(double xMinValue, double xMaxValue, double aValue, double uValue, double vValue){
        arcs.add(new Arc(xMinValue,xMaxValue,aValue,uValue,vValue));
    }
    //--------------------------------------------------------------------------------------
    public void addEmptyParabolas() {
        for (int i = 0; i < 8; i++) {
            this.addParabola(0, 0, 0, 0, 0, 0);
        }
    }
    public void addEmptyArcs(){
        for(int i = 0;i<8;i++){
            this.addArc(0,450,0,0,0);
        }
    }
    //--------------------------------------------------------------------------------------
    public void createRandomDots() {
        for (int i = 1; i <= 8; i++) {
            int randomXValue = rd.nextInt(430);
            int randomYValue = rd.nextInt(530);
            this.addDot(randomXValue, randomYValue);
        }
    }
    //--------------------------------------------------------------------------------------
    public void startCalculation(double yLine1) {
        int parabolaID = 0;
        for (Dot d : activatedDots) {
            calculateAndStoreParabola(d, parabolaID, yLine1);
            parabolaID++;
        }
    }
    //--------------------------------------------------------------------------------------
    public void calculateAndStoreParabola(Dot d, int parabolaID, double yLine1) {
        double yLine = yLine1;
        for (double i = d.getxMin(); i < d.getxMax(); i++) {

            double a = 1 / (2 * (d.getY() - yLine));
            double u = d.getX();
            double v = 0.5 * (d.getY() + yLine);

            this.paraboles.get(parabolaID).setA(a);
            this.paraboles.get(parabolaID).setU(u);
            this.paraboles.get(parabolaID).setV(v);
            this.paraboles.get(parabolaID).setIncrementedValue(i);
            this.paraboles.get(parabolaID).setbPointX(d.getX());
            this.paraboles.get(parabolaID).setbPointY(d.getY());
        }
        scannForParaboleCollision(d, yLine, this.paraboles.get(parabolaID).getbPointX(), this.paraboles.get(parabolaID).getbPointY(), this.paraboles.get(parabolaID).getA(), this.paraboles.get(parabolaID).getU(), this.paraboles.get(parabolaID).getV(), parabolaID);
    }
    //--------------------------------------------------------------------------------------
    public void scannForParaboleCollision(Dot dot, double yLine1, double bPointX, double bPointY, double aParabola, double uParabola, double vParabola, int parabolaID) {
        double yLine = yLine1;
        for (Parabole p : paraboles) {

            double e = bPointX * (p.getbPointY() - yLine) - p.getbPointX() * (bPointY - yLine);
            double a = Math.sqrt((bPointY - yLine) * (p.getbPointY() - yLine));
            double d = Math.sqrt(((bPointX - p.getbPointX()) * (bPointX - p.getbPointX()) + (bPointY - p.getbPointY()) * (bPointY - p.getbPointY())));
            double b = p.getbPointY() - bPointY;
            double a1 = aParabola;
            double a2 = p.getA();

            double resultForX = (e + (a * d)) / b;
            double resultForY = a1 * ((resultForX - bPointX) * (resultForX - bPointX)) + 0.5 * (bPointY + yLine);
            this.scannForMultipleDotsCollision(resultForX,resultForY);

            if (resultForX <= 450 && resultForX >= 0 && resultForY>=0 && resultForY<=600) {
                if (p.getbPointY() == 0 && bPointY > 0) {
                    this.addDotForLine(resultForX, 0);
                }else if(p.getbPointX() > bPointX){
                    this.addArc(resultForX,dot.getxMax(),aParabola,uParabola,vParabola);
//                    this.arcs.get(parabolaID).setxMinValue(resultForX);
                } else if(p.getbPointX() < bPointX){
                    this.addArc(dot.getxMin(),resultForX,aParabola,uParabola,vParabola);
//                    this.arcs.get(parabolaID).setxMaxValue(resultForX);
                }
            }
        }
    }
    //--------------------------------------------------------------------------------------
    public void scannForMultipleDotsCollision(double resultForX, double resultForY){
        for (Dot dotVoronoi : voronoiDots) {
            if (dotVoronoi.getX() == resultForX || dotVoronoi.getY() == resultForY) {
            } else if (dotVoronoi.getX() != resultForX && dotVoronoi.getY() != resultForY) {
                this.addDotForLine(resultForX, resultForY);
                break;
            }
        }
    }
    //--------------------------------------------------------------------------------------
    public void scannForLineDotCollision(double yLine1, double x, double y) {
        double yLine = yLine1;
        if (yLine == y) {
            this.addActivatedDot(x, y, 0, 450);
        } else {
        }
    }
    //--------------------------------------------------------------------------------------
}