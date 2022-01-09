package com.example.imc.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.imc.MySingleton;
import com.example.imc.PDF.Radios;
import com.example.imc.PDF.Tumors;
import com.example.imc.R;
import com.example.imc.PDF.Resultsdwldview;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
public class Home extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    EditText input;
    CardView layoutResult,Radiography,buttonReservations,dwldBTN,TrueBeam;
    public RequestQueue requestQueue;
    private StringRequest request;
    ProgressDialog progressDialog;
    String InputMedicalN,  P_MedicalNumber,P_NatId , InputNatId;
    Integer P_MedicalNumberInteger,P_NatIdInteger;
    String Verified_Medical_N_FromUser="";
    TextView M_N_TV,State_TV,Logout_TV;
    AlertDialog ad = null;
    SharedPreferences mPrefs;
    LinearLayout imagelayout;

    ImageView questionView;
    String URL1 ="http://aselbehary-001-site1.ctempurl.com/api/patients?medicalNumber=";
    String URL2 ="&NationalId=";

    String[] perms ={Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    Intent intent2;
    private ConnectivityManager connectivityManager;
    boolean haveConnection=false;
    private static  int splashtimeout = 15000;
    boolean result=false;
    Context context;
    SharedPreferences.Editor editor;
    Integer ClassType=1;
    Activity currentActivity;
    public interface FragmentAListener  {
        void onInputSent(CharSequence input);

    }
    @Nullable
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        buttonReservations = findViewById(R.id.button4);
        layoutResult = findViewById(R.id.dwldBTN);
        Radiography = findViewById(R.id.Sonar);
        questionView = findViewById(R.id.questionImgView);
        TrueBeam = findViewById(R.id.TrueBeam);
        M_N_TV =(TextView) findViewById(R.id.textViewMedN);
        State_TV =(TextView) findViewById(R.id.textView10);
        Logout_TV = findViewById(R.id.textView4);
        imagelayout = findViewById(R.id.imagelayout33);
        //M_N_TV.append("غير مسجل");
         Display_MN_SharedPref();

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        checkNetworkConnection();
        //ad.show();
/*****************************************ONCLICK LISTENERS***********************************************************/

        imagelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent (getApplicationContext(),OnBoardActivity.class));

            }
        });


        Radiography.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ClassType = 1;
                        checkPermissionThenStart();

                      //  Verified_Medical_N_FromUser =getIntent().getStringExtra("Medical_Number");
                       // startActivity(new Intent(getApplicationContext(), Radios.class));
                    }
                });


                TrueBeam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClassType = 2;
                        checkPermissionThenStart();

                        //  Verified_Medical_N_FromUser =getIntent().getStringExtra("Medical_Number");
                   //     startActivity(new Intent(getApplicationContext(), Tumors.class));

                    }
                });


                layoutResult.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClassType = 3;
                        checkPermissionThenStart();

                        //startActivity(new Intent(getApplicationContext(), Resultsdwldview.class));
                        //ad.show();


                    }
                });



        buttonReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //isConnected();
                //intent2 = new Intent(android.content.Intent.ACTION_VIEW);
                //Copy App URL from Google Play Store.
                //isDeviceOnline(context);
                //isConnected();
                //intent2.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.lorasoftware.mohamed_salah_zidane.imchelperdates1"));
                // Toast.makeText(Home.this, "Network NOT Available", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), DwldPDF.class));

            }
        });

        M_N_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Med_N_Holder = mPrefs.getString("Medical_Number","");

                if(Med_N_Holder=="" ) {

                    NewAlert();

                }
                else{

                    //AlertDialog2();
                    NewAlert();

                }
            }
        });

        Logout_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewAlertLogOut();
            }
        });

