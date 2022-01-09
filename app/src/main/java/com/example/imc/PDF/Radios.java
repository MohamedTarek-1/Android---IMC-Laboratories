package com.example.imc.PDF;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.imc.Activities.Home;
import com.example.imc.MainAdapter;
import com.example.imc.MySingleton;
import com.example.imc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Radios extends AppCompatActivity {

    SharedPreferences mPrefs;
    public RequestQueue requestQueue;
    TextView MedicalText,PhoneText,AddressText,EmailText,result,resultArray;
    String URL="http://aselbehary-001-site1.ctempurl.com/api/patients?medicalNumber=";
    String URL2 ="&NationalId=";
    private static  int splashtimeout = 1000;
    String u="s";

    List<ArrayList<String>> Tests = new ArrayList<>();
    ArrayList<String> DateTimeArray = new ArrayList<String>();
    ArrayList<String> nameArray = new ArrayList<String>();
    ArrayList<String> TestNameArray = new ArrayList<String>();
    ArrayList<String> IdfromjsonArray = new ArrayList<String>();
    ArrayList<String> Patient_ID_Array = new ArrayList<String>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ProgressDialog progressDialog;
    String InputMedicalN,InputNatId,P_MedicalNumber,P_NatId;
    String idvalue;
    // String url = "https://docs.google.com/viewer?url=http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
    Button dwldPDF,viewPDF;
    ProgressBar progressBar;
    TextView noTests;
    Integer Med_Num=0,P_MedicalNumberInteger,P_InputNatId;
    String Verified_Medical_N_FromUser="";
    String outputURL,str,str2;
    AlertDialog ad = null;
    TextView M_N_TV,Nat_Id_TV;
    String[] perms ={Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    ConnectivityManager connectivityManager;
    boolean haveConnection=false;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radios);


        dwldPDF = findViewById(R.id.dwld_pdf);
        viewPDF = findViewById(R.id.view_pdf);
        progressBar = findViewById(R.id.progressBar);
        noTests = findViewById(R.id.notests);
        M_N_TV = findViewById(R.id.textViewMedN);
        mRecyclerView = findViewById(R.id.recyvlerView);
        mLayoutManager  = new LinearLayoutManager(this);
        boolean haveConnection=false;
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        noTests.setVisibility(View.INVISIBLE);

        SharedPreferences mPrefs = getSharedPreferences("sharedPref",0);
        str = mPrefs.getString("Medical_Number", "");
        str2 = mPrefs.getString("National_ID", "");

        if (str.equals("")) {
            progressBar.setVisibility(View.INVISIBLE);

            //AlertAskForMedNum();
            NewAlert();

            //      Toast.makeText(getApplicationContext(),"No Med Num",Toast.LENGTH_SHORT).show();
        } else {
            // Toast.makeText(getApplicationContext(), str,Toast.LENGTH_SHORT).show();

            // STR visible MED NUM AND NAT ID ON HOMEPAGE
            outputURL =URL+str+URL2+str2;
            jsonParse();

        }
    }
    /////////////////////////////******************///////////////////////////////////////////////////////******************//////////////////////////
    public void jsonParse() {

        if(str.equals(""))
        {
            outputURL = URL + InputMedicalN+URL2+InputNatId;
        }
        else {
            outputURL = URL + str+URL2+str2;
        }
        //String URL="http://ashaaban-001-site1.btempurl.com/api/patients/"+idvalue;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, outputURL, null, new Response.Listener<JSONObject>()
        {

            @Override
            public void onResponse(JSONObject response) {

                try {


                    JSONArray jsonArray = response.getJSONArray("Radios");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject arraydata = jsonArray.getJSONObject(i);
                        // Tests.add(jsonObject.getString(String.valueOf(i)));
                        String  urlName = arraydata.getString("urlName");
                        String Name =arraydata.getString("Name");//THE NAME SHOWN TO USER IN RESULTS
                        String TestName =arraydata.getString("testName");
                        String DateTime =arraydata.getString("Datetime");
                        String Idfromjson =arraydata.getString("Id");
                        String P_ID_Array =arraydata.getString("patients_Id");

                        TestNameArray.add(TestName);
                        nameArray.add(Name);
                        DateTimeArray.add(DateTime);
                        IdfromjsonArray.add(Idfromjson);
                        Patient_ID_Array.add(P_ID_Array);

                        Tests.add(TestNameArray);
                        Tests.add(nameArray);
                        Tests.add(DateTimeArray);
                        Tests.add(Patient_ID_Array);
                        //  resultArray.append("urlName = " +urlName+" \n\n Name"+Name);


                    }

                    //resultArray.append(Tests+"  ,  \n ");
                    // mRecyclerView.setHasFixedSize(true);

                }
                catch (JSONException e)
                {

                    e.printStackTrace();
                    //displayExceptionMessage(e.getMessage());

                }

/*
try {
                    Integer  P_MedicalNumber = response.getInt("MedicalNumber");
                    Integer P_id = response.getInt("Id");
                    String  P_Username = response.getString("UserName");
                    Integer  P_PhoneNumber = response.getInt("phoneNumber");
                    String  P_Address = response.getString("Address");
                    String  P_Email = response.getString("Email");
                    //result.append("Medical Number : "+P_MedicalNumber+" \n id :"+P_id+"UserName"+P_Username);
                } catch (JSONException e) {
                    e.printStackTrace();
                   // displayExceptionMessage(e.getMessage());
                }
*/



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        mAdapter = new MainAdapter(Tests,nameArray,TestNameArray,DateTimeArray);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setHasFixedSize(true);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyItemRangeInserted(0, Tests.size());
                        progressBar.setVisibility(View.INVISIBLE);
                        noTests.setVisibility(View.INVISIBLE);

                        if(Tests.size()== 0)
                        {
                            //Toast.makeText(getActivity(),"Empty List", Toast.LENGTH_SHORT).show();
                            // AlertzeroList();

                            noTests.setVisibility(View.VISIBLE);

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"تم تحميل التحاليل",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);

                            //progressDialog.cancel();

                        }

                    }
                },splashtimeout);



                //    progressBar.setVisibility(View.INVISIBLE);
                // webView.loadUrl(PDFurl);

                //Toast.makeText(getApplicationContext(),"تم تحميل التحاليل",Toast.LENGTH_SHORT).show();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // checkNetworkConnection();
                if(!isNetworkAvailable())
                {

                    Toast.makeText(getApplicationContext(),"تأكد من الإتصال بالشبكة",Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                    //  AlertCheckConnection();
                    if(!isFinishing())
                    {
                        NewAlertNoConnection();
                    }



                }

                else{

                    // Toast.makeText(getApplicationContext(),"هذا الرقم الطبي غير صحيح",Toast.LENGTH_SHORT).show();
                    // NewAlertNoConnection();
                    // NewAlertWrongData();
                    // progressDialog.cancel();
                    try
                    {
                        deliverError(error);  // get Error Number if 403  Then Display Wrong Med Num
                    }
                    catch (Exception e)  // Catch for no connection
                    {

                        //progressDialog.cancel();
                        //  AlertCheckConnection();

                        if(!isFinishing())
                        {
                            NewAlertNoConnection();
                        }





                        //Toast.makeText(getApplicationContext(),"error Response",Toast.LENGTH_SHORT).show();
                        //   AlertWrongMed_N();
                    }
                }
                // Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();

            }
        });

        // requestQueue.add(request);
        MySingleton.getInstance(Radios.this).addToReqeustQueue(jsonObjectRequest);



    }
    /////////////////////////////******************///////////////////////////////////////////////////////******************//////////////////////////
    public void Display_MN_SharedPref(){


        try {

            mPrefs = getSharedPreferences("sharedPref", MODE_PRIVATE);
            String Med_N_Holder = mPrefs.getString("Medical_Number","");
            String National_ID_Holder = mPrefs.getString("National_ID","");

            if (Med_N_Holder =="" && National_ID_Holder==""){

                // ad.show();

                M_N_TV.setText("ليس مسجل");
                M_N_TV.setPaintFlags(M_N_TV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                Nat_Id_TV.setText("ليس مسجل");
                Nat_Id_TV.setPaintFlags(Nat_Id_TV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            }
            else
            {
                M_N_TV.setText(Med_N_Holder);
                M_N_TV.setPaintFlags(M_N_TV.getPaintFlags() |Paint.UNDERLINE_TEXT_FLAG);

                Nat_Id_TV.setText(National_ID_Holder);
                Nat_Id_TV.setPaintFlags(Nat_Id_TV.getPaintFlags() |Paint.UNDERLINE_TEXT_FLAG);

            }
            // Toast.makeText(getApplicationContext(), "Saved!!", Toast.LENGTH_SHORT).show();

        }

        catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(), " تأكد من الرقم الطبي", Toast.LENGTH_SHORT).show();
            //displayExceptionMessage(e.getMessage());


        };

    }
    /////////////////////////////******************///////////////////////////////////////////////////////******************//////////////////////////
    public void check_send_MN(){

        progressDialog = new ProgressDialog(Radios.this);
        progressDialog.setMessage("جاري التحقق من الرقم الطبي");
        progressDialog.setTitle("بحث");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL+InputMedicalN+URL2+InputNatId, null
                , new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response)
            {

                try {

                    Intent intent = new Intent(Radios.this, Home.class);
                    P_MedicalNumber = response.getString("medicalNumber");
                    P_NatId = response.getString("NationalID");

                    intent.putExtra("Medical_Number", P_MedicalNumber);
                    intent.putExtra("NationalID", P_NatId);

                    P_MedicalNumberInteger = Integer.valueOf(P_MedicalNumber);
                    P_InputNatId = Integer.valueOf(P_NatId);

                    jsonParse();
                    saveInfo();
                    Display_MN_SharedPref();
                    //progressDialog.cancel();

                    progressDialog.cancel();

                } catch (JSONException e) {
                    e.printStackTrace();
                    // displayExceptionMessage(e.getMessage());
                    progressDialog.cancel();
                    /**    THIS CLSAS WILL GET RESULTS EVEN IF BLANK  WITH NO EXCEPTION
                     ProgressDialog mDialog = new ProgressDialog(getApplicationContext());
                     mDialog.setMessage("هذا الرقم الطبي غير صحيح أو لم يتم رفع إلية تحاليل بعد");
                     mDialog.setCancelable(false);
                     mDialog.show();*/
                    Toast.makeText(getApplicationContext(), "catch", Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                    //
                    // NewAlertWrongData();

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
                    if(!isFinishing())
                    {
                        NewAlertNoConnection();
                    }


                    //Toast.makeText(getApplicationContext(),"error Response",Toast.LENGTH_SHORT).show();
                    //   AlertWrongMed_N();
                }

            }
        });
        MySingleton.getInstance(Radios.this).addToReqeustQueue(jsonObjectRequest);
    }
    /////////////////////////////******************///////////////////////////////////////////////////////******************//////////////////////////
    public  void saveInfo(){

        //SAVING INFO
        mPrefs = getSharedPreferences("sharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString("Medical_Number", P_MedicalNumber);
        editor.putString("National_ID", P_NatId);
        editor.apply();


        //   Toast.makeText(getApplicationContext(), "Saved!!", Toast.LENGTH_SHORT).show();

    }
    /////////////////////////////******************///////////////////////////////////////////////////////******************//////////////////////////
    public void displayExceptionMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    /////////////////////////////******************///////////////////////////////////////////////////////******************//////////////////////////
  /*  public void AlertCheckConnection(){

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
                if (isNetworkAvailable())
                {
                    check_send_MN();
                    // onResume();
                    Display_MN_SharedPref();
                }
                else{
                    //checkNetworkConnection();
                   // AlertCheckConnection();
                    NewAlertNoConnection();

                }
            }
        });


        builder.setNegativeButton("خروج", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progressDialog.cancel();
                /*~~Jump to About or INFO Activity~~///
                startActivity(new Intent(getApplicationContext(),Home.class));
                dialogInterface.dismiss();
            }

        });
        ad = builder.create();
        ad.show();
    }*/
    /////////////////////////////******************///////////////////////////////////////////////////////******************//////////////////////////
   /* public void checkNetworkConnection() {

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo !=null && networkInfo.isConnected())
        {

          //  Toast.makeText(getApplicationContext(),"Have Network Connection",Toast.LENGTH_SHORT).show();
            haveConnection=true;
        }else
        {

           // Toast.makeText(getApplicationContext(),"No Network Connection",Toast.LENGTH_SHORT).show();
            haveConnection=false;

        }

    }
*/
   /* public void AlertzeroList(){

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
                if (isNetworkAvailable()){
                    check_send_MN();
                    // onResume();
                  //  jsonParse();
                    Display_MN_SharedPref();
                }
                else{
                    //checkNetworkConnection();
                  //  AlertCheckConnection();
                    NewAlertNoConnection();

                }
            }
        });


        builder.setNegativeButton("تواصل مع المعمل", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progressDialog.cancel();
                /*~~Jump to About or INFO Activity~~//
                startActivity(new Intent(getApplicationContext(),Home.class));
                dialogInterface.dismiss();
            }

        });
        ad = builder.create();
        ad.show();
    }
    */
    /*
    public void AlertWrongMed_N(){

        progressDialog.cancel();
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

                check_send_MN();
                //jsonParse();
                onResume();
                Display_MN_SharedPref();
                progressBar.setVisibility(View.INVISIBLE);

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
    */
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
                if(!isFinishing()) {
                    NewAlertWrongData();
                }
                break;
            case 404:
                //  Toast.makeText(getApplicationContext(), "403", Toast.LENGTH_SHORT).show();
                if(!isFinishing()) {
                    NewAlertNoConnection();
                }

            case 307:
                break;
            default:

        }
    }
    /*
    public void progressDialogMethod (){

    }
*/
    public void NewAlert() {

        final AlertDialog builder = new AlertDialog.Builder(Radios.this).create();
        builder.setCancelable(false);

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

                    check_send_MN();
                    // onResume();
                    // jsonParse();

                    Display_MN_SharedPref();
                    builder.dismiss();
                    progressDialog.cancel();


                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                builder.dismiss();
                // progressDialog.cancel();  gives error if it has not been activated
                startActivity(new Intent(getApplicationContext(),Home.class));

            }
        });




        // action buttons
        builder.setView(mView);
        //  AlertDialog dialog = builder.create();
        builder.show();

    }
    public void NewAlertWrongData() {

        final AlertDialog builder = new AlertDialog.Builder(Radios.this).create();
        builder.setCancelable(false);

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
                progressBar.setVisibility(View.VISIBLE);
                InputMedicalN = mMed_Num.getText().toString().trim();
                InputNatId = mNatID.getText().toString().trim();

                if(InputMedicalN.equals("")||InputNatId.equals("")|| InputNatId==null||InputMedicalN==null)
                {
                    Toast.makeText(getApplicationContext(), " أكمل المربعات الفارغة", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);

                    check_send_MN();
                    //jsonParse();
                    onResume();
                    Display_MN_SharedPref();
                    builder.dismiss();
                    progressDialog.cancel();
                    //progressBar.setVisibility(View.INVISIBLE);


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
        if(!isFinishing()) {
            builder.show();
        }


    }
    public void NewAlertNoConnection() {
        final AlertDialog builder = new AlertDialog.Builder(Radios.this).create();
        builder.setCancelable(false);
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
                    outputURL =URL+str+URL2+str2;
                    jsonParse();
                    // check_send_MN();
                    // onResume();
                    Display_MN_SharedPref();
                    builder.cancel();
                    progressDialog.cancel();
                }
                else
                {

                    //checkNetworkConnection();

                    if(!isFinishing())
                    {
                        NewAlertNoConnection();
                    }


                    progressDialog.cancel();

                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                /*~~Jump to About or INFO Activity~~*/
                builder.dismiss();
                progressDialog.cancel();
                startActivity(new Intent(getApplicationContext(),Home.class));
            }
        });
        // action buttons
        builder.setView(mView);
        //  AlertDialog dialog = builder.create();
        if(!isFinishing())
        {
            builder.show();

        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),Home.class));

    }


}
