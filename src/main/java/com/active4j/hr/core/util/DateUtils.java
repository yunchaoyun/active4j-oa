package com.active4j.hr.core.util;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.util.StringUtils;

/**
 * 
 * 类描述：时间操作定义类
 * 
 * @author: chenxl
 */
public class DateUtils extends PropertyEditorSupport {
	// 各种时间格式
	public static final SimpleDateFormat SDF_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	
	public static final SimpleDateFormat SDF_YYYY_MM = new SimpleDateFormat("yyyy年MM月");
	
	public static final SimpleDateFormat SDF_YYYY_MM_ = new SimpleDateFormat("yyyy-MM");
	
	public static final SimpleDateFormat SDF_YYYY = new SimpleDateFormat("yyyy");
	
	public static final String STR_YYYY_MM = "yyyy年MM月";
	// 各种时间格式
	public static final SimpleDateFormat SDF_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	// 各种时间格式
	public static final SimpleDateFormat SDF_YYYY_MM_DD_PINYIN = new SimpleDateFormat("yyyy年MM月dd日");
	public static final SimpleDateFormat SDF_YYYY_MM_DD_HH_MM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat SDF_YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final SimpleDateFormat SDF_YYYYMMDDHHMM = new SimpleDateFormat("yyyyMMddHHmm");
	public static final SimpleDateFormat SDF_HHMM = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat SDF_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat SDF_HH_MM_SS = new SimpleDateFormat("HH:mm:ss");

	public static final String YYYY_MM_DD_MM_HH_SS = "yyyy-MM-dd HH:mm:ss";
	
	public static final String STR_YYYY_MM_DD_MM_HH = "yyyy-MM-dd HH:mm";
	
	public static final String STR_YYYY_MM_DD = "yyyy-MM-dd";
	
	public static final String STR_HH_MM = "HH:mm";
	

	// 以毫秒表示的时间
	private static final long DAY_IN_MILLIS = 24 * 3600 * 1000;
	private static final long HOUR_IN_MILLIS = 3600 * 1000;
	private static final long MINUTE_IN_MILLIS = 60 * 1000;
	private static final long SECOND_IN_MILLIS = 1000;
	
	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static Date getNow() {
		return new Date();
	}

	/**
	 * 获取年月日日期的字符串表现形式
	 * @return
	 */
	public static String getYYYYMMDDStr() {
		return date2Str(SDF_YYYYMMDD);
	}
	
	// 指定模式的时间格式
	public static SimpleDateFormat getSDFormat(String pattern) {
		return new SimpleDateFormat(pattern);
	}

	/**
	 * 当前日历，这里用中国时间表示
	 * 
	 * @return 以当地时区表示的系统当前日历
	 */
	public static Calendar getCalendar() {
		return Calendar.getInstance();
	}

