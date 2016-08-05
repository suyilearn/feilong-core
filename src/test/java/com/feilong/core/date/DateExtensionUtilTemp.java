/*
 * Copyright (C) 2008 feilong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feilong.core.date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.DatePattern;
import com.feilong.tools.jsonlib.JsonUtil;

import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.date.DateUtil.toCalendar;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME_WITHOUT_SECOND;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME_WITHOUT_YEAR_AND_SECOND;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND;
import static com.feilong.core.DatePattern.COMMON_TIME_WITHOUT_SECOND;

/**
 * The Class DateExtensionUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.0.8
 */
public class DateExtensionUtilTemp extends BaseDateUtilTest{

    /** The Constant LOGGER. */
    private static final Logger   LOGGER                  = LoggerFactory.getLogger(DateExtensionUtilTemp.class);

    /** 星期. */
    private static final String   WEEK                    = "星期";

    /** 昨天. */
    private static final String   YESTERDAY               = "昨天";

    /** 前天. */
    private static final String   THEDAY_BEFORE_YESTERDAY = "前天";

    /** 小时. */
    private static final String   HOUR                    = "小时";

    /** 分钟. */
    private static final String   MINUTE                  = "分钟";

    /** 秒. */
    private static final String   SECOND                  = "秒";

    /**
     * 中文星期.<br>
     * { "日", "一", "二", "三", "四", "五", "六" }
     */
    private static final String[] WEEK_CHINESES           = { "日", "一", "二", "三", "四", "五", "六" };

    /**
     * 英文星期.<br>
     * { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" }
     */
    private static final String[] WEEK_ENGLISHS           = {
                                                              "Sunday",
                                                              "Monday",
                                                              "Tuesday",
                                                              "Wednesday",
                                                              "Thursday",
                                                              "Friday",
                                                              "Saturday" };

    /**
     * 获得英文星期.
     * 
     * @param week
     *            星期 日从0开始 1 2 --6
     * @return 如 Sunday { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" } 中一个
     */
    public static String getEnglishWeek(int week){
        return WEEK_ENGLISHS[week];
    }

    /**
     * 获得中文星期.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * DateExtensionUtil.getChineseWeek(0) = 星期日
     * </pre>
     * 
     * </blockquote>
     * 
     * @param week
     *            星期几,从0开始 ,依次1 2 --6
     * @return 如 星期一
     */
    public static String getChineseWeek(int week){
        return WEEK + WEEK_CHINESES[week];
    }

    /**
     * Test get chinese week.
     */
    @Test
    public void testGetChineseWeek(){
        LOGGER.debug(getChineseWeek(0));
    }

    /**
     * TestDateUtilTest.
     */
    @Test
    public void testDateUtilTest(){
        List<Date> list = new ArrayList<Date>();
        list.add(new Date());
        list.add(new Date());
        list.add(new Date());
        list.add(new Date());
        LOGGER.debug(JsonUtil.format(toStringList(list, COMMON_DATE_AND_TIME_WITH_MILLISECOND)));
    }

    /**
     * Test to humanization date string.
     */
    @Test
    public void testToPrettyDateString(){
        LOGGER.debug(toPrettyDateString(toDate("2012-10-18 13:55:00", COMMON_DATE_AND_TIME)));
        LOGGER.debug(toPrettyDateString(toDate("2012-10-18 14:14:22", COMMON_DATE_AND_TIME)));
        LOGGER.debug(toPrettyDateString(toDate("2012-10-18 14:15:22", COMMON_DATE_AND_TIME)));
        LOGGER.debug(toPrettyDateString(toDate("2012-10-17 14:15:02", COMMON_DATE_AND_TIME)));
        LOGGER.debug(toPrettyDateString(toDate("2012-10-16 14:15:02", COMMON_DATE_AND_TIME)));
        LOGGER.debug(toPrettyDateString(toDate("2012-10-15 14:15:02", COMMON_DATE_AND_TIME)));
        LOGGER.debug(toPrettyDateString(toDate("2012-09-15 14:15:02", COMMON_DATE_AND_TIME)));
        LOGGER.debug(toPrettyDateString(toDate("2015-08-02 14:15:02", COMMON_DATE_AND_TIME)));
        LOGGER.debug(toPrettyDateString(toDate("2015-7-30 13:00:00", COMMON_DATE_AND_TIME)));
    }

