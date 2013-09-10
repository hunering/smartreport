package nc.pub.smp.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SMPUtil {

	public static int getWeeksOfMonth(Date date){
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		int weekOfMonth = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);		//一月里的周数
		return weekOfMonth;
	}
	
	/**
	 * @param date
	 * @param week
	 * @return
	 */
	public static Date getFirsDayOfWeek(Date date,int week){
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		
		Calendar cal2 = (GregorianCalendar) cal.clone(); 
		int weekOfMonth = cal2.getActualMaximum(Calendar.WEEK_OF_MONTH);	//一月里的周数
//		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);		//每周的天数
		cal2.add(Calendar.DATE , week * weekOfMonth);
		return getFirstDayOfWeek(cal2.getTime()); 
	}
	
	public static Date getLastDayOfWeek(Date date,int week){
		Calendar c = new GregorianCalendar(); 
		c.setTime(date);
		c.set(Calendar.DATE, 1);
		
		Calendar cal = (GregorianCalendar) c.clone(); 
		int weekOfMonth = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);	//一月里的周数
//		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);		//每周的天数
		cal.add(Calendar.DATE , week * weekOfMonth);
		return getLastDayOfWeek(cal.getTime()); 
	}
	
	
	/** 
	 * 取得指定日期所在周的第一天 
	 * 
	 * @param date 
	 * @return 
	 */ 
	 public static Date getFirstDayOfWeek(Date date) { 
		 Calendar c = new GregorianCalendar(); 
		 c.setFirstDayOfWeek(Calendar.SATURDAY);		//以周六为起始日
		 c.setTime(date); 
		 c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday 
		 return c.getTime (); 
	 }
	 
	 
	 /** 
	 * 取得指定日期所在周的最后一天 
	 * 
	 * @param date 
	 * @return 
	 */ 
	 public static Date getLastDayOfWeek(Date date) { 
		 Calendar c = new GregorianCalendar(); 
		 c.setFirstDayOfWeek(Calendar.SATURDAY);		//以周六为起始日
		 c.setTime(date); 
		 c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		 return c.getTime(); 
	 } 
	
}
