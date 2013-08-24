/*      */ package nc.vo.pf.change;
/*      */ 
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Vector;
/*      */ import nc.bs.framework.common.NCLocator;
/*      */ import nc.bs.framework.common.RuntimeEnv;
/*      */ import nc.bs.logging.Logger;
/*      */ import nc.bs.pf.change.AbstractConversion;
/*      */ import nc.bs.pf.pub.PfDataCache;
/*      */ import nc.bs.pub.mobile.PfMobileSendTask;
/*      */ import nc.bs.pub.pf.PfUtilTools;
/*      */ import nc.bs.uap.scheduler.ITaskManager;
/*      */ import nc.itf.uap.IUAPQueryBS;
/*      */ import nc.itf.uap.bd.access.IBdinfo;
/*      */ import nc.itf.uap.pf.IPFWorkflowQry;
/*      */ import nc.itf.uap.pf.metadata.IFlowBizItf;
/*      */ import nc.itf.uap.pf.metadata.IVoChangeAdjustItf;
/*      */ import nc.jdbc.framework.processor.ArrayListProcessor;
/*      */ import nc.md.model.AssociationKind;
/*      */ import nc.md.model.IBean;
/*      */ import nc.md.model.IBusinessEntity;
/*      */ import nc.md.model.ITable;
/*      */ import nc.md.model.access.javamap.AggVOStyle;
/*      */ import nc.md.model.access.javamap.IBeanStyle;
/*      */ import nc.uap.pf.metadata.PfMetadataTools;
/*      */ import nc.ui.pf.pub.PfUIDataCache;
/*      */ import nc.vo.bd.access.BdinfoVO;
/*      */ import nc.vo.graph.rule.MappingPath;
/*      */ import nc.vo.jcom.lang.StringUtil;
/*      */ import nc.vo.ml.AbstractNCLangRes;
/*      */ import nc.vo.ml.NCLangRes4VoTransl;
/*      */ import nc.vo.pf.changeui02.VotableVO;
/*      */ import nc.vo.pf.pub.BasedocTempVO;
/*      */ import nc.vo.pf.pub.BasedocVO;
/*      */ import nc.vo.pf.pub.FunctionVO;
/*      */ import nc.vo.pub.AggregatedValueObject;
/*      */ import nc.vo.pub.BusinessException;
/*      */ import nc.vo.pub.CircularlyAccessibleValueObject;
/*      */ import nc.vo.pub.billtobill.BilltobillreferVO;
/*      */ import nc.vo.pub.billtype.BilltypeVO;
/*      */ import nc.vo.pub.change.PublicHeadVO;
/*      */ import nc.vo.pub.compiler.PfParameterVO;
/*      */ import nc.vo.pub.lang.UFBoolean;
/*      */ import nc.vo.pub.lang.UFDate;
/*      */ import nc.vo.pub.lang.UFDateTime;
/*      */ import nc.vo.pub.lang.UFDouble;
/*      */ import nc.vo.pub.mobile.MobileMsg;
/*      */ import nc.vo.pub.pf.CurrencyInfo;
/*      */ import nc.vo.pub.pf.IGetBusiDataForFlow;
/*      */ import nc.vo.pub.pf.IGetBusiDataForFlow2;
/*      */ import nc.vo.pub.pf.PfUtilWorkFlowVO;
/*      */ import nc.vo.pub.workflownote.WorkflownoteVO;
/*      */ import nc.vo.uap.pf.PFBusinessException;
/*      */ import nc.vo.uap.pf.PFRuntimeException;
/*      */ import nc.vo.uap.scheduler.TaskPriority;
/*      */ 
/*      */ public class PfUtilBaseTools
/*      */ {
/*      */   public static final String PARAM_NOAPPROVE = "nosendmessage";
/*      */   public static final String PARAM_FLOWPK = "flowdefpk";
/*      */   public static final String PARAM_RELOAD_VO = "reload_vo";
/*      */   public static final String PARAM_NO_LOCK = "nolockandconsist";
/*      */   public static final String PARAM_NOTE_CHECKED = "notechecked";
/*      */   public static final String CLUSTER_MSG_HEADER_FILENAME = "fileName";
/*      */   public static final String CLUSTER_MSG_HEADER_FILETYPE = "fileType";
/*      */   public static final String CLUSTER_MSG_HEADER_PATH = "relativePath";
/*      */   public static final String CLUSTER_MSG_HEADER_MODULE = "module";
/*      */   public static final String CLUSTER_MSG_HEADER_TS = "ts";
/*      */   public static final String m_strBackChangeClassPack = "nc.bs.pf.changedir";
/*      */   public static final String m_strBackChangeClassDir = "nc/bs/pf/changedir/";
/*      */   public static final String m_strUIChangeClassPack = "nc.ui.pf.changedir";
/*      */   public static final String m_strUIChangeClassDir = "nc/ui/pf/changedir/";
/*      */   public static final String ENV_LOGINDATE = "SYSDATE";
/*      */   public static final String ENV_LOGINUSER = "SYSOPERATOR";
/*      */   public static final String ENV_LOGINCORP = "SYSCORP";
/*      */   public static final String ENV_ACCOUNTYEAR = "SYSACCOUNTYEAR";
/*      */   public static final String ENV_NOWTIME = "SYSTIME";
/*      */   public static final String ENV_DEST_BILLTYPE = "DESTBILLTYPE";
/*      */   public static final String ENV_DEST_BUSITYPE = "DESTBUSITYPE";
/*  144 */   public static final String[] ENV_CODES = { "SYSDATE", "SYSOPERATOR", "SYSCORP", "SYSACCOUNTYEAR", "SYSTIME", "DESTBILLTYPE", "DESTBUSITYPE" };
/*      */ 
/*  150 */   public static String MAIL_APPROVE_RESULT = "RESULT";
/*      */ 
/*  152 */   public static String MAIL_APPROVE_UID = "USERID";
/*      */ 
/*  154 */   public static String MAIL_SIGNED_APPROVE_UID = "SIGNED_USERID";
/*      */ 
/*  156 */   public static String MAIL_APPROVE_PWD = "PASSWORD";
/*      */ 
/*  158 */   public static String MAIL_APPROVE_BILLID = "BILLID";
/*      */ 
/*  160 */   public static String MAIL_APPROVE_CHECKNOTE = "CHECKNOTE";
/*      */ 
/*  162 */   public static String MAIL_APPROVE_BILLTYPE = "BILLTYPE";
/*      */ 
/*  164 */   public static String MAIL_APPROVE_DS = "DATASOURCE";
/*      */ 
/*  166 */   public static String MAIL_DISPATCH_ID = "DISPATCHID";
/*      */ 
/*      */   public static String[] getEnvNames()
/*      */   {
/*  177 */     return new String[] { NCLangRes4VoTransl.getNCLangRes().getStrByID("pfworkflow", "UPPpfworkflow-000605"), NCLangRes4VoTransl.getNCLangRes().getStrByID("pfworkflow", "UPPpfworkflow-000606"), NCLangRes4VoTransl.getNCLangRes().getStrByID("pfworkflow", "UPPpfworkflow-000607"), NCLangRes4VoTransl.getNCLangRes().getStrByID("common", "UC000-0000237"), NCLangRes4VoTransl.getNCLangRes().getStrByID("pfworkflow", "UPPpfworkflow-000608"), "目的单据类型", "目的业务流程" };
/*      */   }
/*      */ 
/*      */   public static boolean isSystemEnvField(String srcField)
/*      */   {
/*  194 */     return ((srcField.equals("SYSDATE")) || (srcField.equals("SYSOPERATOR")) || (srcField.equals("SYSACCOUNTYEAR")) || (srcField.equals("SYSCORP")) || (srcField.equals("SYSTIME")) || (srcField.equals("DESTBILLTYPE")) || (srcField.equals("DESTBUSITYPE")));
/*      */   }
/*      */ 
/*      */   public static Class parseTypeClass(String dataType)
/*      */   {
/*  205 */     if (dataType.toUpperCase().equals("STRING"))
/*  206 */       return String.class;
/*  207 */     if (dataType.toUpperCase().equals("UFDATE"))
/*  208 */       return UFDate.class;
/*  209 */     if (dataType.toUpperCase().equals("UFDOUBLE"))
/*  210 */       return UFDouble.class;
/*  211 */     if (dataType.toUpperCase().equals("DOUBLE"))
/*  212 */       return UFDouble.class;
/*  213 */     if (dataType.toUpperCase().equals("UFBOOLEAN"))
/*  214 */       return UFBoolean.class;
/*  215 */     if (dataType.toUpperCase().equals("BOOLEAN"))
/*  216 */       return UFBoolean.class;
/*  217 */     if (dataType.toUpperCase().equals("UFDATETIME"))
/*  218 */       return UFDateTime.class;
/*  219 */     if (dataType.toUpperCase().equals("INTEGER"))
/*  220 */       return Integer.class;
/*  221 */     if (dataType.toUpperCase().equals("ARRAYLIST"))
/*  222 */       return ArrayList.class;
/*  223 */     if (dataType.toUpperCase().equals("OBJECT"))
/*  224 */       return Object.class;
/*  225 */     if (dataType.toUpperCase().equals("STRING[]"))
/*  226 */       return java.lang.String[].class;
/*  227 */     if (dataType.toUpperCase().equals("UFDATE[]"))
/*  228 */       return nc.vo.pub.lang.UFDate[].class;
/*  229 */     if (dataType.toUpperCase().equals("UFDOUBLE[]"))
/*  230 */       return nc.vo.pub.lang.UFDouble[].class;
/*  231 */     if (dataType.toUpperCase().equals("UFBOOLEAN[]"))
/*  232 */       return nc.vo.pub.lang.UFBoolean[].class;
/*  233 */     if (dataType.toUpperCase().equals("UFDATETIME[]"))
/*  234 */       return nc.vo.pub.lang.UFDateTime[].class;
/*  235 */     if (dataType.toUpperCase().equals("INTEGER[]"))
/*  236 */       return java.lang.Integer[].class;
/*  237 */     if (dataType.toUpperCase().equals("OBJECT[]"))
/*  238 */       return java.lang.Object[].class;
/*  239 */     return null;
/*      */   }
/*      */ 
/*      */   public static UserDefineFunction[] changeFunctionVOs(ArrayList<FunctionVO> alFuncs)
/*      */   {
/*  251 */     Vector vec = new Vector();
/*      */     try {
/*  253 */       for (FunctionVO fVO : alFuncs) {
/*  254 */         if (StringUtil.isEmptyWithTrim(fVO.getFunctionnote())) {
/*      */           continue;
/*      */         }
/*  257 */         if (!(fVO.getFunctionnote().startsWith("<"))) {
/*      */           continue;
/*      */         }
/*  260 */         if ((fVO.getParameter() != null) && (fVO.getParameter().indexOf(".") > 0)) {
/*      */           continue;
/*      */         }
/*  263 */         UserDefineFunction temp = new UserDefineFunction();
/*  264 */         temp.setClassName(fVO.getClassname());
/*  265 */         temp.setMethodName(fVO.getMethod());
/*  266 */         temp.setFunctionNote(fVO.getFunctionnote());
/*  267 */         temp.setReturnType(parseTypeClass(fVO.getReturntype()));
/*      */ 
/*  269 */         String[] par = getParameters(fVO);
/*  270 */         if (par != null) {
/*  271 */           Class[] classes = new Class[par.length];
/*  272 */           String[] argNames = new String[par.length];
/*  273 */           for (int j = 0; j < par.length; ++j) {
/*  274 */             classes[j] = parseTypeClass(par[j].substring(par[j].indexOf(":") + 1));
/*  275 */             argNames[j] = par[j].substring(0, par[j].indexOf(":"));
/*      */           }
/*  277 */           temp.setArgNames(argNames);
/*  278 */           temp.setArgTypes(classes);
/*      */         }
/*      */ 
/*  281 */         vec.addElement(temp);
/*      */       }
/*      */     }
/*      */     catch (Throwable e) {
/*  285 */       Logger.error(e.getMessage(), e);
/*      */     }
/*      */ 
/*  288 */     UserDefineFunction[] functions = new UserDefineFunction[vec.size()];
/*  289 */     vec.copyInto(functions);
/*  290 */     return functions;
/*      */   }
/*      */ 
/*      */   public static String[] getParameters(FunctionVO fVO) {
/*  294 */     if ((fVO == null) || (fVO.getParameter() == null)) {
/*  295 */       return null;
/*      */     }
/*  297 */     String str = fVO.getParameter();
/*  298 */     if ((str.length() == 0) || (str.indexOf(".") > 0)) {
/*  299 */       return null;
/*      */     }
/*  301 */     Vector vec = new Vector();
/*  302 */     while (str.indexOf(",") >= 0) {
/*  303 */       vec.addElement(str.substring(0, str.indexOf(",")));
/*  304 */       str = str.substring(str.indexOf(",") + 1);
/*      */     }
/*  306 */     vec.addElement(str);
/*      */ 
/*  308 */     String[] res = new String[vec.size()];
/*  309 */     vec.copyInto(res);
/*  310 */     return res;
/*      */   }
/*      */ 
/*      */   public static String getEnvNameByCode(String env_code)
/*      */   {
/*  320 */     String[] names = getEnvNames();
/*  321 */     for (int i = 0; i < ENV_CODES.length; ++i) {
/*  322 */       if (ENV_CODES[i].equals(env_code)) return names[i];
/*      */     }
/*  324 */     return null;
/*      */   }
/*      */ 
/*      */   public static void getVariableValue(String billType, String actionName, String currentDate, AggregatedValueObject billvo, AggregatedValueObject[] billvos, Object userObj, Object[] userObjs, PfUtilWorkFlowVO workFlow, Hashtable hashBilltypeToParavo)
/*      */   {
/*  334 */     Logger.debug(">>>getVariableValue(" + actionName + "," + billType + ") START<<<");
/*      */ 
/*  336 */     PfParameterVO paraVo = new PfParameterVO();
/*  337 */     paraVo.m_billType = billType;
/*      */ 
/*  340 */     AggregatedValueObject singleVO = null;
/*  341 */     if (billvo != null)
/*  342 */       singleVO = billvo;
/*  343 */     else if ((billvos != null) && (billvos.length > 0)) {
/*  344 */       singleVO = billvos[0];
/*      */     }
/*  346 */     PublicHeadVO standHeadVo = fetchHeadVO(singleVO, billType);
/*      */ 
/*  349 */     if (StringUtil.isEmptyWithTrim(standHeadVo.businessType))
/*  350 */       paraVo.m_businessType = "KHHH0000000000000001";
/*      */     else {
/*  352 */       paraVo.m_businessType = standHeadVo.businessType;
/*      */     }
/*  354 */     paraVo.m_billNo = standHeadVo.billNo;
/*  355 */     paraVo.m_billId = standHeadVo.pkBillId;
/*  356 */     paraVo.m_coId = standHeadVo.corpId;
/*  357 */     paraVo.m_currentDate = currentDate;
/*  358 */     paraVo.m_makeBillOperator = standHeadVo.operatorId;
/*  359 */     paraVo.m_preValueVo = billvo;
/*  360 */     paraVo.m_preValueVos = billvos;
/*  361 */     paraVo.m_standHeadVo = standHeadVo;
/*  362 */     paraVo.m_userObj = userObj;
/*  363 */     paraVo.m_userObjs = userObjs;
/*  364 */     paraVo.m_workFlow = workFlow;
/*      */ 
/*  366 */     if (actionName.length() > 20) {
/*  367 */       paraVo.m_actionName = actionName.substring(0, actionName.length() - 20);
/*  368 */       paraVo.m_operator = actionName.substring(actionName.length() - 20);
/*      */     } else {
/*  370 */       paraVo.m_actionName = actionName;
/*  371 */       paraVo.m_operator = standHeadVo.approveId;
/*      */     }
/*      */ 
/*  374 */     if (StringUtil.isEmptyWithTrim(paraVo.m_operator)) {
/*  375 */       paraVo.m_operator = paraVo.m_makeBillOperator;
/*      */     }
/*      */ 
/*  378 */     Logger.debug(">>>billType=" + paraVo.m_billType + " busiType=" + paraVo.m_businessType);
/*  379 */     Logger.debug(">>>billMaker=" + paraVo.m_makeBillOperator + " operator=" + paraVo.m_operator);
/*  380 */     Logger.debug(">>>corpPK=" + paraVo.m_coId + " billId=" + paraVo.m_billId);
/*  381 */     Logger.debug(">>>actionName=" + paraVo.m_actionName + " billNo=" + paraVo.m_billNo);
/*      */ 
/*  384 */     hashBilltypeToParavo.put(billType, paraVo);
/*      */ 
/*  387 */     Logger.debug(">>>getVariableValue(" + actionName + "," + billType + ") END<<<");
/*      */   }
/*      */ 
/*      */   private static void getHeadInfoByVotable(PublicHeadVO headvo, AggregatedValueObject billvo, String billtype)
/*      */   {
/*  399 */     headvo.billType = billtype;
/*      */ 
/*  401 */     CircularlyAccessibleValueObject parentVO = billvo.getParentVO();
/*  402 */     VotableVO headVt = null;
/*  403 */     if (RuntimeEnv.getInstance().isRunningInServer())
/*  404 */       headVt = PfDataCache.getBillTypeToVO(billtype, true);
/*      */     else
/*  406 */       headVt = PfUIDataCache.getBillTypeToVO(billtype, true);
/*  407 */     if (headVt == null)
/*  408 */       throw new PFRuntimeException("该单据类型没有注册VO对照信息");
/*  409 */     if (parentVO == null) {
/*  410 */       throw new PFRuntimeException("传入的单据聚合VO无主表");
/*      */     }
/*      */ 
/*  413 */     if (headVt.getApproveid() != null) {
/*  414 */       headvo.approveId = ((String)parentVO.getAttributeValue(headVt.getApproveid()));
/*      */     }
/*  416 */     if (headVt.getBillno() != null) {
/*  417 */       headvo.billNo = ((String)parentVO.getAttributeValue(headVt.getBillno()));
/*      */     }
/*  419 */     if (headVt.getBusitype() != null) {
/*  420 */       headvo.businessType = ((String)parentVO.getAttributeValue(headVt.getBusitype()));
/*      */     }
/*  422 */     headvo.corpId = ((String)parentVO.getAttributeValue("pk_corp"));
/*  423 */     if (headvo.corpId == null) {
/*  424 */       headvo.corpId = ((String)parentVO.getAttributeValue("dwbm"));
/*      */     }
/*  426 */     if (headVt.getOperator() != null) {
/*  427 */       headvo.operatorId = ((String)parentVO.getAttributeValue(headVt.getOperator()));
/*      */     }
/*  429 */     if (headVt.getBillid() != null) {
/*  430 */       headvo.pkBillId = ((String)parentVO.getAttributeValue(headVt.getBillid()));
/*      */     }
/*  432 */     headvo.billIdField = headVt.getBillid();
/*      */   }
/*      */ 
/*      */   private static void getHeadInfoByMeta(PublicHeadVO headvo, Object singleBillEntity, String billType)
/*      */   {
/*  445 */     headvo.billType = billType;
/*      */ 
/*  447 */     IFlowBizItf fbi = (IFlowBizItf)PfMetadataTools.getBizItfImpl(singleBillEntity, IFlowBizItf.class);
/*  448 */     if (fbi == null) {
/*  449 */       throw new PFRuntimeException("单据实体没有提供业务接口IFlowBizInf的实现类");
/*      */     }
/*  451 */     headvo.approveId = fbi.getApprover();
/*  452 */     headvo.billNo = fbi.getBillNo();
/*  453 */     headvo.businessType = fbi.getBusitype();
/*  454 */     headvo.corpId = fbi.getCorpId();
/*  455 */     headvo.operatorId = fbi.getBillMaker();
/*  456 */     headvo.pkBillId = fbi.getBillId();
/*      */ 
/*  458 */     headvo.billIdField = fbi.getColumnName("billid");
/*      */   }
/*      */ 
/*      */   public static void fetchBillId(PfParameterVO paravo, Object singleBillEntity, Object retObj)
/*      */   {
/*  469 */     paravo.m_billId = getBillID(retObj);
/*      */ 
/*  474 */     PublicHeadVO standHeadVo = fetchHeadVO((AggregatedValueObject)singleBillEntity, paravo.m_billType);
/*      */ 
/*  476 */     paravo.m_billNo = standHeadVo.billNo;
/*  477 */     if (paravo.m_billId != null)
/*      */       return;
/*  479 */     paravo.m_billId = standHeadVo.pkBillId;
/*      */   }
/*      */ 
/*      */   public static PublicHeadVO fetchHeadVO(AggregatedValueObject billVO, String billType) {
/*  483 */     PublicHeadVO standHeadVo = new PublicHeadVO();
/*  484 */     if (PfMetadataTools.isBilltypeRelatedMeta(billType))
/*      */     {
/*  486 */       getHeadInfoByMeta(standHeadVo, billVO, billType);
/*      */     }
/*      */     else {
/*  489 */       getHeadInfoByVotable(standHeadVo, billVO, billType);
/*      */     }
/*      */ 
/*  492 */     return standHeadVo;
/*      */   }
/*      */ 
/*      */   private static String getBillID(Object tmpObj)
/*      */   {
/*  502 */     Logger.debug("****从动作脚本执行后的返回值中获得单据Id开始****");
/*  503 */     String billId = null;
/*  504 */     if (tmpObj == null) return billId;
/*      */ 
/*  506 */     if (tmpObj instanceof ArrayList) {
/*  507 */       ArrayList alRetObj = (ArrayList)tmpObj;
/*  508 */       Object[] retObjAry = alRetObj.toArray();
/*  509 */       if ((retObjAry.length > 1) && (retObjAry[1] instanceof ArrayList)) {
/*  510 */         billId = ((ArrayList)retObjAry[1]).toArray()[0].toString();
/*  511 */         Logger.debug("获得单据Id:Array的第一维的数组的第0维为单据ID");
/*      */       }
/*  513 */       else if ((retObjAry[0] != null) && (!(retObjAry[0].equals("")))) {
/*  514 */         billId = retObjAry[0].toString();
/*  515 */         Logger.debug("获得单据Id:Array的第0维为单据ID");
/*      */       }
/*      */     }
/*  518 */     else if (tmpObj instanceof String) {
/*  519 */       billId = (String)tmpObj;
/*  520 */       Logger.debug("直接从对象String取的ID");
/*      */     }
/*  522 */     Logger.debug("****从动作脚本执行后的返回值中获得单据Id=" + billId + "结束****");
/*  523 */     return billId;
/*      */   }
/*      */ 
/*      */   public static BasedocVO[] getAllBasedocVO()
/*      */   {
/*  533 */     BdinfoVO[] bdinfovos = null;
/*      */     try {
/*  535 */       IBdinfo name = (IBdinfo)NCLocator.getInstance().lookup(IBdinfo.class.getName());
/*  536 */       bdinfovos = name.queryAllBdinfoVOs(null);
/*      */     } catch (Exception e) {
/*  538 */       Logger.error(e.getMessage(), e);
/*      */     }
/*      */ 
/*  542 */     if (bdinfovos == null)
/*  543 */       return null;
/*  544 */     BasedocTempVO[] vos = new BasedocTempVO[bdinfovos.length];
/*  545 */     for (int i = 0; i < vos.length; ++i) {
/*  546 */       vos[i] = new BasedocTempVO();
/*  547 */       vos[i].setDocCode(bdinfovos[i].getBdcode());
/*  548 */       vos[i].setDocName(bdinfovos[i].getBdname());
/*  549 */       vos[i].setDocPK(bdinfovos[i].getPrimaryKey());
/*  550 */       vos[i].setRefNodeName(bdinfovos[i].getRefnodename());
/*      */ 
/*  552 */       UFBoolean isDef = bdinfovos[i].getIsdef();
/*  553 */       if (isDef != null) {
/*  554 */         vos[i].setDef(isDef.booleanValue());
/*      */       }
/*      */ 
/*  557 */       UFBoolean isSelfRef = bdinfovos[i].getIsselfref();
/*  558 */       if (isSelfRef != null) {
/*  559 */         vos[i].setSelfRef(isSelfRef.booleanValue());
/*      */       }
/*      */ 
/*  562 */       vos[i].setPkDef(bdinfovos[i].getPk_defdef());
/*  563 */       vos[i].setRefModelClass(bdinfovos[i].getSelfrefclass());
/*      */     }
/*      */ 
/*  756 */     return vos;
/*      */   }
/*      */ 
/*      */   public static AggregatedValueObject pfInitVoClass(String billType, int iChildLength)
/*      */     throws BusinessException
/*      */   {
/*  770 */     String[] billInfo = getStrBillVo(billType);
/*  771 */     String strBillVoClassName = billInfo[0];
/*  772 */     String strHeadVoClassName = billInfo[1];
/*  773 */     String strItemVoClassName = billInfo[2];
/*      */ 
/*  775 */     AggregatedValueObject retObj = pfInitVo(strBillVoClassName, strHeadVoClassName, strItemVoClassName, iChildLength);
/*      */ 
/*  778 */     return retObj;
/*      */   }
/*      */ 
/*      */   public static String[] getStrBillVo(String pkBillType)
/*      */     throws BusinessException
/*      */   {
/*  788 */     String[] retStrs = new String[3];
/*  789 */     if (PfMetadataTools.isBilltypeRelatedMeta(pkBillType))
/*      */     {
/*  791 */       IBusinessEntity be = PfMetadataTools.queryMetaOfBilltype(pkBillType);
/*  792 */       IBeanStyle bs = be.getBeanStyle();
/*  793 */       if (bs instanceof AggVOStyle)
/*  794 */         retStrs[0] = ((AggVOStyle)bs).getAggVOClassName();
/*      */       else
/*  796 */         throw new PFBusinessException("单据实体必须符合聚合VO的样式");
/*  797 */       retStrs[1] = be.getFullClassName();
/*      */ 
/*  799 */       List relatedBeans = be.getRelatedEntities(AssociationKind.Composite, 2);
/*      */ 
/*  803 */       IBean relatedBean = (relatedBeans.size() > 0) ? (IBean)relatedBeans.iterator().next() : null;
/*  804 */       retStrs[2] = ((relatedBean == null) ? null : relatedBean.getFullClassName());
/*      */     }
/*      */     else {
/*  807 */       VotableVO headVt = null;
/*  808 */       VotableVO bodyVt = null;
/*  809 */       if (RuntimeEnv.getInstance().isRunningInServer()) {
/*  810 */         headVt = PfDataCache.getBillTypeToVO(pkBillType, true);
/*  811 */         bodyVt = PfDataCache.getBillTypeToVO(pkBillType, false);
/*      */       } else {
/*  813 */         headVt = PfUIDataCache.getBillTypeToVO(pkBillType, true);
/*  814 */         bodyVt = PfUIDataCache.getBillTypeToVO(pkBillType, false);
/*      */       }
/*  816 */       if (headVt == null)
/*  817 */         throw new PFRuntimeException("该单据类型没有注册VO对照信息");
/*  818 */       retStrs[0] = headVt.getBillvo();
/*  819 */       retStrs[1] = headVt.getHeaditemvo();
/*  820 */       retStrs[2] = ((bodyVt == null) ? null : bodyVt.getHeaditemvo());
/*      */     }
/*  822 */     return retStrs;
/*      */   }
/*      */ 
/*      */   public static AggregatedValueObject[] pfInitVosClass(String billType, AggregatedValueObject[] sourceBillVOs)
/*      */     throws BusinessException
/*      */   {
/*  836 */     String[] billInfo = getStrBillVo(billType);
/*  837 */     String strBillVoClassName = billInfo[0];
/*      */ 
/*  840 */     AggregatedValueObject[] retVos = pfInitVos(strBillVoClassName, sourceBillVOs.length);
/*      */ 
/*  842 */     for (int i = 0; i < sourceBillVOs.length; ++i) {
/*  843 */       CircularlyAccessibleValueObject[] bodyVOs = sourceBillVOs[i].getChildrenVO();
/*  844 */       retVos[i] = pfInitVoClass(billType, (bodyVOs == null) ? 0 : bodyVOs.length);
/*      */     }
/*  846 */     return retVos;
/*      */   }
/*      */ 
/*      */   public static AggregatedValueObject pfInitVo(String strBillVoClassName, String strHeadVoClassName, String strItemVoClassName, int iChildLength)
/*      */     throws BusinessException
/*      */   {
/*  862 */     AggregatedValueObject billVo = null;
/*  863 */     CircularlyAccessibleValueObject headVo = null;
/*  864 */     CircularlyAccessibleValueObject[] itemVos = null;
/*      */     try
/*      */     {
/*  867 */       Object o = Class.forName(strBillVoClassName).newInstance();
/*  868 */       billVo = (AggregatedValueObject)o;
/*      */ 
/*  871 */       o = Class.forName(strHeadVoClassName).newInstance();
/*  872 */       headVo = (CircularlyAccessibleValueObject)o;
/*      */ 
/*  875 */       if ((iChildLength > 0) && (strItemVoClassName != null)) {
/*  876 */         itemVos = (CircularlyAccessibleValueObject[])(CircularlyAccessibleValueObject[])Array.newInstance(Class.forName(strItemVoClassName), iChildLength);
/*      */ 
/*  878 */         for (int i = 0; i < itemVos.length; ++i) {
/*  879 */           itemVos[i] = ((CircularlyAccessibleValueObject)Class.forName(strItemVoClassName).newInstance());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  884 */       billVo.setParentVO(headVo);
/*      */ 
/*  886 */       billVo.setChildrenVO(itemVos);
/*      */     }
/*      */     catch (Exception e) {
/*  889 */       Logger.error(e.getMessage(), e);
/*  890 */       throw new PFBusinessException(e.getMessage());
/*      */     }
/*  892 */     return billVo;
/*      */   }
/*      */ 
/*      */   public static AggregatedValueObject[] pfInitVos(String strBillVoClassName, int voLen)
/*      */     throws BusinessException
/*      */   {
/*      */     AggregatedValueObject[] retVos;
/*      */     try
/*      */     {
/*  907 */       retVos = (AggregatedValueObject[])(AggregatedValueObject[])Array.newInstance(Class.forName(strBillVoClassName), voLen);
/*      */     }
/*      */     catch (Exception e) {
/*  910 */       throw new PFBusinessException(e.getMessage(), e);
/*      */     }
/*  912 */     return retVos;
/*      */   }
/*      */ 
/*      */   public static Object[] pfInitObjects(String strClassName, int aryLen)
/*      */     throws BusinessException
/*      */   {
/*      */     Object[] retVos;
/*      */     try
/*      */     {
/*  925 */       retVos = (Object[])(Object[])Array.newInstance(Class.forName(strClassName), aryLen);
/*      */     } catch (Exception e) {
/*  927 */       throw new PFBusinessException(e.getMessage(), e);
/*      */     }
/*  929 */     return retVos;
/*      */   }
/*      */ 
/*      */   protected static void adjustBeforeVoMapping(String srcBilltype, String destBilltype, AggregatedValueObject[] sourceBillVOs)
/*      */   {
/*  941 */     boolean hasMeta = PfMetadataTools.isBilltypeRelatedMeta(srcBilltype);
/*      */ 
/*  943 */     for (int i = 0; i < ((sourceBillVOs == null) ? 0 : sourceBillVOs.length); ++i)
/*  944 */       if (hasMeta)
/*      */       {
/*  946 */         IVoChangeAdjustItf srcChangeItf = (IVoChangeAdjustItf)PfMetadataTools.getBizItfImpl(sourceBillVOs[i], IVoChangeAdjustItf.class);
/*      */ 
/*  948 */         if (srcChangeItf != null)
/*  949 */           srcChangeItf.adjustSourceVoBeforeChange(destBilltype);
/*  950 */       } else if (sourceBillVOs[i] instanceof ISourceChangeVO) {
/*  951 */         ISourceChangeVO srcChangeItf = (ISourceChangeVO)sourceBillVOs[i];
/*  952 */         if (srcChangeItf != null)
/*  953 */           srcChangeItf.adjustBeforeChange(destBilltype);
/*      */       }
/*      */   }
/*      */ 
/*      */   protected static void adjustAfterVoMapping(String sourceBillType, String destBilltype, AggregatedValueObject[] sourceBillVOs, AggregatedValueObject[] destBillVOs)
/*      */   {
/*  968 */     boolean srcHasMeta = PfMetadataTools.isBilltypeRelatedMeta(sourceBillType);
/*  969 */     boolean destHasMeta = PfMetadataTools.isBilltypeRelatedMeta(destBilltype);
/*      */ 
/*  971 */     for (int i = 0; i < ((sourceBillVOs == null) ? 0 : sourceBillVOs.length); ++i) {
/*  972 */       if (srcHasMeta)
/*      */       {
/*  974 */         IVoChangeAdjustItf srcChangeItf = (IVoChangeAdjustItf)PfMetadataTools.getBizItfImpl(sourceBillVOs[i], IVoChangeAdjustItf.class);
/*      */ 
/*  976 */         if (srcChangeItf != null)
/*  977 */           srcChangeItf.adjustSourceVoAfterChange(sourceBillType);
/*  978 */       } else if (sourceBillVOs[i] instanceof ISourceChangeVO) {
/*  979 */         ISourceChangeVO srcChangeItf = (ISourceChangeVO)sourceBillVOs[i];
/*  980 */         if (srcChangeItf != null) {
/*  981 */           srcChangeItf.adjustAfterChange(destBilltype);
/*      */         }
/*      */       }
/*      */     }
/*  985 */     for (int i = 0; i < ((destBillVOs == null) ? 0 : destBillVOs.length); ++i)
/*  986 */       if (destHasMeta)
/*      */       {
/*  988 */         IVoChangeAdjustItf destChangeItf = (IVoChangeAdjustItf)PfMetadataTools.getBizItfImpl(destBillVOs[i], IVoChangeAdjustItf.class);
/*      */ 
/*  990 */         if (destChangeItf != null)
/*  991 */           destChangeItf.adjustTargetVoAfterChange(sourceBillType);
/*  992 */       } else if (destBillVOs[i] instanceof ITargetChangeVO) {
/*  993 */         ITargetChangeVO destChangeItf = (ITargetChangeVO)destBillVOs[i];
/*  994 */         if (destChangeItf != null)
/*  995 */           destChangeItf.adjustAfterChange(sourceBillType);
/*      */       }
/*      */   }
/*      */ 
/*      */   public static void sendSMS(String[] userIds, String content)
/*      */     throws BusinessException
/*      */   {
/* 1011 */     String[] targetPhones = PfUtilTools.fetchPhonesByUserId(userIds);
/*      */ 
/* 1013 */     MobileMsg mm = new MobileMsg();
/* 1014 */     mm.setTargetPhones(targetPhones);
/* 1015 */     mm.setMsg(content);
/*      */ 
/* 1017 */     sendSMS(mm);
/*      */   }
/*      */ 
/*      */   public static void sendSMS(MobileMsg mobileVO)
/*      */     throws BusinessException
/*      */   {
/* 1030 */     Logger.info("========================");
/* 1031 */     Logger.info(">> 使用调度引擎异步发送短信");
/* 1032 */     Logger.info("========================");
/* 1033 */     String[] targetPhones = mobileVO.getTargetPhones();
/* 1034 */     if ((targetPhones == null) || (targetPhones.length == 0)) {
/* 1035 */       Logger.warn(">>找不到手机号，无法发送短信，返回");
/* 1036 */       return;
/*      */     }
/*      */ 
/* 1040 */     ITaskManager tmgr = (ITaskManager)NCLocator.getInstance().lookup(ITaskManager.class.getName());
/* 1041 */     PfMobileSendTask task = new PfMobileSendTask(mobileVO);
/* 1042 */     tmgr.add(task, TaskPriority.GENERAL);
/*      */   }
/*      */ 
/*      */   public static BilltypeVO[] getTransTypesOfBilltype(String strBilltype)
/*      */   {
/* 1052 */     ArrayList alRet = new ArrayList();
/* 1053 */     Collection coBilltype = null;
/* 1054 */     if (RuntimeEnv.getInstance().isRunningInServer())
/* 1055 */       coBilltype = PfDataCache.getBilltypes().values();
/*      */     else
/* 1057 */       coBilltype = PfUIDataCache.getBilltypes().values();
/* 1058 */     for (Iterator iterator = coBilltype.iterator(); iterator.hasNext(); ) {
/* 1059 */       BilltypeVO btVO = (BilltypeVO)iterator.next();
/* 1060 */       UFBoolean isTransaction = btVO.getIstransaction();
/* 1061 */       if ((strBilltype.equals(btVO.getParentbilltype())) && (isTransaction != null) && (isTransaction.booleanValue()))
/*      */       {
/* 1063 */         alRet.add(btVO); }
/*      */     }
/* 1065 */     return ((BilltypeVO[])alRet.toArray(new BilltypeVO[0]));
/*      */   }
/*      */ 
/*      */   public static boolean fetchMoneyInfo(Object billEntity, CurrencyInfo cinfo, String billtype)
/*      */   {
/* 1076 */     if (PfMetadataTools.isBilltypeRelatedMeta(billtype))
/*      */     {
/* 1078 */       IGetBusiDataForFlow2 gff2 = (IGetBusiDataForFlow2)PfMetadataTools.getBizItfImpl(billEntity, IGetBusiDataForFlow2.class);
/*      */ 
/* 1080 */       if (gff2 == null)
/*      */       {
/* 1082 */         Logger.warn("该单据类型关联了元数据组件，但没有实现IGetBusiDataForFlow2业务接口");
/* 1083 */         UFDouble zeroMny = new UFDouble(0);
/* 1084 */         cinfo.setMoney(zeroMny);
/* 1085 */         cinfo.setLocalMoney(zeroMny);
/* 1086 */         cinfo.setAssMoney(zeroMny);
/* 1087 */         cinfo.setCurrency(null);
/* 1088 */         cinfo.setPreMoney(zeroMny);
/* 1089 */         cinfo.setPreLocalMoney(zeroMny);
/* 1090 */         cinfo.setPreAssMoney(zeroMny);
/* 1091 */         return false;
/*      */       }
/* 1093 */       UFDouble mny = gff2.getPfMoney();
/* 1094 */       UFDouble localMny = gff2.getPfLocalMoney();
/* 1095 */       UFDouble assMny = gff2.getPfAssMoney();
/* 1096 */       cinfo.setMoney(mny);
/* 1097 */       cinfo.setLocalMoney(localMny);
/* 1098 */       cinfo.setAssMoney(assMny);
/* 1099 */       cinfo.setCurrency(gff2.getPfCurrency());
/* 1100 */       cinfo.setPreMoney(mny);
/* 1101 */       cinfo.setPreLocalMoney(localMny);
/* 1102 */       cinfo.setPreAssMoney(assMny);
/* 1103 */       cinfo.setPriority(gff2.getPriority());
/* 1104 */       cinfo.setShowMoney(true);
/* 1105 */       return true;
/*      */     }
/* 1107 */     if (billEntity instanceof IGetBusiDataForFlow2)
/*      */     {
/* 1109 */       IGetBusiDataForFlow2 gff2 = (IGetBusiDataForFlow2)billEntity;
/* 1110 */       UFDouble mny = gff2.getPfMoney();
/* 1111 */       UFDouble localMny = gff2.getPfLocalMoney();
/* 1112 */       UFDouble assMny = gff2.getPfAssMoney();
/* 1113 */       cinfo.setMoney(mny);
/* 1114 */       cinfo.setLocalMoney(localMny);
/* 1115 */       cinfo.setAssMoney(assMny);
/* 1116 */       cinfo.setCurrency(gff2.getPfCurrency());
/* 1117 */       cinfo.setPreMoney(mny);
/* 1118 */       cinfo.setPreLocalMoney(localMny);
/* 1119 */       cinfo.setPreAssMoney(assMny);
/* 1120 */       cinfo.setPriority(gff2.getPriority());
/* 1121 */       cinfo.setShowMoney(true);
/* 1122 */       return true; }
/* 1123 */     if (billEntity instanceof IGetBusiDataForFlow)
/*      */     {
/* 1125 */       IGetBusiDataForFlow gff = (IGetBusiDataForFlow)billEntity;
/* 1126 */       UFDouble mny = gff.getPfMoney();
/* 1127 */       UFDouble localMny = gff.getPfLocalMoney();
/* 1128 */       UFDouble assMny = gff.getPfAssMoney();
/* 1129 */       cinfo.setMoney(mny);
/* 1130 */       cinfo.setLocalMoney(localMny);
/* 1131 */       cinfo.setAssMoney(assMny);
/* 1132 */       cinfo.setCurrency(gff.getPfCurrency());
/* 1133 */       cinfo.setPreMoney(mny);
/* 1134 */       cinfo.setPreLocalMoney(localMny);
/* 1135 */       cinfo.setPreAssMoney(assMny);
/* 1136 */       cinfo.setShowMoney(true);
/* 1137 */       return true;
/*      */     }
/* 1139 */     UFDouble zeroMny = new UFDouble(0);
/* 1140 */     cinfo.setMoney(zeroMny);
/* 1141 */     cinfo.setLocalMoney(zeroMny);
/* 1142 */     cinfo.setAssMoney(zeroMny);
/* 1143 */     cinfo.setCurrency(null);
/* 1144 */     cinfo.setPreMoney(zeroMny);
/* 1145 */     cinfo.setPreLocalMoney(zeroMny);
/* 1146 */     cinfo.setPreAssMoney(zeroMny);
/* 1147 */     return false;
/*      */   }
/*      */ 
/*      */   public static String queryAllCheckers(String billId)
/*      */   {
/* 1158 */     IPFWorkflowQry wfQry = (IPFWorkflowQry)NCLocator.getInstance().lookup(IPFWorkflowQry.class.getName());
/*      */ 
/* 1160 */     String strAllCheckers = "";
/*      */     try {
/* 1162 */       WorkflownoteVO[] noteVOs = wfQry.queryAlreadyCheckInfo(billId, null);
/* 1163 */       for (int i = 0; i < ((noteVOs == null) ? 0 : noteVOs.length); ++i) {
/* 1164 */         strAllCheckers = strAllCheckers + noteVOs[i].getCheckname() + ",";
/*      */       }
/* 1166 */       if (strAllCheckers.length() > 0) {
/* 1167 */         strAllCheckers = strAllCheckers.substring(0, strAllCheckers.length() - 1);
/*      */       }
/* 1169 */       Logger.debug(">>获得单据ID" + billId + "的所有审批人=" + strAllCheckers);
/*      */     } catch (BusinessException e) {
/* 1171 */       Logger.error(e.getMessage(), e);
/*      */     }
/* 1173 */     return strAllCheckers;
/*      */   }
/*      */ 
/*      */   public static boolean isValueChanged(Object oldValue, Object newValue)
/*      */   {
/* 1184 */     boolean isChange = false;
/* 1185 */     if ((oldValue != null) && (!(oldValue.equals(newValue))))
/* 1186 */       isChange = true;
/* 1187 */     if ((newValue != null) && (!(newValue.equals(oldValue)))) {
/* 1188 */       isChange = true;
/*      */     }
/* 1190 */     return isChange;
/*      */   }
/*      */ 
/*      */   public static String findPkField(String billType)
/*      */     throws BusinessException
/*      */   {
/* 1200 */     if (PfMetadataTools.isBilltypeRelatedMeta(billType))
/*      */     {
/* 1202 */       IBean bean = PfMetadataTools.queryMetaOfBilltype(billType);
/* 1203 */       return bean.getTable().getPrimaryKeyName();
/*      */     }
/*      */ 
/* 1206 */     VotableVO headVt = null;
/* 1207 */     if (RuntimeEnv.getInstance().isRunningInServer())
/* 1208 */       headVt = PfDataCache.getBillTypeToVO(billType, true);
/*      */     else
/* 1210 */       headVt = PfUIDataCache.getBillTypeToVO(billType, true);
/* 1211 */     if (headVt == null)
/* 1212 */       throw new PFRuntimeException("该单据类型" + billType + "没有注册VO对照信息");
/* 1213 */     return headVt.getPkfield();
/*      */   }
/*      */ 
/*      */   public static String pathFromBillitem(String strBillitem)
/*      */   {
/* 1223 */     boolean isHead = AbstractConversion.isParentField(strBillitem);
/* 1224 */     String attrName = AbstractConversion.getFieldName(strBillitem);
/*      */ 
/* 1226 */     return "/" + ((isHead) ? "H_" : "B_") + "/" + attrName;
/*      */   }
/*      */ 
/*      */   public static String pathFromMetadata(String strAttrname)
/*      */   {
/* 1235 */     return "/" + strAttrname.replace('.', '/');
/*      */   }
/*      */ 
/*      */   public static String pathToBillitem(MappingPath mp)
/*      */   {
/* 1244 */     String[] tokens = mp.getPathTokens();
/* 1245 */     StringBuffer sb = new StringBuffer();
/* 1246 */     for (int i = 0; i < tokens.length; ++i)
/* 1247 */       sb.append(tokens[i]);
/* 1248 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public static String pathToMetadata(MappingPath mp)
/*      */   {
/* 1257 */     String accessPath = mp.getAccessPath();
/* 1258 */     String path = accessPath;
/* 1259 */     if (accessPath.startsWith("/"))
/* 1260 */       path = path.substring(1);
/* 1261 */     return path.replace('/', '.');
/*      */   }
/*      */ 
/*      */   public static String getRealBilltype(String strTypeCode)
/*      */   {
/* 1271 */     String strEditBilltype = strTypeCode.trim();
/*      */ 
/* 1273 */     BilltypeVO btVO = getBilltypeVOByCode(strEditBilltype);
/* 1274 */     if (btVO == null)
/* 1275 */       throw new PFRuntimeException("错误：流程平台缓存中不存在该单据类型=" + strEditBilltype);
/* 1276 */     if ((btVO.getIstransaction() != null) && (btVO.getIstransaction().booleanValue()))
/*      */     {
/* 1278 */       strEditBilltype = btVO.getParentbilltype();
/*      */     }
/* 1280 */     Logger.debug("获得单据类型或交易类型=" + strTypeCode + "的真实单据类型=" + strEditBilltype);
/* 1281 */     return strEditBilltype;
/*      */   }
/*      */ 
/*      */   public static boolean isTranstype(String strTypeCode)
/*      */   {
/* 1291 */     BilltypeVO btVO = getBilltypeVOByCode(strTypeCode);
/*      */ 
/* 1293 */     return ((btVO.getIstransaction() != null) && (btVO.getIstransaction().booleanValue()));
/*      */   }
/*      */ 
/*      */   public static BilltypeVO getBilltypeVOByCode(String strCode)
/*      */   {
/* 1303 */     BilltypeVO btVO = null;
/* 1304 */     if (RuntimeEnv.getInstance().isRunningInServer())
/* 1305 */       btVO = PfDataCache.getBillTypeInfo(strCode);
/*      */     else
/* 1307 */       btVO = PfUIDataCache.getBillTypeInfo(strCode); PfUIDataCache.getBilltypes();
/* 1308 */     return btVO;
/*      */   }
/*      */ 
/*      */   public static BilltobillreferVO findBilltobill(String destBilltype, String srcBilltype)
/*      */   {
/* 1319 */     BilltobillreferVO billtobillVO = PfDataCache.getBillInfo(destBilltype, srcBilltype);
/* 1320 */     if ((billtobillVO == null) && ((
/* 1321 */       (isTranstype(destBilltype)) || (isTranstype(srcBilltype))))) {
/* 1322 */       billtobillVO = PfDataCache.getBillInfo(getRealBilltype(destBilltype), getRealBilltype(srcBilltype));
/*      */     }
/*      */ 
/* 1327 */     return billtobillVO;
/*      */   }
/*      */ 
/*      */   public static HashSet<String> querySimilarTypes(String billType)
/*      */   {
/* 1338 */     HashSet hsRet = new HashSet();
/* 1339 */     hsRet.add(billType);
/* 1340 */     BilltypeVO btVO = getBilltypeVOByCode(billType);
/* 1341 */     if ((btVO.getIstransaction() != null) && (btVO.getIstransaction().booleanValue()))
/*      */     {
/* 1343 */       BilltypeVO parentTypeVO = getBilltypeVOByCode(btVO.getParentbilltype());
/* 1344 */       hsRet.add(parentTypeVO.getPk_billtypecode());
/*      */ 
/* 1346 */       String parentBilltype = findParentBilltypeByStyle(parentTypeVO);
/* 1347 */       if (parentBilltype != null)
/*      */       {
/* 1349 */         hsRet.add(parentBilltype);
/*      */       }
/*      */     }
/*      */     else {
/* 1353 */       String parentBilltype = findParentBilltypeByStyle(btVO);
/* 1354 */       if (parentBilltype != null)
/*      */       {
/* 1356 */         hsRet.add(parentBilltype);
/*      */       }
/*      */     }
/* 1359 */     return hsRet;
/*      */   }
/*      */ 
/*      */   private static String findParentBilltypeByStyle(BilltypeVO btVO)
/*      */   {
/* 1369 */     if (btVO.getBillstyle() == null)
/* 1370 */       return null;
/* 1371 */     String parentBilltype = PfDataCache.getBillTypeByStyle(btVO.getBillstyle().toString());
/* 1372 */     if ((parentBilltype == null) || (parentBilltype.equals(btVO.getPk_billtypecode())))
/*      */     {
/* 1374 */       return null; }
/* 1375 */     return parentBilltype;
/*      */   }
/*      */ 
/*      */   public static HashMap<String, String> fetchValuesByPKs(String tableName, String pkFieldName, String columnName, HashSet<String> hsPK)
/*      */   {
/* 1389 */     String sql = "select " + pkFieldName + "," + columnName + " from " + tableName;
/* 1390 */     String where = " where " + pkFieldName + " in(";
/*      */ 
/* 1392 */     for (String strPK : hsPK) {
/* 1393 */       where = where + "'" + strPK + "',";
/*      */     }
/*      */ 
/* 1396 */     where = where.substring(0, where.length() - 1) + ") order by " + pkFieldName;
/* 1397 */     sql = sql + where;
/*      */ 
/* 1399 */     List lResult = new ArrayList(0);
/* 1400 */     HashMap hmRet = new HashMap();
/*      */     try {
/* 1402 */       IUAPQueryBS uapqry = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
/*      */ 
/* 1404 */       lResult = (List)uapqry.executeQuery(sql, new ArrayListProcessor());
/* 1405 */       for (Object objs : lResult){
				   String[] o=(String[] )objs;
/* 1406 */         hmRet.put(o[0].toString(), (o[1] == null) ? null : o[1].toString());
				 }
/*      */     }
/*      */     catch (BusinessException e) {
/* 1409 */       Logger.error(e.getMessage(), e);
/*      */     }
/*      */ 
/* 1412 */     return hmRet;
/*      */   }
/*      */ }

