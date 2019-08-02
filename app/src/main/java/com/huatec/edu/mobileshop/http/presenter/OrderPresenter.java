package com.huatec.edu.mobileshop.http.presenter;

import com.huatec.edu.mobileshop.entity.OrderEntity;
import com.huatec.edu.mobileshop.http.HttpMethods;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/10/24.
 */

public class OrderPresenter extends HttpMethods {

    //获取订单列表
    public static void orderList(Subscriber<List<OrderEntity>> subscriber, int memberId, int page) {
        Observable observable = orderService.orderList(memberId, page)
                .map(new HttpResultFunc<List<OrderEntity>>());
        toSubscribe(observable, subscriber);
    }
}
