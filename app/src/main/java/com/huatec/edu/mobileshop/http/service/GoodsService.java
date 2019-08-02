package com.huatec.edu.mobileshop.http.service;

import com.huatec.edu.mobileshop.entity.CartGoodsEntity;
import com.huatec.edu.mobileshop.entity.FavoriteGoodsEntity;
import com.huatec.edu.mobileshop.entity.GoodsDetailEntity;
import com.huatec.edu.mobileshop.entity.GoodsEntity;
import com.huatec.edu.mobileshop.entity.HttpResult;

import java.util.List;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/11/9.
 */

public interface GoodsService {

    /**
     * 根据二级分类的id,获取商品列表
     *
     * @param catId
     * @return
     */
    @GET("goods/cat/{catId}")
    Observable<HttpResult<List<GoodsEntity>>> list(
            @Path("catId") int catId
    );

    /**
     * 根据搜索关键词来获取搜索的结果集
     *
     * @param keywords
     * @return
     */
    @FormUrlEncoded
    @POST("goods/find")
    Observable<HttpResult<List<GoodsEntity>>> listByKeywords(
            @Field("input") String keywords
    );

    /**
     * 获取商品详情
     *
     * @param goodsId
     * @return
     */
    @GET("goods/union/{goodsId}")
    Observable<HttpResult<GoodsDetailEntity>> goodsDetail(
            @Path("goodsId") int goodsId
    );

    /**
     * 添加到购物车
     *
     * @param memberId
     * @param goodsId
     * @param goodsNum
     * @return
     */
    @FormUrlEncoded
    @POST("cart")
    Observable<HttpResult> addToCart(
            @Field("memberId") int memberId,
            @Field("goodsId") int goodsId,
            @Field("goodsNum") int goodsNum
    );

    /**
     * 获取购物车商品列表
     *
     * @param memberId
     * @return
     */
    @GET("cart/member/{memberId}")
    Observable<HttpResult<List<CartGoodsEntity>>> cartList(
            @Path("memberId") int memberId
    );

    /**
     * 删除购物车商品
     *
     * @param cartId
     * @return
     */
    @DELETE("cart/{cartId}")
    Observable<HttpResult> deleteCartGoods(
            @Path("cartId") int cartId
    );

    /**
     * 修改购物车商品数量
     *
     * @param cartId
     * @param goodsNum
     * @return
     */
    @PUT("cart/num/{cartId}")
    Observable<HttpResult> updateCartNum(
            @Path("cartId") int cartId,
            @Query("goodsNum") int goodsNum
    );

    /**
     * 添加到收藏列表
     *
     * @param memberId 会员id
     * @param goodsId  商品id
     * @return
     */
    @FormUrlEncoded
    @POST("like")
    Observable<HttpResult> addToFavorite(
            @Field("memberId") int memberId,
            @Field("goodsId") int goodsId
    );

    /**
     * 获取收藏列表
     *
     * @param memberId
     * @return
     */
    @GET("like/member/{memberId}")
    Observable<HttpResult<List<FavoriteGoodsEntity>>> favoriteList(
            @Path("memberId") int memberId
    );

    /**
     * 取消收藏
     *
     * @param likeId 收藏的商品id
     * @return
     */
    @DELETE("like/{likeId}")
    Observable<HttpResult> deleteFavoriteGoods(
            @Path("likeId") int likeId
    );

}
