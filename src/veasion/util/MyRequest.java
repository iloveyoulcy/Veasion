package veasion.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 处理Get请求编码的Request.
 * @author zhuowei.luo
 * @date 2017/5/7 
 */
public class MyRequest extends HttpServletRequestWrapper{

	public MyRequest(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public String getParameter(String name) {
		String str=super.getParameter(name);
		if(str==null)return null;
		try {
			str=new String(str.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@Override
	public String[] getParameterValues(String name) {
		String str[]= super.getParameterValues(name);
		if(str!=null)
			for (int i = 0; i < str.length; i++) {
				try {
					str[i]=new String(str[i].getBytes("ISO-8859-1"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		return str;
	}
}
