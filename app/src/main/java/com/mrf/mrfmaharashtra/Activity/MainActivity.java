package com.mrf.mrfmaharashtra.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.mrf.mrfmaharashtra.Fragment.DashboardFragment;
import com.mrf.mrfmaharashtra.Fragment.FragmentICard;
import com.mrf.mrfmaharashtra.Fragment.FragmentNewsLetter;
import com.mrf.mrfmaharashtra.Fragment.UserProfileFragment;
import com.mrf.mrfmaharashtra.Model.NewsLetter;
import com.mrf.mrfmaharashtra.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    public static int backPressed = 0;
    public static ImageView iv_menu,iv_share;

    DrawerLayout drawer;
    public static NavigationView navigationView;
    public static TextView tvHeaderText;
    public static TextView tvCount;

    public static TextView iv_Notification;

    Preferences  preferences;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        iv_menu = findViewById(R.id.iv_menu);
        iv_share=findViewById(R.id.iv_share);
        tvCount=findViewById(R.id.tvCount);

        iv_Notification=findViewById(R.id.ivNotification);

        iv_menu.setOnClickListener(this);
        preferences=new Preferences(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        tvHeaderText = findViewById(R.id.tvHeaderText);


        tvCount.setText(""+preferences.getInt("count"));
        Log.e("counter",""+preferences.getInt("count"));


        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent =   new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody="Your body here";
                String subject="Your subject here";
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,subject);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                String app_url = "https://play.google.com/store/apps/details?id=com.mrf.mrfmaharashtra";
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
                startActivity(Intent.createChooser(shareIntent, "Share via"));

            }
        });
        iv_Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ActivityNotifications.class);
                startActivity(intent);
            }
        });

        tvHeaderText.setText(getString(R.string.app_name));
        replaceFragmentWithAnimation(new DashboardFragment());

        drawer.closeDrawer(GravityCompat.START);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        if (navigationView != null) {
            Menu menu = navigationView.getMenu();
           /* if (preferences.get("login").equalsIgnoreCase("yes")) {

                menu.findItem(R.id.logout).setTitle("Login");
            } else {
                menu.findItem(R.id.logout).setTitle("Logout");
            }*/
            navigationView.setNavigationItemSelectedListener(this);
        }
        /*navigationView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {

                navigationView.removeOnLayoutChangeListener( this );
                TextView tvUsername = (TextView) navigationView.findViewById(R.id.tvUsefullname);
                TextView tvUserContact = (TextView) navigationView.findViewById(R.id.tvUserContact);

                tvUsername.setText(preferences.get("first_name"));
                tvUserContact.setText("+91-"+preferences.get("contact_no"));

            }
        });*/
    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu:
                drawer.openDrawer(Gravity.LEFT);
                break;
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_home) {
            replaceFragmentWithAnimation( new DashboardFragment());
        }
        else if (id == R.id.nav_policy) {
            //replaceFragmentWithAnimation(new AboutUsFragment());
        }
        else if (id == R.id.nav_idCard) {

            replaceFragmentWithAnimation(new FragmentICard());

        }
        else if (id == R.id.nav_newsSlater) {

            replaceFragmentWithAnimation(new FragmentNewsLetter());

        }
        else if (id == R.id.nav_pofile) {
            if (Utils.isNetworkConnectedMainThred(this)) {
                replaceFragmentWithAnimation(new UserProfileFragment());


            } else {
                Toasty.error(this, "No Internet Connection!", Toast.LENGTH_LONG).show();

            }
        }
        else if (id == R.id.nav_apply) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mcfrakshak.in/reg_now.php"));
            startActivity(intent);
           // replaceFragmentWithAnimation(new ApplyOnlineDefence());

        }
        else if (id == R.id.nav_rate) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mrf.mrfmaharashtra"));
            startActivity(intent);

        }
        else if (id == R.id.nav_update) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mrf.mrfmaharashtra"));
            startActivity(intent);

        }else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void logout() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.logout_dialog);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);


        //findId
        TextView tvYes = (TextView) dialog.findViewById(R.id.tvOk);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tvcancel);
        TextView tvReason = (TextView) dialog.findViewById(R.id.textView22);
        TextView tvAlertMsg = (TextView) dialog.findViewById(R.id.tvAlertMsg);

        //set value
        tvAlertMsg.setText("Confirmation Alert..!!!");
        tvReason.setText("Are you sure you want to logout?");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        //set listener
        tvYes.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
//                pref.set(AppSettings.CustomerID, "");
//                pref.commit();
                preferences.set("User_id","");
                preferences.commit();
                finishAffinity();
                dialog.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        backPressed = backPressed + 1;
        if (backPressed == 1) {
            Toast.makeText(MainActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
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