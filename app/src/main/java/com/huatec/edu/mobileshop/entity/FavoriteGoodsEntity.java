package com.huatec.edu.mobileshop.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/25.
 */

public class FavoriteGoodsEntity implements Serializable {

    /**
     * like_id : 4
     * goods_id : 21
     * member_id : 16
     * creatime : 1478488484000
     * modifytime : 1478488484000
     * briefGoods : {"goods_id":21,"name":"测试茶饮料007","price":19.9,"mktprice":22,
     * "small":"http://localhost:8080/MobileShop/upload/MBJZgst2D/QcfuwhquZPHg==_small.jpg","mkt_enable":"",
     * "briefGoodsCat":{"cat_id":1,"name":""},"briefBrand":{"brand_id":1,"name":"","logo":""}}
     */
    private int like_id;
    private int goods_id;
    private int member_id;
    private long creatime;
    private long modifytime;
    private BriefGoodsBean briefGoods;

    public int getLike_id() {
        return like_id;
    }

    public void setLike_id(int like_id) {
        this.like_id = like_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
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
         * goods_id : 21
         * name : 测试茶饮料007
         * price : 19.9
         * mktprice : 22
         * small : http://localhost:8080/MobileShop/upload/MBJZgst2D/QcfuwhquZPHg==_small.jpg
         * mkt_enable :
         * briefGoodsCat : {"cat_id":1,"name":""}
         * briefBrand : {"brand_id":1,"name":"","logo":""}
         */

        private int goods_id;
        private String name;
        private double price;
        private int mktprice;
        private String small;
        private String mkt_enable;
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

        public String getMkt_enable() {
            return mkt_enable;
        }

        public void setMkt_enable(String mkt_enable) {
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
