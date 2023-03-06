package com.kaligotla.oms.AdminView.Sponsor;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.kaligotla.oms.R;
import com.kaligotla.oms.SponsorView.Sponsor;

import java.util.List;

public class SponsorsTableListAdapter extends RecyclerView.Adapter<SponsorsTableListAdapter.MyViewHolder> {
    Context context;
    List<Sponsor> sponsorList;

    public SponsorsTableListAdapter(Context context, List<Sponsor> sponsorList) {
        this.context = context;
        this.sponsorList = sponsorList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.activity_sponsor_list, null );
        return new MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull SponsorsTableListAdapter.MyViewHolder holder, int position) {
        holder.mobile.setText(sponsorList.get(position).getMobile());
        holder.email.setText(sponsorList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return sponsorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialTextView mobile, email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mobile = itemView.findViewById( R.id.mobile );
            email = itemView.findViewById( R.id.email );

            itemView.setOnClickListener( this );
        }

        @Override
        public void onClick(View v) {
            int sid = sponsorList.get(getAdapterPosition()).getId();
            context.startActivity(new Intent(context, SponsorTableDetails.class)
                    .putExtra("sid",sid));
        }
    }
}