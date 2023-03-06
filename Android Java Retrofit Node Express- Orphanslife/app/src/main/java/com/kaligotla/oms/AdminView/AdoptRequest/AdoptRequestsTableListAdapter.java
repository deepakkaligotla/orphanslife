package com.kaligotla.oms.AdminView.AdoptRequest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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

public class AdoptRequestsTableListAdapter extends RecyclerView.Adapter<AdoptRequestsTableListAdapter.MyViewHolder> {

    Context context;
    List<AdoptRequest> adoptRequestList;
    int req_no;

    public AdoptRequestsTableListAdapter(Context context, List<AdoptRequest> adoptRequestList) {
        this.context = context;
        this.adoptRequestList = adoptRequestList;
    }

    @NonNull
    @Override
    public AdoptRequestsTableListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.activity_adopt_requests_table_list_adapter, null );
        return new AdoptRequestsTableListAdapter.MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull AdoptRequestsTableListAdapter.MyViewHolder holder, int position) {
        Log.e("ID",""+adoptRequestList.get(position).getReq_no());
        holder.reqNo.setText(""+adoptRequestList.get(position).getReq_no());
        holder.reqStage.setText(adoptRequestList.get(position).getReq_stage());
    }

    @Override
    public int getItemCount() {
        return adoptRequestList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialTextView reqNo, reqStage;
        AppCompatImageButton deleteAdoptReq;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            reqNo = itemView.findViewById( R.id.reqNo);
            reqStage = itemView.findViewById( R.id.reqStage);
            deleteAdoptReq = itemView.findViewById(R.id.deleteAdoptReq);
            itemView.setOnClickListener( this );

            deleteAdoptReq.setOnClickListener(view -> {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                deleteAdoptReq(adoptRequestList.get(getAdapterPosition()).getReq_no(),getAdapterPosition());
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Confirm to Delete - \""+reqNo.getText()+"\" permanently").setNegativeButton("No", dialogClickListener)
                        .setPositiveButton("Confirm", dialogClickListener)
                        .show();
            });
        }

        @Override
        public void onClick(View v) {
            req_no = adoptRequestList.get(getAdapterPosition()).getReq_no();
            Log.e("Clicked position",""+adoptRequestList.get(getAdapterPosition()).getReq_no());
            context.startActivity(new Intent(context, AdoptRequestsTableDetails.class)
                    .putExtra("req_no",req_no));
        }

        public void deleteAdoptReq(int deleteAdoptReqNo, int position) {
            new Retrofit.Builder()
                    .addConverterFactory( GsonConverterFactory.create() )
                    .baseUrl( Constants.BASE_URL )
                    .build()
                    .create( DBService.class )
                    .deleteAdoptReqByID(deleteAdoptReqNo)
                    .enqueue( new Callback<JsonObject>() {

                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            JsonObject data = response.body().getAsJsonObject( "data" );
                            if (data.get("affectedRows").getAsInt()==1) {
                                Toast.makeText(context,"Deleted Adopt Request - "+deleteAdoptReqNo+" from Database",Toast.LENGTH_SHORT).show();
                                adoptRequestList.remove(position);
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