package veasion.util.face;

import veasion.util.ConfigUtil;

/**
 * Face帮助类 
 */
public class FaceUtil {
	
	private static String FaceKey="Face_Key";
	private static String FaceSecret="Face_Secret";
	
	static{
		FaceKey=ConfigUtil.getProperty(FaceKey, null);
		FaceSecret=ConfigUtil.getProperty(FaceSecret, null);
	}
	
	/**获取Face_Key*/
	public static String getFaceKey() {
		return FaceKey;
	}
	
	/**获取Face_Secret*/
	public static String getFaceSecret() {
		return FaceSecret;
	}
	
}
