package veasion.control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import veasion.bean.BeanConstant;
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
		return "page/desktop/iconList.jsp";
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
		System.out.println(pm.getCount());
		return map;
	}
	
	/**Go ICON增加、修改页面*/
	public String goIconModify(){
		Object id=json.get(DesktopCloumn.id);
		if(id!=null && SQLUtil.valueOfInteger(id)!=null){
			service=new MysqlServieImpl(DesktopCloumn.tableName);
			req.setAttribute(BeanConstant.OBJECT, service.QueryById(DesktopCloumn.id, id));
		}
		req.setAttribute("showTypes", DesktopCloumn.showTypes);
		List<String> icons=new ArrayList<>();
		//读取icon图标名称
		try {
			String path=req.getServletContext().getRealPath("page/images");
			String imgPath=req.getContextPath()+"/page/images/";
			File fd=new File(path);
			if(fd.isDirectory()){
				for (File f : fd.listFiles()) {
					if(f.getName().startsWith("icon_"))
						icons.add(f.getName());
				}
			}
		} catch (Exception e) {e.printStackTrace();}
		req.setAttribute("icons", icons);
		return "page/desktop/iconModify.jsp";
	}
	
	/**ICON增加、修改*/
	public String iconUpdate() throws IOException{
		Object idObj=json.get(DesktopCloumn.id);
		service=new MysqlServieImpl(DesktopCloumn.tableName);
		JSONObject data=new JSONObject();
		Integer id=null;
		int count=0;
		SQLUtil.fillJsonObject(json, data, DesktopCloumn.title,DesktopCloumn.url,DesktopCloumn.width,DesktopCloumn.height,DesktopCloumn.icon,DesktopCloumn.showType);
		if(idObj!=null && (id=SQLUtil.valueOfInteger(idObj))!=null){
			JSONObject where=new JSONObject();
			where.put(DesktopCloumn.id, id);
			//修改
			count=service.Update(data, where);
		}else{
			data.put(DesktopCloumn.status, 1);
			data.put(DesktopCloumn.createDate, SQLUtil.getDate());
			//新增
			count=service.Add(data);
		}
		if(count>0)
			return "page/success.jsp";
		else
			return "page/failure.jsp";
	}
	
	/**ICON删除*/
	public String iconDelete(){
		Object obj=json.get(DesktopCloumn.id);
		Integer id=null;
		int count=0;
		if(obj!=null && (id=SQLUtil.valueOfInteger(obj))!=null){
			service=new MysqlServieImpl(DesktopCloumn.tableName);
			count=service.Delete(DesktopCloumn.id, id);
		}
		if(count>0)
			return "page/success.jsp";
		else
			return "page/failure.jsp";
	}
	
}
