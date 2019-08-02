package com.huatec.edu.mobileshop.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.common.ImageLoaderManager;
import com.huatec.edu.mobileshop.entity.GoodsDetailEntity;
import com.huatec.edu.mobileshop.entity.HttpResult;
import com.huatec.edu.mobileshop.http.presenter.GoodsPresenter;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsSpecFragment extends Fragment {

    //商品图片
    @BindView(R.id.goods_image)
    ImageView goodsImage;
    //商品价格
    @BindView(R.id.goods_price)
    TextView goodsPrice;
    //减少
    @BindView(R.id.btn_release)
    ImageButton btnRelease;
    //数量
    @BindView(R.id.goods_count)
    EditText goodsCount;
    //增加
    @BindView(R.id.btn_add)
    ImageButton btnAdd;
    //添加购物车
    @BindView(R.id.add_to_cart)
    TextView addToCart;
    private GoodsDetailEntity entity;
    private int count;
    private double totalPrice;

    public GoodsSpecFragment(){

    }

    /**
     * 创建Fragment视图
     *
     * @param inflater           布局加载器
     * @param container          布局父容器
     * @param savedInstanceState
     * @return 返回加载的View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_goods_spec, container, false);
        ButterKnife.bind(this, view);

        entity  = (GoodsDetailEntity) getArguments().getSerializable("entity");
        goodsCount.setText("" + 1);
        goodsPrice.setText("" + entity.getPrice());
        ImageLoader.getInstance().displayImage(entity.getBig(), goodsImage,
                ImageLoaderManager.product_options);
        return view;
    }


    @OnClick({R.id.btn_release, R.id.btn_add, R.id.add_to_cart})
    public void onClick(View view) {
        count = Integer.parseInt(goodsCount.getText().toString().trim());
        switch (view.getId()) {
            case R.id.btn_release:
                if (count > 1) {
                    goodsCount.setText("" + (count - 1));
                    totalPrice = entity.getPrice() * (count - 1);
                    //保留两位小数
                    goodsPrice.setText(String.format("%2.f", totalPrice));
                } else {
                    goodsCount.setText("" + 1);
                    goodsPrice.setText("" + entity.getPrice());
                }
                break;
            case R.id.btn_add:
                goodsCount.setText("" + (count + 1));
                totalPrice = entity.getPrice() * (count + 1);
                goodsPrice.setText("" + totalPrice);
                break;
            case R.id.add_to_cart:
                addToCart(entity.getGoods_id(), count);
                break;
        }
    }

    /**
     * 添加到购物车
     *
     * @param goodsId
     * @param goodsNum
     */
    private void addToCart(int goodsId, int goodsNum) {
        SharedPreferences sp = getActivity().getSharedPreferences("user", 0);
        int memberId = sp.getInt("member_id", -1);
        if (memberId != -1) {
            GoodsPresenter.addToCart(new Subscriber<HttpResult>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(HttpResult httpResult) {
                    Toast.makeText(getActivity(), httpResult.getMsg(), Toast.LENGTH_SHORT).show();
                    if(httpResult.getStatus() == 0){
                        //同步到本地购物车列表
                        Intent intent = new Intent();
                        intent.setAction("com.goods.shoopingcart");
                        intent.putExtra("goods_image", entity.getBig()); //商品大图
                        intent.putExtra("goods_count", "" + count); //商品数量
                        intent.putExtra("goods_price", entity.getPrice()); //商品单价
                        intent.putExtra("goods_totalPrice", totalPrice); //商品总价
                        getActivity().sendBroadcast(intent);
                    }

                }
            }, memberId, goodsId, goodsNum);
        }
    }


}
