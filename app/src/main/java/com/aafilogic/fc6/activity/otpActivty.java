package com.aafilogic.fc6.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aafilogic.fc6.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class otpActivty extends AppCompatActivity {


    private static final String TAG = "OTP Activity";


    String user_id, token, otp;

    Button submit_otp;
    EditText pin_first_edittext, pin_second_edittext, pin_third_edittext, pin_forth_edittext, pin_fifth_edittext, pin_six_edittext;
    String user_otp;
    TextView txt_resend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otplayout);

        pin_six_edittext = (EditText) findViewById(R.id.pin_six_edittext);
        pin_fifth_edittext = (EditText) findViewById(R.id.pin_fifth_edittext);
        pin_forth_edittext = (EditText) findViewById(R.id.pin_forth_edittext);
        pin_third_edittext = (EditText) findViewById(R.id.pin_third_edittext);
        pin_second_edittext = (EditText) findViewById(R.id.pin_second_edittext);
        pin_first_edittext = (EditText) findViewById(R.id.pin_first_edittext);


        pin_six_edittext.addTextChangedListener(new GenericTextWatcher(pin_six_edittext));
        pin_fifth_edittext.addTextChangedListener(new GenericTextWatcher(pin_fifth_edittext));
        pin_forth_edittext.addTextChangedListener(new GenericTextWatcher(pin_forth_edittext));
        pin_third_edittext.addTextChangedListener(new GenericTextWatcher(pin_third_edittext));
        pin_second_edittext.addTextChangedListener(new GenericTextWatcher(pin_second_edittext));
        pin_first_edittext.addTextChangedListener(new GenericTextWatcher(pin_first_edittext));

        txt_resend=(TextView)findViewById(R.id.txt_resend);

