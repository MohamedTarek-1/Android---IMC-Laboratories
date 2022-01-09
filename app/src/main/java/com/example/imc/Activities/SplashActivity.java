package com.example.imc.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imc.R;

public class SplashActivity extends AppCompatActivity {

    ImageView Logo,Logojci;
    TextView text1,text2;
    private static  int splashtimeout = 4000;
    SharedPreferences mPrefs;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Logo =(ImageView) findViewById(R.id.logoIMG);
      //  Logojci =(ImageView) findViewById(R.id.imageView2);
      ///  text1 = findViewById(R.id.textView13);
      //  text2 = findViewById(R.id.textView14);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

             //   Intent i = new Intent(SplashActivity.this, Home.class);
               // startActivity(i);
                startclasS();
                    finish();
            }
        },splashtimeout);


        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mysplashanimation);
        Logo.startAnimation(myanim);







        //Animation myanim2 = AnimationUtils.loadAnimation(this,R.anim.mysplashanimation);
        //Logojci.startAnimation(myanim2);

        //Logojci.startAnimation(myanim2);
      //  text1.startAnimation(myanim);
       // text2.startAnimation(myanim);
    }


    public  void  startclasS(){
        mPrefs = getSharedPreferences("sharedPref", MODE_PRIVATE);
        String Med_N_Holder = mPrefs.getString("Medical_Number","");

        if(Med_N_Holder==""){
             startActivity(new Intent(getApplicationContext(), OnBoardActivity.class));



        }
        else {
           startActivity(new Intent(getApplicationContext(), Home.class));

        }

    }
}
