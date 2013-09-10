package nc.bs.smp;

import javax.naming.NamingException;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.DataManageObject;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.exception.DbException;
import nc.jdbc.framework.processor.ResultSetProcessor;

public class SMPBaseDAO extends DataManageObject{

	private String dbName;		//数据源
	private int maxRows = 100000;	//最大返回结果集
	
	public SMPBaseDAO() throws NamingException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SMPBaseDAO(String dbsource) throws NamingException {
		super(dbsource);
		this.dbName=dbsource;
		// TODO Auto-generated constructor stub
	}

	private BaseDAO dao = null;
	
	public BaseDAO getBaseDAO(){
		if(dao==null){
			dao = new BaseDAO();
		}
		return dao;
	}
	
	/**
	 * 查询
	 * @param sql
	 * @param parameter
	 * @param processor
	 * @return
	 * @throws DAOException
	 */
	public Object executeQuery(String sql, SQLParameter parameter, ResultSetProcessor processor) throws DAOException{
		
		PersistenceManager manager = null;
	    Object value = null;
	    try {
	    	manager = createPersistenceManager(getDataSourceName());
	    	JdbcSession session = manager.getJdbcSession();
	    	session.setSQLTranslator(false);		//是否翻译SQL
	    	value = session.executeQuery(sql, parameter, processor);
	    }catch (DbException e){
	    	
	    }finally {
	      if (manager != null)
	        manager.release();
	    }
	    return value;
		
	}
	
	private String getDataSourceName(){
        if(dbName != null)
            return dbName;
        String dsName = InvocationInfoProxy.getInstance().getUserDataSource();
        if(isEmpty(dsName))
            dsName = InvocationInfoProxy.getInstance().getDefaultDataSource();
        if(isEmpty(dsName))
            dsName = "design";
        return dsName;
    }
	
	private PersistenceManager createPersistenceManager(String ds) throws DbException{
	    PersistenceManager manager = PersistenceManager.getInstance(ds);
	    manager.setMaxRows(maxRows);
	    return manager;
	}
	
	private boolean isEmpty(String str) {
        return str == null || "".equals(str.trim()) || "null".equals(str.trim());
    }
	
}
