package cn.haiwai.newsnotification.dao.imp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.haiwai.newsnotification.dao.TagDao;
import cn.haiwai.newsnotification.dao.bean.ContentDO;
import cn.haiwai.newsnotification.dao.bean.TagDO;
import cn.haiwai.newsnotification.manage.util.TimeTransfer;
import cn.haiwai.newsnotification.service.ContentBO;
import cn.haiwai.newsnotification.service.TagBO;

/**
 * 实现TagDao 方法
 * 
 * @author lh
 * @date 2017年11月1日
 * @version 1.0
 */
@Repository
public class TagDaoImpl implements TagDao {

	@Autowired
	private TagDaoImplJPA tagDaoJpa;

	@Override
	@Transactional
	public TagDO save(TagDO tag) {
		// TODO Auto-generated method stub
		
		
		return tagDaoJpa.save(tag);
	}

	@Override
	public boolean updateName(TagDO tag) {
		// TODO Auto-generated method stub
		return tagDaoJpa.updateName(tag.getId(), tag.getName()) > 0 ? true : false;
	}

	@Override
	public TagDO getTagByName(String name) {
		// TODO Auto-generated method stub
		return tagDaoJpa.getTagByName(name);
	}

	@Override
	public TagDO getTagById(Integer id) {
		// TODO Auto-generated method stub
		return tagDaoJpa.getOne(id);
	}

	@Override
	public List<TagDO> listAll() {
		// TODO Auto-generated method stub
		return tagDaoJpa.findAll();
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		tagDaoJpa.delete(id);
	}

	@Override
	public TagDO getTagFromContentTagTable(Integer id) {
		// TODO Auto-generated method stub
		return tagDaoJpa.getTagFromContentTagTable(id);
	}

}
