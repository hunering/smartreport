package nc.impl.smp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import nc.bs.dao.BaseDAO;
import nc.bs.smp.SMPBaseDAO;
import nc.itf.smp.ISMPConstant;
import nc.itf.smp.ISMPReport;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.pub.smp.util.SMPUtil;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.smp.course.CourseVO;
import nc.vo.smp.report.detail.DetailQueryVO;
import nc.vo.smp.report.detail.DetailReportVO;

/**
 * 报表实现类
 * @author LINQI
 *
 */
public class ISMPReportImpl implements ISMPReport{

	/**
	 * 取明细统计表数据
	 */
	public DetailReportVO[] getDetailData(DetailQueryVO queryVO) throws BusinessException {
		
		List<DetailReportVO> rtList = new ArrayList<DetailReportVO>();
		
		String month = queryVO.getMonth();
		UFDate date = new UFDate(month+"-"+"01");
		int weeks = SMPUtil.getWeeksOfMonth(date.toDate());
		
		SMPBaseDAO dao = null;
		try {
			dao = new SMPBaseDAO();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new BusinessException("初始SMPBaseDAO发生异常！"+e.getMessage());
		}
		String SQL = getAchievementSQL();
		SQLParameter param = new SQLParameter();
		for(int i=1;i<=weeks;i++){
			Date date1 = SMPUtil.getFirsDayOfWeek(date.toDate(), i);
			Date date2 = SMPUtil.getLastDayOfWeek(date.toDate(), i);
			UFDate dateFrom = new UFDate(date1);
			UFDate dateEnd = new UFDate(date2);
			
			param.clearParams();
			param.addParam(dateFrom.toString());
			param.addParam(dateEnd.toString());
			param.addParam(ISMPConstant.SMP_ACHIEVETYPE_ID_ZY);			//自由业绩
			List list0 = (List) dao.executeQuery(SQL, param, new MapListProcessor());
			DetailReportVO vo0 = this.transResultSet(ISMPConstant.SMP_ACHIEVETYPE_NAME_ZY, list0);		//数据转换
			rtList.add(vo0);
			
			param.clearParams();
			param.addParam(dateFrom.toString());
			param.addParam(dateEnd.toString());
			param.addParam(ISMPConstant.SMP_ACHIEVETYPE_ID_DLS);			//代理商业绩
			List list1 = (List) dao.executeQuery(SQL, param, new MapListProcessor());
			DetailReportVO vo1 = this.transResultSet(ISMPConstant.SMP_ACHIEVETYPE_NAME_DLS, list1);		//数据转换
			rtList.add(vo1);
			
			DetailReportVO totalvo = this.getSubtotalRow("第"+i+"周业绩合计("+dateFrom.getDay()+"-"+dateEnd.getDay()+"日)", vo0, vo1);	//生成小计
			rtList.add(totalvo);
			
		}
		List totalList =  this.getTotalRow(rtList);		//生成合计
		rtList.addAll(totalList);
		if(!rtList.isEmpty()){
			return rtList.toArray(new DetailReportVO[0]);
		}
		return null;
	}
	
