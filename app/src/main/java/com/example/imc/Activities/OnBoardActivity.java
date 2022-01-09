package com.example.imc.Activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.imc.R;

import org.w3c.dom.Text;

public class OnBoardActivity extends AppCompatActivity {

    private ViewPager mSliderViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDots;
    private SliderAdapter sliderAdapter;

    private Button mNextButton,mPreviousButton,mFinishButton;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        mSliderViewPager = (ViewPager) findViewById(R.id.SliderViewPager);
        mDotLayout =(LinearLayout) findViewById(R.id.dotsLayout);

        mNextButton=(Button) findViewById(R.id.nxtButton);
        mFinishButton=(Button) findViewById(R.id.nxtButton2);
        mPreviousButton=(Button) findViewById(R.id.previousBtn);

        sliderAdapter =new SliderAdapter(this);
        mSliderViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);

        mSliderViewPager.addOnPageChangeListener(viewListener);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mSliderViewPager.setCurrentItem(mCurrentPage + 1);

            }
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSliderViewPager.setCurrentItem(mCurrentPage-1);
            }
        });
        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new
                        Intent(OnBoardActivity.this,Home.class);
                startActivity(reg);

            }
        });
    }

    public void addDotsIndicator(int position){
        mDots =new TextView[2];
        mDotLayout.removeAllViews();
        for (int i=0;i<mDots.length;i++)
        {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.sss));
            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length>0){

            mDots[position].setTextColor(getResources().getColor(R.color.AppTheme2));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            if(i==mSliderViewPager.getAdapter().getCount()-1){
//                Intent reg = new
//                        Intent(OnBoardActivity.this,Home.class);
//                startActivity(reg);

                mFinishButton.setVisibility(View.VISIBLE);
                mNextButton.setVisibility(View.INVISIBLE);

            }



            addDotsIndicator(i);
            mCurrentPage= i;
            if(i==0){
                mNextButton.setEnabled(true);
                mPreviousButton.setEnabled(false);
                mPreviousButton.setVisibility(View.INVISIBLE);
                mNextButton.setVisibility(View.VISIBLE);
                mFinishButton.setVisibility(View.INVISIBLE);

                mNextButton.setText("التالى");
                mPreviousButton.setText("");

            }
            else if(i == mDots.length-1){

                mNextButton.setEnabled(true);
                mPreviousButton.setEnabled(true);
                mPreviousButton.setVisibility(View.VISIBLE);

                mNextButton.setText("انتهاء");
                mPreviousButton.setText("رجوع");

            }
            else{

                mNextButton.setEnabled(true);
                mPreviousButton.setEnabled(true);
                mPreviousButton.setVisibility(View.VISIBLE);

                mFinishButton.setVisibility(View.INVISIBLE);
                mNextButton.setText("التالى");
                mPreviousButton.setText("رجوع");

            }

        }

        @Override
        public void onPageScrollStateChanged(int i)
        {


        }
    };




}
