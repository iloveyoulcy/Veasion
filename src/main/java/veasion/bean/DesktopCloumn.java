package veasion.bean;

/**
 * 桌面ICON列
 * 
 * @author zhuowei.luo
 * @date 2017/5/20
 */
public class DesktopCloumn {
	
	/**
	 * show类型
	 * <br/>
	 * "常规","最大化","最小化","打开新窗体","不准最大化"
	 */
	public static final String[] showTypes={"常规","最大化","最小化","打开新窗体","不准最大化"};
	
	/**表名*/
	public static final String tableName="desktop_cloumn";
	/**id*/
	public static final String id="id";
	/**打开链接,int对应veasion_url的id*/
	public static final String url="url";
	/**标题*/
	public static final String title="title";
	/**图标,int对应veasion_url的id*/
	public static final String icon="icon";
	/**宽度*/
	public static final String width="width";
	/**高度*/
	public static final String height="height";
	/**show样式类型*/
	public static final String showType="show_type";
	/**状态*/
	public static final String status="status";
	/**创建时间*/
	public static final String createDate="create_date";
	
}
