package veasion.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;
import veasion.bean.StaticValue;
import veasion.bean.Where;
import veasion.dao.JdbcDao;
import veasion.service.BeanService;
import veasion.util.PageModel;
import veasion.util.TextUtil;

public class BeanServieImpl implements BeanService{
	/**
	 * Logger. 
	 */
	private final Logger LOGGER=Logger.getLogger(BeanServieImpl.class);
	/**
	 * JDBC. 
	 */
	JdbcDao dao=new JdbcDao();
	/**
	 * ±íÃû
	 */
	private String tableName;
	
	public BeanServieImpl(final String tableName){
		this.tableName=tableName;
	};
	
	
	@Override
	public final int Add(JSONObject json) {
		List<String> keys=TextUtil.getKeys(json);
		StringBuilder sql=new StringBuilder();
		sql.append("INSERT INTO ").append(tableName);
		sql.append("(");
		sql.append(TextUtil.getKeys(keys, ","));
		sql.append(") ");
		sql.append("VALUES(");
		sql.append(TextUtil.getSymbol("?", ",", keys.size()));
		sql.append(")");
		if(StaticValue.PRINT_SQL)
			System.out.println(sql);
		return dao.executeInsert(sql.toString(),TextUtil.getValues(json, keys));
	}
	
	@Override
	public final int Update(JSONObject setJson,JSONObject whereJson) {
		List<String> setKeys=TextUtil.getKeys(setJson);
		List<String> whereKeys=TextUtil.getKeys(whereJson);
		StringBuilder sql=new StringBuilder();
		sql.append("UPDATE ").append(tableName).append(" SET ");
		sql.append(TextUtil.getKeys(setKeys, "=?,"));
		sql.append("=? ").append("WHERE ");
		sql.append(TextUtil.getKeys(whereKeys, "=? AND "));
		sql.append("=?");
		Object[]values=TextUtil.joinObject(
				TextUtil.getValues(setJson, setKeys), 
				TextUtil.getValues(whereJson, whereKeys)
				);
		if(StaticValue.PRINT_SQL)
			System.out.println(sql);
		return dao.executeUpdate(sql.toString(),values);
	}

	@Override
	public int Delete(String column,Object value) {
		StringBuilder sql=new StringBuilder();
		sql.append("DELETE FROM ").append(tableName);
		sql.append(" WHERE ").append(column);
		sql.append("=?");
		if(StaticValue.PRINT_SQL)
			System.out.println(sql);
		return dao.executeUpdate(sql.toString(), new Object[]{value});
	}
	
	@Override
	public int Count(List<Where> wheres) {
		List<String> sqls=new ArrayList<>();
		List<Object> values=new ArrayList<>();
		for (Where w : wheres) {
			sqls.add(w.getSQL().toString());
			values.add(w.getValue());
		}
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT COUNT(*) FROM ");
		sql.append(tableName);
		if(sqls.size()>0)
			sql.append(" WHERE ");
		sql.append(TextUtil.getKeys(sqls, " AND "));
		if(StaticValue.PRINT_SQL)
			System.out.println(sql);
		Object result=dao.QueryOnly(sql.toString(), TextUtil.getObjectByList(values));
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
		if(StaticValue.PRINT_SQL)
			System.out.println(sql);
		List<Map<String, Object>> result=dao.Query(sql.toString(), new Object[]{value});
		if(result==null||result.size()<1)
			return null;
		return result.get(0);
	}
	
	@Override
	public List<Map<String, Object>> Query(List<Where> wheres) {
		List<String> sqls=new ArrayList<>();
		List<Object> values=new ArrayList<>();
		for (Where w : wheres) {
			sqls.add(w.getSQL().toString());
			values.add(w.getValue());
		}
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT * FROM ").append(tableName);
		if(sqls.size()>0)
			sql.append(" WHERE ");
		sql.append(TextUtil.getKeys(sqls, " AND "));
		if(StaticValue.PRINT_SQL)
			System.out.println(sql);
		return dao.Query(sql.toString(), TextUtil.getObjectByList(values));
	}
	
	@Override
	public List<Map<String, Object>> Query(List<Where> wheres, PageModel pm) {
		List<String> sqls=new ArrayList<>();
		List<Object> values=new ArrayList<>();
		for (Where w : wheres) {
			sqls.add(w.getSQL().toString());
			values.add(w.getValue());
		}
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT * FROM ").append(tableName);
		if(sqls.size()>0)
			sql.append(" WHERE ");
		sql.append(TextUtil.getKeys(sqls, " AND "));
		String countSQL=sql.toString().replace("*", "COUNT(*)");
		sql.append(" LIMIT ?,?");
		if(StaticValue.PRINT_SQL)
			System.out.println(countSQL);
		Object countObj=dao.QueryOnly(countSQL, TextUtil.getObjectByList(values));
		if(countObj!=null){
			pm.setCount(Integer.parseInt(String.valueOf(countObj)));
		}
		int indexPage=pm.getIndexPage();
		int pageCount=pm.getPageCount();
		values.add((indexPage-1)*pageCount);
		values.add((indexPage-1)*pageCount+pageCount);
		if(StaticValue.PRINT_SQL)
			System.out.println(sql);
		return dao.Query(sql.toString(), TextUtil.getObjectByList(values));
	}
	
}
