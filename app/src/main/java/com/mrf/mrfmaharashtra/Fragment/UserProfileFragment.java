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
import androidx.fragment.app.FragmentTransaction;


import com.bumptech.glide.Glide;
import com.mrf.mrfmaharashtra.Activity.MainActivity;
import com.mrf.mrfmaharashtra.Activity.Preferences;
import com.mrf.mrfmaharashtra.Activity.Utils;
import com.mrf.mrfmaharashtra.R;

import es.dmoral.toasty.Toasty;


public class UserProfileFragment extends Fragment {
    TextView userFirstName;
    TextView tvReg;
    TextView userContact;
    TextView tvAddress;
    Preferences preferences;
    TextView tvChange;
    ImageView profile;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        userFirstName=view.findViewById(R.id.first_name);
        tvReg=view.findViewById(R.id.tvReg);
        userContact=view.findViewById(R.id.phone_number);
        tvAddress=view.findViewById(R.id.tvAddress);

        tvChange=view.findViewById(R.id.tvChange);
        profile=view.findViewById(R.id.profile_photo);


        preferences=new Preferences(getActivity());

        MainActivity.tvHeaderText.setText(getString(R.string.user_profile));
        userContact.setText("Mob:+91-"+preferences.get("Contact1"));
        userFirstName.setText(preferences.get("Name"));
        //tvReg.setText(preferences.get("Address"));
        tvAddress.setText(preferences.get("Address"));
        Log.e("Name",preferences.get("Name"));
        Log.e("Contact1",preferences.get("Contact1"));

        Log.e("address",preferences.get("Address"));

        Glide.with(getActivity())
                .load(preferences.get("User_photo"))
                .into(profile);

        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    replaceFragmentWithAnimation(new UpdateUserDetailsFragment());
                } else {
                    Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        MainActivity.iv_menu.setImageResource(R.drawable.ic_back);
        MainActivity.iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        });


        return view;
    }
    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

}
