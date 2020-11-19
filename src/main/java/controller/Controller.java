package controller;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import model.Arc;
import model.Dot;

import java.util.ArrayList;

public class Controller {

    @FXML
    private Button button;

    @FXML
    private Canvas canvas;

    private int anzahlParaboles = 8;
    double minusXPoint = 0;
    double plusXPoint = 450;
    private double xLine1, yLine1, xLine2, yLine2;
    private GraphicsContext context;
    private ArrayList<Dot> dotsWithCollisions = new ArrayList<Dot>();

    //---------------------------------------------------------------------
    @FXML
    void startAction(ActionEvent event) {
        Calculation c = new Calculation();
        c.addEmptyParabolas();
        c.addEmptyArcs();
        this.strokeSweepLine(c);

    }

    //---------------------------------------------------------------------
    public void strokeSweepLine(final Calculation calculation) {
        context = canvas.getGraphicsContext2D();
        xLine1 = minusXPoint;
        yLine1 = 0;
        xLine2 = plusXPoint;
        yLine2 = 0;
        calculation.createRandomDots();
        final AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long nanotime) {

                yLine1++;
                yLine2++;
                context.setFill(Color.WHITE);
                context.fillRect(minusXPoint, 0, plusXPoint, 550);

                strokeDots(calculation);

                for (Dot d : calculation.getDots()) {
                    calculation.scannForLineDotCollision(yLine1, d.getX(), d.getY());
                }

                calculation.startCalculation(yLine1);

                drawArc(calculation);
                strokeDotLines(calculation);

                context.setStroke(Color.RED);
                context.strokeLine(xLine1, yLine1, xLine2, yLine2);
                if (yLine1 == 1200) {
                    this.stop();
                }
            }
        };
        timer.start();
    }
    //---------------------------------------------------------------------
    public void strokeDots(Calculation calculation) {
        for (Dot d : calculation.getDots()) {
            context.setStroke(Color.GREEN);
            context.setFill(Color.GREEN);
            context.strokeOval(d.getX(), d.getY(), 6, 6);
            context.fillOval(d.getX(), d.getY(), 6, 6);
        }
    }

    //---------------------------------------------------------------------
    public void strokeDotLines(Calculation calculation) {
        Calculation c = new Calculation();
        for (Dot d : calculation.getVoronoiDots()) {
            context.setStroke(Color.BLACK);
            context.setFill(Color.BLACK);
            context.strokeOval(d.getX(), d.getY(), 2, 2);
            context.fillOval(d.getX(), d.getY(), 2, 2);
        }
    }
    //---------------------------------------------------------------------
    public void drawArc(Calculation calculation) {
        for(Arc a : calculation.getArcs()){
            double aArc = a.getaValue();
            double uArc = a.getuValue();
            double vArc = a.getvValue();
            for(double i = a.getxMinValue();i<=a.getxMaxValue();i++){
                double pointY = aArc * ((i-uArc)*(i-uArc))+vArc;
                double pointX = i;
                
                context.strokeOval(pointX, pointY, 1, 1);
                context.fillOval(pointX, pointY, 1, 1);
            }
        }
    }
    //---------------------------------------------------------------------
}