package com.tensquare.blog.service;

import com.tensquare.blog.dao.ImageFileDao;
import com.tensquare.blog.pojo.Images;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @auther alan.chen
 * @time 2019/7/6 12:04 PM
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED) // 支持当前事务，如果当前没有事务，就新建一个事务
public class ImageFileService {

	@Autowired
	private ImageFileDao imageFileDao;

	/**
	 * 保存图片记录到数据库
	 * @param image
	 */
	public void save(Images image) {
		imageFileDao.save(image);
	}


}
