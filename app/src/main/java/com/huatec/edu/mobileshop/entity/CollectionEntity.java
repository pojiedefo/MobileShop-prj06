package com.huatec.edu.mobileshop.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/24.
 */

public class CollectionEntity  implements Serializable {
    private int like_id;//收藏id
    private int member_id;//用户id
    private String createtime;//收藏时间
    private String modifytime;//修改时间

    private int goods_id;//商品编号
    private String name;//名称
    private Double price;//销售价
    private Double mktprice;//市场价
    private String small;//小图

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getLike_id() {
        return like_id;
    }

    public void setLike_id(int like_id) {
        this.like_id = like_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public Double getMktprice() {
        return mktprice;
    }

    public void setMktprice(Double mktprice) {
        this.mktprice = mktprice;
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    @Override
    public String toString() {
        return "CollectionEntity{" +
                "createtime='" + createtime + '\'' +
                ", like_id=" + like_id +
                ", member_id=" + member_id +
                ", modifytime='" + modifytime + '\'' +
                ", goods_id=" + goods_id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", mktprice=" + mktprice +
                ", small='" + small + '\'' +
                '}';
    }
}
