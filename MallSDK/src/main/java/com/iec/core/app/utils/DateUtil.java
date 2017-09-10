package com.iec.core.app.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * 时间工具类
 * @author xyp
 *
 */
public class DateUtil {
	
	/**
	 * 年月日 140411
	 * 格式：yyMMdd
	 */
	public static String YY_MM_DD = "yyMMdd";
	
	/**
	 * 时分秒毫秒 110200333
	 * 格式：HHmmssSSS
	 */
	public static String HH_MM_SS_SSS = "HHmmssSSS";
	
	/**
	 * 时分秒 164747
	 * 格式：HHmmss
	 */
	public static String HH_MM_SS = "HHmmss";
	
	
	/**
	 * 格式：yyyy
	 */
	public static String YYYY = "yyyy";
	
	/**
	 * 格式：yyyy/MM/dd
	 */
	public static String YYYY_MM_DD = "yyyy/MM/dd";
	
	
	/**
	 * 格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 格式：dd/MM/yy
	 */
	public static String DD_MM_YY = "dd/MM/yy";
	
	/**
	 * 格式：yyyyMMdd
	 */
	public static String YYYYMMDD = "yyyyMMdd";
	
	/**
	 * 格式：MM/dd/yy
	 */
	public static String MM_DD_YY = "MM/dd/yy";
	
	/**
	 * 格式：yyyy-MM-dd
	 */
	public static String _YYYY_MM_DD = "yyyy-MM-dd";
	
	
	public static String YYYYMMDDHHMMSS="yyyyMMddHHmmss";
	
	 /**  
     * 取得当前日期的年份，以yyyy格式返回. 
     *   
     * @return 当年 yyyy  
     */    
    public static String getCurrentYear() {   
        return getFormatCurrentTime(YYYY);    
    }  
    

    /** 
     * 根据给定的格式，返回时间字符串。 
     * <p> 
     * 格式参照类描绘中说明.和方法getFormatDate是一样的。 
     *  
     * @param format 
     *            日期格式字符串 
     * @return String 指定格式的日期字符串. 
     */  
  
    public static String getFormatCurrentTime(String format) {  
        return getFormatDateTime(new Date(), format);  
    } 
    
	  /** 
     * 判断一个日期是否在开始日期和结束日期之间。 
     * @param srcDate 
     *            目标日期 YYYY_MM_DD 或者 yyyy-MM-dd 
     * @param startDate 
     *            开始日期 YYYY_MM_DD 或者 yyyy-MM-dd 
     * @param endDate 
     *            结束日期 YYYY_MM_DD 或者 yyyy-MM-dd 
 
     * @return 大于等于开始日期小于等于结束日期，那么返回true，否则返回false 
     */  
  
    public static boolean isInStartEnd(String srcDate, String startDate,String endDate) {  
        if (startDate.compareTo(srcDate) <= 0 && endDate.compareTo(srcDate) >= 0) {  
            return true;  
        } else {  
            return false;  
        }  
    } 
    
	 /** 	 
     * 根据给定的格式与时间(Date类型的)，返回时间字符串。
     *   
     * @param date  
     *            指定的日期  
     * @param format  
     *            日期格式字符串  
     * @return String 指定格式的日期字符串.
     *   
     */	
    public static String getFormatDateTime(Date date, String format) {    
        SimpleDateFormat sdf = new SimpleDateFormat(format);    
        return sdf.format(date);    
    }  
    
    /** 
     * 获取与指定日期相差指定天数的日期。 
     *  
     * @param date 
     *            YYYY_MM_DD 
     * @param offset 
     *            正整数 
     * @return YYYY_MM_DD 
     */  
    public static String getDateOffset(String date,int offset) {  
        Date tempDate = null;  
        if(date.indexOf("/")>0)  
        tempDate = getDateObj(date, "[/]");  
        if(date.indexOf("-")>0)  
            tempDate = getDateObj(date, "[-]");       
        tempDate = getDateAdd(tempDate, offset);  
        return getFormatDateTime(tempDate,YYYY_MM_DD );  
  
    }  
    
