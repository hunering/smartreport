/*      */ package nc.bs.pub.pf;
/*      */ 
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import nc.bs.dao.BaseDAO;
/*      */ import nc.bs.dao.DAOException;
/*      */ import nc.bs.pf.pub.PfDataCache;
/*      */ import nc.bs.uap.oid.OidGenerator;
/*      */ import nc.jdbc.framework.JdbcSession;
/*      */ import nc.jdbc.framework.PersistenceManager;
/*      */ import nc.jdbc.framework.SQLParameter;
/*      */ import nc.jdbc.framework.exception.DbException;
/*      */ import nc.jdbc.framework.processor.BaseProcessor;
/*      */ import nc.jdbc.framework.processor.BeanListProcessor;
/*      */ import nc.jdbc.framework.processor.ColumnListProcessor;
/*      */ import nc.jdbc.framework.processor.ColumnProcessor;
/*      */ import nc.vo.jcom.lang.StringUtil;
/*      */ import nc.vo.pf.change.PfUtilBaseTools;
/*      */ import nc.vo.pf.pub.BusitypeVO;
/*      */ import nc.vo.pub.BusinessException;
/*      */ import nc.vo.pub.billtype.BilltypeVO;
/*      */ import nc.vo.pub.billtype.DefitemVO;
/*      */ import nc.vo.pub.lang.UFBoolean;
/*      */ import nc.vo.pub.pf.PfPOArriveVO;
/*      */ import nc.vo.pub.pf.PfUtilActionConstrictVO;
/*      */ import nc.vo.pub.pf.PfUtilActionVO;
/*      */ import nc.vo.pub.pf.PfUtilBillActionVO;
/*      */ import nc.vo.pub.pf.Pfi18nTools;
/*      */ import nc.vo.pub.pfflow01.BillbusinessVO;
/*      */ import nc.vo.trade.voutils.VOUtil;
/*      */ 
/*      */ public class PfUtilDMO
/*      */ {
/*      */   public void insertBillitems(DefitemVO[] defItemVOs)
/*      */     throws DAOException
/*      */   {
/*   58 */     if ((defItemVOs == null) || (defItemVOs.length == 0)) {
/*   59 */       return;
/*      */     }
/*   61 */     String[] oids = OidGenerator.getInstance().nextOid("0001", defItemVOs.length);
/*   62 */     for (int i = 0; i < defItemVOs.length; ++i)
/*      */     {
/*   64 */       defItemVOs[i].setItemname(oids[i]);
/*   65 */       defItemVOs[i].setPk_voitem(oids[i]);
/*      */     }
/*   67 */     BaseDAO dao = new BaseDAO();
/*   68 */     dao.insertVOArrayWithPK(defItemVOs);
/*      */   }
/*      */ 
/*      */   public boolean queryLastStep(String billType, String actionName)
/*      */     throws DbException
/*      */   {
/*   79 */     boolean retflag = false;
/*   80 */     String sql = "select finishflag from pub_billaction where pk_billType=? and actiontype=?";
/*   81 */     PersistenceManager persist = null;
/*      */     try {
/*   83 */       persist = PersistenceManager.getInstance();
/*   84 */       JdbcSession jdbc = persist.getJdbcSession();
/*      */ 
/*   86 */       SQLParameter para = new SQLParameter();
/*   87 */       para.addParam(billType);
/*   88 */       para.addParam(actionName);
/*   89 */       Object obj = jdbc.executeQuery(sql, para, new ColumnProcessor(1));
/*   90 */       if (String.valueOf(obj).equals("Y"))
/*   91 */         retflag = true;
/*      */     }
/*      */     finally {
/*   94 */       if (persist != null) {
/*   95 */         persist.release();
/*      */       }
/*      */     }
/*   98 */     return retflag;
/*      */   }
/*      */ 
/*      */   public PfUtilActionConstrictVO[] queryActionConstrict(String billType, String businessType, String actionName, String corpId, String operator)
/*      */     throws DbException
/*      */   {
/*  119 */     String sql = "select configflag,operator,a.classname as aclassname, a.returntype as areturntype,a.functionnote as afunctionnote,  a.method as amethod,a.parameter as aparameter,ysf,value,  b.classname as bclassname,b.functionnote as bfunctionnote,  b.method as bmethod,b.parameter as bparameter,errhintmsg,c.isbefore  from pub_actionconstrict c inner join pub_function a on  c.functionid=a.pk_function left join pub_function b on c.value=b.pk_function  where (pk_corp=? or pk_corp='@@@@')  and c.pk_billtype=? and actiontype=? and pk_businesstype=?  and (configflag=1 or (configflag=2 and operator=?)  or (configflag=3 and operator in(select pk_role from sm_user_role where cuserid=? and pk_corp=?))  ) order by c.sysindex";
/*      */ 
/*  139 */     PersistenceManager persist = null;
/*      */     try {
/*  141 */       persist = PersistenceManager.getInstance();
/*  142 */       JdbcSession jdbc = persist.getJdbcSession();
/*  143 */       SQLParameter para = new SQLParameter();
/*      */ 
/*  146 */       para.addParam(corpId);
/*  147 */       para.addParam(billType);
/*  148 */       para.addParam(actionName);
/*  149 */       para.addParam(businessType);
/*  150 */       para.addParam(operator);
/*  151 */       para.addParam(operator);
/*  152 */       para.addParam(corpId);
/*  153 */       List lResult = (List)jdbc.executeQuery(sql, para, new BaseProcessor() {
/*      */         public Object processResultSet(ResultSet rs) throws SQLException {
/*  155 */           ArrayList al = new ArrayList();
/*  156 */           while (rs.next()) {
/*  157 */             PfUtilActionConstrictVO actionConstrict = new PfUtilActionConstrictVO();
/*  158 */             String tmpString = null;
/*      */ 
/*  160 */             int configFlag = rs.getInt("configflag");
/*  161 */             actionConstrict.setConfigFlag(configFlag);
/*      */ 
/*  163 */             tmpString = rs.getString("operator");
/*  164 */             if (tmpString != null)
/*  165 */               actionConstrict.setRelaPk(tmpString.trim());
/*      */             else {
/*  167 */               actionConstrict.setRelaPk(null);
/*      */             }
/*      */ 
/*  171 */             tmpString = rs.getString("aclassname");
/*  172 */             if (tmpString == null)
/*  173 */               actionConstrict.setFunClassName(null);
/*      */             else {
/*  175 */               actionConstrict.setFunClassName(tmpString.trim());
/*      */             }
/*      */ 
/*  178 */             tmpString = rs.getString("areturntype");
/*  179 */             if (tmpString == null)
/*  180 */               actionConstrict.setFunReturnType(null);
/*      */             else {
/*  182 */               actionConstrict.setFunReturnType(tmpString.trim());
/*      */             }
/*      */ 
/*  185 */             tmpString = rs.getString("afunctionnote");
/*  186 */             if (tmpString == null)
/*  187 */               actionConstrict.setFunNote(null);
/*      */             else {
/*  189 */               actionConstrict.setFunNote(tmpString.trim());
/*      */             }
/*      */ 
/*  192 */             tmpString = rs.getString("amethod");
/*  193 */             if (tmpString == null)
/*  194 */               actionConstrict.setMethod(null);
/*      */             else {
/*  196 */               actionConstrict.setMethod(tmpString.trim());
/*      */             }
/*      */ 
/*  199 */             tmpString = rs.getString("aparameter");
/*  200 */             if (tmpString == null)
/*  201 */               actionConstrict.setParameter(null);
/*      */             else {
/*  203 */               actionConstrict.setParameter(tmpString.trim());
/*      */             }
/*      */ 
/*  206 */             tmpString = rs.getString("ysf");
/*  207 */             if (tmpString == null)
/*  208 */               actionConstrict.setYsf(null);
/*      */             else {
/*  210 */               actionConstrict.setYsf(tmpString.trim());
/*      */             }
/*      */ 
/*  213 */             tmpString = rs.getString("value");
/*  214 */             if (tmpString == null)
/*  215 */               actionConstrict.setValue(null);
/*      */             else {
/*  217 */               actionConstrict.setValue(tmpString.trim());
/*      */             }
/*      */ 
/*  220 */             tmpString = rs.getString("bclassname");
/*  221 */             if (tmpString == null)
/*  222 */               actionConstrict.setClassName2(null);
/*      */             else {
/*  224 */               actionConstrict.setClassName2(tmpString.trim());
/*      */             }
/*      */ 
/*  227 */             tmpString = rs.getString("bfunctionnote");
/*  228 */             if (tmpString == null)
/*  229 */               actionConstrict.setFunNote2(null);
/*      */             else {
/*  231 */               actionConstrict.setFunNote2(tmpString.trim());
/*      */             }
/*      */ 
/*  234 */             tmpString = rs.getString("bmethod");
/*  235 */             if (tmpString == null)
/*  236 */               actionConstrict.setMethod2(null);
/*      */             else {
/*  238 */               actionConstrict.setMethod2(tmpString.trim());
/*      */             }
/*      */ 
/*  241 */             tmpString = rs.getString("bparameter");
/*  242 */             if (tmpString == null)
/*  243 */               actionConstrict.setParameter2(null);
/*      */             else {
/*  245 */               actionConstrict.setParameter2(tmpString.trim());
/*      */             }
/*      */ 
/*  248 */             tmpString = rs.getString("errhintmsg");
/*  249 */             if (tmpString == null)
/*  250 */               actionConstrict.setErrHintMsg(null);
/*      */             else {
/*  252 */               actionConstrict.setErrHintMsg(tmpString.trim());
/*      */             }
/*      */ 
/*  255 */             tmpString = rs.getString("isbefore");
/*  256 */             if (tmpString == null)
/*  257 */               actionConstrict.setIsBefore("Y");
/*      */             else {
/*  259 */               actionConstrict.setIsBefore(tmpString);
/*      */             }
/*      */ 
/*  262 */             al.add(actionConstrict);
/*      */           }
/*  264 */           return al;
/*      */         }
/*      */       });
/*  268 */       PfUtilActionConstrictVO[] arrayOfPfUtilActionConstrictVO = (PfUtilActionConstrictVO[])(PfUtilActionConstrictVO[])lResult.toArray(new PfUtilActionConstrictVO[lResult.size()]);
/*      */ 
/*  273 */       return arrayOfPfUtilActionConstrictVO;
/*      */     }
/*      */     finally
/*      */     {
/*  271 */       if (persist != null)
/*  272 */         persist.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public String queryActionHint(String billType, String actionType)
/*      */     throws DbException
/*      */   {
/*  285 */     String sql = "select showhint from pub_billaction where pk_billtype=? and actiontype=?";
/*  286 */     PersistenceManager persist = null;
/*      */     try {
/*  288 */       persist = PersistenceManager.getInstance();
/*  289 */       JdbcSession jdbc = persist.getJdbcSession();
/*  290 */       SQLParameter para = new SQLParameter();
/*      */ 
/*  292 */       para.addParam(billType);
/*  293 */       para.addParam(actionType);
/*  294 */       Object obj = jdbc.executeQuery(sql, para, new ColumnProcessor(1));
/*  295 */       String str1 = String.valueOf(obj);
/*      */ 
/*  299 */       return str1;
/*      */     }
/*      */     finally
/*      */     {
/*  297 */       if (persist != null)
/*  298 */         persist.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public PfUtilBillActionVO[] queryBillActionStyle(final String billType, String actionStyle)
/*      */     throws DbException
/*      */   {
/*  313 */     String sql = "select c.actiontype,c.actionnote from pub_billactiongroup a inner join pub_billactionconfig b on a.pk_billactiongroup=b.pk_billactiongroup inner join pub_billaction c on (b.actiontype=c.actiontype and c.pk_billtype=a.pk_billtype) where c.pk_billtype=? and a.actionstyle=? order by b.sysindex";
/*      */ 
/*  319 */     PersistenceManager persist = null;
/*      */     try {
/*  321 */       persist = PersistenceManager.getInstance();
/*  322 */       JdbcSession jdbc = persist.getJdbcSession();
/*  323 */       SQLParameter para = new SQLParameter();
/*      */ 
/*  327 */       para.addParam(billType);
/*      */ 
/*  329 */       para.addParam(actionStyle);
/*  330 */       List lResult = (List)jdbc.executeQuery(sql, para, new BaseProcessor()
/*      */       {
/*      */         public Object processResultSet(ResultSet rs) throws SQLException {
/*  333 */           ArrayList al = new ArrayList();
/*      */ 
/*  335 */           while (rs.next()) {
/*  336 */             PfUtilBillActionVO billVo = new PfUtilBillActionVO();
/*  337 */             billVo.setPkBillType(billType);
/*  338 */             String tmpString = rs.getString("actiontype");
/*  339 */             if (tmpString == null)
/*  340 */               billVo.setActionName(null);
/*      */             else {
/*  342 */               billVo.setActionName(tmpString.trim());
/*      */             }
/*  344 */             tmpString = rs.getString("actionnote");
/*  345 */             if (tmpString == null)
/*  346 */               billVo.setActionNote(null);
/*      */             else {
/*  348 */               billVo.setActionNote(tmpString.trim());
/*      */             }
/*  350 */             al.add(billVo);
/*      */           }
/*  352 */           return al;
/*      */         }
/*      */       });
/*  355 */       PfUtilBillActionVO[] arrayOfPfUtilBillActionVO = (PfUtilBillActionVO[])(PfUtilBillActionVO[])lResult.toArray(new PfUtilBillActionVO[lResult.size()]);
/*      */ 
/*  359 */       return arrayOfPfUtilBillActionVO;
/*      */     }
/*      */     finally
/*      */     {
/*  357 */       if (persist != null)
/*  358 */         persist.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   private String createBilltypeSql(String billType, String transType) {
/*  363 */     String strField = "";
/*  364 */     if (StringUtil.isEmptyWithTrim(transType))
/*      */     {
/*  366 */       if (PfUtilBaseTools.isTranstype(billType))
/*  367 */         strField = "a.transtype='" + billType + "'";
/*      */       else
/*  369 */         strField = "a.pk_billtype='" + billType + "'";
/*      */     } else {
/*  371 */       strField = "((a.pk_billtype='" + billType + "' and a.transtype='" + transType + "')";
/*  372 */       strField = strField + " or (a.pk_billtype='" + billType + "' and a.transtype is null))";
/*      */     }
/*  374 */     return strField;
/*      */   }
/*      */ 
/*      */   public BillbusinessVO[] queryMakeFlagBillSource(String corpId, String billType, String transType, String[] businessTypes)
/*      */     throws DAOException
/*      */   {
/*  389 */     String strField = createBilltypeSql(billType, transType);
/*  390 */     String sql = "select a.pk_businesstype pk_businesstype,d.businame busitypename from pub_billbusiness a left join pub_billsource b on a.pk_billbusiness=b.billbusinessid  left join bd_busitype d on a.pk_businesstype = d.pk_busitype where (a.pk_corp='" + corpId + "' or a.pk_corp='@@@@') and a.makebillflag = 'Y' and " + strField;
/*      */ 
/*  394 */     StringBuffer sqlBusitypeIn = new StringBuffer();
/*  395 */     if ((businessTypes != null) && (businessTypes.length > 0)) {
/*  396 */       sqlBusitypeIn.append(" and a.pk_businesstype in (");
/*  397 */       for (int i = 0; i < businessTypes.length; ++i) {
/*  398 */         sqlBusitypeIn.append("'" + businessTypes[i] + " ',");
/*      */       }
/*  400 */       sqlBusitypeIn.deleteCharAt(sqlBusitypeIn.length() - 1);
/*  401 */       sqlBusitypeIn.append(")");
/*  402 */       sql = sql + sqlBusitypeIn.toString();
/*      */     }
/*  404 */     BaseDAO dao = new BaseDAO();
/*  405 */     List billlBusiList = (ArrayList)dao.executeQuery(sql, new BeanListProcessor(BillbusinessVO.class));
/*      */ 
/*  407 */     if (billlBusiList != null) {
/*  408 */       return ((BillbusinessVO[])billlBusiList.toArray(new BillbusinessVO[0]));
/*      */     }
/*  410 */     return null;
/*      */   }
/*      */ 
/*      */   public BillbusinessVO[] queryBillSource(String corpId, String billType, String transType, String businessType)
/*      */     throws DbException
/*      */   {
/*  428 */     String strField = createBilltypeSql(billType, transType);
/*      */ 
/*  430 */     String sql = "select a.makebillflag,b.referbilltype,b.referbusinesstype,c.billtypename from pub_billbusiness a left join pub_billsource b on a.pk_billbusiness=b.billbusinessid left join bd_billtype c on b.referbilltype=c.pk_billtypecode where (a.pk_corp=? or a.pk_corp='@@@@') and " + strField;
/*      */ 
/*  435 */     if (businessType != null) {
/*  436 */       sql = sql + " and a.pk_businesstype=?";
/*      */     }
/*  438 */     PersistenceManager persist = null;
/*      */     try {
/*  440 */       persist = PersistenceManager.getInstance();
/*  441 */       JdbcSession jdbc = persist.getJdbcSession();
/*  442 */       SQLParameter para = new SQLParameter();
/*      */ 
/*  445 */       para.addParam(corpId);
/*  446 */       if (businessType != null) {
/*  447 */         para.addParam(businessType);
/*      */       }
/*  449 */       List lResult = (List)jdbc.executeQuery(sql, para, new BaseProcessor() {
/*      */         public Object processResultSet(ResultSet rs) throws SQLException {
/*  451 */           ArrayList al = new ArrayList();
/*      */ 
/*  453 */           if (!(rs.next()))
/*      */           {
/*  456 */             return al;
/*      */           }
/*  458 */           BillbusinessVO referVo = null;
/*  459 */           if (rs.getString("makebillflag").equals("Y")) {
/*  460 */             referVo = new BillbusinessVO();
/*  461 */             referVo.setBilltypename("自制单据");
/*  462 */             referVo.setPk_billtype("makeflag");
/*  463 */             referVo.setPk_businesstype("");
/*  464 */             al.add(referVo);
/*      */           }
/*  466 */           String tmpString = rs.getString("referbilltype");
/*  467 */           if (!(StringUtil.isEmptyWithTrim(tmpString))) {
/*  468 */             referVo = new BillbusinessVO();
/*  469 */             referVo.setPk_billtype(tmpString.trim());
/*  470 */             tmpString = rs.getString("referbusinesstype");
/*  471 */             tmpString = (tmpString == null) ? "" : tmpString.trim();
/*  472 */             referVo.setPk_businesstype(tmpString);
/*  473 */             tmpString = rs.getString("billtypename");
/*  474 */             tmpString = (tmpString == null) ? "" : tmpString.trim();
/*  475 */             referVo.setBilltypename(tmpString);
/*  476 */             al.add(referVo);
/*      */           }
/*  478 */           while (rs.next()) {
/*  479 */             tmpString = rs.getString("referbilltype");
/*  480 */             if (!(StringUtil.isEmptyWithTrim(tmpString)));
/*  481 */             referVo = new BillbusinessVO();
/*  482 */             referVo.setPk_billtype(tmpString.trim());
/*  483 */             tmpString = rs.getString("referbusinesstype");
/*  484 */             tmpString = (tmpString == null) ? "" : tmpString;
/*  485 */             referVo.setPk_businesstype(tmpString);
/*  486 */             tmpString = rs.getString("billtypename");
/*  487 */             tmpString = (tmpString == null) ? "" : tmpString;
/*  488 */             referVo.setBilltypename(tmpString);
/*      */ 
/*  490 */             if (!(isAlreadyExist(al, referVo.getPk_billtype())));
/*  491 */             al.add(referVo);
/*      */           }
/*      */ 
/*  494 */           return al;
/*      */         }
/*      */ 
/*      */         private boolean isAlreadyExist(ArrayList<BillbusinessVO> alList, String srcBilltype)
/*      */         {
/*  504 */           for (BillbusinessVO bbVO : alList) {
/*  505 */             if (bbVO.getPk_billtype().equals(srcBilltype))
/*  506 */               return true;
/*      */           }
/*  508 */           return false;
/*      */         }
/*      */       });
/*  511 */       BillbusinessVO[] arrayOfBillbusinessVO = (BillbusinessVO[])(BillbusinessVO[])lResult.toArray(new BillbusinessVO[lResult.size()]);
/*      */ 
/*  515 */       return arrayOfBillbusinessVO;
/*      */     }
/*      */     finally
/*      */     {
/*  513 */       if (persist != null)
/*  514 */         persist.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public PfUtilBillActionVO[] queryBillStateActionStyle(String corpId,final  String billType, String billState, String businessType, String actionStyle)
/*      */     throws DbException
/*      */   {
/*  533 */     String sql = "Select e.configflag,e.operator,c.actiontype,c.actionnote from pub_billactiongroup a inner join pub_billactionconfig b on a.pk_billactiongroup=b.pk_billactiongroup inner join pub_billaction c on (b.actiontype=c.actiontype and a.pk_billtype=c.pk_billtype) inner join pub_billstateactionconfig d on d.billactionconfigid=b.pk_billactionconfig inner join pub_billactionpower e on d.pk_billstateactionconfig=e.pk_billactionconfig where e.pk_corp=? and c.pk_billtype=? and d.billstate=? and a.actionstyle=? ";
/*      */ 
/*  541 */     String strTemp = null;
/*  542 */     if (businessType == null)
/*  543 */       strTemp = "e.pk_businesstype is null";
/*      */     else {
/*  545 */       strTemp = "e.pk_businesstype=?";
/*      */     }
/*  547 */     sql = sql + " and " + strTemp + " order by b.sysindex";
/*      */ 
/*  549 */     PersistenceManager persist = null;
/*      */     try {
/*  551 */       persist = PersistenceManager.getInstance();
/*  552 */       JdbcSession jdbc = persist.getJdbcSession();
/*  553 */       SQLParameter para = new SQLParameter();
/*      */ 
/*  556 */       para.addParam(corpId);
/*  557 */       para.addParam(billType);
/*  558 */       para.addParam(billState);
/*  559 */       para.addParam(actionStyle);
/*  560 */       if (businessType != null) {
/*  561 */         para.addParam(businessType);
/*      */       }
/*  563 */       List lResult = (List)jdbc.executeQuery(sql, para, new BaseProcessor()
/*      */       {
/*      */         public Object processResultSet(ResultSet rs) throws SQLException {
/*  566 */           ArrayList al = new ArrayList();
/*  567 */           while (rs.next()) {
/*  568 */             PfUtilBillActionVO billVo = new PfUtilBillActionVO();
/*  569 */             billVo.setPkBillType(billType);
/*  570 */             int configFlag = rs.getInt("configflag");
/*  571 */             billVo.setConfigFlag(configFlag);
/*      */ 
/*  573 */             String tmpString = rs.getString("operator");
/*  574 */             if (tmpString != null)
/*  575 */               billVo.setRelaPk(tmpString.trim());
/*      */             else {
/*  577 */               billVo.setRelaPk(null);
/*      */             }
/*      */ 
/*  580 */             tmpString = rs.getString("actiontype");
/*  581 */             if (tmpString == null)
/*  582 */               billVo.setActionName(null);
/*      */             else {
/*  584 */               billVo.setActionName(tmpString.trim());
/*      */             }
/*  586 */             tmpString = rs.getString("actionnote");
/*  587 */             if (tmpString == null)
/*  588 */               billVo.setActionNote(null);
/*      */             else {
/*  590 */               billVo.setActionNote(tmpString.trim());
/*      */             }
/*  592 */             al.add(billVo);
/*      */           }
/*  594 */           return al;
/*      */         }
/*      */       });
/*  597 */       PfUtilBillActionVO[] arrayOfPfUtilBillActionVO = (PfUtilBillActionVO[])(PfUtilBillActionVO[])lResult.toArray(new PfUtilBillActionVO[lResult.size()]);
/*      */ 
/*  601 */       return arrayOfPfUtilBillActionVO;
/*      */     }
/*      */     finally
/*      */     {
/*  599 */       if (persist != null)
/*  600 */         persist.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public BillbusinessVO[] queryBillDest(String billType, String busiType)
/*      */     throws DbException
/*      */   {
/*  614 */     boolean isNull = StringUtil.isEmptyWithTrim(busiType);
/*  615 */     String sql = "select a.pk_billbusiness,a.pk_corp,a.pk_billtype,a.transtype,a.pk_businesstype,a.referbillflag,a.makebillflag,a.powerflag from pub_billbusiness a left join pub_billsource b on a.pk_billbusiness=b.billbusinessid where b.referbilltype=?" + ((isNull) ? "" : " and b.referbusinesstype=?");
/*      */ 
/*  619 */     PersistenceManager persist = null;
/*      */     try {
/*  621 */       persist = PersistenceManager.getInstance();
/*  622 */       JdbcSession jdbc = persist.getJdbcSession();
/*  623 */       SQLParameter para = new SQLParameter();
/*  624 */       para.addParam(billType);
/*  625 */       if (!(isNull))
/*  626 */         para.addParam(busiType);
/*  627 */       ArrayList alRet = (ArrayList)jdbc.executeQuery(sql, para, new BeanListProcessor(BillbusinessVO.class));
/*      */ 
/*  629 */       BillbusinessVO[] arrayOfBillbusinessVO = (BillbusinessVO[])(BillbusinessVO[])alRet.toArray(new BillbusinessVO[alRet.size()]);
/*      */ 
/*  633 */       return arrayOfBillbusinessVO;
/*      */     }
/*      */     finally
/*      */     {
/*  631 */       if (persist != null)
/*  632 */         persist.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public BusitypeVO[] queryBillBusinessType(String corpId, String billType)
/*      */     throws DbException
/*      */   {
/*      */     String strField;
/*  649 */     if (PfUtilBaseTools.isTranstype(billType))
/*  650 */       strField = "a.transtype";
/*      */     else
/*  652 */       strField = "a.pk_billtype";
/*  653 */     String sql = "select a.pk_businesstype,b.businame,b.busicode,a.pk_corp from pub_billbusiness a,bd_busitype b where a.pk_businesstype=b.pk_busitype and " + strField + "=? and (a.pk_corp=? or a.pk_corp='" + "@@@@" + "') order by b.busicode";
/*      */ 
/*  659 */     PersistenceManager persist = null;
/*      */     try {
/*  661 */       persist = PersistenceManager.getInstance();
/*  662 */       JdbcSession jdbc = persist.getJdbcSession();
/*  663 */       SQLParameter para = new SQLParameter();
/*  664 */       para.addParam(billType);
/*  665 */       para.addParam(corpId);
/*  666 */       List lResult = (List)jdbc.executeQuery(sql, para, new BaseProcessor() {
/*      */         public Object processResultSet(ResultSet rs) throws SQLException {
/*  668 */           ArrayList al = new ArrayList();
/*  669 */           while (rs.next()) {
/*  670 */             BusitypeVO btVO = new BusitypeVO();
/*  671 */             String tmpString = rs.getString("pk_businesstype");
/*  672 */             if (tmpString == null)
/*  673 */               btVO.setPrimaryKey(null);
/*      */             else {
/*  675 */               btVO.setPrimaryKey(tmpString.trim());
/*      */             }
/*  677 */             tmpString = rs.getString("businame");
/*  678 */             if (tmpString == null)
/*  679 */               btVO.setBusiname(null);
/*      */             else {
/*  681 */               btVO.setBusiname(tmpString.trim());
/*      */             }
/*  683 */             tmpString = rs.getString("busicode");
/*  684 */             if (tmpString == null)
/*  685 */               btVO.setBusicode(null);
/*      */             else {
/*  687 */               btVO.setBusicode(tmpString.trim());
/*      */             }
/*  689 */             tmpString = rs.getString("pk_corp");
/*  690 */             if (tmpString == null)
/*  691 */               btVO.setPk_corp(null);
/*      */             else {
/*  693 */               btVO.setPk_corp(tmpString.trim());
/*      */             }
/*      */ 
/*  697 */             if (!(isAlreadyExist(al, btVO.getPrimaryKey())))
/*  698 */               al.add(btVO);
/*      */           }
/*  700 */           return al;
/*      */         }
/*      */ 
/*      */         private boolean isAlreadyExist(ArrayList<BusitypeVO> al, String pk_busitype)
/*      */         {
/*  710 */           if (StringUtil.isEmptyWithTrim(pk_busitype))
/*  711 */             return false;
/*  712 */           for (BusitypeVO btVO : al) {
/*  713 */             if (pk_busitype.equals(btVO.getPrimaryKey()))
/*  714 */               return true;
/*      */           }
/*  716 */           return false;
/*      */         }
/*      */       });
/*  719 */       BusitypeVO[] voRet = null;
/*  720 */       if ((lResult != null) && (lResult.size() > 0)) {
/*  721 */         voRet = new BusitypeVO[lResult.size()];
/*  722 */         lResult.toArray(voRet);
/*  723 */         VOUtil.descSort(voRet, new String[] { "pk_corp" });
/*      */       }
/*  725 */       BusitypeVO[] arrayOfBusitypeVO1 = voRet;
/*      */ 
/*  729 */       return arrayOfBusitypeVO1;
/*      */     }
/*      */     finally
/*      */     {
/*  727 */       if (persist != null)
/*  728 */         persist.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public PfUtilBillActionVO[] queryBillStateActionStyleNoBusi(final String billType, String billState, String actionStyle)
/*      */     throws DbException
/*      */   {
/*  745 */     String sql = "Select c.actiontype,c.actionnote from pub_billactiongroup a inner join pub_billactionconfig b on a.pk_billactiongroup=b.pk_billactiongroup inner join pub_billaction c on (b.actiontype=c.actiontype and a.pk_billtype=c.pk_billtype) inner join pub_billstateactionconfig d on d.billactionconfigid=b.pk_billactionconfig where c.pk_billtype=? and d.billstate=? and a.actionstyle=? order by b.sysindex";
/*      */ 
/*  750 */     PersistenceManager persist = null;
/*      */     try {
/*  752 */       persist = PersistenceManager.getInstance();
/*  753 */       JdbcSession jdbc = persist.getJdbcSession();
/*  754 */       SQLParameter para = new SQLParameter();
/*      */ 
/*  757 */       para.addParam(billType);
/*  758 */       para.addParam(billState);
/*  759 */       para.addParam(actionStyle);
/*  760 */       List lResult = (List)jdbc.executeQuery(sql, para, new BaseProcessor()
/*      */       {
/*      */         public Object processResultSet(ResultSet rs) throws SQLException {
/*  763 */           ArrayList al = new ArrayList();
/*      */ 
/*  765 */           while (rs.next()) {
/*  766 */             PfUtilBillActionVO billVo = new PfUtilBillActionVO();
/*  767 */             billVo.setPkBillType(billType);
/*  768 */             String tmpString = rs.getString("actiontype");
/*  769 */             if (tmpString == null)
/*  770 */               billVo.setActionName(null);
/*      */             else {
/*  772 */               billVo.setActionName(tmpString.trim());
/*      */             }
/*  774 */             tmpString = rs.getString("actionnote");
/*  775 */             if (tmpString == null)
/*  776 */               billVo.setActionNote(null);
/*      */             else {
/*  778 */               billVo.setActionNote(tmpString.trim());
/*      */             }
/*  780 */             al.add(billVo);
/*      */           }
/*  782 */           return al;
/*      */         }
/*      */       });
/*  785 */       PfUtilBillActionVO[] arrayOfPfUtilBillActionVO = (PfUtilBillActionVO[])(PfUtilBillActionVO[])lResult.toArray(new PfUtilBillActionVO[lResult.size()]);
/*      */ 
/*  789 */       return arrayOfPfUtilBillActionVO;
/*      */     }
/*      */     finally
/*      */     {
/*  787 */       if (persist != null)
/*  788 */         persist.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public PfUtilActionVO[] queryDriveAction(final String billType, String businessType, String corpId, String sourceAction, String operator)
/*      */     throws DbException
/*      */   {
/*  806 */     String sql = "select pk_driveconfig,configflag,operator,pk_billtype,pk_sourcebusinesstype,actiontype  from pub_messagedrive where (pk_corp=? or pk_corp='@@@@')  and pk_sourcebilltype=? and sourceaction=? ";
/*      */ 
/*  809 */     if (businessType == null)
/*  810 */       sql = sql + " and pk_businesstype is null ";
/*      */     else {
/*  812 */       sql = sql + " and pk_businesstype=? ";
/*      */     }
/*  814 */     sql = sql + " and ( configflag=1 or (configflag=2 and operator=?)  or (configflag=3     and operator in(select pk_role from sm_user_role where cuserid=? and pk_corp=?))  )order by sysindex,pk_billtype";
/*      */ 
/*  822 */     PersistenceManager persist = null;
/*      */     try {
/*  824 */       persist = PersistenceManager.getInstance();
/*  825 */       JdbcSession jdbc = persist.getJdbcSession();
/*  826 */       SQLParameter para = new SQLParameter();
/*      */ 
/*  828 */       para.addParam(corpId);
/*  829 */       para.addParam(billType);
/*  830 */       para.addParam(sourceAction);
/*  831 */       if (businessType != null) {
/*  832 */         para.addParam(businessType);
/*      */       }
/*  834 */       para.addParam(operator);
/*  835 */       para.addParam(operator);
/*  836 */       para.addParam(corpId);
/*      */ 
/*  838 */       List lResult = (List)jdbc.executeQuery(sql, para, new BaseProcessor() {
/*      */         public Object processResultSet(ResultSet rs) throws SQLException {
/*  840 */           ArrayList al = new ArrayList();
/*  841 */           while (rs.next()) {
/*  842 */             PfUtilActionVO action = new PfUtilActionVO();
/*  843 */             String tmpString = null;
/*      */ 
/*  845 */             tmpString = rs.getString("pk_driveconfig");
/*  846 */             action.setPkMessageDrive(tmpString.trim());
/*      */ 
/*  848 */             int configFlag = rs.getInt("configflag");
/*  849 */             action.setConfigFlag(new Integer(configFlag));
/*      */ 
/*  851 */             tmpString = rs.getString("operator");
/*  852 */             if (tmpString != null)
/*  853 */               action.setRelaPK(tmpString.trim());
/*      */             else {
/*  855 */               action.setRelaPK(null);
/*      */             }
/*      */ 
/*  859 */             tmpString = rs.getString("pk_billtype");
/*  860 */             if (tmpString == null)
/*  861 */               action.setBillType(null);
/*      */             else {
/*  863 */               action.setBillType(tmpString.trim());
/*      */             }
/*      */ 
/*  866 */             tmpString = rs.getString("pk_sourcebusinesstype");
/*  867 */             if (tmpString == null)
/*  868 */               action.setBusinessType(null);
/*      */             else {
/*  870 */               action.setBusinessType(tmpString.trim());
/*      */             }
/*      */ 
/*  873 */             tmpString = rs.getString("actiontype");
/*  874 */             if (tmpString == null)
/*  875 */               action.setActionName(null);
/*      */             else {
/*  877 */               action.setActionName(tmpString.trim());
/*      */             }
/*      */ 
/*  880 */             action.setDriveBillType(billType);
/*  881 */             al.add(action);
/*      */           }
/*  883 */           return al;
/*      */         }
/*      */       });
/*  886 */       PfUtilActionVO[] arrayOfPfUtilActionVO = (PfUtilActionVO[])(PfUtilActionVO[])lResult.toArray(new PfUtilActionVO[lResult.size()]);
/*      */ 
/*  890 */       return arrayOfPfUtilActionVO;
/*      */     }
/*      */     finally
/*      */     {
/*  888 */       if (persist != null)
/*  889 */         persist.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public PfPOArriveVO queryIsSameByBillTypeAry(String billTypeAry, String busiType)
/*      */     throws DbException
/*      */   {
/*  904 */     String sql = " SELECT a.pk_billtype,a.pk_businesstype,b.busicode,b.businame FROM pub_billbusiness a LEFT OUTER JOIN bd_busitype b ON a.pk_businesstype = b.pk_busitype WHERE a.pk_businesstype=? AND a.pk_billtype IN (" + billTypeAry + ")";
/*      */ 
/*  908 */     PersistenceManager persist = null;
/*      */     try {
/*  910 */       persist = PersistenceManager.getInstance();
/*  911 */       JdbcSession jdbc = persist.getJdbcSession();
/*  912 */       SQLParameter para = new SQLParameter();
/*  913 */       para.addParam(busiType);
/*  914 */       PfPOArriveVO retVo = null;
/*  915 */       retVo = (PfPOArriveVO)jdbc.executeQuery(sql, para, new BaseProcessor() {
/*      */         public Object processResultSet(ResultSet rs) throws SQLException {
/*  917 */           PfPOArriveVO arriveVo = null;
/*      */ 
/*  919 */           while (rs.next()) {
/*  920 */             if (arriveVo == null) {
/*  921 */               arriveVo = new PfPOArriveVO();
/*      */             }
/*  923 */             String tmpString = rs.getString(1);
/*      */ 
/*  925 */             if ("23".equals(tmpString)) {
/*  926 */               arriveVo.setArriveFlag(new UFBoolean("Y"));
/*      */             }
/*  928 */             if ("45".equals(tmpString)) {
/*  929 */               arriveVo.setStoreFlag(new UFBoolean("Y"));
/*      */             }
/*      */ 
/*  932 */             tmpString = rs.getString(2);
/*  933 */             if (tmpString == null)
/*  934 */               arriveVo.setPk_busiType(null);
/*      */             else {
/*  936 */               arriveVo.setPk_busiType(tmpString.trim());
/*      */             }
/*  938 */             tmpString = rs.getString(3);
/*  939 */             if (tmpString == null)
/*  940 */               arriveVo.setBusiCode(null);
/*      */             else {
/*  942 */               arriveVo.setBusiCode(tmpString.trim());
/*      */             }
/*  944 */             tmpString = rs.getString(4);
/*  945 */             if (tmpString == null)
/*  946 */               arriveVo.setBusiName(null);
/*      */             else {
/*  948 */               arriveVo.setBusiName(tmpString.trim());
/*      */             }
/*      */           }
/*  951 */           return arriveVo;
/*      */         }
/*      */       });
/*  954 */       PfPOArriveVO localPfPOArriveVO1 = retVo;
/*      */ 
/*  958 */       return localPfPOArriveVO1;
/*      */     }
/*      */     finally
/*      */     {
/*  956 */       if (persist != null)
/*  957 */         persist.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public String[] getBillbusiPKsOfBilltype(String[] busiPks, String billtype)
/*      */     throws BusinessException
/*      */   {
/*  970 */     if ((busiPks == null) || (busiPks.length == 0))
/*  971 */       return null;
/*      */     String strField;
/*  975 */     if (PfUtilBaseTools.isTranstype(billtype))
/*  976 */       strField = "transtype";
/*      */     else {
/*  978 */       strField = "pk_billtype";
/*      */     }
/*  980 */     String sWhere = strField + "='" + billtype + "' and (pk_billbusiness='" + busiPks[0] + "'";
/*  981 */     for (int i = 1; i < busiPks.length; ++i) {
/*  982 */       sWhere = sWhere + " or pk_billbusiness='" + busiPks[i] + "'";
/*      */     }
/*  984 */     sWhere = sWhere + ")";
/*  985 */     Collection collection = new BaseDAO().retrieveByClause(BillbusinessVO.class, sWhere, new String[] { "pk_businesstype" });
/*      */ 
/*  987 */     if ((collection == null) || (collection.size() == 0))
/*  988 */       return null;
/*  989 */     String[] sRet = new String[collection.size()];
/*  990 */     BillbusinessVO voTmp = null;
/*  991 */     int i = 0;
/*  992 */     for (Iterator iter = collection.iterator(); iter.hasNext(); ) {
/*  993 */       voTmp = (BillbusinessVO)iter.next();
/*  994 */       sRet[i] = voTmp.getPk_businesstype();
/*  995 */       ++i;
/*      */     }
/*  997 */     return sRet;
/*      */   }
/*      */ 
/*      */   public String[] queryBusitypeOfNoControl(String corpId, String billType)
/*      */     throws DbException
/*      */   {
/*      */     String strField;
/* 1012 */     if (PfUtilBaseTools.isTranstype(billType))
/* 1013 */       strField = "transtype";
/*      */     else {
/* 1015 */       strField = "pk_billtype";
/*      */     }
/* 1017 */     String sql = "select pk_businesstype from pub_billbusiness where (pk_corp=? or pk_corp='@@@@') and " + strField + "=? and (powerflag is null or powerflag='N')";
/*      */ 
/* 1020 */     PersistenceManager persist = null;
/*      */     try {
/* 1022 */       persist = PersistenceManager.getInstance();
/* 1023 */       JdbcSession jdbc = persist.getJdbcSession();
/* 1024 */       SQLParameter para = new SQLParameter();
/*      */ 
/* 1026 */       para.addParam(corpId);
/* 1027 */       para.addParam(billType);
/* 1028 */       List list = (List)jdbc.executeQuery(sql, para, new ColumnListProcessor(1));
/* 1029 */       String[] sRet = null;
/* 1030 */       if ((list != null) && (list.size() > 0)) {
/* 1031 */         sRet = new String[list.size()];
/* 1032 */         list.toArray(sRet);
/*      */       }
/* 1034 */       String[] arrayOfString1 = sRet;
/*      */ 
/* 1038 */       return arrayOfString1;
/*      */     }
/*      */     finally
/*      */     {
/* 1036 */       if (persist != null)
/* 1037 */         persist.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public BillbusinessVO[] queryBillbusiVOs(String biztype, String pkcorp)
/*      */     throws DbException
/*      */   {
/* 1050 */     String sql = "select a.*, d.businame as destbiztypename from pub_billbusiness a left outer join bd_busitype d on a.destbiztype=d.pk_busitype where a.pk_businesstype=?";
/*      */ 
/* 1053 */     if (!(StringUtil.isEmptyWithTrim(pkcorp)))
/* 1054 */       sql = sql + " and a.pk_corp='" + pkcorp + "'";
/* 1055 */     PersistenceManager persist = null;
/*      */     try {
/* 1057 */       persist = PersistenceManager.getInstance();
/* 1058 */       JdbcSession jdbc = persist.getJdbcSession();
/* 1059 */       SQLParameter para = new SQLParameter();
/* 1060 */       para.addParam(biztype);
/* 1061 */       List list = (List)jdbc.executeQuery(sql, para, new BeanListProcessor(BillbusinessVO.class));
/* 1062 */       BillbusinessVO[] vos = (BillbusinessVO[])(BillbusinessVO[])list.toArray(new BillbusinessVO[list.size()]);
/* 1063 */       for (BillbusinessVO billbusiVO : vos) {
/* 1064 */         String billType = billbusiVO.getPk_billtype();
/* 1065 */         String transType = billbusiVO.getTranstype();
/* 1066 */         BilltypeVO btVO = PfDataCache.getBillType(billType);
/* 1067 */         billbusiVO.setBilltypename(Pfi18nTools.i18nBilltypeName(billType, (btVO == null) ? null : btVO.getBilltypename()));
/*      */ 
/* 1069 */         if (!(StringUtil.isEmptyWithTrim(transType))) {
/* 1070 */           BilltypeVO transtypeVO = PfDataCache.getBillType(transType);
/* 1071 */           billbusiVO.setTranstypename(Pfi18nTools.i18nBilltypeName(transType, (transtypeVO == null) ? null : transtypeVO.getBilltypename()));
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/* 1079 */       return vos;
/*      */     }
/*      */     finally
/*      */     {
/* 1077 */       if (persist != null)
/* 1078 */         persist.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public String queryDestBusitype(String busitype, String billtype)
/*      */     throws DbException
/*      */   {
/* 1091 */     boolean isTranstype = PfUtilBaseTools.isTranstype(billtype);
/* 1092 */     String sql = "select destbiztype from pub_billbusiness where pk_businesstype=? ";
/* 1093 */     if (isTranstype)
/* 1094 */       sql = sql + "and transtype=?";
/*      */     else
/* 1096 */       sql = sql + "and pk_billtype=?";
/* 1097 */     PersistenceManager persist = PersistenceManager.getInstance();
/* 1098 */     JdbcSession jdbc = persist.getJdbcSession();
/* 1099 */     SQLParameter para = new SQLParameter();
/* 1100 */     para.addParam(busitype);
/* 1101 */     para.addParam(billtype);
/* 1102 */     Object obj = jdbc.executeQuery(sql, para, new ColumnProcessor());
/* 1103 */     return ((String)obj);
/*      */   }
/*      */ }

