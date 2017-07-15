package veasion.constant;

import javax.swing.filechooser.FileSystemView;
import veasion.util.ConfigUtil;

/**
 * 全局变量
 * @author zhuowei.luo
 * @date 2017/5/7 
 */
public class Constant {
	
	/**在线人数*/
	public static Integer ON_LINE;
	
	/**打印SQL*/
	public static boolean PRINT_SQL=false;
	
	/**打印在线人数*/
	public static boolean PRINT_ON_LINE=false;
	
	/**打印SQL配置key*/
	private static final String SQL_PRINT="PrintSql";
	
	/**打印在线人数配置key*/
	public static final String SYSTEM_PRINT_ON_LINE="PrintOnLine";
	
	/**最大文件上传字节 2G*/
	public static final long UP_FILE_MAX= 2*1024*1024*1024L;
	
	/**JdbcUrl配置key*/
	public static final String SQL_JDBC_URL="JdbcUrl";
	
	/**Driver配置key*/
	public static final String SQL_DRIVER="Driver";
	
	/**UserName配置key*/
	public static final String SQL_USER_NAME="UserName";
	
	/**UserPwd配置key*/
	public static final String SQL_USER_PWD="UserPwd";
	
	/**control包名*/
	public static final String CONTROL_PACKAGE_NAME="veasion.control";
	
	/**control.Class命名*/
	public static final String CONTROL_CLASS_NAME="Veasion";
	
	/**重定向规则*/
	public static final String REDIRECT="redirect:";
	
	/**admin codes*/
	public static final String ADMIN_CODES="86,69,65,83,73,79,78,187,65,190,74,65,86,65";
	
	/**作者*/
	public static final String AUTHOR="veasion";
	
	/**获取桌面路径*/
	public static String HOME_PATH = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
	
	static{
		try{
			PRINT_SQL="TRUE".equals(ConfigUtil.getProperty(SQL_PRINT,"FALSE").toUpperCase());
			PRINT_ON_LINE="TRUE".equals(ConfigUtil.getProperty(SYSTEM_PRINT_ON_LINE,"FALSE").toUpperCase());
		}catch(Exception e){
			e.getMessage();
		}
	}
}
