package com.mrf.mrfmaharashtra.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.mrf.mrfmaharashtra.Activity.Preferences;
import com.mrf.mrfmaharashtra.Activity.Utils;
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

public class AllIndiaHelpline  extends Fragment {
    public static final String help_url = "http://mcfrakshak.in/mrfWebservices/fetch_helpContact.php";

    private List<SopsModel> subcategorylist;
    RecyclerView recyclerView;
    AllIndiaHelpAdapter mAdapter;

    View view;
    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sop_content_main, container, false);


        subcategorylist = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recyclerView);

        if (Utils.isNetworkConnectedMainThred(getActivity())) {
            ProgressDialog();
            dialog.show();
            ApiHelp("1");

        } else {
            Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

        }

       return view;
    }

    private void ApiHelp(final String id) {
        StringRequest request = new StringRequest(Request.Method.POST, help_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                dialog.cancel();
                Log.e("id_list", response);
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        SopsModel product = new SopsModel();
                        String contact_name=jsonObject.getString("contact_name");
                        String contact_number=jsonObject.getString("contact_number");



                        product.setHelpContact_name(contact_name);
                        product.setHelpContact_number(contact_number);
                        subcategorylist.add(product);
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("sub_id", id);
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new AllIndiaHelpAdapter(subcategorylist, getActivity());
        recyclerView.setAdapter(mAdapter);
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
    public class AllIndiaHelpAdapter extends  RecyclerView.Adapter<AllIndiaHelpAdapter.ViewHolder> {


        private List<SopsModel> mModel;
        private Context mContext;

        public AllIndiaHelpAdapter(List<SopsModel> mModel, Context mContext) {
            this.mModel = mModel;
            this.mContext = mContext;

        }

        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_help_data, parent, false));


        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.data_name.setText( mModel.get(position).getHelpContact_name());
            holder.data_number.setText( mModel.get(position).getHelpContact_number());
            holder.callUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+mModel.get(position).getHelpContact_number()));
                    startActivity(intent);
                }
            });






            //get first letter of each String item
            String firstLetter= String.valueOf(mModel.get(position).getHelpContact_name().charAt(0));

            //String firstLetter = String.valueOf(personNames.get(position).charAt(0));

            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            // generate random color
            // int color = generator.getColor(personNames.get(position));
            int color = generator.getRandomColor();

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(firstLetter, color); // radius in px

            holder.profilePhoto.setImageDrawable(drawable);
            holder.cd_SopsContent.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fads_transitions));


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
            TextView data_number;
            ImageView callUser;
            CardView cd_SopsContent;




            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                profilePhoto = itemView.findViewById(R.id.profile_image);
                data_name = itemView.findViewById(R.id.data_name);
                data_number = itemView.findViewById(R.id.data_number);
                callUser=itemView.findViewById(R.id.call);
                cd_SopsContent=itemView.findViewById(R.id.help_contentMain);



            }
        }


    }


}
