/*      */ package nc.ui.pub;
/*      */ 
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.event.EventListenerList;
/*      */ import nc.bs.logging.Logger;
/*      */ import nc.bs.uap.sf.facility.SFServiceFacility;
/*      */ import nc.itf.uap.sf.IFuncRegisterQueryService;
/*      */ import nc.itf.uap.sf.IHotKeyRegisterQueryService;
/*      */ import nc.itf.uap.sf.IHotKeyRegisterService;
/*      */ import nc.ui.glpub.IUiPanel;
/*      */ import nc.ui.glpub.UiManager;
/*      */ import nc.ui.ml.NCLangRes;
/*      */ import nc.ui.pub.beans.MessageDialog;
/*      */ import nc.ui.pub.beans.UIPanel;
/*      */ import nc.ui.pub.ccp.ClientCtrlParas;
/*      */ import nc.ui.pub.style.Style;
/*      */ import nc.ui.sf.menuext.FuncMenuExtends;
/*      */ import nc.ui.sf.menuext.FuncNodeApiFactory;
/*      */ import nc.ui.sm.cmenu.Desktop;
/*      */ import nc.ui.sm.log.OperateLog;
/*      */ import nc.ui.sm.login.NCAppletStub;
/*      */ import nc.vo.bd.CorpVO;
/*      */ import nc.vo.pub.BusinessException;
/*      */ import nc.vo.pub.hotkey.HotkeyRegisterVO;
/*      */ import nc.vo.pub.hotkey.NCKey;
/*      */ import nc.vo.pub.lang.UFDateTime;
/*      */ import nc.vo.sm.UserVO;
/*      */ import nc.vo.sm.funcreg.FuncRegisterVO;
/*      */ import nc.vo.sm.log.OperatelogVO;
/*      */ 
/*      */ public abstract class ToftPanel extends UIPanel
/*      */ {
/*   36 */   private FramePanel m_frame = null;
/*   37 */   private ArrayList<FuncMenuExtends> alfuncMenuExtends = null;
/*   38 */   private ArrayList<String> alSysDefMenuCode = new ArrayList();
/*   39 */   private ButtonObject[] m_aryCurrentButtons = null;
/*      */ 
/*   42 */   protected ButtonObject m_boOK = new ButtonObject(NCLangRes.getInstance().getStrByID("COMMON", "UC001-0000044"), NCLangRes.getInstance().getStrByID("smcomm", "UPP1005-000108"), 0, "确定");
/*      */ 
/*   50 */   protected ButtonObject m_boCancel = new ButtonObject(NCLangRes.getInstance().getStrByID("COMMON", "UC001-0000008"), NCLangRes.getInstance().getStrByID("smcomm", "UPP1005-000109"), 0, "取消");
/*      */ 
/*   58 */   protected ButtonObject[] m_ConfirmButtonGroup = { this.m_boOK, this.m_boCancel };
/*      */ 
/*   60 */   private ClientEnvironment m_ceSingleton = null;
/*      */ 
/*   62 */   private Boolean m_isNeedButtonLog = null;
/*      */ 
/*  345 */   private HashMap<String, FuncMenuExtends> hmBOCodeToFuncMenuExtends = new HashMap();
/*      */ 
/*  348 */   private ButtonObject[] sysBos = null;
/*      */ 
/*  528 */   private Hashtable hashHotkey = null;
/*      */ 
/*  530 */   private Hashtable hashNameBO = null;
/*      */ 
/*  532 */   private Hashtable hashNameBOinvalid = null;
/*      */ 
/*  535 */   protected final int IMAGE_APPROVE = 2;
/*      */ 
/*  538 */   protected final int IMAGE_APPROVEANDFAIL = 3;
/*      */ 
/*  541 */   protected final int IMAGE_APPROVING = 4;
/*      */ 
/*  544 */   protected final int IMAGE_AUDIT = 5;
/*      */ 
/*  547 */   protected final int IMAGE_CANCEL = 0;
/*      */ 
/*  550 */   protected final int IMAGE_CLOSE = 6;
/*      */ 
/*  553 */   protected final int IMAGE_FINISH = 7;
/*      */ 
/*  556 */   protected final int IMAGE_FREEZE = 1;
/*      */ 
/*  560 */   protected final int IMAGE_NULL = -1;
/*      */ 
/*  563 */   protected final int IMAGE_RECORD = 8;
/*      */ 
/*  565 */   private boolean isReadHotKey = false;
/*      */ 
/*  568 */   private HashMap<String, String> m_aryParams = null;
/*      */ 
/*  570 */   private HotkeyRegisterVO[] m_hotkeyRegisterVOs = null;
/*      */ 
/*  572 */   private ArrayList m_notRegButtons = null;
/*      */ 
/*  574 */   private ArrayList m_regButtonsInvalid = null;
/*      */ 
/* 1294 */   private HashMap<String, Object> hmApiInterface = new HashMap();
/*      */ 
/*      */   public ToftPanel()
/*      */   {
/*   70 */     initialize();
/*      */   }
/*      */ 
/*      */   public void addMessageListener(MessageListener ml)
/*      */   {
/*   80 */     this.listenerList.add(MessageListener.class, ml);
/*      */   }
/*      */ 
/*      */   protected void firePostMessage(MessageEvent e)
/*      */   {
/*   91 */     Object[] listeners = this.listenerList.getListenerList();
/*   92 */     if (e == null) {
/*   93 */       e = new MessageEvent(this, 0, "");
/*      */     }
/*      */ 
/*   98 */     for (int i = listeners.length - 2; i >= 0; i -= 2)
/*   99 */       if (listeners[i] == MessageListener.class)
/*  100 */         ((MessageListener)listeners[(i + 1)]).messageReceived(e);
/*      */   }
/*      */ 
/*      */   public ButtonObject[] getButtons()
/*      */   {
/*  112 */     return this.m_aryCurrentButtons;
/*      */   }
/*      */ 
/*      */   public ButtonObject getButtonObjectByCode(String code) {
/*  116 */     return getBOByCode(getButtons(), code); }
/*      */ 
/*      */   private ButtonObject getBOByCode(ButtonObject[] bos, String code) {
/*  119 */     int count = (bos == null) ? 0 : bos.length;
/*  120 */     ButtonObject bo = null;
/*  121 */     for (int i = 0; i < count; ++i) {
/*  122 */       if (code.equals(bos[i].getCode())) {
/*  123 */         bo = bos[i];
/*  124 */         break; }
/*  125 */       if (bos[i].getChildCount() > 0) {
/*  126 */         bo = getBOByCode(bos[i].getChildButtonGroup(), code);
/*  127 */         if (bo != null) {
/*      */           break;
/*      */         }
/*      */       }
/*      */     }
/*  132 */     return bo;
/*      */   }
/*      */ 
/*      */   protected ClientEnvironment getClientEnvironment()
/*      */   {
/*  142 */     if (this.m_ceSingleton == null) {
/*  143 */       this.m_ceSingleton = ClientEnvironment.getInstance();
/*      */     }
/*  145 */     return this.m_ceSingleton;
/*      */   }
/*      */ 
/*      */   public FramePanel getFrame()
/*      */   {
/*  154 */     return this.m_frame;
/*      */   }
/*      */ 
/*      */   public abstract String getTitle();
/*      */ 
/*      */   public void handleButtonEvent(ButtonObject bo)
/*      */   {
/*  173 */     showHintMessage("");
/*      */ 
/*  175 */     if (bo == this.m_boOK)
/*  176 */       onOK();
/*  177 */     else if (bo == this.m_boCancel) {
/*  178 */       onCancel();
/*      */     }
/*  180 */     if ((getALFuncMenuExtends() != null) && (getALFuncMenuExtends().size() > 0)) {
/*  181 */       if (this.alSysDefMenuCode.contains(bo.getCode())) {
/*  182 */         boolean continueDone = true;
/*  183 */         for (int i = 0; (i < getALFuncMenuExtends().size()) && (continueDone); ++i) {
/*  184 */           continueDone = ((FuncMenuExtends)getALFuncMenuExtends().get(i)).preDoneSysDefMenuAction(bo);
/*      */         }
/*  186 */         if (continueDone) {
/*  187 */           onButtonClicked(bo);
/*  188 */           for (int i = 0; i < getALFuncMenuExtends().size(); ++i)
/*  189 */             ((FuncMenuExtends)getALFuncMenuExtends().get(i)).postDoneSysDefMenuAction(bo);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  194 */         FuncMenuExtends extend = (FuncMenuExtends)this.hmBOCodeToFuncMenuExtends.get(bo.getCode());
/*  195 */         if (extend != null)
/*  196 */           extend.doExtendsAction(bo);
/*      */         else {
/*  198 */           System.out.println(super.getClass().getCanonicalName() + " done menu " + bo.getCode() + " , no find FuncMenuExtends.");
/*      */         }
/*      */       }
/*      */     }
/*      */     else {
/*  203 */       onButtonClicked(bo);
/*      */     }
/*  205 */     if (!(getClientEnvironment().isInDebug()))
/*  206 */       writeButtonLog(bo);
/*      */   }
/*      */ 
/*      */   protected String getCurrentBillNo()
/*      */   {
/*  217 */     return null;
/*      */   }
/*      */ 
/*      */   private void writeButtonLog(final ButtonObject bo)
/*      */   {
/*  223 */     if (getClientEnvironment().getUserType() == 1) {
/*      */       return;
/*      */     }
/*  226 */     if (getClientEnvironment().getUserType() == 5) {
/*      */       return;
/*      */     }
/*      */ 
/*  230 */     new Thread()
/*      */     {
/*      */       public void run()
/*      */       {
/*  234 */         if ((OperateLog.isNeedButtonLog()) && (ToftPanel.this.isNeedButtonLog().booleanValue())) {
/*  235 */           OperatelogVO log = new OperatelogVO();
/*      */           try {
/*  237 */             log.setCompanyname(ToftPanel.this.getClientEnvironment().getCorporation().getUnitname());
/*  238 */             log.setPKCorp(ToftPanel.this.getClientEnvironment().getCorporation().getPrimaryKey());
/*  239 */             String enterTime = ClientEnvironment.getServerTime().toString();
/*  240 */             log.setLoginTime(enterTime);
/*  241 */             log.setEnterip(Desktop.getApplet().getParameter("USER_IP"));
/*  242 */             log.setOperatetype("button");
/*  243 */             log.setOpratorname(ToftPanel.this.getClientEnvironment().getUser().getUserName());
/*  244 */             log.setOperatorId(ToftPanel.this.getClientEnvironment().getUser().getPrimaryKey());
/*  245 */             log.setEnterfunction(ToftPanel.this.getFuncRegisterVO().getFunName());
/*  246 */             log.setEnterfunctioncode(ToftPanel.this.getFuncRegisterVO().getFunCode());
/*  247 */             log.setEnterbutton(bo.getName());
/*  248 */             log.setBillcode(ToftPanel.this.getCurrentBillNo());
/*  249 */             Desktop.getApplet().getLog().insertLog(log);
/*      */           } catch (Exception ex) {
/*  251 */             ex.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  230 */     .start();
/*      */   }
/*      */ 
/*      */   private Boolean isNeedButtonLog()
/*      */   {
/*  260 */     if (this.m_isNeedButtonLog == null) {
/*      */       try
/*      */       {
/*  263 */         this.m_isNeedButtonLog = Boolean.valueOf(SFServiceFacility.getFuncRegisterQueryService().queryIsNeedButtonLogByFunCode(getFuncRegisterVO().getFunCode()));
/*      */       } catch (BusinessException e) {
/*  265 */         Logger.error("query funcregister parameter error!", e);
/*  266 */         this.m_isNeedButtonLog = Boolean.TRUE;
/*      */       }
/*      */     }
/*  269 */     return this.m_isNeedButtonLog; }
/*      */ 
/*      */   void setIsNeedButtonLog(Boolean isNeedLog) {
/*  272 */     this.m_isNeedButtonLog = isNeedLog;
/*      */   }
/*      */ 
/*      */   private void handleException(Throwable exception)
/*      */   {
/*  283 */     System.out.println("--------- 未捕捉到的异常 ---------");
/*  284 */     exception.printStackTrace(System.out);
/*      */   }
/*      */ 
/*      */   private void initialize()
/*      */   {
/*      */     try
/*      */     {
/*  295 */       setName("ToftPanel");
/*  296 */       setLayout(new BorderLayout());
/*  297 */       setSize(774, 419);
/*      */ 
/*  299 */       setBackground(Style.getColor("业务界面.背景"));
/*      */     }
/*      */     catch (Throwable ivjExc) {
/*  302 */       handleException(ivjExc);
/*      */     }
/*      */ 
/*  305 */     this.isReadHotKey = false;
/*      */   }
/*      */ 
/*      */   public abstract void onButtonClicked(ButtonObject paramButtonObject);
/*      */ 
/*      */   protected void onCancel()
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void onOK()
/*      */   {
/*      */   }
/*      */ 
/*      */   public void removeMessageListener(MessageListener ml)
/*      */   {
/*  342 */     this.listenerList.remove(MessageListener.class, ml);
/*      */   }
/*      */ 
/*      */   public final ButtonObject[] getSysButtons()
/*      */   {
/*  350 */     if (this.sysBos == null) {
/*  351 */       return getButtons();
/*      */     }
/*  353 */     return this.sysBos;
/*      */   }
/*      */ 
/*      */   private UiManager getNCUIManager(Container con)
/*      */   {
/*  358 */     if (con instanceof UiManager)
/*  359 */       return ((UiManager)con);
/*  360 */     if (con.getParent() != null) {
/*  361 */       return getNCUIManager(con.getParent());
/*      */     }
/*  363 */     return null;
/*      */   }
/*      */ 
/*      */   protected void setButtons(ButtonObject[] btns)
/*      */   {
/*  375 */     this.sysBos = btns;
/*  376 */     UiManager ui = getNCUIManager(this);
/*  377 */     if ((ui != null) && (this != ui) && (this instanceof IUiPanel)) {
/*  378 */       this.m_aryCurrentButtons = btns;
/*  379 */       ui.setButtons(btns);
/*      */     } else {
/*  381 */       setButtons0(btns);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void setButtons(ButtonObject[] btns, String moduleCodeForButnPower) {
/*  386 */     if (this.m_frame != null) {
/*  387 */       String oldCode = this.m_frame.getModuleCodeForButnPower();
/*  388 */       this.m_frame.setModuleCodeForButnPower(moduleCodeForButnPower);
/*  389 */       if ((oldCode != null) && (!(oldCode.equals(moduleCodeForButnPower))) && (this.isReadHotKey)) {
/*  390 */         this.isReadHotKey = false;
/*      */       }
/*      */     }
/*  393 */     setButtons(btns); }
/*      */ 
/*      */   protected void setButtons(ButtonObject[] btns, String moduleCodeForButnPower, String pkCorp) {
/*  396 */     if (this.m_frame != null) {
/*  397 */       String oldCode = this.m_frame.getModuleCodeForButnPower();
/*  398 */       this.m_frame.setModuleCodeForButnPower(moduleCodeForButnPower);
/*  399 */       if ((oldCode != null) && (!(oldCode.equals(moduleCodeForButnPower))) && (this.isReadHotKey)) {
/*  400 */         this.isReadHotKey = false;
/*      */       }
/*  402 */       String oldPKCorp = this.m_frame.getClientPKCorp();
/*  403 */       this.m_frame.setClientPKCorp(pkCorp);
/*  404 */       if ((oldPKCorp != null) && (!(oldPKCorp.equals(pkCorp))) && (this.isReadHotKey)) {
/*  405 */         this.isReadHotKey = false;
/*      */       }
/*      */     }
/*  408 */     setButtons(btns);
/*      */   }
/*      */ 
/*      */   private void setButtons0(ButtonObject[] buttons) {
/*  412 */     ArrayList tempAl = new ArrayList();
/*  413 */     int count = (buttons == null) ? 0 : buttons.length;
/*  414 */     for (int i = 0; i < count; ++i) {
/*  415 */       if (buttons[i] != null)
/*  416 */         tempAl.add(buttons[i]);
/*      */     }
/*  418 */     buttons = (ButtonObject[])tempAl.toArray(new ButtonObject[0]);
/*      */ 
/*  420 */     reInitALSysDefMenuCodeData(buttons);
/*  421 */     ArrayList al = new ArrayList();
/*  422 */     al.addAll(this.alSysDefMenuCode);
/*      */ 
/*  424 */     if ((getALFuncMenuExtends() != null) && (getALFuncMenuExtends().size() > 0)) {
/*  425 */       List list = Arrays.asList(buttons);
/*  426 */       ArrayList alBos = new ArrayList(list);
/*  427 */       for (int i = 0; i < getALFuncMenuExtends().size(); ++i) {
/*  428 */         FuncMenuExtends extend = (FuncMenuExtends)getALFuncMenuExtends().get(i);
/*  429 */         extend.addExtendsMenus(alBos);
/*  430 */         initHMBOCodeToFuncMenuExtends(extend, al, alBos);
/*      */       }
/*  432 */       buttons = (ButtonObject[])alBos.toArray(new ButtonObject[0]);
/*      */     }
/*      */ 
/*  437 */     this.m_aryCurrentButtons = buttons;
/*  438 */     if (this.m_frame != null) {
/*  439 */       String moduleCode = this.m_frame.getModuleCodeForButnPower();
/*  440 */       if ((moduleCode != null) && (moduleCode.trim().length() > 0)) {
/*  441 */         initHotkeys();
/*      */       }
/*      */     }
/*  444 */     firePostMessage(new MessageEvent(this, 5, buttons)); }
/*      */ 
/*      */   private void initHMBOCodeToFuncMenuExtends(FuncMenuExtends extend, ArrayList<String> al, ArrayList<ButtonObject> alBos) {
/*  447 */     for (int i = 0; i < alBos.size(); ++i) {
/*  448 */       ButtonObject bo = (ButtonObject)alBos.get(i);
/*  449 */       if (!(al.contains(bo.getCode()))) {
/*  450 */         this.hmBOCodeToFuncMenuExtends.put(bo.getCode(), extend);
/*  451 */         al.add(bo.getCode());
/*  452 */         if (bo.getChildCount() > 0) {
/*  453 */           ArrayList bos = new ArrayList(Arrays.asList(bo.getChildButtonGroup()));
/*  454 */           initHMBOCodeToFuncMenuExtends(extend, al, bos); }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void reInitALSysDefMenuCodeData(ButtonObject[] buttons) {
/*  460 */     this.alSysDefMenuCode.clear();
/*  461 */     int count = (buttons == null) ? 0 : buttons.length;
/*  462 */     for (int i = 0; i < count; ++i)
/*  463 */       addAllBtnCodeToAL(this.alSysDefMenuCode, buttons[i]);
/*      */   }
/*      */ 
/*      */   private void addAllBtnCodeToAL(ArrayList<String> al, ButtonObject bo)
/*      */   {
/*  468 */     al.add(bo.getCode());
/*  469 */     ButtonObject[] children = bo.getChildButtonGroup();
/*  470 */     int count = (children == null) ? 0 : children.length;
/*  471 */     for (int i = 0; i < count; ++i)
/*  472 */       addAllBtnCodeToAL(al, children[i]);
/*      */   }
/*      */ 
/*      */   public void setFrame(FramePanel fp)
/*      */   {
/*  482 */     this.m_frame = fp;
/*      */   }
/*      */ 
/*      */   public void showErrorMessage(String err)
/*      */   {
/*  494 */     firePostMessage(new MessageEvent(this, 1, err));
/*      */   }
/*      */ 
/*      */   public void showHintMessage(String hint)
/*      */   {
/*  504 */     firePostMessage(new MessageEvent(this, 0, hint));
/*      */   }
/*      */ 
/*      */   public void showWarningMessage(String msg)
/*      */   {
/*  515 */     firePostMessage(new MessageEvent(this, 8, msg));
/*      */   }
/*      */ 
/*      */   public void updateButton(ButtonObject bo)
/*      */   {
/*  525 */     firePostMessage(new MessageEvent(this, 4, bo));
/*      */   }
/*      */ 
/*      */   protected void buttonBarFocus(KeyStroke hotKey, KeyEvent e)
/*      */     throws Exception
/*      */   {
/*  580 */     if (this.m_aryCurrentButtons == null) {
/*  581 */       firePostMessage(new MessageEvent(this, 12, null));
/*  582 */       return;
/*      */     }
/*  584 */     int modifiers = e.getModifiers();
/*  585 */     int keyCode = e.getKeyCode();
/*      */     try {
/*  587 */       if ((modifiers == 8) && (keyCode == 18))
/*  588 */         firePostMessage(new MessageEvent(this, 13, null));
/*  589 */       else if ((modifiers == 0) && (keyCode == 10))
/*  590 */         firePostMessage(new MessageEvent(this, 14, null));
/*  591 */       else if ((modifiers == 0) && (keyCode == 39))
/*  592 */         firePostMessage(new MessageEvent(this, 16, null));
/*  593 */       else if ((modifiers == 0) && (keyCode == 37))
/*  594 */         firePostMessage(new MessageEvent(this, 15, null));
/*  595 */       else if ((modifiers == 0) && (keyCode == 40))
/*  596 */         firePostMessage(new MessageEvent(this, 17, null));
/*      */     }
/*      */     catch (Exception ex) {
/*  599 */       System.out.println(ex.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean callButtonObjectMethod(String buttonObjctName, boolean isStartwith)
/*      */   {
/*  612 */     ButtonObject bo = null;
/*  613 */     ButtonObject[] boArray = getBtnbarButtons();
/*  614 */     int count = (boArray == null) ? 0 : boArray.length;
/*  615 */     for (int i = 0; (i < count) && (bo == null); ++i) {
/*  616 */       if (isStartwith) {
/*  617 */         if (boArray[i].getCode().trim().startsWith(buttonObjctName)) {
/*  618 */           bo = boArray[i];
/*      */         }
/*  620 */         int childcount = boArray[i].getChildCount();
/*  621 */         if ((bo == null) && (childcount > 0)) {
/*  622 */           ButtonObject[] childs = boArray[i].getChildButtonGroup();
/*  623 */           for (int j = 0; j < childcount; ++j)
/*  624 */             if (childs[j].getCode().trim().startsWith(buttonObjctName))
/*  625 */               bo = childs[j];
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  630 */         if (boArray[i].getCode().trim().equals(buttonObjctName)) {
/*  631 */           bo = boArray[i];
/*      */         }
/*  633 */         int childcount = boArray[i].getChildCount();
/*  634 */         if ((bo == null) && (childcount > 0)) {
/*  635 */           ButtonObject[] childs = boArray[i].getChildButtonGroup();
/*  636 */           for (int j = 0; j < childcount; ++j) {
/*  637 */             if (childs[j].getCode().trim().equals(buttonObjctName)) {
/*  638 */               bo = childs[j];
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  645 */     if ((bo == null) || (!(bo.isEnabled())) || (!(bo.isPower())) || ((bo.getParent() == null) && (!(bo.isVisible()))) || ((bo.getParent() != null) && (((!(bo.getParent().isEnabled())) || (!(bo.getParent().isVisible()))))))
/*  646 */       return false;
/*  647 */     if (bo.getChildCount() == 0)
/*      */     {
/*  650 */       onButtonClicked(bo);
/*      */     }
/*      */ 
/*  655 */     return true;
/*      */   }
/*      */ 
/*      */   protected String checkPrerequisite()
/*      */   {
/*  669 */     return null;
/*      */   }
/*      */ 
/*      */   protected String getCorpPrimaryKey()
/*      */   {
/*  678 */     return getClientEnvironment().getCorporation().getPrimaryKey();
/*      */   }
/*      */ 
/*      */   public Hashtable getHashHotkey()
/*      */   {
/*  687 */     return this.hashHotkey;
/*      */   }
/*      */ 
/*      */   public HotkeyRegisterVO[] getHotKeyVOs() {
/*  691 */     return this.m_hotkeyRegisterVOs;
/*      */   }
/*      */ 
/*      */   public String getModuleCode()
/*      */   {
/*  700 */     return getFrame().getModuleCode();
/*      */   }
/*      */ 
/*      */   public String getModuleID()
/*      */   {
/*  709 */     return getFrame().getModuleID();
/*      */   }
/*      */ 
/*      */   protected String getParameter(String key)
/*      */   {
/*  721 */     String value = null;
/*  722 */     if (getFrame().getApplet() != null) {
/*  723 */       value = getFrame().getApplet().getParameter(key);
/*      */     } else {
/*  725 */       if (this.m_aryParams == null) {
/*      */         try {
/*  727 */           String[][] params = SFServiceFacility.getFuncRegisterQueryService().queryParameter(ClientEnvironment.getInstance().getCorporation().getPk_corp(), getModuleCode());
/*  728 */           if (params == null) {
/*  729 */             params = new String[0][0];
/*      */           }
/*  731 */           this.m_aryParams = new HashMap();
/*  732 */           for (int i = 0; i < params.length; ++i)
/*  733 */             this.m_aryParams.put(params[i][0], params[i][1]);
/*      */         }
/*      */         catch (Exception e) {
/*  736 */           reportException(e);
/*      */         }
/*      */       }
/*  739 */       if (this.m_aryParams != null) {
/*  740 */         return ((String)this.m_aryParams.get(key));
/*      */       }
/*      */     }
/*  743 */     return value;
/*      */   }
/*      */ 
/*      */   public Dimension getPreferredSize()
/*      */   {
/*  755 */     return new Dimension(774, 419);
/*      */   }
/*      */ 
/*      */   public String getStatusBusinessDate()
/*      */   {
/*  765 */     return null;
/*      */   }
/*      */ 
/*      */   public String getStatusCurrentUser()
/*      */   {
/*  774 */     return null;
/*      */   }
/*      */ 
/*      */   public String getStatusHintStr()
/*      */   {
/*  783 */     return null;
/*      */   }
/*      */ 
/*      */   protected FuncRegisterVO getFuncRegisterVO()
/*      */   {
/*  792 */     if (getFrame() == null) return null;
/*  793 */     return getFrame().getFuncRegisterVO();
/*      */   }
/*      */ 
/*      */   protected void hotKeyPressed(KeyStroke hotKey)
/*      */     throws Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void hotKeyPressed(KeyStroke hotKey, KeyEvent e)
/*      */     throws Exception
/*      */   {
/*  826 */     if (this.m_aryCurrentButtons == null) {
/*  827 */       firePostMessage(new MessageEvent(this, 12, null));
/*  828 */       return;
/*      */     }
/*      */ 
/*  837 */     ButtonObject bo = null;
/*  838 */     if (getHashHotkey() != null)
/*  839 */       bo = (ButtonObject)getHashHotkey().get(hotKey);
/*  840 */     if ((bo == null) || (!(bo.isEnabled())) || (!(bo.isPower())) || ((bo.getParent() == null) && (!(bo.isVisible()))) || ((bo.getParent() != null) && (((!(bo.getParent().isEnabled())) || (!(bo.getParent().isVisible())))))) {
/*  841 */       if (bo == null) {
/*  842 */         String[] butnNames = NCKey.getNameByKeyStroke(hotKey);
/*  843 */         if (butnNames != null) {
/*  844 */           for (int i = 0; !(callButtonObjectMethod(butnNames[i], false)); ++i);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  849 */       return;
/*      */     }
/*  851 */     if (bo.getChildCount() == 0) {
/*  852 */       firePostMessage(new MessageEvent(this, 12, null));
/*  853 */       onButtonClicked(bo);
/*  854 */       e.consume();
/*      */     } else {
/*  856 */       firePostMessage(new MessageEvent(this, 11, bo));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void initHotkeys() {
/*  861 */     String moduleCode = this.m_frame.getModuleCodeForButnPower();
/*  862 */     String strParam = NCAppletStub.getInstance().getParameter("LOGIN_TYPE");
/*  863 */     if ((!(ClientEnvironment.getInstance().isInDebug())) && (strParam != null) && (NCAppletStub.getInstance().getParameter("LOGIN_TYPE").equals("SYS_ADM"))) {
/*  864 */       return;
/*      */     }
/*      */ 
/*  868 */     HotkeyRegisterVO vo = null;
/*  869 */     if (!(this.isReadHotKey)) {
/*      */       try {
/*  871 */         if ((this.m_frame == null) || (moduleCode == null) || (moduleCode.trim().length() == 0)) {
/*  872 */           if (this.m_hotkeyRegisterVOs == null)
/*  873 */             this.m_hotkeyRegisterVOs = SFServiceFacility.getHotKeyRegisterQueryService().queryAllHotkeyRegisterVOs(null);
/*      */         }
/*      */         else {
/*  876 */           this.m_hotkeyRegisterVOs = this.m_frame.getHotKeyVOs();
/*  877 */           if (this.m_hotkeyRegisterVOs == null) {
/*  878 */             this.m_hotkeyRegisterVOs = SFServiceFacility.getHotKeyRegisterQueryService().queryAllHotkeyRegisterVOs(moduleCode);
/*      */           }
/*  880 */           this.isReadHotKey = true;
/*      */         }
/*      */       } catch (Exception e) {
/*  883 */         this.isReadHotKey = false;
/*  884 */         handleException(e);
/*  885 */         return;
/*      */       }
/*  887 */       this.hashNameBO = new Hashtable();
/*  888 */       this.hashNameBOinvalid = new Hashtable();
/*  889 */       if (this.m_hotkeyRegisterVOs != null) {
/*  890 */         for (int i = 0; i < this.m_hotkeyRegisterVOs.length; ++i) {
/*  891 */           if (this.m_hotkeyRegisterVOs[i].getKeyStroke() != null)
/*  892 */             this.hashNameBO.put(this.m_hotkeyRegisterVOs[i].getButton_name(), this.m_hotkeyRegisterVOs[i]);
/*      */           else
/*  894 */             this.hashNameBOinvalid.put(this.m_hotkeyRegisterVOs[i].getButton_name(), "InValid");
/*      */         }
/*      */       }
/*      */     }
/*  898 */     if (getHashHotkey() == null)
/*  899 */       this.hashHotkey = new Hashtable();
/*      */     else
/*  901 */       getHashHotkey().clear();
/*  902 */     this.m_notRegButtons = new ArrayList();
/*  903 */     this.m_regButtonsInvalid = new ArrayList();
/*  904 */     if ((getButtons() != null) && (this.hashNameBO != null)) {
/*  905 */       for (int i = 0; i < getButtons().length; ++i) {
/*  906 */         ButtonObject bo = getButtons()[i];
/*  907 */         if ((bo != null) && (bo.getCode() != null)) {
/*  908 */           vo = (HotkeyRegisterVO)this.hashNameBO.get(bo.getCode());
/*  909 */           if (vo != null) {
/*  910 */             bo.setHotKey(vo.getHotkey());
/*  911 */             bo.setDisplayHotkey(vo.getDisplay());
/*  912 */             Integer modif = vo.getAlt_meta_ctrl_shift();
/*  913 */             bo.setModifiers((modif == null) ? -1 : modif.intValue());
/*  914 */             getHashHotkey().put(vo.getKeyStroke(), bo);
/*      */           }
/*  921 */           else if (!(this.hashNameBOinvalid.containsKey(bo.getCode()))) {
/*  922 */             this.m_notRegButtons.add(bo.getCode());
/*      */           } else {
/*  924 */             this.m_regButtonsInvalid.add(bo.getCode());
/*      */           }
/*  926 */           recursionUpdateButton(bo);
/*      */         }
/*      */       }
/*      */     }
/*  930 */     if (ClientCtrlParas.getInstance().isAutoRegisterMenubutton()) {
/*  931 */       if ((this.m_notRegButtons != null) && (this.m_notRegButtons.size() > 0) && (this.m_frame != null) && (moduleCode != null)) {
/*  932 */         System.out.println("----未注册快捷键的按钮数据");
/*  933 */         System.out.println("----节点：" + moduleCode);
/*  934 */         HotkeyRegisterVO[] vos = new HotkeyRegisterVO[this.m_notRegButtons.size()];
/*  935 */         for (int i = 0; i < this.m_notRegButtons.size(); ++i) {
/*  936 */           vo = new HotkeyRegisterVO();
/*  937 */           vo.setFun_code(moduleCode);
/*  938 */           String butnName = (String)this.m_notRegButtons.get(i);
/*  939 */           vo.setButton_name(butnName);
/*  940 */           String[] hotKey = NCKey.getHotKeyByName(butnName);
/*  941 */           if (hotKey != null) {
/*  942 */             vo.setHotkey(hotKey[1]);
/*  943 */             vo.setDisplay(hotKey[0]);
/*  944 */             vo.setAlt_meta_ctrl_shift(new Integer(hotKey[2]));
/*      */           } else {
/*  946 */             vo.setHotkey("*");
/*  947 */             vo.setAlt_meta_ctrl_shift(new Integer(-1));
/*      */           }
/*  949 */           vos[i] = vo;
/*  950 */           System.out.print(vo.getButton_name() + " ");
/*      */         }
/*  952 */         System.out.println("\n");
/*      */         try
/*      */         {
/*  955 */           SFServiceFacility.getHotKeyRegisterService().insertArray(vos);
/*      */         } catch (Exception e) {
/*  957 */           handleException(e);
/*      */         }
/*      */       }
/*  960 */       if ((this.m_regButtonsInvalid != null) && (this.m_regButtonsInvalid.size() > 0)) {
/*  961 */         System.out.println("----注册但是无效的按钮数据");
/*  962 */         System.out.println("----节点：" + moduleCode);
/*  963 */         for (int i = 0; i < this.m_regButtonsInvalid.size(); ++i) {
/*  964 */           System.out.print(this.m_regButtonsInvalid.get(i) + " ");
/*      */         }
/*  966 */         System.out.println("\n");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isshowMenuPanel() {
/*  972 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean isshowTitlePanel() {
/*  976 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean onClosing()
/*      */   {
/*  987 */     return true;
/*      */   }
/*      */ 
/*      */   protected void postInitParameters()
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void postInit()
/*      */   {
/*      */   }
/*      */ 
/*      */   protected ButtonObject recursionFindButton(ButtonObject bo, String name)
/*      */   {
/* 1004 */     if (bo != null) {
/* 1005 */       if (bo.getCode().equalsIgnoreCase(name))
/* 1006 */         return bo;
/* 1007 */       if ((bo.getChildCount() > 0) && (bo.isEnabled())) {
/* 1008 */         int i = 0; if (i < bo.getChildCount()) {
/* 1009 */           return recursionFindButton(bo.getChildButtonGroup()[i], name);
/*      */         }
/*      */       }
/*      */     }
/* 1013 */     return null;
/*      */   }
/*      */ 
/*      */   protected void recursionUpdateButton(ButtonObject bo) {
/* 1017 */     if (bo != null) {
/* 1018 */       updateButton(bo);
/* 1019 */       for (int i = 0; i < bo.getChildCount(); ++i) {
/* 1020 */         ButtonObject childBO = bo.getChildButtonGroup()[i];
/* 1021 */         HotkeyRegisterVO vo = null;
/* 1022 */         if (this.hashNameBO != null) {
/* 1023 */           vo = (HotkeyRegisterVO)this.hashNameBO.get(childBO.getCode());
/*      */         }
/* 1025 */         if (vo != null) {
/* 1026 */           childBO.setHotKey(vo.getHotkey());
/* 1027 */           childBO.setDisplayHotkey(vo.getDisplay());
/* 1028 */           Integer modif = vo.getAlt_meta_ctrl_shift();
/* 1029 */           childBO.setModifiers((modif == null) ? -1 : modif.intValue());
/* 1030 */           getHashHotkey().put(vo.getKeyStroke(), childBO);
/*      */         }
/* 1032 */         else if ((this.hashNameBOinvalid != null) && (!(this.hashNameBOinvalid.containsKey(childBO.getCode())))) {
/* 1033 */           this.m_notRegButtons.add(childBO.getCode());
/*      */         } else {
/* 1035 */           this.m_regButtonsInvalid.add(childBO.getCode()); }
/* 1036 */         recursionUpdateButton(childBO);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void reportException(Exception e)
/*      */   {
/* 1068 */     getClientEnvironment().handleException(e);
/*      */   }
/*      */ 
/*      */   public void setHashHotkey(Hashtable newHashHotkey)
/*      */   {
/* 1078 */     this.hashHotkey = newHashHotkey;
/*      */   }
/*      */ 
/*      */   public void setHotKeyVOs(HotkeyRegisterVO[] hotKeyVOs) {
/* 1082 */     this.m_hotkeyRegisterVOs = hotKeyVOs;
/*      */   }
/*      */ 
/*      */   public void setImageType(int type)
/*      */   {
/* 1093 */     getFrame().setImageType(type);
/*      */   }
/*      */ 
/*      */   void setMenuAndTilte()
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void setParameters(HashMap<String, String> params)
/*      */   {
/* 1124 */     this.m_aryParams = params;
/*      */   }
/*      */ 
/*      */   public void setReadHotKey(boolean newIsReadHotKey)
/*      */   {
/* 1134 */     this.isReadHotKey = newIsReadHotKey;
/*      */   }
/*      */ 
/*      */   public void setTitleText(String title)
/*      */   {
/* 1144 */     if (getFrame() != null)
/* 1145 */       getFrame().setTitleText(title);
/*      */   }
/*      */ 
/*      */   public int showOkCancelMessage(String msg)
/*      */   {
/* 1155 */     return MessageDialog.showOkCancelDlg(this, null, msg);
/*      */   }
/*      */ 
/*      */   public int showYesNoCancelMessage(String msg)
/*      */   {
/* 1165 */     return MessageDialog.showYesNoCancelDlg(this, null, msg);
/*      */   }
/*      */ 
/*      */   public int showYesNoMessage(String msg)
/*      */   {
/* 1175 */     return MessageDialog.showYesNoDlg(this, null, msg);
/*      */   }
/*      */ 
/*      */   public void updateButtons()
/*      */   {
/* 1183 */     if (this.m_frame != null) {
/* 1184 */       String moduleCode = this.m_frame.getModuleCodeForButnPower();
/* 1185 */       if ((moduleCode != null) && (moduleCode.trim().length() > 0)) {
/* 1186 */         initHotkeys();
/*      */       }
/*      */     }
/* 1189 */     firePostMessage(new MessageEvent(this, 6, null));
/*      */   }
/*      */ 
/*      */   public void windowClosed(WindowEvent e)
/*      */   {
/*      */   }
/*      */ 
/*      */   private ButtonObject[] getBtnbarButtons()
/*      */   {
/* 1209 */     return getFrame().getButtonBar().getValidateBtnArray();
/*      */   }
/*      */ 
/*      */   public boolean isPowerBtn(String btnCode)
/*      */   {
/* 1216 */     boolean isFind = false;
/* 1217 */     ButtonObject[] boArray = getBtnbarButtons();
/* 1218 */     int count = (boArray == null) ? 0 : boArray.length;
/* 1219 */     for (int i = 0; (i < count) && (!(isFind)); ++i) {
/* 1220 */       if (boArray[i].getCode().trim().equals(btnCode)) {
/* 1221 */         isFind = true;
/* 1222 */         break;
/*      */       }
/* 1224 */       int childcount = boArray[i].getChildCount();
/* 1225 */       if ((!(isFind)) && (childcount > 0)) {
/* 1226 */         ButtonObject[] childs = boArray[i].getChildButtonGroup();
/* 1227 */         for (int j = 0; j < childcount; ++j) {
/* 1228 */           if (childs[j].getCode().trim().equals(btnCode)) {
/* 1229 */             isFind = true;
/* 1230 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1236 */     return (isFind);
/*      */   }
/*      */ 
/*      */   public void addLinkListener(LinkListener listener)
/*      */   {
/* 1241 */     this.listenerList.add(LinkListener.class, listener); }
/*      */ 
/*      */   public void fireLinkEvent(Object userObject, int operateID) {
/* 1244 */     LinkEvent event = new LinkEvent(this, operateID, userObject);
/* 1245 */     LinkListener[] listeners = (LinkListener[])getListeners(LinkListener.class);
/* 1246 */     int count = (listeners == null) ? 0 : listeners.length;
/* 1247 */     for (int i = 0; i < count; ++i)
/* 1248 */       listeners[i].dealLinkEvent(event);
/*      */   }
/*      */ 
/*      */   public int getLinkedType() {
/* 1252 */     if (getFrame() == null)
/* 1253 */       throw new RuntimeException("framepanel is null, can't invoke nc.ui.pub.ToftPanel.getLinkedType() method. ");
/* 1254 */     return getFrame().getLinkType();
/*      */   }
/*      */ 
/*      */   public void updateStatusBarAccountMsg(String newMsg)
/*      */   {
/* 1261 */     if ((getFrame() != null) && (newMsg != null))
/* 1262 */       getFrame().getStatusBar().setAccountMessage(newMsg);
/*      */   }
/*      */ 
/*      */   public void showProgressBar(boolean b)
/*      */   {
/* 1267 */     if (getFrame() != null)
/* 1268 */       if (b)
/* 1269 */         getFrame().getStatusBar().showProgressBar();
/*      */       else
/* 1271 */         getFrame().getStatusBar().hideProgressBar();
/*      */   }
/*      */ 
/*      */   public void addComponentIntoStatusBar(Component comp, int index)
/*      */   {
/* 1276 */     if (getFrame() != null)
/* 1277 */       getFrame().getStatusBar().insertCompToStatusBar(comp, index);
/*      */   }
/*      */ 
/*      */   public void removeComponentFromStatusBar(Component comp) {
/* 1281 */     if (getFrame() != null)
/* 1282 */       getFrame().getStatusBar().removeCompFromStatusBar(comp);
/*      */   }
/*      */ 
/*      */   public ArrayList<FuncMenuExtends> getALFuncMenuExtends()
/*      */   {
/* 1288 */     return this.alfuncMenuExtends; }
/*      */ 
/*      */   void setALFuncMenuExtends(ArrayList<FuncMenuExtends> alExtends) {
/* 1291 */     this.alfuncMenuExtends = alExtends;
/*      */   }
/*      */ 
/*      */   public Object lookupUIApi(String interfaceClassName)
/*      */   {
/* 1296 */     Object o = this.hmApiInterface.get(interfaceClassName);
/* 1297 */     if (o == null) {
/* 1298 */       o = FuncNodeApiFactory.getInstance().lookup(this, interfaceClassName);
/* 1299 */       if (o != null)
/* 1300 */         this.hmApiInterface.put(interfaceClassName, o);
/*      */     }
/* 1302 */     return o;
/*      */   }
/*      */ 
/*      */   public void fireFunNodeConstructed(IFuncWindow window)
/*      */   {
/*      */   }
/*      */ 
/*      */   public boolean lockUI()
/*      */   {
/* 1314 */     if (getFrame() != null) {
/* 1315 */       getFrame().lockFrame();
/* 1316 */       return true;
/*      */     }
/* 1318 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean releaseUI()
/*      */   {
/* 1323 */     if (getFrame() != null) {
/* 1324 */       getFrame().releaseFrame();
/* 1325 */       return true;
/*      */     }
/* 1327 */     return false;
/*      */   }
/*      */ }

 