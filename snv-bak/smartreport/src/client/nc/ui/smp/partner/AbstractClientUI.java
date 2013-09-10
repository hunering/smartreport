package nc.ui.smp.partner;

import java.util.List;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.linkoperate.ILinkQuery;
import nc.ui.pub.linkoperate.ILinkQueryData;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.bill.BillTemplateWrapper;
import nc.ui.trade.bocommand.IUserDefButtonCommand;
import nc.ui.trade.bsdelegate.BusinessDelegator;
import nc.ui.trade.button.IBillButton;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.CircularlyAccessibleValueObject;

/**
 * <b> �ڴ˴���Ҫ��������Ĺ��� </b>
 *
 * <p>
 *     �ڴ˴���Ӵ����������Ϣ
 * </p>
 *
 *
 * @author author
 * @version tempProject version
 */
  public abstract class AbstractClientUI extends nc.ui.trade.manage.BillManageUI implements  ILinkQuery{

	private List<IUserDefButtonCommand> bos = null;
	 
	public AbstractClientUI()
	{
		super();
		getBillCardPanel().setAutoExecHeadEditFormula(true);
	}
	 
	protected AbstractManageController createController() {
		return new ClientUICtrl();
	}
	
	/**
	 * ������ݲ���ƽ̨ʱ��UI����Ҫ���ش˷��������ز���ƽ̨��ҵ������� 
	 * @return BusinessDelegator ����ƽ̨��ҵ�������
	 */
	protected BusinessDelegator createBusinessDelegator() {
		return new nc.ui.smp.refund.MyDelegator();
	}

	/**
	  * ע���Զ��尴ť
	  */
	 protected void initPrivateButton() {
	 
	  if (getUserButtons() != null) {
		   for(IUserDefButtonCommand cmd : getUserButtons())
			   addPrivateButton(cmd.getButtonVO());
		  	}
	 }
	 
	 public List<IUserDefButtonCommand> getUserButtons() {
		  if(bos == null)
			  bos = creatUserButtons();
		  return bos;
	 }
	 
	 protected abstract List<IUserDefButtonCommand> creatUserButtons();
	
		
		
	
	/**
	 * ע��ǰ̨У����
	 */
	public Object getUserObject() {
		return null;
	}
	
	public void doQueryAction(ILinkQueryData querydata) {
	        String billId = querydata.getBillID();
	        if (billId != null) {
	            try {
	            	setCurrentPanel(BillTemplateWrapper.CARDPANEL);
	            	AggregatedValueObject vo = loadHeadData(billId);
	                getBufferData().addVOToBuffer(vo);
	                setListHeadData(new CircularlyAccessibleValueObject[]{vo.getParentVO()});
	                getBufferData().setCurrentRow(getBufferData().getCurrentRow());
	                setBillOperate(IBillOperate.OP_NO_ADDANDEDIT);
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        }
    	}
    	
	protected void saveOnClosing() throws Exception {

		ButtonObject bo = getButtonManager().getButton(IBillButton.Save);
		getManageEventHandler().onButton(bo);
		
	}
}
