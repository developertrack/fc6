package com.aafilogic.fc6.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aafilogic.fc6.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    String TAG = "Change Password Activity";
    String group_id, user_id;
    String user_image, new_password,old_password, token, username;
    private RequestQueue rQueue;
    LinearLayout et_name;
    EditText oldpassword,newpassword;
    Button loginbutton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        oldpassword=(EditText)findViewById(R.id.oldpassword);
        newpassword=(EditText)findViewById(R.id.newpassword);
        loginbutton=(Button) findViewById(R.id.loginbutton);

        findViewById(R.id.layoutfirstclosssign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_password=oldpassword.getText().toString();
                new_password=newpassword.getText().toString();

                if(old_password.length()>5 && new_password.length()>5){
                    changePassword();
                }else{
                    Toast.makeText(ChangePassword.this,"Password Length Should be 6 or more",Toast.LENGTH_LONG).show();
                }
            }
        });




        try {

            SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
            token = pref.getString("token", "");
            group_id = pref.getString("group_id", "");
            username = pref.getString("user_name", "");
            user_id = pref.getString("user_id", "");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void back(View v) {

        finish();
    }

    public void changePassword(){
        final ProgressDialog loading = ProgressDialog.show(ChangePassword.this, "Please wait...", "Logging in...", false, true);


        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        // final String img_url="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUsoakftmf6A5PVKLwpeG-yfLmlXgdMzQeJw-N4dxi0UmYt4bB";
        // String url="https://www.aafilogicinfotech.com/demo/FC6/api/register.php?user_name="+username+"&&user_email="+email+"&&user_gender="+gender+"&&user_mobile="+mobile+"&&user_password="+password+"&&device_id="+new_token+"&&device_type=android&&user_image="+img_url+"&&user_type=app&&group_id="+team_name+"&&company_name="+company_name;

        String url = "https://www.aafilogicinfotech.com/demo/FC6/api/change_password.php";
        Log.e(TAG, "Login URL: " + url);


        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String result = response.toString();
                Log.e("changePassword detail",result);
                loading.dismiss();

                PersonUtils personUtils = new PersonUtils();

                try {

                    JSONObject jObj = new JSONObject(response);

                    String success = jObj.getString("Success");

                    if (success.equals("1")) {

                        Toast.makeText(getApplicationContext(), "Password Change successfully", Toast.LENGTH_LONG).show();

                    } else if (success.equals("0"))

                    {
                        Toast.makeText(getApplicationContext(), "Check password", Toast.LENGTH_LONG).show();


                    }

                    oldpassword.setText("");
                    newpassword.setText("");


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
                params.put("old_password", old_password);
                params.put("new_password", new_password);


                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, TAG);

    }
}