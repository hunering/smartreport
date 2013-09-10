/*      */ package nc.ui.bd.ref;
/*      */ 
/*      */ import java.io.PrintStream;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.event.EventListenerList;
/*      */ import nc.bs.framework.common.InvocationInfoProxy;
/*      */ import nc.bs.framework.common.RuntimeEnv;
/*      */ import nc.bs.logging.Logger;
/*      */ import nc.jdbc.framework.misc.LRUMap;
/*      */ import nc.ui.bd.BDGLOrgBookAccessor;
/*      */ import nc.ui.bd.mmpub.DataDictionaryReader;
/*      */ import nc.ui.pub.formulaparse.FormulaParse;
/*      */ import nc.ui.uap.bd.def.DefFieldInfo;
/*      */ import nc.ui.uap.bd.def.UFRefDefTanslateUtil;
/*      */ import nc.vo.bd.access.BdinfoVO;
/*      */ import nc.vo.bd.ref.DocSealWherePartMng;
/*      */ import nc.vo.bd.ref.RefColumnDispConvertVO;
/*      */ import nc.vo.bd.ref.RefQueryResultVO;
/*      */ import nc.vo.bd.ref.RefQueryVO;
/*      */ import nc.vo.bd.ref.RefVO_mlang;
/*      */ import nc.vo.bd.ref.RefcolumnVO;
/*      */ import nc.vo.bd.ref.ReftableVO;
/*      */ import nc.vo.bd.refdatatemp.RefdatatempVO;
/*      */ import nc.vo.jcom.lang.StringUtil;
/*      */ import nc.vo.logging.Debug;
/*      */ import nc.vo.ml.AbstractNCLangRes;
/*      */ import nc.vo.ml.NCLangRes4VoTransl;
/*      */ import nc.vo.pub.ValueObject;
/*      */ import nc.vo.pub.formulaset.FormulaParseFather;
/*      */ import nc.vo.pub.lang.UFBoolean;
/*      */ 
/*      */ public abstract class AbstractRefModel
/*      */   implements IRefModel
/*      */ {
/*   53 */   private String para1 = null;
/*      */ 
/*   55 */   private String para2 = null;
/*      */ 
/*   57 */   private String para3 = null;
/*      */   protected IRefModelHandler modelHandler;
/*   61 */   private ArrayList hiddenFieldList = new ArrayList();
/*      */ 
/*   64 */   private boolean isCaseSensitve = false;
/*      */ 
/*   67 */   private Vector vecAllColumnNames = null;
/*      */ 
/*   69 */   private RefQueryVO queryVO = new RefQueryVO();
/*      */ 
/*  100 */   private String[] m_strFieldCode = null;
/*      */ 
/*  102 */   private String[] m_strFieldName = null;
/*      */ 
/*  105 */   private int[] m_intFieldType = null;
/*      */ 
/*  107 */   private String m_strPkFieldCode = null;
/*      */ 
/*  109 */   private String m_strTableName = null;
/*      */ 
/*  111 */   private String m_strBlurValue = null;
/*      */ 
/*  113 */   private String m_strRefTitle = null;
/*      */ 
/*  115 */   protected Vector m_vecData = null;
/*      */ 
/*  117 */   private int m_iDefaultFieldCount = 2;
/*      */ 
/*  119 */   private String m_strOrderPart = null;
/*      */ 
/*  121 */   protected String m_strPk_corp = null;
/*      */ 
/*  123 */   protected String m_strRefCodeField = null;
/*      */ 
/*  125 */   protected String m_strRefNameField = null;
/*      */ 
/*  127 */   protected Vector m_vecSelectedData = null;
/*      */ 
/*  129 */   private String m_strStrPatch = "";
/*      */ 
/*  131 */   private String m_strWherePart = null;
/*      */ 
/*  133 */   private boolean m_bCacheEnabled = true;
/*      */ 
/*  135 */   private Hashtable m_htCodeIndex = null;
/*      */ 
/*  137 */   private String m_orgTypeCode = "1";
/*      */ 
/*  140 */   private String m_fun_code = null;
/*      */ 
/*  143 */   private String m_fun_code_DocOpen = null;
/*      */ 
/*  146 */   private String m_pk_glOrgBook = null;
/*      */ 
/*  149 */   private boolean isFromTempTable = false;
/*      */ 
/*  152 */   private boolean isIncludeBlurPart = true;
/*      */ 
/*  155 */   private boolean isIncludeSub = false;
/*      */ 
/*  157 */   private boolean isPKMatch = false;
/*      */ 
/*  160 */   private AbstractRefModel m_defGridmodel = null;
/*      */ 
/*  164 */   private boolean m_isRefreshMatch = true;
/*      */ 
/*  168 */   private boolean m_isSealedDataShow = false;
/*      */ 
/*  171 */   private String m_matchField = null;
/*      */ 
/*  174 */   private HashMap m_matchHM = new HashMap();
/*      */ 
/*  178 */   private String[] m_mnecodes = null;
/*      */ 
/*  181 */   private String querySql = null;
/*      */ 
/*  184 */   private String refFilterDlgClaseName = null;
/*      */ 
/*  186 */   private String refQueryDlgClaseName = null;
/*      */ 
/*  189 */   private Object userPara = null;
/*      */   protected static final String LARGE_NODE_NAME = "客商基本档案:客商档案:客户档案:供应商档案:客商辅助核算:客户辅助核算:供应商辅助核算:客商档案包含冻结:客户档案包含冻结:供应商档案包含冻结:存货基本档案:存货档案:存货辅助核算:物料档案";
/*  194 */   private boolean isMatchPkWithWherePart = false;
/*      */ 
/*  197 */   private boolean m_isRefEnable = true;
/*      */ 
/*  201 */   private String[][] formulas = (String[][])null;
/*      */ 
/*  204 */   boolean isLocQueryEnable = true;
/*      */ 
/*  207 */   private boolean m_bNotLeafSelectedEnabled = true;
/*      */ 
/*  210 */   private boolean m_bUserDataPower = true;
/*      */ 
/*  213 */   private String m_dynamicColClassName = null;
/*      */ 
/*  218 */   private int m_filterStrategy = 0;
/*      */ 
/*  220 */   private HashMap m_hmFilterPks = new HashMap();
/*      */ 
/*  222 */   private String[] m_filterPks = null;
/*      */ 
/*  225 */   private Hashtable m_htDispConvertor = null;
/*      */ 
/*  228 */   private boolean m_isDynamicCol = false;
/*      */ 
/*  230 */   private int[] m_iShownColumns = null;
/*      */ 
/*  233 */   private boolean m_mnecodeInput = false;
/*      */ 
/*  235 */   private String[] m_strBlurFields = null;
/*      */ 
/*  237 */   private String m_strDataSource = null;
/*      */ 
/*  240 */   private String[] m_strDynamicFieldNames = null;
/*      */ 
/*  242 */   private String m_strGroupPart = null;
/*      */ 
/*  244 */   private String[] m_strHiddenFieldCode = null;
/*      */   private String m_strOriginWherePart;
/*  249 */   protected String m_strRefNodeName = null;
/*      */ 
/*  252 */   private Object m_userParameter = null;
/*      */ 
/*  257 */   private String refTempDataWherePart = null;
/*      */ 
/*  260 */   private boolean isOnlyMneFields = false;
/*      */ 
/*  263 */   private String m_addWherePart = null;
/*      */ 
/*  266 */   private Object[][] m_columnDispConverter = (Object[][])null;
/*      */ 
/*  269 */   private String m_pk_user = null;
/*      */ 
/*  271 */   FormulaHandler formulaHandler = null;
/*      */ 
/*  273 */   DefFieldsHandler defFieldHandler = null;
/*      */ 
/*  275 */   private EventListenerList eListenerList = new EventListenerList();
/*      */ 
/*  277 */   private Map eventListMap = new HashMap();
/*      */ 
/*  279 */   private transient ChangeEvent changeEvent = null;
/*      */ 
/*  282 */   private boolean isGroupAssignData = false;
/*      */ 
/*  285 */   private String refDataCachekeyPreStr = "REFDATACACHE";
/*      */ 
/*  287 */   private DDReaderVO ddReaderVO = null;
/*      */ 
/*  290 */   private int resourceID = -1;
/*      */ 
/*  292 */   private String sealedWherePart = null;
/*      */ 
/*  295 */   private String envWherePart = null;
/*      */ 
/*  297 */   private BdinfoVO bdinfoVO = null;
/*      */ 
/*  300 */   private boolean isInitBDinfoVO = false;
/*      */ 
/*  303 */   private boolean isChangeTableSeq = true;
/*      */ 
/*  306 */   private boolean isMaintainButtonEnabled = true;
/*      */ 
/*  309 */   private String dataPowerResourceColumn = null;
/*      */ 
/*  312 */   private String refCardInfoComponentImplClass = null;
/*      */ 
/*  315 */   private String refShowNameField = null;
/*      */ 
/*  318 */   private String[] defFields = null;
/*      */ 
/*  321 */   private Map<String, Integer> alignMap = null;
/*      */ 
/*  324 */   private String uiControlComponentClassName = null;
/*      */ 
/*  327 */   private String refDocEditClassName = null;
/*      */ 
/*      */   public AbstractRefModel()
/*      */   {
/*      */     try
/*      */     {
/*      */       Class c;
/*   79 */       if (RuntimeEnv.getInstance().isRunningInServer()) {
/*   80 */         c = Class.forName("nc.ui.bd.ref.impl.RefModelHandlerForServer");
/*      */       }
/*      */       else
/*      */       {
/*   89 */         c = Class.forName("nc.ui.bd.ref.RefModelHandlerForClient");
/*      */       }
/*      */ 
/*   92 */       Constructor cs = c.getConstructor(new Class[] { AbstractRefModel.class });
/*      */ 
/*   94 */       this.modelHandler = ((IRefModelHandler)cs.newInstance(new Object[] { this }));
/*      */     } catch (Exception e) {
/*   96 */       Logger.error(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object[][] getColumnDispConverter()
/*      */   {
/*  369 */     return this.m_columnDispConverter;
/*      */   }
/*      */ 
/*      */   public void setColumnDispConverter(Object[][] dispConverter)
/*      */   {
/*  377 */     this.m_columnDispConverter = dispConverter;
/*      */   }
/*      */ 
/*      */   public void addWherePart(String newWherePart)
/*      */   {
/*  399 */     this.m_addWherePart = newWherePart;
/*      */ 
/*  402 */     resetSelectedData_WhenDataChanged();
/*      */   }
/*      */ 
/*      */   public void setMatchField(String newM_matchField)
/*      */   {
/*  412 */     this.m_matchField = newM_matchField;
/*      */   }
/*      */ 
/*      */   public void clearData()
/*      */   {
/*  422 */     clearModelData();
/*  423 */     clearCacheData();
/*      */   }
/*      */ 
/*      */   public void clearCacheData()
/*      */   {
/*  431 */     this.modelHandler.clearCacheData();
/*      */   }
/*      */ 
/*      */   protected void clearModelData() {
/*  435 */     this.m_vecData = null;
/*  436 */     setSelectedData(null);
/*      */   }
/*      */ 
/*      */   protected void clearDataPowerCache()
/*      */   {
/*  441 */     this.modelHandler.clearDataPowerCache();
/*      */   }
/*      */ 
/*      */   public ValueObject convertToVO(Vector vData)
/*      */   {
/*  452 */     return null;
/*      */   }
/*      */ 
/*      */   public ValueObject[] convertToVOs(Vector vData)
/*      */   {
/*  463 */     if ((vData == null) || (vData.size() == 0))
/*  464 */       return null;
/*  465 */     ValueObject[] vos = new ValueObject[vData.size()];
/*  466 */     for (int i = 0; i < vData.size(); ++i) {
/*  467 */       Vector vRecord = (Vector)vData.elementAt(i);
/*  468 */       vos[i] = convertToVO(vRecord);
/*      */     }
/*  470 */     return vos;
/*      */   }
/*      */ 
/*      */   public String getBlurValue()
/*      */   {
/*  479 */     return this.m_strBlurValue;
/*      */   }
/*      */ 
/*      */   public final Vector getRefData()
/*      */   {
/*  496 */     return this.modelHandler.getRefData();
/*      */   }
/*      */ 
/*      */   public Vector getData()
/*      */   {
/*  507 */     String sql = getRefSql();
/*      */ 
/*  509 */     Vector v = null;
/*  510 */     if (isCacheEnabled())
/*      */     {
/*  512 */       v = this.modelHandler.getFromCache(getRefDataCacheKey(), getRefCacheSqlKey());
/*      */     }
/*      */ 
/*  516 */     if (v == null)
/*      */     {
/*      */       try
/*      */       {
/*  522 */         if (isFromTempTable()) {
/*  523 */           v = this.modelHandler.queryRefDataFromTemplateTable(sql);
/*      */         }
/*      */         else
/*  526 */           v = getQueryResultVO();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  530 */         Debug.debug(e.getMessage(), e);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  535 */     putToCache(v);
/*      */ 
/*  537 */     this.m_vecData = v;
/*  538 */     return this.m_vecData;
/*      */   }
/*      */ 
/*      */   public void putToCache(Vector v) {
/*  542 */     if ((v == null) || (!(isCacheEnabled())))
/*      */       return;
/*  544 */     this.modelHandler.putToCache(getRefDataCacheKey(), getRefCacheSqlKey(), v);
/*      */   }
/*      */ 
/*      */   public Vector getConvertedData1(boolean isDataFromCache, Vector v, boolean isDefConverted)
/*      */   {
/*  551 */     return getConvertedData(isDataFromCache, v, isDefConverted);
/*      */   }
/*      */ 
/*      */   protected Vector getConvertedData(boolean isDataFromCache, Vector v, boolean isDefConverted)
/*      */   {
/*  568 */     if (v == null) {
/*  569 */       return v;
/*      */     }
/*  571 */     Vector vecData = v;
/*      */ 
/*  574 */     if ((vecData != null) && (vecData.size() > 0)) {
/*  575 */       vecData = getFilterPKsVector(vecData, getFilterStrategy());
/*      */     }
/*      */ 
/*  580 */     if (!(isDataFromCache)) {
/*  581 */       setMlangValues(vecData, getRefVO_mlang());
/*  582 */       if (getFormulas() != null) {
/*  583 */         getFormulaHandler().setFormulaValues(vecData, getFormulas());
/*      */       }
/*      */ 
/*  586 */       if (isDefConverted) {
/*  587 */         getDefFieldHandler().setDefFieldValues(vecData);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  593 */     handleDispConvertor(vecData);
/*      */ 
/*  596 */     if (getColumnDispConverter() != null) {
/*  597 */       setColDispValues(vecData, getColumnDispConverter());
/*      */     }
/*  599 */     return vecData;
/*      */   }
/*      */ 
/*      */   private void handleDispConvertor(Vector v)
/*      */   {
/*  616 */     if (getDispConvertor() != null) {
/*  617 */       Hashtable convert = getDispConvertor();
/*  618 */       Enumeration e = convert.keys();
/*  619 */       while (e.hasMoreElements()) {
/*  620 */         String convertColumn = (String)e.nextElement();
/*  621 */         Hashtable conv = (Hashtable)convert.get(convertColumn);
/*  622 */         Integer Idx = (Integer)getHtCodeIndex().get(convertColumn);
/*  623 */         if (Idx == null) continue; if (Idx.intValue() < 0)
/*      */           continue;
/*  625 */         int idx = Idx.intValue();
/*  626 */         if (v != null) {
/*  627 */           int rows = v.size();
/*  628 */           for (int i = 0; i < rows; ++i) {
/*  629 */             Vector vRow = (Vector)v.elementAt(i);
/*  630 */             if (vRow != null) {
/*  631 */               Object oldValue = vRow.elementAt(idx);
/*  632 */               if (oldValue == null)
/*      */                 continue;
/*  634 */               Object newValue = conv.get(oldValue.toString());
/*  635 */               if (newValue != null)
/*  636 */                 vRow.setElementAt(newValue, idx);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getDefaultFieldCount()
/*      */   {
/*  654 */     return this.m_iDefaultFieldCount;
/*      */   }
/*      */ 
/*      */   public int getFieldIndex(String field)
/*      */   {
/*  668 */     if ((field == null) || (field.trim().length() == 0) || (getHtCodeIndex() == null) || (getHtCodeIndex().size() == 0))
/*      */     {
/*  670 */       return -1; }
/*  671 */     Object o = getHtCodeIndex().get(field.trim());
/*  672 */     if (o == null)
/*      */     {
/*  674 */       int index = this.m_htCodeIndex.size();
/*  675 */       if ((isDynamicCol()) && (getDynamicFieldNames() != null)) {
/*  676 */         for (int i = 0; i < getDynamicFieldNames().length; ++i) {
/*  677 */           this.m_htCodeIndex.put(getDynamicFieldNames()[i].trim(), new Integer(index + i));
/*      */         }
/*      */       }
/*      */ 
/*  681 */       o = getHtCodeIndex().get(field.trim());
/*      */     }
/*      */ 
/*  684 */     return ((o == null) ? -1 : ((Integer)o).intValue());
/*      */   }
/*      */ 
/*      */   public Hashtable getHtCodeIndex()
/*      */   {
/*  693 */     if ((this.m_htCodeIndex == null) || (this.m_htCodeIndex.size() == 0)) {
/*  694 */       this.m_htCodeIndex = new Hashtable();
/*  695 */       if (getFieldCode() != null) {
/*  696 */         for (int i = 0; i < getFieldCode().length; ++i) {
/*  697 */           this.m_htCodeIndex.put(getFieldCode()[i].trim(), new Integer(i));
/*      */         }
/*      */       }
/*  700 */       if (getHiddenFieldCode() != null) {
/*  701 */         int index = 0;
/*  702 */         if (getFieldCode() != null) {
/*  703 */           index = getFieldCode().length;
/*      */         }
/*  705 */         for (int i = 0; i < getHiddenFieldCode().length; ++i) {
/*  706 */           if (getHiddenFieldCode()[i] != null) {
/*  707 */             this.m_htCodeIndex.put(getHiddenFieldCode()[i].trim(), new Integer(index + i));
/*      */           }
/*      */           else {
/*  710 */             System.out.println("Waring: The RefModel has some errors.");
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  717 */     return this.m_htCodeIndex;
/*      */   }
/*      */ 
/*      */   public String getOrderPart()
/*      */   {
/*  726 */     if ((this.m_strOrderPart == null) && (getFieldCode() != null) && (getFieldCode().length > 0))
/*      */     {
/*  728 */       this.m_strOrderPart = getFieldCode()[0]; }
/*  729 */     return this.m_strOrderPart;
/*      */   }
/*      */ 
/*      */   public String getPk_corp()
/*      */   {
/*  738 */     if (this.m_strPk_corp == null) {
/*  739 */       this.m_strPk_corp = this.modelHandler.getPk_corp();
/*      */     }
/*  741 */     return this.m_strPk_corp;
/*      */   }
/*      */ 
/*      */   public String getPkValue()
/*      */   {
/*  750 */     String strValue = null;
/*  751 */     Object objValue = getValue(getPkFieldCode());
/*  752 */     if (objValue != null) {
/*  753 */       strValue = objValue.toString();
/*      */     }
/*      */ 
/*  756 */     return strValue;
/*      */   }
/*      */ 
/*      */   public String[] getPkValues()
/*      */   {
/*  766 */     Object[] oDatas = getValues(getPkFieldCode());
/*  767 */     String[] sDatas = objs2Strs(oDatas);
/*  768 */     return sDatas;
/*      */   }
/*      */ 
/*      */   public String getRefCodeField()
/*      */   {
/*  777 */     if ((this.m_strRefCodeField == null) && (getFieldCode() != null) && (getFieldCode().length > 0))
/*      */     {
/*  779 */       this.m_strRefCodeField = getFieldCode()[0]; }
/*  780 */     return this.m_strRefCodeField;
/*      */   }
/*      */ 
/*      */   public String getRefCodeValue()
/*      */   {
/*  790 */     String strValue = null;
/*  791 */     String[] strValues = getRefCodeValues();
/*  792 */     if ((strValues != null) && (strValues.length > 0)) {
/*  793 */       strValue = strValues[0];
/*      */     }
/*  795 */     return strValue;
/*      */   }
/*      */ 
/*      */   public String[] getRefCodeValues()
/*      */   {
/*  804 */     Object[] oDatas = getValues(getRefCodeField());
/*  805 */     String[] sDatas = objs2Strs(oDatas);
/*  806 */     return sDatas;
/*      */   }
/*      */ 
/*      */   protected String[] objs2Strs(Object[] oDatas) {
/*  810 */     if (oDatas == null)
/*  811 */       return null;
/*  812 */     String[] sDatas = new String[oDatas.length];
/*  813 */     for (int i = 0; i < oDatas.length; ++i) {
/*  814 */       if (oDatas[i] == null)
/*  815 */         sDatas[i] = null;
/*      */       else
/*  817 */         sDatas[i] = oDatas[i].toString().trim();
/*      */     }
/*  819 */     return sDatas;
/*      */   }
/*      */ 
/*      */   public String getRefNameField()
/*      */   {
/*  828 */     if ((this.m_strRefNameField == null) && (getFieldCode() != null) && (getFieldCode().length > 1))
/*      */     {
/*  830 */       this.m_strRefNameField = getFieldCode()[1]; }
/*  831 */     return this.m_strRefNameField;
/*      */   }
/*      */ 
/*      */   public String getRefNameValue()
/*      */   {
/*  841 */     String strValue = null;
/*  842 */     String[] strValues = getRefNameValues();
/*  843 */     if ((strValues != null) && (strValues.length > 0)) {
/*  844 */       strValue = strValues[0];
/*      */     }
/*  846 */     return strValue;
/*      */   }
/*      */ 
/*      */   public String[] getRefNameValues()
/*      */   {
/*  856 */     Object[] oDatas = getValues(getRefNameField(), getSelectedData(), false);
/*      */ 
/*  858 */     String[] sDatas = objs2Strs(oDatas);
/*      */ 
/*  860 */     return sDatas;
/*      */   }
/*      */ 
/*      */   public String[] getRefShowNameValues()
/*      */   {
/*  865 */     Object[] oDatas = getValues(getRefShowNameField(), getSelectedData(), false);
/*      */ 
/*  868 */     return objs2Strs(oDatas);
/*      */   }
/*      */ 
/*      */   public String getRefSql()
/*      */   {
/*  878 */     return getSql(getStrPatch(), getFieldCode(), getHiddenFieldCode(), getTableName(), getWherePart(), getGroupPart(), getOrderPart());
/*      */   }
/*      */ 
/*      */   public Vector getSelectedData()
/*      */   {
/*  889 */     return this.m_vecSelectedData;
/*      */   }
/*      */ 
/*      */   public String getStrPatch()
/*      */   {
/*  898 */     return this.m_strStrPatch;
/*      */   }
/*      */ 
/*      */   public Object getValue(String field)
/*      */   {
/*  907 */     Object[] values = getValues(field);
/*      */ 
/*  909 */     Object value = null;
/*      */ 
/*  911 */     if ((values != null) && (values.length > 0)) {
/*  912 */       value = values[0];
/*      */     }
/*      */ 
/*  915 */     return value;
/*      */   }
/*      */ 
/*      */   public Object[] getValues(String field)
/*      */   {
/*  925 */     return getValues(field, true);
/*      */   }
/*      */ 
/*      */   public Object[] getValues(String field, boolean isOriginValue)
/*      */   {
/*  947 */     return getValues(field, getSelectedData(), isOriginValue);
/*      */   }
/*      */ 
/*      */   public Object[] getValues(String field, Vector datas)
/*      */   {
/*  955 */     return getValues(field, datas, true);
/*      */   }
/*      */ 
/*      */   protected Object[] getValues(String field, Vector datas, boolean isOriginValue)
/*      */   {
/*  960 */     int col = getFieldIndex(field);
/*  961 */     if ((datas == null) || (datas.size() == 0))
/*  962 */       return null;
/*  963 */     if (col < 0) {
/*  964 */       throw new RuntimeException("参照中没有该属性");
/*      */     }
/*  966 */     Vector vRecord = null;
/*  967 */     Object[] oData = new Object[datas.size()];
/*  968 */     for (int i = 0; i < datas.size(); ++i) {
/*  969 */       vRecord = (Vector)datas.elementAt(i);
/*  970 */       if ((vRecord == null) || (vRecord.size() == 0) || (col >= vRecord.size()))
/*  971 */         oData[i] = null;
/*      */       else {
/*  973 */         oData[i] = getValueOfRefvalueVO(vRecord.elementAt(col), isOriginValue);
/*      */       }
/*      */     }
/*      */ 
/*  977 */     return oData;
/*      */   }
/*      */ 
/*      */   private Object getValueOfRefvalueVO(Object obj, boolean isOriginValue) {
/*  981 */     Object value = obj;
/*  982 */     if ((obj instanceof RefValueVO) && (isOriginValue)) {
/*  983 */       value = ((RefValueVO)obj).getOriginValue();
/*      */     }
/*      */ 
/*  990 */     value = RefPubUtil.bigDecimal2UFDouble(value);
/*      */ 
/*  992 */     return value;
/*      */   }
/*      */ 
/*      */   public Vector getVecData()
/*      */   {
/* 1002 */     return this.m_vecData;
/*      */   }
/*      */ 
/*      */   public ValueObject getVO()
/*      */   {
/* 1011 */     if ((getSelectedData() != null) && (getSelectedData().size() > 0)) {
/* 1012 */       return convertToVO((Vector)getSelectedData().elementAt(0));
/*      */     }
/* 1014 */     return null;
/*      */   }
/*      */ 
/*      */   public ValueObject[] getVOs()
/*      */   {
/* 1023 */     return convertToVOs(getSelectedData());
/*      */   }
/*      */ 
/*      */   public String getWherePart()
/*      */   {
/* 1030 */     return this.m_strWherePart;
/*      */   }
/*      */ 
/*      */   public boolean isCacheEnabled()
/*      */   {
/* 1039 */     return this.m_bCacheEnabled;
/*      */   }
/*      */ 
/*      */   public Vector matchBlurData(String strBlurValue)
/*      */   {
/* 1048 */     Vector v = null;
/* 1049 */     if ((strBlurValue != null) && (strBlurValue.trim().length() > 0)) {
/* 1050 */       v = matchData(getBlurFields(), new String[] { strBlurValue });
/*      */ 
/* 1054 */       if ((!(isNotLeafSelectedEnabled())) && (((!(isNull(strBlurValue))) || (isMnecodeInput()))))
/*      */       {
/* 1057 */         v = filterNotLeafNode(v);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1062 */     setSelectedData(v);
/* 1063 */     return v;
/*      */   }
/*      */ 
/*      */   private boolean isNull(String str)
/*      */   {
/* 1068 */     return ((str == null) || (str.trim().length() == 0));
/*      */   }
/*      */ 
/*      */   public Vector matchData(String field, String value)
/*      */   {
/* 1079 */     return matchData(new String[] { field }, new String[] { value });
/*      */   }
/*      */ 
/*      */   public Vector matchPkData(String strPkValue)
/*      */   {
/* 1090 */     if ((strPkValue == null) || (strPkValue.trim().length() == 0)) {
/* 1091 */       setSelectedData(null);
/* 1092 */       return null;
/*      */     }
/*      */ 
/* 1095 */     return matchPkData(new String[] { strPkValue });
/*      */   }
/*      */ 
/*      */   public Vector reloadData()
/*      */   {
/* 1105 */     reloadData1();
/*      */ 
/* 1107 */     return getRefData();
/*      */   }
/*      */ 
/*      */   public void reloadData1()
/*      */   {
/* 1113 */     String[] selectedPKs = getPkValues();
/*      */ 
/* 1115 */     clearData();
/*      */ 
/* 1117 */     clearDataPowerCache();
/*      */ 
/* 1119 */     this.modelHandler.getLRUMap().clear();
/*      */ 
/* 1122 */     if ((selectedPKs != null) && (selectedPKs.length > 0)) {
/* 1123 */       matchPkData(selectedPKs);
/*      */     }
/* 1125 */     this.modelHandler.fireDBCache();
/*      */   }
/*      */ 
/*      */   public void setBlurValue(String strBlurValue)
/*      */   {
/* 1132 */     this.m_strBlurValue = strBlurValue;
/*      */   }
/*      */ 
/*      */   public void setCacheEnabled(boolean cacheEnabled)
/*      */   {
/* 1139 */     this.m_bCacheEnabled = cacheEnabled;
/*      */   }
/*      */ 
/*      */   public void setData(Vector vData)
/*      */   {
/* 1149 */     this.m_vecData = vData;
/*      */ 
/* 1151 */     if (isCacheEnabled()) {
/* 1152 */       String sql = getRefSql();
/* 1153 */       this.modelHandler.putToCache(getRefDataCacheKey(), sql, vData);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setDefaultFieldCount(int iDefaultFieldCount)
/*      */   {
/* 1161 */     this.m_iDefaultFieldCount = iDefaultFieldCount;
/*      */   }
/*      */ 
/*      */   public void setOrderPart(String strOrderPart)
/*      */   {
/* 1168 */     this.m_strOrderPart = strOrderPart;
/*      */   }
/*      */ 
/*      */   public void setPk_corp(String strPk_corp)
/*      */   {
/* 1177 */     if (strPk_corp == null) {
/* 1178 */       return;
/*      */     }
/*      */ 
/* 1181 */     this.m_pk_glOrgBook = null;
/*      */ 
/* 1183 */     setPk_corpForDefRef(strPk_corp, this.m_strPk_corp);
/* 1184 */     this.m_strPk_corp = strPk_corp;
/*      */ 
/* 1187 */     resetWherePart();
/*      */   }
/*      */ 
/*      */   private void resetWherePart()
/*      */   {
/* 1203 */     setRefNodeName(getRefNodeName());
/*      */ 
/* 1205 */     String wherePart = getWherePart();
/*      */ 
/* 1207 */     if ((getAddWherePart() == null) || (wherePart == null) || (wherePart.indexOf(getAddWherePart()) >= 0))
/*      */       return;
/* 1209 */     addWherePart(getAddWherePart());
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void setPk_corpAndRefreshRefNodeName(String strPk_corp)
/*      */   {
/* 1219 */     setPk_corp(strPk_corp);
/*      */   }
/*      */ 
/*      */   private void setPk_corpForDefRef(String pk_corp, String pk_corpOld)
/*      */   {
/* 1228 */     if ((pk_corpOld == null) || (pk_corp == null) || (pk_corpOld.equals(pk_corp)))
/*      */       return;
/* 1230 */     String wherePartOld = getWherePart();
/* 1231 */     String wherePart = "";
/* 1232 */     if (wherePartOld != null) {
/* 1233 */       int index = wherePartOld.indexOf("'" + pk_corpOld + "'");
/* 1234 */       if (index > 0) {
/* 1235 */         wherePart = wherePartOld.replaceFirst("'" + pk_corpOld + "'", "'" + pk_corp + "'");
/*      */ 
/* 1237 */         setWherePart(wherePart);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setRefCodeField(String strRefCodeField)
/*      */   {
/* 1247 */     this.m_strRefCodeField = strRefCodeField;
/*      */   }
/*      */ 
/*      */   public void setRefNameField(String strRefNameField)
/*      */   {
/* 1254 */     this.m_strRefNameField = strRefNameField;
/*      */   }
/*      */ 
/*      */   public void setSelectedData(Vector vecSelectedData)
/*      */   {
/* 1264 */     this.m_vecSelectedData = vecSelectedData;
/*      */   }
/*      */ 
/*      */   public void setSelectedDataAndConvertData(Vector vecSelectedData) {
/* 1268 */     if (vecSelectedData == null) {
/* 1269 */       setSelectedData(vecSelectedData);
/* 1270 */       return;
/*      */     }
/* 1272 */     Vector matchVec = getConvertedData(false, vecSelectedData, false);
/* 1273 */     setSelectedData(matchVec);
/* 1274 */     putLruMap(matchVec);
/*      */   }
/*      */ 
/*      */   public void setStrPatch(String newStrPatch)
/*      */   {
/* 1284 */     this.m_strStrPatch = newStrPatch;
/*      */   }
/*      */ 
/*      */   public void setWherePart(String newWherePart)
/*      */   {
/* 1295 */     if (newWherePart != null) {
/* 1296 */       newWherePart = newWherePart.trim();
/* 1297 */       int index = newWherePart.toLowerCase().indexOf("where ");
/* 1298 */       if (index == 0) {
/* 1299 */         newWherePart = newWherePart.substring(index + 5, newWherePart.length());
/*      */       }
/*      */     }
/* 1302 */     this.m_strWherePart = newWherePart;
/*      */ 
/* 1304 */     this.m_strOriginWherePart = this.m_strWherePart;
/*      */ 
/* 1306 */     resetSelectedData_WhenDataChanged();
/*      */   }
/*      */ 
/*      */   private void resetSelectedData_WhenDataChanged()
/*      */   {
/* 1315 */     String[] selectedPKs = getPkValues();
/*      */ 
/* 1317 */     clearModelData();
/*      */ 
/* 1319 */     if ((selectedPKs != null) && (selectedPKs.length > 0)) {
/* 1320 */       matchPkData(selectedPKs);
/*      */     }
/*      */ 
/* 1323 */     Vector selectedData = getSelectedData();
/*      */ 
/* 1325 */     setSelectedData(selectedData);
/*      */ 
/* 1327 */     fireChange();
/*      */   }
/*      */ 
/*      */   protected Vector filterNotLeafNode(Vector vec)
/*      */   {
/* 1334 */     return vec;
/*      */   }
/*      */ 
/*      */   public String[] getBlurFields()
/*      */   {
/* 1344 */     if ((this.m_strBlurFields == null) || (this.m_strBlurFields.length == 0))
/*      */     {
/* 1346 */       if (isOnlyMneFields())
/* 1347 */         return (this.m_strBlurFields = getMnecodes());
/* 1348 */       if (getFieldCode() != null) {
/* 1349 */         ArrayList al = new ArrayList();
/* 1350 */         int len = getFieldCode().length;
/*      */ 
/* 1352 */         al.add(getFieldCode()[0]);
/* 1353 */         if (len >= 2) {
/* 1354 */           al.add(getFieldCode()[1]);
/*      */         }
/*      */ 
/* 1357 */         if (getMnecodes() != null) {
/* 1358 */           for (int i = 0; i < getMnecodes().length; ++i) {
/* 1359 */             if (al.contains(getMnecodes()[i])) {
/*      */               continue;
/*      */             }
/* 1362 */             al.add(getMnecodes()[i]);
/*      */           }
/*      */         }
/* 1365 */         this.m_strBlurFields = new String[al.size()];
/* 1366 */         al.toArray(this.m_strBlurFields);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1372 */     return this.m_strBlurFields;
/*      */   }
/*      */ 
/*      */   public String getDataSource()
/*      */   {
/* 1381 */     return this.m_strDataSource;
/*      */   }
/*      */ 
/*      */   public Hashtable getDispConvertor()
/*      */   {
/* 1391 */     if ((getDdReaderVO() != null) && (this.m_htDispConvertor == null)) {
/* 1392 */       this.m_htDispConvertor = getDDReaderMap(getDdReaderVO().getTableName(), getDdReaderVO().getFieldNames());
/*      */     }
/*      */ 
/* 1395 */     return this.m_htDispConvertor;
/*      */   }
/*      */ 
/*      */   public String getDynamicColClassName()
/*      */   {
/* 1404 */     return this.m_dynamicColClassName;
/*      */   }
/*      */ 
/*      */   public String[] getDynamicFieldNames()
/*      */   {
/* 1413 */     return this.m_strDynamicFieldNames;
/*      */   }
/*      */ 
/*      */   public Map getFieldCNName()
/*      */   {
/* 1424 */     return this.modelHandler.getFieldCNName();
/*      */   }
/*      */ 
/*      */   public Vector getFilterPKsVector(Vector vecData, int filterStrategy)
/*      */   {
/* 1431 */     Vector filterVector = null;
/* 1432 */     int pkIndex = getFieldIndex(getPkFieldCode());
/* 1433 */     if (vecData != null)
/*      */     {
/* 1435 */       if (getFilterPks() == null) {
/* 1436 */         return vecData;
/*      */       }
/* 1438 */       filterVector = new Vector();
/* 1439 */       for (int i = 0; i < vecData.size(); ++i) {
/* 1440 */         Object vecpk = ((Vector)vecData.elementAt(i)).elementAt(pkIndex);
/*      */ 
/* 1443 */         switch (filterStrategy)
/*      */         {
/*      */         case 0:
/* 1446 */           if (this.m_hmFilterPks.size() == 0) {
/* 1447 */             return null;
/*      */           }
/* 1449 */           if (this.m_hmFilterPks.get(vecpk.toString()) != null)
/* 1450 */             filterVector.add(vecData.elementAt(i));
/* 1451 */           break;
/*      */         case 1:
/* 1456 */           if (this.m_hmFilterPks.size() == 0) {
/* 1457 */             return vecData;
/*      */           }
/* 1459 */           if (this.m_hmFilterPks.get(vecpk.toString()) == null) {
/* 1460 */             filterVector.add(vecData.elementAt(i));
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1469 */     return filterVector;
/*      */   }
/*      */ 
/*      */   public String getGroupPart()
/*      */   {
/* 1479 */     return this.m_strGroupPart;
/*      */   }
/*      */ 
/*      */   public String[] getHiddenFieldCode()
/*      */   {
/* 1488 */     return this.m_strHiddenFieldCode;
/*      */   }
/*      */ 
/*      */   public String getOriginWherePart()
/*      */   {
/* 1497 */     return this.m_strOriginWherePart;
/*      */   }
/*      */ 
/*      */   public String getRefNodeName()
/*      */   {
/* 1506 */     return ((this.m_strRefNodeName == null) ? super.getClass().getName() : this.m_strRefNodeName);
/*      */   }
/*      */ 
/*      */   public int[] getShownColumns()
/*      */   {
/* 1516 */     if ((((this.m_iShownColumns == null) || (this.m_iShownColumns.length == 0))) && (getDefaultFieldCount() > 0))
/*      */     {
/* 1518 */       this.m_iShownColumns = new int[getDefaultFieldCount()];
/* 1519 */       for (int i = 0; i < getDefaultFieldCount(); ++i) {
/* 1520 */         this.m_iShownColumns[i] = i;
/*      */       }
/*      */     }
/* 1523 */     return this.m_iShownColumns;
/*      */   }
/*      */ 
/*      */   public Object getUserParameter()
/*      */   {
/* 1542 */     return this.m_userParameter;
/*      */   }
/*      */ 
/*      */   public boolean isDynamicCol()
/*      */   {
/* 1551 */     return this.m_isDynamicCol;
/*      */   }
/*      */ 
/*      */   public boolean isMnecodeInput()
/*      */   {
/* 1560 */     return this.m_mnecodeInput;
/*      */   }
/*      */ 
/*      */   public boolean isNotLeafSelectedEnabled()
/*      */   {
/* 1569 */     return this.m_bNotLeafSelectedEnabled;
/*      */   }
/*      */ 
/*      */   public boolean isUseDataPower()
/*      */   {
/* 1578 */     return this.m_bUserDataPower;
/*      */   }
/*      */ 
/*      */   public void setBlurFields(String[] strBlurFields)
/*      */   {
/* 1585 */     this.m_strBlurFields = strBlurFields;
/*      */   }
/*      */ 
/*      */   public void setDataSource(String newDataSource)
/*      */   {
/* 1595 */     this.m_strDataSource = newDataSource;
/*      */   }
/*      */ 
/*      */   public void setDispConvertor(Hashtable newDispConvertor)
/*      */   {
/* 1605 */     this.m_htDispConvertor = newDispConvertor;
/*      */   }
/*      */ 
/*      */   public void setDynamicColClassName(String newColClassName)
/*      */   {
/* 1615 */     this.m_dynamicColClassName = newColClassName;
/*      */   }
/*      */ 
/*      */   public void setDynamicFieldNames(String[] newDynamicFieldNames)
/*      */   {
/* 1625 */     this.m_strDynamicFieldNames = newDynamicFieldNames;
/*      */   }
/*      */ 
/*      */   public void setFilterPks(String[] newM_filterPks)
/*      */   {
/* 1637 */     setFilterPks(newM_filterPks, 0);
/*      */   }
/*      */ 
/*      */   public void setFilterPks(String[] newM_filterPks, int filterStrategy)
/*      */   {
/* 1651 */     this.m_filterPks = newM_filterPks;
/*      */ 
/* 1653 */     this.m_filterStrategy = filterStrategy;
/* 1654 */     this.m_hmFilterPks.clear();
/* 1655 */     if ((this.m_filterPks == null) || (this.m_filterPks.length <= 0))
/*      */       return;
/* 1657 */     for (int i = 0; i < this.m_filterPks.length; ++i)
/* 1658 */       this.m_hmFilterPks.put(this.m_filterPks[i], this.m_filterPks[i]);
/*      */   }
/*      */ 
/*      */   public void setGroupPart(String newGroupPart)
/*      */   {
/* 1670 */     this.m_strGroupPart = newGroupPart;
/*      */   }
/*      */ 
/*      */   public void setHiddenFieldCode(String[] newHiddenFieldCode)
/*      */   {
/* 1680 */     getHtCodeIndex().clear();
/* 1681 */     this.hiddenFieldList.clear();
/* 1682 */     this.hiddenFieldList.addAll(Arrays.asList(newHiddenFieldCode));
/* 1683 */     this.m_strHiddenFieldCode = newHiddenFieldCode;
/*      */   }
/*      */ 
/*      */   public void setHtCodeIndex(Hashtable ht)
/*      */   {
/* 1692 */     this.m_htCodeIndex = ht;
/*      */   }
/*      */ 
/*      */   public void setIsDynamicCol(boolean newDynamicCol)
/*      */   {
/* 1702 */     this.m_isDynamicCol = newDynamicCol;
/*      */   }
/*      */ 
/*      */   public void setMnecodeInput(boolean newM_mnecodeInput)
/*      */   {
/* 1712 */     this.m_mnecodeInput = newM_mnecodeInput;
/*      */   }
/*      */ 
/*      */   public void setNotLeafSelectedEnabled(boolean newM_bNotLeafSelectedEnabled)
/*      */   {
/* 1722 */     this.m_bNotLeafSelectedEnabled = newM_bNotLeafSelectedEnabled;
/*      */   }
/*      */ 
/*      */   public void setRefNodeName(String newRefNodeName)
/*      */   {
/* 1732 */     this.m_strRefNodeName = newRefNodeName;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void setRefNodeName(String newRefNodeName, String pk_corp)
/*      */   {
/* 1741 */     this.m_strPk_corp = pk_corp;
/* 1742 */     this.m_strRefNodeName = newRefNodeName;
/*      */   }
/*      */ 
/*      */   public void setRefTitle(String newRefTitle)
/*      */   {
/* 1749 */     this.m_strRefTitle = newRefTitle;
/*      */   }
/*      */ 
/*      */   public void setShownColumns(int[] iShownColumns)
/*      */   {
/* 1758 */     this.m_iShownColumns = iShownColumns;
/*      */   }
/*      */ 
/*      */   public void setTableName(String newTableName)
/*      */   {
/* 1766 */     this.m_strTableName = newTableName;
/*      */   }
/*      */ 
/*      */   public void setUseDataPower(boolean useDataPower)
/*      */   {
/* 1773 */     this.m_bUserDataPower = useDataPower;
/*      */   }
/*      */ 
/*      */   public void setUserParameter(Object newParameter)
/*      */   {
/* 1783 */     this.m_userParameter = newParameter;
/*      */   }
/*      */ 
/*      */   public void setValue(int recordIndex, String field, Object value)
/*      */   {
/* 1790 */     Vector vecData = getSelectedData();
/* 1791 */     if ((vecData == null) || (recordIndex >= vecData.size())) {
/* 1792 */       return;
/*      */     }
/* 1794 */     Vector vRecord = (Vector)vecData.get(recordIndex);
/* 1795 */     int col = getFieldIndex(field);
/*      */ 
/* 1798 */     if (isDynamicCol()) {
/* 1799 */       if ((vRecord == null) || (vRecord.size() == 0) || (col < 0)) {
/* 1800 */         return;
/*      */       }
/* 1802 */       if (value != null)
/*      */       {
/* 1804 */         if (col >= vRecord.size())
/* 1805 */           vRecord.add(value);
/*      */         else {
/* 1807 */           vRecord.setElementAt(value, col);
/*      */         }
/* 1809 */         return;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1814 */     if ((vRecord == null) || (vRecord.size() == 0) || (col < 0) || (col >= vRecord.size()))
/*      */     {
/* 1816 */       return;
/*      */     }
/* 1818 */     if (value != null)
/* 1819 */       vRecord.setElementAt(value, col);
/*      */   }
/*      */ 
/*      */   public void addSealedWherePart(String refNodeName, String sealedWherePart)
/*      */   {
/* 1830 */     DocSealWherePartMng.addSealedWherePart(refNodeName, sealedWherePart);
/*      */   }
/*      */ 
/*      */   public void addWherePart(String newWherePart, boolean isRrefreshData)
/*      */   {
/* 1837 */     addWherePart(newWherePart);
/* 1838 */     if (isRrefreshData)
/* 1839 */       clearModelData();
/*      */   }
/*      */ 
/*      */   public String getFilterDlgClaseName()
/*      */   {
/* 1850 */     return this.refFilterDlgClaseName;
/*      */   }
/*      */ 
/*      */   public String getMatchField()
/*      */   {
/* 1859 */     if (this.m_matchField == null) {
/* 1860 */       return getPkFieldCode();
/*      */     }
/* 1862 */     return this.m_matchField;
/*      */   }
/*      */ 
/*      */   private HashMap getMatchHM()
/*      */   {
/* 1871 */     return this.m_matchHM;
/*      */   }
/*      */ 
/*      */   public String[] getMnecodes()
/*      */   {
/* 1880 */     return this.m_mnecodes;
/*      */   }
/*      */ 
/*      */   public Object getPara()
/*      */   {
/* 1889 */     return this.userPara;
/*      */   }
/*      */ 
/*      */   public String getQuerySql()
/*      */   {
/* 1898 */     return this.querySql;
/*      */   }
/*      */ 
/*      */   public String getRefQueryDlgClaseName()
/*      */   {
/* 1907 */     return this.refQueryDlgClaseName;
/*      */   }
/*      */ 
/*      */   public String getTempDataWherePart()
/*      */   {
/* 1916 */     return this.refTempDataWherePart;
/*      */   }
/*      */ 
/*      */   public boolean isFromTempTable()
/*      */   {
/* 1925 */     return this.isFromTempTable;
/*      */   }
/*      */ 
/*      */   public boolean isIncludeSub()
/*      */   {
/* 1934 */     return this.isIncludeSub;
/*      */   }
/*      */ 
/*      */   public boolean isRefreshMatch()
/*      */   {
/* 1943 */     return this.m_isRefreshMatch;
/*      */   }
/*      */ 
/*      */   public boolean isSealedDataShow()
/*      */   {
/* 1952 */     return this.m_isSealedDataShow;
/*      */   }
/*      */ 
/*      */   public Vector matchData(String field, String[] values)
/*      */   {
/* 1963 */     return matchData(new String[] { field }, values);
/*      */   }
/*      */ 
/*      */   public Vector matchPkData(String[] strPkValues)
/*      */   {
/* 1974 */     if (strPkValues == null) {
/* 1975 */       return null;
/*      */     }
/*      */ 
/* 1978 */     Vector v = null;
/* 1979 */     String matchsql = null;
/*      */ 
/* 1981 */     matchsql = getMatchSql(strPkValues);
/*      */ 
/* 1983 */     Vector matchVec = getMatchVectorFromLRU(strPkValues);
/*      */ 
/* 1985 */     if (matchVec != null)
/*      */     {
/* 1987 */       setSelectedData(matchVec);
/*      */ 
/* 1989 */       return matchVec;
/*      */     }
/*      */     try
/*      */     {
/* 1993 */       if (isNonSqlMatch(matchsql)) {
/* 1994 */         v = matchData(getMatchField(), strPkValues);
/*      */       }
/* 1998 */       else if (isFromTempTable())
/* 1999 */         v = this.modelHandler.queryRefDataFromTemplateTable(matchsql);
/*      */       else {
/* 2001 */         v = this.modelHandler.matchPK(getDataSource(), matchsql);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2006 */       Debug.error(e.getMessage(), e);
/*      */     }
/*      */ 
/* 2011 */     setSelectedDataAndConvertData(v);
/*      */ 
/* 2013 */     return getSelectedData();
/*      */   }
/*      */ 
/*      */   public Vector getMatchVectorFromLRU(String[] strPkValues) {
/* 2017 */     if (isCacheEnabled()) {
/* 2018 */       LRUMap lruMap = this.modelHandler.getLRUMap();
/*      */ 
/* 2020 */       String lruKey = getLRUKey(strPkValues);
/*      */ 
/* 2022 */       Vector matchVec = (Vector)lruMap.get(lruKey);
/* 2023 */       return matchVec;
/*      */     }
/* 2025 */     return null;
/*      */   }
/*      */ 
/*      */   private void putLruMap(Vector vecData) {
/* 2029 */     if (isCacheEnabled()) {
/* 2030 */       String[] pkValues = getPkValues();
/* 2031 */       if (pkValues == null) {
/* 2032 */         return;
/*      */       }
/* 2034 */       LRUMap lruMap = this.modelHandler.getLRUMap();
/* 2035 */       String lruKey = getLRUKey(pkValues);
/* 2036 */       lruMap.put(lruKey, vecData);
/*      */     }
/*      */   }
/*      */ 
/*      */   private String getLRUKey(String[] strPkValues) {
/* 2041 */     StringBuffer sb = new StringBuffer();
/* 2042 */     String dsName = getDataSource();
/* 2043 */     if (dsName == null) {
/* 2044 */       dsName = InvocationInfoProxy.getInstance().getUserDataSource();
/*      */     }
/* 2046 */     sb.append(dsName).append(getRefNodeName()).append(getMatchSql(strPkValues));
/*      */ 
/* 2049 */     if ((strPkValues != null) && (strPkValues.length > 0)) {
/* 2050 */       for (int i = 0; i < strPkValues.length; ++i) {
/* 2051 */         sb.append(strPkValues[i]);
/*      */       }
/*      */     }
/*      */ 
/* 2055 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public boolean isNonSqlMatch(String matchsql)
/*      */   {
/* 2062 */     return this.modelHandler.isNonSqlMatch(matchsql);
/*      */   }
/*      */ 
/*      */   public String getMatchSql(String[] strPkValues) {
/* 2066 */     setPKMatch(true);
/* 2067 */     boolean originSealedDataShow = isSealedDataShow();
/* 2068 */     boolean originUseDataPower = isUseDataPower();
/*      */ 
/* 2070 */     if (!(isRefEnable()))
/*      */     {
/* 2072 */       setUseDataPower(false);
/* 2073 */       setSealedDataShow(true);
/*      */     }
/*      */ 
/* 2078 */     String strPatch = "distinct";
/* 2079 */     String strWherePart = null;
/*      */ 
/* 2081 */     String[] fieldCodes = getFieldCode();
/*      */ 
/* 2083 */     if ((fieldCodes != null) && (fieldCodes.length > 0) && 
/* 2084 */       (fieldCodes[0] != null) && (strPatch.indexOf(fieldCodes[0].toLowerCase()) >= 0))
/*      */     {
/* 2086 */       strPatch = "";
/*      */     }
/*      */ 
/* 2089 */     if (isMatchPkWithWherePart())
/* 2090 */       strWherePart = getWherePart();
/*      */     String matchsql;
/* 2093 */     if ((isLargeDataRef()) && (canUseDB())) {
/* 2094 */       matchsql = getSpecialMatchSql(new String[] { getMatchField() }, strPkValues, strPatch, getFieldCode(), getHiddenFieldCode(), getTableName(), strWherePart, getOrderPart());
/*      */     }
/*      */     else
/*      */     {
/* 2100 */       matchsql = getSql_Match(new String[] { getMatchField() }, strPkValues, strPatch, getFieldCode(), getHiddenFieldCode(), getTableName(), strWherePart, getOrderPart());
/*      */     }
/*      */ 
/* 2105 */     setPKMatch(false);
/*      */ 
/* 2107 */     setSealedDataShow(originSealedDataShow);
/* 2108 */     setUseDataPower(originUseDataPower);
/*      */ 
/* 2110 */     return matchsql;
/*      */   }
/*      */ 
/*      */   public void setFilterDlgClaseName(String newFilterDlgClaseName)
/*      */   {
/* 2120 */     this.refFilterDlgClaseName = newFilterDlgClaseName;
/*      */   }
/*      */ 
/*      */   public void setFromTempTable(boolean newFromTempTable)
/*      */   {
/* 2130 */     this.isFromTempTable = newFromTempTable;
/*      */   }
/*      */ 
/*      */   public void setIncludeSub(boolean newIncludeSub)
/*      */   {
/* 2140 */     this.isIncludeSub = newIncludeSub;
/*      */   }
/*      */ 
/*      */   public void setIsRefreshMatch(boolean newRefreshMatch)
/*      */   {
/* 2150 */     this.m_isRefreshMatch = newRefreshMatch;
/*      */   }
/*      */ 
/*      */   public void setMnecode(String[] newM_mnecodes)
/*      */   {
/* 2160 */     this.m_mnecodes = newM_mnecodes;
/*      */   }
/*      */ 
/*      */   public void setPara(Object newPara)
/*      */   {
/* 2170 */     this.userPara = newPara;
/*      */   }
/*      */ 
/*      */   public void setPara(Object newPara, boolean isRrefreshData)
/*      */   {
/* 2177 */     setPara(newPara);
/* 2178 */     if (isRrefreshData)
/* 2179 */       clearModelData();
/*      */   }
/*      */ 
/*      */   public void setPk_corp(String strPk_corp, boolean isRrefreshData)
/*      */   {
/* 2187 */     setPk_corp(strPk_corp);
/* 2188 */     if (isRrefreshData)
/* 2189 */       clearModelData();
/*      */   }
/*      */ 
/*      */   public void setPk_corpValue(String strPk_corp)
/*      */   {
/* 2197 */     this.m_strPk_corp = strPk_corp;
/*      */   }
/*      */ 
/*      */   public void setQuerySql(String newQuerySql)
/*      */   {
/* 2207 */     this.querySql = newQuerySql;
/*      */   }
/*      */ 
/*      */   public void setRefQueryDlgClaseName(String newRefQueryDlgClaseName)
/*      */   {
/* 2217 */     this.refQueryDlgClaseName = newRefQueryDlgClaseName;
/*      */   }
/*      */ 
/*      */   public void setSealedDataShow(boolean newSealedDataShow)
/*      */   {
/* 2227 */     this.m_isSealedDataShow = newSealedDataShow;
/*      */   }
/*      */ 
/*      */   public void setTempDataWherePart(String newTempDataWherePart)
/*      */   {
/* 2237 */     this.refTempDataWherePart = newTempDataWherePart;
/*      */   }
/*      */ 
/*      */   public void setWherePart(String newWherePart, boolean isRrefreshData)
/*      */   {
/* 2244 */     setWherePart(newWherePart);
/* 2245 */     if (isRrefreshData)
/* 2246 */       clearModelData();
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public AbstractRefModel getM_defGridmodel()
/*      */   {
/* 2258 */     return this.m_defGridmodel;
/*      */   }
/*      */ 
/*      */   public String getPk_GlOrgBook()
/*      */   {
/* 2265 */     return this.m_pk_glOrgBook;
/*      */   }
/*      */ 
/*      */   public String getOrgTypeCode()
/*      */   {
/* 2272 */     return this.m_orgTypeCode;
/*      */   }
/*      */ 
/*      */   public void setPk_GlOrgBook(String orgTypeCode, String pk_GlOrgBook)
/*      */   {
/* 2286 */     String pk = pk_GlOrgBook;
/* 2287 */     String type = orgTypeCode;
/* 2288 */     BdinfoVO infoVO = getBdinfoVO();
/* 2289 */     if (infoVO != null) {
/* 2290 */       pk = BDGLOrgBookAccessor.getAccuratePK(infoVO.getOrgtypecode(), orgTypeCode, pk_GlOrgBook);
/*      */ 
/* 2292 */       type = infoVO.getOrgtypecode();
/*      */     }
/* 2294 */     setOrgTypeCodeAndPK(type, pk);
/*      */ 
/* 2297 */     resetWherePart();
/*      */   }
/*      */ 
/*      */   public void setOrgTypeCodeAndPK(String orgTypeCode, String pk)
/*      */   {
/* 2306 */     if ("1".equals(orgTypeCode)) {
/* 2307 */       setPk_corp(pk);
/* 2308 */       this.m_orgTypeCode = null;
/* 2309 */       this.m_pk_glOrgBook = null;
/*      */     }
/* 2311 */     else if ("2".equals(orgTypeCode)) {
/* 2312 */       this.m_orgTypeCode = orgTypeCode;
/* 2313 */       this.m_pk_glOrgBook = pk;
/*      */     }
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void setM_defGridmodel(AbstractRefModel newM_defGridmodel)
/*      */   {
/* 2324 */     this.m_defGridmodel = newM_defGridmodel;
/*      */   }
/*      */ 
/*      */   public String getDataPowerSubSql(String strTableName, String strTableShowName)
/*      */   {
/* 2332 */     String tableName = strTableName;
/* 2333 */     if (strTableName != null) {
/* 2334 */       tableName = strTableName.trim();
/*      */     }
/* 2336 */     String powerSql = this.modelHandler.getDataPowerSubSql(tableName, strTableShowName, this);
/*      */ 
/* 2338 */     return powerSql;
/*      */   }
/*      */ 
/*      */   protected String getDataPowerSqlKey(String strTableName, String strTableShowName)
/*      */   {
/* 2343 */     String pk_org = null;
/* 2344 */     if (getPk_GlOrgBook() != null)
/* 2345 */       pk_org = getPk_GlOrgBook();
/*      */     else {
/* 2347 */       pk_org = getPk_corp();
/*      */     }
/* 2349 */     String tableName = strTableName;
/* 2350 */     if (strTableName != null) {
/* 2351 */       tableName = strTableName.trim();
/*      */     }
/* 2353 */     String dataPowerKey = tableName + "_" + strTableShowName + pk_org;
/* 2354 */     return dataPowerKey;
/*      */   }
/*      */ 
/*      */   public String getFun_code()
/*      */   {
/* 2361 */     return this.m_fun_code;
/*      */   }
/*      */ 
/*      */   public void setFun_code(String m_fun_code)
/*      */   {
/* 2370 */     boolean isEqual = this.modelHandler.equals(this.m_fun_code, m_fun_code);
/*      */ 
/* 2372 */     this.m_fun_code = m_fun_code;
/*      */ 
/* 2375 */     if (!(isEqual))
/* 2376 */       setRefNodeName(getRefNodeName());
/*      */   }
/*      */ 
/*      */   protected RefVO_mlang[] getRefVO_mlang()
/*      */   {
/* 2385 */     return null;
/*      */   }
/*      */ 
/*      */   private Vector getValues_mlang(Vector vecData, RefVO_mlang[] refVO_mlangs)
/*      */   {
/* 2404 */     Vector datas = new Vector();
/*      */ 
/* 2406 */     if ((vecData != null) && (vecData.size() > 0)) {
/* 2407 */       int recordAccout = vecData.size();
/* 2408 */       if (refVO_mlangs != null) {
/* 2409 */         for (int i = 0; i < refVO_mlangs.length; ++i) {
/* 2410 */           RefVO_mlang refVO_mlang = refVO_mlangs[i];
/* 2411 */           String resid = "";
/* 2412 */           String[] resIDFieldNames = refVO_mlangs[i].getResIDFieldNames();
/*      */ 
/* 2415 */           Object[] oData_mlang = new Object[recordAccout];
/*      */ 
/* 2417 */           for (int j = 0; j < recordAccout; ++j)
/*      */           {
/* 2419 */             Vector record = (Vector)vecData.get(j);
/* 2420 */             resid = "";
/* 2421 */             if (resIDFieldNames != null) {
/* 2422 */               resid = resid + refVO_mlang.getPreStr();
/* 2423 */               for (int k = 0; k < resIDFieldNames.length; ++k) {
/* 2424 */                 Object oValue = record.get(getFieldIndex(resIDFieldNames[k]));
/*      */ 
/* 2426 */                 if (oValue != null) {
/* 2427 */                   resid = resid + oValue.toString();
/*      */                 }
/*      */ 
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/* 2434 */             String str_multiLang = NCLangRes4VoTransl.getNCLangRes().getStrByID(refVO_mlang.getDirName(), resid);
/*      */ 
/* 2438 */             if (resid.equals(str_multiLang)) {
/* 2439 */               str_multiLang = record.get(getFieldIndex(refVO_mlang.getFieldName())).toString();
/*      */             }
/*      */ 
/* 2443 */             oData_mlang[j] = str_multiLang;
/*      */           }
/*      */ 
/* 2446 */           datas.add(oData_mlang);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2452 */     return datas;
/*      */   }
/*      */ 
/*      */   private void setMlangValues(Vector vecData, RefVO_mlang[] refVO_mlangs)
/*      */   {
/* 2462 */     if ((vecData == null) || (vecData.size() == 0) || (refVO_mlangs == null)) {
/* 2463 */       return;
/*      */     }
/* 2465 */     Vector vec_malng = getValues_mlang(vecData, refVO_mlangs);
/* 2466 */     if ((vec_malng == null) || (vec_malng.size() != refVO_mlangs.length)) {
/* 2467 */       return;
/*      */     }
/* 2469 */     String[] fieldNames = new String[refVO_mlangs.length];
/* 2470 */     for (int i = 0; i < refVO_mlangs.length; ++i) {
/* 2471 */       fieldNames[i] = refVO_mlangs[i].getFieldName();
/*      */     }
/* 2473 */     setValuesByFieldName(vecData, vec_malng, fieldNames);
/*      */   }
/*      */ 
/*      */   public boolean isLargeDataRef()
/*      */   {
/* 2660 */     if ((this.m_strRefNodeName == null) || (this.m_strRefNodeName.equals(""))) {
/* 2661 */       return false;
/*      */     }
/*      */ 
/* 2664 */     return (("客商基本档案:客商档案:客户档案:供应商档案:客商辅助核算:客户辅助核算:供应商辅助核算:客商档案包含冻结:客户档案包含冻结:供应商档案包含冻结:存货基本档案:存货档案:存货辅助核算:物料档案".indexOf(this.m_strRefNodeName) >= 0) && (isChangeTableSeq()));
/*      */   }
/*      */ 
/*      */   public String changeBaseTable(String table)
/*      */   {
/* 2677 */     if ((table == null) || (table.indexOf("join") < 0))
/* 2678 */       return table;
/* 2679 */     StringTokenizer token = new StringTokenizer(table);
/* 2680 */     String firstTable = "";
/* 2681 */     String secondTable = "";
/* 2682 */     String lastElement = "";
/* 2683 */     String joinStr = "";
/* 2684 */     String onStr = "";
/* 2685 */     boolean isJoin = false;
/* 2686 */     boolean isOn = false;
/* 2687 */     int index = 0;
/* 2688 */     while (token.hasMoreTokens()) {
/* 2689 */       String element = token.nextToken();
/* 2690 */       if (lastElement.equalsIgnoreCase("join")) {
/* 2691 */         secondTable = element;
/* 2692 */         isJoin = false;
/*      */       }
/* 2694 */       if (element.equalsIgnoreCase("on")) {
/* 2695 */         isOn = true;
/*      */       }
/* 2697 */       if (isJoin) {
/* 2698 */         joinStr = joinStr + new StringBuffer().append(" ").append(element).append(" ").toString();
/*      */       }
/*      */ 
/* 2701 */       if (isOn) {
/* 2702 */         onStr = onStr + new StringBuffer().append(" ").append(element).append(" ").toString();
/*      */       }
/*      */ 
/* 2705 */       if (index == 0) {
/* 2706 */         firstTable = element + " ";
/*      */ 
/* 2708 */         isJoin = true;
/*      */       }
/*      */ 
/* 2711 */       lastElement = element;
/* 2712 */       ++index;
/*      */     }
/*      */ 
/* 2715 */     return secondTable + joinStr + firstTable + onStr;
/*      */   }
/*      */ 
/*      */   public String getSpecialMatchSql(String[] fieldNames, String[] values, String strPatch, String[] strFieldCode, String[] hiddenFields, String strTableName, String strWherePart, String strOrderField)
/*      */   {
/* 2726 */     if ((strTableName == null) || (strTableName.trim().length() == 0))
/* 2727 */       return null;
/* 2728 */     if (!(isPKMatch())) {
/* 2729 */       strTableName = changeBaseTable(strTableName);
/*      */     }
/* 2731 */     if (strOrderField != null) {
/* 2732 */       strOrderField = filterColumn(strOrderField);
/*      */     }
/* 2734 */     return getSql_Match(fieldNames, values, strPatch, strFieldCode, hiddenFields, strTableName, strWherePart, strOrderField);
/*      */   }
/*      */ 
/*      */   public String buildBaseSql(String patch, String[] columns, String[] hiddenColumns, String tableName, String whereCondition)
/*      */   {
/* 2744 */     StringBuffer whereClause = new StringBuffer();
/* 2745 */     StringBuffer sql = new StringBuffer("select ").append(patch).append(" ");
/*      */ 
/* 2747 */     int columnCount = (columns == null) ? 0 : columns.length;
/* 2748 */     addQueryColumn(columnCount, sql, columns, hiddenColumns);
/*      */ 
/* 2750 */     sql.append(" from ").append(tableName);
/*      */ 
/* 2752 */     if ((whereCondition != null) && (whereCondition.trim().length() != 0))
/* 2753 */       whereClause.append(" where (").append(whereCondition).append(" )");
/*      */     else {
/* 2755 */       whereClause.append(" where 11=11 ");
/*      */     }
/* 2757 */     appendAddWherePartCondition(whereClause);
/*      */ 
/* 2759 */     addDataPowerCondition(getTableName(), whereClause);
/* 2760 */     addSealCondition(whereClause);
/* 2761 */     addEnvWherePart(whereClause);
/* 2762 */     sql.append(" ").append(whereClause.toString());
/*      */ 
/* 2764 */     return sql.toString();
/*      */   }
/*      */ 
/*      */   protected void addDataPowerCondition(String tableName, StringBuffer whereClause)
/*      */   {
/* 2769 */     if (!(isUseDataPower()))
/*      */       return;
/* 2771 */     String powerSql = getDataPowerSubSql(tableName, getDataPowerName());
/*      */ 
/* 2773 */     if (powerSql == null)
/*      */       return;
/* 2775 */     whereClause.append(" and (").append(getDataPowerResourceColumn()).append(" in (").append(powerSql).append("))");
/*      */   }
/*      */ 
/*      */   public String getDataPowerResourceColumn()
/*      */   {
/* 2784 */     if (this.dataPowerResourceColumn == null) {
/* 2785 */       return getPkFieldCode();
/*      */     }
/* 2787 */     return this.dataPowerResourceColumn;
/*      */   }
/*      */ 
/*      */   private void appendAddWherePartCondition(StringBuffer whereClause)
/*      */   {
/* 2792 */     if (getAddWherePart() == null) {
/* 2793 */       return;
/*      */     }
/*      */ 
/* 2796 */     if ((isPKMatch()) && (!(isMatchPkWithWherePart())))
/*      */     {
/* 2798 */       return;
/*      */     }
/*      */ 
/* 2801 */     whereClause.append(" ").append(getAddWherePart());
/*      */   }
/*      */ 
/*      */   protected String getDataPowerName()
/*      */   {
/* 2809 */     return getRefNodeName();
/*      */   }
/*      */ 
/*      */   public void addQueryColumn(int iSelectFieldCount, StringBuffer strSql, String[] strFieldCode, String[] hiddenFields)
/*      */   {
/* 2822 */     for (int i = 0; i < iSelectFieldCount; ++i) {
/* 2823 */       strSql.append(strFieldCode[i]);
/* 2824 */       if (i < iSelectFieldCount - 1) {
/* 2825 */         strSql.append(",");
/*      */       }
/*      */     }
/* 2828 */     if ((hiddenFields != null) && (hiddenFields.length > 0))
/* 2829 */       for (int k = 0; k < hiddenFields.length; ++k) {
/* 2830 */         if ((hiddenFields[k] == null) || (hiddenFields[k].trim().length() <= 0))
/*      */           continue;
/* 2832 */         strSql.append(",");
/* 2833 */         strSql.append(hiddenFields[k]);
/*      */       }
/*      */   }
/*      */ 
/*      */   public void addSealCondition(StringBuffer whereClause)
/*      */   {
/* 2846 */     if (isSealedDataShow())
/*      */       return;
/* 2848 */     String wherePart = getSealedWherePart();
/* 2849 */     if (wherePart != null)
/* 2850 */       whereClause.append(" and (").append(wherePart).append(") ");
/*      */   }
/*      */ 
/*      */   public String getSealedWherePart()
/*      */   {
/* 2863 */     if (this.sealedWherePart != null) {
/* 2864 */       return this.sealedWherePart;
/*      */     }
/*      */ 
/* 2867 */     this.sealedWherePart = DocSealWherePartMng.getSealedWherePart(getRefNodeName());
/*      */ 
/* 2869 */     if (this.sealedWherePart == null) {
/* 2870 */       this.sealedWherePart = DocSealWherePartMng.getSealedWherePart(getTableName());
/*      */     }
/*      */ 
/* 2874 */     return this.sealedWherePart;
/*      */   }
/*      */ 
/*      */   public void setSealedWherePart(String sealedWherePart)
/*      */   {
/* 2884 */     this.sealedWherePart = sealedWherePart;
/*      */   }
/*      */ 
/*      */   public String filterColumn(String column)
/*      */   {
/* 2894 */     return column.substring(column.indexOf(".") + 1, column.length());
/*      */   }
/*      */ 
/*      */   protected String getSql(String strPatch, String[] strFieldCode, String[] hiddenFields, String strTableName, String strWherePart, String strGroupField, String strOrderField)
/*      */   {
/* 2905 */     if ((strTableName == null) || (strTableName.trim().length() == 0)) {
/* 2906 */       return null;
/*      */     }
/*      */ 
/* 2909 */     String basSQL = buildBaseSql(strPatch, strFieldCode, hiddenFields, strTableName, strWherePart);
/*      */ 
/* 2911 */     StringBuffer sqlBuffer = new StringBuffer(basSQL);
/* 2912 */     if (getQuerySql() != null) {
/* 2913 */       addQueryCondition(sqlBuffer);
/*      */     }
/* 2915 */     if ((getBlurValue() != null) && (isIncludeBlurPart())) {
/* 2916 */       String blurSql = addBlurWherePart();
/* 2917 */       sqlBuffer.append(blurSql);
/*      */     }
/*      */ 
/* 2922 */     if (strGroupField != null) {
/* 2923 */       sqlBuffer.append(" group by ").append(strGroupField).toString();
/*      */     }
/*      */ 
/* 2926 */     if ((strOrderField != null) && (strOrderField.trim().length() != 0)) {
/* 2927 */       sqlBuffer.append(" order by ").append(strOrderField).toString();
/*      */     }
/* 2929 */     return sqlBuffer.toString();
/*      */   }
/*      */ 
/*      */   protected void addQueryCondition(StringBuffer sqlBuffer) {
/* 2933 */     sqlBuffer.append(" and (").append(getPkFieldCode()).append(" in (").append(getQuerySql()).append("))").toString();
/*      */   }
/*      */ 
/*      */   public String addBlurWherePart()
/*      */   {
/* 2941 */     return this.modelHandler.addBlurWherePart();
/*      */   }
/*      */ 
/*      */   protected String getSql_Match(String[] fieldNames, String[] values, String strPatch, String[] strFieldCode, String[] hiddenFields, String strTableName, String strWherePart, String strOrderField)
/*      */   {
/* 2949 */     if ((strTableName == null) || (strTableName.trim().length() == 0)) {
/* 2950 */       return null;
/*      */     }
/* 2952 */     String basSQL = null;
/*      */ 
/* 2954 */     basSQL = buildBaseSql(strPatch, strFieldCode, hiddenFields, strTableName, strWherePart);
/*      */ 
/* 2957 */     StringBuffer buffer = new StringBuffer(basSQL);
/*      */ 
/* 2959 */     buffer.append(" and (");
/* 2960 */     if (isPKMatch()) {
/* 2961 */       buffer.append(getWherePartByFieldsAndValues(fieldNames, values));
/*      */     } else {
/* 2963 */       String[] toLowCasefieldNames = new String[fieldNames.length];
/* 2964 */       String[] toLowCaseValues = new String[values.length];
/* 2965 */       for (int i = 0; i < toLowCasefieldNames.length; ++i) {
/* 2966 */         toLowCasefieldNames[i] = RefPubUtil.toLowerDBFunctionWrapper(this, fieldNames[i]);
/*      */       }
/*      */ 
/* 2969 */       for (int i = 0; i < toLowCaseValues.length; ++i) {
/* 2970 */         toLowCaseValues[i] = RefPubUtil.toLowerCaseStr(this, values[i]);
/*      */       }
/*      */ 
/* 2973 */       buffer.append(getWherePartByFieldsAndValues(toLowCasefieldNames, toLowCaseValues));
/*      */     }
/*      */ 
/* 2976 */     buffer.append(") ");
/*      */ 
/* 2978 */     if (strOrderField != null) {
/* 2979 */       buffer.append(" order by ").append(strOrderField).toString();
/*      */     }
/* 2981 */     return buffer.toString();
/*      */   }
/*      */ 
/*      */   public Hashtable getConvertHT(String tableName, String[] fieldNames)
/*      */   {
/* 2991 */     setDdReaderVO(tableName, fieldNames);
/*      */ 
/* 2993 */     return null;
/*      */   }
/*      */ 
/*      */   private Hashtable getDDReaderMap(String tableName, String[] fieldNames)
/*      */   {
/* 3009 */     DataDictionaryReader ddReader = new DataDictionaryReader(tableName);
/* 3010 */     Hashtable convert = new Hashtable();
/*      */ 
/* 3012 */     for (int i = 0; i < fieldNames.length; ++i)
/*      */     {
/* 3014 */       String newfieldName = fieldNames[i];
/* 3015 */       if (newfieldName.indexOf(".") > 0) {
/* 3016 */         newfieldName = newfieldName.substring(newfieldName.indexOf(".") + 1, newfieldName.length());
/*      */       }
/*      */ 
/* 3019 */       String[] keys = ddReader.getQzsm(newfieldName);
/* 3020 */       Hashtable contents = new Hashtable();
/* 3021 */       for (int j = 0; j < keys.length; ++j) {
/* 3022 */         if (keys[j] != null) {
/* 3023 */           contents.put(ddReader.getNumberByQzsm(newfieldName, keys[j]).toString(), keys[j]);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3029 */       convert.put(fieldNames[i], contents);
/*      */     }
/* 3031 */     return convert;
/*      */   }
/*      */ 
/*      */   public boolean isMatchPkWithWherePart()
/*      */   {
/* 3039 */     return this.isMatchPkWithWherePart;
/*      */   }
/*      */ 
/*      */   public void setMatchPkWithWherePart(boolean isMatchPkWithWherePart)
/*      */   {
/* 3047 */     this.isMatchPkWithWherePart = isMatchPkWithWherePart;
/*      */   }
/*      */ 
/*      */   public boolean isRefEnable()
/*      */   {
/* 3054 */     return this.m_isRefEnable;
/*      */   }
/*      */ 
/*      */   public void setisRefEnable(boolean refEnable)
/*      */   {
/* 3063 */     this.m_isRefEnable = refEnable;
/*      */   }
/*      */ 
/*      */   public String[][] getFormulas()
/*      */   {
/* 3071 */     return this.formulas;
/*      */   }
/*      */ 
/*      */   public void setFormulas(String[][] formulas)
/*      */   {
/* 3079 */     this.formulas = formulas;
/*      */   }
/*      */ 
/*      */   public boolean isLocQueryEnable()
/*      */   {
/* 3086 */     return this.isLocQueryEnable;
/*      */   }
/*      */ 
/*      */   public void setLocQueryEnable(boolean isLocQueryEnable)
/*      */   {
/* 3094 */     this.isLocQueryEnable = isLocQueryEnable;
/*      */   }
/*      */ 
/*      */   public HashMap getHmFilterPks()
/*      */   {
/* 3101 */     return this.m_hmFilterPks;
/*      */   }
/*      */ 
/*      */   public int getFilterStrategy()
/*      */   {
/* 3108 */     return this.m_filterStrategy;
/*      */   }
/*      */ 
/*      */   public String[] getFilterPks()
/*      */   {
/* 3115 */     return this.m_filterPks;
/*      */   }
/*      */ 
/*      */   public Vector matchData(String[] fields, String[] values)
/*      */   {
/* 3127 */     Vector vMatchData = getMatchedRecords(fields, values);
/*      */ 
/* 3131 */     if ((isCacheEnabled()) && (vMatchData == null) && (isRefreshMatch())) {
/* 3132 */       clearData();
/* 3133 */       vMatchData = getMatchedRecords(fields, values);
/*      */     }
/*      */ 
/* 3142 */     setSelectedData(vMatchData);
/*      */ 
/* 3144 */     return vMatchData;
/*      */   }
/*      */ 
/*      */   protected Vector getMatchedRecords(String[] fieldNames, String[] values)
/*      */   {
/* 3157 */     Vector vMatchedRecords = null;
/*      */ 
/* 3159 */     if ((values == null) || (values.length == 0)) {
/* 3160 */       return vMatchedRecords;
/*      */     }
/*      */ 
/* 3163 */     if ((fieldNames == null) || (fieldNames.length == 0))
/*      */     {
/* 3165 */       return vMatchedRecords;
/*      */     }
/*      */ 
/* 3168 */     getMatchHM().clear();
/* 3169 */     for (int i = 0; i < values.length; ++i) {
/* 3170 */       if (values[i] != null) {
/* 3171 */         getMatchHM().put(RefPubUtil.toLowerCaseStr(this, values[i]), RefPubUtil.toLowerCaseStr(this, values[i]));
/*      */       }
/*      */     }
/*      */ 
/* 3175 */     vMatchedRecords = new Vector();
/*      */ 
/* 3177 */     setIncludeBlurPart(false);
/*      */ 
/* 3179 */     Vector data = null;
/*      */ 
/* 3181 */     data = getRefData();
/*      */ 
/* 3183 */     if (data != null) {
/* 3184 */       for (int i = 0; i < data.size(); ++i) {
/* 3185 */         Vector vRecord = (Vector)data.elementAt(i);
/* 3186 */         if (vRecord == null)
/*      */           continue;
/* 3188 */         for (int j = 0; j < fieldNames.length; ++j) {
/* 3189 */           int col = getFieldIndex(fieldNames[j]);
/* 3190 */           if (col < 0) continue; if (col >= vRecord.size()) {
/*      */             continue;
/*      */           }
/* 3193 */           if ((vRecord.elementAt(col) == null) || (getMatchHM().get(RefPubUtil.toLowerCaseStr(this, vRecord.elementAt(col).toString().trim())) == null))
/*      */           {
/*      */             continue;
/*      */           }
/*      */ 
/* 3198 */           vMatchedRecords.addElement(vRecord);
/* 3199 */           break;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3206 */     if ((vMatchedRecords != null) && (vMatchedRecords.size() == 0))
/* 3207 */       vMatchedRecords = null;
/* 3208 */     setIncludeBlurPart(true);
/*      */ 
/* 3210 */     return vMatchedRecords;
/*      */   }
/*      */ 
/*      */   public boolean isOnlyMneFields()
/*      */   {
/* 3219 */     return this.isOnlyMneFields;
/*      */   }
/*      */ 
/*      */   public void setOnlyMneFields(boolean newIsOnlyMneFields)
/*      */   {
/* 3229 */     this.isOnlyMneFields = newIsOnlyMneFields;
/*      */   }
/*      */ 
/*      */   public String getWherePartByFieldsAndValues(String[] fields, String[] values)
/*      */   {
/* 3239 */     String wherePart = "";
/* 3240 */     StringBuffer sb = new StringBuffer();
/* 3241 */     if ((fields != null) && (values != null)) {
/* 3242 */       int length = fields.length;
/* 3243 */       for (int i = 0; i < length; ++i) {
/* 3244 */         if (values.length == 1)
/*      */         {
/* 3246 */           sb.append(fields[i]).append("='").append(values[0]).append("' ");
/*      */         }
/*      */         else
/*      */         {
/* 3252 */           sb.append(RefPubUtil.getInSubSql(fields[i], values));
/*      */         }
/*      */ 
/* 3256 */         if (i == length - 1) {
/*      */           continue;
/*      */         }
/* 3259 */         sb.append(" or ");
/*      */       }
/*      */     }
/*      */     else {
/* 3263 */       return null;
/*      */     }
/*      */ 
/* 3266 */     wherePart = sb.toString();
/* 3267 */     return wherePart;
/*      */   }
/*      */ 
/*      */   public String getAddWherePart()
/*      */   {
/* 3275 */     return this.m_addWherePart;
/*      */   }
/*      */ 
/*      */   public void saveTempData(Object[] retPKs)
/*      */   {
/* 3282 */     if ((retPKs == null) || (retPKs.length == 0)) {
/* 3283 */       setTempDataWherePart(null);
/* 3284 */       return;
/*      */     }
/*      */ 
/* 3287 */     String pk_ts = null;
/*      */ 
/* 3289 */     RefdatatempVO[] vos = new RefdatatempVO[retPKs.length];
/* 3290 */     for (int i = 0; i < retPKs.length; ++i) {
/* 3291 */       vos[i] = new RefdatatempVO();
/* 3292 */       vos[i].setPk_selecteddata(retPKs[i].toString());
/* 3293 */       vos[i].setPk_corp(getPk_corp());
/* 3294 */       vos[i].setCuserid(getPk_user());
/* 3295 */       vos[i].setRefnodename(getRefNodeName());
/* 3296 */       vos[i].setPk_ts("temp");
/*      */     }
/*      */     try
/*      */     {
/* 3300 */       pk_ts = this.modelHandler.saveRefDataTempVOs(vos);
/*      */     }
/*      */     catch (Exception e) {
/* 3303 */       Debug.error(e.getMessage(), e);
/*      */     }
/*      */ 
/* 3306 */     String selSql = "select pk_selecteddata from bd_refdatatemp ";
/*      */ 
/* 3308 */     String wherePart = selSql + " where pk_corp='" + getPk_corp() + "' and cuserid = '" + getPk_user() + "' " + " and refnodename='" + getRefNodeName() + "' and pk_ts = '" + pk_ts + "'";
/*      */ 
/* 3312 */     setTempDataWherePart(wherePart);
/*      */   }
/*      */ 
/*      */   public void setColDispValues(Vector vecData, Object[][] dispConverter)
/*      */   {
/* 3322 */     if ((vecData == null) || (vecData.size() == 0) || (dispConverter == null)) {
/* 3323 */       return;
/*      */     }
/*      */ 
/* 3327 */     for (int i = 0; i < dispConverter.length; ++i)
/*      */     {
/* 3330 */       RefColumnDispConvertVO convertVO = (RefColumnDispConvertVO)dispConverter[i][0];
/* 3331 */       if (convertVO == null) {
/*      */         continue;
/*      */       }
/*      */ 
/* 3335 */       String className = dispConverter[i][1].toString();
/*      */ 
/* 3339 */       convertVO.setRefData(vecData);
/*      */ 
/* 3341 */       this.m_vecData = this.modelHandler.setColDispValue(vecData, convertVO, className);
/*      */     }
/*      */   }
/*      */ 
/*      */   public ReftableVO getRefTableVO(String pk_corp)
/*      */   {
/* 3351 */     return this.modelHandler.getRefTableVO(pk_corp);
/*      */   }
/*      */ 
/*      */   public void setReftableVO2Cache(ReftableVO vo, String pk_org)
/*      */   {
/* 3359 */     this.modelHandler.setReftableVO2Cache(vo, pk_org);
/*      */   }
/*      */ 
/*      */   public String getReftableVOCacheKey(String pk_org) {
/* 3363 */     String refNodeName = getRefNodeName();
/*      */ 
/* 3372 */     String key = refNodeName + pk_org;
/* 3373 */     return key;
/*      */   }
/*      */ 
/*      */   public Vector getQueryResultVO()
/*      */   {
/* 3380 */     boolean isQueryRefColumn = !(this.modelHandler.isReftableVOCached(getPk_corp()));
/*      */ 
/* 3383 */     RefQueryVO queryVO = getRefQueryVO();
/*      */ 
/* 3386 */     queryVO.setPk_org(getPk_corp());
/* 3387 */     RefQueryResultVO resultVO = null;
/*      */ 
/* 3389 */     resultVO = this.modelHandler.queryRefdataByQueryVO(queryVO);
/*      */ 
/* 3391 */     if (resultVO == null) {
/* 3392 */       return null;
/*      */     }
/* 3394 */     Vector v = resultVO.getRefData();
/* 3395 */     if (isQueryRefColumn) {
/* 3396 */       setReftableVO2Cache(resultVO.getRefTableVO(), getPk_corp());
/*      */     }
/*      */ 
/* 3399 */     return v;
/*      */   }
/*      */ 
/*      */   protected RefQueryVO getRefQueryVO() {
/* 3403 */     boolean isQueryRefColumn = !(this.modelHandler.isReftableVOCached(getPk_corp()));
/*      */ 
/* 3406 */     this.queryVO.setDataSourceName(getDataSource());
/* 3407 */     this.queryVO.setRefNodeName(getRefNodeName());
/* 3408 */     this.queryVO.setQuerySql(getRefSql());
/* 3409 */     this.queryVO.setObj(getPara());
/* 3410 */     this.queryVO.setQueryRefColumn(isQueryRefColumn);
/* 3411 */     return this.queryVO;
/*      */   }
/*      */ 
/*      */   public String getRefTitle()
/*      */   {
/* 3420 */     if (this.m_strRefTitle != null) {
/* 3421 */       return this.m_strRefTitle;
/*      */     }
/*      */ 
/* 3424 */     this.m_strRefTitle = this.modelHandler.getRefNodeName_mLang(getRefNodeName());
/*      */ 
/* 3426 */     if (this.m_strRefTitle == null) {
/* 3427 */       this.m_strRefTitle = getRefNodeName();
/*      */     }
/*      */ 
/* 3430 */     return this.m_strRefTitle;
/*      */   }
/*      */ 
/*      */   public void setPk_user(String m_pk_user)
/*      */   {
/* 3438 */     this.m_pk_user = m_pk_user;
/*      */   }
/*      */ 
/*      */   public void resetFieldName()
/*      */   {
/* 3445 */     if ((getFieldCode() == null) || (getTableName() == null) || ((getFieldName() != null) && (getFieldName().length != 0))) {
/*      */       return;
/*      */     }
/* 3448 */     String[] name = new String[getFieldCode().length];
/*      */ 
/* 3450 */     for (int i = 0; i < getFieldCode().length; ++i)
/*      */     {
/* 3452 */       String fieldCode = this.modelHandler.getFieldCodeWithTableName(getFieldCode()[i]);
/*      */ 
/* 3455 */       String resid = this.modelHandler.getResID(fieldCode);
/*      */ 
/* 3457 */       name[i] = this.modelHandler.getRefMultiLangStr(resid, fieldCode);
/*      */     }
/*      */ 
/* 3460 */     setFieldName(name);
/*      */   }
/*      */ 
/*      */   private FormulaHandler getFormulaHandler()
/*      */   {
/* 3468 */     if (this.formulaHandler == null) {
/* 3469 */       this.formulaHandler = new FormulaHandler();
/*      */     }
/* 3471 */     return this.formulaHandler;
/*      */   }
/*      */ 
/*      */   private DefFieldsHandler getDefFieldHandler() {
/* 3475 */     if (this.defFieldHandler == null) {
/* 3476 */       this.defFieldHandler = new DefFieldsHandler();
/*      */     }
/* 3478 */     return this.defFieldHandler;
/*      */   }
/*      */ 
/*      */   private void setValuesByFieldName(Vector vecData, Vector newVecData, String[] fieldNames)
/*      */   {
/* 3489 */     int col = -1;
/* 3490 */     for (int i = 0; i < fieldNames.length; ++i)
/*      */     {
/* 3492 */       col = getFieldIndex(fieldNames[i]);
/* 3493 */       Object[] values = (Object[])(Object[])newVecData.get(i);
/* 3494 */       if (values == null) {
/*      */         continue;
/*      */       }
/* 3497 */       for (int j = 0; j < values.length; ++j) {
/* 3498 */         Vector vRecord = (Vector)vecData.get(j);
/* 3499 */         if ((vRecord == null) || (vRecord.size() == 0) || (col < 0)) continue; if (col >= vRecord.size())
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/* 3504 */         Object obj = getRefValueVO(vRecord.elementAt(col), values[j]);
/*      */ 
/* 3506 */         vRecord.setElementAt(obj, col);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private RefValueVO getRefValueVO(Object originValue, Object newValue)
/*      */   {
/* 3525 */     if (originValue instanceof RefValueVO) {
/* 3526 */       return ((RefValueVO)originValue);
/*      */     }
/* 3528 */     RefValueVO valueVO = new RefValueVO();
/* 3529 */     valueVO.setOriginValue(originValue);
/* 3530 */     valueVO.setNewValue(newValue);
/* 3531 */     return valueVO;
/*      */   }
/*      */ 
/*      */   public String getPk_user()
/*      */   {
/* 3538 */     if (this.m_pk_user == null) {
/* 3539 */       this.m_pk_user = this.modelHandler.getPk_user();
/*      */     }
/* 3541 */     return this.m_pk_user;
/*      */   }
/*      */ 
/*      */   public void addChangeListener(ChangeListener l)
/*      */   {
/* 3559 */     if (this.eventListMap.get(l) == null) {
/* 3560 */       this.eListenerList.add(ChangeListener.class, l);
/* 3561 */       this.eventListMap.put(l, l);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeChangeListener(ChangeListener l) {
/* 3566 */     this.eListenerList.add(ChangeListener.class, l);
/* 3567 */     this.eventListMap.remove(l);
/*      */   }
/*      */ 
/*      */   public ChangeListener[] getChangeListeners() {
/* 3571 */     return ((ChangeListener[])(ChangeListener[])this.eListenerList.getListeners(ChangeListener.class));
/*      */   }
/*      */ 
/*      */   public void fireChange()
/*      */   {
/* 3576 */     Object[] listeners = this.eListenerList.getListenerList();
/* 3577 */     for (int i = listeners.length - 2; i >= 0; i -= 2)
/* 3578 */       if (listeners[i] == ChangeListener.class) {
/* 3579 */         if (this.changeEvent == null) {
/* 3580 */           this.changeEvent = new ChangeEvent(this);
/*      */         }
/* 3582 */         ((ChangeListener)listeners[(i + 1)]).stateChanged(this.changeEvent);
/*      */       }
/*      */   }
/*      */ 
/*      */   public String[] getFieldCode()
/*      */   {
/* 3593 */     return this.m_strFieldCode;
/*      */   }
/*      */ 
/*      */   public String[] getFieldName()
/*      */   {
/* 3602 */     return this.m_strFieldName;
/*      */   }
/*      */ 
/*      */   public String getGroupCode()
/*      */   {
/* 3611 */     return "0001";
/*      */   }
/*      */ 
/*      */   public String getPkFieldCode()
/*      */   {
/* 3620 */     return this.m_strPkFieldCode;
/*      */   }
/*      */ 
/*      */   public String getTableName()
/*      */   {
/* 3635 */     return this.m_strTableName;
/*      */   }
/*      */ 
/*      */   public void setFieldCode(String[] newFieldCode)
/*      */   {
/* 3645 */     getHtCodeIndex().clear();
/* 3646 */     this.m_strFieldCode = newFieldCode;
/*      */   }
/*      */ 
/*      */   public void setFieldName(String[] newFieldName)
/*      */   {
/* 3656 */     this.m_strFieldName = newFieldName;
/*      */   }
/*      */ 
/*      */   public void setPkFieldCode(String strPkFieldCode)
/*      */   {
/* 3663 */     this.m_strPkFieldCode = strPkFieldCode;
/*      */   }
/*      */ 
/*      */   public String getFun_code_DocOpen()
/*      */   {
/* 3670 */     return this.m_fun_code_DocOpen;
/*      */   }
/*      */ 
/*      */   public void setFun_code_DocOpen(String docOpen)
/*      */   {
/* 3678 */     this.m_fun_code_DocOpen = docOpen;
/*      */   }
/*      */ 
/*      */   public int[] getIntFieldType()
/*      */   {
/* 3685 */     return this.m_intFieldType;
/*      */   }
/*      */ 
/*      */   public void setIntFieldType(int[] fieldType)
/*      */   {
/* 3693 */     this.m_intFieldType = fieldType;
/*      */   }
/*      */ 
/*      */   public boolean isGroupAssignData()
/*      */   {
/* 3700 */     return this.isGroupAssignData;
/*      */   }
/*      */ 
/*      */   public void setGroupAssignData(boolean isGroupAssignData)
/*      */   {
/* 3708 */     this.isGroupAssignData = isGroupAssignData;
/*      */   }
/*      */ 
/*      */   public String getRefDataCacheKey() {
/* 3712 */     String key = this.refDataCachekeyPreStr + getDataPowerSqlKey(getTableName(), getRefNodeName());
/*      */ 
/* 3715 */     return key;
/*      */   }
/*      */ 
/*      */   public String getFieldShowName(String fieldCode) {
/* 3719 */     int index = getFieldIndex(fieldCode);
/* 3720 */     if ((index < 0) || (index > getFieldName().length - 1)) {
/* 3721 */       return null;
/*      */     }
/* 3723 */     return getFieldName()[index];
/*      */   }
/*      */ 
/*      */   private DDReaderVO getDdReaderVO()
/*      */   {
/* 3731 */     return this.ddReaderVO;
/*      */   }
/*      */ 
/*      */   public void setDdReaderVO(String tableName, String[] fieldnames)
/*      */   {
/* 3739 */     if (this.ddReaderVO == null) {
/* 3740 */       this.ddReaderVO = new DDReaderVO();
/*      */     }
/* 3742 */     this.ddReaderVO.setTableName(tableName);
/* 3743 */     this.ddReaderVO.setFieldNames(fieldnames);
/*      */   }
/*      */ 
/*      */   public int getResourceID()
/*      */   {
/* 3750 */     return this.resourceID;
/*      */   }
/*      */ 
/*      */   public void setResourceID(int resourceID)
/*      */   {
/* 3758 */     this.resourceID = resourceID;
/*      */   }
/*      */ 
/*      */   public boolean isPKMatch()
/*      */   {
/* 3765 */     return this.isPKMatch;
/*      */   }
/*      */ 
/*      */   public void setPKMatch(boolean isPKMatch)
/*      */   {
/* 3773 */     this.isPKMatch = isPKMatch;
/*      */   }
/*      */ 
/*      */   public boolean isIncludeBlurPart()
/*      */   {
/* 3780 */     return this.isIncludeBlurPart;
/*      */   }
/*      */ 
/*      */   public void setIncludeBlurPart(boolean isIncludeBlurPart)
/*      */   {
/* 3788 */     this.isIncludeBlurPart = isIncludeBlurPart;
/*      */   }
/*      */ 
/*      */   private String getEnvWherePart()
/*      */   {
/* 3795 */     return this.envWherePart;
/*      */   }
/*      */ 
/*      */   public void setEnvWherePart(String envWherePart)
/*      */   {
/* 3805 */     this.envWherePart = envWherePart;
/*      */   }
/*      */ 
/*      */   private void addEnvWherePart(StringBuffer whereClause)
/*      */   {
/* 3813 */     if (isPKMatch()) {
/* 3814 */       return;
/*      */     }
/* 3816 */     String wherePart = getEnvWherePart();
/* 3817 */     if (wherePart == null)
/*      */       return;
/* 3819 */     whereClause.append(" and (").append(wherePart).append(") ");
/*      */   }
/*      */ 
/*      */   public BdinfoVO getBdinfoVO()
/*      */   {
/* 3829 */     if ((this.bdinfoVO == null) && (!(this.isInitBDinfoVO))) {
/* 3830 */       this.bdinfoVO = this.modelHandler.getBdInfoVObyName(getRefNodeName());
/* 3831 */       this.isInitBDinfoVO = true;
/*      */     }
/* 3833 */     return this.bdinfoVO;
/*      */   }
/*      */ 
/*      */   public void setBdinfoVO(BdinfoVO bdinfoVO)
/*      */   {
/* 3841 */     this.bdinfoVO = bdinfoVO;
/* 3842 */     this.isInitBDinfoVO = true;
/*      */   }
/*      */ 
/*      */   public boolean isChangeTableSeq()
/*      */   {
/* 3849 */     return this.isChangeTableSeq;
/*      */   }
/*      */ 
/*      */   public void setChangeTableSeq(boolean isChangeTableSeq)
/*      */   {
/* 3857 */     this.isChangeTableSeq = isChangeTableSeq;
/*      */   }
/*      */ 
/*      */   public boolean isMaintainButtonEnabled()
/*      */   {
/* 3864 */     return this.isMaintainButtonEnabled;
/*      */   }
/*      */ 
/*      */   public void setMaintainButtonEnabled(boolean isMaintainButtonEnabled)
/*      */   {
/* 3872 */     this.isMaintainButtonEnabled = isMaintainButtonEnabled;
/*      */   }
/*      */ 
/*      */   public void setDataPowerResourceColumn(String dataPowerResourceColumn)
/*      */   {
/* 3880 */     this.dataPowerResourceColumn = dataPowerResourceColumn;
/*      */   }
/*      */ 
/*      */   public String getRefCardInfoComponentImplClass()
/*      */   {
/* 3887 */     return this.refCardInfoComponentImplClass;
/*      */   }
/*      */ 
/*      */   public void setRefCardInfoComponentImplClass(String refCardInfoComponentImplClass)
/*      */   {
/* 3896 */     this.refCardInfoComponentImplClass = refCardInfoComponentImplClass;
/*      */   }
/*      */ 
/*      */   public String getRefShowNameField() {
/* 3900 */     return ((this.refShowNameField == null) ? getRefNameField() : this.refShowNameField);
/*      */   }
/*      */ 
/*      */   public void setRefShowNameField(String refShowNameField) {
/* 3904 */     this.refShowNameField = refShowNameField;
/*      */   }
/*      */ 
/*      */   public String[] getDefFields()
/*      */   {
/* 3910 */     return this.defFields;
/*      */   }
/*      */ 
/*      */   public String[] getShowDefFields()
/*      */   {
/* 3919 */     String[] defFieldNames = null;
/* 3920 */     if ((getDefFields() == null) || (getDefFields().length == 0)) {
/* 3921 */       return defFieldNames;
/*      */     }
/*      */ 
/* 3924 */     Map map = this.modelHandler.getRefColumnVOsMap();
/*      */ 
/* 3926 */     List list = new ArrayList();
/* 3927 */     for (int i = 0; i < getDefFields().length; ++i) {
/* 3928 */       if ((map.get(getDefFields()[i]) == null) || (!(((RefcolumnVO)map.get(getDefFields()[i])).getIscolumnshow().booleanValue()))) {
/*      */         continue;
/*      */       }
/* 3931 */       list.add(getDefFields()[i]);
/*      */     }
/*      */ 
/* 3935 */     if (list.size() > 0) {
/* 3936 */       String[] fieldNames = new String[list.size()];
/* 3937 */       list.toArray(fieldNames);
/* 3938 */       defFieldNames = fieldNames;
/*      */     }
/*      */ 
/* 3941 */     return defFieldNames;
/*      */   }
/*      */ 
/*      */   public void setDefFields(String[] defFields) {
/* 3945 */     this.defFields = defFields;
/*      */   }
/*      */ 
/*      */   public Map<String, Integer> getAlignMap() {
/* 3949 */     return this.alignMap;
/*      */   }
/*      */ 
/*      */   private void setAlignMap(Map<String, Integer> alignMap) {
/* 3953 */     this.alignMap = alignMap;
/*      */   }
/*      */ 
/*      */   public void addFieldAlignment(String fieldName, int alignment)
/*      */   {
/* 3965 */     if (getAlignMap() == null) {
/* 3966 */       setAlignMap(new HashMap());
/*      */     }
/* 3968 */     getAlignMap().put(fieldName, new Integer(alignment));
/*      */   }
/*      */ 
/*      */   public boolean canUseDB() {
/* 3972 */     return this.modelHandler.canUseDB();
/*      */   }
/*      */ 
/*      */   public Vector getCacheValue(String sql) {
/* 3976 */     return this.modelHandler.getCacheValue(sql);
/*      */   }
/*      */ 
/*      */   public Vector queryMain(String dsName, String sql) {
/* 3980 */     return this.modelHandler.queryMain(dsName, sql);
/*      */   }
/*      */ 
/*      */   public void removeCacheValue(String sql) {
/* 3984 */     this.modelHandler.removeCache(sql);
/*      */   }
/*      */ 
/*      */   public void setCacheValue(String sql, Vector value) {
/* 3988 */     this.modelHandler.setCacheValue(sql, value);
/*      */   }
/*      */ 
/*      */   public String getCodeRuleFromPara(String orgTypeCode, String pk_GlOrgBook, String codingRule, UFBoolean boolean1)
/*      */   {
/* 3993 */     return this.modelHandler.getCodeRuleFromPara(orgTypeCode, pk_GlOrgBook, codingRule, boolean1);
/*      */   }
/*      */ 
/*      */   public String getCodeRuleFromPara(String codingRule)
/*      */   {
/* 3998 */     return this.modelHandler.getCodeRuleFromPara(codingRule);
/*      */   }
/*      */ 
/*      */   public String getNumberCodingRule(String codingRule) {
/* 4002 */     return this.modelHandler.getNumberCodingRule(codingRule);
/*      */   }
/*      */ 
/*      */   public String[] getDefFields(String[] fieldCode) {
/* 4006 */     return this.modelHandler.getDefFields(fieldCode);
/*      */   }
/*      */ 
/*      */   public String getPara1() {
/* 4010 */     return this.para1;
/*      */   }
/*      */ 
/*      */   public void setPara1(String para1) {
/* 4014 */     this.para1 = para1;
/*      */   }
/*      */ 
/*      */   public String getPara2() {
/* 4018 */     return this.para2;
/*      */   }
/*      */ 
/*      */   public void setPara2(String para2) {
/* 4022 */     this.para2 = para2;
/*      */   }
/*      */ 
/*      */   public String getPara3() {
/* 4026 */     return this.para3;
/*      */   }
/*      */ 
/*      */   public void setPara3(String para3) {
/* 4030 */     this.para3 = para3;
/*      */   }
/*      */ 
/*      */   protected IRefModelHandler getModelHandler() {
/* 4034 */     return this.modelHandler;
/*      */   }
/*      */ 
/*      */   public String getUiControlComponentClassName() {
/* 4038 */     return this.uiControlComponentClassName;
/*      */   }
/*      */ 
/*      */   public void setUiControlComponentClassName(String uiControlComponentClassName)
/*      */   {
/* 4043 */     this.uiControlComponentClassName = uiControlComponentClassName;
/*      */   }
/*      */ 
/*      */   public void UiControlClassValueChanged(UIControlClassEvent event)
/*      */   {
/*      */   }
/*      */ 
/*      */   public String getRefDocEditClassName()
/*      */   {
/* 4057 */     return this.refDocEditClassName;
/*      */   }
/*      */ 
/*      */   public void setRefDocEditClassName(String refDocEditClassName) {
/* 4061 */     this.refDocEditClassName = refDocEditClassName;
/*      */   }
/*      */ 
/*      */   public boolean isShowField(String fieldName) {
/* 4065 */     boolean isShowField = false;
/* 4066 */     int[] showColumns = getShownColumns();
/* 4067 */     int index = getFieldIndex(fieldName);
/* 4068 */     for (int i = 0; i < showColumns.length; ++i) {
/* 4069 */       if (showColumns[i] == index) {
/* 4070 */         isShowField = true;
/* 4071 */         break;
/*      */       }
/*      */     }
/* 4074 */     return isShowField;
/*      */   }
/*      */ 
/*      */   public String[] getShowFields() {
/* 4078 */     String[] showFields = new String[getShownColumns().length];
/* 4079 */     int fieldCodeLength = getFieldCode().length;
/* 4080 */     for (int i = 0; i < showFields.length; ++i) {
/* 4081 */       int index = getShownColumns()[i];
/* 4082 */       if (index < fieldCodeLength)
/* 4083 */         showFields[i] = getFieldCode()[index];
/*      */       else {
/* 4085 */         Logger.debug("参照栏目信息有误，请点击参照栏目中的重置按钮。该参照为：" + getRefNodeName());
/*      */       }
/*      */     }
/* 4088 */     return showFields;
/*      */   }
/*      */ 
/*      */   public boolean isHiddenField(String fieldCode)
/*      */   {
/* 4093 */     return this.hiddenFieldList.contains(fieldCode);
/*      */   }
/*      */ 
/*      */   public boolean isCaseSensitve()
/*      */   {
/* 4098 */     return this.isCaseSensitve;
/*      */   }
/*      */ 
/*      */   public void setCaseSensive(boolean isCaseSensitve) {
/* 4102 */     this.isCaseSensitve = isCaseSensitve;
/*      */   }
/*      */ 
/*      */   public Vector getAllColumnNames()
/*      */   {
/* 4107 */     if (this.vecAllColumnNames != null) {
/* 4108 */       return this.vecAllColumnNames;
/*      */     }
/*      */ 
/* 4111 */     this.vecAllColumnNames = new Vector();
/* 4112 */     if ((getFieldCode() != null) && (getFieldCode().length > 0)) {
/* 4113 */       if ((getFieldName() == null) || (getFieldName().length == 0)) {
/* 4114 */         for (int i = 0; i < getFieldCode().length; ++i) {
/* 4115 */           this.vecAllColumnNames.addElement(getFieldCode()[i]);
/*      */         }
/*      */       }
/* 4118 */       else if (getFieldName().length >= getFieldCode().length) {
/* 4119 */         for (int i = 0; i < getFieldCode().length; ++i)
/* 4120 */           this.vecAllColumnNames.addElement(getFieldName()[i]);
/*      */       }
/*      */       else {
/* 4123 */         for (int i = 0; i < getFieldName().length; ++i) {
/* 4124 */           this.vecAllColumnNames.addElement(getFieldName()[i]);
/*      */         }
/* 4126 */         for (int i = getFieldName().length; i < getFieldCode().length; ++i) {
/* 4127 */           this.vecAllColumnNames.addElement(getFieldCode()[i]);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 4134 */     if (getHiddenFieldCode() != null) {
/* 4135 */       for (int i = 0; i < getHiddenFieldCode().length; ++i) {
/* 4136 */         this.vecAllColumnNames.addElement(getHiddenFieldCode()[i]);
/*      */       }
/*      */     }
/*      */ 
/* 4140 */     if (isDynamicCol())
/*      */     {
/* 4142 */       String[] dynamicColNames = getDynamicFieldNames();
/* 4143 */       if (getDynamicFieldNames() != null)
/*      */       {
/* 4145 */         for (int i = 0; i < dynamicColNames.length; ++i)
/*      */         {
/* 4148 */           this.vecAllColumnNames.addElement(dynamicColNames[i]);
/*      */         }
/*      */       }
/*      */     }
/* 4152 */     return this.vecAllColumnNames;
/*      */   }
/*      */ 
/*      */   protected String getRefCacheSqlKey()
/*      */   {
/* 4157 */     return ((getRefSql() == null) ? super.getClass().getName() : getRefSql());
/*      */   }
/*      */ 
/*      */   class FormulaHandler
/*      */   {
/*      */     private FormulaParseFather parse;
/*      */ 
/*      */     FormulaHandler()
/*      */     {
/* 2517 */       this.parse = null;
/*      */     }
/*      */ 
/*      */     void setFormulaValues(Vector vecData, String[][] formulas)
/*      */     {
/* 2525 */       if ((vecData == null) || (vecData.size() == 0) || (formulas == null)) {
/* 2526 */         return;
/*      */       }
/* 2528 */       String[][] showFormulas = getShowFieldFormulas(formulas);
/* 2529 */       if (showFormulas == null) {
/* 2530 */         return;
/*      */       }
/* 2532 */       Object[][] datas = getValues_formulas(vecData, showFormulas);
/*      */ 
/* 2534 */       if ((datas == null) || (datas.length != showFormulas.length)) {
/* 2535 */         return;
/*      */       }
/* 2537 */       Vector v = new Vector();
/* 2538 */       String[] fieldNames = new String[datas.length];
/* 2539 */       for (int i = 0; i < datas.length; ++i) {
/* 2540 */         v.add(datas[i]);
/* 2541 */         fieldNames[i] = showFormulas[i][0];
/*      */       }
/* 2543 */       AbstractRefModel.this.setValuesByFieldName(vecData, v, fieldNames);
/*      */     }
/*      */ 
/*      */     private String[][] getShowFieldFormulas(String[][] formulas)
/*      */     {
/* 2548 */       List list = new ArrayList();
/* 2549 */       for (int i = 0; i < formulas.length; ++i) {
/* 2550 */         String formulaField = formulas[i][0];
/* 2551 */         if (AbstractRefModel.this.isShowField(formulaField)) {
/* 2552 */           list.add(formulas[i]);
/*      */         }
/*      */       }
/* 2555 */       String[][] showFormulas = (String[][])null;
/* 2556 */       if (list.size() > 0) {
/* 2557 */         showFormulas = new String[list.size()][];
/* 2558 */         list.toArray(showFormulas);
/*      */       }
/* 2560 */       return showFormulas;
/*      */     }
/*      */ 
/*      */     private Object[][] getValues_formulas(Vector vecData, String[][] formulas)
/*      */     {
/* 2567 */       Object[][] datas = (Object[][])null;
/* 2568 */       int flen = formulas.length;
/* 2569 */       if ((vecData != null) && (vecData.size() > 0) && (flen > 0))
/*      */       {
/* 2571 */         int recordAccout = vecData.size();
/* 2572 */         datas = new Object[flen][recordAccout];
/* 2573 */         ArrayList list = new ArrayList();
/* 2574 */         String formulaField = null;
/* 2575 */         String strFormula = null;
/* 2576 */         for (int i = 0; i < flen; ++i) {
/* 2577 */           list.clear();
/* 2578 */           formulaField = formulas[i][0];
/* 2579 */           strFormula = formulas[i][1];
/*      */ 
/* 2581 */           for (int j = 0; j < recordAccout; ++j) {
/* 2582 */             Vector record = (Vector)vecData.get(j);
/* 2583 */             list.add(getFormulaValue(record, formulaField));
/*      */           }
/* 2585 */           datas[i] = getFormulaValues(formulaField, strFormula, list);
/*      */         }
/*      */       }
/*      */ 
/* 2589 */       return datas;
/*      */     }
/*      */ 
/*      */     private String getFormulaValue(Vector record, String formulaField)
/*      */     {
/* 2606 */       int fieldIndex = AbstractRefModel.this.getFieldIndex(formulaField);
/* 2607 */       if (fieldIndex == -1) {
/* 2608 */         return null;
/*      */       }
/* 2610 */       Object obj = record.get(fieldIndex);
/*      */ 
/* 2612 */       String value = null;
/*      */ 
/* 2614 */       if ((obj instanceof RefValueVO) && (obj != null))
/*      */       {
/* 2616 */         Object originValue = ((RefValueVO)obj).getOriginValue();
/*      */ 
/* 2618 */         value = (originValue == null) ? null : originValue.toString();
/*      */       }
/* 2621 */       else if (obj != null) {
/* 2622 */         value = obj.toString();
/*      */       }
/*      */ 
/* 2625 */       return value;
/*      */     }
/*      */ 
/*      */     private Object[] getFormulaValues(String fieldName, String formulas, List givenvalues)
/*      */     {
/* 2630 */       FormulaParseFather parse = getParse();
/*      */ 
/* 2632 */       String[] expresses = StringUtil.toArray(formulas, ";");
/* 2633 */       parse.setExpressArray(expresses);
/* 2634 */       parse.addVariable(fieldName, givenvalues);
/* 2635 */       Object[][] values = parse.getValueOArray();
/* 2636 */       return values[(values.length - 1)];
/*      */     }
/*      */ 
/*      */     private FormulaParseFather getParse()
/*      */     {
/* 2643 */       if (this.parse == null) {
/* 2644 */         this.parse = new FormulaParse();
/*      */       }
/* 2646 */       return this.parse;
/*      */     }
/*      */   }
/*      */ 
/*      */   class DefFieldsHandler
/*      */   {
/*      */     void setDefFieldValues(Vector vecData)
/*      */     {
/* 2485 */       String[] defFields = AbstractRefModel.this.getShowDefFields();
/*      */ 
/* 2487 */       if ((vecData == null) || (vecData.size() == 0) || (defFields == null) || (defFields.length == 0))
/*      */       {
/* 2489 */         return;
/*      */       }
/* 2491 */       Vector rawData = new Vector();
/* 2492 */       Vector convertedData = null;
/* 2493 */       DefFieldInfo defFieldInfo = new DefFieldInfo(defFields);
/*      */ 
/* 2495 */       for (int i = 0; i < defFields.length; ++i)
/* 2496 */         rawData.add(AbstractRefModel.this.getValues(defFields[i], vecData));
/*      */       try
/*      */       {
/* 2499 */         convertedData = UFRefDefTanslateUtil.getDefValues(defFieldInfo, rawData, Integer.valueOf(AbstractRefModel.this.getOrgTypeCode()).intValue(), AbstractRefModel.this.getPk_corp());
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 2503 */         Logger.error(e.getMessage(), e);
/*      */       }
/*      */ 
/* 2506 */       if (convertedData == null) {
/* 2507 */         return;
/*      */       }
/*      */ 
/* 2510 */       AbstractRefModel.this.setValuesByFieldName(vecData, convertedData, defFields);
/*      */     }
/*      */   }
/*      */ 
/*      */   private class DDReaderVO
/*      */   {
/*      */     String tableName;
/*      */     String[] fieldNames;
/*      */ 
/*      */     private DDReaderVO()
/*      */     {
/*  330 */       this.tableName = null;
/*      */ 
/*  332 */       this.fieldNames = null;
/*      */     }
/*      */ 
/*      */     public String[] getFieldNames()
/*      */     {
/*  338 */       return this.fieldNames;
/*      */     }
/*      */ 
/*      */     public void setFieldNames(String[] fieldNames)
/*      */     {
/*  346 */       this.fieldNames = fieldNames;
/*      */     }
/*      */ 
/*      */     public String getTableName()
/*      */     {
/*  353 */       return this.tableName;
/*      */     }
/*      */ 
/*      */     public void setTableName(String tableName)
/*      */     {
/*  361 */       this.tableName = tableName;
/*      */     }
/*      */   }
/*      */ }
 