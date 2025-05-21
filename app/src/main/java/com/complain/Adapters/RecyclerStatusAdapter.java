package com.complain.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.complain.Models.StatusModel;
import com.complain.R;

import java.util.ArrayList;

public class RecyclerStatusAdapter extends RecyclerView.Adapter<RecyclerStatusAdapter.viewholder> {

    Context context;
    ArrayList<StatusModel> arr_statusdetail;

    public RecyclerStatusAdapter(Context context,ArrayList<StatusModel> arr_statusdetail){
        this.context=context;
        this.arr_statusdetail=arr_statusdetail;
    }


    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.status_model,parent,false);
        viewholder viewholder=new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.complainname.setText(arr_statusdetail.get(position).getCName());
        holder.date.setText(arr_statusdetail.get(position).getDate());
        holder.statusname.setText(arr_statusdetail.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return arr_statusdetail.size();
    }

    public  class viewholder extends RecyclerView.ViewHolder {

        TextView complainname,date,statusname;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            complainname=itemView.findViewById(R.id.complainname);
            date=itemView.findViewById(R.id.date);
            statusname=itemView.findViewById(R.id.statusname);
        }
    }
}
