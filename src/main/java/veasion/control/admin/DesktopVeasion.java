package veasion.control.admin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aliyun.oss.OSSClient;

import net.sf.json.JSONObject;
import veasion.bean.BeanConstant;
import veasion.bean.DesktopCloumn;
import veasion.bean.DesktopStyle;
import veasion.bean.VeasionUrl;
import veasion.dao.JdbcDao;
import veasion.dao.JoinSql;
import veasion.dao.Where;
import veasion.service.BeanService;
import veasion.service.impl.MysqlServieImpl;
import veasion.util.PageModel;
import veasion.util.SQLUtil;
import veasion.util.TextUtil;
import veasion.util.oss.OssUploadFile;
import veasion.util.oss.OssUtil;

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
	
	final BeanService service=new MysqlServieImpl(null);
	
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
		VeasionUrl.fillUrlForMap(result, DesktopCloumn.icon, DesktopCloumn.url);
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
			Map<String, Object> map=service.QueryOnly(DesktopCloumn.id, id);
			VeasionUrl.fillUrlForMap(map, DesktopCloumn.icon, DesktopCloumn.url);
			req.setAttribute(BeanConstant.object, map);
		}
		req.setAttribute("showTypes", DesktopCloumn.showTypes);
		service.useTable(VeasionUrl.tableName);
		List<Where> wheres=new ArrayList<>();
		// 加载所有图标
		wheres.add(new Where(VeasionUrl.type, JoinSql.eq, VeasionUrl.TYPE_ICON));
		List<Map<String, Object>> icons=service.Query(wheres);
		wheres.clear();
		// 加载所有Url
		wheres.add(new Where(VeasionUrl.type, JoinSql.eq, VeasionUrl.TYPE_URL));
		List<Map<String, Object>> urls=service.Query(wheres);
		req.setAttribute("icons", icons);
		req.setAttribute("urls", urls);
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
			req.setAttribute("tabid", "updateIcon");
		}else{
			data.put(DesktopCloumn.status, 1);
			data.put(DesktopCloumn.createDate, SQLUtil.getDate());
			//新增
			count=service.Add(data);
			req.setAttribute("tabid", "addIcon");
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
		VeasionUrl.fillUrlForMap(result, DesktopStyle.bgimg);
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
			Map<String, Object> map=service.QueryOnly(DesktopStyle.id, id);
			VeasionUrl.fillUrlForMap(map, DesktopStyle.bgimg);
			req.setAttribute(BeanConstant.object, map);
		}
		service.useTable(VeasionUrl.tableName);
		List<Where> wheres=new ArrayList<>();
		wheres.add(new Where(VeasionUrl.type, JoinSql.eq, VeasionUrl.TYPE_STYLE));
		List<Map<String, Object>> bgimgs=service.Query(wheres);
		service.useTable(DesktopCloumn.tableName);
		List<Map<String, Object>> icons=service.Query(null);
		VeasionUrl.fillUrlForMap(icons, DesktopCloumn.icon, DesktopCloumn.url);
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
			req.setAttribute("tabid", "updateStyle");
		}else{
			data.put(DesktopStyle.createDate, SQLUtil.getDate());
			//新增
			count=service.Add(data);
			req.setAttribute("tabid", "addStyle");
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
	
	/**Url管理*/
	public String url(){
		return "page/desktop/urlList.jsp";
	}
	
	/**Url分页查询*/
	public Map<String, Object> urlSearch() {
		//切换表
		service.useTable(VeasionUrl.tableName);
		System.out.println(json);
		Integer indexPage = json.getInt("page");
		Integer pageCount = json.getInt("pagesize");
		String name = json.optString(VeasionUrl.name, null);
		int type=json.optInt(VeasionUrl.type, 0);
		Map<String, Object> map = new HashMap<String, Object>();
		PageModel pm = new PageModel(indexPage, pageCount);
		List<Where> wheres = new ArrayList<>();
		if (name != null && !"".equals(name.trim())) {
			wheres.add(new Where(VeasionUrl.name, JoinSql.like, name));
		}
		if(type != 0){
			wheres.add(new Where(VeasionUrl.type, JoinSql.eq, type));
		}
		List<Map<String, Object>> result = service.Query(wheres, pm);
		result = SQLUtil.filterListMap(result, null, new String[] { VeasionUrl.createDate }, 
				VeasionUrl.id,VeasionUrl.name, VeasionUrl.url,VeasionUrl.type, VeasionUrl.createDate);
		map.put("Rows", result);
		map.put("Total", pm.getCount());
		return map;
	}
	
	/**Go Url增加、修改页面*/
	public String goUrlModify(){
		Object id=json.get(VeasionUrl.id);
		if(id!=null && SQLUtil.valueOfInteger(id)!=null){
			service.useTable(VeasionUrl.tableName);
			Map<String, Object> map=service.QueryOnly(VeasionUrl.id, id);
			req.setAttribute(BeanConstant.object, map);
		}
		return "page/desktop/urlModify.jsp";
	}
	
	/**url增加、修改*/
	public String urlUpdate() {
		Object idObj=json.get(VeasionUrl.id);
		service.useTable(VeasionUrl.tableName);
		JSONObject data=new JSONObject();
		Integer id=null;
		int count=0;
		SQLUtil.fillJsonObject(json, data, VeasionUrl.name,VeasionUrl.url,VeasionUrl.type);
		System.out.println(data);
		if(idObj!=null && (id=SQLUtil.valueOfInteger(idObj))!=null){
			JSONObject where=new JSONObject();
			where.put(VeasionUrl.id, id);
			//修改
			count=service.Update(data, where);
			req.setAttribute("tabid", "updateUrl");
		}else{
			data.put(VeasionUrl.createDate, SQLUtil.getDate());
			data.put(VeasionUrl.type, VeasionUrl.TYPE_URL);
			//新增
			count=service.Add(data);
			req.setAttribute("tabid", "addUrl");
		}
		if(count>0)
			return "page/success.jsp";
		else
			return "page/failure.jsp";
	}
	
	/**url删除*/
	public String urlDelete(){
		Object obj=json.get(VeasionUrl.id);
		Integer id=null;
		int count=0;
		if(obj!=null && (id=SQLUtil.valueOfInteger(obj))!=null){
			service.useTable(VeasionUrl.tableName);
			count=service.Delete(VeasionUrl.id, id);
		}
		if(count>0)
			return "page/success.jsp";
		else
			return "page/failure.jsp";
	}
	
	/**上传图片*/
	public String upFile() {
		try{
			String typeName=json.optString("type", "").trim();
			String name=json.optString("name", "").trim();
			String filePath=json.optString("filePath","").trim();
			int type=0;
			boolean up=true;
			if("bgimg".equals(typeName)){
				type=VeasionUrl.TYPE_STYLE;
			}else if("icon".equals(typeName)){
				type=VeasionUrl.TYPE_ICON;
			}else{
				up=false;
			}
			if(!up) throw new Exception("未知上传！");
			
			File tempFile=new File(json.getString("file"));
			
			String fileName=tempFile.getName();
			if("".equals(name)) name=fileName;
			// 上传文件到Oss
			OSSClient client=OssUtil.getOssClient();
			OssUploadFile uploadFile=new OssUploadFile(tempFile, "images/", 
					UUID.randomUUID()+fileName.substring(fileName.lastIndexOf(".")));
			String url=OssUtil.uploadObject(client, OssUtil.bucketName, uploadFile, null);
			client.shutdown();
			
			service.useTable(VeasionUrl.tableName);
			JSONObject json=new JSONObject();
			json.put(VeasionUrl.name, name);
			json.put(VeasionUrl.type, type);
			json.put(VeasionUrl.url, url);
			json.put(VeasionUrl.createDate, SQLUtil.getDate());
			service.Add(json);
			return "page/success.jsp";
		}catch(Exception e){
			e.printStackTrace();
			return "page/failure.jsp";
		}
	}
	
}
