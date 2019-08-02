package com.huatec.edu.mobileshop.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.Toast;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.adapter.OrderListAdapter;
import com.huatec.edu.mobileshop.entity.OrderEntity;
import com.huatec.edu.mobileshop.http.presenter.OrderPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class MyOrderActivity extends BaseActivity {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.listview_myorder)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager linearLayoutManager;
    OrderListAdapter adapter;
    private List<OrderEntity> orderList = new ArrayList<>();
    //当前页码
    private int currentPage = 1;
    private int memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        initViews();
        initData();
    }

    private void initViews() {
        memberId = getSharedPreferences("user", 0).getInt("member_id", 0);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new OrderListAdapter(this, orderList);
        mRecyclerView.setAdapter(adapter);
        //下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData(memberId, currentPage++); //加载下一页
            }
        });
    }

    //初始化数据
    private void initData() {
        currentPage = 1;
        orderList.clear();
        //默认第一次进入的时候自动下拉刷新加载数据
        swipeRefreshLayout.setRefreshing(true);
        requestData(memberId, currentPage);
    }

    //联网请求数据
    private void requestData(int memberId, int page) {
        if (memberId == 0) {
            Toast.makeText(this, "登录已过期，请重新登录！", Toast.LENGTH_SHORT).show();
            return;
        }
        OrderPresenter.orderList(new Subscriber<List<OrderEntity>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MyOrderActivity.this, "获取订单数据失败！", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onNext(List<OrderEntity> orderEntities) {
                orderList.addAll(orderEntities);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false); //获取数据完毕，取消下拉刷新
            }
        }, memberId, page);
    }

    @OnClick(R.id.title_back)
    public void onClick() {
        finish();
    }
}
