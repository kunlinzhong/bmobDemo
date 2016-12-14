package com.zhong.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by zhong on 2016/12/14.
 */

public class getLocation {
    private Context con;
    private String deviceId;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    public getLocation(Context context,String deviceId){
        this.con = context;
        this.deviceId = deviceId;
    }
    public void getLocation(){
        //初始化定位
        mLocationClient = new AMapLocationClient(con);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(2000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否强制刷新WIFI，默认为true，强制刷新。
//        mLocationOption.setWifiActiveScan(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @SuppressLint("LongLogTag")
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {

            Latlng latlng = new Latlng();
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
//                    latlng.setDeviceId(deviceId);
                    latlng.setLatitude(String.valueOf(aMapLocation.getLatitude()));
                    latlng.setLongitude(String.valueOf(aMapLocation.getLongitude()));
                    latlng.setAddress(aMapLocation.getAddress());
                    latlng.setCity(aMapLocation.getCity());
                    latlng.setLocationStatus("success");
//                    saveLatlngData(String.valueOf(aMapLocation.getLatitude()),String.valueOf(aMapLocation.getLongitude()),aMapLocation.getAddress());
                    Log.e("getErrorCode", "ErrorCode is 0");
                    Log.e("getLatitude  getLongitude",aMapLocation.getLatitude()+"---"+aMapLocation.getLongitude());
                    Log.e(" getAddress", aMapLocation.getAddress());
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                    latlng.setLatitude("latitude is null "+aMapLocation.getErrorCode());
                    latlng.setLongitude("longitude is null "+aMapLocation.getErrorCode());
                    latlng.setAddress("address is null "+aMapLocation.getErrorCode());
                    latlng.setCity("city is null "+aMapLocation.getErrorCode());
                    latlng.setLocationStatus("location is null "+aMapLocation.getErrorCode());
//                    latlng.setDeviceId(deviceId);
//                    latlng.setLatitude("ErrCode:"+ aMapLocation.getErrorCode());
//                    latlng.setLongitude("ErrCode:"+ aMapLocation.getErrorCode());
//                    latlng.setAddress("errInfo:"+ aMapLocation.getErrorInfo());
//                    latlng.setCity("city");
                    latlng.setLocationStatus("fail");
//                    saveLatlngData("latitude","longitude","address");
                }
            }else{
//                saveLatlngData("latitude is null","longitude is null","address is null");
//                latlng.setDeviceId(deviceId);
                latlng.setLatitude("latitude is null");
                latlng.setLongitude("longitude is null");
                latlng.setAddress("address is null");
                latlng.setCity("city is null");
                latlng.setLocationStatus("location is null");
                latlng.setLocationStatus("fail");
//                Log.e("aMapLocation", "aMapLocation is null");
            }
            saveLatlngData(latlng);
        }
    };

    public void saveLatlngData(Latlng mlatlng) {
        Latlng latlng = new Latlng();
        latlng.setLatitude(mlatlng.getLatitude());
        latlng.setLongitude(mlatlng.getLongitude());
        latlng.setCity(mlatlng.getCity());
        latlng.setDeviceId(deviceId);

//        latlng.setAddress(mlatlng.getAddress());
//        latlng.setDeviceId(mlatlng.getDeviceId());
        latlng.setLocationStatus(mlatlng.getLocationStatus());
        latlng.save(new SaveListener<String>() {
            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    Toast.makeText(con,"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(con,"创建数据失败：" + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
