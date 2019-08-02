package com.huatec.edu.mobileshop.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.adapter.GoodsViewPagerAdapter;
import com.huatec.edu.mobileshop.entity.GoodsDetailEntity;
import com.huatec.edu.mobileshop.entity.HttpResult;
import com.huatec.edu.mobileshop.fragment.GoodsSpecFragment;
import com.huatec.edu.mobileshop.http.ProgressDialogSubscriber;
import com.huatec.edu.mobileshop.http.presenter.GoodsPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class GoodsActivity extends BaseActivity {

    private static final String TAG = "GoodsActivity";
    //返回图标
    @BindView(R.id.title_back)
    ImageView titleBack;
    //喜欢
    @BindView(R.id.favorite_layout)
    LinearLayout favoriteLayout;
    //购物车
    @BindView(R.id.cat_layout)
    LinearLayout catLayout;
    //添加购物车
    @BindView(R.id.add_to_cart)
    TextView addToCart;
    //viewpager
    @BindView(R.id.image_pager)
    ViewPager imagePager;
    //图片索引
    @BindView(R.id.image_pager_index)
    TextView imagePagerIndex;
    //商品名称
    @BindView(R.id.goods_name)
    TextView goodsName;
    //商品价格
    @BindView(R.id.goods_price)
    TextView goodsPrice;
    //商品品牌
    @BindView(R.id.goods_brand)
    TextView goodsBrand;
    //商品库存
    @BindView(R.id.goods_store)
    TextView goodsStore;
    //商品重量
    @BindView(R.id.goods_view_count)
    TextView goodsViewCount;
    @BindView(R.id.goods_drawerlayout)
    DrawerLayout goodsDrawerlayout;
    @BindView(R.id.choose_count)
    ImageView chooseCount;

    private int goodsId;
    List<String> images = new ArrayList<>();
    GoodsViewPagerAdapter adapter;
    private FragmentManager fragmentManager;
    private GoodsSpecFragment goodsSpecFragment;
    private GoodsDetailEntity entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        goodsId = intent.getIntExtra("goodsId", 0);

        fragmentManager = getFragmentManager();
        loadGoodsDetail(goodsId);

    }

    private void loadGoodsDetail(int goodsId) {
        GoodsPresenter.goodsDetail(new Subscriber<GoodsDetailEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final GoodsDetailEntity entity) {
                Log.e(TAG, "result:" + entity.toString());
                Log.e(TAG, "fitst url：" + entity.getGis().get(0).getBig());

                goodsName.setText(entity.getName());
                goodsPrice.setText("￥" + entity.getPrice());
                goodsBrand.setText(entity.getBriefBrand().getName());

                goodsStore.setText("" + entity.getGoodStore().getStore());
                goodsViewCount.setText("" + entity.getView_count());
                showImages(entity);

                initDrawer(entity);

                GoodsActivity.this.entity = entity;
            }
        }, goodsId);
    }

    /**
     * 初始化抽屉
     */
    private void initDrawer(GoodsDetailEntity entity) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (goodsSpecFragment == null) {
            goodsSpecFragment = new GoodsSpecFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("entity",entity);
            goodsSpecFragment.setArguments(bundle);
        }
        transaction.replace(R.id.right_frame, goodsSpecFragment);
        transaction.commit();
    }

    /**
     * 显示图片
     *
     * @param entity
     */
    private void showImages(GoodsDetailEntity entity) {
        List<GoodsDetailEntity.GisBean> gises = entity.getGis();
        Log.e(TAG,"entity:"+entity);
        final int size = gises.size();
        imagePagerIndex.setText("1/" + size);
        images.clear();
        String url;
        for (int i = 0; i < gises.size(); i++) {
            url = gises.get(i).getBig();
            images.add(url);
            Log.e(TAG, "url ：" + i + "-->"+url);
        }
        adapter = new GoodsViewPagerAdapter(this, images);
        imagePager.setAdapter(adapter);
        imagePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                imagePagerIndex.setText((position + 1) + "/" + size);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @OnClick({R.id.title_back, R.id.favorite_layout, R.id.cat_layout, R.id.add_to_cart, R.id.choose_count})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.favorite_layout: //收藏
                addToFavorite(goodsId);
                break;
            case R.id.cat_layout:  //购物车
                Intent intent = new Intent(this,CartActivity.class);
                startActivity(intent);
                break;
            case R.id.add_to_cart: //加入购物车
                addToCart(goodsId);
                break;
            case R.id.choose_count: //选择数量
                goodsDrawerlayout.openDrawer(Gravity.RIGHT);
                break;
        }
    }


    /**
     * 添加到购物车
     *
     * @param goodsId
     */
    private void addToCart(int goodsId) {
        SharedPreferences sp = getSharedPreferences("user", 0);
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
                    //同步到本地购物车列表
                    Intent intent = new Intent();
                    intent.setAction("com.goods.shoopingcart");
                    GoodsActivity.this.sendBroadcast(intent);
                    Toast.makeText(GoodsActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
                }
            }, memberId, goodsId, 1);
        }

    }

    /**
     * 添加到收藏
     *
     * @param goodsId
     */
    private void addToFavorite(int goodsId) {
        SharedPreferences sp = getSharedPreferences("user", 0);
        int memberId = sp.getInt("member_id", -1);
        if (memberId != -1) {
            GoodsPresenter.addToFavorite(new ProgressDialogSubscriber<HttpResult>(this) {
                @Override
                public void onNext(HttpResult httpResult) {
                    //同步到收藏列表
                    Intent intent = new Intent();
                    intent.setAction("com.goods.favoritelist");
                    intent.putExtra("goods_image", entity.getBig()); //商品大图
                    intent.putExtra("goods_count", "" + 1); //商品数量
                    intent.putExtra("goods_price", entity.getPrice()); //商品单价
                    intent.putExtra("goods_totalPrice", entity.getPrice()); //商品总价
                    GoodsActivity.this.sendBroadcast(intent);
                    Toast.makeText(GoodsActivity.this,"添加成功",Toast.LENGTH_LONG).show();
                }
            }, memberId, goodsId);
        }
    }

    /**
     * 将String转换成Map集合
     *
     * @param string
     * @return
     */
    private Map<String, String> stringToMap(String string) {
        //string = string.replaceAll("\\\\", "");
        Map<String, String> params = new HashMap<>();
        JSONObject obj = null;
        try {
            obj = new JSONObject(string);
            Iterator<String> iterator = obj.keys();
            String key, value;
            while (iterator.hasNext()) {
                key = iterator.next();
                value = (String) obj.get(key);
                params.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }

}
