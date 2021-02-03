package com.mrf.mrfmaharashtra.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mrf.mrfmaharashtra.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    public static final String log_in = "http://mcfrakshak.in/mrfWebservices/userlogin.php";
    public static final String forgot_password = "http://mcfrakshak.in/mrfWebservices/forgot_password.php";


    TextView tvLoginButton;
    public static int backPressed = 0;

    TextView tvSignupbtn;
    TextView forgotPassword;
    EditText contact_no;
    EditText Password;
    Dialog dialog;
    Preferences preferences;
    String newToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        tvLoginButton=findViewById(R.id.tvLoginButton);
        forgotPassword=findViewById(R.id.tvForgotPassword);
        tvSignupbtn=findViewById(R.id.tvSignupbtn);

        contact_no=findViewById(R.id.ContactEdit);
        Password=findViewById(R.id.PasswordEdit);
        preferences = new Preferences(this);

        tvSignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(LoginActivity.this)) {
                    ProgressDialog();
                    dialog.show();
                    ForgotPassword();

                } else {
                    Toasty.error(LoginActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tvLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (contact_no.getText().toString().trim().length() == 0) {
                    contact_no.setError("Contact number Required");
                    contact_no.requestFocus();

                } else if (Password.getText().toString().trim().length() == 0) {
                    Password.setError("Password Required");
                    Password.requestFocus();


                } else {


                    if (Utils.isNetworkConnectedMainThred(LoginActivity.this)) {

                        ProgressDialog();
                        dialog.show();
                        LoginSuccess();
                    } else {
                        Toasty.error(LoginActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
                    }

                }
               /* Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);*/
            }
        });


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {

                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.e("newToken", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        newToken = task.getResult();

                        // Log and toast
                        String msg = newToken;
                        Log.e("newToken", msg);
                        // Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void ForgotPassword() {
        StringRequest request = new StringRequest(Request.Method.POST, forgot_password, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                dialog.cancel();
                Log.e("forgot password",response);

                ProgressForgotPassword();
                //Toasty.normal(LoginActivity.this, "Check your Email!Password has been sent to your Email!", Toast.LENGTH_LONG).show();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();
                Log.e("error_response", "" + error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("Email",contact_no.getText().toString());
                return param;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(request);

    }

    private void ProgressForgotPassword() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.password_popup);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Button tvOk=dialog.findViewById(R.id.btnOk);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

            }
        });
    }

    private void ProgressDialog() {
        dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_for_load);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void LoginSuccess() {
        StringRequest request = new StringRequest(Request.Method.POST, log_in, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                dialog.cancel();
                Log.e("success", "onResponse: "+response);
                Password.setText("");
                try {
                    JSONObject object=new JSONObject(response);
                    String error=object.getString("error");

                    if(error.equals("false"))
                    {
                        JSONObject user=object.getJSONObject("user");
                        String user_id=user.getString("User_id");
                        String useremail=user.getString("Email");
                        String name=user.getString("Name");
                        String contact=user.getString("Contact1");
                        String profile_photo=user.getString("User_photo");
                        String res= profile_photo.replace("//","");

                        preferences.set("User_id",user_id);
                        preferences.set("Email",useremail);
                        preferences.set("Name",name);
                        preferences.set("Contact1",contact);
                        preferences.set("User_photo",profile_photo);
                        preferences.commit();

                        Intent in=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(in);

                    }
                    else{
                        Toasty.error(getApplicationContext(), "Wrong userId or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();
                Toasty.error(LoginActivity.this, "Some error occurred \n Please try again later ", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("Contact1", contact_no.getText().toString());
                parameters.put("Password", Password.getText().toString());
                return parameters;
            }
        };
        RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
        rQueue.add(request);
    }
    @Override
    public void onBackPressed() {
        backPressed = backPressed + 1;
        if (backPressed == 1) {
            Toast.makeText(LoginActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            new CountDownTimer(5000, 1000) { // adjust the milli seconds here
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    backPressed = 0;
                }
            }.start();
        }
        if (backPressed == 2) {
            backPressed = 0;
            finishAffinity();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
