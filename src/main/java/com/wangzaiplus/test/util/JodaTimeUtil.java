package com.wangzaiplus.test.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

@Slf4j
public class JodaTimeUtil {

    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * date类型 -> string类型
     *
     * @param date
     * @return
     */
    public static String dateToStr(Date date) {
        return dateToStr(date, STANDARD_FORMAT);
    }

    /**
     * date类型 -> string类型
     *
     * @param date
     * @param format 自定义日期格式
     * @return
     */
    public static String dateToStr(Date date, String format) {
        if (date == null) {
            return null;
        }

        format = StringUtils.isBlank(format) ? STANDARD_FORMAT : format;
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(format);
    }

    /**
     * string类型 -> date类型
     *
     * @param timeStr
     * @return
     */
    public static Date strToDate(String timeStr) {
        return strToDate(timeStr, STANDARD_FORMAT);
    }

    /**
     * string类型 -> date类型
     *
     * @param timeStr
     * @param format  自定义日期格式
     * @return
     */
    public static Date strToDate(String timeStr, String format) {
        if (StringUtils.isBlank(timeStr)) {
            return null;
        }

        format = StringUtils.isBlank(format) ? STANDARD_FORMAT : format;

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(format);
        DateTime dateTime;
        try {
            dateTime = dateTimeFormatter.parseDateTime(timeStr);
        } catch (Exception e) {
            log.error("strToDate error: timeStr: {}", timeStr, e);
            return null;
        }

        return dateTime.toDate();
    }

    /**
     * 判断date日期是否过期(与当前时刻比较)
     *
     * @param date
     * @return
     */
    public static Boolean isTimeExpired(Date date) {
        String timeStr = dateToStr(date);
        return isBeforeNow(timeStr);
    }

    /**
     * 判断date日期是否过期(与当前时刻比较)
     *
     * @param timeStr
     * @return
     */
    public static Boolean isTimeExpired(String timeStr) {
        if (StringUtils.isBlank(timeStr)) {
            return true;
        }

        return isBeforeNow(timeStr);
    }

    /**
     * 判断timeStr是否在当前时刻之前
     *
     * @param timeStr
     * @return
     */
    private static Boolean isBeforeNow(String timeStr) {
        DateTimeFormatter format = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime;
        try {
            dateTime = DateTime.parse(timeStr, format);
        } catch (Exception e) {
            log.error("isBeforeNow error: timeStr: {}", timeStr, e);
            return null;
        }
        return dateTime.isBeforeNow();
    }

    /**
     * 日期加天数
     *
     * @param date
     * @param days
     * @return
     */
    public static Date plusDays(Date date, int days) {
        return plusOrMinusDays(date, days, 0);
    }

    /**
     * 日期减天数
     *
     * @param date
     * @param days
     * @return
     */
    public static Date minusDays(Date date, int days) {
        return plusOrMinusDays(date, days, 1);
    }

    /**
     * 加减天数
     *
     * @param date
     * @param days
     * @param type 0:加天数 1:减天数
     * @return
     */
    private static Date plusOrMinusDays(Date date, int days, Integer type) {
        if (null == date) {
            return null;
        }

        DateTime dateTime = new DateTime(date);
        if (type == 0) {
            dateTime = dateTime.plusDays(days);
        } else {
            dateTime = dateTime.minusDays(days);
        }

        return dateTime.toDate();
    }

    /**
     * 日期加分钟
     *
     * @param date
     * @param minutes
     * @return
     */
    public static Date plusMinutes(Date date, int minutes) {
        return plusOrMinusMinutes(date, minutes, 0);
    }

    /**
     * 日期减分钟
     *
     * @param date
     * @param minutes
     * @return
     */
    public static Date minusMinutes(Date date, int minutes) {
        return plusOrMinusMinutes(date, minutes, 1);
    }

    /**
     * 加减分钟
     *
     * @param date
     * @param minutes
     * @param type    0:加分钟 1:减分钟
     * @return
     */
    private static Date plusOrMinusMinutes(Date date, int minutes, Integer type) {
        if (null == date) {
            return null;
        }

        DateTime dateTime = new DateTime(date);
        if (type == 0) {
            dateTime = dateTime.plusMinutes(minutes);
        } else {
            dateTime = dateTime.minusMinutes(minutes);
        }

        return dateTime.toDate();
    }

    /**
     * 日期加月份
     *
     * @param date
     * @param months
     * @return
     */
    public static Date plusMonths(Date date, int months) {
        return plusOrMinusMonths(date, months, 0);
    }

    /**
     * 日期减月份
     *
     * @param date
     * @param months
     * @return
     */
    public static Date minusMonths(Date date, int months) {
        return plusOrMinusMonths(date, months, 1);
    }

    /**
     * 加减月份
     *
     * @param date
     * @param months
     * @param type   0:加月份 1:减月份
     * @return
     */
    private static Date plusOrMinusMonths(Date date, int months, Integer type) {
        if (null == date) {
            return null;
        }

        DateTime dateTime = new DateTime(date);
        if (type == 0) {
            dateTime = dateTime.plusMonths(months);
        } else {
            dateTime = dateTime.minusMonths(months);
        }

        return dateTime.toDate();
    }

    /**
     * 判断target是否在开始和结束时间之间
     *
     * @param target
     * @param startTime
     * @param endTime
     * @return
     */
    public static Boolean isBetweenStartAndEndTime(Date target, Date startTime, Date endTime) {
        if (null == target || null == startTime || null == endTime) {
            return false;
        }

        DateTime dateTime = new DateTime(target);
        return dateTime.isAfter(startTime.getTime()) && dateTime.isBefore(endTime.getTime());
    }

}
