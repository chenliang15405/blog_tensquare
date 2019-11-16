package com.test.ffmpeg;

import com.ffmpeg.common.response.Result;
import com.ffmpeg.common.video.VideoOperation;
import org.junit.Test;

import java.io.IOException;

/**
 * @auther alan.chen
 * @time 2019/10/6 7:15 PM
 */
public class FFMpegTest {

    private static final String ffmpegEXE = "/Users/alan.chen/Documents/notes/ffmpeg";

    @Test
    public void testConverTest() throws IOException {
        String inputPath = "/Users/alan.chen/Documents/notes/test/2222.mp4";
        String outPutPath = "/Users/alan.chen/Documents/notes/test/1/convert.flv";
        VideoOperation ffmpeg = VideoOperation.builder(ffmpegEXE);
        Result result = ffmpeg.videoConvert(inputPath, outPutPath);
        System.out.println(result.getCode());
        System.out.println(result.getErrMessage());
    }

    @Test
    public void videoConverToGifTest() throws IOException {
        String inputPath = "/Users/alan.chen/Documents/notes/test/11111.mp4";
        String outPutPath = "/Users/alan.chen/Documents/notes/test/1/out.gif";
        VideoOperation ffmpeg = VideoOperation.builder(ffmpegEXE);
        Result result = ffmpeg.videoConvertToGif(inputPath,outPutPath, true);
        System.out.println(result.getCode());
        System.out.println(result.getErrMessage());
    }

    @Test
    public void videoRotateTest() throws IOException {
        String inputPath = "/Users/alan.chen/Documents/notes/test/2222.mp4";
        String outPutPath = "/Users/alan.chen/Documents/notes/test/1/rotate.mp4";
        VideoOperation ffmpeg = VideoOperation.builder(ffmpegEXE);
        Result result = ffmpeg.videoRotate(inputPath,2,"","" , outPutPath);
        System.out.println(result.getCode());
        System.out.println(result.getErrMessage());
    }

}
