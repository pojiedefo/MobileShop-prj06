package com.huatec.edu.mobileshop.http.service;

import com.huatec.edu.mobileshop.entity.AddressEntity;
import com.huatec.edu.mobileshop.entity.HttpResult;

import java.util.List;
import java.util.Map;

import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/27.
 */

public interface AddressService {
    /*
    * {"status":0,"msg":"新增会员地址信息成功","data":{"address_id":20,"member_id":16,"provice":
"±±¾©ÊÐ","city":"±±¾©ÊÐ","region":"º£µíÇø","addr":"ÒÃºÍÔ°Â·6ºÅ","mobile":"185755
93069","receiver":"ÖìÊ¤","creatime":null,"modifytime":null,"member":null}}
    * */
    //新增收货地址
    //memberId、provice、city、region、addr、mobile、receiver
    @FormUrlEncoded
    @POST("member_address")
    Observable<HttpResult<AddressEntity>> add(
            @FieldMap Map<String, Object> address
    );

    //查询收货地址
    //http://localhost:8080/MobileShop/member_address/loadByMemberId/{memberId}
    @GET("member_address/member2/{memberId}")
    Observable<HttpResult<List<AddressEntity>>> load(
            @Path("memberId") String memberId
    );

    //删除收货地址
    //http://localhost:8080/MobileShop/member_address/{addressId}
    @DELETE("member_address/{addressId}")
    Observable<HttpResult> delete(
            @Path("addressId") String addressId
    );
}
