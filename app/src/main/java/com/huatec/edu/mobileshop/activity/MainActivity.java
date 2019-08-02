package com.huatec.edu.mobileshop.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.fragment.NavigationFragment;


/**
 * 设置当前Activity的启动模式为SingleTask.
 * 当通过Intent来启动的时候，不会再调用onCreate,替代的是onNewIntent
 */
public class MainActivity extends BaseActivity {
    private NavigationFragment navigationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //测试API
        //startActivity(new Intent(this, APITestActivity.class));

        setContentView(R.layout.activity_main);

        //加载NavigationFragment
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        navigationFragment = new NavigationFragment();
        fragmentTransaction.add(R.id.main_frame, navigationFragment).commit();
    }


    /**
     * 切换下方的tab标签
     *
     * @param index
     */
    public void changeTab(int index) {
        switch (index) {
            case 1:
                this.navigationFragment.setTabSelection(R.id.tab_item_home);
                break;
            case 2:
                this.navigationFragment.setTabSelection(R.id.tab_item_category);
                break;
            case 3:
                this.navigationFragment.setTabSelection(R.id.tab_item_cart);
                break;
            case 4:
                this.navigationFragment.setTabSelection(R.id.tab_item_personal);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String action = intent.getStringExtra("action");
        if ("toIndex".equals(action)) {
            changeTab(1);
        }
    }

    /**
     * 点击返回按键监听
     */
    @Override
    public void onBackPressed() {
        if (navigationFragment.currentId != R.id.tab_item_home) {
            changeTab(1);
            return;
        }
        super.onBackPressed();
    }

}
