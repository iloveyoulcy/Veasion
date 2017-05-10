package veasion.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Config配置Util类.
 * @author zhuowei.luo
 * @date 2017/5/9 
 */
public class ConfigUtil {
	
	/**Config文件名*/
	private static String CONFIG_NAME="config.properties";
	
	/**Proerties对象*/
	private static Properties p;
	
	static{
		InputStream input=ConfigUtil.class.getClassLoader().getResourceAsStream(CONFIG_NAME);
		p=new Properties();
		try {
			p.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据key获取值. 
	 */
	public static String getProperty(String key){
		if(p!=null){
			try {
				return p.getProperty(key);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}else{
			System.err.println(CONFIG_NAME+"，加载异常！");
			return null;
		}
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
			System.err.println(CONFIG_NAME+"，加载异常！");
		}
		return value!=null?value:defaultValue;
	}
	
}
