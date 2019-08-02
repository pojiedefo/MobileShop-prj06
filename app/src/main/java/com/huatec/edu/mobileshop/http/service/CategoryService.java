package com.huatec.edu.mobileshop.http.service;

import com.huatec.edu.mobileshop.entity.CategoryEntity;
import com.huatec.edu.mobileshop.entity.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/11/3.
 */

public interface CategoryService {
    /**
     * {
     * "status": 0,
     * "msg": "加载可显示的顶级分类成功",
     * "data": [
     * {
     * "cat_id": 5,
     * "name": "酒水",
     * "parent_id": 0,
     * "cat_path": "0,2",
     * "goods_count": 0,
     * "sort": 0,
     * "type_id": 5,
     * "list_show": 0,
     * "image": "",
     * "creatime": 1477357645000,
     * "modifytime": 1477357645000,
     * "briefGoodsType": null
     * },
     * {
     * "cat_id": 9,
     * "name": "服装",
     * "parent_id": 0,
     * "cat_path": "0,3",
     * "goods_count": 0,
     * "sort": 0,
     * "type_id": 7,
     * "list_show": 0,
     * "image": "",
     * "creatime": 1477366261000,
     * "modifytime": 1477366261000,
     * "briefGoodsType": null
     * },
     * {
     * "cat_id": 15,
     * "name": "test",
     * "parent_id": 0,
     * "cat_path": "0,4",
     * "goods_count": 2,
     * "sort": 1,
     * "type_id": 4,
     * "list_show": 0,
     * "image": "",
     * "creatime": 1477466568000,
     * "modifytime": 1477466686000,
     * "briefGoodsType": null
     * },
     * {
     * "cat_id": 1,
     * "name": "饮料",
     * "parent_id": 0,
     * "cat_path": "0,1",
     * "goods_count": 22,
     * "sort": 5,
     * "type_id": 4,
     * "list_show": 0,
     * "image": "",
     * "creatime": 1473306495000,
     * "modifytime": 1473306495000,
     * "briefGoodsType": null
     * }
     * ]
     * }
     */

    /**
     * 加载一级分类
     *
     * @return
     */
    @GET("cat/show")
    Observable<HttpResult<List<CategoryEntity>>> getTopList();

    /**
     * 加载二级分类
     *
     * @param parentId
     * @return
     */
    @GET("cat/show/{parentId}")
    Observable<HttpResult<List<CategoryEntity>>> getSecondList(
            @Path("parentId") int parentId
    );
}

