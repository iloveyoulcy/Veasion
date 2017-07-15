package veasion.util.oss;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import veasion.util.ConfigUtil;

/**
 * Oss Util.
 * 
 * @author zhuowei.luo
 */
public class OssUtil {
	
	private static String ossHttp="http://";
	private static String endpoint="oss-cn-shanghai.aliyuncs.com";
	public static String bucketName="veasion";
	
	private final static String Oss_Key="Oss_Key";
	private final static String Oss_Secret="Oss_Secret";
	private final static String Oss_Bucket="Oss_Bucket";
	private final static String Oss_Http="Oss_Http";
	private final static String Oss_EndPoint="Oss_EndPoint";
	
	
	/**
	 * 服务端获取OssClient
	 * 
	 * @since 记得用完关闭控制台 ossClient.shutdown()
	 */
	public static OSSClient getOssClient() throws Exception {
		ossHttp=ConfigUtil.getProperty(Oss_Http, ossHttp);
		endpoint=ConfigUtil.getProperty(Oss_EndPoint, endpoint);
		bucketName=ConfigUtil.getProperty(Oss_Bucket, bucketName);
		
		String key=ConfigUtil.getProperty(Oss_Key);
		String secret=ConfigUtil.getProperty(Oss_Secret);
		return new OSSClient(ossHttp+endpoint, key, secret);
	}
	
	/**
	 * 上传文件
	 * 
	 * @param ossListener 进度监听器
	 * @return url链接地址
	 */
	public static String uploadObject(OSSClient ossClient, String bucketName, OssUploadFile ossUploadFile,
			OssListener ossListener) throws Exception {
		Object object = ossUploadFile.getObject();
		String key = ossUploadFile.getOssKey();
		PutObjectRequest putObjectRequest = null;
		ObjectMetadata metdata = new ObjectMetadata();
		metdata.setContentEncoding("UTF-8");
		metdata.setContentType(FileUtil.getContentType(ossUploadFile.getOssKey()));
		PutObjectResult result = null;

		if (object instanceof File) {
			putObjectRequest = new PutObjectRequest(bucketName, key, (File) object);
		} else if (object instanceof InputStream) {
			putObjectRequest = new PutObjectRequest(bucketName, key, (InputStream) object, metdata);
		} else if (object instanceof URL) {
			putObjectRequest = new PutObjectRequest(bucketName, key, ((URL) object).openStream(), metdata);
		} else {
			throw new Exception("未知文件！");
		}

		// 上传监听
		if (ossListener != null) {
			putObjectRequest.<PutObjectRequest> withProgressListener(ossListener);
		}

		result = ossClient.putObject(putObjectRequest);

		System.out.println(result.getETag());

		return getOssFileUrl(bucketName, key);
	}
	
	/**
	 * 创建虚拟文件夹
	 */
	public static boolean createDirectory(OSSClient ossClient, String bucketName,String directory){
		try{
			if(directory.startsWith("/") || directory.startsWith("\\"))
				directory=directory.substring(1);
			if(!directory.endsWith("/"))
				directory+="/";
			ossClient.putObject(bucketName, directory, new ByteArrayInputStream(new byte[0]));
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 下载文件
	 * 
	 * @param listener 进度监听器
	 * @param key 目录+文件名
	 */
	public static InputStream downloadFile(OSSClient ossClient, String bucketName, String key,
			OssListener listener) throws Exception {
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
		// 下载监听
		if (listener != null) {
			getObjectRequest.<GetObjectRequest> withProgressListener(listener);
		}
		OSSObject object = ossClient.getObject(getObjectRequest);
		if (object != null)
			return object.getObjectContent();
		else
			return null;
	}
	
	/**
	 * 删除文件
	 * @param key 目录+文件名
	 */
	public static boolean deleteFile(OSSClient ossClient, String bucketName, String key) {
		try {
			ossClient.deleteObject(bucketName, key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 根据文件夹获取其下的文件和文件夹.
	 * 
	 * @param ossFilePage 分页
	 * @return getObjectSummaries所有文件，getCommonPrefixes所有文件夹
	 */
	public static ObjectListing listObjects(OSSClient ossClient, String bucketName, String directory,
			OssFilePage ossFilePage) {
		if (ossFilePage == null)
			ossFilePage = new OssFilePage(100);
		if(directory.startsWith("/") || directory.startsWith("\\"))
			directory=directory.substring(1);
		if(!directory.endsWith("/"))
			directory+="/";
		ListObjectsRequest objectListRequest = new ListObjectsRequest();
		objectListRequest.setBucketName(bucketName);
		objectListRequest.setPrefix(directory);
		objectListRequest.setMarker(ossFilePage.getNextMarker());
		objectListRequest.setMaxKeys(ossFilePage.getMaxFile());
		objectListRequest.setDelimiter("/");
		ObjectListing objectList = ossClient.listObjects(objectListRequest);

		ossFilePage.setHasNext(objectList.isTruncated());
		List<String> history = ossFilePage.getHistoryMarker();
		String nextMarker = objectList.getNextMarker();
		if (!history.contains(nextMarker)) {
			history.add(nextMarker);
		}
		
		return objectList;
	}
	
	/** 
	 * 获取文件链接 
	 * @param key 目录+文件名
	 */
	public static String getOssFileUrl(String bucketName, String key) {
		StringBuilder sb = new StringBuilder();
		sb.append(ossHttp).append(bucketName).append(".");
		sb.append(endpoint).append("/").append(key);
		return sb.toString();
	}
	
}
