/*      */ package nc.ui.trade.manage;
/*      */ 
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.CardLayout;
/*      */ import java.io.PrintStream;
/*      */ import java.util.List;
/*      */ import java.util.Observable;
/*      */ import java.util.Observer;
/*      */ import javax.swing.ListSelectionModel;
/*      */ import javax.swing.SwingUtilities;
/*      */ import nc.ui.ml.NCLangRes;
/*      */ import nc.ui.pf.query.ICheckRetVO;
/*      */ import nc.ui.pub.ButtonObject;
/*      */ import nc.ui.pub.FramePanel;
/*      */ import nc.ui.pub.beans.UIPanel;
/*      */ import nc.ui.pub.beans.UITable;
/*      */ import nc.ui.pub.bill.BillCardPanel;
/*      */ import nc.ui.pub.bill.BillData;
/*      */ import nc.ui.pub.bill.BillEditEvent;
/*      */ import nc.ui.pub.bill.BillEditListener;
/*      */ import nc.ui.pub.bill.BillEditListener2;
/*      */ import nc.ui.pub.bill.BillItem;
/*      */ import nc.ui.pub.bill.BillListPanel;
/*      */ import nc.ui.pub.bill.BillModel;
/*      */ import nc.ui.pub.bill.BillMouseEnent;
/*      */ import nc.ui.pub.bill.BillScrollPane;
/*      */ import nc.ui.pub.bill.BillSortListener2;
/*      */ import nc.ui.pub.bill.BillTableMouseListener;
/*      */ import nc.ui.pub.bill.IBillRelaSortListener;
/*      */ import nc.ui.pub.linkoperate.ILinkApprove;
/*      */ import nc.ui.pub.linkoperate.ILinkApproveData;
/*      */ import nc.ui.pub.linkoperate.ILinkMaintain;
/*      */ import nc.ui.pub.linkoperate.ILinkMaintainData;
/*      */ import nc.ui.querytemplate.IBillReferQuery;
/*      */ import nc.ui.trade.base.AbstractBillUI;
/*      */ import nc.ui.trade.bill.AbstractManageController;
/*      */ import nc.ui.trade.bill.BillCardPanelWrapper;
/*      */ import nc.ui.trade.bill.BillListPanelWrapper;
/*      */ import nc.ui.trade.bill.ITableTreeController;
/*      */ import nc.ui.trade.billsource.IHYBillSource;
/*      */ import nc.ui.trade.bsdelegate.BDBusinessDelegator;
/*      */ import nc.ui.trade.bsdelegate.BusinessDelegator;
/*      */ import nc.ui.trade.buffer.BillUIBuffer;
/*      */ import nc.ui.trade.button.ButtonManager;
/*      */ import nc.ui.trade.pub.BillTableCreateTreeTableTool;
/*      */ import nc.ui.trade.pub.IVOTreeData;
/*      */ import nc.ui.trade.pub.IVOTreeDataByCode;
/*      */ import nc.ui.trade.pub.IVOTreeDataByID;
/*      */ import nc.vo.pub.AggregatedValueObject;
/*      */ import nc.vo.pub.CircularlyAccessibleValueObject;
/*      */ import nc.vo.pub.SuperVO;
/*      */ import nc.vo.pub.lang.UFBoolean;
/*      */ import nc.vo.trade.field.IBillField;
/*      */ import nc.vo.trade.pub.BillStatus;
/*      */ import nc.vo.trade.pub.IExAggVO;
/*      */ 
/*      */ public abstract class BillManageUI extends AbstractBillUI
/*      */   implements ICheckRetVO, Observer, BillEditListener, BillEditListener2, IHYBillSource, BillTableMouseListener, ILinkApprove, ILinkMaintain
/*      */ {
/*      */   private BillListPanelWrapper m_BillListPanelWrapper;
/*      */   private BillCardPanelWrapper m_BillCardPanelWrapper;
/*   62 */   private ManageEventHandler m_btnAction = null;
/*      */ 
/*   65 */   private BillUIBuffer m_modelData = null;
/*      */ 
/*   68 */   private AbstractManageController m_uiCtl = null;
/*      */ 
/*   71 */   private UIPanel m_managePanel = null;
/*      */ 
/*   75 */   protected String m_CurrentPanel = "LISTPANEL";
/*      */ 
/*   78 */   private int m_billOperate = 2;
/*      */ 
/*   80 */   private CardLayout m_cardLayOut = null;
/*      */ 
/*   82 */   private BorderLayout m_borderLayOut = null;
/*      */ 
/*   85 */   private boolean m_isUseBillSource = false;
/*      */ 
/*   89 */   private IVOTreeData m_tabletreedata = null;
/*      */ 
/*   91 */   private BillTableCreateTreeTableTool m_listtabledata = null;
/*   92 */   private BillTableCreateTreeTableTool m_cardtabledata = null;
/*      */ 
/*      */   public BillManageUI()
/*      */   {
/*   98 */     initialize();
/*      */   }
/*      */ 
/*      */   public BillManageUI(FramePanel fp) {
/*  102 */     setFrame(fp);
/*  103 */     initialize();
/*      */   }
/*      */ 
/*      */   public BillManageUI(Boolean useBillSource)
/*      */   {
/*  111 */     this.m_isUseBillSource = useBillSource.booleanValue();
/*  112 */     initialize();
/*      */   }
/*      */ 
/*      */   public BillManageUI(String pk_corp, String pk_billType, String pk_busitype, String operater, String billId)
/*      */   {
/*  121 */     setBusinessType(pk_busitype);
/*  122 */     initialize();
/*  123 */     setCurrentPanel("CARDPANEL");
/*      */     try
/*      */     {
/*  126 */       getBufferData().addVOToBuffer(loadHeadData(billId));
/*  127 */       getBufferData().setCurrentRow(getBufferData().getCurrentRow());
/*      */     } catch (Exception ex) {
/*  129 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addListVo()
/*      */     throws Exception
/*      */   {
/*  140 */     CircularlyAccessibleValueObject vo = null;
/*  141 */     if (getBufferData().getCurrentVO() != null) {
/*  142 */       vo = getBufferData().getCurrentVO().getParentVO();
/*  143 */       getBillListWrapper().addListVo(vo);
/*  144 */       setHeadSpecialData(vo, getBufferData().getCurrentRow());
/*      */ 
/*  146 */       getBillListWrapper().execLoadHeadRowFormula(getBufferData().getCurrentRow());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addMouseSelectListener(BillTableMouseListener ml)
/*      */   {
/*  155 */     getBillListPanel().addMouseListener(ml);
/*      */   }
/*      */ 
/*      */   public void afterEdit(BillEditEvent e)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void afterUpdate()
/*      */   {
/*      */   }
/*      */ 
/*      */   public boolean beforeEdit(BillEditEvent e)
/*      */   {
/*  190 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean beforeUpdate()
/*      */   {
/*  206 */     return true;
/*      */   }
/*      */ 
/*      */   public void bodyRowChange(final BillEditEvent e)
/*      */   {
/*  217 */     if (e.getSource() == getBillListPanel().getParentListPanel().getTable())
/*  218 */       if ((isUseBillSource()) && (!(isMultiChildSource()))) {
/*  219 */         System.out.println("界面上：行发生变换由" + e.getOldRow() + "到" + e.getRow());
/*      */ 
/*  221 */         int iOldRow = e.getOldRow();
/*  222 */         int iNewRow = e.getRow();
/*  223 */         System.out.println("数据模型中：行发生变换由" + iOldRow + "到" + iNewRow);
/*  224 */         getBillListPanel().setBodyModelDataCopy(iOldRow);
/*  225 */         if (!(getBillListPanel().setBodyModelData(iNewRow))) {
/*  226 */           getBufferData().setCurrentRow(e.getRow());
/*  227 */           getBillListPanel().setBodyModelDataCopy(iNewRow);
/*      */         } else {
/*  229 */           getBillListPanel().repaint(); }
/*      */       } else {
/*  231 */         SwingUtilities.invokeLater(new Runnable() {
/*      */           public void run() {
/*  233 */             BillManageUI.this.getBufferData().setCurrentRow(e.getRow());
/*      */           }
/*      */         });
/*      */       }
/*      */   }
/*      */ 
/*      */   public void clearBody()
/*      */     throws Exception
/*      */   {
/*  248 */     getBillListPanel().setBodyValueVO(null);
/*      */   }
/*      */ 
/*      */   protected BillCardPanelWrapper createBillCardPanelWrapper()
/*      */     throws Exception
/*      */   {
/*  258 */     String funcode = null;
/*      */     try
/*      */     {
/*  261 */       funcode = getModuleCode();
/*      */     }
/*      */     catch (RuntimeException e)
/*      */     {
/*      */     }
/*      */ 
/*  267 */     return new BillCardPanelWrapper(getClientEnvironment(), getUIControl(), getBusinessType(), getNodeKey(), getBillDef(getUIControl().getHeadZYXKey(), getUIControl().getBodyZYXKey()), funcode);
/*      */   }
/*      */ 
/*      */   protected BillListPanelWrapper createBillListPanelWrapper()
/*      */     throws Exception
/*      */   {
/*  280 */     String funcode = null;
/*      */     try
/*      */     {
/*  283 */       funcode = getModuleCode();
/*      */     }
/*      */     catch (RuntimeException e)
/*      */     {
/*      */     }
/*      */ 
/*  289 */     return new BillListPanelWrapper(getClientEnvironment(), getUIControl(), getBusinessType(), getNodeKey(), getBillDef(getUIControl().getHeadZYXKey(), getUIControl().getBodyZYXKey()), funcode);
/*      */   }
/*      */ 
/*      */   protected abstract AbstractManageController createController();
/*      */ 
/*      */   protected BusinessDelegator createBusinessDelegator()
/*      */   {
/*  307 */     if (getUIControl().getBusinessActionType() == 1) {
/*  308 */       return new BDBusinessDelegator();
/*      */     }
/*  310 */     return new BusinessDelegator();
/*      */   }
/*      */ 
/*      */   protected ManageEventHandler createEventHandler()
/*      */   {
/*  317 */     return new ManageEventHandler(this, getUIControl());
/*      */   }
/*      */ 
/*      */   public final BillCardPanel getBillCardPanel()
/*      */   {
/*  324 */     return this.m_BillCardPanelWrapper.getBillCardPanel();
/*      */   }
/*      */ 
/*      */   public final BillCardPanelWrapper getBillCardWrapper()
/*      */   {
/*  333 */     return this.m_BillCardPanelWrapper;
/*      */   }
/*      */ 
/*      */   public final BillListPanel getBillListPanel()
/*      */   {
/*  340 */     return this.m_BillListPanelWrapper.getBillListPanel();
/*      */   }
/*      */ 
/*      */   public final BillListPanelWrapper getBillListWrapper()
/*      */   {
/*  349 */     return this.m_BillListPanelWrapper;
/*      */   }
/*      */ 
/*      */   protected String getBillNo()
/*      */     throws Exception
/*      */   {
/*  359 */     return null;
/*      */   }
/*      */ 
/*      */   public final int getBillOperate()
/*      */   {
/*  368 */     return this.m_billOperate;
/*      */   }
/*      */ 
/*      */   protected String getBillState(int intState)
/*      */   {
/*  380 */     String name = null;
/*  381 */     if (intState < 0)
/*  382 */       name = NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000101");
/*      */     else
/*  384 */       name = nc.ui.trade.buffer.BillStateModel.strStateRemark[intState];
/*  385 */     return name;
/*      */   }
/*      */ 
/*      */   public final BillUIBuffer getBufferData()
/*      */   {
/*  394 */     if (this.m_modelData == null)
/*  395 */       this.m_modelData = createBillUIBuffer();
/*  396 */     return this.m_modelData;
/*      */   }
/*      */ 
/*      */   protected BillUIBuffer createBillUIBuffer() {
/*  400 */     return new BillUIBuffer(getUIControl(), getBusiDelegator());
/*      */   }
/*      */ 
/*      */   public AggregatedValueObject getChangedVOFromUI()
/*      */     throws Exception
/*      */   {
/*  412 */     return this.m_BillCardPanelWrapper.getChangedVOFromUI();
/*      */   }
/*      */ 
/*      */   public String[][] getHeadShowNum()
/*      */   {
/*  424 */     return ((String[][])null);
/*      */   }
/*      */ 
/*      */   public String[][] getItemShowNum()
/*      */   {
/*  436 */     return ((String[][])null);
/*      */   }
/*      */ 
/*      */   protected BorderLayout getLayOutManager()
/*      */   {
/*  446 */     if (this.m_borderLayOut == null)
/*  447 */       this.m_borderLayOut = new BorderLayout();
/*  448 */     return this.m_borderLayOut;
/*      */   }
/*      */ 
/*      */   protected final ManageEventHandler getManageEventHandler()
/*      */   {
/*  457 */     if (this.m_btnAction == null)
/*  458 */       this.m_btnAction = createEventHandler();
/*  459 */     return this.m_btnAction;
/*      */   }
/*      */ 
/*      */   protected CardLayout getManageLayOutManager()
/*      */   {
/*  469 */     if (this.m_cardLayOut == null)
/*  470 */       this.m_cardLayOut = new CardLayout();
/*  471 */     return this.m_cardLayOut;
/*      */   }
/*      */ 
/*      */   public UIPanel getManagePane()
/*      */   {
/*  480 */     if (this.m_managePanel == null) {
/*  481 */       this.m_managePanel = new UIPanel();
/*  482 */       this.m_managePanel.setLayout(getManageLayOutManager());
/*      */     }
/*  484 */     return this.m_managePanel;
/*      */   }
/*      */ 
/*      */   public String getRefBillType()
/*      */   {
/*  491 */     return null;
/*      */   }
/*      */ 
/*      */   public String getTitle()
/*      */   {
/*  502 */     return this.m_BillCardPanelWrapper.getBillCardPanel().getBillData().getTitle();
/*      */   }
/*      */ 
/*      */   public final AbstractManageController getUIControl()
/*      */   {
/*  514 */     if (this.m_uiCtl == null)
/*  515 */       this.m_uiCtl = createController();
/*  516 */     return this.m_uiCtl;
/*      */   }
/*      */ 
/*      */   public final AggregatedValueObject getVo()
/*      */     throws Exception
/*      */   {
/*  525 */     return getBufferData().getCurrentVO();
/*      */   }
/*      */ 
/*      */   public AggregatedValueObject getVOFromUI()
/*      */     throws Exception
/*      */   {
/*  536 */     return this.m_BillCardPanelWrapper.getBillVOFromUI();
/*      */   }
/*      */ 
/*      */   private void initButton()
/*      */     throws Exception
/*      */   {
/*  547 */     getButtonManager().getButtonAry(getUIControl().getListButtonAry());
/*  548 */     getButtonManager().getButtonAry(getUIControl().getCardButtonAry());
/*      */   }
/*      */ 
/*      */   protected void initEventListener()
/*      */   {
/*  555 */     this.m_BillCardPanelWrapper.addEditListener(this);
/*  556 */     this.m_BillCardPanelWrapper.addBodyEditListener2(this);
/*  557 */     this.m_BillListPanelWrapper.getBillListPanel().addEditListener(this);
/*      */ 
/*  559 */     initTableSoftEventListener();
/*      */   }
/*      */ 
/*      */   private void initialize()
/*      */   {
/*      */     try
/*      */     {
/*  568 */       setLayout(getLayOutManager());
/*      */ 
/*  570 */       if (!(isUseBillSource()))
/*      */       {
/*  572 */         initButton();
/*      */ 
/*  575 */         getManageEventHandler().initNodeKeyButton();
/*      */ 
/*  578 */         getManageEventHandler().initActionButton();
/*      */       }
/*  580 */       add(getManagePane(), "Center");
/*      */ 
/*  583 */       initUI();
/*      */ 
/*  585 */       if (!(isUseBillSource())) {
/*  586 */         addMouseSelectListener(this);
/*      */       }
/*      */ 
/*  589 */       getBufferData().addObserver(this);
/*      */     } catch (Exception ex) {
/*  591 */       ex.printStackTrace();
/*  592 */       showErrorMessage(NCLangRes.getInstance().getStrByID("uifactory", "UPPuifactory-000118"));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void initTableSoftEventListener()
/*      */   {
/*  612 */     getBillListPanel().getHeadBillModel().addSortRelaObjectListener(new IBillRelaSortListener() {
/*      */       public List getRelaSortObject() {
/*  614 */         return BillManageUI.this.getBufferData().getRelaSortObject();
/*      */       }
/*      */     });
/*  619 */     getBillListPanel().getHeadBillModel().addBillSortListener2(new BillSortListener2()
/*      */     {
/*      */       public void currentRowChange(int row) {
/*  622 */         BillManageUI.this.getBufferData().setCurrentRowWithOutTriggerEvent(row);
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public final void initUI()
/*      */     throws Exception
/*      */   {
/*  636 */     getManagePane().removeAll();
/*      */ 
/*  639 */     this.m_BillCardPanelWrapper = createBillCardPanelWrapper();
/*      */ 
/*  641 */     getManagePane().add(this.m_BillCardPanelWrapper.getBillCardPanel(), "CARDPANEL");
/*      */ 
/*  645 */     this.m_BillListPanelWrapper = createBillListPanelWrapper();
/*      */ 
/*  647 */     getManagePane().add(this.m_BillListPanelWrapper.getBillListPanel(), "LISTPANEL");
/*      */ 
/*  650 */     setCurrentPanel("LISTPANEL");
/*      */ 
/*  653 */     initSelfData();
/*      */ 
/*  656 */     initEventListener();
/*      */ 
/*  659 */     getBillListWrapper().setListDecimalDigits(getHeadShowNum(), getItemShowNum());
/*      */ 
/*  661 */     getBillCardWrapper().setCardDecimalDigits(getHeadShowNum(), getItemShowNum());
/*      */ 
/*  665 */     if ((getUIControl().isExistBillStatus()) && (!(getBillCardPanel().getBillData().isMeataDataTemplate()))) {
/*  666 */       getBillCardWrapper().initHeadComboBox(getBillField().getField_BillStatus(), new BillStatus().strStateRemark, true);
/*      */ 
/*  669 */       getBillListWrapper().initHeadComboBox(getBillField().getField_BillStatus(), new BillStatus().strStateRemark, true);
/*      */     }
/*      */ 
/*  675 */     setBillOperate(4);
/*      */   }
/*      */ 
/*      */   public boolean isListPanelSelected()
/*      */   {
/*  684 */     return this.m_CurrentPanel.equals("LISTPANEL");
/*      */   }
/*      */ 
/*      */   protected boolean isMultiChildSource()
/*      */   {
/*  693 */     AggregatedValueObject vo = getBufferData().getCurrentVO();
/*      */ 
/*  695 */     return ((vo != null) && (vo instanceof IExAggVO));
/*      */   }
/*      */ 
/*      */   protected boolean isSetRowNormalState()
/*      */   {
/*  706 */     return true;
/*      */   }
/*      */ 
/*      */   protected boolean isUseBillSource()
/*      */   {
/*  715 */     return this.m_isUseBillSource;
/*      */   }
/*      */ 
/*      */   protected AggregatedValueObject loadHeadData(String key)
/*      */     throws Exception
/*      */   {
/*  728 */     AggregatedValueObject retVo = (AggregatedValueObject)Class.forName(getUIControl().getBillVoName()[0]).newInstance();
/*      */ 
/*  733 */     SuperVO tmpvo = (SuperVO)Class.forName(getUIControl().getBillVoName()[1]).newInstance();
/*      */ 
/*  735 */     retVo.setParentVO(getBusiDelegator().queryByPrimaryKey(tmpvo.getClass(), key));
/*      */ 
/*  739 */     return retVo;
/*      */   }
/*      */ 
/*      */   public void loadListHeadData(String strWhere, IBillReferQuery qryCondition)
/*      */     throws Exception
/*      */   {
/*  752 */     SuperVO[] queryVos = getBusiDelegator().queryHeadAllData(Class.forName(getUIControl().getBillVoName()[1]), getUIControl().getBillType(), strWhere);
/*      */ 
/*  757 */     getBufferData().clear();
/*  758 */     if ((queryVos != null) && (queryVos.length != 0)) {
/*  759 */       for (int i = 0; i < queryVos.length; ++i) {
/*  760 */         AggregatedValueObject aVo = (AggregatedValueObject)Class.forName(getUIControl().getBillVoName()[0]).newInstance();
/*      */ 
/*  763 */         aVo.setParentVO(queryVos[i]);
/*  764 */         getBufferData().addVOToBuffer(aVo);
/*      */       }
/*  766 */       setListHeadData(queryVos);
/*  767 */       getBufferData().setCurrentRow(0);
/*      */     } else {
/*  769 */       setListHeadData(queryVos);
/*      */ 
/*  771 */       getBufferData().setCurrentRow(-1);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void mouse_doubleclick(BillMouseEnent e)
/*      */   {
/*  782 */     if ((!(isListPanelSelected())) || (e.getPos() != 0)) return;
/*      */     try {
/*  784 */       getManageEventHandler().onBoCard();
/*      */     } catch (Exception ex) {
/*  786 */       System.out.println("卡片切换错误::" + ex.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void onButtonClicked(ButtonObject bo)
/*      */   {
/*  800 */     getManageEventHandler().onButton(bo);
/*      */   }
/*      */ 
/*      */   public void removeListHeadData(int intRow)
/*      */     throws Exception
/*      */   {
/*  809 */     this.m_BillListPanelWrapper.getBillListPanel().addEditListener(null);
/*  810 */     getBillListWrapper().removeListVo(intRow);
/*  811 */     this.m_BillListPanelWrapper.getBillListPanel().addEditListener(this);
/*      */   }
/*      */ 
/*      */   protected void setBillNo()
/*      */     throws Exception
/*      */   {
/*  821 */     BillItem noItem = getBillCardPanel().getHeadItem(getBillField().getField_BillNo());
/*      */ 
/*  823 */     if (noItem == null)
/*  824 */       return;
/*  825 */     noItem.setValue(getBillNo());
/*  826 */     noItem.setEnabled(getBusiDelegator().getParaBillNoEditable().booleanValue());
/*      */ 
/*  828 */     noItem.setEdit(getBusiDelegator().getParaBillNoEditable().booleanValue());
/*      */   }
/*      */ 
/*      */   public final void setBillOperate(int newBillOperate)
/*      */     throws Exception
/*      */   {
/*  839 */     this.m_billOperate = newBillOperate;
/*  840 */     setTotalUIState(newBillOperate);
/*      */   }
/*      */ 
/*      */   public void setBodyCondition(String strBodyCond)
/*      */     throws Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   public abstract void setBodySpecialData(CircularlyAccessibleValueObject[] paramArrayOfCircularlyAccessibleValueObject)
/*      */     throws Exception;
/*      */ 
/*      */   public final void setBufferData(BillUIBuffer newBufferData)
/*      */   {
/*  871 */     this.m_modelData = newBufferData;
/*      */   }
/*      */ 
/*      */   public void setCardUIData(AggregatedValueObject vo)
/*      */     throws Exception
/*      */   {
/*  881 */     getBillCardWrapper().setCardData(vo);
/*  882 */     if (vo == null)
/*  883 */       return;
/*  884 */     if (vo.getParentVO() != null)
/*  885 */       setHeadSpecialData(vo.getParentVO(), -1);
/*  886 */     if (vo.getChildrenVO() != null)
/*  887 */       setBodySpecialData(vo.getChildrenVO());
/*      */   }
/*      */ 
/*      */   public final void setCardUIState()
/*      */   {
/*  894 */     if (isListPanelSelected())
/*  895 */       setCurrentPanel("CARDPANEL");
/*      */   }
/*      */ 
/*      */   public void setCurrentPanel(String newCurrentPanel)
/*      */   {
/*  905 */     this.m_CurrentPanel = newCurrentPanel;
/*  906 */     getManageLayOutManager().show(getManagePane(), this.m_CurrentPanel);
/*  907 */     if (isListPanelSelected()) {
/*  908 */       setButtons(getButtonManager().getButtonAry(getUIControl().getListButtonAry()));
/*      */       try
/*      */       {
/*  911 */         if (getFrame() != null)
/*  912 */           setTitleText(getTitle());
/*      */       } catch (Exception e) {
/*  914 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     else {
/*  918 */       setButtons(getButtonManager().getButtonAry(getUIControl().getCardButtonAry()));
/*      */ 
/*  920 */       if (getFrame() != null)
/*  921 */         setTitleText(getTitle());
/*      */     }
/*  923 */     updateButtons();
/*      */   }
/*      */ 
/*      */   protected abstract void setHeadSpecialData(CircularlyAccessibleValueObject paramCircularlyAccessibleValueObject, int paramInt)
/*      */     throws Exception;
/*      */ 
/*      */   protected void setListBodyData()
/*      */     throws Exception
/*      */   {
/*  941 */     if (getBufferData().getCurrentVO() == null) {
/*  942 */       this.m_BillListPanelWrapper.setListBodyData(getBufferData().getCurrentRow(), null);
/*      */     }
/*      */     else {
/*  945 */       this.m_BillListPanelWrapper.setListBodyData(getBufferData().getCurrentRow(), getBufferData().getCurrentVO());
/*      */ 
/*  948 */       if (getBufferData().getCurrentVO() != null)
/*  949 */         setBodySpecialData(getBufferData().getCurrentVO().getChildrenVO());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setListHeadData(CircularlyAccessibleValueObject[] headVOs)
/*      */     throws Exception
/*      */   {
/*  961 */     getBillListWrapper().setListHeadData(headVOs);
/*  962 */     setTotalHeadSpecialData(headVOs);
/*      */   }
/*      */ 
/*      */   protected final void setSaveListData(boolean isAdding)
/*      */     throws Exception
/*      */   {
/*  969 */     if (isAdding)
/*  970 */       addListVo();
/*      */     else
/*  972 */       updateListVo();
/*      */   }
/*      */ 
/*      */   protected abstract void setTotalHeadSpecialData(CircularlyAccessibleValueObject[] paramArrayOfCircularlyAccessibleValueObject)
/*      */     throws Exception;
/*      */ 
/*      */   protected void setTotalUIState(int intOpType)
/*      */     throws Exception
/*      */   {
/*  995 */     getButtonManager().setButtonByOperate(intOpType);
/*  996 */     updateButtons();
/*      */ 
/*  998 */     switch (intOpType)
/*      */     {
/*      */     case 1:
/* 1000 */       getBillCardPanel().setEnabled(true);
/* 1001 */       getBillCardPanel().addNew();
/* 1002 */       setDefaultData();
/* 1003 */       setBillNo();
/*      */ 
/* 1006 */       break;
/*      */     case 0:
/* 1009 */       getBillCardPanel().setEnabled(true);
/* 1010 */       getBillCardPanel().transferFocusToFirstEditItem();
/* 1011 */       if (!(isSetRowNormalState())) return;
/* 1012 */       this.m_BillCardPanelWrapper.setRowStateToNormal(); break;
/*      */     case 3:
/* 1016 */       getBillCardPanel().setEnabled(true);
/* 1017 */       break;
/*      */     case 4:
/* 1020 */       this.m_BillCardPanelWrapper.setCardData(null);
/*      */     case 2:
/* 1023 */       getBillCardPanel().setEnabled(false);
/* 1024 */       getBillListPanel().setEnabled(false);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void update(Observable o, Object arg)
/*      */   {
/* 1068 */     if (beforeUpdate()) {
/*      */       try {
/* 1070 */         if (isListPanelSelected())
/*      */         {
/* 1072 */           setListBodyData();
/* 1073 */           int nrow = getBufferData().getCurrentRow();
/* 1074 */           int maxSelIndex = getBillListPanel().getHeadTable().getSelectionModel().getMaxSelectionIndex();
/* 1075 */           int minSelIndex = getBillListPanel().getHeadTable().getSelectionModel().getMinSelectionIndex();
/* 1076 */           if ((nrow >= 0) && (maxSelIndex == minSelIndex)) {
/* 1077 */             getBillListPanel().addEditListener(null);
/* 1078 */             getBillListPanel().getHeadTable().getSelectionModel().setSelectionInterval(nrow, nrow);
/* 1079 */             getBillListPanel().addEditListener(this);
/*      */           }
/*      */         }
/*      */         else {
/* 1083 */           setCardUIData(getBufferData().getCurrentVO());
/*      */         }
/* 1085 */         getBillCardPanel().updateValue();
/*      */ 
/* 1087 */         updateBtnStateByCurrentVO();
/*      */       } catch (Exception e) {
/* 1089 */         e.printStackTrace();
/* 1090 */         showErrorMessage(e.getMessage());
/*      */       }
/* 1092 */       if (getUIControl() instanceof ITableTreeController) {
/* 1093 */         setTableToTreeTable();
/*      */       }
/*      */     }
/*      */ 
/* 1097 */     afterUpdate();
/*      */   }
/*      */ 
/*      */   protected final void updateBtnStateByCurrentVO()
/*      */     throws Exception
/*      */   {
/* 1104 */     if (getUIControl().isExistBillStatus()) {
/* 1105 */       getButtonManager().setButtonByBillStatus(getBufferData(), getUIControl().isEditInGoing());
/*      */     }
/*      */ 
/* 1108 */     getButtonManager().setButtonByextendStatus(getExtendStatus(getBufferData().getCurrentVO()));
/*      */ 
/* 1111 */     getButtonManager().setPageButtonState(getBufferData());
/* 1112 */     updateButtons();
/*      */   }
/*      */ 
/*      */   protected void updateListVo()
/*      */     throws Exception
/*      */   {
/* 1122 */     CircularlyAccessibleValueObject vo = null;
/* 1123 */     if (getBufferData().getCurrentVO() != null) {
/* 1124 */       vo = getBufferData().getCurrentVO().getParentVO();
/* 1125 */       getBillListWrapper().updateListVo(vo, getBufferData().getCurrentRow());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected BillTableCreateTreeTableTool getBillListTableTreeTool()
/*      */   {
/* 1138 */     if (this.m_listtabledata == null) {
/* 1139 */       this.m_listtabledata = new BillTableCreateTreeTableTool(getBillListPanel());
/*      */     }
/* 1141 */     return this.m_listtabledata;
/*      */   }
/*      */ 
/*      */   protected BillTableCreateTreeTableTool getBillCardTableTreeTool()
/*      */   {
/* 1150 */     if (this.m_cardtabledata == null) {
/* 1151 */       this.m_cardtabledata = new BillTableCreateTreeTableTool(getBillCardPanel());
/*      */     }
/* 1153 */     return this.m_cardtabledata;
/*      */   }
/*      */ 
/*      */   protected IVOTreeData getCreateTableTreeData()
/*      */   {
/* 1160 */     if (this.m_tabletreedata == null) {
/* 1161 */       this.m_tabletreedata = ((ITableTreeController)getUIControl()).getTableTreeData();
/*      */     }
/* 1163 */     return this.m_tabletreedata;
/*      */   }
/*      */ 
/*      */   protected void setTableToTreeTable()
/*      */   {
/* 1171 */     if (getCreateTableTreeData() == null)
/*      */       return;
/* 1173 */     BillTableCreateTreeTableTool tabletreetool = null;
/*      */ 
/* 1175 */     if (isListPanelSelected())
/* 1176 */       tabletreetool = getBillListTableTreeTool();
/*      */     else {
/* 1178 */       tabletreetool = getBillCardTableTreeTool();
/*      */     }
/* 1180 */     if (getCreateTableTreeData() instanceof IVOTreeDataByID) {
/* 1181 */       IVOTreeDataByID idtree = (IVOTreeDataByID)getCreateTableTreeData();
/* 1182 */       tabletreetool.changeToTreeTable(idtree.getIDFieldName(), idtree.getParentIDFieldName(), idtree.getShowFieldName());
/*      */     } else {
/* 1184 */       IVOTreeDataByCode idtree = (IVOTreeDataByCode)getCreateTableTreeData();
/* 1185 */       tabletreetool.changeToTreeTableByCode(idtree.getCodeFieldName(), idtree.getCodeRule(), idtree.getShowFieldName());
/*      */     }
/* 1187 */     tabletreetool.expandToLevel(-1);
/*      */   }
/*      */ 
/*      */   public void doApproveAction(ILinkApproveData approvedata)
/*      */   {
/* 1195 */     setCurrentPanel("CARDPANEL");
/*      */     try
/*      */     {
/* 1198 */       getBufferData().addVOToBuffer(loadHeadData(approvedata.getBillID()));
/* 1199 */       setListHeadData(getBufferData().getAllHeadVOsFromBuffer());
/* 1200 */       getBufferData().setCurrentRow(getBufferData().getCurrentRow());
/*      */     } catch (Exception ex) {
/* 1202 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doMaintainAction(ILinkMaintainData maintaindata)
/*      */   {
/* 1208 */     setCurrentPanel("CARDPANEL");
/*      */     try
/*      */     {
/* 1211 */       getBufferData().addVOToBuffer(loadHeadData(maintaindata.getBillID()));
/* 1212 */       setListHeadData(getBufferData().getAllHeadVOsFromBuffer());
/* 1213 */       getBufferData().setCurrentRow(getBufferData().getCurrentRow());
/*      */     } catch (Exception ex) {
/* 1215 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void saveOnClosing()
/*      */     throws Exception
/*      */   {
/* 1222 */     getManageEventHandler().onBoSave();
/*      */   }
/*      */ }

