package com.eos.adminstoryforme;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.Holder>{

    private Context context;
    private ArrayList<Notice_data> dataList;

    public NoticeListAdapter(Context context, ArrayList<Notice_data> dataList){
        this.context = context;
        this.dataList = dataList;
        Log.e("log","make");
    }

    public static class Holder extends RecyclerView.ViewHolder{
        protected ConstraintLayout cell;

        protected TextView nl_tv_title;
        protected TextView nl_tv_num;
        protected TextView nl_tv_date;


        public Holder(View view){
            super(view);
            this.cell = view.findViewById(R.id.notice_cell);
            this.nl_tv_date = view.findViewById(R.id.nl_tv_date);
            this.nl_tv_num = view.findViewById(R.id.nl_tv_num);
            this.nl_tv_title = view.findViewById(R.id.nl_tv_title);
        }
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_notice, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        Log.d("log", dataList.get(position).title);

        holder.nl_tv_title.setText(dataList.get(position).title);
        holder.nl_tv_num.setText((position + 1) + ". ");
        holder.nl_tv_date.setText(dataList.get(position).date);


        if(dataList.get(position).title.length() > 14) {
            /* Text Auto Scroll 구현 하기*/
            holder.nl_tv_title.setSingleLine();

            // 애니메이션 반복
            holder.nl_tv_title.setMarqueeRepeatLimit(-1);
            holder.nl_tv_title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            holder.nl_tv_title.setSelected(true); // 해당 텍스트 뷰에 포커스가 없더라도 문자를 슬라이딩 하기.
        }

        holder.cell.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Notice.class);
                intent.putExtra("id", dataList.get(position).id);
                intent.putExtra("title", dataList.get(position ).title);
                intent.putExtra("context", dataList.get(position).contents);
                intent.putExtra("date", dataList.get(position).date);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){
        if(dataList == null){
            return 0;
        }
        else{
            return dataList.size();
        }
    }
}
