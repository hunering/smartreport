package nc.impl.uif.pub;

 
 import java.lang.reflect.Array;
 import java.util.ArrayList;
 import java.util.Hashtable;
 import java.util.List;
 import java.util.Map;
 import java.util.Vector;
 import nc.bs.bd.def.DefBO;
 import nc.bs.dao.DAOException;
 import nc.bs.logging.Logger;
 import nc.bs.ml.NCLangResOnserver;
 import nc.bs.pub.SuperDMO;
 import nc.bs.pub.billcodemanage.BillcodeGenerater;
 import nc.bs.pub.billcodemanage.BillcodeRuleBO;
 import nc.bs.pub.pf.PfUtilTools;
 import nc.bs.pub.pflock.IPfBusinessLock;
 import nc.bs.pub.pflock.PfBusinessLock;
 import nc.bs.trade.accperiod.AccountPeriod;
 import nc.bs.trade.billsource.IBillFinder;
 import nc.bs.trade.business.HYSuperDMO;
 import nc.bs.trade.comdelete.BillDelete;
 import nc.bs.trade.comsave.BillSave;
 import nc.bs.trade.comstatus.BillCommit;
 import nc.bs.trade.lock.BDConsistenceCheck;
 import nc.bs.trade.lock.BDLockData;
 import nc.itf.uif.pub.IUifService;
 import nc.uif.pub.exception.UifException;
 import nc.vo.bd.def.DefVO;
 import nc.vo.bd.period3.AccperiodquarterVO;
 import nc.vo.pub.AggregatedValueObject;
 import nc.vo.pub.BusinessException;
 import nc.vo.pub.CircularlyAccessibleValueObject;
 import nc.vo.pub.SuperVO;
 import nc.vo.pub.billcodemanage.BillCodeObjValueVO;
 import nc.vo.trade.billsource.LightBillVO;
 import nc.vo.trade.pub.IExAggVO;
 
 public class UifServiceImp
   implements IUifService
 {
   public void checkConsistence(AggregatedValueObject billVo)
     throws UifException
   {
     try
     {
       SuperDMO dmo = new SuperDMO();
       if (billVo.getParentVO().getAttributeValue("ts") == null)
         return;
       SuperVO dbParentVO = dmo.queryByPrimaryKey(billVo.getParentVO().getClass(), billVo.getParentVO().getPrimaryKey());
       if (dbParentVO == null) {
         throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000002"));
       }
 
       if (!(dbParentVO.getAttributeValue("ts").equals(billVo.getParentVO().getAttributeValue("ts")))) {
         throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000003"));
       }
     }
     catch (BusinessException be)
     {
       be.printStackTrace();
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000004"));
     }
     catch (Exception e)
     {
       e.printStackTrace();
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000005"));
     }
   }
 
   public ArrayList commitBill(AggregatedValueObject billVo)
     throws UifException
   {
     BillCommit billcommit = new BillCommit();
     try {
       return billcommit.commitBill(billVo);
     } catch (BusinessException be) {
       Logger.error(be);
       throw new UifException(be.getMessage(), be);
     } catch (Exception ex) {
       Logger.error(ex.getMessage(), ex);
       throw new UifException(ex.getMessage());
     }
   }
 
   public void delete(SuperVO vo)
     throws UifException
   {
     try
     {
       SuperDMO dmo = new SuperDMO();
       dmo.delete(vo);
     }
     catch (DAOException de) {
       Logger.error(de.getMessage(), de);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000006"));
     }
     catch (Exception e)
     {
       Logger.error(e.getMessage(), e);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000005"));
     }
   }
 
   public AggregatedValueObject deleteBD(AggregatedValueObject billVo, Object userObj)
     throws UifException
   {
     IPfBusinessLock bdLock = new PfBusinessLock();
     try {
       bdLock.lock(new BDLockData(billVo), new BDConsistenceCheck(billVo));
       BillDelete billdelete = new BillDelete();
       AggregatedValueObject localAggregatedValueObject = billdelete.deleteBD(billVo, userObj);
 
       return localAggregatedValueObject;
     }
     catch (BusinessException be)
     {
       throw new UifException(be.getMessage());
     }
     catch (Exception ex) {
       throw new UifException(ex.getMessage(), ex);
     } finally {
       bdLock.unLock();
     }
   }
 
   public AggregatedValueObject deleteBill(AggregatedValueObject billVo)
     throws UifException
   {
     BillDelete billdelete = new BillDelete();
     try {
       return billdelete.deleteBill(billVo);
     } catch (BusinessException e) {
       Logger.error(e.getMessage(), e);
       throw new UifException(e.getMessage());
     }
   }
 
   public void deleteByWhereClause(Class c, String where)
     throws UifException
   {
     try
     {
       SuperDMO dmo = new SuperDMO();
       dmo.deleteByWhereClause(c, where);
     } catch (DAOException de) {
       Logger.error(de.getMessage(), de);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000006"));
     }
     catch (Exception e)
     {
       Logger.error(e.getMessage(), e);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000005"));
     }
   }
 
   public Object findColValue(String tablename, String fieldname, String strwhere)
     throws UifException
   {
     Object o = null;
     try
     {
       HYSuperDMO dmo = new HYSuperDMO();
       o = dmo.getCommonData(tablename, fieldname, strwhere);
     } catch (DAOException de) {
       Logger.error(de.getMessage(), de);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000006"));
     }
     catch (Exception e)
     {
       Logger.error(e.getMessage(), e);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000005"));
     }
 
     return o;
   }
 
   public String[] getAllPeriodByQuarter(String year_quarter)
     throws UifException
   {
     String[] allPeriod = null;
     try
     {
       AccountPeriod acc = new AccountPeriod();
       String year = year_quarter.substring(0, 4);
       int quarter = new Integer(year_quarter.substring(5, year_quarter.length())).intValue();
       AccperiodquarterVO vo = acc.findByYearAndQuarter(year, quarter);
       String beginPeriod = year + "_" + vo.getBeginmonth();
       String endPeriod = year + "_" + vo.getEndmonth();
       allPeriod = getMiddlePeriod(beginPeriod, endPeriod);
     } catch (Exception e) {
       Logger.error(e.getMessage(), e);
       throw new UifException(e.getMessage());
     }
     return allPeriod;
   }
 
   public String[] getAllPeriodByQuarter(String begin_year_quarter, String end_year_quarter)
     throws UifException
   {
     String[] allPeriod = null;
     try {
       AccountPeriod acc = new AccountPeriod();
       allPeriod = acc.getAllPeriodByQuarter(begin_year_quarter, end_year_quarter);
     } catch (Exception e) {
       Logger.error(e.getMessage(), e);
       throw new UifException(e.getMessage());
     }
     return allPeriod;
   }
 
   public String[] getAllPeriodByYear(String year_quarter)
     throws UifException
   {
     String[] allPeriod = null;
     try {
       allPeriod = new AccountPeriod().getAllPeriodByYear(year_quarter);
     } catch (Exception e) {
       Logger.error(e.getMessage(), e);
       throw new UifException(e.getMessage());
     }
     return allPeriod;
   }
 
   public String[] getAllPeriodByYear(String beginYear, String endYear)
     throws UifException
   {
     String[] allPeriod = null;
     try {
       AccountPeriod acc = new AccountPeriod();
       allPeriod = acc.getAllPeriodByYear(beginYear, endYear);
     } catch (Exception e) {
       Logger.error(e.getMessage(), e);
       throw new UifException(e.getMessage());
     }
     return allPeriod;
   }
 
   public String[] getAllQuarterByYear(String year)
     throws UifException
   {
     String[] allQuarter = null;
     try {
       allQuarter = new AccountPeriod().getAllQuarterByYear(year);
     } catch (Exception e) {
       Logger.error(e.getMessage(), e);
       throw new UifException(e.getMessage());
     }
     return allQuarter;
   }
 
   public String[] getAllQuarterByYear(String beginYear, String endYear)
     throws UifException
   {
     String[] allQuarter = null;
     try {
       AccountPeriod acc = new AccountPeriod();
       allQuarter = acc.getAllQuarterByYear(beginYear, endYear);
     } catch (Exception e) {
       Logger.error(e.getMessage(), e);
       throw new UifException(e.getMessage());
     }
     return allQuarter;
   }
 
   public String[] getBatchBillNo(String billtype, String pk_corp, BillCodeObjValueVO vo, int intNum)
     throws UifException
   {
     try
     {
       return new BillcodeRuleBO().getBatchBillCodes(billtype, pk_corp, vo, intNum);
     } catch (Exception e) {
       Logger.error(e.getMessage(), e);
       throw new UifException(e.getMessage());
     }
   }
 
   public String getBillNo(String billtype, String pk_corp, String custBillCode, BillCodeObjValueVO vo)
     throws UifException
   {
     try
     {
       return new BillcodeGenerater().getBillCode(billtype, pk_corp, custBillCode, vo);
     } catch (Exception e) {
       Logger.error(e.getMessage(), e);
       throw new UifException("ExMsg", e);
     }
   }
 
   public String[] getMiddlePeriod(String beginPeriod, String endPeriod)
     throws UifException
   {
     String[] period = null;
     try {
       period = new AccountPeriod().getMiddlePeriod(beginPeriod, endPeriod);
     } catch (Exception e) {
       Logger.error(e.getMessage(), e);
       throw new UifException(e.getMessage());
     }
     return period;
   }
 
   public String[] getMiddleQuarter(String beginQuarter, String endQuarter)
     throws UifException
   {
     String[] quarter = null;
     try {
       quarter = new AccountPeriod().getMiddleQuarter(beginQuarter, endQuarter);
     } catch (Exception e) {
       Logger.error(e.getMessage(), e);
       throw new UifException(e.getMessage());
     }
     return quarter;
   }
 
   public String[] getMiddleYear(String beginYear, String endYear)
     throws UifException
   {
     String[] year = null;
     try {
       year = AccountPeriod.getMiddleYear(beginYear, endYear);
     } catch (Exception e) {
       Logger.error(e.getMessage(), e);
       throw new UifException(e.getMessage());
     }
     return year;
   }
 
   public String[] getPreMonth(String year, String month)
     throws UifException
   {
     String[] premonth = null;
     try {
       premonth = new AccountPeriod().getPreMonth(year, month);
     } catch (Exception e) {
       Logger.error(e.getMessage(), e);
       throw new UifException(e.getMessage());
     }
     return premonth;
   }
 
   public String getQuarterByPeriod(String year_period)
     throws UifException
   {
     String year_quarter = null;
     try {
       year_quarter = new AccountPeriod().getQuarterByPeriod(year_period);
     } catch (Exception e) {
       Logger.error(e.getMessage(), e);
       throw new UifException(e.getMessage());
     }
     return year_quarter;
   }
 
   public String insert(SuperVO vo)
     throws UifException
   {
     String key = null;
     try {
       SuperDMO dmo = new SuperDMO();
       key = dmo.insert(vo);
     } catch (DAOException de) {
       Logger.error(de.getMessage(), de);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000006"), new BusinessException(de.getMessage()));
     }
     catch (Exception e)
     {
       Logger.error(e.getMessage(), e);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000005"), new BusinessException(e.getMessage()));
     }
 
     return key;
   }
 
   public String[] insertAry(SuperVO[] vo)
     throws UifException
   {
     String[] key = null;
     try {
       SuperDMO dmo = new SuperDMO();
       key = dmo.insertArray(vo);
     } catch (DAOException de) {
       Logger.error(de.getMessage(), de);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000006") + " " + de.getMessage());
     }
     catch (Exception e)
     {
       Logger.error(e.getMessage(), e);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000005"), new BusinessException(e.getMessage()));
     }
 
     return key;
   }
 
   public SuperVO[] queryAll(Class voClass) throws UifException {
     SuperVO[] vos = null;
     try {
       SuperDMO dmo = new SuperDMO();
       vos = dmo.queryAll(voClass);
     } catch (DAOException de) {
       Logger.error(de.getMessage(), de);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000006"));
     }
     catch (Exception e)
     {
       Logger.error(e.getMessage(), e);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000005"), new BusinessException(e.getMessage()));
     }
 
     return vos;
   }
 
   public CircularlyAccessibleValueObject[] queryAllBodyData(String strBillType, Class c, String key, String strWhere)
     throws UifException
   {
     CircularlyAccessibleValueObject[] vo = null;
     try {
       HYSuperDMO dmo = new HYSuperDMO();
       vo = dmo.queryAllBodyData(strBillType, c, key, strWhere);
     } catch (Exception e) {
       Logger.error(e.getMessage(), e);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000005"), new BusinessException(e.getMessage()));
     }
 
     return vo;
   }
 
   public LightBillVO queryBillGraph(String billFinderClassName, String id, String type)
     throws UifException
   {
     try
     {
       IBillFinder billFinder = (IBillFinder)PfUtilTools.findBizImplOfBilltype(type, billFinderClassName);
 
       return billFinder.queryBillGraph(id, type);
     } catch (BusinessException be) {
       Logger.error(be.getMessage(), be);
       throw new UifException(be.getMessage(), be);
     }
     catch (Exception ex)
     {
       Logger.error(ex.getMessage(), ex);
       throw new UifException(ex.getMessage(), ex);
     }
   }
 
   public AggregatedValueObject[] queryBillVOByCondition(Object userObj, String strWhere)
     throws UifException
   {
     AggregatedValueObject[] vos = null;
     try
     {
       if (!(userObj instanceof String[])) {
         throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000007"));
       }
 
       String[] cls = (String[])(String[])userObj;
 
       Class AggCls = Class.forName(cls[0]);
       Class parentCls = Class.forName(cls[1]);
 
       SuperVO[] parentVOs = queryByCondition(parentCls, strWhere);
 
       if ((parentVOs == null) || (parentVOs.length == 0)) {
         return null;
       }
       Hashtable billHash = new Hashtable();
       for (int i = 0; i < parentVOs.length; ++i) {
         AggregatedValueObject aggVO = (AggregatedValueObject)AggCls.newInstance();
         aggVO.setParentVO(parentVOs[i]);
         billHash.put(parentVOs[i].getPrimaryKey(), aggVO);
       }
 
       String[] tableCode = null;
       if (cls.length > 3) {
         tableCode = ((IExAggVO)AggCls.newInstance()).getTableCodes();
       }
 
       for (int j = 2; j < cls.length; ++j) {
         Class itemCls = Class.forName(cls[j]);
         SuperVO item = (SuperVO)Class.forName(cls[j]).newInstance();
         String subTableFPKName = item.getParentPKFieldName();
         String mainTablePKName = parentVOs[0].getPKFieldName();
         String mainTableName = parentVOs[0].getTableName();
 
         if ((subTableFPKName == null) || (subTableFPKName.trim().equals(""))) {
           throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000500", null, new String[] { cls[j] }));
         }
 
         SuperVO[] items = querySubvoByClause(itemCls, mainTableName, mainTablePKName, subTableFPKName, strWhere);
 
         if (items == null) continue; if (items.length == 0) {
           continue;
         }
 
         Hashtable hash = new Hashtable();
         for (int i = 0; i < items.length; ++i) {
           String parentKey = items[i].getAttributeValue(subTableFPKName).toString();
           if (hash.containsKey(parentKey)) {
             Vector v = (Vector)hash.get(parentKey);
             v.add(items[i]);
             hash.put(parentKey, v);
           } else {
             Vector v = new Vector();
             v.addElement(items[i]);
             hash.put(parentKey, v);
           }
         }
 
         for (int i = 0; i < parentVOs.length; ++i) {
           if (hash.containsKey(parentVOs[i].getPrimaryKey())) {
             AggregatedValueObject bill = (AggregatedValueObject)billHash.get(parentVOs[i].getPrimaryKey());
             Vector v = (Vector)hash.get(parentVOs[i].getPrimaryKey());
             SuperVO[] children = (SuperVO[])(SuperVO[])Array.newInstance(itemCls, v.size());
             v.copyInto(children);
 
             if (cls.length > 3) {
               String code = tableCode[(j - 2)];
               ((IExAggVO)bill).setTableVO(code, children);
             } else {
               bill.setChildrenVO(children);
             }
           }
         }
       }
 
       vos = (AggregatedValueObject[])(AggregatedValueObject[])Array.newInstance(AggCls, parentVOs.length);
       for (int i = 0; i < parentVOs.length; ++i) {
         if (billHash.containsKey(parentVOs[i].getPrimaryKey()))
           vos[i] = ((AggregatedValueObject)billHash.get(parentVOs[i].getPrimaryKey()));
       }
     }
     catch (Exception e)
     {
       Logger.error(e.getMessage(), e);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000008"), e);
     }
 
     return vos;
   }
 
   public AggregatedValueObject queryBillVOByPrimaryKey(Object userObj, String parentPK)
     throws UifException
   {
     try
     {
       if ((parentPK == null) || (parentPK.trim().equals(""))) {
         throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000009"));
       }
 
       if (!(userObj instanceof String[])) {
         throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000007"));
       }
 
       String[] cls = (String[])(String[])userObj;
 
       Class AggCls = Class.forName(cls[0]);
       Class parentCls = Class.forName(cls[1]);
 
       SuperVO parentVO = queryByPrimaryKey(parentCls, parentPK);
 
       if (parentVO == null) {
         return null;
       }
       AggregatedValueObject aggVO = (AggregatedValueObject)AggCls.newInstance();
       aggVO.setParentVO(parentVO);
 
       String[] tableCode = null;
       if (cls.length > 3) {
         tableCode = ((IExAggVO)AggCls.newInstance()).getTableCodes();
       }
 
       for (int j = 2; j < cls.length; ++j) {
         Class itemCls = Class.forName(cls[j]);
         SuperVO item = (SuperVO)Class.forName(cls[j]).newInstance();
         String key = item.getParentPKFieldName();
 
         if ((key == null) || (key.trim().equals(""))) {
           throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000500", null, new String[] { cls[j] }));
         }
 
         String whereStr = key + " = '" + parentVO.getPrimaryKey() + "'";
 
         SuperVO[] items = queryByCondition(itemCls, whereStr);
 
         if (cls.length > 3) {
           String code = tableCode[(j - 2)];
           ((IExAggVO)aggVO).setTableVO(code, items);
         } else {
           aggVO.setChildrenVO(items);
         }
       }
       return aggVO;
     }
     catch (Exception e)
     {
       Logger.error(e.getMessage(), e);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000008"));
     }
   }
 
   public SuperVO[] queryByCondition(Class voClass, String strWhere)
     throws UifException
   {
     SuperVO[] vo = null;
     try {
       SuperDMO dmo = new SuperDMO();
 
       strWhere = getWhereClause(strWhere);
 
       vo = dmo.queryByWhereClause(voClass, strWhere);
     } catch (DAOException de) {
       Logger.error(de.getMessage(), de);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000006"));
     }
     catch (Exception e)
     {
       Logger.error(e.getMessage(), e);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000005"));
     }
 
     return vo;
   }
 
   private String getWhereClause(String strWhere)
   {
     if ((strWhere != null) && (strWhere.length() != 0))
       strWhere = " (isnull(dr,0)=0) and " + strWhere;
     else
       strWhere = "isnull(dr,0)=0";
     return strWhere;
   }
 
   private SuperVO[] querySubvoByClause(Class clazz, String mainTableName, String mainTablePKname, String subFPKName, String mainTableStrWhere)
     throws UifException
   {
     String where = " " + subFPKName + " in ( select " + mainTablePKname + " from " + mainTableName + " where " + getWhereClause(mainTableStrWhere) + ")";
     where = getWhereClause(where);
     return queryByCondition(clazz, where);
   }
 
   public SuperVO queryByPrimaryKey(Class voClass, String pk) throws UifException {
     SuperVO vo = null;
     try {
       SuperDMO dmo = new SuperDMO();
       vo = dmo.queryByPrimaryKey(voClass, pk);
     }
     catch (DAOException de) {
       Logger.error(de.getMessage(), de);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000006"));
     }
     catch (Exception e)
     {
       Logger.error(e.getMessage(), e);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000005"));
     }
 
     return vo;
   }
 
   public ArrayList queryDefVO(String[] objNames, String pk_corp)
     throws UifException
   {
     ArrayList result = new ArrayList();
     try {
       DefBO def = new DefBO();
       Map map = def.queryDefVOs(objNames, pk_corp);
       for (String objName : objNames) {
         List voList = (List)map.get(objName);
         DefVO[] array = (voList == null) ? null : (DefVO[])voList.toArray(new DefVO[0]);
         result.add(array);
       }
     } catch (Exception e) {
       Logger.error(e.getMessage(), e);
       throw new UifException(e.getMessage());
     }
     return result;
   }
 
   public AggregatedValueObject saveBD(AggregatedValueObject billVo, Object userObj)
     throws UifException
   {
     IPfBusinessLock bdLock = new PfBusinessLock();
     try {
       bdLock.lock(new BDLockData(billVo), new BDConsistenceCheck(billVo));
       BillSave billsave = new BillSave();
       AggregatedValueObject localAggregatedValueObject = billsave.saveBD(billVo, userObj);
 
       return localAggregatedValueObject;
     }
     catch (BusinessException be)
     {
    	 be.printStackTrace();
       throw new UifException(be.getMessage());
     }
     catch (Exception ex)
     {
       throw new UifException(ex.getMessage());
     } finally {
       bdLock.unLock();
     }
   }
 
   public AggregatedValueObject[] saveBDs(AggregatedValueObject[] billVos, Object userObj)
     throws UifException
   {
     BillSave billsave = new BillSave();
     try {
       return billsave.saveBDs(billVos, userObj);
     } catch (Exception e) {
       Logger.error(e.getMessage(), e);
       throw new UifException(e.getMessage());
     }
   }
 
   public AggregatedValueObject saveBill(AggregatedValueObject billVo)
     throws UifException
   {
     BillSave billsave = new BillSave();
     try {
       return billsave.saveBillCom(billVo);
     } catch (Exception e) {
       Logger.error(e.getMessage(), e);
       throw new UifException(e.getMessage());
     }
   }
 
   public AggregatedValueObject setBillTs(AggregatedValueObject billVo)
     throws UifException
   {
     try
     {
       SuperVO headVO = (SuperVO)billVo.getParentVO();
 
       billVo.setParentVO(queryByPrimaryKey(headVO.getClass(), headVO.getPrimaryKey()));
 
       return billVo;
     } catch (Exception ex) {
       Logger.error(ex.getMessage(), ex);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000010") + ex.getMessage());
     }
   }
 
   public void update(SuperVO vo)
     throws UifException
   {
     try
     {
       SuperDMO dmo = new SuperDMO();
       dmo.update(vo);
     } catch (DAOException de) {
       Logger.error(de.getMessage(), de);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000006"));
     }
     catch (Exception e)
     {
       Logger.error(e.getMessage(), e);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000005"));
     }
   }
 
   public void updateAry(SuperVO[] vo)
     throws UifException
   {
     try
     {
       SuperDMO dmo = new SuperDMO();
       dmo.updateArray(vo);
     } catch (DAOException de) {
       Logger.error(de.getMessage(), de);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000006"));
     }
     catch (Exception e)
     {
       Logger.error(e.getMessage(), e);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000005"));
     }
   }
 
   public SuperVO[] queryByConditionWithoutConstr(Class voClass, String strWhere, int maxrowcount)
     throws UifException
   {
     SuperVO[] vo = null;
     try {
       SuperDMO dmo = new SuperDMO();
       dmo.setMaxRows(maxrowcount);
 
       strWhere = getWhereClause(strWhere);
 
       vo = dmo.queryByWhereClause(voClass, strWhere);
     } catch (DAOException de) {
       Logger.error(de.getMessage(), de);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000006"));
     }
     catch (Exception e)
     {
       Logger.error(e.getMessage(), e);
       throw new UifException(NCLangResOnserver.getInstance().getStrByID("uifactory", "UPPuifactory-000005"));
     }
 
     return vo;
   }
 }

