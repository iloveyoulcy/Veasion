package veasion.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;

public class TextUtil {
	
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
	
	/**转义单引号和双引号*/
	public static String replace(String str){
		if(str==null||"".equals(str.trim()))
			return str;
		if (str.indexOf("'") >= 0)
			str = str.replaceAll("'", "\\\\'");
		if (str.indexOf("\"") >= 0)
			str = str.replaceAll("\"", "\\\\\"");
		return str;
	}
}
