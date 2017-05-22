package veasion.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import net.sf.json.JSONObject;
import veasion.constant.Constant;
import veasion.dao.JdbcDao;
import veasion.dao.Relation;
import veasion.dao.Where;
import veasion.service.BeanService;
import veasion.util.PageModel;
import veasion.util.SQLUtil;

/**
 * 基本数据操作Service实现类 For Mysql.
 * @author zhuowei.luo
 * @date 2017/5/7
 */
public class MysqlServieImpl implements BeanService{
	/**
	 * Logger. 
	 */
	private final Logger LOGGER=Logger.getLogger(MysqlServieImpl.class);
	/**
	 * JDBC. 
	 */
	JdbcDao dao=new JdbcDao();
	/**
	 * 表名
	 */
	private String tableName;
	
	/**
	 * 基本数据操作Service实现类 For Mysql.
	 * @param tableName 数据表名
	 */
	public MysqlServieImpl(final String tableName){
		this.tableName=tableName;
	};
	
	
	@Override
	public final int Add(JSONObject json) {
		List<String> keys=SQLUtil.getKeys(json);
		StringBuilder sql=new StringBuilder();
		sql.append("INSERT INTO ").append(tableName);
		sql.append("(");
		sql.append(SQLUtil.getKeys(keys, ","));
		sql.append(") ");
		sql.append("VALUES(");
		sql.append(SQLUtil.getSymbol("?", ",", keys.size()));
		sql.append(")");
		return dao.executeInsert(sql.toString(),SQLUtil.getValues(json, keys));
	}
	
	@Override
	public final int Update(JSONObject setJson,JSONObject whereJson) {
		List<String> setKeys=SQLUtil.getKeys(setJson);
		List<String> whereKeys=SQLUtil.getKeys(whereJson);
		StringBuilder sql=new StringBuilder();
		sql.append("UPDATE ").append(tableName).append(" SET ");
		sql.append(SQLUtil.getKeys(setKeys, "=?,"));
		sql.append("=? ").append("WHERE ");
		sql.append(SQLUtil.getKeys(whereKeys, "=? AND "));
		sql.append("=?");
		Object[]values=SQLUtil.joinObject(
				SQLUtil.getValues(setJson, setKeys), 
				SQLUtil.getValues(whereJson, whereKeys)
				);
		return dao.executeUpdate(sql.toString(),values);
	}

	@Override
	public int Delete(String column,Object value) {
		StringBuilder sql=new StringBuilder();
		sql.append("DELETE FROM ").append(tableName);
		sql.append(" WHERE ").append(column);
		sql.append("=?");
		return dao.executeUpdate(sql.toString(), new Object[]{value});
	}
	
	@Override
	public int Count(List<Where> wheres) {
		List<String> sqls=new ArrayList<>();
		List<Object> values=new ArrayList<>();
		if(wheres!=null){
			for (Where w : wheres) {
				sqls.add(w.getSQL(null).toString());
				values.add(w.getValue());
			}
		}
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT COUNT(*) FROM ");
		sql.append(tableName);
		if(sqls.size()>0)
			sql.append(" WHERE ");
		sql.append(SQLUtil.getKeys(sqls, " AND "));
		Object result=dao.QueryOnly(sql.toString(), SQLUtil.getObjectByList(values));
		int count=0;
		if(result==null){
			count=-1;
		}else{
			try{
				count=Integer.parseInt(result.toString());
			}catch(Exception e){count=-1;}
		}
		return count;
	}
	
	@Override
	public Map<String, Object> QueryById(String column, Object value) {
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT * FROM ").append(tableName);
		sql.append(" WHERE ").append(column);
		sql.append(" =? ");
		List<Map<String, Object>> result=dao.Query(sql.toString(), new Object[]{value});
		if(result==null||result.size()<1)
			return null;
		return result.get(0);
	}
	
	@Override
	public List<Map<String, Object>> Query(List<Where> wheres) {
		List<String> sqls=new ArrayList<>();
		List<Object> values=new ArrayList<>();
		if(wheres!=null){
			for (Where w : wheres) {
				sqls.add(w.getSQL(null).toString());
				values.add(w.getValue());
			}
		}
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT * FROM ").append(tableName);
		if(sqls.size()>0)
			sql.append(" WHERE ");
		sql.append(SQLUtil.getKeys(sqls, " AND "));
		return dao.Query(sql.toString(), SQLUtil.getObjectByList(values));
	}
	