	/**
	 * 将结果集转成行格式
	 * @param achievetype	业绩类型
	 * @param list
	 * @return
	 */
	private DetailReportVO transResultSet(String achievetype, List<Map> list){
		DetailReportVO vo = new DetailReportVO();
		vo.setAchievetype(achievetype);		//业绩类型
		UFDouble total = UFDouble.ZERO_DBL; 
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = list.get(i);
				UFDouble remit_amount = map.get("remit_amount")!=null ? new UFDouble(map.get("remit_amount").toString()) : UFDouble.ZERO_DBL;
				total = total.add(remit_amount);
				vo.setAttributeValue((String)map.get("course_code"), remit_amount);
			}
		}
		vo.setRealitynum(total);	//本月实际额
		return vo;
	}
	
	/**
	 * 生成小计行
	 * @param rowHeading
	 * @param vo0	自由业绩
	 * @param vo1	代理商业绩
	 * @return
	 */
	private DetailReportVO getSubtotalRow(String rowHeading,DetailReportVO vo0,DetailReportVO vo1){
		
		DetailReportVO totalvo = (DetailReportVO) vo0.clone();
		String[] fields = totalvo.getAttributeNames();
		
		UFDouble realTotal = UFDouble.ZERO_DBL; 	//本月实际
		for(int i=0;i<fields.length;i++){
			if(!fields[i].equals("achievetype") && !fields[i].startsWith("vfree")){
				Object obj_t = totalvo.getAttributeValue(fields[i]);
				Object obj_1 = vo1.getAttributeValue(fields[i]);
				UFDouble totalValue = (obj_t!=null ? new UFDouble(obj_t.toString()) : UFDouble.ZERO_DBL).add(obj_1 != null ? new UFDouble(obj_1.toString()): UFDouble.ZERO_DBL);
				totalvo.setAttributeValue(fields[i], totalValue);
				realTotal = realTotal.add(totalValue);
			}
		}
		totalvo.setRealitynum(realTotal);	//本月实际额
		totalvo.setAchievetype(rowHeading);	//设置行头名称
		return totalvo;
	}
	
	/**
	 * 生成最后的合计行
	 * @param list
	 * @return
	 */
	private List<DetailReportVO> getTotalRow(List<DetailReportVO> list){
		
		
		List<DetailReportVO> rtList = new ArrayList<DetailReportVO>();
		
		DetailReportVO totalvo0 = new DetailReportVO();	//自由业绩合计
		totalvo0.setAchievetype(ISMPConstant.SMP_ACHIEVETYPE_NAME_SUTOTAL_ZY);
		
		DetailReportVO totalvo1 = new DetailReportVO();	//代理业绩合计
		totalvo1.setAchievetype(ISMPConstant.SMP_ACHIEVETYPE_NAME_SUBTOTAL_DLS);
		
		DetailReportVO totalvo = new DetailReportVO();	//本月合计
		totalvo.setAchievetype(ISMPConstant.SMP_ACHIEVETYPE_NAME_TOTAL);
		
		if(list!=null && list.size()>0){
			
			UFDouble realitynum_0 = UFDouble.ZERO_DBL;		//本月自由业绩本月实际统计
			UFDouble realitynum_1 = UFDouble.ZERO_DBL;		//本月代理商业绩本月实际统计
			UFDouble realitynum_t = UFDouble.ZERO_DBL;		//本月合计
			
			String[] fields = list.get(0).getAttributeNames();
			for(DetailReportVO vo:list){
				for(int i=0;i<fields.length;i++){
					if(!fields[i].equals("achievetype")){
						if(vo.getAchievetype().equals(ISMPConstant.SMP_ACHIEVETYPE_NAME_ZY)){	//自由业绩合计
							Object obj1 = totalvo0.getAttributeValue(fields[i]);
							Object obj2 = vo.getAttributeValue(fields[i]);
							UFDouble totalValue = (obj1!=null?new UFDouble(obj1.toString()):UFDouble.ZERO_DBL).add(obj2!=null?new UFDouble(obj2.toString()):UFDouble.ZERO_DBL);
							totalvo0.setAttributeValue(fields[i], totalValue);
							realitynum_0 = realitynum_0.add(totalValue);
							
						}else if(vo.getAchievetype().equals(ISMPConstant.SMP_ACHIEVETYPE_NAME_DLS)){	//代理商业绩
							Object obj1 = totalvo1.getAttributeValue(fields[i]);
							Object obj2 = vo.getAttributeValue(fields[i]);
							UFDouble totalValue = (obj1!=null?new UFDouble(obj1.toString()):UFDouble.ZERO_DBL).add(obj2!=null?new UFDouble(obj2.toString()):UFDouble.ZERO_DBL);
							totalvo1.setAttributeValue(fields[i], totalValue);
							realitynum_1 = realitynum_1.add(totalValue);
							
						}else{		//本月合计
							Object obj1 = totalvo.getAttributeValue(fields[i]);
							Object obj2 = vo.getAttributeValue(fields[i]);
							UFDouble totalValue = (obj1!=null?new UFDouble(obj1.toString()):UFDouble.ZERO_DBL).add(obj2!=null?new UFDouble(obj2.toString()):UFDouble.ZERO_DBL);
							totalvo.setAttributeValue(fields[i], totalValue);
							realitynum_t = realitynum_t.add(totalValue);
						}
					}
				}
			}
			totalvo0.setRealitynum(realitynum_0);
			totalvo1.setRealitynum(realitynum_1);
			totalvo.setRealitynum(realitynum_t);
			rtList.add(totalvo0);
			rtList.add(totalvo1);
			rtList.add(totalvo);
		}
		
		
		return rtList;
	}
	
	private String getAchievementSQL(){
		StringBuffer str = new StringBuffer();
		str.append("select b.pk_course,b.course_code,b.course_name,sum(nvl(a.remit_amount,0.00)) remit_amount ")
		   .append("from smp_course b left join  smp_performance a  on ( ")
		   .append("b.pk_course=a.pk_course ")
		   .append("and a.performance_date>=? ")
		   .append("and a.performance_date<=? ")
		   .append("and achievetype=? ")
		   .append(") ")
		   .append("where nvl(a.dr,0)=0 ")
		   .append("group by a.achievetype,b.pk_course,b.course_code,b.course_name ")
		   .append("order by b.course_code ")
		   .append(" ");
		return str.toString();
	}

	/**
	 * 取课程表数据
	 */
	public CourseVO[] getCourseVO(String str) throws BusinessException {
		List list = (List) new BaseDAO().retrieveByClause(CourseVO.class, str, new String[]{"course_code","course_name"});
		if(list!=null&&list.size()>0){
			return (CourseVO[]) list.toArray(new CourseVO[0]);
		}
		return null;
	}

}
