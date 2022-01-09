package com.example.imc.Activities;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.example.imc.MainAdapter;
import com.example.imc.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DwldPDF extends AppCompatActivity {

    Button reservation;
    DownloadManager downloadManager;
    TextView textView,textView2;
    String BaseURL="http://aselbehary-001-site1.ctempurl.com/Labs/Reader?FileName=";
    String filedwldNamefromServer = "Chapter1.pdf";
    String fileNamefromServer = "";
    String outputURL;
    private long lDownloadID;
    String outputfilename;
    Intent intent2;
    TextView hotlineText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dwld_pdf);

        reservation = findViewById(R.id.buttonReservation);
        hotlineText = findViewById(R.id.hotlinetxt);

        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                if(isAppInstalled("com.whatsapp", "Whatsapp"))
                {

                }else{
                }else{

                }
                */




               intent2 = new Intent(android.content.Intent.ACTION_VIEW);
                //Copy App URL from Google Play Store.
                intent2.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.lorasoftware.mohamed_salah_zidane.imchelperdates1"));
                startActivity(intent2);


            }

        });

        hotlineText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });


    }



    public  void  download(){

         outputfilename = fileNamefromServer + ".pdf";
         final File File = new File(Environment.getExternalStorageDirectory(),outputfilename);


        outputURL = BaseURL + filedwldNamefromServer;

        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(outputURL))
                .setMimeType("application/pdf")
                .setTitle(fileNamefromServer)// Title of the Download Notification
                .setDescription("Downloading")// Description of the Download Notification
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(File))// Uri of the destination file
                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
                request.allowScanningByMediaScanner();
                DownloadManager downloadManager= (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                lDownloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.

    }
    public  void downloadOnclick(){
        /*
                dwldBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                          //  String URLDatafromServer = getIntent().getStringExtra("URLkey");
                String FileNameFromServer = getIntent().getStringExtra("key");
                outputURL = BaseURL + FileNameFromServer;
                downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri= Uri.parse(outputURL);
                DownloadManager.Request request =new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long reference = downloadManager.enqueue(request);
                textView.setText(outputURL);
              // textView2.setText(URLData);


                download();



            }
        });

*/

    }

    public void call() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        int phoneNumber=0;

        switch (phoneNumber)
        {
            case 2:
                callIntent.setData(Uri.parse("tel:"+16221));
                break;

            case 1:
                callIntent.setData(Uri.parse("tel:"+16221));
                break;

            case 3:
                callIntent.setData(Uri.parse("tel:"+16221));
                break;

            default:
                callIntent.setData(Uri.parse("tel:"+16221));
                break;

        }

        startActivity(callIntent);
    }
    public boolean isAppInstalled(String package_name, String app_name)
    {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo info = pm.getPackageInfo("" + package_name, PackageManager.GET_META_DATA);
            return true;

        }
        catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Your device has not installed " + app_name, Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
    }


    ///METHOD INFO
    //isAppInstalled("com.whatsapp", "Whatsapp"); // it will return true if your device is having whatsApp.
    //
    //isAppInstalled("com.randomname", "anyname"); //it will return false

}

