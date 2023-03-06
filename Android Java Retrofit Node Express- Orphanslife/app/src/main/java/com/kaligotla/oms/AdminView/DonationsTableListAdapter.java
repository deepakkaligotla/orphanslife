package com.kaligotla.oms.AdminView;

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

public class DonationsTableListAdapter extends RecyclerView.Adapter<DonationsTableListAdapter.MyViewHolder> {

    Context context;
    List<Donation> donationList;

    public DonationsTableListAdapter(Context context, List<Donation> donationList) {
        this.context = context;
        this.donationList = donationList;
    }

    @NonNull
    @Override
    public DonationsTableListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.activity_donations_table_list_adapter, null );
        return new DonationsTableListAdapter.MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull DonationsTableListAdapter.MyViewHolder holder, int position) {
        Log.e("ID",""+donationList.get(position).getId());
        holder.id.setText(""+donationList.get(position).getId());
        holder.amount.setText(""+donationList.get(position).getAmount());
        holder.payment_status.setText(donationList.get(position).getPayment_status());
        holder.sponsor.setText(""+donationList.get(position).getSponsor().getId());
    }

    @Override
    public int getItemCount() {
        return donationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id, amount, payment_status, sponsor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById( R.id.id);
            amount = itemView.findViewById( R.id.amount);
            payment_status = itemView.findViewById(R.id.payment_status);
            sponsor = itemView.findViewById(R.id.sponsor);

            itemView.setOnClickListener( this );
        }

        @Override
        public void onClick(View v) {
            int donation_id = donationList.get(getAdapterPosition()).getId();
            Log.e("Clicked position",""+donationList.get(getAdapterPosition()).getId());
            context.startActivity(new Intent(context, DonationsTableDetails.class)
                    .putExtra("donation_id",donation_id));
        }
    }
}