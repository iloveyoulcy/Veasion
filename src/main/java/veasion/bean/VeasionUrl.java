package veasion.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import veasion.dao.JoinSql;
import veasion.dao.Where;
import veasion.service.BeanService;
import veasion.service.impl.MysqlServieImpl;
import veasion.util.VeaUtil;

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
	/**文件链接*/
	public static final int TYPE_FILE=4;
	/**默认URL*/
	public static final String DEFAULT_URL="#";
	
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
	
	/**
	 * 填充url
	 * @since 根据指定key填充url
	 */
	public static void fillUrlForMap(final Map<String, Object> map,final String ...keys){
		if(!VeaUtil.isNullEmpty(map) && !VeaUtil.isNullEmpty(keys)){
			for (String key : keys) {
				Object id=map.getOrDefault(key, null);
				if(id==null){
					map.put(key, DEFAULT_URL);
				}else{
					BeanService service=new MysqlServieImpl(tableName);
					Map<String, Object> mapData=service.QueryOnly(VeasionUrl.id, id);
					if(VeaUtil.isNullEmpty(mapData))
						map.put(key,DEFAULT_URL);
					else
						map.put(key, mapData.getOrDefault(VeasionUrl.url, DEFAULT_URL));
				}
			}
		}
	}
	
	/**
	 * 填充url
	 * @since 根据指定key填充url
	 */
	public static void fillUrlForMap(final List<Map<String, Object>> mapList,final String ...key){
		if(VeaUtil.isNullEmpty(mapList)) return;
		mapList.forEach((map)->{
			fillUrlForMap(map,key);
		});
	}
	
}
