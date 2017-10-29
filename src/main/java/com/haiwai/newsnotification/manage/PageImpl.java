package com.haiwai.newsnotification.manage;

/**
 * 始终保持当前页码在中间的算法
 * 
 * @author lihao
 * @date 2017年10月28日
 * @version 1.0
 */
public class PageImpl extends AbstractPage {

	@Override
	protected void previousPage() {
		// TODO Auto-generated method stub
		if (currentPage <= 1)
			return;
		this.currentPage--;
		currentPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.haiwai.newsnotification.manage.AbstractPage#nextPage()
	 * 下一页算法和上一页算法思路一致,简直完美
	 */
	@Override
	protected void nextPage() {
		// TODO Auto-generated method stub
		if (currentPage >= totalPage)
			return;
		currentPage++;
		currentPage();

	}

	@Override
	protected void currentPage() {
		// TODO Auto-generated method stub
		// 如果总页数比页码数还小，不作任何处理
		if (totalPage <= eachPage) {
			return;
		}
		// 当前页码比1\2页码还小的情况
		if (currentPage <= bound) {
			beginPage = 1;
			endPage = eachPage;
			return;
		}
		// 如果当前页码在最后几页的情况
		if (currentPage + bound - 1 >= totalPage) {
			this.endPage = totalPage;
			this.beginPage = totalPage - eachPage + 1;
			return;
		}
		this.beginPage = this.currentPage - bound + 1;
		this.endPage = beginPage + eachPage - 1;

	}
	
	

	/* 
	 * 重写父类方法  每次查询出最新的几条数据
	 */
	@Override
	protected void caculateBeginAndOffset() {
		// TODO Auto-generated method stub
	    this.beginNumber=totalNumber-currentPage*eachPageNumber+1;
		this.offset=eachPageNumber;
		if(beginNumber<=0) {
			this.offset=eachPageNumber+beginNumber-1;
			beginNumber=1;
		}
	}

	public PageImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PageImpl(int totalNumber, int currentPage, int eachPage, int eachPageNumber) {
		super(totalNumber, currentPage, eachPage, eachPageNumber);
		// TODO Auto-generated constructor stub
	}

	public PageImpl(int totalNumber, int currentPage) {
		super(totalNumber, currentPage);
		// TODO Auto-generated constructor stub
	}

	public PageImpl(int currentPage, int beginPage, int endPage) {
		super(currentPage, beginPage, endPage);
		// TODO Auto-generated constructor stub
	}

}
