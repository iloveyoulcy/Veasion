package veasion.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import veasion.bean.DesktopCloumn;
import veasion.bean.DesktopStyle;
import veasion.dao.JoinSql;
import veasion.dao.Where;
import veasion.service.BeanService;
import veasion.service.impl.MysqlServieImpl;
import veasion.util.SQLUtil;

public class IndexVeasion {
	
	HttpServletRequest req;
	HttpServletResponse resp;
	JSONObject json;
	
	BeanService service=new MysqlServieImpl(null);
	
	/**桌面主页*/
	public String index(){
		return "page/win7.jsp";
	}
	
	/**请求桌面数据*/
	public Map<String, Object> desktopData(){
		Map<String,Object> data=new HashMap<>();
		service.useTable(DesktopStyle.tableName);
		Map<String, Object> styleMap=service.QueryOnly(DesktopStyle.status, DesktopStyle.STATUS_USE);
		if(styleMap!=null && !styleMap.isEmpty()){
			data.put("style", styleMap);
			Object get=styleMap.get(DesktopStyle.cloumnIds);
			if(get!=null){
				String []ids=String.valueOf(get).split(",");
				service.useTable(DesktopCloumn.tableName);
				List<Where> wheres=new ArrayList<>();
				wheres.add(new Where(DesktopCloumn.id, JoinSql.in, String.valueOf(get).split(",")));
				List<Map<String, Object>> icons=service.Query(wheres);
				for (Map<String, Object> icon : icons) {
					int showType=SQLUtil.valueOfInteger(icon.get(DesktopCloumn.showType));
					//"常规","最大化","最小化","打开新窗体","不准最大化"
					switch (showType) {
						case 1:icon.put("openMax", true);break;
						case 2:icon.put("openMin", true);break;
						case 3:icon.put("open", true);break;
						case 4:icon.put("showMax", false);break;
						default:break;
					}
				}
				data.put("icons", icons);
			}
		}
		return data;
	}
	
}
