package veasion.util;

/**
 * 分页Model
 * @author zhuowei.luo
 * @date 2017/5/7
 */
public class PageModel {
	
	public final static String INDEX_PAGE="indexPage";
	public final static String PAGE_COUNT="pageCount";
	
	/**当前页数*/
	private int indexPage=1;
	/**最大页数*/
	private int maxPage;
	/**每页数量*/
	private int pageCount=10;
	/**总结果*/
	private int count;
	
	public PageModel(){};
	
	/**
	 * 分页Model
	 * @param indexPage 当前页数
	 * @param pageCount 每页数量
	 */
	public PageModel(int indexPage,int pageCount){
		this.indexPage=indexPage;
		this.pageCount=pageCount;
	};
	
	/**上一页*/
	public int getTopPage(){
		return indexPage>1?indexPage-1:1;
	}
	
	/**下一页*/
	public int getNextPage(){
		return indexPage<maxPage?indexPage+1:maxPage;
	}
	
	/**当前页数*/
	public int getIndexPage() {
		return indexPage;
	}
	
	/**当前页数*/
	public void setIndexPage(int indexPage) {
		this.indexPage = indexPage;
	}
	
	/**最大页数*/
	public int getMaxPage() {
		return (count-1)/pageCount+1;
	}
	
	/**每页数量*/
	public int getPageCount() {
		return pageCount;
	}
	
	/**每页数量*/
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	/**总结果*/
	public int getCount() {
		return count;
	}
	
	/**总结果*/
	public void setCount(int count) {
		maxPage=(count-1)/pageCount+1;
		this.count = count;
	}
	
	@Override
	public String toString() {
		return "PageInfo [indexPage=" + indexPage + ", maxPage=" + maxPage + ", pageCount=" + pageCount + ", count="
				+ count + "]";
	} 
	
}
