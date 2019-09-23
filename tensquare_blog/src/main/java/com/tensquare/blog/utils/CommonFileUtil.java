package com.tensquare.blog.utils;

import com.github.tobato.fastdfs.domain.MataData;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Set;

/**
 *   Trackerserver作用是负载均衡和调度，通过Trackerserver在文件上传时可以根据一些策略找到Storageserver提供文件上传服务。可以将tracker称为追踪服务器或调度服务器。
 *
 *   Storageserver作用是文件存储，客户端上传的文件最终存储在Storage服务器上，Storage server没有实现自己的文件系统而是利用操作系统 的文件系统来管理文件。可以将storage称为存储服务器。
 *
 *
 * @auther alan.chen
 * @time 2019/6/17 8:52 PM
 */
@Slf4j
@Component
public class CommonFileUtil {


    @Autowired
    private FastFileStorageClient storageClient;


    /**
     *	MultipartFile类型的文件上传ַ
     * @param file
     * @return
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return getResAccessUrl(storePath);
    }

    /**
     * 普通的文件上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    public String uploadFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        StorePath path = storageClient.uploadFile(inputStream, file.length(),
                FilenameUtils.getExtension(file.getName()), null);
        return getResAccessUrl(path);
    }

    /**
     * 带输入流形式的文件上传
     *
     * @param is
     * @param size
     * @param fileName
     * @return
     */
    public String uploadFileStream(InputStream is, long size, String fileName) {
        StorePath path = storageClient.uploadFile(is, size, fileName, null);
        return getResAccessUrl(path);
    }

    /**
     * 下载文件，返回字节数组
     * @param group fastdfs分组名
     * @param path  文件路径
     * @return
     */
    public byte[] downloadFile(String group, String path) {
        byte[] bytes = storageClient.downloadFile(group, path, new DownloadByteArray());
        return bytes;
    }

    /**
     * @param fileUrl 文件访问地址,完整url，包含gorup
     * @author qbanxiaoli
     * @description 下载文件，将文件保存到指定的路径
     */
    public byte[] downloadFileWithFullUrl(String fileUrl) {
        StorePath storePath = StorePath.praseFromUrl(fileUrl);
        byte[] bytes = storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());
        return bytes;
    }



    /**
     * 将一段文本文件写到fastdfs的服务器上
     *
     * @param content
     * @param fileExtension
     * @return
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath path = storageClient.uploadFile(stream, buff.length, fileExtension, null);
        return getResAccessUrl(path);
    }

    /**
     * 返回文件上传成功后的地址名称ַ
     * @param storePath
     * @return
     */
    private String getResAccessUrl(StorePath storePath) {
        String fileUrl = storePath.getFullPath();
        return fileUrl;
    }

    /**
     * 删除文件
     * @param fileUrl 文件的完整路径
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 上传图片，缩略图, 上传之后会讲图片压缩，变小
     * 访问的时候体积很小，下载还是会下载原来上传的大小
     * @param is
     * @param size
     * @param fileExtName
     * @param mataData
     * @return
     */
    public String uploadImage(InputStream is, long size, String fileExtName, Set<MataData> mataData) {
        StorePath path = storageClient.uploadImageAndCrtThumbImage(is, size, fileExtName, mataData);
        return getResAccessUrl(path);
    }

}
