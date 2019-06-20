package com.tensquare.blog;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @auther alan.chen
 * @time 2019/6/16 7:47 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FastDFSTest {

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * 方法一：上传
     * @throws FileNotFoundException
     */
    @Test
    public void testUpload() throws FileNotFoundException {
        File file = new File("/Users/alan.chen/Desktop/p4m-info-1.png");
        // 上传并且生成缩略图
        StorePath storePath = this.storageClient.uploadFile(
                new FileInputStream(file), file.length(), "png", null);
        // 带分组的路径
        System.out.println(storePath.getFullPath());
        // 不带分组的路径
        System.out.println(storePath.getPath());
    }


    // controller 可以使用
    // MultipartFile是用来接收上传的文件
    // myFile的名字必须和上传的表单的名字一样
    @Test
    public void testUpload2(MultipartFile myFile) throws IOException {
        // myFile.getOriginalFilename():取到文件的名字
        // FilenameUtils.getExtension(""):取到一个文件的后缀名
        String extension = FilenameUtils.getExtension(myFile.getOriginalFilename());

        // group1:指storage服务器的组名
        // myFile.getInputStream():指这个文件中的输入流
        // myFile.getSize():文件的大小
        // 这一行是通过storageClient将文件传到storage容器
        StorePath uploadFile = storageClient.uploadFile("group1", myFile.getInputStream(), myFile.getSize(), extension);

        // 上传数据库
        String sql = "insert into file(filename,groupname,filepath) values(?,?,?)";
//        jdbcTemplate.update(sql, myFile.getOriginalFilename(), uploadFile.getGroup(), uploadFile.getPath());

        // 返回它在storage容器的的路径
        System.out.println("上传返回的路径" + uploadFile.getFullPath());
    }


    @Test
    public void testDownload(HttpServletResponse response) throws IOException {
//        List query = jdbcTemplate.query("select * from file where fileid=" + id, new ColumnMapRowMapper());
//        Map map = (Map) query.get(0);
//        String filename = URLEncoder.encode(map.get("filename").toString(), "utf-8"); // 解决中文文件名下载后乱码的问题
        // 告诉浏览器 下载的文件名
//        response.setHeader("Content-Disposition", "attachment; filename=" + filename + "");
//        String groupName = map.get("groupName").toString();
//        String filepath = map.get("filepath").toString();
        // 将文件的内容输出到浏览器 fastdfs
//        byte[] downloadFile = storageClient.downloadFile(groupName, filepath);
//        response.getOutputStream().write(downloadFile);
    }



}
