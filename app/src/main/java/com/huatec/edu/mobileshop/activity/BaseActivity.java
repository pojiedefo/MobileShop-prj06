package com.huatec.edu.mobileshop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

/**
 * Created by Dawei on 5/25/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 购物车商品数量
     */
    private static int cartCount = 0;

    /**
     * 是否登录了
     *
     * @return
     */
    public boolean isLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
        return !TextUtils.isEmpty(sharedPreferences.getString("uname", ""));
    }

    /**
     * 显示商品
     * @param goodsid
     */
    public void showGoods(int goodsid){
        Intent intent = new Intent(this, GoodsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("goods_id", goodsid);
        startActivity(intent);
    }

    /**
     * 显示商品列表
     * @param cid
     * @param brand
     */
    public void showGoodsList(int cid, int brand){
        Intent intent = new Intent(this, GoodsListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("cid", cid);
        intent.putExtra("brand", brand);
        startActivity(intent);
    }

    /**
     * 显示秒杀商品列表
     */
    public void showSeckillList(){
        Intent intent = new Intent(this, GoodsListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("seckill", 1);
        startActivity(intent);
    }

    public int getCartCount() {
        return cartCount;
    }

    public void setCartCount(int cartCount) {
        this.cartCount = cartCount;
    }

}
