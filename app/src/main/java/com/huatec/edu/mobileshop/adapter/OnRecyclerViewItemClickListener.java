package com.huatec.edu.mobileshop.adapter;

import android.view.View;

import com.huatec.edu.mobileshop.entity.CategoryEntity;

/**
 * Created by Administrator on 2016/11/7.
 */

public interface OnRecyclerViewItemClickListener {
    void onItemClick(View view , CategoryEntity data);
}
