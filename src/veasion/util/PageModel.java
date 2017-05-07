package veasion.util;

/**
 * ��ҳModel
 * @author zhuowei.luo
 * @date 2017/5/7
 */
public class PageModel {
	
	/**��ǰҳ��*/
	private int indexPage=1;
	/**���ҳ��*/
	private int maxPage;
	/**ÿҳ����*/
	private int pageCount=10;
	/**�ܽ��*/
	private int count;
	
	public PageModel(){};
	
	public PageModel(int indexPage,int pageCount){
		this.indexPage=indexPage;
		this.pageCount=pageCount;
	};
	/**��ǰҳ��*/
	public int getIndexPage() {
		return indexPage;
	}
	/**��ǰҳ��*/
	public void setIndexPage(int indexPage) {
		this.indexPage = indexPage;
	}
	/**���ҳ��*/
	public int getMaxPage() {
		return (count-1)/pageCount+1;
	}
	/**ÿҳ����*/
	public int getPageCount() {
		return pageCount;
	}
	/**ÿҳ����*/
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	/**�ܽ��*/
	public int getCount() {
		return count;
	}
	/**�ܽ��*/
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
