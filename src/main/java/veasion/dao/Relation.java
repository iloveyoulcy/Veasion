package veasion.dao;

import java.util.Map;

/**
 * 表关系对应
 * 
 * @author zhuowei.luo
 * @date 2017/5/22
 */
public class Relation {
	
	private String tableName1;
	private String key1;
	private String tableName2;
	private String key2;
	
	/**表关系对应*/
	public Relation(String tableName1,String key1,String tableName2,String key2){
		this.tableName1=tableName1;
		this.key1=key1;
		this.tableName2=tableName2;
		this.key2=key2;
	}
	
	/**表名和命名*/
	public StringBuilder getSQL(Map<String, String> tableAs){
		StringBuilder sql=new StringBuilder(" ");
		sql.append(tableAs.get(tableName1));
		sql.append(".").append(key1);
		sql.append("=");
		sql.append(tableAs.get(tableName2));
		sql.append(".").append(key2).append(" ");
		return sql;
	}
	
	public String getKey1() {
		return key1;
	}
	public String getKey2() {
		return key2;
	}
	public String getTableName1() {
		return tableName1;
	}
	public String getTableName2() {
		return tableName2;
	}
	
}
