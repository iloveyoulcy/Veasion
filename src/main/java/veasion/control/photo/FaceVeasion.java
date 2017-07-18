package veasion.control.photo;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aliyun.oss.OSSClient;

import net.sf.json.JSONObject;
import veasion.constant.Constant;
import veasion.util.ConfigUtil;
import veasion.util.FileUtil;
import veasion.util.face.CommonOperate;
import veasion.util.face.FaceResponse;
import veasion.util.face.FaceUtil;
import veasion.util.face.bean.CompareBean;
import veasion.util.face.bean.DetectBean;
import veasion.util.face.bean.DetectBean.Faces;
import veasion.util.oss.OssUploadFile;
import veasion.util.oss.OssUtil;

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
		// 保存图像
		this.upFileToOss(base64, type);
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
	
	/**异步保存图片到Oss*/
	private void upFileToOss(final String base64,final String type){
		// 判断是否保存图片，不保存则return
		if(!ConfigUtil.getPropertyBoolean(Constant.SaveFaceImg, false))
			return;
		new Thread(()->{
			OSSClient client=null;
			File file=null;
			try{
				String filePath=FileUtil.HOME_PATH+"/"+UUID.randomUUID()+"."+type;
				boolean saveImg=FileUtil.generateImage(base64, filePath);
				if(saveImg){
					file=new File(filePath);
					OssUploadFile upFile=new OssUploadFile(file, "faceImg/", null);
					client=OssUtil.getOssClient();
					OssUtil.uploadObject(client, OssUtil.bucketName, upFile, null);
				}
			}catch(Exception e){
				System.err.println("保存图片失败！");
				e.printStackTrace();
			}finally {
				if(client!=null)
					client.shutdown();
				if(file!=null && file.exists())
					file.delete();
			}
		}).start();
	}
	
	/**填充html*/
	private void fillHtml(DetectBean detect,StringBuilder html){
		String math[]=new String[]{"零","一","二","三","四","五","六","七","八","九","十"};
		List<Faces> facesList=detect.getFaces();
		boolean isMul=facesList.size()>1;
		html.append("<table>");
		for (int i = 0,len=facesList.size(); i < len; i++) {
			Faces f=facesList.get(i);
			String faceToken=f.getFaceToken();
			// 和作者人脸比较
			boolean isAuthor=this.isAutho(faceToken);
			if(isMul){
				// 判断是否有多个人
				html.append("<tr><td colspan='2'>").append("<br/>检测"+math[i+1]).append("</td></tr>");
			}
			if(isAuthor){
				// 判断是否是作者
				html.append("<tr><td colspan='2' style='color:green;font-size: 16px;'>").append("&nbsp;&nbsp;&nbsp;&nbsp;").append(this.randomZm()).append("</td></tr>");
				continue;
			}
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
	
	/**和作者人脸比较*/
	private boolean isAutho(String faceToken){
		// 是否和作者进行比较
		if(!ConfigUtil.getPropertyBoolean(Constant.FaceCompareAuthor, false))
			return false;
		try {
			Object token=req.getSession().getAttribute("faceToken");
			String authoFaceToken=token!=null?token.toString():null;
			CommonOperate comm=new CommonOperate(FaceUtil.getFaceKey(), FaceUtil.getFaceSecret(), false);
			FaceResponse faceResponse=comm.compare(faceToken, null, null, null, authoFaceToken, authoFaceToken==null?FaceUtil.getMyFaceImg():null, null, null);
			if(faceResponse.getStatus()==200){
				CompareBean bean=new CompareBean(faceResponse);
				double xsl=bean.getConfidence();
				System.out.println("相似度："+xsl);
				if(authoFaceToken==null){
					req.getSession().setAttribute("faceToken", bean.getFaceToken2());
				}
				if(xsl>85) return true;
				else return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**随机赞美自己*/
	private String randomZm(){
		String zm[]=
		{
				"经检测此人帅到爆表！",
				"检测发生系统故障！可能是他太帅~",
				"他的是我的工程师Veasion",
				"帅气值：100%",
				"这么帅，你可以靠脸吃饭了!",
				"你是我见过最帅的一个",
				"颜值爆表！！！",
				"小伙子很有前途！",
				"你不得了！,颜值爆表",
				"魅力已超上线，系统故障！",
				"此人人值超凡！",
				"他的名字叫Veasion，我的工程师爸爸！",
				"卓越又伟大用来形容你刚刚好！",
				"小伙是块上等好料",
				"我能猜到你是我的工程师爸爸！"
		};
		return zm[(int)(Math.random()*zm.length)];
	}
}
