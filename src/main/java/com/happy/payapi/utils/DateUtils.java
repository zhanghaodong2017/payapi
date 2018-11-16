package com.happy.payapi.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 将数据库去除时间与支付要求的时间格式进行转换
 */
public class DateUtils {

	public static final String yearFormat = "yyyy";

	public static final String monthFormat = "yyyy-MM";
	
	public static final String monthChineseFormat = "yyyy年MM月";

	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	
	public static final String HHmmss = "HHmmss";
	
	public static final String dayFormat = "yyyy-MM-dd";

	public static final String dayChineseFormat = "yyyy年MM月dd日";

	public static final String dayWithoutYearFormat = "MM-dd";

	public static final String dayChineseWithoutYearFormat = "MM月dd日";

	public static final String minuteFormat = "yyyy-MM-dd HH:mm";

	public static final String minuteWithoutYearFormat = "MM-dd HH:mm";

	public static final String secondFormat = "yyyy-MM-dd HH:mm:ss";

	public static final String default_startTime = "2011-06-01 00:00:00";

	public static final String FMT_yyMMdd = "yyMMdd";

	public static final String FMT_yyyyMMdd = "yyyyMMdd";

	public static final String timeFormat = "HH:mm:ss";

	public static final String timeWithoutSecondFormat = "HH:mm";

	public static final String timeWithoutYearFormat = "MM-dd HH:mm:ss";

	public static final String weekChineseFormat = "EEEE";
	
	public static final String MdHms_FORMAT="MM月dd日 HH时mm分ss秒";

	@SuppressWarnings("finally")
	public static String convert(String date, String patternDatabase, String pattern) {
		String resStr = null;
		DateFormat reqSdf = new SimpleDateFormat(patternDatabase);
		DateFormat resSdf = new SimpleDateFormat(pattern);
		try {
			resStr = resSdf.format(reqSdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			return resStr;
		}
	}

	public static String getDateStr(Date date, String pattern) {
		if(date==null)return null;
		DateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false); // 严格解析
		return sdf.format(date);
	}

	public static Date parseDate(String Date) throws ParseException {

		if ((Date == null) || (Date.length() != secondFormat.length())) {
			throw new ParseException("length is not right", 0);
		}
		return parse(Date, secondFormat);
	}

