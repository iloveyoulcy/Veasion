package veasion.control.admin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import veasion.bean.BeanConstant;
import veasion.bean.DesktopCloumn;
import veasion.bean.DesktopStyle;
import veasion.dao.JdbcDao;
import veasion.dao.JoinSql;
import veasion.dao.Where;
import veasion.service.BeanService;
import veasion.service.impl.MysqlServieImpl;
import veasion.util.FileUtil;
import veasion.util.PageModel;
import veasion.util.SQLUtil;
import veasion.util.TextUtil;

/**
 * desktop管理
 * 
 * @author zhuowei.luo
 * @date 2017/5/21
 */
public class DesktopVeasion {
	
	HttpServletRequest req;
	HttpServletResponse resp;
	JSONObject json;
	
	BeanService service=new MysqlServieImpl(null);
	
	/**ICON管理*/
	public String icon(){
		return "page/desktop/iconList.jsp";
	}
	
	/**ICON分页查询*/
	public Map<String,Object> iconSearch() {
		//切换表
		service.useTable(DesktopCloumn.tableName);
		//System.out.println(json);
		Integer indexPage = json.getInt("page");
		Integer pageCount = json.getInt("pagesize");
		String title = json.optString(DesktopCloumn.title, null);
		Map<String, Object> map = new HashMap<String, Object>();
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
	
	/**Go ICON增加、修改页面*/
	public String goIconModify(){
		Object id=json.get(DesktopCloumn.id);
		if(id!=null && SQLUtil.valueOfInteger(id)!=null){
			service.useTable(DesktopCloumn.tableName);
			req.setAttribute(BeanConstant.object, service.QueryOnly(DesktopCloumn.id, id));
		}
		req.setAttribute("showTypes", DesktopCloumn.showTypes);
		List<String> icons=new ArrayList<>();
		//读取icon图标名称
		try {
			String path=req.getServletContext().getRealPath("page/images");
			//String imgPath=req.getContextPath()+"/page/images/";
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
		service.useTable(DesktopCloumn.tableName);
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
			service.useTable(DesktopCloumn.tableName);
			count=service.Delete(DesktopCloumn.id, id);
		}
		if(count>0)
			return "page/success.jsp";
		else
			return "page/failure.jsp";
	}
	
	/**Style管理*/
	public String style(){
		return "page/desktop/styleList.jsp";
	}
	
	/**Style分页查询*/
	public Map<String, Object> styleSearch() {
		//切换表
		service.useTable(DesktopStyle.tableName);
		System.out.println(json);
		Integer indexPage = json.getInt("page");
		Integer pageCount = json.getInt("pagesize");
		String name = json.optString(DesktopStyle.name, null);
		Map<String, Object> map = new HashMap<String, Object>();
		PageModel pm = new PageModel(indexPage, pageCount);
		List<Where> wheres = new ArrayList<>();
		if (name != null && !"".equals(name.trim())) {
			wheres.add(new Where(DesktopStyle.name, JoinSql.like, name));
		}
		List<Map<String, Object>> result = service.Query(wheres, pm);
		result = SQLUtil.filterListMap(result, null, new String[] { DesktopStyle.createDate }, DesktopStyle.id,
				DesktopStyle.author, DesktopStyle.name, DesktopStyle.bgimg, DesktopStyle.cloumnHeight,
				DesktopStyle.cloumnWidth, DesktopStyle.cloumnIds, DesktopStyle.createDate,DesktopStyle.status);
		map.put("Rows", result);
		map.put("Total", pm.getCount());
		return map;
	}
	
	/**Go Style增加、修改页面*/
	public String goStyleModify(){
		Object id=json.get(DesktopStyle.id);
		if(id!=null && SQLUtil.valueOfInteger(id)!=null){
			service.useTable(DesktopStyle.tableName);
			req.setAttribute(BeanConstant.object, service.QueryOnly(DesktopStyle.id, id));
		}
		List<String> bgimgs=new ArrayList<>();
		//读取Style背景图标名称
		try {
			String path=req.getServletContext().getRealPath("page/images");
			//String imgPath=req.getContextPath()+"/page/images/";
			File fd=new File(path);
			if(fd.isDirectory()){
				for (File f : fd.listFiles()) {
					if(f.getName().startsWith("bgimg_"))
						bgimgs.add(f.getName());
				}
			}
		} catch (Exception e) {e.printStackTrace();}
		service.useTable(DesktopCloumn.tableName);
		List<Map<String, Object>> icons=service.Query(null);
		req.setAttribute(DesktopStyle.cloumnIds, icons);
		req.setAttribute("bgimgs", bgimgs);
		return "page/desktop/styleModify.jsp";
	}
	
	/**Style增加、修改*/
	public String styleUpdate() throws IOException{
		Object idObj=json.get(DesktopStyle.id);
		service.useTable(DesktopStyle.tableName);
		JSONObject data=new JSONObject();
		Integer id=null;
		int count=0;
		SQLUtil.fillJsonObject(json, data, DesktopStyle.name,DesktopStyle.author,DesktopStyle.bgimg,DesktopStyle.cloumnHeight,DesktopStyle.cloumnWidth);
		String []cloumnIds=req.getParameterValues(DesktopStyle.cloumnIds);
		data.put(DesktopStyle.cloumnIds, TextUtil.joinObjectArr(cloumnIds, ","));
		System.out.println(data);
		if(idObj!=null && (id=SQLUtil.valueOfInteger(idObj))!=null){
			JSONObject where=new JSONObject();
			where.put(DesktopStyle.id, id);
			//修改
			count=service.Update(data, where);
		}else{
			data.put(DesktopStyle.createDate, SQLUtil.getDate());
			//新增
			count=service.Add(data);
		}
		if(count>0)
			return "page/success.jsp";
		else
			return "page/failure.jsp";
	}
	
	/**Style删除*/
	public String styleDelete(){
		Object obj=json.get(DesktopStyle.id);
		Integer id=null;
		int count=0;
		if(obj!=null && (id=SQLUtil.valueOfInteger(obj))!=null){
			service.useTable(DesktopStyle.tableName);
			count=service.Delete(DesktopStyle.id, id);
		}
		if(count>0)
			return "page/success.jsp";
		else
			return "page/failure.jsp";
	}
	
	/**改变Style状态*/
	public int styleSwitchStatus() {
		int id = json.optInt(DesktopStyle.id, -1);
		if (id <= 0)
			return 0;
		StringBuilder sql = new StringBuilder();
		sql.append("update ");
		sql.append(DesktopStyle.tableName);
		sql.append(" set ").append(DesktopStyle.status);
		sql.append(" = case ").append(DesktopStyle.id);
		sql.append(" when ?").append(" then ?");
		sql.append(" else ?").append(" end ");
		JdbcDao dao = new JdbcDao();
		return dao.executeUpdate(sql.toString(),
				new Object[] { id, DesktopStyle.STATUS_USE, DesktopStyle.STATUS_STOP });
	}
	
	/**上传图片*/
	public String upFile() {
		FileUtil fileUtil=new FileUtil();
		try{
			String type=json.getString("type")+"_";
			//最大500kb
			String fileName=fileUtil.upFile(req, 1024*500L, "page/images",type);
			System.out.println("上传成功！\n"+fileName);
			return "page/success.jsp";
		}catch(Exception e){
			e.printStackTrace();
			return "page/failure.jsp";
		}
	}
	
}
