package veasion.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import veasion.bean.DesktopCloumn;
import veasion.constant.Constant;
import veasion.dao.JoinSql;
import veasion.dao.Where;
import veasion.service.BeanService;
import veasion.service.impl.MysqlServieImpl;
import veasion.util.PageModel;
import veasion.util.SQLUtil;

public class AdminVeasion {
	
	public final static String ADMIN_NAME="admin";
	public final static String ADMIN_VEA="Veasion";
	
	HttpServletRequest req;
	HttpServletResponse resp;
	JSONObject json;
	BeanService service;
	
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
	
	/**ICON管理*/
	public String icon(){
		return "page/icon.jsp";
	}
	
	/**ICON分页查询*/
	public Map iconSearch() {
		Integer indexPage = json.getInt(PageModel.INDEX_PAGE);
		Integer pageCount = json.getInt(PageModel.PAGE_COUNT);
		String title = json.getString(DesktopCloumn.title);

		Map<String, Object> map = new HashMap();
		service = new MysqlServieImpl(DesktopCloumn.tableName);
		PageModel pm = new PageModel(indexPage, pageCount);
		List<Where> wheres = new ArrayList<>();
		if (title != null && !"".equals(title.trim())) {
			wheres.add(new Where(DesktopCloumn.title, JoinSql.like, title));
		}
		List<Map<String, Object>> result = service.Query(wheres, pm);
		result = SQLUtil.filterListMap(result, null, new String[] { DesktopCloumn.createDate }, DesktopCloumn.id,
				DesktopCloumn.icon, DesktopCloumn.title, DesktopCloumn.url, DesktopCloumn.width, DesktopCloumn.height,
				DesktopCloumn.showType, DesktopCloumn.status, DesktopCloumn.createDate);
		map.put("Rows", result);
		map.put("Total", pm.getCount());
		return map;
	}
	
}
