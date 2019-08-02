package com.huatec.edu.mobileshop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.adapter.FavoriteGoodsListAdapter;
import com.huatec.edu.mobileshop.entity.FavoriteGoodsEntity;
import com.huatec.edu.mobileshop.entity.HttpResult;
import com.huatec.edu.mobileshop.http.ProgressDialogSubscriber;
import com.huatec.edu.mobileshop.http.presenter.GoodsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyFavoriteActivity extends BaseActivity {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.product_list)
    RecyclerView productList;
    @BindView(R.id.favorite_index_btn)
    Button favoriteIndexBtn;
    @BindView(R.id.favorite_empty)
    LinearLayout favoriteEmpty;

    private FavoriteGoodsListAdapter adapter;
    private List<FavoriteGoodsEntity> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite);
        ButterKnife.bind(this);


        SharedPreferences sp = getSharedPreferences("user", 0);
        int memberId = sp.getInt("member_id", -1);
        requestList(memberId);


        productList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoriteGoodsListAdapter(this, data);
        productList.setAdapter(adapter);
        adapter.setOnGoodsItemClickListener(new FavoriteGoodsListAdapter.OnGoodsItemClickListener() {
            @Override
            public void onClick(View view, FavoriteGoodsEntity entity) {
                Intent intent = new Intent(MyFavoriteActivity.this, GoodsActivity.class);
                intent.putExtra("goodsId", entity.getGoods_id());
                startActivity(intent);
            }
        });
        productList.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("选择操作");
                menu.add(0, 0, 0, "移除收藏");
            }
        });
    }

    //给菜单项添加事件
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 0:
                remove(menuInfo.position -1 );
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * 取消收藏
     * @param position
     */
    private void remove(int position) {
        int likeId = data.get(position).getLike_id();
        GoodsPresenter.deleteFavoriteGoods(new ProgressDialogSubscriber<HttpResult>(this) {
            @Override
            public void onNext(HttpResult httpResult) {
                Toast.makeText(MyFavoriteActivity.this, httpResult.getMsg(), Toast.LENGTH_SHORT).show();
            }
        },likeId);
    }

    @OnClick({R.id.title_back, R.id.favorite_index_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.favorite_index_btn:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    /**
     * 请求收藏列表
     */
    private void requestList(int memberId) {
        if (memberId != -1) {
            GoodsPresenter.getFavoriteList(new ProgressDialogSubscriber<List<FavoriteGoodsEntity>>(this) {
                @Override
                public void onNext(List<FavoriteGoodsEntity> favoriteGoodsEntities) {
                    if (favoriteGoodsEntities.size() == 0) {
                        productList.setVisibility(View.GONE);
                        favoriteEmpty.setVisibility(View.VISIBLE);
                    } else {
                        favoriteEmpty.setVisibility(View.GONE);
                        productList.setVisibility(View.VISIBLE);
                        //更新数据
                        data.clear();
                        data.addAll(favoriteGoodsEntities);
                        adapter.notifyDataSetChanged();
                    }
                }
            }, memberId);
        }
    }
}
