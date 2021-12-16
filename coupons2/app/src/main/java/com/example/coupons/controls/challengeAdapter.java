package com.example.coupons.controls;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coupons.R;
import com.example.coupons.model.challenge_model;
import com.example.coupons.user.Challenge;

import java.util.ArrayList;
import java.util.List;

public class challengeAdapter extends RecyclerView.Adapter<challengeAdapter.challengeViewHolder> implements View.OnClickListener {

    private Context context;
    private List<challenge_model> challengeList;

    public challengeAdapter(Context context, List<challenge_model> challengeList) {
        this.context = context;
        this.challengeList = challengeList;
    }

//    public void clearAdapter() {
//        this.challengeList.clear();
//    }


    @NonNull
    @Override
    public challengeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.near_challange, null);
        challengeViewHolder holder= new challengeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull challengeViewHolder holder, int position) {
        challenge_model challenge= challengeList.get(position);
//        holder.idTV.setText(""+challenge.getId());
        context = holder.itemView.getContext();
        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                // redirect user to the challenge
                Intent i = new Intent(context, Challenge.class);
                i.putExtra("id", challengeList.get(0).getId()+"");
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return challengeList.size();
    }

    @Override
    public void onClick(View v) {

    }

    class  challengeViewHolder extends RecyclerView.ViewHolder{

        Button playButton;
//        TextView idTV;

        public challengeViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

//            idTV = itemView.findViewById(R.id.idTV);
            playButton= itemView.findViewById(R.id.playButton);
        }
    }
}
