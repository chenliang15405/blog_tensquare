package com.tensquare.blog;

import com.tensquare.blog.utils.FastDFSClientUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

/**
 *
 * @auther alan.chen
 * @time 2019/6/16 8:35 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FastDFSUtil1Test {


    @Test
    public void Upload() {
        String fileUrl = "/Users/alan.chen/Desktop/选择页0509-layout.jpg";
        File file = new File(fileUrl);
        String str = FastDFSClientUtil.uploadFile(file);
        String url = FastDFSClientUtil.getResAccessUrl(str);
        System.out.println("这是一个url:   " + url);
    }


    @Test
    public void Delete() {
        FastDFSClientUtil.deleteFile("group1/M00/00/00/rBsACV0HrlOAfKpxAAFmQSzH81U551.png");
    }



}
