/**
 * 
 */
package nc.ui.smp.report;

import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.table.TableCellEditor;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.MessageDialog;
import nc.vo.jcom.lang.StringUtil;
import nc.vo.smp.report.detail.DetailQueryVO;

/**
 * 查询对话框
 * 创建日期：2012-03-06
 * @author LINQI
 *
 */
public class DetailReportQueryDlg extends nc.ui.pub.query.QueryConditionClient {

	private static final long serialVersionUID = 7059148648371835678L;
	
	private DetailReportNormalPnl normalPanel;
	
	private DetailQueryVO qryVo;
	
	private ClientEnvironment clientEnv;

	public DetailQueryVO getQryVo() {
		return qryVo;
	}

	/**
	 * 
	 */
	public DetailReportQueryDlg() {
		super();
		initialize();
	}

	/**
	 * @param parent
	 */
	public DetailReportQueryDlg(Container parent) {
		super(parent);
		initialize();
	}

	private void initialize() {
		
		this.hideUnitButton();	//隐藏多单位参照按钮
		this.setShowDefine(false);	//显示自定义页签
		this.getUIPanelNormal().add(getNormalPanel());
		this.setSize(520, 380);
		
//		设置自定义查询模板
//		this.setTempletID(getClientEnvironment().getCorporation().getPk_corp(), "M1050570", getClientEnvironment().getUser().getPrimaryKey(), "");
	}
	
	private DetailReportNormalPnl getNormalPanel() {
		if (normalPanel == null) {
			normalPanel = new DetailReportNormalPnl();
		}
		return normalPanel;
	}
	
	@Override
	public void closeOK() {
		if (!checkValue()) {
			return;
		}
		setValuesToQueryVO();
		super.closeOK();
	}
	
	private boolean checkValue() {
		
		String strMonth = this.getNormalPanel().getRefMonth(0, 0, 0, 0).getText();
		if (StringUtil.isEmptyWithTrim(strMonth)) {
			MessageDialog.showErrorDlg(this, "错误", "查询月份不可为空！");
			return false;
		}
		return true;
	}
	
	private void setValuesToQueryVO() {
		
//		ConditionVO[] cvos = this.getConditionVO();
//		for(ConditionVO cvo : cvos){
//			System.out.println(cvo.getFieldCode()+">>>"+cvo.getSQLStr());
//		}
		
		qryVo = new DetailQueryVO();
		qryVo.setUnitcode(getClientEnvironment().getCorporation().getUnitcode());
		qryVo.setPk_corp(getClientEnvironment().getCorporation().getPrimaryKey());
		qryVo.setMonth(getNormalPanel().getRefMonth(0, 0, 0, 0).getText());
		qryVo.setFromDate(null);
		qryVo.setEndDate(null);
		
	}
	
	public ClientEnvironment getClientEnvironment(){
		if(clientEnv==null){
			clientEnv = ClientEnvironment.getInstance();
		}
		return clientEnv;
	}
	
	/* (non-Javadoc)
	 * @see nc.ui.mbt.m1050101.IRefPaneActionListener#afterAction(java.awt.event.ActionEvent)
	 */
	public void afterAction(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see nc.ui.mbt.m1050101.IRefPaneActionListener#beforeAction(java.awt.event.ActionEvent)
	 */
	public void beforeAction(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void afterEdit(TableCellEditor editor, int row, int col) {
		// TODO Auto-generated method stub
		super.afterEdit(editor, row, col);
	}

	@Override
	public void changeValueRef(String fieldcode, Object editor) {
		// TODO Auto-generated method stub
		super.changeValueRef(fieldcode, editor);
	}

	@Override
	public void changeValueRef(String tableCode, String fieldcode, Object editor) {
		// TODO Auto-generated method stub
		super.changeValueRef(tableCode, fieldcode, editor);
	}

	 
	

}
