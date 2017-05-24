package veasion.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * Tables配置Util类.
 * @author zhuowei.luo
 * @date 2017/5/9 
 */
public class TablesUtil {
	
	/**Tables文件名*/
	private static String TABLES_NAME="tables.properties";
	
	/**Proerties对象*/
	private static Properties p;
	
	static{
		InputStream input=TablesUtil.class.getClassLoader().getResourceAsStream(TABLES_NAME);
		p=new Properties();
		try {
			p.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**获取创建表SQL*/
	public static Map<String, String> getTablesMap(){
		if(p==null){
			System.err.println(TABLES_NAME+"，加载异常！");
			return null;
		}
		Map<String, String> map=new HashMap<>();
		Set<Entry<Object, Object>> set=p.entrySet();
		for (Entry<Object, Object> entry : set) {
			map.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
		}
		return map;
	}
	
	
	/**
	 * 根据key获取值. 
	 * @param defaultValue 如果没有或报错就返回该默认值.
	 */
	public static String getProperty(String key,String defaultValue){
		String value=null;
		if(p!=null){
			try {
				value=p.getProperty(key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			System.err.println(TABLES_NAME+"，加载异常！");
		}
		return value!=null?value:defaultValue;
	}
	
}
