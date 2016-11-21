package com.zhong.tool;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/11/18.
 */

public class Person extends BmobObject {
    private Integer id;
    private String name;
    private String address;
    private String appllication;

    public Person() {
        this.setTableName("t_user");//设置数据库表名
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getApplication() {
        return appllication;
    }
    public void setAppllication(String appllication) {
        this.appllication = appllication;
    }

}