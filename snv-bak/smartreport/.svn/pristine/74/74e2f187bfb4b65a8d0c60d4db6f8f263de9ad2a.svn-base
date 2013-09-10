package nc.ui.smp.coursetype.command;

import nc.ui.trade.bocommand.IButtonCommand;
import nc.ui.pub.bill.BillData;
import nc.ui.pub.ButtonObject;
/**
 * <b> 带单据模板校验功能的保存操作 </b><br>
 *
 * <p>
 *     在此处添加此类的描述信息
 * </p>
 *
 *
 * @author author
 * @version tempProject version
 */
public class SaveBoCommand extends nc.ui.trade.manage.ManageEventHandler implements IButtonCommand {
	 

	public SaveBoCommand(nc.ui.trade.manage.BillManageUI billUI, nc.ui.trade.controller.IControllerBase control) {
		super(billUI, control);
	}

	public void execute(ButtonObject bo) throws Exception {
	
		BillData data = getBillCardPanelWrapper().getBillCardPanel().getBillData();
		if(data != null)
			data.dataNotNullValidate();
		super.onBoSave();
		 
	}
}
 