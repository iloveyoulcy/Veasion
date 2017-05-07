package veasion.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuowei.luo
 * @date 2017/5/7
 */
public class JdbcDao {
	
	private  final String JDBC_URL="jdbc:mysql://localhost:3306/solo?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
	private static final String DRIVER="com.mysql.jdbc.Driver";
	private  final String USER_NAME="root";
	private  final String USER_PWD="root";
	
	
	static{
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**获取Connection连接*/
	public Connection getConnection(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(JDBC_URL,USER_NAME,USER_PWD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**关闭所有连接*/
	public void closeAll(Connection conn, Statement ps, ResultSet rs){
		try {
			if(rs!=null) rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
		try {
			if(ps!=null) ps.close();
		} catch (SQLException e) { e.printStackTrace(); }
		try {
			if(conn!=null) conn.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}

	/**执行增删改*/
	public int executeUpdate(String sql, Object[] obj){
		int count = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			if(obj!=null && obj.length>0){
				for (int i = 0; i < obj.length; i++) {
					ps.setObject(i+1, obj[i]);
				}
			}
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			closeAll(conn, ps, null);
		}
		return count;
	}
	
	/**执行Insert，返回自增长id*/
	public int executeInsert(String sql, Object[] obj){
		int id = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			if(obj!=null && obj.length>0){
				for (int i = 0; i < obj.length; i++) {
					ps.setObject(i+1, obj[i]);
				}
			}
			int count = ps.executeUpdate();
			if(count<1){
				id=-1;
			}else{
				result=ps.getGeneratedKeys();
				if(result!=null&&result.next()){
					id=result.getInt(1);
				}else{
					id=-1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			closeAll(conn, ps, result);
		}
		return id;
	}
	
	/**通过sql查询,返回Map,Key是字段名，Value是值*/
	public List<Map<String, Object>> Query(final String sql,Object []obj){
		List<Map<String, Object>> list=new ArrayList<>();
		Map<String,Object> map=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn=getConnection();
			ps=conn.prepareStatement(sql);
			if(obj!=null && obj.length>0){
				for (int i = 0; i < obj.length; i++) {
					ps.setObject(i+1, obj[i]);
				}
			}
			rs=ps.executeQuery();
			ResultSetMetaData rsmd=null;
			while(rs.next()){
				map=new HashMap<>();
				rsmd=rs.getMetaData();
				int count=rsmd.getColumnCount();
				for (int i = 1; i <= count; i++) {
					map.put(rsmd.getColumnName(i),rs.getObject(i));
				}
				list.add(map);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(conn, ps, rs);
		}
		return list;
	}
	
	/**获取单个值**/
	public Object QueryOnly(final String sql,Object []obj){
		Object value=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn=getConnection();
			ps=conn.prepareStatement(sql);
			if(obj!=null && obj.length>0){
				for (int i = 0; i < obj.length; i++) {
					ps.setObject(i+1, obj[i]);
				}
			}
			rs=ps.executeQuery();
			if(rs!=null && rs.next()){
				value=rs.getObject(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(conn, ps, rs);
		}
		return value;
	}
	
	
	public static void main(String[] args) throws SQLException {
		JdbcDao dao=new JdbcDao();
		List<Map<String, Object>> list=dao.Query("select * from veasion_music limit ?,?",new Object[]{0,1});
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
}
