/*     */ package nc.ui.pf.export;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.table.TableColumn;
/*     */ import nc.ui.pub.beans.MessageDialog;
/*     */ import nc.ui.pub.beans.UIButton;
/*     */ import nc.ui.pub.beans.UIDialog;
/*     */ import nc.ui.pub.beans.UIPanel;
/*     */ import nc.ui.pub.beans.UITable;
/*     */ import nc.ui.pub.beans.UITablePane;
/*     */ import nc.ui.pub.beans.table.NCTableModel;
/*     */ import nc.ui.pub.pfflow02.BorderCustomerLine;
/*     */ import nc.ui.pub.table.TableStruDataBO_Client;
/*     */ import nc.vo.pf.export.ControlVO;
/*     */ import nc.vo.pf.export.ItemVO;
/*     */ import nc.vo.pub.table.TableFieldVO;
/*     */ import nc.vo.pub.table.TableVO;
/*     */ import nc.vo.pub.util.CommonUtilities;
/*     */ 
/*     */ public class CreateScriptDlg extends UIDialog
/*     */   implements ActionListener
/*     */ {
/*  34 */   private JLabel lbChoose = null;
/*  35 */   private UIButton btnCancel = null;
/*  36 */   private UIButton btnExport = null;
/*  37 */   private UIButton btnDelAll = null;
/*  38 */   private UIButton btnSelectAll = null;
/*  39 */   private UIPanel pnlContainer = null;
/*  40 */   private UIPanel pnlSouth = null;
/*  41 */   private UIPanel pnlNorth = null;
/*  42 */   private JPanel dlgContentPane = null;
/*  43 */   private UITablePane tablePnl = null;
/*     */ 
/*  45 */   private ItemVO m_itemvo = null;
/*  46 */   private ControlVO m_ctlvo = null;
/*  47 */   private String[] m_pkList = null;
/*  48 */   private String[] m_dirList = null;
/*  49 */   private TableVO m_tableVO = null;
/*     */ 
/*     */   public CreateScriptDlg(ItemVO itemvo, ControlVO ctlvo, Container parent)
/*     */   {
/*  73 */     super(parent);
/*  74 */     setItemvo(itemvo);
/*  75 */     setCtlvo(ctlvo);
/*  76 */     initialize();
/*     */   }
/*     */ 
/*     */   private void initialize()
/*     */   {
/*     */     try
/*     */     {
/*  83 */       setName("CreateScriptDlg");
/*  84 */       setDefaultCloseOperation(2);
/*  85 */       setSize(600, 480);
/*  86 */       setContentPane(getDlgContentPane());
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/*  90 */       handleException(e);
/*     */     }
/*  92 */     getTablePnl().getTable().setAutoResizeMode(0);
/*     */   }
/*     */ 
/*     */   public void initData()
/*     */   {
/*  97 */     getPnlSouth().setBorder(new BorderCustomerLine(1, 0, 0, 0, new Color(159, 142, 124)));
/*  98 */     getBtnCancel().addActionListener(this);
/*  99 */     getBtnExport().addActionListener(this);
/* 100 */     getBtnSelectAll().addActionListener(this);
/* 101 */     getBtnDelAll().addActionListener(this);
/*     */ 
/* 104 */     String tableName = getItemvo().getItemRule();
/* 105 */     setTableVO(getTableVOFromDb(tableName));
/* 106 */     displayTable();
/*     */ 
/* 109 */     doGetTableData();
/*     */   }
/*     */ 
/*     */   private JLabel getLbChoose()
/*     */   {
/* 114 */     if (this.lbChoose == null)
/*     */     {
/*     */       try
/*     */       {
/* 118 */         this.lbChoose = new JLabel();
/* 119 */         this.lbChoose.setName("lbChoose");
/* 120 */         this.lbChoose.setText("请选择");
/* 121 */         this.lbChoose.setMaximumSize(new Dimension(50, 22));
/* 122 */         this.lbChoose.setForeground(Color.black);
/* 123 */         this.lbChoose.setPreferredSize(new Dimension(42, 22));
/* 124 */         this.lbChoose.setFont(new Font("dialog", 1, 18));
/*     */       }
/*     */       catch (Throwable e)
/*     */       {
/* 128 */         handleException(e);
/*     */       }
/*     */     }
/* 131 */     return this.lbChoose;
/*     */   }
/*     */ 
/*     */   private UIButton getBtnCancel()
/*     */   {
/* 136 */     if (this.btnCancel == null)
/*     */     {
/*     */       try
/*     */       {
/* 140 */         this.btnCancel = new UIButton();
/* 141 */         this.btnCancel.setName("BtnCancel");
/* 142 */         this.btnCancel.setText("取消");
/*     */       }
/*     */       catch (Throwable ivjExc)
/*     */       {
/* 146 */         handleException(ivjExc);
/*     */       }
/*     */     }
/* 149 */     return this.btnCancel;
/*     */   }
/*     */ 
/*     */   private UIButton getBtnDelAll()
/*     */   {
/* 154 */     if (this.btnDelAll == null)
/*     */     {
/*     */       try
/*     */       {
/* 158 */         this.btnDelAll = new UIButton();
/* 159 */         this.btnDelAll.setName("BtnDelAll");
/* 160 */         this.btnDelAll.setPreferredSize(new Dimension(70, 22));
/* 161 */         this.btnDelAll.setText("全消");
/*     */       }
/*     */       catch (Throwable ivjExc)
/*     */       {
/* 165 */         handleException(ivjExc);
/*     */       }
/*     */     }
/* 168 */     return this.btnDelAll;
/*     */   }
/*     */ 
/*     */   private UIButton getBtnExport()
/*     */   {
/* 173 */     if (this.btnExport == null)
/*     */     {
/*     */       try
/*     */       {
/* 177 */         this.btnExport = new UIButton();
/* 178 */         this.btnExport.setName("BtnExport");
/* 179 */         this.btnExport.setText("导出");
/*     */       }
/*     */       catch (Throwable ivjExc)
/*     */       {
/* 183 */         handleException(ivjExc);
/*     */       }
/*     */     }
/* 186 */     return this.btnExport;
/*     */   }
/*     */ 
/*     */   private UIButton getBtnSelectAll()
/*     */   {
/* 191 */     if (this.btnSelectAll == null)
/*     */     {
/*     */       try
/*     */       {
/* 195 */         this.btnSelectAll = new UIButton();
/* 196 */         this.btnSelectAll.setName("BtnSelectAll");
/* 197 */         this.btnSelectAll.setPreferredSize(new Dimension(70, 22));
/* 198 */         this.btnSelectAll.setText("全选");
/*     */       }
/*     */       catch (Throwable ivjExc)
/*     */       {
/* 202 */         handleException(ivjExc);
/*     */       }
/*     */     }
/* 205 */     return this.btnSelectAll;
/*     */   }
/*     */ 
/*     */   private UIPanel getPnlContainer()
/*     */   {
/* 210 */     if (this.pnlContainer == null)
/*     */     {
/*     */       try
/*     */       {
/* 214 */         this.pnlContainer = new UIPanel();
/* 215 */         this.pnlContainer.setName("PnlContainer");
/* 216 */         this.pnlContainer.setPreferredSize(new Dimension(453, 403));
/* 217 */         this.pnlContainer.setLayout(new BorderLayout());
/* 218 */         getPnlContainer().add(getTablePnl(), "Center");
/* 219 */         getPnlContainer().add(getPnlSouth(), "South");
/* 220 */         getPnlContainer().add(getPnlNorth(), "North");
/*     */       }
/*     */       catch (Throwable ivjExc)
/*     */       {
/* 224 */         handleException(ivjExc);
/*     */       }
/*     */     }
/* 227 */     return this.pnlContainer;
/*     */   }
/*     */ 
/*     */   private UIPanel getPnlNorth()
/*     */   {
/* 232 */     if (this.pnlNorth == null)
/*     */     {
/*     */       try
/*     */       {
/* 236 */         this.pnlNorth = new UIPanel();
/* 237 */         this.pnlNorth.setName("UIPanel1");
/* 238 */         this.pnlNorth.setPreferredSize(new Dimension(10, 30));
/* 239 */         this.pnlNorth.setLayout(new BorderLayout());
/* 240 */         getPnlNorth().add(getLbChoose(), "North");
/*     */       }
/*     */       catch (Throwable ivjExc)
/*     */       {
/* 244 */         handleException(ivjExc);
/*     */       }
/*     */     }
/* 247 */     return this.pnlNorth;
/*     */   }
/*     */ 
/*     */   private JPanel getDlgContentPane()
/*     */   {
/* 252 */     if (this.dlgContentPane == null)
/*     */     {
/*     */       try
/*     */       {
/* 256 */         this.dlgContentPane = new JPanel();
/* 257 */         this.dlgContentPane.setName("dlgContentPane");
/* 258 */         this.dlgContentPane.setLayout(new BorderLayout());
/* 259 */         getDlgContentPane().add(getPnlContainer(), "Center");
/*     */       }
/*     */       catch (Throwable e)
/*     */       {
/* 263 */         handleException(e);
/*     */       }
/*     */     }
/* 266 */     return this.dlgContentPane;
/*     */   }
/*     */ 
/*     */   private UITablePane getTablePnl()
/*     */   {
/* 271 */     if (this.tablePnl == null)
/*     */     {
/*     */       try
/*     */       {
/* 275 */         this.tablePnl = new UITablePane();
/* 276 */         this.tablePnl.setName("tablePnl");
/* 277 */         this.tablePnl.setPreferredSize(new Dimension(453, 380));
/*     */       }
/*     */       catch (Throwable ivjExc)
/*     */       {
/* 281 */         handleException(ivjExc);
/*     */       }
/*     */     }
/* 284 */     return this.tablePnl;
/*     */   }
/*     */ 
/*     */   private UIPanel getPnlSouth()
/*     */   {
/* 289 */     if (this.pnlSouth == null)
/*     */     {
/*     */       try
/*     */       {
/* 293 */         this.pnlSouth = new UIPanel();
/* 294 */         this.pnlSouth.setName("PnlSouth");
/* 295 */         this.pnlSouth.setPreferredSize(new Dimension(0, 40));
/* 296 */         this.pnlSouth.setLayout(new FlowLayout());
/* 297 */         this.pnlSouth.setBackground(Color.white);
/* 298 */         getPnlSouth().add(getBtnSelectAll(), getBtnSelectAll().getName());
/* 299 */         getPnlSouth().add(getBtnDelAll(), getBtnDelAll().getName());
/* 300 */         getPnlSouth().add(getBtnExport(), getBtnExport().getName());
/* 301 */         getPnlSouth().add(getBtnCancel(), getBtnCancel().getName());
/*     */       }
/*     */       catch (Throwable ivjExc)
/*     */       {
/* 305 */         handleException(ivjExc);
/*     */       }
/*     */     }
/* 308 */     return this.pnlSouth;
/*     */   }
/*     */ 
/*     */   public ControlVO getCtlvo()
/*     */   {
/* 313 */     return this.m_ctlvo;
/*     */   }
/*     */ 
/*     */   public String[] getDirList()
/*     */   {
/* 318 */     return this.m_dirList;
/*     */   }
/*     */ 
/*     */   public ItemVO getItemvo()
/*     */   {
/* 323 */     return this.m_itemvo;
/*     */   }
/*     */ 
/*     */   public String[] getPkList()
/*     */   {
/* 328 */     return this.m_pkList;
/*     */   }
/*     */ 
/*     */   public TableVO getTableVO()
/*     */   {
/* 333 */     return this.m_tableVO;
/*     */   }
/*     */ 
/*     */   public void setCtlvo(ControlVO newM_ctlvo)
/*     */   {
/* 338 */     this.m_ctlvo = newM_ctlvo;
/*     */   }
/*     */ 
/*     */   public void setDirList(String[] newM_dirList)
/*     */   {
/* 343 */     this.m_dirList = newM_dirList;
/*     */   }
/*     */ 
/*     */   public void setItemvo(ItemVO newM_itemvo)
/*     */   {
/* 348 */     this.m_itemvo = newM_itemvo;
/*     */   }
/*     */ 
/*     */   public void setPkList(String[] newM_pkList)
/*     */   {
/* 353 */     this.m_pkList = newM_pkList;
/*     */   }
/*     */ 
/*     */   public void setTableVO(TableVO newM_tableVO)
/*     */   {
/* 358 */     this.m_tableVO = newM_tableVO;
/*     */   }
/*     */ 
/*     */   public void showDlg()
/*     */   {
/* 363 */     initData();
/* 364 */     showModal();
/*     */   }
/*     */ 
/*     */   private void handleException(Throwable exception)
/*     */   {
/*     */   }
/*     */ 
/*     */   public TableVO getTableVOFromDb(String tableName)
/*     */   {
/* 376 */     TableVO table = null;
/*     */     try
/*     */     {
/* 388 */       table = TableStruDataBO_Client.getTable(0, "", tableName);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 392 */       e.printStackTrace();
/*     */     }
/* 394 */     return table;
/*     */   }
/*     */ 
/*     */   private void doGetTableData()
/*     */   {
/*     */     try
/*     */     {
/* 404 */       Vector data = XMLTableStruDataBO_Client.getTableData(getTableVO(), getWhereCond());
/* 405 */       if (data != null)
/*     */       {
/* 407 */         ((NCTableModel)getTablePnl().getTable().getModel()).setDataVector(data);
/* 408 */         ((NCTableModel)getTablePnl().getTable().getModel()).setColEditable(0, true);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 413 */       reportException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void displayTable()
/*     */   {
/*     */     try
/*     */     {
/* 424 */       TableVO table = getTableVO();
				if(table==null)
					return;
/* 425 */       Vector tableHeader = new Vector();
/* 426 */       tableHeader.addElement("selected");
/* 427 */       for (int i = 0; i < table.getTableFields().length; ++i)
/*     */       {
/* 429 */         tableHeader.addElement(table.getTableFields()[i].getFieldName().toLowerCase());
/*     */       }
/* 431 */       NCTableModel tableModel = new NCTableModel();
/* 432 */       tableModel.setColumnIdentifiers(tableHeader);
/* 433 */       getTablePnl().getTable().setModel(tableModel);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 437 */       reportException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private String getWhereCond()
/*     */   {
/* 446 */     String condition = getItemvo().getFixedWhere();
/* 447 */     if ((getItemvo().getCorpField() != null) && (getCtlvo().getPkCorp() != null)) {
/* 448 */       condition = condition + " and " + getItemvo().getCorpField() + "='" + getCtlvo().getPkCorp() + "'";
/*     */     }
/* 450 */     if ((getItemvo().getSysField() != null) && (getCtlvo().getPkSys() != null)) {
/* 451 */       condition = condition + " and " + getItemvo().getSysField() + "='" + getCtlvo().getPkSys() + "'";
/*     */     }
/* 453 */     condition = CommonUtilities.spaceToNull(condition);
/* 454 */     return condition;
/*     */   }
/*     */ 
/*     */   private void getSelectedPkListAndDirList()
/*     */   {
/* 462 */     Vector vec = new Vector();
/* 463 */     Vector vecDir = new Vector();
/* 464 */     String[] tablePrimaryKey = getTableVO().getTablePrimaryKey();
/*     */ 
/* 466 */     if ((tablePrimaryKey == null) || (tablePrimaryKey[0] == null))
/*     */     {
/* 468 */       MessageDialog.showErrorDlg(this, "错误", "表主键为空, 无法生成数据库脚本");
/* 469 */       return;
/*     */     }
/*     */ 
/* 473 */     int primaryKeyIndex = getTablePnl().getTable().getColumn(tablePrimaryKey[0].toLowerCase()).getModelIndex();
/*     */ 
/* 476 */     int dirFieldIndex = -1;
/* 477 */     if (getItemvo().getGrpField() != null) {
/* 478 */       dirFieldIndex = getTablePnl().getTable().getColumn(getItemvo().getGrpField().trim().toLowerCase()).getModelIndex();
/*     */     }
/*     */ 
/* 481 */     Object[][] data = ((NCTableModel)getTablePnl().getTable().getModel()).getDataArray();
/*     */ 
/* 484 */     for (int i = 0; i < data.length; ++i)
/*     */     {
/* 486 */       boolean isSelected = ((Boolean)data[i][0]).booleanValue();
/* 487 */       if (!(isSelected))
/*     */         continue;
/* 489 */       vec.add(data[i][primaryKeyIndex].toString());
/* 490 */       if (dirFieldIndex >= 0) {
/* 491 */         vecDir.add(data[i][dirFieldIndex].toString());
/*     */       }
/*     */     }
/*     */ 
/* 495 */     if ((vec != null) && (vec.size() != 0))
/*     */     {
/* 497 */       this.m_pkList = new String[vec.size()];
/* 498 */       vec.copyInto(this.m_pkList);
/*     */     }
/*     */ 
/* 501 */     if ((dirFieldIndex == -1) || (vecDir == null) || (vecDir.size() == 0))
/*     */       return;
/* 503 */     this.m_dirList = new String[vecDir.size()];
/* 504 */     vecDir.copyInto(this.m_dirList);
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 510 */     if (e.getSource() == getBtnExport())
/* 511 */       doSaveByRule();
/* 512 */     else if (e.getSource() == getBtnCancel())
/* 513 */       closeCancel();
/* 514 */     else if (e.getSource() == getBtnSelectAll())
/* 515 */       doSelectAll();
/* 516 */     else if (e.getSource() == getBtnDelAll())
/* 517 */       doDelAll();
/*     */   }
/*     */ 
/*     */   public void doDelAll()
/*     */   {
/* 525 */     Object[][] data = ((NCTableModel)getTablePnl().getTable().getModel()).getDataArray();
/* 526 */     for (int i = 0; i < data.length; ++i)
/*     */     {
/* 528 */       ((NCTableModel)getTablePnl().getTable().getModel()).setValueAt(new Boolean(false), i, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doSelectAll()
/*     */   {
/* 537 */     Object[][] data = ((NCTableModel)getTablePnl().getTable().getModel()).getDataArray();
/* 538 */     for (int i = 0; i < data.length; ++i)
/*     */     {
/* 540 */       ((NCTableModel)getTablePnl().getTable().getModel()).setValueAt(new Boolean(true), i, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doSaveByRule()
/*     */   {
/* 550 */     String[] pkName = getTableVO().getTablePrimaryKey();
/*     */ 
/* 553 */     String filePath = null;
/* 554 */     filePath = CommonUtilities.spaceToNull(getCtlvo().getOutDirectory());
/* 555 */     if ((filePath != null) && (!(filePath.endsWith("\\")))) {
/* 556 */       filePath = filePath + "\\";
/*     */     }
/*     */ 
/* 559 */     String dbDelimiter = null;
/* 560 */     if (getCtlvo().getDbType() == 2)
/* 561 */       dbDelimiter = " ;";
/* 562 */     if (getCtlvo().getDbType() == 1) {
/* 563 */       dbDelimiter = "\r\ngo";
/*     */     }
/*     */ 
/* 566 */     getSelectedPkListAndDirList();
/* 567 */     if (getPkList() == null) {
/* 568 */       return;
/*     */     }
/*     */ 
/* 571 */     boolean iDirFlag = getItemvo().getGrpField() != null;
/*     */ 
/* 574 */     SynchronizedMsg run = null;
/*     */     try
/*     */     {
/* 577 */       run = new SynchronizedMsg();
/* 578 */       run.setUilabel(getLbChoose());
/* 579 */       run.setMessage("正在生成脚本文件");
/* 580 */       run.start();
/* 581 */       XMLTableStruDataBO_Client.geneInsertScriptByRule(getTableVO(), pkName[0], getPkList(), filePath, dbDelimiter, getDirList(), getWhereCond(), iDirFlag);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 585 */       reportException(e);
/*     */     }
/*     */     finally
/*     */     {
/* 589 */       if (run != null)
/*     */       {
/* 591 */         run.setKeepRunning(false);
/*     */       }
/* 593 */       getLbChoose().setForeground(Color.black);
/* 594 */       getLbChoose().setText("数据库脚本导出完成, 按[取消]退出");
/*     */     }
/*     */   }
/*     */ }

 