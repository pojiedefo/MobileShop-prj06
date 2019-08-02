package com.huatec.edu.mobileshop.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.entity.AddressEntity;
import com.huatec.edu.mobileshop.entity.CategoryEntity;
import com.huatec.edu.mobileshop.entity.HttpResult;
import com.huatec.edu.mobileshop.entity.MemberEntity;
import com.huatec.edu.mobileshop.http.ProgressDialogSubscriber;
import com.huatec.edu.mobileshop.http.presenter.AddressPresenter;
import com.huatec.edu.mobileshop.http.presenter.CategoryPresenter;
import com.huatec.edu.mobileshop.http.presenter.MemberPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class SampleActivity extends AppCompatActivity {


    @BindView(R.id.left_drawer)
    ListView leftDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample);
        ButterKnife.bind(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getData());
        leftDrawer.setAdapter(adapter);
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        data.add("item 01");
        data.add("item 02");
        data.add("item 03");
        data.add("item 04");
        data.add("item 05");
        data.add("item 06");
        data.add("item 07");
        data.add("item 08");

        return data;
    }

    /**
     * 新增收货地址
     */
    private void addAddress() {
        Map<String, Object> address = new HashMap<>();
        // //memberId、provice、city、region、addr、mobile、receiver
        address.put("memberId", 16);
        address.put("provice", "北京市");
        address.put("city", "北京市");
        address.put("region", "海淀区");
        address.put("addr", "颐和园路5号");
        address.put("mobile", "18575593069");
        address.put("receiver", "朱胜");
        AddressPresenter.add(new ProgressDialogSubscriber<AddressEntity>(this) {
            @Override
            public void onNext(AddressEntity addressEntity) {
            }
        }, address);
    }

    /**
     * 查询收货地址
     */
    private void loadAddress() {
        AddressPresenter.load(new ProgressDialogSubscriber<List<AddressEntity>>(this) {
            @Override
            public void onNext(List<AddressEntity> addressEntities) {
                StringBuffer sb = new StringBuffer();
                for (AddressEntity entity : addressEntities) {
                    sb.append(entity.toString() + "\r\n");
                }
            }
        }, "16");
    }

    /**
     * 删除收获地址
     */
    private void deleteAddress() {
        AddressPresenter.delete(new ProgressDialogSubscriber<HttpResult>(this) {
            @Override
            public void onNext(HttpResult httpResult) {
            }
        }, "2");
    }

    /**
     * 用户注册
     */
    private void userRegister() {
        MemberPresenter.register(new ProgressDialogSubscriber<MemberEntity>(this) {
            @Override
            public void onNext(MemberEntity memberEntity) {
            }
        }, "朱胜12134", "zhusheng@12134.com", "123456");
    }

    /**
     * 商品列表
     */
    private void getList() {
        CategoryPresenter.getTopList(new Subscriber<List<CategoryEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<CategoryEntity> categoryEntities) {

            }
        });
    }
}
