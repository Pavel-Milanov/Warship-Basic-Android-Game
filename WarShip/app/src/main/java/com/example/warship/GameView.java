package com.example.warship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.VelocityTracker;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable{

    public static final int MAX_STARS = 350;
    boolean isAlive;
    PlayerShip player;

    Paint paint;
    SurfaceHolder surfaceHolder;
    Canvas canvas;
    Thread gameThread;

    int screenSizeX;
    int screenSizeY;

    ArrayList<Star> stars = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();

    int score = 0;

    public GameView(Context context, int screenSizeX, int screenSizeY) {
        super(context);
        this.screenSizeX = screenSizeX;
        this.screenSizeY = screenSizeY;

        player = new PlayerShip(context,screenSizeX,screenSizeY);
        isAlive = true;

        enemies.add(new Enemy(context,screenSizeX,screenSizeY));

        for (int i = 0; i < MAX_STARS; i++) {
            stars.add(new Star(screenSizeX,screenSizeY));
        }

        paint = new Paint();
        surfaceHolder = getHolder();

        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {
        while (isAlive){
            draw();
            update();

            score++;

            if (score % 750 == 0){
                enemies.add(new Enemy(getContext(),screenSizeX,screenSizeY));
            }

            try {
                gameThread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        for (Enemy enemy : enemies) {
            enemy.update();

            if (Rect.intersects(enemy.detectCollision,player.detectCollision)){
                isAlive = false;
            }
        }

        for (Star star : stars) {
            star.update();
        }
    }

    private void draw() {

        if (surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.DKGRAY);

            paint.setColor(Color.WHITE);

            for (Star star : stars) {
                canvas.drawPoint(star.x,star.y,paint);
            }

            for (Enemy enemy : enemies) {
                canvas.drawBitmap(enemy.image,enemy.x,enemy.y,paint);
            }

            canvas.drawBitmap(player.image,player.x,player.y,paint);

            paint.setTextSize(40);
            canvas.drawText(score + "",canvas.getWidth() / 2,250,paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    VelocityTracker velocityTracker;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionIndex();
        int pointer = event.getPointerId(action);

        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                if (velocityTracker == null){
                    velocityTracker = VelocityTracker.obtain();
                }else {
                    velocityTracker.clear();
                }

                velocityTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                velocityTracker.addMovement(event);
                velocityTracker.computeCurrentVelocity(1000);

                if (velocityTracker.getXVelocity() > 0){
                    player.update(10);
                }else {
                    player.update(-10);
                }

                break;
        }
        return true;
    }
}
