package com.aafilogic.fc6;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aafilogic.fc6.activity.AppController;
import com.aafilogic.fc6.activity.Config;
import com.aafilogic.fc6.activity.PersonUtils;
import com.aafilogic.fc6.fragment.LikeUserFragment;
import com.aafilogic.fc6.fragment.comment_datastart;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.plattysoft.leonids.ParticleSystem;
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


public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {

    private static final String TAG_T = "Measurement Activity";
    public List<PersonUtils> personUtils,personUtils2;
    private comment_datastart mCallBack;
    public String user_id, token;
    String[] separated;
    int count = 0;
    private Context context;
    View v;
    int pos=0;
    int co=0;

    private Fragment fragment;



    public CustomRecyclerAdapter(Context context, List personUtils,comment_datastart data) {
        this.context = context;
        this.personUtils = personUtils;
        mCallBack=data;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.userpostdetails, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    public void downloadimage(final StoriesProgressView storiesProgressView, final ArrayList<String> post_image, final ImageView post_image1, int mycounter){


        if(mycounter<post_image.size()) {
            if(!(post_image.get(mycounter).isEmpty())) {

                Picasso.with(context).load("https://www.aafilogicinfotech.com/demo/FC6/api/"+post_image.get(mycounter)).placeholder( R.drawable.progress_animation ).into(post_image1, new Callback() {

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

        co=mycounter;

        storiesProgressView.setStoriesListener(new StoriesProgressView.StoriesListener() {
            int co=mycounter;

            @Override

            public void onNext() {
                storiesProgressView.resume();
                downloadimage(storiesProgressView, post_image, post_image1, ++co);

            }

            @Override
            public void onPrev() {
                if(co>0) {
                    downloadimage(storiesProgressView, post_image, post_image1, --co);
                    }
            }


            @Override
            public void onComplete() {

                storiesProgressView.setVisibility(View.VISIBLE);
                storiesProgressView.setStoriesCount(post_image.size()); // <- set stories
                storiesProgressView.setStoryDuration(6200L); // <- set a story duration
                int counter=0;
                loadimage(post_image,post_image1,storiesProgressView,counter);

            }
        });


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

//        post_image1.setOnLongClickListener(new View.OnLongClickListener() {
//            Boolean ispause=false;
//
//            @Override
//            public boolean onLongClick(View v) {
//
//                if(ispause) {
//                    storiesProgressView.resume();
//                    ispause=false;
//                }else{
//                    ispause=true;
//                    storiesProgressView.pause();
//                }
//
//                return true;
//            }
//        });


    }



    public void loadimage(final ArrayList<String> post_image, final ImageView post_image1, final StoriesProgressView storiesProgressView, int counter){

        final int mycounter=counter++;
        //   Toast.makeText(v.getContext(),"Total Images :counter "+counter+String.valueOf(post_image.size()),Toast.LENGTH_SHORT).show();
        Log.d("Image no : ",String.valueOf(counter));

        counter=0;
        if(!post_image.get(counter).isEmpty()) {
            final String imagepath=(post_image.get(counter));

            Picasso.with(context).load("https://www.aafilogicinfotech.com/demo/FC6/api/"+imagepath).placeholder( R.drawable.progress_animation ).into(post_image1, new com.squareup.picasso.Callback() {

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
                Toast.makeText(v.getContext(),"Right to left ",Toast.LENGTH_SHORT).show();

                return false; // Right to left
            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Toast.makeText(v.getContext()," left to right ",Toast.LENGTH_SHORT).show();
                return false; // Left to right
            }

            if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

                Toast.makeText(v.getContext(),"bottom to up ",Toast.LENGTH_SHORT).show();
                return false; // Bottom to top
            }  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                Toast.makeText(v.getContext(),"top to bottom",Toast.LENGTH_SHORT).show();

                return false; // Top to bottom
            }
            return false;
        }
    }



    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setTag(personUtils.get(position));

        try {


            final SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF, 0);
            user_id = pref.getString("user_id", "");


            final PersonUtils pu = personUtils.get(position);


//            Date currDate = new Date();
//            DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
//            String strCurrDate = dateFormat.format(pu.getPost_add_time());

//            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
//            Date date = format.parse(pu.getPost_add_time());

            try {

                SimpleDateFormat dateFormatParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


                DateFormat targetFormat = new SimpleDateFormat("EE MMM dd yyyy hh:mm:ss aa ", Locale.ENGLISH);

                Date dateString = dateFormatParse.parse(pu.getPost_add_time());

                String formattedDate = targetFormat.format(dateString);

                holder.post_time.setText(String.valueOf(formattedDate));
            }catch (Exception e){
                Log.e("error",e.getMessage());
            }

            holder.post_user_name.setText(pu.getUser_name());
            holder.post_text.setText(pu.getPost_text());


            String login_user_id = "1";

            if (pu.getUserid().equals(user_id)) {


                // holder.like_icon.setImageResource(R.drawable.like);

                holder.delete_post_img.setVisibility(View.VISIBLE);


            } else {

                holder.delete_post_img.setVisibility(View.INVISIBLE);

            }


            if (!pu.getLike_count().equals("0")) {

                holder.like_count.setText(pu.getLike_count());

            }


            if (pu.getLike_status().equals("1")) {

                holder.like_icon.setImageResource(R.drawable.likefly);

            }


            if (!pu.getComment_count().equals("0")) {

                holder.comment_count.setText(pu.getComment_count());


            }

            Picasso.with(context).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + pu.getUser_image()).placeholder( R.drawable.progress_animation ).into(holder.post_user_image);


            if(pu.getPost_image().toString().length()>2){
//                holder.post_image.setBackgroundColor(0x7F000000);
                holder.post_image.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                try{

                    final GestureDetector gdt = new GestureDetector(new GestureListener());


                    String[] imageArray = pu.getPost_image().split(",");
                    for(String name : imageArray){
                        System.out.println(name);
                    }
                    final ArrayList<String> namesList = new ArrayList<>(Arrays.asList(imageArray));

                    holder.imagelayout.setVisibility(View.VISIBLE);

                    holder.storiesProgressView.setVisibility(View.VISIBLE);
                    holder.storiesProgressView.setStoriesCount(imageArray.length); // <- set stories
                    holder.storiesProgressView.setStoryDuration(6200L); // <- set a story duration
//                    holder.storiesProgressView.setStoriesListener(this); // <- set listener

                    int counter=0;

                    loadimage(namesList,holder.post_image,holder.storiesProgressView,counter);


                }catch (Exception e){


                    e.printStackTrace();
                }

            }





            holder.like_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    try {


                        if (pu.getLike_status().equals("1")) {


                            holder.like_icon.setImageResource(R.drawable.like);
                            int l_cnt = Integer.valueOf(pu.getLike_count());
                            int lcnt_m = l_cnt--;
                            lcnt_m--;

                            if (!String.valueOf(lcnt_m).equals("0")) {

                                holder.like_count.setText(String.valueOf(lcnt_m));

                            } else if (lcnt_m < 1) {


                                holder.like_count.setText("");


                            }


                            pu.setLike_status("0");

                            pu.setLike_count(String.valueOf(lcnt_m));

                        } else {


                            holder.like_icon.setImageResource(R.drawable.likefly);

                            int l_cntmn = Integer.valueOf(pu.getLike_count());
                            int lcnt_mp = l_cntmn++;

                            lcnt_mp++;
//                               Toast.makeText(context, String.valueOf(lcnt_mp), Toast.LENGTH_LONG).show();

                            holder.like_count.setText(String.valueOf(lcnt_mp));
                            pu.setLike_status("1");
                            pu.setLike_count(String.valueOf(lcnt_mp));

                            new ParticleSystem((Activity) context, 100, R.drawable.likefly, 5000)
                                    .setSpeedRange(0.1f, 0.25f)
                                    .setRotationSpeedRange(90, 180)
                                    .setInitialRotationRange(0, 360)
                                    .oneShot(v, 100);


                        }


                        notifyDataSetChanged();
                        token = pref.getString("token", "");


                        String tag_string_req = "req_POST";

                        String url = "https://www.aafilogicinfotech.com/demo/FC6/api/like_dislike_post.php";


                        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String result = response.toString();
                                // loading.dismiss();

                                notifyDataSetChanged();
                                Log.e("like response", result);


                                try {


                                    JSONObject jObj = new JSONObject(response);


                                } catch (JSONException e) {
                                    // JSON error
                                    e.printStackTrace();
                                    Toast.makeText(context, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }


                            }
                        }, new Response.ErrorListener()

                        {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG_T, "Post Loading Error: " + error.getMessage());
                                Toast.makeText(context,
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
                                params.put("post_id", pu.getPost_id());


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

            holder.like_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

//                        SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF, 0);
//                        SharedPreferences.Editor editor = pref.edit();
//                        editor.putString("visible_position", String.valueOf(pos));
//                        Log.e("visible_position", String.valueOf(pos));
//                        editor.apply();
                        Intent i2 = new Intent(context, LikeUserFragment.class);
                        i2.putExtra("post_id", pu.getPost_id());
                        i2.putExtra("helloString", "createpost");
                        context.startActivity(i2);



                    } catch (Exception e) {


                        e.printStackTrace();
                    }

                }
            });


            holder.delete_post_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    try {


                        token = pref.getString("token", "");


                        String tag_string_req = "req_POST";

                        String url = "https://www.aafilogicinfotech.com/demo/FC6/api/delete_post.php";


                        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String result = response.toString();
                                // loading.dismiss();
                                Log.e("like response", result);

                                //Toast.makeText(context, result, Toast.LENGTH_LONG).show();


                                try {


                                    JSONObject jObj = new JSONObject(response);

                                    String success = jObj.getString("Success");

                                     if(success.equals("1")){
                                         personUtils.remove(position);
                                         notifyItemRemoved(position);
                                         notifyDataSetChanged();
                                     }


                                     JSONArray jsonArray=jObj.getJSONArray("post");


                                     for(int i=0;i<jsonArray.length();i++) {

                                     PersonUtils personUtils = new PersonUtils();


                                     JSONObject jsonQuiz = jsonArray.getJSONObject(i);

//

                                     }


                                } catch (JSONException e) {
                                    // JSON error
                                    e.printStackTrace();
                                    Toast.makeText(context, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }


                            }
                        }, new Response.ErrorListener()

                        {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG_T, "Post Loading Error: " + error.getMessage());
                                Toast.makeText(context,
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
                                params.put("post_id", pu.getPost_id());
                                params.put("post_type", pu.getPost_type());

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

            pos=position;
            holder.reverse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.storiesProgressView.reverse();
                }
            });
            holder.skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.storiesProgressView.skip();
                }
            });

            holder.comment_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF, 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("visible_position", String.valueOf(pos));
                    Log.e("visible_position", String.valueOf(pos));
                    editor.apply();
                    Intent i2 = new Intent(context, Comment_on_Post.class);
                    i2.putExtra("post_id", pu.getPost_id());
                    i2.putExtra("helloString", "createpost");
                    mCallBack.startCommentActivity(i2);

                }
            });


        } catch (Exception e) {

            e.printStackTrace();
        }

        holder.reverse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pressTime = System.currentTimeMillis();
                        holder.storiesProgressView.pause();
                        return false;
                    case MotionEvent.ACTION_UP:
                        long now = System.currentTimeMillis();
                        holder.storiesProgressView.resume();
                        return limit < now - pressTime;
                }
                return false;
            }
        });

        holder.skip.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pressTime = System.currentTimeMillis();
                        holder.storiesProgressView.pause();
                        return false;
                    case MotionEvent.ACTION_UP:
                        long now = System.currentTimeMillis();
                        holder.storiesProgressView.resume();
                        return limit < now - pressTime;
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return personUtils.size();
    }

    long pressTime = 0L;
    long limit = 500L;




    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView post_text, post_user_name, post_time, like_count, comment_count, discp, quantity;
        //   public TextView pJobProfile;
        ImageView post_image, post_user_image, like_icon, comment_icon, delete_post_img;
        Button add;
        StoriesProgressView  storiesProgressView ;
        RelativeLayout imagelayout;
        View reverse,skip;
        public ViewHolder(View itemView) {
            super(itemView);

            post_text = (TextView) itemView.findViewById(R.id.post_text_data);
            post_user_name = (TextView) itemView.findViewById(R.id.post_user_name);
            post_time = (TextView) itemView.findViewById(R.id.post_time);
            like_count = (TextView) itemView.findViewById(R.id.like_count);
            comment_count = (TextView) itemView.findViewById(R.id.comment_count);
            imagelayout=(RelativeLayout)itemView.findViewById(R.id.imagelayout);
            reverse =itemView. findViewById(R.id.reverse);
            skip =itemView. findViewById(R.id.skip);
            // add=(Button)itemView.findViewById(R.id.add);

            // price = (TextView) itemView.findViewById(R.id.price);
            //  location = (TextView) itemView.findViewById(R.id.location);

            storiesProgressView = (StoriesProgressView) itemView.findViewById(R.id.stories);
            post_image = (ImageView) itemView.findViewById(R.id.postimage);
            post_user_image = (ImageView) itemView.findViewById(R.id.postuserimage);
            like_icon = (ImageView) itemView.findViewById(R.id.like_icon);
            comment_icon = (ImageView) itemView.findViewById(R.id.comment_icon);

            delete_post_img = (ImageView) itemView.findViewById(R.id.delete_post_img);



            itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {



                                            }
                                        }

            );

        }
    }

}

