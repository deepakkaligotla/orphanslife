package com.kaligotla.oms.AdminView.Role;

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

public class RolesTableListAdapter extends RecyclerView.Adapter<RolesTableListAdapter.MyViewHolder> {

    Context context;
    List<Role> roleList;

    public RolesTableListAdapter(Context context, List<Role> roleList) {
        this.context = context;
        this.roleList = roleList;
    }

    @NonNull
    @Override
    public RolesTableListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.activity_roles_table_list_adapter, null );
        return new RolesTableListAdapter.MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull RolesTableListAdapter.MyViewHolder holder, int position) {
        Log.e("ID",""+roleList.get(position).getId());
        holder.id.setText(""+roleList.get(position).getId());
        holder.role.setText(roleList.get(position).getRole());
    }

    @Override
    public int getItemCount() {
        return roleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id, role;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById( R.id.id);
            role = itemView.findViewById( R.id.role);

            itemView.setOnClickListener( this );
        }

        @Override
        public void onClick(View v) {
            int aid = roleList.get(getAdapterPosition()).getId();
            Log.e("Clicked position",""+roleList.get(getAdapterPosition()).getId());
            context.startActivity(new Intent(context, RolesTableDetails.class)
                    .putExtra("aid",aid));
        }
    }
}