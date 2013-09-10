/*     */ package nc.impl.uap.pf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import nc.bs.dao.BaseDAO;
/*     */ import nc.bs.dao.DAOException;
/*     */ import nc.bs.framework.common.InvocationInfoProxy;
/*     */ import nc.bs.framework.common.NCLocator;
/*     */ import nc.bs.logging.Logger;
/*     */ import nc.bs.pf.pub.PfDataCache;
/*     */ import nc.bs.pub.mobile.WirelessManager;
/*     */ import nc.bs.pub.msg.PFMessageDMO;
/*     */ import nc.bs.pub.pf.IQueryData;
/*     */ import nc.bs.pub.pf.IQueryData2;
/*     */ import nc.bs.pub.pf.IQueryData3;
/*     */ import nc.bs.pub.pf.IQueryFlatData;
/*     */ import nc.bs.pub.pf.IQueryVoData;
/*     */ import nc.bs.pub.pf.PfUtilDMO;
/*     */ import nc.bs.pub.pf.PfUtilTools;
/*     */ import nc.bs.pub.pf.QueryVoContext;
/*     */ import nc.itf.uap.pf.IPFConfig;
/*     */ import nc.itf.uap.pf.IPFMetaModel;
/*     */ import nc.itf.uap.pf.metadata.IFlowBizItf;
/*     */ import nc.itf.uap.pf.metadata.IHeadBodyQueryItf;
/*     */ import nc.itf.uap.rbac.IPowerManageQuery;
/*     */ import nc.jdbc.framework.exception.DbException;
/*     */ import nc.jdbc.framework.processor.BeanListProcessor;
/*     */ import nc.md.data.access.NCObject;
/*     */ import nc.md.model.IBusinessEntity;
/*     */ import nc.md.model.ITable;
/*     */ import nc.md.model.access.javamap.AggVOStyle;
/*     */ import nc.md.model.access.javamap.IBeanStyle;
/*     */ import nc.uap.pf.metadata.PfMetadataTools;
/*     */ import nc.vo.jcom.lang.StringUtil;
/*     */ import nc.vo.pf.change.PfUtilBaseTools;
/*     */ import nc.vo.pf.changeui02.VotableVO;
/*     */ import nc.vo.pf.pub.BusitypeVO;
/*     */ import nc.vo.pub.AggregatedValueObject;
/*     */ import nc.vo.pub.BusinessException;
/*     */ import nc.vo.pub.CircularlyAccessibleValueObject;
/*     */ import nc.vo.pub.billtype.BilltypeVO;
/*     */ import nc.vo.pub.billtype.DefitemVO;
/*     */ import nc.vo.pub.ddc.datadict.Datadict;
/*     */ import nc.vo.pub.ddc.datadict.FieldDef;
/*     */ import nc.vo.pub.ddc.datadict.FieldDefList;
/*     */ import nc.vo.pub.ddc.datadict.TableDef;
/*     */ import nc.vo.pub.lang.UFBoolean;
/*     */ import nc.vo.pub.msg.SysMessageParam;
/*     */ import nc.vo.pub.pf.PfUtilBillActionVO;
/*     */ import nc.vo.pub.pf.Pfi18nTools;
/*     */ import nc.vo.pub.pfflow.BillactionVO;
/*     */ import nc.vo.pub.pfflow.PFBusiAddInfo;
/*     */ import nc.vo.pub.pfflow.PfFlowBtnInfo;
/*     */ import nc.vo.pub.pfflow01.BillbusinessVO;
/*     */ import nc.vo.uap.pf.PFBusinessException;
/*     */ import nc.vo.uap.pf.PFRuntimeException;
/*     */ import nc.vo.uap.rbac.RoleVO;
/*     */ import nc.vo.uap.rbac.power.PowerQueryByResVO;
/*     */ import nc.vo.uap.rbac.power.PowerResultVO;
/*     */ import nc.vo.uap.rbac.power.UserPowerQueryVO;
/*     */ 
/*     */ public class PFConfigImpl
/*     */   implements IPFConfig
/*     */ {
/*     */   public String[] getBusitypeByCorpAndStyle(String pk_corp, String style)
/*     */     throws BusinessException
/*     */   {
/*  79 */     Collection co = null;
/*     */     try {
/*  81 */       BusitypeVO condvo = new BusitypeVO();
/*  82 */       condvo.setPk_corp(pk_corp);
/*  83 */       condvo.setVerifyrule(style);
/*  84 */       BaseDAO dao = new BaseDAO();
/*  85 */       co = dao.retrieve(condvo, true, new String[] { condvo.getPKFieldName() });
/*     */     } catch (DAOException ex) {
/*  87 */       Logger.error(ex.getMessage(), ex);
/*  88 */       throw new PFBusinessException(ex.getMessage());
/*     */     }
/*  90 */     if ((co == null) || (co.size() == 0))
/*  91 */       return null;
/*  92 */     ArrayList alRet = new ArrayList();
/*  93 */     for (Iterator iter = co.iterator(); iter.hasNext(); ) {
/*  94 */       BusitypeVO busitype = (BusitypeVO)iter.next();
/*  95 */       alRet.add(busitype.getPrimaryKey());
/*     */     }
/*  97 */     return ((String[])(String[])alRet.toArray(new String[alRet.size()]));
/*     */   }
/*     */ 
/*     */   public BusitypeVO[] querybillBusinessType(String corpId, String billType) throws BusinessException
/*     */   {
/* 102 */     BusitypeVO[] busiVos = null;
/*     */     try {
/* 104 */       PfUtilDMO dmo = new PfUtilDMO();
/* 105 */       busiVos = dmo.queryBillBusinessType(corpId, billType);
/*     */     } catch (DbException e) {
/* 107 */       Logger.error(e.getMessage(), e);
/* 108 */       throw new PFBusinessException(e.getMessage());
/*     */     }
/* 110 */     return busiVos;
/*     */   }
/*     */ 
/*     */   public BillbusinessVO[] querybillSource(String corpId, String billType, String businessType) throws BusinessException
/*     */   {
/* 115 */     BillbusinessVO[] billReferVo = null;
/*     */     try {
/* 117 */       PfUtilDMO dmo = new PfUtilDMO();
/* 118 */       billReferVo = dmo.queryBillSource(corpId, billType, null, businessType);
/*     */     } catch (DbException e) {
/* 120 */       Logger.error(e.getMessage(), e);
/* 121 */       throw new PFBusinessException(e.getMessage());
/*     */     }
/* 123 */     return billReferVo;
/*     */   }
/*     */ 
/*     */   public CircularlyAccessibleValueObject[] queryHeadAllData(String billType, String businessType, String whereString) throws BusinessException
/*     */   {
/* 128 */     Logger.info("*****查询单据的表头数据开始*****");
/* 129 */     BilltypeVO btVO = PfDataCache.getBillTypeInfo(billType);
/* 130 */     boolean hasMeta = PfMetadataTools.isBilltypeRelatedMeta(billType);
/*     */ 
/* 133 */     StringBuffer retWhere = new StringBuffer();
/*     */ 
/* 135 */     boolean isExistDbWhere = true;
/* 136 */     if ((btVO.getWherestring() != null) && (!(btVO.getWherestring().trim().equals("")))) {
/* 137 */       retWhere.append(" ").append(btVO.getWherestring());
/*     */     } else {
/* 139 */       isExistDbWhere = false;
/* 140 */       retWhere.append(" ");
/*     */     }
/* 142 */     if ((whereString != null) && (!(whereString.trim().equals("")))) {
/* 143 */       if (isExistDbWhere)
/* 144 */         retWhere.append(" and ").append(whereString);
/*     */       else {
/* 146 */         retWhere.append(whereString);
/*     */       }
/*     */     }
/*     */ 
/* 150 */     String busitypeField = null;
/* 151 */     IBusinessEntity be = null;
/* 152 */     if (hasMeta) {
/* 153 */       be = PfMetadataTools.queryMetaOfBilltype(billType);
/* 154 */       IFlowBizItf fbi = (IFlowBizItf)NCObject.newInstance(be, null).getBizInterface(IFlowBizItf.class);
/* 155 */       busitypeField = (fbi == null) ? null : fbi.getColumnName("busitype");
/*     */     } else {
/* 157 */       VotableVO votable = PfDataCache.getBillTypeToVO(billType, true);
/* 158 */       busitypeField = votable.getBusitype();
/*     */     }
/* 160 */     if (retWhere.length() > 10) {
/* 161 */       if ((!(StringUtil.isEmptyWithTrim(businessType))) && (!(StringUtil.isEmptyWithTrim(busitypeField))))
/* 162 */         retWhere.append(" and ").append(busitypeField).append("='").append(businessType).append("'");
/*     */     }
/*     */     else {
/* 165 */       retWhere.delete(0, retWhere.length());
/*     */     }
/*     */ 
/* 169 */     if (hasMeta) {
/* 170 */       IHeadBodyQueryItf qi = (IHeadBodyQueryItf)NCObject.newInstance(be, null).getBizInterface(IHeadBodyQueryItf.class);
/*     */ 
/* 172 */       if (qi != null) {
/* 173 */         return qi.queryAllHeadData(billType, retWhere.toString());
/*     */       }
/*     */     }
/*     */ 
/* 177 */     Object queryObj = PfUtilTools.findBizImplOfBilltype(billType, btVO.getReferclassname());
/* 178 */     IQueryData tmpObj = (IQueryData)queryObj;
/* 179 */     CircularlyAccessibleValueObject[] retVos = tmpObj.queryAllHeadData(retWhere.toString());
/* 180 */     Logger.info("*****查询单据的表头数据结束*****");
/* 181 */     return retVos;
/*     */   }
/*     */ 
/*     */   public CircularlyAccessibleValueObject[] queryBodyAllData(String billType, String parentPK, String bodyCondition)
/*     */     throws BusinessException
/*     */   {
/* 187 */     BilltypeVO btVO = PfDataCache.getBillTypeInfo(billType);
/* 188 */     boolean hasMeta = PfMetadataTools.isBilltypeRelatedMeta(billType);
/*     */ 
/* 191 */     if (hasMeta) {
/* 192 */       IBusinessEntity be = PfMetadataTools.queryMetaOfBilltype(billType);
/* 193 */       IHeadBodyQueryItf qi = (IHeadBodyQueryItf)NCObject.newInstance(be, null).getBizInterface(IHeadBodyQueryItf.class);
/*     */ 
/* 195 */       if (qi != null) {
/* 196 */         if (StringUtil.isEmptyWithTrim(bodyCondition)) {
/* 197 */           return qi.queryAllBodyData(billType, parentPK);
/*     */         }
/* 199 */         return qi.queryAllBodyData(billType, parentPK, bodyCondition);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 204 */     Object queryObj = PfUtilTools.findBizImplOfBilltype(billType, btVO.getReferclassname());
/* 205 */     if (StringUtil.isEmptyWithTrim(bodyCondition)) {
/* 206 */       Logger.info("使用接口IQueryData查询表体数据");
/* 207 */       IQueryData tmpObj = (IQueryData)queryObj;
/* 208 */       return tmpObj.queryAllBodyData(parentPK);
/*     */     }
/* 210 */     Logger.info("使用接口IQueryData2查询表体数据");
/* 211 */     Logger.info("使用接口IQueryData2查询表体数据,子表查询条件为:" + bodyCondition);
/* 212 */     IQueryData2 tmpObj = (IQueryData2)queryObj;
/* 213 */     return tmpObj.queryAllBodyData(parentPK, bodyCondition);
/*     */   }
/*     */ 
/*     */   public AggregatedValueObject queryOnlyHeadData(String billType, String billId)
/*     */     throws BusinessException
/*     */   {
/* 219 */     return fetchBillVO(billType, billId, false);
/*     */   }
/*     */ 
/*     */   public AggregatedValueObject queryBillDataVO(String billType, String billId) throws BusinessException
/*     */   {
/* 224 */     return fetchBillVO(billType, billId, true);
/*     */   }
/*     */ 
/*     */   private AggregatedValueObject fetchBillVO(String billType, String billId, boolean isWithBody)
/*     */     throws BusinessException
/*     */   {
/* 239 */     String billVoClassName = null;
/*     */ 
/* 241 */     String pkField = null;
/*     */ 
/* 243 */     String billidField = null;
/*     */ 
/* 245 */     String tablename = null;
/*     */ 
/* 248 */     boolean hasMeta = PfMetadataTools.isBilltypeRelatedMeta(billType);
/* 249 */     if (hasMeta) {
/* 250 */       IBusinessEntity be = PfMetadataTools.queryMetaOfBilltype(billType);
/* 251 */       IBeanStyle bs = be.getBeanStyle();
/* 252 */       if (bs instanceof AggVOStyle)
/* 253 */         billVoClassName = ((AggVOStyle)bs).getAggVOClassName();
/*     */       else {
/* 255 */         throw new PFBusinessException("流程平台：单据实体必须符合聚合VO的样式");
/*     */       }
/* 257 */       IHeadBodyQueryItf qi = (IHeadBodyQueryItf)NCObject.newInstance(be, null).getBizInterface(IHeadBodyQueryItf.class);
/*     */ 
/* 259 */       if (qi != null)
/*     */       {
/* 261 */         Logger.debug(">>通过元数据业务接口查询单据聚合VO");
/* 262 */         AggregatedValueObject billVO = null;
/*     */         try {
/* 264 */           billVO = (AggregatedValueObject)(AggregatedValueObject)Class.forName(billVoClassName).newInstance();
/*     */         } catch (Exception e) {
/* 266 */           Logger.error(e.getMessage(), e);
/* 267 */           throw new PFBusinessException("无法实例化单据VO类：" + e.getMessage());
/*     */         }
/* 269 */         CircularlyAccessibleValueObject headVO = qi.queryHeadData(billType, billId);
/* 270 */         billVO.setParentVO(headVO);
/* 271 */         if (isWithBody) {
/* 272 */           CircularlyAccessibleValueObject[] bodyVOs = qi.queryAllBodyData(billType, billId);
/* 273 */           billVO.setChildrenVO(bodyVOs);
/*     */         }
/* 275 */         return billVO;
/*     */       }
/* 277 */       pkField = be.getTable().getPrimaryKeyName();
/* 278 */       tablename = be.getTable().getName();
/*     */ 
/* 280 */       IFlowBizItf fbi = (IFlowBizItf)NCObject.newInstance(be, null).getBizInterface(IFlowBizItf.class);
/* 281 */       if (fbi == null)
/* 282 */         throw new PFBusinessException("单据实体没有提供业务接口IFlowBizInf的实现类");
/* 283 */       billidField = fbi.getColumnName("billid");
/*     */     }
/*     */     else
/*     */     {
/* 287 */       VotableVO votable = PfDataCache.getBillTypeToVO(billType, true);
/* 288 */       billVoClassName = votable.getBillvo();
/* 289 */       pkField = votable.getPkfield();
/* 290 */       billidField = votable.getBillid();
/* 291 */       tablename = votable.getVotable();
/*     */     }
/*     */ 
/* 295 */     BilltypeVO btVO = PfDataCache.getBillTypeInfo(billType);
/* 296 */     String referClsName = btVO.getReferclassname();
/* 297 */     Object queryObj = PfUtilTools.findBizImplOfBilltype(billType, referClsName);
/*     */ 
/* 300 */     Logger.debug(">>通过业务单据查询类=" + referClsName + ",查询单据聚合VO");
/* 301 */     AggregatedValueObject billVO = null;
/*     */     try {
/* 303 */       billVO = (AggregatedValueObject)(AggregatedValueObject)Class.forName(billVoClassName).newInstance();
/*     */     } catch (Exception e) {
/* 305 */       Logger.error(e.getMessage(), e);
/* 306 */       throw new PFBusinessException("无法实例化单据VO类：" + e.getMessage());
/*     */     }
/*     */ 
/* 309 */     if (queryObj instanceof IQueryVoData) {
/* 310 */       Logger.info("使用接口IQueryVoData查询单据VO");
/* 311 */       IQueryVoData queryDataObj = (IQueryVoData)queryObj;
/* 312 */       QueryVoContext qvc = new QueryVoContext();
/* 313 */       qvc.setBillId(billId);
/* 314 */       qvc.setRetArray(false);
/* 315 */       CircularlyAccessibleValueObject headVO = (CircularlyAccessibleValueObject)queryDataObj.queryHeadVO(qvc);
/*     */ 
/* 317 */       if (headVO == null)
/* 318 */         throw new PFBusinessException("获取单据主表VO失败");
/* 319 */       billVO.setParentVO(headVO);
/*     */ 
/* 321 */       if (isWithBody) {
/* 322 */         CircularlyAccessibleValueObject[] bodyVOs = (CircularlyAccessibleValueObject[])(CircularlyAccessibleValueObject[])queryDataObj.queryBodyVO(qvc);
/*     */ 
/* 324 */         billVO.setChildrenVO(bodyVOs);
/*     */       }
/* 326 */     } else if (queryObj instanceof IQueryData3) {
/* 327 */       Logger.info("使用接口IQueryData3查询单据VO");
/* 328 */       IQueryData3 queryDataObj = (IQueryData3)queryObj;
/* 329 */       CircularlyAccessibleValueObject headVO = queryDataObj.queryHeadData(billId);
/* 330 */       if (headVO == null)
/* 331 */         throw new PFBusinessException("获取单据主表VO失败");
/* 332 */       billVO.setParentVO(headVO);
/*     */ 
/* 334 */       if (isWithBody) {
/* 335 */         CircularlyAccessibleValueObject[] bodyVOs = queryDataObj.queryAllBodyData(billId);
/* 336 */         billVO.setChildrenVO(bodyVOs);
/*     */       }
/* 338 */     } else if (queryObj instanceof IQueryData) {
/* 339 */       Logger.info("使用接口IQueryData查询单据VO");
/* 340 */       IQueryData queryDataObj = (IQueryData)queryObj;
/*     */ 
/* 342 */       String m_wherestr = tablename + "." + billidField + "='" + billId + "'";
/* 343 */       CircularlyAccessibleValueObject[] headVOs = queryDataObj.queryAllHeadData(m_wherestr);
/* 344 */       if ((headVOs == null) || (headVOs.length == 0)) {
/* 345 */         throw new PFBusinessException("获取单据主表VO失败");
/*     */       }
/* 347 */       billVO.setParentVO(headVOs[0]);
/* 348 */       if (isWithBody) {
/* 349 */         CircularlyAccessibleValueObject[] bodyVOs = queryDataObj.queryAllBodyData(billId);
/* 350 */         billVO.setChildrenVO(bodyVOs);
/*     */       }
/*     */     } else {
/* 353 */       throw new PFBusinessException("流程平台：单据类型注册查询单据VO的类没有实现平台接口");
/*     */     }
/*     */ 
/* 356 */     return billVO;
/*     */   }
/*     */ 
/*     */   public PfUtilBillActionVO[] querybillActionStyle(String billType, String actionStyle) throws BusinessException
/*     */   {
/* 361 */     PfUtilBillActionVO[] billActionVos = null;
/*     */     try {
/* 363 */       PfUtilDMO dmo = new PfUtilDMO();
/* 364 */       billActionVos = dmo.queryBillActionStyle(billType, actionStyle);
/*     */     } catch (DbException e) {
/* 366 */       Logger.error(e.getMessage(), e);
/* 367 */       throw new PFBusinessException(e.getMessage());
/*     */     }
/* 369 */     return billActionVos;
/*     */   }
/*     */ 
/*     */   public SysMessageParam getSysMsgParam() throws BusinessException {
/* 373 */     return WirelessManager.fetchSysMsgParam();
/*     */   }
/*     */ 
/*     */   public void saveSysMsgParam(SysMessageParam smp) throws BusinessException {
/*     */     try {
/* 378 */       WirelessManager.saveSysMsgParam(smp);
/*     */     } catch (IOException e) {
/* 380 */       Logger.error(e.getMessage(), e);
/* 381 */       throw new PFBusinessException(e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public PowerResultVO queryPowerBusiness(String userID, String corpPK, String billtype) throws BusinessException
/*     */   {
/* 387 */     PowerResultVO voRet = new PowerResultVO();
/*     */ 
/* 389 */     PowerResultVO voPower = getPowerBillBusiness(userID, corpPK);
/* 390 */     voRet.setPowerControl(voPower.isPowerControl());
/* 391 */     if (voPower.isPowerControl()) {
/* 392 */       PfUtilDMO pfdmo = new PfUtilDMO();
/*     */ 
/* 394 */       String[] sBusinessCtrl = pfdmo.getBillbusiPKsOfBilltype(voPower.getPowerId(), billtype);
/*     */ 
/* 396 */       String[] sBusinessNoCtrl = null;
/*     */       try {
/* 398 */         sBusinessNoCtrl = pfdmo.queryBusitypeOfNoControl(corpPK, billtype);
/*     */       } catch (DbException e) {
/* 400 */         Logger.error(e.getMessage(), e);
/* 401 */         throw new PFBusinessException(e.getMessage());
/*     */       }
/*     */ 
/* 404 */       if ((sBusinessCtrl == null) || (sBusinessCtrl.length == 0)) {
/* 405 */         voRet.setPowerId(sBusinessNoCtrl);
/* 406 */       } else if ((sBusinessNoCtrl == null) || (sBusinessNoCtrl.length == 0)) {
/* 407 */         voRet.setPowerId(sBusinessCtrl);
/*     */       } else {
/* 409 */         String[] sBusinessAll = new String[sBusinessCtrl.length + sBusinessNoCtrl.length];
/* 410 */         int i = 0;
/* 411 */         for (; i < sBusinessCtrl.length; ++i) {
/* 412 */           sBusinessAll[i] = sBusinessCtrl[i];
/*     */         }
/* 414 */         for (int j = 0; j < sBusinessNoCtrl.length; ++j) {
/* 415 */           sBusinessAll[(i++)] = sBusinessNoCtrl[j];
/*     */         }
/* 417 */         voRet.setPowerId(sBusinessAll);
/*     */       }
/*     */     }
/* 420 */     return voRet;
/*     */   }
/*     */ 
/*     */   private PowerResultVO getPowerBillBusiness(String userID, String corpPK)
/*     */     throws BusinessException
/*     */   {
/* 430 */     UserPowerQueryVO voQuery = new UserPowerQueryVO();
/* 431 */     voQuery.setResouceId(10);
/* 432 */     voQuery.setUserPK(userID);
/* 433 */     voQuery.setOrgTypeCode(1);
/* 434 */     voQuery.setOrgPK(corpPK);
/* 435 */     voQuery.setCorpPK(corpPK);
/* 436 */     IPowerManageQuery iPower = (IPowerManageQuery)NCLocator.getInstance().lookup(IPowerManageQuery.class.getName());
/*     */ 
/* 438 */     PowerResultVO voPower = iPower.getUserPower(voQuery);
/* 439 */     return voPower;
/*     */   }
/*     */ 
/*     */   public void generateBillItemByDDC(String mainTable, String subTable, String pk_billtype) throws BusinessException
/*     */   {
/* 444 */     if (StringUtil.isEmptyWithTrim(mainTable)) {
/* 445 */       throw new PFBusinessException("主表名不可为空");
/*     */     }
/*     */ 
/* 448 */     Datadict datadict = Datadict.getDefaultInstance();
/* 449 */     TableDef m_tableDef = datadict.findTableDef(mainTable);
/* 450 */     if (m_tableDef == null) {
/* 451 */       throw new PFBusinessException("数据字典中没有找到主表=" + mainTable);
/*     */     }
/* 453 */     ArrayList alRet = new ArrayList();
/*     */ 
/* 455 */     FieldDefList m_fieldDefList = m_tableDef.getFieldDefs();
/* 456 */     int mCount = m_fieldDefList.getCount();
/* 457 */     for (int i = 0; i < mCount; ++i) {
/* 458 */       FieldDef fieldDef = m_fieldDefList.getFieldDef(i);
/* 459 */       DefitemVO defitem = new DefitemVO();
/* 460 */       defitem.setHeadflag(UFBoolean.TRUE);
/* 461 */       setDefItemVOByFieldDef(defitem, fieldDef, pk_billtype);
/* 462 */       alRet.add(defitem);
/*     */     }
/*     */ 
/* 466 */     TableDef s_tableDef = datadict.findTableDef(subTable);
/* 467 */     if (s_tableDef != null) {
/* 468 */       FieldDefList s_tableDefList = s_tableDef.getFieldDefs();
/* 469 */       for (int j = 0; j < s_tableDefList.getCount(); ++j) {
/* 470 */         DefitemVO defitem = new DefitemVO();
/* 471 */         defitem.setHeadflag(UFBoolean.FALSE);
/* 472 */         setDefItemVOByFieldDef(defitem, s_tableDefList.getFieldDef(j), pk_billtype);
/* 473 */         alRet.add(defitem);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 478 */     if (alRet.size() > 0) {
/* 479 */       PfUtilDMO pfdmo = new PfUtilDMO();
/* 480 */       pfdmo.insertBillitems((DefitemVO[])(DefitemVO[])alRet.toArray(new DefitemVO[alRet.size()]));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setDefItemVOByFieldDef(DefitemVO defitemVO, FieldDef fieldDef, String pk_billtype)
/*     */   {
/* 492 */     if ((defitemVO != null) && (fieldDef != null)) {
/* 493 */       defitemVO.setPk_billtype(pk_billtype);
/* 494 */       defitemVO.setAttrname(fieldDef.getID());
/* 495 */       defitemVO.setItemname("");
/* 496 */       defitemVO.setResourceid(fieldDef.getDisplayName());
/* 497 */       int itemtype = transDataType(fieldDef.getDataType());
/* 498 */       defitemVO.setItemtype(new Integer(itemtype));
/*     */     }
/*     */   }
/*     */ 
/*     */   private int transDataType(int dataType)
/*     */   {
/* 509 */     int itemtype = 0;
/* 510 */     switch (dataType)
/*     */     {
/*     */     case 2:
/*     */     case 3:
/*     */     case 6:
/*     */     case 7:
/*     */     case 8:
/* 516 */       itemtype = 1;
/* 517 */       break;
/*     */     case 16:
/* 519 */       itemtype = 5;
/* 520 */       break;
/*     */     case 91:
/* 522 */       itemtype = 2;
/* 523 */       break;
/*     */     case 92:
/*     */     case 93:
/* 526 */       itemtype = 4;
/* 527 */       break;
/*     */     case -5:
/*     */     case 4:
/*     */     case 5:
/* 531 */       itemtype = 3;
/* 532 */       break;
/*     */     default:
/* 534 */       itemtype = 0;
/*     */     }
/*     */ 
/* 537 */     return itemtype;
/*     */   }
/*     */ 
/*     */   public BillbusinessVO[] queryBillDest(String billType, String busiType) throws BusinessException {
/*     */     try {
/* 542 */       return new PfUtilDMO().queryBillDest(billType, busiType);
/*     */     } catch (DbException e) {
/* 544 */       Logger.error(e.getMessage(), e);
/* 545 */       throw new PFBusinessException(e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public RoleVO[] queryRolesHasBillbusi(String pkCorp, String billType, String busiType, boolean bQueryAllCorp)
/*     */     throws BusinessException
/*     */   {
/* 552 */     BillbusinessVO condVO = new BillbusinessVO();
/* 553 */     condVO.setPk_billtype(billType);
/* 554 */     condVO.setPk_businesstype(busiType);
/* 555 */     BaseDAO dao = new BaseDAO();
/* 556 */     Collection co = dao.retrieve(condVO, true, new String[] { "pk_billbusiness" });
/* 557 */     BillbusinessVO billbusiVO = null;
/* 558 */     if (co.size() > 0) {
/* 559 */       Iterator iter = co.iterator();
/* 560 */       billbusiVO = (BillbusinessVO)iter.next();
/*     */     } else {
/* 562 */       return null;
/*     */     }
/* 564 */     PowerQueryByResVO voQuery = new PowerQueryByResVO();
/* 565 */     voQuery.setResouceId(10);
/* 566 */     voQuery.setResourceDataId(billbusiVO.getPrimaryKey());
/* 567 */     voQuery.setOrgTypeCode(1);
/* 568 */     voQuery.setOrgPK(pkCorp);
/* 569 */     voQuery.setCorpPK(pkCorp);
/* 570 */     voQuery.setQueryAllCorp(bQueryAllCorp);
/* 571 */     voQuery.setQueryAllocRole(false);
/* 572 */     IPowerManageQuery query = (IPowerManageQuery)NCLocator.getInstance().lookup(IPowerManageQuery.class.getName());
/*     */ 
/* 574 */     return query.queryPowerRole(voQuery);
/*     */   }
/*     */ 
/*     */   public void completeWorkitem(String billId, String[] srcBillIds, String checkman, String billtype, String srcBilltype) throws BusinessException
/*     */   {
/* 579 */     HashSet hs = new HashSet();
/* 580 */     if ((billId != null) && (billId.length() != 0))
/* 581 */       hs.add(billId);
/* 582 */     for (int i = 0; i < ((srcBillIds == null) ? 0 : srcBillIds.length); ++i) {
/* 583 */       hs.add(srcBillIds[i]);
/*     */     }
/* 585 */     if (hs.size() == 0)
/* 586 */       return;
/*     */     try {
/* 588 */       new PFMessageDMO().completeWorkitem(hs, checkman, billtype, srcBilltype);
/*     */     } catch (DbException e) {
/* 590 */       Logger.error(e.getMessage(), e);
/* 591 */       throw new PFBusinessException(e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public CircularlyAccessibleValueObject[] queryAllFlatVOs(String billType, String busiType, String whereString) throws BusinessException
/*     */   {
/* 597 */     Logger.info("*****查询单据的平铺扩展数据开始*****");
/*     */ 
/* 599 */     StringBuffer retWhere = new StringBuffer();
/* 600 */     BilltypeVO btVO = PfDataCache.getBillTypeInfo(billType);
/*     */ 
/* 602 */     boolean isExistDbWhere = true;
/* 603 */     if ((btVO.getWherestring() != null) && (!(btVO.getWherestring().trim().equals("")))) {
/* 604 */       retWhere.append(" ").append(btVO.getWherestring());
/*     */     } else {
/* 606 */       isExistDbWhere = false;
/* 607 */       retWhere.append(" ");
/*     */     }
/*     */ 
/* 610 */     if ((whereString != null) && (!(whereString.trim().equals("")))) {
/* 611 */       if (isExistDbWhere)
/* 612 */         retWhere.append(" and ").append(whereString);
/*     */       else {
/* 614 */         retWhere.append(whereString);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 619 */     String busitypeField = null;
/* 620 */     if (PfMetadataTools.isBilltypeRelatedMeta(billType)) {
/* 621 */       NCObject ncObj = NCObject.newInstance(PfMetadataTools.queryMetaOfBilltype(billType), null);
/* 622 */       IFlowBizItf fbi = (IFlowBizItf)ncObj.getBizInterface(IFlowBizItf.class);
/* 623 */       if (fbi == null)
/* 624 */         throw new PFRuntimeException("流程平台：单据实体没有提供业务接口IFlowBizInf的实现类");
/* 625 */       busitypeField = fbi.getColumnName("busitype");
/*     */     } else {
/* 627 */       VotableVO votable = PfDataCache.getBillTypeToVO(billType, true);
/* 628 */       busitypeField = votable.getBusitype();
/*     */     }
/* 630 */     if (retWhere.length() > 10)
/* 631 */       if ((!(StringUtil.isEmptyWithTrim(busiType))) && (!(StringUtil.isEmptyWithTrim(busitypeField))))
/* 632 */         retWhere.append(" and ").append(busitypeField).append("='").append(busiType).append("'");
/*     */     else {
/* 634 */       retWhere.delete(0, retWhere.length());
/*     */     }
/*     */ 
/* 637 */     Object queryObj = PfUtilTools.findBizImplOfBilltype(billType, btVO.getReferclassname());
/* 638 */     Logger.info("查询主表的条件语句：" + retWhere);
/* 639 */     IQueryFlatData tmpObj = (IQueryFlatData)queryObj;
/* 640 */     CircularlyAccessibleValueObject[] retVos = tmpObj.queryAllFlatVOs(retWhere.toString());
/* 641 */     Logger.info("*****查询单据的平铺扩展数据结束*****");
/* 642 */     return retVos;
/*     */   }
/*     */ 
/*     */   public BillbusinessVO[] findBillbusinessVOs(String biztype, String pkcorp) throws BusinessException
/*     */   {
/*     */     try
/*     */     {
/* 649 */       return new PfUtilDMO().queryBillbusiVOs(biztype, pkcorp);
/*     */     } catch (DbException e) {
/* 651 */       Logger.error(e.getMessage(), e);
/* 652 */       throw new PFBusinessException(e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public String findDestBusitype(String busitype, String billtype) throws BusinessException
/*     */   {
/*     */     try
/*     */     {
/* 660 */       return new PfUtilDMO().queryDestBusitype(busitype, billtype);
/*     */     } catch (DbException e) {
/* 662 */       Logger.error(e.getMessage(), e);
/* 663 */       throw new PFBusinessException(e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public BillbusinessVO[] querybillSource2(String corpId, String billType, String transType, String businessType) throws BusinessException
/*     */   {
/* 669 */     BillbusinessVO[] billReferVo = null;
/*     */     try {
/* 671 */       PfUtilDMO dmo = new PfUtilDMO();
/* 672 */       billReferVo = dmo.queryBillSource(corpId, billType, transType, businessType);
/*     */     } catch (DbException e) {
/* 674 */       Logger.error(e.getMessage(), e);
/* 675 */       throw new PFBusinessException(e.getMessage());
/*     */     }
/* 677 */     return billReferVo;
/*     */   }
/*     */ 
/*     */   public BillbusinessVO[][] querybillSource3(String corpId, String billType, String transType, String[] businessTypes)
/*     */     throws BusinessException
/*     */   {
/* 683 */     BillbusinessVO[][] retSrcVOs = new BillbusinessVO[businessTypes.length][];
/*     */     try {
/* 685 */       PfUtilDMO dmo = new PfUtilDMO();
/* 686 */       for (int j = 0; j < businessTypes.length; ++j)
/* 687 */         retSrcVOs[j] = dmo.queryBillSource(corpId, billType, transType, businessTypes[j]);
/*     */     }
/*     */     catch (DbException e) {
/* 690 */       Logger.error(e.getMessage(), e);
/* 691 */       throw new PFBusinessException(e.getMessage());
/*     */     }
/* 693 */     return retSrcVOs;
/*     */   }
/*     */ 
/*     */   public List<BillactionVO> diffCopyActions(String transType) throws BusinessException {
/* 697 */     ArrayList alDiff = new ArrayList();
/* 698 */     if (StringUtil.isEmptyWithTrim(transType))
/*     */     {
/* 700 */       String sql = "select pk_billtypecode,parentbilltype from bd_billtype where istransaction='Y'";
/* 701 */       BaseDAO dao = new BaseDAO();
/* 702 */       ArrayList<BilltypeVO> alTranstypeVOs = (ArrayList)dao.executeQuery(sql, new BeanListProcessor(BilltypeVO.class));
/*     */ 
/* 705 */       for (BilltypeVO ttVO : alTranstypeVOs)
/* 706 */         alDiff.addAll(syncActions(ttVO));
/*     */     }
/* 708 */     else if (PfUtilBaseTools.isTranstype(transType.trim()))
/*     */     {
/* 710 */       alDiff.addAll(syncActions(PfDataCache.getBillType(transType)));
/*     */     }
/* 712 */     return alDiff;
/*     */   }
/*     */ 
/*     */   private ArrayList<BillactionVO> syncActions(BilltypeVO ttVO) throws BusinessException {
/* 716 */     ArrayList<BillactionVO> alDiff = new ArrayList();
/*     */ 
/* 718 */     if (ttVO == null) {
/* 719 */       return alDiff;
/*     */     }
/*     */ 
/* 722 */     BillactionVO[] actionsOfTranstype = queryActionVOsOfBilltype(ttVO.getPrimaryKey());
/* 723 */     if (StringUtil.isEmptyWithTrim(ttVO.getParentbilltype())) {
/* 724 */       return alDiff;
/*     */     }
/*     */ 
/* 727 */     BillactionVO[] actionsOfBilltype = queryActionVOsOfBilltype(ttVO.getParentbilltype());
/* 728 */     for (int i = 0; i < ((actionsOfBilltype == null) ? 0 : actionsOfBilltype.length); ++i) {
/* 729 */       boolean bFound = false;
/* 730 */       for (int j = 0; j < ((actionsOfTranstype == null) ? 0 : actionsOfTranstype.length); ++j) {
/* 731 */         if (!(actionsOfBilltype[i].getActiontype().equalsIgnoreCase(actionsOfTranstype[j].getActiontype()))) {
/*     */           continue;
/*     */         }
/* 734 */         bFound = true;
/* 735 */         break;
/*     */       }
/*     */ 
/* 738 */       if (!(bFound)) {
/* 739 */         alDiff.add(actionsOfBilltype[i]);
/*     */       }
/*     */     }
/* 742 */     if (alDiff.size() > 0)
/*     */     {
/* 744 */       for (BillactionVO actionVO : alDiff) {
/* 745 */         actionVO.setPk_billtype(ttVO.getPrimaryKey());
/* 746 */         actionVO.setPrimaryKey(null);
/*     */       }
/* 748 */       new BaseDAO().insertVOList(alDiff);
/*     */     }
/* 750 */     return alDiff;
/*     */   }
/*     */ 
/*     */   private BillactionVO[] queryActionVOsOfBilltype(String billType) throws BusinessException {
/* 754 */     PfUtilBillActionVO[] actionVos = ((IPFMetaModel)NCLocator.getInstance().lookup(IPFMetaModel.class)).queryBillActions(billType);
/*     */ 
/* 756 */     if ((actionVos == null) || (actionVos.length == 0))
/* 757 */       return null;
/* 758 */     BillactionVO[] baVOs = new BillactionVO[actionVos.length];
/* 759 */     for (int i = 0; i < actionVos.length; ++i) {
/* 760 */       baVOs[i] = actionVos[i].toBillactionVO();
/*     */     }
/* 762 */     return baVOs;
/*     */   }
/*     */ 
/*     */   public PfFlowBtnInfo[] findAddBtn2Info(String corpId, String billType, String transType) throws BusinessException
/*     */   {
/* 768 */     String businessType = null;
/* 769 */     List btnInfoList = new ArrayList();
/*     */     HashMap  refbills;
/*     */     try
/*     */     {
/* 772 */       BusitypeVO[] biztypeVOs = getBusiByCorpAndBill(corpId, billType);
/* 773 */       refbills = new HashMap();
/* 774 */       for (int i = 0; i < ((biztypeVOs == null) ? 0 : biztypeVOs.length); ++i) {
/* 775 */         businessType = biztypeVOs[i].getPk_busitype();
/*     */ 
/* 777 */         BillbusinessVO[] billBusiVOs = querybillSource2(corpId, billType, transType, businessType);
/*     */ 
/* 779 */         for (int j = 0; j < billBusiVOs.length; ++j) {
/* 780 */           PfFlowBtnInfo btnInfo = new PfFlowBtnInfo();
/* 781 */           String showName = Pfi18nTools.i18nBilltypeName(billBusiVOs[j].getPk_billtype(), billBusiVOs[j].getBilltypename());
/*     */ 
/* 783 */           if (billBusiVOs[j].getPk_billtype().toUpperCase().equals("MAKEFLAG")) {
/* 784 */             showName = showName + "_[" + biztypeVOs[i].getBusiname() + "]";
/*     */ 
/* 787 */             String btnTag = billBusiVOs[j].getPk_billtype().trim() + ":" + businessType;
/* 788 */             btnInfo.setShowName(showName);
/* 789 */             btnInfo.setBtnTag(btnTag);
/* 790 */             btnInfoList.add(btnInfo);
/*     */           } else {
/* 792 */             StringBuffer tag = null;
/* 793 */             if (refbills.get(showName) == null) {
/* 794 */               tag = new StringBuffer();
/* 795 */               tag.append(billBusiVOs[j].getPk_billtype().trim());
/* 796 */               tag.append(":");
/*     */             } else {
/* 798 */               tag = (StringBuffer)refbills.get(showName);
/* 799 */               tag.append(",");
/*     */             }
/* 801 */             tag.append(businessType);
/* 802 */             refbills.put(showName, tag);
/*     */           }
/*     */         }
/*     */       }
/* 806 */       for (Object key : refbills.keySet()) {
/* 807 */         PfFlowBtnInfo btnInfo = new PfFlowBtnInfo();
/*     */ 
/* 809 */         btnInfo.setBtnTag(((StringBuffer)refbills.get(key)).toString());
/* 810 */         btnInfo.setShowName(String.valueOf(key));
/* 811 */         btnInfoList.add(btnInfo);
/*     */       }
/*     */     } catch (Exception ex) {
/* 814 */       Logger.error(ex.getMessage(), ex);
/*     */     }
/* 816 */     return ((PfFlowBtnInfo[])btnInfoList.toArray(new PfFlowBtnInfo[0]));
/*     */   }
/*     */ 
/*     */   public PfFlowBtnInfo[] findCopyBtnInfo(String corpId, String billType, String transType) throws BusinessException
/*     */   {
/* 821 */     List btnInfoList = new ArrayList();
/*     */     try
/*     */     {
/* 824 */       BusitypeVO[] types = getBusiByCorpAndBill(corpId, billType);
/* 825 */       if ((types == null) || (types.length == 0)) return null;
/* 826 */       String[] pkBusitypes = new String[types.length];
/* 827 */       for (int i = 0; i < types.length; ++i) {
/* 828 */         pkBusitypes[i] = types[i].getPk_busitype();
/*     */       }
/*     */ 
/* 831 */       BillbusinessVO[] billbusiVOs = new PfUtilDMO().queryMakeFlagBillSource(corpId, billType, transType, pkBusitypes);
/*     */ 
/* 833 */       HashSet hs = new HashSet();
/* 834 */       for (int i = 0; i < billbusiVOs.length; ++i)
/*     */       {
/* 836 */         String tag = "MAKEFLAG:" + billbusiVOs[i].getPk_businesstype();
/* 837 */         if (hs.contains(tag)) {
/*     */           continue;
/*     */         }
/* 840 */         hs.add(tag);
/* 841 */         PfFlowBtnInfo btnInfo = new PfFlowBtnInfo();
/*     */ 
/* 843 */         btnInfo.setShowName("[" + billbusiVOs[i].getBusitypename() + "]");
/*     */ 
/* 845 */         btnInfo.setBtnTag(tag);
/* 846 */         btnInfoList.add(btnInfo);
/*     */       }
/*     */     } catch (Exception ex) {
/* 849 */       Logger.error(ex.getMessage(), ex);
/*     */     }
/* 851 */     return ((PfFlowBtnInfo[])btnInfoList.toArray(new PfFlowBtnInfo[0]));
/*     */   }
/*     */ 
/*     */   public BusitypeVO[] getBusiByCorpAndBill(String corpId, String billType)
/*     */     throws BusinessException
/*     */   {
/* 864 */     BusitypeVO[] busitypeAll = querybillBusinessType(corpId, billType);
/* 865 */     if ((busitypeAll == null) || (busitypeAll.length == 0)) {
/* 866 */       return null;
/*     */     }
/*     */ 
/* 869 */     String sUserid = InvocationInfoProxy.getInstance().getUserCode();
/* 870 */     PowerResultVO voPower = queryPowerBusiness(sUserid, corpId, billType);
/* 871 */     if (!(voPower.isPowerControl()))
/* 872 */       return busitypeAll;
/* 873 */     String[] sBusinessOfPower = voPower.getPowerId();
/* 874 */     if ((sBusinessOfPower == null) || (sBusinessOfPower.length == 0))
/* 875 */       return null;
/* 876 */     HashSet setBusitypeOfPower = new HashSet();
/* 877 */     for (int i = 0; i < sBusinessOfPower.length; ++i) {
/* 878 */       setBusitypeOfPower.add(sBusinessOfPower[i]);
/*     */     }
/* 880 */     ArrayList listBusitype = new ArrayList();
/* 881 */     for (int i = 0; i < busitypeAll.length; ++i) {
/* 882 */       if (setBusitypeOfPower.contains(busitypeAll[i].getPrimaryKey()))
/* 883 */         listBusitype.add(busitypeAll[i]);
/*     */     }
/* 885 */     return ((BusitypeVO[])listBusitype.toArray(new BusitypeVO[0]));
/*     */   }
/*     */ 
/*     */   public PFBusiAddInfo getPFBillAndBusiInfo(String corpId, String billType) throws BusinessException
/*     */   {
/* 890 */     PFBusiAddInfo busiAddInfo = new PFBusiAddInfo();
/* 891 */     BusitypeVO[] busiVOS = getBusiByCorpAndBill(corpId, billType);
/* 892 */     busiAddInfo.setBusiTypeVos(busiVOS);
/* 893 */     if ((busiVOS != null) && (busiVOS.length > 0) && (busiVOS[0] != null)) {
/* 894 */       String businessType = busiVOS[0].getPrimaryKey();
/* 895 */       BillbusinessVO[] billbusiVOS = querybillSource(corpId, billType, businessType);
/* 896 */       busiAddInfo.setBillBusiVOs(billbusiVOS);
/*     */     }
/* 898 */     return busiAddInfo;
/*     */   }
/*     */ }

 