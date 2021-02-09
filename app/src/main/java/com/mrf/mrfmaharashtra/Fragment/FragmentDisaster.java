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
import com.mrf.mrfmaharashtra.Activity.MainActivity;
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

public class FragmentDisaster extends Fragment {
    public static final String url_sops = "http://mcfrakshak.in/mrfWebservices/subCategory_list.php";

    private List<SopsModel> subcategorylist;

    RecyclerView recyclerView;
    FragmentAdapter mAdapter;
    Preferences preferences;
    Dialog dialog;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sop_content_main, container, false);

        subcategorylist = new ArrayList<>();

        preferences = new Preferences(getActivity());
        recyclerView = view.findViewById(R.id.recyclerView);

        MainActivity.tvHeaderText.setText(getString(R.string.disaster));
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
        StringRequest request = new StringRequest(Request.Method.POST, url_sops, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                dialog.cancel();
                Log.e("subcategory_list", response);
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        SopsModel product = new SopsModel();
                        String category_id=jsonObject.getString("cat_id");
                        String subCategory_id=jsonObject.getString("sub_catid");
                        String sub_catname=jsonObject.getString("sub_catname");
                        String pdf_content=jsonObject.getString("pdf_product");



                        product.setSopsId(category_id);
                        product.setSubCategoryId(subCategory_id);
                        product.setSopsName(sub_catname);
                        product.setPdf_content(pdf_content);


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
                parameters.put("cat_id", getArguments().getString("id"));
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new FragmentAdapter(subcategorylist, getActivity());
        recyclerView.setAdapter(mAdapter);
    }
    public class  FragmentAdapter extends  RecyclerView.Adapter<FragmentAdapter.ViewHolder> {
        //ArrayList source;

        private List<SopsModel> mModel;
        private Context mContext;

        public FragmentAdapter(List<SopsModel> mModel, Context mContext) {
            this.mModel = mModel;
            this.mContext = mContext;

        }


        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data_content, parent, false));


        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.data_name.setText( mModel.get(position).getSopsName());
            holder.cd_SopsContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id=mModel.get(position).getSubCategoryId();
                    String pdf_content=mModel.get(position).getPdf_content();

                    Log.e("id" ,""+id);
                    replaceFragmentWithAnimation(new FragmentPdfDisaster(),id,pdf_content);
                }
            });


            //get first letter of each String item
            String firstLetter= String.valueOf(mModel.get(position).getSopsName().charAt(0));

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
            CardView cd_SopsContent;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                profilePhoto = itemView.findViewById(R.id.profile_image);
                data_name = itemView.findViewById(R.id.data_name);
                cd_SopsContent=itemView.findViewById(R.id.sop_contentMain);


            }
        }


    }
    public void replaceFragmentWithAnimation(Fragment fragment, String id,String pdf_content) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("pdf_content", pdf_content);
        fragment.setArguments(bundle);
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
