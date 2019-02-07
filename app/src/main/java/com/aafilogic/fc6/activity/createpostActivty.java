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
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.adapter.badges.badgesAdapterMenu;
import com.aafilogic.fc6.dto.badgesDto;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class createpostActivty extends AppCompatActivity implements StoriesProgressView.StoriesListener{

    private static final String TAG_T = "Create_post Activity";
    EditText et_thought_text2;
    String timeline_msg, user_id, token, group_id;
    ImageView user_img,post_image;
    TextView tv_username,chooseimage,chooseimage_camera;
    String[] photoarray;
    private static final int REQUEST_CODE = 732;
    private ArrayList<String> mResults = new ArrayList<>();
    String BASE_URL = "https://www.aafilogicinfotech.com/demo/FC6/api/image_multipart.php";
    String user_image="";
    StoriesProgressView storiesProgressView ;
    RelativeLayout imagelayout;
    String[] badges, badges_type;
    private int REQUEST_CAMERA = 0;
    ProgressDialog progress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createpostlayout);


        et_thought_text2 = (EditText) findViewById(R.id.thoughtext2);
        user_img = (ImageView) findViewById(R.id.profile_image2);
        tv_username = (TextView) findViewById(R.id.username);
        chooseimage=(TextView)findViewById(R.id.chooseimage);
        chooseimage_camera=(TextView)findViewById(R.id.chooseimage_camera);
        storiesProgressView = (StoriesProgressView) findViewById(R.id.stories);
        post_image = (ImageView) findViewById(R.id.postimage);
         progress = new ProgressDialog(this);
        chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start multiple photos selector
                Intent intent = new Intent(createpostActivty.this, ImagesSelectorActivity.class);
                // max number of images to be selected
                intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 4);
                // min size of image which will be shown; to filter tiny images (mainly icons)
                intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
                // show camera or not
                intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
                // pass current selected images as the initial value
                intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
                // start the selector
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        chooseimage_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraIntent();

            }
        });

try {
    Context context = this;

    SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);

    String user_image = pref.getString("user_image", "");
    final String cover_img = pref.getString("cover_image", "");
    final String token = pref.getString("token", "");
    final String username = pref.getString("user_name", "");
    final String workout = pref.getString("total_workout", "");
    final String meals = pref.getString("total_meals", "");
    final String badges1 = pref.getString("badges1", "");

    Log.e("badges", badges1);
//            Log.e("badges_type",badges_type);

    List<badgesDto> badgesDtoList = new ArrayList<badgesDto>();

    JSONArray arr_badge=new JSONArray(badges1.toString());
    badges_type=new String[arr_badge.length()];
    badges=new String[arr_badge.length()];
    int badge_length=0;
    if(arr_badge.length()>3){
        badge_length =3;
    }else{
        badge_length=arr_badge.length();
    }
    for (int i = 0; i < badge_length; i++) {

        JSONObject jsonQuiz = arr_badge.getJSONObject(i);

        badges_type[i] = jsonQuiz.getString("type");
        Log.e("badges",badges_type[i]);
        badges[i] = jsonQuiz.getString("badge");
        Log.e("badges",badges[i]);

        if( badges_type[i].equals("W")){
            badgesDtoList.add(new badgesDto("https://cdn6.aptoide.com/imgs/e/6/f/e6fbcf2c74b0e34d5092e424c9d34190_icon.png?w=240", "WORKOUT"+"_"+ badges[i]));
        }else if( badges_type[i].equals("M")){
            badgesDtoList.add(new badgesDto("https://cdn6.aptoide.com/imgs/e/6/f/e6fbcf2c74b0e34d5092e424c9d34190_icon.png?w=240", "MEALS"+"_"+ badges[i]));

        }

    }

    if(arr_badge.length()<3){
        for (int i = 0; i < 3-arr_badge.length(); i++) {
            badgesDtoList.add(new badgesDto("https://cdn6.aptoide.com/imgs/e/6/f/e6fbcf2c74b0e34d5092e424c9d34190_icon.png?w=240", "."+"_"+ "..."));

        }
    }


    final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.badgesDetails);

    badgesAdapterMenu badgesAdapter = new badgesAdapterMenu(badgesDtoList, context);
    final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(badgesAdapter);


    findViewById(R.id.layoutfirstclosssign).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    });


    recyclerviewscrool(mLayoutManager, recyclerView);

}catch (Exception e){

}

        try {


            SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);

            String user_image = pref.getString("user_image", "");
            user_id = pref.getString("user_id", "");
            token = pref.getString("token", "");
            group_id = pref.getString("group_id", "");


            final String username = pref.getString("user_name", "");

            Picasso.with(getApplicationContext()).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + user_image).placeholder( R.drawable.progress_animation ).into(user_img);

            tv_username.setText(username);


        } catch (Exception e) {


            e.printStackTrace();
        }


    }


    public void recyclerviewscrool(final RecyclerView.LayoutManager mLayoutManager, RecyclerView recyclerView) {
        final LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 800 / displayMetrics.densityDpi;
            }
        };


