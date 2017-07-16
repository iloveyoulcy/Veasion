package veasion.util.face.bean;


import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import veasion.util.face.FaceResponse;

/**
 * 人脸识别. 
 */
public class DetectBean {
	
	private JSONObject json=null;
	private int status;
	private String imageId;
	private String requestId;
	private int timeUsed;
	private List<Faces> faces;
	private JSONArray facesJson;
	
	public DetectBean(FaceResponse resp){
		if((this.status=resp.getStatus())==200){
			try {
				json=JSONObject.fromObject(new String(resp.getContent(),"UTF-8"));
				if(json!=null)
					this.fill();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void fill(){
		this.imageId=json.optString("image_id");
		this.requestId=json.optString("request_id");
		this.timeUsed=json.optInt("time_used");
		this.facesJson=json.getJSONArray("faces");
		this.faces=new ArrayList<>();
		for (Object face : facesJson) {
			Faces f=new Faces();
			JSONObject faceJson=JSONObject.fromObject(face);
			JSONObject faceRectangle=JSONObject.fromObject(faceJson.opt("face_rectangle"));
			f.setFaceRectangle(faceRectangle);
			f.setFaceToken(faceJson.optString("face_token"));
			f.setFaceWidth(faceRectangle.optInt("width"));
			f.setFaceHeight(faceRectangle.optInt("height"));
			f.setFaceTop(faceRectangle.optInt("top"));
			f.setFaceLeft(faceRectangle.optInt("left"));
			
			JSONObject attributes=JSONObject.fromObject(faceJson.opt("attributes"));
			f.setGlass(JSONObject.fromObject(attributes.opt("glass")).optString("value"));
			JSONObject temp=JSONObject.fromObject(attributes.opt("headpose"));
			f.setYawAngle(temp.optInt("yaw_angle"));
			f.setPitchAngle(temp.optInt("pitch_angle"));
			f.setRollAngle(temp.optInt("roll_angle"));
			temp=JSONObject.fromObject(attributes.opt("smile"));
			f.setSmiling(temp.optInt("value"));
			f.setHasSmiling(f.getSmiling()>=temp.optInt("threshold"));
			temp=JSONObject.fromObject(attributes.opt("gender"));
			f.setGender(temp.optString("value"));
			temp=JSONObject.fromObject(attributes.opt("age"));
			f.setAge(temp.optInt("value"));
			temp=JSONObject.fromObject(attributes.opt("blur"));
			f.setBlurness(JSONObject.fromObject(temp.opt("gaussianblur")).optInt("value"));
			temp=JSONObject.fromObject(attributes.opt("facequality"));
			f.setFacequality(temp.optInt("value"));
			if(faceJson.opt("landmark")!=null)
				f.setLandmark(JSONObject.fromObject(faceJson.opt("landmark")));
			this.faces.add(f);
		}
	}
	
	
	
	public JSONObject getJson() {
		return json;
	}
	public int getStatus() {
		return status;
	}
	public List<Faces> getFaces() {
		return faces;
	}
	public String getImageId() {
		return imageId;
	}
	/**用时*/
	public int getTimeUsed() {
		return timeUsed;
	}
	/**requestId*/
	public String getRequestId() {
		return requestId;
	}
	public JSONArray getFacesJson() {
		return facesJson;
	}

	public class Faces{
		/**人脸标识*/
		private String faceToken;
		/**人脸的关键点*/
		private JSONObject landmark;
		/**人脸框的位置*/
		private JSONObject faceRectangle;
		/**人脸属性特征*/
		private JSONObject attributes;
		
		/**人脸框的位置的宽度*/
		private int faceWidth;
		/**人脸框的位置的高度*/
		private int faceHeight;
		/**人脸框的位置的Top*/
		private int faceTop;
		/**人脸框的位置的Left*/
		private int faceLeft;
		/**性别*/
		private String gender;
		/**年龄*/
		private int age;
		/**笑容 1-100*/
		private int smiling;
		/**是否在笑*/
		private boolean hasSmiling;
		/**佩戴什么眼镜，null为不佩戴*/
		private String glass;
		/**抬头， [-180,180]度*/
		private int pitchAngle;
		/**平面旋转，[-180,180]度*/
		private int rollAngle;
		/**摇头，[-180,180]度*/
		private int  yawAngle;
		/**人脸模糊度,范围[0,100]*/
		private int blurness;
		/**人脸质量,范围[0,100]*/
		private int facequality;
		
		/**人脸标识*/
		public String getFaceToken() {
			return faceToken;
		}
		/**人脸标识*/
		public void setFaceToken(String faceToken) {
			this.faceToken = faceToken;
		}
		/**人脸的关键点*/
		public JSONObject getLandmark() {
			return landmark;
		}
		/**人脸的关键点*/
		public void setLandmark(JSONObject landmark) {
			this.landmark = landmark;
		}
		/**人脸框的位置*/
		public JSONObject getFaceRectangle() {
			return faceRectangle;
		}
		/**人脸框的位置*/
		public void setFaceRectangle(JSONObject faceRectangle) {
			this.faceRectangle = faceRectangle;
		}
		/**人脸属性特征*/
		public JSONObject getAttributes() {
			return attributes;
		}
		/**人脸属性特征*/
		public void setAttributes(JSONObject attributes) {
			this.attributes = attributes;
		}
		/**人脸框的位置的宽度*/
		public int getFaceWidth() {
			return faceWidth;
		}
		/**人脸框的位置的宽度*/
		public void setFaceWidth(int faceWidth) {
			this.faceWidth = faceWidth;
		}
		/**人脸框的位置的高度*/
		public int getFaceHeight() {
			return faceHeight;
		}
		/**人脸框的位置的高度*/
		public void setFaceHeight(int faceHeight) {
			this.faceHeight = faceHeight;
		}
		/**人脸框的位置的Top*/
		public int getFaceTop() {
			return faceTop;
		}
		/**人脸框的位置的Top*/
		public void setFaceTop(int faceTop) {
			this.faceTop = faceTop;
		}
		/**人脸框的位置的Left*/
		public int getFaceLeft() {
			return faceLeft;
		}
		/**人脸框的位置的Left*/
		public void setFaceLeft(int faceLeft) {
			this.faceLeft = faceLeft;
		}
		/**性别*/
		public String getGender() {
			return "Male".equals(this.gender)?"男":"女";
		}
		/**性别*/
		public void setGender(String gender) {
			this.gender = gender;
		}
		/**年龄*/
		public int getAge() {
			return age;
		}
		/**年龄*/
		public void setAge(int age) {
			this.age = age;
		}
		/**笑容 1-100*/
		public int getSmiling() {
			return smiling;
		}
		/**笑容 1-100*/
		public void setSmiling(int smiling) {
			this.smiling = smiling;
		}
		/**是否在笑*/
		public boolean isHasSmiling() {
			return hasSmiling;
		}
		/**是否在笑*/
		public void setHasSmiling(boolean hasSmiling) {
			this.hasSmiling = hasSmiling;
		}
		/**佩戴什么眼镜，null为不佩戴*/
		public String getGlass() {
			if(this.glass!=null){
				if("Normal".equals(this.glass))
					return "没有戴眼镜";
				else if ("Dark".equals(this.glass))
					return "墨镜";
				else if("Normal".equals(this.glass))
					return "普通眼镜";
				else
					return glass;
			}
			return glass;
		}
		/**佩戴什么眼镜，null为不佩戴*/
		public void setGlass(String glass) {
			this.glass = glass;
		}
		/**抬头， [-180,180]度*/
		public int getPitchAngle() {
			return pitchAngle;
		}
		/**抬头， [-180,180]度*/
		public void setPitchAngle(int pitchAngle) {
			this.pitchAngle = pitchAngle;
		}
		/**平面旋转， [-180,180]度*/
		public int getRollAngle() {
			return rollAngle;
		}
		/**平面旋转， [-180,180]度*/
		public void setRollAngle(int rollAngle) {
			this.rollAngle = rollAngle;
		}
		/**摇头， [-180,180]度*/
		public int getYawAngle() {
			return yawAngle;
		}
		/**摇头， [-180,180]度*/
		public void setYawAngle(int yawAngle) {
			this.yawAngle = yawAngle;
		}
		/**人脸模糊度,范围[0,100]*/
		public int getBlurness() {
			return blurness;
		}
		/**人脸模糊度,范围[0,100]*/
		public void setBlurness(int blurness) {
			this.blurness = blurness;
		}
		/**是否模糊度*/
		public boolean isBlurness() {
			return this.blurness>=50;
		}
		
		/**人脸质量,范围[0,100]*/
		public int getFacequality() {
			return facequality;
		}
		/**人脸质量,范围[0,100]*/
		public void setFacequality(int facequality) {
			this.facequality = facequality;
		}
		
		/**获取评价*/
		public String veasionFace(){
			if(isBlurness())
				return "照片很模糊哦，请重新拍一下~";
			StringBuilder sb=new StringBuilder();
			String name="",zx="",yy="";
			boolean isGender="男".equals(getGender());
			if(getAge()>60){
				name=isGender?"大爷":"阿婆";
			}else if(getAge()>35){
				name=isGender?"大叔":"阿姨";
			}else if(getAge()>22){
				name=isGender?"大哥":"小姐姐";
			}else if(getAge()>18){
				name=isGender?"兄弟":"妞";
			}else if(getAge()>15){
				name=isGender?"小弟弟":"小妹妹";
			}else{
				name=isGender?"小屁孩":"小娃子";
			}
			if(getFacequality()>90){
				zx=getAge()>40?"保养的超级超级好":
					getAge()>35?isGender?"比我还帅":"超级有气质，年轻时候很定是美女":
						getAge()>25?isGender?"比我还帅":"好漂亮哦":
							getAge()>15?isGender? "比我还帅":"是小仙女":
								"是块好料";
							yy=getAge()<25&&!isGender?"，约吗？":"";
			}else if(getFacequality()>80){
				zx=getAge()>40?"保养的真好":
					getAge()>35?isGender?"真帅":"有气质":
						getAge()>25?isGender?"很帅":"好漂亮":
							getAge()>15?isGender? "很帅":"好漂亮":
								"是块好料";
			}else if(getFaceHeight()>70){
				zx=getAge()>40?"保养的很好":
					getAge()>35?isGender?"长相一般":"有点气质":
						getAge()>25?isGender?"长相一般":"气质小成":
							getAge()>15?isGender? "长相一般":"女大十八变":
								"被长相糟蹋了";
			}else{
				zx=getAge()>40?"要好好保养":
					getAge()>15?isGender? "长相有点~":"长相有点~":
								"这不是靠脸的时代";
			}
			sb.append(name).append(zx).append(yy);
			return sb.toString();
		}
		
		public String zhuangTai(){
			StringBuilder sb=new StringBuilder();
			if(getPitchAngle()<-40){
				sb.append("低头;");
			}else if(getPitchAngle()<-20){
				sb.append("微微低头;");
			}else if(getPitchAngle()>20){
				sb.append("微微抬头;");
			}else if(getPitchAngle()>40){
				sb.append("抬头;");
			}
			if(getRollAngle()<-40){
				sb.append("在转身;");
			}else if(getPitchAngle()>40){
				sb.append("在转身;");
			}
			if(getYawAngle()<-40){
				sb.append("头是歪的;");
			}else if(getPitchAngle()>40){
				sb.append("头是歪的;");
			}
			if(sb.length()<1)sb.append("端正;");
			return sb.toString();
		}
		
		public String xiao(){
			if(isHasSmiling()){
				if(this.smiling>90)
					return "哈哈哈大笑";
				else if(this.smiling>80)
					return "哈哈大笑";
				else if(this.smiling>60)
					return "傻笑";
				else if(this.smiling>50)
					return "微笑";
				else
					return "微微一笑";
			}else{
				return "没有笑";
			}
		}
		
		@Override
		public String toString() {
			return "Faces [faceToken=" + faceToken + ", landmark=" + landmark + ", faceRectangle=" + faceRectangle
					+ ", attributes=" + attributes + ", faceWidth=" + faceWidth + ", faceHeight=" + faceHeight
					+ ", faceTop=" + faceTop + ", faceLeft=" + faceLeft + ", gender=" + gender + ", age=" + age
					+ ", smiling=" + smiling + ", hasSmiling=" + hasSmiling + ", glass=" + glass + ", pitchAngle="
					+ pitchAngle + ", rollAngle=" + rollAngle + ", yawAngle=" + yawAngle + ", blurness=" + blurness
					+ ", facequality=" + facequality + "]";
		}
	}
}
