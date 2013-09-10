package nc.ui.smp.performance;
import java.util.ArrayList;
import java.util.List;

import nc.ui.pub.bill.BillEditEvent;
import nc.ui.smp.performance.MyDelegator;
import nc.ui.trade.bocommand.IUserDefButtonCommand;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.trade.pub.IBillStatus;


/**
 * <b> 在此处简要描述此类的功能 </b>
 *
 * <p>
 *     在此处添加此类的描述信息
 * </p>
 *
 *
 * @author author
 * @version tempProject version
 */
 public class ClientUI extends AbstractClientUI{
       
       protected ManageEventHandler createEventHandler() {
		return new MyEventHandler(this, getUIControl());
	}
       
	public void setBodySpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {}

	protected void setHeadSpecialData(CircularlyAccessibleValueObject vo,
			int intRow) throws Exception {}

	protected void setTotalHeadSpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {	}

	protected void initSelfData() {
		
		 
	}
	
		
	 protected List<IUserDefButtonCommand> creatUserButtons(){
		 
		  List<IUserDefButtonCommand> bos = new ArrayList<IUserDefButtonCommand>();
		  return bos;
		}

	public void setDefaultData() throws Exception {
		 
	}
	@Override
	public void afterEdit(BillEditEvent e) {
		if("pk_parter".equalsIgnoreCase(e.getKey())){
			 
		 String pk_parter=	""+this.getBillCardPanel().getHeadItem("pk_parter").getValue();
		 if(!pk_parter.equalsIgnoreCase("NULL")){
			 MyDelegator m=new  MyDelegator();
			 String pk_team= m.getPKTeamByPKPartern(pk_parter);
			 this.getBillCardPanel().setHeadItem("pk_team", pk_team);
			 String pk_director=m.getPKDirectorByPKTeam(pk_team);
			 this.getBillCardPanel().setHeadItem("pk_director", pk_director);
		 }
		 
		}
	}


}
