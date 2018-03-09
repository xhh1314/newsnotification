package cn.haiwai.newsnotification.manage;

import cn.haiwai.newsnotification.manage.exception.PageException;

/**
 * 第一个抽象page类，具体的下一页 上一页算法由子类去实现 要求从前台返回beginPage endPage currentPage几个字段
 * 
 * @author lihao
 * @date 2017年10月28日
 * @version 1.0
 */
public abstract class AbstractPage {
	protected int beginPage;
	protected int endPage;
	protected int currentPage;
	protected int totalNumber;
	protected int totalPage;
	/**
	 * 页码中间分界线
	 */
	protected int bound;

	/**
	 * 一个页面要展示几个页码，默认值10
	 */
	protected int eachPage;

	/**
	 * 每页显示的行数，默认10行
	 */
	protected int eachPageNumber;

	/**
	 * 传入数据库的起始行
	 */
	protected int beginNumber;

	/**
	 * 返回的行数
	 */
	protected int offset;

	/**
	 *
	 * @param totalNumber
	 * @param currentPage
	 */
	protected AbstractPage(int totalNumber, int currentPage) {
		this();
		this.totalNumber = totalNumber;
		this.currentPage = currentPage;
	}

	protected AbstractPage(int totalNumber, int currentPage, int eachPage, int eachPageNumber) {
		this.eachPage = eachPage;
		this.eachPageNumber = eachPageNumber;
		this.totalNumber = totalNumber;
		this.currentPage = currentPage;
	}

	protected AbstractPage(int currentPage, int beginPage, int endPage) {
		this();
		this.currentPage = currentPage;
		this.beginPage = beginPage;
		this.endPage = endPage;
	}

	/**
	 * 每页显示的行数使用默认 10 最多10个页码 初始化当前页码为1
	 */
	protected AbstractPage() {
		this.eachPageNumber = 10;
		this.eachPage = 10;
		this.currentPage = 1; // 初始化当前页为第一页
	}

	/**
	 * 工厂方法 获取page实例
	 * 
	 * @param totalNumber
	 * @param currentPage
	 * @param eachPage
	 * @param eachPageNumber
	 * @return
	 */
	public static synchronized AbstractPage getPageInstance(int totalNumber, int currentPage, int eachPage,
			int eachPageNumber) {
		return new PageImpl(totalNumber, currentPage, eachPage, eachPageNumber);
	}

	public static synchronized AbstractPage getPageInstance(int totalNumber, int currentPage) {
		return new PageImpl(totalNumber, currentPage);
	}

	/**
	 * 如果beginPage endPage未知，则传入0
	 * 
	 * @param currentPage
	 * @param beginPage
	 * @param endPage
	 * @return
	 */
	public static synchronized AbstractPage getPageInstance(int currentPage, int beginPage, int endPage) {
		return new PageImpl(currentPage, beginPage, endPage);
	}

	/**
	 * 获取下一页
	 * 
	 * @return
	 */
	public final AbstractPage getNextPage() {
		initialPageNumber();
		nextPage();
		caculateBeginAndOffset();
		return this;

	}

	/**
	 * 获取上一页
	 * 
	 * @return
	 */
	public final AbstractPage getPreviousPage() {
		initialPageNumber();
		previousPage();
		caculateBeginAndOffset();
		return this;

	}

	/**
	 * 获取指定页
	 * 
	 * @return
	 */
	public final AbstractPage getAssignPage() {
		initialPageNumber();
		currentPage();
		caculateBeginAndOffset();

		return this;
	}

	/**
	 * 初始化开始和结束页码
	 */
	protected void initialPageNumber() {
		if (eachPageNumber == 0 || eachPage == 0)
			throw new PageException("初始化page错误！eachPageNumber 或 eachPage 不能为0！");
		if (totalNumber % eachPageNumber == 0)// 算出总页数
			this.totalPage = totalNumber / eachPageNumber;
		else
			this.totalPage = totalNumber / eachPageNumber + 1;
		// 除以2再1
		this.bound = this.eachPage / 2 + 1;

		if (beginPage * endPage != 0) {// 如果page已经被实例化过了，不再重新计算beginPage endPage
			return;
		}
		if (totalPage <= eachPage) {
			this.beginPage = 1;
			this.endPage = totalPage;
		} else {
			this.beginPage = 1;
			this.endPage = this.eachPage;
		}

	}

	/**
	 * 点击上一页算法 计算出currentPage、beginPage、endPage
	 */
	protected abstract void previousPage();

	/**
	 * 点击下一页算法 计算出currentPage、beginPage、endPage
	 */
	protected abstract void nextPage();

	/**
	 * 点击指定页算法 直接根据当前传入的页码计算出currentPage、beginPage、endPage
	 */
	protected abstract void currentPage();

	/**
	 * 计算数据库分页查询时需要的起始参数
	 */
	protected void caculateBeginAndOffset() {
		this.beginNumber = this.currentPage * eachPageNumber + 1;
		this.offset = eachPageNumber;
		if ((beginNumber + offset) > totalNumber)
			this.offset = totalNumber - beginNumber + 1;
	}

	public int getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getBeginNumber() {

		return beginNumber;
	}

	public int getOffset() {
		return offset;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public int getTotalPage() {
		return totalPage;
	}

}