/*****************************************ONCLICK LISTENERS***********************************************************/
            }

            public void check_MN(){


                        progressDialog = new ProgressDialog(Home.this);
                        progressDialog.setMessage("جاري التحقق من البيانات");
                        progressDialog.setTitle("بحث");
                        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL1 + InputMedicalN+ URL2+InputNatId, null
                        , new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response)
                    {


                        try
                        {

                            Intent intent =  new Intent(Home.this,Resultsdwldview.class);

                            P_MedicalNumber = response.getString("medicalNumber");
                            P_NatId = response.getString("NationalID");

                            intent.putExtra("Medical_Number", P_MedicalNumber);
                            intent.putExtra("NationalID", P_NatId);

                            P_MedicalNumberInteger = Integer.valueOf(P_MedicalNumber);
                            P_NatIdInteger = Integer.valueOf(P_MedicalNumber);

//                            Intent intent2 =  new Intent(Home.this,ssTumors.class);
//                            P_MedicalNumber = response.getString("medicalNumber");
//                            intent2.putExtra("Medical_Number2", P_MedicalNumber);


                            saveInfo();
                            Display_MN_SharedPref();
                            progressDialog.cancel();



                            //result.append("Medical Number : "+P_MedicalNumber+" \n id :"+P_id+"UserName"+P_Username);
                            //progressDialog.cancel();
                            // Resultsdwldview.sendBroadcast(intent);

                           // startActivity(intent);

                        }

                        catch (JSONException e)
                        {

                            e.printStackTrace();

                            progressDialog.cancel();


                            ProgressDialog mDialog = new ProgressDialog(getApplicationContext());
                            mDialog.setMessage("هذا الرقم الطبي غير صحيح أو لم يتم رفع إلية تحاليل بعد");
                            mDialog.setCancelable(false);
                            mDialog.show();



                        }


                    }


                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        try
                        {
                            deliverError(error);  // get Error Number if 403  Then Display Wrong Med Num
                        }
                        catch (Exception e)  // Catch for no connection
                        {

                            progressDialog.cancel();
                            //  AlertCheckConnection();
                            NewAlertNoConnection();

                            //Toast.makeText(getApplicationContext(),"error Response",Toast.LENGTH_SHORT).show();
                            //   AlertWrongMed_N();
                        }


                    }
                });


                MySingleton.getInstance(Home.this).addToReqeustQueue(jsonObjectRequest);





            }

            public  void saveInfo(){

                    //SAVING INFO
                    mPrefs = getSharedPreferences("sharedPref", MODE_PRIVATE);
                   // SharedPreferences.Editor editor = mPrefs.edit(); editor = mPrefs.edit();
                    editor = mPrefs.edit(); editor = mPrefs.edit();
                    editor.putString("Medical_Number", P_MedicalNumber);
                    editor.putString("National_ID", P_NatId);
                    editor.apply();


                 //   Toast.makeText(getApplicationContext(), "Saved!!", Toast.LENGTH_SHORT).show();
                }

            public  void Display_MN_SharedPref(){
                ////DISPLAYNG MEDICAL N FROM SHARED PREF TO TEXTVIEW

                try {

                        mPrefs = getSharedPreferences("sharedPref", MODE_PRIVATE);
                        String Med_N_Holder = mPrefs.getString("Medical_Number","");


                        if (Med_N_Holder ==""){

                           // ad.show();



                            State_TV.setText("تسجيل الدخول :");
                            Logout_TV.setVisibility(View.INVISIBLE);
                            M_N_TV.setText("ليس مسجل");
                            M_N_TV.setPaintFlags(M_N_TV.getPaintFlags() |Paint.UNDERLINE_TEXT_FLAG);

                        }
                        else
                        {

                            State_TV.setText("الرقم الطبي:");
                            Logout_TV.setVisibility(View.VISIBLE);
                            M_N_TV.setText("الرقم الطبي: ");
                            M_N_TV.setText(Med_N_Holder);
                            M_N_TV.setPaintFlags(M_N_TV.getPaintFlags() |Paint.UNDERLINE_TEXT_FLAG);

                        }
                       // Toast.makeText(getApplicationContext(), "Saved!!", Toast.LENGTH_SHORT).show();

                    }

                 catch (Exception e) {
                        e.printStackTrace();
                     Toast.makeText(getApplicationContext(), " تأكد من الرقم الطبي", Toast.LENGTH_SHORT).show();
                 };
                    };

    @Override
    public void onResume(){
        super.onResume();
       // saveInfo();
       // check_send_MN();
        Display_MN_SharedPref();
    }

    public void checkPermissionThenStart(){

        if(EasyPermissions.hasPermissions(this,perms))
        {
            // startActivity(new Intent(getApplicationContext(), Resultsdwldview.class));
                switch (ClassType)
                {
                    case 1:
                        startActivity(new Intent(getApplicationContext(), Radios.class));
                        break;

                    case 2:
                        startActivity(new Intent(getApplicationContext(), Tumors.class));
                        break;

                    case 3:
                        startActivity(new Intent(getApplicationContext(), Resultsdwldview.class));
                        break;

                    default:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        break;
                }

        }
        else
            {
                EasyPermissions.requestPermissions(this," السماح للبرنامج لتخزين ملفات",123,perms);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }


    public void AlertDialog(){


            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("الرقم طبي");
            builder.setIcon(R.drawable.imclogo1);
            builder.setMessage("من فضلك أدخل رقمك الطبي للدخول علي التحاليل الخاصة بك");
            input = new EditText(this);
            builder.setView(input);


            builder.setPositiveButton("دخول", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i)
                {
                    check_MN();
                    onResume();
                    Display_MN_SharedPref();
                }
            });


            builder.setNegativeButton("ليس لدي", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    progressDialog.cancel();
                    dialogInterface.dismiss();
                }

            });
            ad = builder.create();
            ad.show();
    }
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

        switch (ClassType)
        {
            case 1:
                startActivity(new Intent(getApplicationContext(), Radios.class));
                break;

            case 2:
                startActivity(new Intent(getApplicationContext(), Tumors.class));
                break;

            case 3:
                startActivity(new Intent(getApplicationContext(), Resultsdwldview.class));
                break;

            default:
                startActivity(new Intent(getApplicationContext(), Home.class));
                break;
        }
    }
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){

            new AppSettingsDialog.Builder(this).build().show();

        }
    }
    public void checkNetworkConnection() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo !=null){

           // AlertCheckConnection();
           // Toast.makeText(getApplicationContext(),"Have Network Connection",Toast.LENGTH_SHORT).show();
             haveConnection=true;
        }
        else
        {
            //Toast.makeText(getApplicationContext(),"No Network Connection",Toast.LENGTH_SHORT).show();
             haveConnection=false;
        }
    }
    public void AlertWrongMed_N(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("خطأ");
        builder.setIcon(R.drawable.imclogo1);
       // builder.setCustomTitle(title);
        builder.setMessage("هذا الرقم طبي خطأ");
        input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("دخول", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i)
            {
                check_MN();
                onResume();
                Display_MN_SharedPref();
            }
        });


        builder.setNegativeButton("ليس لدي", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }

        });
        ad = builder.create();
        ad.show();
    }
    public void AlertCheckConnection(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("خطأ في الإتصال");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.imclogo1);
        builder.setMessage("يرجي التأكد من الإتصال بالشبكة");
        //  input = new EditText(this);
        // builder.setView(input);

        builder.setPositiveButton(" أعد المحاولة", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i)
            {
                checkNetworkConnection();
                if (haveConnection){

                    check_MN();
                    // onResume();
                    //jsonParse();
                    Display_MN_SharedPref();

                }
                else{
                    checkNetworkConnection();

                    AlertCheckConnection();
                }
            }
        });


        builder.setNegativeButton("خروج", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progressDialog.cancel();
                /*~~Jump to About or INFO Activity ~~*/
                startActivity(new Intent(getApplicationContext(),Home.class));
                dialogInterface.dismiss();
            }

        });
        ad = builder.create();
        ad.show();
    }
    public  boolean isConnected () {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void displayExceptionMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public void AlertDialog2(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("الرقم طبي");
        builder.setIcon(R.drawable.imclogo1);
        builder.setMessage("من فضلك أدخل رقمك الطبي للدخول علي التحاليل الخاصة بك");
        input = new EditText(this);
        builder.setView(input);


        builder.setPositiveButton("دخول", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i)
            {
                check_MN();
                onResume();
                Display_MN_SharedPref();
            }
        });


        builder.setNegativeButton(" تسجيل خروج", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                M_N_TV.setText("ليس مسجل");
                Logout_TV.setVisibility(View.INVISIBLE);
                M_N_TV.setPaintFlags(M_N_TV.getPaintFlags() |Paint.UNDERLINE_TEXT_FLAG);
                M_N_TV.setVisibility(View.VISIBLE);
               // editor.clear().apply();
                SharedPreferences preferences =getSharedPreferences("sharedPref",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
              //3  finish();


            dialogInterface.dismiss();
            }

        });
        ad = builder.create();
        ad.show();
    }
    public void NewAlert2() {

        final AlertDialog builder = new AlertDialog.Builder(Home.this).create();

        View mView =getLayoutInflater().inflate(R.layout.alert_dialog,null);
        final EditText mMed_Num=mView.findViewById(R.id.MedNum);
        final EditText mNatID=mView.findViewById(R.id.NatID);
        final Button mLogin=(Button) mView.findViewById(R.id.logindialog);
        final Button logout=(Button) mView.findViewById(R.id.logout);


        // get the layout inflater
        //LayoutInflater inflater = Home.this.getLayoutInflater();
        // inflate and set the layout for the dialog
        // pass null as the parent view because its going in the dialog layout

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(InputMedicalN.equals("")||InputNatId.equals("")|| InputNatId==null||InputMedicalN==null)
                {
                    Toast.makeText(getApplicationContext(), " أكمل المربعات السفارغة", Toast.LENGTH_SHORT).show();
                }
                else {
                    InputMedicalN = mMed_Num.getText().toString().trim().replace("٠","0").replace("١","1").replace("٢","2").replace("٣","3")
                            .replace("٤","4").replace("٥","5").replace("٦","6").
                                    replace("٧","7").replace("٨","8").replace("٩","9");

                    InputNatId = mNatID.getText().toString().trim().replace("٠","0").replace("١","1").replace("٢","2").replace("٣","3")
                            .replace("٤","4").replace("٥","5").replace("٦","6").
                                    replace("٧","7").replace("٨","8").replace("٩","9");

                    Toast.makeText(getApplicationContext(), InputMedicalN, Toast.LENGTH_SHORT).show();

                    check_MN();
                    onResume();
                    Display_MN_SharedPref();
                    builder.dismiss();

                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                M_N_TV.setText("ليس مسجل");
                M_N_TV.setPaintFlags(M_N_TV.getPaintFlags() |Paint.UNDERLINE_TEXT_FLAG);
                Logout_TV.setVisibility(View.INVISIBLE);

                M_N_TV.setVisibility(View.VISIBLE);
                //State_TV.setVisibility(View.INVISIBLE);

                // editor.clear().apply();
                SharedPreferences preferences =getSharedPreferences("sharedPref",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();

                builder.dismiss();
            }
        });




        // action buttons
                builder.setView(mView);
              //  AlertDialog dialog = builder.create();
                 builder.show();

    }
    public void NewAlert() {

        final AlertDialog builder = new AlertDialog.Builder(Home.this).create();

        View mView =getLayoutInflater().inflate(R.layout.alert_dialog2,null);
        final EditText mMed_Num=mView.findViewById(R.id.MedNum);
        final EditText mNatID=mView.findViewById(R.id.NatID);
        final Button mLogin=(Button) mView.findViewById(R.id.logindialog);
        final Button logout=(Button) mView.findViewById(R.id.logout);


        // get the layout inflater
        //LayoutInflater inflater = Home.this.getLayoutInflater();
        // inflate and set the layout for the dialog
        // pass null as the parent view because its going in the dialog layout


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMedicalN = mMed_Num.getText().toString().trim().replace("٠","0").replace("١","1").replace("٢","2").replace("٣","3")
                        .replace("٤","4").replace("٥","5").replace("٦","6").
                                replace("٧","7").replace("٨","8").replace("٩","9");

                InputNatId = mNatID.getText().toString().trim().replace("٠","0").replace("١","1").replace("٢","2").replace("٣","3")
                        .replace("٤","4").replace("٥","5").replace("٦","6").
                                replace("٧","7").replace("٨","8").replace("٩","9");


                if(InputMedicalN.equals("")||InputNatId.equals("")|| InputNatId==null||InputMedicalN==null)
                {
                    Toast.makeText(getApplicationContext(), " أكمل المربعات الفارغة", Toast.LENGTH_SHORT).show();
                }

                else {
                    //Toast.makeText(getApplicationContext(), InputNatId, Toast.LENGTH_SHORT).show();

                    check_MN();
                    onResume();
                    Display_MN_SharedPref();
                    builder.dismiss();

                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });




        // action buttons
        builder.setView(mView);
        //  AlertDialog dialog = builder.create();
        builder.show();

    }

    public void NewAlertWrongData() {
        final AlertDialog builder = new AlertDialog.Builder(Home.this).create();
        View mView =getLayoutInflater().inflate(R.layout.alert_wronginput,null);
        final EditText mMed_Num=mView.findViewById(R.id.MedNum);
        final EditText mNatID=mView.findViewById(R.id.NatID);
        final Button mLogin=(Button) mView.findViewById(R.id.logindialog);
        final Button logout=(Button) mView.findViewById(R.id.logout);


        // get the layout inflater
        //LayoutInflater inflater = Home.this.getLayoutInflater();
        // inflate and set the layout for the dialog
        // pass null as the parent view because its going in the dialog layout

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMedicalN = mMed_Num.getText().toString().trim();
                InputNatId = mNatID.getText().toString().trim();

                if(InputMedicalN.equals("")||InputNatId.equals("")|| InputNatId==null||InputMedicalN==null)
                {
                    Toast.makeText(getApplicationContext(), " أكمل المربعات الفارغة", Toast.LENGTH_SHORT).show();
                }

                else {
                    progressDialog.cancel();
                    check_MN();
                    //jsonParse();
                    onResume();
                    Display_MN_SharedPref();
                    //prog.setVisibility(View.INVISIBLE);
                    progressDialog.cancel();
                    builder.dismiss();


                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.dismiss();
                progressDialog.cancel();
                startActivity(new Intent(getApplicationContext(),Home.class));

            }
        });




        // action buttons
        builder.setView(mView);
        //  AlertDialog dialog = builder.create();
        builder.show();

    }
    public void NewAlertNoConnection() {

        final AlertDialog builder = new AlertDialog.Builder(Home.this).create();

        View mView =getLayoutInflater().inflate(R.layout.alert_noconnection,null);
        final Button mLogin=(Button) mView.findViewById(R.id.logindialog);
        final Button logout=(Button) mView.findViewById(R.id.logout);


        // get the layout inflater
        //LayoutInflater inflater = Home.this.getLayoutInflater();
        // inflate and set the layout for the dialog
        // pass null as the parent view because its going in the dialog layout

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable())
                {
                    check_MN();
                    onResume();
                    Display_MN_SharedPref();
                    builder.dismiss();
                    // check_send_MN();
                    // onResume();
                    // progressDialog.cancel();

                }
                else{
                    //checkNetworkConnection();
                    builder.dismiss();
                    progressDialog.cancel();
                    NewAlertNoConnection();

                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                /*~~Jump to About or INFO Activity~~*/
                builder.dismiss();
            }
        });




        // action buttons
        builder.setView(mView);
        //  AlertDialog dialog = builder.create();
        builder.show();

    }
    public void NewAlertLogOut(){

        final AlertDialog builder = new AlertDialog.Builder(Home.this).create();

        View mView =getLayoutInflater().inflate(R.layout.alert_logout,null);
        final Button mLogin=(Button) mView.findViewById(R.id.logindialog);
        final Button logout=(Button) mView.findViewById(R.id.logout);


        // get the layout inflater
        //LayoutInflater inflater = Home.this.getLayoutInflater();
        // inflate and set the layout for the dialog
        // pass null as the parent view because its going in the dialog layout

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable())
                {
                    M_N_TV.setText("ليس مسجل");
                    State_TV.setText("تسجيل دخول :");
                    M_N_TV.setPaintFlags(M_N_TV.getPaintFlags() |Paint.UNDERLINE_TEXT_FLAG);
                    Logout_TV.setVisibility(View.INVISIBLE);

                    M_N_TV.setVisibility(View.VISIBLE);
                    //State_TV.setVisibility(View.INVISIBLE);

                    // editor.clear().apply();
                    SharedPreferences preferences =getSharedPreferences("sharedPref",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();
                    builder.dismiss();


                }
                else{
                    //checkNetworkConnection();
                    builder.dismiss();
                    progressDialog.cancel();
                    //NewAlertNoConnection();

                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                /*~~Jump to About or INFO Activity~~*/
                builder.dismiss();
            }
        });




        // action buttons
        builder.setView(mView);
        //  AlertDialog dialog = builder.create();
        builder.show();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void deliverError(VolleyError error)   //I <3 u  // gets Error(Type) Number
    {
        Log.e("deliverError", " here");
        int status = error.networkResponse.statusCode;

        switch (status)
        {
            case 403:
                //  Toast.makeText(getApplicationContext(), "403", Toast.LENGTH_SHORT).show();
                NewAlertWrongData();

                break;
            case 404:
                //  Toast.makeText(getApplicationContext(), "403", Toast.LENGTH_SHORT).show();
                if(!isFinishing()) {
                    progressDialog.cancel();
                    NewAlertNoConnection();
                }

            case 307:
                break;
            default:

        }
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //startActivity(new Intent(getApplicationContext(),Home.class));
        finish();
    }


}