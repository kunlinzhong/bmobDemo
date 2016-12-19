package com.zhong.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MyTime {

	public MyTime() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 时间戳输入例如（1402733340）输出（"2014年06月14日16时09分"）
	 * @param time
	 * @return
	 */
	 public String timet(String time) {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日",Locale.getDefault());
			String mytime = sdf.format(time);
         return mytime;
 }
	 
	 /**
	  * 获取系统的当前时间
	  * @return
	  */
	public String getNowTime() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sDateFormat.format(new Date());
		return date;
	}

	/**
	 * 获取系统的当前时间
	 * @return
	 */
	public String getNowTime_ddHHmmss() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("dd HH:mm:ss");
		String date = sDateFormat.format(new Date());
		return date;
	}
	public String getNowDate() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = sDateFormat.format(new Date());
		return date;
	}

	public String getNowDate_DDMMYY() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String date = sDateFormat.format(new Date());
		return date;
	}

	/**
	 * 获取系统的当前时间
	 * @return
	 */
	public String getNowTime_ddmmyyyy() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String date = sDateFormat.format(new Date());
		return date;
	}

	/**
	 * 获取系统的当前时间
	 * @return
	 */
	public String getNowTime_yyyyMMddHHmmss() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = sDateFormat.format(new Date());
		return date;
	}


//	/**
//	 * 获取系统的当前时间
//	 * @return
//	 */
//	public String getNowTimeStamp() {
//		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		String date = sDateFormat.format(new Date());
//		return date2TimeStamp(date);
//	}


	public static String date2TimeStamp(String format){
//		Log.e("date2TimeStamp format", format);
		try {
			TimeZone.setDefault(TimeZone.getTimeZone("GMT-6:00"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Log.e("date2TimeStamp  sdf.parse(format).getTime()/1000",""+sdf.parse(format).getTime());
			return String.valueOf(sdf.parse(format).getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String StampTest(){
		try {
			TimeZone.setDefault(TimeZone.getTimeZone("GMT-6:00"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Log.e("date2TimeStamp  sdf.parse(StampTest).getTime()/1000",""+sdf.parse("1466186548475").getTime());
			return String.valueOf(sdf.parse("1466186548475").getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}


	public String comparingTimeRD(String time1,String time2) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = df.parse(time1);
		Date date= df.parse(time2);
		long l=now.getTime()-date.getTime();
		long day=l/(24*60*60*1000);
		long hour=(l/(60*60*1000)-day*24);
		long min=((l/(60*1000))-day*24*60-hour*60);
		long s=(l/1000-day*24*60*60-hour*60*60-min*60);
		System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
		return String.valueOf(day);
	}

	/**
	 * 根据时间戳获取日期
	 * @param dataFormat
	 * @param timeStamp
     * @return
     */
	public String formatData(String dataFormat, long timeStamp) {
		if (timeStamp == 0) {
			return "";
		}
		String result = "";
		SimpleDateFormat format = new SimpleDateFormat(dataFormat);
		result = format.format(new Date(timeStamp));
		return result;
	}

	/**
	 * 获取当前时间的时间戳
 	 * @return
     */
	public Long getNowTimeStamp(){
		return new Date().getTime();
	}

	/**
	 * @param endTime  newtime
	 * @param startTime  oldtime
	 * @param mintime
	 * @return
	 * @throws ParseException
	 */
	public Boolean comparingTimeByTime(String endTime,String startTime,String mintime) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = df.parse(endTime.substring(0,10));
		Date date= df.parse(startTime.substring(0,10));
		long l=now.getTime()-date.getTime();
		long day=l/(24*60*60*1000);
		long hour=(l/(60*60*1000)-day*24);
		long min=((l/(60*1000))-day*24*60-hour*60);
		long s=(l/1000-day*24*60*60-hour*60*60-min*60);
		System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
		if(day>0){
			return true;
		}else if(hour>0){
			return true;
		}else if(min>Long.parseLong(mintime)){
			return true;
		}
		return false;
	}

	public Boolean comparingTimeByTime2(String endTime,String startTime,String mintime) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date now = df.parse(endTime.substring(0,10));
//		Date date= df.parse(startTime.substring(0,10));
//		long l=now.getTime()-date.getTime();
		long l=Long.parseLong(endTime)-Long.parseLong(startTime);
		long day=l/(24*60*60*1000);
		long hour=(l/(60*60*1000)-day*24);
		long min=((l/(60*1000))-day*24*60-hour*60);
		long s=(l/1000-day*24*60*60-hour*60*60-min*60);
		System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
		if(day>0){
			return true;
		}else if(hour>0){
			return true;
		}else if(min>Long.parseLong(mintime)){
			return true;
		}
		return false;
	}

	public String UTCToMexicoTime(String time){
		String result = null;
		int hour = Integer.parseInt(time.split(":")[0]);
		if(hour-5>0){
			result = hour-5+":"+time.split(":")[1];
		}else if(hour-5 ==0){
			result = "00:"+time.split(":")[1];
		}else{
			result = 24+hour+":"+time.split(":")[1];
		}
		return result;
	}


}