//        findViewById(R.id.leftarrow).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getBaseContext(), "left arrow ", Toast.LENGTH_LONG);
//
//
//                if (((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition() > 0) {
//
//                    //recyclerView.smoothScrollToPosition();
//                    linearSmoothScroller.setTargetPosition(((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition() - 1);
//                    mLayoutManager.startSmoothScroll(linearSmoothScroller);
//
//                } else {
//                    // recyclerView.smoothScrollToPosition(0);
//                    linearSmoothScroller.setTargetPosition(0);
//                    mLayoutManager.startSmoothScroll(linearSmoothScroller);
//
//                }
//
//
//            }
//        });
//
//        findViewById(R.id.rigtarrow).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                //  recyclerView.smoothScrollToPosition(((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition() +1);
//                linearSmoothScroller.setTargetPosition(((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition() + 1);
//
//                mLayoutManager.startSmoothScroll(linearSmoothScroller);
//
//            }
//        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // get selected images from selector
        if(requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                assert mResults != null;

                // show results in textview
                StringBuilder sb = new StringBuilder();
//                sb.append(String.format("Totally %d images selected:", mResults.size())).append("\n");
                for(String result : mResults) {
                    sb.append(result).append(",");
                }
//                tvResults.setText(sb.toString());

                Log.e("image_path",sb.toString());

                photoarray = sb.toString().split(",");

                for(int i=0;i<photoarray.length;i++){
                    Log.e("user_image"+String.valueOf(i),photoarray[i]);
                }

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
            }
        }

        else if (requestCode == REQUEST_CAMERA) {
            onCaptureImageResult(data);
        }



        super.onActivityResult(requestCode, resultCode, data);
    }
    String imgFile;
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        Uri tempUri = getImageUri(getApplicationContext(), thumbnail);

