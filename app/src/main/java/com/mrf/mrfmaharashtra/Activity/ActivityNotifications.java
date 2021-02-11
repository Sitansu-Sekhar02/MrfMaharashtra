package com.mrf.mrfmaharashtra.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.mrf.mrfmaharashtra.Fragment.FragmentCdComm;
import com.mrf.mrfmaharashtra.Fragment.FragmentPdfComm;
import com.mrf.mrfmaharashtra.Model.NewsLetter;
import com.mrf.mrfmaharashtra.Model.SopsModel;
import com.mrf.mrfmaharashtra.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ActivityNotifications extends AppCompatActivity {
    public static final String url_sops = "http://mcfrakshak.in/mrfWebservices/notification.php";

    private List<NewsLetter> newsLetter;

    RecyclerView recyclerView;
    NotificationAdapter mAdapter;
    Preferences preferences;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sop_content_main);

        newsLetter = new ArrayList<>();

        preferences = new Preferences(this);
        recyclerView = findViewById(R.id.recyclerView);
        if (Utils.isNetworkConnectedMainThred(this)) {
            ProgressDialog();
            dialog.show();
            ApiNotifications();

        } else {
            Toasty.error(this, "No Internet Connection!", Toast.LENGTH_LONG).show();

        }

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

    private void ApiNotifications() {
        StringRequest request = new StringRequest(Request.Method.POST, url_sops, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                dialog.cancel();
                Log.e("news_list", response);
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        NewsLetter news = new NewsLetter();
                        String notification_id=jsonObject.getString("id");
                        String notification_heading=jsonObject.getString("heading");
                        String notification_description=jsonObject.getString("content");
                        String notification_image=jsonObject.getString("img");
                        String notification_date=jsonObject.getString("date");

                        news.setNotification_heading(notification_heading);
                        news.setNotification_description(notification_description);
                        news.setNotification_img(notification_image);
                        news.setNotification_date(notification_date);

                        newsLetter.add(news);
                    }

                    setAdapter();
                }
                catch (JSONException e) {
                    Log.d("JSONException", e.toString());
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();
                Log.e("error_response", "" + error);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new NotificationAdapter(newsLetter,this);
        recyclerView.setAdapter(mAdapter);

    }

    public class NotificationAdapter extends  RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
        //ArrayList source;

        private List<NewsLetter> mModel;
        private Context mContext;

        public NotificationAdapter(List<NewsLetter> mModel, Context mContext) {
            this.mModel = mModel;
            this.mContext = mContext;

        }

        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notification_data, parent, false));


        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.data_name.setText( mModel.get(position).getNotification_heading());
            holder.not_data.setText( mModel.get(position).getNotification_date());

            Glide.with(mContext)
                    .load(mModel.get(position).getNotification_img())
                    .into(holder.profilePhoto);
            holder.cd_SopsContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //MainActivity.tvCount.setText(""+preferences.set("count",0));
                    //Log.e("zero",""+preferences.getInt("count"));


                    Intent intent = new Intent(getBaseContext(), NotificationDetails.class);
                    intent.putExtra("news_image",mModel.get(position).getNotification_img());
                    Log.e("newsnews",""+mModel.get(position).getNotification_img());
                    intent.putExtra("news_desc",mModel.get(position).getNotification_description());

                    startActivity(intent);
                   // String id=mModel.get(position).getSubCategoryId();
                 //   String pdf_content=mModel.get(position).getPdf_content();


                   // Log.e("id" ,""+id);
                    //replaceFragmentWithAnimation(new FragmentPdfComm(),id,pdf_content);
                }
            });


        }


        @Override
        public int getItemCount() {
            return mModel.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }



        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView profilePhoto;
            TextView data_name;
            TextView not_data;
            CardView cd_SopsContent;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                profilePhoto = itemView.findViewById(R.id.profile_image);
                data_name = itemView.findViewById(R.id.data_name);
                cd_SopsContent=itemView.findViewById(R.id.sop_contentMain);
                not_data=itemView.findViewById(R.id.not_data);


            }
        }


    }

}
