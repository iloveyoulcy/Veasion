package veasion.bean;

/**
 * url表
 * 
 * @author zhuowei.luo
 * @date 2017/7/15
 */
public class VeasionUrl {
	
	/**访问链接*/
	public static final int TYPE_URL=1;
	/**桌面背景URL*/
	public static final int TYPE_STYLE=2;
	/**ICON图标URL*/
	public static final int TYPE_ICON=3;
	
	/**表名*/
	public static final String tableName="veasion_url";
	/**id*/
	public static final String id="id";
	/**名称*/
	public static final String name="name";
	/**url链接*/
	public static final String url="url";
	/**类型*/
	public static final String type="type";
	/**创建时间*/
	public static final String createDate="create_date";
	
}
