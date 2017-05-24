package veasion.util;

/**
 * 帮助类
 *  
 * @author zhuowei.luo
 * @date 2017/5/23
 */
public class TextUtil {
	
	/**
	 * 拼接数组元素
	 * 
	 * @param join 连接符
	 */
	public static String joinObjectArr(Object[]arr,String join){
		if(arr==null||arr.length<1)return "";
		StringBuilder sb=new StringBuilder();
		for(int i=0,l=arr.length;i<l;i++){
			sb.append(arr[i]);
			if(join!=null && i<l-1)
				sb.append(join);
		}
		return sb.toString();
	}
	
	
}
