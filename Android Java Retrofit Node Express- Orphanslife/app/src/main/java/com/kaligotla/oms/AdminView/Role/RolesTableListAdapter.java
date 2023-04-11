package com.kaligotla.oms.AdminView.Role;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonObject;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        MaterialTextView id, role;
        AppCompatImageButton deleteRole;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById( R.id.id);
            role = itemView.findViewById( R.id.role);
            deleteRole = itemView.findViewById(R.id.deleteRole);

            itemView.setOnClickListener( this );
            deleteRole.setOnClickListener(view -> {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                deleteRole(roleList.get(getAdapterPosition()).getId(),getAdapterPosition());
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Confirm to Delete - \""+roleList.get(getAdapterPosition()).getRole()+"\" permanently").setNegativeButton("No", dialogClickListener)
                        .setPositiveButton("Confirm", dialogClickListener)
                        .show();
            });
        }

        @Override
        public void onClick(View v) {
            int aid = roleList.get(getAdapterPosition()).getId();
            Log.e("Clicked position",""+roleList.get(getAdapterPosition()).getId());
            context.startActivity(new Intent(context, RolesTableDetails.class)
                    .putExtra("aid",aid));
        }

        public void deleteRole(int deleteRoleId, int position) {
            new Retrofit.Builder()
                    .addConverterFactory( GsonConverterFactory.create() )
                    .baseUrl( Constants.BASE_URL )
                    .build()
                    .create( DBService.class )
                    .deleteRoleByID(deleteRoleId)
                    .enqueue( new Callback<JsonObject>() {

                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            JsonObject data = response.body().getAsJsonObject( "data" );
                            if (data.get("affectedRows").getAsInt()==1) {
                                Toast.makeText(context,"Role ID "+deleteRoleId+" deleted from Database",Toast.LENGTH_LONG).show();
                                roleList.remove(position);
                                notifyItemRemoved(position);
                            }
                            else {
                                Toast.makeText(context,"Unable to Delete, please try again",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(context,"DB Connection failed, please check wifi / mobile data",Toast.LENGTH_SHORT).show();
                        }
                    } );
        }
    }
}