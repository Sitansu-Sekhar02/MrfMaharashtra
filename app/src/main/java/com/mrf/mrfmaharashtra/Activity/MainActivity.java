package com.mrf.mrfmaharashtra.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.mrf.mrfmaharashtra.Fragment.DashboardFragment;
import com.mrf.mrfmaharashtra.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    public static int backPressed = 0;
    public static ImageView iv_menu,iv_share;
    DrawerLayout drawer;
    public static NavigationView navigationView;
    public static TextView tvHeaderText;
    Preferences  preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        iv_menu = findViewById(R.id.iv_menu);
        iv_share=findViewById(R.id.iv_share);


        iv_menu.setOnClickListener(this);
        preferences=new Preferences(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        tvHeaderText = findViewById(R.id.tvHeaderText);

        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent =   new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody="Your body here";
                String subject="Your subject here";
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,subject);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                String app_url = "https://play.google.com/store/apps/details?id=com.blucore.chalochale";
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
                startActivity(Intent.createChooser(shareIntent, "Share via"));


            }
        });

        tvHeaderText.setText("MRF POLICE MITRA");
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
        } else if (id == R.id.nav_org) {
           // replaceFragmentWithAnimation( new YourRidingFragment());

        }
        else if (id == R.id.nav_sops) {
           // replaceFragmentWithAnimation( new SupportFragment());

        }
        else if (id == R.id.nav_order) {
            //replaceFragmentWithAnimation(new AboutUsFragment());
        }
        else if (id == R.id.nav_training) {
            //replaceFragmentWithAnimation(new AboutUsFragment());
        }
        else if (id == R.id.nav_help) {
            //replaceFragmentWithAnimation(new AboutUsFragment());
        }
        else if (id == R.id.nav_cdcom) {
            //replaceFragmentWithAnimation(new AboutUsFragment());
        }
        else if (id == R.id.nav_fire) {
            //replaceFragmentWithAnimation(new AboutUsFragment());
        }
        else if (id == R.id.nav_rescue) {
            //replaceFragmentWithAnimation(new AboutUsFragment());
        }
        else if (id == R.id.nav_firstaid) {
            //replaceFragmentWithAnimation(new AboutUsFragment());
        }
        else if (id == R.id.nav_disaster) {
            //replaceFragmentWithAnimation(new AboutUsFragment());
        }
        else if (id == R.id.nav_selfdefence) {
            //replaceFragmentWithAnimation(new AboutUsFragment());
        }
        else if (id == R.id.nav_contact) {
            //replaceFragmentWithAnimation(new AboutUsFragment());
        }
        else if (id == R.id.nav_nearbyhospital) {
            try {
                String uri = "http://maps.google.com/maps?q=Hospitals";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }catch (ActivityNotFoundException e){
                Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps,maps");
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }

        }
        else if (id == R.id.nav_cdofc) {
            try {
                String uri = "http://maps.google.com/maps?q=civildefenceoffice";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }catch (ActivityNotFoundException e){
                Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps,maps");
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        }
        else if (id == R.id.nav_blog) {
            //replaceFragmentWithAnimation(new AboutUsFragment());
        }
        else if (id == R.id.nav_apply) {
            //replaceFragmentWithAnimation(new AboutUsFragment());
        }
        else if (id == R.id.nav_cert) {
            //replaceFragmentWithAnimation(new AboutUsFragment());
        }
        else if (id == R.id.nav_policy) {
            //replaceFragmentWithAnimation(new AboutUsFragment());
        }
        else if (id == R.id.nav_rate) {
            //replaceFragmentWithAnimation(new AboutUsFragment());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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