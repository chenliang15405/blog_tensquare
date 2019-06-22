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


}
