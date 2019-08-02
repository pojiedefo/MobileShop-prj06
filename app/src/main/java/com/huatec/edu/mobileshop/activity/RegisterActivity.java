package com.huatec.edu.mobileshop.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.entity.MemberEntity;
import com.huatec.edu.mobileshop.http.ProgressDialogSubscriber;
import com.huatec.edu.mobileshop.http.presenter.MemberPresenter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.login_input_name)
    EditText loginInputName;
    @BindView(R.id.login_input_email)
    EditText loginInputEmail;
    @BindView(R.id.login_input_password)
    EditText loginInputPassword;
    @BindView(R.id.login_input_repassword)
    EditText loginInputRepassword;
    @BindView(R.id.register_button)
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        Intent intent = new Intent("android.intent.action.MY_BROADCAST");
        intent.putExtra("msg", "hello receiver.");
        sendOrderedBroadcast(intent, null);

    }

    @OnClick({R.id.title_back, R.id.register_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.register_button:
                register();
                break;
        }
    }

    private void register() {
        String username = loginInputName.getText().toString().trim();
        String email = loginInputEmail.getText().toString().trim();
        String password = loginInputPassword.getText().toString().trim();
        String rePassword = loginInputRepassword.getText().toString().trim();

        checkUsername(username);
        checkEmail(email);
        checkPWD(password, rePassword);

        MemberPresenter.register(new ProgressDialogSubscriber<MemberEntity>(this) {
            @Override
            public void onNext(MemberEntity memberEntity) {
                Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor localEditor = getSharedPreferences("user", 0).edit();
                localEditor.putInt("member_id", memberEntity.getMember_id()); //用户id
                localEditor.putString("uname", memberEntity.getUname());//用户名
                localEditor.putString("email", memberEntity.getEmail());//用户邮箱
                localEditor.putString("image", memberEntity.getImage());//用户头像
                localEditor.commit();

                //返回到之前的页面并回传数据
                Intent returnIntent = new Intent();
                returnIntent.putExtra("logined", true);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        }, username, email, password);
    }

    //验证用户名
    private void checkUsername(String username) {
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(RegisterActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (username.length() < 4 || username.length() > 20) {
            Toast.makeText(RegisterActivity.this, "用户名的长度为4-20个字符！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (username.contains("@")) {
            Toast.makeText(RegisterActivity.this, "用户名中不能包含@等特殊字符！", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    //验证邮箱
    private void checkEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(RegisterActivity.this, "邮箱不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(email);
        if (!m.matches()) {
            Toast.makeText(RegisterActivity.this, "邮箱格式不正确！", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    //验证密码
    private void checkPWD(String password, String rePassword) {
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(rePassword)) {
            Toast.makeText(RegisterActivity.this, "两次输入密码不一致！", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    public class BatteryChangedReceiver extends BroadcastReceiver {

        private static final String TAG = "BatteryChangedReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);  //当前电量
            int totalLevel = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 1);      //总电量
            int percent = currentLevel * 100 / totalLevel;
            Log.i(TAG, "battery: " + percent + "%");
        }

    }
}
