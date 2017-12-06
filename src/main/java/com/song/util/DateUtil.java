package com.song.util;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间格式类 util
 */
public class DateUtil {

	/**
	 * Base ISO 8601 Date format yyyyMMdd i.e., 20021225 for the 25th day of
	 * December in the year 2002
	 */
	public static final String ISO_DATE_FORMAT = "yyyyMMdd";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String MONTH_AND_DAY = "MM-dd";

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static String DATETIME_PATTERN = "yyyyMMddHHmmss";
	public static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
     * 
     */
	public static String DATETIME_MILLIONSECOND_PATTERN = "yyyyMMddHHmmssSSS";

	/**
	 * Default lenient setting for getDate.
	 */
	private static boolean LENIENT_DATE = false;

	/**
	 * @param JD
	 * @return
	 */
	protected static final float normalizedJulian(float JD) {

		float f = Math.round(JD + 0.5f) - 0.5f;

		return f;
	}

	/**
	 * Returns the Date from a julian. The Julian date will be converted to noon
	 * GMT, such that it matches the nearest half-integer (i.e., a julian date
	 * of 1.4 gets changed to 1.5, and 0.9 gets changed to 0.5.)
	 * 
	 * @param JD
	 *            the Julian date
	 * @return the Gregorian date
	 */
	public static final Date toDate(float JD) {

		/*
		 * To convert a Julian Day Number to a Gregorian date, assume that it is
		 * for 0 hours, Greenwich time (so that it ends in 0.5). Do the
		 * following calculations, again dropping the fractional part of all
		 * multiplicatons and divisions. Note: This method will not give dates
		 * accurately on the Gregorian Proleptic Calendar, i.e., the calendar
		 * you get by extending the Gregorian calendar backwards to years
		 * earlier than 1582. using the Gregorian leap year rules. In
		 * particular, the method fails if Y<400.
		 */
		float Z = (normalizedJulian(JD)) + 0.5f;
		float W = (int) ((Z - 1867216.25f) / 36524.25f);
		float X = (int) (W / 4f);
		float A = Z + 1 + W - X;
		float B = A + 1524;
		float C = (int) ((B - 122.1) / 365.25);
		float D = (int) (365.25f * C);
		float E = (int) ((B - D) / 30.6001);
		float F = (int) (30.6001f * E);
		int day = (int) (B - D - F);
		int month = (int) (E - 1);

		if (month > 12) {
			month = month - 12;
		}

		int year = (int) (C - 4715); // (if Month is January or February) or
										// C-4716 (otherwise)

		if (month > 2) {
			year--;
		}

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1); // damn 0 offsets
		c.set(Calendar.DATE, day);

