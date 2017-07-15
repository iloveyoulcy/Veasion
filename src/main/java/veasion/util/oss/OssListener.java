package veasion.util.oss;

import java.io.Serializable;

import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;

/**
 * Oss文件上传/下载进度监听器.
 * 
 * @author zhuowei.luo
 */
public class OssListener implements ProgressListener,Serializable{

	private long bytesWritten = 0;
	private long totalBytes = -1;
	private Boolean succed = null;
	
	@Override
	public void progressChanged(ProgressEvent progressEvent) {
		long bytes = progressEvent.getBytes();
		ProgressEventType eventType = progressEvent.getEventType();
		switch (eventType) {
		case TRANSFER_STARTED_EVENT:
			System.out.println("开始上传...");
			break;
			
		case REQUEST_CONTENT_LENGTH_EVENT:
			this.totalBytes = bytes;
			break;

		case REQUEST_BYTE_TRANSFER_EVENT:
			this.bytesWritten += bytes;
			break;

		case TRANSFER_COMPLETED_EVENT:
			this.succed = true;
			break;

		case TRANSFER_FAILED_EVENT:
			this.succed = false;
			break;
		default:
			break;
		}
	}

	/** 
	 * 是否成功！
	 * @since 没有结果时返回 null，
	 * 		     否则成功 true，失败 false
	 */
	public Boolean isSucced() {
		return succed;
	}
	
	public Boolean getSucced() {
		return this.isSucced();
	}
	
	/** 已上传字节数 */
	public long getBytesWritten() {
		return bytesWritten;
	}

	/** 总字节数 */
	public long getTotalBytes() {
		return totalBytes;
	}
	
	/**获取百分比*/
	public String getBfb(){
		return this.getBfbDouble()*100+"%";
	}
	
	/**获取百分比小数(保留四位)*/
	public Double getBfbDouble(){
		return Double.parseDouble(new java.text.DecimalFormat("#.0000").format((double)bytesWritten/totalBytes));
	}
}