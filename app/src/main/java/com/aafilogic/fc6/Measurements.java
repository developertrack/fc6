package com.aafilogic.fc6;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aafilogic.fc6.activity.AppController;
import com.aafilogic.fc6.activity.Config;
import com.aafilogic.fc6.activity.PersonUtils;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Measurements extends AppCompatActivity {


    private static final String TAG_T = "Measurement Activity";

    TextView tv_start_weight, tv_mid_weight, tv_end_weight, gender;
    EditText et_mid_weight;
    ImageView save_weight;
    LinearLayout et_mid;
    String str_mid_weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurements);


        tv_start_weight = (TextView) findViewById(R.id.starting_weight);
        tv_mid_weight = (TextView) findViewById(R.id.mid_weight);
        tv_end_weight = (TextView) findViewById(R.id.ending_weight);
        gender = (TextView) findViewById(R.id.gender);
        et_mid_weight = (EditText) findViewById(R.id.et_mid_weight);
        save_weight = (ImageView) findViewById(R.id.save_weight);
        et_mid=(LinearLayout)findViewById(R.id.et_mid);

        tv_start_weight.setPaintFlags(tv_start_weight.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv_mid_weight.setPaintFlags(tv_mid_weight.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv_end_weight.setPaintFlags(tv_end_weight.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        gender.setPaintFlags(gender.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tv_mid_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_mid_weight.setVisibility(View.GONE);
                et_mid.setVisibility(View.VISIBLE);
                et_mid_weight.setText(str_mid_weight);
            }
        });

        save_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String wght=et_mid_weight.getText().toString();

                if(wght.length()>0){
                    update_mid_weight(wght);
                }

            }
        });


        getMeasurement();
    }


    public void back(View v) {


        finish();

    }


    public void getMeasurement(){
        try {


            SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);

            final String user_id = pref.getString("user_id", "");
            final String token = pref.getString("token", "");


            // final ProgressDialog loading = ProgressDialog.show(getApplicationContext(), "Please wait...", "Fetching measurements...", false, true);


            String tag_string_req = "req_POST";

            //   String url = "https://go1shop.com/api/get_deshboard.php";
            String url = "https://www.aafilogicinfotech.com/demo/FC6/api/get_measurement.php";


            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String result = response.toString();
                    // loading.dismiss();


                    // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


                    try {


                        JSONObject jObj = new JSONObject(response);


                        String success = jObj.getString("Success");

                        JSONArray jsonArray = jObj.getJSONArray("post");


                        for (int i = 0; i < jsonArray.length(); i++) {

                            PersonUtils personUtils = new PersonUtils();


                            JSONObject jsonQuiz = jsonArray.getJSONObject(i);


                            gender.setText(jsonQuiz.getString("gender"));
                            tv_start_weight.setText(jsonQuiz.getString("weight_in_lbs") + "lbs.");
                            tv_mid_weight.setText(jsonQuiz.getString("mid_weight_lbs") + "lbs.");
                            str_mid_weight=jsonQuiz.getString("mid_weight_lbs");
                            tv_end_weight.setText(jsonQuiz.getString("end_weight_in_lbs") + "lbs.");


                        }



                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }


                }
            }, new Response.ErrorListener()

            {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG_T, "Post Loading Error: " + error.getMessage());
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


                    return params;


                }


            };


            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


        } catch (Exception e) {


            e.printStackTrace();
        }


    }


    public void update_mid_weight(final String mid_wght){
        try {


            SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);

            final String user_id = pref.getString("user_id", "");
            final String token = pref.getString("token", "");


            // final ProgressDialog loading = ProgressDialog.show(getApplicationContext(), "Please wait...", "Fetching measurements...", false, true);


            String tag_string_req = "req_POST";

            //   String url = "https://go1shop.com/api/get_deshboard.php";
            String url = "https://www.aafilogicinfotech.com/demo/FC6/api/measurement.php";


            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String result = response.toString();

                    Log.e("result",result);


                    try {


                        JSONObject jObj = new JSONObject(response);


                        String success = jObj.getString("Success");

                        if (success.equals("1")) {

                            tv_mid_weight.setVisibility(View.VISIBLE);
                            et_mid.setVisibility(View.GONE);

                            getMeasurement();

                        } else if (success.equals("0"))

                        {
                            Toast.makeText(getApplicationContext(), "something went wrong! Try again", Toast.LENGTH_LONG).show();

                        }

                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }


                }
            }, new Response.ErrorListener()

            {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG_T, "Post Loading Error: " + error.getMessage());
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
                    params.put("mid_weight_lbs",mid_wght);


                    return params;


                }


            };


            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


        } catch (Exception e) {


            e.printStackTrace();
        }


    }

}
