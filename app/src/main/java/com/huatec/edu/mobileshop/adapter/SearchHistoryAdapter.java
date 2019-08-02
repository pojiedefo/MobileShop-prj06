package com.huatec.edu.mobileshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huatec.edu.mobileshop.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/16.
 */

public class SearchHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View
        .OnClickListener {
    private Context mContext;
    private List<String> mData;
    private LayoutInflater mInflater;
    //自定义的监听器
    private OnHistoryItemClickListener mOnItemClickListener = null;

    public SearchHistoryAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mData = data;
        mInflater = LayoutInflater.from(context);
    }
    //加载条目视图，并加载到ViewHolder中
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_search_history, parent, false);
        view.setOnClickListener(this);
        return new ItemViewHolder(view);
    }
    //数据适配
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.keyword.setText(mData.get(position));
            itemViewHolder.itemView.setTag(mData.get(position));
        }
    }
    //返回数据大小
    @Override
    public int getItemCount() {
        return mData.size();
    }
    //自定义一个点击事件方法
    public void setOnItemClickListener(OnHistoryItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    //item view的点击事件
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (String) v.getTag());
        }
    }
    //自定义的ViewHolder类
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.keyword)
        TextView keyword;
        @BindView(R.id.keyword_correct_layout)
        LinearLayout keywordCorrectLayout;

        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    //自定义的监听器
    public interface OnHistoryItemClickListener {
        void onItemClick(View view, String data);
    }
}
