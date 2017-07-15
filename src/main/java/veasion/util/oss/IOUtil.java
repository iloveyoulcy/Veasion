package veasion.util.oss;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * IO Util.
 * 
 *  @author zhuowei.luo
 */
public class IOUtil {
	
	/** 根据inputStream返回byte[]的方法 **/
	public static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}
	
	/** 通过URL返回InputStream */
	public static InputStream loadUrl(URL url) throws IOException {
		URLConnection conn = url.openConnection();
		// 设置超时间为5秒
		conn.setConnectTimeout(5 * 1000);
		// 防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		// 获取inputStream
		return conn.getInputStream();
	}
}
