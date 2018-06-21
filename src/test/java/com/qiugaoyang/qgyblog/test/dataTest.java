package com.qiugaoyang.qgyblog.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class dataTest {
    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

        String format = simpleDateFormat.format(new Date());
        try {
            Date date=simpleDateFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = simpleDateFormat.getCalendar();

        Date time = calendar.getTime();

        for (int i = 0; i < 7; i++) {
            calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-1);
            Date time1 = calendar.getTime();
            System.out.println(time1.toString());
        }

        System.out.println(format);
    }
}
