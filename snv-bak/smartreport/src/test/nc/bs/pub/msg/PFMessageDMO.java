/*     */ package nc.bs.pub.msg;
/*     */ 
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import nc.bs.framework.common.NCLocator;
/*     */ import nc.itf.uap.IUAPQueryBS;
/*     */ import nc.itf.uap.rbac.IUserManageQuery;
/*     */ import nc.jdbc.framework.JdbcSession;
/*     */ import nc.jdbc.framework.PersistenceManager;
/*     */ import nc.jdbc.framework.SQLParameter;
/*     */ import nc.jdbc.framework.exception.DbException;
/*     */ import nc.jdbc.framework.processor.BaseProcessor;
/*     */ import nc.ui.pub.msg.MessageFilter;
/*     */ import nc.vo.pub.BusinessException;
/*     */ import nc.vo.pub.lang.UFDateTime;
/*     */ import nc.vo.pub.msg.CommonMessageVO;
/*     */ import nc.vo.pub.msg.MessageVO;
/*     */ import nc.vo.pub.msg.UserNameObject;
/*     */ import nc.vo.sm.UserVO;
/*     */ import nc.vo.wfengine.pub.WfTaskStatus;
/*     */ 
/*     */ public class PFMessageDMO
/*     */ {
/*     */   /** @deprecated */
/*     */   public void delReceivedMessages(String[] pks)
/*     */     throws DbException
/*     */   {
/*  52 */     if ((pks == null) || (pks.length == 0))
/*  53 */       return;
/*  54 */     String sql = "update pub_workflownote set receivedeleteflag='Y' where approvestatus!=" + WfTaskStatus.Started.getIntValue() + " and pk_checkflow=? ";
/*     */ 
/*  56 */     PersistenceManager persist = null;
/*     */     try {
/*  58 */       persist = PersistenceManager.getInstance();
/*  59 */       JdbcSession jdbc = persist.getJdbcSession();
/*     */ 
/*  61 */       SQLParameter para = new SQLParameter();
/*  62 */       for (int i = 0; i < pks.length; ++i) {
/*  63 */         para.clearParams();
/*  64 */         para.addParam(pks[i]);
/*  65 */         jdbc.addBatch(sql, para);
/*     */       }
/*  67 */       jdbc.executeBatch();
/*     */     }
/*     */     finally {
/*  70 */       if (persist != null)
/*  71 */         persist.release();
/*     */     }
/*     */   }
/*     */ 
/*     */   public UserNameObject[] getAllUser(String pkCorp)
/*     */     throws BusinessException
/*     */   {
/*  85 */     IUserManageQuery iUserQry = (IUserManageQuery)NCLocator.getInstance().lookup(IUserManageQuery.class.getName());
/*     */ 
/*  87 */     UserVO[] users = iUserQry.queryAllUsersByRight(pkCorp);
/*  88 */     if ((users == null) || (users.length == 0))
/*  89 */       return null;
/*  90 */     UserNameObject[] ret = new UserNameObject[users.length];
/*  91 */     for (int i = 0; i < users.length; ++i) {
/*  92 */       ret[i] = new UserNameObject(users[i].getUserName());
/*  93 */       ret[i].setUserPK(users[i].getPrimaryKey());
/*  94 */       ret[i].setUserCode(users[i].getUserCode());
/*     */     }
/*  96 */     return ret;
/*     */   }
/*     */ 
/*     */   public ArrayList queryWorkitems(String userPK, UFDateTime lastAccessTime)
/*     */     throws DbException
/*     */   {
/* 111 */     Workflownote2MessageVOProcessor wiProcessor = new Workflownote2MessageVOProcessor();
/*     */ 
/* 113 */     String sql = wiProcessor.getSelectSql() + " where a.checkman=? and (a.approvestatus is null or a.approvestatus!=" + WfTaskStatus.Inefficient.getIntValue() + ") and a.ts>=? and a.receivedeleteflag='N'" + " order by a.senddate desc";
/*     */ 
/* 118 */     PersistenceManager persist = null;
/*     */     try {
/* 120 */       persist = PersistenceManager.getInstance();
/* 121 */       JdbcSession jdbc = persist.getJdbcSession();
/* 122 */       SQLParameter para = new SQLParameter();
/* 123 */       para.addParam(userPK);
/*     */ 
/* 125 */       if (lastAccessTime == null)
/* 126 */         para.addParam("0");
/*     */       else {
/* 128 */         para.addParam(lastAccessTime.toString());
/*     */       }
/* 130 */       ArrayList lResult = (ArrayList)jdbc.executeQuery(sql, para, wiProcessor);
/* 131 */       ArrayList localArrayList1 = lResult;
/*     */ 
/* 135 */       return localArrayList1;
/*     */     }
/*     */     finally
/*     */     {
/* 133 */       if (persist != null)
/* 134 */         persist.release();
/*     */     }
/*     */   }
/*     */ 
/*     */   public ArrayList queryDeletedItems(String userPK, UFDateTime lastAccessTime)
/*     */     throws DbException
/*     */   {
/* 149 */     Workflownote2MessageVOProcessor wiProcessor = new Workflownote2MessageVOProcessor();
/*     */ 
/* 151 */     String sql = wiProcessor.getSelectSql() + " where a.checkman=? and a.ts>=? and a.receivedeleteflag='Y'";
/*     */ 
/* 154 */     PersistenceManager persist = null;
/*     */     try {
/* 156 */       persist = PersistenceManager.getInstance();
/* 157 */       JdbcSession jdbc = persist.getJdbcSession();
/* 158 */       SQLParameter para = new SQLParameter();
/* 159 */       para.addParam(userPK);
/*     */ 
/* 161 */       if (lastAccessTime == null)
/* 162 */         para.addParam("0");
/*     */       else {
/* 164 */         para.addParam(lastAccessTime.toString());
/*     */       }
/* 166 */       ArrayList lResult = (ArrayList)jdbc.executeQuery(sql, para, wiProcessor);
/* 167 */       ArrayList localArrayList1 = lResult;
/*     */ 
/* 171 */       return localArrayList1;
/*     */     }
/*     */     finally
/*     */     {
/* 169 */       if (persist != null)
/* 170 */         persist.release();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void insertCommonMessage(CommonMessageVO msg, String pkcorp)
/*     */     throws DbException
/*     */   {
/* 183 */     ArrayList al = new ArrayList();
/* 184 */     al.add(msg);
/* 185 */     MessageInfoDAO dao = new MessageInfoDAO();
/* 186 */     dao.insertAryCommonMessage(al, pkcorp);
/*     */   }
/*     */ 
/*     */   public void updateCommonMessage(MessageVO msg)
/*     */     throws DbException
/*     */   {
/* 198 */     MessageInfoDAO dao = new MessageInfoDAO();
/* 199 */     dao.updateCommonMessage(msg);
/*     */   }
/*     */ 
/*     */   public void insertCommonMessagesAry(ArrayList aryCommonMsg, String pkcorp) throws DbException {
/* 203 */     if (aryCommonMsg != null) {
/* 204 */       MessageInfoDAO dao = new MessageInfoDAO();
/* 205 */       dao.insertAryCommonMessage(aryCommonMsg, pkcorp);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void signMessageDeal(String checkFlowPK, UFDateTime dealTime)
/*     */     throws DbException
/*     */   {
/* 218 */     String sql = "update pub_workflownote set ischeck='Y',dealdate=? where pk_checkflow=? ";
/* 219 */     PersistenceManager persist = null;
/*     */     try {
/* 221 */       persist = PersistenceManager.getInstance();
/* 222 */       JdbcSession jdbc = persist.getJdbcSession();
/*     */ 
/* 224 */       SQLParameter para = new SQLParameter();
/*     */ 
/* 226 */       para.addParam(dealTime.toString());
/* 227 */       para.addParam(checkFlowPK);
/* 228 */       jdbc.executeUpdate(sql, para);
/*     */     } finally {
/* 230 */       if (persist != null)
/* 231 */         persist.release();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void signMessageUndeal(String checkFlowPK)
/*     */     throws DbException
/*     */   {
/* 243 */     String sql = "update pub_workflownote set ischeck='N', approvestatus=?,dealdate=null where pk_checkflow=? ";
/* 244 */     PersistenceManager persist = null;
/*     */     try {
/* 246 */       persist = PersistenceManager.getInstance();
/* 247 */       JdbcSession jdbc = persist.getJdbcSession();
/* 248 */       SQLParameter para = new SQLParameter();
/* 249 */       para.addParam(WfTaskStatus.Started.getIntValue());
/* 250 */       para.addParam(checkFlowPK);
/* 251 */       jdbc.executeUpdate(sql, para);
/*     */     } finally {
/* 253 */       if (persist != null)
/* 254 */         persist.release();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void signWorkitems(ArrayList alNotePKs, int iDeleteOrRestore)
/*     */     throws DbException
/*     */   {
/* 265 */     if ((alNotePKs == null) || (alNotePKs.size() == 0))
/* 266 */       return;
/* 267 */     String sql = "";
/* 268 */     if (iDeleteOrRestore == 0)
/*     */     {
/* 270 */       sql = "update pub_workflownote set receivedeleteflag='Y' where pk_checkflow=? ";
/*     */     }
/*     */     else
/* 273 */       sql = "update pub_workflownote set receivedeleteflag='N' where pk_checkflow=? ";
/* 274 */     PersistenceManager persist = null;
/*     */     try {
/* 276 */       persist = PersistenceManager.getInstance();
/* 277 */       JdbcSession jdbc = persist.getJdbcSession();
/* 278 */       SQLParameter para = new SQLParameter();
/* 279 */       for (int i = 0; i < alNotePKs.size(); ++i) {
/* 280 */         para.clearParams();
/* 281 */         para.addParam(alNotePKs.get(i).toString());
/* 282 */         jdbc.addBatch(sql, para);
/*     */       }
/* 284 */       jdbc.executeBatch();
/*     */     } finally {
/* 286 */       if (persist != null)
/* 287 */         persist.release();
/*     */     }
/*     */   }
/*     */ 
/*     */   public ArrayList signMessageinfos(ArrayList alInfoPKs, int iDeleteOrRestore)
/*     */     throws DbException
/*     */   {
/* 298 */     if ((alInfoPKs == null) || (alInfoPKs.size() == 0))
/* 299 */       return null;
/* 300 */     ArrayList aryPAMsg = new ArrayList();
/* 301 */     String sql = "";
/* 302 */     if (iDeleteOrRestore == 0)
/*     */     {
/* 304 */       sql = "update pub_messageinfo set receivedeleteflag='Y' where pk_messageinfo=? ";
/*     */     }
/*     */     else
/* 307 */       sql = "update pub_messageinfo set receivedeleteflag='N' where pk_messageinfo=? ";
/* 308 */     PersistenceManager persist = null;
/*     */     try {
/* 310 */       persist = PersistenceManager.getInstance();
/* 311 */       JdbcSession jdbc = persist.getJdbcSession();
/*     */ 
/* 313 */       SQLParameter para = new SQLParameter();
/* 314 */       for (int i = 0; i < alInfoPKs.size(); ++i) {
/* 315 */         para.clearParams();
/* 316 */         para.addParam(alInfoPKs.get(i).toString());
/* 317 */         jdbc.addBatch(sql, para);
/*     */       }
/* 319 */       jdbc.executeBatch();
/*     */     }
/*     */     finally {
/* 322 */       if (persist != null)
/* 323 */         persist.release();
/*     */     }
/* 325 */     return aryPAMsg;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public UserNameObject[] getAllUserByRolePKs(String roles)
/*     */     throws BusinessException
/*     */   {
/* 335 */     IUAPQueryBS qryBS = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
/* 336 */     String sql = "select distinct cuserid from sm_user_role where pk_role in(" + roles + ")";
/* 337 */     List aryResult = (List)qryBS.executeQuery(sql, new BaseProcessor() {
/*     */       public Object processResultSet(ResultSet rs) throws SQLException {
/* 339 */         ArrayList ary = new ArrayList();
/* 340 */         while (rs.next()) {
/* 341 */           UserNameObject oneUser = new UserNameObject(null);
/* 342 */           oneUser.setUserPK(rs.getString(1).trim());
/* 343 */           ary.add(oneUser);
/*     */         }
/* 345 */         return ary;
/*     */       }
/*     */     });
/* 349 */     return ((UserNameObject[])(UserNameObject[])aryResult.toArray(new UserNameObject[aryResult.size()]));
/*     */   }
/*     */ 
/*     */   public void completeWorkitem(HashSet hsBillId, String checkman, String billtype, String srcBilltype)
/*     */     throws DbException
/*     */   {
/* 362 */     String sql = "update pub_messageinfo set state=?,dealdate=? where billid=? and checkman=? and pk_billtype=? and pk_srcbilltype=? and type in (3,4,5,6)";
/*     */ 
/* 367 */     PersistenceManager persist = null;
/*     */     try {
/* 369 */       persist = PersistenceManager.getInstance();
/* 370 */       JdbcSession jdbc = persist.getJdbcSession();
/* 371 */       for (Iterator iter = hsBillId.iterator(); iter.hasNext(); ) {
/* 372 */         String billId = (String)iter.next();
/* 373 */         SQLParameter para = new SQLParameter();
/* 374 */         para.addParam(1);
/* 375 */         para.addParam(new UFDateTime(new Date()).toString());
/*     */ 
/* 377 */         para.addParam(billId);
/* 378 */         para.addParam(checkman);
/* 379 */         para.addParam(billtype);
/* 380 */         para.addParam(srcBilltype);
/*     */ 
/* 382 */         jdbc.addBatch(sql, para);
/*     */       }
/*     */ 
/* 385 */       jdbc.executeBatch();
/*     */     } finally {
/* 387 */       if (persist != null)
/* 388 */         persist.release();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setWorkflownoteFields(MessageFilter filter) {
/* 393 */     if (filter.isWorkflownoteFiltered())
/* 394 */       filter.setStatusField("a.approvestatus");
/*     */     else
/* 396 */       filter.setStatusField("a.ischeck");
/* 397 */     filter.setPriorityField("a.priority");
/* 398 */     filter.setSenderField("a.senderman");
/* 399 */     filter.setSenddateField("a.senddate");
/* 400 */     filter.setDealdateField("a.dealdate");
/* 401 */     filter.setTitleField("a.messagenote");
/* 402 */     filter.setBilltypeField("a.pk_billtype");
/*     */   }
/*     */ 
/*     */   public ArrayList queryWorkitems(String userPK, MessageFilter worklistFilter)
/*     */     throws DbException
/*     */   {
/* 415 */     Workflownote2MessageVOProcessor wiProcessor = new Workflownote2MessageVOProcessor();
/*     */ 
/* 417 */     String sql = wiProcessor.getSelectSql() + " where a.checkman=? and (a.approvestatus is null or a.approvestatus!=" + WfTaskStatus.Inefficient.getIntValue() + ") and a.receivedeleteflag='N' ";
/*     */ 
/* 422 */     setWorkflownoteFields(worklistFilter);
/*     */ 
/* 424 */     String filterWhereSql = MessageInfoDAO.contactFilterSql(worklistFilter);
/* 425 */     String orderSql = " order by senddate desc";
/*     */ 
/* 427 */     String sql2 = null;
/* 428 */     if (filterWhereSql.length() == 0)
/* 429 */       sql2 = sql + orderSql;
/*     */     else {
/* 431 */       sql2 = sql + "and" + filterWhereSql + orderSql;
/*     */     }
/* 433 */     PersistenceManager persist = null;
/*     */     try {
/* 435 */       persist = PersistenceManager.getInstance();
/* 436 */       JdbcSession jdbc = persist.getJdbcSession();
/* 437 */       SQLParameter para = new SQLParameter();
/* 438 */       para.addParam(userPK);
/*     */ 
/* 440 */       ArrayList lResult = (ArrayList)jdbc.executeQuery(sql2, para, wiProcessor);
/* 441 */       ArrayList localArrayList1 = lResult;
/*     */ 
/* 445 */       return localArrayList1;
/*     */     }
/*     */     finally
/*     */     {
/* 443 */       if (persist != null)
/* 444 */         persist.release();
/*     */     }
/*     */   }
/*     */ }

