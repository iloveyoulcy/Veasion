package veasion.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import veasion.util.ReturnJson;
import veasion.util.ReturnJson.ReturnCode;

/**
 * 测试Control
 * @author zhuowei.luo
 * @date 2017/5/8 
 */
public class TestVeasion {
	
	/***
	 *  这里介绍本框架使用流程！
	 *  首先这是一个control，类的命名方式xxx+Veasion
	 *  请求url，/项目名/test/method.vea
	 *  test：类名除Veasion之外的字符串，小写
	 *  method：将被调用的方法名
	 *  
	 *  method定义：
	 *     返回String，表示跳转的url或页面(默认转发，重定向请加redirect:).
	 *     返回除String之外的Object，返回该对象的json.
	 *  
	 *  该类中可以添加属性HttpServletRequest获取请求对象.
	 *  该类中可以添加属性HttpServletResponse获取响应对象.
	 *  该类中可以添加属性JSONObject获取分装的数据.
	 *  
	 *  分析代码可发现本类就是一个常见的control.
	 */
	
	
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
	
	/**
	 * 测试方法1
	 * @throws IOException 
	 */
	public String Method1() throws IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter write=response.getWriter();
		write.append("<script>");
		write.append("alert('Hello Word!');");
		write.append("</script>");
		write.close();
		return null;
	}
	
	/**
	 * 测试方法2
	 */
	public String Method2(){
		request.setAttribute("temp", "测试！");
		System.out.println("测试2！！！");
		return "index.jsp";
	}
	
	/**
	 * 测试方法3
	 */
	public JSONObject Method3(){
		json=new JSONObject();
		json.put("method", "测试：Method3");
		return json;
	}
	
	/**
	 * 测试方法4 
	 */
	public Object Method4(){
		ReturnJson rj=new ReturnJson();
		rj.setObject(new Date());
		rj.setMessage("测试！！！");
		rj.setCode(ReturnCode.SUCCESS.getCode());
		return rj;
	}
	
	
}
