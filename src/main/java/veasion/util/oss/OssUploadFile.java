package veasion.util.oss;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import veasion.util.VeaUtil;

/**
 * Oss File.
 * 
 * @author zhuowei.luo
 */  
public class OssUploadFile {
	
	private String directory;
	
	private String fileName;
	
	private File file;
	
	private InputStream input;
	
	private URL url;
	
	private Object object;
	
	/**根据文件上传，fileName可空*/
	public OssUploadFile(File file,String directory,String fileName){
		this(directory, fileName);
		this.file=file;
		this.object=this.file;
		if(VeaUtil.isNullEmpty(fileName))
			this.fileName=file.getName();
	}
	
	/**根据IO流上传*/
	public OssUploadFile(InputStream input,String directory,String fileName){
		this(directory, fileName);
		this.input=input;
		this.object=this.input;
	}
	
	/**根据Url链接上传*/
	public OssUploadFile(URL url,String directory,String fileName){
		this(directory, fileName);
		this.url=url;
		this.object=this.url;
	}
	
	private OssUploadFile(String directory,String fileName){
		// 目录不能以/开头
		if(directory.startsWith("/") || directory.startsWith("\\")){
			directory=directory.substring(1);
		}
		// 目录必须以/结尾
		if (!directory.endsWith("/")) {
			directory += "/";
		}
		// 文件名不能包含/，有则替换为_
		if (fileName!=null && fileName.indexOf("/") != -1) {
			fileName = fileName.replace("/", "_");
		}
		this.directory=directory;
		this.fileName=fileName;
	}
	
	public String getOssKey() {
		return directory+fileName;
	}
	
	public Object getObject() {
		return object;
	}
	
}
