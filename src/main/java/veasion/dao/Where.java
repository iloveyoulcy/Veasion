package veasion.dao;

import java.util.ArrayList;
import java.util.List;

import veasion.util.SQLUtil;
import veasion.util.TextUtil;

/**
 * 条件
 * @author zhuowei.luo
 * @date 2017/5/7 
 */
public class Where {
	
	/**字段名*/
	private String column;
	/**连接符*/
	private JoinSql joinSql;
	/**值*/
	private Object value;
	
	/**
	 * 条件
	 * @param column 条件字段
	 * @param joinSql 连接符
	 * @param value 条件值
	 */
	public Where(String column,JoinSql joinSql,Object value){
		if(joinSql==JoinSql.like){
			if(value!=null
					&& !String.valueOf(value).trim().startsWith("%")){
				value="%"+value+"%";
			}
		}
		
		this.column=column;
		this.joinSql=joinSql;
		this.value=value;
	}
	
	/**
	 * 获取占位符的SQL，如 xxx=?
	 * @param as 命名xx.xxx=?
	 */
	public StringBuilder getSQL(String as){
		StringBuilder sql=new StringBuilder();
		if(as!=null){
			sql.append(as).append(".");
		}
		sql.append(column);
		switch (joinSql) {
			case eq:
				sql.append(" = ?");
				break;
			case notEq:
				sql.append(" <> ?");
				break;
			case greater:
				sql.append(" > ?");
				break;
			case greaterEq:
				sql.append(" >= ?");
				break;
			case less:
				sql.append(" < ?");
				break;
			case lessEq:
				sql.append(" <= ?");
			case like:
				sql.append(" like ?");
				break;
			case in:
				if(value instanceof Object[]){
					sql.append(" in (").append(SQLUtil.getSymbol("?", ",", ((Object[])value).length)).append(")");
				}else{
					sql.append(" in (?)");
				}
				break;
			default:
				sql.append(" = ?");
				break;
		}
		return sql;
	}
	
	/**
	 * 获取条件值（占位符的值）
	 * 
	 * @return Object或List
	 */
	public Object getValue() {
		if(joinSql==JoinSql.in && value instanceof Object[]){
			List<Object> list=new ArrayList<>();
			Object []objs=(Object[])value;
			for (Object obj : objs) {
				list.add(obj);
			}
			return list;
		}else{
			return value;
		}
	}
	
	/**连接符*/
	public JoinSql getJoinSql() {
		return joinSql;
	}
	
}
