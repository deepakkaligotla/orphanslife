package com.kaligotla.oms.AdminView.Location;

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

import java.util.List;

public class LocationsTableListAdapter extends RecyclerView.Adapter<LocationsTableListAdapter.MyViewHolder> {

    Context context;
    List<Location> locationList;

    public LocationsTableListAdapter(Context context, List<Location> locationList) {
        this.context = context;
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public LocationsTableListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.activity_locations_table_list_adapter, null );
        return new LocationsTableListAdapter.MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull LocationsTableListAdapter.MyViewHolder holder, int position) {
        Log.e("ID",""+locationList.get(position).getId());
        holder.id.setText(""+locationList.get(position).getId());
        holder.pincode.setText(""+locationList.get(position).getPincode());
        holder.district.setText(locationList.get(position).getDistrict());
        holder.state.setText(locationList.get(position).getState());
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id, pincode, district, state;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById( R.id.id);
            pincode = itemView.findViewById( R.id.pincode);
            district = itemView.findViewById( R.id.district);
            state = itemView.findViewById( R.id.state);

            itemView.setOnClickListener( this );
        }

        @Override
        public void onClick(View v) {
            int location_id = locationList.get(getAdapterPosition()).getId();
            Log.e("Clicked position",""+locationList.get(getAdapterPosition()).getId());
            context.startActivity(new Intent(context, LocationsTableDetails.class)
                    .putExtra("location_id",location_id));
        }
    }
}