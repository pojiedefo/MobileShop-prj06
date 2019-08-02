package com.huatec.edu.mobileshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.entity.CategoryEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */

public class CategoryLeftListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View
        .OnClickListener {
    private Context mContext;
    private List<CategoryEntity> mLeftData;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    //选中的索引
    private int selectedCategoryId = 0;

    public CategoryLeftListAdapter(Context context, List<CategoryEntity> leftData) {
        this.mContext = context;
        this.mLeftData = leftData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_list_left, parent, false);
        ViewHolder holder = new ViewHolder(view);

        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CategoryLeftListAdapter.ViewHolder) {
            CategoryLeftListAdapter.ViewHolder newHolder = (CategoryLeftListAdapter.ViewHolder) holder;
            //填充数据
            CategoryEntity entity = mLeftData.get(position);
            newHolder.textView.setText(entity.getName());
            //将数据保存在itemView的Tag中，以便点击时进行获取
            newHolder.itemView.setTag(entity);

            //修改状态
            if (entity.getCat_id() == selectedCategoryId) {
                newHolder.itemView.setBackgroundResource(R.drawable.category_left_bg_focus);
                newHolder.textView.setTextColor(holder.itemView.getResources().getColor(R.color
                        .category_left_red_font));
            } else {
                holder.itemView.setBackgroundResource(R.drawable.category_left_bg_normal);
                newHolder.textView.setTextColor(holder.itemView.getResources().getColor(R.color
                        .category_left_dark_font));
            }
        }

    }

    @Override
    public int getItemCount() {
        return mLeftData.size();
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /**
     * 设置选中行
     *
     * @param categoryId
     */
    public void setSelection(int categoryId) {
        selectedCategoryId = categoryId;
        this.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            CategoryEntity entity = (CategoryEntity) view.getTag();
            mOnItemClickListener.onItemClick(view, entity);
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
