package com.huatec.edu.mobileshop.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.adapter.AddressListAdapter;
import com.huatec.edu.mobileshop.entity.AddressEntity;
import com.huatec.edu.mobileshop.entity.HttpResult;
import com.huatec.edu.mobileshop.http.ProgressDialogSubscriber;
import com.huatec.edu.mobileshop.http.presenter.AddressPresenter;
import com.huatec.edu.mobileshop.view.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class AddressActivity extends BaseActivity {
    private final int CREATE_ADDRESS = 1;
    private final int EDIT_ADDRESS = 2;
    private int type;

    @BindView(R.id.title_back)
    ImageView titleBack;
    //没有收货地址时的布局
    @BindView(R.id.layout_address_no_data)
    LinearLayout layoutAddressNoData;
    @BindView(R.id.button_address_new)
    Button buttonAddressNew;
    //新增收货地址
    @BindView(R.id.layout_address_create)
    LinearLayout layoutAddressCreate;
    @BindView(R.id.button_address_create)
    Button buttonAddressCreate;
    //列表
    @BindView(R.id.address_list)
    RecyclerView addressRecyclerView;
    private int memberId;
    private AddressListAdapter adapter;
    private List<AddressEntity> listData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);

        init();
        loadAddress(memberId);


    }

    private void init() {
        type = getIntent().getIntExtra("type", 0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        addressRecyclerView.setLayoutManager(layoutManager);

        SharedPreferences sp = getSharedPreferences("user", 0);
        memberId = sp.getInt("member_id", -1);

        adapter = new AddressListAdapter(this, listData);
        addressRecyclerView.setAdapter(adapter);
        //设置监听器
        addressRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(AddressActivity.this,
                addressRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, int position) {
                new AlertDialog.Builder(AddressActivity.this)
                        .setTitle("删除收货地址")
                        .setMessage("您确认要删除该地址吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = listData.get(which).getAddress_id();
                                deleteAddress(id);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        }));
    }

    /**
     * 删除收货地址
     *
     * @param id
     */
    private void deleteAddress(int id) {
        AddressPresenter.delete(new ProgressDialogSubscriber<HttpResult>(this) {
            @Override
            public void onNext(HttpResult httpResult) {
                Toast.makeText(AddressActivity.this, httpResult.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }, id + "");
    }

    @OnClick({R.id.title_back, R.id.button_address_new, R.id.button_address_create})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.button_address_new: //列表为空，新增收货地址
            case R.id.button_address_create: //列表不为空，新增收货地址
                startActivityForResult(new Intent(AddressActivity.this, AddressModifyActivity.class), CREATE_ADDRESS);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_ADDRESS) {
            if (resultCode == Activity.RESULT_OK) {
                loadAddress(memberId);
            }
            return;
        }
        if (requestCode == EDIT_ADDRESS) {
            if (resultCode == Activity.RESULT_OK) {
                loadAddress(memberId);
            }
            return;
        }
    }

    /**
     * 获取收货地址列表
     *
     * @param id
     */
    private void loadAddress(int id) {
        if (TextUtils.isEmpty(id + ""))
            return;
        AddressPresenter.load(new Subscriber<List<AddressEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<AddressEntity> addressEntities) {
                listData.clear();
                listData.addAll(addressEntities);
                adapter.notifyDataSetChanged();
            }
        }, id + "");
    }
}
