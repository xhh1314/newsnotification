package newsnotification.dao;

import com.haiwai.newsnotification.manage.AbstractPage;

public class PageTest {
	public static void main(String[] args) {
		AbstractPage page=AbstractPage.getPageInstance(205, 20, 5, 10);
		page.getNextPage();
		page.getPreviousPage();
		page.getPreviousPage();
		page.getPreviousPage();
		page.getPreviousPage();
		page.getPreviousPage();
		
	}

}
