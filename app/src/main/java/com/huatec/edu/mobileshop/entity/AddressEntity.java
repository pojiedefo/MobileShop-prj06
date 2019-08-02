package com.huatec.edu.mobileshop.entity;


import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/27.
 */

public class AddressEntity implements Serializable {

    /**
     * address_id : 3
     * member_id : 16
     * provice : 广东省
     * city : 深圳市
     * region : 南山区
     * addr : 西丽
     * mobile : 13976543198
     * receiver : 李诗诗
     * creatime : 1473230181000
     * modifytime : 1473230181000
     */

    private int address_id;
    private int member_id;
    private String provice;
    private String city;
    private String region;
    private String addr;
    private String mobile;
    private String receiver;
    private long creatime;
    private long modifytime;

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

    @Override
    public String toString() {
        return "AddressEntity{" +
                "addr='" + addr + '\'' +
                ", address_id=" + address_id +
                ", member_id=" + member_id +
                ", provice='" + provice + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", mobile='" + mobile + '\'' +
                ", receiver='" + receiver + '\'' +
                ", creatime=" + creatime +
                ", modifytime=" + modifytime +
                '}';
    }
}
