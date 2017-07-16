package veasion.control.photo;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import veasion.util.FileUtil;
import veasion.util.face.CommonOperate;
import veasion.util.face.FaceResponse;
import veasion.util.face.FaceUtil;
import veasion.util.face.bean.DetectBean;
import veasion.util.face.bean.DetectBean.Faces;

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
		JSONObject j=new JSONObject();
		String jtBase64Url=json.optString("jtBase64Url");
		String []str=FileUtil.base64Type(jtBase64Url);
		String type=str[0];
		String base64=str[1];
		jtBase64Url=null;str=null;
		boolean isSuccess=true;
		// 人脸识别
		CommonOperate comm=new CommonOperate(FaceUtil.getFaceKey(), FaceUtil.getFaceSecret(), false);
		try {
			FaceResponse resp=comm.detectBase64(base64, 0, "gender,age,smiling,glass,headpose,facequality,blur");
			DetectBean detect=new DetectBean(resp);
			if(resp.getStatus()!=200) throw new Exception("请求失败！错误码："+resp.getStatus());
			StringBuilder html=new StringBuilder();
			this.fillHtml(detect, html);
			j.put("html", html.toString());
		} catch (Exception e) {
			isSuccess=false;
			e.printStackTrace();
		}
		if(!isSuccess) j.put("message", "检测失败！");
		return j;
	}
	
	/**填充html*/
	private void fillHtml(DetectBean detect,StringBuilder html){
		String math[]=new String[]{"零","一","二","三","四","五","六","七","八","九","十"};
		List<Faces> facesList=detect.getFaces();
		boolean isMul=facesList.size()>1;
		html.append("<table>");
		for (int i = 0,len=facesList.size(); i < len; i++) {
			Faces f=facesList.get(i);
			if(isMul)
				html.append("<tr><td colspan='2'>").append("<br/>检测"+math[i+1]).append("</td></tr>");
			// 性别
			html.append("<tr><td>").append("性别：").append("</td>");
			html.append("<td>").append(f.getGender()).append("</td></tr>");
			// 年龄
			html.append("<tr><td>").append("年龄：").append("</td>");
			html.append("<td>").append(f.getAge()).append("</td></tr>");
			// 相貌
			html.append("<tr><td>").append("相貌：").append("</td>");
			html.append("<td>").append(f.getFacequality()).append("</td></tr>");
			// 此时状态
			html.append("<tr><td>").append("状态：").append("</td>");
			html.append("<td>").append(f.zhuangTai()).append("</td></tr>");
			// 笑
			html.append("<tr><td>").append("is笑：").append("</td>");
			html.append("<td>").append(f.xiao()).append("</td></tr>");
			// 评估
			html.append("<tr><td>").append("评价：").append("</td>");
			html.append("<td>").append(f.veasionFace()).append("</td></tr>");
		}
		html.append("</table>");
	}
}
