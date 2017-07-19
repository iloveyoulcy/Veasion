package veasion.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Http帮助类
 * 
 * @author zhuowei.luo
 * @date 2017/5/20
 */
public class HttpUtil {

	/**
	 * 获取用户真实ip地址 
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**根据ip获取所在地*/
	public static String getAreaByIp(String ip){
		if ("0:0:0:0:0:0:0:1".equals(ip) 
			|| "127.0.0.1".equals(ip) 
			|| "localhost".equals(ip)) 
			return "本地";
		try {
			Document doc=Jsoup.connect("http://ip.cn/index.php?ip="+ip)
					.header("accept", "*/*")
					.header("connection", "Keep-Alive")
					.header("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)")
					.timeout(1_0000).get();
			String area="p:contains(地理位置) code";
			Elements es=doc.select(area);
			return es.text();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		String area=getAreaByIp("211.95.45.66");
		System.out.println(area);
	}
	
}