//        listner();




        submit_otp = (Button) findViewById(R.id.submitotp);


        try {

            Intent inte = getIntent();


            user_id = inte.getStringExtra("user_id");
            Log.e("user_id",user_id);
            token = inte.getStringExtra("token");
            Log.e("token",token);
            otp = inte.getStringExtra("otp");
            Log.e("otp",otp);
            Log.e("otp",otp);

        } catch (Exception e) {


            e.printStackTrace();
        }


        submit_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                user_otp=pin_first_edittext.getText().toString()+pin_second_edittext.getText().toString()+pin_third_edittext.getText().toString()+pin_forth_edittext.getText().toString()+pin_fifth_edittext.getText().toString()+pin_six_edittext.getText().toString();

                Log.e("userotp",user_otp);
                Log.e("otp",otp);

                if(user_otp.trim().equals(otp)){
                    final ProgressDialog loading = ProgressDialog.show(otpActivty.this, "Please wait...", "Verifying OTP...", false, false);


                    //   SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                    //  new_token=pref.getString("regId",null);

                    //   Toast.makeText(getApplicationContext(),"Token on signup time : "+new_token,Toast.LENGTH_LONG).show();


                    String tag_string_req = "req_signup";

                    // final String img_url="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUsoakftmf6A5PVKLwpeG-yfLmlXgdMzQeJw-N4dxi0UmYt4bB";
                    // String url="https://www.aafilogicinfotech.com/demo/FC6/api/register.php?user_name="+username+"&&user_email="+email+"&&user_gender="+gender+"&&user_mobile="+mobile+"&&user_password="+password+"&&device_id="+new_token+"&&device_type=android&&user_image="+img_url+"&&user_type=app&&group_id="+team_name+"&&company_name="+company_name;

                    String url = "https://www.aafilogicinfotech.com/demo/FC6/api/verify_otp.php";
                    Log.e(TAG, "OTP Verify :" + url);


                    // Toast.makeText(getApplicationContext()," "+url,Toast.LENGTH_LONG).show();


                    StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = response.toString();
                            Log.e("resend detail",result);
                            loading.dismiss();


                            //  Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();


                            PersonUtils personUtils = new PersonUtils();


                            try {


                                JSONObject jObj = new JSONObject(response);

                                String success = jObj.getString("Success");


                                if (success.equals("1")) {

                                    //  Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_LONG).show();
                                    JSONObject user = jObj.getJSONObject("post");

                                    // String user_id=user.getString("user_id");
                                    // String token=user.getString("token");
                                    // String otp=user.getString("otp");

                                    Toast.makeText(getApplicationContext(),"User Successfully Registered", Toast.LENGTH_LONG).show();
                                    Intent in = new Intent(getApplicationContext(), login.class);
                                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(in);


                                } else if (success.equals("0"))

                                {
                                    Toast.makeText(getApplicationContext(), "something went wrong! Try again", Toast.LENGTH_LONG).show();


                                }


                                //  JSONObject user = jObj.getJSONObject("post");

                                //  customer_id = user.getString("customer_id");
                                // token = user.getString("token");


                                // String user_id=user.getString("user_id");
                                //  String token=user.getString("token");
                                //  String otp=user.getString("otp");


                                // personUtils.setUserid(user_id);
                                //  personUtils.setToken(token);
                                //  personUtils.setOtp(otp);


                                //  Intent in=new Intent(getApplicationContext(),otpActivty.class);
                                //  in.putExtra("otp",otp);
                                //  in.putExtra("token",token);
                                //  in.putExtra("user_id",user_id);
                                //  startActivity(in);


                            } catch (JSONException e) {

                                e.printStackTrace();

                            }


                            //  Intent intent = new Intent(Signup.this,LoginActivity.class);

                            // startActivity(intent);


                        }
                    }, new Response.ErrorListener()

                    {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "Signup Error: " + error.getMessage());
                            Toast.makeText(getApplicationContext(),
                                    error.getMessage(), Toast.LENGTH_LONG).show();
                            //  hideDialog();
                        }
                    }

                    ) {


                        @Override
                        protected Map<String, String> getParams() {
                            // Posting parameters to login url
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("user_id", user_id);
                            params.put("token", token);
                            params.put("otp", otp);


                            return params;


                        }


                    };


                    // Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
                }else{
                    Toast.makeText(getApplicationContext(), "OTP mismatch! Try again", Toast.LENGTH_LONG).show();

                }

            }
        });


        txt_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtp();
            }
        });


    }

    public void listner() {
        final Button login = findViewById(R.id.submitotp);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent fragment = new Intent(otpActivty.this, login.class);
                fragment.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(fragment);

            }
        });


        findViewById(R.id.closesign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });


    }

    public class GenericTextWatcher implements TextWatcher {
        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

//    pin_first_edittext,pin_second_edittext,pin_third_edittext,pin_forth_edittext,pin_fifth_edittext,pin_six_edittext

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch (view.getId()) {

                case R.id.pin_first_edittext:
                    if (text.length() == 1)
                        pin_second_edittext.requestFocus();
                    break;
                case R.id.pin_second_edittext:
                    if (text.length() == 1)
                        pin_third_edittext.requestFocus();
                    break;
                case R.id.pin_third_edittext:
                    if (text.length() == 1)
                        pin_forth_edittext.requestFocus();
                    break;
                case R.id.pin_forth_edittext:
                    if (text.length() == 1)
                        pin_fifth_edittext.requestFocus();
                    break;
                case R.id.pin_fifth_edittext:
                    if (text.length() == 1)
                        pin_six_edittext.requestFocus();
                    break;
                case R.id.pin_six_edittext:
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }


    public void resendOtp(){

        final ProgressDialog loading = ProgressDialog.show(otpActivty.this, "Please wait...", "Logging in...", false, true);


        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
        final String user_id = pref.getString("user_id", "");
        final String token = pref.getString("token", "");
        String tag_string_req = "req_login";
        // final String img_url="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUsoakftmf6A5PVKLwpeG-yfLmlXgdMzQeJw-N4dxi0UmYt4bB";
        // String url="https://www.aafilogicinfotech.com/demo/FC6/api/register.php?user_name="+username+"&&user_email="+email+"&&user_gender="+gender+"&&user_mobile="+mobile+"&&user_password="+password+"&&device_id="+new_token+"&&device_type=android&&user_image="+img_url+"&&user_type=app&&group_id="+team_name+"&&company_name="+company_name;

        String url = "https://www.aafilogicinfotech.com/demo/FC6/api/resend_otp.php";
        Log.e(TAG, "Login URL: " + url);


        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String result = response.toString();
                Log.e("resend detail",result);
                loading.dismiss();

                PersonUtils personUtils = new PersonUtils();

                try {

                    JSONObject jObj = new JSONObject(response);

                    String success = jObj.getString("Success");

                    if (success.equals("1")) {

                        JSONObject user = jObj.getJSONObject("post");
                        String otp_data = user.getString("otp");
                        otp=otp_data;

                        Toast.makeText(otpActivty.this,"OTP Sent",Toast.LENGTH_LONG).show();
                    }

                    else if (success.equals("0"))

                    {
                        Toast.makeText(getApplicationContext(), "Wrong Email/Password !", Toast.LENGTH_LONG).show();


                    }


                } catch (JSONException e) {

                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener()

        {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
//                            Toast.makeText(getApplicationContext(),
//                                    error.getMessage(), Toast.LENGTH_LONG).show();
                //  hideDialog();
            }
        }

        ) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", user_id);
                params.put("token", token);

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }




}
