package nc.ui.sa.refund;

import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import nc.bs.logging.Logger;
import nc.ui.pub.ButtonObject;
import nc.ui.trade.bocommand.IButtonCommand;
import nc.ui.trade.bocommand.MetaDataPrintBoCommand;
import nc.ui.trade.bocommand.IUserDefButtonCommand; 
import nc.ui.smp.course.command.SaveBoCommand;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.pub.BusinessException;
import nc.ui.trade.base.IBillOperate;

import nc.ui.trade.controller.IControllerBase;
import nc.vo.trade.button.ButtonVO;

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
		// TODO Auto-generated method stub
		super.onBoSave();
	}

		
}