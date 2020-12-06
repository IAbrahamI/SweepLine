package controller;

import model.Dot;
import java.util.ArrayList;
import java.util.Random;

public class Calculation {

    Random rd = new Random();
    //---------------------------------------------------------------------
    public int createRandomXDot(int maxX){
        int rdValue = rd.nextInt(maxX-20);
        return rdValue;
    }
    public int createRandomYDot(int maxY){
        int rdValue = rd.nextInt(maxY-20);
        return rdValue;
    }
    //---------------------------------------------------------------------

    //---------------------------------------------------------------------
}
