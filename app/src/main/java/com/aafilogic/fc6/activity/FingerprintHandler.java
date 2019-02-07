package com.aafilogic.fc6.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.aafilogic.fc6.New_Home;
import com.aafilogic.fc6.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FingerprintHandler  extends FingerprintManager.AuthenticationCallback {
    private CancellationSignal cancellationSignal;
    private Context appContext;
    private static final String TAG = "Login Activity";
    String email, password;
    String[] badges, badges_type;
    String new_token;
    SharedPreferences pref;
    public FingerprintHandler(Context mContext) {
        appContext = mContext;

    }


    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(appContext, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }


    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Fingerprint Authentication error\n" + errString, false);
    }


    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("Fingerprint Authentication help\n" + helpString, false);
    }


    @Override
    public void onAuthenticationFailed() {
        this.update("Fingerprint Authentication failed.", false);
    }


    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Fingerprint Authentication succeeded.", true);
    }


    public void update(String e, Boolean success){
        TextView textView = (TextView) ((Activity)appContext).findViewById(R.id.fb_login_text);
        textView.setText(e);
        if(success){

            pref = appContext.getSharedPreferences(Config.SHARED_PREF, 0);
            email = pref.getString("rememb_email", "");
            password = pref.getString("rememb_password", "");

            Log.e("data",String.valueOf(email.length()));

            if(email.length()>0){
                fingerprintlogin_data();
            }else{
               textView.setText("Login from email and password first with remember");
                textView.setTextColor(appContext.getColor(R.color.btn_bg));
            }

        }
    }


    public void fingerprintlogin_data(){

        String tag_string_req = "req_login";
        SharedPreferences pref = appContext.getSharedPreferences(Config.SHARED_PREF, 0);
        new_token = pref.getString("regId", null);
        String url = "https://www.aafilogicinfotech.com/demo/FC6/api/login.php";
        Log.e(TAG, "Login URL: " + url);
//            loading = ProgressDialog.show(context, "Please wait...", "Logging in...", false, true);


        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String result = response.toString();
                Log.e("login detail",result);
//                loading.cancel();

                PersonUtils personUtils = new PersonUtils();

                try {

                    JSONObject jObj = new JSONObject(response);

                    String success = jObj.getString("Success");

                    if (success.equals("1")) {


                        //  Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_LONG).show();
                        JSONObject user = jObj.getJSONObject("post");

                        String user_id = user.getString("user_id");
                        String token = user.getString("token");
                        String device_id = user.getString("device_id");
                        String group_id = user.getString("group_id");
                        String company_name = user.getString("company_name");
                        String user_name = user.getString("user_name");
                        String user_email = user.getString("user_email");
                        String user_gender = user.getString("user_gender");
                        String user_mobile = user.getString("user_mobile");
                        String user_password = user.getString("user_password");
                        String user_image = user.getString("user_image");
                        String cover_image = user.getString("cover_image");
                        String total_workout = user.getString("total_workout");
                        String total_meals = user.getString("total_meals");
                        String total_point = user.getString("total_point");
                        //  String badges3=user.getString("badges3");

                        String user_status = user.getString("user_status");
                        String user_add_time = user.getString("user_add_time");


                        JSONArray jsonArray = user.getJSONArray("badges");

                        Log.e("badges array",jsonArray.toString());

                        badges_type=new String[jsonArray.length()];
                        badges=new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonQuiz = jsonArray.getJSONObject(i);

                            badges_type[i] = jsonQuiz.getString("type");
                            Log.e("badges",badges_type[i]);
                            badges[i] = jsonQuiz.getString("badge");
                            Log.e("badges",badges[i]);
                        }

                        SharedPreferences pref = (appContext.getSharedPreferences(Config.SHARED_PREF, 0));
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("user_id", user_id);
                        editor.putString("token", token);
                        editor.putString("device_id", device_id);
                        editor.putString("group_id", group_id);
                        editor.putString("company_name", company_name);
                        editor.putString("user_name", user_name);
                        editor.putString("user_email", user_email);
                        editor.putString("user_gender", user_gender);
                        editor.putString("user_mobile", user_mobile);
                        editor.putString("user_password", user_password);
                        editor.putString("user_image", user_image);
                        editor.putString("cover_image", cover_image);
                        editor.putString("total_workout", total_workout);
                        editor.putString("total_meals", total_meals);
                        editor.putString("badges1", jsonArray.toString());
                        editor.putString("total_point", total_point);
                        editor.putString("visible_position", "0");
                        editor.putString("badges_type", badges_type.toString());

                        //  editor.putString("badges3", badges3);
                        editor.putString("user_status", user_status);
                        editor.putString("user_add_time", user_add_time);


                        editor.commit();

                        if(user_status.equals("1")) {

                            Intent in = new Intent(appContext, New_Home.class);
                            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            ((Activity) appContext).startActivity(in);
                        }


                    } else if (success.equals("0"))
                    {
                        Toast.makeText(appContext, "Wrong Email/Password !", Toast.LENGTH_LONG).show();
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
            }
        }

        ) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("device_id", new_token);
                params.put("user_email", email);
                params.put("user_password", password);
                params.put("device_type", "android");

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        }

    }



