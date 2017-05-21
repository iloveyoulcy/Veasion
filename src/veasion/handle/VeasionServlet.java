package veasion.handle;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import veasion.constant.Constant;


/**
 * 请求中央处理器
 * @author zhuowei.luo
 * @date 2017/5/7 
 */
public class VeasionServlet extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String uri=request.getRequestURI();
		String contextPath=request.getContextPath();
		String vea=uri.replaceFirst(contextPath, "");
		//匹配规则，/xxx/xx.vea
		if(vea.matches("/.*(/.*){1}\\.vea")){
			vea=vea.replaceFirst("/", "").replace(".vea", "").trim();
			int index=vea.lastIndexOf("/");
			String className=vea.substring(0, index).replace("/", ".");
			int classIndex=className.lastIndexOf(".");
			if(classIndex!=-1){
				String packageName=className.substring(0, classIndex);
				String clazz=className.substring(classIndex+1);
				clazz=clazz.replaceFirst(".", String.valueOf(clazz.charAt(0)).toUpperCase());
				className=packageName+"."+clazz;
			}else{
				className=className.replaceFirst(".", String.valueOf(className.charAt(0)).toUpperCase());
			}
			String methodName=vea.substring(index+1);
			methodName=methodName.replaceFirst(".", String.valueOf(methodName.charAt(0)).toUpperCase());
			className=Constant.CONTROL_PACKAGE_NAME+"."+className+Constant.CONTROL_CLASS_NAME;
			Class c=null;
			//反射找到对应control.veasion
			try {
				c = Class.forName(className);
				Object obj=c.newInstance();
				Field []fields=c.getDeclaredFields();
				
				//反射属性request，response和json数据
				if(fields!=null&&fields.length>0){
					for (Field f : fields) {
						f.setAccessible(true);
						if(f.getType().getName().endsWith("HttpServletRequest")){
							f.set(obj,request);
						}else if(f.getType().getName().endsWith("HttpServletResponse")){
							f.set(obj, response);
						}else if(f.getType().getName().endsWith("net.sf.json.JSONObject")){
							f.set(obj, getParameterData(request));
						}
					}
				}
				
				//反射对应类的对应方法
				Method[] methods = c.getMethods();
				boolean success=false;
				for (int i = 0; i < methods.length; i++) {
					Method method = methods[i];
					if (method.getName().toLowerCase().startsWith(methodName.toLowerCase())) {
						//查找看有没有同名开头的，有就匹配最短的
						int methodIndex=i,minLen=method.getName().length();
						for (int j = 0; j < methods.length; j++) {
							Method method2 = methods[j];
							if (method2.getName().toLowerCase().startsWith(methodName.toLowerCase()) 
									&& method2.getName().length()<minLen){
								methodIndex=j;
							}
						}
						//有同名开头的方法就匹配最短的
						if(methodIndex!=i){
							method = methods[methodIndex];
						}
						//调用对应方法
						Object returnObj=method.invoke(obj);
						if(returnObj==null){
							success=true;
							break;
						}else if(!(returnObj instanceof String)){
							JSONObject json=null;
							if(!(returnObj instanceof JSONObject)
									&&!(returnObj instanceof Map)){
								Map<String, Object> map=new HashMap<>();
								map.put("object", returnObj);
								json=JSONObject.fromObject(map);
							}else{
								json=JSONObject.fromObject(returnObj);
							}
							response.setContentType("text/json;charset=utf-8");
							PrintWriter pw=response.getWriter();
							pw.append(json.toString());
							pw.close();
							success=true;
							break;
						}
						String returnUrl=String.valueOf(returnObj);
						//System.out.println(returnUrl);
						if(returnUrl.startsWith(Constant.REDIRECT)){
							returnUrl=returnUrl.replace(Constant.REDIRECT, "").trim();
							response.sendRedirect(returnUrl);
						}else{
							int xgIndex=-1;
							String mlStr="";
							//url目录层级用../解决
							while((xgIndex=vea.indexOf("/",xgIndex+1))!=-1){
								mlStr+="../";
							}
							request.getRequestDispatcher(mlStr+returnUrl).forward(request, response);
						}
						success=true;
						break;
					}
				}
				if(!success){
					response.sendError(404);
				}
			} catch (Exception e) {
				response.sendError(500);
				e.printStackTrace();
			}
			
		}else{
			response.sendError(404);
		}
	}
	
	/**分装请求数据*/
	private JSONObject getParameterData(HttpServletRequest request){
		Map<String, Object> map=new HashMap<>();
		Enumeration<String> e=request.getParameterNames();
		while(e.hasMoreElements()){
			String element=e.nextElement();
			map.put(element,request.getParameter(element));
		}
		return JSONObject.fromObject(map);
	}
}
