/*     */ package nc.bs.pf.pub;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import nc.bs.framework.common.InvocationInfoProxy;
/*     */ import nc.bs.framework.common.NCLocator;
/*     */ import nc.bs.framework.common.RuntimeEnv;
/*     */ import nc.bs.logging.Logger;
/*     */ import nc.itf.uap.IUAPQueryBS;
/*     */ import nc.itf.uap.bd.currtype.ICurrtype;
/*     */ import nc.jdbc.framework.SQLParameter;
/*     */ import nc.jdbc.framework.processor.BeanListProcessor;
/*     */ import nc.ui.pub.ClientEnvironment;
/*     */ import nc.vo.bd.b20.CurrtypeVO;
/*     */ import nc.vo.cache.ext.CacheToMapAdapter;
/*     */ import nc.vo.cache.ext.ElementVersionSensitiveMap;
/*     */ import nc.vo.cache.ext.ICacheVersionMonitor;
/*     */ import nc.vo.cache.ext.ObjectCacheVersionMonitor;
/*     */ import nc.vo.cache.ext.VersionMonitorFactory;
/*     */ import nc.vo.jcom.lang.StringUtil;
/*     */ import nc.vo.pf.change.PfUtilBaseTools;
/*     */ import nc.vo.pf.changeui02.VotableVO;
/*     */ import nc.vo.pf.pub.FunctionVO;
/*     */ import nc.vo.pub.BusinessException;
/*     */ import nc.vo.pub.billtobill.BilltobillreferVO;
/*     */ import nc.vo.pub.billtype.BilltypeVO;
/*     */ import nc.vo.pub.billtype.DapsystemVO;
/*     */ import nc.vo.pub.billtype.DefitemVO;
/*     */ import nc.vo.pub.billtype2.Billtype2VO;
/*     */ import nc.vo.pub.lang.UFBoolean;
/*     */ import nc.vo.pub.pfflow.BillactionVO;
/*     */ import nc.vo.sm.config.Account;
/*     */ import nc.vo.uap.pf.DynamicOrgRegInfo;
/*     */ import nc.vo.uap.pf.DynamicOrganizeUnitRegistry;
/*     */ import nc.vo.uap.pf.PFBusinessException;
/*     */ import nc.vo.wfengine.core.application.WorkflowgadgetVO;
/*     */ import nc.vo.wfengine.core.parser.UfXPDLParser;
/*     */ import nc.vo.wfengine.core.parser.XPDLParserException;
/*     */ import nc.vo.wfengine.core.workflow.BasicWorkflowProcess;
/*     */ import nc.vo.wfengine.definition.WorkflowDefinitionVO;
/*     */ 
/*     */ public class PfDataCache
/*     */ {
/*     */   protected static final String CACHE_REGION = "platform";
/*  75 */   public static String STR_BILLTYPEINFO = "BILLTYPEINFO";
/*     */ 
/*  80 */   public static String STR_WORKFLOWGADGETINFO = "WORKFLOWGADGETINFO";
/*     */ 
/*  85 */   public static String STR_BILLTYPE2INFO = "BILLTYPE2INFO";
/*     */   public static final String STR_BILLTOBILLREFER = "KHHINFOHAS";
/*  95 */   public static String STR_BILLSTYLETOTYPE = "BILLSTYLETOTYPE";
/*     */ 
/* 100 */   public static String STR_BILLTYPETOVO = "BILLTYPETOVO";
/*     */ 
/* 105 */   public static String STR_BILLTYPETOFUNCTION = "BILLTYPETOFUNCTION";
/*     */ 
/* 110 */   public static String STR_SYSTEMTYPEINFO = "SYSTEMTYPEINFO";
/*     */ 
/* 115 */   private static String DYNAMIC_ORG_REGINFO = "DYNAMICORGINFO";
/*     */   private static final String KHHCURRENCY = "KHHCURRENCY";
/*     */   public static final String STR_BILLITEM = "BILLITEM";
/*     */   public static final String STR_BILLACTION = "BILLACTION";
/*     */   public static final String STR_WORKFLOWPROCESS = "WORKFLOWPROCESS";
/* 140 */   private static Hashtable dsName_instance_map = new Hashtable();
/*     */ 
/*     */   private static String getCurrentDs() {
/* 143 */     if (RuntimeEnv.getInstance().isRunningInServer())
/*     */     {
/* 145 */       String dsName = InvocationInfoProxy.getInstance().getUserDataSource();
/* 146 */       return dsName;
/*     */     }
/*     */ 
/* 149 */     Account account = ClientEnvironment.getInstance().getConfigAccount();
/* 150 */     String dsName = account.getDataSourceName();
/* 151 */     return dsName;
/*     */   }
/*     */ 
/*     */   public static ElementVersionSensitiveMap getVersionSensitiveCache()
/*     */   {
/* 161 */     String currDS = getCurrentDs();
/* 162 */     if (dsName_instance_map.get(currDS) == null) {
/* 163 */       CacheToMapAdapter adapter = (CacheToMapAdapter)CacheToMapAdapter.getInstance(currDS + "platform");
/*     */ 
/* 165 */       ElementVersionSensitiveMap versionMap = new ElementVersionSensitiveMap(adapter, new PfVersionMonitorFacotry());
/*     */ 
/* 167 */       dsName_instance_map.put(currDS, versionMap);
/*     */     }
/* 169 */     return ((ElementVersionSensitiveMap)dsName_instance_map.get(currDS));
/*     */   }
/*     */ 
/*     */   public static CurrtypeVO getCurrType(String strPk)
/*     */   {
/* 177 */     HashMap hashCacheObj = null;
/* 178 */     String hashKey = strPk;
/*     */     try {
/* 180 */       hashCacheObj = (HashMap)getVersionSensitiveCache().get("KHHCURRENCY");
/* 181 */       if (hashCacheObj == null) {
/* 182 */         hashCacheObj = new HashMap();
/* 183 */         setCurrTypeInfo(hashCacheObj);
/* 184 */         getVersionSensitiveCache().put("KHHCURRENCY", hashCacheObj);
/* 185 */       } else if (!(hashCacheObj.containsKey(hashKey))) {
/* 186 */         setCurrTypeInfo(hashCacheObj);
/*     */       }
/*     */     } catch (Exception ex) {
/* 189 */       Logger.error(ex.getMessage(), ex);
/*     */     }
/* 191 */     return ((CurrtypeVO)hashCacheObj.get(hashKey));
/*     */   }
/*     */ 
/*     */   private static void setCurrTypeInfo(HashMap tmpHas)
/*     */   {
/*     */     try
/*     */     {
/* 199 */       ICurrtype currtype = (ICurrtype)NCLocator.getInstance().lookup(ICurrtype.class.getName());
/* 200 */       CurrtypeVO[] tmpVos = currtype.queryAllCurrtypeVO(null);
/* 201 */       if (tmpVos != null)
/* 202 */         for (int i = 0; i < tmpVos.length; ++i)
/* 203 */           tmpHas.put(tmpVos[i].getPk_currtype(), tmpVos[i]);
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 207 */       Logger.error(ex.getMessage(), ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static ArrayList getBillType2Info(String billType, int classtype)
/*     */   {
/* 218 */     HashMap hashCacheObj = null;
/* 219 */     String strTypeCode = PfUtilBaseTools.getRealBilltype(billType);
/*     */     try {
/* 221 */       hashCacheObj = (HashMap)getVersionSensitiveCache().get(STR_BILLTYPE2INFO);
/*     */ 
/* 223 */       if (hashCacheObj == null) {
/* 224 */         hashCacheObj = queryAllBilltype2();
/*     */ 
/* 226 */         getVersionSensitiveCache().put(STR_BILLTYPE2INFO, hashCacheObj);
/* 227 */       } else if ((!(hashCacheObj.containsKey(Integer.valueOf(classtype)))) && (!(hasCachedBilltype2(hashCacheObj, strTypeCode))))
/*     */       {
/* 230 */         HashMap hmBilltype2VOs = queryAllBilltype2();
/* 231 */         hashCacheObj.putAll(hmBilltype2VOs);
/*     */       }
/*     */     } catch (Exception ex) {
/* 234 */       Logger.error(ex.getMessage(), ex);
/*     */     }
/*     */ 
/* 237 */     return findBilltype2ByBilltypeAndClasstype(strTypeCode, classtype, hashCacheObj);
/*     */   }
/*     */ 
/*     */   private static boolean hasCachedBilltype2(HashMap hashCacheObj, String billType)
/*     */   {
/* 247 */     for (Iterator iter = hashCacheObj.values().iterator(); iter.hasNext(); ) {
/* 248 */       ArrayList alBilltype2VO = (ArrayList)iter.next();
/* 249 */       for ( Iterator iterator = alBilltype2VO.iterator(); iterator.hasNext(); ) {
/* 250 */         Billtype2VO bt2VO = (Billtype2VO)iterator.next();
/* 251 */         if (billType.equals(bt2VO.getPk_billtype()))
/* 252 */           return true;
/*     */       }
/*     */     }
/*     */     Iterator iterator;
/* 255 */     return false;
/*     */   }
/*     */ 
/*     */   private static HashMap queryAllBilltype2() throws BusinessException {
/* 259 */     HashMap hm = new HashMap();
/* 260 */     Collection coAll = ((IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class)).retrieveAll(Billtype2VO.class);
/*     */ 
/* 262 */     for (Iterator iterator = coAll.iterator(); iterator.hasNext(); ) {
/* 263 */       Billtype2VO bt2VO = (Billtype2VO)iterator.next();
/* 264 */       ArrayList alTemp = (ArrayList)hm.get(bt2VO.getClasstype());
/* 265 */       if (alTemp == null) {
/* 266 */         alTemp = new ArrayList();
/* 267 */         hm.put(bt2VO.getClasstype(), alTemp);
/*     */       }
/* 269 */       alTemp.add(bt2VO);
/*     */     }
/*     */ 
/* 272 */     return hm;
/*     */   }
/*     */ 
/*     */   private static ArrayList findBilltype2ByBilltypeAndClasstype(String billType, int classtype, HashMap hashCacheObj)
/*     */   {
/* 284 */     ArrayList alRet = new ArrayList();
/* 285 */     ArrayList alBilltype2VO = (ArrayList)hashCacheObj.get(Integer.valueOf(classtype));
/* 286 */     if (alBilltype2VO == null)
/* 287 */       return alRet;
/* 288 */     for (Iterator iter = alBilltype2VO.iterator(); iter.hasNext(); ) {
/* 289 */       Billtype2VO bt2VO = (Billtype2VO)iter.next();
/* 290 */       if ((classtype == bt2VO.getClasstype().intValue()) && ((
/* 292 */         (billType.equals(bt2VO.getPk_billtype())) || (bt2VO.getPk_billtype().equals("XX")))))
/*     */       {
/* 294 */         alRet.add(bt2VO);
/*     */       }
/*     */     }
/*     */ 
/* 298 */     return alRet;
/*     */   }
/*     */ 
/*     */   public static ArrayList<FunctionVO> getFunctionsOfBilltype(String billtype)
/*     */   {
/* 307 */     HashMap hashCacheObj = null;
/* 308 */     hashCacheObj = (HashMap)getVersionSensitiveCache().get(STR_BILLTYPETOFUNCTION);
/*     */ 
/* 310 */     if (hashCacheObj == null) {
/* 311 */       hashCacheObj = new HashMap();
/* 312 */       setFunctionVOs(hashCacheObj, billtype);
/* 313 */       getVersionSensitiveCache().put(STR_BILLTYPETOFUNCTION, hashCacheObj);
/* 314 */     } else if (!(hashCacheObj.containsKey(billtype))) {
/* 315 */       setFunctionVOs(hashCacheObj, billtype);
/*     */     }
/*     */ 
/* 318 */     return ((ArrayList)hashCacheObj.get(billtype));
/*     */   }
/*     */ 
/*     */   public static ArrayList getWorkflowGadget(String billType)
/*     */   {
/* 326 */     HashMap hashCacheObj = null;
/*     */     try {
/* 328 */       hashCacheObj = (HashMap)getVersionSensitiveCache().get(STR_WORKFLOWGADGETINFO);
/*     */ 
/* 330 */       if (hashCacheObj == null) {
/* 331 */         hashCacheObj = new HashMap();
/* 332 */         setWorkflowgadgetVOs(hashCacheObj, billType);
/*     */ 
/* 334 */         getVersionSensitiveCache().put(STR_WORKFLOWGADGETINFO, hashCacheObj);
/* 335 */       } else if (!(hashCacheObj.containsKey(billType)))
/*     */       {
/* 337 */         setWorkflowgadgetVOs(hashCacheObj, billType);
/*     */       }
/*     */     } catch (Exception ex) {
/* 340 */       Logger.error(ex.getMessage(), ex);
/*     */     }
/*     */ 
/* 343 */     return ((ArrayList)hashCacheObj.get(billType));
/*     */   }
/*     */ 
/*     */   private static void setWorkflowgadgetVOs(HashMap hasWorkflowgadgetInfo, String billtype)
/*     */     throws BusinessException
/*     */   {
/* 351 */     IUAPQueryBS uapQry = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
/*     */ 
/* 354 */     String where = "billtype = '" + billtype + "'";
/*     */     try
/*     */     {
/* 357 */       Collection wfVos = uapQry.retrieveByClause(WorkflowgadgetVO.class, where);
/* 358 */       hasWorkflowgadgetInfo.put(billtype, wfVos);
/*     */     } catch (BusinessException e) {
/* 360 */       Logger.error(e.getMessage(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static BasicWorkflowProcess getWorkflowProcess(String defPK)
/*     */     throws XPDLParserException, BusinessException
/*     */   {
/* 375 */     HashMap hashCacheObj = null;
/*     */ 
/* 377 */     hashCacheObj = (HashMap)getVersionSensitiveCache().get("WORKFLOWPROCESS");
/*     */ 
/* 379 */     if (hashCacheObj == null) {
/* 380 */       hashCacheObj = new HashMap();
/* 381 */       queryAndCacheProcess(hashCacheObj, defPK);
/*     */ 
/* 383 */       getVersionSensitiveCache().put("WORKFLOWPROCESS", hashCacheObj);
/* 384 */     } else if (!(hashCacheObj.containsKey(defPK)))
/*     */     {
/* 386 */       queryAndCacheProcess(hashCacheObj, defPK);
/*     */     }
/*     */ 
/* 389 */     return ((BasicWorkflowProcess)hashCacheObj.get(defPK));
/*     */   }
/*     */ 
/*     */   private static void queryAndCacheProcess(HashMap<String, BasicWorkflowProcess> hashCacheObj, String defPK)
/*     */     throws BusinessException, XPDLParserException
/*     */   {
/* 401 */     String sql = "select content from pub_wf_def where pk_wf_def=?";
/* 402 */     SQLParameter para = new SQLParameter();
/* 403 */     para.addParam(defPK);
/* 404 */     ArrayList alDefVOs = (ArrayList)((IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class)).executeQuery(sql, para, new BeanListProcessor(WorkflowDefinitionVO.class));
/*     */ 
/* 407 */     BasicWorkflowProcess bwp = null;
/* 408 */     if (alDefVOs.size() <= 0)
/*     */       return;
/* 410 */     WorkflowDefinitionVO defVO = (WorkflowDefinitionVO)alDefVOs.get(0);
/* 411 */     bwp = UfXPDLParser.getInstance().parseProcess((String)defVO.getContent());
/* 412 */     bwp.setPrimaryKey(defPK);
/*     */ 
/* 415 */     hashCacheObj.put(defPK, bwp);
/*     */   }
/*     */ 
/*     */   public static BilltypeVO getBillTypeInfo(String billType)
/*     */   {
/* 424 */     if (StringUtil.isEmptyWithTrim(billType))
/* 425 */       return new BilltypeVO();
/* 426 */     HashMap hashCacheObj = null;
/*     */     try {
/* 428 */       hashCacheObj = (HashMap)getVersionSensitiveCache().get(STR_BILLTYPEINFO);
/*     */ 
/* 430 */       if (hashCacheObj == null) {
/* 431 */         hashCacheObj = new HashMap();
/* 432 */         setBilltypeVOs(hashCacheObj);
/*     */ 
/* 434 */         getVersionSensitiveCache().put(STR_BILLTYPEINFO, hashCacheObj);
/* 435 */       } else if (!(hashCacheObj.containsKey(billType)))
/*     */       {
/* 437 */         setBilltypeVOs(hashCacheObj);
/*     */       }
/*     */     } catch (Exception ex) {
/* 440 */       Logger.error(ex.getMessage(), ex);
/*     */     }
/*     */ 
/* 443 */     BilltypeVO btVO = (BilltypeVO)hashCacheObj.get(billType);
/* 444 */     if ((btVO != null) && (btVO.getIstransaction() != null) && (btVO.getIstransaction().booleanValue()))
/*     */     {
/* 446 */       String strBt = btVO.getParentbilltype();
/* 447 */       if (!(StringUtil.isEmptyWithTrim(strBt))) {
/* 448 */         BilltypeVO pbt = getBillTypeInfo(strBt);
/* 449 */         if ((pbt != null) && (!(StringUtil.isEmptyWithTrim(pbt.getPrimaryKey()))))
/* 450 */           copySomeFieldsToTranstype(btVO, pbt);
/*     */       }
/*     */     }
/* 453 */     return btVO;
/*     */   }
/*     */ 
/*     */   protected static void copySomeFieldsToTranstype(BilltypeVO transtypeVO, BilltypeVO pbt)
/*     */   {
/* 462 */     if (StringUtil.isEmptyWithTrim(transtypeVO.getAccountclass()))
/* 463 */       transtypeVO.setAccountclass(pbt.getAccountclass());
/* 464 */     if (StringUtil.isEmptyWithTrim(transtypeVO.getCheckclassname()))
/* 465 */       transtypeVO.setCheckclassname(pbt.getCheckclassname());
/* 466 */     if (StringUtil.isEmptyWithTrim(transtypeVO.getDatafinderclz()))
/* 467 */       transtypeVO.setDatafinderclz(pbt.getDatafinderclz());
/* 468 */     if (StringUtil.isEmptyWithTrim(transtypeVO.getComponent())) {
/* 469 */       transtypeVO.setComponent(pbt.getComponent());
/*     */     }
/* 471 */     if (StringUtil.isEmptyWithTrim(transtypeVO.getDef1()))
/* 472 */       transtypeVO.setDef1(pbt.getDef1());
/* 473 */     if (StringUtil.isEmptyWithTrim(transtypeVO.getDef2()))
/* 474 */       transtypeVO.setDef2(pbt.getDef2());
/* 475 */     if (StringUtil.isEmptyWithTrim(transtypeVO.getDef3())) {
/* 476 */       transtypeVO.setDef3(pbt.getDef3());
/*     */     }
/* 478 */     if (StringUtil.isEmptyWithTrim(transtypeVO.getForwardbilltype())) {
/* 479 */       transtypeVO.setForwardbilltype(pbt.getForwardbilltype());
/*     */     }
/* 481 */     transtypeVO.setIsaccount(pbt.getIsaccount());
/* 482 */     transtypeVO.setIsitem(pbt.getIsitem());
/* 483 */     if (StringUtil.isEmptyWithTrim(transtypeVO.getReferclassname()))
/* 484 */       transtypeVO.setReferclassname(pbt.getReferclassname());
/* 485 */     if (StringUtil.isEmptyWithTrim(transtypeVO.getWherestring()))
/* 486 */       transtypeVO.setWherestring(pbt.getWherestring());
/*     */   }
/*     */ 
/*     */   public static BilltypeVO getBillType(String billType)
/*     */   {
/* 496 */     return getBillTypeInfo(billType);
/*     */   }
/*     */ 
/*     */   public static HashMap getBilltypes()
/*     */   {
/* 504 */     HashMap hashCacheObj = (HashMap)getVersionSensitiveCache().get(STR_BILLTYPEINFO);
/*     */     try
/*     */     {
/* 507 */       if (hashCacheObj == null) {
/* 508 */         hashCacheObj = new HashMap();
/* 509 */         setBilltypeVOs(hashCacheObj);
/*     */ 
/* 511 */         getVersionSensitiveCache().put(STR_BILLTYPEINFO, hashCacheObj);
/*     */       }
/*     */     } catch (Exception ex) {
/* 514 */       Logger.error(ex.getMessage(), ex);
/*     */     }
/* 516 */     return hashCacheObj;
/*     */   }
/*     */ 
/*     */   protected static void setBilltypeVOs(HashMap<String, BilltypeVO> hasBillTypeInfo)
/*     */     throws BusinessException
/*     */   {
/* 524 */     Collection coBilltypes = ((IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class)).retrieveAll(BilltypeVO.class);
/*     */ 
/* 526 */     for (Iterator iter = coBilltypes.iterator(); iter.hasNext(); ) {
/* 527 */       BilltypeVO billtype = (BilltypeVO)iter.next();
/* 528 */       hasBillTypeInfo.put(billtype.getPrimaryKey(), billtype);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static ArrayList getBillitemVOs(String billtype)
/*     */   {
/* 539 */     HashMap hashCacheObj = (HashMap)getVersionSensitiveCache().get("BILLITEM");
/*     */ 
/* 542 */     if (hashCacheObj == null) {
/* 543 */       hashCacheObj = new HashMap();
/* 544 */       setBillitemVOs(hashCacheObj, billtype);
/* 545 */       getVersionSensitiveCache().put("BILLITEM", hashCacheObj);
/* 546 */     } else if (!(hashCacheObj.containsKey(billtype))) {
/* 547 */       setBillitemVOs(hashCacheObj, billtype);
/*     */     }
/*     */ 
/* 550 */     return ((ArrayList)hashCacheObj.get(billtype));
/*     */   }
/*     */ 
/*     */   public static ArrayList getBillactionVOs(String billtype)
/*     */   {
/* 559 */     HashMap hashCacheObj = null;
/* 560 */     hashCacheObj = (HashMap)getVersionSensitiveCache().get("BILLACTION");
/*     */ 
/* 562 */     if (hashCacheObj == null) {
/* 563 */       hashCacheObj = new HashMap();
/* 564 */       setBillactionVOs(hashCacheObj, billtype);
/* 565 */       getVersionSensitiveCache().put("BILLACTION", hashCacheObj);
/* 566 */     } else if (!(hashCacheObj.containsKey(billtype))) {
/* 567 */       setBillactionVOs(hashCacheObj, billtype);
/*     */     }
/*     */ 
/* 570 */     return ((ArrayList)hashCacheObj.get(billtype));
/*     */   }
/*     */ 
/*     */   private static void setBillactionVOs(HashMap hasBillaction, String billtype) {
/*     */     try {
/* 575 */       IUAPQueryBS uapQry = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
/*     */ 
/* 578 */       BillactionVO condVO = new BillactionVO();
/* 579 */       condVO.setPk_billtype(billtype);
/* 580 */       Collection coRet = uapQry.retrieve(condVO, true);
/*     */ 
/* 582 */       hasBillaction.put(billtype, coRet);
/*     */     } catch (Exception ex) {
/* 584 */       Logger.error(ex.getMessage(), ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void setFunctionVOs(HashMap hmBillToFunction, String billtype) {
/*     */     try {
/* 590 */       FunctionVO condVO = new FunctionVO();
/* 591 */       condVO.setPk_billtype(billtype);
/* 592 */       Collection coRet = ((IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class)).retrieve(condVO, true);
/*     */ 
/* 594 */       hmBillToFunction.put(billtype, coRet);
/*     */     } catch (Exception ex) {
/* 596 */       Logger.error(ex.getMessage(), ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void setBillitemVOs(HashMap hasBillitemInfo, String billtype) {
/*     */     try {
/* 602 */       DefitemVO condVO = new DefitemVO();
/* 603 */       condVO.setPk_billtype(billtype);
/* 604 */       Collection coRet = ((IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class)).retrieve(condVO, true);
/*     */ 
/* 606 */       hasBillitemInfo.put(billtype, coRet);
/*     */     } catch (Exception ex) {
/* 608 */       Logger.error(ex.getMessage(), ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static LinkedHashMap getDynamicOrgReg()
/*     */   {
/* 618 */     LinkedHashMap hmCacheObj = null;
/*     */     try {
/* 620 */       hmCacheObj = (LinkedHashMap)getVersionSensitiveCache().get(DYNAMIC_ORG_REGINFO);
/* 621 */       if (hmCacheObj == null) {
/* 622 */         hmCacheObj = new LinkedHashMap();
/* 623 */         IUAPQueryBS uapBS = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
/*     */ 
/* 626 */         Collection co = uapBS.retrieveAll(DynamicOrgRegInfo.class);
/* 627 */         for (Iterator iter = co.iterator(); iter.hasNext(); ) {
/* 628 */           DynamicOrgRegInfo reg = (DynamicOrgRegInfo)iter.next();
/* 629 */           DynamicOrganizeUnitRegistry.register(reg.getOrgclass(), reg.getBuilderclass(), reg.getResolverclass(), hmCacheObj);
/*     */         }
/*     */ 
/* 634 */         getVersionSensitiveCache().put(DYNAMIC_ORG_REGINFO, hmCacheObj);
/*     */       }
/*     */     } catch (Exception ex) {
/* 637 */       Logger.warn("注册动态组织时出现错误，将无法使用动态组织=" + ex.getMessage(), ex);
/*     */     }
/* 639 */     return hmCacheObj;
/*     */   }
/*     */ 
/*     */   public static BilltobillreferVO getBillInfo(String currentBillType, String srcBillType)
/*     */   {
/* 649 */     HashMap hashCacheObj = null;
/* 650 */     String hashKey = currentBillType + srcBillType;
/*     */     try {
/* 652 */       hashCacheObj = (HashMap)getVersionSensitiveCache().get("KHHINFOHAS");
/* 653 */       if (hashCacheObj == null) {
/* 654 */         hashCacheObj = new HashMap();
/* 655 */         getBillReferInfos(hashCacheObj);
/* 656 */         getVersionSensitiveCache().put("KHHINFOHAS", hashCacheObj);
/* 657 */       } else if (!(hashCacheObj.containsKey(hashKey))) {
/* 658 */         getBillReferInfos(hashCacheObj);
/*     */       }
/*     */     } catch (Exception ex) {
/* 661 */       Logger.error(ex.getMessage(), ex);
/*     */     }
/* 663 */     return ((BilltobillreferVO)hashCacheObj.get(hashKey));
/*     */   }
/*     */ 
/*     */   public static BilltobillreferVO getBilltoBillRefer(String currentBillType, String srcBillType)
/*     */   {
/* 674 */     return getBillInfo(currentBillType, srcBillType);
/*     */   }
/*     */ 
/*     */   private static void getBillReferInfos(HashMap tmpHas)
/*     */   {
/*     */     Iterator iter;
/*     */     try
/*     */     {
/* 682 */       IUAPQueryBS name = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
/* 683 */       Collection co = name.retrieveAll(BilltobillreferVO.class);
/* 684 */       for (iter = co.iterator(); iter.hasNext(); ) {
/* 685 */         BilltobillreferVO bb = (BilltobillreferVO)iter.next();
/* 686 */         String strkey = bb.getBilltype().trim() + bb.getSourcebilltype().trim();
/* 687 */         tmpHas.put(strkey, bb);
/*     */       }
/*     */     } catch (Exception ex) {
/* 690 */       Logger.error(ex.getMessage(), ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getBillTypeByStyle(String billStyle)
/*     */   {
/* 698 */     HashMap hashCacheObj = null;
/*     */     try {
/* 700 */       hashCacheObj = (HashMap)getVersionSensitiveCache().get(STR_BILLSTYLETOTYPE);
/* 701 */       if (hashCacheObj == null) {
/* 702 */         hashCacheObj = new HashMap();
/* 703 */         setBilltypeOfStyle(hashCacheObj);
/*     */ 
/* 705 */         getVersionSensitiveCache().put(STR_BILLSTYLETOTYPE, hashCacheObj);
/* 706 */       } else if (!(hashCacheObj.containsKey(billStyle))) {
/* 707 */         setBilltypeOfStyle(hashCacheObj);
/*     */       }
/*     */     } catch (Exception ex) {
/* 710 */       Logger.error(ex.getMessage(), ex);
/*     */     }
/* 712 */     return ((String)hashCacheObj.get(billStyle));
/*     */   }
/*     */ 
/*     */   private static void setBilltypeOfStyle(HashMap hashCacheObj)
/*     */   {
/* 721 */     HashMap hashPkToBilltype = getBilltypes();
/* 722 */     Collection coBilltypes = hashPkToBilltype.values();
/*     */ 
/* 724 */     for (Iterator iter = coBilltypes.iterator(); iter.hasNext(); ) {
/* 725 */       BilltypeVO billtype = (BilltypeVO)iter.next();
/* 726 */       if ((billtype.getIsroot() != null) && (billtype.getIsroot().booleanValue()) && (billtype.getBillstyle() != null))
/*     */       {
/* 728 */         hashCacheObj.put(billtype.getBillstyle().toString(), billtype.getPrimaryKey());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static VotableVO getBillTypeToVO(String pk_billType, boolean isMain)
/*     */   {
/* 740 */     VotableVO votable = getVotableInfo(pk_billType, isMain);
/* 741 */     if (votable != null) {
/* 742 */       return votable;
/*     */     }
/* 744 */     String strTypeCode = PfUtilBaseTools.getRealBilltype(pk_billType);
/* 745 */     return getVotableInfo(strTypeCode, isMain);
/*     */   }
/*     */ 
/*     */   private static VotableVO getVotableInfo(String strTypeCode, boolean isMain)
/*     */   {
/* 755 */     String hashKey = null;
/* 756 */     if (isMain)
/* 757 */       hashKey = strTypeCode + "Y";
/*     */     else {
/* 759 */       hashKey = strTypeCode + "N";
/*     */     }
/* 761 */     HashMap hashCacheObj = null;
/*     */     try {
/* 763 */       hashCacheObj = (HashMap)getVersionSensitiveCache().get(STR_BILLTYPETOVO);
/* 764 */       if (hashCacheObj == null) {
/* 765 */         hashCacheObj = new HashMap();
/* 766 */         getVoTables(hashCacheObj);
/*     */ 
/* 768 */         getVersionSensitiveCache().put(STR_BILLTYPETOVO, hashCacheObj);
/* 769 */       } else if (!(hashCacheObj.containsKey(hashKey))) {
/* 770 */         getVoTables(hashCacheObj);
/*     */       }
/*     */     } catch (Exception ex) {
/* 773 */       Logger.error(ex.getMessage(), ex);
/*     */     }
/* 775 */     return ((VotableVO)hashCacheObj.get(hashKey));
/*     */   }
/*     */ 
/*     */   private static void getVoTables(HashMap hasVoTableInfo) throws PFBusinessException
/*     */   {
/* 784 */     String strKey = null;
/*     */     Iterator iter;
/*     */     try {
/* 786 */       IUAPQueryBS uapQry = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
/*     */ 
/* 789 */       Collection coTables = uapQry.retrieveAll(VotableVO.class);
/* 790 */       for (iter = coTables.iterator(); iter.hasNext(); ) {
/* 791 */         VotableVO votable = (VotableVO)iter.next();
/*     */ 
/* 793 */         if (votable.getHeadbodyflag().booleanValue())
/* 794 */           strKey = votable.getPk_billtype() + "Y";
/*     */         else {
/* 796 */           strKey = votable.getPk_billtype() + "N";
/*     */         }
/* 798 */         hasVoTableInfo.put(strKey, votable);
/*     */       }
/*     */     } catch (Exception ex) {
/* 801 */       Logger.error(ex.getMessage(), ex);
/* 802 */       throw new PFBusinessException("缓存单据VO对照表时错误");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static HashMap<String, DapsystemVO> getSystemtypes()
/*     */   {
/* 812 */     HashMap hashCacheObj = (HashMap)getVersionSensitiveCache().get(STR_SYSTEMTYPEINFO);
/*     */     try
/*     */     {
/* 815 */       if (hashCacheObj == null) {
/* 816 */         hashCacheObj = new HashMap();
/* 817 */         setSystemTypeVOs(hashCacheObj);
/*     */ 
/* 819 */         getVersionSensitiveCache().put(STR_SYSTEMTYPEINFO, hashCacheObj);
/*     */       }
/*     */     } catch (Exception ex) {
/* 822 */       Logger.error(ex.getMessage(), ex);
/*     */     }
/* 824 */     return hashCacheObj;
/*     */   }
/*     */ 
/*     */   public static DapsystemVO getSystemtypeVO(String systypeCode)
/*     */   {
/* 832 */     HashMap hashCacheObj = null;
/*     */     try {
/* 834 */       hashCacheObj = (HashMap)getVersionSensitiveCache().get(STR_SYSTEMTYPEINFO);
/*     */ 
/* 836 */       if (hashCacheObj == null) {
/* 837 */         hashCacheObj = new HashMap();
/* 838 */         setSystemTypeVOs(hashCacheObj);
/* 839 */         getVersionSensitiveCache().put(STR_SYSTEMTYPEINFO, hashCacheObj);
/* 840 */       } else if (!(hashCacheObj.containsKey(systypeCode))) {
/* 841 */         setSystemTypeVOs(hashCacheObj);
/*     */       }
/*     */     } catch (Exception ex) {
/* 844 */       Logger.error(ex.getMessage(), ex);
/*     */     }
/* 846 */     return ((DapsystemVO)hashCacheObj.get(systypeCode));
/*     */   }
/*     */ 
/*     */   private static void setSystemTypeVOs(HashMap hasSystemTypeInfo)
/*     */     throws PFBusinessException
/*     */   {
/*     */     Iterator iter;
/*     */     try
/*     */     {
/* 855 */       IUAPQueryBS uapQry = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
/*     */ 
/* 857 */       Collection coSystemtypes = uapQry.retrieveAll(DapsystemVO.class);
/*     */ 
/* 859 */       for (iter = coSystemtypes.iterator(); iter.hasNext(); ) {
/* 860 */         DapsystemVO systemVO = (DapsystemVO)iter.next();
/* 861 */         hasSystemTypeInfo.put(systemVO.getPrimaryKey(), systemVO);
/*     */       }
/*     */     } catch (Exception ex) {
/* 864 */       Logger.error(ex.getMessage(), ex);
/* 865 */       throw new PFBusinessException("缓存系统类型表时错误");
/*     */     }
/*     */   }
/*     */ 
/*     */   static class PfVersionMonitorFacotry
/*     */     implements VersionMonitorFactory
/*     */   {
/*     */     public ICacheVersionMonitor createVersionMonitor(Object arg0)
/*     */     {
/*  63 */       return new ObjectCacheVersionMonitor((String)arg0, 60000L);
/*     */     }
/*     */   }
/*     */ }

 