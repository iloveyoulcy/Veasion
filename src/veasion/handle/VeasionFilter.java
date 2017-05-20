package veasion.handle;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import veasion.bean.IpRecord;
import veasion.constant.Constant;
import veasion.control.AdminVeasion;
import veasion.service.BeanService;
import veasion.service.impl.MysqlServieImpl;
import veasion.util.DateUtil;
import veasion.util.HttpUtil;
import veasion.util.MyRequest;
import veasion.util.SQLUtil;

/**
 * 编码过滤器
 * @author zhuowei.luo
 * @date 2017/5/7 
 */
public class VeasionFilter implements Filter{

	@Override
	public void destroy() {}
	@Override
	public void init(FilterConfig config) throws ServletException {}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response=(HttpServletResponse)resp;
		
		String uri=request.getRequestURI();
		String vea=uri.replaceFirst(request.getContextPath(), "");
		
		//简单过滤Admin页面
		if (vea.startsWith("/admin/") && vea.indexOf("validation.vea") == -1) {
			Object obj = request.getSession().getAttribute(AdminVeasion.ADMIN_NAME);
			if (obj == null || !AdminVeasion.ADMIN_VEA.equals(obj)) {
				return;
			}
		}
		
		if(request.getSession().isNew()){
			String ip=HttpUtil.getIpAddress(request);
			if (	!"0:0:0:0:0:0:0:1".equals(ip) 
					&& !"127.0.0.1".equals(ip) 
					&& !"localhost".equals(ip)) {
				try {
					// 来访记录
					BeanService service = new MysqlServieImpl(IpRecord.tableName);
					JSONObject data = new JSONObject();
					data.put(IpRecord.ip, ip);
					data.put(IpRecord.date, SQLUtil.getDate());
					data.put(IpRecord.line, Constant.ON_LINE);
					service.Add(data);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
		String met=request.getMethod();
		
		if("GET".equalsIgnoreCase(met)){
			MyRequest mr=new MyRequest(request);
			chain.doFilter(mr, response);
		}else if("POST".equalsIgnoreCase(met)){
			req.setCharacterEncoding("UTF-8");
			chain.doFilter(request, response);
		}else{
			chain.doFilter(request, response);
		}
		
	}
}
