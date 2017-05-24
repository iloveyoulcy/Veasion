package veasion.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

public class IndexVeasion {
	
	HttpServletRequest req;
	HttpServletResponse resp;
	JSONObject json;
	
	/**桌面主页*/
	public String index(){
		return "page/win7.jsp";
	}
	
}
