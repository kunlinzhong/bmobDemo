package com.zhong.bmobdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.zhong.tool.Latlng;
import com.zhong.tool.LogCatUtil;
import com.zhong.tool.Person;
import com.zhong.tool.getLocation;

import org.json.JSONArray;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Context con;
    private Button save_btn,query_btn,bmobquery_btn,update_btn,delete_btn;
    private int number = 0;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private String deviceId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, getResources().getString(R.string.bmob_application_id));
        setContentView(R.layout.activity_main);
        con = MainActivity.this;
        deviceId =((TelephonyManager) con.getSystemService(TELEPHONY_SERVICE)).getDeviceId();
//        Log.e("deviceId--->",deviceId);
        SharedPreferences sp = this.getSharedPreferences("config",0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("deviceId", deviceId);
        editor.commit();
        Intent serviceIntent = new Intent(con, MyService.class);
        startService(serviceIntent);
       Log.e(" sHA1(con)", sHA1(con));

//        new getLocation(con).getLocation();
//        initView();
//        getData();
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initView() {
        save_btn = (Button)findViewById(R.id.save_btn);
        query_btn = (Button)findViewById(R.id.query_btn);
        bmobquery_btn = (Button)findViewById(R.id.bmobquery_btn);
        update_btn = (Button)findViewById(R.id.update_btn);
        delete_btn = (Button)findViewById(R.id.delete_btn);
        save_btn.setOnClickListener(this);
        query_btn.setOnClickListener(this);
        bmobquery_btn.setOnClickListener(this);
        update_btn.setOnClickListener(this);
        delete_btn.setOnClickListener(this);
//        setUp();
        new Thread(new ThreadShow()).start();
    }

    private void setUp() {
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
                    latlng.setDeviceId(deviceId);
                    latlng.setLatitude(String.valueOf(aMapLocation.getLatitude()));
                    latlng.setLongitude(String.valueOf(aMapLocation.getLongitude()));
                    latlng.setAddress(aMapLocation.getAddress());
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
                    latlng.setDeviceId(deviceId);
                    latlng.setLatitude("ErrCode:"+ aMapLocation.getErrorCode());
                    latlng.setLongitude("ErrCode:"+ aMapLocation.getErrorCode());
                    latlng.setAddress("errInfo:"+ aMapLocation.getErrorInfo());
                    latlng.setLocationStatus("fail");
//                    saveLatlngData("latitude","longitude","address");
                }
            }else{
//                saveLatlngData("latitude is null","longitude is null","address is null");
                latlng.setDeviceId(deviceId);
                latlng.setLatitude("latitude is null");
                latlng.setLongitude("longitude is null");
                latlng.setAddress("address is null");
                latlng.setLocationStatus("location is null");
                Log.e("aMapLocation", "aMapLocation is null");
            }
            saveLatlngData(latlng);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_btn:{
                saveData();
                break;
            }
            case R.id.query_btn:{
                queryData();
                break;
            }
            case R.id.bmobquery_btn:{
              bmobQuery();
                break;
            }
            case R.id.update_btn:{
                updateData();
                break;
            }
            case R.id.delete_btn:{
                deleteData();
                break;
            }
            default:
                break;
        }
    }
    public void saveLatlngData(Latlng mlatlng) {
        Latlng latlng = new Latlng();
        latlng.setLatitude(mlatlng.getLatitude());
        latlng.setLongitude(mlatlng.getLongitude());
        latlng.setAddress(mlatlng.getAddress());
        latlng.setDeviceId(mlatlng.getDeviceId());
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

    public void saveData() {
        number++;
        Person p2 = new Person();
        p2.setId(number);
        p2.increment("id");
        p2.setName(String.valueOf(number));
        p2.setAddress("广州 同和");
        p2.setAppllication(getResources().getString(R.string.bmob_application_id));
        p2.save(new SaveListener<String>() {
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

    public void queryData() {
        BmobQuery query =new BmobQuery("t_user");
//        query.addWhereEqualTo("age", 25);
//        query.setLimit(2);
        query.order("createdAt");
        //v3.5.0版本提供`findObjectsByTable`方法查询自定义表名的数据
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray ary, BmobException e) {
                if(e==null){
                    Log.e("bmob","查询成功："+ary.toString());
                }else{
                    Log.e("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });


//        //查找Person表里面id为17b8c87241的数据
//        BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
//        bmobQuery.getObject("17b8c87241", new QueryListener<Person>() {
//            @Override
//            public void done(Person object,BmobException e) {
//                if(e==null){
//                    Log.e("query--->","query name:"+object.getName());
////                    System.out.print("query name:"+object.getName());
//                    Toast.makeText(con,"查询成功",Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(con,"查询失败",Toast.LENGTH_LONG).show();
//                }
//            }
//        });
    }


    private void bmobQuery(){
        //只返回Person表的objectId这列的值
        BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
        bmobQuery.addQueryKeys("objectId");
        bmobQuery.findObjects(new FindListener<Person>() {
            @Override
            public void done(List<Person> object, BmobException e) {
                if(e==null){
//                    toast("查询成功：共" + object.size() + "条数据。");
                    for (int i = 0;i<object.size();i++)
                        Log.e("objectId--->"+i,object.toString().trim());
                    //注意：这里的Person对象中只有指定列的数据。
                }else{
                    Log.e("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    public void updateData() {
        number++;
        //更新Person表里面id为17b8c87241的数据，address内容更新为“北京朝阳”
        Person p2 = new Person();
        p2.setName(String.valueOf(number));
        p2.update("17b8c87241", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(con,"更新成功:",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(con,"更新失败：" + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    public void deleteData() {
        Person p2 = new Person();
        p2.setObjectId("17b8c87241");
        p2.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(con,"删除成功:",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(con,"删除失败：" + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    // handler类接收数据
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                setUp();
                System.out.println("getLocation");
            }
        };
    };

    // 线程类
    class ThreadShow implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (true) {
                try {
                    Thread.sleep(1000*10);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                    System.out.println("send...");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("thread error...");
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new LogCatUtil().writerLog("Activity onDestroy ！");
    }

}
