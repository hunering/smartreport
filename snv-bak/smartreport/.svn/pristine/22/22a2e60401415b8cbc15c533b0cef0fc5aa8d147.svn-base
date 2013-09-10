package nc.vo.smp.report.detail;

import java.util.HashMap;
import java.util.Vector;

import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFDouble;

/**
 * 明细报表VO
 * @author LINQI
 *
 */
public class DetailReportVO extends CircularlyAccessibleValueObject implements Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private HashMap values;
	 private Vector fields;

	private String achievetype;		//业绩类型
	private UFDouble realitynum;	//本月实际
	private UFDouble nextbudget;	//下月预算
	private UFDouble budget;		//本月预算
	private String vfree1;	//
	private String vfree2;	//
	private String vfree3;	//
	private String vfree4;	//
	private String vfree5;	//
	
	public DetailReportVO(){
		values = new HashMap();
        fields = new Vector();
	}
	
	public String getAchievetype() {
		return achievetype;
	}

	public void setAchievetype(String achievetype) {
		this.achievetype = achievetype;
	}

	public UFDouble getRealitynum() {
		return realitynum;
	}

	public void setRealitynum(UFDouble realitynum) {
		this.realitynum = realitynum;
	}

	public UFDouble getNextbudget() {
		return nextbudget;
	}

	public void setNextbudget(UFDouble nextbudget) {
		this.nextbudget = nextbudget;
	}

	public UFDouble getBudget() {
		return budget;
	}

	public void setBudget(UFDouble budget) {
		this.budget = budget;
	}

	public String getVfree1() {
		return vfree1;
	}

	public void setVfree1(String vfree1) {
		this.vfree1 = vfree1;
	}

	public String getVfree2() {
		return vfree2;
	}

	public void setVfree2(String vfree2) {
		this.vfree2 = vfree2;
	}

	public String getVfree3() {
		return vfree3;
	}

	public void setVfree3(String vfree3) {
		this.vfree3 = vfree3;
	}

	public String getVfree4() {
		return vfree4;
	}

	public void setVfree4(String vfree4) {
		this.vfree4 = vfree4;
	}

	public String getVfree5() {
		return vfree5;
	}

	public void setVfree5(String vfree5) {
		this.vfree5 = vfree5;
	}

	@Override
	public String[] getAttributeNames() {
		// TODO Auto-generated method stub
		return (String[])(String[])fields.toArray(new String[0]);
	}

	@Override
	public Object getAttributeValue(String attributeName) {
		// TODO Auto-generated method stub
		return values.get(attributeName);
	}

	@Override
	public void setAttributeValue(String name, Object value) {
		// TODO Auto-generated method stub
		 if(name != null){
			 if(value != null){
				 values.put(name, value);
				 if(!fields.contains(name))
					 fields.addElement(name);
			 } else{
				 values.remove(name);
			 }
		 }
	}

	@Override
	public String getEntityName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate() throws ValidationException {
		// TODO Auto-generated method stub
		
	}

}
