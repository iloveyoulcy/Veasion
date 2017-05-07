package veasion.control;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import veasion.bean.StaticValue;

/**
 * “Ù¿÷«Î«Û¥¶¿Ì.
 * @author zhuowei.luo
 */
public class MusicVeasion {
	public HttpServletRequest request;
	public HttpServletResponse response;
	
	public String Veasion(){
		request.setAttribute("count", StaticValue.ON_LINE);
		return "index.jsp";
	}
}
