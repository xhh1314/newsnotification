package cn.haiwai.newsnotification.dao;

import java.util.List;

import cn.haiwai.newsnotification.dao.bean.TagDO;

/**
 *  tag实体dao接口
 * @author lh
 * @date 2017年11月1日
 * @version 1.0
 */
public interface TagDao {
	
	 /**
	  * 保存
	 * @param tag
	 * @return
	 */
	TagDO save(TagDO tag);
	 /**
	  * 更新名字
	 * @param tag
	 * @return
	 */
	boolean updateName(TagDO tag);
	 /**
	  * 以名字为关键字查找
	 * @param name
	 * @return
	 */
	TagDO getTagByName(String name );
	 /**
	  * 以id为关键字查找
	 * @param id
	 * @return
	 */
	TagDO getTagById(Integer id);
	 /**
	  * 列示出所有的tag
	 * @return
	 */
	List<TagDO> listAll();
	 /**
	  * 通过Id删除一条记录
	 * @param id
	 */
	void deleteById(Integer id);
	/**
	 * 查询tag标签是否被引用，与中间表联查下
	 * @param valueOf
	 * @return
	 */
	TagDO getTagFromContentTagTable(Integer valueOf);

}
