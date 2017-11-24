package cn.haiwai.newsnotification.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import javax.transaction.Transactional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import cn.haiwai.newsnotification.dao.ContentDao;
import cn.haiwai.newsnotification.dao.TagDao;
import cn.haiwai.newsnotification.dao.bean.ContentDO;
import cn.haiwai.newsnotification.dao.bean.TagDO;
import cn.haiwai.newsnotification.manage.AbstractPage;
import cn.haiwai.newsnotification.manage.elasticsearch.ElasticSearchConduct;
import cn.haiwai.newsnotification.manage.util.TimeTransfer;
import cn.haiwai.newsnotification.web.controller.ContentVO;

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
	@Autowired
	private TagDao tagDao;
	@Autowired
	private ElasticSearchConduct esc;

	private static final String DOCUMENT = "newsnotification";
	private static final String TYPE = "article";

	/**
	 * 保存content的逻辑
	 * 
	 * @param content
	 * @return contentBO
	 * @throws IOException
	 */
	@Transactional
	public ContentBO saveContent(ContentVO contentVO) throws IOException {
		ContentBO content = new ContentBO(contentVO);
		ContentDO newContent = contentDao.saveContent(new ContentDO(content));
		contentVO.setTagArray(contentVO.getTags().split("\\s+"));
		contentVO.setCid(newContent.getId());
		if (!esc.insertIndex(DOCUMENT, TYPE, contentVO))
			return null;
		return newContent == null ? null : new ContentBO(newContent);
	}

	/**
	 * 处理分页查询Content
	 * 
	 * @param page
	 * @param action
	 * @return
	 */
	public List<ContentVO> listContentsByPage(AbstractPage page, String action) {
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
		// List<ContentBO> contentBOs = transferfromContentDO(contentDOs);
		List<ContentVO> contentVOs = transferContentVOfromContentDO(contentDOs);
		Collections.sort(contentVOs);
		return contentVOs;
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
			logger.info("删除了一遍文章id{}", cid);
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

	private List<ContentVO> transferContentVOfromContentDO(List<ContentDO> contents) {
		List<ContentVO> ContentVOs = new LinkedList<ContentVO>();
		for (ContentDO e : contents) {
			ContentVOs.add(new ContentVO(e));
		}
		return ContentVOs;
	}

	/**
	 * 把TagDO集合转换成TagBO集合
	 * 
	 * @param contents
	 * @return
	 */
	private List<TagBO> transferfromTagDO(List<TagDO> contents) {
		List<TagBO> TagBOs = new LinkedList<TagBO>();
		for (TagDO e : contents) {
			TagBOs.add(new TagBO(e));
		}
		return TagBOs;
	}

	/**
	 * @return 默认返回返回最近一周数据，最近一周都没有数据，则返回null
	 */
	public List<ContentBO> listContents() {
		// 获取最近7天数据
		List<ContentDO> contentsDO = contentDao.listByRecentSevenDay();
		if (contentsDO == null || contentsDO.isEmpty())
			return null;
		List<ContentBO> contents = transferfromContentDO(contentsDO);
		Collections.sort(contents);
		return parseHtml(contents);

	}

	/**
	 * 前台列表只显示一部分正文数据，需要先解析出html文本，再截取前部分的数据
	 * 
	 * @param contents
	 * @return
	 */
	private final int LEN = 120;

	private List<ContentBO> parseHtml(List<ContentBO> contents) {
		// 由于是列表，正文不全部显示，只截取出其中一部分
		for (ContentBO cb : contents) {
			// 使用jsoup解析html
			Document doc = Jsoup.parse(cb.getContent());
			// 获取出所有的字符串
			String str = doc.text();
			// 替换掉换行和空格
			str.replaceAll("\\s\\n\\r", "");
			// 再截取出来前len字符
			if (str.length() > LEN) {
				// 截取再补上...
				str = str.substring(0, LEN) + "...";
			}
			cb.setContent(str);

		}
		return contents;
	}

	/**
	 * 根据关键字搜索
	 * 
	 * @param key
	 * @return
	 */
	public List<ContentBO> listContentsByKey(String key) {
		// TODO Auto-generated method stub
		List<ContentDO> contentsDO = contentDao.listByKey(key);
		if (contentsDO == null || contentsDO.isEmpty())
			return null;
		List<ContentBO> contents = transferfromContentDO(contentsDO);
		Collections.sort(contents);
		return parseHtml(contents);
	}

	public List<TagBO> listAllTag() {
		List<TagDO> tags = tagDao.listAll();
		if (tags == null || tags.isEmpty())
			return null;
		return transferfromTagDO(tags);
	}

	@Transactional
	public boolean updateContentStatus(int cid, int status) {
		// TODO Auto-generated method stub
		if (contentDao.updateContentStatus(cid, status) == 1)
			return true;
		else
			return false;

	}

	public boolean updateTag(String tid, String name) {
		TagDO tag = tagDao.getTagById(Integer.valueOf(tid));
		tag.setName(name);
		return tagDao.save(tag) != null ? true : false;

	}

	public boolean deleteTag(String tid) {
		// TODO Auto-generated method stub
		tagDao.deleteById(Integer.valueOf(tid));
		return true;
	}

	/**
	 * 根据多个查询条件查询
	 * 
	 * @param word
	 * @param date
	 * @param tag
	 * @return
	 * @throws IOException
	 */
	public List<ContentBO> searchByArgus(String word, String date, String tag) throws IOException {
		// 如果传入的参数为空，则调用listContens方法，查询最近一周或者一天的数据
		if (!StringUtils.hasText(word) && !StringUtils.hasText(date) && !StringUtils.hasText(tag))
			return listContents();
		List<ContentDO> contentDOs = searchByArgusActionFromES(word, date, tag);
		if (contentDOs == null || contentDOs.isEmpty())
			return null;
		List<ContentBO> contents = transferfromContentDO(contentDOs);
		return parseHtml(contents);
	}

	/**
	 * 根据多个维度查询，涉及关键字的查询,从ElasticSearch搜索结果
	 * 
	 * @param word
	 * @param date
	 * @param tag
	 * @return
	 * @throws IOException
	 */
	private List<ContentDO> searchByArgusActionFromES(String word, String date, String tag) throws IOException {
		String[] ids = null;
		List<ContentDO> contents = null;
		if (StringUtils.hasText(word) && StringUtils.hasText(date) && StringUtils.hasText(tag))
			contents = contentDao
					.listByIdArray(ids = esc.searchIndexByKeyAndDateAndTag(DOCUMENT, TYPE, word, date, tag));
		else if (!StringUtils.hasText(word) && StringUtils.hasText(date) && StringUtils.hasText(tag))
			contents = contentDao.listByDateAndTag(date, tag);
		else if (!StringUtils.hasText(date) && StringUtils.hasText(word) && StringUtils.hasText(tag))
			contents = contentDao.listByIdArray(ids = esc.searchIndexByKeyAndTag(DOCUMENT, TYPE, word, tag));
		else if (!StringUtils.hasText(tag) && StringUtils.hasText(word) && StringUtils.hasText(date))
			contents = contentDao.listByIdArray(ids = esc.searchIndexByKeyAndDate(DOCUMENT, TYPE, word, date));
		else if (StringUtils.hasText(word))
			contents = contentDao.listByIdArray(ids = esc.searchIndexByKey(DOCUMENT, TYPE, word));
		else if (StringUtils.hasText(date))
			contents = contentDao.listByDate(date);
		else if (StringUtils.hasText(tag))
			// 直接使用标签进行分类查询时，最多返回200条数据，以防数据太多
			contents = contentDao.listByTagAndLimit(tag, 0, 200);
		if(contents==null || contents.isEmpty())
			return null;
		if (ids == null || ids.length==0) {
			//降序排列
			Collections.sort(contents, new Comparator<ContentDO>() {
				@Override
				public int compare(ContentDO o1, ContentDO o2) {
					if (o1.getId() == o2.getId())
						return 0;
					return o1.getId() > o2.getId() ? -1 : 1;
				}

			});
			return contents;
		}else{
		List<ContentDO> contentSort=new ArrayList<ContentDO>(contents.size());
		for(String id:ids){
			for(ContentDO e:contents){
				if(Integer.parseInt(id)==e.getId()){
					contentSort.add(e);
					break;
				}
			}
			
		}
		return contentSort;
		}
	}

	/**
	 * 以时间 标签 关键字为维度进行查询，分析关键字是否为空，来决定使用哪种查询 直接从数据库查询实现
	 * 
	 * @param word
	 * @param date
	 * @param tag
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<ContentDO> searchByArgusAction(String word, String date, String tag) {
		if (StringUtils.hasText(word) && StringUtils.hasText(date) && StringUtils.hasText(tag))
			return contentDao.listByKeyAndDateAndTag(word, date, tag);
		if (!StringUtils.hasText(word) && StringUtils.hasText(date) && StringUtils.hasText(tag))
			return contentDao.listByDateAndTag(date, tag);
		if (!StringUtils.hasText(date) && StringUtils.hasText(word) && StringUtils.hasText(tag))
			return contentDao.ListByKeyAndTag(word, tag);
		if (!StringUtils.hasText(tag) && StringUtils.hasText(word) && StringUtils.hasText(date))
			return contentDao.listByKeyAndDate(word, date);
		if (StringUtils.hasText(word))
			return contentDao.listByKey(word);
		if (StringUtils.hasText(date))
			return contentDao.listByDate(date);
		if (StringUtils.hasText(tag))
			// 直接使用标签进行分类查询时，最多返回200条数据，以防数据太多
			return contentDao.listByTagAndLimit(tag, 0, 200);
		return null;
	}

	/**
	 * 新增tag对象
	 * 
	 * @param name
	 */
	public void addTag(String name) {
		// 有可能前端传过来的是一个带空格的标签
		String[] names = name.split("\\s+");
		for (String tagName : names) {
			if (tagDao.getTagByName(tagName) == null)
				tagDao.save(new TagDO(tagName));
		}
	}

	public TagDO getTagFromContentTagTable(String tid) {
		// TODO Auto-generated method stub
		return tagDao.getTagFromContentTagTable(Integer.valueOf(tid));
	}

}
