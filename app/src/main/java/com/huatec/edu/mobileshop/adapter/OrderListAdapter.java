package com.huatec.edu.mobileshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.common.ImageLoaderManager;
import com.huatec.edu.mobileshop.entity.OrderEntity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private List<OrderEntity> orderList;
    private Context mContext;

    public OrderListAdapter(Context context, List<OrderEntity> orderList) {
        this.mContext = context;
        this.orderList = orderList;
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * item显示类型
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_orderlist, parent, false);
        //这边可以做一些属性设置，甚至事件监听绑定
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * 数据的绑定显示
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //获取当前位置的订单数据
        OrderEntity order = orderList.get(position);
        //数据绑定
        //订单状态
        int orderStatus = order.getStatus();
        switch (orderStatus) {
            case 0: //待付款
                holder.orderStatussTV.setText("待付款");
                holder.finishIV.setVisibility(View.GONE);
                break;
            case 1: //待发货
                holder.orderStatussTV.setText("待发货");
                holder.finishIV.setVisibility(View.GONE);
                break;
            case 2: //待收货
                holder.orderStatussTV.setText("待收货");
                holder.finishIV.setVisibility(View.GONE);
                break;
            case 3://完成
                holder.orderStatussTV.setText("已完成");
                holder.finishIV.setVisibility(View.VISIBLE);
                break;
            case 4://取消
                holder.orderStatussTV.setText("已取消");
                holder.finishIV.setVisibility(View.GONE);
                break;
        }
        //订单编号
        holder.snTV.setText(order.getSn());
        //付款金额
        holder.paymentTV.setText("实付款:￥" + String.format("%.2f", order.getTotal_amount()));
        //订单所包含的上商品
        holder.goodsContainerLayout.removeAllViewsInLayout();
        List<OrderEntity.OrderItem> orderItems = order.getOrderItems();
        for (OrderEntity.OrderItem orderItem : orderItems) {
            RelativeLayout goodsLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout
                    .item_orderlist_item_goods, null);
            //商品图片
            ImageView imageIV = (ImageView) goodsLayout.findViewById(R.id.product_image);
            ImageLoader.getInstance().displayImage(orderItem.getImage(), imageIV, ImageLoaderManager.product_options);
            //商品名称
            TextView nameTV = (TextView) goodsLayout.findViewById(R.id.product_name);
            nameTV.setText(orderItem.getName());
            //商品数量
            TextView numberTV = (TextView) goodsLayout.findViewById(R.id.product_num);
            numberTV.setText("x" + orderItem.getNum());
            //商品单价
            TextView priceTV = (TextView) goodsLayout.findViewById(R.id.product_price);
            priceTV.setText("￥" + String.format("%.2f", orderItem.getPrice()));

            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            //添加一个商品View
            holder.goodsContainerLayout.addView(goodsLayout, layoutParams);
        }
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView finishIV; //订单完成的标记戳图片
        public TextView orderStatussTV;//订单状态文字信息
        public TextView snTV;//订单编码
        public TextView paymentTV;//订单金额
        public LinearLayout goodsContainerLayout;//订单所包含的商品

        public ViewHolder(View view) {
            super(view);
            finishIV = (ImageView) view.findViewById(R.id.order_item_finish_singet_iv);
            orderStatussTV = (TextView) view.findViewById(R.id.order_item_order_status_tv);
            snTV = (TextView) view.findViewById(R.id.order_item_order_number_tv);
            paymentTV = (TextView) view.findViewById(R.id.order_item_order_payment_tv);
            goodsContainerLayout = (LinearLayout) view.findViewById(R.id.goods_container);
        }
    }
}
