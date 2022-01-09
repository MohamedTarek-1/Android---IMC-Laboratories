package com.example.imc.Activities;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.imc.MySingleton;
import com.example.imc.R;

import java.util.HashMap;
import java.util.Map;

public class Regsitration extends AppCompatActivity {

    private RequestQueue requestQueue;
    private StringRequest request;
    private static final String URL="http://ashaaban-001-site1.btempurl.com/api/RegisterationPatients";
    public EditText Medical_N,NatId;
    String Medical_N_Holder,Nat_Id_Holder;
    Button RegisterBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsitration);

        Medical_N = findViewById(R.id.medNumReg);
        NatId = findViewById(R.id.natIdReg);
        RegisterBtn = findViewById(R.id.RegisterButton);



        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Medical_N_Holder =Medical_N.getText().toString().trim();
                Nat_Id_Holder =NatId.getText().toString().trim();
            }
        });



    }


    public  void  Registering(){

        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try
                {
                    deliverError(error);  // get Error Number if 403  Then Display Wrong Med Num
                }
                catch (Exception e)  // Catch for no connection
                {

                  //  progressDialog.cancel();
                    //  AlertCheckConnection();
                    NewAlertNoConnection();

                    //Toast.makeText(getApplicationContext(),"error Response",Toast.LENGTH_SHORT).show();
                    //   AlertWrongMed_N();
                }

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("medicalNumber", Medical_N_Holder);
                hashMap.put("nationalId", Nat_Id_Holder);
                return hashMap;
            }

        };
        MySingleton.getInstance(Regsitration.this).addToReqeustQueue(request);
    }




    public void deliverError(VolleyError error)   //I <3 u  // gets Error(Type) Number
    {
        Log.e("deliverError", " here");
        int status = error.networkResponse.statusCode;

        switch (status)
        {
            case 403:
                //  Toast.makeText(getApplicationContext(), "403", Toast.LENGTH_SHORT).show();
            //    NewAlertWrongData();      ********************Remove Comment

                break;
            case 307:
                break;
            default:

        }
    }



    public void NewAlertNoConnection() {

        final AlertDialog builder = new AlertDialog.Builder(Regsitration.this).create();

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

               /*
                if (isNetworkAvailable())
                {
                    builder.dismiss();
                    // check_send_MN();
                    // onResume();
                    // progressDialog.cancel();

                }
                else{
                    //checkNetworkConnection();
                    builder.dismiss();
                   // progressDialog.cancel();
                    NewAlertNoConnection();

                }

                */
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

}