/**
 * 
 */
package nc.ui.smp.report;

import java.awt.LayoutManager;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.smp.pub.UIComponentFactory;
import nc.vo.pub.lang.UFDate;

/**
 * 
 * @author LINQI
 *
 */
public class DetailReportNormalPnl extends UIPanel {

	private static final long serialVersionUID = -3129827417647467318L;
	
	private UILabel lblMonth;		//查询月份
	private UIRefPane refMonth;
	
	private UILabel lblUnit;		//查询单位
	private UIRefPane refUnit;
	

	public UILabel getLbMonth(int x, int y, int w, int h) {
		if (lblMonth == null) {
			lblMonth = UIComponentFactory.createUILabel("lblMonth", "查询月份");
			lblMonth.setBounds(x, y, w, h);
		}
		return lblMonth;
	}
	public UIRefPane getRefMonth(int x, int y, int w, int h) {
		if (refMonth == null) {
			refMonth = UIComponentFactory.createUIRefPane("refMonth", "会计期间", null);
			refMonth.setBounds(x, y, w, h);
		}
		return refMonth;
	}

	public UILabel getLbUnit(int x, int y, int w, int h) {
		if (lblUnit == null) {
			lblUnit = UIComponentFactory.createUILabel("lblUnit", "当前公司");
			lblUnit.setBounds(x, y, w, h);
		}
		return lblUnit;
	}
	public UIRefPane getRefUnit(int x, int y, int w, int h) {
		if (refUnit == null) {
			refUnit = UIComponentFactory.createUIRefPane("refUnit", "公司目录", null);
			refUnit.setMultiSelectedEnabled(false);
			String pk_corp = this.getDefaultValue();
			if(pk_corp.equals("0001")){
				refUnit.setEnabled(true);
			}else{
				refUnit.setEnabled(false);
			}
			refUnit.setPK(pk_corp);
			refUnit.setBounds(x, y, w, h);
		}
		return refUnit;
	}
	
	private void initialize() {
		
		this.setLayout(null);
		this.setSize(520, 380);
		
		add(this.getLbMonth(20, 20, 80, 20));		//查询月份
		add(this.getRefMonth(110, 20, 260, 20));
			
		add(this.getLbUnit(20, 50, 80, 20));		//当前公司
		add(this.getRefUnit(110, 50, 260, 20));
		
	}

	protected String getDefaultValue(){
		return ClientEnvironment.getInstance().getCorporation().getPk_corp();
	}
	
	private String getDefaultStartDate() {
		UFDate logondate = ClientEnvironment.getInstance().getDate();
		String rt = logondate.getYear() + "-";
		int mon = logondate.getMonth();
		String smon = mon + "";
		while(smon.length() < 2) {
			smon = "0" + smon;
		}
		rt += smon + "-01";
		return rt;
	}
	
	private String getDefaultEndDate() {
		UFDate logondate = ClientEnvironment.getInstance().getDate();
		return logondate.toString();
//		String year = logondate.getYear() + "";
//		int mon = logondate.getMonth();
//		String month = mon + "";
//		while (month.length() < 2) {
//			month = "0" + month;
//		}
//		String lastDay = logondate.getDaysMonth() + "";
//		String rt = year + "-" + month + "-" + lastDay;
//		return rt;
	}
	
	public DetailReportNormalPnl() {
		initialize();
	}

	/**
	 * @param layout
	 */
	public DetailReportNormalPnl(LayoutManager layout) {
		super(layout);
		initialize();
	}

	
}
