package com.mrf.mrfmaharashtra.Fragment;

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
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.mrf.mrfmaharashtra.Activity.MainActivity;
import com.mrf.mrfmaharashtra.Activity.Preferences;
import com.mrf.mrfmaharashtra.Activity.Utils;
import com.mrf.mrfmaharashtra.Model.NewsLetter;
import com.mrf.mrfmaharashtra.Model.SopsModel;
import com.mrf.mrfmaharashtra.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class FragmentNewsLetter extends Fragment {
    public static final String url_newsletter = "http://mcfrakshak.in/mrfWebservices/fetch_news.php";

    private List<NewsLetter> newsLetter;

    RecyclerView recyclerView;
    NewsAdapter mAdapter;
    Preferences preferences;
    Dialog dialog;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sop_content_main, container, false);

        newsLetter = new ArrayList<>();

        preferences = new Preferences(getActivity());
        recyclerView = view.findViewById(R.id.recyclerView);

        MainActivity.tvHeaderText.setText(getString(R.string.news));
        MainActivity.iv_menu.setImageResource(R.drawable.ic_back);
        MainActivity.iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

            }
        });

        if (Utils.isNetworkConnectedMainThred(getActivity())) {
            ProgressDialog();
            dialog.show();
            ApiFire();

        } else {
            Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

        }
        return view;
    }

    private void ProgressDialog() {
        dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
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

    private void ApiFire() {
        StringRequest request = new StringRequest(Request.Method.POST, url_newsletter, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                dialog.cancel();
                Log.e("news_list", response);
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        NewsLetter news = new NewsLetter();
                        String news_id=jsonObject.getString("id");
                        String heading=jsonObject.getString("heading");
                        String description=jsonObject.getString("content");
                        String news_image=jsonObject.getString("img");


                        news.setNews_id(news_id);
                        news.setNews_name(heading);
                        news.setNews_description(description);
                        news.setNews_image(news_image);

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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new NewsAdapter(newsLetter, getActivity());
        recyclerView.setAdapter(mAdapter);
    }
    public class NewsAdapter extends  RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        //ArrayList source;

        private List<NewsLetter> mModel;
        private Context mContext;

        public NewsAdapter(List<NewsLetter> mModel, Context mContext) {
            this.mModel = mModel;
            this.mContext = mContext;

        }
        /*ArrayList personNames;
        Context context;
        public SopsAadapter(Context context, ArrayList personNames) {
            this.context = context;
            this.personNames = personNames;
        }*/

        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_news_letter, parent, false));


        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.news_heading.setText( mModel.get(position).getNews_name());
            holder.news_description.setText( mModel.get(position).getNews_description());
            Glide.with(mContext)
                    .load(mModel.get(position).getNews_image())
                    .into(holder.newsPhoto);

            holder.card_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id=mModel.get(position).getNews_id();
                    String image=mModel.get(position).getNews_image();
                    String desc=mModel.get(position).getNews_description();

                    replaceFragmentWithAnimation(new FragmentNewsDetails(),id,image,desc);
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
            ImageView newsPhoto;
            TextView news_heading;
            TextView news_description;
            CardView card_main;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                newsPhoto = itemView.findViewById(R.id.news_image);
                news_heading = itemView.findViewById(R.id.tvhead);
                news_description = itemView.findViewById(R.id.tvdesc);
                card_main=itemView.findViewById(R.id.cd_content);


            }
        }


    }
    public void replaceFragmentWithAnimation(Fragment fragment, String id, String image,String desc) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("image",image);
        bundle.putString("desc",desc);

        fragment.setArguments(bundle);
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
