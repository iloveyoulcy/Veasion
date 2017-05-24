package veasion.dao;

/**
 * SQL连接符
 * @author zhuowei.luo
 * @date 2017/5/7 
 */
public enum JoinSql {
	/**等于，=*/
	eq,
	/**不等于，<>*/
	notEq,
	/**like*/
	like,
	/**大于，>*/
	greater,
	/**小于，<*/
	less,
	/**大于等于，>=*/
	greaterEq,
	/**小于等于，<=*/
	lessEq
}
