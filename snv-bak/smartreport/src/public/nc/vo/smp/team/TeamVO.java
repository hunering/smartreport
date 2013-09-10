/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.vo.smp.team;
	
import nc.vo.pub.*;
import nc.vo.pub.lang.*;
	
/**
 * <b> �ڴ˴���Ҫ��������Ĺ��� </b>
 * <p>
 *     �ڴ˴����Ӵ����������Ϣ
 * </p>
 * ��������:2013-09-09 22:06:37
 * @author Administrator
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class TeamVO extends SuperVO {
	private String pk_corp;
	private String pk_director;
	private Integer dr;
	private String voperatorid;
	private String team_director_name;
	private String team_name;
	private String ts;
	private String pk_team;

	public static final String PK_CORP = "pk_corp";
	public static final String PK_DIRECTOR = "pk_director";
	public static final String VOPERATORID = "voperatorid";
	public static final String TEAM_DIRECTOR_NAME = "team_director_name";
	public static final String TEAM_NAME = "team_name";
	public static final String PK_TEAM = "pk_team";
			
	/**
	 * ����pk_corp��Getter����.
	 * ��������:2013-09-09 22:06:37
	 * @return String
	 */
	public String getPk_corp () {
		return pk_corp;
	}   
	/**
	 * ����pk_corp��Setter����.
	 * ��������:2013-09-09 22:06:37
	 * @param newPk_corp String
	 */
	public void setPk_corp (String newPk_corp ) {
	 	this.pk_corp = newPk_corp;
	} 	  
	/**
	 * ����pk_director��Getter����.
	 * ��������:2013-09-09 22:06:37
	 * @return String
	 */
	public String getPk_director () {
		return pk_director;
	}   
	/**
	 * ����pk_director��Setter����.
	 * ��������:2013-09-09 22:06:37
	 * @param newPk_director String
	 */
	public void setPk_director (String newPk_director ) {
	 	this.pk_director = newPk_director;
	} 	  
	/**
	 * ����dr��Getter����.
	 * ��������:2013-09-09 22:06:37
	 * @return Integer
	 */
	public Integer getDr () {
		return dr;
	}   
	/**
	 * ����dr��Setter����.
	 * ��������:2013-09-09 22:06:37
	 * @param newDr Integer
	 */
	public void setDr (Integer newDr ) {
	 	this.dr = newDr;
	} 	  
	/**
	 * ����voperatorid��Getter����.
	 * ��������:2013-09-09 22:06:37
	 * @return String
	 */
	public String getVoperatorid () {
		return voperatorid;
	}   
	/**
	 * ����voperatorid��Setter����.
	 * ��������:2013-09-09 22:06:37
	 * @param newVoperatorid String
	 */
	public void setVoperatorid (String newVoperatorid ) {
	 	this.voperatorid = newVoperatorid;
	} 	  
	/**
	 * ����team_director_name��Getter����.
	 * ��������:2013-09-09 22:06:37
	 * @return String
	 */
	public String getTeam_director_name () {
		return team_director_name;
	}   
	/**
	 * ����team_director_name��Setter����.
	 * ��������:2013-09-09 22:06:37
	 * @param newTeam_director_name String
	 */
	public void setTeam_director_name (String newTeam_director_name ) {
	 	this.team_director_name = newTeam_director_name;
	} 	  
	/**
	 * ����team_name��Getter����.
	 * ��������:2013-09-09 22:06:37
	 * @return String
	 */
	public String getTeam_name () {
		return team_name;
	}   
	/**
	 * ����team_name��Setter����.
	 * ��������:2013-09-09 22:06:37
	 * @param newTeam_name String
	 */
	public void setTeam_name (String newTeam_name ) {
	 	this.team_name = newTeam_name;
	} 	  
	/**
	 * ����ts��Getter����.
	 * ��������:2013-09-09 22:06:37
	 * @return String
	 */
	public String getTs () {
		return ts;
	}   
	/**
	 * ����ts��Setter����.
	 * ��������:2013-09-09 22:06:37
	 * @param newTs String
	 */
	public void setTs (String newTs ) {
	 	this.ts = newTs;
	} 	  
	/**
	 * ����pk_team��Getter����.
	 * ��������:2013-09-09 22:06:37
	 * @return String
	 */
	public String getPk_team () {
		return pk_team;
	}   
	/**
	 * ����pk_team��Setter����.
	 * ��������:2013-09-09 22:06:37
	 * @param newPk_team String
	 */
	public void setPk_team (String newPk_team ) {
	 	this.pk_team = newPk_team;
	} 	  
 
	/**
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:2013-09-09 22:06:37
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:2013-09-09 22:06:37
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_team";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2013-09-09 22:06:37
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "SMP_TEAM";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:2013-09-09 22:06:37
	  */
     public TeamVO() {
		super();	
	}    
} 