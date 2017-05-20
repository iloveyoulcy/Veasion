package veasion.dao;

import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
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
	private final static String DATABASE = "veasion";

	/** 
	 * 开始执行自动创建数据库和表 
	 */
	public static void autoCreateDB() {
		LOGGER.info("正在检查数据库和表...");
		// 切换到mysql数据库
		JdbcDao dao = new JdbcDao("mysql");
		
		Object obj = dao.QueryOnly("SELECT COUNT(*) FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME=?",
				new Object[] { DATABASE });
		
		Integer count = SQLUtil.valueOfInteger(obj);
		
		// 判断数据库是否存在，不存在则创建
		if (count == null || count < 1) {
			
			count = dao.executeCreate("create database " + DATABASE);
			
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
	
}
