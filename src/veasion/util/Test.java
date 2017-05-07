package veasion.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import veasion.bean.Music;
import veasion.bean.StaticValue;
import veasion.bean.Where;
import veasion.dao.JoinSql;
import veasion.service.BeanService;
import veasion.service.impl.BeanServieImpl;
import veasion.util.PageModel;

public class Test {

	public static void main(String[] args) {
		StaticValue.PRINT_SQL=true;
		Map<String, Object> map=new HashMap<>();
		map.put(Music.name, "ºÃÄÐÈË");
		map.put(Music.url, "http://www.veasion.cn");
		JSONObject json=JSONObject.fromObject(map);
		BeanService service=new BeanServieImpl(Music.tableName);
		List<Where> list=new ArrayList<>();
		list.add(new Where(Music.click, JoinSql.eq, 0));
		list.add(new Where(Music.source, JoinSql.like, "%Î´%"));
		PageModel pm=new PageModel(1, 10);
		List<Map<String, Object>> listMap=service.Query(list, pm);
		Map<String, Object> mapResult=new HashMap<>();
		mapResult.put("result", listMap);
		System.out.println(JSONObject.fromObject(mapResult).toString());
		System.out.println(pm);
	}
}

