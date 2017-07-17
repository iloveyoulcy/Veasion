package veasion.handle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.sf.json.JSONObject;
import veasion.bean.BeanConstant;
import veasion.constant.Constant;
import veasion.util.VeaUtil;
import veasion.util.annotation.Veasion;

/**
 * 请求中央处理器
 * 
 * @author zhuowei.luo
 * @date 2017/5/7
 */
public class VeasionServlet extends HttpServlet {

	/**
	 * 保存文件上传的临时文件，请求结束便删除临时文件. 
	 */
	List<File> fileList=new ArrayList<>();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String vea = uri.replaceFirst(contextPath, "");

		// 匹配规则，/xxx/xx.vea
		if (!isVea(vea)) {
			response.sendError(404);
			return;
		}

		String[] classMethod = getClassMethod(vea);
		String className = classMethod[0];
		String methodName = classMethod[1];
		vea= classMethod[2];

		Class c = null;
		// 反射找到对应control.veasion
		try {
			c = Class.forName(className);
			Object obj = c.newInstance();
			
			//反射属性request，response和json数据
			this.reflectAttribute(request, response, c, obj);
			
			// 反射对应类的对应方法
			Method[] methods = c.getMethods();
			boolean success = false;
			
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				String fName=method.getName().toLowerCase().trim();
				boolean isRedirect=false;
				Veasion v=method.getAnnotation(Veasion.class);
				
				//注解方法判断
				success=this.annotationVeasion(v,methodName,isRedirect);
				if(success){
					isRedirect=v.redirect();
				}
				//普通方法判断
				if (!success && fName.equalsIgnoreCase(methodName)) {
					success=true;
				}
				
				if(success){
					// 调用对应方法
					Object returnObj = method.invoke(obj);
					//封装响应数据并跳转
					success=dispatcher(request, response, vea, returnObj, isRedirect);
					if(success)break;
				}
			}
			if (!success) {
				response.sendError(404);
			}
		} catch (Exception e) {
			response.sendError(500);
			e.printStackTrace();
		}finally {
			// 如果是文件上传则删除临时文件
			if(!VeaUtil.isNullEmpty(fileList)){
				fileList.forEach((f)->{
					if(f.isFile() && f.exists())
						f.delete();
				});
			}
			fileList.clear();
		}
	}
	
	/**注解方法判断*/
	private boolean annotationVeasion(Veasion v, String fName, Boolean isRedirect) {
		if(v==null)return false;
		return v.value().trim().equalsIgnoreCase(fName);
	}
	
	/**封装响应数据并跳转*/
	private boolean dispatcher(HttpServletRequest request, HttpServletResponse response, String vea, Object returnObj, boolean isRedirect)
			throws IOException, ServletException {
		//封装响应数据并跳转
		if (returnObj == null) {
			return true;
		} else if (!(returnObj instanceof String) 
				|| !String.valueOf(returnObj).matches(".*\\.(jsp|html|htm)")) {
			JSONObject json = null;
			if (!(returnObj instanceof JSONObject) && !(returnObj instanceof Map)) {
				Map<String, Object> map = new HashMap<>();
				map.put(BeanConstant.jsonObject, returnObj);
				json = JSONObject.fromObject(map);
			} else {
				json = JSONObject.fromObject(returnObj);
			}
			response.setContentType("text/json;charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.append(json.toString());
			pw.close();
			return true;
		}
		
		//获取url
		String returnUrl = String.valueOf(returnObj);
		//获取返回根目录..
		String rootDir=this.rootDir(vea);
		
		// 判断转发或重定向
		if (isRedirect || returnUrl.startsWith(Constant.REDIRECT)) {
			returnUrl = returnUrl.replace(Constant.REDIRECT, "").trim();
			response.sendRedirect(rootDir + returnUrl);
		} else {
			request.getRequestDispatcher(rootDir + returnUrl).forward(request, response);
		}
		return true;
	}
	
	/**反射属性request，response和json数据*/
	private void reflectAttribute(HttpServletRequest request, HttpServletResponse response, Class c, Object obj)
			throws IllegalAccessException {
		Field[] fields = c.getDeclaredFields();
		// 反射属性request，response和json数据
		if (fields != null && fields.length > 0) {
			for (Field f : fields) {
				f.setAccessible(true);
				if (f.getType().getName().endsWith("HttpServletRequest")) {
					f.set(obj, request);
				} else if (f.getType().getName().endsWith("HttpServletResponse")) {
					f.set(obj, response);
				} else if (f.getType().getName().endsWith("net.sf.json.JSONObject")) {
					f.set(obj, getParameterData(request));
				}
			}
		}
	}

	/** 分装请求数据 */
	private JSONObject getParameterData(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Enumeration<String> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String element = e.nextElement();
			String[] values = request.getParameterValues(element);
			if (values == null || values.length <= 1) {// 只有一个值
				map.put(element, request.getParameter(element));
			} else {// 多个值
				map.put(element, values);
			}
		}
		
		// 判断是否为文件上传
		if(ServletFileUpload.isMultipartContent(request)){
			DiskFileItemFactory factory = new DiskFileItemFactory(); 
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(Constant.UP_FILE_MAX);
			try{
				List<FileItem> items = upload.parseRequest(request); 
				//Map param = new HashMap(); 
				for(FileItem item : items){ 
				    if (item.isFormField()) {
				    	map.put(item.getFieldName(), item.getString("utf-8"));
				    }else{
				    	String fileName = new File(item.getName()).getName();
				    	InputStream fileSource = item.getInputStream();
						String tempFileName = Constant.HOME_PATH+File.separator+fileName;
						// tempFile指向临时文件
						File tempFile = new File(tempFileName);
						// outputStram文件输出流指向这个临时文件
						FileOutputStream outputStream = new FileOutputStream(tempFile);
						byte b[] = new byte[1024];
						int n;
						while ((n = fileSource.read(b)) != -1) {
							outputStream.write(b, 0, n);
						}
						// 关闭输出流、输入流
						outputStream.close();
						fileSource.close();
						fileList.add(tempFile);
						map.put(item.getFieldName(), tempFileName);
				    }
				}
			}catch(Exception e1){e1.printStackTrace();}
		}
		
		return JSONObject.fromObject(map);
	}

	/** 验证是否为有效访问 */
	private boolean isVea(String vea) {
		return vea.matches("/.*(/.*){1}\\.vea");
	}

	/** 获取className,methodName */
	private String[] getClassMethod(String vea) {
		String className = null;
		String methodName = null;
		vea = vea.replaceFirst("/", "").replace(".vea", "").trim();
		int index = vea.lastIndexOf("/");
		className = vea.substring(0, index).replace("/", ".");
		int classIndex = className.lastIndexOf(".");
		if (classIndex != -1) {
			String packageName = className.substring(0, classIndex);
			String clazz = className.substring(classIndex + 1);
			clazz = clazz.replaceFirst(".", String.valueOf(clazz.charAt(0)).toUpperCase());
			className = packageName + "." + clazz;
		} else {
			className = className.replaceFirst(".", String.valueOf(className.charAt(0)).toUpperCase());
		}
		methodName = vea.substring(index + 1);
		methodName = methodName.replaceFirst(".", String.valueOf(methodName.charAt(0)).toUpperCase());
		className = Constant.CONTROL_PACKAGE_NAME + "." + className + Constant.CONTROL_CLASS_NAME;
		if(methodName!=null)methodName=methodName.trim();
		return new String[] { className, methodName, vea};
	}
	
	/**获取返回根目录../*/
	public String rootDir(String vea){
		int xgIndex = -1;
		String dir = "";
		// url目录层级用../解决
		while ((xgIndex = vea.indexOf("/", xgIndex + 1)) != -1) {
			dir += "../";
		}
		return dir;
	}
}
