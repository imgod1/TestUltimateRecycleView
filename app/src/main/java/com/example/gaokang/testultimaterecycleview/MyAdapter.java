package com.example.gaokang.testultimaterecycleview;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

/**
 * 项目名称：TestUltimateRecycleView
 * 类描述：
 * 创建人：gaokang
 * 创建时间：2016/3/23 17:58
 * 修改人：gaokang
 * 修改时间：2016/3/23 17:58
 * 修改备注：
 */
public class MyAdapter extends UltimateViewAdapter<MyAdapter.MyViewHolder> {

    private Activity context;
    private List<String> titles;

    public MyAdapter(Activity context, List<String> titles) {
        this.context = context;
        this.titles = titles;
    }

    @Override
    public MyViewHolder getViewHolder(View view) {
        return new MyViewHolder(view);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_text, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return titles == null ? 0 : titles.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= titles.size() : position < titles.size()) && (customHeaderView != null ? position > 0 : true)) {
            position -= customHeaderView == null ? 0 : 1;
            holder.txt_title.setText(titles.get(position));
            final int tempPosition = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Click:" + titles.get(tempPosition), Toast.LENGTH_SHORT).show();
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(context, "Long Click:" + titles.get(tempPosition), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }


    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }


    public static class MyViewHolder extends UltimateRecyclerviewViewHolder {

        public TextView txt_title;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
        }
    }
}
