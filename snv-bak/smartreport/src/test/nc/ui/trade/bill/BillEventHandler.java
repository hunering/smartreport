/*      */ package nc.ui.trade.bill;
/*      */ 
/*      */ import java.io.PrintStream;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import nc.bs.logging.Logger;
/*      */ import nc.ui.ml.NCLangRes;
/*      */ import nc.ui.pub.ButtonObject;
/*      */ import nc.ui.pub.ClientEnvironment;
/*      */ import nc.ui.pub.beans.MessageDialog;
/*      */ import nc.ui.pub.beans.UIDialog;
/*      */ import nc.ui.pub.beans.UITable;
/*      */ import nc.ui.pub.bill.BillCardPanel;
/*      */ import nc.ui.pub.bill.BillItem;
/*      */ import nc.ui.pub.bill.BillModel;
/*      */ import nc.ui.pub.pf.PfUtilClient;
/*      */ import nc.ui.pub.print.IDataSource;
/*      */ import nc.ui.pub.print.PrintEntry;
/*      */ import nc.ui.pub.workflownote.FlowStateDlg;
/*      */ import nc.ui.trade.base.AbstractBillUI;
/*      */ import nc.ui.trade.bsdelegate.BusinessDelegator;
/*      */ import nc.ui.trade.bsdelegate.DefaultBusinessSplit;
/*      */ import nc.ui.trade.bsdelegate.IBusinessSplit;
/*      */ import nc.ui.trade.buffer.BillUIBuffer;
/*      */ import nc.ui.trade.buffer.RecordNotFoundExcetption;
/*      */ import nc.ui.trade.businessaction.BdBusinessAction;
/*      */ import nc.ui.trade.businessaction.BusinessAction;
/*      */ import nc.ui.trade.businessaction.IBusinessController;
/*      */ import nc.ui.trade.button.ButtonManager;
/*      */ import nc.ui.trade.controller.IControllerBase;
/*      */ import nc.ui.trade.handler.EventHandler;
/*      */ import nc.ui.trade.pub.BillDirectPrint;
/*      */ import nc.ui.trade.pub.CardPanelPRTS;
/*      */ import nc.ui.trade.pub.ReportTreeTableModelAdapter;
/*      */ import nc.ui.trade.query.INormalQuery;
/*      */ import nc.vo.bd.CorpVO;
/*      */ import nc.vo.format.Format;
/*      */ import nc.vo.jcom.lang.StringUtil;
/*      */ import nc.vo.pf.pub.BusitypeVO;
/*      */ import nc.vo.pub.AggregatedValueObject;
/*      */ import nc.vo.pub.BusinessException;
/*      */ import nc.vo.pub.CircularlyAccessibleValueObject;
/*      */ import nc.vo.pub.SuperVO;
/*      */ import nc.vo.pub.lang.UFBoolean;
/*      */ import nc.vo.pub.lang.UFDate;
/*      */ import nc.vo.pub.lang.UFDateTime;
/*      */ import nc.vo.trade.button.ButtonVO;
/*      */ import nc.vo.trade.field.IBillField;
/*      */ import nc.vo.trade.pub.HYBillVO;
/*      */ import nc.vo.trade.pub.IExAggVO;
/*      */ import nc.vo.trade.summarize.Hashlize;
/*      */ import nc.vo.trade.summarize.VOHashPrimaryKeyAdapter;
/*      */ 
/*      */ public abstract class BillEventHandler extends EventHandler
/*      */ {
/*      */   private static final String staticACTION = "BOACTION";
/*      */   private static final String staticASS = "BOASS";
/* 1969 */   private IBillBusiListener m_bbl = null;
/*      */ 
/*      */   public BillEventHandler(AbstractBillUI billUI, IControllerBase control)
/*      */   {
/*   68 */     super(billUI, control);
/*      */   }
/*      */ 
/*      */   protected void afterOnBoAction(int intBtn, AggregatedValueObject billVo)
/*      */     throws Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void afterOnBoAss(ButtonObject bo)
/*      */     throws Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void beforeOnBoAction(int intBtn, AggregatedValueObject billVo)
/*      */     throws Exception
/*      */   {
/*  104 */     if (billVo instanceof HYBillVO)
/*  105 */       ((HYBillVO)billVo).setM_billField(getBillField());
/*      */   }
/*      */ 
/*      */   protected void beforeOnBoAss(ButtonObject bo)
/*      */     throws Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void busiTypeBefore(AbstractBillUI billUI, ButtonObject bo)
/*      */     throws Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   private void clearChildPk(CircularlyAccessibleValueObject[] vos)
/*      */     throws Exception
/*      */   {
/*  137 */     if ((vos == null) || (vos.length == 0))
/*  138 */       return;
/*  139 */     for (int i = 0; i < vos.length; ++i)
/*  140 */       vos[i].setPrimaryKey(null);
/*      */   }
/*      */ 
/*      */   private void complexOnButton(int intBtn, ButtonObject bo)
/*      */     throws Exception
/*      */   {
/*  154 */     switch (intBtn)
/*      */     {
/*      */     case 2:
/*  156 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000060"));
/*      */ 
/*  161 */       onBoBusiType(bo);
/*  162 */       break;
/*      */     case 1:
/*  165 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000061"));
/*      */ 
/*  170 */       onBoBusiTypeAdd(bo, null);
/*  171 */       break;
/*      */     case 25:
/*  174 */       onBoAction(bo);
/*  175 */       break;
/*      */     case 29:
/*  178 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000062"));
/*      */ 
/*  184 */       onBoAss(bo);
/*  185 */       break;
/*      */     case 10:
/*  188 */       onBoLine(bo);
/*  189 */       break;
/*      */     case 16:
/*  192 */       onBoFile(bo);
/*      */     case 20:
/*  195 */       onBoBrow(bo);
/*  196 */       break;
/*      */     case 33:
/*  199 */       onBoNodekey(bo);
/*  200 */       break;
/*      */     case 9:
/*  203 */       ButtonVO btnVo = (ButtonVO)bo.getData();
/*  204 */       onBoElse(btnVo.getBtnNo());
/*  205 */       break;
/*      */     case 3:
/*      */     case 4:
/*      */     case 5:
/*      */     case 6:
/*      */     case 7:
/*      */     case 8:
/*      */     case 11:
/*      */     case 12:
/*      */     case 13:
/*      */     case 14:
/*      */     case 15:
/*      */     case 17:
/*      */     case 18:
/*      */     case 19:
/*      */     case 21:
/*      */     case 22:
/*      */     case 23:
/*      */     case 24:
/*      */     case 26:
/*      */     case 27:
/*      */     case 28:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     default:
/*  208 */       if ((bo.getData() == null) || (!(bo.getData() instanceof ButtonVO))) return;
/*  209 */       btnVo = (ButtonVO)bo.getData();
/*  210 */       onBoElse(btnVo.getBtnNo());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected IBusinessController createBusinessAction()
/*      */   {
/*  221 */     switch (getUIController().getBusinessActionType())
/*      */     {
/*      */     case 0:
/*  223 */       return new BusinessAction(getBillUI());
/*      */     case 1:
/*  225 */       return new BdBusinessAction(getBillUI());
/*      */     }
/*  227 */     return new BusinessAction(getBillUI());
/*      */   }
/*      */ 
/*      */   protected abstract BillCardPanelWrapper getBillCardPanelWrapper();
/*      */ 
/*      */   protected final BusinessDelegator getBusiDelegator()
/*      */   {
/*  245 */     return getBillUI().getBusiDelegator();
/*      */   }
/*      */ 
/*      */   public final void initActionButton()
/*      */   {
/*      */     try
/*      */     {
/*  253 */       ButtonObject boAction = getButtonManager().getButton(25);
/*      */ 
/*  256 */       if (getBillUI().isBusinessType().booleanValue()) {
/*  257 */         ButtonObject boBusitype = getButtonManager().getButton(2);
/*      */ 
/*  259 */         ButtonObject boAdd = getButtonManager().getButton(1);
/*      */ 
/*  262 */         ButtonObject boSelectBusitype = null;
/*      */ 
/*  264 */         if (boBusitype != null) {
/*  265 */           getBusiDelegator().retBusinessBtn(boBusitype, _getCorp().getPrimaryKey(), getUIController().getBillType());
/*      */ 
/*  268 */           if ((boBusitype.getChildButtonGroup() != null) && (boBusitype.getChildButtonGroup().length > 0))
/*      */           {
/*  271 */             boBusitype.getChildButtonGroup()[0].setSelected(true);
/*  272 */             boBusitype.setCheckboxGroup(true);
/*      */ 
/*  274 */             ButtonObject bo = boBusitype.getChildButtonGroup()[0];
/*      */ 
/*  276 */             BusitypeVO vo = (BusitypeVO)bo.getData();
/*      */ 
/*  278 */             getBillUI().setBusinessType(vo.getPrimaryKey());
/*      */ 
/*  280 */             getBillUI().setBusicode(vo.getBusicode());
/*      */ 
/*  282 */             boSelectBusitype = boBusitype.getChildButtonGroup()[0];
/*      */ 
/*  285 */             if (boAction != null) {
/*  286 */               getBusiDelegator().retElseBtn(boAction, getUIController().getBillType(), "BOACTION");
/*      */             }
/*  288 */             initAssButton(boSelectBusitype);
/*      */           }
/*      */         }
/*      */ 
/*  292 */         if (boAdd != null) {
/*  293 */           getBusiDelegator().retAddBtn(boAdd, _getCorp().getPrimaryKey(), getUIController().getBillType(), boSelectBusitype);
/*      */         }
/*      */ 
/*      */       }
/*  328 */       else if (boAction != null) {
/*  329 */         getBusiDelegator().retElseBtn(boAction, getUIController().getBillType(), "BOACTION");
/*      */       }
/*      */ 
/*  333 */       if (boAction != null) {
/*  334 */         getButtonManager().setActionButtonVO(getBillUI().isSaveAndCommitTogether());
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  339 */       ex.printStackTrace();
/*  340 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000063"));
/*      */     }
/*      */   }
/*      */ 
/*      */   private void initAssButton(ButtonObject bo)
/*      */     throws Exception
/*      */   {
/*  350 */     ButtonObject boAss = getButtonManager().getButton(29);
/*  351 */     if (boAss == null)
/*  352 */       return;
/*  353 */     getBusiDelegator().retElseBtn(boAss, getUIController().getBillType(), "BOASS");
/*      */   }
/*      */ 
/*      */   public boolean isAdding()
/*      */   {
/*  363 */     return ((getBillUI().getBillOperate() == 1) || (getBillUI().getBillOperate() == 3));
/*      */   }
/*      */ 
/*      */   public boolean isEditing()
/*      */   {
/*  373 */     return (getBillUI().getBillOperate() == 0);
/*      */   }
/*      */ 
/*      */   public void onBillRef()
/*      */     throws Exception
/*      */   {
/*  380 */     ButtonObject btn = getButtonManager().getButton(9);
/*  381 */     btn.setTag(getBillUI().getRefBillType() + ":");
/*  382 */     onBoBusiTypeAdd(btn, null);
/*      */   }
/*      */ 
/*      */   private final void onBoAction(ButtonObject bo)
/*      */     throws Exception
/*      */   {
/*  394 */     ButtonVO btnVo = (ButtonVO)bo.getData();
/*  395 */     if (btnVo == null)
/*  396 */       return;
/*  397 */     switch (btnVo.getBtnNo())
/*      */     {
/*      */     case 26:
/*  399 */       onBoAudit();
/*  400 */       break;
/*      */     case 27:
/*  403 */       onBoCancelAudit();
/*  404 */       break;
/*      */     case 28:
/*  407 */       onBoCommit();
/*  408 */       break;
/*      */     case 4:
/*  411 */       onBoDel();
/*  412 */       break;
/*      */     default:
/*  415 */       onBoActionElse(bo);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void onBoActionElse(ButtonObject bo)
/*      */     throws Exception
/*      */   {
/*  433 */     AggregatedValueObject modelVo = getBufferData().getCurrentVOClone();
/*  434 */     int intBtn = 0;
/*  435 */     if (bo.getData() != null)
/*  436 */       intBtn = ((ButtonVO)bo.getData()).getBtnNo();
/*  437 */     beforeOnBoAction(intBtn, modelVo);
/*      */ 
/*  439 */     Object retObj = getBusinessAction().processAction(bo.getTag(), modelVo, getUIController().getBillType(), getBillUI()._getDate().toString(), getBillUI().getUserObject());
/*      */ 
/*  443 */     if (PfUtilClient.isSuccess()) {
/*  444 */       if (retObj instanceof AggregatedValueObject) {
/*  445 */         AggregatedValueObject retVo = (AggregatedValueObject)retObj;
/*  446 */         afterOnBoAction(intBtn, retVo);
/*  447 */         CircularlyAccessibleValueObject[] childVos = getChildVO(retVo);
/*  448 */         if (childVos == null)
/*  449 */           modelVo.setParentVO(retVo.getParentVO());
/*      */         else {
/*  451 */           modelVo = retVo;
/*      */         }
/*      */       }
/*  454 */       getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
/*  455 */       getBufferData().setCurrentRow(getBufferData().getCurrentRow());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void onBoAdd(ButtonObject bo)
/*      */     throws Exception
/*      */   {
/*  464 */     getBillUI().setBillOperate(1);
/*      */   }
/*      */ 
/*      */   private final void onBoAss(ButtonObject bo)
/*      */     throws Exception
/*      */   {
/*  471 */     beforeOnBoAss(bo);
/*  472 */     AggregatedValueObject modelVo = getBufferData().getCurrentVO();
/*  473 */     Object ret = getBusinessAction().processAction(bo.getTag(), modelVo, getUIController().getBillType(), getBillUI()._getDate().toString(), getBillUI().getUserObject());
/*      */ 
/*  476 */     if ((ret != null) && (ret instanceof AggregatedValueObject)) {
/*  477 */       AggregatedValueObject vo = (AggregatedValueObject)ret;
/*      */ 
/*  479 */       modelVo.getParentVO().setAttributeValue(getBillField().getField_BillStatus(), vo.getParentVO().getAttributeValue(getBillField().getField_BillStatus()));
/*      */ 
/*  484 */       modelVo.getParentVO().setAttributeValue("ts", vo.getParentVO().getAttributeValue("ts"));
/*      */ 
/*  487 */       getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
/*  488 */       getBufferData().setCurrentRow(getBufferData().getCurrentRow());
/*      */     }
/*  490 */     afterOnBoAss(bo);
/*      */   }
/*      */ 
/*      */   protected void onBoAssign()
/*      */     throws Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   public void onBoAudit()
/*      */     throws Exception
/*      */   {
/*  504 */     AggregatedValueObject modelVo = getBufferData().getCurrentVOClone();
/*  505 */     setCheckManAndDate(modelVo);
/*      */ 
/*  507 */     if (checkVOStatus(modelVo, new int[] { 1 })) {
/*  508 */       System.out.println("无效的鼠标处理机制");
/*  509 */       return;
/*      */     }
/*  511 */     beforeOnBoAction(26, modelVo);
/*      */ 
/*  513 */     AggregatedValueObject retVo = getBusinessAction().approve(modelVo, getUIController().getBillType(), getBillUI()._getDate().toString(), getBillUI().getUserObject());
/*      */ 
/*  518 */     if (!(PfUtilClient.isSuccess()))
/*      */       return;
/*  520 */     afterOnBoAction(26, retVo);
/*  521 */     CircularlyAccessibleValueObject[] childVos = getChildVO(retVo);
/*  522 */     if (childVos == null)
/*  523 */       modelVo.setParentVO(retVo.getParentVO());
/*      */     else {
/*  525 */       modelVo = retVo;
/*      */     }
/*  527 */     getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
/*  528 */     getBufferData().setCurrentRow(getBufferData().getCurrentRow());
/*      */   }
/*      */ 
/*      */   protected void onBoBodyQuery()
/*      */     throws Exception
/*      */   {
/*  539 */     StringBuffer strWhere = new StringBuffer();
/*      */ 
/*  541 */     if (!(askForBodyQueryCondition(strWhere)))
/*  542 */       return;
/*  543 */     doBodyQuery(strWhere.toString());
/*      */   }
/*      */ 
/*      */   protected void doBodyQuery(String strWhere)
/*      */     throws Exception, ClassNotFoundException, InstantiationException, IllegalAccessException
/*      */   {
/*  557 */     SuperVO[] queryVos = getBusiDelegator().queryByCondition(Class.forName(getUIController().getBillVoName()[2]), (strWhere == null) ? "" : strWhere);
/*      */ 
/*  561 */     getBufferData().clear();
/*      */ 
/*  563 */     AggregatedValueObject vo = (AggregatedValueObject)Class.forName(getUIController().getBillVoName()[0]).newInstance();
/*      */ 
/*  565 */     vo.setChildrenVO(queryVos);
/*  566 */     getBufferData().addVOToBuffer(vo);
/*      */ 
/*  568 */     updateBuffer();
/*      */   }
/*      */ 
/*      */   protected boolean askForBodyQueryCondition(StringBuffer sqlWhereBuf)
/*      */     throws Exception
/*      */   {
/*  578 */     if (sqlWhereBuf == null) {
/*  579 */       throw new IllegalArgumentException("askForQueryCondition().sqlWhereBuf cann't be null");
/*      */     }
/*  581 */     UIDialog querydialog = getQueryUI();
/*      */ 
/*  583 */     if (querydialog.showModal() != 1)
/*  584 */       return false;
/*  585 */     INormalQuery query = (INormalQuery)querydialog;
/*      */ 
/*  587 */     String strWhere = query.getWhereSql();
/*  588 */     if (strWhere == null) {
/*  589 */       strWhere = "1=1";
/*      */     }
/*  591 */     strWhere = "(" + strWhere + ") and (isnull(dr,0)=0)";
/*      */ 
/*  593 */     if (getUIController().getBodyCondition() != null) {
/*  594 */       strWhere = strWhere + " and " + getUIController().getBodyCondition();
/*      */     }
/*      */ 
/*  597 */     sqlWhereBuf.append(strWhere);
/*  598 */     return true;
/*      */   }
/*      */ 
/*      */   private void onBoBrow(ButtonObject bo)
/*      */     throws Exception
/*      */   {
/*  608 */     int intBtn = Integer.parseInt(bo.getTag());
/*      */ 
/*  610 */     buttonActionBefore(getBillUI(), intBtn);
/*  611 */     switch (intBtn)
/*      */     {
/*      */     case 21:
/*  613 */       getBufferData().first();
/*  614 */       break;
/*      */     case 23:
/*  617 */       getBufferData().prev();
/*  618 */       break;
/*      */     case 22:
/*  621 */       getBufferData().next();
/*  622 */       break;
/*      */     case 24:
/*  625 */       getBufferData().last();
/*      */     }
/*      */ 
/*  630 */     buttonActionAfter(getBillUI(), intBtn);
/*  631 */     getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000503", null, new String[] { Format.indexFormat(getBufferData().getCurrentRow() + 1) }));
/*      */   }
/*      */ 
/*      */   private final void onBoBusiType(ButtonObject bo)
/*      */     throws Exception
/*      */   {
/*  654 */     busiTypeBefore(getBillUI(), bo);
/*  655 */     bo.setSelected(true);
/*      */ 
/*  657 */     BusitypeVO vo = (BusitypeVO)bo.getData();
/*      */ 
/*  659 */     getBusiDelegator().retAddBtn(getButtonManager().getButton(1), _getCorp().getPrimaryKey(), getUIController().getBillType(), bo);
/*      */ 
/*  664 */     getBusiDelegator().retElseBtn(getButtonManager().getButton(25), getUIController().getBillType(), "BOACTION");
/*      */ 
/*  668 */     getButtonManager().setActionButtonVO(getBillUI().isSaveAndCommitTogether());
/*      */ 
/*  671 */     String oldtype = getBillUI().getBusinessType();
/*  672 */     String newtype = vo.getPrimaryKey();
/*  673 */     String oldcode = getBillUI().getBusicode();
/*  674 */     String newcode = vo.getBusicode();
/*      */ 
/*  677 */     getBillUI().setBusinessType(newtype);
/*      */ 
/*  679 */     getBillUI().setBusicode(newcode);
/*      */ 
/*  682 */     getBillUI().initUI();
/*      */ 
/*  684 */     getBillUI().getBufferData().clear();
/*  685 */     getBillUI().getBufferData().setCurrentRow(-1);
/*      */ 
/*  687 */     getBillUI().updateButtonUI();
/*      */ 
/*  689 */     if (this.m_bbl != null) {
/*  690 */       BusiTypeChangeEvent e = new BusiTypeChangeEvent(this, oldtype, newtype, oldcode, newcode);
/*      */ 
/*  692 */       this.m_bbl.busiTypeChange(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void onBoRefAdd(String strRefBillType, String sourceBillId)
/*      */     throws Exception
/*      */   {
/*  707 */     onBoBusiTypeAdd(getBusiDelegator().getRefButton(getButtonManager().getButton(1), strRefBillType), sourceBillId);
/*      */   }
/*      */ 
/*      */   private final void onBoBusiTypeAdd(ButtonObject bo, String sourceBillId)
/*      */     throws Exception
/*      */   {
/*  722 */     getBusiDelegator().childButtonClicked(bo, _getCorp().getPrimaryKey(), getBillUI()._getModuleCode(), _getOperator(), getUIController().getBillType(), getBillUI(), getBillUI().getUserObject(), sourceBillId);
/*      */ 
/*  726 */     if (PfUtilClient.makeFlag)
/*      */     {
/*  728 */       getBillUI().setCardUIState();
/*      */ 
/*  730 */       getBillUI().setBillOperate(1);
/*      */     }
/*  732 */     else if (PfUtilClient.isCloseOK()) {
/*  733 */       if (this.m_bbl != null) {
/*  734 */         String tmpString = bo.getTag();
/*  735 */         int findIndex = tmpString.indexOf(":");
/*  736 */         String newtype = tmpString.substring(0, findIndex);
/*  737 */         RefBillTypeChangeEvent e = new RefBillTypeChangeEvent(this, null, newtype);
/*      */ 
/*  739 */         this.m_bbl.refBillTypeChange(e);
/*      */       }
/*  741 */       if (isDataChange())
/*  742 */         setRefData(PfUtilClient.getRetVos());
/*      */       else
/*  744 */         setRefData(PfUtilClient.getRetOldVos());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void onBoCancel()
/*      */     throws Exception
/*      */   {
/*  754 */     if (getBufferData().isVOBufferEmpty()) {
/*  755 */       getBillUI().setBillOperate(4);
/*      */     } else {
/*  757 */       getBillUI().setBillOperate(2);
/*      */ 
/*  759 */       if (getBufferData().getCurrentRow() == -1)
/*  760 */         getBufferData().setCurrentRow(0);
/*      */       else
/*  762 */         getBufferData().setCurrentRow(getBufferData().getCurrentRow());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void onBoCancelAudit()
/*      */     throws Exception
/*      */   {
/*  774 */     AggregatedValueObject modelVo = getBufferData().getCurrentVOClone();
/*      */ 
/*  776 */     setCheckManAndDate(modelVo);
/*      */ 
/*  778 */     if (checkVOStatus(modelVo, new int[] { 8 })) {
/*  779 */       System.out.println("无效的鼠标处理机制");
/*  780 */       return;
/*      */     }
/*  782 */     beforeOnBoAction(27, modelVo);
/*      */ 
/*  784 */     AggregatedValueObject retVo = getBusinessAction().unapprove(modelVo, getUIController().getBillType(), getBillUI()._getDate().toString(), getBillUI().getUserObject());
/*      */ 
/*  789 */     if (PfUtilClient.isSuccess()) {
/*  790 */       afterOnBoAction(27, retVo);
/*  791 */       CircularlyAccessibleValueObject[] childVos = getChildVO(retVo);
/*  792 */       if (childVos == null)
/*  793 */         modelVo.setParentVO(retVo.getParentVO());
/*      */       else {
/*  795 */         modelVo = retVo;
/*      */       }
/*  797 */       Integer intState = (Integer)modelVo.getParentVO().getAttributeValue(getBillField().getField_BillStatus());
/*      */ 
/*  799 */       if (intState.intValue() == 8) {
/*  800 */         modelVo.getParentVO().setAttributeValue(getBillField().getField_CheckMan(), null);
/*      */ 
/*  802 */         modelVo.getParentVO().setAttributeValue(getBillField().getField_CheckDate(), null);
/*      */       }
/*      */ 
/*  806 */       getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
/*  807 */       getBufferData().setCurrentRow(getBufferData().getCurrentRow());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void onBoCard()
/*      */     throws Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void onBoCommit()
/*      */     throws Exception
/*      */   {
/*  824 */     AggregatedValueObject modelVo = getBufferData().getCurrentVOClone();
/*      */ 
/*  826 */     modelVo.getParentVO().setAttributeValue(getBillField().getField_Operator(), getBillUI()._getOperator());
/*      */ 
/*  828 */     beforeOnBoAction(28, modelVo);
/*  829 */     String strTime = ClientEnvironment.getServerTime().toString();
/*      */ 
/*  831 */     ArrayList retList = getBusinessAction().commit(modelVo, getUIController().getBillType(), getBillUI()._getDate().toString() + strTime.substring(10), getBillUI().getUserObject());
/*      */ 
/*  836 */     if (PfUtilClient.isSuccess()) {
/*  837 */       Object o = retList.get(1);
/*  838 */       if (o instanceof AggregatedValueObject) {
/*  839 */         AggregatedValueObject retVo = (AggregatedValueObject)o;
/*  840 */         afterOnBoAction(28, retVo);
/*  841 */         CircularlyAccessibleValueObject[] childVos = getChildVO(retVo);
/*  842 */         if (childVos == null)
/*  843 */           modelVo.setParentVO(retVo.getParentVO());
/*      */         else
/*  845 */           modelVo = retVo;
/*      */       }
/*  847 */       getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
/*  848 */       getBufferData().setCurrentRow(getBufferData().getCurrentRow());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void onBoCopy()
/*      */     throws Exception
/*      */   {
/*  857 */     AggregatedValueObject copyVo = getBufferData().getCurrentVOClone();
/*      */ 
/*  859 */     copyVo.getParentVO().setPrimaryKey(null);
/*  860 */     if (copyVo instanceof IExAggVO)
/*  861 */       clearChildPk(((IExAggVO)copyVo).getAllChildrenVO());
/*      */     else {
/*  863 */       clearChildPk(copyVo.getChildrenVO());
/*      */     }
/*      */ 
/*  866 */     getBillUI().setBillOperate(1);
/*      */ 
/*  868 */     String noField = getBillUI().getBillField().getField_BillNo();
/*  869 */     BillItem noitem = getBillCardPanelWrapper().getBillCardPanel().getHeadItem(noField);
/*      */ 
/*  871 */     if (noitem != null) {
/*  872 */       copyVo.getParentVO().setAttributeValue(noField, noitem.getValueObject());
/*      */     }
/*      */ 
/*  875 */     getBillUI().setCardUIData(copyVo);
/*      */   }
/*      */ 
/*      */   protected void onBoDel()
/*      */     throws Exception
/*      */   {
/*  882 */     AggregatedValueObject modelVo = getBufferData().getCurrentVO();
/*      */ 
/*  884 */     if (modelVo instanceof HYBillVO) {
/*  885 */       ((HYBillVO)modelVo).setM_billField(getBillField());
/*      */     }
/*  887 */     AggregatedValueObject delVo = getBusinessAction().delete(modelVo, getUIController().getBillType(), getBillUI()._getDate().toString(), getBillUI().getUserObject());
/*      */ 
/*  891 */     if (PfUtilClient.isSuccess()) {
/*  892 */       getBillUI().setBillOperate(2);
/*      */ 
/*  894 */       modelVo.getParentVO().setAttributeValue(getBillField().getField_BillStatus(), new Integer(4));
/*      */ 
/*  897 */       modelVo.getParentVO().setAttributeValue("ts", delVo.getParentVO().getAttributeValue("ts"));
/*      */ 
/*  899 */       getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
/*  900 */       getBufferData().setCurrentRow(getBufferData().getCurrentRow());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void onBoDelete()
/*      */     throws Exception
/*      */   {
/*  910 */     if (getBufferData().getCurrentVO() == null) {
/*  911 */       return;
/*      */     }
/*  913 */     if (MessageDialog.showOkCancelDlg(getBillUI(), NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000064"), NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000065"), 2) != 1)
/*      */     {
/*  919 */       return;
/*      */     }
/*  921 */     AggregatedValueObject modelVo = getBufferData().getCurrentVO();
/*  922 */     getBusinessAction().delete(modelVo, getUIController().getBillType(), getBillUI()._getDate().toString(), getBillUI().getUserObject());
/*      */ 
/*  924 */     if (PfUtilClient.isSuccess())
/*      */     {
/*  926 */       getBillUI().removeListHeadData(getBufferData().getCurrentRow());
/*  927 */       if (getUIController() instanceof ISingleController) {
/*  928 */         ISingleController sctl = (ISingleController)getUIController();
/*  929 */         if (!(sctl.isSingleDetail()))
/*  930 */           getBufferData().removeCurrentRow();
/*      */       } else {
/*  932 */         getBufferData().removeCurrentRow();
/*      */       }
/*      */     }
/*      */ 
/*  936 */     if (getBufferData().getVOBufferSize() == 0)
/*  937 */       getBillUI().setBillOperate(4);
/*      */     else
/*  939 */       getBillUI().setBillOperate(2);
/*  940 */     getBufferData().setCurrentRow(getBufferData().getCurrentRow());
/*      */   }
/*      */ 
/*      */   protected void onBoEdit()
/*      */     throws Exception
/*      */   {
/*  948 */     if (getBufferData().getCurrentVO() == null)
/*  949 */       return;
/*  950 */     String strTime = getBillUI()._getServerTime().toString();
/*  951 */     AggregatedValueObject modelVo = getBufferData().getCurrentVO();
/*  952 */     Object o = getBusinessAction().edit(modelVo, getUIController().getBillType(), getBillUI()._getDate().toString() + strTime.substring(10), null);
/*      */ 
/*  959 */     if (o instanceof AggregatedValueObject) {
/*  960 */       AggregatedValueObject retVo = (AggregatedValueObject)o;
/*  961 */       if (retVo.getChildrenVO() == null)
/*  962 */         modelVo.setParentVO(retVo.getParentVO());
/*      */       else
/*  964 */         modelVo = retVo;
/*  965 */       getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
/*  966 */       getBufferData().setCurrentRow(getBufferData().getCurrentRow());
/*      */     }
/*      */ 
/*  969 */     getBillUI().setBillOperate(0);
/*      */   }
/*      */ 
/*      */   protected void onBoElse(int intBtn)
/*      */     throws Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   private void onBoLine(ButtonObject bo)
/*      */     throws Exception
/*      */   {
/*  985 */     int intBtn = -1;
/*      */ 
/*  987 */     if ((bo.getData() != null) && (bo.getData() instanceof ButtonVO)) {
/*  988 */       ButtonVO btnVo = (ButtonVO)bo.getData();
/*  989 */       intBtn = btnVo.getBtnNo();
/*      */     } else {
/*  991 */       intBtn = Integer.parseInt(bo.getTag());
/*      */     }
/*      */ 
/*  995 */     buttonActionBefore(getBillUI(), intBtn);
/*      */ 
/*  997 */     getBillUI().showHintMessage(bo.getName());
/*      */ 
/*  999 */     switch (intBtn)
/*      */     {
/*      */     case 11:
/* 1001 */       onBoLineAdd();
/* 1002 */       break;
/*      */     case 12:
/* 1005 */       onBoLineDel();
/* 1006 */       break;
/*      */     case 13:
/* 1009 */       onBoLineCopy();
/* 1010 */       break;
/*      */     case 15:
/* 1013 */       onBoLineIns();
/* 1014 */       break;
/*      */     case 14:
/* 1017 */       onBoLinePaste();
/* 1018 */       break;
/*      */     case 54:
/* 1021 */       onBoLinePasteToTail();
/* 1022 */       break;
/*      */     default:
/* 1025 */       onBoElse(intBtn);
/*      */     }
/*      */ 
/* 1030 */     buttonActionAfter(getBillUI(), intBtn);
/*      */   }
/*      */ 
/*      */   protected void onBoLineAdd()
/*      */     throws Exception
/*      */   {
/* 1038 */     getBillCardPanelWrapper().addLine();
/*      */ 
/* 1042 */     postProcessOfAddNewLine();
/*      */   }
/*      */ 
/*      */   protected void postProcessOfAddNewLine()
/*      */   {
/*      */     try
/*      */     {
/* 1062 */       CircularlyAccessibleValueObject vo = processNewBodyVO(getBillCardPanelWrapper().getSelectedBodyVOs()[0]);
/*      */ 
/* 1065 */       int row = getBillCardPanelWrapper().getBillCardPanel().getBillTable().getSelectedRow();
/*      */ 
/* 1067 */       if (row == -1) {
/* 1068 */         row = getBillCardPanelWrapper().getBillCardPanel().getBillModel().getRowCount() - 1;
/*      */       }
/* 1070 */       if (row < 0)
/* 1071 */         throw new RuntimeException("cann't get selected row");
/* 1072 */       if (vo != null)
/* 1073 */         getBillCardPanelWrapper().getBillCardPanel().getBillModel().setBodyRowVO(vo, row);
/*      */     }
/*      */     catch (NullPointerException e) {
/* 1076 */       System.out.println("错误：增行或删行后没有获取到被选择的VO");
/* 1077 */       e.printStackTrace();
/*      */     } catch (ArrayIndexOutOfBoundsException e) {
/* 1079 */       System.out.println("错误：增行或删行后没有获取到被选择的VO");
/* 1080 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void onBoLineCopy()
/*      */     throws Exception
/*      */   {
/* 1088 */     getBillCardPanelWrapper().copySelectedLines();
/*      */   }
/*      */ 
/*      */   protected void onBoLineDel()
/*      */     throws Exception
/*      */   {
/* 1095 */     getBillCardPanelWrapper().deleteSelectedLines();
/*      */   }
/*      */ 
/*      */   protected void onBoLineIns()
/*      */     throws Exception
/*      */   {
/* 1103 */     getBillCardPanelWrapper().insertLine();
/* 1104 */     if (getBillCardPanelWrapper().getBillCardPanel().getRowCount() > 0)
/* 1105 */       postProcessOfAddNewLine();
/*      */   }
/*      */ 
/*      */   protected CircularlyAccessibleValueObject processNewBodyVO(CircularlyAccessibleValueObject newBodyVO)
/*      */   {
/* 1117 */     return newBodyVO;
/*      */   }
/*      */ 
/*      */   protected void onBoLinePaste()
/*      */     throws Exception
/*      */   {
/* 1126 */     processCopyedBodyVOsBeforePaste(getBillCardPanelWrapper().getCopyedBodyVOs());
/*      */ 
/* 1128 */     getBillCardPanelWrapper().pasteLines();
/*      */   }
/*      */ 
/*      */   protected void onBoLinePasteToTail()
/*      */     throws Exception
/*      */   {
/* 1137 */     processCopyedBodyVOsBeforePaste(getBillCardPanelWrapper().getCopyedBodyVOs());
/*      */ 
/* 1139 */     getBillCardPanelWrapper().pasteLinesToTail();
/*      */   }
/*      */ 
/*      */   protected void onBoPrint()
/*      */     throws Exception
/*      */   {
/* 1148 */     IDataSource dataSource = new CardPanelPRTS(getBillUI()._getModuleCode(), getBillCardPanelWrapper().getBillCardPanel());
/*      */ 
/* 1150 */     PrintEntry print = new PrintEntry(null, dataSource);
/*      */ 
/* 1152 */     print.setTemplateID(getBillUI()._getCorp().getPrimaryKey(), getBillUI()._getModuleCode(), getBillUI()._getOperator(), getBillUI().getBusinessType(), getBillUI().getNodeKey());
/*      */ 
/* 1155 */     if (print.selectTemplate() == 1)
/* 1156 */       print.preview();
/*      */   }
/*      */ 
/*      */   protected void onBoDirectPrint()
/*      */     throws Exception
/*      */   {
/* 1165 */     BillModel billmodel = getBillCardPanelWrapper().getBillCardPanel().getBillModel();
/*      */ 
/* 1167 */     if (billmodel == null)
/* 1168 */       return;
/* 1169 */     if (billmodel instanceof ReportTreeTableModelAdapter) {
/* 1170 */       ((ReportTreeTableModelAdapter)billmodel).setPrinting(true);
/*      */     }
/*      */ 
/* 1173 */     BillDirectPrint print = new BillDirectPrint(getBillCardPanelWrapper().getBillCardPanel(), getBillCardPanelWrapper().getBillCardPanel().getTitle());
/*      */ 
/* 1176 */     print.onPrint();
/*      */ 
/* 1178 */     if (billmodel instanceof ReportTreeTableModelAdapter)
/* 1179 */       ((ReportTreeTableModelAdapter)billmodel).setPrinting(false);
/*      */   }
/*      */ 
/*      */   protected void onBoQuery()
/*      */     throws Exception
/*      */   {
/* 1190 */     StringBuffer strWhere = new StringBuffer();
/*      */ 
/* 1192 */     if (!(askForQueryCondition(strWhere))) {
/* 1193 */       return;
/*      */     }
/* 1195 */     SuperVO[] queryVos = queryHeadVOs(strWhere.toString());
/*      */ 
/* 1197 */     getBufferData().clear();
/*      */ 
/* 1199 */     addDataToBuffer(queryVos);
/*      */ 
/* 1201 */     updateBuffer();
/*      */   }
/*      */ 
/*      */   protected SuperVO[] queryHeadVOs(String strWhere)
/*      */     throws Exception, ClassNotFoundException
/*      */   {
/* 1215 */     SuperVO[] queryVos = getBusiDelegator().queryHeadAllData(Class.forName(getUIController().getBillVoName()[1]), getUIController().getBillType(), strWhere.toString());
/*      */ 
/* 1218 */     return queryVos;
/*      */   }
/*      */ 
/*      */   protected boolean askForQueryCondition(StringBuffer sqlWhereBuf)
/*      */     throws Exception
/*      */   {
/* 1231 */     if (sqlWhereBuf == null) {
/* 1232 */       throw new IllegalArgumentException("askForQueryCondition().sqlWhereBuf cann't be null");
/*      */     }
/* 1234 */     UIDialog querydialog = getQueryUI();
/*      */ 
/* 1236 */     if (querydialog.showModal() != 1)
/* 1237 */       return false;
/* 1238 */     INormalQuery query = (INormalQuery)querydialog;
/*      */ 
/* 1240 */     String strWhere = query.getWhereSql();
/* 1241 */     if ((strWhere == null) || (strWhere.trim().length() == 0)) {
/* 1242 */       strWhere = "1=1";
/*      */     }
/* 1244 */     if (getButtonManager().getButton(2) != null) {
/* 1245 */       if (getBillIsUseBusiCode().booleanValue())
/*      */       {
/* 1247 */         strWhere = "(" + strWhere + ") and " + getBillField().getField_BusiCode() + "='" + getBillUI().getBusicode() + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1253 */         strWhere = "(" + strWhere + ") and " + getBillField().getField_Busitype() + "='" + getBillUI().getBusinessType() + "'";
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1259 */     strWhere = "(" + strWhere + ") and (isnull(dr,0)=0)";
/*      */ 
/* 1261 */     if (getHeadCondition() != null) {
/* 1262 */       strWhere = strWhere + " and " + getHeadCondition();
/*      */     }
/* 1264 */     sqlWhereBuf.append(strWhere);
/* 1265 */     return true;
/*      */   }
/*      */ 
/*      */   protected final void updateBuffer()
/*      */     throws Exception
/*      */   {
/* 1277 */     if (getBufferData().getVOBufferSize() != 0)
/*      */     {
/* 1279 */       getBillUI().setListHeadData(getBufferData().getAllHeadVOsFromBuffer());
/*      */ 
/* 1281 */       getBillUI().setBillOperate(2);
/* 1282 */       getBufferData().setCurrentRow(0);
/*      */     } else {
/* 1284 */       getBillUI().setListHeadData(null);
/* 1285 */       getBillUI().setBillOperate(4);
/* 1286 */       getBufferData().setCurrentRow(-1);
/* 1287 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000066"));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void addDataToBuffer(SuperVO[] queryVos)
/*      */     throws Exception
/*      */   {
/* 1297 */     if (queryVos == null) {
/* 1298 */       getBufferData().clear();
/* 1299 */       return;
/*      */     }
/* 1301 */     for (int i = 0; i < queryVos.length; ++i) {
/* 1302 */       AggregatedValueObject aVo = (AggregatedValueObject)Class.forName(getUIController().getBillVoName()[0]).newInstance();
/*      */ 
/* 1304 */       aVo.setParentVO(queryVos[i]);
/* 1305 */       getBufferData().addVOToBuffer(aVo);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void onBoRefresh()
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/* 1315 */       getBufferData().refresh();
/*      */     }
/*      */     catch (RecordNotFoundExcetption e) {
/* 1318 */       if (getBillUI().showYesNoMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000185")) == 4)
/*      */       {
/*      */         return;
/*      */       }
/*      */ 
/* 1324 */       getBufferData().removeCurrentRow();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void onBoReturn()
/*      */     throws Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void onBoSave()
/*      */     throws Exception
/*      */   {
/* 1342 */     AggregatedValueObject billVO = getBillUI().getChangedVOFromUI();
/* 1343 */     setTSFormBufferToVO(billVO);
/* 1344 */     AggregatedValueObject checkVO = getBillUI().getVOFromUI();
/* 1345 */     setTSFormBufferToVO(checkVO);
/*      */ 
/* 1347 */     Object o = null;
/* 1348 */     ISingleController sCtrl = null;
/* 1349 */     if (getUIController() instanceof ISingleController) {
/* 1350 */       sCtrl = (ISingleController)getUIController();
/* 1351 */       if (sCtrl.isSingleDetail()) {
/* 1352 */         o = billVO.getParentVO();
/* 1353 */         billVO.setParentVO(null);
/*      */       } else {
/* 1355 */         o = billVO.getChildrenVO();
/* 1356 */         billVO.setChildrenVO(null);
/*      */       }
/*      */     }
/*      */ 
/* 1360 */     boolean isSave = true;
/*      */ 
/* 1363 */     if ((billVO.getParentVO() == null) && (((billVO.getChildrenVO() == null) || (billVO.getChildrenVO().length == 0))))
/*      */     {
/* 1365 */       isSave = false;
/*      */     }
/* 1367 */     else if (getBillUI().isSaveAndCommitTogether()) {
/* 1368 */       billVO = getBusinessAction().saveAndCommit(billVO, getUIController().getBillType(), _getDate().toString(), getBillUI().getUserObject(), checkVO);
/*      */     }
/*      */     else
/*      */     {
/* 1374 */       billVO = getBusinessAction().save(billVO, getUIController().getBillType(), _getDate().toString(), getBillUI().getUserObject(), checkVO);
/*      */     }
/*      */ 
/* 1380 */     if ((sCtrl != null) && 
/* 1381 */       (sCtrl.isSingleDetail())) {
/* 1382 */       billVO.setParentVO((CircularlyAccessibleValueObject)o);
/*      */     }
/* 1384 */     int nCurrentRow = -1;
/* 1385 */     if (isSave) {
/* 1386 */       if (isEditing()) {
/* 1387 */         if (getBufferData().isVOBufferEmpty()) {
/* 1388 */           getBufferData().addVOToBuffer(billVO);
/* 1389 */           nCurrentRow = 0;
/*      */         }
/*      */         else {
/* 1392 */           getBufferData().setCurrentVO(billVO);
/* 1393 */           nCurrentRow = getBufferData().getCurrentRow();
/*      */         }
/*      */       } else {
/* 1396 */         getBufferData().addVOsToBuffer(new AggregatedValueObject[] { billVO });
/*      */ 
/* 1398 */         nCurrentRow = getBufferData().getVOBufferSize() - 1;
/*      */       }
/*      */     }
/*      */ 
/* 1402 */     if (nCurrentRow >= 0) {
/* 1403 */       getBufferData().setCurrentRowWithOutTriggerEvent(nCurrentRow);
/*      */     }
/*      */ 
/* 1406 */     setAddNewOperate(isAdding(), billVO);
/*      */ 
/* 1409 */     setSaveOperateState();
/*      */ 
/* 1411 */     if (nCurrentRow >= 0)
/* 1412 */       getBufferData().setCurrentRow(nCurrentRow);
/*      */   }
/*      */ 
/*      */   protected void onBusinessException(BusinessException e)
/*      */   {
/* 1420 */     MessageDialog.showErrorDlg(getBillUI(), null, e.getMessage());
/* 1421 */     Logger.error(e.getMessage(), e);
/*      */   }
/*      */ 
/*      */   public void onButton(ButtonObject bo)
/*      */   {
/* 1432 */     if ((((getBillUI().getBillOperate() == 1) || (getBillUI().getBillOperate() == 0))) && 
/* 1434 */       (getBillCardPanelWrapper() != null)) {
/* 1435 */       getBillCardPanelWrapper().getBillCardPanel().stopEditing();
/*      */     }
/*      */     try
/*      */     {
/* 1439 */       ButtonObject parentBtn = bo.getParent();
/*      */ 
/* 1441 */       if ((parentBtn != null) && (Integer.parseInt(parentBtn.getTag()) < 100)) {
/* 1442 */         int intParentBtn = Integer.parseInt(parentBtn.getTag());
/* 1443 */         complexOnButton(intParentBtn, bo);
/*      */       } else {
/* 1445 */         if (bo.getTag() == null)
/* 1446 */           System.out.println("新增按钮必须设置TAG,TAG>100的整数.....");
/* 1447 */         int intBtn = Integer.parseInt(bo.getTag());
/* 1448 */         if (intBtn > 100) {
/* 1449 */           onBoElse(intBtn);
/*      */         }
/*      */         else
/* 1452 */           simpleOnButton(intBtn, bo);
/*      */       }
/*      */     } catch (BusinessException ex) {
/* 1455 */       onBusinessException(ex);
/*      */     } catch (SQLException ex) {
/* 1457 */       getBillUI().showErrorMessage(ex.getMessage());
/*      */     } catch (Exception e) {
/* 1459 */       getBillUI().showErrorMessage(e.getMessage());
/* 1460 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected AggregatedValueObject[] onSplitBillVos(AggregatedValueObject[] refVos)
/*      */     throws Exception
/*      */   {
/* 1474 */     return null;
/*      */   }
/*      */ 
/*      */   protected void processCopyedBodyVOsBeforePaste(CircularlyAccessibleValueObject[] vos)
/*      */   {
/* 1485 */     if (vos == null) {
/* 1486 */       return;
/*      */     }
/* 1488 */     for (int i = 0; i < vos.length; ++i) {
/* 1489 */       vos[i].setAttributeValue(getUIController().getPkField(), null);
/* 1490 */       vos[i].setAttributeValue(getUIController().getChildPkField(), null);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected AggregatedValueObject refVOChange(AggregatedValueObject[] vos)
/*      */     throws Exception
/*      */   {
/* 1501 */     return vos[0];
/*      */   }
/*      */ 
/*      */   private void setCheckManAndDate(AggregatedValueObject vo) throws Exception
/*      */   {
/* 1506 */     vo.getParentVO().setAttributeValue(getBillField().getField_CheckDate(), getBillUI()._getDate());
/*      */ 
/* 1508 */     vo.getParentVO().setAttributeValue(getBillField().getField_CheckMan(), getBillUI()._getOperator());
/*      */   }
/*      */ 
/*      */   private void simpleOnButton(int intBtn, ButtonObject bo)
/*      */     throws Exception
/*      */   {
/* 1522 */     buttonActionBefore(getBillUI(), intBtn);
/* 1523 */     switch (intBtn)
/*      */     {
/*      */     case 1:
/* 1525 */       if (getBillUI().isBusinessType().booleanValue()) return;
/* 1526 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000061"));
/*      */ 
/* 1532 */       onBoAdd(bo);
/*      */ 
/* 1534 */       buttonActionAfter(getBillUI(), intBtn);
/* 1535 */       break;
/*      */     case 3:
/* 1539 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000067"));
/*      */ 
/* 1544 */       onBoEdit();
/*      */ 
/* 1546 */       buttonActionAfter(getBillUI(), intBtn);
/* 1547 */       break;
/*      */     case 4:
/* 1550 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000068"));
/*      */ 
/* 1555 */       onBoDel();
/*      */ 
/* 1557 */       buttonActionAfter(getBillUI(), intBtn);
/* 1558 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000069"));
/*      */ 
/* 1562 */       break;
/*      */     case 32:
/* 1565 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000070"));
/*      */ 
/* 1570 */       onBoDelete();
/*      */ 
/* 1572 */       buttonActionAfter(getBillUI(), intBtn);
/* 1573 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000071"));
/*      */ 
/* 1577 */       break;
/*      */     case 5:
/* 1580 */       getBillUI().showHintMessage(bo.getName());
/*      */ 
/* 1582 */       if (super.getUIController() instanceof ISingleController) {
/* 1583 */         ISingleController strl = (ISingleController)super.getUIController();
/*      */ 
/* 1585 */         if (strl.isSingleDetail())
/* 1586 */           onBoBodyQuery();
/*      */         else
/* 1588 */           onBoQuery();
/*      */       } else {
/* 1590 */         onBoQuery();
/*      */       }
/* 1592 */       buttonActionAfter(getBillUI(), intBtn);
/*      */ 
/* 1594 */       break;
/*      */     case 0:
/* 1597 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000072"));
/*      */ 
/* 1602 */       onBoSave();
/*      */ 
/* 1604 */       buttonActionAfter(getBillUI(), intBtn);
/* 1605 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000073"));
/*      */ 
/* 1609 */       break;
/*      */     case 7:
/* 1612 */       onBoCancel();
/*      */ 
/* 1614 */       getBillUI().showHintMessage("");
/*      */ 
/* 1616 */       buttonActionAfter(getBillUI(), intBtn);
/* 1617 */       break;
/*      */     case 6:
/* 1620 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000074"));
/*      */ 
/* 1625 */       onBoPrint();
/*      */ 
/* 1627 */       buttonActionAfter(getBillUI(), intBtn);
/* 1628 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000075"));
/*      */ 
/* 1632 */       break;
/*      */     case 50:
/* 1635 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000074"));
/*      */ 
/* 1640 */       onBoDirectPrint();
/*      */ 
/* 1642 */       buttonActionAfter(getBillUI(), intBtn);
/* 1643 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000075"));
/*      */ 
/* 1647 */       break;
/*      */     case 31:
/* 1651 */       onBoReturn();
/*      */ 
/* 1653 */       buttonActionAfter(getBillUI(), intBtn);
/* 1654 */       break;
/*      */     case 30:
/* 1657 */       onBoCard();
/*      */ 
/* 1659 */       buttonActionAfter(getBillUI(), intBtn);
/* 1660 */       break;
/*      */     case 8:
/* 1663 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000076"));
/*      */ 
/* 1668 */       onBoRefresh();
/*      */ 
/* 1670 */       buttonActionAfter(getBillUI(), intBtn);
/* 1671 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000077"));
/*      */ 
/* 1676 */       break;
/*      */     case 9:
/* 1679 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000078"));
/*      */ 
/* 1684 */       onBillRef();
/*      */ 
/* 1686 */       buttonActionAfter(getBillUI(), intBtn);
/* 1687 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000079"));
/*      */ 
/* 1691 */       break;
/*      */     case 41:
/* 1694 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000080"));
/*      */ 
/* 1699 */       onBoCopy();
/*      */ 
/* 1701 */       buttonActionAfter(getBillUI(), intBtn);
/* 1702 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000081"));
/*      */ 
/* 1706 */       break;
/*      */     case 26:
/* 1715 */       onBoAudit();
/*      */ 
/* 1722 */       break;
/*      */     case 27:
/* 1731 */       onBoCancelAudit();
/*      */ 
/* 1738 */       break;
/*      */     case 28:
/* 1747 */       onBoCommit();
/*      */ 
/* 1754 */       break;
/*      */     case 35:
/* 1764 */       onBoSelAll();
/*      */ 
/* 1772 */       break;
/*      */     case 36:
/* 1781 */       onBoSelNone();
/*      */ 
/* 1788 */       break;
/*      */     case 51:
/* 1797 */       onBoImport();
/*      */ 
/* 1804 */       break;
/*      */     case 52:
/* 1813 */       onBoExport();
/*      */ 
/* 1820 */       break;
/*      */     case 53:
/* 1823 */       onBoApproveInfo();
/* 1824 */       break;
/*      */     case 2:
/*      */     case 10:
/*      */     case 11:
/*      */     case 12:
/*      */     case 13:
/*      */     case 14:
/*      */     case 15:
/*      */     case 16:
/*      */     case 17:
/*      */     case 18:
/*      */     case 19:
/*      */     case 20:
/*      */     case 21:
/*      */     case 22:
/*      */     case 23:
/*      */     case 24:
/*      */     case 25:
/*      */     case 29:
/*      */     case 33:
/*      */     case 34:
/*      */     case 37:
/*      */     case 38:
/*      */     case 39:
/*      */     case 40:
/*      */     case 42:
/*      */     case 43:
/*      */     case 44:
/*      */     case 45:
/*      */     case 46:
/*      */     case 47:
/*      */     case 48:
/*      */     case 49:
/*      */     default:
/* 1834 */       onBoActionElse(bo);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void onBoApproveInfo()
/*      */     throws Exception
/*      */   {
/* 1849 */     String strBilltype = getBillField().getField_Billtype();
/* 1850 */     String billType = (String)getBufferData().getCurrentVO().getParentVO().getAttributeValue(strBilltype);
/*      */ 
/* 1852 */     String billId = getBufferData().getCurrentVO().getParentVO().getPrimaryKey();
/*      */ 
/* 1856 */     if (StringUtil.isEmptyWithTrim(billType)) {
/* 1857 */       billType = getUIController().getBillType();
/*      */     }
/* 1859 */     FlowStateDlg dlg = new FlowStateDlg(getBillUI().getParent(), billType, "KHHH0000000000000001", billId);
/*      */ 
/* 1861 */     dlg.setVisible(true);
/*      */   }
/*      */ 
/*      */   protected void onBoExport()
/*      */     throws Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void onBoImport()
/*      */     throws Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void addBillBusiListener(IBillBusiListener bbl)
/*      */   {
/* 1977 */     this.m_bbl = bbl;
/*      */   }
/*      */ 
/*      */   protected boolean checkVOStatus(AggregatedValueObject vo, int[] intStatus)
/*      */     throws Exception
/*      */   {
/* 1990 */     if ((vo == null) || (vo.getParentVO() == null))
/* 1991 */       return true;
/* 1992 */     Integer intState = (Integer)vo.getParentVO().getAttributeValue(getBillField().getField_BillStatus());
/*      */ 
/* 1994 */     if (intState == null) {
/* 1995 */       throw new BusinessException(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000082"));
/*      */     }
/*      */ 
/* 2000 */     int intCurrentState = intState.intValue();
/* 2001 */     for (int i = 0; i < intStatus.length; ++i) {
/* 2002 */       if (intStatus[i] == intCurrentState)
/* 2003 */         return true;
/*      */     }
/* 2005 */     return false;
/*      */   }
/*      */ 
/*      */   protected IBusinessSplit createBusinessSplit()
/*      */   {
/* 2012 */     return new DefaultBusinessSplit();
/*      */   }
/*      */ 
/*      */   public UFBoolean getBillIsUseBusiCode()
/*      */   {
/* 2022 */     return new UFBoolean(false);
/*      */   }
/*      */ 
/*      */   private CircularlyAccessibleValueObject[] getChildVO(AggregatedValueObject retVo)
/*      */   {
/* 2032 */     CircularlyAccessibleValueObject[] childVos = null;
/* 2033 */     if (retVo instanceof IExAggVO)
/* 2034 */       childVos = ((IExAggVO)retVo).getAllChildrenVO();
/*      */     else
/* 2036 */       childVos = retVo.getChildrenVO();
/* 2037 */     return childVos;
/*      */   }
/*      */ 
/*      */   protected String getHeadCondition()
/*      */   {
/* 2047 */     if ((getBillCardPanelWrapper() != null) && 
/* 2048 */       (getBillCardPanelWrapper().getBillCardPanel().getHeadTailItem(getBillField().getField_Corp()) != null))
/*      */     {
/* 2050 */       return getBillField().getField_Corp() + "='" + getBillUI()._getCorp().getPrimaryKey() + "'";
/*      */     }
/* 2052 */     return null;
/*      */   }
/*      */ 
/*      */   public final void initNodeKeyButton()
/*      */   {
/*      */     try
/*      */     {
/* 2060 */       ButtonObject boNodeKey = getButtonManager().getButton(33);
/*      */ 
/* 2063 */       if (boNodeKey != null)
/* 2064 */         if ((boNodeKey.getChildButtonGroup() != null) && (boNodeKey.getChildButtonGroup().length > 0))
/*      */         {
/* 2066 */           ButtonObject bo = boNodeKey.getChildButtonGroup()[0];
/* 2067 */           bo.setSelected(true);
/*      */ 
/* 2070 */           getBillUI().setNodeKey(bo.getTag());
/*      */         } else {
/* 2072 */           System.out.println("没有初始化NodeKey类型!");
/*      */         }
/*      */     } catch (Exception ex) {
/* 2075 */       ex.printStackTrace();
/* 2076 */       getBillUI().showHintMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000083"));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean isDataChange()
/*      */   {
/* 2090 */     return true;
/*      */   }
/*      */ 
/*      */   protected void onBillRef(int intBtn, String refBilltype)
/*      */     throws Exception
/*      */   {
/* 2097 */     ButtonObject btn = getButtonManager().getButton(intBtn);
/* 2098 */     btn.setTag(refBilltype + ":");
/* 2099 */     onBoBusiTypeAdd(btn, null);
/*      */   }
/*      */ 
/*      */   private final void onBoNodekey(ButtonObject bo)
/*      */     throws Exception
/*      */   {
/* 2106 */     bo.setSelected(true);
/*      */ 
/* 2108 */     getBillUI().setNodeKey(bo.getTag());
/*      */ 
/* 2111 */     getBillUI().initUI();
/*      */ 
/* 2113 */     setQueryUI(null);
/*      */ 
/* 2115 */     getBillUI().getBufferData().clear();
/* 2116 */     getBillUI().getBufferData().setCurrentRow(-1);
/*      */ 
/* 2118 */     getBillUI().updateButtonUI();
/*      */   }
/*      */ 
/*      */   protected void onBoSelAll()
/*      */     throws Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void onBoSelNone()
/*      */     throws Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void removeBillBusiListener()
/*      */   {
/* 2141 */     this.m_bbl = null;
/*      */   }
/*      */ 
/*      */   protected void setAddNewOperate(boolean isAdding, AggregatedValueObject billVO)
/*      */     throws Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void setRefData(AggregatedValueObject[] vos)
/*      */     throws Exception
/*      */   {
/* 2161 */     getBillUI().setCardUIState();
/*      */ 
/* 2163 */     AggregatedValueObject vo = refVOChange(vos);
/* 2164 */     if (vo == null) {
/* 2165 */       throw new BusinessException(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000084"));
/*      */     }
/*      */ 
/* 2171 */     getBillUI().setBillOperate(3);
/*      */ 
/* 2173 */     getBillCardPanelWrapper().setCardData(vo);
/*      */   }
/*      */ 
/*      */   protected void setSaveOperateState()
/*      */     throws Exception
/*      */   {
/* 2180 */     getBillUI().setBillOperate(2);
/*      */   }
/*      */ 
/*      */   protected void setTSFormBufferToVO(AggregatedValueObject setVo)
/*      */     throws Exception
/*      */   {
/* 2193 */     if (setVo == null)
/* 2194 */       return;
/* 2195 */     AggregatedValueObject vo = getBufferData().getCurrentVO();
/* 2196 */     if (vo == null)
/* 2197 */       return;
/* 2198 */     if (getBillUI().getBillOperate() != 0)
/*      */       return;
/* 2200 */     if ((vo.getParentVO() != null) && (setVo.getParentVO() != null)) {
/* 2201 */       setVo.getParentVO().setAttributeValue("ts", vo.getParentVO().getAttributeValue("ts"));
/*      */     }
/*      */ 
/* 2204 */     SuperVO[] changedvos = (SuperVO[])(SuperVO[])getChildVO(setVo);
/*      */ 
/* 2206 */     if ((changedvos == null) || (changedvos.length == 0))
/*      */       return;
/* 2208 */     HashMap bufferedVOMap = null;
/*      */ 
/* 2210 */     SuperVO[] bufferedVOs = (SuperVO[])(SuperVO[])getChildVO(vo);
/* 2211 */     if ((bufferedVOs != null) && (bufferedVOs.length != 0)) {
/* 2212 */       bufferedVOMap = Hashlize.hashlizeObjects(bufferedVOs, new VOHashPrimaryKeyAdapter());
/*      */ 
/* 2214 */       for (int i = 0; i < changedvos.length; ++i)
/* 2215 */         if (changedvos[i].getPrimaryKey() != null) {
/* 2216 */           ArrayList bufferedAl = (ArrayList)bufferedVOMap.get(changedvos[i].getPrimaryKey());
/*      */ 
/* 2219 */           if (bufferedAl != null) {
/* 2220 */             SuperVO bufferedVO = (SuperVO)bufferedAl.get(0);
/*      */ 
/* 2222 */             changedvos[i].setAttributeValue("ts", bufferedVO.getAttributeValue("ts"));
/*      */           }
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   public IBillBusiListener getBillBusiListener()
/*      */   {
/* 2233 */     return this.m_bbl;
/*      */   }
/*      */ }
 