    /**
     * TestCalendarUtilTest.
     */
    @Test
    public void testCalendarUtilTest(){
        List<String> weekDateStringList = getWeekDateStringList(Calendar.THURSDAY, COMMON_DATE_AND_TIME_WITH_MILLISECOND);
        LOGGER.debug(JsonUtil.format(weekDateStringList));
    }

    /**
     * 将日期集合转成特定pattern的字符串集合.
     * 
     * @param dateList
     *            日期集合
     * @param datePattern
     *            模式 {@link DatePattern}
     * @return 如果 <code>dateList</code> 是null或者empty,返回 {@link Collections#emptyList()}<br>
     *         否则循环date转成string,返回{@code List<String>}
     */
    public static List<String> toStringList(List<Date> dateList,String datePattern){
        if (isNullOrEmpty(dateList)){
            return Collections.emptyList();
        }

        Validate.notBlank(datePattern, "datePattern can't be blank!");

        List<String> stringList = new ArrayList<String>();
        for (Date date : dateList){
            stringList.add(DateUtil.toString(date, datePattern));
        }
        return stringList;
    }

    /**
     * 获得一年中所有的 指定的 <code>week</code> 周几集合.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <p>
     * 如果当前年是 2016 年,那么
     * </p>
     * 
     * <pre class="code">
     * getWeekDateStringList(Calendar.THURSDAY, COMMON_DATE_AND_TIME_WITH_MILLISECOND)
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * [
     * "2016-01-07 00:00:00.000",
     * "2016-01-14 00:00:00.000",
     * "2016-01-21 00:00:00.000",
     * "2016-01-28 00:00:00.000",
     * "2016-02-04 00:00:00.000",
     * "2016-02-11 00:00:00.000",
     * "2016-02-18 00:00:00.000",
     * "2016-02-25 00:00:00.000",
     * "2016-03-03 00:00:00.000",
     * "2016-03-10 00:00:00.000",
     * "2016-03-17 00:00:00.000",
     * "2016-03-24 00:00:00.000",
     * "2016-03-31 00:00:00.000",
     * "2016-04-07 00:00:00.000",
     * "2016-04-14 00:00:00.000",
     * "2016-04-21 00:00:00.000",
     * "2016-04-28 00:00:00.000",
     * "2016-05-05 00:00:00.000",
     * "2016-05-12 00:00:00.000",
     * "2016-05-19 00:00:00.000",
     * "2016-05-26 00:00:00.000",
     * "2016-06-02 00:00:00.000",
     * "2016-06-09 00:00:00.000",
     * "2016-06-16 00:00:00.000",
     * "2016-06-23 00:00:00.000",
     * "2016-06-30 00:00:00.000",
     * "2016-07-07 00:00:00.000",
     * "2016-07-14 00:00:00.000",
     * "2016-07-21 00:00:00.000",
     * "2016-07-28 00:00:00.000",
     * "2016-08-04 00:00:00.000",
     * "2016-08-11 00:00:00.000",
     * "2016-08-18 00:00:00.000",
     * "2016-08-25 00:00:00.000",
     * "2016-09-01 00:00:00.000",
     * "2016-09-08 00:00:00.000",
     * "2016-09-15 00:00:00.000",
     * "2016-09-22 00:00:00.000",
     * "2016-09-29 00:00:00.000",
     * "2016-10-06 00:00:00.000",
     * "2016-10-13 00:00:00.000",
     * "2016-10-20 00:00:00.000",
     * "2016-10-27 00:00:00.000",
     * "2016-11-03 00:00:00.000",
     * "2016-11-10 00:00:00.000",
     * "2016-11-17 00:00:00.000",
     * "2016-11-24 00:00:00.000",
     * "2016-12-01 00:00:00.000",
     * "2016-12-08 00:00:00.000",
     * "2016-12-15 00:00:00.000",
     * "2016-12-22 00:00:00.000",
     * "2016-12-29 00:00:00.000"
     * ]
     * </pre>
     * 
     * </blockquote>
     * 
     * @param week
     *            周几<br>
     *            星期天开始为1 依次2 3 4 5 6 7,<br>
     *            建议使用 常量 {@link Calendar#SUNDAY}, {@link Calendar#MONDAY}, {@link Calendar#TUESDAY},
     *            {@link Calendar#WEDNESDAY}, {@link Calendar#THURSDAY}, {@link Calendar#FRIDAY}, {@link Calendar#SATURDAY}
     * @param datePattern
     *            获得集合里面时间字符串模式 see {@link DatePattern}
     * @return 获得一年中所有的周几集合<br>
     *         如果 <code>datePattern</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>datePattern</code> 是blank,抛出 {@link IllegalArgumentException}
     * @see org.apache.commons.lang3.time.DateUtils#iterator(Date, int)
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     */
    public static List<String> getWeekDateStringList(int week,String datePattern){
        Validate.notBlank(datePattern, "datePattern can't be blank!");
        Date now = new Date();
        Date firstWeekOfSpecifyDateYear = DateUtil.getFirstWeekOfSpecifyDateYear(now, week);
        //当年最后一天
        Calendar calendarEnd = CalendarUtil.resetYearEnd(toCalendar(now));

        List<String> list = new ArrayList<String>();
        Calendar firstWeekOfSpecifyDateYearCalendar = toCalendar(firstWeekOfSpecifyDateYear);
        for (Calendar calendar = firstWeekOfSpecifyDateYearCalendar; calendar.before(calendarEnd); calendar.add(Calendar.DAY_OF_YEAR, 7)){
            list.add(CalendarUtil.toString(calendar, datePattern));
        }
        return list;
    }

