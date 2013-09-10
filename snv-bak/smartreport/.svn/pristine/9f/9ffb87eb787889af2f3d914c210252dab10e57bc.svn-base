/*     */ package nc.ui.trade.manage;
/*     */ 
/*     */ import javax.swing.ListSelectionModel;
/*     */ import nc.ui.pub.ButtonObject;
/*     */ import nc.ui.pub.beans.UITable;
/*     */ import nc.ui.pub.bill.BillListData;
/*     */ import nc.ui.pub.bill.BillListPanel;
/*     */ import nc.ui.pub.print.IDataSource;
/*     */ import nc.ui.pub.print.PrintEntry;
/*     */ import nc.ui.trade.base.AbstractBillUI;
/*     */ import nc.ui.trade.bill.AbstractManageController;
/*     */ import nc.ui.trade.bill.BillCardPanelWrapper;
/*     */ import nc.ui.trade.bill.BillEventHandler;
/*     */ import nc.ui.trade.buffer.BillUIBuffer;
/*     */ import nc.ui.trade.controller.IControllerBase;
/*     */ import nc.ui.trade.pub.BillDirectPrint;
/*     */ import nc.ui.trade.pub.ListPanelPRTS;
/*     */ import nc.vo.bd.CorpVO;
/*     */ import nc.vo.pub.AggregatedValueObject;
/*     */ 
/*     */ public class ManageEventHandler extends BillEventHandler
/*     */ {
/*     */   public ManageEventHandler(BillManageUI billUI, IControllerBase control)
/*     */   {
/*  29 */     super(billUI, control);
/*     */   }
/*     */ 
/*     */   protected BillCardPanelWrapper getBillCardPanelWrapper()
/*     */   {
/*  38 */     return getBillManageUI().getBillCardWrapper();
/*     */   }
/*     */ 
/*     */   protected void onBoDirectPrint()
/*     */     throws Exception
/*     */   {
/*  47 */     if (getBillManageUI().isListPanelSelected()) {
/*  48 */       BillDirectPrint print = new BillDirectPrint(getBillManageUI().getBillListPanel(), getBillManageUI().getBillListPanel().getBillListData().getTitle());
/*  49 */       print.onPrint();
/*     */     }
/*     */     else {
/*  52 */       super.onBoDirectPrint();
/*     */     }
/*     */   }
/*     */ 
/*     */   private BillManageUI getBillManageUI()
/*     */   {
/*  62 */     return ((BillManageUI)getBillUI());
/*     */   }
/*     */ 
/*     */   protected BillUIBuffer getBufferData()
/*     */   {
/*  71 */     return getBillManageUI().getBufferData();
/*     */   }
/*     */ 
/*     */   protected AbstractManageController getUIManageController()
/*     */   {
/*  80 */     return ((AbstractManageController)getUIController());
/*     */   }
/*     */ 
/*     */   public void onBoActionElse(ButtonObject bo)
/*     */     throws Exception
/*     */   {
/*  87 */     super.onBoActionElse(bo);
/*  88 */     getBillManageUI().updateListVo();
/*     */   }
/*     */ 
/*     */   public void onBoAdd(ButtonObject bo)
/*     */     throws Exception
/*     */   {
/*  95 */     if (getBillManageUI().isListPanelSelected())
/*  96 */       getBillManageUI().setCurrentPanel("CARDPANEL");
/*  97 */     super.onBoAdd(bo);
/*     */   }
/*     */ 
/*     */   public void onBoAudit()
/*     */     throws Exception
/*     */   {
/* 104 */     super.onBoAudit();
/* 105 */     getBillManageUI().updateListVo();
/*     */   }
/*     */ 
/*     */   protected void onBoCancelAudit()
/*     */     throws Exception
/*     */   {
/* 112 */     super.onBoCancelAudit();
/* 113 */     getBillManageUI().updateListVo();
/*     */   }
/*     */ 
/*     */   protected void onBoCard()
/*     */     throws Exception
/*     */   {
/* 121 */     getBillManageUI().setCurrentPanel("CARDPANEL");
/* 122 */     getBufferData().updateView();
/*     */   }
/*     */ 
/*     */   protected void onBoCommit()
/*     */     throws Exception
/*     */   {
/* 129 */     super.onBoCommit();
/* 130 */     getBillManageUI().updateListVo();
/*     */   }
/*     */ 
/*     */   protected void onBoCopy()
/*     */     throws Exception
/*     */   {
/* 137 */     getBillManageUI().setCurrentPanel("CARDPANEL");
/* 138 */     super.onBoCopy();
/*     */   }
/*     */ 
/*     */   protected void onBoDel()
/*     */     throws Exception
/*     */   {
/* 145 */     super.onBoDel();
/* 146 */     getBillManageUI().updateListVo();
/*     */   }
/*     */ 
/*     */   protected void onBoEdit()
/*     */     throws Exception
/*     */   {
/* 153 */     if (getBillManageUI().isListPanelSelected()) {
/* 154 */       getBillManageUI().setCurrentPanel("CARDPANEL");
/* 155 */       getBufferData().updateView();
/*     */     }
/* 157 */     super.onBoEdit();
/*     */   }
/*     */ 
/*     */   protected void onBoRefresh()
/*     */     throws Exception
/*     */   {
/* 165 */     int count_before_refresh = getBufferData().getVOBufferSize();
/* 166 */     super.onBoRefresh();
/* 167 */     int count_after_refresh = getBufferData().getVOBufferSize();
/* 168 */     if (count_before_refresh != count_after_refresh)
/*     */     {
/* 170 */       if (count_after_refresh == 0)
/*     */       {
/* 172 */         getBillManageUI().setListHeadData(null);
/*     */       }
/*     */       else
/*     */       {
/* 176 */         getBillManageUI().setListHeadData(getBufferData().getAllHeadVOsFromBuffer());
/*     */       }
/*     */     }
/*     */     else
/* 180 */       getBillManageUI().updateListVo();
/*     */   }
/*     */ 
/*     */   protected void onBoReturn()
/*     */     throws Exception
/*     */   {
/* 187 */     getBillManageUI().setCurrentPanel("LISTPANEL");
/* 188 */     getBufferData().updateView();
/*     */ 
/* 190 */     if (getBufferData().getCurrentRow() == -1)
/*     */       return;
/* 192 */     int selectedRow = getBufferData().getCurrentRow();
/* 193 */     UITable table = getBillManageUI().getBillListPanel().getHeadTable();
/*     */ 
/* 195 */     table.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
/* 196 */     table.scrollRectToVisible(table.getCellRect(getBufferData().getCurrentRow(), 0, true));
/*     */   }
/*     */ 
/*     */   protected void onBoPrint()
/*     */     throws Exception
/*     */   {
/* 204 */     if (getBillManageUI().isListPanelSelected()) {
/* 205 */       IDataSource dataSource = new ListPanelPRTS(getBillUI()._getModuleCode(), ((BillManageUI)getBillUI()).getBillListPanel());
/*     */ 
/* 207 */       PrintEntry print = new PrintEntry(null, dataSource);
/*     */ 
/* 209 */       print.setTemplateID(getBillUI()._getCorp().getPrimaryKey(), getBillUI()._getModuleCode(), getBillUI()._getOperator(), getBillUI().getBusinessType(), getBillUI().getNodeKey());
/*     */ 
/* 212 */       if (print.selectTemplate() == 1)
/* 213 */         print.preview();
/*     */     }
/*     */     else
/*     */     {
/* 217 */       super.onBoPrint();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void setAddNewOperate(boolean isAdding, AggregatedValueObject billVO)
/*     */     throws Exception
/*     */   {
/* 226 */     super.setAddNewOperate(isAdding, billVO);
/* 227 */     getBillManageUI().setSaveListData(isAdding);
/*     */   }
/*     */ 
/*     */   protected void onBoSave() throws Exception
/*     */   {
/* 232 */     super.onBoSave();
/*     */   }
/*     */ }

 