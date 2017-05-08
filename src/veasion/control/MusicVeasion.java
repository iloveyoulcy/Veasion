package veasion.control;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import veasion.bean.StaticValue;

/**
 * 音乐请求处理.
 * @author zhuowei.luo
 */
public class MusicVeasion {
	/**
	 * 请求Request. 
	 */
	public HttpServletRequest request;
	/**
	 * 响应Response. 
	 */
	public HttpServletResponse response;
	
	
	public String Veasion(){
		request.setAttribute("count", StaticValue.ON_LINE);
		return "index.jsp";
	}
}
