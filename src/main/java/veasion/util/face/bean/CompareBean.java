package veasion.util.face.bean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import veasion.util.face.FaceResponse;

/**
 * 人脸比较 
 */
public class CompareBean {
	
	private JSONObject json;
	
	private JSONArray faces1;
	
	private JSONArray faces2;
	
	private String faceToken1;
	
	private String faceToken2;
	
	/**相似率*/
	private double confidence;
	
	private int status;
	
	public CompareBean(FaceResponse resp){
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
		this.confidence=json.optDouble("confidence", 0.0);
		this.faces1=json.optJSONArray("faces1");
		this.faces2=json.optJSONArray("faces2");
		if(this.faces1!=null && this.faces1.size()>0){
			this.faceToken1=JSONObject.fromObject(this.faces1.get(0)).optString("face_token");
		}
		if(this.faces2!=null && this.faces2.size()>0){
			this.faceToken2=JSONObject.fromObject(this.faces2.get(0)).optString("face_token");
		}
	}

	public JSONObject getJson() {
		return json;
	}
	public JSONArray getFaces1() {
		return faces1;
	}
	public JSONArray getFaces2() {
		return faces2;
	}
	public String getFaceToken1() {
		return faceToken1;
	}
	public String getFaceToken2() {
		return faceToken2;
	}
	public double getConfidence() {
		return confidence;
	}
	public int getStatus() {
		return status;
	}
	
	@Override
	public String toString() {
		return json.toString();
	}
	
}
