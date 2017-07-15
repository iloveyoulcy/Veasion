package veasion.bean;

/**
 * 模拟桌面样式 
 * 
 * @author zhuowei.luo
 * @date 2017/5/20
 */
public class DesktopStyle {
	
	/**使用状态*/
	public static final int STATUS_USE=1;
	/**停用状态*/
	public static final int STATUS_STOP=0;
	
	/**表名*/
	public static final String tableName="desktop_style";
	/**id*/
	public static final String id="id";
	/**名称*/
	public static final String name="name";
	/**作者*/
	public static final String author="author";
	/**背景图,int对应veasion_url的id*/
	public static final String bgimg="bgimg";
	/**列宽*/
	public static final String cloumnWidth="cloumn_width";
	/**列高*/
	public static final String cloumnHeight="cloumn_height";
	/**icon列*/
	public static final String cloumnIds="cloumn_ids";
	/**创建时间*/
	public static final String createDate="create_date";
	/**状态*/
	public static final String status="status";
	
}