    // [start]转换成特色时间 toPrettyDateString

    /**
     * 人性化显示date时间,依据是和现在的时间间隔.
     * 
     * <p>
     * 转换规则,将传入的inDate和 new Date()当前时间比较;当两者的时间差,(一般inDate小于当前时间 ,暂时不支持大于当前时间)
     * </p>
     * 
     * <ul>
     * <li>如果时间差为0天,<br>
     * 如果小时间隔等于0,如果分钟间隔为0,则显示间隔秒 + "秒钟前"<br>
     * 如果小时间隔等于0,如果分钟间隔不为0,则显示间隔分钟 + "分钟前"<br>
     * </li>
     * 
     * <li>如果时间差为0天,<br>
     * 如果小时间隔不等于0,如果inDate的day 和current的day 相等,则显示space_hour + "小时前"<br>
     * 如果小时间隔不等于0,如果inDate的day 和current的day不相等,则显示"昨天 " + toString(inDate, "HH:mm")<br>
     * </li>
     * 
     * <li>如果时间差为1天,且inDate的day+1和currentDate的day 相等,则显示"昨天 HH:mm"</li>
     * <li>如果时间差为1天,且inDate的day+1和currentDate的day 不相等,则显示"前天 HH:mm"</li>
     * <li>如果时间差为2天,且inDate的day+2和currentDate的day 相等,则显示"前天 HH:mm"</li>
     * <li>如果时间差为2天,且inDate的day+2和currentDate的day 不相等,<br>
     * 1).如果inDate的year和currentDate的year相等,则显示"MM-dd HH:mm"<br>
     * 2).如果inDate的year和currentDate的year不相等,则显示"yyyy-MM-dd HH:mm"
     * </li>
     * 
     * <li>如果时间差大于2天<br>
     * 1).如果inDate的year和currentDate的year相等,则显示"MM-dd HH:mm"<br>
     * 2).如果inDate的year和currentDate的year不相等,则显示"yyyy-MM-dd HH:mm"
     * </li>
     * 
     * </ul>
     * 
     * @param inDate
     *            任意日期<br>
     *            warn:一般inDate{@code <=}当前时间 ,暂时不支持大于当前时间
     * @return 人性化显示date时间<br>
     *         如果 <code>inDate</code> 是null,抛出 {@link NullPointerException}<br>
     * @see DateUtil#toString(Date, String)
     * @see DateUtil#getYear(Date)
     * @see DateUtil#getDayOfMonth(Date)
     * @see DateUtil#getYear(Date)
     * @see DateExtensionUtil#getIntervalTime(Date, Date)
     * @see DateExtensionUtil#getIntervalDay(long)
     * @see DateExtensionUtil#getIntervalHour(long)
     * @see DateExtensionUtil#getIntervalMinute(long)
     * @see DateExtensionUtil#getIntervalSecond(long)
     */
    public static String toPrettyDateString(Date inDate){
        Validate.notNull(inDate, "inDate can't be null!");

        Date nowDate = new Date();

        // 传过来的日期的年份
        int inYear = DateUtil.getYear(inDate);
        //**************************************************************************************/
        int currentYear = DateUtil.getYear(nowDate);// 当前时间的年
        boolean isSameYear = currentYear == inYear;//是否是同一年
        long spaceTime = DateExtensionUtil.getIntervalTime(inDate, nowDate);// 任意日期和现在相差的毫秒数
        int spaceDay = DateExtensionUtil.getIntervalDay(spaceTime);// 相差天数
        //**************************************************************************************/
        switch (spaceDay) {
            case 0: // 间隔0天
                return doWithZeroDayInterval(inDate, nowDate, spaceTime);
            case 1: // 间隔一天
                return doWithOneDayInterval(inDate, nowDate);
            case 2: // 间隔2天
                return doWithTwoDaysInterval(inDate, nowDate, isSameYear);
            default://spaceDay > 2     // 间隔大于2天
                return isSameYear ? DateUtil.toString(inDate, COMMON_DATE_AND_TIME_WITHOUT_YEAR_AND_SECOND)
                                : DateUtil.toString(inDate, COMMON_DATE_AND_TIME_WITHOUT_SECOND);
        }
    }

