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

import veasion.util.MyRequest;

/**
 * ±àÂë¹ýÂËÆ÷
 * @author zhuowei.luo
 * @date 2017/5/7 
 */
public class VeasionFilter implements Filter{

	@Override
	public void destroy() {}
	@Override
	public void init(FilterConfig arg0) throws ServletException {}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response=(HttpServletResponse)resp;
		
		String met=request.getMethod();
		
		if("GET".equalsIgnoreCase(met)){
			MyRequest mr=new MyRequest(request);
			chain.doFilter(mr, response);
		}else if("POST".equalsIgnoreCase(met)){
			req.setCharacterEncoding("UTF-8");
			chain.doFilter(request, response);
		}
		
		chain.doFilter(request, response);
		
	}
}