	public static Date parse(String date, String pattern) throws ParseException {
		DateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false); // 严格解析
		return sdf.parse(date);
	}

	public static String formatDateStr(Date date) {

		if (date == null) {
			return null;
		}

		return getDateStr(date, secondFormat);
	}

	public static String getDateString(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		return dateFormat.format(calendar.getTime());
	}

	public static Date getCST2GMTTime(String time) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(secondFormat);
		DateFormat gmtFormat = new SimpleDateFormat(secondFormat);
		gmtFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		if (time == null || time.length() != secondFormat.length()) {
			throw new ParseException("length is not right", 0);
		}
		return gmtFormat.parse(gmtFormat.format(dateFormat.parse(time)));
	}

	public static Date getCST2GMTTime(String time, String format) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(format);
		DateFormat gmtFormat = new SimpleDateFormat(format);
		gmtFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		return gmtFormat.parse(gmtFormat.format(dateFormat.parse(time)));
	}

	public static String getGMT2CSTTime(Date time) {
		if (time == null) {
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat(secondFormat);
		return dateFormat.format(time);
	}

	public static String getGMT2CSTTime(String gmtTime) {
		return null;
	}

	/**
	 * 获得当前时间的年-月
	 * 
	 * @return
	 */
	public static String getCurrentMonth() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 查询条件中的日期与当前日期比较，如果比当前日期早返回false 如果和当前日期相等或者晚于当前日期返回true
	 * 
	 * @param queryTime 查询条件中的参数yy-mm
	 * @return
	 */
	public static boolean compareTo(String queryTime) {
		boolean result = true;
		String currentMonth = getCurrentMonth();
		if (!currentMonth.equals(queryTime)) {
			String[] yearAndMonth = queryTime.split("-");
			String[] currentYearAndMonth = currentMonth.split("-");
			int qYear = Integer.parseInt(yearAndMonth[0]);
			int qMonth = Integer.parseInt(yearAndMonth[1]);
			int cYear = Integer.parseInt(currentYearAndMonth[0]);
			int cMonth = Integer.parseInt(currentYearAndMonth[1]);
			if (qYear < cYear) {
				result = false;
			} else if ((qYear == cYear) && (qMonth < cMonth)) {
				result = false;
			}
		}
		return result;
	}

	public static String getBeforeDays(Date dNow, int day, String pattern) {
		// Date dNow = new Date(); //当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		calendar.add(Calendar.DATE, day); // 设置天数
		dBefore = calendar.getTime();
		return getDateStr(dBefore, pattern);
	}

	public static String getBeforeDays(int day, String pattern) {
		Date now = new Date();
		return getBeforeDays(now, day, pattern);
	}

	public static String getBeforeDays(Date dNow, int day) {
		return getBeforeDays(dNow, day, secondFormat);
	}

	public static String getTodayZeroHourStr() {
		return getZeroHourStr(0);
	}

	public static String getZeroHourStr(int day) {
		return getZeroHourStr(day, secondFormat);
	}

	public static String getZeroHourStr(int day, String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		return getBeforeDays(cal.getTime(), day, pattern);
	}

	public static Date getNowDateBegin(Date nowDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int date = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.set(year, month, date, 0, 0, 0);
		return calendar.getTime();
	}

	public static Date getNowDateEnd(Date nowDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int date = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.set(year, month, date, 23, 59, 59);
		return calendar.getTime();
	}

	public static String format(String format, Date date) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 返回指定日期的N天后的日期
	 * 
	 * @param date
	 * @param day
	 * @return 当day>0，返回之后的天数，否则返回之前的天数
	 */
	public static Date getAfterDays(Date date, int day) {
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(date);// 把当前时间赋给日历
		calendar.add(Calendar.DATE, day); // 设置天数
		return calendar.getTime();
	}

	/**
	 * 获取指定日期N月后的日期
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date getAfterMonth(Date date, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期所在周的周一
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		return calendar.getTime();
	}

	/**
	 * 获取指定日期所在月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getfirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * 获取指定日期所在月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * 获取指定日期所在季度的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfSeason(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		if (currentMonth >= 1 && currentMonth <= 3) {
			calendar.set(Calendar.MONTH, 0);
		} else if (currentMonth >= 4 && currentMonth <= 6) {
			calendar.set(Calendar.MONTH, 3);
		} else if (currentMonth >= 7 && currentMonth <= 9) {
			calendar.set(Calendar.MONTH, 6);
		} else if (currentMonth >= 10 && currentMonth <= 12) {
			calendar.set(Calendar.MONTH, 9);
		}
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期N年后的日期
	 * 
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date getAfterYear(Date date, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 返回更小的时间
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Date min(Date date1, Date date2) {
		LocalDateTime localDateTime1 = dateToLocalDateTime(date1);
		LocalDateTime localDateTime2 = dateToLocalDateTime(date2);
		return (localDateTime1.isBefore(localDateTime2)) ? localDateTimeToDate(localDateTime1) : localDateTimeToDate(localDateTime2);
	}

	/**
	 * 返回更大的时间
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Date max(Date date1, Date date2) {
		LocalDateTime localDateTime1 = dateToLocalDateTime(date1);
		LocalDateTime localDateTime2 = dateToLocalDateTime(date2);
		return (localDateTime1.isAfter(localDateTime2)) ? localDateTimeToDate(localDateTime1) : localDateTimeToDate(localDateTime2);
	}

	/**
	 * 比较两个Date的日期大小（忽略时间）
	 * 
	 * @param date1
	 * @param date2
	 * @return date1 > date2，返回1，相等返回0，否则返回-1
	 */
	public static int compareToDate(Date date1, Date date2) {
		LocalDate localDate1 = dateToLocalDate(date1);
		LocalDate localDate2 = dateToLocalDate(date2);
		return localDate1.isEqual(localDate2) ? 0 : (localDate1.isAfter(localDate2) ? 1 : -1);
	}

	/**
	 * 比较两个Date的时间大小（忽略日期）
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareToTime(Date date1, Date date2) {
		LocalTime time1 = dateToLocalTime(date1);
		LocalTime time2 = dateToLocalTime(date2);
		return time1.equals(time2) ? 0 : (time1.isAfter(time2) ? 1 : -1);
	}

	private static LocalDate dateToLocalDate(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		return localDateTime.toLocalDate();
	}

	private static LocalTime dateToLocalTime(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		return localDateTime.toLocalTime();
	}

	private static LocalDateTime dateToLocalDateTime(Date date) {
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone);
	}

	private static Date localDateTimeToDate(LocalDateTime localDateTime) {
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		return Date.from(instant);
	}

	/**
	 * 指定日期按秒增(减)
	 * 
	 * @param date 指定日期
	 * @param amount 增(减)量
	 * @return 增(减)后的日期
	 */
	public static Date addSeconds(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	/**
	 * 指定日期按分增(减)
	 * 
	 * @param date 指定日期
	 * @param amount 增(减)量
	 * @return 增(减)后的日期
	 */
	public static Date addMinutes(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}

	/**
	 * 指定日期按时增(减)
	 * 
	 * @param date 指定日期
	 * @param amount 增(减)量
	 * @return 增(减)后的日期
	 */
	public static Date addHours(Date date, int amount) {
		return add(date, Calendar.HOUR_OF_DAY, amount);
	}

	/**
	 * 指定日期按天增(减)
	 * 
	 * @param date 指定日期
	 * @param amount 增(减)量
	 * @return 增(减)后的日期
	 */
	public static Date addDays(Date date, int amount) {
		return add(date, Calendar.DAY_OF_MONTH, amount);
	}

	/**
	 * 指定日期按周增(减)
	 * 
	 * @param date 指定日期
	 * @param amount 增(减)量
	 * @return 增(减)后的日期
	 */
	public static Date addWeeks(Date date, int amount) {
		return add(date, Calendar.WEEK_OF_YEAR, amount);
	}

	/**
	 * 指定日期按月增(减)
	 * 
	 * @param date 指定日期
	 * @param amount 增(减)量
	 * @return 增(减)后的日期
	 */
	public static Date addMonths(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	/**
	 * 指定日期按年增(减)
	 * 
	 * @param date 指定日期
	 * @param amount 增(减)量
	 * @return 增(减)后的日期
	 */
	public static Date addYears(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}

	/**
	 * 指定日期增(减)
	 * 
	 * @param date 指定日期
	 * @param calendarField 日期单位
	 * @param amount 增(减)量
	 * @return Date 增(减)后的日期
	 */
	public static Date add(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}

	/**
	 * 计算两个日期相差天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long calculateDays(Date date1, Date date2) {
		long dayMillSecs = date2.getTime() - date1.getTime();
		return Math.abs(dayMillSecs / 86400000);
	}
	/**
	 *计算两个日期相差的小时数 
	 */
	public static long calculateHours(Date date1, Date date2) {
		long dayMillSecs = date2.getTime() - date1.getTime();
		return Math.abs(dayMillSecs / 3600000);
	}
	/**
	 *计算两个日期相差的分钟数
	 */
	public static long calculateMins(Date date1, Date date2) {
		long dayMillSecs = date2.getTime() - date1.getTime();
		return Math.abs(dayMillSecs / 60000);
	}
	
}
