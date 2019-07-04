package com.blog.label;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @auther alan.chen
 * @time 2019/7/3 11:04 PM
 */
public class jsonTest {

	@Test
	public void jsonParseTest() {
		String json = "{\"tagList\":[1,2,10],\"blogId\":\"1146434182503862272\"}";
		Map<String,Object> map = (Map<String, Object>) JSON.parse(json);
		assertEquals("1146434182503862272", map.get("blogId"));
		List<Integer> tagList = JSON.parseArray(map.get("tagList").toString(), Integer.class);
		System.out.println(tagList);
	}
}
