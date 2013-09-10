/*      */ package nc.ui.pub.beans;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.io.File;
/*      */ import java.io.PrintStream;
/*      */ import java.util.HashSet;
/*      */ import java.util.Vector;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.JApplet;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.event.EventListenerList;
/*      */ import javax.swing.filechooser.FileSystemView;
/*      */ import nc.ui.bd.ref.AbstractRefModel;
/*      */ import nc.ui.bd.ref.AbstractRefTreeModel;
/*      */ import nc.ui.bd.ref.IRefUICreator;
/*      */ import nc.ui.bd.ref.IRefUINew;
/*      */ import nc.ui.bd.ref.RefContext;
/*      */ import nc.ui.bd.ref.RefPubUtil;
/*      */ import nc.ui.bd.ref.RefUIConfig;
/*      */ import nc.ui.bd.ref.UFRefManage;
/*      */ import nc.ui.bd.ref.costomize.RefCustomizedDlg;
/*      */ import nc.ui.ml.NCLangRes;
/*      */ import nc.ui.pub.beans.calculator.UICalculator;
/*      */ import nc.ui.pub.beans.calendar.UICalendar;
/*      */ import nc.ui.pub.style.Style;
/*      */ import nc.vo.bd.access.AccessorManager;
/*      */ import nc.vo.bd.access.BddataVO;
/*      */ import nc.vo.bd.access.BdinfoVO;
/*      */ import nc.vo.bd.access.IBDAccessor;
/*      */ import nc.vo.logging.Debug;
/*      */ import nc.vo.pub.ValueObject;
/*      */ import nc.vo.pub.lang.UFDate;
/*      */ import nc.vo.pub.lang.UFDouble;
/*      */ 
/*      */ public class UIRefPane extends UIPanel
/*      */   implements ChangeListener
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*      */   public static final int REFINPUTTYPE_CODE = 0;
/*      */   public static final int REFINPUTTYPE_NAME = 1;
/*      */   public static final int REFINPUTTYPE_MNECODE = 2;
/*   76 */   private String pk_bdinfo = null;
/*      */ 
/*   81 */   private RefUIConfig refUIConfig = null;
/*      */ 
/*   83 */   private UIButton button = null;
/*      */ 
/*   85 */   private UITextField ivjUITextField = null;
/*      */ 
/*   87 */   private int fieldRefType = 0;
/*      */ 
/*   89 */   private boolean fieldButtonVisible = true;
/*      */ 
/*   91 */   private String fieldDelStr = new String();
/*      */ 
/*   94 */   private int fieldMaxLength = 2147483647;
/*      */ 
/*   96 */   private int fieldNumPoint = 0;
/*      */ 
/*   98 */   private String fieldTextType = "TextStr";
/*      */ 
/*  101 */   private boolean fieldReturnCode = false;
/*      */ 
/*  103 */   private boolean fieldEnabled = true;
/*      */ 
/*  106 */   protected String fieldText = new String();
/*      */ 
/*  108 */   private boolean fieldEditable = true;
/*      */ 
/*  110 */   private UITable fieldTable = null;
/*      */ 
/*  112 */   private String fieldRefNodeName = new String();
/*      */ 
/*  114 */   IvjEventHandler ivjEventHandler = new IvjEventHandler();
/*      */ 
/*  116 */   private String fieldRefPK = null;
/*      */ 
/*  119 */   private String fieldRefCode = new String();
/*      */ 
/*  121 */   private String fieldRefName = new String();
/*      */ 
/*  123 */   private Color colorBackgroundDefault = null;
/*      */ 
/*  126 */   private boolean displayedEnabled = true;
/*      */ 
/*  129 */   private boolean fieldAutoCheck = true;
/*      */ 
/*  132 */   private boolean fieldButtonFireEvent = false;
/*      */ 
/*  134 */   private boolean fieldCacheEnabled = true;
/*      */ 
/*  136 */   private Color fieldColor = new Color(0);
/*      */ 
/*  138 */   private String fieldMaxDateStr = null;
/*      */ 
/*  140 */   private double fieldMaxValue = 1.7976931348623157E+308D;
/*      */ 
/*  142 */   private String fieldMinDateStr = null;
/*      */ 
/*  144 */   private double fieldMinValue ;
/*      */ 
/*  146 */   private boolean fieldMultiSelectedEnabled = false;
/*      */ 
/*  148 */   private boolean fieldNotLeafSelectedEnabled = true;
/*      */ 
/*  151 */   private int fieldRefInputType = 0;
/*      */ 
/*  153 */   private Vector fieldSelectedData = new Vector();
/*      */ 
/*  155 */   private boolean fieldTextFieldVisible = true;
/*      */ 
/*  158 */   protected boolean isButtonClicked = false;
/*      */ 
/*  161 */   protected boolean isKeyPressed = false;
/*      */ 
/*  164 */   protected boolean isLostFocus = true;
/*      */ 
/*  167 */   private boolean isMultiCorpRef = false;
/*      */ 
/*  170 */   private String pk_corp = null;
/*      */ 
/*  173 */   private boolean isTreeGridNodeMultiSelected = false;
/*      */ 
/*  176 */   protected int m_iReturnButtonCode = -1;
/*      */ 
/*  178 */   private boolean m_isCustomDefined = false;
/*      */ 
/*  182 */   public UFRefManage m_refManage = null;
/*      */ 
/*  184 */   private Container parentContainer = null;
/*      */ 
/*  186 */   private boolean processFocusLost = true;
/*      */ 
/*  190 */   private boolean isIncludeSubShow = false;
/*      */ 
/*  194 */   private boolean isVersionButtonShow = false;
/*      */ 
/*  197 */   private boolean isFilerDlgShow = false;
/*      */ 
/*  200 */   private boolean isBatchData = true;
/*      */ 
/*  202 */   private boolean hasButtonAction = false;
/*      */ 
/*  205 */   private int matchMode = 0;
/*      */ 
/*  208 */   private HashSet<String> specialRef_hm = new HashSet();
/*      */ 
/*  212 */   private boolean isSealedDataButtonShow = false;
/*      */ 
/*  214 */   private String[] refNodeNames = { "", "日历", "文件", "颜色", "计算器", "文本框", "参照定制" };
/*      */ 
/*  217 */   private String multiInputToken = ",";
/*      */ 
/*  222 */   private Object refUI = null;
/*      */ 
/*  224 */   private RefEditListener refEditlistener = null;
/*      */ 
/*  226 */   private boolean isRefEditEventCanFire = true;
/*      */ 
/*      */   public UIRefPane()
/*      */   {
/*  241 */     initialize();
/*      */   }
/*      */ 
/*      */   public UIRefPane(Container parent)
/*      */   {
/*  253 */     this.parentContainer = parent;
/*  254 */     initialize();
/*      */   }
/*      */ 
/*      */   public UIRefPane(LayoutManager p0)
/*      */   {
/*  264 */     super(p0);
/*  265 */     initialize();
/*      */   }
/*      */ 
/*      */   public UIRefPane(LayoutManager p0, boolean p1)
/*      */   {
/*  277 */     super(p0, p1);
/*  278 */     initialize();
/*      */   }
/*      */ 
/*      */   public UIRefPane(boolean p0)
/*      */   {
/*  288 */     super(p0);
/*  289 */     initialize();
/*      */   }
/*      */ 
/*      */   public UIRefPane(String refNodeName)
/*      */   {
/*  294 */     initialize();
/*  295 */     setRefNodeName(refNodeName);
/*      */   }
/*      */ 
/*      */   public void addValueChangedListener(ValueChangedListener aListener)
/*      */   {
/*  302 */     this.listenerList.add(ValueChangedListener.class, aListener);
/*      */   }
/*      */ 
/*      */   private void callCalculator()
/*      */   {
/*  310 */     String input = this.fieldText;
/*      */     UICalculator calculator;
/*  311 */     if (input.equals(""))
/*  312 */       calculator = new UICalculator(this);
/*      */     else {
/*  314 */       calculator = new UICalculator(this, Double.valueOf(input).doubleValue());
/*      */     }
/*      */ 
/*  317 */     this.m_iReturnButtonCode = calculator.showModal();
/*  318 */     if (calculator.processCheck()) {
/*  319 */       this.isKeyPressed = false;
/*  320 */       this.isButtonClicked = true;
/*  321 */       double result = calculator.getResultData();
/*  322 */       setProcessFocusLost(false);
/*  323 */       setText(new UFDouble(result).toString());
/*      */     }
/*      */ 
/*  326 */     calculator.destroy();
/*      */   }
/*      */ 
/*      */   private void callCalendar()
/*      */   {
/*  334 */     UICalendar calendar = new UICalendar(this, this.fieldMinDateStr, this.fieldMaxDateStr);
/*      */     try
/*      */     {
/*  343 */       UFDate date = new UFDate(getUITextField().getText());
/*      */ 
/*  347 */       calendar.setNewdate(date);
/*      */     }
/*      */     catch (Exception e) {
/*      */     }
/*  351 */     this.m_iReturnButtonCode = calendar.showModal();
/*  352 */     if (getReturnButtonCode() == 1) {
/*  353 */       this.isKeyPressed = false;
/*  354 */       this.isButtonClicked = true;
/*  355 */       setProcessFocusLost(false);
/*      */ 
/*  357 */       setText(calendar.getCalendarString());
/*      */     }
/*  359 */     calendar.destroy();
/*      */   }
/*      */ 
/*      */   private void callRefCostomizedDefine()
/*      */   {
/*  370 */     RefCustomizedDlg dlg = (RefCustomizedDlg)getRefUI();
/*      */ 
/*  372 */     if (dlg == null) {
/*  373 */       return;
/*      */     }
/*      */ 
/*  376 */     dlg.setText(getUITextField().getText());
/*  377 */     this.m_iReturnButtonCode = dlg.showModal();
/*  378 */     if (getReturnButtonCode() == 1) {
/*  379 */       this.isKeyPressed = false;
/*  380 */       this.isButtonClicked = true;
/*  381 */       setProcessFocusLost(false);
/*  382 */       setText(dlg.getResultStr());
/*      */     }
/*  384 */     dlg.destroy();
/*      */   }
/*      */ 
/*      */   private void callColor()
/*      */   {
/*  392 */     Container m_parentTemp = null;
/*  393 */     if (this.parentContainer == null)
/*  394 */       if (getParent() != null)
/*  395 */         this.parentContainer = getParent();
/*      */       else
/*  397 */         this.parentContainer = this;
/*  398 */     if (this.parentContainer == null)
/*  399 */       m_parentTemp = getParent();
/*      */     else
/*  401 */       m_parentTemp = this.parentContainer;
/*  402 */     while ((m_parentTemp.getParent() != null) && 
/*  403 */       (!(m_parentTemp instanceof JApplet))) { if (m_parentTemp instanceof JDialog) {
/*      */         break;
/*      */       }
/*  406 */       m_parentTemp = m_parentTemp.getParent();
/*      */     }
/*  408 */     Color color = UIColorChooser.showDialog(m_parentTemp, NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000086"), getUITextField().getBackground());
/*      */ 
/*  412 */     if (color != null) {
/*  413 */       this.isKeyPressed = false;
/*  414 */       this.isButtonClicked = true;
/*  415 */       setProcessFocusLost(false);
/*  416 */       setColor(color);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void callFile()
/*      */   {
/*  424 */     Container m_parentTemp = null;
/*  425 */     if (this.parentContainer == null)
/*  426 */       if (getParent() != null)
/*  427 */         this.parentContainer = getParent();
/*      */       else
/*  429 */         this.parentContainer = this;
/*  430 */     if (this.parentContainer == null)
/*  431 */       m_parentTemp = getParent();
/*      */     else
/*  433 */       m_parentTemp = this.parentContainer;
/*  434 */     while ((m_parentTemp.getParent() != null) && 
/*  435 */       (!(m_parentTemp instanceof JApplet))) { if (m_parentTemp instanceof JDialog) {
/*      */         break;
/*      */       }
/*  438 */       m_parentTemp = m_parentTemp.getParent();
/*      */     }
/*  440 */     UIFileChooser chooser = (UIFileChooser)getRefUI();
/*      */ 
/*  442 */     if (chooser == null) {
/*  443 */       return;
/*      */     }
/*  445 */     String fileText = getText();
/*  446 */     if ((fileText != null) && (fileText.trim().length() > 0))
/*      */     {
/*  448 */       chooser.setSelectedFile(FileSystemView.getFileSystemView().createFileObject(fileText));
/*      */     }
/*      */     else {
/*  451 */       chooser.setCurrentDirectory(null);
/*      */     }
/*      */ 
/*  454 */     if (getRefType() == 0) {
/*  455 */       chooser.setFileSelectionMode(0);
/*      */     }
/*  457 */     else if (getRefType() == 1) {
/*  458 */       chooser.setFileSelectionMode(1);
/*      */     }
/*      */     else {
/*  461 */       chooser.setFileSelectionMode(2);
/*      */     }
/*  463 */     int returnValue = chooser.showOpenDialog(m_parentTemp);
/*      */     try
/*      */     {
/*  466 */       if ((returnValue == 0) && (chooser.getSelectedFile() != null))
/*      */       {
/*  468 */         this.isKeyPressed = false;
/*  469 */         this.isButtonClicked = true;
/*  470 */         setProcessFocusLost(false);
/*  471 */         getUITextField().setMaxLength(1024);
/*      */ 
/*  473 */         if (isReturnCode()) {
/*  474 */           setText(chooser.getSelectedFile().getName());
/*      */         }
/*      */         else
/*  477 */           setText(chooser.getSelectedFile().getCanonicalPath());
/*      */       }
/*      */     } catch (Exception e) {
/*  480 */       handleException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void connEtoC1()
/*      */   {
/*      */     try
/*      */     {
/*  495 */       if (this.displayedEnabled) {
/*  496 */         this.displayedEnabled = false;
/*  497 */         onButtonClicked();
/*      */       }
/*      */     }
/*      */     catch (Throwable ivjExc)
/*      */     {
/*  502 */       handleException(ivjExc);
/*      */     }
/*  504 */     this.isLostFocus = true;
/*  505 */     this.displayedEnabled = true;
/*      */   }
/*      */ 
/*      */   private void doFireValueChanged()
/*      */   {
/*  517 */     String sText = getUITextField().getText();
/*      */ 
/*  519 */     boolean isValueChanged = false;
/*      */ 
/*  522 */     if (!(RefPubUtil.equals(sText, this.fieldText))) {
/*  523 */       isValueChanged = true;
/*      */     }
/*      */ 
/*  526 */     if ((getRefModel() != null) && (!(RefPubUtil.equals(getRefModel().getSelectedData(), getSelectedData()))))
/*      */     {
/*  529 */       isValueChanged = true;
/*      */     }
/*      */ 
/*  532 */     if (!(isValueChanged))
/*      */       return;
/*  534 */     this.fieldText = sText;
/*  535 */     if (getRefModel() == null) {
/*  536 */       setSelectedData(null);
/*      */     }
/*  539 */     else if (getRefModel().getSelectedData() == null) {
/*  540 */       setSelectedData(null);
/*      */     }
/*      */     else
/*      */     {
/*  544 */       setSelectedData((Vector)getRefModel().getSelectedData().clone());
/*      */     }
/*      */ 
/*  548 */     fireValueChanged(new ValueChangedEvent(getUITextField().getParent(), sText));
/*      */   }
/*      */ 
/*      */   private void doKeyPressed()
/*      */   {
/*  560 */     String sText = getUITextField().getText().trim();
/*  561 */     String sOriginText = sText;
/*      */ 
/*  563 */     if (isCalendar())
/*      */     {
/*  565 */       sText = UFDate.getValidUFDateString(sText);
/*  566 */       getUITextField().setText(sText);
/*      */     }
/*      */     else
/*      */     {
/*  570 */       if (isButtonVisible()) {
/*  571 */         setBlurValue(sText, false);
/*      */       }
/*      */ 
/*  575 */       if ((getRefCodes() != null) && (getRefCodes().length > 1) && (!(RefPubUtil.isIncludeBlurChar(sOriginText))) && (!(isMultiInput(sText))))
/*      */       {
/*  578 */         this.m_refManage.setIsMneCodes(true);
/*  579 */         this.m_refManage.setAutoBlurMatch(false);
/*  580 */         getRefModel().setBlurValue(sText);
/*      */ 
/*  582 */         if (getRefModel() != null) {
/*  583 */           getRefModel().setSelectedData(null);
/*      */         }
/*  585 */         this.m_iReturnButtonCode = getRef().showModal();
/*  586 */         if (getReturnButtonCode() == 1) {
/*  587 */           doReturnOk();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  592 */       if (!(isReturnCode())) {
/*  593 */         processFocusLost(null);
/*      */       }
/*      */ 
/*  596 */       sText = getRefCode();
/*      */     }
/*      */ 
/*  601 */     if ((!(isAutoCheck())) && (((sText == null) || (sText.length() == 0)))) {
/*  602 */       sText = sOriginText;
/*  603 */       getUITextField().setText(sText);
/*      */     }
/*      */ 
/*  611 */     dealWithTreeModel();
/*      */ 
/*  613 */     setToolTipText();
/*      */   }
/*      */ 
/*      */   private void dealWithTreeModel()
/*      */   {
/*  618 */     if ((isIncludeSubShow()) && (getRef().getRefType() == 1)) {
/*  619 */       AbstractRefTreeModel treeModel = (AbstractRefTreeModel)getRefModel();
/*  620 */       if (treeModel != null) {
/*  621 */         treeModel.setIncludeSub(isIncludeSubShow());
/*  622 */         treeModel.saveTempData(treeModel.getSubPks(getRefPK()));
/*  623 */         treeModel.setSelectedData(treeModel.getSubRecords(getRefPK()));
/*      */       }
/*      */ 
/*  627 */       setTextFieldShowStrs(getRefShowName());
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean isCalendar()
/*      */   {
/*  633 */     return getRefNodeName().equals("日历");
/*      */   }
/*      */ 
/*      */   protected void fireValueChanged(ValueChangedEvent event)
/*      */   {
/*  640 */     Object[] listeners = this.listenerList.getListenerList();
/*      */ 
/*  643 */     for (int i = listeners.length - 2; i >= 0; i -= 2) {
/*  644 */       if (listeners[i] != ValueChangedListener.class)
/*      */         continue;
/*  646 */       ((ValueChangedListener)listeners[(i + 1)]).valueChanged(event);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Color getColor()
/*      */   {
/*  660 */     return this.fieldColor;
/*      */   }
/*      */ 
/*      */   public String getDelStr()
/*      */   {
/*  670 */     return this.fieldDelStr;
/*      */   }
/*      */ 
/*      */   public String getMaxDateStr()
/*      */   {
/*  680 */     return this.fieldMaxDateStr;
/*      */   }
/*      */ 
/*      */   public int getMaxLength()
/*      */   {
/*  690 */     return this.fieldMaxLength;
/*      */   }
/*      */ 
/*      */   public double getMaxValue()
/*      */   {
/*  700 */     return this.fieldMaxValue;
/*      */   }
/*      */ 
/*      */   public String getMinDateStr()
/*      */   {
/*  710 */     return this.fieldMinDateStr;
/*      */   }
/*      */ 
/*      */   public double getMinValue()
/*      */   {
/*  720 */     return this.fieldMinValue;
/*      */   }
/*      */ 
/*      */   public int getNumPoint()
/*      */   {
/*  730 */     return this.fieldNumPoint;
/*      */   }
/*      */ 
/*      */   public String getPk_corp()
/*      */   {
/*  740 */     if (getRefModel() != null)
/*  741 */       return getRefModel().getPk_corp();
/*  742 */     return this.pk_corp;
/*      */   }
/*      */ 
/*      */   public UFRefManage getRef()
/*      */   {
/*  754 */     if (isSpecialRef()) {
/*  755 */       return null;
/*      */     }
/*      */ 
/*  758 */     if (this.m_refManage == null) {
/*  759 */       if (this.parentContainer == null) {
/*  760 */         this.m_refManage = new UFRefManage(this, getRefNodeName(), getRefType(), isCustomDefined());
/*      */       }
/*      */       else {
/*  763 */         this.m_refManage = new UFRefManage(this.parentContainer, getRefNodeName(), getRefType(), isCustomDefined());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  769 */     return this.m_refManage;
/*      */   }
/*      */ 
/*      */   public BddataVO[] getRefBDdats()
/*      */   {
/*  776 */     if (getRefModel() == null) {
/*  777 */       return null;
/*      */     }
/*  779 */     String[] pks = getRefModel().getPkValues();
/*  780 */     BddataVO[] vos = null;
/*  781 */     if (pks != null) {
/*  782 */       BdinfoVO bdinfovo = getRefModel().getBdinfoVO();
/*      */ 
/*  784 */       if (bdinfovo != null) {
/*  785 */         vos = new BddataVO[pks.length];
/*  786 */         String pk_org = null;
/*  787 */         if ("1".equals(getRefModel().getOrgTypeCode()))
/*      */         {
/*  789 */           pk_org = getRefModel().getPk_corp();
/*  790 */         } else if ("2".equals(getRefModel().getOrgTypeCode()))
/*      */         {
/*  792 */           pk_org = getRefModel().getPk_GlOrgBook();
/*      */         }
/*  794 */         IBDAccessor accessor = AccessorManager.getAccessor(bdinfovo.getPk_bdinfo(), getRefModel().getOrgTypeCode(), pk_org);
/*      */ 
/*  798 */         for (int i = 0; i < vos.length; ++i) {
/*  799 */           vos[i] = accessor.getDocByPk(pks[i]);
/*      */         }
/*      */       }
/*      */     }
/*  803 */     return vos;
/*      */   }
/*      */ 
/*      */   public String getRefCode()
/*      */   {
/*  814 */     return ((getRefCodes() == null) ? null : getRefCodes()[0]);
/*      */   }
/*      */ 
/*      */   public String[] getRefCodes()
/*      */   {
/*  822 */     String[] fieldCodes = null;
/*  823 */     if ((getRefModel() == null) || (isSpecialRef())) {
/*  824 */       fieldCodes = new String[] { getText() };
/*      */     }
/*      */     else {
/*  827 */       fieldCodes = getRefModel().getRefCodeValues();
/*      */     }
/*  829 */     return fieldCodes;
/*      */   }
/*      */ 
/*      */   public int getRefInputType()
/*      */   {
/*  840 */     return this.fieldRefInputType;
/*      */   }
/*      */ 
/*      */   public AbstractRefModel getRefModel()
/*      */   {
/*  850 */     if (getRef() == null) {
/*  851 */       return null;
/*      */     }
/*      */ 
/*  854 */     AbstractRefModel model = getRef().getRefModel();
/*  855 */     if (model != null) {
/*  856 */       model.addChangeListener(this);
/*      */     }
/*  858 */     return model;
/*      */   }
/*      */ 
/*      */   public String getRefName()
/*      */   {
/*  868 */     if ((getRefModel() == null) || (isSpecialRef()))
/*  869 */       this.fieldRefName = getText();
/*      */     else
/*  871 */       this.fieldRefName = getRefModel().getRefNameValue();
/*  872 */     return this.fieldRefName;
/*      */   }
/*      */ 
/*      */   public String[] getRefNames()
/*      */   {
/*  882 */     if (getRefModel() == null)
/*  883 */       return null;
/*  884 */     return getRefModel().getRefNameValues();
/*      */   }
/*      */ 
/*      */   public String getRefNodeName()
/*      */   {
/*  894 */     if (this.fieldRefNodeName == null)
/*  895 */       this.fieldRefNodeName = "";
/*  896 */     return this.fieldRefNodeName;
/*      */   }
/*      */ 
/*      */   public String getRefPK()
/*      */   {
/*  906 */     if ((getRefModel() == null) || (isSpecialRef()))
/*  907 */       this.fieldRefPK = getText();
/*      */     else
/*  909 */       this.fieldRefPK = getRefModel().getPkValue();
/*  910 */     return this.fieldRefPK;
/*      */   }
/*      */ 
/*      */   public String[] getRefPKs()
/*      */   {
/*  920 */     if (getRefModel() == null)
/*  921 */       return null;
/*  922 */     return getRefModel().getPkValues();
/*      */   }
/*      */ 
/*      */   public String getRefTempDataWherePart() {
/*  926 */     if (getRefModel() == null)
/*  927 */       return null;
/*  928 */     return getRefModel().getTempDataWherePart();
/*      */   }
/*      */ 
/*      */   public int getRefType()
/*      */   {
/*  938 */     return this.fieldRefType;
/*      */   }
/*      */ 
/*      */   public Object getRefValue(String dbColumnName) {
/*  942 */     if (getRefModel() == null)
/*  943 */       return null;
/*  944 */     return getRef().getRefModel().getValue(dbColumnName);
/*      */   }
/*      */ 
/*      */   public Object getRefValues(String dbColumnName) {
/*  948 */     if (getRefModel() == null)
/*  949 */       return null;
/*  950 */     return getRefModel().getValues(dbColumnName);
/*      */   }
/*      */ 
/*      */   public int getReturnButtonCode()
/*      */   {
/*  960 */     return this.m_iReturnButtonCode;
/*      */   }
/*      */ 
/*      */   public Vector getSelectedData()
/*      */   {
/*  970 */     return this.fieldSelectedData;
/*      */   }
/*      */ 
/*      */   public UITable getTable()
/*      */   {
/*  980 */     return this.fieldTable;
/*      */   }
/*      */ 
/*      */   public String getText()
/*      */   {
/*  990 */     String text = getUITextField().getText();
/*  991 */     return ((text == null) ? text : text.trim());
/*      */   }
/*      */ 
/*      */   public String getTextType()
/*      */   {
/* 1001 */     return this.fieldTextType;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public String getToolTipText()
/*      */   {
/* 1008 */     return getText();
/*      */   }
/*      */ 
/*      */   private void setToolTipText() {
/* 1012 */     String text = getText();
/* 1013 */     if ((text != null) && (text.trim().length() == 0)) {
/* 1014 */       text = null;
/*      */     }
/* 1016 */     getUIButton().setToolTipText(text);
/* 1017 */     getUITextField().setToolTipText(text);
/*      */   }
/*      */ 
/*      */   public UIButton getUIButton()
/*      */   {
/* 1028 */     if (this.button == null)
/*      */     {
/* 1030 */       this.button = new UIButton() {
/*      */         protected void processMouseEvent(MouseEvent e) {
/* 1032 */           if (e.getID() == 501) {
/* 1033 */             UIRefPane.this.isLostFocus = false;
/*      */           }
/*      */ 
/* 1036 */           super.processMouseEvent(e);
/*      */         }
/*      */ 
/*      */         public boolean isFocusTraversable()
/*      */         {
/* 1042 */           return false;
/*      */         }
/*      */ 
/*      */         public void setEnabled(boolean bEnabled) {
/* 1046 */           super.setEnabled(bEnabled);
/* 1047 */           setBorder(null);
/* 1048 */           UIRefPane.this.setRefModelEnable(bEnabled);
/*      */         }
/*      */       };
/* 1075 */       this.button.setName("UIButton");
/*      */ 
/* 1078 */       this.button.setText("");
/*      */ 
/* 1086 */       this.button.setBorder(null);
/* 1087 */       this.button.setOpaque(false);
/*      */ 
/* 1089 */       setButtonIcon();
/*      */     }
/*      */ 
/* 1092 */     return this.button;
/*      */   }
/*      */ 
/*      */   public Color getBackGroudColor()
/*      */   {
/* 1114 */     Color color = null;
/* 1115 */     if (isEnabled())
/* 1116 */       color = Color.WHITE;
/*      */     else {
/* 1118 */       color = new Color(12895428);
/*      */     }
/* 1120 */     return color;
/*      */   }
/*      */ 
/*      */   public UITextField getUITextField()
/*      */   {
/* 1130 */     if (this.ivjUITextField == null) {
/*      */       try
/*      */       {
/* 1133 */         UIRefPaneTextField ui1 = new UIRefPaneTextField();
/* 1134 */         ui1.setUIRefPane(this);
/* 1135 */         this.ivjUITextField = ui1;
/* 1136 */         this.ivjUITextField.setName("UITextField");
/* 1137 */         this.colorBackgroundDefault = this.ivjUITextField.getBackground();
/* 1138 */         this.ivjUITextField.setTextType(this.fieldTextType);
/* 1139 */         this.ivjUITextField.setMaxLength(this.fieldMaxLength);
/* 1140 */         this.ivjUITextField.setNumPoint(this.fieldNumPoint);
/* 1141 */         this.ivjUITextField.setAutoscrolls(true);
/* 1142 */         this.ivjUITextField.setEditable(this.fieldEditable);
/* 1143 */         this.ivjUITextField.setMaxValue(getMaxValue());
/* 1144 */         this.ivjUITextField.setMinValue(getMinValue());
/* 1145 */         this.ivjUITextField.setPreferredSize(new Dimension(120, 18));
/* 1146 */         this.ivjUITextField.setBorder(null);
/*      */       }
/*      */       catch (Throwable ivjExc)
/*      */       {
/* 1150 */         handleException(ivjExc);
/*      */       }
/*      */     }
/* 1153 */     return this.ivjUITextField;
/*      */   }
/*      */ 
/*      */   public ValueObject getVO()
/*      */   {
/* 1163 */     if (getRefModel() != null) {
/* 1164 */       return getRefModel().getVO();
/*      */     }
/* 1166 */     return null;
/*      */   }
/*      */ 
/*      */   public ValueObject[] getVOs()
/*      */   {
/* 1176 */     if (getRefModel() != null) {
/* 1177 */       return getRefModel().getVOs();
/*      */     }
/* 1179 */     return null;
/*      */   }
/*      */ 
/*      */   private void handleException(Throwable exception)
/*      */   {
/* 1190 */     System.out.println("--------- 未捕捉到的异常 ---------");
/* 1191 */     exception.printStackTrace(System.out);
/*      */   }
/*      */ 
/*      */   private void initConnections()
/*      */   {
/* 1203 */     getUIButton().addActionListener(this.ivjEventHandler);
/*      */   }
/*      */ 
/*      */   private void initialize()
/*      */   {
/* 1212 */     setName("UIRefPane");
/* 1213 */     setPreferredSize(new Dimension(122, 20));
/* 1214 */     setRequestFocusEnabled(false);
/*      */ 
/* 1216 */     setLayout(new UIRefLayout());
/* 1217 */     add(getUITextField(), getUITextField().getName());
/* 1218 */     if (!(getRefNodeName().equals("文本框"))) {
/* 1219 */       add(getUIButton(), getUIButton().getName());
/*      */     }
/*      */ 
/* 1222 */     this.displayedEnabled = true;
/* 1223 */     initConnections();
/*      */ 
/* 1225 */     this.pk_corp = RefContext.getInstance().getPk_corp();
/*      */ 
/* 1228 */     initSpecialRef_hm();
/*      */ 
/* 1230 */     setBorder(BorderFactory.createLineBorder(Style.NCborderColor));
/*      */   }
/*      */ 
/*      */   public boolean isAutoCheck()
/*      */   {
/* 1241 */     return this.fieldAutoCheck;
/*      */   }
/*      */ 
/*      */   public boolean isBatchData()
/*      */   {
/* 1251 */     return this.isBatchData;
/*      */   }
/*      */ 
/*      */   public boolean isButtonFireEvent()
/*      */   {
/* 1261 */     return this.fieldButtonFireEvent;
/*      */   }
/*      */ 
/*      */   public boolean isButtonVisible()
/*      */   {
/* 1271 */     return this.fieldButtonVisible;
/*      */   }
/*      */ 
/*      */   public boolean isCacheEnabled()
/*      */   {
/* 1281 */     return this.fieldCacheEnabled;
/*      */   }
/*      */ 
/*      */   public boolean isCustomDefined()
/*      */   {
/* 1291 */     return this.m_isCustomDefined;
/*      */   }
/*      */ 
/*      */   public boolean isEditable()
/*      */   {
/* 1298 */     return getUITextField().isEditable();
/*      */   }
/*      */ 
/*      */   public boolean isRefEditable()
/*      */   {
/* 1303 */     return this.fieldEditable;
/*      */   }
/*      */ 
/*      */   public boolean isEnabled()
/*      */   {
/* 1313 */     return this.fieldEnabled;
/*      */   }
/*      */ 
/*      */   public boolean isFilerDlgShow()
/*      */   {
/* 1323 */     return this.isFilerDlgShow;
/*      */   }
/*      */ 
/*      */   public boolean isIncludeSub()
/*      */   {
/* 1328 */     if (getRefModel() == null)
/* 1329 */       return false;
/* 1330 */     return getRefModel().isIncludeSub();
/*      */   }
/*      */ 
/*      */   public boolean isIncludeSubShow()
/*      */   {
/* 1340 */     return this.isIncludeSubShow;
/*      */   }
/*      */ 
/*      */   public boolean isMultiCorpRef()
/*      */   {
/* 1347 */     return this.isMultiCorpRef;
/*      */   }
/*      */ 
/*      */   public boolean isMultiSelectedEnabled()
/*      */   {
/* 1357 */     return this.fieldMultiSelectedEnabled;
/*      */   }
/*      */ 
/*      */   public boolean isNotLeafSelectedEnabled()
/*      */   {
/* 1367 */     return this.fieldNotLeafSelectedEnabled;
/*      */   }
/*      */ 
/*      */   public boolean isProcessFocusLost()
/*      */   {
/* 1377 */     return this.processFocusLost;
/*      */   }
/*      */ 
/*      */   public boolean isReturnCode()
/*      */   {
/* 1387 */     return this.fieldReturnCode;
/*      */   }
/*      */ 
/*      */   public boolean isTextFieldVisible()
/*      */   {
/* 1397 */     return this.fieldTextFieldVisible;
/*      */   }
/*      */ 
/*      */   public boolean isTreeGridNodeMultiSelected()
/*      */   {
/* 1407 */     return this.isTreeGridNodeMultiSelected;
/*      */   }
/*      */ 
/*      */   public boolean isVersionButtonShow()
/*      */   {
/* 1417 */     return this.isVersionButtonShow;
/*      */   }
/*      */ 
/*      */   public void onButtonClicked()
/*      */   {
/* 1425 */     fireRefEdit(new RefEditEvent(this));
/* 1426 */     UITextField tf = getUITextField();
/* 1427 */     if ((!(tf.isFocusOwner())) && (isVisible()) && (getParent() != null) && (tf.isFocusable()) && (tf.isVisible()) && (getUIButton().isVisible()) && 
/* 1430 */       (!(this.hasButtonAction))) {
/* 1431 */       tf.requestFocus();
/* 1432 */       this.hasButtonAction = true;
/* 1433 */       return;
/*      */     }
/*      */ 
/* 1436 */     this.hasButtonAction = false;
/*      */ 
/* 1438 */     if (!(isEnabled())) {
/* 1439 */       return;
/*      */     }
/*      */     try
/*      */     {
/* 1443 */       if (this.m_refManage != null) {
/* 1444 */         this.m_refManage.setIsMneCodes(false);
/*      */       }
/* 1446 */       if (isSpecialRef()) {
/* 1447 */         if ((!(isCustomDefined())) && (getRefNodeName().equalsIgnoreCase("日历")))
/*      */         {
/* 1449 */           callCalendar();
/* 1450 */         } else if (getRefNodeName().equalsIgnoreCase("计算器"))
/* 1451 */           callCalculator();
/* 1452 */         else if (getRefNodeName().equalsIgnoreCase("颜色"))
/* 1453 */           callColor();
/* 1454 */         else if (getRefNodeName().equalsIgnoreCase("文件"))
/* 1455 */           callFile();
/* 1456 */         else if (getRefNodeName().equalsIgnoreCase("参照定制")) {
/* 1457 */           callRefCostomizedDefine();
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1462 */         String blur = getUITextField().getText();
/*      */ 
/* 1465 */         if (getRefModel() == null) {
/* 1466 */           System.out.println("UIRefPane.getModle()==null, 可能的原因为参照没有正确初始化，请检查参照定义。参照的名称为" + getRefNodeName());
/*      */ 
/* 1469 */           return;
/*      */         }
/*      */ 
/* 1472 */         setUIConfig();
/* 1473 */         setRefModelConfig();
/* 1474 */         if (this.isKeyPressed) {
/* 1475 */           getRefModel().setBlurValue(blur);
/*      */ 
/* 1477 */           getRef().setAutoBlurMatch(this.isKeyPressed);
/*      */         } else {
/* 1479 */           getRefModel().setBlurValue(null);
/*      */         }
/*      */ 
/* 1482 */         this.m_iReturnButtonCode = getRef().showModal();
/* 1483 */         if (getReturnButtonCode() == 1) {
/* 1484 */           doReturnOk();
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1490 */       handleException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void processFocusGained(FocusEvent e1)
/*      */   {
/* 1502 */     setFocusColorAndIcon(true);
/* 1503 */     fireRefEdit(new RefEditEvent(this));
/* 1504 */     if (!(isEnabled())) {
/* 1505 */       return;
/*      */     }
/*      */ 
/* 1508 */     if (!(isEditable())) {
/* 1509 */       return;
/*      */     }
/*      */ 
/* 1513 */     if (isSpecialRef()) {
/* 1514 */       return;
/*      */     }
/*      */ 
/* 1517 */     String text = getUITextField().getText();
/* 1518 */     if ((text != null) && (text.equalsIgnoreCase("null"))) {
/* 1519 */       getUITextField().setText(text);
/* 1520 */       this.fieldText = text;
/* 1521 */       return;
/*      */     }
/*      */ 
/* 1524 */     if (!(isAutoCheck())) {
/* 1525 */       return;
/*      */     }
/* 1527 */     String[] showStrs = null;
/* 1528 */     switch (getRefInputType())
/*      */     {
/*      */     case 0:
/* 1530 */       showStrs = getRefCodes();
/* 1531 */       break;
/*      */     case 1:
/* 1533 */       showStrs = getRefNames();
/* 1534 */       break;
/*      */     case 2:
/*      */     case 3:
/*      */     }
/*      */ 
/* 1540 */     setTextFieldShowStrs(getComposedString(showStrs));
/*      */   }
/*      */ 
/*      */   public void processFocusLost()
/*      */   {
/* 1549 */     setFocusColorAndIcon(false);
/*      */ 
/* 1551 */     this.m_iReturnButtonCode = 1;
/*      */ 
/* 1553 */     String sText = getUITextField().getText();
/*      */ 
/* 1556 */     if ((this.isKeyPressed) && (isNull(sText)))
/*      */     {
/* 1558 */       clearText(sText);
/*      */ 
/* 1560 */       setfocusLostVariableState();
/* 1561 */       return;
/*      */     }
/*      */ 
/* 1564 */     if (!(isSpecialRef()))
/*      */     {
/* 1566 */       if (this.isKeyPressed)
/*      */       {
/* 1568 */         doKeyPressed();
/*      */       }
/* 1573 */       else if ((!(isReturnCode())) && (isAutoCheck()))
/*      */       {
/* 1575 */         processFocusLost(null);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1581 */     if ((isCalendar()) && (this.isKeyPressed)) {
/* 1582 */       doKeyPressed();
/*      */     }
/*      */ 
/* 1585 */     if (this.m_iReturnButtonCode == 1) {
/* 1586 */       doFireValueChanged();
/*      */     }
/*      */ 
/* 1589 */     setfocusLostVariableState();
/*      */   }
/*      */ 
/*      */   private void setfocusLostVariableState()
/*      */   {
/* 1594 */     this.isButtonClicked = false;
/*      */ 
/* 1596 */     this.isKeyPressed = false;
/*      */ 
/* 1598 */     setProcessFocusLost(true);
/*      */ 
/* 1600 */     this.isRefEditEventCanFire = true;
/*      */   }
/*      */ 
/*      */   private void clearText(String sText)
/*      */   {
/* 1605 */     if (getRefModel() != null) {
/* 1606 */       getRefModel().setSelectedData(null);
/*      */     }
/* 1608 */     doFireValueChanged();
/*      */ 
/* 1610 */     setTextFieldShowStrs(sText);
/*      */   }
/*      */ 
/*      */   private boolean isNull(String sText) {
/* 1614 */     return ((sText != null) && (((sText.trim().length() == 0) || (sText.equalsIgnoreCase("null")))));
/*      */   }
/*      */ 
/*      */   protected void processFocusLost(FocusEvent e)
/*      */   {
/* 1626 */     setTextFieldShowStrs(getRefShowName());
/*      */   }
/*      */ 
/*      */   private void setTextFieldShowStrs(String text)
/*      */   {
/* 1647 */     getUITextField().setMaxLength(2147483647);
/* 1648 */     getUITextField().setText(text);
/*      */ 
/* 1650 */     getUITextField().setMaxLength(getMaxLength());
/*      */ 
/* 1652 */     setToolTipText();
/*      */   }
/*      */ 
/*      */   public void removeValueChangedListener(ValueChangedListener aListener)
/*      */   {
/* 1659 */     this.listenerList.remove(ValueChangedListener.class, aListener);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void reshape(int x, int y, int width, int height)
/*      */   {
/* 1667 */     super.reshape(x, y, width, height);
/*      */ 
/* 1669 */     if (getUITextField() instanceof UIRefPaneTextField) {
/* 1670 */       UIRefPaneTextField ref = (UIRefPaneTextField)getUITextField();
/* 1671 */       if (!(ref.isInternalAdjustingSize()))
/* 1672 */         ref.setMinWidth(width);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setAutoCheck(boolean autoCheck)
/*      */   {
/* 1685 */     boolean oldValue = this.fieldAutoCheck;
/* 1686 */     this.fieldAutoCheck = autoCheck;
/* 1687 */     firePropertyChange("autoCheck", new Boolean(oldValue), new Boolean(autoCheck));
/*      */   }
/*      */ 
/*      */   public void setBlurValue(String strBlurValue)
/*      */   {
/* 1699 */     setBlurValue(strBlurValue, true);
/*      */   }
/*      */ 
/*      */   private void setBlurValue(String strBlurValue, boolean isNeedInitSelectedData)
/*      */   {
/* 1705 */     if (isSpecialRef()) {
/* 1706 */       return;
/*      */     }
/* 1708 */     if (getRefModel() == null) {
/* 1709 */       return;
/*      */     }
/*      */ 
/* 1712 */     setRefModelConfig();
/* 1713 */     Vector vData = null;
/* 1714 */     if ((isMultiSelectedEnabled()) && (isMultiInput(strBlurValue)))
/*      */     {
/* 1716 */       vData = getRefModel().matchData(getRefModel().getRefCodeField(), getMultiInputStrs(strBlurValue));
/*      */     }
/*      */     else
/*      */     {
/* 1721 */       vData = getRefModel().matchBlurData(strBlurValue);
/*      */     }
/*      */ 
/* 1725 */     if ((vData != null) && (vData.size() > 1)) {
/* 1726 */       return;
/*      */     }
/*      */ 
/* 1729 */     resetDataFromModel(isNeedInitSelectedData);
/*      */   }
/*      */ 
/*      */   public void setBounds(int x, int y, int width, int height)
/*      */   {
/* 1734 */     if (!(isAdjustHight()))
/* 1735 */       super.setBounds(x, y, width, 20);
/*      */     else {
/* 1737 */       super.setBounds(x, y, width, height);
/*      */     }
/* 1739 */     if (getUITextField() instanceof UIRefPaneTextField) {
/* 1740 */       UIRefPaneTextField ref = (UIRefPaneTextField)getUITextField();
/* 1741 */       if (!(ref.isInternalAdjustingSize()))
/* 1742 */         ref.setMinWidth(width);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setBounds(Rectangle r)
/*      */   {
/* 1749 */     Rectangle r1 = new Rectangle(r);
/* 1750 */     if (!(isAdjustHight())) {
/* 1751 */       r1.height = 20;
/*      */     }
/* 1753 */     super.setBounds(r1);
/*      */   }
/*      */ 
/*      */   private boolean isAdjustHight()
/*      */   {
/* 1758 */     return getUITextField().isAdjustHight();
/*      */   }
/*      */ 
/*      */   public void setButtonFireEvent(boolean buttonFireEvent)
/*      */   {
/* 1769 */     boolean oldValue = this.fieldButtonFireEvent;
/* 1770 */     this.fieldButtonFireEvent = buttonFireEvent;
/* 1771 */     firePropertyChange("buttonFireEvent", new Boolean(oldValue), new Boolean(buttonFireEvent));
/*      */   }
/*      */ 
/*      */   public void setButtonVisible(boolean buttonVisible)
/*      */   {
/* 1783 */     boolean oldValue = this.fieldButtonVisible;
/* 1784 */     this.fieldButtonVisible = buttonVisible;
/* 1785 */     getUIButton().setVisible(buttonVisible);
/* 1786 */     if (!(isButtonVisible()))
/*      */     {
/* 1788 */       setReturnCode(true);
/* 1789 */       setAutoCheck(false);
/*      */     }
/* 1791 */     firePropertyChange("buttonVisible", new Boolean(oldValue), new Boolean(buttonVisible));
/*      */   }
/*      */ 
/*      */   public void setCacheEnabled(boolean cacheEnabled)
/*      */   {
/* 1803 */     boolean oldValue = this.fieldCacheEnabled;
/* 1804 */     this.fieldCacheEnabled = cacheEnabled;
/* 1805 */     if (getRefModel() != null) {
/* 1806 */       getRefModel().setCacheEnabled(isCacheEnabled());
/*      */     }
/*      */ 
/* 1809 */     firePropertyChange("cacheEnabled", new Boolean(oldValue), new Boolean(cacheEnabled));
/*      */   }
/*      */ 
/*      */   public void setColor(Color color)
/*      */   {
/* 1821 */     if ((((color != null) || (this.fieldColor == null))) && (((color == null) || (this.fieldColor != null))) && (((color == null) || (this.fieldColor == null) || (this.fieldColor.equals(color))))) {
/*      */       return;
/*      */     }
/* 1824 */     Color oldValue = this.fieldColor;
/* 1825 */     getUITextField().setBackground(color);
/* 1826 */     this.fieldColor = color;
/* 1827 */     firePropertyChange("color", oldValue, color);
/* 1828 */     fireValueChanged(new ValueChangedEvent(this, this.fieldColor));
/*      */   }
/*      */ 
/*      */   public void setDelStr(String sDelStr)
/*      */   {
/* 1841 */     this.fieldDelStr = sDelStr;
/* 1842 */     getUITextField().setDelStr(sDelStr);
/*      */   }
/*      */ 
/*      */   public void setEditable(boolean editable)
/*      */   {
/* 1854 */     getUITextField().setEditable(editable);
/* 1855 */     setRefModelEnable(editable);
/*      */   }
/*      */ 
/*      */   public void setButtonEnable(boolean enable)
/*      */   {
/* 1860 */     getUIButton().setEnabled(enable);
/* 1861 */     setRefModelEnable(enable);
/*      */   }
/*      */ 
/*      */   public void setRefEditable(boolean refEditable)
/*      */   {
/* 1868 */     if ("颜色".equals(getRefNodeName()))
/* 1869 */       getUITextField().setEditable(false);
/*      */     else {
/* 1871 */       getUITextField().setEditable(refEditable);
/*      */     }
/*      */ 
/* 1874 */     setButtonEnable(refEditable);
/*      */ 
/* 1876 */     if (refEditable)
/* 1877 */       setBorder(BorderFactory.createLineBorder(Style.NCborderColor));
/*      */     else {
/* 1879 */       setBorder(BorderFactory.createLineBorder(Style.NCborderDisableColor));
/*      */     }
/*      */ 
/* 1882 */     this.fieldEditable = refEditable;
/*      */   }
/*      */ 
/*      */   public void setEnabled(boolean param)
/*      */   {
/* 1895 */     this.fieldEnabled = param;
/* 1896 */     getUIButton().setEnabled(param);
/* 1897 */     getUITextField().setEnabled(param);
/* 1898 */     setRefModelEnable(param);
/* 1899 */     setFocusColorAndIcon(false);
/*      */   }
/*      */ 
/*      */   private void setRefModelEnable(boolean param)
/*      */   {
/* 1904 */     if (getRefModel() != null)
/* 1905 */       getRefModel().setisRefEnable(param);
/*      */   }
/*      */ 
/*      */   public void setFilerDlgShow(boolean newFilerDlgShow)
/*      */   {
/* 1918 */     this.isFilerDlgShow = newFilerDlgShow;
/*      */   }
/*      */ 
/*      */   public void setIncludeSubShow(boolean newIncludeSubShow)
/*      */   {
/* 1933 */     this.isIncludeSubShow = newIncludeSubShow;
/*      */   }
/*      */ 
/*      */   public void setIsBatchData(boolean newIsBatchData)
/*      */   {
/* 1947 */     this.isBatchData = newIsBatchData;
/*      */   }
/*      */ 
/*      */   public void setIsCustomDefined(boolean isCustomDefined)
/*      */   {
/* 1958 */     this.m_isCustomDefined = isCustomDefined;
/*      */   }
/*      */ 
/*      */   public void setMaxDateStr(String maxDateStr)
/*      */   {
/* 1970 */     this.fieldMaxDateStr = maxDateStr;
/*      */   }
/*      */ 
/*      */   public void setMaxLength(int maxLength)
/*      */   {
/* 1983 */     this.fieldMaxLength = maxLength;
/* 1984 */     getUITextField().setMaxLength(maxLength);
/*      */   }
/*      */ 
/*      */   public void setMaxValue(double maxValue)
/*      */   {
/* 1998 */     this.fieldMaxValue = maxValue;
/* 1999 */     getUITextField().setMaxValue(maxValue);
/*      */   }
/*      */ 
/*      */   public void setMinDateStr(String minDateStr)
/*      */   {
/* 2013 */     this.fieldMinDateStr = minDateStr;
/*      */   }
/*      */ 
/*      */   public void setMinValue(double minValue)
/*      */   {
/* 2026 */     this.fieldMinValue = minValue;
/* 2027 */     getUITextField().setMinValue(minValue);
/*      */   }
/*      */ 
/*      */   public void setMultiCorpRef(boolean multiCorpRef)
/*      */   {
/* 2037 */     this.isMultiCorpRef = multiCorpRef;
/*      */   }
/*      */ 
/*      */   public void setMultiSelectedEnabled(boolean multiSelectedEnabled)
/*      */   {
/* 2052 */     this.fieldMultiSelectedEnabled = multiSelectedEnabled;
/*      */   }
/*      */ 
/*      */   public void setNotLeafSelectedEnabled(boolean notLeafSelectedEnabled)
/*      */   {
/* 2069 */     this.fieldNotLeafSelectedEnabled = notLeafSelectedEnabled;
/*      */   }
/*      */ 
/*      */   public void setNumPoint(int iNumPoint)
/*      */   {
/* 2086 */     this.fieldNumPoint = iNumPoint;
/* 2087 */     getUITextField().setNumPoint(iNumPoint);
/*      */   }
/*      */ 
/*      */   public void setParentContainer(Container newParentContainer)
/*      */   {
/* 2100 */     this.parentContainer = newParentContainer;
/*      */   }
/*      */ 
/*      */   public void setPK(Object pk)
/*      */   {
/* 2110 */     if (pk == null)
/*      */     {
/* 2112 */       setPK(null);
/*      */     }
/*      */     else setPK(pk.toString());
/*      */   }
/*      */ 
/*      */   public void setPK(String pk)
/*      */   {
/* 2169 */     if ((pk == null) || (pk.trim().length() == 0))
/*      */     {
/* 2171 */       resetToNull();
/* 2172 */       return;
/*      */     }
/*      */ 
/* 2176 */     setPKs(new String[] { pk });
/*      */   }
/*      */ 
/*      */   private void resetToNull()
/*      */   {
/* 2194 */     setSelectedData(null);
/* 2195 */     setTextFieldShowStrs(null);
/* 2196 */     this.fieldText = new String();
/*      */ 
/* 2198 */     if (this.m_refManage == null) {
/* 2199 */       return;
/*      */     }
/* 2201 */     if (this.m_refManage.getRefModel() != null)
/* 2202 */       this.m_refManage.getRefModel().setSelectedData(null);
/*      */   }
/*      */ 
/*      */   private void resetDataFromModel(boolean isNeedInitSelectedData)
/*      */   {
/* 2222 */     if (getRefModel() == null) {
/* 2223 */       return;
/*      */     }
/*      */ 
/* 2226 */     if (isNeedInitSelectedData) {
/* 2227 */       setSelectedData(getRefModel().getSelectedData());
/* 2228 */       this.fieldText = getRefShowName();
/*      */     }
/*      */ 
/* 2231 */     setTextFieldShowStrs(getRefShowName());
/*      */   }
/*      */ 
/*      */   public void setPk_corp(String newPk_corp)
/*      */   {
/* 2243 */     if (getRef() == null) {
/* 2244 */       return;
/*      */     }
/* 2246 */     this.pk_corp = newPk_corp;
/* 2247 */     getRef().setPk_corp(newPk_corp);
/*      */   }
/*      */ 
/*      */   public void setProcessFocusLost(boolean newProcessFocusLost)
/*      */   {
/* 2259 */     this.processFocusLost = newProcessFocusLost;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   protected void setRefCode(String refCode)
/*      */   {
/* 2271 */     this.fieldRefCode = refCode;
/*      */   }
/*      */ 
/*      */   public void setRefInputType(int refInputType)
/*      */   {
/* 2284 */     this.fieldRefInputType = refInputType;
/*      */   }
/*      */ 
/*      */   public void setRefModel(AbstractRefModel model)
/*      */   {
/* 2298 */     if (model == null) {
/* 2299 */       return;
/*      */     }
/*      */ 
/* 2302 */     setIsCustomDefined(true);
/*      */ 
/* 2304 */     this.fieldRefNodeName = model.getRefNodeName();
/* 2305 */     if ((this.fieldRefNodeName == null) || (this.fieldRefNodeName.trim().length() == 0)) {
/* 2306 */       this.fieldRefNodeName = model.getClass().getName();
/*      */     }
/*      */ 
/* 2309 */     getRef().setRefModel(model);
/*      */ 
/* 2311 */     model.addChangeListener(this);
/*      */ 
/* 2313 */     model.setCacheEnabled(isCacheEnabled());
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   protected void setRefName(String refName)
/*      */   {
/* 2326 */     this.fieldRefName = refName;
/*      */   }
/*      */ 
/*      */   public void setRefNodeName(String refNodeName)
/*      */   {
/* 2337 */     if (refNodeName != null) {
/* 2338 */       if (refNodeName.equalsIgnoreCase("日历")) {
/* 2339 */         setTextType("TextDate");
/* 2340 */         setMaxLength(10);
/* 2341 */         getUITextField().setBackground(this.colorBackgroundDefault);
/* 2342 */         setEditable(true);
/*      */       }
/* 2344 */       else if (refNodeName.equalsIgnoreCase("计算器")) {
/* 2345 */         setTextType("TextDbl");
/* 2346 */         getUITextField().setBackground(this.colorBackgroundDefault);
/* 2347 */         setEditable(true);
/* 2348 */         setNumPoint(10);
/* 2349 */         setMaxLength(28);
/* 2350 */         setAutoCheck(false);
/* 2351 */         getUITextField().setAutoParse(false);
/*      */       }
/* 2353 */       else if (refNodeName.equalsIgnoreCase("颜色")) {
/* 2354 */         setEditable(false);
/* 2355 */         getUITextField().setBackground(this.fieldColor);
/* 2356 */       } else if (refNodeName.equalsIgnoreCase("文件")) {
/* 2357 */         UIFileChooser chooser = new UIFileChooser(this.fieldText);
/*      */ 
/* 2359 */         setRefUI(chooser);
/* 2360 */       } else if (refNodeName.equalsIgnoreCase("参照定制")) {
/* 2361 */         RefCustomizedDlg dlg = new RefCustomizedDlg(this);
/* 2362 */         setRefUI(dlg);
/*      */       } else {
/* 2364 */         setTextType("TextStr");
/* 2365 */         getUITextField().setBackground(this.colorBackgroundDefault);
/* 2366 */         setEditable(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2371 */     String oldValue = this.fieldRefNodeName;
/* 2372 */     this.fieldRefNodeName = refNodeName;
/*      */ 
/* 2374 */     this.m_refManage = null;
/*      */ 
/* 2377 */     if (isSpecialRef()) {
/* 2378 */       setIsBatchData(false);
/*      */     }
/* 2380 */     firePropertyChange("refNodeName", oldValue, refNodeName);
/*      */ 
/* 2382 */     setButtonIcon();
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   protected void setRefPK(String refPK)
/*      */   {
/* 2394 */     this.fieldRefPK = refPK;
/*      */   }
/*      */ 
/*      */   public void setRefType(int refType)
/*      */   {
/* 2406 */     this.fieldRefType = refType;
/*      */ 
/* 2408 */     this.m_refManage = null;
/* 2409 */     if (getRef() == null) {
/* 2410 */       return;
/*      */     }
/* 2412 */     getRef().setRefType(refType);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void setRefUI(IRefUINew refUI)
/*      */   {
/* 2430 */     if (getRef() == null) {
/* 2431 */       return;
/*      */     }
/* 2433 */     getRef().setRefUI(refUI);
/*      */   }
/*      */ 
/*      */   public void setReturnCode(boolean returnCode)
/*      */   {
/* 2445 */     this.fieldReturnCode = returnCode;
/*      */   }
/*      */ 
/*      */   public void setSelectedData(Vector selectedData)
/*      */   {
/* 2459 */     this.fieldSelectedData = selectedData;
/*      */   }
/*      */ 
/*      */   public void setStrPatch(String newStrPatch)
/*      */   {
/* 2471 */     if (getRefModel() != null)
/* 2472 */       getRefModel().setStrPatch(newStrPatch);
/*      */   }
/*      */ 
/*      */   public void setTable(UITable table)
/*      */   {
/* 2484 */     this.fieldTable = table;
/*      */   }
/*      */ 
/*      */   public void setText(String sText)
/*      */   {
/* 2496 */     getUITextField().setText(sText);
/* 2497 */     setToolTipText();
/*      */ 
/* 2500 */     if ((((sText == null) || (sText.trim().length() == 0))) && 
/* 2501 */       (getRefModel() != null)) {
/* 2502 */       getRefModel().setSelectedData(null);
/*      */     }
/*      */ 
/* 2506 */     doFireValueChanged();
/*      */   }
/*      */ 
/*      */   public void setTextFieldVisible(boolean textFieldVisible)
/*      */   {
/* 2518 */     this.fieldTextFieldVisible = textFieldVisible;
/* 2519 */     getUITextField().setVisible(textFieldVisible);
/*      */   }
/*      */ 
/*      */   public void setTextType(String sTextType)
/*      */   {
/* 2533 */     this.fieldTextType = sTextType;
/* 2534 */     getUITextField().setTextType(sTextType);
/*      */   }
/*      */ 
/*      */   public void setTreeGridNodeMultiSelected(boolean newTreeGridNodeMultiSelected)
/*      */   {
/* 2555 */     if (getRef() == null) {
/* 2556 */       return;
/*      */     }
/* 2558 */     this.isTreeGridNodeMultiSelected = newTreeGridNodeMultiSelected;
/*      */ 
/* 2560 */     getRef().setTreeGridNodeMultiSelected(this.isTreeGridNodeMultiSelected);
/*      */   }
/*      */ 
/*      */   public void setValue(String sText)
/*      */   {
/* 2570 */     if (sText != null)
/* 2571 */       this.fieldText = sText.trim();
/*      */     else {
/* 2573 */       this.fieldText = new String();
/*      */     }
/*      */ 
/* 2578 */     getUITextField().setText(this.fieldText);
/* 2579 */     setToolTipText();
/*      */   }
/*      */ 
/*      */   public void setVersionButtonShow(boolean newVersionButtonShow)
/*      */   {
/* 2592 */     this.isVersionButtonShow = newVersionButtonShow;
/*      */   }
/*      */ 
/*      */   public void setWhereString(String where)
/*      */   {
/* 2606 */     if (getRefModel() != null)
/* 2607 */       getRefModel().setWherePart(where);
/*      */   }
/*      */ 
/*      */   public boolean isFocusOwner() {
/* 2611 */     return ((super.isFocusOwner()) || (getUITextField().isFocusOwner()));
/*      */   }
/*      */ 
/*      */   public void requestFocus()
/*      */   {
/* 2620 */     getUITextField().requestFocus();
/*      */   }
/*      */ 
/*      */   public boolean requestFocusInWindow()
/*      */   {
/* 2631 */     return getUITextField().requestFocusInWindow();
/*      */   }
/*      */ 
/*      */   protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed)
/*      */   {
/* 2643 */     if (isTableParent()) {
/* 2644 */       ((UIRefPaneTextField)getUITextField()).setIsInputKeyPressed(e);
/*      */     }
/* 2646 */     boolean flag = getUITextField().processKeyBind(ks, e, condition, pressed);
/*      */ 
/* 2650 */     return flag;
/*      */   }
/*      */ 
/*      */   private boolean isTableParent() {
/* 2654 */     boolean isTableParent = false;
/* 2655 */     Component parent_refPane = getParent();
/* 2656 */     if (parent_refPane instanceof JTable) {
/* 2657 */       isTableParent = true;
/*      */     }
/* 2659 */     return isTableParent;
/*      */   }
/*      */ 
/*      */   public void setPKs(String[] pks)
/*      */   {
/* 2666 */     if (isSpecialRef())
/*      */     {
/* 2671 */       String pk = null;
/*      */ 
/* 2673 */       if ((pks != null) && (pks.length > 0)) {
/* 2674 */         pk = pks[0];
/*      */       }
/*      */ 
/* 2677 */       if ((getRefNodeName().equalsIgnoreCase("日历")) || (getRefNodeName().equals("参照定制")))
/*      */       {
/* 2679 */         getUITextField().setText(pk);
/* 2680 */         this.fieldText = pk;
/*      */       }
/* 2682 */       return;
/*      */     }
/*      */ 
/* 2686 */     if ((pks == null) || (pks.length == 0)) {
/* 2687 */       resetToNull();
/* 2688 */       return;
/*      */     }
/*      */ 
/* 2692 */     if (getRefModel() == null) {
/* 2693 */       Debug.debug("UIRefPane.setPK(), 但参照的Model为空，可能的原因为参照没有正确初始化，请检查参照定义。参照的名称为" + getRefNodeName());
/*      */ 
/* 2696 */       return;
/*      */     }
/*      */ 
/* 2699 */     if ((pks != null) && (pks.length > 0)) {
/* 2700 */       setRefModelConfig();
/* 2701 */       getRefModel().matchPkData(pks);
/*      */     } else {
/* 2703 */       getRefModel().setSelectedData(null);
/*      */     }
/*      */ 
/* 2706 */     resetDataFromModel(true);
/*      */ 
/* 2708 */     this.fieldText = getRefShowName();
/*      */   }
/*      */ 
/*      */   public String getRefShowName()
/*      */   {
/* 2714 */     String showName = "";
/* 2715 */     if (getRefModel() == null) {
/* 2716 */       return showName;
/*      */     }
/*      */ 
/* 2719 */     String[] names = null;
/*      */ 
/* 2721 */     if (isReturnCode())
/* 2722 */       names = getRefCodes();
/*      */     else {
/* 2724 */       names = (getRefModel() == null) ? null : getRefModel().getRefShowNameValues();
/*      */     }
/*      */ 
/* 2728 */     showName = getComposedString(names);
/*      */ 
/* 2730 */     return showName;
/*      */   }
/*      */ 
/*      */   private String getComposedString(String[] names)
/*      */   {
/* 2746 */     String showName = "";
/* 2747 */     StringBuffer sb = new StringBuffer();
/* 2748 */     if ((names != null) && (names.length > 0))
/*      */     {
/* 2750 */       for (int i = 0; i < names.length; ++i) {
/* 2751 */         if (names[i] == null) {
/*      */           continue;
/*      */         }
/* 2754 */         sb.append(names[i]).append(",");
/*      */       }
/*      */ 
/* 2758 */       if (sb.length() > 0) {
/* 2759 */         showName = sb.toString().substring(0, sb.length() - 1);
/*      */       }
/*      */     }
/*      */ 
/* 2763 */     return showName;
/*      */   }
/*      */ 
/*      */   boolean hasButtonAction() {
/* 2767 */     return this.hasButtonAction;
/*      */   }
/*      */ 
/*      */   public int getMatchMode()
/*      */   {
/* 2776 */     return this.matchMode;
/*      */   }
/*      */ 
/*      */   public void setMatchMode(int newMatchMode)
/*      */   {
/* 2786 */     this.matchMode = newMatchMode;
/*      */   }
/*      */ 
/*      */   private void doReturnOk()
/*      */   {
/* 2792 */     this.isKeyPressed = false;
/* 2793 */     this.isButtonClicked = true;
/* 2794 */     ((UIRefPaneTextField)getUITextField()).isInputKeyPress = false;
/* 2795 */     setProcessFocusLost(false);
/*      */ 
/* 2797 */     setTextFieldShowStrs(getRefShowName());
/*      */ 
/* 2801 */     if (isButtonFireEvent())
/* 2802 */       doFireValueChanged();
/*      */   }
/*      */ 
/*      */   private void initSpecialRef_hm()
/*      */   {
/* 2808 */     for (int i = 0; i < this.refNodeNames.length; ++i)
/* 2809 */       this.specialRef_hm.add(this.refNodeNames[i]);
/*      */   }
/*      */ 
/*      */   public boolean isSpecialRef()
/*      */   {
/* 2815 */     return ((this.specialRef_hm.contains(getRefNodeName())) && (!(isCustomDefined())));
/*      */   }
/*      */ 
/*      */   public void stateChanged(ChangeEvent e)
/*      */   {
/* 2821 */     resetDataFromModel(true);
/*      */   }
/*      */ 
/*      */   public boolean isSealedDataButtonShow()
/*      */   {
/* 2829 */     return this.isSealedDataButtonShow;
/*      */   }
/*      */ 
/*      */   public void setSealedDataButtonShow(boolean isSealedDataButtonShow)
/*      */   {
/* 2837 */     this.isSealedDataButtonShow = isSealedDataButtonShow;
/*      */   }
/*      */ 
/*      */   private void setUIConfig()
/*      */   {
/* 2845 */     UFRefManage refMng = getRef();
/* 2846 */     if (refMng == null) {
/* 2847 */       return;
/*      */     }
/* 2849 */     setRefUIConfigInfo();
/*      */ 
/* 2851 */     refMng.setRefUIConfig(getRefUIConfig());
/*      */ 
/* 2854 */     refMng.setMatchMode(getMatchMode());
/* 2855 */     refMng.setTreeGridNodeMultiSelected(isTreeGridNodeMultiSelected());
/*      */   }
/*      */ 
/*      */   private void setRefUIConfigInfo() {
/* 2859 */     getRefUIConfig().setMutilSelected(isMultiSelectedEnabled());
/* 2860 */     getRefUIConfig().setNotLeafSelectedEnabled(isNotLeafSelectedEnabled());
/* 2861 */     getRefUIConfig().setMultiCorpRef(isMultiCorpRef());
/* 2862 */     getRefUIConfig().setIncludeSubShow(isIncludeSubShow());
/* 2863 */     getRefUIConfig().setFilterDlgShow(isFilerDlgShow());
/* 2864 */     getRefUIConfig().setVersionButtonShow(isVersionButtonShow());
/* 2865 */     getRefUIConfig().setSealedDataButtonShow(isSealedDataButtonShow());
/* 2866 */     getRefUIConfig().setTreeGridNodeMultiSelected(isTreeGridNodeMultiSelected());
/*      */   }
/*      */ 
/*      */   private void setRefModelConfig()
/*      */   {
/* 2871 */     AbstractRefModel refModel = getRefModel();
/* 2872 */     if (refModel == null) {
/* 2873 */       return;
/*      */     }
/* 2875 */     refModel.setNotLeafSelectedEnabled(isNotLeafSelectedEnabled());
/*      */   }
/*      */ 
/*      */   public String getPk_bdinfo()
/*      */   {
/* 2883 */     return this.pk_bdinfo;
/*      */   }
/*      */ 
/*      */   public void setPk_bdinfo(String pk_bdinfo)
/*      */   {
/* 2891 */     this.pk_bdinfo = pk_bdinfo;
/*      */   }
/*      */ 
/*      */   public RefUIConfig getRefUIConfig()
/*      */   {
/* 2898 */     if (this.refUIConfig == null) {
/* 2899 */       this.refUIConfig = new RefUIConfig();
/*      */     }
/* 2901 */     return this.refUIConfig;
/*      */   }
/*      */ 
/*      */   private Icon getRefIcon(String state) {
/* 2905 */     String iconName = getRefNodeName();
/*      */ 
/* 2907 */     if ((!(isSpecialRef())) || ("文本框".equals(getRefNodeName())) || ("参照定制".equals(getRefNodeName())) || ("".equals(iconName)))
/*      */     {
/* 2909 */       iconName = "参照";
/*      */     }
/*      */ 
/* 2912 */     return RefPaneIconFactory.getInstance().getImageIcon(iconName, state);
/*      */   }
/*      */ 
/*      */   private void setButtonIcon()
/*      */   {
/* 2917 */     this.button.setIcon(getRefIcon(RefPaneIconFactory.REFENABLE));
/* 2918 */     this.button.setDisabledIcon(getRefIcon(RefPaneIconFactory.REFDISENABLE));
/* 2919 */     this.button.setRolloverIcon(getRefIcon(RefPaneIconFactory.REFMOUSEOVER));
/* 2920 */     this.button.setPressedIcon(getRefIcon(RefPaneIconFactory.REFPRESSED));
/*      */ 
/* 2922 */     Icon icon = getRefIcon(RefPaneIconFactory.REFENABLE);
/* 2923 */     this.button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
/*      */   }
/*      */ 
/*      */   public Object getRefUI()
/*      */   {
/* 2932 */     Object ui = null;
/* 2933 */     if (isSpecialRef()) {
/* 2934 */       ui = this.refUI;
/*      */     } else {
/* 2936 */       setRefUIConfigInfo();
/* 2937 */       getRef().setRefUIConfig(getRefUIConfig());
/* 2938 */       ui = getRef().getRefUI();
/*      */     }
/* 2940 */     return ui;
/*      */   }
/*      */ 
/*      */   private void setRefUI(Object refUI)
/*      */   {
/* 2949 */     this.refUI = refUI;
/*      */   }
/*      */ 
/*      */   public void setSelectedData(String pk, String code, String name)
/*      */   {
/* 2961 */     AbstractRefModel model = getRefModel();
/* 2962 */     if (model == null) {
/* 2963 */       return;
/*      */     }
/* 2965 */     Vector data = new Vector();
/*      */ 
/* 2967 */     Vector columnVec = RefPubUtil.getAllColumnNames(model);
/* 2968 */     Vector record = (Vector)columnVec.clone();
/* 2969 */     record.set(model.getFieldIndex(model.getPkFieldCode()), pk);
/* 2970 */     record.set(model.getFieldIndex(model.getRefCodeField()), code);
/* 2971 */     record.set(model.getFieldIndex(model.getRefNameField()), name);
/* 2972 */     data.add(record);
/* 2973 */     model.setSelectedData(data);
/* 2974 */     stateChanged(null);
/*      */   }
/*      */ 
/*      */   public IRefUICreator getRefUICreator() {
/* 2978 */     if (getRef() == null) {
/* 2979 */       return null;
/*      */     }
/* 2981 */     return getRef().getRefUICreator();
/*      */   }
/*      */ 
/*      */   public void setRefUICreator(IRefUICreator creator)
/*      */   {
/* 2992 */     if (getRef() == null) {
/* 2993 */       return;
/*      */     }
/* 2995 */     getRef().setRefUICreator(creator);
/*      */   }
/*      */ 
/*      */   protected boolean isMultiInput(String inputStr)
/*      */   {
/* 3001 */     if (inputStr == null) {
/* 3002 */       return false;
/*      */     }
/*      */ 
/* 3005 */     return (inputStr.indexOf(this.multiInputToken) > 0);
/*      */   }
/*      */ 
/*      */   private String[] getMultiInputStrs(String inputStr) {
/* 3009 */     return inputStr.split(this.multiInputToken);
/*      */   }
/*      */ 
/*      */   public void addRefEditListener(RefEditListener aListener)
/*      */   {
/* 3016 */     this.refEditlistener = aListener;
/*      */   }
/*      */ 
/*      */   public void removeRefEditListener(RefEditListener aListener)
/*      */   {
/* 3023 */     this.refEditlistener = null;
/*      */   }
/*      */ 
/*      */   protected void fireRefEdit(RefEditEvent event)
/*      */   {
/* 3030 */     if (this.refEditlistener == null) {
/* 3031 */       return;
/*      */     }
/* 3033 */     if (this.isRefEditEventCanFire) {
/* 3034 */       setEnabled(this.refEditlistener.beforeEdit(event));
/* 3035 */       this.isRefEditEventCanFire = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setAdjustHight(boolean adjustingBounds)
/*      */   {
/* 3041 */     getUITextField().setAdjustHight(adjustingBounds);
/*      */   }
/*      */ 
/*      */   private void setFocusColorAndIcon(boolean isFocus) {
/* 3045 */     if ((isFocus) && (isRefEditable()))
/*      */     {
/* 3047 */       setBorder(BorderFactory.createLineBorder(Style.NCborderFocusColor));
/* 3048 */       this.button.setIcon(getRefIcon(RefPaneIconFactory.REFMOUSEOVER));
/* 3049 */       return;
/*      */     }
/* 3051 */     setButtonIcon();
/*      */ 
/* 3054 */     if ((isEnabled()) && (isRefEditable()))
/* 3055 */       setBorder(BorderFactory.createLineBorder(Style.NCborderColor));
/*      */     else
/* 3057 */       setBorder(BorderFactory.createLineBorder(Style.NCborderDisableColor));
/*      */   }
/*      */ 
/*      */   class IvjEventHandler
/*      */     implements ActionListener
/*      */   {
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/*  231 */       if (e.getSource() == UIRefPane.this.getUIButton())
/*  232 */         UIRefPane.this.connEtoC1();
/*      */     }
/*      */   }
/*      */ }

 