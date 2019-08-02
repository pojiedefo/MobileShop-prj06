package com.huatec.edu.mobileshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.entity.AddressEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */

public class AddressListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AddressEntity> mDatas;
    private Context mContext;

    public AddressListAdapter(Context context, List<AddressEntity> data) {
        super();
        this.mContext = context;
        this.mDatas = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_address_list, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AddressEntity entity = mDatas.get(position);
        if (holder instanceof MyHolder) {
            MyHolder newHolder = (MyHolder) holder;
            newHolder.tv_name.setText(entity.getReceiver());
            String address = entity.getProvice() + entity.getCity() + entity.getRegion() + entity.getAddr();
            newHolder.tv_content.setText(address);
            newHolder.tv_phone.setText(entity.getMobile());

            newHolder.iv_arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "编辑地址", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private static class MyHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_content, tv_phone;
        private ImageView iv_arrow;

        public MyHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.address_name);  //收件人
            tv_content = (TextView) itemView.findViewById(R.id.address_content); //地址
            tv_phone = (TextView) itemView.findViewById(R.id.address_phone); //手机号
            iv_arrow = (ImageView) itemView.findViewById(R.id.address_arrow);
        }
    }
}
