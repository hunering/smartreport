package nc.ui.smp.performance;


import nc.ui.bd.ref.AbstractRefModel;

public class PerformanceModel  extends  AbstractRefModel{
	public PerformanceModel() {
		super();
	}
	/**
	 * ��ʾ�ֶ��б�
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldCode() {
		return new String[]{"pk_performance","performance_date"};
	}
	/**
	 * ��ʾ�ֶ�������
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldName() {
		return new String[]{"ҵ������","ҵ������"};
	}
		 
	/**
	 * �����ֶ���
	 * @return java.lang.String
	 */
	public String getPkFieldCode() {
		return "pk_performance";
	} 
	/**
	 * ���ձ���
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public String getRefTitle() {
		return "ҵ��ѡ��";
	}
	/**
	 * �������ݿ�������ͼ��
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public String getTableName() {
		return "smp_performance";
	}
	 public String getRefShowNameField() {
		      return "pk_performance";
		  }
}