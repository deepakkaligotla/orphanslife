package com.kaligotla.oms.AdminView.AdoptiveStatus;

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

public class AdoptiveStatusTableListAdapter extends RecyclerView.Adapter<AdoptiveStatusTableListAdapter.MyViewHolder> {

    Context context;
    List<AdoptiveStatus> adoptiveStatusList;

    int adoptiveStatusId;

    public AdoptiveStatusTableListAdapter(Context context, List<AdoptiveStatus> adoptiveStatusList) {
        this.context = context;
        this.adoptiveStatusList = adoptiveStatusList;
    }

    @NonNull
    @Override
    public AdoptiveStatusTableListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.activity_adoptive_status_table_list_adapter, null );
        return new AdoptiveStatusTableListAdapter.MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull AdoptiveStatusTableListAdapter.MyViewHolder holder, int position) {
        Log.e("ID",""+adoptiveStatusList.get(position).getId());
        holder.id.setText(""+adoptiveStatusList.get(position).getId());
        holder.status.setText(adoptiveStatusList.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return adoptiveStatusList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id, status;
        AppCompatImageButton deleteAdoptiveStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById( R.id.id);
            status = itemView.findViewById( R.id.status);
            deleteAdoptiveStatus = itemView.findViewById(R.id.deleteAdoptiveStatus);
            itemView.setOnClickListener( this );
            deleteAdoptiveStatus.setOnClickListener(view -> {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                deleteAdoptiveStatus(adoptiveStatusList.get(getAdapterPosition()).getId(),getAdapterPosition());
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Confirm to Delete - \""+status.getText()+"\" permanently").setNegativeButton("No", dialogClickListener)
                        .setPositiveButton("Confirm", dialogClickListener)
                        .show();
            });
        }

        @Override
        public void onClick(View v) {
            adoptiveStatusId = adoptiveStatusList.get(getAdapterPosition()).getId();
            Log.e("Clicked position",""+adoptiveStatusList.get(getAdapterPosition()).getId());
            context.startActivity(new Intent(context, AdoptiveStatusTableDetails.class)
                    .putExtra("adoptiveStatusId",adoptiveStatusId));
        }

        public void deleteAdoptiveStatus(int deleteAdoptiveStatusId, int position) {
            new Retrofit.Builder()
                    .addConverterFactory( GsonConverterFactory.create() )
                    .baseUrl( Constants.BASE_URL )
                    .build()
                    .create( DBService.class )
                    .deleteAdoptiveStatusByID(deleteAdoptiveStatusId)
                    .enqueue( new Callback<JsonObject>() {

                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            JsonObject data = response.body().getAsJsonObject( "data" );
                            if (data.get("affectedRows").getAsInt()==1) {
                                Toast.makeText(context,"Deleted Adoptive Status ID - "+deleteAdoptiveStatusId+" from Database",Toast.LENGTH_SHORT).show();
                                adoptiveStatusList.remove(position);
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