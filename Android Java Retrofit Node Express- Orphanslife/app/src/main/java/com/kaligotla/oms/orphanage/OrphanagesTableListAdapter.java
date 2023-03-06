package com.kaligotla.oms.orphanage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kaligotla.oms.R;

import java.util.List;

public class OrphanagesTableListAdapter extends RecyclerView.Adapter<OrphanagesTableListAdapter.MyViewHolder> {
    Context context;
    List<Orphanage> orphanageList;

    public OrphanagesTableListAdapter(Context context, List<Orphanage> orphanageList) {
        this.context = context;
        this.orphanageList = orphanageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.activity_orphanages_table_list_adapter, null );
        return new MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull OrphanagesTableListAdapter.MyViewHolder holder, int position) {
        holder.id.setText(""+orphanageList.get(position).getId());
        holder.orphange_image.setText(orphanageList.get(position).getOrphanage_image());
        holder.in_home.setText(""+orphanageList.get(position).getIn_home());
        holder.adoptable.setText(""+orphanageList.get(position).getAdoptable());

    }

    @Override
    public int getItemCount() {
        return orphanageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id, orphange_image, in_home, adoptable;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById( R.id.id);
            orphange_image = itemView.findViewById( R.id.orphange_image );
            in_home = itemView.findViewById( R.id.in_home );
            adoptable = itemView.findViewById( R.id.adoptable );


            itemView.setOnClickListener( this );
        }

        @Override
        public void onClick(View v) {
            int sid = orphanageList.get(getAdapterPosition()).getId();
            context.startActivity(new Intent(context, OrphanagesTableDetails.class)
                    .putExtra("sid",sid));
        }
    }
}