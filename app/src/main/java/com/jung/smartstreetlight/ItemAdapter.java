package com.jung.smartstreetlight;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapter extends RecyclerView.Adapter{
    //리사이클러뷰에 넣을 데이터 리스트
    ArrayList<ItemData> dataModels;
    Context context;

    //생성자를 통하여 데이터 리스트 context를 받음
    public ItemAdapter(Context context, ArrayList<ItemData> dataModels){
        this.dataModels = dataModels;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        //데이터 리스트의 크기를 전달해주어야 함
        return dataModels.size();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //자신이 만든 itemview를 inflate한 다음 뷰홀더 생성
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.info_recycler,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);


        //생선된 뷰홀더를 리턴하여 onBindViewHolder에 전달한다.
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder)holder;

        myViewHolder.textView.setText(dataModels.get(position).getTitle());
        myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, position+"번째 텍스트 뷰 클릭", Toast.LENGTH_SHORT).show();
            }
        });

        myViewHolder.imageView.setImageResource(dataModels.get(position).image_path);
        myViewHolder.content.setText(dataModels.get(position).getContent());

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        TextView content;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView =  itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            imageView = itemView.findViewById(R.id.image);

       //     imageView.setBackground(new ShapeDrawable(new OvalShape()));
        //    imageView.setClipToOutline(true);
        }
    }


}
