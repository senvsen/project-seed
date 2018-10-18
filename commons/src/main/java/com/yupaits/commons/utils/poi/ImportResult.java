package com.yupaits.commons.utils.poi;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 导入处理结果
 * @author yupaits
 * @date 2018/5/15
 */
public enum ImportResult {
    /**
     * 导入完成
     */
    IMPORT_DONE("导入完成"),
    FILE_NOT_EXISTED("文件不存在"),
    NOT_EXCEL_FILE("不是Excel格式文件"),
    GET_WORKBOOK_EXCEPTION("获取Workbook异常"),
    IMPORT_FAIL("导入Excel数据失败");

    private String message;

    ImportResult(String message) {
        this.message = message;
    }

    @JsonValue
    public String getMessage() {
        return message;
    }
}
