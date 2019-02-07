package com.aafilogic.fc6.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aafilogic.fc6.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class mainactivity extends AppCompatActivity {


    private static final String TAG = mainactivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.termscondition);


        txtRegId = (TextView) findViewById(R.id.txt_reg_id);
        txtMessage = (TextView) findViewById(R.id.txt_push_message);

        final Context context = this;


        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Toast.makeText(getApplicationContext(),"stratup Token generated : "+refreshedToken,Toast.LENGTH_LONG).show();

        Log.e(TAG, "Firebase reg id: " + refreshedToken);


        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", refreshedToken);
        editor.commit();


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);


                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    txtMessage.setText(message);


                }
            }
        };


        displayFirebaseRegId();






/*
        List<badgesDto> badgesDotList=new ArrayList<badgesDto>();
          badgesDotList.add(new badgesDto("https://images.mid-day.com/images/2018/jul/Salman-mall-story_d.jpg","Salman Khan"));
        badgesDotList.add(new badgesDto("https://pbs.twimg.com/profile_images/841956276141711360/Yh7xHO41_400x400.jpg","Akshay Kumar"));
        badgesDotList.add(new badgesDto("https://images.mid-day.com/images/2018/jul/Salman-mall-story_d.jpg","Salman Khan"));


      final  RecyclerView recyclerView = (RecyclerView) findViewById(R.id.badgesDetails);

        shppinglistAdapter shppinglistAdapter = new shppinglistAdapter(badgesDotList,this);
      final  RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(shppinglistAdapter);



        findViewById(R.id.rigtarrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition() > 0) {
                    new CountDownTimer(100, 100) {
                        public void onTick(long millisUntilFinished) {
                        }
                        public void onFinish() {
                            recyclerView.smoothScrollToPosition(((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition() - 1);

                        }
                    }.start();
                } else {

                    new CountDownTimer(100, 100) {
                        public void onTick(long millisUntilFinished) {
                        }
                        public void onFinish() {

                            recyclerView.smoothScrollToPosition(0);
                        }
                    }.start();
                }

            }
        });

        findViewById(R.id.leftarrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CountDownTimer(100, 100) {
                    public void onTick(long millisUntilFinished) {
                    }
                    public void onFinish() {

                        recyclerView.smoothScrollToPosition(((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition() + 2);
                    }
                }.start();
            }
        });

*/


        TextView TV = (TextView) findViewById(R.id.termsConditionText);

        Spannable wordtoSpan = new SpannableString("By tapping 'Agree & Continue' you accept the \n      FC6 Terms and Service and Privacy Policy");

        wordtoSpan.setSpan(new ForegroundColorSpan(getColor(R.color.green)), 56, 92, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        TV.setText(wordtoSpan);


        TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View vi = LayoutInflater.from(context).inflate(R.layout.termsmessage, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);


                builder.setView(vi);

                final AlertDialog alert = builder.create();

                vi.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });


                alert.setCancelable(false);
                alert.show();

            }
        });


        ((Button) findViewById(R.id.condition)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent losi = new Intent(mainactivity.this, login.class);
                startActivity(losi);
            }
        });


    }


    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        String new_token = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);


        if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");


        //  Toast.makeText(getApplicationContext(),"Token generated : "+new_token,Toast.LENGTH_LONG).show();


    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


}
