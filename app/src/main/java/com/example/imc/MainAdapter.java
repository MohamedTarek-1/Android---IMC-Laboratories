package com.example.imc;

import android.animation.ObjectAnimator;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.imc.Activities.PDFview;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    List<ArrayList<String>> Tests;
    ArrayList<String> urlArray;
    ArrayList<String> nameArray;
    ArrayList<String> TestNameArray;
    ArrayList<String> DateTimeArray ;
    ArrayList<String> Patient_ID_Array;
    SparseBooleanArray expandState =new SparseBooleanArray();
    private  Context context;
    public static final String PASSED_NAME="com.example.imc.PASSED_NAME";
    String outputfilename;
    private long lDownloadID;
    String BaseDwldURL ="http://aselbehary-001-site1.ctempurl.com/Labs/Reader?FileName=", outputURLdwld;
    //constructor...



    public MainAdapter(List<ArrayList<String>> tests, ArrayList<String> nameArray,ArrayList<String> TestName,ArrayList<String> DateTimeArray)
    {
        this.context=context;
        Tests = tests;
       // this.urlArray = urlArray;
        this.nameArray = nameArray;
        this.TestNameArray = TestName;
        this.DateTimeArray = DateTimeArray;
    }

    public MainAdapter(List<ArrayList<String>> tests, ArrayList<String> nameArray) {
        this.context=context;
        Tests = tests;
        this.nameArray = nameArray;


    }



    @NonNull
    @Override
    //Initialize the viewholder
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row,viewGroup,false);

        return new ViewHolder(view);
    }


    /***************////////////////onBind Holds values to pass it to the viewholder///////////////////*******************

    /***************////////////////
    // This method is called for each ViewHolder to
    // bind it to the adapter
    // . This is where we will// pass our data to our ViewHolder///////////////////*******************

    @Override
    public void onBindViewHolder(@NonNull final MainAdapter.ViewHolder holder, final int i) {

       // holder.URLName.setText( urlArray.get(i));


        String s = nameArray.get(i);
        //String URLArrayPosition = urlArray.get(i);

         //holder.mFullname.setText( nameArray.get(i));
         holder.mTestName.setText( nameArray.get(i));
         //holder.mArrayURL.setText(urlArray.get(i)) ;
         holder.mDateTime.setText(DateTimeArray.get(i)) ;
         holder.mFullname.setText(TestNameArray.get(i)) ;

        holder.expandableLayout.setInRecyclerView(true);
        holder.expandableLayout.setExpanded(expandState.get(i));
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {

            @Override
            public void onPreOpen() {
                changeRotate(holder.button,0f,180f).start();
                expandState.put(i,true);
-            }

            @Override
            public void onPreClose() {
                changeRotate(holder.button,180f,0f).start();
                expandState.put(i,false);

            }
        });

        holder.button.setRotation(expandState.get(i)?0f:180f);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Expandable child item

                holder.expandableLayout.toggle();

            }
        });





    }
    private ObjectAnimator changeRotate(RelativeLayout button, float to, float from) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(button,"rotation",from,to);
        animator.setDuration(190);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    @Override
    public int getItemCount()
    {
            return nameArray.size();
    }




   /***************////////////////VIEWHOLDER///////////////////*******************

    public class ViewHolder extends RecyclerView.ViewHolder {


        ///public TextView URLName;
        public TextView mFullname;
        public TextView mArrayURL;
        public TextView mTestName;
        public TextView mDateTime;
        public Button viewPDF,DwldPDF;
        public RelativeLayout button;
        public ExpandableLinearLayout expandableLayout;




       public ViewHolder(@NonNull View itemView) {
            super(itemView);

           mFullname=itemView.findViewById(R.id.nameArray);
            mTestName=itemView.findViewById(R.id.TestNameText);
            mDateTime=itemView.findViewById(R.id.DateTimeText);

            button=itemView.findViewById(R.id.button);
            DwldPDF=itemView.findViewById(R.id.dwld_pdf);
            expandableLayout=itemView.findViewById(R.id.expandableLayout);
               viewPDF=itemView.findViewById(R.id.view_pdf);
               context = itemView.getContext();



           viewPDF.setOnClickListener(new View.OnClickListener()
           {

               @Override
               public void onClick(View view) {

                   //view.getContext()
                   Intent intent =  new Intent(context, PDFview.class);


                   String s = nameArray.get(getAdapterPosition()); // here you will get current item
                   intent.putExtra("key", s);

                   String URLTestNamePosition = TestNameArray.get(getAdapterPosition()); // here you will get current item
                   intent.putExtra("TestNameKey", URLTestNamePosition);

                   String DateTimePosition = DateTimeArray.get(getAdapterPosition()); // here you will get current item
                   intent.putExtra("DateTimeKey", DateTimePosition);


/*
                   String URLArrayPosition = urlArray.get(getAdapterPosition()); // here you will get current item
                   intent.putExtra("URLkey", URLArrayPosition);
*/

                   context.startActivity(intent);



               }
           });





           DwldPDF.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   //view.getContext()
                   Intent intent =  new Intent(context, com.example.imc.Activities.DwldPDF.class);

                   String s = nameArray.get(getAdapterPosition()); // here you will get current item
                   intent.putExtra("key", s);

                   String URLTestNamePosition = TestNameArray.get(getAdapterPosition()); // here you will get current item
                   intent.putExtra("TestNameKey", URLTestNamePosition);

                   String DateTimePosition = DateTimeArray.get(getAdapterPosition()); // here you will get current item
                   intent.putExtra("DateTimeKey", DateTimePosition);

                   //context.startActivity(intent);


                   outputfilename = TestNameArray.get(getAdapterPosition()) + ".pdf";
                   outputURLdwld = BaseDwldURL+s;
                   final File File = new File(Environment.getExternalStorageDirectory(),outputfilename);


                   DownloadManager.Request request=new DownloadManager.Request(Uri.parse(outputURLdwld))
                           .setMimeType("application/pdf")
                           .setTitle(outputfilename)// Title of the Download Notification
                           .setDescription("المركز الطبي العالمي")// Description of the Download Notification
                           .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                           .setDestinationUri(Uri.fromFile(File))// Uri of the destination file
                           .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                           .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
                   request.allowScanningByMediaScanner();
                   DownloadManager downloadManager= (DownloadManager)context.getSystemService(DOWNLOAD_SERVICE);
                   lDownloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.

                   openFolder();






               //    context.startActivity(intent);

                  // Download_JC open = new Download_JC();
                   //open.D_W();

               }



           });



        }
    }




    public void insertItemInList(String beanChat) {

        if (nameArray == null) nameArray = new ArrayList<>();
        nameArray.add(beanChat);
        notifyItemInserted(nameArray.size() - 1);

        if (TestNameArray == null) TestNameArray = new ArrayList<>();
        TestNameArray.add(beanChat);
        notifyItemInserted(TestNameArray.size() - 1);

        if (DateTimeArray == null) DateTimeArray = new ArrayList<>();
        DateTimeArray.add(beanChat);
        notifyItemInserted(DateTimeArray.size() - 1);

    }


    public void insertItemsInList(ArrayList<String> myList) {

        if (nameArray == null) nameArray = new ArrayList<>();
        for (String beanChat : myList) {
            insertItemInList(beanChat);
        }
        if (TestNameArray == null) TestNameArray = new ArrayList<>();
        for (String beanChat : myList) {
            insertItemInList(beanChat);
        }
        if (DateTimeArray == null) DateTimeArray = new ArrayList<>();
        for (String beanChat : myList) {
            insertItemInList(beanChat);
        }

//        if (urlArray == null) urlArray = new ArrayList<>();
//        for (String beanChat : myList) {
//            insertItemInList(beanChat);
//        }

    }

/*
    public ArrayList<String> getList() {

        if (nameArray == null) nameArray = new ArrayList<>();
        return nameArray;

        if (urlArray == null) urlArray = new ArrayList<>();
        return urlArray;

    }
*/
    public void clearList() {

        if (nameArray == null) return;
        nameArray.clear();
        notifyDataSetChanged();

        if (TestNameArray == null) return;
        TestNameArray.clear();
        notifyDataSetChanged();

        if (DateTimeArray == null) return;
        DateTimeArray.clear();
        notifyDataSetChanged();
//
//        if (urlArray == null) return;
//        urlArray.clear();
//        notifyDataSetChanged();

    }


    public void openFolder()
    {
        context.startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));

    }


}




