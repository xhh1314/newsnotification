package com.haiwai.newsnotification.manage.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * 时间转换工具类  转换localDate 与 Date
 * 使用jdk1.8日期类，线程安全、便捷
 * @author lh
 * @date 2017年10月27日
 * @version 1.0
 */
public class TimeTransfer {
	 /**
	 * 时区
	 */
	private static final ZoneId zoneId = ZoneId.systemDefault();
	
	/**
	 * 2017-10-27 的日期格式
	 */
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);

	
	/**
	 * 把字符串日期转换成 Date对象
	 * @param date
	 * @return Date 
	 */
	public static Date stringToDate(String date){
		return localDateToDate(stringToLocalDate(date)); 
	}
	
	
	/**
	 * LocalDate 转换成Date
	 * @param date
	 * @return LocalDate
	 */
	public static Date localDateToDate(LocalDate date){
		ZonedDateTime zdt = date.atStartOfDay(zoneId);
		return Date.from(zdt.toInstant());
	}
	
	/**
	 * Date 转换成LocalDate
	 * @param date
	 * @return
	 */
	public static LocalDate dateToLocalDate(Date date){
		if(date instanceof java.sql.Date) {
			date=new java.util.Date(date.getTime());
		}
		Instant instant = date.toInstant();
		return instant.atZone(zoneId).toLocalDate();
		
	}
	
	/**
	 * 把字符串日期转换成 LocalDate对象
	 * @param date
	 * @return
	 */
	public static LocalDate stringToLocalDate(String date){
		return LocalDate.parse(date,formatter);
	}
	
	

}
