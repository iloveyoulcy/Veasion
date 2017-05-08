package veasion.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

/**
 * 音乐请求处理.
 * @author zhuowei.luo
 */
public class MusicVeasion{
	/**
	 * 请求Request. 
	 */
	public HttpServletRequest request;
	/**
	 * 响应Response. 
	 */
	public HttpServletResponse response;
	/**
	 * 数据JSONObject. 
	 */
	public JSONObject json;
	
	
	public String Veasion(){
		
		request.setAttribute("json",json.toString());
		
		return "index.jsp";
	}
	
}
