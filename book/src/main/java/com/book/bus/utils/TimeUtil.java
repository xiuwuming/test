package com.book.bus.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author 林钊全
 * @date 2019/6/3 11:18
 */
public class TimeUtil {

    /**
     * 获取mysql时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static Timestamp getTimestamp() {
        java.util.Date date = new java.util.Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        //2017-05-06 15:54:21.0
        Timestamp ts = Timestamp.valueOf(dateStr);
        return ts;
    }
    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(new Date());
        return dateString;
    }
    /**
     * 返回某个日期前几天的日期
     * @param date
     * @param i
     * @return
     */
    public static Date getFrontDay(Date date, int i) {
        Calendar cal=new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE)-i);
        return cal.getTime();
    }

    public static void main(String[] args) {
        System.out.println(getFrontDay(new Date(),3));
    }
}
