package com.yupaits.commons.utils.poi;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 导入工具
 * @author yupaits
 * @date 2018/5/15
 */
@Slf4j
public class ImportUtils {

    private static final String XLS_SUFFIX = "xls";
    private static final String XLSX_SUFFIX = "xlsx";

    /**
     * 导入Excel表格数据
     * @param file 上传的Excel文件
     * @param processor 导入数据处理
     * @return 导入结果
     */
    public static ImportResponse importExcel(MultipartFile file, ImportProcessor processor) {
        if (file == null) {
            return ImportResponse.fail(ImportResult.FILE_NOT_EXISTED);
        }
        String filename = file.getOriginalFilename();
        boolean isXls = StringUtils.endsWith(filename, XLS_SUFFIX);
        boolean isXlsx = StringUtils.endsWith(filename, XLSX_SUFFIX);
        if (!isXls && !isXlsx) {
            return ImportResponse.fail(ImportResult.NOT_EXCEL_FILE);
        }
        Workbook workbook;
        try {
            InputStream inputStream = file.getInputStream();
            if (isXls) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                workbook = new XSSFWorkbook(inputStream);
            }
            return processor.processImport(workbook);
        } catch (IOException e) {
            log.error("Import poi fail. Get workbook with exception.", e);
            return ImportResponse.fail(ImportResult.GET_WORKBOOK_EXCEPTION);
        }
    }

    /**
     * 根据文件路径导入Excel表格数据
     * @param filePath 文件路径
     * @param processor 导入数据处理
     * @return 导入结果
     */
    public static ImportResponse importExcel(String filePath, ImportProcessor processor) {
        boolean isXls = StringUtils.endsWith(filePath, XLS_SUFFIX);
        boolean isXlsx = StringUtils.endsWith(filePath, XLSX_SUFFIX);
        if (!isXls && !isXlsx) {
            return ImportResponse.fail(ImportResult.NOT_EXCEL_FILE);
        }
        Workbook workbook;
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            if (isXls) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                workbook = new XSSFWorkbook(inputStream);
            }
            return processor.processImport(workbook);
        } catch (FileNotFoundException e) {
            log.error("Import poi fail. Excel file not found.", e);
            return ImportResponse.fail(ImportResult.FILE_NOT_EXISTED);
        } catch (IOException e) {
            log.error("Import poi fail. Get workbook with exception.", e);
            return ImportResponse.fail(ImportResult.GET_WORKBOOK_EXCEPTION);
        }
    }

    /**
     * 以String类型获取单元格的值
     * @param cell 单元格
     * @return String类型的单元格的值
     */
    public static String getCellStringValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        switch (cellType) {
            case NUMERIC:
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case ERROR:
                return String.valueOf(cell.getErrorCellValue());
            case BLANK:
            default:
                return StringUtils.EMPTY;
        }
    }
}
