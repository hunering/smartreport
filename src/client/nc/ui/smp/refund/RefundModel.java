package nc.ui.smp.refund;

import nc.ui.bd.ref.AbstractRefModel;

public class RefundModel  extends AbstractRefModel{
	public RefundModel() {
		super();
	}
	/**
	 * ��ʾ�ֶ��б�
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldCode() {
		return new String[]{"pk_refund","performance_date","remit_company","remit_name"};
	}
	/**
	 * ��ʾ�ֶ�������
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldName() {
		return new String[]{"ҵ������","ҵ������","��˾","�������"};
	}
		 
	/**
	 * �����ֶ���
	 * @return java.lang.String
	 */
	public String getPkFieldCode() {
		return "pk_refund";
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
		return "SMP_refund";
	}
	 public String getRefShowNameField() {
		      return "pk_refund";
		  }
}
