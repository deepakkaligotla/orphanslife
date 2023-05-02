package com.kaligotla.oms.AdminView.Child;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonObject;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.R;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ChildsTableListAdapter.MyViewHolder holder, int position) {
        holder.name.setText(childList.get(position).getChild_name());
        holder.gender.setText(childList.get(position).getChild_gender());
        if(childList.get(position).getChild_dob()!=null) {
            LocalDate dob = LocalDate.parse(childList.get(position).getChild_dob());
            LocalDate curDate = LocalDate.now();
            holder.age.setText(""+Period.between(dob, curDate).getYears());
        } else holder.age.setText("NA");
        holder.adoptive_status.setText(""+childList.get(position).getAdoptiveStatus());
    }

    @Override
    public int getItemCount() {
        return childList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialTextView name, gender, age, adoptive_status;
        AppCompatImageButton deleteChild;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById( R.id.name);
            gender = itemView.findViewById( R.id.gender );
            age = itemView.findViewById( R.id.age );
            adoptive_status = itemView.findViewById( R.id.adoptive_status );
            deleteChild = itemView.findViewById(R.id.deleteChild);

            itemView.setOnClickListener( this );
            deleteChild.setOnClickListener(view -> {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                deleteChild(childList.get(getAdapterPosition()).getChild_id(),getAdapterPosition());
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Confirm to Delete - \""+childList.get(getAdapterPosition()).getChild_name()+"\" permanently").setNegativeButton("No", dialogClickListener)
                        .setPositiveButton("Confirm", dialogClickListener)
                        .show();
            });
        }

        @Override
        public void onClick(View v) {
            int child_id = childList.get(getAdapterPosition()).getChild_id();
            Log.e("Clicked position",""+childList.get(getAdapterPosition()).getChild_id());
            context.startActivity(new Intent(context, ChildsTableDetails.class)
                    .putExtra("child_id", child_id));
        }

        public void deleteChild(int deleteChildId, int position) {
            new Retrofit.Builder()
                    .addConverterFactory( GsonConverterFactory.create() )
                    .baseUrl( Constants.BASE_URL )
                    .build()
                    .create( DBService.class )
                    .deleteChildByID(context.getSharedPreferences("store",Context.MODE_PRIVATE).getString("API_Token",""), deleteChildId)
                    .enqueue( new Callback<JsonObject>() {

                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            JsonObject data = response.body().getAsJsonObject( "data" );
                            if (data.get("affectedRows").getAsInt()==1) {
                                Toast.makeText(context,"Child ID "+deleteChildId+" deleted from Database",Toast.LENGTH_SHORT).show();
                                childList.remove(position);
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