package nc.ui.smp.performance;


import nc.ui.bd.ref.AbstractRefModel;

public class PerformanceModel  extends  AbstractRefModel{
	public PerformanceModel() {
		super();
	}
	/**
	 * 显示字段列表
	 * 创建日期：(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldCode() {
		return new String[]{"pk_performance","performance_date"};
	}
	/**
	 * 显示字段中文名
	 * 创建日期：(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldName() {
		return new String[]{"业绩主键","业绩日期"};
	}
		 
	/**
	 * 主键字段名
	 * @return java.lang.String
	 */
	public String getPkFieldCode() {
		return "pk_performance";
	} 
	/**
	 * 参照标题
	 * 创建日期：(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public String getRefTitle() {
		return "业绩选择";
	}
	/**
	 * 参照数据库表或者视图名
	 * 创建日期：(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public String getTableName() {
		return "smp_performance";
	}
	 public String getRefShowNameField() {
		      return "pk_performance";
		  }
}