package nc.ui.smp.parter;

import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.ui.trade.manage.ManageEventHandler;
 
  
import nc.ui.trade.bocommand.IUserDefButtonCommand;
import java.util.ArrayList;
import java.util.List;  


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

	protected void initSelfData() {	}
	
		
	 protected List<IUserDefButtonCommand> creatUserButtons(){
		 
		  List<IUserDefButtonCommand> bos = new ArrayList<IUserDefButtonCommand>();
		  return bos;
		}

	public void setDefaultData() throws Exception {
	}
	


}
