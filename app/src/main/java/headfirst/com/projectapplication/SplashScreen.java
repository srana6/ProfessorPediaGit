package headfirst.com.projectapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView im =(ImageView)findViewById(R.id.pplogo);
        TranslateAnimation moveLefttoRight = new TranslateAnimation(-4500, 0, 0, 0);
        moveLefttoRight.setDuration(3000);
        moveLefttoRight.setFillAfter(true);
        im.startAnimation(moveLefttoRight);
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashScreen.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}