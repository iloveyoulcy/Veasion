package veasion.util;

import java.util.List;

/**
 * 返回json对象Model
 * @author zhuowei.luo
 * @date 2017/5/11 
 */
public class ReturnJson {
	
	/**code代码*/
	private Integer code;
	/**消息*/
	private String message;
	/**集合*/
	private List<?> list;
	/**对象*/
	private Object object;
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	
	/**
	 * 操作状态. 
	 */
	public enum ReturnCode{
		
		SUCCESS("成功",1),FAIL("失败",0);
		
		private String name;
		private Integer code;
		
		private ReturnCode(String name,Integer code){
			this.name=name;
			this.code=code;
		}
		
		public String getName() {
			return name;
		}
		
		public Integer getCode() {
			return code;
		}
	}
}
