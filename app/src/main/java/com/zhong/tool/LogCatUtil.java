package com.zhong.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

/**
 * Created by lenovo on 2016/10/21.
 */
public class LogCatUtil {
    private String PATH = "/storage/emulated/0/comodin/image/camera/catalina.";
    public LogCatUtil(){}
    public void writerLog(String str){
        try {
            if(new StringUtils().checkStringIsNull(str)){//打印空行
                str = "\r\n";
            }else{
                str = "\r\n"+str+ "   "+ new MyTime().getNowTime_ddHHmmss();
            }
            PATH = PATH + new MyTime().getNowDate()+".txt";
            FileWriter fw = new FileWriter(PATH,true);
            fw.flush();
            fw.write(str);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void saveFile(String str) {
        String filePath = PATH + new MyTime().getNowDate()+".txt";
        str = str+ "  "+ new MyTime().getNowTime();
//        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
//        if (hasSDCard) { // SD卡根目录的hello.text
//            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + "hello.txt";
//        } else  // 系统下载缓存根目录的hello.text
//            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator
// + "hello.txt";

        try {
//            File file = new File(filePath, String.valueOf(true));
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(str.getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
