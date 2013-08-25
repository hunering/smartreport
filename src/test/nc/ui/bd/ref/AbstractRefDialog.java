/*      */ package nc.ui.bd.ref;
/*      */ 
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.ItemEvent;
/*      */ import java.awt.event.ItemListener;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.KeyListener;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.io.PrintStream;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Vector;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.ActionMap;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ import nc.ui.ml.NCLangRes;
/*      */ import nc.ui.pub.beans.MessageDialog;
/*      */ import nc.ui.pub.beans.RefPaneIconFactory;
/*      */ import nc.ui.pub.beans.UIButton;
/*      */ import nc.ui.pub.beans.UICheckBox;
/*      */ import nc.ui.pub.beans.UIComboBox;
/*      */ import nc.ui.pub.beans.UIDialog;
/*      */ import nc.ui.pub.beans.UIPanel;
/*      */ import nc.ui.pub.beans.UIRefPane;
/*      */ import nc.ui.pub.beans.UITextField;
/*      */ import nc.ui.pub.beans.ValueChangedEvent;
/*      */ import nc.ui.pub.beans.ValueChangedListener;
/*      */ import nc.vo.bd.ref.RefcolumnVO;
/*      */ import nc.vo.bd.ref.ReftableVO;
/*      */ import nc.vo.logging.Debug;
/*      */ import nc.vo.pub.lang.UFBoolean;
/*      */ 
/*      */ public abstract class AbstractRefDialog extends UIDialog
/*      */   implements IRefUINew2, ValueChangedListener
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*   59 */   private JPanel dlgContentPane = null;
/*      */ 
/*   61 */   private UIPanel pnl_center = null;
/*      */ 
/*   63 */   private UIPanel pnl_component = null;
/*      */ 
/*   65 */   private UIButton btn_GridTree = null;
/*      */ 
/*   67 */   private RefButtonPanelFactory buttonPanelFactory = null;
/*      */ 
/*   69 */   private JComponent dataShowComponent = null;
/*      */ 
/*   71 */   private AbstractRefModel refModel = null;
/*      */ 
/*   73 */   private RefcolumnVO[] RefcolumnVOs = null;
/*      */ 
/*   75 */   private boolean isMutilSelected = false;
/*      */ 
/*   77 */   private boolean isMultiCorpRef = false;
/*      */ 
/*   80 */   private boolean isIncludeSubShow = false;
/*      */ 
/*   82 */   private boolean isNotLeafSelectedEnabled = true;
/*      */ 
/*   84 */   private boolean isVersionButtonShow = false;
/*      */ 
/*   86 */   private RefUIConfig refUIConfig = null;
/*      */ 
/*   89 */   private boolean isFilterDlgShow = false;
/*      */ 
/*   91 */   private int refType = 0;
/*      */ 
/*   93 */   private final String GRID = "grid";
/*      */ 
/*   95 */   private final String TREE = "tree";
/*      */ 
/*   97 */   private HashMap hm_dataShowCom = new HashMap();
/*      */ 
/*   99 */   private EventHandler eventHandler = new EventHandler();
/*      */ 
/*  101 */   private IRefCardInfoComponent refCardInfoComponent = null;
/*      */ 
/*  103 */   private boolean okBtnTreeEnableState = true;
/*      */ 
/*  106 */   private boolean isRefreshBtnClicked = false;
/*      */ 
/*  111 */   int width = 658;
/*      */ 
/*  113 */   int height = 390;
/*      */   private IDynamicColumn dynamicColClass;
/*      */ 
/*      */   public AbstractRefDialog(Container parent, AbstractRefModel model, RefUIConfig refUIConfig)
/*      */   {
/*  119 */     super(parent, model.getRefTitle());
/*  120 */     this.refUIConfig = refUIConfig;
/*  121 */     this.refModel = model;
/*  122 */     initialize();
/*      */   }
/*      */ 
/*      */   public void setFilterDlgShow(boolean isFilterDlgShow) {
/*  126 */     this.isFilterDlgShow = isFilterDlgShow;
/*      */   }
/*      */ 
/*      */   public void setVersionButtonShow(boolean isVersionButtonShow)
/*      */   {
/*  131 */     this.isVersionButtonShow = isVersionButtonShow;
/*      */   }
/*      */ 
/*      */   public AbstractRefModel getRefModel()
/*      */   {
/*  137 */     return this.refModel;
/*      */   }
/*      */ 
/*      */   public void setIncludeSubShow(boolean newIncludeSubShow)
/*      */   {
/*  142 */     this.isIncludeSubShow = newIncludeSubShow;
/*      */   }
/*      */ 
/*      */   public void setMultiCorpRef(boolean isMultiCorpRef)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void setMultiSelectedEnabled(boolean isMultiSelectedEnabled)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void setNotLeafSelectedEnabled(boolean newNotLeafSelectedEnabled)
/*      */   {
/*  156 */     this.isNotLeafSelectedEnabled = newNotLeafSelectedEnabled;
/*      */   }
/*      */ 
/*      */   public void setRefModel(AbstractRefModel refModel)
/*      */   {
/*  161 */     this.refModel = refModel;
/*      */   }
/*      */ 
/*      */   public void setTreeGridNodeMultiSelected(boolean isMulti)
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void onButtonColumn()
/*      */   {
/*  175 */     UFRefColumnsDlg refColumnsDlg = new UFRefColumnsDlg(this, NCLangRes.getInstance().getStrByID("ref", "UPPref-000340"), getRefModel());
/*      */ 
/*  179 */     if (refColumnsDlg.showModal() == 1)
/*      */     {
/*  183 */       getRefModel().setShownColumns(refColumnsDlg.getSelectedColumns());
/*  184 */       getRefModel().reloadData();
/*      */ 
/*  186 */       setRefcolumnVOs(null);
/*  187 */       setColumnSeq();
/*  188 */       this.hm_dataShowCom.clear();
/*      */     }
/*      */ 
/*  191 */     refColumnsDlg.destroy();
/*      */   }
/*      */ 
/*      */   protected void onButtonExit()
/*      */   {
/*  199 */     closeCancel();
/*      */   }
/*      */ 
/*      */   protected void onButtonOK()
/*      */   {
/*  205 */     getRefModel().setSelectedData(getIRefDataComponent().getSelectData());
/*  206 */     closeOK();
/*      */   }
/*      */ 
/*      */   protected void onButtonMaintenance()
/*      */   {
/*  212 */     Object[] selectPks = getRefModel().getValues(getRefModel().getPkFieldCode(), getIRefDataComponent().getSelectData());
/*      */ 
/*  216 */     RefAddDocument.getInstance().openDocFrame(this, getRefModel(), new Object[] { selectPks, getIRefDataComponent().getSelectData() });
/*      */ 
/*  221 */     onButtonRefresh();
/*      */   }
/*      */ 
/*      */   public void onButtonRefresh()
/*      */   {
/*  226 */     clearOtherDataShowCom();
/*  227 */     getIRefDataComponent().setData(getRefModel().reloadData());
/*  228 */     getIRefDataComponent().requestComponentFocus();
/*  229 */     getIRefDataComponent().setMatchedPKs(getRefModel().getPkValues());
/*  230 */     getBtnOK().setEnabled(true);
/*      */   }
/*      */ 
/*      */   protected void onButtonFilter()
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void onButtonGridTree()
/*      */   {
/*  240 */     if (!(getBtn_GridTree().isVisible())) {
/*  241 */       return;
/*      */     }
/*  243 */     boolean isNeedSetData = false;
/*  244 */     JComponent com = null;
/*      */ 
/*  246 */     if ("grid".equals(getBtn_GridTree().getName())) {
/*  247 */       setBtn_GridTreeState("tree");
/*      */ 
/*  249 */       getChkIncludeSubNode().setVisible(false);
/*  250 */       getRefModel().setIncludeSub(false);
/*  251 */       RefGridComponent gridCom = (RefGridComponent)this.hm_dataShowCom.get(new Integer(0));
/*      */ 
/*  253 */       if (gridCom == null) {
/*  254 */         gridCom = new RefGridComponent(this);
/*      */ 
/*  256 */         isNeedSetData = true;
/*      */       }
/*      */ 
/*  260 */       com = gridCom;
/*  261 */       this.okBtnTreeEnableState = getBtnOK().isEnabled();
/*  262 */       getBtnOK().setEnabled(true);
/*      */     }
/*  264 */     else if ("tree".equals(getBtn_GridTree().getName())) {
/*  265 */       getBtnOK().setEnabled(this.okBtnTreeEnableState);
/*  266 */       setBtn_GridTreeState("grid");
/*  267 */       getChkIncludeSubNode().setVisible(getRefUIConfig().isIncludeSubShow());
/*      */ 
/*  270 */       getRefModel().setIncludeSub(getChkIncludeSubNode().isSelected());
/*  271 */       RefTreeComponent treeCom = (RefTreeComponent)this.hm_dataShowCom.get(new Integer(1));
/*      */ 
/*  273 */       if (treeCom == null) {
/*  274 */         treeCom = new RefTreeComponent(this);
/*      */ 
/*  276 */         isNeedSetData = true;
/*      */       }
/*      */ 
/*  280 */       com = treeCom;
/*      */     }
/*      */ 
/*  284 */     setDataShowComponent(com);
/*      */ 
/*  286 */     if (isNeedSetData) {
/*  287 */       initDataToRefDataComponent();
/*      */     }
/*      */ 
/*  290 */     addDataShowComponent();
/*      */   }
/*      */ 
/*      */   protected void onComboBoxColumnItemStateChanged(ItemEvent itemEvent)
/*      */   {
/*  297 */     if (itemEvent.getStateChange() != 1)
/*      */       return;
/*  299 */     if ((getTfLocate().getText() != null) && (getTfLocate().getText().trim().length() > 0))
/*      */     {
/*  301 */       getIRefDataComponent().blurInputValue(null, null);
/*      */     }
/*  303 */     getTfLocate().setText("");
/*  304 */     getTfLocate().grabFocus();
/*      */   }
/*      */ 
/*      */   protected void onTextFieldLocateFocusLost()
/*      */   {
/*  314 */     String fieldCode = ((RefcolumnVO)getCbbColumn().getSelectedItem()).getFieldname();
/*      */ 
/*  316 */     String value = getTfLocate().getText();
/*  317 */     getIRefDataComponent().matchTreeNode(fieldCode, value);
/*      */   }
/*      */ 
/*      */   protected void onButtonQuery()
/*      */   {
/*  323 */     String className = getRefModel().getRefQueryDlgClaseName();
/*  324 */     Object interfaceClass = null;
/*  325 */     IRefQueryDlg queryDlg = null;
/*      */     try
/*      */     {
/*  329 */       Class modelClass = Class.forName(className);
/*  330 */       Constructor cs = null;
/*      */       try {
/*  332 */         cs = modelClass.getConstructor(new Class[] { Container.class });
/*  333 */         interfaceClass = cs.newInstance(new Object[] { this });
/*      */       } catch (NoSuchMethodException ee) {
/*  335 */         interfaceClass = modelClass.newInstance();
/*      */       }
/*      */     } catch (Exception e) {
/*  338 */       Debug.error(e.getMessage(), e);
/*  339 */       return;
/*      */     }
/*      */ 
/*  342 */     if (interfaceClass == null) {
/*  343 */       return;
/*      */     }
/*  345 */     if (interfaceClass instanceof IRefQueryDlg) {
/*  346 */       queryDlg = (IRefQueryDlg)interfaceClass;
/*  347 */       if (interfaceClass instanceof IRefQueryDlg2)
/*  348 */         ((IRefQueryDlg2)queryDlg).setRefModel(getRefModel());
/*      */     }
/*      */     else {
/*  351 */       MessageDialog.showErrorDlg(this, NCLangRes.getInstance().getStrByID("ref", "UPPref-000341"), NCLangRes.getInstance().getStrByID("ref", "UPPref-000366"));
/*      */ 
/*  358 */       return;
/*      */     }
/*      */ 
/*  363 */     queryDlg.setParent(this);
/*  364 */     queryDlg.setPk_corp(getRefModel().getPk_corp());
/*  365 */     queryDlg.showModal();
/*  366 */     if (queryDlg.getResult() == 1) {
/*  367 */       getRefModel().setQuerySql(queryDlg.getConditionSql());
/*  368 */       Vector vDataAll = getRefModel().getRefData();
/*      */ 
/*  370 */       if (vDataAll == null)
/*  371 */         vDataAll = new Vector();
/*  372 */       getIRefDataComponent().setData(vDataAll);
/*      */     }
/*      */ 
/*  375 */     getRefModel().setQuerySql(null);
/*      */   }
/*      */ 
/*      */   protected void onButtonSimpleQuery()
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void onChkSealedDataButton()
/*      */   {
/*  384 */     getRefModel().setSealedDataShow(getChkSealedDataShow().isSelected());
/*  385 */     onButtonRefresh();
/*      */   }
/*      */ 
/*      */   private void initialize() {
/*      */     try {
/*  390 */       setName("AbstrectRefDialog");
/*  391 */       setDefaultCloseOperation(2);
/*      */ 
/*  393 */       setDefaultSize();
/*      */ 
/*  395 */       setResizable(true);
/*  396 */       setContentPane(getDlgContentPane());
/*      */     } catch (Throwable ivjExc) {
/*  398 */       handleException(ivjExc);
/*      */     }
/*      */ 
/*  401 */     registerKey(getDlgContentPane());
/*      */ 
/*  403 */     initConnections();
/*      */   }
/*      */ 
/*      */   private void setDefaultSize()
/*      */   {
/*  421 */     Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
/*  422 */     this.width = (size.width * 64 / 100);
/*  423 */     this.height = (size.height * 51 / 100);
/*      */ 
/*  425 */     setSize(this.width, this.height);
/*      */   }
/*      */ 
/*      */   private void handleException(Throwable exception)
/*      */   {
/*  431 */     System.out.println("--------- 未捕捉到的异常 ---------");
/*  432 */     exception.printStackTrace(System.out);
/*      */   }
/*      */ 
/*      */   private UIButton getBtnColumn()
/*      */   {
/*  440 */     return getButtonPanelFactory().getBtnColumn();
/*      */   }
/*      */ 
/*      */   private UIButton getBtnExit()
/*      */   {
/*  449 */     return getButtonPanelFactory().getBtnExit();
/*      */   }
/*      */ 
/*      */   private UIButton getBtnMaintenanceDoc()
/*      */   {
/*  457 */     return getButtonPanelFactory().getBtnMaintenanceDoc();
/*      */   }
/*      */ 
/*      */   public UIButton getBtnOK()
/*      */   {
/*  466 */     return getButtonPanelFactory().getBtnOK();
/*      */   }
/*      */ 
/*      */   private UIButton getBtnQuery()
/*      */   {
/*  474 */     return getButtonPanelFactory().getBtnQuery();
/*      */   }
/*      */ 
/*      */   private UIButton getBtnRefresh()
/*      */   {
/*  482 */     return getButtonPanelFactory().getBtnRefresh();
/*      */   }
/*      */ 
/*      */   private UIButton getBtnSimpleQuery()
/*      */   {
/*  490 */     return getButtonPanelFactory().getBtnSimpleQuery();
/*      */   }
/*      */ 
/*      */   private JPanel getDlgContentPane()
/*      */   {
/*  497 */     if (this.dlgContentPane == null) {
/*  498 */       this.dlgContentPane = new UIPanel();
/*  499 */       this.dlgContentPane.setName("dlgContentPane");
/*  500 */       this.dlgContentPane.setBorder(new EmptyBorder(5, 5, 0, 5));
/*  501 */       this.dlgContentPane.setLayout(new BorderLayout());
/*  502 */       this.dlgContentPane.add(getPnl_center(), "Center");
/*  503 */       this.dlgContentPane.add(getPnl_refCorp(), "North");
/*  504 */       this.dlgContentPane.add(getPnl_south(), "South");
/*      */     }
/*  506 */     return this.dlgContentPane;
/*      */   }
/*      */ 
/*      */   private UIPanel getPnl_center()
/*      */   {
/*  514 */     if (this.pnl_center == null) {
/*  515 */       this.pnl_center = new UIPanel();
/*  516 */       this.pnl_center.setLayout(new BorderLayout());
/*  517 */       this.pnl_center.add(getPnl_locate_btn(), "North");
/*  518 */       this.pnl_center.add(getPnl_component(), "Center");
/*      */     }
/*  520 */     return this.pnl_center;
/*      */   }
/*      */ 
/*      */   private UIPanel getPnl_south()
/*      */   {
/*  525 */     return getButtonPanelFactory().getPnl_south(true);
/*      */   }
/*      */ 
/*      */   private UITextField getTfLocate()
/*      */   {
/*  533 */     return getButtonPanelFactory().getTfLocate();
/*      */   }
/*      */ 
/*      */   private UIComboBox getCbbColumn()
/*      */   {
/*  541 */     return getButtonPanelFactory().getCbbColumn();
/*      */   }
/*      */ 
/*      */   private void initConnections()
/*      */   {
/*  546 */     getBtnRefresh().addActionListener(this.eventHandler);
/*  547 */     getBtnColumn().addActionListener(this.eventHandler);
/*  548 */     getBtnOK().addActionListener(this.eventHandler);
/*  549 */     getBtnExit().addActionListener(this.eventHandler);
/*  550 */     getBtnQuery().addActionListener(this.eventHandler);
/*  551 */     getBtn_GridTree().addActionListener(this.eventHandler);
/*      */ 
/*  553 */     getTfLocate().addKeyListener(this.eventHandler);
/*  554 */     getTfLocate().addFocusListener(this.eventHandler);
/*  555 */     getBtnMaintenanceDoc().addActionListener(this.eventHandler);
/*  556 */     getBtnFilter().addActionListener(this.eventHandler);
/*      */ 
/*  558 */     getChkSealedDataShow().addActionListener(this.eventHandler);
/*  559 */     getRefCorp().addValueChangedListener(this);
/*      */   }
/*      */ 
/*      */   private void registerKey(JComponent c)
/*      */   {
/*  565 */     InputMap map = c.getInputMap(2);
/*  566 */     ActionMap am = c.getActionMap();
/*      */ 
/*  569 */     map.put(KeyStroke.getKeyStroke(27, 0, false), "RefcolumnDialog_ESCEPE");
/*      */ 
/*  571 */     am.put("RefcolumnDialog_ESCEPE", new ShortCutKeyAction(27));
/*      */ 
/*  574 */     map.put(KeyStroke.getKeyStroke(67, 8, false), "RefcolumnDialog_C");
/*      */ 
/*  576 */     am.put("RefcolumnDialog_C", new ShortCutKeyAction(67));
/*      */ 
/*  579 */     map.put(KeyStroke.getKeyStroke(79, 8, false), "RefcolumnDialog_O");
/*      */ 
/*  581 */     am.put("RefcolumnDialog_O", new ShortCutKeyAction(79));
/*      */ 
/*  585 */     map.put(KeyStroke.getKeyStroke(81, 8, false), "RefcolumnDialog_Q");
/*      */ 
/*  587 */     am.put("RefcolumnDialog_Q", new ShortCutKeyAction(81));
/*      */ 
/*  590 */     map.put(KeyStroke.getKeyStroke(82, 8, false), "RefcolumnDialog_R");
/*      */ 
/*  592 */     am.put("RefcolumnDialog_R", new ShortCutKeyAction(82));
/*      */ 
/*  596 */     map.put(KeyStroke.getKeyStroke(88, 8, false), "RefcolumnDialog_X");
/*      */ 
/*  598 */     am.put("RefcolumnDialog_X", new ShortCutKeyAction(88));
/*      */ 
/*  601 */     map.put(KeyStroke.getKeyStroke(77, 8, false), "RefcolumnDialog_M");
/*      */ 
/*  603 */     am.put("RefcolumnDialog_M", new ShortCutKeyAction(77));
/*      */ 
/*  607 */     map.put(KeyStroke.getKeyStroke(84, 8, false), "RefcolumnDialog_T");
/*      */ 
/*  609 */     am.put("RefcolumnDialog_T", new ShortCutKeyAction(84));
/*      */   }
/*      */ 
/*      */   private void uITextFieldLocate_KeyReleased(KeyEvent keyEvent)
/*      */   {
/*  760 */     if ((keyEvent.getKeyCode() == 20) || (keyEvent.getKeyCode() == 17) || (keyEvent.getKeyCode() == 16) || (keyEvent.getKeyCode() == 18))
/*      */     {
/*  764 */       return; }
/*  765 */     String strInput = getTfLocate().getText();
/*  766 */     getIRefDataComponent().blurInputValue(getCbbColumn().getSelectedItem().toString(), strInput);
/*      */   }
/*      */ 
/*      */   public JComponent getDataShowComponent()
/*      */   {
/*  775 */     return this.dataShowComponent;
/*      */   }
/*      */ 
/*      */   public void setDataShowComponent(JComponent dataShowComponent)
/*      */   {
/*  783 */     this.dataShowComponent = dataShowComponent;
/*  784 */     int refType = ((IRefDataComponent)this.dataShowComponent).getRefType();
/*  785 */     setRefType(refType);
/*  786 */     this.hm_dataShowCom.put(new Integer(refType), dataShowComponent);
/*      */   }
/*      */ 
/*      */   private void clearOtherDataShowCom()
/*      */   {
/*  792 */     int refType = ((IRefDataComponent)this.dataShowComponent).getRefType();
/*  793 */     Object com = this.hm_dataShowCom.get(new Integer(refType));
/*  794 */     this.hm_dataShowCom.clear();
/*  795 */     this.hm_dataShowCom.put(new Integer(refType), com);
/*      */   }
/*      */ 
/*      */   private void addDataShowComponent()
/*      */   {
/*  811 */     getPnl_component().removeAll();
/*  812 */     getPnl_component().add(this.dataShowComponent, "Center");
/*  813 */     String refCardClassName = getRefModel().getRefCardInfoComponentImplClass();
/*      */ 
/*  815 */     if (refCardClassName != null) {
/*  816 */       getPnl_component().add((JComponent)initRefCardInfoComponent(refCardClassName), "East");
/*      */     }
/*      */ 
/*  821 */     repaint();
/*      */   }
/*      */ 
/*      */   private IRefCardInfoComponent initRefCardInfoComponent(String className)
/*      */   {
/*      */     try
/*      */     {
/*  828 */       this.refCardInfoComponent = ((IRefCardInfoComponent)Class.forName(className).newInstance());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  832 */       Debug.error(e.getMessage(), e);
/*      */     }
/*      */ 
/*  836 */     return this.refCardInfoComponent;
/*      */   }
/*      */ 
/*      */   protected IRefDataComponent getIRefDataComponent() {
/*  840 */     return ((IRefDataComponent)getDataShowComponent());
/*      */   }
/*      */ 
/*      */   public int showModal() {
/*  844 */     if (getRefModel() == null) {
/*  845 */       return -1;
/*      */     }
/*      */ 
/*  848 */     setUserSize();
/*      */ 
/*  851 */     setComponentVisibleState();
/*      */ 
/*  853 */     initDynamicColClass();
/*      */ 
/*  855 */     setShowIndexToModel();
/*      */ 
/*  857 */     initDataToRefDataComponent();
/*      */ 
/*  859 */     addDataShowComponent();
/*      */ 
/*  861 */     handleSpecial();
/*      */ 
/*  863 */     setMultiCorpPK();
/*      */ 
/*  865 */     int iResult = super.showModal();
/*      */ 
/*  868 */     RefUtil.putRefSize(getRefModel().getRefNodeName(), new Dimension(getWidth(), getHeight()));
/*      */ 
/*  871 */     return iResult;
/*      */   }
/*      */ 
/*      */   private void setShowIndexToModel() {
/*  875 */     ReftableVO vo = getRefModel().getRefTableVO(getRefModel().getPk_corp());
/*  876 */     if ((vo == null) || (vo.getColumnVOs() == null))
/*      */       return;
/*  878 */     ArrayList list = new ArrayList();
/*  879 */     for (int i = 0; i < vo.getColumnVOs().length; ++i) {
/*  880 */       if (vo.getColumnVOs()[i].getIscolumnshow().booleanValue()) {
/*  881 */         list.add(vo.getColumnVOs()[i].getColumnshowindex());
/*      */       }
/*      */     }
/*      */ 
/*  885 */     if (list.size() > 0) {
/*  886 */       int[] showIndex = new int[list.size()];
/*  887 */       for (int i = 0; i < showIndex.length; ++i) {
/*  888 */         showIndex[i] = ((Integer)list.get(i)).intValue();
/*      */       }
/*  890 */       getRefModel().setShownColumns(showIndex);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setMultiCorpPK()
/*      */   {
/*  909 */     if (getPnl_refCorp().isVisible()) {
/*  910 */       getRefCorp().getRefModel().setFilterPks(getRefUIConfig().getMultiCorpRefPks());
/*      */ 
/*  912 */       getRefCorp().setPK(getRefModel().getPk_corp());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setUserSize()
/*      */   {
/*  930 */     Dimension dim = RefUtil.getRefSize(getRefModel().getRefNodeName(), new Dimension(this.width, this.height));
/*      */ 
/*  932 */     this.width = (int)dim.getWidth();
/*  933 */     this.height = (int)dim.getHeight();
/*  934 */     setSize(this.width, this.height);
/*      */   }
/*      */ 
/*      */   private void initDataToRefDataComponent()
/*      */   {
/*  952 */     Vector vDataAll = getData();
/*  953 */     if (getIRefDataComponent().getRefModel() == null) {
/*  954 */       getIRefDataComponent().setRefModel(getRefModel());
/*      */     }
/*  956 */     getIRefDataComponent().setData(vDataAll);
/*  957 */     setColumnSeq();
/*      */ 
/*  961 */     getIRefDataComponent().requestComponentFocus();
/*      */ 
/*  963 */     getIRefDataComponent().setMatchedPKs(getRefModel().getPkValues());
/*  964 */     if (getRefModel().getPkValues() != null)
/*  965 */       setRefCardInfoPk((getRefModel().getPkValues()[0] == null) ? null : getRefModel().getPkValues()[0].toString());
/*      */   }
/*      */ 
/*      */   private void setComponentVisibleState()
/*      */   {
/*  984 */     if (RefAddDocument.getInstance().getFunCode(getRefModel()) == null) {
/*  985 */       getBtnMaintenanceDoc().setVisible(false);
/*      */     }
/*  987 */     if (getRefModel().getRefQueryDlgClaseName() == null) {
/*  988 */       getBtnQuery().setVisible(false);
/*  989 */       getBtnSimpleQuery().setVisible(false);
/*      */     }
/*      */ 
/*  992 */     getPnl_refCorp().setVisible(getRefUIConfig().isMultiCorpRef());
/*      */ 
/* 1003 */     getChkSealedDataShow().setVisible(getRefUIConfig().isSealedDataButtonShow());
/*      */ 
/* 1006 */     if (getRefUIConfig().isSealedDataButtonShow()) {
/* 1007 */       getRefModel().setSealedDataShow(getChkSealedDataShow().isSelected());
/*      */     }
/*      */ 
/* 1012 */     if (getRefType() == 1)
/*      */     {
/* 1014 */       if (isNotLeafSelectedEnabled()) {
/* 1015 */         getBtn_GridTree().setVisible(getRefUIConfig().isNotLeafSelectedEnabled());
/*      */       }
/*      */ 
/* 1025 */       if (isIncludeSubShow()) {
/* 1026 */         getChkIncludeSubNode().setVisible(getRefUIConfig().isIncludeSubShow());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1031 */     getBtnColumn().setVisible(getRefUIConfig().isColumnBtnShow());
/*      */   }
/*      */ 
/*      */   private void setColumnSeq()
/*      */   {
/* 1048 */     setComboBoxData(getRefcolumnVOs());
/* 1049 */     getIRefDataComponent().setNewColumnSequence();
/*      */   }
/*      */ 
/*      */   protected RefcolumnVO[] getRefcolumnVOs() {
/* 1053 */     if (this.RefcolumnVOs == null) {
/* 1054 */       this.RefcolumnVOs = RefPubUtil.getColumnSequences(getRefModel());
/*      */     }
/* 1056 */     return this.RefcolumnVOs;
/*      */   }
/*      */ 
/*      */   private void setComboBoxData(RefcolumnVO[] items) {
/* 1060 */     getCbbColumn().removeAllItems();
/* 1061 */     for (int i = 0; i < items.length; ++i)
/* 1062 */       if (items[i].getIscolumnshow().booleanValue())
/* 1063 */         getCbbColumn().addItem(items[i]);
/*      */   }
/*      */ 
/*      */   public boolean isMutilSelected()
/*      */   {
/* 1074 */     return this.isMutilSelected;
/*      */   }
/*      */ 
/*      */   public UIRefPane getRefCorp()
/*      */   {
/* 1082 */     return getButtonPanelFactory().getRefCorp();
/*      */   }
/*      */ 
/*      */   private UIPanel getPnl_refCorp()
/*      */   {
/* 1090 */     return getButtonPanelFactory().getPnl_refCorp();
/*      */   }
/*      */ 
/*      */   private boolean isMultiCorpRef()
/*      */   {
/* 1097 */     return this.isMultiCorpRef;
/*      */   }
/*      */ 
/*      */   private UICheckBox getChkIncludeSubNode()
/*      */   {
/* 1105 */     return getButtonPanelFactory().getChkIncludeSubNode();
/*      */   }
/*      */ 
/*      */   private UICheckBox getChkSealedDataShow()
/*      */   {
/* 1110 */     return getButtonPanelFactory().getChkSealedDataShow();
/*      */   }
/*      */ 
/*      */   private boolean isIncludeSubShow()
/*      */   {
/* 1117 */     return this.isIncludeSubShow;
/*      */   }
/*      */ 
/*      */   protected UIButton getBtnFilter()
/*      */   {
/* 1125 */     return getButtonPanelFactory().getBtnFilter();
/*      */   }
/*      */ 
/*      */   public boolean isNotLeafSelectedEnabled()
/*      */   {
/* 1133 */     return this.isNotLeafSelectedEnabled;
/*      */   }
/*      */ 
/*      */   public boolean isIncludeSubNode()
/*      */   {
/* 1140 */     return ((isIncludeSubShow()) && (getChkIncludeSubNode().isSelected()));
/*      */   }
/*      */ 
/*      */   private int getRefType()
/*      */   {
/* 1153 */     return this.refType;
/*      */   }
/*      */ 
/*      */   abstract void handleSpecial();
/*      */ 
/*      */   protected boolean isFilterDlgShow()
/*      */   {
/* 1165 */     return this.isFilterDlgShow;
/*      */   }
/*      */ 
/*      */   protected UIButton getBtnVersion()
/*      */   {
/* 1173 */     return getButtonPanelFactory().getBtnVersion();
/*      */   }
/*      */ 
/*      */   public boolean isVersionButtonShow()
/*      */   {
/* 1180 */     return this.isVersionButtonShow;
/*      */   }
/*      */ 
/*      */   public RefUIConfig getRefUIConfig()
/*      */   {
/* 1187 */     return this.refUIConfig;
/*      */   }
/*      */ 
/*      */   public void setRefUIConfig(RefUIConfig refUIConfig)
/*      */   {
/* 1195 */     this.refUIConfig = refUIConfig;
/*      */   }
/*      */ 
/*      */   public void setRefType(int refType)
/*      */   {
/* 1203 */     this.refType = refType;
/*      */   }
/*      */ 
/*      */   private void setRefcolumnVOs(RefcolumnVO[] refcolumnVOs)
/*      */   {
/* 1211 */     this.RefcolumnVOs = refcolumnVOs;
/*      */   }
/*      */ 
/*      */   public void valueChanged(ValueChangedEvent event)
/*      */   {
/* 1218 */     String pk = getRefCorp().getRefPK();
/* 1219 */     getRefModel().setPk_corp(pk);
/* 1220 */     onButtonRefresh();
/*      */   }
/*      */ 
/*      */   protected void processWindowEvent(WindowEvent e)
/*      */   {
/* 1226 */     super.processWindowEvent(e);
/* 1227 */     if (e.getID() == 200) {
/* 1228 */       getIRefDataComponent().requestComponentFocus();
/*      */     }
/* 1230 */     getButtonPanelFactory().setTfLocateTextAndColor();
/*      */   }
/*      */ 
/*      */   private void initDynamicColClass()
/*      */   {
/* 1239 */     String className = getRefModel().getDynamicColClassName();
/* 1240 */     if (className == null) {
/* 1241 */       return;
/*      */     }
/* 1243 */     if (this.dynamicColClass != null) {
/* 1244 */       return;
/*      */     }
/*      */     try
/*      */     {
/* 1248 */       this.dynamicColClass = ((IDynamicColumn)Class.forName(className).newInstance());
/*      */     }
/*      */     catch (Exception e) {
/* 1251 */       Debug.debug(e.getMessage());
/* 1252 */       return;
/*      */     }
/*      */ 
/* 1256 */     initDyamicCol();
/*      */   }
/*      */ 
/*      */   private void initDyamicCol()
/*      */   {
/* 1262 */     if (getRefModel().isDynamicCol()) {
/* 1263 */       if (this.dynamicColClass == null) {
/* 1264 */         return;
/*      */       }
/* 1266 */       String[][] dynamicFieldNames = this.dynamicColClass.getDynaminColNameAndLoc();
/*      */ 
/* 1268 */       if (dynamicFieldNames != null) {
/* 1269 */         String[] strNames = new String[dynamicFieldNames.length];
/* 1270 */         for (int i = 0; i < strNames.length; ++i) {
/* 1271 */           strNames[i] = dynamicFieldNames[i][0];
/*      */         }
/*      */ 
/* 1274 */         getRefModel().setDynamicFieldNames(strNames);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private Vector getData()
/*      */   {
/* 1298 */     if (getIRefDataComponent().getRefType() == 1) {
/* 1299 */       return null;
/*      */     }
/* 1301 */     Vector vDataAll = getRefModel().getRefData();
/*      */ 
/* 1303 */     if (vDataAll == null) {
/* 1304 */       vDataAll = new Vector();
/*      */     }
/*      */ 
/* 1307 */     if ((vDataAll.size() > 0) && 
/* 1310 */       (getRefModel().isDynamicCol()))
/*      */     {
/* 1312 */       AbstractRefModel refModel = new ParaRefModel();
/* 1313 */       refModel.setFieldCode(getRefModel().getFieldCode());
/* 1314 */       refModel.setFieldName(getRefModel().getFieldName());
/* 1315 */       refModel.setHiddenFieldCode(getRefModel().getFieldName());
/* 1316 */       refModel.setPkFieldCode(getRefModel().getPkFieldCode());
/* 1317 */       refModel.setHiddenFieldCode(getRefModel().getHiddenFieldCode());
/* 1318 */       refModel.setData(vDataAll);
/* 1319 */       refModel.setSelectedData(vDataAll);
/* 1320 */       refModel.setIsDynamicCol(true);
/* 1321 */       refModel.setDynamicFieldNames(getRefModel().getDynamicFieldNames());
/*      */ 
/* 1324 */       refModel = this.dynamicColClass.getDynamicInfo(getRefModel().getUserParameter(), refModel);
/*      */ 
/* 1326 */       getRefModel().setData(refModel.getSelectedData());
/* 1327 */       vDataAll = refModel.getSelectedData();
/*      */     }
/*      */ 
/* 1330 */     return vDataAll;
/*      */   }
/*      */ 
/*      */   private UIPanel getPnl_locate_btn()
/*      */   {
/* 1337 */     return getButtonPanelFactory().getPnl_locate_btn(false, false, false);
/*      */   }
/*      */ 
/*      */   private UIPanel getPnl_component()
/*      */   {
/* 1345 */     if (this.pnl_component == null) {
/* 1346 */       this.pnl_component = new UIPanel();
/* 1347 */       this.pnl_component.setLayout(new BorderLayout());
/*      */     }
/*      */ 
/* 1351 */     return this.pnl_component;
/*      */   }
/*      */ 
/*      */   protected UIButton getBtn_GridTree()
/*      */   {
/* 1358 */     if (this.btn_GridTree == null)
/*      */     {
/* 1360 */       this.btn_GridTree = getButtonPanelFactory().getBtn_GridTree();
/*      */     }
/*      */ 
/* 1363 */     return this.btn_GridTree;
/*      */   }
/*      */ 
/*      */   private void setBtn_GridTreeState(String state)
/*      */   {
/* 1368 */     String name = null;
/* 1369 */     String text = null;
/* 1370 */     ImageIcon icon = null;
/*      */ 
/* 1372 */     if (state.equals("grid")) {
/* 1373 */       name = "grid";
/* 1374 */       text = NCLangRes.getInstance().getStrByID("ref", "UPPref-000501");
/*      */ 
/* 1377 */       icon = RefPaneIconFactory.getInstance().getImageIcon("参照.表");
/* 1378 */     } else if (state.equals("tree")) {
/* 1379 */       name = "tree";
/* 1380 */       text = NCLangRes.getInstance().getStrByID("ref", "UPPref-000502");
/*      */ 
/* 1382 */       icon = RefPaneIconFactory.getInstance().getImageIcon("参照.树");
/*      */     }
/* 1384 */     this.btn_GridTree.setName(name);
/* 1385 */     this.btn_GridTree.setToolTipText(text + "(ALT+T)");
/* 1386 */     this.btn_GridTree.setText(text);
/* 1387 */     this.btn_GridTree.setPreferredSize(new Dimension(50, 20));
/* 1388 */     this.btn_GridTree.setIcon(icon);
/*      */   }
/*      */ 
/*      */   public RefButtonPanelFactory getButtonPanelFactory()
/*      */   {
/* 1395 */     if (this.buttonPanelFactory == null) {
/* 1396 */       this.buttonPanelFactory = new RefButtonPanelFactory();
/*      */     }
/* 1398 */     return this.buttonPanelFactory;
/*      */   }
/*      */ 
/*      */   private IRefCardInfoComponent getRefCardInfoComponent()
/*      */   {
/* 1406 */     return this.refCardInfoComponent;
/*      */   }
/*      */ 
/*      */   public void setRefCardInfoPk(String pk) {
/* 1410 */     if (getRefCardInfoComponent() != null)
/* 1411 */       getRefCardInfoComponent().setPK(pk, getRefModel());
/*      */   }
/*      */ 
/*      */   public boolean isRefreshBtnClicked()
/*      */   {
/* 1417 */     return this.isRefreshBtnClicked;
/*      */   }
/*      */ 
/*      */   public void setRefreshBtnClicked(boolean isRefreshBtnClicked) {
/* 1421 */     this.isRefreshBtnClicked = isRefreshBtnClicked;
/*      */   }
/*      */ 
/*      */   class EventHandler
/*      */     implements ActionListener, ItemListener, KeyListener, FocusListener
/*      */   {
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/*  691 */       if (e.getSource() == AbstractRefDialog.this.getBtnRefresh()) {
/*  692 */         AbstractRefDialog.this.setRefreshBtnClicked(true);
/*  693 */         AbstractRefDialog.this.onButtonRefresh();
/*  694 */         AbstractRefDialog.this.setRefreshBtnClicked(false);
/*      */       }
/*  696 */       if (e.getSource() == AbstractRefDialog.this.getBtnColumn())
/*  697 */         AbstractRefDialog.this.onButtonColumn();
/*  698 */       if (e.getSource() == AbstractRefDialog.this.getBtnOK())
/*  699 */         AbstractRefDialog.this.onButtonOK();
/*  700 */       if (e.getSource() == AbstractRefDialog.this.getBtnExit())
/*  701 */         AbstractRefDialog.this.onButtonExit();
/*  702 */       if (e.getSource() == AbstractRefDialog.this.getBtnQuery())
/*  703 */         AbstractRefDialog.this.onButtonQuery();
/*  704 */       if (e.getSource() == AbstractRefDialog.this.getBtnMaintenanceDoc())
/*  705 */         AbstractRefDialog.this.onButtonMaintenance();
/*  706 */       if (e.getSource() == AbstractRefDialog.this.getBtnFilter())
/*  707 */         AbstractRefDialog.this.onButtonFilter();
/*  708 */       if (e.getSource() == AbstractRefDialog.this.getBtn_GridTree()) {
/*  709 */         AbstractRefDialog.this.onButtonGridTree();
/*      */       }
/*  711 */       if (e.getSource() != AbstractRefDialog.this.getChkSealedDataShow())
/*      */         return;
/*  713 */       AbstractRefDialog.this.onChkSealedDataButton();
/*      */     }
/*      */ 
/*      */     public void itemStateChanged(ItemEvent e)
/*      */     {
/*  725 */       if (e.getSource() == AbstractRefDialog.this.getCbbColumn())
/*  726 */         AbstractRefDialog.this.onComboBoxColumnItemStateChanged(e);
/*      */     }
/*      */ 
/*      */     public void keyPressed(KeyEvent e) {
/*  730 */       if ((e.getSource() != AbstractRefDialog.this.getTfLocate()) || 
/*  731 */         (e.getKeyChar() != '\27')) return;
/*  732 */       String str = AbstractRefDialog.this.getTfLocate().getText();
/*  733 */       if ((str == null) || (str.trim().length() == 0))
/*  734 */         AbstractRefDialog.this.onButtonExit();
/*      */     }
/*      */ 
/*      */     public void keyReleased(KeyEvent e)
/*      */     {
/*  741 */       if (e.getSource() == AbstractRefDialog.this.getTfLocate())
/*  742 */         AbstractRefDialog.this.uITextFieldLocate_KeyReleased(e);
/*      */     }
/*      */ 
/*      */     public void keyTyped(KeyEvent e)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void focusGained(FocusEvent e)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void focusLost(FocusEvent e) {
/*  754 */       AbstractRefDialog.this.onTextFieldLocateFocusLost();
/*      */     }
/*      */   }
/*      */ 
/*      */   class ShortCutKeyAction extends AbstractAction
/*      */   {
/*  616 */     int keycode = -1;
/*      */     static final int VK_ESCAPE = 27;
/*      */     static final int VK_C = 67;
/*      */     static final int VK_O = 79;
/*      */     static final int VK_Q = 81;
/*      */     static final int VK_R = 82;
/*      */     static final int VK_X = 88;
/*      */     static final int VK_M = 77;
/*      */     static final int VK_T = 84;
/*      */     static final String KEY_REFCOLUMNDIALOG_ESCAPE = "RefcolumnDialog_ESCEPE";
/*      */     static final String KEY_REFCOLUMNDIALOG_C = "RefcolumnDialog_C";
/*      */     static final String KEY_REFCOLUMNDIALOG_O = "RefcolumnDialog_O";
/*      */     static final String KEY_REFCOLUMNDIALOG_Q = "RefcolumnDialog_Q";
/*      */     static final String KEY_REFCOLUMNDIALOG_R = "RefcolumnDialog_R";
/*      */     static final String KEY_REFCOLUMNDIALOG_X = "RefcolumnDialog_X";
/*      */     static final String KEY_REFCOLUMNDIALOG_M = "RefcolumnDialog_M";
/*      */     static final String KEY_REFCOLUMNDIALOG_T = "RefcolumnDialog_T";
/*      */ 
/*      */     public ShortCutKeyAction(int paramInt)
/*      */     {
/*  651 */       this.keycode = paramInt;
/*      */     }
/*      */ 
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/*  656 */       switch (this.keycode)
/*      */       {
/*      */       case 27:
/*  658 */         AbstractRefDialog.this.onButtonExit();
/*  659 */         break;
/*      */       case 67:
/*  661 */         AbstractRefDialog.this.onButtonColumn();
/*  662 */         break;
/*      */       case 79:
/*  664 */         AbstractRefDialog.this.onButtonOK();
/*  665 */         break;
/*      */       case 81:
/*  667 */         AbstractRefDialog.this.onButtonQuery();
/*  668 */         break;
/*      */       case 82:
/*  670 */         AbstractRefDialog.this.onButtonRefresh();
/*  671 */         break;
/*      */       case 88:
/*  673 */         AbstractRefDialog.this.onButtonExit();
/*  674 */         break;
/*      */       case 77:
/*  677 */         AbstractRefDialog.this.onButtonMaintenance();
/*  678 */         break;
/*      */       case 84:
/*  681 */         AbstractRefDialog.this.onButtonGridTree();
/*      */       }
/*      */     }
/*      */   }
/*      */ }

 