    /** 
     * 根据主机的默认 TimeZone，来获得指定形式的时间字符串。 
     *  
     * @param dateFormat 
     * @return 返回日期字符串，形式和formcat一致。 
     */  
    public static String getCurrentDateString(String dateFormat) {  
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());  
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);  
        sdf.setTimeZone(TimeZone.getDefault());  
        return sdf.format(cal.getTime());  
    }  
    
    /** 
     * 返回当前指定的时间戳。格式为yyyy-MM-dd HH:mm:ss 
     * @return 格式为yyyy-MM-dd HH:mm:ss，总共19位。 
     */  
    public static String getCurrentDateTime() {  
        return getFormatDateTime(new Date(), YYYY_MM_DD_HH_MM_SS);  
    }  
    /** 
     * 取得指定分隔符分割的年月日的日期对象. 
     *  
     * @param argsDate 
     *            格式为"yyyy-MM-dd" 
     * @param split 
     *            时间格式的间隔符，例如“-”，“/”，要和时间一致起来。 
     * @return 一个java.util.Date()类型的对象 
     */  
    public static Date getDateObj(String argsDate, String split) {  
    	String[] temp = argsDate.split(split);  
        int year = new Integer(temp[0]).intValue();  
        int month = new Integer(temp[1]).intValue();  
        int day = new Integer(temp[2]).intValue();  
        return getDateObj(year, month, day);   
    }  
    

    /** 
     * 取得当前Date对象. 
     *  
     * @return Date 当前Date对象. 
     */  
    public static Date getDateObj() {  
        Calendar c = new GregorianCalendar();  
        return c.getTime();  
    }  
    
    /** 
     * 取得指定年月日的日期对象. 
     * @param year 
     *            年 
     * @param month 
     *            月注意是从1到12 
     * @param day 
     *            日 
     * @return 一个java.util.Date()类型的对象 
     */  
    public static Date getDateObj(int year, int month, int day) {  
        Calendar c = new GregorianCalendar();  
        c.set(year, month - 1, day);  
        return c.getTime();  
    } 
    
    /** 
     * 取得给定日期加上一定天数后的日期对象. 
     *  
     * @param date 
     *            给定的日期对象 
     * @param amount 
     *            需要添加的天数，如果是向前的天数，使用负数就可以. 
     * @return Date 加上一定天数以后的Date对象. 
     */  
    public static Date getDateAdd(Date date, int amount) {  
        Calendar cal = new GregorianCalendar();  
        cal.setTime(date);  
        cal.add(GregorianCalendar.DATE, amount);  
        return cal.getTime();  
    }  
    
	public static String stringDate2StringDate111(String date) throws ParseException{
		SimpleDateFormat d1=new SimpleDateFormat(DD_MM_YY);
		SimpleDateFormat d2=new SimpleDateFormat(YY_MM_DD);
		return d2.format(d1.parse(date));
	}
	/**
	 * 当前日期
	 * @return yyyyMMdd
	 */
	public static String getTodayDate2() {
		// 初始化时间
		Calendar RightNow = Calendar.getInstance();
		return changeDatetoString2(RightNow);
	}
	
	/**
	 * 当前日期
	 * @return yyyy-MM-dd
	 */
	public static String getTodayDate() {
		// 初始化时间
		Calendar RightNow = Calendar.getInstance();
		return changeDatetoString(RightNow);
	}
	
	/**
	 * 当前日期
	 * @return yyyy-MM-dd hh:mm:ss,SSS
	 */
	public static String getTodayDate3() {
		// 初始化时间
		return DateUtil.changeLongtoString(DateUtil.getTodayDateLong());
	}
	
	
	public static Long getTodayDateLong() {
		// 初始化时间
		Calendar RightNow = Calendar.getInstance();
		return RightNow.getTimeInMillis();
	}
	
	public static Date xmlDateToDate(XMLGregorianCalendar xmlCalendar){
		GregorianCalendar calendar =xmlCalendar.toGregorianCalendar();
		return calendar.getTime();
	}
	
	public static Date getDateByLong(Long time){
		Date date = new Date();
		date.setTime(time);
		return date;
	}
	
	/** 
	* 用来获日期前后若干天的日期 
	* @param cycleNum  要去前若干天传负整数，后若干天传整数
	* @param jsDate 相对日期（20080303）
	*/ 
	public static String getJsDate2(int cycleNum,String sDate) {
		sDate = zhihuan(sDate, YYYY_MM_DD, YYYYMMDD);
		return getJsDate(cycleNum, sDate);
	}
	
	
	public static String zhihuan(String sDate,String newCss,String oldCss){
		SimpleDateFormat ft = new SimpleDateFormat(newCss); 
		SimpleDateFormat ft1 = new SimpleDateFormat(oldCss); 
		try {
			return ft.format(ft1.parse(sDate));
		} catch (ParseException e) {
			return "";
		}
	}
	/** 
	* 用来获日期前后若干天的日期 
	* @param cycleNum  要去前若干天传负整数，后若干天传整数
	* @param jsDate 相对日期（2008/03/03）
	*/ 
	@SuppressWarnings("deprecation")
	public static String getJsDate(int cycleNum,String jsDate) {
		// 初始化时间
		Calendar RightNow = Calendar.getInstance();
		RightNow.setTime(new Date(jsDate));
		RightNow.add(Calendar.DATE, cycleNum);
		return changeDatetoString2(RightNow);
	}
	
	
	// 取得当前日期前后若干天的日期,格式yyyymmdd
	public static String getDate2(int day) {
		// 初始化时间

		Calendar RightNow = Calendar.getInstance();
		RightNow.add(Calendar.DATE, day);
		return changeDatetoString2(RightNow);
	}
	
	// 取得当前日期前后若干天的日期,格式yyyy-mm-dd
	public static String getDate(int day) {
		// 初始化时间

		Calendar RightNow = Calendar.getInstance();
		RightNow.add(Calendar.DATE, day);
		return changeDatetoString(RightNow);
	}
	
	/**
	 * 取得现在的日期，格式yyyymmddhhmmss
	 */
	@SuppressWarnings("static-access")
	public static String getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(cal.YEAR);
		int month = cal.get(cal.MONTH) + 1;
		int day = cal.get(cal.DAY_OF_MONTH);
		int hour = cal.get(cal.HOUR_OF_DAY);
		int minute = cal.get(cal.MINUTE);
		int second = cal.get(cal.SECOND);
		String cDate = Integer.toString(year);
		if (month < 10) {
			cDate = cDate + "0" + Integer.toString(month);
		} else {
			cDate = cDate + Integer.toString(month);
		}
		if (day < 10) {
			cDate = cDate + "0" + Integer.toString(day);
		} else {
			cDate = cDate + Integer.toString(day);
		}
		if (hour < 10) {
			cDate = cDate + "0" + Integer.toString(hour);
		} else {
			cDate = cDate + Integer.toString(hour);
		}
		if (minute < 10) {
			cDate = cDate + "0" + Integer.toString(minute);
		} else {
			cDate = cDate + Integer.toString(minute);
		}
		if (second < 10) {
			cDate = cDate + "0" + Integer.toString(second);
		} else {
			cDate = cDate + Integer.toString(second);
		}
		return cDate.trim();
	}
	
	
	public static String stringDate2StringDate(String date) throws ParseException{
		SimpleDateFormat d1=new SimpleDateFormat(MM_DD_YY);
		SimpleDateFormat d2=new SimpleDateFormat(YY_MM_DD);
		return d2.format(d1.parse(date));
	}
	
	/**
	 * 获取月份的天数
	 * @param year
	 * @param month
	 * @return
	 */
	 public static int getDay(int year ,int month){
		Calendar time=Calendar.getInstance(); 
		time.clear(); 
		time.set(Calendar.YEAR,year); 
		time.set(Calendar.MONTH,month-1);//Calendar对象默认一月为0            
		int day=time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
		return day;
	 }
	 public static String addMonthAndDay(int changeMon,int changeDay) {
			String str = "";
			SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);

			Calendar lastDate = Calendar.getInstance();
			lastDate.add(Calendar.MONTH, changeMon);// 减一个月
			lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
			lastDate.roll(Calendar.DATE, changeDay);// 日期回滚一天，也就是本月最后一天
			str = sdf.format(lastDate.getTime());
			return str;
		}
	 public static String addMonthAndDay(String proc_date,int changeMon,int changeDay) {
			String str = "";
			SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
			SimpleDateFormat sdf1 = new SimpleDateFormat(YYYYMMDD);
			Calendar lastDate = Calendar.getInstance();
			try {
				lastDate.setTime(sdf.parse(proc_date));
				lastDate.add(Calendar.MONTH, changeMon);// 减一个月
				lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
				lastDate.roll(Calendar.DATE, changeDay);// 日期回滚一天，也就是本月最后一天
				str = sdf1.format(lastDate.getTime());
				return str;
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
	 
	 /**
	  * 将格式为20021212080000的日期转换成2002-12-12 08:00:00时
	  */
		public static String customDateTimeFormat(String dateTime) {
			String year, month, day, hour, Second, Minute;

			year = dateTime.substring(0, 4);
			month = dateTime.substring(4, 6);
			day = dateTime.substring(6, 8);
			hour = dateTime.substring(8, 10);
			Minute = dateTime.substring(10, 12);
			Second = dateTime.substring(12, 14);
			String str = year + "-" + month + "-" + day + " " + hour + ":" + Minute
					+ ":" + Second;
			return str;
		}
		
		// 将日期转换成字符串,格式yyyy-mm-dd
		public static String changeDatetoString(Calendar cDate) {
			int Year;
			int Month;
			int Day;
			String sDate = "";

			// 初始化时间

			Year = cDate.get(Calendar.YEAR);
			Month = cDate.get(Calendar.MONTH) + 1;
			Day = cDate.get(Calendar.DAY_OF_MONTH);

			sDate = Integer.toString(Year) + "-";
			if (Month >= 10) {
				sDate = sDate + Integer.toString(Month) + "-";
			} else {
				sDate = sDate + "0" + Integer.toString(Month) + "-";
			}
			if (Day >= 10) {
				sDate = sDate + Integer.toString(Day);
			} else {
				sDate = sDate + "0" + Integer.toString(Day);
			}
			return sDate;
		}
		
		// 将LONG毫秒转换成字符串,格式yyyy-MM-dd hh:mm:ss,SSS
		public static String changeLongtoString(long ms) {
			
			Date date = new Date();
			date.setTime(ms);
			
			SimpleDateFormat sdf=new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
			String res = sdf.format(date);
			return res;
		}
		
		// 将LONG毫秒转换成字符串,格式yyyy-MM-dd hh:mm:ss,SSS
		public static String changeDatetoString(Date date) {
			if(date==null) return null ;
			SimpleDateFormat sdf=new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
			String res = sdf.format(date);
			return res;
		}
		
		
		
		// 将格式yyyy-MM-dd hh:mm:ss,SSS 转换成 LONG毫秒
		public static long changeStringtoLong(String formatDate) {
			try{
				SimpleDateFormat sdf=new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
				
				Date d = sdf.parse(formatDate);
				long l = d.getTime();
				
				return l;
			}catch(Exception ex){
				return 0;
			}
			
			
		}
		
		// 将格式yyyy-MM-dd hh:mm:ss 转换成 LONG毫秒
		public static long changeStringtoLong2(String formatDate) {
			try{
				SimpleDateFormat sdf=new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
				
				Date d = sdf.parse(formatDate);
				long l = d.getTime();
				
				return l;
			}catch(Exception ex){
				return 0;
			}
			
			
		}
		
		
		/**
		 * 当前日期
		 * @return yyyyMMdd
		 */
		public static String changeDatetoString2(Calendar cDate) {
			int Year;
			int Month;
			int Day;
			String sDate = "";
			// 初始化时间
			Year = cDate.get(Calendar.YEAR);
			Month = cDate.get(Calendar.MONTH) + 1;
			Day = cDate.get(Calendar.DAY_OF_MONTH);
			sDate = Integer.toString(Year);
			if (Month >= 10) {
				sDate = sDate + Integer.toString(Month);
			} else {
				sDate = sDate + "0" + Integer.toString(Month);
			}
			if (Day >= 10) {
				sDate = sDate + Integer.toString(Day);
			} else {
				sDate = sDate + "0" + Integer.toString(Day);
			}
			return sDate;
		}
		
		/**
	     * 把字符串转换成指定的日期格式
	     * @author ex_kjkfb_yanjf
	     * @param str
	     * @param format
	     * @return
	     */
		public static Date convert(String str, String format) {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format,
					Locale.CHINA);
			try {
				java.util.Date d = sdf.parse(str);
				return d;
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
		
		/** 
	     * 根据给定的格式，返回时间字符串。 和getFormatDate(String format)相似。 
	     *  
	     * @param format 
	     *            yyyy MM dd hh mm ss 
	     * @return 返回一个时间字符串 
	     */  
	    public static String getCurTimeByFormat(String format) {  
	        Date newdate = new Date(System.currentTimeMillis());  
	        SimpleDateFormat sdf = new SimpleDateFormat(format);  
	        return sdf.format(newdate);  
	    } 
		
		/**
		 * 判断当前时间是否在截止时间 之前
		 * @author ex_kjkfb_yanjf
		 * @since 2014/3/25
		 * @param endDate
		 * @param currentDate
		 * @return
		 */
		public static boolean isBefore(Date endDate,Date currentDate){
			return currentDate.before(endDate);
		}
		
		/**
		 * 判断当前时间是否在截止时间 之后
		 * @author ex_kjkfb_yanjf
		 * @since 2014/3/25
		 * @param endDate
		 * @param currentDate
		 * @return
		 */
		public static boolean isAfter(Date currentDate, Date startDate){
			return currentDate.after(startDate);
		}

		 /**
	     * 根据所给的起止时间来计算间隔的天数
	     * 
	     * @param startDate
	     *            起始时间
	     * @param endDate
	     *            结束时间
	     * @return int 返回间隔天数
	     */
	    public static int getIntervalDays(Date startDate,Date endDate) {
			long startdate = startDate.getTime();
			long enddate = endDate.getTime();
			long interval = enddate - startdate;
			int intervalday = (int) (interval / (1000 * 60 * 60 * 24));
			return intervalday;
	    }

	    /**
	     * 根据所给的起止时间来计算间隔的月数
	     * 
	     * @param startDate
	     *            起始时间
	     * @param endDate
	     *            结束时间
	     * @return int 返回间隔月数
	     */
	    @SuppressWarnings("static-access")
		public static int getIntervalMonths(Date startDate, Date endDate) {
			Calendar startCal = Calendar.getInstance();
			startCal.setTime(startDate);
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(endDate);
			int startDateM = startCal.MONTH;
			int startDateY = startCal.YEAR;
			int enddatem = endCal.MONTH;
			int enddatey = endCal.YEAR;
			int interval = ((enddatey * 12) + enddatem) - ((startDateY * 12) + startDateM);
			return interval;
	    }

	    /**
	     * 返回当前日期时间字符串<br>
	     * 默认格式:yyyy-mm-dd hh:mm:ss
	     * 
	     * @return String 返回当前字符串型日期时间
	     */
	    public static String getCurrentTime() {
			String returnStr = null;
			SimpleDateFormat f = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
			Date date = new Date();
			returnStr = f.format(date);
			return returnStr;
	    }

	    /**
	     * 返回自定义格式的当前日期时间字符串
	     * 
	     * @param format
	     *            格式规则
	     * @return String 返回当前字符串型日期时间
	     */
	    public static String getCurrentTime(String format) {
			String returnStr = null;
			SimpleDateFormat f = new SimpleDateFormat(format);
			Date date = new Date();
			returnStr = f.format(date);
			return returnStr;
	    }

	    /**
	     * 返回当前字符串型日期
	     * 
	     * @return String 返回的字符串型日期
	     */
	    public static String getCurDate() {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat simpledateformat = new SimpleDateFormat(_YYYY_MM_DD);
			String strDate = simpledateformat.format(calendar.getTime());
			return strDate;
	    }

	    /**
	     * 返回TimeStamp对象
	     * 
	     * @return
	     * @throws Exception
	     */
	    public static Timestamp getCurrentTimestamp() throws Exception {
			Object obj = TypeConvertHelper.convert(getCurrentTime(), "Timestamp",YYYY_MM_DD_HH_MM_SS);
			if (obj != null) {
			    return (Timestamp) obj;
			} else {
			    return null;
			}
	    }

	    /**
	     * 将字符串型日期转换为日期型
	     * 
	     * @param strDate
	     *            字符串型日期
	     * @param srcDateFormat
	     *            源日期格式
	     * @param dstDateFormat
	     *            目标日期格式
	     * @return Date 返回的util.Date型日期
	     */
	    public static Date stringToDate(String strDate, String srcDateFormat,String dstDateFormat) {
			Date rtDate = null;
			Date tmpDate = (new SimpleDateFormat(srcDateFormat)).parse(strDate,
				new ParsePosition(0));
			String tmpString = null;
			if (tmpDate != null) {
			    tmpString = (new SimpleDateFormat(dstDateFormat)).format(tmpDate);
			}
			if (tmpString != null) {
			    rtDate = (new SimpleDateFormat(dstDateFormat)).parse(tmpString,
				    new ParsePosition(0));
			}
			return rtDate;
	    }
		
	    /**
	     * 根据日期获取星期
	     * 
	     * @param strdate
	     * @return
	     */
	    public static String getWeekDayByDate(String strdate) {
			final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
			SimpleDateFormat sdfInput = new SimpleDateFormat(_YYYY_MM_DD);
			Calendar calendar = Calendar.getInstance();
			Date date = new Date();
			try {
			    date = sdfInput.parse(strdate);
			} catch (ParseException e) {
			    e.printStackTrace();
			}
			calendar.setTime(date);
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			if (dayOfWeek < 0) {
			    dayOfWeek = 0;
			}
			return dayNames[dayOfWeek];
	    }
	    
	    
	/**
	 * 获取某年第一天日期
	 * @param year 年份
	 * @return Date
	 */
	public static Date getCurrYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取某年最后一天日期
	 * @param year 年份
	 * @return Date
	 */
	public static Date getCurrYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return currYearLast;
	}
	    
	    
//		
//	 public static void main(String[] args) {
//		 //System.out.println(addMonthAndDay("2011/08/02",-2,1));
//		 //System.out.println("当前日期 yyyyMMdd = "+DateUtil.getTodayDate2());
//		 //System.out.println("当前日期 yyyy-MM-dd hh:mm:ss,SSS  = "+DateUtil.getTodayDate3());
//		 //System.out.println("当前日期 = "+DateUtil.changeDatetoString2(Calendar.getInstance()));
//		 //System.out.println();
//		 
//		 //System.out.println("获当前日期后若干天的日期 = "+DateUtil.getDate(-1));
//		 //System.out.println("获当前日期后若干天的日期 = "+DateUtil.getDate(1));
//		 //System.out.println();
//		 
//		 
//		 //System.out.println("获日期前后若干天的日期 = "+DateUtil.getJsDate(-1, "2011/08/02"));
//		 //System.out.println("获日期前后若干天的日期 = "+DateUtil.getJsDate(1, "2011/08/02"));
//		 //System.out.println("获日期前后若干天的日期 = "+DateUtil.getJsDate2(-1, "20110802"));
//		 //System.out.println("获日期前后若干天的日期 = "+DateUtil.getJsDate2(1, "20110802"));
//		 //System.out.println();
//		 
//	
//		 //System.out.println("当前时间 = "+DateUtil.getCurrentDate());
//		 //System.out.println("月份天数 = "+DateUtil.getDay(2011, 2));
//		 //System.out.println();
//		 
//		 //System.out.println("转换时间格式 = "+DateUtil.customDateTimeFormat("20110802111846"));
//		 
//		 long l = 1312368059406L;
//		 //System.out.println("毫秒转换成字符串 = "+DateUtil.changeLongtoString(l));
//		 
//		 String s = "2011-08-03 06:36:34,703";
//		 //System.out.println("毫秒字符串转换成long = "+DateUtil.changeStringtoLong(s));
//		 
//		 String ss = "2011-08-03 06:36:34";
//		 //System.out.println("秒字符串转换成long = "+DateUtil.changeStringtoLong2(ss));
//	}
}
