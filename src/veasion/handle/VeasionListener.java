package veasion.handle;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import veasion.bean.StaticValue;
import veasion.util.ConfigUtil;
import veasion.util.VeasionUtil;

/**
 * 监听器
 * @author zhuowei.luo
 * @date 2017/5/7
 */
public class VeasionListener implements HttpSessionListener,ServletContextListener{
	
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		//统计在线人数
		//当一个浏览器访问时Tomcat会创建一个session
		if(StaticValue.ON_LINE!=null)
			StaticValue.ON_LINE++;
		else
			StaticValue.ON_LINE=1;
		if(StaticValue.PRINT_ON_LINE)
			System.out.println("在线人数："+StaticValue.ON_LINE+"\n"+VeasionUtil.getDate("yyyy-MM-dd HH:mm:ss"));
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		//会话结束
		if(StaticValue.ON_LINE!=null&&StaticValue.ON_LINE>0)
			StaticValue.ON_LINE--;
		if(StaticValue.PRINT_ON_LINE)
			System.out.println("在线人数："+StaticValue.ON_LINE+"\n"+VeasionUtil.getDate("yyyy-MM-dd HH:mm:ss"));
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("停用~\n");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println(ConfigUtil.getProperty("Hello","欢迎使用！--Veasion"));
		System.out.println(VeasionUtil.getDate("yyyy-MM-dd HH:mm:ss"));
	}
	
}
