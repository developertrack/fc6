package com.aafilogic.fc6.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aafilogic.fc6.New_Home;
import com.aafilogic.fc6.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class login extends AppCompatActivity {


    private static final String TAG = "Login Activity";
    private static final String EMAIL = "email";
    private static int SPLASH_TIME_OUT = 1500;
    EditText et_email, et_password;
    String email, password;
    String new_token;
    TextView forgot_pass, signup;
    String[] badges, badges_type;
    ImageView remem_me_checkbox;
    LoginButton loginButton;
    CallbackManager callbackManager;
    ImageView fb_login;
    TextView tv_fb_login;

    String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    TextView textView;
    String otp="0";
    private KeyStore keyStore;
    private static final String KEY_NAME = "fc6";
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyGenerator keyGenerator;
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        checkPermissions();
        textView = (TextView)findViewById(R.id.fb_login_text);
        fingerprintManager = (FingerprintManager) getSystemService(FingerprintManager.class);
        keyguardManager = (KeyguardManager) getSystemService(KeyguardManager.class);

//        if (!keyguardManager.isKeyguardSecure()) {
//            // Show a message that the user hasn't set up a fingerprint or lock screen.
//            Toast.makeText(this,
//                    "Secure lock screen hasn't set up.\n"
//                            + "Go to 'Settings -> Security -> Fingerprint' to set up a fingerprint",
//                    Toast.LENGTH_LONG).show();
//            return;
//        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.USE_FINGERPRINT}, 1);
        }