    // [end]

    //******************************************************************************************

    /**
     * Do with one day interval.
     *
     * @param inDate
     *            the in date
     * @param nowDate
     *            the now date
     * @return the string
     * @since 1.4.0
     */
    private static String doWithOneDayInterval(Date inDate,Date nowDate){
        return DateUtil.isEquals(DateUtil.addDay(inDate, 1), nowDate, COMMON_DATE)
                        ? YESTERDAY + " " + DateUtil.toString(inDate, COMMON_TIME_WITHOUT_SECOND)
                        : THEDAY_BEFORE_YESTERDAY + " " + DateUtil.toString(inDate, COMMON_TIME_WITHOUT_SECOND);
    }

    /**
     * Do with two days interval.
     *
     * @param inDate
     *            the in date
     * @param nowDate
     *            the now date
     * @param isSameYear
     *            the is same year
     * @return the string
     * @since 1.4.0
     */
    private static String doWithTwoDaysInterval(Date inDate,Date nowDate,boolean isSameYear){
        if (DateUtil.isEquals(DateUtil.addDay(inDate, 2), nowDate, COMMON_DATE)){
            return THEDAY_BEFORE_YESTERDAY + " " + DateUtil.toString(inDate, COMMON_TIME_WITHOUT_SECOND);
        }
        return isSameYear ? DateUtil.toString(inDate, COMMON_DATE_AND_TIME_WITHOUT_YEAR_AND_SECOND)
                        : DateUtil.toString(inDate, COMMON_DATE_AND_TIME_WITHOUT_SECOND);
    }

