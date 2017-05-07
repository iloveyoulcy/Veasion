package veasion.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import veasion.bean.Where;
import veasion.util.PageModel;

/**
 * BeanService
 * @author zhuowei.luo
 * @date 2017/5/7 
 */
public interface BeanService {
	
	/**
	 * 增加
	 * @return 自增长id
	 */
	public int Add(JSONObject json);
	
	/**修改*/
	public int Update(JSONObject setJson,JSONObject whereJson);
	
	/**删除*/
	public int Delete(String column,Object value);
	
	/**查询*/
	public List<Map<String, Object>> Query(List<Where> wheres);
	
	/**分页查询*/
	public List<Map<String, Object>> Query(List<Where> wheres,PageModel pm);
	
	
}
