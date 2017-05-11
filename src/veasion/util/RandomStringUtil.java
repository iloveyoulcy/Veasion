package veasion.util;

import java.util.Random;
import java.util.UUID;

/**
 * 生成随机字符串
 * @author zhuowei.luo
 * @date 2017/5/11
 */
public class RandomStringUtil {
	
	/**
	 * 获取随机字符串
	 * @param type 类型
	 * @param len 长度
	 */
	public static String getRandom(final RandomType type,int len){
		StringBuilder charSb=null;
		Random r=new Random();
		StringBuilder sb=new StringBuilder();
		switch (type) {
			case digital:
				charSb=new StringBuilder("0123456789");
				break;
			case englishLower:
				charSb=new StringBuilder("qwertyuiopasdfghjklzxcvbnm");
				break;
			case englishUpper:
				charSb=new StringBuilder("QWERTYUIOPASDFGHJKLZXCVBNM");
				break;
			case english:
				charSb=new StringBuilder("qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM");
				break;
			case digitalEnglishLower:
				charSb=new StringBuilder("0123456789qwertyuiopasdfghjklzxcvbnm");
				break;
			case digitalEnglishUpper:
				charSb=new StringBuilder("0123456789QWERTYUIOPASDFGHJKLZXCVBNM");
				break;
			case digitalEnglish:
				charSb=new StringBuilder("0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM");
				break;
			case uuid:
				charSb=null;
				break;
		}
		if(charSb!=null){
			int charLen=charSb.length();
			for (int i = 0; i <len; i++) {
				sb.append(charSb.charAt(r.nextInt(charLen)));
			}
		}else{
			String uuid=UUID.randomUUID().toString().replace("-", "");
			sb.append(len>=uuid.length()?uuid:uuid.substring(0, len));
		}
		
		return sb.toString();
	}
	
	
	enum RandomType{
		/**纯数字*/
		digital,
		/**英文小写*/
		englishLower,
		/**英文大写*/
		englishUpper,
		/**英文，大小写全有*/
		english,
		/**数字+小写英文*/
		digitalEnglishLower,
		/**数字+大写英文*/
		digitalEnglishUpper,
		/**数字+英文（包含大小写）*/
		digitalEnglish,
		/**UUID，去掉'-'，32位*/
		uuid
	}
	
	public static void main(String[] args) {
		System.out.println(getRandom(RandomType.english, 33));
	}
}