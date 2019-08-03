package com.tensquare.comment.dao;

import com.tensquare.comment.pojo.UserCommentFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @auther alan.chen
 * @time 2019/8/3 11:30 AM
 */
public interface UserCommentFavoriteDao extends JpaRepository<UserCommentFavorite, Integer>, JpaSpecificationExecutor<UserCommentFavorite> {


	@Query("from UserCommentFavorite where commentId = :commentId and ip = :ip")
	List<UserCommentFavorite> findByCommentIdAndIp(@Param("commentId") Integer id, @Param("ip") String ip);
}
