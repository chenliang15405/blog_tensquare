package com.tensquare.blog;

import com.tensquare.blog.utils.CommonFileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

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
        fileUtil.deleteFile("group1/M00/00/00/rBsACV63_fKAdaGzAAOn6JA93PY591.png");
    }

    @Test
    public void testUpload() throws IOException {
        File file = new File("/Users/alan.chen/Desktop/p4m-info-1.png");
        String s = fileUtil.uploadFile(file);
        System.out.println(s);
    }


}
