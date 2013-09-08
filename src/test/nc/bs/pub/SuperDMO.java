/*      */ package nc.bs.pub;
/*      */ 
/*      */ import java.lang.reflect.Array;
/*      */ import java.sql.Connection;
/*      */ import java.sql.DatabaseMetaData;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import nc.bs.dao.DAOException;
/*      */ import nc.jdbc.framework.DataSourceCenter;
/*      */ import nc.jdbc.framework.JdbcSession;
/*      */ import nc.jdbc.framework.PersistenceManager;
/*      */ import nc.jdbc.framework.SQLParameter;
/*      */ import nc.jdbc.framework.exception.DbException;
/*      */ import nc.jdbc.framework.mapping.MappingMeta;
/*      */ import nc.jdbc.framework.processor.BaseProcessor;
/*      */ import nc.jdbc.framework.processor.BeanMappingListProcessor;
/*      */ import nc.jdbc.framework.processor.ResultSetProcessor;
/*      */ import nc.jdbc.framework.util.DBUtil;
/*      */ import nc.vo.pub.SqlSupportVO;
/*      */ import nc.vo.pub.SuperVO;
/*      */ import nc.vo.pub.UpdateResultVO;
/*      */ import nc.vo.pub.table.TableFieldVO;
/*      */ import nc.vo.pub.table.TableVO;
/*      */ 
/*      */ /** @deprecated */
/*      */ public class SuperDMO
/*      */ {
/*   38 */   private String dataSource = null;
/*      */ 
/*   40 */   private int maxRows = 100000;
/*      */ 
/*      */   public SuperDMO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public SuperDMO(String dataSource)
/*      */   {
/*   57 */     this.dataSource = dataSource;
/*      */   }
/*      */ 
/*      */   public SuperVO[] queryByVO(SuperVO vo, Boolean isAnd)
/*      */     throws DAOException
/*      */   {
/*   72 */     PersistenceManager manager = null;
/*   73 */     Collection values = null;
/*      */     try {
/*   75 */       manager = createPersistenceManager();
/*   76 */       values = manager.retrieve(vo, isAnd.booleanValue());
/*      */     }
/*      */     catch (DbException e)
/*      */     {
/*      */     }
/*      */     finally {
/*   82 */       if (manager != null) {
/*   83 */         manager.release();
/*      */       }
/*      */     }
/*   86 */     return ((SuperVO[])(SuperVO[])values.toArray((SuperVO[])(SuperVO[])Array.newInstance(vo.getClass(), 0)));
/*      */   }
/*      */ 
/*      */   public SuperVO[] queryAll(Class className)
/*      */     throws DAOException
/*      */   {
/*   99 */     PersistenceManager manager = null;
/*  100 */     Collection values = null;
/*      */     try {
/*  102 */       manager = createPersistenceManager();
/*  103 */       values = manager.retrieveAll(className);
/*      */     }
/*      */     catch (DbException e)
/*      */     {
/*      */     }
/*      */     finally {
/*  109 */       if (manager != null)
/*  110 */         manager.release();
/*      */     }
/*  112 */     return ((SuperVO[])(SuperVO[])values.toArray((SuperVO[])(SuperVO[])Array.newInstance(className, 0)));
/*      */   }
/*      */ 
/*      */   public SuperVO[] queryByWhereClause(Class className, String condition, String orderBy)
/*      */     throws DAOException
/*      */   {
/*  125 */     StringBuffer clause = new StringBuffer();
/*      */ 
/*  127 */     if (condition != null)
/*  128 */       clause.append(condition);
/*  129 */     if ((orderBy != null) && (condition == null))
/*  130 */       clause.append("ORDER BY " + orderBy);
/*  131 */     if ((orderBy != null) && (condition != null))
/*  132 */       clause.append(" ORDER BY " + orderBy);
/*  133 */     return queryByWhereClause(className, clause.toString());
/*      */   }
/*      */ 
/*      */   public SuperVO[] queryByWhereClause(Class className, String condition)
/*      */     throws DAOException
/*      */   {
/*  144 */     PersistenceManager manager = null;
/*  145 */     Collection values = null;
/*      */     try {
/*  147 */       manager = createPersistenceManager();
/*  148 */       values = manager.retrieveByClause(className, condition);
/*      */     }
/*      */     catch (DbException e)
/*      */     {
/*      */     }
/*      */     finally {
/*  154 */       if (manager != null)
/*  155 */         manager.release();
/*      */     }
/*  157 */     return ((SuperVO[])(SuperVO[])values.toArray((SuperVO[])(SuperVO[])Array.newInstance(className, 0)));
/*      */   }
/*      */ 
/*      */   private PersistenceManager createPersistenceManager()
/*      */     throws DbException
/*      */   {
/*  163 */     PersistenceManager manager = PersistenceManager.getInstance(this.dataSource);
/*  164 */     manager.setMaxRows(this.maxRows);
/*  165 */     return manager;
/*      */   }
/*      */ 
/*      */   public Object executeQuery(String sql, SQLParameter parameter, ResultSetProcessor processor)
/*      */     throws DAOException
/*      */   {
/*  177 */     PersistenceManager manager = null;
/*  178 */     Object value = null;
/*      */     try {
/*  180 */       manager = createPersistenceManager();
/*  181 */       JdbcSession session = manager.getJdbcSession();
/*  182 */       value = session.executeQuery(sql, parameter, processor);
/*      */     }
/*      */     catch (DbException e)
/*      */     {
/*      */     }
/*      */     finally {
/*  188 */       if (manager != null)
/*  189 */         manager.release();
/*      */     }
/*  191 */     return value;
/*      */   }
/*      */ 
/*      */   public Object executeQuery(String sql, ResultSetProcessor processor)
/*      */     throws DAOException
/*      */   {
/*  202 */     PersistenceManager manager = null;
/*  203 */     Object value = null;
/*      */     try {
/*  205 */       manager = createPersistenceManager();
/*  206 */       JdbcSession session = manager.getJdbcSession();
/*  207 */       value = session.executeQuery(sql, processor);
/*      */     }
/*      */     catch (DbException e)
/*      */     {
/*      */     }
/*      */     finally {
/*  213 */       if (manager != null)
/*  214 */         manager.release();
/*      */     }
/*  216 */     return value;
/*      */   }
/*      */ 
/*      */   public SuperVO[] queryByClauses(Class supervoClass, String strWhere, String strOrderBy, Integer maxRecNo, String[] selectedFields)
/*      */     throws DAOException
/*      */   {
/*  233 */     SuperVO[] result = queryByWhereClause(supervoClass, strWhere, strOrderBy, selectedFields);
/*      */ 
/*  235 */     if ((maxRecNo != null) && (result.length < maxRecNo.intValue()))
/*  236 */       return result;
/*  237 */     SuperVO[] vo = null;
/*  238 */     if (maxRecNo != null) {
/*  239 */       vo = new SuperVO[maxRecNo.intValue()];
/*  240 */       System.arraycopy(result, 0, vo, 0, vo.length);
/*      */     } else {
/*  242 */       vo = result;
/*      */     }
/*  244 */     return vo;
/*      */   }
/*      */ 
/*      */   public SuperVO[] queryByClauses(Class c, String strWhere, String strOrderBy, Integer maxRecNo) throws Exception
/*      */   {
/*  249 */     return queryByClauses(c, strWhere, strOrderBy, maxRecNo, null);
/*      */   }
/*      */ 
/*      */   public SuperVO[] queryByWhereClause(Class className, String condition, String orderBy, String[] fields)
/*      */     throws DAOException
/*      */   {
/*  263 */     StringBuffer clause = new StringBuffer();
/*  264 */     if (condition != null)
/*  265 */       clause.append(condition);
/*  266 */     if ((orderBy != null) && (condition == null))
/*  267 */       clause.append("ORDER BY " + orderBy);
/*  268 */     if ((orderBy != null) && (condition != null)) {
/*  269 */       clause.append(" ORDER BY " + orderBy);
/*      */     }
/*  271 */     return queryByWhereClause(className, clause.toString(), fields);
/*      */   }
/*      */ 
/*      */   public SuperVO[] queryByWhereClause(Class className, String condition, String[] fields)
/*      */     throws DAOException
/*      */   {
/*  286 */     PersistenceManager manager = null;
/*  287 */     Collection values = null;
/*      */     try {
/*  289 */       manager = createPersistenceManager();
/*  290 */       values = manager.retrieveByClause(className, condition, fields);
/*      */     }
/*      */     catch (DbException e) {
/*      */     }
/*      */     finally {
/*  295 */       if (manager != null)
/*  296 */         manager.release();
/*      */     }
/*  298 */     return ((SuperVO[])(SuperVO[])values.toArray((SuperVO[])(SuperVO[])Array.newInstance(className, 0)));
/*      */   }
/*      */ 
/*      */   public SuperVO[] queryByVO(SuperVO vo, Boolean isAnd, String[] fields)
/*      */     throws DAOException
/*      */   {
/*  312 */     PersistenceManager manager = null;
/*  313 */     Collection values = null;
/*      */     try {
/*  315 */       manager = createPersistenceManager();
/*  316 */       values = manager.retrieve(vo, isAnd.booleanValue(), fields);
/*      */     }
/*      */     catch (DbException e)
/*      */     {
/*      */     }
/*      */     finally {
/*  322 */       if (manager != null)
/*  323 */         manager.release();
/*      */     }
/*  325 */     return ((SuperVO[])(SuperVO[])values.toArray((SuperVO[])(SuperVO[])Array.newInstance(vo.getClass(), 0)));
/*      */   }
/*      */ 
/*      */   public Object[] queryByClauses(Class c, SqlSupportVO[] sqlvos, String fromStr, String strWhere, String strOrderBy)
/*      */     throws DAOException
/*      */   {
	Object[] arrayOfObject=null;
/*  343 */     if ((sqlvos == null) || (sqlvos.length == 0))
/*  344 */       throw new NullPointerException("Sqlvos is null;");
/*  345 */     if (fromStr == null)
/*  346 */       throw new NullPointerException("fromStr is null;");
/*  347 */     String[][] fields = new String[2][sqlvos.length];
/*  348 */     MappingMeta meta = new MappingMeta("", "");
/*  349 */     for (int i = 0; i < sqlvos.length; ++i) {
/*  350 */       fields[0][i] = sqlvos[i].getSqlSelectField();
/*  351 */       fields[1][i] = sqlvos[i].getVoAttributeName();
/*  352 */       meta.addMapping(sqlvos[i].getVoAttributeName(), sqlvos[i].getSqlSelectField());
/*      */     }
/*      */ 
/*  355 */     PersistenceManager manager = null;
/*      */     try {
/*  357 */       manager = createPersistenceManager();
/*  358 */       JdbcSession session = manager.getJdbcSession();
/*  359 */       StringBuffer sql = new StringBuffer("SELECT ");
/*  360 */       for (int i = 0; i < fields[0].length; ++i) {
/*  361 */         sql.append(fields[0][i]);
/*  362 */         if (i != fields[0].length - 1)
/*  363 */           sql.append(",");
/*      */       }
/*  365 */       sql.append(" FROM ").append(fromStr);
/*      */ 
/*  367 */       if ((strWhere != null) && (strWhere.trim().length() != 0)) {
/*  368 */         sql.append(" WHERE ").append(strWhere);
/*      */       }
/*      */ 
/*  371 */       if ((strOrderBy != null) && (strOrderBy.trim().length() != 0)) {
/*  372 */         sql.append(" ORDER BY ").append(strOrderBy);
/*      */       }
/*  374 */       BaseProcessor processor = new BeanMappingListProcessor(c, meta);
/*  375 */       List result = (List)session.executeQuery(sql.toString(), processor);
/*      */ 
/*  377 */        arrayOfObject = result.toArray((Object[])(Object[])Array.newInstance(c, 0));
/*      */ 
/*  385 */       return arrayOfObject;
/*      */     }
/*      */     catch (DbException e)
/*      */     {
/*      */     }
/*      */     finally
/*      */     {
/*  383 */       if (manager != null)
/*  384 */         manager.release();
/*      */     }
                  return  arrayOfObject;
/*      */   }
/*      */ 
/*      */   public SuperVO[] queryByPkCorp(Class className, String pkCorp, String[] selectedFields)
/*      */     throws DAOException
/*      */   {
/*  398 */     PersistenceManager manager = null;
/*  399 */     Collection values = null;
/*      */     try {
/*  401 */       manager = createPersistenceManager();
/*  402 */       values = manager.retrieveByCorp(className, pkCorp, selectedFields);
/*      */     }
/*      */     catch (DbException e) {
/*      */     }
/*      */     finally {
/*  407 */       if (manager != null)
/*  408 */         manager.release();
/*      */     }
/*  410 */     return ((SuperVO[])(SuperVO[])values.toArray((SuperVO[])(SuperVO[])Array.newInstance(className, 0)));
/*      */   }
/*      */ 
/*      */   public SuperVO[] queryByPkCorp(Class className, String pkCorp)
/*      */     throws DAOException
/*      */   {
/*  423 */     PersistenceManager manager = null;
/*  424 */     Collection values = null;
/*      */     try {
/*  426 */       manager = createPersistenceManager();
/*  427 */       values = manager.retrieveByCorp(className, pkCorp);
/*      */     }
/*      */     catch (DbException e) {
/*      */     }
/*      */     finally {
/*  432 */       if (manager != null)
/*  433 */         manager.release();
/*      */     }
/*  435 */     return ((SuperVO[])(SuperVO[])values.toArray((SuperVO[])(SuperVO[])Array.newInstance(className, 0)));
/*      */   }
/*      */ 
/*      */   public SuperVO queryByPrimaryKey(Class className, String pk)
/*      */     throws DAOException
/*      */   {
/*  448 */     PersistenceManager manager = null;
/*  449 */     Object values = null;
/*      */     try {
/*  451 */       manager = createPersistenceManager();
/*  452 */       values = manager.retrieveByPK(className, pk);
/*      */     }
/*      */     catch (DbException e) {
/*      */     }
/*      */     finally {
/*  457 */       if (manager != null)
/*  458 */         manager.release();
/*      */     }
/*  460 */     return ((SuperVO)values);
/*      */   }
/*      */ 
/*      */   public SuperVO queryByPrimaryKey(Class className, String pk, String[] selectedFields)
/*      */     throws DAOException
/*      */   {
/*  473 */     PersistenceManager manager = null;
/*  474 */     Object values = null;
/*      */     try {
/*  476 */       manager = createPersistenceManager();
/*  477 */       values = manager.retrieveByPK(className, pk, selectedFields);
/*      */     }
/*      */     catch (DbException e) {
/*      */     }
/*      */     finally {
/*  482 */       if (manager != null)
/*  483 */         manager.release();
/*      */     }
/*  485 */     return ((SuperVO)values);
/*      */   }
/*      */ 
/*      */   public String insert(SuperVO vo)
/*      */     throws DAOException
/*      */   {
/*  495 */     PersistenceManager manager = null;
/*  496 */     String pk = null;
/*      */     try {
/*  498 */       manager = PersistenceManager.getInstance(this.dataSource);
/*  499 */       pk = manager.insertWithPK(vo);
/*      */     }
/*      */     catch (DbException e) {
/*      */     }
/*      */     finally {
/*  504 */       if (manager != null)
/*  505 */         manager.release();
/*      */     }
/*  507 */     return pk;
/*      */   }
/*      */ 
/*      */   public String[] insertList(List vos)
/*      */     throws DAOException
/*      */   {
/*  518 */     PersistenceManager manager = null;
/*  519 */     String[] pk = null;
/*      */     try {
/*  521 */       manager = PersistenceManager.getInstance(this.dataSource);
/*  522 */       pk = manager.insert(vos);
/*      */     }
/*      */     catch (DbException e) {
/*      */     }
/*      */     finally {
/*  527 */       if (manager != null)
/*  528 */         manager.release();
/*      */     }
/*  530 */     return pk;
/*      */   }
/*      */ 
/*      */   public String[] insertArray(SuperVO[] vo)
/*      */     throws DAOException
/*      */   {
/*  540 */     PersistenceManager manager = null;
/*  541 */     String[] pk = null;
/*      */     try {
/*  543 */       manager = PersistenceManager.getInstance(this.dataSource);
/*  544 */       pk = manager.insertWithPK(vo);
/*      */     }
/*      */     catch (DbException e) {
/*      */     }
/*      */     finally {
/*  549 */       if (manager != null)
/*  550 */         manager.release();
/*      */     }
/*  552 */     return pk;
/*      */   }
/*      */ 
/*      */   public void update(SuperVO vo)
/*      */     throws DAOException
/*      */   {
/*  562 */     PersistenceManager manager = null;
/*      */     try {
/*  564 */       manager = PersistenceManager.getInstance(this.dataSource);
/*  565 */       manager.update(vo);
/*      */     }
/*      */     catch (DbException e)
/*      */     {
/*      */     }
/*      */     finally {
/*  571 */       if (manager != null)
/*  572 */         manager.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateList(List vos)
/*      */     throws DAOException
/*      */   {
/*  583 */     PersistenceManager manager = null;
/*      */     try {
/*  585 */       manager = PersistenceManager.getInstance(this.dataSource);
/*  586 */       manager.update(vos);
/*      */     }
/*      */     catch (DbException e)
/*      */     {
/*      */     }
/*      */     finally {
/*  592 */       if (manager != null)
/*  593 */         manager.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateArray(SuperVO[] vo)
/*      */     throws DAOException
/*      */   {
/*  604 */     PersistenceManager manager = null;
/*      */     try {
/*  606 */       manager = PersistenceManager.getInstance(this.dataSource);
/*  607 */       manager.update(vo);
/*      */     }
/*      */     catch (DbException e)
/*      */     {
/*      */     }
/*      */     finally {
/*  613 */       if (manager != null)
/*  614 */         manager.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateArray(SuperVO[] vos, String[] fieldNames)
/*      */     throws DAOException
/*      */   {
/*  621 */     PersistenceManager manager = null;
/*      */     try {
/*  623 */       manager = PersistenceManager.getInstance(this.dataSource);
/*  624 */       manager.update(vos, fieldNames);
/*      */     }
/*      */     catch (DbException e)
/*      */     {
/*      */     }
/*      */     finally {
/*  630 */       if (manager != null)
/*  631 */         manager.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void update(SuperVO vos, String[] fieldNames)
/*      */     throws DAOException
/*      */   {
/*  638 */     updateArray(new SuperVO[] { vos }, fieldNames);
/*      */   }
/*      */ 
/*      */   public void delete(SuperVO vo)
/*      */     throws DAOException
/*      */   {
/*  651 */     PersistenceManager manager = null;
/*      */     try {
/*  653 */       manager = PersistenceManager.getInstance(this.dataSource);
/*  654 */       manager.delete(vo);
/*      */     }
/*      */     catch (DbException e)
/*      */     {
/*      */     }
/*      */     finally {
/*  660 */       if (manager != null)
/*  661 */         manager.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteArray(SuperVO[] vos)
/*      */     throws DAOException
/*      */   {
/*  675 */     PersistenceManager manager = null;
/*      */     try {
/*  677 */       manager = PersistenceManager.getInstance(this.dataSource);
/*  678 */       manager.delete(vos);
/*      */     }
/*      */     catch (DbException e)
/*      */     {
/*      */     }
/*      */     finally {
/*  684 */       if (manager != null)
/*  685 */         manager.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteList(List vos)
/*      */     throws DAOException
/*      */   {
/*  699 */     PersistenceManager manager = null;
/*      */     try {
/*  701 */       manager = PersistenceManager.getInstance(this.dataSource);
/*  702 */       manager.delete(vos);
/*      */     }
/*      */     catch (DbException e)
/*      */     {
/*      */     }
/*      */     finally {
/*  708 */       if (manager != null)
/*  709 */         manager.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteArrayByPKs(Class className, String[] pks)
/*      */     throws DAOException
/*      */   {
/*  726 */     PersistenceManager manager = null;
/*      */     try {
/*  728 */       manager = PersistenceManager.getInstance(this.dataSource);
/*  729 */       manager.deleteByPKs(className, pks);
/*      */     }
/*      */     catch (DbException e)
/*      */     {
/*      */     }
/*      */     finally {
/*  735 */       if (manager != null)
/*  736 */         manager.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteByWhereClause(Class className, String wherestr)
/*      */     throws DAOException
/*      */   {
/*  753 */     PersistenceManager manager = null;
/*      */     try
/*      */     {
/*  756 */       manager = PersistenceManager.getInstance(this.dataSource);
/*  757 */       manager.deleteByClause(className, wherestr);
/*      */     }
/*      */     catch (DbException e)
/*      */     {
/*      */     }
/*      */     finally {
/*  763 */       if (manager != null)
/*  764 */         manager.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteByPK(Class className, String pk)
/*      */     throws DAOException
/*      */   {
/*  780 */     PersistenceManager manager = null;
/*      */     try
/*      */     {
/*  783 */       manager = PersistenceManager.getInstance(this.dataSource);
/*  784 */       manager.deleteByPK(className, pk);
/*      */     }
/*      */     catch (DbException e)
/*      */     {
/*      */     }
/*      */     finally {
/*  790 */       if (manager != null)
/*  791 */         manager.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getDBType()
/*      */     throws DAOException
/*      */   {
/*  806 */     return DataSourceCenter.getInstance().getDatabaseType(this.dataSource);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public TableVO getTable(String tableName)
/*      */     throws DAOException
/*      */   {
/*  825 */     TableVO table = new TableVO(tableName);
/*  826 */     PersistenceManager manager = null;
/*  827 */     ResultSet pkRs = null;
/*  828 */     ResultSet rs = null;
/*      */     try {
/*  830 */       manager = PersistenceManager.getInstance(this.dataSource);
/*  831 */       int dbType = getDBType();
/*  832 */       DatabaseMetaData dbmd = manager.getMetaData();
/*      */ 
/*  834 */       pkRs = dbmd.getPrimaryKeys(manager.getCatalog(), manager.getSchema(), getTableName(dbType, tableName));
/*      */ 
/*  836 */       ArrayList list = new ArrayList();
/*  837 */       while (pkRs.next())
/*      */       {
/*  839 */         list.add(pkRs.getString("COLUMN_NAME"));
/*      */       }
/*  841 */       if (list.size() > 0) {
/*  842 */         String[] priAry = new String[list.size()];
/*  843 */         list.toArray(priAry);
/*  844 */         table.setTablePrimaryKey(priAry);
/*      */       }
/*      */ 
/*  848 */       list.clear();
/*  849 */       rs = dbmd.getColumns(manager.getCatalog(), manager.getSchema(), getTableName(dbType, tableName), null);
/*      */ 
/*  851 */       while (rs.next()) {
/*  852 */         rs.getString("TABLE_CAT");
/*  853 */         rs.getString("TABLE_SCHEM");
/*  854 */         rs.getString("TABLE_NAME");
/*      */ 
/*  856 */         String columnName = rs.getString("COLUMN_NAME");
/*      */ 
/*  858 */         short dataType = rs.getShort("DATA_TYPE");
/*      */ 
/*  860 */         String typeName = rs.getString("TYPE_NAME");
/*      */ 
/*  862 */         int columnSize = rs.getInt("COLUMN_SIZE");
/*  863 */         rs.getInt("DECIMAL_DIGITS");
/*      */ 
/*  865 */         rs.getInt("NUM_PREC_RADIX");
/*      */ 
/*  867 */         rs.getInt("NULLABLE");
/*  868 */         String remarks = rs.getString("REMARKS");
/*      */ 
/*  870 */         rs.getInt("SQL_DATA_TYPE");
/*  871 */         rs.getInt("SQL_DATETIME_SUB");
/*  872 */         rs.getInt("CHAR_OCTET_LENGTH");
/*      */ 
/*  874 */         int ordinalPosition = rs.getInt("ORDINAL_POSITION");
/*  875 */         String isNullable = rs.getString("IS_NULLABLE");
/*      */ 
/*  877 */         TableFieldVO field = new TableFieldVO();
/*  878 */         field.setFieldName(columnName);
/*  879 */         field.setMaxLength(columnSize);
/*  880 */         field.setDataType(dataType);
/*  881 */         field.setDataTypeName(typeName);
/*  882 */         field.setNote(remarks);
/*      */ 
/*  884 */         field.setIndex(ordinalPosition);
/*  885 */         if (isNullable.equals("YES"))
/*  886 */           field.setNullAllowed(true);
/*      */         else
/*  888 */           field.setNullAllowed(false);
/*  889 */         list.add(field);
/*      */       }
/*  891 */       if (list.size() > 0) {
/*  892 */         TableFieldVO [] tableFields = new TableFieldVO[list.size()];
/*  893 */         list.toArray(tableFields);
/*  894 */         table.setTableFields(tableFields);
/*      */       }
/*      */       else {
/*  897 */         table = null;
/*      */       }
/*  899 */       
/*      */ 
/*  908 */       return table;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */     finally
/*      */     {
/*  905 */       DBUtil.closeRs(pkRs);
/*  906 */       DBUtil.closeRs(rs);
/*  907 */       manager.release();
/*      */     }
				return table;
/*      */   }
/*      */ 
/*      */   public String[] getTableNames(String strPrefix)
/*      */     throws DAOException
/*      */   {
/*  925 */     String[] tableNames = null;
/*  926 */     PersistenceManager manager = null;
/*  927 */     ResultSet rs = null;
/*      */     try {
/*  929 */       manager = PersistenceManager.getInstance(this.dataSource);
/*  930 */       int dbType = getDBType();
/*  931 */       List vec = new ArrayList();
/*  932 */       DatabaseMetaData dbmd = manager.getMetaData();
/*  933 */       String tableNamePattern = "%";
/*  934 */       if (strPrefix != null)
/*  935 */         tableNamePattern = strPrefix + "%";
/*  936 */       String[] tableTypes = { "TABLE", "VIEW" };
/*  937 */       rs = dbmd.getTables(manager.getCatalog(), manager.getSchema(), getTableName(dbType, tableNamePattern), tableTypes);
/*      */ 
/*  939 */       while (rs.next()) {
/*  940 */         String name = rs.getString("TABLE_NAME");
/*  941 */         vec.add(name);
/*      */       }
/*  943 */       if (vec.size() > 0) {
/*  944 */         tableNames = new String[vec.size()];
/*  945 */         tableNames = (String[])(String[])vec.toArray(tableNames);
/*      */       }
/*  947 */     
/*  954 */       return tableNames;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */     finally
/*      */     {
/*  952 */       DBUtil.closeRs(rs);
/*  953 */       manager.release();
/*      */     }
return tableNames;
/*      */   }
/*      */ 
/*      */   protected String getTableName(int dbType, String tableName)
/*      */   {
/*  968 */     String strTn = null;
/*  969 */     switch (dbType)
/*      */     {
/*      */     case 2:
/*  971 */       strTn = tableName;
/*  972 */       break;
/*      */     case 0:
/*      */     case 1:
/*  976 */       strTn = tableName.toUpperCase();
/*      */     }
/*      */ 
/*  979 */     return strTn;
/*      */   }
/*      */ 
/*      */   public boolean isTableExisted(String tableName)
/*      */     throws DAOException
/*      */   {
/*  992 */     if (tableName == null)
/*  993 */       throw new NullPointerException("TableName is null!");
/*  994 */     PersistenceManager manager = null;
/*  995 */     ResultSet rs = null;
/*      */     try {
/*  997 */       manager = PersistenceManager.getInstance(this.dataSource);
/*  998 */       int dbType = manager.getDBType();
/*  999 */       DatabaseMetaData dbmd = manager.getMetaData();
/*      */ 
/* 1002 */       rs = dbmd.getTables(manager.getCatalog(), manager.getSchema(), getTableName(dbType, tableName), new String[] { "TABLE" });
/*      */ 
/* 1004 */       if (rs.next()) {
/* 1005 */          
/*      */         return true;
/*      */       }
/* 1007 */      
/* 1014 */       return false;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */     finally
/*      */     {
/* 1012 */       DBUtil.closeRs(rs);
/* 1013 */       manager.release();
/*      */     }
return false;
/*      */   }
/*      */ 
/*      */   public UpdateResultVO execUpdateByVoState(SuperVO[] vos, String[] selectedFields)
/*      */     throws DAOException
/*      */   {
/* 1027 */     ArrayList listInsert = new ArrayList();
/* 1028 */     ArrayList listUpdate = new ArrayList();
/* 1029 */     ArrayList listDelete = new ArrayList();
/* 1030 */     for (int i = 0; i < vos.length; ++i) {
/* 1031 */       int status = vos[i].getStatus();
/* 1032 */       if (status == 2)
/* 1033 */         listInsert.add(vos[i]);
/* 1034 */       else if (status == 1)
/* 1035 */         listUpdate.add(vos[i]);
/* 1036 */       else if (status == 3)
/* 1037 */         listDelete.add(vos[i]);
/*      */     }
/* 1039 */     UpdateResultVO rsVO = new UpdateResultVO();
/* 1040 */     if (listInsert.size() > 0) {
/* 1041 */       rsVO.setPks(insertArray((SuperVO[])(SuperVO[])listInsert.toArray(new SuperVO[listInsert.size()])));
/*      */     }
/*      */ 
/* 1045 */     if (listUpdate.size() > 0) {
/* 1046 */       updateArray((SuperVO[])(SuperVO[])listUpdate.toArray(new SuperVO[listUpdate.size()]), selectedFields);
/*      */     }
/*      */ 
/* 1049 */     if (listDelete.size() > 0) {
/* 1050 */       deleteArray((SuperVO[])(SuperVO[])listDelete.toArray(new SuperVO[listDelete.size()]));
/*      */     }
/*      */ 
/* 1053 */     return rsVO;
/*      */   }
/*      */ 
/*      */   public UpdateResultVO execUpdateByVoState(SuperVO[] vos)
/*      */     throws DAOException
/*      */   {
/* 1066 */     return execUpdateByVoState(vos, null);
/*      */   }
/*      */ 
/*      */   protected void closeConnection(Connection con) {
/*      */     try {
/* 1071 */       if (con != null)
/* 1072 */         con.close();
/*      */     }
/*      */     catch (Exception e) {
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void closeStmt(Statement stmt) {
/*      */     try {
/* 1080 */       if (stmt != null)
/* 1081 */         stmt.close();
/*      */     }
/*      */     catch (Exception e) {
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void closeStmtAndCon(Statement stmt, Connection con) {
/* 1088 */     closeStmt(stmt);
/* 1089 */     closeConnection(con);
/*      */   }
/*      */ 
/*      */   public int getMaxRows() {
/* 1093 */     return this.maxRows;
/*      */   }
/*      */ 
/*      */   public void setMaxRows(int maxRows) {
/* 1097 */     this.maxRows = maxRows;
/*      */   }
/*      */ }

 