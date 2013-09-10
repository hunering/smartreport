/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.vo.smp.coursetype;
	
import nc.vo.pub.*;
import nc.vo.pub.lang.*;
	
/**
 * <b> �ڴ˴���Ҫ��������Ĺ��� </b>
 * <p>
 *     �ڴ˴����Ӵ����������Ϣ
 * </p>
 * ��������:2013-09-09 22:06:04
 * @author Administrator
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class CourseTypeVO extends SuperVO {
	private String pk_corp;
	private String coursetypename;
	private Integer dr;
	private String voperatorid;
	private String pk_coursetype;
	private String ts;
	private String vbusicode;

	public static final String PK_CORP = "pk_corp";
	public static final String COURSETYPENAME = "coursetypename";
	public static final String VOPERATORID = "voperatorid";
	public static final String PK_COURSETYPE = "pk_coursetype";
	public static final String VBUSICODE = "vbusicode";
			
	/**
	 * ����pk_corp��Getter����.
	 * ��������:2013-09-09 22:06:04
	 * @return String
	 */
	public String getPk_corp () {
		return pk_corp;
	}   
	/**
	 * ����pk_corp��Setter����.
	 * ��������:2013-09-09 22:06:04
	 * @param newPk_corp String
	 */
	public void setPk_corp (String newPk_corp ) {
	 	this.pk_corp = newPk_corp;
	} 	  
	/**
	 * ����coursetypename��Getter����.
	 * ��������:2013-09-09 22:06:04
	 * @return String
	 */
	public String getCoursetypename () {
		return coursetypename;
	}   
	/**
	 * ����coursetypename��Setter����.
	 * ��������:2013-09-09 22:06:04
	 * @param newCoursetypename String
	 */
	public void setCoursetypename (String newCoursetypename ) {
	 	this.coursetypename = newCoursetypename;
	} 	  
	/**
	 * ����dr��Getter����.
	 * ��������:2013-09-09 22:06:04
	 * @return Integer
	 */
	public Integer getDr () {
		return dr;
	}   
	/**
	 * ����dr��Setter����.
	 * ��������:2013-09-09 22:06:04
	 * @param newDr Integer
	 */
	public void setDr (Integer newDr ) {
	 	this.dr = newDr;
	} 	  
	/**
	 * ����voperatorid��Getter����.
	 * ��������:2013-09-09 22:06:04
	 * @return String
	 */
	public String getVoperatorid () {
		return voperatorid;
	}   
	/**
	 * ����voperatorid��Setter����.
	 * ��������:2013-09-09 22:06:04
	 * @param newVoperatorid String
	 */
	public void setVoperatorid (String newVoperatorid ) {
	 	this.voperatorid = newVoperatorid;
	} 	  
	/**
	 * ����pk_coursetype��Getter����.
	 * ��������:2013-09-09 22:06:04
	 * @return String
	 */
	public String getPk_coursetype () {
		return pk_coursetype;
	}   
	/**
	 * ����pk_coursetype��Setter����.
	 * ��������:2013-09-09 22:06:04
	 * @param newPk_coursetype String
	 */
	public void setPk_coursetype (String newPk_coursetype ) {
	 	this.pk_coursetype = newPk_coursetype;
	} 	  
	/**
	 * ����ts��Getter����.
	 * ��������:2013-09-09 22:06:04
	 * @return String
	 */
	public String getTs () {
		return ts;
	}   
	/**
	 * ����ts��Setter����.
	 * ��������:2013-09-09 22:06:04
	 * @param newTs String
	 */
	public void setTs (String newTs ) {
	 	this.ts = newTs;
	} 	  
	/**
	 * ����vbusicode��Getter����.
	 * ��������:2013-09-09 22:06:04
	 * @return String
	 */
	public String getVbusicode () {
		return vbusicode;
	}   
	/**
	 * ����vbusicode��Setter����.
	 * ��������:2013-09-09 22:06:04
	 * @param newVbusicode String
	 */
	public void setVbusicode (String newVbusicode ) {
	 	this.vbusicode = newVbusicode;
	} 	  
 
	/**
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:2013-09-09 22:06:04
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:2013-09-09 22:06:04
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_coursetype";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2013-09-09 22:06:04
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "SMP_COURSETYPE";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:2013-09-09 22:06:04
	  */
     public CourseTypeVO() {
		super();	
	}    
} 