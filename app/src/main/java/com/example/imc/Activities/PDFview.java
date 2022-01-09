package com.example.imc.Activities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.toolbox.StringRequest;
import com.example.imc.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class PDFview extends AppCompatActivity  {
    private StringRequest request;
    String BaseURL = "http://aselbehary-001-site1.ctempurl.com/Labs/Reader?FileName=";
    String outputURL="";
    TextView textview ;
    WebView webView;
    String nameData,TestNameData;
    PDFView Pdfview;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);

        Pdfview = findViewById(R.id.pdfview);
        progressBar = findViewById(R.id.progressBar);

        nameData = getIntent().getStringExtra("key");

        outputURL = BaseURL + nameData;
        progressBar.setVisibility(View.VISIBLE);



       // File folder = new File(Environment.getExternalStorageDirectory() +
      //          File.separator + "IMC");

        //File file =  new File(Environment.getExternalStorageDirectory() + "OOO");

        File file = new File(Environment.getExternalStorageDirectory() + "OOO");

        boolean isCreated = file.mkdirs();
        if (!isCreated)
        {
            file.mkdirs();
//            Toast.makeText(getApplicationContext(),
//                    (file.exists() ? "Directory has been created" : "Directory not created"),
//                    Toast.LENGTH_SHORT).show();
                    STT();

        }

        else {
            STT();
           // Toast.makeText(getApplicationContext(), "Directory exists", Toast.LENGTH_SHORT).show();

        }


    }


            public  void STT (){

                FileLoader.with(this)
                        .load(outputURL)
                        .fromDirectory("OOO",FileLoader.DIR_EXTERNAL_PUBLIC)
                        .asFile(new FileRequestListener<File>() {
                            @Override
                            public void onLoad(FileLoadRequest request, FileResponse<File> Fileresponse) {


                                File pdfFiles = Fileresponse.getBody();

                                Pdfview.fromFile(pdfFiles)
                                        .password(null)
                                        .defaultPage(0)
                                        .enableSwipe(true)
                                        .enableDoubletap(true)
                                        .onDraw(new OnDrawListener() {
                                            @Override
                                            public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                                            }
                                        }).onDrawAll(new OnDrawListener() {
                                    @Override
                                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                                    }
                                }).onPageError(new OnPageErrorListener() {
                                    @Override
                                    public void onPageError(int page, Throwable t) {
                                        Toast.makeText(PDFview.this,"ERROR while opening page"+page,Toast.LENGTH_LONG).show();
                                    }
                                }).onPageChange(new OnPageChangeListener() {
                                    @Override
                                    public void onPageChanged(int page, int pageCount) {

                                    }
                                }).onTap(new OnTapListener() {
                                    @Override
                                    public boolean onTap(MotionEvent e) {
                                        return true;
                                    }
                                }).onRender(new OnRenderListener() {
                                    @Override
                                    public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {

                                        Pdfview.fitToWidth();
                                    }
                                })
                                        .enableAnnotationRendering(true)
                                        .invalidPageColor(Color.WHITE)
                                        .load();
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "تم التحميل", Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void onError(FileLoadRequest request, Throwable t) {
                               // Toast.makeText(PDFview.this,""+t.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });


            }

}
      ///  webView.clearCache(true);
       // webView.clearHistory();

/*
        webView.postDelayed(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(outputURL);
            }
        }, 500);
*/


/*
            ////REQUEST BY SENDING NAME///////
        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error...",Toast.LENGTH_LONG).show();

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("Name", nameData);
                return hashMap;
            }

        };
        Toast.makeText(getApplicationContext(),"Request Sent",Toast.LENGTH_LONG).show();
        //requestQueue.add(request);
        MySingleton.getInstance(PDFview.this).addToReqeustQueue(request);
                ///////////////////////REQUEST END///////////////
                */
