package com.kaligotla.oms.AdminView.Child;

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

import java.util.List;

public class ChildsTableListAdapter extends RecyclerView.Adapter<ChildsTableListAdapter.MyViewHolder> {

    Context context;
    List<Child> childList;

    public ChildsTableListAdapter(Context context, List<Child> childList) {
        this.context = context;
        this.childList = childList;
    }

    @NonNull
    @Override
    public ChildsTableListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.activity_childs_table_list_adapter, null );
        return new ChildsTableListAdapter.MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ChildsTableListAdapter.MyViewHolder holder, int position) {
        holder.name.setText(childList.get(position).getName());
        holder.gender.setText(childList.get(position).getGender());
        holder.age.setText(childList.get(position).getDob());
        holder.adoptive_status.setText(""+childList.get(position).getAdoptiveStatus());
    }

    @Override
    public int getItemCount() {
        return childList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialTextView name, gender, age, adoptive_status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById( R.id.name);
            gender = itemView.findViewById( R.id.gender );
            age = itemView.findViewById( R.id.age );
            adoptive_status = itemView.findViewById( R.id.adoptive_status );

            itemView.setOnClickListener( this );
        }

        @Override
        public void onClick(View v) {
            int child_id = childList.get(getAdapterPosition()).getId();
            Log.e("Clicked position",""+childList.get(getAdapterPosition()).getId());
            context.startActivity(new Intent(context, ChildsTableDetails.class)
                    .putExtra("child_id", child_id));
        }
    }
}