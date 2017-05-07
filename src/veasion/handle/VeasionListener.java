package veasion.handle;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import veasion.bean.StaticValue;

/**
 * ͳ����������
 * @author zhuowei.luo
 * @date 2017/5/7
 */
public class VeasionListener implements HttpSessionListener{
	
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		//��һ�����������ʱtomcat�ᴴ��һ��session
		if(StaticValue.ON_LINE!=null)
			StaticValue.ON_LINE++;
		else
			StaticValue.ON_LINE=1;
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		//�Ự����
		if(StaticValue.ON_LINE!=null&&StaticValue.ON_LINE>0)
			StaticValue.ON_LINE--;
	}
	
}
