package nc.ui.smp.clsexe;


import nc.ui.bd.ref.AbstractRefModel;

public class AreaModel  extends  AbstractRefModel{
	public AreaModel() {
		super();
	}
	/**
	 * ��ʾ�ֶ��б�
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldCode() {
		return new String[]{"pk_area","name"};
	}
	/**
	 * ��ʾ�ֶ�������
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldName() {
		return new String[]{"��������","��������"};
	}
		 
	/**
	 * �����ֶ���
	 * @return java.lang.String
	 */
	public String getPkFieldCode() {
		return "pk_area";
	} 
	/**
	 * ���ձ���
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public String getRefTitle() {
		return "����ѡ��";
	}
	/**
	 * �������ݿ�������ͼ��
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public String getTableName() {
		return "SMP_Area";
	}
	 public String getRefShowNameField() {
		      return "name";
		  }
}