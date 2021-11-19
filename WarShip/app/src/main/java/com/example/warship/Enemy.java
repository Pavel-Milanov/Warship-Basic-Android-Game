package com.example.warship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Enemy {

    public static final int starPosition = -50;
    Bitmap image;
    Context context;
    int x;
    int y;
    int maxX;
    int maxY;

    int speed = 10;

    Rect detectCollision;

    int[] skins = {R.drawable.enemy1,R.drawable.enemy2,R.drawable.enemy3 };
    Random random = new Random();

    public Enemy(Context context, int screenX, int screenY) {
        this.context = context;

        setImage(context);

        maxX = screenX - image.getWidth();
        maxY = screenY;

        y = starPosition;
        x = random.nextInt(maxX);

        detectCollision = new Rect(x,y,x+image.getWidth(), y + image.getHeight());
    }

    private void setImage(Context context) {
        image = BitmapFactory.decodeResource(context.getResources(), skins[random.nextInt(skins.length)]);
    }

    public void update(){
        y += speed;
        if (y > maxY){
            y = starPosition;
            x = random.nextInt(maxX);
            setImage(context);

            detectCollision.left = x;
            detectCollision.right = x + image.getWidth();
        }

        detectCollision.top = y;
        detectCollision.bottom = y + image.getHeight();
    }

}
