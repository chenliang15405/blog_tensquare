package com.tensquare.blog.controller;

import com.github.tobato.fastdfs.domain.MataData;
import com.tensquare.blog.utils.CommonFileUtil;
import com.tensquare.common.entity.Response;
import com.tensquare.common.entity.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

/**
 * 文件与fastdfs的操作controller
 *
 * @auther alan.chen
 * @time 2019/6/17 8:45 PM
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController {


    @Autowired
    private CommonFileUtil fileUtil;


    /**
     * 上传文件或者图片到fastdfs
     *
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Response fileUpload(@RequestParam MultipartFile file) {
        if (file.isEmpty()) {
            log.error("this file is empty");
        }
        log.info("开始上传文件到fastdfs");
        try {
            String path = fileUtil.uploadFile(file);
            log.info("上传file: {} , 上传fastdfs返回的路径: {}", file.getOriginalFilename(), path);
            return new Response(true, StatusCode.OK, "上传文件成功", path);
        } catch (IOException e) {
            log.error("上传文件到fastdfsa失败", e);
        }
        return new Response(false, StatusCode.ERROR, "上传失败");
    }

    /**
     * 上传图片
     * 上传缩略图文件，下载的时候仍然是原始大小
     * @return
     */
    @PostMapping(value = "/image/upload")
    public Response imageUpload(@RequestParam MultipartFile file) {
        // 设置文件信息
        Set<MataData> mataData = new HashSet<>();
        mataData.add(new MataData("author", "tangsong"));
        mataData.add(new MataData("description", "xxxx图片文件"));

        try {
            //上传，如果不设置文件信息，直接填入null
            String path = fileUtil.uploadImage(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), mataData);
            return new Response(true, StatusCode.OK, "上传成功", path);
        } catch (IOException e) {
            log.error("上传图片失败", e);
        }

        return new Response(false, StatusCode.ERROR, "上传失败");
    }

    /**
     * 删除文件
     *
     * @param filePath 不带组名的url ,并且传递的参数的key 必须是 filPath，或者@RequestParam中指定
     * @return
     *
     * @RequestParam 用来处理Content-Type: 为 application/x-www-form-urlencoded编码的内容。
     * @RequestBody 处理HttpEntity传递过来的数据，一般用来处理非Content-Type: application/x-www-form-urlencoded编码格式的数据。
     *
     * 要不是使用表单格式
     * 要不就是 ：localhost:9002/file/delete?filePath=group1/M00/00/00/rBsACV0HubCAHxirAAhct7uzQqU807.jpg
     *
     */
    @DeleteMapping("/delete")
    public Response deleteFile(@RequestParam String filePath) {
        try {
            log.info("删除的文件路径：{}" , filePath);
            fileUtil.deleteFile(filePath);
            return new Response(true, StatusCode.OK, "删除成功");
        } catch (Exception e) {
            log.error("删除失败", e);
        }
        return new Response(false, StatusCode.ERROR, "删除失败");
    }

    /**
     * 下载文件
     *
     * @param group
     * @param path
     * @param fileName 下载文件的全称，目前需要带后缀，可以将原来文件的后缀加到这个上面
     * @param response
     */
    @GetMapping("/download")
    public void downloadFile(@RequestParam String group, @RequestParam String path, @RequestParam String fileName, HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            byte[] bytes = fileUtil.downloadFile(group, path);
            //设置相应类型application/octet-stream （注：applicatoin/octet-stream 为通用，一些其它的类型苹果浏览器下载内容可能为空）
            response.reset();
            response.setContentType("application/octet-stream");
            //设置头信息, Content-Disposition为属性名  附件形式打开下载文件 , fileName为了防止乱码
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            // 写入到流
            out = response.getOutputStream();

            out.write(bytes);

        } catch (UnsupportedEncodingException e) {
            log.error("转码失败", e);
        } catch (IOException e) {
            log.error("下载文件失败", e);
        } finally {
            IOUtils.closeQuietly(out);
        }

    }


}
