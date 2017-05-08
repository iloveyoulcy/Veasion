package veasion.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import veasion.bean.Where;
import veasion.util.PageModel;

/**
 * 基本数据操作Service
 * @author zhuowei.luo
 * @date 2017/5/7 
 */
public interface BeanService {
	
	/**
	 * 增加
	 * @param json 插入的字段和值
	 * @return 自增长id
	 */
	public int Add(JSONObject json);
	
	/**
	 * 修改
	 * @param setJson 将要被修改的字段和值
	 * @param whereJson 条件的字段和值
	 */
	public int Update(JSONObject setJson,JSONObject whereJson);
	
	/**
	 * 根据id，主键删除
	 * @param column 条件字段名
	 * @param value 条件值
	 */
	public int Delete(String column,Object value);
	
	/**
	 * Count
	 * @param wheres 条件和值
	 * @return null为-1，其他为>=0
	 */
	public int Count(List<Where> wheres);
	
	/**
	 * 根据id，主键查询
	 * @param column 条件字段
	 * @param value 条件值
	 * @return 只返回第一条数据
	 */
	public Map<String,Object> QueryById(String column,Object value);
	
	/**
	 * 查询
	 * @param wheres 条件字段和值
	 */
	public List<Map<String, Object>> Query(List<Where> wheres);
	
	/**
	 * 分页查询
	 * @param wheres 条件字段和值
	 * @param pm 分页Model
	 * @return 自动填充PageModel的count和maxPage，返回结果集
	 */
	public List<Map<String, Object>> Query(List<Where> wheres,PageModel pm);
	
	
}
