package nc.ui.smp.coursetype;


import nc.ui.bd.ref.AbstractRefModel;

public class CourseTypeModel  extends  AbstractRefModel{
	public CourseTypeModel() {
		super();
	}
	/**
	 * ��ʾ�ֶ��б�
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldCode() {
		return new String[]{"pk_coursetype","coursetypename"};
	}
	/**
	 * ��ʾ�ֶ�������
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldName() {
		return new String[]{"�γ��������","�γ��������"};
	}
		 
	/**
	 * �����ֶ���
	 * @return java.lang.String
	 */
	public String getPkFieldCode() {
		return "pk_coursetype";
	} 
	/**
	 * ���ձ���
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public String getRefTitle() {
		return "�γ�ѡ��";
	}
	/**
	 * �������ݿ�������ͼ��
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public String getTableName() {
		return "smp_coursetype";
	}
	 public String getRefShowNameField() {
		      return "coursetypename";
		  }
}