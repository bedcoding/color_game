package io.jeux.ggavi;

import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class
Splash_Screen extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        ImageView Logo_View=findViewById(R.id.Walk_Away_Logo_Id);
        progressBar = findViewById(R.id.Progress_Bar_View_Id);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(new LightingColorFilter(0xFF000000, getResources().getColor(R.color.colorBlack)));
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
        Logo_View.clearAnimation();
        Logo_View.setAnimation(anim);
        Splash_Control();
    }


    private void Splash_Control()
    {
        // ------ Showing Logo For Some Time -------------
        Thread Control_Splash=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this)
                    {
                        wait(3500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(Splash_Screen.this,NUM0_GameStart.class));
             finish();}});
        Control_Splash.start();
    }
}
