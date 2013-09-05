package nc.ui.smp.refund;

import nc.ui.bd.ref.AbstractRefModel;

public class RefundModel  extends AbstractRefModel{
	public RefundModel() {
		super();
	}
	/**
	 * 显示字段列表
	 * 创建日期：(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldCode() {
		return new String[]{"pk_refund","performance_date","remit_company","remit_name"};
	}
	/**
	 * 显示字段中文名
	 * 创建日期：(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldName() {
		return new String[]{"业绩主键","业绩日期","汇款公司","汇款名称"};
	}
		 
	/**
	 * 主键字段名
	 * @return java.lang.String
	 */
	public String getPkFieldCode() {
		return "pk_refund";
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
		return "SMP_refund";
	}
	 public String getRefShowNameField() {
		      return "pk_refund";
		  }
}
