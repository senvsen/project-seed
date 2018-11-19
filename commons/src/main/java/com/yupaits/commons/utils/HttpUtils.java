package com.yupaits.commons.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yupaits
 * @date 2018/11/19
 */
public class HttpUtils {

    /**
     * 网络请求响应返回文件（下载文件）
     * @param response 响应
     * @param fileBytes 文件字节数组
     * @param filename 文件名
     * @throws IOException IO异常
     */
    public static void sendFile(HttpServletResponse response, byte[] fileBytes, String filename) throws IOException {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setContentLengthLong(fileBytes.length);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        FileCopyUtils.copy(fileBytes, response.getOutputStream());
    }
}
