package com.aafilogic.fc6;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aafilogic.fc6.activity.AppController;
import com.aafilogic.fc6.activity.Config;
import com.aafilogic.fc6.activity.PersonUtils;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class Comment_on_Post extends AppCompatActivity {

    private static final String TAG_T = "Comment Activity";

    public String post_id, post_id_n, cmnt_box_string, user_id, token, like_count, like_status;


    public TextView tv_post_text, tv_post_user_name, tv_post_time, tv_like_count, tv_comment_count, discp, quantity;
    public EditText et_cmntbox;
    //   public TextView pJobProfile;
    ImageView img_post_image, post_user_image, like_icon, comment_icon, img_post_cmnt;
    RecyclerView recyclerView, recyclerView2, recyclerView3;


    RecyclerView.Adapter mAdapter, mAdapter2, mAdapter3;
    String[] separated;
    int count = 0;
    StoriesProgressView storiesProgressView;
    List<PersonUtils>  personUtilsList2;
    private GestureDetector gdt;
    RecyclerView.LayoutManager layoutManager;
    PersonUtils pu;
    RelativeLayout imagelayout;
    String pos_person="0";
    String post_image;
    int counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_on__post);


        tv_post_text = (TextView) findViewById(R.id.post_text_data);
        tv_post_user_name = (TextView) findViewById(R.id.post_user_name);
        tv_post_time = (TextView) findViewById(R.id.post_time);
        tv_like_count = (TextView) findViewById(R.id.like_count);
        tv_comment_count = (TextView) findViewById(R.id.comment_count);
        storiesProgressView = (StoriesProgressView) findViewById(R.id.stories);

        img_post_image = (ImageView) findViewById(R.id.postimage);
        post_user_image = (ImageView) findViewById(R.id.postuserimage);
        like_icon = (ImageView) findViewById(R.id.like_icon);
        comment_icon = (ImageView) findViewById(R.id.comment_icon);
        img_post_cmnt = (ImageView) findViewById(R.id.post_cmnt);
        et_cmntbox = (EditText) findViewById(R.id.cmnt_edittext);
        imagelayout=(RelativeLayout)findViewById(R.id.imagelayout);

        findViewById(R.id.layoutfirstclosssign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        try {


            Intent intent = getIntent();

            post_id = intent.getStringExtra("post_id");
            pos_person=intent.getStringExtra("array_position");

            recyclerView2 = (RecyclerView) findViewById(R.id.user_comment);
            recyclerView2.setHasFixedSize(true);


            layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


            personUtilsList2 = new ArrayList<>();


            SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);

            user_id = pref.getString("user_id", "");
            token = pref.getString("token", "");


            // final ProgressDialog loading = ProgressDialog.show(getApplicationContext(), "Please wait...", "Fetching measurements...", false, true);


            String tag_string_req = "req_POST";

            //   String url = "https://go1shop.com/api/get_deshboard.php";
            String url = "https://www.aafilogicinfotech.com/demo/FC6/api/get_single_post.php";


            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String result = response.toString();
                    // loading.dismiss();


                    // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


                    try {


                        JSONObject jObj = new JSONObject(response);


                        String success = jObj.getString("Success");

                        JSONObject post = jObj.getJSONObject("post");

                        JSONObject inner_post = post.getJSONObject("post");


                        {


                            post_id_n = inner_post.getString("post_id");

                            String user_image = inner_post.getString("user_image");
                            String user_name = inner_post.getString("user_name");
                            String post_add_time = inner_post.getString("post_add_time");
                            post_image = inner_post.getString("post_image");
                            String post_text = inner_post.getString("post_text");
                            like_count = inner_post.getString("like_count");
                            like_status = inner_post.getString("like_status");
                            //  String comment_count=  inner_post.getString("comment_count");


                            try {

                                SimpleDateFormat dateFormatParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


                                DateFormat targetFormat = new SimpleDateFormat("EE MMM dd yyyy hh:mm:ss aa ", Locale.ENGLISH);

                                Date dateString = dateFormatParse.parse(post_add_time);

                                String formattedDate = targetFormat.format(dateString);

//                            holder.post_time.setText(String.valueOf(formattedDate));

                                tv_post_time.setText(String.valueOf(formattedDate));
                            }catch (Exception e){

                            }
                            tv_post_user_name.setText(user_name);
                            tv_post_text.setText(post_text);


                            if (!like_count.equals("0")) {

                                tv_like_count.setText(like_count);


                            }


                            if (like_status.equals("1")) {

                                like_icon.setImageResource(R.drawable.likefly);


                            }


                            if (!post_image.equals("")) {

                                img_post_image.setMaxHeight(200);

                            }

                            if(post_image.length()>2){
//                                img_post_image.setBackgroundColor(0x7F000000);
                                try{

                                   GestureDetector gdt = new GestureDetector(new GestureListener());


                                    String[] imageArray = post_image.split(",");
                                    for(String name : imageArray){
                                        System.out.println(name);
                                    }
                                    final ArrayList<String> namesList = new ArrayList<>(Arrays.asList(imageArray));

                                    imagelayout.setVisibility(View.VISIBLE);

                                    storiesProgressView.setVisibility(View.VISIBLE);
                                    storiesProgressView.setStoriesCount(imageArray.length); // <- set stories
                                    storiesProgressView.setStoryDuration(6200L); // <- set a story duration
//                                    storiesProgressView.setStoriesListener(Comment_on_Post.this); // <- set listener


                                    int counter=0;

                                    loadimage(namesList,img_post_image,storiesProgressView,counter);


                                }catch (Exception e){


                                    e.printStackTrace();
                                }

                            }

                            JSONArray jsonArray = post.getJSONArray("comment");


                            for (int i = 0; i < jsonArray.length(); i++) {

                                PersonUtils personUtils = new PersonUtils();

                                JSONObject jsonQuiz = jsonArray.getJSONObject(i);

                                personUtils.setComment_img(jsonQuiz.getString("user_image"));
                                personUtils.setCommenter_name(jsonQuiz.getString("user_name"));
                                personUtils.setComment_text(jsonQuiz.getString("comment_text"));
                                personUtils.setUserid(jsonQuiz.getString("user_id"));
                                personUtils.setComment_id(jsonQuiz.getString("comment_id"));


                                personUtilsList2.add(personUtils);


                            }


                            mAdapter2 = new CommentAdapter(getApplicationContext(), personUtilsList2);
                            recyclerView2.setAdapter(mAdapter2);
                            mAdapter2.notifyDataSetChanged();


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
                    params.put("post_id", post_id);


                    return params;


                }


            };


            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


        } catch (Exception e) {


            e.printStackTrace();
        }


        recyclerView2.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView2, new ClickListener() {

            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well

                 pu = personUtilsList2.get(position);


            }


            @Override
            public void onLongClick(View view, int position) {


                pu = personUtilsList2.get(position);

                Toast.makeText(Comment_on_Post.this, "cat id  :" + pu.getComment_id(),
                        Toast.LENGTH_LONG).show();



            }

        }));


        img_post_cmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                cmnt_box_string = et_cmntbox.getText().toString().trim();


                try {


                    String tag_string_req = "req_POST";

                    //   String url = "https://go1shop.com/api/get_deshboard.php";
                    String url = "https://www.aafilogicinfotech.com/demo/FC6/api/comment_on_post.php";


                    StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = response.toString();



                            try {

                                et_cmntbox.setText("");

                                load_comment();


                                JSONObject jObj = new JSONObject(response);


                            } catch (JSONException e) {
                                // JSON error
                                e.printStackTrace();
                                load_comment();
                                Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }


                        }
                    }, new Response.ErrorListener()

                    {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG_T, "Post Loading Error: " + error.getMessage());
