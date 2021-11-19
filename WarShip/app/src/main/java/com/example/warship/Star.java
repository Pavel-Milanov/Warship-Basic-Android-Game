package com.example.warship;

import java.util.Random;

public class Star {
    int x;
    int y;
    int maxX;
    int maxY;
    int speed;

    Random random = new Random();

    public Star(int screenX, int screenY) {
        maxX = screenX;
        maxY = screenY;

        x = random.nextInt(maxX);
        y = random.nextInt(maxY);

        speed = random.nextInt(15) + 1;
    }

    public void update(){
        y += speed;
        if (y > maxY){
            y = - 10;
            x = random.nextInt(maxX);
            speed = random.nextInt(15) + 1;
        }
    }
}
