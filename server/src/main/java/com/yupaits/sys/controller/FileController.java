package com.yupaits.sys.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.yupaits.commons.result.ResultCode;
import com.yupaits.commons.result.ResultWrapper;
import com.yupaits.commons.utils.EncryptUtils;
import com.yupaits.commons.utils.HttpUtils;
import com.yupaits.commons.utils.ValidateUtils;
import com.yupaits.sys.entity.StoreFile;
import com.yupaits.sys.service.IStoreFileService;
import com.yupaits.web.shiro.ShiroHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yupaits
 * @date 2018/11/7
 */
@Api(tags = "文件下载接口")
@Controller
@RequestMapping("/file")
public class FileController {

    private final ObjectMapper objectMapper;
    private final FastFileStorageClient storageClient;
    private final IStoreFileService storeFileService;

    @Autowired
    public FileController(ObjectMapper objectMapper, FastFileStorageClient storageClient,
                          IStoreFileService storeFileService) {
        this.objectMapper = objectMapper;
        this.storageClient = storageClient;
        this.storeFileService = storeFileService;
    }

    @ApiOperation("根据文件ID下载文件")
    @ApiImplicitParam(name = "fileId", value = "文件ID", required = true, dataType = "Long", paramType = "path")
    @GetMapping(value = "/{fileId:\\d{19}}")
    public void downloadFile(HttpServletResponse response, @PathVariable Long fileId) throws IOException {
        if (!ValidateUtils.idValid(fileId)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            objectMapper.writeValue(response.getWriter(), ResultWrapper.fail(ResultCode.PARAMS_ERROR));
        }
        StoreFile storeFile = storeFileService.getById(fileId);
        if (storeFile == null) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            objectMapper.writeValue(response.getWriter(), ResultWrapper.fail(ResultCode.DATA_NOT_FOUND));
        } else if (storeFile.isPrivacy() && !StringUtils.equals(storeFile.getPrivacyCode(),
                EncryptUtils.genPrivacyCode((ShiroHelper.principal().getUsername())))) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            objectMapper.writeValue(response.getWriter(), ResultWrapper.fail(HttpStatus.FORBIDDEN));
        } else {
            byte[] fileBytes = storageClient.downloadFile(storeFile.getGroupName(), storeFile.getPath(), new DownloadByteArray());
            HttpUtils.sendFile(response, fileBytes, FilenameUtils.getName(storeFile.getPath()));
        }
    }

}
