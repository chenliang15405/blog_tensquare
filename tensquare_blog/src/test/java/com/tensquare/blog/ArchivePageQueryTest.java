package com.tensquare.blog;

import com.tensquare.blog.pojo.Article;
import com.tensquare.blog.service.ArticleService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

/**
 * @auther alan.chen
 * @time 2019/6/27 10:41 PM
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ArchivePageQueryTest {

	@Autowired
	private ArticleService articleService;

	@Test
	public void testArchiveSort() {
		Page<Article> list = articleService.findArticleArchiveList(1, 6);

		Assert.assertEquals(11, list.getTotalElements());

		Assert.assertEquals(6, list.getContent().size());

		System.out.println(list.getContent());
	}


	@Test
	public void testReponseParse() {
//		UserVo userVo = new UserVo();
//		userVo.setId("1");
//		userVo.setAvatar("www.baiduc.com");
//		userVo.setSex("1");
//		Response response = new Response(true, StatusCode.OK, "查询成功", userVo);

//		Object json = JSON.toJSON(response.getData());
//		Map<String,String> map = (Map<String, String>) JSON.parse(String.valueOf(json));
//
//		System.out.println(map.get("avatar"));

	}


}
