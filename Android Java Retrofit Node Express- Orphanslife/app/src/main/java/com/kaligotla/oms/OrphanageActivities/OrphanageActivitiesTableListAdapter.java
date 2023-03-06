package com.kaligotla.oms.OrphanageActivities;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kaligotla.oms.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrphanageActivitiesTableListAdapter extends RecyclerView.Adapter<OrphanageActivitiesTableListAdapter.MyViewHolder>  {

    Context context;
    List<OrphanageActivities> orphanageActivitiesList;

    public OrphanageActivitiesTableListAdapter(Context context, List<OrphanageActivities> orphanageActivitiesList) {
        this.context = context;
        this.orphanageActivitiesList = orphanageActivitiesList;
    }

    @NonNull
    @Override
    public OrphanageActivitiesTableListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.activity_orphanage_activities_table_list_adapter, null );
        return new OrphanageActivitiesTableListAdapter.MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(!(orphanageActivitiesList.get( position ).getImage_1().equals(""))) {
            Picasso.with(context).load(orphanageActivitiesList.get( position ).getImage_1()).into(holder.images);
        } else if(orphanageActivitiesList.get( position ).getImage_1().equals("")) {
            Picasso.with(context).load(R.drawable.image_not_available).into(holder.images);
        }
    }

    @Override
    public int getItemCount() {
        return orphanageActivitiesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        pl.droidsonroids.gif.GifImageView images;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById( R.id.images );
            itemView.setOnClickListener( this );
        }

        @Override
        public void onClick(View v) {
            int aid = orphanageActivitiesList.get(getAdapterPosition()).getEvent_id();
            Log.e("Clicked position",""+orphanageActivitiesList.get(getAdapterPosition()).getEvent_id());
            context.startActivity(new Intent(context, OrphanageActivitiesTableDetails.class)
                    .putExtra("aid",aid));
        }
    }
}