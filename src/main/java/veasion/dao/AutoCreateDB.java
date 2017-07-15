package veasion.dao;

import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

import veasion.constant.Constant;
import veasion.util.ConfigUtil;
import veasion.util.SQLUtil;
import veasion.util.TablesUtil;

/**
 * 自动创建数据库和表
 * 
 * @author zhuowei.luo
 * @date 2017/5/20
 */
public class AutoCreateDB {

	private final static Logger LOGGER = Logger.getLogger(AutoCreateDB.class);

	/** 默认数据库名 */
	private static String DATABASE = "veasion";

	/** 
	 * 开始执行自动创建数据库和表 
	 */
	public static void autoCreateDB() {
		LOGGER.info("正在检查数据库和表...");
		
		DATABASE=getDataBase();
		
		// 切换到mysql数据库
		JdbcDao dao = new JdbcDao("mysql");
		
		Object obj = dao.QueryOnly("SELECT COUNT(*) FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME=?",
				new Object[] { DATABASE });
		
		Integer count = SQLUtil.valueOfInteger(obj);
		
		// 判断数据库是否存在，不存在则创建
		if (count == null || count < 1) {
			
			//创建数据库 utf-8编码
			count = dao.executeCreate("CREATE DATABASE `"+DATABASE+"` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci");
			
			if (count != null && count > 0) {
				LOGGER.info("创建数据库：" + DATABASE + "，成功！");
			} else {
				LOGGER.error("创建数据库：" + DATABASE + "，失败！");
			}
		}
		// 切换到原数据库
		dao = new JdbcDao();
		
		Map<String, String> map = getCreateTableSQL();
		
		if (map != null && !map.isEmpty()) {
			// 遍历创建表
			for (Entry<String, String> entry : getCreateTableSQL().entrySet()) {
				obj = dao.QueryOnly(
						"SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA=? and TABLE_NAME=?",
						new Object[] { DATABASE, entry.getKey() });
				
				count = SQLUtil.valueOfInteger(obj);
				
				// 判断表是否存在，不存在则创建
				if (count == null || count < 1) {
					
					String sql = entry.getValue().trim();
					int index = sql.lastIndexOf(";");
					if (index != -1 && index >= sql.length() - 1) {
						sql = sql.substring(0, sql.length() - 1);
					}
					
					// 创建表
					count = dao.executeCreate(entry.getValue());
					
					if (count != null && count > -1) {
						LOGGER.info("创建表：" + entry.getKey() + "，成功！");
					} else {
						LOGGER.error("创建表：" + entry.getKey() + "，失败！");
						System.err.println(entry.getValue());
					}
				}
			}
		}
		LOGGER.info("检测完毕！");
	}

	/** 建表的SQL语句 */
	private static Map<String, String> getCreateTableSQL() {
		return TablesUtil.getTablesMap();
	}
	
	private static String getDataBase(){
		String database=ConfigUtil.getProperty(Constant.SQL_JDBC_URL, DATABASE);
		//jdbc:mysql://localhost:3306/veasion?autoReconnect=
		int start=database.lastIndexOf("/");
		int end=database.indexOf("?");
		if(end!=-1){
			return database.substring(start+1, end);
		}else{
			return database.substring(start+1);
		}
	}
}
