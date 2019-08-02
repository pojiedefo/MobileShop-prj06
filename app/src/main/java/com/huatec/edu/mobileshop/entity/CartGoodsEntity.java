package com.huatec.edu.mobileshop.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/1.
 */

public class CartGoodsEntity implements Serializable {

    /**
     * cart_id : 10
     * member_id : 16
     * goods_id : 11
     * goods_num : 5
     * choose : 1
     * amount : 49.5
     * creatime : 1479968843000
     * modifytime : 1479970646000
     * briefGoods : {"goods_id":11,"name":"测试茶饮料002","price":9.9,"mktprice":12,"small":"http://192.168.8
     * .2:8080/MobileShop/upload/C9dC9XckO1M7td5FQK1cKw==_small.jpg","mkt_enable":0,"briefGoodsCat":{"cat_id":1,
     * "name":""},"briefBrand":{"brand_id":1,"name":"","logo":""}}
     */

    private int cart_id;
    private int member_id;
    private int goods_id;
    private int goods_num;
    private int choose;
    private double amount;
    private long creatime;
    private long modifytime;
    private BriefGoodsBean briefGoods;

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public int getChoose() {
        return choose;
    }

    public void setChoose(int choose) {
        this.choose = choose;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getCreatime() {
        return creatime;
    }

    public void setCreatime(long creatime) {
        this.creatime = creatime;
    }

    public long getModifytime() {
        return modifytime;
    }

    public void setModifytime(long modifytime) {
        this.modifytime = modifytime;
    }

    public BriefGoodsBean getBriefGoods() {
        return briefGoods;
    }

    public void setBriefGoods(BriefGoodsBean briefGoods) {
        this.briefGoods = briefGoods;
    }

    public static class BriefGoodsBean {
        /**
         * goods_id : 11
         * name : 测试茶饮料002
         * price : 9.9
         * mktprice : 12
         * small : http://192.168.8.2:8080/MobileShop/upload/C9dC9XckO1M7td5FQK1cKw==_small.jpg
         * mkt_enable : 0
         * briefGoodsCat : {"cat_id":1,"name":""}
         * briefBrand : {"brand_id":1,"name":"","logo":""}
         */

        private int goods_id;
        private String name;
        private double price;
        private int mktprice;
        private String small;
        private int mkt_enable;
        private BriefGoodsCatBean briefGoodsCat;
        private BriefBrandBean briefBrand;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getMktprice() {
            return mktprice;
        }

        public void setMktprice(int mktprice) {
            this.mktprice = mktprice;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public int getMkt_enable() {
            return mkt_enable;
        }

        public void setMkt_enable(int mkt_enable) {
            this.mkt_enable = mkt_enable;
        }

        public BriefGoodsCatBean getBriefGoodsCat() {
            return briefGoodsCat;
        }

        public void setBriefGoodsCat(BriefGoodsCatBean briefGoodsCat) {
            this.briefGoodsCat = briefGoodsCat;
        }

        public BriefBrandBean getBriefBrand() {
            return briefBrand;
        }

        public void setBriefBrand(BriefBrandBean briefBrand) {
            this.briefBrand = briefBrand;
        }

        public static class BriefGoodsCatBean {
            /**
             * cat_id : 1
             * name :
             */

            private int cat_id;
            private String name;

            public int getCat_id() {
                return cat_id;
            }

            public void setCat_id(int cat_id) {
                this.cat_id = cat_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class BriefBrandBean {
            /**
             * brand_id : 1
             * name :
             * logo :
             */

            private int brand_id;
            private String name;
            private String logo;

            public int getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(int brand_id) {
                this.brand_id = brand_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }
        }
    }

}
