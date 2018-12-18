package com.firesoon.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.List;

/**
 * @author create by yingjie.chen on 2018/3/7.
 * @version 2018/3/7 16:06
 */
public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 是否创建头行
     *
     * @param list           list
     * @param title          title
     * @param sheetName      name
     * @param pojoClass      class
     * @param fileName       fileName
     * @param isCreateHeader header
     * @param response       response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,
                                   boolean isCreateHeader, HttpServletResponse response, HttpServletRequest request) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams, request);

    }

    /**
     * title， filename
     *
     * @param list
     * @param title
     * @param sheetName
     * @param pojoClass
     * @param fileName
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,
                                   HttpServletResponse response, HttpServletRequest request) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName), request);
    }

    /**
     * 只有数据
     *
     * @param list
     * @param fileName
     * @param response
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response,
                                   HttpServletRequest request) {
        defaultExport(list, fileName, response, request);
    }

    /**
     * map 导出
     *
     * @param entity
     * @param entityList
     * @param dataSet
     * @param fileName
     * @param response
     */
    public static void exportExcel(ExportParams entity, List<ExcelExportEntity> entityList,
                                   Collection<? extends Map<?, ?>> dataSet, String fileName, HttpServletResponse response,
                                   HttpServletRequest request) {
        defaultExport(entity, entityList, dataSet, fileName, response, request);
    }

    /**
     * 构建Workbook并返回
     *
     * @param entity     entity
     * @param entityList titleList
     * @param dataSet    dataSet
     * @return Workbook
     */
    public static Workbook getWorkBookByData(ExportParams entity, List<ExcelExportEntity> entityList,
                                             Collection<? extends Map<?, ?>> dataSet) {
        return ExcelExportUtil.exportExcel(entity, entityList, dataSet);
    }

    /**
     * 根据workbook下载数据
     *
     * @param fileName 文件名
     * @param response response
     * @param workbook workbook
     * @param request  request
     */
    public static void downLoadExcelByWorkBook(String fileName, HttpServletResponse response,
                                               Workbook workbook, HttpServletRequest request) {
        if (workbook != null) {
            ExcelStyle.fontStyle(workbook);
            downLoadExcel(fileName, response, workbook, request);
        }
    }

    /**
     * map导出 defaultExport
     *
     * @param entity
     * @param entityList
     * @param dataSet
     */
    public static void defaultExport(ExportParams entity, List<ExcelExportEntity> entityList,
                                     Collection<? extends Map<?, ?>> dataSet, String fileName, HttpServletResponse response,
                                     HttpServletRequest request) {
        Workbook workbook = ExcelExportUtil.exportExcel(entity, entityList, dataSet);
        if (workbook != null) {
            ExcelStyle.fontStyle(workbook);
            ExcelStyle.bgColorStyle(workbook);
            downLoadExcel(fileName, response, workbook, request);
        }
    }

    protected static boolean isIE(HttpServletRequest request) {
        return request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0
                || request.getHeader("USER-AGENT").toLowerCase().indexOf("rv:11.0") > 0;
    }

    /**
     * 对象导出
     *
     * @param list
     * @param pojoClass
     * @param fileName
     * @param response
     * @param exportParams
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response,
                                      ExportParams exportParams, HttpServletRequest request) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook, request);
        }
    }

    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response,
                                      HttpServletRequest request) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook, request);
        }
    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook,
                                      HttpServletRequest request) {
        try {
            if (workbook instanceof HSSFWorkbook) {
                fileName = fileName + ".xls";
            } else {
                fileName = fileName + ".xlsx";
            }

            if (isIE(request)) {
                fileName = URLEncoder.encode(fileName, "UTF8");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }

            response.setHeader("content-disposition", "attachment;filename=" + fileName);
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("模板不能为空");
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows,
                                          Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            log.info(e.getMessage());
            throw new RuntimeException("excel文件不能为空");
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }
}
