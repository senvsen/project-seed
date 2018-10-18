package com.yupaits.commons.utils.poi;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * 导入处理接口
 * @author yupaits
 * @date 2018/5/15
 */
public abstract class ImportProcessor {

    /**
     * 抽象Excel导入方法
     * @param workbook Excel的Workbook
     * @return ImportResponse
     */
    protected abstract ImportResponse processImport(Workbook workbook);
}
