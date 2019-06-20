package com.tensquare.blog;

import com.tensquare.blog.utils.CommonFileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @auther alan.chen
 * @time 2019/6/16 9:56 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FastDFSUtilTest {

    @Autowired
    private CommonFileUtil fileUtil;


    @Test
    public void testDelete() {
        fileUtil.deleteFile("group1/M00/00/00/rBsACV0HuJOAC3AyAAhct7uzQqU236.jpg");
    }



}
