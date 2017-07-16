package veasion.handle;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import veasion.constant.Constant;
import veasion.dao.AutoCreateDB;
import veasion.util.ConfigUtil;
import veasion.util.SQLUtil;

/**
 * 监听器
 * @author zhuowei.luo
 * @date 2017/5/7
 */
public class VeasionListener implements HttpSessionListener,ServletContextListener{
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
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
	public void sessionDestroyed(HttpSessionEvent event) {
		//会话结束
		if(Constant.ON_LINE!=null&&Constant.ON_LINE>0)
			Constant.ON_LINE--;
		if(Constant.PRINT_ON_LINE)
			System.out.println("在线人数："+Constant.ON_LINE+"\n"+SQLUtil.getDate("yyyy-MM-dd HH:mm:ss"));
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("停用~\n");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println(ConfigUtil.getProperty("Hello","欢迎使用！--Veasion"));
		System.out.println(SQLUtil.getDate("yyyy-MM-dd HH:mm:ss"));
		try{
			//检查数据库和表，没有则自动创建
			if(ConfigUtil.getPropertyBoolean(Constant.AutoCreateDB, false))
				AutoCreateDB.autoCreateDB();
		}catch(Exception e){e.printStackTrace();}
	}
	
}
