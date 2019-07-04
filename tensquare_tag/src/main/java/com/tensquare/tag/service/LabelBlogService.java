package com.tensquare.tag.service;

import com.google.common.collect.Lists;
import com.tensquare.tag.dao.LabelBlogDao;
import com.tensquare.tag.pojo.Label;
import com.tensquare.tag.pojo.LabelBlog;
import com.tensquare.tag.vo.LabelBlogVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 博客和标签管理服务
 *
 * @auther alan.chen
 * @time 2019/6/22 5:26 PM
 */
@Slf4j
@Service //如果类加了这个注解，那么这个类里面的方法抛出异常，就会回滚，如果不配置rollbackFor属性,那么事物只会在遇到RuntimeException的时候才会回滚,加上rollbackFor=Exception.class,可以让事物在遇到非运行时异常时也回滚
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED) // 支持当前事务，如果当前没有事务，就新建一个事务
public class LabelBlogService {

	@Autowired
	private LabelBlogDao labelBlogDao;

	@Autowired
	private TagService tagService;


	/**
	 * 查询博客关联的标签的属性
	 * TODO 需要加上缓存
	 * @param blogId
	 * @return
	 */
	public List<LabelBlogVo> findByBlogId(String blogId) {
		List<LabelBlog> list = labelBlogDao.findByBlogId(blogId);
		List<LabelBlogVo> voList = Lists.newArrayList();
		log.info("返回labelBlogList数量：{}",list.size());
		for (LabelBlog labelBlog : list) {
			LabelBlogVo labelBlogVo = new LabelBlogVo();
			Label label = tagService.findById(labelBlog.getLabelid());
			// copy properties
			labelBlogVo.setLabelname(label.getLabelname());
			labelBlogVo.setColor(label.getColor());

			voList.add(labelBlogVo);
		}
		return voList;
	}

	/**
	 * 保存博客和标签的关联
	 * @param blogId
	 * @param tagList
	 * @return
	 */
	public String save(String blogId, List<String> tagList) {
		try {
			List<LabelBlog> list = Lists.newArrayList();
			// 循环保存
			//  先查询是否有这个labelName，如果有返回id，如果没有，保存之后，在保存进中间表
			List<Integer> tagIds = hanleTagList(tagList);
			if(tagIds.size() > 0) {
				tagIds.forEach(id -> {
					LabelBlog labelBlog = new LabelBlog();
					labelBlog.setBlogid(blogId);
					labelBlog.setLabelid(id);
					list.add(labelBlog);
				});
				labelBlogDao.saveAll(list);
			}
			return "1";
		} catch (Exception e) {
			log.error("labelBlog save error", e);
		}
		return "0";
	}

	// 处理标签列表，查询是否存在该标签，如果存在，返回id，如果不存在，保存返回id
	private List<Integer> hanleTagList(List<String> tagList) {
		List<Integer> ids = Lists.newArrayList();
		tagList.forEach(item -> {
			Label label = tagService.findByName(item);
			if(label != null) {
				ids.add(label.getId());
			} else {
				Label label1 = new Label();
				label1.setLabelname(item);
				label1.setCount(0L);
				label1.setState("1");
				Integer id = tagService.add(label1).getId();
				ids.add(id);
			}
		});
		return ids;
	}
}
