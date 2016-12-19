package com.zhong.tool;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.zhong.bmobdemo.R;


/**
 * Created by lenovo on 2016/8/11.
 */
public class StringUtils {
    public StringUtils(){}
    public Boolean checkStringIsNull(String str){
        if(str==null||str.isEmpty()||str.equalsIgnoreCase("")||str.equalsIgnoreCase("null"))
            return true;
        return false;
    }

    /**
     * 将每三个数字加上逗号处理（通常使用金额方面的编辑）
     * @param str 无逗号的数字
     * <a href="http://home.51cto.com/index.php?s=/space/34010" target="_blank">@return</a> 加上逗号的数字
     */
    public String addComma(String str) {
        String reverseStr = new StringBuilder(str).reverse().toString();
        String strTemp = "";
        for (int i=0; i<reverseStr.length(); i++) {
            if (i*3+3 > reverseStr.length()){
                strTemp += reverseStr.substring(i*3,reverseStr.length());
                break;
            }
            strTemp += reverseStr.substring(i*3, i*3+3)+",";
        }
        // 将[789,456,] 中最后一个[,]去除
        if (strTemp.endsWith(",")) {
            strTemp = strTemp.substring(0, strTemp.length()-1);
        }
        String resultStr = new StringBuilder(strTemp).reverse().toString();
        return resultStr;
    }
    public String replaceEmpty(String str){
        String result = "";
//        result = str.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
        result = str.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
        result.replaceAll("\\D+","").replaceAll("\r", "").replaceAll("\n", "").trim();
        return result;
    }

    /**
     * 科学计数法转String
     * @param d
     * @return
     */
    public String getNumberFormat(Double d){
        java.text.NumberFormat numberFormat = java.text.NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        return  numberFormat.format(d);
    }

    /**
     * 设置textView 的字体颜色
     * @param context
     * @param start
     * @param end
     * @param startColor
     * @param endColor
     * @param textView
     */
    public void setTextViewColor(Context context,int start,int end,int startColor,int endColor,TextView textView){
        SpannableStringBuilder builder = new SpannableStringBuilder(textView.getText().toString());
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan colorCSpan = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorC));
        ForegroundColorSpan colorBlackSpan = new ForegroundColorSpan(ContextCompat.getColor(context,R.color.colorBlack));
        builder.setSpan(colorCSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(colorBlackSpan, end, textView.getText().toString().trim().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(builder);
    }

    public int getFontSize(int screenWidth, int screenHeight,int size) {
        screenWidth=screenWidth>screenHeight?screenWidth:screenHeight;
        return  (int)(size*(float) screenWidth/320);
//        int rate = (int)(size*(float) screenWidth/320);
//        return rate<14?14:rate;
    }

}

