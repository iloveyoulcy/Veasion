package veasion.bean;

import veasion.util.ConfigUtil;

/**
 * 全局变量
 * @author zhuowei.luo
 * @date 2017/5/7 
 */
public class StaticValue {
	
	/**在线人数*/
	public static Integer ON_LINE;
	/**打印SQL*/
	public static boolean PRINT_SQL=false;
	/**打印SQL配置key*/
	private static final String CONFIG_PRINT_SQL="PrintSql";
	/**control包名*/
	public static final String CONTROL_PACKAGE_NAME="veasion.control";
	/**control.Class命名*/
	public static final String CONTROL_CLASS_NAME="Veasion";
	/**重定向规则*/
	public static final String REDIRECT="redirect:";
	
	static{
		try{
			PRINT_SQL=Boolean.parseBoolean(ConfigUtil.getProperty(CONFIG_PRINT_SQL));
		}catch(Exception e){
			e.getMessage();
		}
	}
	
}
