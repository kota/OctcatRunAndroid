package com.example.fujiwarakota.octcatrun;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends ActionBarActivity {
    int octX;
    int octY;
    int octVelocityY = 0;
    int enemyX;
    boolean gameOver = true;

    static int octSpeed = 15;
    static int enemySpeed = 15;

    int enemyVelocityX = -enemySpeed;

    Timer mTimer = null;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timer timer = new Timer(true);
        timer.schedule( new TimerTask() {
            @Override
            public void run() {
                mHandler.post( new Runnable() {
                    @Override
                    public void run() {
                        if (gameOver){
                            return;
                        }
                        ImageView img = (ImageView)findViewById(R.id.octcat);
                        img.setTranslationY(img.getTranslationY() + octVelocityY);
                        if (img.getTranslationY() < -350 && octVelocityY < 0){
                            octVelocityY = octSpeed;
                        } else if (img.getTranslationY() > 0) {
                            octVelocityY = 0;
                            img.setTranslationY(0);
                        }

                        ImageView img2 = (ImageView)findViewById(R.id.octcat2);
                        if (img2.getTranslationX() < -1000){
                            img2.setTranslationX(0);
                        } else {
                            img2.setTranslationX(img2.getTranslationX() + enemyVelocityX);
                        }

                        View rootView = findViewById(android.R.id.content);
                        int[] octXY = new int[2];
                        img.getLocationOnScreen(octXY);
                        int[] enemyXY = new int[2];
                        img2.getLocationOnScreen(enemyXY);
                        Log.d("oct", "X:" + octXY[0] + ",y:" + octXY[1] );
                        Log.d("enemy", "X:" + enemyXY[0] + ",y:" + enemyXY[1] );

                        if (octXY[0] + 60 >= enemyXY[0] && octXY[0] <= enemyXY[0] + 60){
                            if (octXY[1] <= enemyXY[1] && octXY[1] + 200 >= enemyXY[1]) {
                                octVelocityY = 0;
                                enemyVelocityX = 0;
                                Log.d("oct1", "game over");
                                gameOver = true;
                            }
                        }
                    }
                });
            }
        },0,33);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if(gameOver){
                    gameOver = false;
                    enemyVelocityX = -enemySpeed;
                    ImageView img = (ImageView)findViewById(R.id.octcat);
                    img.setTranslationY(0);
                    ImageView img2 = (ImageView)findViewById(R.id.octcat2);
                    img2.setTranslationX(0);
                } else {
                    ImageView img = (ImageView) findViewById(R.id.octcat);

                            Log.d("TouchEvent", "X:" + event.getX());
                            this.octVelocityY = -octSpeed;

                }
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
