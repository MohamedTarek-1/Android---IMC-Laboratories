package com.example.imc.Activities;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.imc.R;

public class SliderAdapter extends PagerAdapter{


    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter (Context context){

        this.context =context;
    }

    public int[] slide_images ={

            R.drawable.imclogo1,
            R.drawable.imclogo1,
            R.drawable.whiteimg
    };

    public String[] slide_headings={

            "معامل المركز الطبي العالمي",
            "",

    };

    public String[] slide_descs={

            "أهلآ بكم في معامل المركز الطبي العالمي حيث يمكنك الإطلاع علي نتائج تحاليلك و التقارير الخاصة بك"
            ,"لمعرفة نتائج تحاليلك يجب عليك الآتي :- \n" +
            "1-أن تمتلك رقم طبي مسجل لدي المركز\n" +
            "2-أن تتواصل مع المركز لتسجيل الملف الخاص بك \n حتي يمكنك رؤية تحاليلك على البرنامج من خلال رقمك القومي و رقمك الطبي"

    };






    @Override
    public int getCount() {
            return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o)
    {
        return view == (RelativeLayout) o;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
         View view =layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewSlide);
        TextView textView = (TextView) view.findViewById(R.id.textViewHeader);
        TextView textView2 = (TextView) view.findViewById(R.id.textViewDesc);

        imageView.setImageResource(slide_images[position]);
        textView.setText(slide_headings[position]);
        textView2.setText(slide_descs[position]);

        container.addView(view);

        return  view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((RelativeLayout)object);
    }

}
