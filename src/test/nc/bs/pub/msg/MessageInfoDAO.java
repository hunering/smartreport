/*     */ package nc.bs.pub.msg;
/*     */ 
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import nc.bs.dao.BaseDAO;
/*     */ import nc.bs.dao.DAOException;
/*     */ import nc.jdbc.framework.JdbcSession;
/*     */ import nc.jdbc.framework.PersistenceManager;
/*     */ import nc.jdbc.framework.SQLParameter;
/*     */ import nc.jdbc.framework.exception.DbException;
/*     */ import nc.jdbc.framework.generator.SequenceGenerator;
/*     */ import nc.jdbc.framework.processor.BaseProcessor;
/*     */ import nc.ui.pub.msg.MessageFilter;
/*     */ import nc.vo.jcom.lang.StringUtil;
/*     */ import nc.vo.pub.lang.UFDateTime;
/*     */ import nc.vo.pub.msg.CommonMessageVO;
/*     */ import nc.vo.pub.msg.MessageStatus;
/*     */ import nc.vo.pub.msg.MessageVO;
/*     */ import nc.vo.pub.msg.MessageinfoVO;
/*     */ import nc.vo.pub.msg.UserNameObject;
/*     */ import nc.vo.wfengine.pub.WfTaskStatus;
/*     */ 
/*     */ public class MessageInfoDAO
/*     */ {
/*     */   public Vector getReceivedMsgs(String userPK, String corppk, UFDateTime lastAccessTime)
/*     */     throws DbException
/*     */   {
/*  52 */     ArrayList ary = getRecivedMsgsCollection(userPK, corppk, lastAccessTime);
/*  53 */     return separateInfoMsgs(ary);
/*     */   }
/*     */ 
/*     */   public Vector separateInfoMsgs(ArrayList ary)
/*     */   {
/*  58 */     ArrayList aryBulletin = new ArrayList();
/*  59 */     ArrayList aryPA = new ArrayList();
/*  60 */     ArrayList aryWorkflow = new ArrayList();
/*  61 */     for (int i = 0; i < ary.size(); ++i) {
/*  62 */       MessageVO msgvo = MessageinfoVO.transMsgInfoVO2MsgVO((MessageinfoVO)ary.get(i));
/*  63 */       if ((msgvo.getMsgType() == 0) || (msgvo.getMsgType() == 10))
/*     */       {
/*  65 */         aryPA.add(msgvo);
/*  66 */       } else if ((msgvo.getMsgType() == 3) || (msgvo.getMsgType() == 4) || (msgvo.getMsgType() == 5) || (msgvo.getMsgType() == 6))
/*     */       {
/*  70 */         aryWorkflow.add(msgvo);
/*     */       }
/*     */       else aryBulletin.add(msgvo);
/*     */     }
/*  74 */     Vector vec = new Vector();
/*  75 */     vec.add(aryBulletin);
/*  76 */     vec.add(aryPA);
/*  77 */     vec.add(aryWorkflow);
/*  78 */     return vec;
/*     */   }
/*     */ 
/*     */   private ArrayList getRecivedMsgsCollection(String userPK, String corppk, UFDateTime lastAccessTime)
/*     */     throws DbException
/*     */   {
/*  93 */     String sql = "select a.pk_messageinfo,a.senderman,b.user_name,a.checkman,a.pk_corp,a.type,a.state,a.url,a.title,a.content,a.senddate,a.priority,a.dealdate,a.billid,a.billno,a.pk_billtype,a.pk_srcbilltype,a.pk_busitype,a.actiontype,a.titlecolor from pub_messageinfo a left join sm_user b on a.senderman=b.cuserid where (checkman=? and a.ts>?  or (a.type=? and (a.pk_corp=? or a.pk_corp='0001'))) and (a.receivedeleteflag is null or a.receivedeleteflag='N') order by senddate desc";
/*     */ 
/*  98 */     PersistenceManager persist = null;
/*     */     try {
/* 100 */       persist = PersistenceManager.getInstance();
/* 101 */       JdbcSession jdbc = persist.getJdbcSession();
/* 102 */       SQLParameter para = new SQLParameter();
/* 103 */       para.addParam(userPK);
/*     */ 
/* 105 */       if (lastAccessTime == null)
/* 106 */         para.addParam("0");
/*     */       else
/* 108 */         para.addParam(lastAccessTime.toString());
/* 109 */       para.addParam(-1);
/* 110 */       para.addParam(corppk);
/* 111 */       ArrayList aryResult = (ArrayList)jdbc.executeQuery(sql, para, new MessageInfoProcessor());
/* 112 */       ArrayList localArrayList1 = aryResult;
/*     */ 
/* 116 */       return localArrayList1;
/*     */     }
/*     */     finally
/*     */     {
/* 114 */       if (persist != null)
/* 115 */         persist.release();
/*     */     }
/*     */   }
/*     */ 
/*     */   public ArrayList queryDeletedInfos(String userPK, String corppk, UFDateTime lastAccessTime)
/*     */     throws DbException
/*     */   {
/* 130 */     String sql = "select a.pk_messageinfo,a.senderman,b.user_name,a.checkman,a.pk_corp,a.type,a.state,a.url,a.title,a.content,a.senddate,a.priority,a.dealdate,a.billid,a.billno,a.pk_billtype,a.pk_srcbilltype,a.pk_busitype,a.actiontype,a.titlecolor from pub_messageinfo a left join sm_user b on a.senderman=b.cuserid where (checkman=? and a.ts>? or (a.type=? and (a.pk_corp=? or a.pk_corp='0001'))) and a.receivedeleteflag='Y' order by senddate desc";
/*     */ 
/* 135 */     PersistenceManager persist = null;
/*     */     try {
/* 137 */       persist = PersistenceManager.getInstance();
/* 138 */       JdbcSession jdbc = persist.getJdbcSession();
/* 139 */       SQLParameter para = new SQLParameter();
/* 140 */       para.addParam(userPK);
/*     */ 
/* 142 */       if (lastAccessTime == null)
/* 143 */         para.addParam("0");
/*     */       else
/* 145 */         para.addParam(lastAccessTime.toString());
/* 146 */       para.addParam(-1);
/* 147 */       para.addParam(corppk);
/* 148 */       ArrayList aryResult = (ArrayList)jdbc.executeQuery(sql, para, new MessageInfoProcessor());
/* 149 */       ArrayList localArrayList1 = aryResult;
/*     */ 
/* 153 */       return localArrayList1;
/*     */     }
/*     */     finally
/*     */     {
/* 151 */       if (persist != null)
/* 152 */         persist.release();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void insertAryCommonMessage(ArrayList al, String pkcorp)
/*     */     throws DbException
/*     */   {
/* 164 */     String sql = "insert into pub_messageinfo (pk_messageinfo,senderman,checkman,type,state,url,title,content,senddate,priority,pk_corp,pk_billtype,filecontent,actiontype,billid,titlecolor) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*     */ 
/* 168 */     HashSet setHasAdded = new HashSet();
/* 169 */     for (Iterator iter = al.iterator(); iter.hasNext(); ) {
/* 170 */       CommonMessageVO msg = (CommonMessageVO)iter.next();
/* 171 */       String[] oids = new SequenceGenerator().generate(msg.getReceiver().length);
/* 172 */       PersistenceManager persist = null;
/*     */       try {
/* 174 */         persist = PersistenceManager.getInstance();
/* 175 */         JdbcSession jdbc = persist.getJdbcSession();
/*     */ 
/* 177 */         for (int i = 0; i < msg.getReceiver().length; ++i)
/*     */         {
/* 179 */           if (!(setHasAdded.contains(msg.getReceiver()[i].getUserPK()))) {
/* 180 */             SQLParameter para = new SQLParameter();
/* 181 */             para.addParam(oids[i]);
/* 182 */             para.addParam(msg.getSender());
/* 183 */             para.addParam(msg.getReceiver()[i].getUserPK());
/*     */ 
/* 185 */             para.addParam(msg.getType());
/*     */ 
/* 187 */             para.addParam(0);
/* 188 */             para.addParam((msg.getMailAddress() == null) ? null : msg.getMailAddress().trim());
/* 189 */             para.addParam(msg.getTitle());
/* 190 */             para.addParam(msg.getMessageContent());
/* 191 */             para.addParam(msg.getSendDataTime());
/* 192 */             para.addParam(msg.getPriority());
/* 193 */             para.addParam(pkcorp);
/* 194 */             para.addParam(msg.getBilltype());
/* 195 */             para.addBlobParam(msg.getFileContent());
/* 196 */             para.addParam(msg.getActionType());
/* 197 */             para.addParam(msg.getBillid());
/* 198 */             para.addParam(msg.getTitleColor());
/*     */ 
/* 200 */             jdbc.addBatch(sql, para);
/*     */ 
/* 202 */             setHasAdded.add(msg.getReceiver()[i].getUserPK());
/*     */           }
/*     */         }
/*     */ 
/* 206 */         jdbc.executeBatch();
/*     */       } finally {
/* 208 */         if (persist != null)
/* 209 */           persist.release();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteNoBizMessages(String[] pks)
/*     */     throws DbException
/*     */   {
/* 284 */     if ((pks == null) || (pks.length == 0))
/* 285 */       return;
/* 286 */     String sql = "update pub_messageinfo set receivedeleteflag='Y' where pk_messageinfo=? ";
/* 287 */     PersistenceManager persist = null;
/*     */     try {
/* 289 */       persist = PersistenceManager.getInstance();
/* 290 */       JdbcSession jdbc = persist.getJdbcSession();
/* 291 */       for (int i = 0; i < pks.length; ++i) {
/* 292 */         SQLParameter para = new SQLParameter();
/* 293 */         para.addParam(pks[i]);
/* 294 */         jdbc.executeUpdate(sql, para);
/*     */       }
/*     */     } finally {
/* 297 */       if (persist != null)
/* 298 */         persist.release();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteRecSendMessages(ArrayList pks)
/*     */     throws DbException
/*     */   {
/* 308 */     if ((pks == null) || (pks.size() == 0)) {
/* 309 */       return;
/*     */     }
/* 311 */     String sql = "update pub_messageinfo set receivedeleteflag='Y' where pk_messageinfo=? ";
/* 312 */     PersistenceManager persist = null;
/*     */     try {
/* 314 */       persist = PersistenceManager.getInstance();
/* 315 */       JdbcSession jdbc = persist.getJdbcSession();
/* 316 */       for (int i = 0; i < pks.size(); ++i) {
/* 317 */         SQLParameter para = new SQLParameter();
/* 318 */         para.addParam(pks.get(i).toString());
/* 319 */         jdbc.executeUpdate(sql, para);
/*     */       }
/*     */     } finally {
/* 322 */       if (persist != null)
/* 323 */         persist.release();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void signMessageDeal(String noBizmsgpk, UFDateTime serverTime)
/*     */     throws DbException
/*     */   {
/* 333 */     updateMsgState(noBizmsgpk, 1, serverTime);
/*     */   }
/*     */ 
/*     */   public void signMessageUnDeal(String noBizmsgpk) throws DbException {
/* 337 */     updateMsgState(noBizmsgpk, 0, null);
/*     */   }
/*     */ 
/*     */   public void signMessageSeal(ArrayList alInfoPKs, UFDateTime serverTime)
/*     */     throws DbException
/*     */   {
/* 347 */     updateMsgStateBatch(alInfoPKs, 2, serverTime);
/*     */   }
/*     */ 
/*     */   public void signMessageUnSeal(ArrayList alInfoPKs, UFDateTime serverTime)
/*     */     throws DbException
/*     */   {
/* 356 */     updateMsgStateBatch(alInfoPKs, 0, serverTime);
/*     */   }
/*     */ 
/*     */   public void updateCommonMessage(MessageVO msg)
/*     */     throws DbException
/*     */   {
/* 365 */     String sql = "";
/*     */ 
/* 367 */     if (msg.isAttachChanged()) {
/* 368 */       sql = "update pub_messageinfo set priority=?, title=?, content=?, url=?, filecontent=?  where pk_messageinfo=? ";
/*     */     }
/*     */     else {
/* 371 */       sql = "update pub_messageinfo set priority=?, title=?, content=?  where pk_messageinfo=? ";
/*     */     }
/* 373 */     PersistenceManager persist = null;
/*     */     try {
/* 375 */       persist = PersistenceManager.getInstance();
/* 376 */       JdbcSession jdbc = persist.getJdbcSession();
/* 377 */       SQLParameter para = new SQLParameter();
/* 378 */       para.addParam(msg.getPriority());
/* 379 */       para.addParam(msg.getTitle());
/* 380 */       para.addParam(msg.getMessageNote());
/* 381 */       if (msg.isAttachChanged()) {
/* 382 */         para.addParam(msg.getMailAddress());
/* 383 */         para.addBlobParam(msg.getFilecontent());
/*     */       }
/* 385 */       para.addParam(msg.getPrimaryKey());
/* 386 */       jdbc.executeUpdate(sql, para);
/*     */     } finally {
/* 388 */       if (persist != null)
/* 389 */         persist.release();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateMsgState(String noBizmsgpk, int state, UFDateTime serverTime)
/*     */     throws DbException
/*     */   {
/* 400 */     String sql = "";
/* 401 */     if (serverTime != null) {
/* 402 */       sql = "update pub_messageinfo set state=?, dealdate='" + serverTime.toString() + "'" + " where pk_messageinfo=? ";
/*     */     }
/*     */     else
/* 405 */       sql = "update pub_messageinfo set state=? where pk_messageinfo=? ";
/* 406 */     PersistenceManager persist = null;
/*     */     try {
/* 408 */       persist = PersistenceManager.getInstance();
/* 409 */       JdbcSession jdbc = persist.getJdbcSession();
/* 410 */       SQLParameter para = new SQLParameter();
/* 411 */       para.addParam(state);
/* 412 */       para.addParam(noBizmsgpk);
/* 413 */       jdbc.executeUpdate(sql, para);
/*     */     } finally {
/* 415 */       if (persist != null)
/* 416 */         persist.release();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateMsgStateBatch(ArrayList alInfoPKs, int state, UFDateTime serverTime)
/*     */     throws DbException
/*     */   {
/* 429 */     String sql = "";
/* 430 */     if (serverTime != null) {
/* 431 */       sql = "update pub_messageinfo set state=?, dealdate='" + serverTime.toString() + "'" + " where pk_messageinfo=? ";
/*     */     }
/*     */     else
/* 434 */       sql = "update pub_messageinfo set state=? where pk_messageinfo=? ";
/* 435 */     PersistenceManager persist = null;
/*     */     try {
/* 437 */       persist = PersistenceManager.getInstance();
/* 438 */       JdbcSession jdbc = persist.getJdbcSession();
/*     */ 
/* 440 */       SQLParameter para = new SQLParameter();
/* 441 */       for (int i = 0; i < alInfoPKs.size(); ++i) {
/* 442 */         para.clearParams();
/* 443 */         para.addParam(state);
/* 444 */         para.addParam(alInfoPKs.get(i).toString());
/* 445 */         jdbc.addBatch(sql, para);
/*     */       }
/* 447 */       jdbc.executeBatch();
/*     */     }
/*     */     finally {
/* 450 */       if (persist != null)
/* 451 */         persist.release();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void phyDeleteAryByPKS(ArrayList aryMsgPKs)
/*     */     throws DAOException
/*     */   {
/* 462 */     if (aryMsgPKs != null) {
/* 463 */       String[] pks = (String[])(String[])aryMsgPKs.toArray(new String[aryMsgPKs.size()]);
/* 464 */       BaseDAO dao = new BaseDAO();
/* 465 */       dao.deleteByPKs(MessageinfoVO.class, pks);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void physicalDeleteMsg(String[] pks)
/*     */     throws DAOException
/*     */   {
/* 476 */     BaseDAO dao = new BaseDAO();
/* 477 */     dao.deleteByPKs(MessageinfoVO.class, pks);
/*     */   }
/*     */ 
/*     */   public ArrayList queryBulletinMsgs(String userPK, String corppk, MessageFilter bulletinFilter)
/*     */     throws DbException
/*     */   {
/* 493 */     String bulletinSql = "select a.pk_messageinfo,a.senderman,b.user_name,a.checkman,a.pk_corp,a.type,a.state,a.url,a.title,a.content,a.senddate,a.priority,a.dealdate,a.billid,a.billno,a.pk_billtype,a.pk_srcbilltype,a.pk_busitype,a.actiontype,a.titlecolor from pub_messageinfo a left join sm_user b on a.senderman=b.cuserid where ((checkman=? and a.type=1) or (a.type=-1 and a.state<>" + MessageStatus.SEALED.getValue() + " and (a.pk_corp=? or a.pk_corp='0001'))) and (a.receivedeleteflag is null or a.receivedeleteflag='N') ";
/*     */ 
/* 505 */     setMessageinfoFields(bulletinFilter);
/*     */ 
/* 507 */     String filterWhereSql = contactFilterSql(bulletinFilter);
/* 508 */     String orderSql = " order by senddate desc";
/*     */ 
/* 510 */     String sql2 = null;
/* 511 */     if (filterWhereSql.length() == 0)
/* 512 */       sql2 = bulletinSql + orderSql;
/*     */     else
/* 514 */       sql2 = bulletinSql + "and" + filterWhereSql + orderSql;
/* 515 */     PersistenceManager persist = null;
/*     */     try {
/* 517 */       persist = PersistenceManager.getInstance();
/* 518 */       JdbcSession jdbc = persist.getJdbcSession();
/* 519 */       SQLParameter para = new SQLParameter();
/* 520 */       para.addParam(userPK);
/* 521 */       para.addParam(corppk);
/* 522 */       ArrayList aryMsgInfo = (ArrayList)jdbc.executeQuery(sql2, para, new MessageInfoProcessor());
/*     */ 
/* 525 */       ArrayList aryResult = new ArrayList();
/* 526 */       for (int i = 0; i < aryMsgInfo.size(); ++i) {
/* 527 */         MessageVO msgvo = MessageinfoVO.transMsgInfoVO2MsgVO((MessageinfoVO)aryMsgInfo.get(i));
/* 528 */         aryResult.add(msgvo);
/*     */       }
/* 530 */      
/*     */ 
/* 534 */       return aryResult;
/*     */     }
/*     */     finally
/*     */     {
/* 532 */       if (persist != null)
/* 533 */         persist.release();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setMessageinfoFields(MessageFilter filter) {
/* 538 */     filter.setStatusField("a.state");
/* 539 */     filter.setPriorityField("a.priority");
/* 540 */     filter.setSenderField("a.senderman");
/* 541 */     filter.setSenddateField("a.senddate");
/* 542 */     filter.setDealdateField("a.dealdate");
/* 543 */     filter.setTitleField("a.title");
/* 544 */     filter.setBilltypeField("a.pk_billtype");
/*     */   }
/*     */ 
/*     */   public static String contactFilterSql(MessageFilter filter)
/*     */   {
/* 553 */     String whereSql = "";
/* 554 */     boolean bNeedAnd = false;
/*     */ 
/* 559 */     if (filter.getStatus() == 1) {
/* 560 */       if (filter.isWorkflownoteFiltered()) {
/* 561 */         whereSql = whereSql + " " + filter.getStatusField() + "=" + WfTaskStatus.Started.getIntValue() + " and ischeck='N'";
/*     */       }
/*     */       else
/* 564 */         whereSql = whereSql + " " + filter.getStatusField() + "=" + 0;
/* 565 */       bNeedAnd = true;
/* 566 */     } else if (filter.getStatus() == 2) {
/* 567 */       if (filter.isWorkflownoteFiltered()) {
/* 568 */         whereSql = whereSql + " (" + filter.getStatusField() + "=" + WfTaskStatus.Finished.getIntValue() + " or ischeck='Y')";
/*     */       }
/*     */       else
/* 571 */         whereSql = whereSql + " " + filter.getStatusField() + "=" + 1;
/* 572 */       bNeedAnd = true;
/*     */     }
/*     */ 
/* 578 */     if ((filter.getPriorityOne() != -2) && (filter.getPriorityTwo() != -2))
/* 579 */       if (bNeedAnd) {
/* 580 */         whereSql = whereSql + " and (" + filter.getPriorityField() + "=" + filter.getPriorityOne() + " or " + filter.getPriorityField() + "=" + filter.getPriorityTwo() + ")";
/*     */       }
/*     */       else {
/* 583 */         whereSql = whereSql + " (" + filter.getPriorityField() + "=" + filter.getPriorityOne() + " or " + filter.getPriorityField() + "=" + filter.getPriorityTwo() + ")";
/*     */ 
/* 585 */         bNeedAnd = true;
/*     */       }
/* 587 */     else if (filter.getPriorityOne() != -2)
/* 588 */       if (bNeedAnd) {
/* 589 */         whereSql = whereSql + " and " + filter.getPriorityField() + "=" + filter.getPriorityOne();
/*     */       } else {
/* 591 */         whereSql = whereSql + " " + filter.getPriorityField() + "=" + filter.getPriorityOne();
/* 592 */         bNeedAnd = true;
/*     */       }
/* 594 */     else if (filter.getPriorityTwo() != -2) {
/* 595 */       if (bNeedAnd) {
/* 596 */         whereSql = whereSql + " and " + filter.getPriorityField() + "=" + filter.getPriorityTwo();
/*     */       } else {
/* 598 */         whereSql = whereSql + " " + filter.getPriorityField() + "=" + filter.getPriorityTwo();
/* 599 */         bNeedAnd = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 606 */     if (filter.isFilterSendDate()) {
/* 607 */       String sendDateBegin = filter.getSendDateBegin();
/* 608 */       String sendDateEnd = filter.getSendDateEnd();
/*     */ 
/* 610 */       if ((!(StringUtil.isEmpty(sendDateBegin))) && (!(StringUtil.isEmpty(sendDateEnd)))) {
/* 611 */         sendDateEnd = sendDateEnd + " 24:00:00";
/* 612 */         if (bNeedAnd) {
/* 613 */           whereSql = whereSql + " and " + filter.getSenddateField() + "<='" + sendDateEnd + "' and " + filter.getSenddateField() + ">='" + sendDateBegin + "'";
/*     */         }
/*     */         else {
/* 616 */           whereSql = whereSql + " " + filter.getSenddateField() + "<='" + sendDateEnd + "' and " + filter.getSenddateField() + ">='" + sendDateBegin + "'";
/*     */ 
/* 618 */           bNeedAnd = true;
/*     */         }
/* 620 */       } else if (!(StringUtil.isEmpty(sendDateBegin))) {
/* 621 */         if (bNeedAnd) {
/* 622 */           whereSql = whereSql + " and " + filter.getSenddateField() + ">='" + sendDateBegin + "'";
/*     */         } else {
/* 624 */           whereSql = whereSql + " " + filter.getSenddateField() + ">='" + sendDateBegin + "'";
/* 625 */           bNeedAnd = true;
/*     */         }
/* 627 */       } else if (!(StringUtil.isEmpty(sendDateEnd))) {
/* 628 */         sendDateEnd = sendDateEnd + " 24:00:00";
/* 629 */         if (bNeedAnd) {
/* 630 */           whereSql = whereSql + " and " + filter.getSenddateField() + "<='" + sendDateEnd + "'";
/*     */         } else {
/* 632 */           whereSql = whereSql + " " + filter.getSenddateField() + "<='" + sendDateEnd + "'";
/* 633 */           bNeedAnd = true;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 640 */     if (filter.isFilterDealDate()) {
/* 641 */       String dealDateBegin = filter.getDealDateBegin();
/* 642 */       String dealDateEnd = filter.getDealDateEnd();
/* 643 */       if ((!(StringUtil.isEmpty(dealDateBegin))) && (!(StringUtil.isEmpty(dealDateEnd)))) {
/* 644 */         dealDateEnd = dealDateEnd + " 24:00:00";
/* 645 */         if (bNeedAnd) {
/* 646 */           whereSql = whereSql + " and " + filter.getDealdateField() + "<='" + dealDateEnd + "' and " + filter.getDealdateField() + ">='" + dealDateBegin + "'";
/*     */         }
/*     */         else {
/* 649 */           whereSql = whereSql + " " + filter.getDealdateField() + "<='" + dealDateEnd + "' and " + filter.getDealdateField() + ">='" + dealDateBegin + "'";
/*     */ 
/* 651 */           bNeedAnd = true;
/*     */         }
/* 653 */       } else if (!(StringUtil.isEmpty(dealDateBegin))) {
/* 654 */         if (bNeedAnd) {
/* 655 */           whereSql = whereSql + " and " + filter.getDealdateField() + ">='" + dealDateBegin + "'";
/*     */         } else {
/* 657 */           whereSql = whereSql + " " + filter.getDealdateField() + ">='" + dealDateBegin + "'";
/* 658 */           bNeedAnd = true;
/*     */         }
/* 660 */       } else if (!(StringUtil.isEmpty(dealDateEnd))) {
/* 661 */         dealDateEnd = dealDateEnd + " 24:00:00";
/* 662 */         if (bNeedAnd) {
/* 663 */           whereSql = whereSql + " and " + filter.getDealdateField() + "<='" + dealDateEnd + "'";
/*     */         } else {
/* 665 */           whereSql = whereSql + " " + filter.getDealdateField() + "<='" + dealDateEnd + "'";
/* 666 */           bNeedAnd = true;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 674 */     if (filter.isFilterSender()) {
/* 675 */       if (bNeedAnd) {
/* 676 */         whereSql = whereSql + " and " + filter.getSenderField() + "='" + filter.getSender() + "'";
/*     */       } else {
/* 678 */         whereSql = whereSql + " " + filter.getSenderField() + "='" + filter.getSender() + "'";
/* 679 */         bNeedAnd = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 686 */     if (filter.isFilterBilltype()) {
/* 687 */       if (bNeedAnd) {
/* 688 */         whereSql = whereSql + " and " + filter.getBilltypeField() + "='" + filter.getBilltype() + "'";
/*     */       } else {
/* 690 */         whereSql = whereSql + " " + filter.getBilltypeField() + "='" + filter.getBilltype() + "'";
/* 691 */         bNeedAnd = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 698 */     if (filter.isFilterTitle()) {
/* 699 */       String title = filter.getTitle();
/*     */ 
/* 701 */       String[] titles = title.split("\\s+");
/* 702 */       String tsql = "";
/* 703 */       for (int i = 0; i < ((titles == null) ? 0 : titles.length); ++i) {
/* 704 */         tsql = tsql + " or " + filter.getTitleField() + " like '%" + titles[i] + "%'";
/*     */       }
/* 706 */       if (tsql != null) {
/* 707 */         if (bNeedAnd)
/* 708 */           whereSql = whereSql + " and (" + tsql.substring(3) + ")";
/*     */         else
/* 710 */           whereSql = whereSql + tsql.substring(3);
/*     */       }
/*     */     }
/* 713 */     return whereSql;
/*     */   }
/*     */ 
/*     */   public ArrayList queryPushAndPullMsgs(String userPK, MessageFilter worklistFilter)
/*     */     throws DbException
/*     */   {
/* 728 */     String worklistSql = "select a.pk_messageinfo,a.senderman,b.user_name,a.checkman,a.pk_corp,a.type,a.state,a.url,a.title,a.content,a.senddate,a.priority,a.dealdate,a.billid,a.billno,a.pk_billtype,a.pk_srcbilltype,a.pk_busitype,a.actiontype,a.titlecolor from pub_messageinfo a left join sm_user b on a.senderman=b.cuserid where checkman=? and (a.type in (3,4,5,6)) and (a.receivedeleteflag is null or a.receivedeleteflag='N') ";
/*     */ 
/* 742 */     setMessageinfoFields(worklistFilter);
/*     */ 
/* 744 */     String filterWhereSql = contactFilterSql(worklistFilter);
/* 745 */     String orderSql = " order by senddate desc";
/* 746 */     String sql2 = null;
/* 747 */     if (filterWhereSql.length() == 0)
/* 748 */       sql2 = worklistSql + orderSql;
/*     */     else {
/* 750 */       sql2 = worklistSql + "and" + filterWhereSql + orderSql;
/*     */     }
/* 752 */     PersistenceManager persist = null;
/*     */     try {
/* 754 */       persist = PersistenceManager.getInstance();
/* 755 */       JdbcSession jdbc = persist.getJdbcSession();
/* 756 */       SQLParameter para = new SQLParameter();
/* 757 */       para.addParam(userPK);
/*     */ 
/* 759 */       ArrayList aryMsgInfo = (ArrayList)jdbc.executeQuery(sql2, para, new MessageInfoProcessor());
/*     */ 
/* 762 */       ArrayList aryResult = new ArrayList();
/* 763 */       for (int i = 0; i < aryMsgInfo.size(); ++i) {
/* 764 */         MessageVO msgvo = MessageinfoVO.transMsgInfoVO2MsgVO((MessageinfoVO)aryMsgInfo.get(i));
/* 765 */         aryResult.add(msgvo);
/*     */       }
/*     */ 
/* 771 */       return aryResult;
/*     */     }
/*     */     finally
/*     */     {
/* 769 */       if (persist != null)
/* 770 */         persist.release();
/*     */     }
/*     */   }
/*     */ 
/*     */   public ArrayList queryPaMsgs(String userPK, String corppk, MessageFilter paFilter)
/*     */     throws DbException
/*     */   {
/* 786 */     String paSql = "select a.pk_messageinfo,a.senderman,b.user_name,a.checkman,a.pk_corp,a.type,a.state,a.url,a.title,a.content,a.senddate,a.priority,a.dealdate,a.billid,a.billno,a.pk_billtype,a.pk_srcbilltype,a.pk_busitype,a.actiontype,a.titlecolor from pub_messageinfo a left join sm_user b on a.senderman=b.cuserid where (checkman=? and a.type in(0,10)) and (a.receivedeleteflag is null or a.receivedeleteflag='N') ";
/*     */ 
/* 797 */     setMessageinfoFields(paFilter);
/*     */ 
/* 799 */     String filterWhereSql = contactFilterSql(paFilter);
/* 800 */     String orderSql = " order by senddate desc";
/*     */ 
/* 802 */     String sql2 = null;
/* 803 */     if (filterWhereSql.length() == 0)
/* 804 */       sql2 = paSql + orderSql;
/*     */     else {
/* 806 */       sql2 = paSql + "and" + filterWhereSql + orderSql;
/*     */     }
/* 808 */     PersistenceManager persist = null;
/*     */     try {
/* 810 */       persist = PersistenceManager.getInstance();
/* 811 */       JdbcSession jdbc = persist.getJdbcSession();
/* 812 */       SQLParameter para = new SQLParameter();
/* 813 */       para.addParam(userPK);
/*     */ 
/* 815 */       ArrayList aryMsgInfo = (ArrayList)jdbc.executeQuery(sql2, para, new MessageInfoProcessor());
/*     */ 
/* 818 */       ArrayList aryResult = new ArrayList();
/* 819 */       for (int i = 0; i < aryMsgInfo.size(); ++i) {
/* 820 */         MessageVO msgvo = MessageinfoVO.transMsgInfoVO2MsgVO((MessageinfoVO)aryMsgInfo.get(i));
/* 821 */         aryResult.add(msgvo);
/*     */       }
/*     */ 
/* 827 */       return aryResult;
/*     */     }
/*     */     finally
/*     */     {
/* 825 */       if (persist != null)
/* 826 */         persist.release();
/*     */     }
/*     */   }
/*     */ 
/*     */   public ArrayList queryMsgsByAttach(String fileName)
/*     */     throws DbException
/*     */   {
/* 838 */     String sqlCon = "url = '" + fileName + "'";
/* 839 */     PersistenceManager persist = null;
/*     */     try {
/* 841 */       persist = PersistenceManager.getInstance();
/* 842 */       ArrayList alPks = (ArrayList)persist.retrieveByClause(MessageinfoVO.class, sqlCon, new String[] { "pk_messageinfo" });
/*     */ 
/* 844 */       ArrayList localArrayList1 = alPks;
/*     */ 
/* 848 */       return localArrayList1;
/*     */     }
/*     */     finally
/*     */     {
/* 846 */       if (persist != null)
/* 847 */         persist.release();
/*     */     }
/*     */   }
/*     */ 
/*     */   class MessageInfoProcessor extends BaseProcessor
/*     */   {
/*     */     public Object processResultSet(ResultSet rs)
/*     */       throws SQLException
/*     */     {
/* 217 */       if (null == rs) return null;
/* 218 */       ArrayList al = new ArrayList();
/* 219 */       while (rs.next()) {
/* 220 */         MessageinfoVO mivo = new MessageinfoVO();
/* 221 */         String key = rs.getString(1);
/* 222 */         mivo.setPrimaryKey((key == null) ? null : key.trim());
/* 223 */         String sendman = rs.getString(2);
/* 224 */         mivo.setSenderman((sendman == null) ? null : sendman.trim());
/* 225 */         String senderName = rs.getString(3);
/* 226 */         mivo.setSendermanName((senderName == null) ? sendman : senderName.trim());
/*     */ 
/* 228 */         mivo.setCheckmanName((senderName == null) ? null : senderName.trim());
/* 229 */         String checkman = rs.getString(4);
/* 230 */         mivo.setCheckman((checkman == null) ? null : checkman.trim());
/* 231 */         String pkcorp = rs.getString(5);
/* 232 */         mivo.setPk_corp((pkcorp == null) ? null : pkcorp.trim());
/* 233 */         int type = rs.getInt(6);
/* 234 */         mivo.setType(new Integer(type));
/* 235 */         mivo.setState(new Integer(rs.getInt(7)));
/*     */ 
/* 237 */         String url = rs.getString(8);
/* 238 */         mivo.setUrl((url == null) ? null : url.trim());
/* 239 */         String title = rs.getString(9);
/* 240 */         mivo.setTitle((title == null) ? null : title.trim());
/* 241 */         String content = rs.getString(10);
/* 242 */         mivo.setContent((content == null) ? null : content.trim());
/* 243 */         String senddate = rs.getString(11);
/* 244 */         mivo.setSenddate(new UFDateTime(senddate.trim()));
/* 245 */         mivo.setPriority(new Integer(rs.getInt(12)));
/* 246 */         String dealdate = rs.getString(13);
/* 247 */         mivo.setDealdate(new UFDateTime(dealdate.trim()));
/*     */ 
/* 251 */         mivo.setBillid(rs.getString(14));
/*     */ 
/* 253 */         mivo.setBillno(rs.getString(15));
/*     */ 
/* 255 */         mivo.setPk_billtype(rs.getString(16));
/*     */ 
/* 257 */         mivo.setPk_srcbilltype(rs.getString(17));
/*     */ 
/* 259 */         mivo.setPk_busitype(rs.getString(18));
/*     */ 
/* 261 */         String actionTypeCode = rs.getString(19);
/* 262 */         mivo.setActiontype((actionTypeCode == null) ? null : actionTypeCode.trim());
/*     */ 
/* 265 */         mivo.setTitlecolor(rs.getString(20));
/*     */ 
/* 268 */         if ((type == 0) && (sendman.equals(MessageVO.getPAMutliLangName()))) {
/* 269 */           mivo.setSendermanName(MessageVO.getPAMutliLangName());
/*     */         }
/* 271 */         al.add(mivo);
/*     */       }
/* 273 */       return al;
/*     */     }
/*     */   }
/*     */ }

 