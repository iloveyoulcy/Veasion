package veasion.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import veasion.constant.Constant;

/**
 * 文件Util
 * 
 * @author zhuowei.luo
 * @date 2017/5/24
 */
public class FileUtil {

	/**获取单个上传文件，返回文件名字*/
	public String upFile(HttpServletRequest req,Long maxSize,String webPath,String fileNamePrefix) throws Exception {
		// 从request当中获取流信息
		InputStream fileSource = req.getInputStream();
		String tempFileName = Constant.HOME_PATH + "/VeasionTempFile";
		// tempFile指向临时文件
		File tempFile = new File(tempFileName);
		// outputStram文件输出流指向这个临时文件
		FileOutputStream outputStream = new FileOutputStream(tempFile);
		byte b[] = new byte[1024];
		int n;
		while ((n = fileSource.read(b)) != -1) {
			outputStream.write(b, 0, n);
		}
		// 关闭输出流、输入流
		outputStream.close();
		fileSource.close();
		
		if(tempFile.length()>=maxSize){
			tempFile.delete();
			throw new Exception("文件太大！");
		}
		
		// 获取上传文件的名称
		RandomAccessFile randomFile = new RandomAccessFile(tempFile, "r");
		String str2 = randomFile.readLine();
		// 编码转换
		str2 = new String(str2.getBytes("8859_1"), "utf-8");
		String str = randomFile.readLine();
		str = new String(str.getBytes("8859_1"), "utf-8");
		int beginIndex = str.lastIndexOf("=") + 2;
		int endIndex = str.lastIndexOf("\"");
		String filename = str.substring(beginIndex, endIndex);
		if(!filename.startsWith(fileNamePrefix))
			filename=fileNamePrefix+filename;
		// 重新定位文件指针到文件头
		randomFile.seek(0);
		long startPosition = 0;
		int i = 1;
		// 获取文件内容 开始位置
		while ((n = randomFile.readByte()) != -1 && i <= 4) {
			if (n == '\n') {
				startPosition = randomFile.getFilePointer();
				i++;
			}
		}
		startPosition = randomFile.getFilePointer() - 1;
		// 获取文件内容 结束位置
		randomFile.seek(randomFile.length());
		long endPosition = randomFile.getFilePointer();
		int j = 1;
		while (endPosition >= 0 && j <= 2) {
			endPosition--;
			randomFile.seek(endPosition);
			if (randomFile.readByte() == '\n') {
				j++;
			}
		}
		endPosition = endPosition - 1;
		// 设置保存上传文件的路径
		String realPath = req.getServletContext().getRealPath("/") + webPath;
		System.out.println("上传位置："+realPath);
		File fileupload = new File(realPath);
		if (!fileupload.exists()) {
			fileupload.mkdir();
		}
		File saveFile = new File(realPath, filename);
		RandomAccessFile randomAccessFile = new RandomAccessFile(saveFile, "rw");
		// 从临时文件当中读取文件内容（根据起止位置获取）
		randomFile.seek(startPosition);
		while (startPosition < endPosition) {
			randomAccessFile.write(randomFile.readByte());
			startPosition = randomFile.getFilePointer();
		}
		// 关闭输入输出流、删除临时文件
		randomAccessFile.close();
		randomFile.close();
		tempFile.delete();
		return filename;
	}
	
	/**下载单个文件*/
	public void downloadFile(HttpServletRequest req, HttpServletResponse resp, String webFilePath) throws Exception {
		String webPath = webFilePath.substring(0, webFilePath.lastIndexOf("/")+1);
		String fileName = webFilePath.replace(webPath, "");
		System.out.println(webFilePath + "\n" + webPath + "\n" + fileName);
		// 进行编码的转换，因为不能识别中文
		resp.setHeader("content-type", "text/html;charset=UTF-8");
		// ServletContext
		String path = req.getServletContext().getRealPath("/") + webPath;// "images/"
		File file = new File(path + fileName);
		if (file.exists()) {
			String value = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
			resp.setContentType("application/x-msdownload");
			resp.setHeader("Content-Disposition", "attachment;filename=\"" + value + "\"");
			InputStream inputStream = new FileInputStream(file);
			ServletOutputStream outputStream = resp.getOutputStream();
			byte b[] = new byte[1024];
			int n;
			while ((n = inputStream.read(b)) != -1) {
				outputStream.write(b, 0, n);
			}
			outputStream.close();
			inputStream.close();
		} else {
			throw new Exception("文件不存在！");
		}
	}
	
}
