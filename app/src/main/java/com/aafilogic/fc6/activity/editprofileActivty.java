package com.aafilogic.fc6.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aafilogic.fc6.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class editprofileActivty extends AppCompatActivity {

    static final int PICK_IMAGE_REQUEST = 1;
    public static String BASE_URL = "https://www.aafilogicinfotech.com/demo/FC6/api/image_multipart.php";
    public static String BASE_URL_edit = "https://www.aafilogicinfotech.com/demo/FC6/api/edit_profile.php";
    private final int GALLERY = 1;
    TextView tv_username;
    Bitmap bitmap;
    String group_id, user_id;
    String user_image, cover_img, token, username;
    String post_img_url;
    String  path1;
    ImageView profile_img1, cover_image,profile_img2,save_name;
    private RequestQueue rQueue;
    private ArrayList<HashMap<String, String>> arraylist;
    String upload_type="cover";
    LinearLayout et_name;
    ProgressDialog progress;
    EditText et_username;
    TextView changePassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);

        tv_username = (TextView) findViewById(R.id.username);
        et_name=(LinearLayout)findViewById(R.id.et_name);
        et_username=(EditText) findViewById(R.id.et_username);
        save_name=(ImageView) findViewById(R.id.save_name);
        changePassword=(TextView)findViewById(R.id.changePassword);

        progress = new ProgressDialog(this);

        findViewById(R.id.layoutfirstclosssign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent change_Password=new Intent(editprofileActivty.this,ChangePassword.class);
                startActivity(change_Password);

            }
        });


        try {

            profile_img1 = (ImageView) findViewById(R.id.userimage);
            profile_img2 = (ImageView) findViewById(R.id.notificationimage);
            cover_image = (ImageView) findViewById(R.id.cover_image);
            ImageView edit_user_image = (ImageView) findViewById(R.id.edit_user_image);
            ImageView edit_cover_image = (ImageView) findViewById(R.id.edit_cover_image);

            SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
            user_image = pref.getString("user_image", "");
            cover_img = pref.getString("cover_image", "");
            token = pref.getString("token", "");
            group_id = pref.getString("group_id", "");
            username = pref.getString("user_name", "");
            user_id = pref.getString("user_id", "");

            Picasso.with(editprofileActivty.this).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + user_image).placeholder( R.drawable.progress_animation ).into(profile_img1);
            Picasso.with(getApplicationContext()).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + user_image).placeholder( R.drawable.progress_animation ).into(profile_img2);
            Picasso.with(getApplicationContext()).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + cover_img).placeholder( R.drawable.progress_animation ).into(cover_image);


            tv_username.setText(username);

            tv_username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_name.setVisibility(View.VISIBLE);
                    tv_username.setVisibility(View.GONE);
                    et_username.setText(username);
                }
            });

            save_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(et_username.getText().toString().length()>1){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progress.setMessage("Wait..");
                                progress.show();
                            }
                        });
                        UsernameEdit(et_username.getText().toString());
                    }
                }
            });


            //  tv_workout.setText(workout);
            //  tv_meals.setText(meals);


        } catch (Exception e) {


            e.printStackTrace();
        }


    }

    public void back(View v) {


        finish();

    }




    public void startImagePicker(View v) {
        galleryIntent();
        upload_type="na";

    }

    public void startcoverimgPicker(View v) {
        galleryIntent();
        upload_type="cover";

    }

    private void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

                onSelectFromGalleryResult(data);

                } else {
                    Toast.makeText(getApplicationContext(), "Image not selected!", Toast.LENGTH_LONG).show();
                }

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
            String user_image=data.getJSONObject("post").getString("user_image");

            Log.e("response data",success+"_"+user_image);

            imageUploadEdit(user_image);

        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }
    }


    public byte[] getdata(String mfile){
        Log.e("path image",mfile);
      byte[] b=null;
        try {
            File upload_file = new File(mfile);
            FileInputStream fis = null;
            fis = new FileInputStream(upload_file);
            Bitmap bm = BitmapFactory.decodeStream(fis);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 90 , baos);
             b = baos.toByteArray();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;

    }


    private void onSelectFromGalleryResult(Intent data) {
        String imgFile;
        Bitmap bm = null;
        if (data != null) {
            try {
                Uri selectedImageUri = data.getData();
                imgFile = getPath(editprofileActivty.this, selectedImageUri);
                Log.d("Picture Path", imgFile);

                path1=imgFile;
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            upload();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progress.setMessage("Wait..");
                                    progress.show();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                t1.start();

                bm = MediaStore.Images.Media.getBitmap(editprofileActivty.this.getContentResolver(), data.getData());

                if(upload_type.equals("cover")){
                    cover_image.setImageBitmap(bm);
                }else{
                    profile_img1.setImageBitmap(bm);
                    profile_img2.setImageBitmap(bm);
                }

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

    private void imageUploadEdit(final String imagepath_url) {



        String tag_string_req = "req_img_upld";


        StringRequest strReq1 = new StringRequest(Request.Method.POST, BASE_URL_edit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String result = response.toString();

//                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                Log.e("data upload",result);

                progress.dismiss();


                if(upload_type.equals("cover")){
                    SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("cover_image",imagepath_url);
                    Log.e("cover_image",imagepath_url);
                    editor.apply();
                }else{
                    SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("user_image",imagepath_url);
                    Log.e("user_image",imagepath_url);
                    editor.apply();
                }

                PersonUtils personUtils = new PersonUtils();

                try {


                    JSONObject jObj = new JSONObject(response);
                    String success = jObj.getString("success");


                    if (success.equals("1")) {

                        //  Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_LONG).show();
//                        JSONObject user = jObj.getJSONObject("post");

                    } else if (success.equals("0"))

                    {
                        Toast.makeText(getApplicationContext(), "something went wrong! Try again", Toast.LENGTH_LONG).show();

                    }


                } catch (JSONException e) {

                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener()

        {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "Img Upload Error: " + error.getMessage());
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
                Log.e("map data",user_id+","+token+"-"+group_id+"-"+cover_img+","+imagepath_url+","+username+",");
                params.put("user_id", user_id);
                params.put("token", token);
                params.put("group_id", group_id);

                if(upload_type.equals("cover")){
                    params.put("user_image", "");
                    params.put("cover_image", imagepath_url);
                }else{
                    params.put("user_image", imagepath_url);
                    params.put("cover_image", "");
                }
                params.put("user_name", "");

                return params;

            }

        };

        AppController.getInstance().addToRequestQueue(strReq1, tag_string_req);

    }



    private void UsernameEdit(final String user_name) {



        String tag_string_req = "req_img_upld";


        StringRequest strReq1 = new StringRequest(Request.Method.POST, BASE_URL_edit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String result = response.toString();

//                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                Log.e("data upload",result);

                progress.dismiss();

                    SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("user_name",user_name);
                    Log.e("user_name",user_name);
                    editor.apply();

                et_name.setVisibility(View.GONE);
                tv_username.setVisibility(View.VISIBLE);
                tv_username.setText(user_name);

                PersonUtils personUtils = new PersonUtils();

                try {


                    JSONObject jObj = new JSONObject(response);
                    String success = jObj.getString("success");

                    if (success.equals("1")) {

                        //  Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_LONG).show();
//                        JSONObject user = jObj.getJSONObject("post");

                    } else if (success.equals("0"))

                    {
                        Toast.makeText(getApplicationContext(), "something went wrong! Try again", Toast.LENGTH_LONG).show();

                    }


                } catch (JSONException e) {

                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener()

        {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "Img Upload Error: " + error.getMessage());
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
//                Log.e("map data",user_id+","+token+"-"+group_id+"-"+cover_img+","+imagepath_url+","+username+",");
                params.put("user_id", user_id);
                params.put("token", token);
                params.put("group_id", group_id);
                params.put("user_image", "");
                params.put("cover_image", "");
                Log.e("user_name",user_name);
                params.put("user_name", user_name);

                return params;

            }

        };

        AppController.getInstance().addToRequestQueue(strReq1, tag_string_req);

    }



}