//                            Toast.makeText(getApplicationContext(),
//                                    error.getMessage(), Toast.LENGTH_LONG).show();
                            et_cmntbox.setText("");
                            load_comment();
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
                            params.put("post_id", post_id);
                            params.put("comment_text", cmnt_box_string);


                            return params;


                        }


                    };


                    // Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


                } catch (Exception e) {


                    e.printStackTrace();
                }


            }
        });




        like_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {


                    if (like_status.equals("1")) {


                        like_icon.setImageResource(R.drawable.like);
                        int l_cnt = Integer.valueOf(like_count);
                        int lcnt_m = l_cnt--;
                        lcnt_m--;


                        if (!String.valueOf(lcnt_m).equals("0")) {

                            tv_like_count.setText(String.valueOf(lcnt_m));


                        } else if (lcnt_m < 1) {


                            tv_like_count.setText("");


                        }

                        like_status = "0";
                        like_count = String.valueOf(lcnt_m);

                        pu.setLike_count(String.valueOf(lcnt_m));
                        Comment_on_Post.this.notify();


                    } else {


                        like_icon.setImageResource(R.drawable.likefly);

                        int l_cntmn = Integer.valueOf(like_count);
                        int lcnt_mp = l_cntmn++;
                        lcnt_mp++;
                        tv_like_count.setText(String.valueOf(lcnt_mp));
                        like_status = "1";

                        like_count = String.valueOf(lcnt_mp);


                    }


                    SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);

                    user_id = pref.getString("user_id", "");
                    token = pref.getString("token", "");


                    String tag_string_req = "req_POST";

                    String url = "https://www.aafilogicinfotech.com/demo/FC6/api/like_dislike_post.php";


                    StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = response.toString();
                            // loading.dismiss();


                            //  Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


                            try {


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
                            params.put("post_id", post_id);


                            return params;


                        }


                    };


                    // Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


                } catch (Exception e) {


                    e.printStackTrace();
                }


            }
        });

        load_comment();

        View reverse = findViewById(R.id.reverse);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.reverse();
            }
        });
        reverse.setOnTouchListener(onTouchListener);

        // bind skip view
        View skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.skip();
            }
        });
        skip.setOnTouchListener(onTouchListener);
    }

    long pressTime = 0L;
    long limit = 500L;

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pressTime = System.currentTimeMillis();
                    storiesProgressView.pause();
                    return false;
                case MotionEvent.ACTION_UP:
                    long now = System.currentTimeMillis();
                    storiesProgressView.resume();
                    return limit < now - pressTime;
            }
            return false;
        }
    };


    public void load_comment() {


        try {


            recyclerView2 = (RecyclerView) findViewById(R.id.user_comment);
            recyclerView2.setHasFixedSize(true);


            layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


            personUtilsList2 = new ArrayList<>();


            SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);

            user_id = pref.getString("user_id", "");
            token = pref.getString("token", "");


            // final ProgressDialog loading = ProgressDialog.show(getApplicationContext(), "Please wait...", "Fetching measurements...", false, true);


            String tag_string_req = "req_POST";

            //   String url = "https://go1shop.com/api/get_deshboard.php";
            String url = "https://www.aafilogicinfotech.com/demo/FC6/api/get_single_post.php";


            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String result = response.toString();
                    // loading.dismiss();


                    // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


                    try {


                        JSONObject jObj = new JSONObject(response);


                        String success = jObj.getString("Success");

                        JSONObject post = jObj.getJSONObject("post");

                        JSONObject inner_post = post.getJSONObject("post");


                        {


                            JSONArray jsonArray = post.getJSONArray("comment");


                            for (int i = 0; i < jsonArray.length(); i++) {

                                PersonUtils personUtils = new PersonUtils();

                                JSONObject jsonQuiz = jsonArray.getJSONObject(i);

                                personUtils.setComment_img(jsonQuiz.getString("user_image"));
                                personUtils.setCommenter_name(jsonQuiz.getString("user_name"));
                                personUtils.setComment_text(jsonQuiz.getString("comment_text"));
                                personUtils.setUserid(jsonQuiz.getString("user_id"));
                                personUtils.setComment_id(jsonQuiz.getString("comment_id"));


                                personUtilsList2.add(personUtils);


                            }


                            mAdapter2 = new CommentAdapter(getApplicationContext(), personUtilsList2);
                            recyclerView2.setAdapter(mAdapter2);


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
                    params.put("post_id", post_id);


                    return params;


                }


            };


            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


        } catch (Exception e) {


            e.printStackTrace();
        }


    }


    public void back(View v) {


        finish();

    }


    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }


    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener) {

            this.clicklistener = clicklistener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    public void downloadimage(final StoriesProgressView storiesProgressView, final ArrayList<String> post_image, final ImageView post_image1, int mycounter){


        if(mycounter<post_image.size()) {
            if(!(post_image.get(mycounter).isEmpty())) {

                Picasso.with(Comment_on_Post.this).load("https://www.aafilogicinfotech.com/demo/FC6/api/"+post_image.get(mycounter)).into(post_image1, new Callback() {

                    @Override
                    public void onSuccess() {
                        storiesProgressView.resume();

                        //  startstoriesbar(storiesProgressView,post_image,post_image1);
                        //storiesProgressView.resume();
                    }

                    @Override
                    public void onError() {
//                        storiesProgressView.resume();

                    }
                });

            }else{
                storiesProgressView.resume();
            }

        }else{
            storiesProgressView.resume();
        }

    }


    public void startstoriesbar(final StoriesProgressView storiesProgressView, final ArrayList<String> post_image, final ImageView post_image1,final int mycounter) {


        int co = mycounter;
//        co++;


        storiesProgressView.setStoriesListener(new StoriesProgressView.StoriesListener() {
            int co = mycounter;


            @Override

            public void onNext() {
//                co++;

                //   storiesProgressView.pause();/
                //
                //
                // Toast.makeText(v.getContext(),String.valueOf(storiesProgressView.getChildCount()),Toast.LENGTH_SHORT).show();
                storiesProgressView.resume();
                downloadimage(storiesProgressView, post_image, post_image1, ++co);
//                Toast.makeText(Comment_on_Post.this, String.valueOf(co)+"On Next call", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPrev() {

//                    Toast.makeText(Comment_on_Post.this, "On previous call", Toast.LENGTH_SHORT).show();
                    if(co>0) {

                        downloadimage(storiesProgressView, post_image, post_image1, --co);
//                        Picasso.with(Comment_on_Post.this).load(("https://www.aafilogicinfotech.com/demo/FC6/api/" + post_image.get(--co)).toString()).into(post_image1);
                }
            }


            @Override
            public void onComplete() {

//                    img_post_image.setBackgroundColor(0x7F000000);
                    try{

                        imagelayout.setVisibility(View.VISIBLE);

                        storiesProgressView.setVisibility(View.VISIBLE);
                        storiesProgressView.setStoriesCount(post_image.size()); // <- set stories
                        storiesProgressView.setStoryDuration(6200L); // <- set a story duration
//                                    storiesProgressView.setStoriesListener(Comment_on_Post.this); // <- set listener




                        loadimage(post_image,img_post_image,storiesProgressView,counter);


                    }catch (Exception e){


                        e.printStackTrace();
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

            Picasso.with(Comment_on_Post.this).load("https://www.aafilogicinfotech.com/demo/FC6/api/"+imagepath).into(post_image1, new com.squareup.picasso.Callback() {

                @Override
                public void onSuccess() {

                    startstoriesbar(storiesProgressView,post_image,post_image1,mycounter);
                    storiesProgressView.startStories();

                }


                @Override
                public void onError() {

//                    Toast.makeText(v.getContext(),"Something wentwrong ",Toast.LENGTH_SHORT).show();

//                    startstoriesbar(storiesProgressView,post_image,post_image1, mycounter);
//                    storiesProgressView.startStories();


                }
            });



        }
        gdt = new GestureDetector(new GestureListener());
        post_image1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getActionMasked();
                gdt.onTouchEvent(event);
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
    private static final int MIN_SWIPPING_DISTANCE = 50;
    private static final int THRESHOLD_VELOCITY = 50;
    private class GestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            if (e1.getX() - e2.getX() > MIN_SWIPPING_DISTANCE && Math.abs(velocityX) > THRESHOLD_VELOCITY)
            {
                Toast.makeText(getApplicationContext(), "You have swipped left side", Toast.LENGTH_SHORT).show();
                /* Code that you want to do on swiping left side*/


                return false;
            }
            else if (e2.getX() - e1.getX() > MIN_SWIPPING_DISTANCE && Math.abs(velocityX) > THRESHOLD_VELOCITY)
            {
                Toast.makeText(getApplicationContext(), "You have swipped right side", Toast.LENGTH_SHORT).show();
                /* Code that you want to do on swiping right side*/
                return false;
            }
            return false;
        }
    }


}