//        Uri selectedImageUri = data.getData( );
        imgFile = getPath(getApplicationContext(), tempUri);
        Log.d("Picture Path", imgFile);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        photoarray = imgFile.split(",");

        for(int i=0;i<photoarray.length;i++){
            Log.e("user_image"+String.valueOf(i),photoarray[i]);
        }

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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    public void upload() throws Exception {
        //Url of the server
        String url = BASE_URL;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                url);

        try {
            MultipartEntity entity = new MultipartEntity();

            entity.addPart("count", new StringBody(String.valueOf(photoarray.length)));

            for(int i=0;i<photoarray.length;i++){
                Log.e("user_image"+String.valueOf(i),photoarray[i]);
                File file = new File(photoarray[i]);
                entity.addPart("user_image"+String.valueOf(i), new FileBody(file));
            }

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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(user_image.toString().length()>2){
                        post_image.setBackgroundColor(0x7F000000);
                        try{
                            final GestureDetector gdt = new GestureDetector(new GestureListener());
                            String[] imageArray = user_image.split(",");
                            for(String name : imageArray){
                                System.out.println(name);
                            }
                            final ArrayList<String> namesList = new ArrayList<>(Arrays.asList(imageArray));


                            storiesProgressView.setVisibility(View.VISIBLE);
                            storiesProgressView.setStoriesCount(imageArray.length); // <- set stories
                            storiesProgressView.setStoryDuration(6200L); // <- set a story duration
                            storiesProgressView.setStoriesListener(createpostActivty.this); // <- set listener

                            int counter=0;

                            loadimage(namesList,post_image,storiesProgressView,counter);


                        }catch (Exception e){


                            e.printStackTrace();
                        }

                    }

    }
});



        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }
    }




    public void timeline_post(View v) {

        try {


            timeline_msg = et_thought_text2.getText().toString().trim();

            if (timeline_msg.isEmpty() && user_image.equals("")) {
                Toast.makeText(createpostActivty.this, "Please write message or upload image", Toast.LENGTH_LONG).show();
            } else {

                progress.setMessage("Wait..");
                progress.show();
                try {


                    // final ProgressDialog loading = ProgressDialog.show(getApplicationContext(), "Please wait...", "Fetching measurements...", false, true);


                    String tag_string_req = "req_POST";

                    //   String url = "https://go1shop.com/api/get_deshboard.php";
                    String url = "https://www.aafilogicinfotech.com/demo/FC6/api/create_new_post.php";


                    StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = response.toString();
                            // loading.dismiss();
                            progress.dismiss();
                            finish();

//                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


                            try {

                                et_thought_text2.setText("");


                                JSONObject jObj = new JSONObject(response);


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
                            params.put("post_type", "T");
                            params.put("post_text", timeline_msg);
                            params.put("group_id", group_id);
                            params.put("post_image", user_image);

                            return params;
                        }
                    };
                    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


                } catch (Exception e) {


                    e.printStackTrace();
                }
            }

            } catch(Exception e){


                e.printStackTrace();
            }



    }


    public void meal_post(View v) {


        try {


            timeline_msg = et_thought_text2.getText().toString().trim();
            if (timeline_msg.isEmpty() && user_image.equals("")) {
                Toast.makeText(createpostActivty.this, "Please write message or upload image", Toast.LENGTH_LONG).show();
            } else {
                progress.setMessage("Wait..");
                progress.show();

                try {


                    // final ProgressDialog loading = ProgressDialog.show(getApplicationContext(), "Please wait...", "Fetching measurements...", false, true);


                    String tag_string_req = "req_POST";

                    //   String url = "https://go1shop.com/api/get_deshboard.php";
                    String url = "https://www.aafilogicinfotech.com/demo/FC6/api/create_new_post.php";


                    StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = response.toString();
                            // loading.dismiss();
                            progress.dismiss();
                            finish();

//                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


                            try {

                                et_thought_text2.setText("");


                                JSONObject jObj = new JSONObject(response);


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
                            params.put("post_type", "M");
                            params.put("post_text", timeline_msg);
                            params.put("group_id", group_id);
                            params.put("post_image", user_image);


                            return params;


                        }


                    };


                    // Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


                } catch (Exception e) {


                    e.printStackTrace();
                }
            }

        } catch (Exception e) {


            e.printStackTrace();
        }


    }


