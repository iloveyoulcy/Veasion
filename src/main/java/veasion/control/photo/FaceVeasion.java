package veasion.control.photo;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import veasion.util.FileUtil;

/**
 * 人脸识别.
 * 
 * @author zhuowei.luo
 */
public class FaceVeasion {
	
	public HttpServletRequest req;
	public HttpServletResponse resp;
	public JSONObject json;
	
	public String goPhoto(){
		return "page/photo/video.jsp";
	}
	
	/**上传截图*/
	public JSONObject upImgFile(){
		String jtBase64Url=json.optString("jtBase64Url");
		String []str=FileUtil.base64Type(jtBase64Url);
		String type=str[0];
		String base64=str[1];
		jtBase64Url=null;str=null;
		String filePath=FileUtil.HOME_PATH+"/"+UUID.randomUUID()+"."+type;
		boolean isSuccess=FileUtil.generateImage(base64, filePath);
		if(isSuccess){
			File imgFile=new File(filePath);
			// 人脸识别
			//imgFile.delete();
		}
		JSONObject j=new JSONObject();
		j.put("message", isSuccess?"上传成功！":"上传失败！");
		return j;
	}
	
}
