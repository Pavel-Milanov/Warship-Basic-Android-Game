package com.example.warship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class PlayerShip {

    Context context;
    int x;
    int y;
    int maxX;
    int maxY;
    Bitmap image;
    Rect detectCollision;

    public PlayerShip(Context context, int sizeScreenX, int sizeScreenY) {
        this.context = context;
        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_ship);

        x = (sizeScreenX / 2) - (image.getWidth() / 2);
        y = sizeScreenY - image.getHeight() - 200;

        this.maxX = sizeScreenX - image.getWidth();
        this.maxY = sizeScreenY - image.getHeight();

        detectCollision = new Rect(x, y, x + image.getWidth(), y + image.getHeight());
    }

    public void update(int move){
        x += move;

        if (x < 0){
            x = 0;
        }else if (x > maxX){
            x = maxX;
        }

        detectCollision.left = x;
        detectCollision.right = x + image.getWidth();
    }


}
