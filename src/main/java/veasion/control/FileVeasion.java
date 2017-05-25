package veasion.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import veasion.util.FileUtil;

public class FileVeasion {
	
	HttpServletRequest req;
	HttpServletResponse resp;
	JSONObject json;
	
	/**上传单个文件*/
	public String upFile() throws Exception {
		FileUtil fileUtil=new FileUtil();
		//最大1M
		String fileName=fileUtil.upFile(req, 1*1024*1024L, "page/desktop","test_");
		
		System.out.println(fileName);
		
		req.setAttribute("result", "上传成功！");
		
		return "file.jsp";
	}
	
	/**下载单个文件*/
	public String downloadFile() throws Exception {
		FileUtil fileUtil=new FileUtil();
		
		String fileName=String.valueOf(json.get("fileName"));
		try{
			fileUtil.downloadFile(req, resp, "images/"+fileName);
		}catch(Exception e){
			e.printStackTrace();
			req.setAttribute("downloadFileErr", "文件不存在！");
			return "file.jsp";
		}
		return null;
	}
	
}
