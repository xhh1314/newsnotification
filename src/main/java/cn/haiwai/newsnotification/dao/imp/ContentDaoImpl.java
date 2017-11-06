package cn.haiwai.newsnotification.dao.imp;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.haiwai.newsnotification.dao.ContentDao;
import cn.haiwai.newsnotification.dao.TagDao;
import cn.haiwai.newsnotification.dao.bean.ContentDO;
import cn.haiwai.newsnotification.dao.bean.TagDO;

/**
 * 实现ContentDao
 * 
 * @author lh
 * @time 2017年10月25日
 * @version
 */
@Repository
public class ContentDaoImpl implements ContentDao {

	private static final Logger logger = LoggerFactory.getLogger(ContentDaoImpl.class);
	@Autowired
	private ContentDaoImplJPA contentJpa;
	@Autowired
	private TagDao tagDao;

	/*
	 * 保存content之前，先检查包含的tags是否存在
	 */
	@Override
	@Transactional
	public ContentDO saveContent(ContentDO content) {
		Set<TagDO> tags = content.getTags();
		Set<TagDO> tagCache = new HashSet<TagDO>(16);
		// 遍历tags，看看前端传过来的标签数据库中是否都存在
		tags.forEach(new Consumer<TagDO>() {
			@Override
			public void accept(TagDO t) {
				// TODO Auto-generated method stub
				TagDO tag;
				if ((tag = tagDao.getTagByName(t.getName())) == null) {
					tag = tagDao.save(new TagDO(t.getName()));
				}
				tagCache.add(tag);
			}
		});
		content.setTags(tagCache);
		ContentDO contentOld = null;
		// 数据库中不存在
		if ((content.getId() == null || content.getId() == 0) || (contentOld = getContent(content.getId())) == null) {
			return contentJpa.save(content);
			// 如果已经存在，则更新
		} else {
			contentOld.setContent(content.getContent());
			contentOld.setReceiveTime(content.getReceiveTime());
			contentOld.setStatus(content.getStatus());
			contentOld.setTitle(content.getTitle());
			contentOld.setTags(content.getTags());
			return contentJpa.save(contentOld);
		}
	}

	@Override
	@Transactional
	public Integer updateContent(ContentDO content) {
		// TODO Auto-generated method stub
		return contentJpa.updateContent(content.getId(), content.getContent());

	}

	@Override
	@Transactional
	public void deleteContent(Integer id) {
		// TODO Auto-generated method stub
		ContentDO content=getContent(id);
		if(content!=null) {
		contentJpa.delete(content);
		contentJpa.deleteContentTag(id);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ContentDO> listContent() {
		// TODO Auto-generated method stub
		return contentJpa.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ContentDO> listByDate(String date) {
		// TODO Auto-generated method stub
		return contentJpa.listContentByDate(date);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ContentDO> listByKey(String key) {
		// TODO Auto-generated method stub
		return contentJpa.listByKey(key);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ContentDO> listByDateAndLimit(Date date, int begin, int offset) {
		// TODO Auto-generated method stub
		logger.error("该方法没有实现{}", ContentDaoImpl.class);
		throw new RuntimeException("方法没有实现！");
	}

	@Override
	@Transactional(readOnly = true)
	public List<ContentDO> listByLimit(int begin, int offset) {
		// TODO Auto-generated method stub
		return contentJpa.listByLimit(begin, offset);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countContent() {
		// TODO Auto-generated method stub
		return contentJpa.countContent();
	}

	@Override
	public ContentDO getContent(Integer id) {
		// TODO Auto-generated method stub
		return contentJpa.findOne(id);
	}

	@Override
	public List<ContentDO> listByRecentSevenDay() {
		// TODO Auto-generated method stub
		return contentJpa.listByRecentSevenDay();
	}

	@Override
	public Integer updateContentStatus(int cid,int status) {
		// TODO Auto-generated method stub
		return contentJpa.updateContentStatus(cid,status);
	}

}
