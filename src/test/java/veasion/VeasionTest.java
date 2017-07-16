package veasion;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import com.aliyun.oss.OSSClient;

import net.sf.json.JSONObject;
import veasion.util.face.CommonOperate;
import veasion.util.face.FaceUtil;
import veasion.util.face.bean.CompareBean;
import veasion.util.face.bean.DetectBean;
import veasion.util.face.FaceResponse;
import veasion.util.oss.OssUploadFile;
import veasion.util.oss.OssUtil;

/**
 * Test. 
 */
@RunWith(JUnit4ClassRunner.class)
public class VeasionTest {
	
	@Test
	public void test() {
		System.out.println("test...");
	}
	
	@Ignore
	@Test
	public void oss(){
		try{
			OSSClient client=OssUtil.getOssClient();
			OssUploadFile ossUploadFile=new OssUploadFile(
					new File("C:\\a.jpg"), "imgs/", null);
			OssUtil.uploadObject(client, OssUtil.bucketName, ossUploadFile, null);
			client.shutdown();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void face1(){
		// 人脸识别
		String apiKey=FaceUtil.getFaceKey();
		String apiSecret=FaceUtil.getFaceSecret();
		CommonOperate comm=new CommonOperate(apiKey, apiSecret, false);
		//comm.detectBase64(base64, landmark, attributes)
		try {
			FaceResponse resp=comm.detectUrl("http://59.110.241.52/Veasion/images/ws10.jpg", 0, "gender,age,smiling,glass,headpose,facequality,blur");
			System.out.println(resp.getStatus());
			System.out.println(new JSONObject().fromObject(new String(resp.getContent(),"UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void face2(){
		// 人脸对比
		String apiKey=FaceUtil.getFaceKey();
		String apiSecret=FaceUtil.getFaceSecret();
		CommonOperate comm=new CommonOperate(apiKey, apiSecret, false);
		try {
			FaceResponse resp=comm.compare("http://veasion.oss-cn-shanghai.aliyuncs.com/faceImg/veasion.jpg", null, null, null
					, null, "http://veasion.oss-cn-shanghai.aliyuncs.com/faceImg/veasion.jpg", null, null);
			System.out.println(resp.getStatus());
			CompareBean bean=new CompareBean(resp);
			System.out.println(bean.getConfidence());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

