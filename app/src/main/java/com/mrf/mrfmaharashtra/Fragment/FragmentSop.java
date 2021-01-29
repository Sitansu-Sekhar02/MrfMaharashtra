package com.mrf.mrfmaharashtra.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.mrf.mrfmaharashtra.Activity.MainActivity;
import com.mrf.mrfmaharashtra.R;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentSop extends Fragment {
    ArrayList personNames = new ArrayList<>(Arrays.asList("Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6", "Person 7","Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6", "Person 7"));


    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sop_content_main, container, false);

        MainActivity.tvHeaderText.setText(getString(R.string.SOPs));
        MainActivity.iv_menu.setImageResource(R.drawable.ic_back);
        MainActivity.iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

            }
        });
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        SopsAadapter customAdapter = new SopsAadapter(getActivity(), personNames);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

        return view;

    }

    public class SopsAadapter extends  RecyclerView.Adapter<SopsAadapter.ViewHolder> {
        //ArrayList source;
        ArrayList personNames;
        Context context;
        public SopsAadapter(Context context, ArrayList personNames) {
            this.context = context;
            this.personNames = personNames;
        }

        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data_content, parent, false));


        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
           // holder.data_name.setText((Integer) personNames.get(position));


            //get first letter of each String item
            String firstLetter= String.valueOf(personNames.get(position).toString().charAt(0));
            Log.e("First letter",""+firstLetter);
            //String firstLetter = String.valueOf(personNames.get(position).charAt(0));

            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            // generate random color
           // int color = generator.getColor(personNames.get(position));
            int color = generator.getRandomColor();

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(firstLetter, color); // radius in px

            holder.profilePhoto.setImageDrawable(drawable);
            holder.cd_SopsContent.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fads_transitions));


        }


        @Override
        public int getItemCount() {
            return personNames.size();
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



}