	@Override
	public List<Map<String, Object>> Query(List<Where> wheres, PageModel pm) {
		List<String> sqls=new ArrayList<>();
		List<Object> values=new ArrayList<>();
		if(wheres!=null){
			for (Where w : wheres) {
				sqls.add(w.getSQL(null).toString());
				values.add(w.getValue());
			}
		}
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT * FROM ").append(tableName);
		if(sqls.size()>0)
			sql.append(" WHERE ");
		sql.append(SQLUtil.getKeys(sqls, " AND "));
		String countSQL=sql.toString().replace("*", "COUNT(*)");
		sql.append(" LIMIT ?,?");
		Object countObj=dao.QueryOnly(countSQL, SQLUtil.getObjectByList(values));
		if(countObj!=null){
			pm.setCount(Integer.parseInt(String.valueOf(countObj)));
		}
		int indexPage=pm.getIndexPage();
		int pageCount=pm.getPageCount();
		values.add((indexPage-1)*pageCount);
		values.add((indexPage-1)*pageCount+pageCount);
		return dao.Query(sql.toString(), SQLUtil.getObjectByList(values));
	}


	@Override
	public List<Map<String, Object>> MultiTableQuery(Map<String, List<Where>> whereMap,List<Relation> relations,PageModel pm) {
		//select * from A a,B b,C c where 1=1 and a.id=b.id and b.id=c.id and a.xxx=? and b.xxx=? and c.xx=? limit ?,?
		
		if(whereMap==null)whereMap=new HashedMap();
		
		//select * from A a,B b,C c 
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT * FROM ");
		Map<String, String> tableAs=new HashMap<>();
		if(!whereMap.containsKey(tableName)){
			whereMap.put(tableName, null);
		}
		if(relations!=null && !relations.isEmpty()){
			for (Relation r : relations) {
				if(!whereMap.containsKey(r.getTableName1())){
					whereMap.put(r.getTableName1(), null);
				}
				if(!whereMap.containsKey(r.getTableName2())){
					whereMap.put(r.getTableName2(), null);
				}
			}
		}
		Set<String> tables=whereMap.keySet();
		String asTemp=null;
		for (String table : tables) {
			asTemp=Constant.AUTHOR+"_"+table;
			tableAs.put(table,asTemp);
			sql.append(table).append(" ");
			sql.append(asTemp).append(",");
		}
		
		sql.setLength(sql.length()-1);
		
		//where 1=1 and a.id=b.id and b.id=c.id 
		sql.append(" WHERE 1=1 ");
		if(relations!=null && !relations.isEmpty()){
			for (Relation r : relations) {
				sql.append(" AND ");
				sql.append(r.getSQL(tableAs));
			}
		}
		List<Object> values=new ArrayList<>();
		for (String table : tables) {
			//and a.xxx=? and b.xxx=? and c.xx=? 
			List<Where> list=whereMap.get(table);
			if(list!=null){
				for (Where w : list) {
					sql.append(" AND ");
					sql.append(w.getSQL(tableAs.get(table)));
					values.add(w.getValue());
				}
			}
		}
		//不分页
		if(pm==null){
			return dao.Query(sql.toString(), SQLUtil.getObjectByList(values));
		}
		//分页，查询count
		String countSQL=sql.toString().replace("*", "COUNT(*)");
		Object countObj=dao.QueryOnly(countSQL, SQLUtil.getObjectByList(values));
		if(countObj!=null){
			pm.setCount(Integer.parseInt(String.valueOf(countObj)));
		}
		
		//分页，查询结果
		sql.append(" LIMIT ?,?");
		int indexPage=pm.getIndexPage();
		int pageCount=pm.getPageCount();
		values.add((indexPage-1)*pageCount);
		values.add((indexPage-1)*pageCount+pageCount);
		return dao.Query(sql.toString(), SQLUtil.getObjectByList(values));
	}
	
	
	
}
