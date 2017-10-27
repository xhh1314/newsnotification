package com.haiwai.newsnotification.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.haiwai.newsnotification.dao.ContentDao;
import com.haiwai.newsnotification.dao.bean.ContentDO;

@Service
@Scope("singleton")
public class ContentService {

	@Autowired
	private ContentDao contentDao;
	@Transactional
	public ContentBO saveContent(ContentBO content) {
		// TODO Auto-generated method stub
		if((content.getCid()==null || content.getCid()==0 ) || contentDao.getContent(content.getCid())==null){
		ContentDO c=contentDao.saveContent(new ContentDO(content));
		return c==null?null:new ContentBO(c);
		}else{
			//如果已经存在，则更新
			if(contentDao.updateContent(new ContentDO(content))==0)
				return null;
			else return new ContentBO(contentDao.getContent(content.getCid()));
		}
	}

}
