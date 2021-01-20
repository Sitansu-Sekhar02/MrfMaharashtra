package com.mrf.mrfmaharashtra.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mrf.mrfmaharashtra.R;

import java.util.Locale;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView imageSplash;
    TextView tvEnglish;
    TextView tvMarathi;

    ArrayAdapter<String> selectLang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        imageSplash=findViewById(R.id.imageSplash);
        tvEnglish=findViewById(R.id.tvEnglish);
        tvMarathi=findViewById(R.id.TvMarathi);


        String language[] = {"English","मराठी"};


        final Animation zoomAnimation= AnimationUtils.loadAnimation(this,R.anim.zoomin);
        imageSplash.startAnimation(zoomAnimation);


        tvEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setLocale("en");
                Intent intent=new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(intent);
                LocaleHelper.setLocale(SplashScreenActivity.this,"en"); //for english;

            }
        });
        tvMarathi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setLocale("en");
                Intent intent=new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(intent);
                LocaleHelper.setLocale(SplashScreenActivity.this,"mr"); //for english;

            }
        });


    }


    public static class LocaleHelper {

        private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

        public static void onCreate(Context context) {

            String lang;
            if(getLanguage(context).isEmpty()){
                lang = getPersistedData(context, Locale.getDefault().getLanguage());
            }else {
                lang = getLanguage(context);
            }

            setLocale(context, lang);
        }

        public static void onCreate(Context context, String defaultLanguage) {
            String lang = getPersistedData(context, defaultLanguage);
            setLocale(context, lang);
        }

        public static String getLanguage(Context context) {
            return getPersistedData(context, Locale.getDefault().getLanguage());
        }

        public static void setLocale(Context context, String language) {
            persist(context, language);
            updateResources(context, language);
        }

        private static String getPersistedData(Context context, String defaultLanguage) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(SELECTED_LANGUAGE, defaultLanguage);
        }

        private static void persist(Context context, String language) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString(SELECTED_LANGUAGE, language);
            editor.apply();
        }

        private static void updateResources(Context context, String language) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            Resources resources = context.getResources();

            Configuration configuration = resources.getConfiguration();
            configuration.locale = locale;

            resources.updateConfiguration(configuration, resources.getDisplayMetrics());


        }
    }

}
