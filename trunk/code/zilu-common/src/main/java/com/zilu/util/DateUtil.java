package com.zilu.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 获得不带年份的日期窜
	 * @param date
	 * @return
	 */
	public static String getDisplayDate(String date) {
		return date.replaceFirst("\\d{4}-", "");
	}
	
	/**
	 * 获得不带日期，的时间窜
	 * @param date
	 * @return
	 */
	public static String getDisplayTime(String date) {
		return date.replaceFirst("\\d{4}-\\d{2}-\\d{2}\\s", "");
	}
	
	/**
	 * 输入MM-dd获得最近的yyyy-MM-dd
	 * @param time
	 * @return
	 */
	public static String getRecentTime(String time) {
		Date current = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = sdf.format(current);
		String[] dateFlags = dateString.split("-");
		int year = Integer.parseInt(dateFlags[0]);
		int month = Integer.parseInt(dateFlags[1]);
		int date =  Integer.parseInt(dateFlags[2]);
		String mString = time.substring(0, 2);
		String dString = time.substring(2);
		int mt = Integer.parseInt(mString);
		int dt = Integer.parseInt(dString);
		if (mt <= month) {
			if (mt < month || dt < date) {
				year = year + 1;
			}
		}
		return year + "-" + mString + "-" + dString;
	}
	
	/**
	 * 获取当前时间窜
	 * @return
	 */
	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date(System.currentTimeMillis());
		return sdf.format(d);
	}
	
	public static String getCurrentTime14() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date d = new Date(System.currentTimeMillis());
		return sdf.format(d);
	}
	
	/**
	 * 获取当前时间日期窜
	 * @return
	 */
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date(System.currentTimeMillis());
		return sdf.format(d);
	}
	
	/**
	 * 获取指定格式的当前时间窜
	 * @return
	 */
	public static String getCurrentDate(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date d = new Date(System.currentTimeMillis());
		return sdf.format(d);
	}
	
	/**
	 * 把任意符合yyyy-MM-dd HH:mm:ss的窜转换成相应的时间对象
	 * @param dateString
	 * @return
	 */
	public static Date getMatchedDate(String dateString) {
		String dateRegex = "\\d{4}-\\d{2}-\\d{2}";
		String timeRegex = "\\d{2}:\\d{2}:\\d{2}.*";
		String dateTimeRegex = dateRegex + "\\s" + timeRegex;
		if (dateString.matches(dateRegex)) {
			return java.sql.Date.valueOf(dateString);
		} else if (dateString.matches(timeRegex)) {
			return Time.valueOf(dateString);
		} else if (dateString.matches(dateTimeRegex)) {
			return Timestamp.valueOf(dateString);
		} else {
			return null;
		}
	}
	
	public static Date getStringToDate(String dateString,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try{
			return sdf.parse(dateString);
		}catch(ParseException e){
			return null;
		}
	}
	
	/**
	 * 把时间对象转换成相应时间窜
	 * @param date
	 * @return
	 */
	public static String getDateString(Date date) {
		if (date instanceof Timestamp) {
			return getDateTime(date, "yyyy-MM-dd HH:mm:ss");
		}
		else if (date instanceof Time) {
			return getDateTime(date, "HH:mm:ss");
		}
		else if (date instanceof java.sql.Date) {
			return getDateTime(date, "yyyy-MM-dd");
		}
		else {
			return date.toString();
		}
	}
	
	public static String getCurDate10() {
		Calendar cal = Calendar.getInstance();
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String month = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String days = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
		if (month.length() == 1) {
			month = "0" + month;
		}
		days = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
		if (days.length() == 1) {
			days = "0" + days;
		}
		return year+"-"+month+"-"+days;
		
	}
	
	public static String getCurDate8() {
		Calendar cal = Calendar.getInstance();
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String month = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String days = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
		if (month.length() == 1) {
			month = "0" + month;
		}
		days = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
		if (days.length() == 1) {
			days = "0" + days;
		}
		return year+month+days;
		
	}
	public static Date getDateByDate8(String date8) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(date8.substring(0, 4)));
		cal.set(Calendar.MONTH,
				Integer.parseInt(date8.substring(4, 6)) - 1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date8
				.substring(6, 8)));

		return cal.getTime();
	}
	
	public static String getNextMonthFirstDate(String currdate, int i) {
		if(currdate==null) return "";
		if(currdate.length()<8) return currdate;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(currdate.substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.parseInt(currdate.substring(4, 6)) -1 + i);
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt("01"));
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String month = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String days = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
		if (month.length() == 1) {
			month = "0" + month;
		}
		if (days.length() == 1) {
			days = "0" + days;
		}
		return year+month+days;
	}
	
	public static String date10to8(String date10) {
		if(date10==null) return "";
		String date8 = "";
		int y = date10.indexOf("-");
		if (y == -1)
			return "";
		String year = date10.substring(0, y);
		if (year.length() != 4)
			return "";
		int m = date10.indexOf("-", y + 1);
		if (y == -1)
			return "";
		String month = date10.substring(y + 1, m);
		String days = date10.substring(m + 1);
		date8 = year + month + days;

		return date8;
	}
	public static String date8to10(String date8) {
		if(date8==null) return "";
		if(date8.trim().length()<8) return date8;
		String date10 = "";
		String year = date8.substring(0,4);
		String month = date8.substring(4,6);
		String days = date8.substring(6,8);
		date10 = year +"-"+ month +"-"+ days;

		return date10;
	}
	
	public static Date getAdDate(Date currdate, int days) {
		Date myDate =currdate;

		long myTime=(myDate.getTime()/1000)+60*60*24*days; 

		myDate.setTime(myTime*1000); 
		
		return myDate;
	}
	
	public static String getDate8ByDate(Date date) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String month = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String days = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
		if (month.length() == 1) {
			month = "0" + month;
		}
		if (days.length() == 1) {
			days = "0" + days;
		}
		return year+month+days;
	}
	
	public static String getAdDate(String date8, int adddays) {
		Date date = getDateByDate8(date8);
		date = getAdDate(date,adddays);
		return getDate8ByDate(date);
	}
	
	public static String getDateTime(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	public static Date getSToday(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = sdf.format(date);
		return java.sql.Date.valueOf(dateString);
	}
	
	public static boolean isMatch(String date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			Date d = format.parse(date);
			System.out.println(d);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 取得最近的时间 ，比如输入4点，假设当前时间在4点前 则返回今天4点 ，否则返回明天4点
	 * @param time
	 * @return
	 */
	public static Date getRecentHour(int hour) {
		Calendar calendar =	Calendar.getInstance();
		int chour = calendar.get(Calendar.HOUR_OF_DAY);
		if (chour >= hour) {
			calendar.add(Calendar.DATE, 1);
		}
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	
	
	public static void main(String[] args) {
		System.out.println(DateUtil.getDateTime(getRecentHour(23), "yyyy-MM-dd HH:mm:ss"));
	}
}
