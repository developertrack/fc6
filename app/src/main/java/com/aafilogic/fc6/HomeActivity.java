package com.aafilogic.fc6;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.aafilogic.fc6.activity.AppController;
import com.aafilogic.fc6.activity.Config;
import com.aafilogic.fc6.activity.PersonUtils;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG_T = "Timeline Activity";
    public String user_image;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);

        user_image = pref.getString("user_image", "");


        String user_id = pref.getString("user_id", "");
        String token = pref.getString("token", "");
        String device_id = pref.getString("device_id", "");
        //  String group_id=user.getString("group_id");
        //  String company_name=user.getString("company_name");
        //  String user_name=user.getString("user_name");
        //   String user_email=user.getString("user_email");
        //   String user_gender=user.getString("user_gender");
        //   String user_mobile=user.getString("user_mobile");
        //  String user_password=user.getString("user_password");
        //   String cover_image=user.getString("cover_image");
        //    String total_workout=user.getString("total_workout");
        //   String total_meals=user.getString("total_meals");
        //  String badges1=user.getString("badges1");
        //  String badges2=user.getString("badges2");
        //  String badges3=user.getString("badges3");

        //  String user_status=user.getString("user_status");
        //  String user_add_time=user.getString("user_add_time");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);


            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.timeline, container, false);


            // TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //  textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            ImageView profile_img = (ImageView) rootView.findViewById(R.id.profile_image2);

            SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);

            String user_image2 = pref.getString("user_image", "");
            final String user_id = pref.getString("user_id", "");
            final String token = pref.getString("token", "");


            Picasso.with(getContext()).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + user_image2).into(profile_img);


            final RecyclerView recyclerView, recyclerView2, recyclerView3;


            final List<PersonUtils> personUtilsList, personUtilsList2, personUtilsList3;

            RecyclerView.LayoutManager layoutManager;


            recyclerView2 = (RecyclerView) rootView.findViewById(R.id.userpost);
            recyclerView2.setHasFixedSize(true);


            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


            personUtilsList2 = new ArrayList<>();


            final ProgressDialog loading = ProgressDialog.show(getActivity(), "Please wait...", "Fetching posts...", false, true);


            String tag_string_req = "req_POST";

            //   String url = "https://go1shop.com/api/get_deshboard.php";
            String url = "https://www.aafilogicinfotech.com/demo/FC6/api/get_all_post.php";


            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String result = response.toString();
                    loading.dismiss();


                    //  Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();


                    try {


                        JSONObject jObj = new JSONObject(response);

                        // boolean error = jObj.getBoolean("Success");

                        // Check for error node in json
                        // Now store the user in SQLite
                        String success = jObj.getString("Success");


                        // JSONObject user = jObj.getJSONObject("post");


                        JSONArray jsonArray = jObj.getJSONArray("post");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            PersonUtils personUtils = new PersonUtils();


                            JSONObject jsonQuiz = jsonArray.getJSONObject(i);


                            personUtils.setPost_id(jsonQuiz.getString("post_id"));
                            personUtils.setUser_image(jsonQuiz.getString("user_image"));
                            personUtils.setUser_name(jsonQuiz.getString("user_name"));
                            personUtils.setPost_add_time(jsonQuiz.getString("post_add_time"));
//                            personUtils.setPost_image(jsonQuiz.getString("post_image"));
                            personUtils.setPost_text(jsonQuiz.getString("post_text"));
                            personUtils.setLike_count(jsonQuiz.getString("like_count"));
                            personUtils.setComment_count(jsonQuiz.getString("comment_count"));

                            personUtilsList2.add(personUtils);


                        }


                        RecyclerView.Adapter mAdapter, mAdapter2, mAdapter3;
//                        mAdapter2 = new CustomRecyclerAdapter(getActivity(), personUtilsList2);
//                        recyclerView2.setAdapter(mAdapter2);


                        if (success.equals("1")) {


                        } else if (success.equals("0"))

                        {
                            Toast.makeText(getActivity(), "No post found.", Toast.LENGTH_LONG).show();


                        }


                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }


                }
            }, new Response.ErrorListener()

            {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG_T, "Post Loading Error: " + error.getMessage());
                    Toast.makeText(getActivity(),
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


            if (ARG_SECTION_NUMBER.equals("3")) {

                View rootView2 = inflater.inflate(R.layout.profile, container, false);


            }


            return rootView;


        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).


            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }
    }
}
