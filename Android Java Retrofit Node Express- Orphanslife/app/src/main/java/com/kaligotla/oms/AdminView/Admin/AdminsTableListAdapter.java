package com.kaligotla.oms.AdminView.Admin;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AdminsTableListAdapter extends RecyclerView.Adapter<AdminsTableListAdapter.MyViewHolder> {

    Context context;
    List<Admin> adminList;
    int aid;

    public AdminsTableListAdapter(Context context, List<Admin> adminList) {
        this.context = context;
        this.adminList = adminList;
    }

    @NonNull
    @Override
    public AdminsTableListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.activity_admins_table_list_adapter, null );
        return new AdminsTableListAdapter.MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull AdminsTableListAdapter.MyViewHolder holder, int position) {
        holder.mobile.setText(adminList.get(position).getAdmin_mobile());
        holder.email.setText(adminList.get(position).getAdmin_email());
    }

    @Override
    public int getItemCount() {
        return adminList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialTextView mobile, email;
        AppCompatImageButton deleteAdmin;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mobile = itemView.findViewById( R.id.mobile );
            email = itemView.findViewById( R.id.email );
            deleteAdmin = itemView.findViewById(R.id.deleteAdmin);
            itemView.setOnClickListener( this );
            deleteAdmin.setOnClickListener(view -> {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                deleteAdmin(adminList.get(getAdapterPosition()).getAdmin_id(),getAdapterPosition());
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Confirm to Delete - \""+adminList.get(getAdapterPosition()).getAdmin_name()+"\" permanently").setNegativeButton("No", dialogClickListener)
                        .setPositiveButton("Confirm", dialogClickListener)
                        .show();
            });
        }

        @Override
        public void onClick(View v) {
            aid = adminList.get(getAdapterPosition()).getAdmin_id();
            Toast.makeText(itemView.getContext(), "Loading "+adminList.get(getAdapterPosition()).getAdmin_name()+" details give sometime", Toast.LENGTH_LONG).show();
            context.startActivity(new Intent(context, AdminTableDetails.class)
                    .putExtra("aid",aid));
        }

        public void deleteAdmin(int deleteAdminId, int position) {
            new Retrofit.Builder()
                    .addConverterFactory( GsonConverterFactory.create() )
                    .baseUrl( Constants.ADMIN_URL )
                    .build()
                    .create( DBService.class )
                    .deleteAdminByID(context.getSharedPreferences("store", MODE_PRIVATE).getString("API_Token",""), deleteAdminId)
                    .enqueue( new Callback<JsonObject>() {

                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            JsonObject data = response.body().getAsJsonObject( "data" );
                            if (data.get("affectedRows").getAsInt()==1) {
                                Toast.makeText(context,"Deleted "+deleteAdminId+"Admin ID from Database",Toast.LENGTH_SHORT).show();
                                adminList.remove(position);
                                notifyItemRemoved(position);
                            }
                            else {
                                Toast.makeText(context,"Unable to Delete, please try again",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    } );
        }
    }
}