		return c.getTime();
	}

	/**
	 * @param isoString
	 * @param fmt
	 * @param field
	 *            Calendar.YEAR/Calendar.MONTH/Calendar.DATE
	 * @param amount
	 * @return
	 * @throws ParseException
	 */
	public static final String dateIncrease(String isoString, String fmt,
                                            int field, int amount) {

		try {
			Calendar cal = GregorianCalendar.getInstance(TimeZone
					.getTimeZone("GMT"));
			cal.setTime(stringToDate(isoString, fmt, true));
			cal.add(field, amount);

			return dateToString(cal.getTime(), fmt);

		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * java.util.Date
	 * 
	 * @param dateText
	 * @param format
	 * @param lenient
	 * @return
	 */
	public static Date stringToDate(String dateText, String format,
                                    boolean lenient) {

		if (dateText == null) {

			return null;
		}

		DateFormat df = null;

		try {

			if (format == null) {
				df = new SimpleDateFormat();
			} else {
				df = new SimpleDateFormat(format);
			}

			// setLenient avoids allowing dates like 9/32/2001
			// which would otherwise parse to 10/2/2001
			df.setLenient(false);

			return df.parse(dateText);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * java.util.Date
	 * 
	 * @param format
	 * @return
	 */
	public static Date stringToDate(String dateString, String format) {

		return stringToDate(dateString, format, LENIENT_DATE);
	}

	/**
	 * @return
	 * @param date
	 */
	public static String dateToString(Date date) {
		return dateToString(date,DATE_FORMAT);
	}
	public static String dateToString(Date date, String pattern) {
		if (date == null) {
			return null;
		}

		try {
			SimpleDateFormat sfDate = new SimpleDateFormat(pattern);
			sfDate.setLenient(false);

			return sfDate.format(date);
		} catch (Exception e) {

			return null;
		}
	}

	/**
	 * @return
	 */
	public static Date getCurrentDateTime() {
		Calendar calNow = Calendar.getInstance();
		Date dtNow = calNow.getTime();

		return dtNow;
	}
	
	public static Timestamp getCurrentTimestamp() {
		Calendar calNow = Calendar.getInstance();
		Date dtNow = calNow.getTime();

		return new Timestamp(dtNow.getTime());
	}

	/**
	 * @param pattern
	 * @return
	 */
	public static String getCurrentDateString(String pattern) {
		return dateToString(getCurrentDateTime(), pattern);
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStringWithTime(Date date) {

		return dateToString(date, DATETIME_PATTERN);
	}

	/**
	 * @param date
	 * @param days
	 * @return java.util.Date
	 */
	public static Date dateIncreaseByDay(Date date, int days) {

		Calendar cal = GregorianCalendar.getInstance(TimeZone
				.getTimeZone("GMT"));
		cal.setTime(date);
		cal.add(Calendar.DATE, days);

		return cal.getTime();
	}

	/**
	 * @param date
	 * @return java.util.Date
	 */
	public static Date dateIncreaseByMonth(Date date, int mnt) {

		Calendar cal = GregorianCalendar.getInstance(TimeZone
				.getTimeZone("GMT"));
		cal.setTime(date);
		cal.add(Calendar.MONTH, mnt);

		return cal.getTime();
	}

	/**
	 * @param date
	 * @param mnt
	 * @return java.util.Date
	 */
	public static Date dateIncreaseByYear(Date date, int mnt) {

		Calendar cal = GregorianCalendar.getInstance(TimeZone
				.getTimeZone("GMT"));
		cal.setTime(date);
		cal.add(Calendar.YEAR, mnt);

		return cal.getTime();
	}

	/**
	 * @param date
	 *            yyyy-MM-dd
	 * @param days
	 * @return yyyy-MM-dd
	 */
	public static String dateIncreaseByDay(String date, int days) {
		return dateIncreaseByDay(date, ISO_DATE_FORMAT, days);
	}

	/**
	 * @param date
	 * @param fmt
	 * @param days
	 * @return
	 */
	public static String dateIncreaseByDay(String date, String fmt, int days) {
		return dateIncrease(date, fmt, Calendar.DATE, days);
	}

	public static String stringToString(String src, String srcfmt, String desfmt) {
		return dateToString(stringToDate(src, srcfmt), desfmt);
	}

	public static int getDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static String getCurDate() {
		return dateToString(new Date(), "yyyyMMdd");
	}

	public static String getCurTime() {
		return dateToString(new Date(), "HHmmss");
	}

	public static String getCurDateTime() {
		String curTime = dateToString(new Date(), "yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();

		int iHour = cal.get(Calendar.HOUR_OF_DAY);
		String hour = "" + iHour;
		while (hour.length() < 2) {
			hour = "0" + hour;
		}

		curTime = curTime.substring(0, 8) + hour + curTime.substring(10);
		return curTime;
	}
	
	public static String getCurDateTime(String pattern) {
		String curTime = dateToString(new Date(), pattern);
		return curTime;
	}

	public static String getCalculateTime(int CalendarType, int value) {
		Calendar cal = new GregorianCalendar();
		if (CalendarType == Calendar.YEAR) {
			cal.add(Calendar.YEAR, value);
		} else if (CalendarType == Calendar.MONTH) {
			cal.add(Calendar.MONTH, value);
		} else if (CalendarType == Calendar.DATE) {
			cal.add(Calendar.DATE, value);
		} else if (CalendarType == Calendar.HOUR) {
			cal.add(Calendar.HOUR, value);
		} else if (CalendarType == Calendar.MINUTE) {
			cal.add(Calendar.MINUTE, value);
		} else if (CalendarType == Calendar.SECOND) {
			cal.add(Calendar.SECOND, value);
		}
		String y = "" + cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		String m = "" + (month > 9 ? "" + month : "0" + month);
		int day = cal.get(Calendar.DATE);
		String d = "" + (day > 9 ? "" + (day) : "0" + (day));
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		String h = "" + (hour > 9 ? "" + (hour) : "0" + (hour));
		int minute = cal.get(Calendar.MINUTE);
		String mm = "" + (minute > 9 ? "" + (minute) : "0" + (minute));
		int second = cal.get(Calendar.SECOND);
		String s = "" + (second > 9 ? "" + (second) : "0" + (second));
		return y + "-" + m + "-" + d + " " + h + ":" + mm + ":" + s;
	}

	public static String getCalculateTimeString(int CalendarType, int value) {
		Calendar cal = new GregorianCalendar();
		if (CalendarType == Calendar.YEAR) {
			cal.add(Calendar.YEAR, value);
		} else if (CalendarType == Calendar.MONTH) {
			cal.add(Calendar.MONTH, value);
		} else if (CalendarType == Calendar.DATE) {
			cal.add(Calendar.DATE, value);
		} else if (CalendarType == Calendar.HOUR) {
			cal.add(Calendar.HOUR, value);
		} else if (CalendarType == Calendar.MINUTE) {
			cal.add(Calendar.MINUTE, value);
		} else if (CalendarType == Calendar.SECOND) {
			cal.add(Calendar.SECOND, value);
		}
		String y = "" + cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		String m = "" + (month > 9 ? "" + month : "0" + month);
		int day = cal.get(Calendar.DATE);
		String d = "" + (day > 9 ? "" + (day) : "0" + (day));
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		String h = "" + (hour > 9 ? "" + (hour) : "0" + (hour));
		int minute = cal.get(Calendar.MINUTE);
		String mm = "" + (minute > 9 ? "" + (minute) : "0" + (minute));
		int second = cal.get(Calendar.SECOND);
		String s = "" + (second > 9 ? "" + (second) : "0" + (second));
		return y + m + d + h + mm + s;
	}

	public static String getCalculateDate(String dateTime, int value) {

		Calendar cal = new GregorianCalendar();
		try {
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			cal.setTime(df.parse(dateTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		cal.add(Calendar.DATE, value);
		String y = "" + cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		String m = "" + (month > 9 ? "" + month : "0" + month);
		int day = cal.get(Calendar.DATE);
		String d = "" + (day > 9 ? "" + (day) : "0" + (day));
		return y + m + d;
	}

	public static String getCalculateDate(int value) {

		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, value);
		String y = "" + cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		String m = "" + (month > 9 ? "" + month : "0" + month);
		int day = cal.get(Calendar.DATE);
		String d = "" + (day > 9 ? "" + (day) : "0" + (day));
		return y + m + d;
	}

	public static List getDates(String startTime, String endTime) {
		List<String> dates = new ArrayList<String>();
		String _startTime = startTime.substring(0, 8);
		String _endTime = endTime.substring(0, 8);
		if (_startTime.compareTo(_endTime) < 0) {
			int counter = 0;
			while (_startTime.compareTo(_endTime) <= 0) {
				dates.add(_startTime);
				counter++;
				_startTime = getCalculateDate(startTime, counter);
			}
		} else if (_startTime.compareTo(_endTime) == 0) {
			dates.add(_startTime);
		}
		return dates;
	}

	public static long getTime(String startTime) {
		long time = 0;
		SimpleDateFormat d = new SimpleDateFormat("yyyyMMddHHmmss");
		String nowtime = d.format(new Date());
		try {
			time = (d.parse(nowtime).getTime() - d.parse(startTime).getTime()) / 60000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	public static Timestamp stringToTimestamp(String dateStr) {
		Timestamp ts = null;
		try {
			ts = Timestamp.valueOf(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;
	}
	
	public static Timestamp getNextMonth(Timestamp timestamp){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp.getTime());
		calendar.add(Calendar.MONTH, 1);
		timestamp = new Timestamp(calendar.getTimeInMillis());
		return timestamp;
	}
	
	public static Timestamp getPrevMonth(Timestamp timestamp){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp.getTime());
		calendar.add(Calendar.MONTH,-1);
		timestamp = new Timestamp(calendar.getTimeInMillis());
		return timestamp;
	}
	
	public static Timestamp getNextDay(Timestamp timestamp){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp.getTime());
		calendar.add(Calendar.DATE, 1);
		timestamp = new Timestamp(calendar.getTimeInMillis());
		return timestamp;
	}
	
	public static Timestamp getPrevDay(Timestamp timestamp){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp.getTime());
		calendar.add(Calendar.DATE, -1);
		timestamp = new Timestamp(calendar.getTimeInMillis());
		return timestamp;
	}
	
	public static Timestamp getIntervalDay(Timestamp timestamp, int days){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp.getTime());
		calendar.add(Calendar.DATE, days);
		timestamp = new Timestamp(calendar.getTimeInMillis());
		return timestamp;
	}
	
	public static String getFirstDayOfMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		return dateToStr(calendar.getTime(), "yyyy-MM-dd");
		
	}
	
	public static Timestamp stringToTimestamp(String dateStr, String pattern){
		if(Utility.isEmpty(dateStr)){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
		}
		return new Timestamp(date.getTime());
	}

	public static String TimestampToString(Timestamp ts, String pattern) {
		if(ts==null){
			return null;
		}
		String result = "";
		DateFormat sdf = new SimpleDateFormat(pattern);
		try {
			result = sdf.format(ts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String calRemainDay(Date d1, Date d2) {
		long r = (d2.getTime() - d1.getTime()) / (24 * 60 * 60 * 1000);
		return (r < 0) ? "0" : String.valueOf(r);
	}

	public static String daysBetween(Date d1, Date d2) {
		long r = (d2.getTime() - d1.getTime()) / (24 * 60 * 60 * 1000);
		return String.valueOf(r);
	}

	public static String dateToStr(Date date, String fm) {
		SimpleDateFormat f = new SimpleDateFormat(fm);
		String time = f.format(date);
		return time;
	}

	// yyyy-MM-dd hh:mm
	public static Date stringToDate2(String str, String fm) {
		DateFormat format = new SimpleDateFormat(fm);
		Date date = null;
		try {
			date = format.parse(str);

		} catch (ParseException e) {

			e.printStackTrace();
		}
		return date;
	}

	public static Date addMonth(Date d, int i) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(d);
		calender.add(Calendar.MONTH, i);
		return calender.getTime();
	}

	public static Date addDay(Date d, int i) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(d);
		calender.add(Calendar.DAY_OF_MONTH, i);
		return calender.getTime();
	}
	
	public static Date addMins(Date d, int i){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.MINUTE, i);
		return calendar.getTime();
	}

	/**
	 * 鍒ゆ柇褰撳墠鏃ユ湡鏄槦鏈熷嚑
	 * 
	 * @param pTime
	 *            淇鍒ゆ柇鐨勬椂闂�
	 * @return dayForWeek 鍒ゆ柇缁撴灉
	 * @Exception 鍙戠敓寮傚父
	 */
	public static Integer dayForWeek(String pTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Integer dayForWeek = null;
		try {
			c.setTime(format.parse(pTime));
			if (c.get(Calendar.DAY_OF_WEEK) == 1) {
				dayForWeek = 7;
			} else {
				dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dayForWeek;
	}
	
	public static int getIntervalDays(Timestamp beginDate, Timestamp endDate){
		Long beginMillis = beginDate.getTime();
		Long endMillis = endDate.getTime();
		return Integer.valueOf(String.valueOf((endMillis - beginMillis) / (1000*3600*24)));
	}

	/**
	 * 获取现在时间
	 *
	 * @return 返回时间类型 yyyy-MM-dd
	 */
	public static Date getNowDate() {
		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		String dateString = formatter.format(currentDate);
		ParsePosition pos = new ParsePosition(0);
		Date currentDateNew = formatter.parse(dateString, pos);
		return currentDateNew;
	}

	/**
	 * 获取现在时间
	 *
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDateTime() {
		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_FORMAT);
		String dateString = formatter.format(currentDate);
		ParsePosition pos = new ParsePosition(0);
		Date currentDateTime = formatter.parse(dateString, pos);
		return currentDateTime;
	}


	public static void main(String[] args) {
		System.out.println("getTime=="
				+ DateUtil.dateIncrease("20111201235959", "yyyyMMddHHmmss",
						Calendar.SECOND, Integer.parseInt("18")));
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(DateUtil.dateToString(timestamp, "yyyy-MM-dd HH:mm:ss"));
		
		System.out.println(timestamp);
		System.out.println(DateUtil.getPrevMonth(timestamp));
		System.out.println(DateUtil.stringToTimestamp("2012-02-13", "yyyy-MM-dd"));
		
		System.out.println(DateUtil.getCurDateTime(DateUtil.DATETIME_FORMAT));
		

		Timestamp timestamp1 = new Timestamp(1458783304000l);
		System.out.println(DateUtil.dateToString(timestamp1, "yyyy-MM-dd HH:mm:ss"));
		
		String timeString = "145878330486148288";
		System.out.println(DateUtil.getNowDate());
	}

}
