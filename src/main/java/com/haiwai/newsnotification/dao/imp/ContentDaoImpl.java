package com.haiwai.newsnotification.dao.imp;

import java.util.Date;
import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.haiwai.newsnotification.dao.ContentDao;
import com.haiwai.newsnotification.dao.bean.ContentDO;

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

	@Override
	@Transactional
	public ContentDO saveContent(ContentDO content) {
		// TODO Auto-generated method stub
		return contentJpa.save(content);
	}

	@Override
	@Transactional
	public Integer updateContent(ContentDO content) {
		// TODO Auto-generated method stub
		return contentJpa.updateContent(content.getId(),content.getContent());

	}

	@Override
	@Transactional
	public void deleteContent(Integer id) {
		// TODO Auto-generated method stub
		logger.error("该方法没有实现{}", ContentDaoImpl.class);
		throw new RuntimeException("方法没有实现！");
	}

	@Override
	@Transactional(readOnly=true)
	public List<ContentDO> listContent() {
		// TODO Auto-generated method stub
		return contentJpa.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<ContentDO> listByDate(Date date) {
		// TODO Auto-generated method stub
		return contentJpa.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<ContentDO> listByKey(String key) {
		// TODO Auto-generated method stub
		return contentJpa.listByKey(key);
	}

	@Override
	@Transactional(readOnly=true)
	public List<ContentDO> listByDateAndLimit(Date date, int begin, int offset) {
		// TODO Auto-generated method stub
		logger.error("该方法没有实现{}", ContentDaoImpl.class);
		throw new RuntimeException("方法没有实现！");
	}

	@Override
	@Transactional(readOnly=true)
	public List<ContentDO> listByLimit(int begin, int offset) {
		// TODO Auto-generated method stub
		return contentJpa.listByLimit(begin,offset);
	}

	@Override
	@Transactional(readOnly=true)
	public Integer countContent() {
		// TODO Auto-generated method stub
		return contentJpa.countContent();
	}

	@Override
	public ContentDO getContent(Integer id) {
		// TODO Auto-generated method stub
		return contentJpa.findOne(id);
	}

}
