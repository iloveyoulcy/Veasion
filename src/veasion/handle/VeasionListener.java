package veasion.handle;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import veasion.bean.StaticValue;

/**
 * Session会话监听
 * @author zhuowei.luo
 * @date 2017/5/7
 */
public class VeasionListener implements HttpSessionListener{
	
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		//统计在线人数
		//当一个浏览器访问时Tomcat会创建一个session
		if(StaticValue.ON_LINE!=null)
			StaticValue.ON_LINE++;
		else
			StaticValue.ON_LINE=1;
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		//会话结束
		if(StaticValue.ON_LINE!=null&&StaticValue.ON_LINE>0)
			StaticValue.ON_LINE--;
	}
	
}
