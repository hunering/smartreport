package nc.ui.fudan.refund;

import nc.vo.pub.lang.UFBoolean;

/**
  *
  *����ҵ��������ȱʡʵ��
  *@author author
  *@version tempProject version
  */
public class MyDelegator extends AbstractMyDelegator{

 /**
   *
   *
   *�÷������ڻ�ȡ��ѯ�������û����Զ�������޸ġ�
   *
   */
 public String getBodyCondition(Class bodyClass,String key){
   return super.getBodyCondition(bodyClass,key);
 }
 public UFBoolean getParaBillNoEditable() throws Exception {
	  return new UFBoolean(true);
 }

}