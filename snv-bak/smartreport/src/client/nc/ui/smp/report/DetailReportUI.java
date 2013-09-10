package nc.ui.smp.report;

import nc.bs.framework.common.NCLocator;
import nc.itf.smp.ISMPReport;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.report.ReportBaseClass;
import nc.ui.pub.report.ReportItem;
import nc.ui.report.base.ReportUIBase;
import nc.vo.logging.Debug;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;
import nc.vo.smp.course.CourseVO;
import nc.vo.smp.report.detail.DetailQueryVO;
import nc.vo.smp.report.detail.DetailReportVO;

/**
 * 统计明细表
 * @author LINQI
 *
 */
public class DetailReportUI extends ReportUIBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DetailQueryVO qryVo;		//查询条件
	private DetailReportQueryDlg qryDlg;//查询对话框
	private ReportItem[] dynamicItems;	//动态列

	public DetailReportUI(){
		super();
		init();
	}
	
	public void init(){
		
		reBuildButtons();
		getConditionPanel().setVisible(false);	//去掉表头查询条件
		
		//组装报表列
		ReportItem[] items = getDynamicColumns();		//动态列
		if(items!=null && items.length>0){
			ReportItem[] currtItems = this.getReportBase().getBody_Items();		//当前列
			ReportItem[] newItems = new ReportItem[items.length+currtItems.length];		//拼装成新的列
			System.arraycopy(currtItems, 0, newItems, 0, currtItems.length);
			System.arraycopy(items, 0, newItems, currtItems.length, items.length);
			this.getReportBase().setBody_Items(newItems);	//重置报表列
		}
		
	}
	
	/**
	 * 去掉不需要的按钮
	 */
	protected void reBuildButtons(){
		ButtonObject[] btns = this.getButtons();
		for(ButtonObject btn : btns){
			if(!btn.getName().equals("查询") && !btn.getName().equals("打印"))
				this.unRegisterButton(btn);
		}
		this.updateAllButtons();
	}
	
	/**
	 * 获取动态列
	 * @return
	 */
	protected ReportItem[] getDynamicColumns(){
		if(dynamicItems==null){
			
			ISMPReport sv = (ISMPReport) NCLocator.getInstance().lookup(ISMPReport.class.getName());
			CourseVO[] vos;
			try {
				vos = sv.getCourseVO(null);
				if(vos!=null && vos.length>0){
					dynamicItems = new ReportItem[vos.length];
					for(int i=0;i<vos.length;i++){
						dynamicItems[i] = new ReportItem();
						dynamicItems[i].setKey(vos[i].getCourse_code());
						dynamicItems[i].setName(vos[i].getCourse_name());
						dynamicItems[i].setDataType(BillItem.DECIMAL);
						dynamicItems[i].setWidth(80);
						dynamicItems[i].setShowOrder(20+i);	//从20开始排序
					}
					
				}
				
			} catch (BusinessException e) {
				e.printStackTrace();
				Debug.error("获取动态列发生异常！");
			}
		}
		return dynamicItems;
	}
	
	@Override
	protected void initColumnGroups() {
		// TODO Auto-generated method stub
		super.initColumnGroups();
	}

	@Override
	public void onButtonClicked(ButtonObject arg0) {
		// TODO Auto-generated method stub
		super.onButtonClicked(arg0);
	}
	
	@Override
	public void onQuery() throws BusinessException {
		
		int act = this.getQueryDlg().showModal();
		if (act == UIDialog.ID_CANCEL) {
			return;
		}
		qryVo = getQueryDlg().getQryVo();
		ISMPReport sv = (ISMPReport) NCLocator.getInstance().lookup(ISMPReport.class.getName());
		DetailReportVO[] vos = sv.getDetailData(qryVo);
//		DetailReportVO[] vos = this.getDetailReportVO();
		
		//先调用系统的方法生成报表行然后对单元格进行设置值
		this.setBodyDataVO(vos, true);	
//		this.setBodyData(vos);
//		for(int i=0;i<vos.length;i++){
//			DetailReportVO vo = vos[i];
//			String fields[] = vo.getAttributeNames();
//			for(String field:fields){
//				this.getReportBase().setBodyValueAt(vos[i].getAttributeValue(field), 0, field);
//			}
//		}
	}
	
	/**
	 * 设置报表数据
	 * @param vos
	 */
	public void setBodyData(DetailReportVO[] vos){
		this.getReportBase().getBillModel().clearBodyData();	//
		if(vos!=null && vos.length>0){
			System.out.println("vos:"+vos.length);
			ReportBaseClass rbc = this.getReportBase();
			ReportItem[] items = getDynamicColumns();
			System.out.println("items:"+items.length);
			for(int i=0;i<vos.length;i++){
				DetailReportVO vo = vos[i];
				for(ReportItem item:items){
					rbc.setBodyValueAt(vo.getAttributeValue(item.getKey()), i, item.getKey());
				}
			}
		}
	}
	
	public DetailReportVO[] getDetailReportVO(){
		DetailReportVO[] vos = new DetailReportVO[2];
		vos[0] = new DetailReportVO();
		vos[0].setAttributeValue("unitname", "自有业绩");
		vos[0].setAttributeValue("realitynum",new UFDouble(99.23));
		vos[0].setAttributeValue("key1", new UFDouble(88));
		
		vos[1] = new DetailReportVO();
		vos[1].setAttributeValue("unitname", "公司业绩");
		vos[1].setAttributeValue("realitynum",new UFDouble(99.23));
		vos[1].setAttributeValue("key2", new UFDouble(88));
		vos[1].setAttributeValue("key4", new UFDouble(88));
		
		return vos;
	}
	
	public DetailReportQueryDlg getQueryDlg() {
		if (qryDlg == null) {
			qryDlg = new DetailReportQueryDlg(this);
		}
		return qryDlg;
	}

	public String getModuleCode() {
		return "102203";
	}
}
