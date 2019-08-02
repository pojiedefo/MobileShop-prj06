package com.huatec.edu.mobileshop.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/12.
 */

public class MemberEntity implements Serializable{
    /**
     * member_id : 21
     * uname : zhang
     * password : xMpCOKC5I4INzFCab3WEmw==
     * email : 1
     * sex : 0
     * mobile :
     * regtime : null
     * lastlogin : null
     * image :
     * memberAddresses : null
     */
    private int member_id;
    private String uname;
    private String password;
    private String email;
    private String sex;
    private String mobile;
    private Object regtime;
    private Object lastlogin;
    private String image;
    private Object memberAddresses;

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Object getRegtime() {
        return regtime;
    }

    public void setRegtime(Object regtime) {
        this.regtime = regtime;
    }

    public Object getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Object lastlogin) {
        this.lastlogin = lastlogin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getMemberAddresses() {
        return memberAddresses;
    }

    public void setMemberAddresses(Object memberAddresses) {
        this.memberAddresses = memberAddresses;
    }

    @Override
    public String toString() {
        return "MemberEntity{" +
                "email='" + email + '\'' +
                ", member_id=" + member_id +
                ", uname='" + uname + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", mobile='" + mobile + '\'' +
                ", regtime=" + regtime +
                ", lastlogin=" + lastlogin +
                ", image='" + image + '\'' +
                ", memberAddresses=" + memberAddresses +
                '}';
    }
}
