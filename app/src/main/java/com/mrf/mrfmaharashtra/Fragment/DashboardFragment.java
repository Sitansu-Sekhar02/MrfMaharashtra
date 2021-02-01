package com.mrf.mrfmaharashtra.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ReportFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mrf.mrfmaharashtra.Activity.Utils;
import com.mrf.mrfmaharashtra.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;

public class DashboardFragment extends Fragment {

    TextView courtexy;
    CardView cd_hospital;
    CardView cd_civilofc;
    CardView cd_police;
    CardView cd_orgn;
    CardView cd_sops;
    CardView cd_orders;
    CardView cd_training;
    CardView cd_help;
    CardView cd_comm;
    CardView cd_fire;
    CardView cd_rescue;
    CardView cd_firstaid;
    CardView cd_disaster;
    CardView cd_selfdef;
    CardView cd_contactus;
    CardView cd_report;





    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard_fragment, container, false);

        courtexy=view.findViewById(R.id.MarqueeText);
        courtexy.setSelected(true);


        cd_hospital=view.findViewById(R.id.cd_hospital);
        cd_civilofc=view.findViewById(R.id.cd_cdofc);
        cd_police=view.findViewById(R.id.cd_policestation);
        cd_orgn=view.findViewById(R.id.cd_orgn);
        cd_sops=view.findViewById(R.id.cd_sops);
        cd_orders=view.findViewById(R.id.cd_orders);
        cd_training=view.findViewById(R.id.cd_training);
        cd_help=view.findViewById(R.id.cd_help);
        cd_comm=view.findViewById(R.id.cd_comm);
        cd_fire=view.findViewById(R.id.cd_fire);
        cd_rescue=view.findViewById(R.id.cd_rescue);
        cd_firstaid=view.findViewById(R.id.cd_firstaid);
        cd_disaster=view.findViewById(R.id.cd_disaster);
        cd_selfdef=view.findViewById(R.id.cd_selfdef);
        cd_contactus=view.findViewById(R.id.cd_contactus);
        cd_report=view.findViewById(R.id.cd_report);


        cd_orgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    replaceFragmentWithAnimation(new FragmentCDOrgans());

                } else {
                    Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

                }
            }
        });
        cd_sops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    replaceFragmentWithAnimation(new FragmentSop());

                } else {
                    Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

                }
            }
        });
        cd_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    replaceFragmentWithAnimation(new FragmentOrders());

                } else {
                    Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

                }
            }
        });
        cd_training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    replaceFragmentWithAnimation(new FragmentTraining());


                } else {
                    Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

                }
            }
        });
        cd_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {

                    replaceFragmentWithAnimation(new FragmentHelp());


                } else {
                    Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

                }
            }
        });
        cd_comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    replaceFragmentWithAnimation(new FragmentCdComm());

                } else {
                    Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

                }
            }
        });
        cd_fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    replaceFragmentWithAnimation(new FragmentFire());

                } else {
                    Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

                }
            }
        });
        cd_rescue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    replaceFragmentWithAnimation(new FragmentRescue());

                } else {
                    Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

                }
            }
        });
        cd_firstaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    replaceFragmentWithAnimation(new FragmentFirstaid());

                } else {
                    Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

                }
            }
        });

        cd_disaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    replaceFragmentWithAnimation(new FragmentDisaster());

                } else {
                    Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

                }
            }
        });
        cd_selfdef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    replaceFragmentWithAnimation(new FragmentDefence());

                } else {
                    Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

                }
            }
        });
        cd_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    replaceFragmentWithAnimation(new FragmentContactus());

                } else {
                    Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

                }
            }
        });

        cd_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {

                    NearByHospital();

                } else {
                    Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

                }
            }
        });
        cd_police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {

                    NearByPoliceStation();

                } else {
                    // Toast.makeText(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();
                    Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

                }
            }
        });

        cd_civilofc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {

                    NearByCDOffice();

                } else {
                    Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

                }
            }
        });
        cd_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    replaceFragmentWithAnimation(new FragmentReport());

                } else {
                    Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

                }
            }
        });

        return view;

    }

    private void NearByPoliceStation() {
        try {
            String uri = "http://maps.google.com/maps?q=policestation";
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

    private void NearByCDOffice() {
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

    private void NearByHospital() {
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
    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

}
