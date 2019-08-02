package com.huatec.edu.mobileshop.http.presenter;

import com.huatec.edu.mobileshop.entity.AddressEntity;
import com.huatec.edu.mobileshop.entity.HttpResult;
import com.huatec.edu.mobileshop.http.HttpMethods;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/10/27.
 */

public class AddressPresenter extends HttpMethods {
    //新增收货地址
    //memberId,province,city,region,addr,mobile,receiver
    public static void add(Subscriber<AddressEntity> subscriber, Map<String, Object> address) {
        Observable observable = addressService.add(address)
                .map(new HttpResultFunc<AddressEntity>());
        toSubscribe(observable, subscriber);
    }

    //查询收货地址
    public static void load(Subscriber<List<AddressEntity>> subscriber, String memberId) {
        Observable observable = addressService.load(memberId)
                .map(new HttpResultFunc<List<AddressEntity>>());
        toSubscribe(observable, subscriber);
    }

    //删除收货地址
    public static void delete(Subscriber<HttpResult> subscriber, String addressId) {
        Observable observable = addressService.delete(addressId);
        toSubscribe(observable, subscriber);
    }
}