	/**
	 * 指定毫秒数表示的日历
	 * 
	 * @param millis
	 *            毫秒数
	 * @return 指定毫秒数表示的日历
	 */
	public static Calendar getCalendar(long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(millis));
		return cal;
	}

	/**
	 * 时间转字符串
	 * 
	 * @return
	 */
	public static String date2SStr() {
		Date date = getDate();
		if (null == date) {
			return null;
		}
		return SDF_YYYYMMDD.format(date);
	}
	
	/**
	 * 时间转字符串
	 * 
	 * @return
	 */
	public static String date2SStr2() {
		Date date = getDate();
		if (null == date) {
			return null;
		}
		return SDF_YYYY_MM_DD.format(date);
	}
	
	public static Date date2Date(Date date) {
		if(null != date) {
			String tmp = getDate2Str(date);
			Date d = str2Date(tmp, SDF_YYYY_MM_DD_HH_MM_SS);
			return d;
		}
		return null;
	}

	public static String getDate2Str() {
		Date date = getDate();
		
		return SDF_YYYY_MM_DD_HH_MM_SS.format(date);
	}
	
	public static String getDate2Str(Date date) {
		return SDF_YYYY_MM_DD_HH_MM_SS.format(date);
	}
	
	// ////////////////////////////////////////////////////////////////////////////
	// getDate
	// 各种方式获取的Date
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 当前日期
	 * 
	 * @return 系统当前时间
	 */
	public static Date getDate() {
		return new Date();
	}
	


	/**
	 * 指定毫秒数表示的日期
	 * 
	 * @param millis
	 *            毫秒数
	 * @return 指定毫秒数表示的日期
	 */
	public static Date getDate(long millis) {
		return new Date(millis);
	}

	/**
	 * 给定时间 + 分钟数
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar ca = DateUtils.getCalendar();
		ca.setTime(date);
		ca.add(Calendar.MINUTE, minute);
	
		return ca.getTime();
	}
	
	/**
	 * 给定时间 + 分钟数
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date subMinute(Date date, int minute) {
		Calendar ca = DateUtils.getCalendar();
		ca.setTime(date);
		ca.add(Calendar.MINUTE, -minute);
	
		return ca.getTime();
	}

	/**
	 * 字符串转换时间戳
	 * 
	 * @param str
	 * @return
	 */
	public static Timestamp str2Timestamp(String str) {
		Date date = str2Date(str, SDF_YYYY_MM_DD);
		return new Timestamp(date.getTime());
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @param sdf
	 * @return
	 */
	public static Date str2Date(String str, SimpleDateFormat sdf) {
		if (null == str || "".equals(str)) {
			return null;
		}
		Date date = null;
		try {
			date = sdf.parse(str);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String date2Str(SimpleDateFormat date_sdf) {
		Date date = getDate();
		if (null == date) {
			return null;
		}
		return date_sdf.format(date);
	}

	/**
	 * 格式化时间
	 * 
	 * @param data
	 * @param format
	 * @return
	 */
	public static String dataformat(String data, String format) {
		SimpleDateFormat sformat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sformat.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sformat.format(date);
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String date2Str(Date date, SimpleDateFormat date_sdf) {
		if (null == date) {
			return null;
		}
		return date_sdf.format(date);
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String getDate(String format) {
		Date date = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 指定毫秒数的时间戳
	 * 
	 * @param millis
	 *            毫秒数
	 * @return 指定毫秒数的时间戳
	 */
	public static Timestamp getTimestamp(long millis) {
		return new Timestamp(millis);
	}

	/**
	 * 以字符形式表示的时间戳
	 * 
	 * @param time
	 *            毫秒数
	 * @return 以字符形式表示的时间戳
	 */
	public static Timestamp getTimestamp(String time) {
		return new Timestamp(Long.parseLong(time));
	}

	/**
	 * 系统当前的时间戳
	 * 
	 * @return 系统当前的时间戳
	 */
	public static Timestamp getTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 指定日期的时间戳
	 * 
	 * @param date
	 *            指定日期
	 * @return 指定日期的时间戳
	 */
	public static Timestamp getTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 指定日历的时间戳
	 * 
	 * @param cal
	 *            指定日历
	 * @return 指定日历的时间戳
	 */
	public static Timestamp getCalendarTimestamp(Calendar cal) {
		return new Timestamp(cal.getTime().getTime());
	}

	public static Timestamp gettimestamp() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = df.format(dt);
		java.sql.Timestamp buydate = java.sql.Timestamp.valueOf(nowTime);
		return buydate;
	}

	// ////////////////////////////////////////////////////////////////////////////
	// getMillis
	// 各种方式获取的Millis
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 系统时间的毫秒数
	 * 
	 * @return 系统时间的毫秒数
	 */
	public static long getMillis() {
		return new Date().getTime();
	}

	/**
	 * 指定日历的毫秒数
	 * 
	 * @param cal
	 *            指定日历
	 * @return 指定日历的毫秒数
	 */
	public static long getMillis(Calendar cal) {
		return cal.getTime().getTime();
	}

	/**
	 * 指定日期的毫秒数
	 * 
	 * @param date
	 *            指定日期
	 * @return 指定日期的毫秒数
	 */
	public static long getMillis(Date date) {
		return date.getTime();
	}

	/**
	 * 指定时间戳的毫秒数
	 * 
	 * @param ts
	 *            指定时间戳
	 * @return 指定时间戳的毫秒数
	 */
	public static long getMillis(Timestamp ts) {
		return ts.getTime();
	}
	
	/**
	 * 获取当前服务器日期 天
	 * @return
	 */
	public static int getDayOfMonth() {
		Calendar ca = getCalendar();
		
		return ca.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取当前服务器日期 时
	 * @return
	 */
	public static int getHours() {
		Calendar ca = getCalendar();
		
		return ca.get(Calendar.HOUR);
	}
	
	/**
	 * 获取当前服务器日期 分
	 * @return
	 */
	public static int getMinute() {
		Calendar ca = getCalendar();
		
		return ca.get(Calendar.MINUTE);
	}
	
	/**
	 * 获取当前服务器日期 秒
	 * @return
	 */
	public static int getSeconds() {
		Calendar ca = getCalendar();
		
		return ca.get(Calendar.SECOND);
	}
	
	
	//获得当前时间的秒数
	public static String getSecond() {
		Date nowDate = getDate();
		
		long nowL = nowDate.getTime() / 1000l;
		
		return String.valueOf(nowL);
	}
	

	// ////////////////////////////////////////////////////////////////////////////
	// formatDate
	// 将日期按照一定的格式转化为字符串
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 默认方式表示的系统当前日期，具体格式：年-月-日
	 * 
	 * @return 默认日期按“年-月-日“格式显示
	 */
	public static String formatDate() {
		return SDF_YYYY_MM_DD.format(getCalendar().getTime());
	}

	/**
	 * 获取时间字符串
	 */
	public static String getDataString(SimpleDateFormat formatstr) {
		return formatstr.format(getCalendar().getTime());
	}

	/**
	 * 指定日期的默认显示，具体格式：年-月-日
	 * 
	 * @param cal
	 *            指定的日期
	 * @return 指定日期按“年-月-日“格式显示
	 */
	public static String formatDate(Calendar cal) {
		return SDF_YYYY_MM_DD.format(cal.getTime());
	}

	/**
	 * 指定日期的默认显示，具体格式：年-月-日
	 * 
	 * @param date
	 *            指定的日期
	 * @return 指定日期按“年-月-日“格式显示
	 */
	public static String formatDate(Date date) {
		return SDF_YYYY_MM_DD.format(date);
	}

	/**
	 * 指定毫秒数表示日期的默认显示，具体格式：年-月-日
	 * 
	 * @param millis
	 *            指定的毫秒数
	 * @return 指定毫秒数表示日期按“年-月-日“格式显示
	 */
	public static String formatDate(long millis) {
		return SDF_YYYY_MM_DD.format(new Date(millis));
	}

	/**
	 * 默认日期按指定格式显示
	 * 
	 * @param pattern
	 *            指定的格式
	 * @return 默认日期按指定格式显示
	 */
	public static String formatDate(String pattern) {
		return getSDFormat(pattern).format(getCalendar().getTime());
	}

	/**
	 * 指定日期按指定格式显示
	 * 
	 * @param cal
	 *            指定的日期
	 * @param pattern
	 *            指定的格式
	 * @return 指定日期按指定格式显示
	 */
	public static String formatDate(Calendar cal, String pattern) {
		return getSDFormat(pattern).format(cal.getTime());
	}

	/**
	 * 指定日期按指定格式显示
	 * 
	 * @param date
	 *            指定的日期
	 * @param pattern
	 *            指定的格式
	 * @return 指定日期按指定格式显示
	 */
	public static String formatDate(Date date, String pattern) {
		return getSDFormat(pattern).format(date);
	}

	// ////////////////////////////////////////////////////////////////////////////
	// formatTime
	// 将日期按照一定的格式转化为字符串
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 默认方式表示的系统当前日期，具体格式：年-月-日 时：分
	 * 
	 * @return 默认日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime() {
		return SDF_YYYY_MM_DD_HH_MM.format(getCalendar().getTime());
	}

	/**
	 * 指定毫秒数表示日期的默认显示，具体格式：年-月-日 时：分
	 * 
	 * @param millis
	 *            指定的毫秒数
	 * @return 指定毫秒数表示日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime(long millis) {
		return SDF_YYYY_MM_DD_HH_MM.format(new Date(millis));
	}

	/**
	 * 指定日期的默认显示，具体格式：年-月-日 时：分
	 * 
	 * @param cal
	 *            指定的日期
	 * @return 指定日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime(Calendar cal) {
		return SDF_YYYY_MM_DD_HH_MM.format(cal.getTime());
	}

	/**
	 * 指定日期的默认显示，具体格式：年-月-日 时：分
	 * 
	 * @param date
	 *            指定的日期
	 * @return 指定日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime(Date date) {
		return SDF_YYYY_MM_DD_HH_MM.format(date);
	}

	// ////////////////////////////////////////////////////////////////////////////
	// formatShortTime
	// 将日期按照一定的格式转化为字符串
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 默认方式表示的系统当前日期，具体格式：时：分
	 * 
	 * @return 默认日期按“时：分“格式显示
	 */
	public static String formatShortTime() {
		return SDF_HHMM.format(getCalendar().getTime());
	}

	/**
	 * 指定毫秒数表示日期的默认显示，具体格式：时：分
	 * 
	 * @param millis
	 *            指定的毫秒数
	 * @return 指定毫秒数表示日期按“时：分“格式显示
	 */
	public static String formatShortTime(long millis) {
		return SDF_HHMM.format(new Date(millis));
	}

	/**
	 * 指定日期的默认显示，具体格式：时：分
	 * 
	 * @param cal
	 *            指定的日期
	 * @return 指定日期按“时：分“格式显示
	 */
	public static String formatShortTime(Calendar cal) {
		return SDF_HHMM.format(cal.getTime());
	}

	/**
	 * 指定日期的默认显示，具体格式：时：分
	 * 
	 * @param date
	 *            指定的日期
	 * @return 指定日期按“时：分“格式显示
	 */
	public static String formatShortTime(Date date) {
		return SDF_HHMM.format(date);
	}

	// ////////////////////////////////////////////////////////////////////////////
	// parseDate
	// parseCalendar
	// parseTimestamp
	// 将字符串按照一定的格式转化为日期或时间
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
	 * 
	 * @param src
	 *            将要转换的原始字符窜
	 * @param pattern
	 *            转换的匹配格式
	 * @return 如果转换成功则返回转换后的日期
	 * @throws ParseException
	 * @throws AIDateFormatException
	 */
	public static Date parseDate(String src, String pattern) throws ParseException {
		return getSDFormat(pattern).parse(src);

	}

	/**
	 * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
	 * 
	 * @param src
	 *            将要转换的原始字符窜
	 * @param pattern
	 *            转换的匹配格式
	 * @return 如果转换成功则返回转换后的日期
	 * @throws ParseException
	 * @throws AIDateFormatException
	 */
	public static Calendar parseCalendar(String src, String pattern) throws ParseException {

		Date date = parseDate(src, pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static String formatAddDate(String src, String pattern, int amount) throws ParseException {
		Calendar cal;
		cal = parseCalendar(src, pattern);
		cal.add(Calendar.DATE, amount);
		return formatDate(cal);
	}

	/**
	 * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
	 * 
	 * @param src
	 *            将要转换的原始字符窜
	 * @param pattern
	 *            转换的匹配格式
	 * @return 如果转换成功则返回转换后的时间戳
	 * @throws ParseException
	 * @throws AIDateFormatException
	 */
	public static Timestamp parseTimestamp(String src, String pattern) throws ParseException {
		Date date = parseDate(src, pattern);
		return new Timestamp(date.getTime());
	}

	// ////////////////////////////////////////////////////////////////////////////
	// dateDiff
	// 计算两个日期之间的差值
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 计算两个时间之间的差值，根据标志的不同而不同
	 * 
	 * @param flag
	 *            计算标志，表示按照年/月/日/时/分/秒等计算
	 * @param calSrc
	 *            减数
	 * @param calDes
	 *            被减数
	 * @return 两个日期之间的差值
	 */
	public static int dateDiff(char flag, Calendar calSrc, Calendar calDes) {

		long millisDiff = getMillis(calSrc) - getMillis(calDes);

		if (flag == 'y') {
			return (calSrc.get(calSrc.YEAR) - calDes.get(calDes.YEAR));
		}

		if (flag == 'd') {
			return (int) (millisDiff / DAY_IN_MILLIS);
		}

		if (flag == 'h') {
			return (int) (millisDiff / HOUR_IN_MILLIS);
		}

		if (flag == 'm') {
			return (int) (millisDiff / MINUTE_IN_MILLIS);
		}

		if (flag == 's') {
			return (int) (millisDiff / SECOND_IN_MILLIS);
		}

		return 0;
	}

	/**
	 * String类型 转换为Date, 如果参数长度为10 转换格式”yyyy-MM-dd“ 如果参数长度为19 转换格式”yyyy-MM-dd
	 * HH:mm:ss“ * @param text String类型的时间值
	 */
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			try {
				if (text.indexOf(":") == -1 && text.length() == 10) {
					setValue(SDF_YYYY_MM_DD.parse(text));
				} else if (text.indexOf(":") > 0 && text.length() == 19) {
					setValue(SDF_YYYY_MM_DD_HH_MM_SS.parse(text));
				} else {
					throw new IllegalArgumentException("Could not parse date, date format is error ");
				}
			} catch (ParseException ex) {
				IllegalArgumentException iae = new IllegalArgumentException("Could not parse date: " + ex.getMessage());
				iae.initCause(ex);
				throw iae;
			}
		} else {
			setValue(null);
		}
	}

	public static int getYear() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(getDate());
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取当前时间前一天0点时间的int值
	 * @return
	 */
	public static int getBeforeDayTimeStart() {
		Date date = getDate();
		
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(date);//把当前时间赋给日历
		//获取前一天
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		//设置为0点
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		Date beforeDate = calendar.getTime();
		long l = beforeDate.getTime() / 1000;
		
		return 	Integer.valueOf(String.valueOf(l));	
	}
	
	/**
	 * 获取当前时间前一天23点59分59秒的int值
	 * @return
	 */
	public static int getBeforeDayTimeEnd() {
		Date date = getDate();
		
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(date);//把当前时间赋给日历
		//获取前一天
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		//设置为0点
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		
		Date beforeDate = calendar.getTime();
		long l = beforeDate.getTime() / 1000;
		
		return 	Integer.valueOf(String.valueOf(l));	
	}
	
	/**
	 * 获取昨天的日期字符串表现形式
	 * @return
	 */
	public static String getBeforeDayStr() {
		Date date = getDate();
		
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(date);//把当前时间赋给日历
		//获取前一天
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		//设置为0点
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		Date beforeDate = calendar.getTime();
		
		return date2Str(beforeDate, SDF_YYYY_MM_DD);
	}
	
	/**
	 * 获取15天前的日期字符串表现形式
	 * @return
	 */
	public static String getFifDayStr() {
		Date date = getDate();
		
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(date);//把当前时间赋给日历
		//获取前一天
		calendar.add(Calendar.DAY_OF_MONTH, -16);
		//设置为0点
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		Date beforeDate = calendar.getTime();
		
		return date2Str(beforeDate, SDF_YYYY_MM_DD);
	}
	
	/**
	 * 获取30天前的日期字符串表现形式
	 * @return
	 */
	public static String getSanshiDayStr() {
		Date date = getDate();
		
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(date);//把当前时间赋给日历
		//获取前一天
		calendar.add(Calendar.DAY_OF_MONTH, -31);
		//设置为0点
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		Date beforeDate = calendar.getTime();
		
		return date2Str(beforeDate, SDF_YYYY_MM_DD);
	}
	
	/**
	 * 根据时间的int值 获取string
	 * @return
	 * 1455724800
	 */
	public static String getStrfromDateTime(int i) {
		long l = i * 1000l;
		Date date = new Date(l);
		return date2Str(date, SDF_YYYY_MM_DD);
	}
	
	public static String getStrAdd7Min() {
		Date date = getDate();
		
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(date);//把当前时间赋给日历
		//获取前一天
		calendar.add(Calendar.MINUTE, +7);
		
		Date addDate = calendar.getTime();
		
		return date2Str(addDate, SDF_YYYYMMDDHHMMSS);
	}
	
	//获得两个日期的相隔天数
	public static int getDayDiff(Date beforeDate, Date afterDate) {
		return  (int)((afterDate.getTime() - beforeDate.getTime())/86400000);
	}
	
	public static int getHoursFromDate(Date fromDate) {
		Date nowDate = DateUtils.getDate();
		return (int)((nowDate.getTime() - fromDate.getTime())/1000/60/60);
	}
	
	public static int getHoursOfNowDate() {
		Date date = getDate();
		
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(date);//把当前时间赋给日历
		
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }

    
    /**
     * 计算给定时间 距离当前时间的距离 字符串
     * @param beforeDate
     * @return
     */
    public static String getDiffTimeStr(Date beforeDate) {
    	Date afterDate = getDate();
    	
    	long beforeL = beforeDate.getTime();
    	long afterL = afterDate.getTime();
    	
    	long diffL = afterL - beforeL;
    	
    	//得到秒
    	long s = diffL / 1000l;
    	
    	if(s < 60) {
    		return s + "秒前";
    	}else {
    		s = s / 60l;
    		if(s < 60) {
    			return s + "分前";
    		}else{
    			s = s / 60l;
    			if(s < 24) {
    				return s + "小时前";
    			}else {
    				s = s / 24l;
    				return s + "天前";
    			}
    		}
    	}
    	
    }
    
    public static String getDayOfWeekStr(){
    	Calendar ca = getCalendar();
    	ca.setTime(new Date());
    	
    	int i = ca.get(Calendar.DAY_OF_WEEK);
    	
    	if(Calendar.MONDAY == i) {
    		return "星期一";
    	}else if(Calendar.TUESDAY == i){
    		return "星期二";
    	}else if(Calendar.WEDNESDAY == i){
    		return "星期三";
    	}else if(Calendar.THURSDAY == i){
    		return "星期四";
    	}else if(Calendar.FRIDAY == i){
    		return "星期五";
    	}else if(Calendar.SATURDAY == i){
    		return "星期六";
    	}else if(Calendar.SUNDAY == i){
    		return "星期天";
    	}
    	return "";
    }
    
    public static String getStrDate() {
    	String nowDay = date2Str(SDF_YYYY_MM_DD_PINYIN);
    	String nowDayOfWeek = getDayOfWeekStr();
    	String hour = date2Str(SDF_HHMM);
    	
    	return nowDay + "(" + nowDayOfWeek + ")" + " " + hour;
    }
    
    /**
     * 将java日期格式 转换为定时任务执行的cronExpression
     * @param date
     * @return
     */
    public static String getCron(Date date) {
    	 String dateFormat="ss mm HH dd MM ? yyyy";  
         return formatDateByPattern(date, dateFormat);
    }
    
    
    public static String formatDateByPattern(Date date,String dateFormat){  
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);  
        String formatTimeStr = null;  
        if (date != null) {  
            formatTimeStr = sdf.format(date);  
        }  
        return formatTimeStr;  
    } 
    
    public static Date getMonthStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (1 - index));
        return calendar.getTime();
    }
 
    public static Date getNext(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }
    
    public static Date getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}
    
    //获取当前月第一天
    public static String getFirstDay() {
    	Calendar c = Calendar.getInstance();   
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);
        return SDF_YYYY_MM_DD.format(c.getTime());
	}
    
    //获取当前月最后一天
    public static String getLastDay() {
    	Calendar ca = Calendar.getInstance();   
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH)); 
        String last = SDF_YYYY_MM_DD.format(ca.getTime());
        return last;
	}
    
    //获取前三个月时间
    public static String getThreeMontDay() {
    	
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.add(calendar.MONTH, -3);  //设置为前3月
        String threemont = SDF_YYYY_MM_DD.format(calendar.getTime());
        return threemont;
	}
    
  //获取前六个月时间
    public static String getSixMontDay() {
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.add(calendar.MONTH, -6);  //设置为前3月
        String threemont = SDF_YYYY_MM_DD.format(calendar.getTime());
        return threemont;
	}

    //获取近一年时间
    public static String getLastYearDay() {
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.add(calendar.MONTH, -12);  //设置为前3月
        String threemont = SDF_YYYY_MM_DD.format(calendar.getTime());
        return threemont;
	}
    
    /**
	 * 获取当前年
	 * @return
	 */
	public static String getCurrentYear() {
		Calendar ca = Calendar.getInstance();
		ca.setTime(getDate());
		
		return String.valueOf(ca.get(Calendar.YEAR));
	}
    
}