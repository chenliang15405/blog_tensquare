package com.tensquare.blog.dao;

import com.tensquare.blog.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{


    @Modifying
    @Query(value = "UPDATE tb_article SET thumbup = thumbup+1 WHERE id = :id",nativeQuery = true)
    void addThumbup(@Param("id") String articleId);

}
