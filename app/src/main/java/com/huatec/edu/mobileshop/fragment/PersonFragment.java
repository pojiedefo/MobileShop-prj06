package com.huatec.edu.mobileshop.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.activity.AddressActivity;
import com.huatec.edu.mobileshop.activity.ChangePWDActivity;
import com.huatec.edu.mobileshop.activity.LoginActivity;
import com.huatec.edu.mobileshop.activity.MainActivity;
import com.huatec.edu.mobileshop.activity.MyFavoriteActivity;
import com.huatec.edu.mobileshop.activity.MyOrderActivity;
import com.huatec.edu.mobileshop.common.ImageLoaderManager;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonFragment extends BaseFragment {
    private final int MY_FAVORITE = 1;
    private final int MY_ORDER = 2;
    private final int MY_ADDRESS = 3;
    private final int MY_ACCOUNT_BEFORE = 4;
    private final int MY_ACCOUNT_AFTER = 5;
    //用户头像
    @BindView(R.id.user_img_view)
    ImageView userImgView;
    //用户名
    @BindView(R.id.user_name)
    TextView userName;
    //用户级别
    @BindView(R.id.user_level)
    TextView userLevel;
    //登陆按钮
    @BindView(R.id.personal_login)
    Button personalLogin;
    //登陆时的布局
    @BindView(R.id.personal_for_login)
    RelativeLayout personalForLogin;
    //未登录时的布局
    @BindView(R.id.personal_for_not_login)
    RelativeLayout personalForNotLogin;
    //我的订单
    @BindView(R.id.person_my_order)
    //收货地址
    RelativeLayout personMyOrder;
    //我的收藏
    @BindView(R.id.my_collect)
    RelativeLayout myCollect;
    @BindView(R.id.my_address)
    RelativeLayout myAddress;
    //我的账户
    @BindView(R.id.my_account)
    RelativeLayout myAccount;
    //退出登陆
    @BindView(R.id.person_logout_layout)
    RelativeLayout personLogoutLayout;

    private MainActivity mainActivity;
    private ProgressDialog progressDialog;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        ButterKnife.bind(this, view);

        mainActivity = (MainActivity) this.getActivity();

        init();
        return view;
    }

    @Override
    public void onResume() {
        init();
        super.onResume();
    }

    @OnClick({R.id.personal_login,
            R.id.person_my_order,
            R.id.my_collect,
            R.id.my_address,
            R.id.my_account,
            R.id.person_logout_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_login: //登录
                startActivity(new Intent(mainActivity, LoginActivity.class));
                break;
            case R.id.person_my_order: //我的订单
                if (mainActivity.isLogin()) {
                    startActivity(new Intent(mainActivity, MyOrderActivity.class));
                    return;
                }
                startActivityForResult(new Intent(mainActivity, LoginActivity.class), MY_ORDER);
                break;
            case R.id.my_collect:  //我的收藏
                if (mainActivity.isLogin()) {
                    startActivity(new Intent(mainActivity, MyFavoriteActivity.class));
                    return;
                }
                startActivityForResult(new Intent(mainActivity, LoginActivity.class), MY_FAVORITE);
                break;
            case R.id.my_address: //收货地址
                if (mainActivity.isLogin()) {
                    Intent intent = new Intent(mainActivity, AddressActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                    return;
                }
                startActivityForResult(new Intent(mainActivity, LoginActivity.class), MY_ADDRESS);
                break;
            case R.id.my_account: //修改密码
                if (mainActivity.isLogin()) {
                    startActivityForResult(new Intent(mainActivity, ChangePWDActivity.class), MY_ACCOUNT_AFTER);
                    return;
                }
                startActivityForResult(new Intent(mainActivity, LoginActivity.class), MY_ACCOUNT_BEFORE);
                break;
            case R.id.person_logout_layout: //退出登陆
                new AlertDialog.Builder(mainActivity)
                        .setTitle("退出登录")
                        .setMessage("您确认要退出登录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MY_ORDER:
                if (resultCode == Activity.RESULT_OK && data.getBooleanExtra("logined", false)) {
                    startActivity(new Intent(mainActivity, MyOrderActivity.class));
                }
                break;
            case MY_FAVORITE:
                if (resultCode == Activity.RESULT_OK && data.getBooleanExtra("logined", false)) {
                    startActivity(new Intent(mainActivity, MyFavoriteActivity.class));
                }
                break;
            case MY_ADDRESS:
                if (resultCode == Activity.RESULT_OK && data.getBooleanExtra("logined", false)) {
                    Intent intent = new Intent(mainActivity, AddressActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                }
                break;
            case MY_ACCOUNT_BEFORE://未登录时修改密码，修改密码后进行登录
                if (resultCode == Activity.RESULT_OK && data.getBooleanExtra("logined", false)) {
                    Intent intent = new Intent(mainActivity, ChangePWDActivity.class);
                    startActivityForResult(intent, MY_ACCOUNT_AFTER);
                }
                break;
            case MY_ACCOUNT_AFTER://登录时修改密码，修改完毕后进行登录
                if (resultCode == Activity.RESULT_OK) {
                    startActivity(new Intent(mainActivity, LoginActivity.class));
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //初始化布局，根据登陆状态显示不同的布局效果
    private void init() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", 0);
        String username = sharedPreferences.getString("uname", "");
        if (TextUtils.isEmpty(username)) {   //未登录
            personalForLogin.setVisibility(View.GONE);
            personalForNotLogin.setVisibility(View.VISIBLE);
            personLogoutLayout.setVisibility(View.GONE);
        } else {   //已登录
            personalForLogin.setVisibility(View.VISIBLE);
            personalForNotLogin.setVisibility(View.GONE);
            personLogoutLayout.setVisibility(View.VISIBLE);
            userName.setText(username);


            String image = sharedPreferences.getString("image", "");
            if (!TextUtils.isEmpty(image)) {
                ImageLoader.getInstance().displayImage(image, userImgView, ImageLoaderManager.user_options);
            }
        }
    }

    //退出登录时，清楚本地用户信息
    private void logout() {
        SharedPreferences.Editor localEditor = mainActivity.getSharedPreferences("user", 0).edit();
        localEditor.remove("member_id");
        localEditor.remove("uname");
        localEditor.remove("email");
        localEditor.remove("image");
        localEditor.commit();
        init();
        Toast.makeText(mainActivity, "退出登录成功！", Toast.LENGTH_SHORT).show();
    }
}
