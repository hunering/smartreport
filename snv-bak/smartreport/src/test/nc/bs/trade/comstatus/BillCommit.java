/*     */ package nc.bs.trade.comstatus;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import nc.bs.dao.BaseDAO;
/*     */ import nc.bs.logging.Logger;
/*     */ import nc.bs.trade.business.HYSuperDMO;
/*     */ import nc.bs.trade.combase.HYComBase;
/*     */ import nc.itf.uap.pf.metadata.IFlowBizItf;
/*     */ import nc.jdbc.framework.SQLParameter;
/*     */ import nc.md.IMDQueryFacade;
/*     */ import nc.md.MDBaseQueryFacade;
/*     */ import nc.md.data.access.NCObject;
/*     */ import nc.md.model.IBean;
/*     */ import nc.md.model.ITable;
/*     */ import nc.md.model.MetaDataException;
/*     */ import nc.vo.pub.AggregatedValueObject;
/*     */ import nc.vo.pub.BusinessException;
/*     */ import nc.vo.pub.CircularlyAccessibleValueObject;
/*     */ import nc.vo.pub.SuperVO;
/*     */ import nc.vo.trade.pub.HYBillVO;
/*     */ import nc.vo.uap.pf.PFBusinessException;
/*     */ import nc.vo.uap.pf.PFRuntimeException;
/*     */ 
/*     */ public class BillCommit extends HYComBase
/*     */   implements IBillCommit
/*     */ {
/*     */   public final ArrayList commitBill(AggregatedValueObject billVo)
/*     */     throws BusinessException
/*     */   {
/*  43 */     IBean bean = null;
/*     */     try {
/*  45 */       bean = MDBaseQueryFacade.getInstance().getBeanByFullClassName(billVo.getParentVO().getClass().getName());
/*     */     }
/*     */     catch (MetaDataException e) {
/*  48 */       Logger.warn(e.getMessage(), e);
/*  49 */       bean = null;
/*     */     }
/*  51 */     if (bean == null) {
/*  52 */       return commitBillWithoutMeta(billVo);
/*     */     }
/*  54 */     return commitBillWithMeta(bean, billVo);
/*     */   }
/*     */ 
/*     */   private ArrayList commitBillWithMeta(IBean bean, AggregatedValueObject aggBillVo)
/*     */     throws BusinessException
/*     */   {
/*  69 */     ArrayList retList = new ArrayList();
/*     */     try
/*     */     {
/*  73 */       NCObject ncObj = NCObject.newInstance(bean, aggBillVo);
/*  74 */       IFlowBizItf fbi = (IFlowBizItf)ncObj.getBizInterface(IFlowBizItf.class);
/*  75 */       if (fbi == null)
/*  76 */         throw new PFRuntimeException("流程平台：单据实体没有提供业务接口IFlowBizInf的实现类");
/*  77 */       String strStatusColumn = fbi.getColumnName("approvestatus");
/*     */ 
/*  79 */       StringBuffer sbStr = new StringBuffer();
/*  80 */       sbStr.append("update ").append(bean.getTable().getName()).append(" set ");
/*  81 */       sbStr.append(strStatusColumn).append("=3");
/*  82 */       sbStr.append(" where ").append(bean.getTable().getPrimaryKeyName()).append("=? and ");
/*  83 */       sbStr.append(strStatusColumn).append("=8");
/*     */ 
/*  85 */       BaseDAO dao = new BaseDAO();
/*  86 */       String billId = aggBillVo.getParentVO().getPrimaryKey();
/*  87 */       SQLParameter param = new SQLParameter();
/*  88 */       param.addParam(billId);
/*  89 */       dao.executeUpdate(sbStr.toString(), param);
/*     */ 
/*  92 */       specialCommit(aggBillVo, new HYSuperDMO());
/*     */ 
/*  95 */       aggBillVo.setParentVO((CircularlyAccessibleValueObject)dao.retrieveByPK(aggBillVo.getParentVO().getClass(), billId));
/*     */ 
/*  99 */       retList.add(billId);
/* 100 */       retList.add(aggBillVo);
/* 101 */       return retList;
/*     */     } catch (BusinessException be) {
/* 103 */       Logger.error(be.getMessage(), be);
/* 104 */       throw be;
/*     */     } catch (Exception ex) {
/* 106 */       Logger.error(ex.getMessage(), ex);
/* 107 */       throw new PFBusinessException(ex.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private ArrayList commitBillWithoutMeta(AggregatedValueObject billVo)
/*     */     throws BusinessException
/*     */   {
/* 119 */     ArrayList retList = new ArrayList();
/*     */     try {
/* 121 */       SuperVO headVO = (SuperVO)billVo.getParentVO();
/* 122 */       HYSuperDMO dmo = new HYSuperDMO();
/* 123 */       if (billVo instanceof HYBillVO) {
/* 124 */         dmo.updateStatus(((HYBillVO)billVo).getM_billField(), headVO.getTableName(), headVO.getPKFieldName(), headVO.getPrimaryKey(), 3, 8);
/*     */       }
/*     */ 
/* 128 */       specialCommit(billVo, dmo);
/*     */ 
/* 131 */       billVo.setParentVO(dmo.queryByPrimaryKey(headVO.getClass(), headVO.getPrimaryKey()));
/*     */ 
/* 133 */       retList.add(headVO.getPrimaryKey());
/* 134 */       retList.add(billVo);
/* 135 */       return retList;
/*     */     } catch (BusinessException be) {
/* 137 */       Logger.error(be);
/* 138 */       throw be;
/*     */     } catch (Exception ex) {
/* 140 */       Logger.error(ex.getMessage(), ex);
				ex.printStackTrace();
/* 141 */       throw new BusinessException(ex.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void specialCommit(AggregatedValueObject vo, HYSuperDMO dmo)
/*     */     throws Exception
/*     */   {
/*     */   }
/*     */ }
 