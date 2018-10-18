package com.yupaits.commons.utils.poi;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * 导入处理结果返回信息
 * @author yupaits
 * @date 2018/5/15
 */
@Data
@Builder
public class ImportResponse {

    private boolean ok;
    private ImportResult result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String detailedMessage;

    public static ImportResponse done() {
        return ImportResponse.builder()
                .ok(true)
                .result(ImportResult.IMPORT_DONE)
                .detailedMessage(null)
                .build();
    }

    public static ImportResponse fail(ImportResult importResult) {
        return ImportResponse.builder()
                .result(importResult)
                .detailedMessage(null)
                .build();
    }

    public static ImportResponse fail(ImportResult importResult, String detailedMessage) {
        return ImportResponse.builder()
                .result(importResult)
                .detailedMessage(detailedMessage)
                .build();
    }
}
