package com.tensquare.comment.service;

import com.tensquare.comment.dao.UserCommentFavoriteDao;
import com.tensquare.comment.pojo.UserCommentFavorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @auther alan.chen
 * @time 2019/8/3 11:46 AM
 */
@Service
@Transactional
public class UserCommentFavoriteService {

	@Autowired
	private UserCommentFavoriteDao userCommentFavoriteDao;


	/**
	 * 保存用户评论关联动作
	 * @param usercomment
	 */
	public void save(UserCommentFavorite usercomment) {
		userCommentFavoriteDao.save(usercomment);
	}

	/**
	 * 根据评论id和用户ip查询
	 * @param id
	 * @param ip
	 * @return
	 */
	public UserCommentFavorite findByCommentIdAndIp(Integer id, String ip) {
		List<UserCommentFavorite> list = userCommentFavoriteDao.findByCommentIdAndIp(id, ip);
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public UserCommentFavorite findById(Integer id) {
		Optional<UserCommentFavorite> optional = userCommentFavoriteDao.findById(id);
		return optional.orElse(null);
	}
}
