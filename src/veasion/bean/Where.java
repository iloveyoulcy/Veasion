package veasion.bean;

import veasion.dao.JoinSql;

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
	
	public Where(String column,JoinSql joinSql,Object value){
		this.column=column;
		this.joinSql=joinSql;
		this.value=value;
	}
	
	public StringBuilder getSQL(){
		StringBuilder sql=new StringBuilder();
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
			default:
				sql.append(" = ?");
				break;
		}
		return sql;
	}
	
	public Object getValue() {
		return value;
	}
	
}