//        if (!fingerprintManager.hasEnrolledFingerprints()) {
//            Toast.makeText(this,
//                    "Go to 'Settings -> Security -> Fingerprint' and register at least one fingerprint",
//                    Toast.LENGTH_LONG).show();
//            return;
//        }
//        generateKey();
//        if(cipherInit()){
//            cryptoObject = new FingerprintManager.CryptoObject(cipher);
//            FingerprintHandler handler = new FingerprintHandler(this);
//            handler.startAuth(fingerprintManager, cryptoObject);
//
//        }


        double size_display=getScreenSizeInches(login.this);
        Log.e("size screen",String.valueOf(size_display));


        et_email = (EditText) findViewById(R.id.emailid);
        et_password = (EditText) findViewById(R.id.userPassword);
        forgot_pass = (TextView) findViewById(R.id.forgotpassword);
        signup = (TextView) findViewById(R.id.signupText);


        remem_me_checkbox = (ImageView) findViewById(R.id.remember_me_checkbox);

        listner();

        if(!fingerprintManager.isHardwareDetected()){

//            Toast.makeText(login.this,"Your Device does not have a Fingerprint Sensor",Toast.LENGTH_LONG).show();
            textView.setText("Your Device does not have a Fingerprint Sensor");
        }else {
            textView.setText("Your can login from Fingerprint Sensor");
            // Checks whether fingerprint permission is set on manifest
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

//                Toast.makeText(login.this,"Fingerprint authentication permission not enabled",Toast.LENGTH_LONG).show();
                textView.setText("Fingerprint authentication permission not enabled");
            }else{
                // Check whether at least one fingerprint is registered
                if (!fingerprintManager.hasEnrolledFingerprints()) {

//                    Toast.makeText(login.this,"Register at least one fingerprint in Settings",Toast.LENGTH_LONG).show();
                    textView.setText("Register at least one fingerprint in Settings");

                }else{

                    // Checks whether lock screen security is enabled or not
                    if (!keyguardManager.isKeyguardSecure()) {
//                        Toast.makeText(login.this,"Lock screen security not enabled in Settings",Toast.LENGTH_LONG).show();
//                        textView.setText("Lock screen security not enabled in Settings");

                    }else{
                        generateKey();

                        if (cipherInit()) {
                            FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                            FingerprintHandler helper = new FingerprintHandler(login.this);
                            helper.startAuth(fingerprintManager, cryptoObject);
                        }
                    }
                }
            }
        }

        try {

            SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);

            String rememb_email = pref.getString("rememb_email", "");
            String rememb_password = pref.getString("rememb_password", "");


            et_email.setText(rememb_email);

            if (!rememb_email.equals("")) {

                remem_me_checkbox.setImageResource(R.drawable.fillbox);


            }


        } catch (Exception e) {

            e.printStackTrace();
        }


        remem_me_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (remem_me_checkbox.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.emptybox).getConstantState()) {
                    remem_me_checkbox.setImageResource(R.drawable.fillbox);

                    try {

                        email = et_email.getText().toString().trim();

                        //   Toast.makeText(getApplicationContext(), "email  id remebered\n"+email, Toast.LENGTH_SHORT).show();


                        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("rememb_email", email);
                        editor.putString("rememb_password", et_password.getText().toString());
                        editor.commit();


                    } catch (Exception e) {


                        e.printStackTrace();
                    }


                } else {
                    remem_me_checkbox.setImageResource(R.drawable.emptybox);

                    et_email.setText("");

                    SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("rememb_email", "");
                    editor.putString("rememb_password", et_password.getText().toString());
                    editor.commit();


                }


            }
        });


        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation1 =
                        AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.blink);


                forgot_pass.startAnimation(animation1);


            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation1 =
                        AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.blink);


                signup.startAnimation(animation1);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        Intent signup = new Intent(login.this, com.aafilogic.fc6.activity.signup.class);
                        startActivity(signup);


                    }
                }, SPLASH_TIME_OUT);


            }
        });


        facebooklogin();

        //  singup();


    }

    public static double getScreenSizeInches(Activity activity){
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        // since SDK_INT = 1;
        int mWidthPixels = displayMetrics.widthPixels;
        int mHeightPixels = displayMetrics.heightPixels;

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
            try{
                mWidthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                mHeightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (Exception ignored) {}
        }

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
                mWidthPixels = realSize.x;
                mHeightPixels = realSize.y;
            } catch (Exception ignored) {}
        }

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(mWidthPixels / dm.xdpi, 2);
        double y = Math.pow(mHeightPixels / dm.ydpi, 2);
        return Math.sqrt(x + y);
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something
            }
            return;
        }
    }


    private void singup() {

        findViewById(R.id.signupText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent signup = new Intent(login.this, com.aafilogic.fc6.activity.signup.class);
                startActivity(signup);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//
//
//


    }

    public void facebooklogin() {


        callbackManager = CallbackManager.Factory.create();


        loginButton = (LoginButton) findViewById(R.id.login_button);
        fb_login = (ImageView) findViewById(R.id.fb_login_btn);


//    loginButton.setReadPermissions("email", "public_profile");

        loginButton.setReadPermissions("public_profile"); // Parámetros que pedimos a facebook


//    loginButton.setReadPermissions(Arrays.asList(
//            "public_profile", "email", "user_birthday", "user_friends"));
        // If you are using in a fragment, call loginButton.setFragment(this);

//    try {
//        PackageInfo info = getPackageManager().getPackageInfo(
//                "com.aafilogic.fc6",
//
//        PackageManager.GET_SIGNATURES);
//        for (Signature signature : info.signatures) {
//            MessageDigest md = MessageDigest.getInstance("SHA");
//            md.update(signature.toByteArray());
//            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//        }
//    } catch (PackageManager.NameNotFoundException e) {
//        Toast.makeText(getBaseContext(),"Package exception"+ e.getMessage(), Toast.LENGTH_LONG).show();
//
//    } catch (NoSuchAlgorithmException e) {
//        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//
//    }

        // Callback registration

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
//                Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_LONG).show();
                System.out.println("login result " + loginResult);
                // App code

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                System.out.println("Facebook user details : " + object.toString());

                                /* make the API call */
                                try {
                                    new GraphRequest(
                                            AccessToken.getCurrentAccessToken(),
                                            object.getString("id"),
                                            null,
                                            HttpMethod.GET,


                                            new GraphRequest.Callback() {
                                                public void onCompleted(GraphResponse response) {
                                                    /* handle the result */
                                                    System.out.print("Json Response" + response.getJSONObject());
//                                                    Toast.makeText(getBaseContext(), "User detail" + response.getJSONObject(), Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                    ).executeAsync();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

//                                String email = object.getString("email");
//                                String birthday = object.getString("birthday"); // 01/31/1980 format
//


                            }
                        });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();


            }


            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getBaseContext(), "cancel", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {
                // App code
                Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println("Facebook error response " + error.getMessage());

            }

        });


    }


    public void listner() {
        final Button login = findViewById(R.id.loginbutton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                email = et_email.getText().toString().trim();
                password = et_password.getText().toString().trim();

                if (email.equals("")) {
                    et_email.setError("can't be blank");
                } else if (password.equals("")) {
                    et_password.setError("can't be blank");
                } else {

                    //  Toast.makeText(getApplicationContext(),""+email+"\n"+password,Toast.LENGTH_SHORT).show();
                    if (remem_me_checkbox.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.fillbox).getConstantState()) {
                        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("rememb_email", email);
                        editor.putString("rememb_password", et_password.getText().toString());
                        editor.commit();
                    }


                    final ProgressDialog loading = ProgressDialog.show(login.this, "Please wait...", "Logging in...", false, true);


                    SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                    new_token = pref.getString("regId", null);
                    String tag_string_req = "req_login";
                    // final String img_url="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUsoakftmf6A5PVKLwpeG-yfLmlXgdMzQeJw-N4dxi0UmYt4bB";
                    // String url="https://www.aafilogicinfotech.com/demo/FC6/api/register.php?user_name="+username+"&&user_email="+email+"&&user_gender="+gender+"&&user_mobile="+mobile+"&&user_password="+password+"&&device_id="+new_token+"&&device_type=android&&user_image="+img_url+"&&user_type=app&&group_id="+team_name+"&&company_name="+company_name;

                    String url = "https://www.aafilogicinfotech.com/demo/FC6/api/login.php";
                    Log.e(TAG, "Login URL: " + url);


                    StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = response.toString();
                            Log.e("login detail",result);
                            loading.dismiss();

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

                                    SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
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

                                        Intent in = new Intent(getApplicationContext(), New_Home.class);
                                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(in);
                                    }else{
                                      resendOtp();
                                    }


                                } else if (success.equals("0"))

                                {
                                    Toast.makeText(getApplicationContext(), "Wrong Email/Password !", Toast.LENGTH_LONG).show();


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
        });

    }

    public void resendOtp(){

        final ProgressDialog loading = ProgressDialog.show(login.this, "Please wait...", "Logging in...", false, true);


        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
        final String user_id = pref.getString("user_id", "");
        final String token = pref.getString("token", "");
        String tag_string_req = "req_login";
        // final String img_url="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUsoakftmf6A5PVKLwpeG-yfLmlXgdMzQeJw-N4dxi0UmYt4bB";
        // String url="https://www.aafilogicinfotech.com/demo/FC6/api/register.php?user_name="+username+"&&user_email="+email+"&&user_gender="+gender+"&&user_mobile="+mobile+"&&user_password="+password+"&&device_id="+new_token+"&&device_type=android&&user_image="+img_url+"&&user_type=app&&group_id="+team_name+"&&company_name="+company_name;

        String url = "https://www.aafilogicinfotech.com/demo/FC6/api/resend_otp.php";
        Log.e(TAG, "Login URL: " + url);


        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String result = response.toString();
                Log.e("resend detail",result);
                loading.dismiss();

                PersonUtils personUtils = new PersonUtils();

                try {

                    JSONObject jObj = new JSONObject(response);

                    String success = jObj.getString("Success");

                    if (success.equals("1")) {

                        JSONObject user = jObj.getJSONObject("post");
                        String otp_data = user.getString("otp");

                        Intent in = new Intent(getApplicationContext(), otpActivty.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.putExtra("otp", otp_data);
                        in.putExtra("token", token);
                        in.putExtra("user_id", user_id);
                        startActivity(in);
                    }

                    else if (success.equals("0"))

                    {
                        Toast.makeText(getApplicationContext(), "Wrong Email/Password !", Toast.LENGTH_LONG).show();


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

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }



    @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }


        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }


        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }


        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }
}
