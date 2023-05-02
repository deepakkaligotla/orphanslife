package com.kaligotla.oms.AdminView.Sponsor;

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
import com.kaligotla.oms.SponsorView.Sponsor;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        holder.mobile.setText(sponsorList.get(position).getSponsor_mobile());
        holder.email.setText(sponsorList.get(position).getSponsor_email());
    }

    @Override
    public int getItemCount() {
        return sponsorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialTextView mobile, email;
        AppCompatImageButton deleteSponsor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mobile = itemView.findViewById( R.id.mobile );
            email = itemView.findViewById( R.id.email );
            deleteSponsor = itemView.findViewById(R.id.deleteSponsor);

            itemView.setOnClickListener( this );
            deleteSponsor.setOnClickListener(view -> {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                deleteSponsor(sponsorList.get(getAdapterPosition()).getSponsor_id(),getAdapterPosition());
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Confirm to Delete - \""+sponsorList.get(getAdapterPosition()).getSponsor_name()+"\" permanently").setNegativeButton("No", dialogClickListener)
                        .setPositiveButton("Confirm", dialogClickListener)
                        .show();
            });
        }

        @Override
        public void onClick(View v) {
            int sid = sponsorList.get(getAdapterPosition()).getSponsor_id();
            context.startActivity(new Intent(context, SponsorTableDetails.class)
                    .putExtra("sid",sid));
        }

        public void deleteSponsor(int deleteSponsorId, int position) {
            new Retrofit.Builder()
                    .addConverterFactory( GsonConverterFactory.create() )
                    .baseUrl( Constants.BASE_URL )
                    .build()
                    .create( DBService.class )
                    .deleteSponsorByID(context.getSharedPreferences("store", Context.MODE_PRIVATE).getString("API_Token",""), deleteSponsorId)
                    .enqueue( new Callback<JsonObject>() {

                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            JsonObject data = response.body().getAsJsonObject( "data" );
                            if (data.get("affectedRows").getAsInt()==1) {
                                Toast.makeText(context,"Deleted "+deleteSponsorId+" Sponsor ID from Database",Toast.LENGTH_SHORT).show();
                                sponsorList.remove(position);
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