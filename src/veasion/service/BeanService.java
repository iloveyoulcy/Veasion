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
	 * ����
	 * @return ������id
	 */
	public int Add(JSONObject json);
	
	/**�޸�*/
	public int Update(JSONObject setJson,JSONObject whereJson);
	
	/**ɾ��*/
	public int Delete(String column,Object value);
	
	/**��ѯ*/
	public List<Map<String, Object>> Query(List<Where> wheres);
	
	/**��ҳ��ѯ*/
	public List<Map<String, Object>> Query(List<Where> wheres,PageModel pm);
	
	
}
