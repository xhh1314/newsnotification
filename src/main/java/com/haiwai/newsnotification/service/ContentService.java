package com.haiwai.newsnotification.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.haiwai.newsnotification.dao.ContentDao;
import com.haiwai.newsnotification.dao.bean.ContentDO;
import com.haiwai.newsnotification.manage.AbstractPage;

/**
 * 处理content业务的类
 * @author lihao
 * @date 2017年10月29日
 * @version 1.0
 */
@Service
@Scope("singleton")
public class ContentService {

	@Autowired
	private ContentDao contentDao;

	@Transactional
	public ContentBO saveContent(ContentBO content) {
		// TODO Auto-generated method stub
		if ((content.getCid() == null || content.getCid() == 0) || contentDao.getContent(content.getCid()) == null) {
			ContentDO c = contentDao.saveContent(new ContentDO(content));
			return c == null ? null : new ContentBO(c);
		} else {
			// 如果已经存在，则更新
			if (contentDao.updateContent(new ContentDO(content)) == 0)
				return null;
			else
				return new ContentBO(contentDao.getContent(content.getCid()));
		}
	}

	/**
	 * 处理分页查询Content
	 * @param page
	 * @param action
	 * @return
	 */
	public List<ContentBO> listContentsByPage(AbstractPage page, String action) {
		// TODO Auto-generated method stub
		Integer count = contentDao.countContent();
		if (count == null)
			return null;
		page.setTotalNumber(count);
		//判定下是什么操作
		if ("previous".equals(action))
			page = page.getPreviousPage();
		else if ("next".equals(action))
			page = page.getNextPage();
		else if ("assign".equals(action))
			page = page.getAssignPage();
		List<ContentDO> contentDOs = contentDao.listByLimit(page.getBeginNumber(), page.getOffset());
		List<ContentBO> contentBOs = new LinkedList<ContentBO>();
		for (ContentDO e : contentDOs) {
			contentBOs.add(new ContentBO(e));
		}
		if (contentBOs.isEmpty())
			return null;
		Collections.sort(contentBOs);
		return contentBOs;
	}

	

}
