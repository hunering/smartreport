package lin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import nc.vo.pub.lang.UFDate;

public class Test {

	public static void main(String[] args){
		
		UFDate date = new UFDate("2013-01-01");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.SATURDAY);		//以周六为起始日
		cal.setTime(date.toDate());
		
//		int weekOfMonth = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);		//一月里的周数
//		System.out.println(cal.getActualMaximum(Calendar.WEEK_OF_MONTH));
//		
//		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);		//每周的天数
//		Calendar firstDayInThisWeek = (Calendar)cal.clone();
//		firstDayInThisWeek.add(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_WEEK)-dayOfWeek);
//		Calendar lastDayInThisWeek = (Calendar)cal.clone();
//		lastDayInThisWeek.add(Calendar.DATE,cal.getActualMaximum(Calendar.DAY_OF_WEEK)-dayOfWeek);
		
//		String firstDate = "";
//		String lastDate = "";
//		if(firstDayInThisWeek.getTime().before(date.toDate())){
//			firstDate = date.toString();
//		}else{
//			firstDate = sdf.format(firstDayInThisWeek.getTime());
//		}
//		if(lastDayInThisWeek.getTime().after(when)){
//			
//		}
//		System.out.println(+"==="+sdf.format(lastDayInThisWeek.getTime()));
		
		
		Date date1 = getFirsDayOfWeek(date.toDate(),5);
		Date date2 = getLastDayOfWeek(date.toDate(),5);
		System.out.println(sdf.format(date1)+"——"+sdf.format(date2));
		
		
	}
	
	private static Date getFirsDayOfWeek(Date date,int week){
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.set(Calendar.DATE, 1);
		
		Calendar cal = (GregorianCalendar) c.clone(); 
//		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);		//每周的天数
		int weekOfMonth = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);	//一月里的周数
		cal.add(Calendar.DATE , week * weekOfMonth);
		return getFirstDayOfWeek(cal.getTime()); 
	}
	
	private static Date getLastDayOfWeek(Date date,int week){
		Calendar c = new GregorianCalendar(); 
		c.setTime(date);
		c.set(Calendar.DATE, 1);
		
		Calendar cal = (GregorianCalendar) c.clone(); 
//		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);		//每周的天数
		int weekOfMonth = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);	//一月里的周数
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
		 System.out.println(">>>>>>>>>>>>>>>"+c.getFirstDayOfWeek());
		 c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday 
		 return c.getTime(); 
	 } 
	
}
