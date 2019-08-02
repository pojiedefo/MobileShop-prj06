package com.huatec.edu.mobileshop.http.presenter;

import com.huatec.edu.mobileshop.entity.MemberEntity;
import com.huatec.edu.mobileshop.http.HttpMethods;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/10/14.
 */

public class MemberPresenter extends HttpMethods {

    //用户注册
    public static void register(Subscriber<MemberEntity> subscriber, String username, String emial, String password) {
        Observable observable = memberService.register(username, password, emial)
                .map(new HttpResultFunc<MemberEntity>());
        toSubscribe(observable, subscriber);
    }

    //用户登录
    public static void login(Subscriber<MemberEntity> subscriber, String username, String password) {
        Observable observable = memberService.login(username, password)
                .map(new HttpResultFunc<MemberEntity>());
        toSubscribe(observable, subscriber);
    }

    //修改密码
    public static void changePassword(Subscriber subscriber, String memberId, String old_pwd, String
            new_pwd) {
        Observable observable = memberService.changePassword(memberId, old_pwd, new_pwd);
        toSubscribe(observable, subscriber);
    }

    //找回密码,修改成功或失败后的data数据为null
    public static void findPassword(Subscriber subscriber, String email) {
        Observable observable = memberService.findPassword(email);
        toSubscribe(observable, subscriber);
    }


}
