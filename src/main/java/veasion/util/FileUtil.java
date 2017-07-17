package veasion.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.filechooser.FileSystemView;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class FileUtil {

	/**获取桌面路径*/
	public static String HOME_PATH = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
	
	/**
	 * 图片转base64
	 * @since 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 */
	public static String GetImageStr(String imgFilePath) {
		byte[] data = null;
		// 读取图片字节数组
		try {
			InputStream in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		// 返回Base64编码过的字节数组字符串
		return encoder.encode(data);
	}
	
	/**base64转图片*/
	public static boolean generateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			// 生成图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 根据base64字符串获取文件后缀和base64
	 * 
	 * @since data:image/png;base64,iVBCA...
	 * @return 0 格式[png],1 base64[iVBCA...]
	 */
	public static String[]	base64Type(String base64Str){
		//data:image/png;base64,iVB...
		String base=";base64,";
		int index=-1;
		if((index=base64Str.indexOf(base))!=-1){
			String type=base64Str.substring(0, index).replace("data:", "").trim();
			if(type.indexOf("/")!=-1){
				type=type.substring(type.indexOf("/")+1);
			}
			return new String[]{type,base64Str.substring(index+base.length())};
		}
		return new String[]{"text",base64Str};
	}
	
	/**文件转成byte数组*/
	public static byte[] fileToBetyArray(File file) {
		FileInputStream fileInputStream = null;
		byte[] bFile = null;
		try {
			bFile = new byte[(int) file.length()];
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileInputStream.close();
				bFile.clone();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bFile;
	}
	
}
