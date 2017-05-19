package veasion.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import veasion.constant.Constant;

public class AdminVeasion {
	
	public final static String ADMIN_NAME="admin";
	public final static String ADMIN_VEA="Veasion";
	
	HttpServletRequest req;
	HttpServletResponse resp;
	JSONObject json;
	
	/**验证*/
	public int validation(){
		//veasion=A.java
		String codes=json.getString("codes");
		if(codes!=null
				&& codes.length()>=Constant.ADMIN_CODES.length()
				&& codes.indexOf(Constant.ADMIN_CODES)!=-1){
			req.getSession().setAttribute(ADMIN_NAME,ADMIN_VEA);
			return 1;
		}else{
			return 0;
		}
	}
	
	/**管理页面*/
	public String index(){
		System.out.println("登录管理页面成功！");
		return "admin.jsp";
	}
	
}
