package com.zhong.tool;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/11/22.
 */

public class Latlng extends BmobObject {
    private String latitude;
    private String longitude;
    private String address;

    public Latlng() {
        this.setTableName("t_latlng");//设置数据库表名
    }


    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress(){return  address;}
    public void setAddress(String address){this.address = address;}
}