package com.aafilogic.fc6.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aafilogic.fc6.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class signup extends AppCompatActivity {

    static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "Signup Activity";
    public static String BASE_URL = "https://www.aafilogicinfotech.com/demo/FC6/api/image_multipart.php";
    private static int SPLASH_TIME_OUT = 1500;
    EditText et_username, et_email, et_password, et_mobile;
    String username, email, password, mobile, company_name, team_name, gender, new_token, post_img_url;
    TextView tv_login_button;
    ImageView post_user_img;
    Bitmap bitmap;
    String filePath;
    File sourceFile;
    long totalSize = 0;
    private Context context;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    String user_image="a";
    String  path1;
    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "Android File Upload");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + "Android File Upload" + " directory");
                return null;
            }
        }


        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    ProgressDialog progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup);
        context = this;



        et_username = (EditText) findViewById(R.id.username);
        et_mobile = (EditText) findViewById(R.id.MobileNo);
        et_email = (EditText) findViewById(R.id.userEmail);
        et_password = (EditText) findViewById(R.id.userPassword);

        tv_login_button = (TextView) findViewById(R.id.logintext);

        progress = new ProgressDialog(this);

        post_user_img = (ImageView) findViewById(R.id.postuserimage);


        radioSexGroup = (RadioGroup) findViewById(R.id.radioGroup);

        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton) findViewById(selectedId);
        gender = radioSexButton.getText().toString();
        // radioSexButton.setTextColor(getResources().getColor(R.color.green));
        //  radioSexButton.setButtonDrawable(getResources().getDrawable(R.drawable.green_dot));


        tv_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation1 =
                        AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.blink);


                tv_login_button.startAnimation(animation1);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        Intent login = new Intent(signup.this, login.class);
                        startActivity(login);


                    }
                }, SPLASH_TIME_OUT);


            }
        });
        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singup();

            }
        });



    }


    public void startImagePicker(View v) {
        //if everything is ok we will open image chooser
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 100);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            onSelectFromGalleryResult(data);

        } else {
            Toast.makeText(getApplicationContext(), "Image not selected!", Toast.LENGTH_LONG).show();
        }
    }


    private void onSelectFromGalleryResult(Intent data) {
        String imgFile;
        Bitmap bm = null;
        if (data != null) {
            try {
                Uri selectedImageUri = data.getData();
                imgFile = getPath(signup.this, selectedImageUri);
                Log.d("Picture Path", imgFile);

                path1=imgFile;
                progress.setMessage("Wait..");
                progress.show();
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            upload();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                t1.start();

                bm = MediaStore.Images.Media.getBitmap(signup.this.getContentResolver(), data.getData());

                post_user_img.setImageBitmap(bm);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getPath(Context context, Uri uri) {
        String result1 = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result1 = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result1 == null) {
            result1 = "Not found";
        }
        return result1;
    }


    public void upload() throws Exception {
        //Url of the server
        String url = BASE_URL;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                url);

        try {
            MultipartEntity entity = new MultipartEntity();

            entity.addPart("count", new StringBody("1"));

            Log.e("path path1",path1);
            File file = new File(path1);
            entity.addPart("user_image0", new FileBody(file));

            httppost.setEntity(entity);
            HttpResponse response = httpclient.execute(httppost);

            Log.e("test", "SC:" + response.getStatusLine().getStatusCode());

            HttpEntity resEntity = response.getEntity();

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent(), "UTF-8"));
            String sResponse;
            StringBuilder s = new StringBuilder();

            while ((sResponse = reader.readLine()) != null) {
                s = s.append(sResponse);
            }


            Log.e("test", "Response: " + s);

            JSONObject data=new JSONObject(s.toString());

            String success=data.getString("Success");
            user_image=data.getJSONObject("post").getString("user_image");

            Log.e("response data",success+"_"+user_image);

//            imageUploadEdit(user_image);

            progress.dismiss();

        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }
    }


    private void singup() {


        username = et_username.getText().toString().trim();
        email = et_email.getText().toString().trim();
        password = et_password.getText().toString().trim();
        mobile = et_mobile.getText().toString().trim();


        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton) findViewById(selectedId);
        gender = radioSexButton.getText().toString();
        radioSexButton.setTextColor(getResources().getColor(R.color.green));


        if (username.equals("")) {
            et_username.setError("can't be blank");
        } else if (mobile.equals("")) {
            et_mobile.setError("can't be blank");
        } else if (email.equals("")) {
            et_email.setError("can't be blank");
        } else if (email.length() < 5) {
            et_email.setError("at least 5 characters long");
        } else if (password.equals("")) {
            et_password.setError("can't be blank");
        } else if (user_image.equals("a")) {
            Toast.makeText(signup.this,"Please select image",Toast.LENGTH_LONG).show();
        } else {

            final View vi = LayoutInflater.from(context).inflate(R.layout.membershipinfo, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setView(vi);

            EditText et_member_name = (EditText) vi.findViewById(R.id.member_name);

            et_member_name.setText(username);


            final AlertDialog alert = builder.create();


            vi.findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditText et_member_name = (EditText) vi.findViewById(R.id.member_name);
                    EditText et_company_name = (EditText) vi.findViewById(R.id.company_name);
                    EditText et_team_name = (EditText) vi.findViewById(R.id.team_name);

                    company_name = et_company_name.getText().toString().trim();
                    team_name = et_team_name.getText().toString().trim();

                    if (company_name.equals("")) {


                        et_company_name.setError("Can't be blank");


                    } else if (team_name.equals("")) {

                        et_team_name.setError("Can't be blank");

                    } else {
                        alert.dismiss();


                        final ProgressDialog loading = ProgressDialog.show(signup.this, "Please wait...", "Registering...", false, false);


                        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                        new_token = pref.getString("regId", null);

                        String tag_string_req = "req_signup";

                        String url = "https://www.aafilogicinfotech.com/demo/FC6/api/register.php";
                        Log.e(TAG, "Signup URL: " + url);

                        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String result = response.toString();

                                Log.e("resp_signup", response);

                                loading.dismiss();

                                PersonUtils personUtils = new PersonUtils();


                                try {


                                    JSONObject jObj = new JSONObject(response);

                                    Log.e("response", jObj.toString());

                                    String success = jObj.getString("Success");


                                    if (success.equals("1")) {

                                        //  Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_LONG).show();
                                        JSONObject user = jObj.getJSONObject("post");

                                        String user_id = user.getString("user_id");
                                        String token = user.getString("token");
                                        String otp = user.getString("otp");


                                        personUtils.setUserid(user_id);
                                        personUtils.setToken(token);
                                        personUtils.setOtp(otp);

                                        Intent in = new Intent(getApplicationContext(), otpActivty.class);
                                        in.putExtra("otp", otp);
                                        in.putExtra("token", token);
                                        in.putExtra("user_id", user_id);
                                        startActivity(in);


                                    } else if (success.equals("0"))

                                    {
                                        Toast.makeText(getApplicationContext(), jObj.getString("error").toString(), Toast.LENGTH_LONG).show();

                                    }

                                } catch (JSONException e) {

                                    e.printStackTrace();

                                }

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
                                params.put("user_name", username);
                                params.put("user_email", email);
                                params.put("user_gender", gender);
                                params.put("user_mobile", mobile);
                                params.put("user_password", password);
                                params.put("device_id", new_token);
                                params.put("device_type", "android");
                                params.put("user_image", user_image);
                                params.put("user_type", "app");
                                params.put("group_id", team_name);
                                params.put("company_name", company_name);

                                return params;

                            }

                        };

                        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

                    }

                }
            });

            alert.setCancelable(true);
            alert.show();


        }


    }


}

