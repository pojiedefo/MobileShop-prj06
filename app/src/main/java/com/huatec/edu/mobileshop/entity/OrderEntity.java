package com.huatec.edu.mobileshop.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */

public class OrderEntity implements Serializable {
    private int order_id;//订单id
    private String sn;//商品编号
    private int member_id;//用户id
    private int status;//状态
    private int payment_id;//支付id
    private int logi_id;//日志id
    private Double total_amount;//总金额
    private int address_id;//地址id
    private String createtime;//创建时间
    private String modifytime;//修改时间

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    private List<OrderItem> orderItems;//订单包含的商品


    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getLogi_id() {
        return logi_id;
    }

    public void setLogi_id(int logi_id) {
        this.logi_id = logi_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    //订单包含的商品信息
    public class OrderItem {
        private String small; //图片
        private String name;//名称
        private int goods_num;//数量
        private String price; //单价

        public String getImage() {
            return small;
        }

        public void setImage(String small) {
            this.small = small;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return goods_num;
        }

        public void setNum(int goods_num) {
            this.goods_num = goods_num;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "address_id=" + address_id +
                ", order_id=" + order_id +
                ", sn='" + sn + '\'' +
                ", member_id=" + member_id +
                ", status=" + status +
                ", payment_id=" + payment_id +
                ", logi_id=" + logi_id +
                ", total_amount=" + total_amount +
                ", createtime='" + createtime + '\'' +
                ", modifytime='" + modifytime + '\'' +
                '}';
    }
}
