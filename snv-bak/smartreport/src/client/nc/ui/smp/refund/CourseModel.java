package nc.ui.smp.refund;


import nc.ui.bd.ref.AbstractRefModel;

public class CourseModel  extends  AbstractRefModel{
	public CourseModel() {
		super();
	}
	/**
	 * 显示字段列表
	 * 创建日期：(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldCode() {
		return new String[]{"pk_course","course_code","course_name"};
	}
	/**
	 * 显示字段中文名
	 * 创建日期：(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldName() {
		return new String[]{"课程主键","课程编码","课程名称"};
	}
		 
	/**
	 * 主键字段名
	 * @return java.lang.String
	 */
	public String getPkFieldCode() {
		return "pk_course";
	} 
	/**
	 * 参照标题
	 * 创建日期：(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public String getRefTitle() {
		return "课程选择";
	}
	/**
	 * 参照数据库表或者视图名
	 * 创建日期：(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public String getTableName() {
		return "SMP_Course";
	}
	 public String getRefShowNameField() {
		      return "course_name";
		  }
}