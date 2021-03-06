package nc.ui.smp.coursetype;
import   nc.ui.bd.ref.AbstractRefModel;
/**
 *课程类别参照，显示课程类别选择框
 * */
public class CourseTypeModel  extends  AbstractRefModel{
	public CourseTypeModel() {
		super();
	}
	/**
	 * 显示字段列表
	 * 创建日期：(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldCode() {
		return new String[]{"pk_coursetype","coursetype_name"};
	}
	/**
	 * 显示字段中文名
	 * 创建日期：(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldName() {
		return new String[]{"课程类别主键","课程类别名称"};
	}
		 
	/**
	 * 主键字段名
	 * @return java.lang.String
	 */
	public String getPkFieldCode() {
		return "pk_coursetype";
	} 
	/**
	 * 参照标题
	 * 创建日期：(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public String getRefTitle() {
		return "课程类别选择";
	}
	/**
	 * 参照数据库表或者视图名
	 * 创建日期：(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public String getTableName() {
		return "SMP_COURSETYPE";
	}
	 public String getRefShowNameField() {
		      return "coursetype_name";
		  }
}
