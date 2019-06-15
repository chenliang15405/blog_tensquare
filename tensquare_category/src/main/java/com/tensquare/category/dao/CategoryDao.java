package com.tensquare.category.dao;

import com.tensquare.category.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface CategoryDao extends JpaRepository<Category,String>,JpaSpecificationExecutor<Category>{

    @Query("from Category where  categoryname = :categoryName")
    List<Category> findByCategoryname(@Param("categoryName") String categoryName);

}
