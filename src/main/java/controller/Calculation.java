package controller;

import java.util.Random;

public class Calculation {

    private Random rd = new Random();
    //--------------------------------------------------------------------------------------
    public void createRandomDots(int amountOfDots, int maxX, int maxY){
        for(int i = 0; i < amountOfDots; i++){
            int rdValueForX = rd.nextInt(maxX-20);
            int rdValueForY = rd.nextInt(maxY-20);
        }
    }
    //--------------------------------------------------------------------------------------
}