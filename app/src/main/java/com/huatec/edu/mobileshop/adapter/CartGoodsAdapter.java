package com.huatec.edu.mobileshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.activity.GoodsActivity;
import com.huatec.edu.mobileshop.entity.CartGoodsEntity;
import com.huatec.edu.mobileshop.entity.HttpResult;
import com.huatec.edu.mobileshop.http.ProgressDialogSubscriber;
import com.huatec.edu.mobileshop.http.presenter.GoodsPresenter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */

public class CartGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CartGoodsEntity> mDatas;
    private Context mContext;

    public CartGoodsAdapter(Context context, List<CartGoodsEntity> data) {
        super();
        this.mContext = context;
        this.mDatas = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cart_goods_list, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final CartGoodsEntity entity = mDatas.get(position);
        if (holder instanceof MyHolder) {
            final MyHolder newholder = (MyHolder) holder;

            newholder.tv_title.setText(entity.getBriefGoods().getName());
            newholder.tv_price.setText("￥" + entity.getAmount());
            newholder.tv_num.setText("" + entity.getGoods_num());
            ImageLoader.getInstance().displayImage(entity.getBriefGoods().getSmall(), newholder.iv_image);


            newholder.ib_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateNumer(entity, 1);
                }
            });
            newholder.ib_reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateNumer(entity, -1);
                }
            });
            newholder.iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GoodsActivity.class);
                    intent.putExtra("goodsId", entity.getGoods_id());
                    mContext.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private static class MyHolder extends RecyclerView.ViewHolder {
        private TextView tv_title, tv_price, tv_num;
        private ImageView iv_image;
        private ImageButton ib_add, ib_reduce;

        public MyHolder(View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.cart_name);
            tv_price = (TextView) itemView.findViewById(R.id.cart_price);
            tv_num = (TextView) itemView.findViewById(R.id.cart_list_number);

            iv_image = (ImageView) itemView.findViewById(R.id.cart_image);

            ib_add = (ImageButton) itemView.findViewById(R.id.cart_add);
            ib_reduce = (ImageButton) itemView.findViewById(R.id.cart_reduce);
        }
    }

    /**
     * 更新数量
     */
    private void updateNumer(CartGoodsEntity entity, int number) {
        int cartId = entity.getCart_id();
        int num = entity.getGoods_num();

        if (num <= 1 && number == -1) {
            Toast.makeText(mContext, "已经是最小数量了！", Toast.LENGTH_SHORT).show();
            return;
        } else {
            num += number;
        }
        final int finalNum = num;
        GoodsPresenter.cartNumUpdate(new ProgressDialogSubscriber<HttpResult>(mContext) {
            @Override
            public void onNext(HttpResult httpResult) {
                Log.e("CartGoodsAdapter", httpResult.toString());
                int status = httpResult.getStatus();
                switch (status) {
                    case 0:
                        //发送广播去更新商品
                        Intent intent = new Intent();
                        intent.setAction("com.goods.shoopingcart");
                        mContext.sendBroadcast(intent);
                        break;
                    case 1:
                        Toast.makeText(mContext, "修改失败！", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, cartId, num);
    }


}
