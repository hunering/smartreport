package nc.ui.smp.performance;

import nc.ui.pub.ButtonObject;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.ManageEventHandler;

/** 
  *
  *�����������а�ť�¼�����, ��չ��ť���¼���ӦӦ��ʵ��IUIButtonCommand������ע��
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