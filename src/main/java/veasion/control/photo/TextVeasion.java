package veasion.control.photo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import veasion.util.FileUtil;
import veasion.util.VeaUtil;
import veasion.util.face.FaceResponse;
import veasion.util.face.FaceUtil;
import veasion.util.face.ImageOperate;
import veasion.util.face.bean.ImageTextBean;

/**
 * 文字识别 
 * 
 * @author zhuowei.luo
 */
public class TextVeasion {

	public HttpServletRequest req;
	public HttpServletResponse resp;
	public JSONObject json;
	
	public String goText(){
		return "page/photo/text.jsp";
	}
	
	/**上传图片进行文字识别*/
	public String textFile() {
		try {
			String base64Url=json.optString("base64Url");
			base64Url=FileUtil.base64Type(base64Url)[1];
			if(VeaUtil.isNullEmpty(base64Url))
				return "请选择图片！";
			System.out.println(base64Url);
			ImageOperate image=new ImageOperate(FaceUtil.getFaceKey(), FaceUtil.getFaceSecret());
			FaceResponse resp=image.textRecognition(null, null, base64Url);
			ImageTextBean bean=new ImageTextBean(resp);
			System.out.println(bean.getStatus()+"\n"+bean.getTextHtml());
			if(bean.getStatus()!=200)
				return bean.getMessage();
			else
				return bean.getTextHtml();
		} catch (Exception e) {
			e.printStackTrace();
			return "识别发生异常！";
		}
	}
	
}
