package com.yupaits.web.prop;

import com.yupaits.commons.consts.AppConsts;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yupaits
 * @date 2018/11/7
 */
@Data
@Configuration
@ConfigurationProperties("upload")
public class UploadProps  {
    private String watermark;
    private String tempDir = AppConsts.UPLOAD_TEMP_DIR;
}
