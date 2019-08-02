package com.huatec.edu.mobileshop.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.activity.GoodsActivity;
import com.huatec.edu.mobileshop.activity.GoodsListActivity;
import com.huatec.edu.mobileshop.activity.LoginActivity;
import com.huatec.edu.mobileshop.activity.MainActivity;
import com.huatec.edu.mobileshop.activity.MyOrderActivity;
import com.huatec.edu.mobileshop.utils.NetworkUtils;
import com.huatec.edu.mobileshop.view.MyWebView;

public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";
    private MainActivity mainActivity;
    private final int SEARCH_ACTIVITY = 1;
    private MyWebView mWebView;
    private SwipeRefreshLayout mSwipeRefreshLayout;//下拉刷新布局
    private Button testBtn;
    private TextView searchTV;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mainActivity = (MainActivity) getActivity();
        //搜索
        searchTV = (TextView) view.findViewById(R.id.home_search);
        searchTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(mainActivity, SearchActivity.class);
                startActivityForResult(intent, SEARCH_ACTIVITY);*/
                Toast.makeText(mainActivity, "等待开发...", Toast.LENGTH_SHORT).show();
            }
        });
        mWebView = (MyWebView) view.findViewById(R.id.webView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        initMyWebView(view);
        initSwipeRefreshLayout();
        return view;
    }


    //设置webview
    @SuppressLint("JavascriptInterface")
    private void initMyWebView(View view) {
        mWebView.setWebViewClient(new JWebViewClient());
        mWebView.addJavascriptInterface(this, "app");
        mWebView.setVerticalScrollBarEnabled(false);//设置无垂直方向的scrollbar
        mWebView.setHorizontalScrollBarEnabled(false);//设置无水平方向的scrollbar

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true); // 启用JS脚本
        settings.setSupportZoom(false); // 支持缩放
        settings.setBuiltInZoomControls(false); // 启用内置缩放装置

        //对webview是否处于顶部进行监听，解决webview往下拉后无法往上拉的冲突（和SwipeRefreshLayout冲突）
        mWebView.setOnCustomScrollChanged(new MyWebView.IWebViewScroll() {
            @Override
            public void onTop() {
                mSwipeRefreshLayout.setEnabled(true);
            }

            @Override
            public void notOnTop() {
                mSwipeRefreshLayout.setEnabled(false);
            }
        });
        // 点击后退按钮,让WebView后退
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            // 当点击链接时,希望覆盖而不是打开浏览器窗口
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.e(TAG, "onReceivedError");
                //用javascript隐藏系统定义的404页面信息
                mWebView.loadUrl("file:///android_asset/error.html");
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mSwipeRefreshLayout.setRefreshing(true);
                Log.e(TAG, "onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mSwipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, "onPageFinished");
            }
        });
        //加载Url
        mWebView.loadUrl("http://www.apple.com/cn-k12/shop");
    }

    /**
     * 设置下拉刷新样式及监听
     */
    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkUtils.isNetworkAvailable(mainActivity)) {//有网络才允许重新刷新
                    mWebView.reload();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);//无网络
                    Toast.makeText(mainActivity, "网络不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SEARCH_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                String keyword = data.getStringExtra("keyword");
                Intent intent = new Intent(this.getActivity(), GoodsListActivity.class);
                intent.putExtra("keyword", keyword);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        }
    }

    private class JWebViewClient extends WebViewClient {

        public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString) {
            paramWebView.loadUrl(paramString);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            view.getSettings().setDefaultTextEncodingName("UTF-8");
            super.onReceivedError(view, request, error);
            view.loadDataWithBaseURL("",
                    "<div style='padding-top:200px;text-align:center;color:#666;" +
                            "'>未打开无线网络</div>",
                    "text/html",
                    "UTF-8", "");
        }
    }
    //*******************定义的用于在JavaScript中调用的方法**********************//

    /**
     * 切换最下方的tab
     *
     * @param index
     */
    @JavascriptInterface
    public void changeTab(final int index) {
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                mainActivity.changeTab(index);
            }
        });
    }

    @JavascriptInterface
    public void showGoods(final int goodsId) {
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                mainActivity.showGoods(goodsId);
            }
        });
    }

    /**
     * 显示团购商品详情
     *
     * @param goodsId
     * @param groupbuy_id 团购活动id
     */
    @JavascriptInterface
    public void showGroupbuy(final int goodsId, final int groupbuy_id) {
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                Intent intent = new Intent(mainActivity, GoodsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("goods_id", goodsId);
                intent.putExtra("groupbuy_id", groupbuy_id);
                startActivity(intent);
            }
        });
    }

    /**
     * 显示秒杀商品详情
     *
     * @param goodsId
     * @param actId   秒杀活动id
     */
    @JavascriptInterface
    public void showSeckill(final int goodsId, final int actId) {
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                Intent intent = new Intent(mainActivity, GoodsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("goods_id", goodsId);
                intent.putExtra("act_id", actId);
                startActivity(intent);
            }
        });
    }

    /**
     * 显示分类下的商品列表
     *
     * @param cid
     */
    @JavascriptInterface
    public void showList(final int cid) {
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                mainActivity.showGoodsList(cid, 0);
            }
        });
    }

    /**
     * 显示秒杀商品列表
     */
    @JavascriptInterface
    public void showSeckillList() {
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                mainActivity.showSeckillList();
            }
        });
    }

    /**
     * 显示品牌下的商品列表
     *
     * @param brand
     */
    @JavascriptInterface
    public void showBrand(final int brand) {
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                mainActivity.showGoodsList(0, brand);
            }
        });
    }

    @JavascriptInterface
    public void myorder() {
        if (mainActivity.isLogin()) {
            startActivity(new Intent(mainActivity, MyOrderActivity.class));
            return;
        }
        startActivity(new Intent(mainActivity, LoginActivity.class));
        Toast.makeText(mainActivity, "请先登录再进行此项操作！", Toast.LENGTH_SHORT).show();
    }
}
