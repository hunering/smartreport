package nc.ui.smp.refund;

import nc.ui.fl.pub.BillNoUtil;
import nc.ui.pub.ButtonObject;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.ManageEventHandler;
import nc.uif.pub.exception.UifException;
import nc.vo.jcom.lang.StringUtil;
import nc.vo.logging.Debug;

/** 
  *
  *该类用来进行按钮事件处理, 扩展按钮的事件响应应该实现IUIButtonCommand并进行注册
  *@author author
  *@version tempProject version
  */
  
public class MyEventHandler  extends ManageEventHandler{
	

	public MyEventHandler(nc.ui.trade.manage.BillManageUI billUI, IControllerBase control){
		super(billUI,control);
	}

	@Override
	public void onBoAdd(ButtonObject bo) throws Exception {
		// TODO Auto-generated method stub
		super.onBoAdd(bo);
		this.getBillCardPanelWrapper().getBillCardPanel().getHeadItem("vbillno").setEdit(true);
	}

	@Override
	public void onBoAudit() throws Exception {
		// TODO Auto-generated method stub
		super.onBoAudit();
	}

	@Override
	protected void onBoCommit() throws Exception {
		// TODO Auto-generated method stub
		super.onBoCommit();
	}

	@Override
	protected void onBoDel() throws Exception {
		// TODO Auto-generated method stub
		super.onBoDel();
	}

	@Override
	protected void onBoEdit() throws Exception {
		// TODO Auto-generated method stub
		super.onBoEdit();
	}

	@Override
	protected void onBoRefresh() throws Exception {
		// TODO Auto-generated method stub
		super.onBoRefresh();
	}

	@Override
	protected void onBoSave() throws Exception {
		
	 
	//	this.getBillCardPanelWrapper().getBillCardPanel().dataNotNullValidate();	//非空校验
		if(isAdding()){
			String vbillno = (String) this.getBillCardPanelWrapper().getBillCardPanel().getHeadItem("vbillno").getValueObject();
			 
			if(vbillno!=null&&vbillno.trim().length()==0){
				vbillno = BillNoUtil.getBillCode(getBillCardPanelWrapper().getBillCardPanel().getBillType(), getBillUI()._getCorp().getPrimaryKey());
			}
			if(isExistBillno(vbillno)){
				this.getBillUI().showErrorMessage("单据号已存在，请重新输入！");
				return;
			}
			getBillCardPanelWrapper().getBillCardPanel().setHeadItem(getBillField().getField_BillNo(), vbillno);
		}
		
		try{
			super.onBoSave();
		}catch(Exception e){
			e.printStackTrace();
			getBillCardPanelWrapper().getBillCardPanel().setHeadItem(getBillField().getField_BillNo(), null);		//取消单据号设置
		}
	}

	/**
	 * 检查单据号是否重复
	 * @param vbillno
	 * @return
	 */
	private boolean isExistBillno(String vbillno){
		try {
			String no = (String) HYPubBO_Client.findColValue("t_refund", "vbillno", "isnull(dr,0)=0 and vbillno='"+vbillno+"' and pk_corp='"+_getCorp().getPk_corp()+"'");
			if(StringUtil.isEmptyWithTrim(no)){
				return false;
			}
		} catch (UifException e) {
			Debug.error("校验单据号发生异常！",e);
		}
		return true;
	}

		
}