//    "post_image":"..\/admin\/images\/8714_15475009105522.jpg,..\/admin\/images\/3470_15475009108940.jpg"

    public void workout_post(View v) {


        try {


            timeline_msg = et_thought_text2.getText().toString().trim();
            if (timeline_msg.isEmpty() && user_image.equals("")) {
                Toast.makeText(createpostActivty.this, "Please write message or upload image", Toast.LENGTH_LONG).show();
            } else {
                progress.setMessage("Wait..");
                progress.show();

                try {


                    // final ProgressDialog loading = ProgressDialog.show(getApplicationContext(), "Please wait...", "Fetching measurements...", false, true);


                    String tag_string_req = "req_POST";

                    //   String url = "https://go1shop.com/api/get_deshboard.php";
                    String url = "https://www.aafilogicinfotech.com/demo/FC6/api/create_new_post.php";


                    StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = response.toString();
                            // loading.dismiss();
                            progress.dismiss();
                            finish();

//                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


                            try {

                                et_thought_text2.setText("");


                                JSONObject jObj = new JSONObject(response);

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
                            params.put("post_type", "W");
                            params.put("post_text", timeline_msg);
                            params.put("group_id", group_id);
                            params.put("post_image", user_image);


                            return params;


                        }


                    };


                    // Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


                } catch (Exception e) {


                    e.printStackTrace();
                }
            }

        } catch (Exception e) {


            e.printStackTrace();
        }


    }

    public void downloadimage(final StoriesProgressView storiesProgressView, final ArrayList<String> post_image, final ImageView post_image1, int mycounter){


        if(mycounter<post_image.size()) {
            if(!(post_image.get(mycounter).isEmpty())) {

                Picasso.with(createpostActivty.this).load("https://www.aafilogicinfotech.com/demo/FC6/api/"+post_image.get(mycounter)).placeholder( R.drawable.progress_animation ).into(post_image1, new Callback() {

                    @Override
                    public void onSuccess() {
                        storiesProgressView.resume();

                        //  startstoriesbar(storiesProgressView,post_image,post_image1);
                        //storiesProgressView.resume();
                    }

                    @Override
                    public void onError() {
                        storiesProgressView.resume();

                    }
                });

            }else{
                storiesProgressView.resume();
            }

        }else{
            storiesProgressView.resume();
        }

    }


    public void startstoriesbar(final StoriesProgressView storiesProgressView, final ArrayList<String> post_image, final ImageView post_image1,final int mycounter){



        int co=mycounter;
        co++;

        final int finalCo = co;



        storiesProgressView.setStoriesListener(new StoriesProgressView.StoriesListener() {
            int co=mycounter;


            @Override

            public void onNext() {
                co++;

                //   storiesProgressView.pause();/
                //
                //
                // Toast.makeText(v.getContext(),String.valueOf(storiesProgressView.getChildCount()),Toast.LENGTH_SHORT).show();
                storiesProgressView.resume();
                downloadimage(storiesProgressView,post_image,post_image1, co);

            }

            @Override
            public void onPrev() {
//                if(counter<post_image.size()) {
//                    if (!(post_image.get(counter).isEmpty())) {
//
//                        Picasso.with(v.getContext()).load(("https://www.aafilogicinfotech.com/demo/FC6/api/" + post_image.get(--counter)).toString()).into(post_image1);
//                    }
//                }
                Toast.makeText(createpostActivty.this,"On previous call",Toast.LENGTH_SHORT).show();

            }


            @Override
            public void onComplete() {


            }
        });


//        post_image1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(counter<post_image.size()) {
//                    if (!(post_image.get(counter).isEmpty())) {
//                        Picasso.with(v.getContext()).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + post_image.get(--counter)).into(post_image1);
//                        storiesProgressView.resume();
//                    }
//                }
//            }
//
//
//        });


        post_image1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getActionMasked();

                switch(action) {
                    case (MotionEvent.ACTION_DOWN) :
                        storiesProgressView.pause();
                        return true;
                    case (MotionEvent.ACTION_MOVE) :
                        storiesProgressView.pause();
                        return true;
                    case (MotionEvent.ACTION_UP) :
                        storiesProgressView.resume();
                        return true;
                    case (MotionEvent.ACTION_CANCEL) :
                        storiesProgressView.resume();
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE) :
                        storiesProgressView.resume();
                        return true;
                    default :
                        return false;
                }

            }
        });


    }



    public void loadimage(final ArrayList<String> post_image, final ImageView post_image1, final StoriesProgressView storiesProgressView, int counter){

        final int mycounter=counter++;
        //   Toast.makeText(v.getContext(),"Total Images :counter "+counter+String.valueOf(post_image.size()),Toast.LENGTH_SHORT).show();
        Log.d("Image no : ",String.valueOf(counter));

        counter=0;
        if(!post_image.get(counter).isEmpty()) {
            final String imagepath=(post_image.get(counter));

            Picasso.with(createpostActivty.this).load("https://www.aafilogicinfotech.com/demo/FC6/api/"+imagepath).placeholder( R.drawable.progress_animation ).into(post_image1, new com.squareup.picasso.Callback() {

                @Override
                public void onSuccess() {

                    startstoriesbar(storiesProgressView,post_image,post_image1,mycounter);
                    storiesProgressView.startStories();

                }


                @Override
                public void onError() {

//                    Toast.makeText(v.getContext(),"Something wentwrong ",Toast.LENGTH_SHORT).show();

                    startstoriesbar(storiesProgressView,post_image,post_image1, mycounter);
                    storiesProgressView.startStories();


                }
            });
        }

    }
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Toast.makeText(createpostActivty.this,"Right to left ",Toast.LENGTH_SHORT).show();

                return false; // Right to left
            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Toast.makeText(createpostActivty.this," left to right ",Toast.LENGTH_SHORT).show();
                return false; // Left to right
            }

            if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

                Toast.makeText(createpostActivty.this,"bottom to up ",Toast.LENGTH_SHORT).show();
                return false; // Bottom to top
            }  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                Toast.makeText(createpostActivty.this,"top to bottom",Toast.LENGTH_SHORT).show();

                return false; // Top to bottom
            }
            return false;
        }
    }


    @Override
    public void onNext() {

    }

    @Override
    public void onPrev() {

    }

    @Override
    public void onComplete() {

    }
}
