package com.haiwai.newsnotification.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.haiwai.newsnotification.dao.ContentDao;
import com.haiwai.newsnotification.dao.bean.ContentDO;
import com.haiwai.newsnotification.manage.AbstractPage;
import com.haiwai.newsnotification.manage.util.TimeTransfer;

/**
 * 处理content业务的类
 * 
 * @author lihao
 * @date 2017年10月29日
 * @version 1.0
 */
@Service
@Scope("singleton")
public class ContentService {

	private static final Logger logger = LoggerFactory.getLogger(ContentService.class);

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
	 * 
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
		// 判定下是什么操作
		if ("previous".equals(action))
			page = page.getPreviousPage();
		else if ("next".equals(action))
			page = page.getNextPage();
		else if ("assign".equals(action))
			page = page.getAssignPage();
		List<ContentDO> contentDOs = contentDao.listByLimit(page.getBeginNumber(), page.getOffset());
		if (contentDOs.isEmpty())
			return null;
		List<ContentBO> contentBOs = transferfromContentDO(contentDOs);
		Collections.sort(contentBOs);
		return contentBOs;
	}

	/**
	 * 根据id取的一个content实例
	 * 
	 * @param id
	 * @return
	 */
	public ContentBO getContent(int id) {
		if (id == 0)
			return null;
		ContentDO contentDo = contentDao.getContent(id);
		if (contentDo == null)
			return null;
		return new ContentBO(contentDo);
	}

	public boolean deleteContent(String cid) {
		// TODO Auto-generated method stub
		if (!StringUtils.hasLength(cid))
			return false;
		try {
			contentDao.deleteContent(Integer.parseInt(cid));
			return true;
		} catch (Exception e) {
			logger.error("删除content失败,id={}{}", cid, e);
			return false;
		}
	}

	/**
	 * @return 返回某天的数据 ,如果没有则返回null
	 */
	public List<ContentBO> listContentsByDate(String date) {
		// TODO Auto-generated method stub
		List<ContentDO> contentsDO = contentDao.listByDate(date);
		if (contentsDO == null || contentsDO.isEmpty())
			return null;
		List<ContentBO> contents = transferfromContentDO(contentsDO);
		Collections.sort(contents);
		return parseHtml(contents);
		
	}

	/**
	 * 把DO集合转换成BO集合
	 * 
	 * @param contents
	 * @return
	 */
	private List<ContentBO> transferfromContentDO(List<ContentDO> contents) {
		List<ContentBO> contentBOs = new LinkedList<ContentBO>();
		for (ContentDO e : contents) {
			contentBOs.add(new ContentBO(e));
		}
		return contentBOs;
	}

	/**
	 * @return 默认返回当天的数据，如果当天没有数据，则返回最近一周数据，最近一周都没有数据，则返回null
	 */
	public List<ContentBO> listContents() {
		List<ContentBO> contents = listContentsByDate(TimeTransfer.getToday());
		if (contents != null)
			return contents;
		// 获取最近7天数据
		List<ContentDO> contentsDO = contentDao.listByRecentSevenDay();
		if (contentsDO == null || contentsDO.isEmpty())
			return null;
		contents = transferfromContentDO(contentsDO);
		Collections.sort(contents);
		return parseHtml(contents);

	}
	
	/**
	 * 前台列表只显示一部分正文数据，需要先解析出html文本，再截取前部分的数据
	 * @param contents
	 * @return
	 */
	private final int  len=120;
	private List<ContentBO> parseHtml(List<ContentBO> contents){
		 //由于是列表，正文不全部显示，只截取出其中一部分
		for (ContentBO cb : contents) {
			// 使用jsoup解析html
			Document doc = Jsoup.parse(cb.getContent());
			// 获取出所有的字符串
			String str = doc.text();
			// 替换掉换行和空格
			str.replaceAll("\\s\\n\\r", "");
			//再截取出来前len字符
			if (str.length() > len) {
				str = str.substring(0, len);
			} else {
				str = str.substring(0, str.length());
			}
			//补上点点点
			str = str + "...";
			cb.setContent(str);

		}
		return contents;
	}

}
