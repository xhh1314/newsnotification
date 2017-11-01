package newsnotification.dao;

import cn.haiwai.newsnotification.manage.AbstractPage;

public class PageTest {
	public static void main(String[] args) {
		AbstractPage page = AbstractPage.getPageInstance(101, 11, 5, 10);
		page.getAssignPage();
		page.getNextPage();
		page.getPreviousPage();
		page.getPreviousPage();
		page.getPreviousPage();
		page.getPreviousPage();
		page.getPreviousPage();

	}

}
