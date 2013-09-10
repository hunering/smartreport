/*     */ package nc.impl.uap.uapde;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import nc.bs.bd.cache.CacheProxy;
/*     */ import nc.bs.dao.BaseDAO;
/*     */ import nc.bs.dao.DAOException;
/*     */ import nc.bs.framework.common.NCLocator;
/*     */ import nc.bs.logging.Logger;
/*     */ import nc.bs.pf.pub.PfDataCache;
/*     */ import nc.bs.uap.sf.facility.SFServiceFacility;
/*     */ import nc.itf.uap.sf.IFuncRegisterQueryService;
/*     */ import nc.itf.uap.sf.IFuncRegisterService;
/*     */ import nc.itf.uap.uapde.IUAPDeUIQueryService;
/*     */ import nc.itf.uap.uapde.IUAPDeUIService;
/*     */ import nc.uapde.exception.UAPDEBusinessException;
/*     */ import nc.uapde.util.CollectionUtils;
/*     */ import nc.uapde.util.UapdeServiceFactory;
/*     */ import nc.uapde.util.lock.SuperVOBusinessLockUtil;
/*     */ import nc.vo.jcom.lang.StringUtil;
/*     */ import nc.vo.pub.BusinessException;
/*     */ import nc.vo.pub.SuperVO;
/*     */ import nc.vo.pub.billtype.BilltypeVO;
/*     */ import nc.vo.pub.lang.UFBoolean;
/*     */ import nc.vo.pub.pftemplate.SystemplateVO;
/*     */ import nc.vo.sm.createcorp.CodetocodeVO;
/*     */ import nc.vo.sm.funcreg.FuncRegisterVO;
/*     */ import nc.vo.uapde.ButtoninfoVO;
/*     */ import nc.vo.uapde.FunnodeuirelationVO;
/*     */ import nc.vo.uapde.SpecialCheckRuleVO;
/*     */ import nc.vo.uapde.TreeinfoVO;
/*     */ import nc.vo.uapde.UiResourceVO;
/*     */ import nc.vo.uapde.UibaseinfoVO;
/*     */ import nc.vo.uapde.UifactoryinfoVO;
/*     */ import nc.vo.uapde.UniquecheckruleVO;
/*     */ import nc.vo.uapde.componentvo.UapDeUIInfoVO;
/*     */ import nc.vo.uapde.publish.PublishDataVO;
/*     */ 
/*     */ public class UAPDeUIServiceImpl
/*     */   implements IUAPDeUIService
/*     */ {
/*     */   private final BaseDAO dao;
/*     */ 
/*     */   public UAPDeUIServiceImpl()
/*     */   {
/*  47 */     this.dao = new BaseDAO();
/*     */   }
/*     */ 
/*     */   public UapDeUIInfoVO insertModelDesignerUIInfoVO(UapDeUIInfoVO modelDesignerUIInfoVO)
/*     */     throws UAPDEBusinessException
/*     */   {
/*  53 */     UibaseinfoVO uibaseinfoVO = modelDesignerUIInfoVO.getUibaseInfoVo();
/*     */     try
/*     */     {
/*  56 */       String pk_uiBaseInfo = this.dao.insertVO(uibaseinfoVO);
/*  57 */       if (modelDesignerUIInfoVO.getUiFactoryInfovo() != null)
/*     */       {
/*  59 */         modelDesignerUIInfoVO.getUiFactoryInfovo().setFk_uibaseinfo(pk_uiBaseInfo);
/*  60 */         String pk_uiFactoryInfo = this.dao.insertVO(modelDesignerUIInfoVO.getUiFactoryInfovo());
/*  61 */         if (modelDesignerUIInfoVO.getTreeInfoVo() != null)
/*     */         {
/*  63 */           modelDesignerUIInfoVO.getTreeInfoVo().setFk_uifactoryinfo(pk_uiFactoryInfo);
/*  64 */           this.dao.insertVO(modelDesignerUIInfoVO.getTreeInfoVo());
/*     */         }
/*     */ 
/*  67 */         if (modelDesignerUIInfoVO.getLsEmptyCKRule().size() > 0)
/*     */         {
/*  69 */           for (SpecialCheckRuleVO vo : modelDesignerUIInfoVO.getLsEmptyCKRule())
/*  70 */             vo.setFk_uifactoryinfo(pk_uiFactoryInfo);
/*  71 */           this.dao.insertVOArray((SuperVO[])modelDesignerUIInfoVO.getLsEmptyCKRule().toArray(new SpecialCheckRuleVO[0]));
/*     */         }
/*     */ 
/*  74 */         if (modelDesignerUIInfoVO.getLsUniCKRule().size() > 0)
/*     */         {
/*  76 */           for (UniquecheckruleVO vo : modelDesignerUIInfoVO.getLsUniCKRule())
/*  77 */             vo.setFk_uifactoryinfo(pk_uiFactoryInfo);
/*  78 */           this.dao.insertVOArray((SuperVO[])modelDesignerUIInfoVO.getLsUniCKRule().toArray(new UniquecheckruleVO[0]));
/*     */         }
/*     */       }
/*     */ 
/*  82 */       UiResourceVO[] uiresVOs = (UiResourceVO[])(UiResourceVO[])modelDesignerUIInfoVO.getResourceList().toArray(new UiResourceVO[0]);
/*  83 */       for (int i = 0; i < uiresVOs.length; ++i) {
/*  84 */         uiresVOs[i].setFk_uibaseinfo(pk_uiBaseInfo);
/*     */       }
/*  86 */       this.dao.insertVOArray(uiresVOs);
/*     */ 
/*  88 */       ButtoninfoVO[] btninfoVOs = (ButtoninfoVO[])(ButtoninfoVO[])modelDesignerUIInfoVO.getBtnList().toArray(new ButtoninfoVO[0]);
/*  89 */       for (int i = 0; i < btninfoVOs.length; ++i) {
/*  90 */         btninfoVOs[i].setFk_uibaseinfo(pk_uiBaseInfo);
/*     */       }
/*  92 */       this.dao.insertVOArray(btninfoVOs);
/*     */     } catch (DAOException e) {
/*  94 */       Logger.error("Error", e);
/*  95 */       throw new UAPDEBusinessException("插入UI资源错误！");
/*     */     }
/*  97 */     return modelDesignerUIInfoVO;
/*     */   }
/*     */ 
/*     */   public void deleteUIInfoByBusiComponentPK(String busiComponentPk, boolean isDeleteFunnode) throws UAPDEBusinessException
/*     */   {
/* 104 */     String condition = "fk_componentinfo = '" + busiComponentPk + "'";
/*     */     Collection coll;
/*     */     try
/*     */     {
/* 107 */       coll = this.dao.retrieveByClause(UibaseinfoVO.class, condition);
/*     */     } catch (DAOException e) {
/* 109 */       Logger.error("error", e);
/* 110 */       throw new UAPDEBusinessException(e.getMessage());
/*     */     }
/* 112 */     if ((coll != null) && (coll.size() > 0)) {
/* 113 */       Iterator it = coll.iterator();
/* 114 */       while (it.hasNext()) {
/* 115 */         UibaseinfoVO uibaseInfoVo = (UibaseinfoVO)it.next();
/* 116 */         deleteModelDesignerUIInfoVOByVO(uibaseInfoVo, isDeleteFunnode);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public UapDeUIInfoVO updateModelDesignerUIInfoVO(UapDeUIInfoVO modelDesignerUIInfoVO)
/*     */     throws UAPDEBusinessException
/*     */   {
/* 126 */     SuperVOBusinessLockUtil util = SuperVOBusinessLockUtil.newInstance();
/* 127 */     SuperVO[] superVOs = { modelDesignerUIInfoVO.getUibaseInfoVo() };
/* 128 */     util.lock(superVOs);
/*     */     try {
/* 130 */       String pk_uiBaseInfo = modelDesignerUIInfoVO.getUibaseInfoVo().getPk_uibaseinfo();
/*     */ 
/* 132 */       String where = "fk_uiFactoryInfo = (select pk_uiFactoryInfo from mde_uifactoryinfo where fk_uiBaseInfo = '" + pk_uiBaseInfo + "')";
/* 133 */       if (modelDesignerUIInfoVO.getUiFactoryInfovo() == null)
/*     */       {
/* 135 */         this.dao.deleteByClause(TreeinfoVO.class, where);
/*     */ 
/* 137 */         this.dao.deleteByClause(UifactoryinfoVO.class, "fk_uiBaseInfo = '" + pk_uiBaseInfo + "'");
/*     */ 
/* 139 */         this.dao.deleteByClause(SpecialCheckRuleVO.class, where);
/*     */ 
/* 141 */         this.dao.deleteByClause(UniquecheckruleVO.class, where);
/*     */       }
/*     */       else {
/* 144 */         if (modelDesignerUIInfoVO.getTreeInfoVo() == null)
/* 145 */           this.dao.deleteByClause(TreeinfoVO.class, where);
/*     */         else {
/* 147 */           this.dao.updateVO(modelDesignerUIInfoVO.getTreeInfoVo());
/*     */         }
/*     */ 
/* 151 */         this.dao.deleteByClause(SpecialCheckRuleVO.class, where);
/* 152 */         this.dao.deleteByClause(UniquecheckruleVO.class, where);
/* 153 */         if (modelDesignerUIInfoVO.getLsEmptyCKRule().size() > 0)
/*     */         {
/* 155 */           for (SpecialCheckRuleVO vo : modelDesignerUIInfoVO.getLsEmptyCKRule())
/* 156 */             vo.setFk_uifactoryinfo(modelDesignerUIInfoVO.getUiFactoryInfovo().getPk_uifactoryinfo());
/* 157 */           this.dao.insertVOArray((SuperVO[])modelDesignerUIInfoVO.getLsEmptyCKRule().toArray(new SpecialCheckRuleVO[0]));
/*     */         }
/* 159 */         if (modelDesignerUIInfoVO.getLsUniCKRule().size() > 0)
/*     */         {
/* 161 */           for (UniquecheckruleVO vo : modelDesignerUIInfoVO.getLsUniCKRule())
/* 162 */             vo.setFk_uifactoryinfo(modelDesignerUIInfoVO.getUiFactoryInfovo().getPk_uifactoryinfo());
/* 163 */           this.dao.insertVOArray((SuperVO[])modelDesignerUIInfoVO.getLsUniCKRule().toArray(new UniquecheckruleVO[0]));
/*     */         }
/*     */ 
/* 167 */         this.dao.updateVO(modelDesignerUIInfoVO.getUiFactoryInfovo());
/*     */       }
/*     */ 
/* 170 */       this.dao.deleteByClause(ButtoninfoVO.class, "fk_uiBaseInfo = '" + pk_uiBaseInfo + "'");
/* 171 */       ButtoninfoVO[] btninfoVOs = (ButtoninfoVO[])(ButtoninfoVO[])modelDesignerUIInfoVO.getBtnList().toArray(new ButtoninfoVO[0]);
/* 172 */       for (int i = 0; i < btninfoVOs.length; ++i) {
/* 173 */         btninfoVOs[i].setFk_uibaseinfo(pk_uiBaseInfo);
/*     */       }
/* 175 */       this.dao.insertVOArray(btninfoVOs);
/*     */ 
/* 177 */       this.dao.deleteByClause(UiResourceVO.class, "fk_uiBaseInfo = '" + pk_uiBaseInfo + "'");
/* 178 */       UiResourceVO[] uiresVOs = (UiResourceVO[])(UiResourceVO[])modelDesignerUIInfoVO.getResourceList().toArray(new UiResourceVO[0]);
/* 179 */       for (int i = 0; i < uiresVOs.length; ++i) {
/* 180 */         uiresVOs[i].setFk_uibaseinfo(pk_uiBaseInfo);
/*     */       }
/* 182 */       this.dao.insertVOArray(uiresVOs);
/*     */ 
/* 184 */       this.dao.updateVO(modelDesignerUIInfoVO.getUibaseInfoVo());
/*     */     }
/*     */     catch (DAOException e) {
/* 187 */       throw new UAPDEBusinessException("更新UI资源错误！");
/*     */     }
/*     */     finally {
/* 190 */       util.unLock();
/*     */     }
/* 192 */     return modelDesignerUIInfoVO;
/*     */   }
/*     */ 
/*     */   private FunnodeuirelationVO insertFunnodeuirelationVO(FunnodeuirelationVO funnodeuirelationVO) throws UAPDEBusinessException
/*     */   {
/*     */     try {
/* 198 */       String pk_funnodeuirelation = this.dao.insertVO(funnodeuirelationVO);
/* 199 */       funnodeuirelationVO.setPk_funnodeuirelation(pk_funnodeuirelation);
/*     */     } catch (DAOException e) {
/* 201 */       Logger.error("Error", e);
/* 202 */       throw new UAPDEBusinessException("插入节点发布资源错误！");
/*     */     }
/* 204 */     return funnodeuirelationVO;
/*     */   }
/*     */ 
/*     */   private FunnodeuirelationVO updateFunnodeuirelationVO(FunnodeuirelationVO funnodeuirelationVO) throws UAPDEBusinessException
/*     */   {
/*     */     try {
/* 210 */       this.dao.updateVO(funnodeuirelationVO);
/*     */     } catch (DAOException e) {
/* 212 */       Logger.error("Error", e);
/* 213 */       throw new UAPDEBusinessException("更新节点发布资源错误！");
/*     */     }
/* 215 */     return funnodeuirelationVO;
/*     */   }
/*     */ 
/*     */   private void deleteFunnodeuirelationVOByPK(String pk_funnodeuirelation) throws UAPDEBusinessException
/*     */   {
/*     */     try {
/* 221 */       this.dao.deleteByPK(FunnodeuirelationVO.class, pk_funnodeuirelation);
/*     */     } catch (DAOException e) {
/* 223 */       Logger.error("Error", e);
/* 224 */       throw new UAPDEBusinessException("删除节点发布资源错误！");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void deleteModelDesignerUIInfoVOByPK(String pk_uiBaseInfo, boolean isDeleteFunnode) throws UAPDEBusinessException {
/* 229 */     String condition = "fk_uiBaseInfo = '" + pk_uiBaseInfo + "'";
/*     */     try
/*     */     {
/* 233 */       UapDeUIInfoVO uiInfoVO = UapdeServiceFactory.getUAPDeUIQueryService().getUIInfo(pk_uiBaseInfo);
/* 234 */       Iterator it = uiInfoVO.getFunnodeRelationVoList().iterator();
/* 235 */       while (it.hasNext()) {
/* 236 */         FunnodeuirelationVO funnodeuirelationVO = (FunnodeuirelationVO)it.next();
/* 237 */         deletePublishData(funnodeuirelationVO, isDeleteFunnode);
/*     */       }
/*     */ 
/* 240 */       Collection coll = this.dao.retrieveByClause(UifactoryinfoVO.class, condition);
/* 241 */       UifactoryinfoVO vo = (UifactoryinfoVO)CollectionUtils.getFirstObject(coll);
/*     */ 
/* 243 */       if (vo != null) {
/* 244 */         String con = "fk_uiFactoryInfo = '" + vo.getPrimaryKey() + "'";
/* 245 */         this.dao.deleteByClause(TreeinfoVO.class, con);
/* 246 */         this.dao.deleteByClause(UniquecheckruleVO.class, con);
/* 247 */         this.dao.deleteByClause(SpecialCheckRuleVO.class, con);
/*     */       }
/*     */ 
/* 250 */       this.dao.deleteByClause(UifactoryinfoVO.class, "fk_uiBaseInfo = '" + pk_uiBaseInfo + "'");
/*     */ 
/* 252 */       this.dao.deleteByClause(ButtoninfoVO.class, "fk_uiBaseInfo = '" + pk_uiBaseInfo + "'");
/*     */ 
/* 254 */       this.dao.deleteByClause(UiResourceVO.class, "fk_uiBaseInfo = '" + pk_uiBaseInfo + "'");
/*     */ 
/* 256 */       this.dao.deleteByPK(UibaseinfoVO.class, pk_uiBaseInfo);
/*     */     } catch (DAOException e) {
/* 258 */       Logger.error("Error", e);
/* 259 */       throw new UAPDEBusinessException("删除UI资源错误！");
/*     */     }
/*     */   }
/*     */ 
/*     */   private PublishDataVO insertFunReg(PublishDataVO publishDataVO) throws UAPDEBusinessException {
/* 264 */     String newOID = "";
/*     */     try {
/* 266 */       newOID = SFServiceFacility.getFuncRegisterService().insert(publishDataVO.getFunRegVO());
/* 267 */       processCodeToCode(publishDataVO.getFunRegVO().getFunCode());
/*     */     } catch (BusinessException e) {
/* 269 */       Logger.error("Error", e);
/* 270 */       throw new UAPDEBusinessException("注册节点出错！");
/*     */     }
/* 272 */     publishDataVO.setResultInfo(newOID);
/* 273 */     if (!(newOID.equals("1"))) {
/* 274 */       publishDataVO.getFunRegVO().setPrimaryKey(newOID);
/* 275 */       publishDataVO.getFunRegVO().setSubsystemId(newOID);
/*     */ 
/* 277 */       String funCode = publishDataVO.getFunRegVO().getFunCode();
/*     */ 
/* 279 */       if (publishDataVO.getFunRegVO().getForbidFlag().intValue() == 0) {
/*     */         try
/*     */         {
/* 282 */           SFServiceFacility.getFuncRegisterService().updateAllParentsEnabled(funCode, 2);
/*     */         } catch (BusinessException e) {
/* 284 */           Logger.error("Error", e);
/* 285 */           throw new UAPDEBusinessException("注册节点出错！");
/*     */         }
/*     */       }
/*     */ 
/* 289 */       if (publishDataVO.getFunRegVO().getGroupFlag().compareTo(Integer.valueOf(3)) <= 0) {
/*     */         try {
/* 291 */           SFServiceFacility.getFuncRegisterService().updatePropTypeOfAllParents(funCode, 2);
/*     */         } catch (BusinessException e) {
/* 293 */           Logger.error("Error", e);
/* 294 */           throw new UAPDEBusinessException("注册节点出错！");
/*     */         }
/*     */       }
/*     */ 
/* 298 */       if (publishDataVO.getFunnodeuirelationVO() == null)
/* 299 */         Logger.warn("insertFunReg:publishDataVO.getFunnodeuirelationVO()为空！");
/* 300 */       if (publishDataVO.getFunRegVO() == null) {
/* 301 */         Logger.warn("insertFunReg:publishDataVO.getFunRegVO()为空！");
/*     */       }
/* 303 */       publishDataVO.getFunnodeuirelationVO().setFunnode(publishDataVO.getFunRegVO().getFunCode());
/* 304 */       publishDataVO.getFunnodeuirelationVO().setFk_funregist(publishDataVO.getFunRegVO().getFunID());
/*     */     }
/* 306 */     return publishDataVO;
/*     */   }
/*     */ 
/*     */   private PublishDataVO updateFunReg(PublishDataVO publishDataVO) throws UAPDEBusinessException
/*     */   {
/* 311 */     IFuncRegisterQueryService funcRegisterQuery = (IFuncRegisterQueryService)NCLocator.getInstance().lookup(IFuncRegisterQueryService.class.getName());
/*     */     try {
/* 313 */       FuncRegisterVO funRegVO = funcRegisterQuery.findFuncRegisterVOByPrimaryKey(publishDataVO.getFunRegVO().getPrimaryKey());
/* 314 */       publishDataVO.setOldFunnode(funRegVO.getFunCode());
/*     */     } catch (BusinessException e) {
/* 316 */       Logger.error("Error", e);
/* 317 */       throw new UAPDEBusinessException("注册节点出错！");
/*     */     }
/*     */     int flag;
/*     */     try
/*     */     {
/* 322 */       flag = SFServiceFacility.getFuncRegisterService().update(publishDataVO.getFunRegVO());
/*     */     } catch (BusinessException e) {
/* 324 */       Logger.error("Error", e);
/* 325 */       throw new UAPDEBusinessException("注册节点出错！");
/*     */     }
/* 327 */     String funCode = publishDataVO.getFunRegVO().getFunCode();
/* 328 */     publishDataVO.setResultInfo(String.valueOf(flag));
/* 329 */     if ((flag != 2) && (flag != 1))
/*     */     {
/* 331 */       if (publishDataVO.getFunRegVO().getForbidFlag().intValue() == 1) {
/*     */         try {
/* 333 */           SFServiceFacility.getFuncRegisterService().updateAllChildrenForbidden(funCode);
/*     */         } catch (BusinessException e) {
/* 335 */           Logger.error("Error", e);
/* 336 */           throw new UAPDEBusinessException("注册节点出错！");
/*     */         }
/*     */       }
/*     */ 
/* 340 */       if (publishDataVO.getFunRegVO().getForbidFlag().intValue() == 0) {
/*     */         try {
/* 342 */           SFServiceFacility.getFuncRegisterService().updateAllParentsEnabled(funCode, 2);
/*     */         } catch (BusinessException e) {
/* 344 */           Logger.error("Error", e);
/* 345 */           throw new UAPDEBusinessException("注册节点出错！");
/*     */         }
/*     */       }
/*     */ 
/* 349 */       if (publishDataVO.getFunRegVO().getGroupFlag().compareTo(Integer.valueOf(3)) <= 0) {
/*     */         try {
/* 351 */           SFServiceFacility.getFuncRegisterService().updatePropTypeOfAllParents(funCode, 2);
/*     */         } catch (BusinessException e) {
/* 353 */           Logger.error("Error", e);
/* 354 */           throw new UAPDEBusinessException("注册节点出错！");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 359 */       publishDataVO.getFunnodeuirelationVO().setFunnode(publishDataVO.getFunRegVO().getFunCode());
/*     */     }
/* 361 */     return publishDataVO;
/*     */   }
/*     */ 
/*     */   public PublishDataVO savePublishDataVO(PublishDataVO publishDataVO) throws UAPDEBusinessException
/*     */   {
/* 366 */     SuperVO[] superVOs = { publishDataVO.getFunnodeuirelationVO() };
/* 367 */     SuperVOBusinessLockUtil util = SuperVOBusinessLockUtil.newInstance();
/* 368 */     util.lock(superVOs);
/*     */     try
/*     */     {
/*     */       PublishDataVO localPublishDataVO;
/* 371 */       if (publishDataVO.isNewPublish()) {
/* 372 */         publishDataVO = insertFunReg(publishDataVO);
/* 373 */         if (publishDataVO.getResultInfo().equals("1"))
/*     */         {
/* 375 */           localPublishDataVO = publishDataVO;
/*     */           return localPublishDataVO;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 378 */         publishDataVO = updateFunReg(publishDataVO);
/* 379 */         if ((publishDataVO.getResultInfo().equals("2")) || (publishDataVO.getResultInfo().equals("1")))
/*     */         {
/* 381 */           localPublishDataVO = publishDataVO;
/*     */           return localPublishDataVO;
/*     */         }
/*     */       }
/* 385 */       registeBtn(publishDataVO);
/*     */ 
/* 387 */       publishDataVO = saveFunnodeUIRelation(publishDataVO);
/*     */ 
/* 389 */       assignFunnodeDefaultTemplate(publishDataVO);
/*     */ 
/* 391 */       updateBillTypeNodecode(publishDataVO);
/*     */     }
/*     */     finally {
/* 394 */       util.unLock();
/*     */     }
/* 396 */     return publishDataVO;
/*     */   }
/*     */ 
/*     */   private void updateBillTypeNodecode(PublishDataVO publishDataVO) throws UAPDEBusinessException
/*     */   {
/*     */     try
/*     */     {
/* 403 */       if (StringUtil.isEmptyWithTrim(publishDataVO.getStrBillType())) {
/* 404 */         return;
/*     */       }
/* 406 */       BaseDAO dao = new BaseDAO();
/* 407 */       BilltypeVO billType = (BilltypeVO)dao.retrieveByPK(BilltypeVO.class, publishDataVO.getStrBillType());
/* 408 */       billType.setNodecode(publishDataVO.getFunRegVO().getFunCode());
/* 409 */       dao.updateVO(billType);
/* 410 */       CacheProxy.fireDataUpdated(PfDataCache.STR_BILLTYPEINFO, null);
/*     */     }
/*     */     catch (BusinessException e) {
/* 413 */       Logger.error("Error", e);
/* 414 */       throw new UAPDEBusinessException("更新单据类型nodecode时出错!原因:" + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void assignFunnodeDefaultTemplate(PublishDataVO publishDataVO) throws UAPDEBusinessException {
/* 419 */     if (!(publishDataVO.isNewPublish()))
/*     */     {
/* 421 */       String where = "funnode = '" + publishDataVO.getOldFunnode() + "' and pk_corp = '@@@@'";
/*     */       try {
/* 423 */         this.dao.deleteByClause(SystemplateVO.class, where);
/*     */       } catch (DAOException e) {
/* 425 */         Logger.error("Error", e);
/* 426 */         throw new UAPDEBusinessException("分配节点默认模板错误！");
/*     */       }
/*     */     }
/*     */     try {
/* 430 */       this.dao.insertVOArray((SuperVO[])publishDataVO.getLsSystemplateVO().toArray(new SystemplateVO[publishDataVO.getLsSystemplateVO().size()]));
/*     */     } catch (DAOException e) {
/* 432 */       Logger.error("Error", e);
/* 433 */       throw new UAPDEBusinessException("分配节点默认模板错误！");
/*     */     }
/*     */   }
/*     */ 
/*     */   private PublishDataVO saveFunnodeUIRelation(PublishDataVO publishDataVO) throws UAPDEBusinessException {
/* 438 */     if (publishDataVO.isNewPublish())
/*     */       try {
/* 440 */         publishDataVO.setFunnodeuirelationVO(insertFunnodeuirelationVO(publishDataVO.getFunnodeuirelationVO()));
/*     */       } catch (UAPDEBusinessException e) {
/* 442 */         Logger.error("Error", e);
/* 443 */         throw new UAPDEBusinessException("节点UI关联数据保存错误！");
/*     */       }
/*     */     else {
/*     */       try {
/* 447 */         publishDataVO.setFunnodeuirelationVO(updateFunnodeuirelationVO(publishDataVO.getFunnodeuirelationVO()));
/*     */       } catch (UAPDEBusinessException e) {
/* 449 */         Logger.error("Error", e);
/* 450 */         throw new UAPDEBusinessException("节点UI关联数据保存错误！");
/*     */       }
/*     */     }
/* 453 */     return publishDataVO;
/*     */   }
/*     */ 
/*     */   private void registeBtn(PublishDataVO publishDataVO) throws UAPDEBusinessException {
/*     */     try {
/* 458 */       String cfunid = publishDataVO.getFunnodeuirelationVO().getFk_funregist();
/* 459 */       if (!(publishDataVO.isNewPublish()))
/*     */       {
/* 462 */         String where = "parent_id = '" + cfunid + "'";
/* 463 */         FuncRegisterVO[] funcs = null;
/* 464 */         funcs = SFServiceFacility.getFuncRegisterQueryService().queryFuncWhere(where);
/* 465 */         if ((funcs != null) && (funcs.length > 0)) {
/* 466 */           for (int i = 0; i < funcs.length; ++i) {
/* 467 */             SFServiceFacility.getFuncRegisterService().deleteByVO(funcs[i]);
/*     */           }
/*     */         }
/*     */       }
/* 471 */       List ls = publishDataVO.getLsElseRegVO();
/* 472 */       if (publishDataVO.isNewPublish())
/*     */       {
/* 474 */         Iterator it = ls.iterator();
/* 475 */         while (it.hasNext()) {
/* 476 */           FuncRegisterVO funRegVO = (FuncRegisterVO)it.next();
/* 477 */           funRegVO.setParentId(cfunid);
/*     */         }
/*     */       }
/* 480 */       SFServiceFacility.getFuncRegisterService().insertButtonArray((FuncRegisterVO[])ls.toArray(new FuncRegisterVO[ls.size()]));
/*     */     } catch (BusinessException e) {
/* 482 */       Logger.error("Error", e);
/* 483 */       throw new UAPDEBusinessException("按钮注册错误！");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deletePublishData(FunnodeuirelationVO funnodeuirelationVO, boolean isDeleteFunnode)
/*     */     throws UAPDEBusinessException
/*     */   {
/* 490 */     deleteFunnodeuirelationVOByPK(funnodeuirelationVO.getPk_funnodeuirelation());
/*     */ 
/* 492 */     if (!(isDeleteFunnode)) {
/* 493 */       return;
/*     */     }
/*     */ 
/* 496 */     if (!(funnodeuirelationVO.getIscreatebytools().booleanValue())) {
/* 497 */       return;
/*     */     }
/*     */ 
/* 500 */     SuperVO[] superVOs = { funnodeuirelationVO };
/* 501 */     SuperVOBusinessLockUtil util = SuperVOBusinessLockUtil.newInstance();
/* 502 */     util.lock(superVOs);
/*     */     try
/*     */     {
/* 505 */       String where = "parent_id = '" + funnodeuirelationVO.getFk_funregist() + "'";
/* 506 */       FuncRegisterVO[] funcs = null;
/*     */       try {
/* 508 */         funcs = SFServiceFacility.getFuncRegisterQueryService().queryFuncWhere(where);
/* 509 */         if ((funcs != null) && (funcs.length > 0))
/* 510 */           for (int i = 0; i < funcs.length; ++i)
/* 511 */             SFServiceFacility.getFuncRegisterService().deleteByVO(funcs[i]);
/*     */       }
/*     */       catch (BusinessException ex)
/*     */       {
/* 515 */         Logger.error("Error", ex);
/* 516 */         throw new UAPDEBusinessException("删除注册按钮出错！");
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 522 */         FuncRegisterVO funRegVO = SFServiceFacility.getFuncRegisterQueryService().findFuncRegisterVOByPrimaryKey(funnodeuirelationVO.getFk_funregist());
/* 523 */         if (funRegVO != null)
/* 524 */           SFServiceFacility.getFuncRegisterService().deleteByVO(funRegVO);
/*     */       }
/*     */       catch (BusinessException ex) {
/* 527 */         Logger.error("Error", ex);
/* 528 */         throw new UAPDEBusinessException("删除注册节点出错！");
/*     */       }
/*     */ 
/* 532 */       deleteFunnodeDefaultTemplate(funnodeuirelationVO.getFunnode());
/*     */     }
/*     */     finally
/*     */     {
/* 536 */       util.unLock();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void deleteFunnodeDefaultTemplate(String funnode) throws UAPDEBusinessException {
/* 541 */     String where = "funnode = '" + funnode + "' and pk_corp = '@@@@'";
/*     */     try {
/* 543 */       this.dao.deleteByClause(SystemplateVO.class, where);
/*     */     } catch (DAOException e) {
/* 545 */       Logger.error("Error", e);
/* 546 */       throw new UAPDEBusinessException("删除节点默认模板出错！");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void processCodeToCode(String funcode) throws UAPDEBusinessException
/*     */   {
/* 552 */     String productCode = funcode.trim().substring(0, 2);
/* 553 */     BaseDAO dao = new BaseDAO();
/*     */     try {
/* 555 */       CodetocodeVO vo = (CodetocodeVO)dao.retrieveByPK(CodetocodeVO.class, productCode);
/* 556 */       if (vo == null)
/*     */       {
/* 558 */         vo = new CodetocodeVO();
/* 559 */         vo.setPrimaryKey(productCode);
/* 560 */         vo.setFunccode(productCode);
/* 561 */         vo.setModuletype(Integer.valueOf(1));
/* 562 */         vo.setIsleaf(new UFBoolean(false));
/* 563 */         dao.insertVOWithPK(vo);
/*     */       }
/*     */     }
/*     */     catch (DAOException e) {
/* 567 */       Logger.error(e.getMessage(), e);
/* 568 */       throw new UAPDEBusinessException("向表sm_codetocode插入记录时出错！");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteModelDesignerUIInfoVOByVO(UibaseinfoVO uibaseInfoVo, boolean isDeleteFunnode)
/*     */     throws UAPDEBusinessException
/*     */   {
/* 575 */     SuperVO[] superVOs = { uibaseInfoVo };
/* 576 */     SuperVOBusinessLockUtil util = SuperVOBusinessLockUtil.newInstance();
/* 577 */     util.lock(superVOs);
/*     */     try
/*     */     {
/* 580 */       deleteModelDesignerUIInfoVOByPK(uibaseInfoVo.getPk_uibaseinfo(), isDeleteFunnode);
/*     */     }
/*     */     finally {
/* 583 */       util.unLock();
/*     */     }
/*     */   }
/*     */ }

 