package com.huatec.edu.mobileshop.http;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.entity.MemberEntity;
import com.huatec.edu.mobileshop.http.presenter.MemberPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class APITestActivity extends AppCompatActivity {


    @BindView(R.id.API_login)
    Button APILogin;
    @BindView(R.id.API_register)
    Button APIRegister;
    @BindView(R.id.API_update)
    Button APIUpdate;
    @BindView(R.id.API_changePassword)
    Button APIChangePassword;
    @BindView(R.id.apitest)
    LinearLayout apitest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apitest);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.API_login, R.id.API_register, R.id.API_update, R.id.API_changePassword})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.API_login:
                login();
                break;
            case R.id.API_register:
                register();
                break;
            case R.id.API_update:
                break;
            case R.id.API_changePassword:
                break;
        }
    }

    private void register() {
        MemberPresenter.register(new ProgressDialogSubscriber<MemberEntity>(this) {
            @Override
            public void onNext(MemberEntity memberEntity) {
                Toast.makeText(APITestActivity.this, "member:" + memberEntity.toString(), Toast.LENGTH_SHORT).show();
            }
        }, "a7", "a7@qq.com", "122222");
        MemberPresenter.register(new Subscriber<MemberEntity>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(APITestActivity.this, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(MemberEntity memberEntity) {
                Toast.makeText(APITestActivity.this, "member:" + memberEntity.toString(), Toast.LENGTH_SHORT).show();
            }
        }, "a7", "a7@qq.com", "122222");
    }

    private void login() {
        MemberPresenter.login(new ProgressDialogSubscriber<MemberEntity>(this) {
            @Override
            public void onNext(MemberEntity memberEntity) {
                Toast.makeText(APITestActivity.this, "member:" + memberEntity.toString(), Toast.LENGTH_SHORT).show();
            }
        }, "a7", "122222");
    }




}
