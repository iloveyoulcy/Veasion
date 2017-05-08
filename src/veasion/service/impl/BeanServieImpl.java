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
import veasion.util.VeasionUtil;

/**
 * 基本数据操作Service实现类 For Mysql.
 * @author zhuowei.luo
 * @date 2017/5/7
 */
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
	 * 表名
	 */
	private String tableName;
	
	/**
	 * 基本数据操作Service实现类 For Mysql.
	 * @param tableName 数据表名
	 */
	public BeanServieImpl(final String tableName){
		this.tableName=tableName;
	};
	
	
	@Override
	public final int Add(JSONObject json) {
		List<String> keys=VeasionUtil.getKeys(json);
		StringBuilder sql=new StringBuilder();
		sql.append("INSERT INTO ").append(tableName);
		sql.append("(");
		sql.append(VeasionUtil.getKeys(keys, ","));
		sql.append(") ");
		sql.append("VALUES(");
		sql.append(VeasionUtil.getSymbol("?", ",", keys.size()));
		sql.append(")");
		if(StaticValue.PRINT_SQL)
			System.out.println(sql);
		return dao.executeInsert(sql.toString(),VeasionUtil.getValues(json, keys));
	}
	
	@Override
	public final int Update(JSONObject setJson,JSONObject whereJson) {
		List<String> setKeys=VeasionUtil.getKeys(setJson);
		List<String> whereKeys=VeasionUtil.getKeys(whereJson);
		StringBuilder sql=new StringBuilder();
		sql.append("UPDATE ").append(tableName).append(" SET ");
		sql.append(VeasionUtil.getKeys(setKeys, "=?,"));
		sql.append("=? ").append("WHERE ");
		sql.append(VeasionUtil.getKeys(whereKeys, "=? AND "));
		sql.append("=?");
		Object[]values=VeasionUtil.joinObject(
				VeasionUtil.getValues(setJson, setKeys), 
				VeasionUtil.getValues(whereJson, whereKeys)
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
		sql.append(VeasionUtil.getKeys(sqls, " AND "));
		if(StaticValue.PRINT_SQL)
			System.out.println(sql);
		Object result=dao.QueryOnly(sql.toString(), VeasionUtil.getObjectByList(values));
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
		sql.append(VeasionUtil.getKeys(sqls, " AND "));
		if(StaticValue.PRINT_SQL)
			System.out.println(sql);
		return dao.Query(sql.toString(), VeasionUtil.getObjectByList(values));
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
		sql.append(VeasionUtil.getKeys(sqls, " AND "));
		String countSQL=sql.toString().replace("*", "COUNT(*)");
		sql.append(" LIMIT ?,?");
		if(StaticValue.PRINT_SQL)
			System.out.println(countSQL);
		Object countObj=dao.QueryOnly(countSQL, VeasionUtil.getObjectByList(values));
		if(countObj!=null){
			pm.setCount(Integer.parseInt(String.valueOf(countObj)));
		}
		int indexPage=pm.getIndexPage();
		int pageCount=pm.getPageCount();
		values.add((indexPage-1)*pageCount);
		values.add((indexPage-1)*pageCount+pageCount);
		if(StaticValue.PRINT_SQL)
			System.out.println(sql);
		return dao.Query(sql.toString(), VeasionUtil.getObjectByList(values));
	}
	
}
