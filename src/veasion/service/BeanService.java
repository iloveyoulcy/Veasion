package veasion.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import veasion.bean.Where;
import veasion.util.PageModel;

/**
 * �������ݲ���Service
 * @author zhuowei.luo
 * @date 2017/5/7 
 */
public interface BeanService {
	
	/**
	 * ����
	 * @param json ������ֶκ�ֵ
	 * @return ������id
	 */
	public int Add(JSONObject json);
	
	/**
	 * �޸�
	 * @param setJson ��Ҫ���޸ĵ��ֶκ�ֵ
	 * @param whereJson �������ֶκ�ֵ
	 */
	public int Update(JSONObject setJson,JSONObject whereJson);
	
	/**
	 * ����id������ɾ��
	 * @param column �����ֶ���
	 * @param value ����ֵ
	 */
	public int Delete(String column,Object value);
	
	/**
	 * Count
	 * @param wheres ������ֵ
	 * @return nullΪ-1������Ϊ>=0
	 */
	public int Count(List<Where> wheres);
	
	/**
	 * ����id��������ѯ
	 * @param column �����ֶ�
	 * @param value ����ֵ
	 * @return ֻ���ص�һ������
	 */
	public Map<String,Object> QueryById(String column,Object value);
	
	/**
	 * ��ѯ
	 * @param wheres �����ֶκ�ֵ
	 */
	public List<Map<String, Object>> Query(List<Where> wheres);
	
	/**
	 * ��ҳ��ѯ
	 * @param wheres �����ֶκ�ֵ
	 * @param pm ��ҳModel
	 * @return �Զ����PageModel��count��maxPage�����ؽ����
	 */
	public List<Map<String, Object>> Query(List<Where> wheres,PageModel pm);
	
	
}
