package com.example.goku.contacts;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

public class ContactApdapter extends RecyclerView.Adapter<ContactApdapter.ViewHolder> {
    private ArrayList<Item> mArrItem;
    private Context mContext;

    public ContactApdapter(ArrayList<Item> mArrItem, Context mContext) {
        this.mArrItem = mArrItem;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.activity_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.mTextViewPhone.setText(mArrItem.get(i).getmPhone());
        viewHolder.mTextViewName.setText(mArrItem.get(i).getmName());
        call(viewHolder.mImageViewCall,mArrItem.get(i).getmPhone());

        viewHolder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemCLickListener!=null){
                    onItemCLickListener.onItemClick(mArrItem.get(i).getmName(),mArrItem.get(i).getmPhone(),i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView mTextViewName;
        protected TextView mTextViewPhone;
        private ImageView mImageViewCall;
        LinearLayout line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageViewCall = itemView.findViewById(R.id.image_call);
            mTextViewName = itemView.findViewById(R.id.text_name);
            mTextViewPhone = itemView.findViewById(R.id.text_phone);
            line=itemView.findViewById(R.id.line);
        }
    }
    public void call(ImageView imageView, final String phone) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                mContext.startActivity(intent);
            }
        });
    }
    public interface OnItemCLickListener{
        void onItemClick(String name,String phone,int i);
    }
    private OnItemCLickListener onItemCLickListener;

    public void setOnItemCLickListener1(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener=onItemCLickListener;
    }

}


