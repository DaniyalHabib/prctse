package com.hl3hl3.arcoremeasure.adapter.admin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hl3hl3.arcoremeasure.R;
import com.hl3hl3.arcoremeasure.Slot_details;
import com.hl3hl3.arcoremeasure.model.admin.p_status;

import java.util.ArrayList;

public class detail_adapter extends RecyclerView.Adapter<detail_adapter.ViewHolder> {

    public View view;
    public static String TAG = detail_adapter.class.getName();
    Context mContext;
    public ArrayList<p_status> arrayList = new ArrayList<>();


    public interface OnRecyclerItemClickListener {

        void onRecyclerItemClick(String data);
    }

    public interface OnItemClickListener {
        void onItemClick(p_status item);
    }

    private ArrayList<String> dataSource;

    private OnItemClickListener listener;


    public detail_adapter(Context ctx, ArrayList<p_status> arrayList, OnItemClickListener listener) {

        this.mContext = ctx;
        this.arrayList = arrayList;
        this.listener = listener;
        Log.d("add","dasd"+arrayList.toString());
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
        view = LayoutInflater.from(mContext).inflate(R.layout.status_child_item, parent, false);


        return new ViewHolder(view, mContext);
    }




    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        p_status example = arrayList.get(position);
        Log.d("getAreaName", "getAreaName" + example.getAreaName() + "---"+arrayList.get(position).getAreaName());
        holder.txt_p_name.setText(arrayList.get(position).getAreaName());
        holder.txt_adress.setText(arrayList.get(position).getLocName());

        holder.detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mContext.startActivity(new Intent(mContext,Slot_details.class));
            }
        });

        holder.text_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mContext.startActivity(new Intent(mContext,Slot_details.class));
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Context context;
        TextView txt_p_name,date,txt_adress,text_detail;
        ImageView detail_btn;

        public ViewHolder(final View itemView, Context ctx) {
            super(itemView);

            context = ctx;
            txt_p_name = (TextView) itemView.findViewById(R.id.txt_p_name);
            txt_adress = (TextView) itemView.findViewById(R.id.txt_adress);
            date = (TextView) itemView.findViewById(R.id.date);
            text_detail = (TextView) itemView.findViewById(R.id.text_detail);
            detail_btn = (ImageView) itemView.findViewById(R.id.detail_btn);

        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @SuppressLint("ResourceType")
        @Override
        public void onClick(View view) {


        }
    }
    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    }
