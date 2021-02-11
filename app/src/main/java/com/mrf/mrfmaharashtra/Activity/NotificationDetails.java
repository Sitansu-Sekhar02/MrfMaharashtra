package com.mrf.mrfmaharashtra.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mrf.mrfmaharashtra.R;

public class NotificationDetails extends AppCompatActivity {
    ImageView newsImage;
    TextView tvDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_news_data);

        newsImage=findViewById(R.id.newsImage);
        tvDescription=findViewById(R.id.tvdescription);

        Intent intent = getIntent();
        String image =intent.getStringExtra("news_image");
        String description=intent.getStringExtra("news_desc");

        Glide.with(this)
                .load(image)
                .into(newsImage);

        tvDescription.setText(description);

    }

}
