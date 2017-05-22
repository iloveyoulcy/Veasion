package veasion.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONObject;
import veasion.constant.Constant;

/**
 * SQL方法帮助类.
 * @author zhuowei.luo
 * @date 2017/5/7
 */
public class SQLUtil {
	
	/**
	 * 获取 JSONObject的Key
	 * @return keys
	 */
	public static List<String> getKeys(JSONObject json){
		if(json==null)return null;
		List<String> list=new ArrayList<>();
		for (String s : (Set<String>)json.keySet()) {
			list.add(s);
		}
		return list;
	}
	/**
	 * 根据keys获取 JSONObject中对应values
	 * @return values
	 */
	public static Object[] getValues(JSONObject json,List<String> keys){
		Object []values=new Object[keys.size()];
		for (int i = 0; i < values.length; i++) {
			values[i]=json.get(keys.get(i));
		}
		return values;
	}
	/**
	 * 连接list中的字符串
	 * @param joinStr:连接符 
	 */
	public static StringBuilder getKeys(List<String> list,String joinStr){
		if(list==null)return null;
		StringBuilder sb=new StringBuilder();
		for (int i=0;i<list.size();i++) {
			sb.append(list.get(i));
			if(i<list.size()-1)
				sb.append(joinStr);
		}
		return sb;
	}
	/**
	 * 获取JSONObject的keys
	 * @param joinStr:连接符 
	 */
	public static StringBuilder getKeys(JSONObject json,String joinStr){
		if(json==null)return null;
		StringBuilder sb=new StringBuilder();
		Set<String> keys=json.keySet();
		int index=0,count=keys.size();
		for (String s : keys) {
			sb.append(s);
			if(++index<count)
				sb.append(joinStr);
		}
		return sb;
	}
	
	/**
	 * 连接符
	 * @param symbol:连接的字符
	 * @param joinStr:中间连接符
	 * @param count:个数
	 */
	public static StringBuilder getSymbol(String symbol,String joinStr,int count){
		StringBuilder sb=new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append(symbol);
			if(i<count-1)
				sb.append(joinStr);
		}
		return sb;
	}
	
	/**
	 * 按顺序组合两个Object[] 
	 */
	public static Object[] joinObject(Object []obj1,Object []obj2){
		Object []obj=new Object[obj1.length+obj2.length];
		for (int i = 0; i < obj1.length; i++) {
			obj[i]=obj1[i];
		}
		for (int i = 0; i < obj2.length; i++) {
			obj[obj1.length+i]=obj2[i];
		}
		return obj;
	}
	
	public static Object[] getObjectByList(List<Object> list){
		if(list==null)return null;
		Object []obj=new Object[list.size()];
		for (int i = 0; i < obj.length; i++) {
			obj[i]=list.get(i);
		}
		return obj;
	}
	
	/**转义单引号，双引号和斜杠*/
	public static String replace(String str){
		if(str==null||"".equals(str.trim()))
			return str;
		if(str.indexOf("\\")>=0)
			str=str.replace("\\", "\\\\");
		if (str.indexOf("'") >= 0)
			str = str.replaceAll("'", "\\\\'");
		if (str.indexOf("\"") >= 0)
			str = str.replace("\"", "\\\\\"");
		return str;
	}
	
	/**获取当前时间*/
	public static String getDate(String pattern){
		return getDate(new Date(),pattern);
	}
	
	/**
	 * 时间转换
	 * @param date 时间
	 * @param pattern 格式
	 */
	public static String getDate(Date date,String pattern){
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * 返回数字 
	 */
	public static Integer valueOfInteger(Object obj){
		Integer v=null;
		try{
			v=Integer.valueOf(String.valueOf(obj));
		}catch(Exception e){}
		return v;
	}
	
	/**获取当前时间*/
	public static String getDate(){
		return DateUtil.getFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	
	/** 
	 * 过滤key
	 * 
	 * @param list 原数据
	 * @param newAdd 新增数据
	 * @param keyValueToStr 转换为String类型的key
	 * @param keys 过滤的key
	 */
	public static List<Map<String, Object>> filterListMap(List<Map<String, Object>> list, Map<String, Object> newAdd,
			String[] keyValueToStr, String... keys) {
		if (list == null || list.isEmpty())
			return list;
		List<Map<String, Object>> newMap = new ArrayList<>();
		Map<String, Object> m = null;
		for (Map<String, Object> map : list) {
			m = new HashMap<>();
			k: for (String key : keys) {
				if (keyValueToStr != null) {
					for (String s : keyValueToStr) {
						if (key.equals(s)) {
							Object v = map.get(key);
							if (v instanceof Date) {
								m.put(key, DateUtil.getFormatDate((Date) v, "yyyy-MM-dd HH:mm:ss"));
							} else {
								m.put(key, String.valueOf(v != null ? v : ""));
							}
							continue k;
						}
					}
				}
				m.put(key, map.get(key));
			}
			if (newAdd != null)
				m.putAll(newAdd);
			newMap.add(m);
		}
		return newMap;
	}
	
	/**打印SQL*/
	public static void printSQL(Object sql,Object param){
		if(Constant.PRINT_SQL){
			System.out.println();
			System.out.println(sql);
			if(param!=null){
				if(param instanceof Object[])
					System.out.println(Arrays.toString((Object[])param));
				else
					System.out.println(String.valueOf(param));
			}
		}
	}
	
	/**
	 * 过滤map返回想要的key-value
	 * @param source 来源数据
	 * @param 封装数据
	 * @param keys 想要封装的key 
	 */
	public static void fillJsonObject(JSONObject source,JSONObject data,String ...keys){
		for (String key : keys) {
			data.put(key, source.get(key));
		}
	} 
}
