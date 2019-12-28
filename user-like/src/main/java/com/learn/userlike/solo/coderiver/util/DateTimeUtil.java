package com.learn.userlike.solo.coderiver.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

/**
 * java8 LocalDateTime 工具类
 *
 * @Author li cong zhi
 * @Date 2019/9/1016:11
 * @Version v1.0
 */
public class DateTimeUtil {

    /**
     * 默认时间格式
     */
    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = TimeFormat.SHORT_DATE_PATTERN_LINE.formatter;


    public static final DateTimeFormatter DATETIME_FORMATTER = TimeFormat.LONG_DATE_PATTERN_LINE.formatter;


    /**
     *   无参数的构造函数,防止被实例化
     *
     */
    private DateTimeUtil() {}


    public static String formatLocalDate(LocalDate date) {
        return date.format(DEFAULT_DATETIME_FORMATTER);
    }

    public static String formatLocalDateTime(LocalDateTime datetime) {
        return datetime.format(DATETIME_FORMATTER);
    }


    /**
     * 日期相隔小时
     *
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public static long durationHours(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toHours();
    }

    /**
     * 日期相隔分钟
     *
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public static long durationMinutes(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMinutes();
    }

    /**
     * 日期相隔毫秒数
     *
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public static long durationMillis(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMillis();
    }

    /**
     * 返回当前的日期
     *
     * @return
     */
    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }

    /**
     * 减去指定的天数返回日期
     * @return
     */
    public static LocalDate subtractMinusDays(int day){
        return getCurrentLocalDate().minusDays(day);
    }

    /**
     * 返回当前时间
     *
     * @return
     */
    public static LocalTime getCurrentLocalTime() {
        return LocalTime.now();
    }

    public static LocalDateTime get() {
        return LocalDateTime.now();
    }

    public static int getYear() {
        return get().getYear();
    }

    public static LocalDateTime withYear(int year) {
        return get().withYear(year);
    }

    public static int getMonth() {
        return get().getMonthValue();
    }

    public static LocalDateTime firstDayOfThisYear(int year) {
        return withYear(year).with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN);
    }

    /**
     * 格式化字符串转为Date
     * @param time 格式化时间
     * @param pattern 格式
     * @return
     */
    public static Date parseDate(String time, String pattern){
        return Date.from(parseLocalDateTime(time, pattern).atZone(ZoneId.systemDefault()).toInstant());
    }


    private static final ConcurrentMap<String, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<>();

    private static final int PATTERN_CACHE_SIZE = 500;
    /**
     * Date转换为格式化时间
     * @param date date
     * @param pattern 格式
     * @return
     */
    public static String format(Date date, String pattern){
        return format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), pattern);
    }

    /**
     * localDateTime转换为格式化时间
     * @param localDateTime localDateTime
     * @param pattern 格式
     * @return
     */
    public static String format(LocalDateTime localDateTime, String pattern){
        DateTimeFormatter formatter = createCacheFormatter(pattern);
        return localDateTime.format(formatter);
    }



    /**
     * 格式化字符串转为LocalDateTime
     * @param time 格式化时间
     * @param pattern 格式
     * @return
     */
    public static LocalDateTime parseLocalDateTime(String time, String pattern){
        DateTimeFormatter formatter = createCacheFormatter(pattern);
        return LocalDateTime.parse(time, formatter);
    }

    /**
     * 在缓存中创建DateTimeFormatter
     * @param pattern 格式
     * @return
     */
    private static DateTimeFormatter createCacheFormatter(String pattern){
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Invalid pattern specification");
        }
        DateTimeFormatter formatter = FORMATTER_CACHE.get(pattern);
        if(formatter == null){
            if(FORMATTER_CACHE.size() < PATTERN_CACHE_SIZE){
                formatter = DateTimeFormatter.ofPattern(pattern);
                DateTimeFormatter oldFormatter = FORMATTER_CACHE.putIfAbsent(pattern, formatter);
                if(oldFormatter != null){
                    formatter = oldFormatter;
                }
            }
        }
        return formatter;
    }


    /**
     * @param year
     * @return String
     * @Title: getFirstDayOfThisYear
     * @Description: 获取设置所属年最初时间
     */
    public static String getFirstDayOfThisYear(int year) {
        LocalDateTime firstDayOfThisYear = firstDayOfThisYear(year);
        return DATETIME_FORMATTER.format(firstDayOfThisYear);
    }

    public static LocalDateTime lastDayOfThisYear(int year) {
        return withYear(year).with(TemporalAdjusters.lastDayOfYear()).with(LocalTime.MAX);
    }

    /**
     * @param year
     * @return String
     * @Title: getLastDayOfThisYear
     * @Description: 获取设置所属年最后时间
     */
    public static String getLastDayOfThisYear(int year) {
        LocalDateTime lastDayOfThisYear = lastDayOfThisYear(year);
        return DATETIME_FORMATTER.format(lastDayOfThisYear);
    }

    /**
     * @return String
     * @Title: getFirstDayOfThisMonth
     * @Description: 获取本月的第一天
     */
    public static String getFirstDayOfThisMonth() {
        LocalDateTime firstDayOfThisYear = get().with(TemporalAdjusters.firstDayOfMonth());
        return DATETIME_FORMATTER.format(firstDayOfThisYear);
    }

    /**
     * @return String
     * @Title: getFirstDayOfThisMonth
     * @Description: 获取本月的最末天
     */
    public static String getLastDayOfThisMonth() {
        LocalDateTime firstDayOfThisYear = get().with(TemporalAdjusters.lastDayOfMonth());
        return DATETIME_FORMATTER.format(firstDayOfThisYear);
    }

    /**
     * @param days
     * @return LocalDateTime
     * @Title: plusDays
     * @Description: 当前日期向后推多少天
     */
    public static LocalDateTime plusDays(int days) {
        return get().plusDays(days);
    }


        
    /**
     * 当前日期往前推多少天
     * @author      li cong zhi
     * @param       days
     * @return      
     * @exception   
     * @date        2019/11/27 18:19
     */
    public static LocalDate minusDays(int days) {
        return getCurrentLocalDate().minusDays(days);
    }
    /**
     * @param year
     * @param month
     * @return LocalDateTime
     * @Title: firstDayOfWeekInYearMonth
     * @Description: 获取指定年月的第一个周一
     */
    public static LocalDateTime firstDayOfWeekInYearMonth(int year, int month) {
        return get().withYear(year).withMonth(month).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
    }

    /**
     * @return LocalDateTime
     * @Title: todayStart
     * @Description: 当天开始时间
     */
    public static LocalDateTime todayStart() {
        return LocalDateTime.of(getCurrentLocalDate(), LocalTime.MIN);
    }

    /**
     * @return LocalDateTime
     * @Title: todayEnd
     * @Description: 当天结束时间
     */
    public static LocalDateTime todayEnd() {
        return LocalDateTime.of(getCurrentLocalDate(), LocalTime.MAX);
    }

    /**
     * @return String
     * @Title: getStartDayOfWeekToString
     * @Description: 获取本周第一天
     */
    public static String getStartDayOfWeekToString() {
        return formatLocalDate(getStartDayOfWeek());
    }

    public static LocalDate getStartDayOfWeek() {
        TemporalAdjuster FIRST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.minusDays(localDate
                .getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue()));
        return getCurrentLocalDate().with(FIRST_OF_WEEK);
    }





    /**
     * String 转化为 LocalDateTime
     *
     * @param timeStr
     *            被转化的字符串
     * @return LocalDateTime
     */
    public static LocalDateTime parseTime(String timeStr) {
        return LocalDateTime.parse(timeStr, DEFAULT_DATETIME_FORMATTER);

    }

    /**
     * String 转化为 LocalDateTime
     *
     * @param timeStr
     *            被转化的字符串
     * @param timeFormat
     *            转化的时间格式
     * @return LocalDateTime
     */
    public static LocalDateTime parseTime(String timeStr, TimeFormat timeFormat) {
        return LocalDateTime.parse(timeStr, timeFormat.formatter);

    }

    /**
     * LocalDateTime 转化为String
     *
     * @param time
     *            LocalDateTime
     * @return String
     */
    public static String parseTime(LocalDateTime time) {
        return DEFAULT_DATETIME_FORMATTER.format(time);

    }

    /**
     * LocalDateTime 时间转 String
     *
     * @param time
     *            LocalDateTime
     * @param format
     *            时间格式
     * @return String
     */
    public static String parseTime(LocalDateTime time, TimeFormat format) {
        return format.formatter.format(time);

    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentDateTime() {
        return DEFAULT_DATETIME_FORMATTER.format(LocalDateTime.now());
    }

    /**
     * 获取当前时间
     *
     * @param timeFormat
     *            时间格式
     * @return
     */
    public static String getCurrentDateTime(TimeFormat timeFormat) {
        return timeFormat.formatter.format(LocalDateTime.now());
    }
    /**
     * 内部枚举类
     *
     * @author xiaowen
     * @date 2016年11月1日 @ version 1.0
     */
    public enum TimeFormat {
        //短时间格式 年月日
        /**
         *时间格式：yyyy-MM-dd
         */
        SHORT_DATE_PATTERN_LINE("yyyy-MM-dd"),
        /**
         * 时间格式:yyyy-MM
         */
        MONTH_FORMATTER("yyyy-MM"),
        /**
         * 时间格式：yyyy-MM-dd HH:mm
         */
        SHORT_DATE_PATTERN_TIME_MINUTE("yyyy-MM-dd HH:mm"),
        /**
         *时间格式：yyyy/MM/dd
         */
        SHORT_DATE_PATTERN_SLASH("yyyy/MM/dd"),
        /**
         *时间格式：yyyy\\MM\\dd
         */
        SHORT_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd"),
        /**
         *时间格式：yyyyMMdd
         */
        SHORT_DATE_PATTERN_NONE("yyyyMMdd"),

        /**
         * 时间格式:HH:mm:ss
         */
        TIME_FORMATTER("HH:mm:ss"),

        // 长时间格式 年月日时分秒
        /**
         *时间格式：yyyy-MM-dd HH:mm:ss
         */
        LONG_DATE_PATTERN_LINE("yyyy-MM-dd HH:mm:ss"),

        /**
         *时间格式：yyyy/MM/dd HH:mm:ss
         */
        LONG_DATE_PATTERN_SLASH("yyyy/MM/dd HH:mm:ss"),
        /**
         *时间格式：yyyy\\MM\\dd HH:mm:ss
         */
        LONG_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss"),
        /**
         *时间格式：yyyyMMdd HH:mm:ss
         */
        LONG_DATE_PATTERN_NONE("yyyyMMdd HH:mm:ss"),

        // 长时间格式 年月日时分秒 带毫秒
        LONG_DATE_PATTERN_WITH_MILSEC_LINE("yyyy-MM-dd HH:mm:ss.SSS"),

        LONG_DATE_PATTERN_WITH_MILSEC_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),

        LONG_DATE_PATTERN_WITH_MILSEC_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss.SSS"),

        LONG_DATE_PATTERN_WITH_MILSEC_NONE("yyyyMMdd HH:mm:ss.SSS");

        private transient DateTimeFormatter formatter;



        TimeFormat(String pattern) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        }


    }

    /**
     * @return String
     * @Title: getEndDayOfWeekToString
     * @Description: 获取周最后一天
     */
    public static String getEndDayOfWeekToString() {
        return formatLocalDate(getEndDayOfWeek());
    }

    public static LocalDate getEndDayOfWeek() {
        TemporalAdjuster LAST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.plusDays(
                DayOfWeek.SUNDAY.getValue() - localDate.getDayOfWeek().getValue()));
        return getCurrentLocalDate().with(LAST_OF_WEEK);
    }
    /**
     * 将某时间字符串转为自定义时间格式的LocalDateTime
     * @author      li cong zhi
     * @param       time   时间
     * @param       format 日期格式
     * @return
     * @exception
     * @date        2019/9/10 16:26
     */
    public static LocalDateTime parseStringToDateTime(String time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }





    public static LocalDate plus(LocalDate localDate
            ,int between,ChronoUnit chronoUnit) {
        return localDate.plus(between,chronoUnit);

    }
    public static String getYesterdayByFormat(String timeFormat){
        return LocalDateTime.now().plusDays(2).format(DateTimeFormatter.ofPattern(timeFormat));
    }


    public static String convertTimeToString(Long time,DateTimeFormatter dateTimeFormatter){

        return dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time),ZoneId.systemDefault()));
    }




    /**
     * 计算年份（当前年份）
     * @author      li cong zhi
     * @param       currentYear 当前年份
     * @param       number
     * @return
     * @exception
     * @date        2019/9/27 16:05
     */
    public static int subtractYear(int currentYear,int number){

        if(number>0){
            return currentYear-number;
        }
        return currentYear;
    }



    /**
     * 获取一段时间之间的每一天日期
     * @author      li cong zhi
     * @param       startDate
     * @param       endDate
     * @return
     * @exception
     * @date        2019/11/27 17:43
     */
    public static List<String> getBetweenDate(LocalDate startDate, LocalDate endDate) {
        List<String> list = new ArrayList<String>();

        long distance = ChronoUnit.DAYS.between(startDate, endDate);
        if (distance < 1) {
            return list;
        }

        Stream.iterate(startDate, d -> d.plusDays(1)).limit(distance + 1).forEach(date -> list.add(date.toString()));
        return list;
    }



    public static void main(String[] args) {

//        LocalDateTime localDateTime = plusDays(7);
//
//        String date = formatLocalDateTime(localDateTime);
//        // 当前日期
//        LocalDate  endDate= subtractMinusDays(1);
//
//
//
//        int dayOfMonth = endDate.getDayOfMonth();


        // 获取当前日期
        LocalDate now = LocalDate.now();


//
//        // 当前日期加上往后推20天
//        LocalDate plusDate = endDate.plus(7, ChronoUnit.DAYS);
//
//        LocalDate plusYear = endDate.plus(10, ChronoUnit.YEARS);
//
//        System.out.println("==========plusDate=========="+plusDate);
//        System.out.println("==========plusYear=========="+plusYear);
//
//        System.out.println("==========当前日期往前推10天==========");
//        // 当前日期往前推10天
//       // LocalDate minusDay = now.minusDays(7);
//
//        LocalDate startDate = minusDays(7);
//
//        String endDayOfWeek = getStartDayOfWeekToString();
//        System.out.println("==========endDayOfWeek=========="+endDayOfWeek);
//        Long num =0L;
//        System.out.println("==========num=========="+num);
//        System.out.println("==========LocalDate=========="+endDate);
//
//        LocalDate minusYear = endDate.minus(7, ChronoUnit.DAYS);
//        System.out.println("==========startDate=========="+startDate);
//        System.out.println("==========endDate=========="+endDate);
//        System.out.println("==========LocalDate minusYear=========="+minusYear);


//        BigInteger zero = BigInteger.ZERO;
//
//        BigDecimal zero1 = BigDecimal.valueOf(20.0);
//        // 部门总问题数据量/(部门所有清洗数据 * 部门规则数据量)
//        BigInteger dataCleanNum = new BigInteger("137");
//        BigInteger ruleNum = new BigInteger("23");
//        System.out.println("==========BigDecimal#dataCleanNum=========="+dataCleanNum);
//        System.out.println("==========BigDecimal#ruleNum=========="+ruleNum);
//
//        BigInteger deptValue = dataCleanNum.multiply(ruleNum);
//        System.out.println("==========BigDecimal#deptValue=========="+deptValue);
//        // 部门问题占比
//        BigInteger issueNum = new BigInteger("37");
//        BigInteger deptRatio = issueNum.divide(deptValue);
//
//
//        BigInteger num = BigInteger.valueOf(0);
//
//        BigInteger issueNumber = BigInteger.valueOf(123655);
//
//        BigInteger deptVal = BigInteger.valueOf(123655);
//
//        BigInteger divide = issueNumber.divide(deptVal);
//        System.out.println("==========BigDecimal#divide=====true====="+divide);
//
//
//        System.out.println("==========BigDecimal#deptRatio=========="+deptRatio);
//        System.out.println("==========BigDecimal#zero1=========="+zero1);
//        System.out.println("==========zero=========="+zero);


        long fileTimestamp = -2L;

        System.out.println("==========fileTimestamp=========="+fileTimestamp);
    }

}
