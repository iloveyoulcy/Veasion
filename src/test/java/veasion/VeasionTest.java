package veasion;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import com.aliyun.oss.OSSClient;

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
	
}