    /**
     * Do with zero day interval.
     *
     * @param inDate
     *            the in date
     * @param nowDate
     *            the now date
     * @param spaceTime
     *            the space time
     * @return the string
     * @since 1.4.0
     */
    private static String doWithZeroDayInterval(Date inDate,Date nowDate,long spaceTime){
        int spaceHour = DateExtensionUtil.getIntervalHour(spaceTime); // 相差小时数
        if (spaceHour == 0){// 小时间隔
            int spaceMinute = DateExtensionUtil.getIntervalMinute(spaceTime);
            return spaceMinute == 0 ? DateExtensionUtil.getIntervalSecond(spaceTime) + SECOND + "前" : spaceMinute + MINUTE + "前";
        }
        // 传过来的日期的日
        int inDay = DateUtil.getDayOfMonth(inDate);
        // 当前时间的日
        int currentDayOfMonth = DateUtil.getDayOfMonth(nowDate);
        return inDay == currentDayOfMonth ? spaceHour + HOUR + "前"
                        : YESTERDAY + " " + DateUtil.toString(inDate, COMMON_TIME_WITHOUT_SECOND);
    }

    /**
     * 获得两个日期时间的日期间隔时间集合(包含最小和最大值),用于统计日报表.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Date fromDate = DateUtil.toDate("2011-03-5 23:31:25.456", DatePattern.COMMON_DATE_AND_TIME);
     * Date toDate = DateUtil.toDate("2011-03-10 01:30:24.895", DatePattern.COMMON_DATE_AND_TIME);
     * LOGGER.debug(JsonUtil.format(DateExtensionUtil.getIntervalDayList(fromDate, toDate)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * ["2011-03-05 00:00:00",
     * "2011-03-06 00:00:00",
     * "2011-03-07 00:00:00",
     * "2011-03-08 00:00:00",
     * "2011-03-09 00:00:00",
     * "2011-03-10 00:00:00"
     * ]
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>每天的日期会被重置清零 <code>00:00:00.000</code></li>
     * <li>方法自动辨识 <code>fromDate</code>和 <code>toDate</code>哪个是开始时间</li>
     * </ol>
     * </blockquote>
     * 
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return 如果 <code>fromDate</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>toDate</code> 是null,抛出 {@link NullPointerException}
     * @see #getIntervalDay(Date, Date)
     * @see org.apache.commons.lang3.time.DateUtils#iterator(Calendar, int)
     * @since 1.5.4
     */
    public static List<Date> getIntervalDayList(Date fromDate,Date toDate){
        Validate.notNull(fromDate, "fromDate can't be null!");
        Validate.notNull(toDate, "toDate can't be null!");

        Date minDate = fromDate.before(toDate) ? fromDate : toDate;
        Date maxDate = fromDate.before(toDate) ? toDate : fromDate;

        // ******重置时间********
        Date beginDateReset = DateUtil.getFirstDateOfThisDay(minDate);
        Date endDateReset = DateUtil.getLastDateOfThisDay(maxDate);

        List<Date> list = new ArrayList<>();
        list.add(beginDateReset);

        // 相隔的天数
        int intervalDay = DateExtensionUtil.getIntervalDay(beginDateReset, endDateReset);
        for (int i = 0; i < intervalDay; ++i){
            list.add(DateUtil.addDay(beginDateReset, i + 1));
        }

        return list;
    }

    /**
     * Test get interval day list.
     */
    @Test
    public void testGetIntervalDayList(){
        String pattern = COMMON_DATE_AND_TIME_WITH_MILLISECOND;
        String begin = "2011-03-05 23:31:25.456";
        String end = "2011-03-10 01:30:24.895";

        List<Date> intervalDayList = getIntervalDayList(toDate(begin, pattern), toDate(end, pattern));
        LOGGER.debug(JsonUtil.format(intervalDayList));

        List<Date> intervalDayList2 = getIntervalDayList(toDate(end, pattern), toDate(begin, pattern));
        LOGGER.debug(JsonUtil.format(intervalDayList2));
    }

}