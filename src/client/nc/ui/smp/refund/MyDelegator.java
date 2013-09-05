package nc.ui.smp.refund;
import java.util.Collection;
import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.BaseProcessor;
import nc.vo.smp.parter.*;
import nc.vo.smp.team.TeamVO;
import nc.vo.pub.*;
/**
  *
  *����ҵ��������ȱʡʵ��
  *@author author
  *@version tempProject version
  */
public class MyDelegator extends AbstractMyDelegator{

 /**
   *
   *
   *�÷������ڻ�ȡ��ѯ�������û����Զ�������޸ġ�
   *
   */
 public String getBodyCondition(Class bodyClass,String key){
   return super.getBodyCondition(bodyClass,key);
 }
 public String getPKTeamByPKPartern(String pkParter){
	 IUAPQueryBS uapQry = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
	 Collection results=null;
	 String pk_team=null;
	try {
		 
		results= uapQry.retrieveByClause(ParterVO.class, "pk_parter='"+pkParter+"'");
	}  catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 if(results!=null&&results.size()>=1){
		ParterVO parterVO=(ParterVO)results.iterator().next();
		pk_team=parterVO.getPk_team();
	 }
	return pk_team;
 }
 public String getPKDirectorByPKTeam(String pkTeam){
	 IUAPQueryBS uapQry = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
	 Collection results=null;
	 String pk_director=null;
	try {
		 
		results= uapQry.retrieveByClause(TeamVO.class, "pk_team='"+pkTeam+"'");
	}  catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 if(results!=null&&results.size()>=1){
		 TeamVO teamVO=(TeamVO)results.iterator().next();
		 pk_director=teamVO.getTeam_director();
	 }
	return pk_director;
 }
}