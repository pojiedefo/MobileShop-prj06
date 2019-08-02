package com.huatec.edu.mobileshop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.adapter.GoodsListAdapter;
import com.huatec.edu.mobileshop.entity.GoodsEntity;
import com.huatec.edu.mobileshop.http.presenter.GoodsPresenter;
import com.huatec.edu.mobileshop.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class GoodsListActivity extends BaseActivity {
    private static final String TAG ="GoodsListActivity" ;
    //返回按钮
    @BindView(R.id.title_back)
    ImageView titleBack;
    //搜索关键词
    @BindView(R.id.search_keyword)
    TextView searchKeyword;
    //按销量排序
    @BindView(R.id.goodslist_orderby_sales_text)
    TextView goodslistOrderbySalesText;
    @BindView(R.id.goodslist_orderby_sales)
    RelativeLayout goodslistOrderbySales;
    //按品牌排序
    @BindView(R.id.goodslist_orderby_grade_text)
    TextView goodslistOrderbyGradeText;
    @BindView(R.id.goodslist_orderby_grade)
    RelativeLayout goodslistOrderbyGrade;
    //按价格排序
    @BindView(R.id.goodslist_orderby_price_text)
    TextView goodslistOrderbyPriceText;
    @BindView(R.id.goodslist_orderby_price)
    RelativeLayout goodslistOrderbyPrice;
    //按新品排序
    @BindView(R.id.goodslist_orderby_newgoods_text)
    TextView goodslistOrderbyNewgoodsText;
    @BindView(R.id.goodslist_orderby_newgoods)
    RelativeLayout goodslistOrderbyNewgoods;
    //商品列表
    @BindView(R.id.goodslist_recyclerview)
    RecyclerView goodslistRecyclerview;
    //下拉刷新
    @BindView(R.id.goodslist_swipe_refresh)
    SwipeRefreshLayout goodslistSwipeRefresh;
    //商品列表为空时显示的界面
    @BindView(R.id.goodslist_nodata)
    TextView goodslistNodata;

    private static int REQUEST_CODE_GOODS_SEARCH = 1;
    private int catId;
    private String keyword;
    private GoodsListAdapter adapter;
    private List<GoodsEntity> listData = new ArrayList<>();
    private int lastVisibleItem;
    private LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        this.catId = intent.getIntExtra("cat_id", 0); //从商品分类界面传递过来的的参数
        Log.e(TAG,"catId:"+this.catId);
        this.keyword = intent.getStringExtra("keyword");//从商品搜索界面传递过来的参数


        initViews();
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        //设置列表样式
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        goodslistRecyclerview.setLayoutManager(mLayoutManager);

        //设置刷新样式
        goodslistSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color
                        .holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        goodslistSwipeRefresh.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        //下拉刷新监听器
        goodslistSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                if (NetworkUtils.isNetworkAvailable(GoodsListActivity.this)) {//有网络才允许重新刷新
                    loadData();
                } else {
                    goodslistSwipeRefresh.setRefreshing(false);//无网络
                    Toast.makeText(GoodsListActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //初始化数据
        loadData();
        //适配数据
        adapter = new GoodsListAdapter(this, listData);
        goodslistRecyclerview.setAdapter(adapter);
        //监听滑动的状态变化
        goodslistRecyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    loadMoreData();
                }
            }
        });
        adapter.setOnGoodsItemClickListener(new GoodsListAdapter.OnGoodsItemClickListener() {
            @Override
            public void onClick(View view, GoodsEntity entity) {
                Intent intent = new Intent(GoodsListActivity.this, GoodsActivity.class);
                intent.putExtra("goodsId", entity.getGoods_id());
                startActivity(intent);
            }
        });
    }

    /**
     * 上拉加载更多数据，分页
     */
    private void loadMoreData() {
    }
    /**
     * 加载数据
     */
    private void loadData() {
        if(catId ==0){//从二级分类页面过来
            Toast.makeText(GoodsListActivity.this, "没有该列表的商品数据！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (catId != 0) {   //从二级分类页面过来
            loadGoodsListByCatId(catId);
            return;
        }
        if (!TextUtils.isEmpty(keyword)) { //从搜索页面过来
            loadGoodsListByKeywords(keyword);
            return;
        }
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.title_back, R.id.search_keyword, R.id.goodslist_orderby_sales, R.id.goodslist_orderby_grade, R.id
            .goodslist_orderby_price, R.id.goodslist_orderby_newgoods})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.search_keyword:
                Intent intent = new Intent(GoodsListActivity.this, SearchActivity.class);
                startActivityForResult(intent, REQUEST_CODE_GOODS_SEARCH);
                break;
            case R.id.goodslist_orderby_sales:
                break;
            case R.id.goodslist_orderby_grade:
                break;
            case R.id.goodslist_orderby_price:
                break;
            case R.id.goodslist_orderby_newgoods:
                break;
        }
    }

    /**
     * 加载二级分类id查询商品列表
     *
     * @param catId
     */
    private void loadGoodsListByCatId(int catId) {
        GoodsPresenter.list(new Subscriber<List<GoodsEntity>>() {
            @Override
            public void onCompleted() {
                goodslistSwipeRefresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                goodslistSwipeRefresh.setRefreshing(false);
            }

            @Override
            public void onNext(List<GoodsEntity> goodsEntities) {
                if(goodsEntities.size() == 0){
                    Toast.makeText(GoodsListActivity.this, "没有该列表的商品数据！", Toast.LENGTH_SHORT).show();
                    goodslistSwipeRefresh.setRefreshing(false);
                    return;
                }
                listData.clear();
                listData.addAll(goodsEntities);
                adapter.notifyDataSetChanged();
            }
        }, catId);
    }

    /**
     * 根据搜索关键词来搜索商品列表
     *
     * @param keyword
     */
    private void loadGoodsListByKeywords(String keyword) {
        GoodsPresenter.listByKeywords(new Subscriber<List<GoodsEntity>>() {
            @Override
            public void onCompleted() {
                goodslistSwipeRefresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                goodslistSwipeRefresh.setRefreshing(false);
            }

            @Override
            public void onNext(List<GoodsEntity> goodsEntities) {
                if(goodsEntities.size() ==0){
                    Toast.makeText(GoodsListActivity.this, "没有该列表的商品数据！", Toast.LENGTH_SHORT).show();
                    return;
                }
                listData.clear();
                listData.addAll(goodsEntities);
                adapter.notifyDataSetChanged();
            }
        }, keyword);
    }

    /**
     * 接收从搜索界面回传的搜索关键词
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_GOODS_SEARCH) {
            if (resultCode == Activity.RESULT_OK) {
                String keyword = data.getStringExtra("keyword");

                Intent intent = new Intent(this, GoodsListActivity.class);
                intent.putExtra("keyword", keyword);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        }
    }
}
