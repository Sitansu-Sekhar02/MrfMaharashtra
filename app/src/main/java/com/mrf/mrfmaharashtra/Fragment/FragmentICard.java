package com.mrf.mrfmaharashtra.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.mrf.mrfmaharashtra.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class FragmentICard extends Fragment {

    public static final String id_card = "http://mcfrakshak.in/mrfWebservices/fetch_user_details.php";
    Preferences preferences;
    TextView tvName;
    TextView number;
    TextView tvMail;
    TextView dob;
    TextView tvFather_name;
    TextView tvMother_name;
    TextView tvAddress;
    TextView tvGender;
    TextView tvTaluka;
    TextView tvDistrict;
    TextView tvPincode;

    ImageView profile_photo;


    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_icard, container, false);
        preferences=new Preferences(getActivity());
        tvName=view.findViewById(R.id.Tvname);
        number=view.findViewById(R.id.number);
        tvMail=view.findViewById(R.id.tvMail);
        dob=view.findViewById(R.id.dob);
        tvFather_name=view.findViewById(R.id.tvFather_name);
        tvMother_name=view.findViewById(R.id.tvMother_name);
        tvAddress=view.findViewById(R.id.tvAddress);
        tvTaluka=view.findViewById(R.id.tvTaluka);
        tvDistrict=view.findViewById(R.id.tvDistrict);
        tvPincode=view.findViewById(R.id.tvPincode);

        tvGender=view.findViewById(R.id.tvGender);
        profile_photo=view.findViewById(R.id.profile_photo);

        MainActivity.tvHeaderText.setText(getString(R.string.idcard));
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

            IDCard();


        } else {
            Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

        }
       return view;
    }
    private void IDCard() {
        StringRequest request = new StringRequest(Request.Method.POST, id_card, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("id details", " "+response);
                try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String name=jsonObject.getString("Name");
                        String father_name=jsonObject.getString("Father_Name");
                        String mother_name=jsonObject.getString("Mother_Name");
                        String dob=jsonObject.getString("DOB");
                        String email=jsonObject.getString("Email");
                        String gender=jsonObject.getString("Gender");
                        String number=jsonObject.getString("Contact1");
                        String address=jsonObject.getString("User_address");
                        String user_photo=jsonObject.getString("Profile_photo");
                        String taluka=jsonObject.getString("Taluka");
                        String district=jsonObject.getString("District");
                        String pincode=jsonObject.getString("Pincode");


                        preferences.set("Name",name);
                        preferences.set("Email",email);
                        preferences.set("Father_Name",father_name);
                        preferences.set("Mother_Name",mother_name);
                        preferences.set("DOB",dob);
                        preferences.set("Gender",gender);
                        preferences.set("Contact1",number);
                        preferences.set("User_address",address);
                        preferences.set("Taluka",taluka);
                        preferences.set("District",district);
                        preferences.set("Pincode",pincode);
                        preferences.set("Profile_photo",user_photo);
                        preferences.commit();

                    }
                    tvName.setText(preferences.get("Name"));
                    tvAddress.setText(preferences.get("User_address"));
                    tvMail.setText(":"+preferences.get("Email"));
                    tvFather_name.setText(":"+preferences.get("Father_Name"));
                    tvMother_name.setText(":"+preferences.get("Mother_Name"));
                    dob.setText(":"+preferences.get("DOB"));
                    tvGender.setText(":"+preferences.get("Gender"));
                    tvTaluka.setText(","+preferences.get("Taluka"));
                    tvDistrict.setText(","+preferences.get("District"));
                    tvPincode.setText(","+preferences.get("Pincode"));
                    number.setText(":"+preferences.get("Contact1"));

                    Glide.with(getActivity())
                            .load(preferences.get("Profile_photo"))
                            .into(profile_photo);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(getActivity(), "Some error occurred \n Please try again later ", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("Contact1", preferences.get("Contact1"));
                parameters.put("Email", preferences.get("Email"));

                return parameters;
            }
        };
        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(request);

    }

}
