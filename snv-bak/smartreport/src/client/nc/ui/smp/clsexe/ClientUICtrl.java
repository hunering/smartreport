package nc.ui.smp.clsexe;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.bill.ISingleController;

import nc.vo.trade.pub.HYBillVO;
import nc.vo.smp.clsexe.ClsexeVO;
import nc.ui.trade.button.IBillButton;


/**
 * <b> 在此处简要描述此类的功能 </b>
 *
 * <p>
 *     在此处添加此类的描述信息
 * </p>
 *
 * Create on 2006-4-6 16:00:51
 *
 * @author author
 * @version tempProject version
 */

public class ClientUICtrl extends AbstractManageController implements ISingleController{

	public String[] getCardBodyHideCol() {
		return null;
	}

	public int[] getCardButtonAry() {
	
        return new int[]{
			nc.ui.trade.button.IBillButton.Brow,
			nc.ui.trade.button.IBillButton.Add,
			nc.ui.trade.button.IBillButton.Edit,
			nc.ui.trade.button.IBillButton.Delete,
			nc.ui.trade.button.IBillButton.Line,
			nc.ui.trade.button.IBillButton.Save,
			nc.ui.trade.button.IBillButton.Cancel,
			nc.ui.trade.button.IBillButton.Refresh,
			nc.ui.trade.button.IBillButton.Print,
			nc.ui.trade.button.IBillButton.Return,
			nc.ui.trade.button.IBillButton.File,
			nc.ui.trade.button.IBillButton.Commit,
			nc.ui.trade.button.IBillButton.Audit,
			nc.ui.trade.button.IBillButton.CancelAudit
        };
  
	}
	
	public int[] getListButtonAry() {		
	
        return new int[]{
			nc.ui.trade.button.IBillButton.Add,
			nc.ui.trade.button.IBillButton.Edit,
			nc.ui.trade.button.IBillButton.Delete,
			nc.ui.trade.button.IBillButton.Query,
			nc.ui.trade.button.IBillButton.Refresh,
			nc.ui.trade.button.IBillButton.Print,
			nc.ui.trade.button.IBillButton.Card,
			nc.ui.trade.button.IBillButton.File,
			nc.ui.trade.button.IBillButton.Commit,
			nc.ui.trade.button.IBillButton.Audit,
			nc.ui.trade.button.IBillButton.CancelAudit        
        };
	
	}

	public boolean isShowCardRowNo() {
		return true;
	}

	public boolean isShowCardTotal() {
		return false;
	}

	public String getBillType() {
		//return "98H102";
		return "SP03";
	}

	public String[] getBillVoName() {
		return new String[]{
			HYBillVO.class.getName(),
			ClsexeVO.class.getName(),
			ClsexeVO.class.getName()
		};
	}

	public String getBodyCondition() {
		return null;
	}

	public String getBodyZYXKey() {
		return null;
	}

	public int getBusinessActionType() {
		return IBusinessActionType.PLATFORM;
	}

	public String getChildPkField() {
		return null;
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return null;
	}

	public Boolean isEditInGoing() throws Exception {
		return null;
	}

	public boolean isExistBillStatus() {
		return true;
	}

	public boolean isLoadCardFormula() {		
		return false;
	}

	public String[] getListBodyHideCol() {	
		return null;
	}

	public String[] getListHeadHideCol() {		
		return null;
	}

	public boolean isShowListRowNo() {		
		return true;
	}

	public boolean isShowListTotal() {
		return false;
	}
	
	/**
	 * 是否单表
	 * @return boolean true:单表体，false:单表头
	 */ 
	public boolean isSingleDetail() {
		return false; //单表头
	}
}
