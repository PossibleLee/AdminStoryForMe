package com.eos.adminstoryforme;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.Holder> {

    private Context context;
    private ArrayList<WritingData> dataList;
    private ArrayList<WritingData> dataListFull;

    public MainAdapter(Context context, ArrayList<WritingData> dataList){
        this.context = context;
        this.dataList = dataList;
        dataListFull = new ArrayList<>(dataList);
    }

    public static class Holder extends RecyclerView.ViewHolder{
        protected ConstraintLayout npCell;
        protected ImageView np_iv_novel_pic;
        protected TextView np_tv_series;
        protected TextView np_tv_novel_num;
        protected TextView np_tv_novel_title;
        protected TextView np_tv_novel_watcher;
        protected TextView np_tv_novel_comment;
        protected TextView np_tv_date;


        public Holder(View view){
            super(view);
            this.npCell = view.findViewById(R.id.npCell);
            this.np_iv_novel_pic = view.findViewById(R.id.np_iv_novel_pic);
            this.np_tv_series = view.findViewById(R.id.np_tv_series);
            this.np_tv_novel_num = view.findViewById(R.id.np_tv_novel_num);
            this.np_tv_novel_title = view.findViewById(R.id.np_tv_novel_title);
            this.np_tv_novel_watcher = view.findViewById(R.id.np_tv_novel_watcher);
            this.np_tv_novel_comment = view.findViewById(R.id.np_tv_novel_comment);
            this.np_tv_date = view.findViewById(R.id.np_tv_date);
        }
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_main, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.Holder holder, final int position) {

        Glide.with(context).load(dataList.get(position).image).into(holder.np_iv_novel_pic);
        holder.np_tv_series.setText(dataList.get(position).series_name);
        holder.np_tv_novel_num.setText(dataList.get(position).episode_num+ "화 | ");
        holder.np_tv_novel_title.setText(dataList.get(position).title);
        holder.np_tv_novel_watcher.setText("조회수 "+dataList.get(position).hits+" ");
        holder.np_tv_novel_comment.setText("| 댓글수 "+dataList.get(position).comment_num);
        holder.np_tv_date.setText(dataList.get(position).date);

        if(dataList.get(position).title.length() > 14) {
            /* Text Auto Scroll 구현 하기*/
            holder.np_tv_novel_title.setSingleLine();

            // 애니메이션 반복
            holder.np_tv_novel_title.setMarqueeRepeatLimit(-1);
            holder.np_tv_novel_title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            holder.np_tv_novel_title.setSelected(true); // 해당 텍스트 뷰에 포커스가 없더라도 문자를 슬라이딩 하기.
        }

        holder.npCell.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WritingActivity.class);
                intent.putExtra("writing_id", dataList.get(position).id);
                intent.putExtra("title", dataList.get(position).title);
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


//    @Override
//    public Filter getFilter() {
//        return dataFilter;
//    }
//
//
//    private final Filter dataFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//
//            ArrayList<WritingData> filteredList = new ArrayList<>();
//
//            if (charSequence == null || charSequence.length() == 0) {
//                filteredList.addAll(dataListFull);
//            } else {
//                String filterPattern = charSequence.toString().toLowerCase().trim();
//
//                for (WritingData item : dataListFull) {
//                    if (item.series_name.toLowerCase().contains(filterPattern)) {
//                        filteredList.add(item);
//                    }
//                }
//            }
//
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//            dataList.clear();
//            dataList.addAll((List) filterResults.values);
//            notifyDataSetChanged();
//        }
//    };
}
