package veasion.handle;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import veasion.constant.Constant;
import veasion.util.ConfigUtil;
import veasion.util.SQLUtil;

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
		if(Constant.ON_LINE!=null)
			Constant.ON_LINE++;
		else
			Constant.ON_LINE=1;
		if(Constant.PRINT_ON_LINE)
			System.out.println("在线人数："+Constant.ON_LINE+"\n"+SQLUtil.getDate("yyyy-MM-dd HH:mm:ss"));
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		//会话结束
		if(Constant.ON_LINE!=null&&Constant.ON_LINE>0)
			Constant.ON_LINE--;
		if(Constant.PRINT_ON_LINE)
			System.out.println("在线人数："+Constant.ON_LINE+"\n"+SQLUtil.getDate("yyyy-MM-dd HH:mm:ss"));
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("停用~\n");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println(ConfigUtil.getProperty("Hello","欢迎使用！--Veasion"));
		System.out.println(SQLUtil.getDate("yyyy-MM-dd HH:mm:ss"));
	}
	
}
