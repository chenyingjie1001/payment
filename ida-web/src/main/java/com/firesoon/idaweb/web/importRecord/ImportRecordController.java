package com.firesoon.idaweb.web.importRecord;

import com.firesoon.idaweb.aop.annotation.IdaPointcut;
import com.firesoon.idaweb.web.base.BaseController;
import com.firesoon.paymentmapper.importRecord.ImportRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.firesoon.utils.FileUtil;
import com.firesoon.utils.PropertiesUtil;

//@IdaPointcut
@Api
@Slf4j
@RestController
//@RequestMapping(value = "/importRecord/", method = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping(value = "/api/importRecord/", method = { RequestMethod.GET, RequestMethod.POST })
public class ImportRecordController extends BaseController {

	@Autowired
	private ImportRecordMapper importRecordMapper;

	@ApiOperation(value = "查扣款单导入记录", notes = "查扣款单导入记录")
	@GetMapping(value = "/query")
	public Object query(@ApiParam(value = "{\"month\" : \"2018-11\"}") @RequestBody Map<String, Object> map) {
		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageNum").toString();
		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
		List<Map<String, Object>> chargeBills = new ArrayList<>();
		if (importRecordMapper.findImportRecord(map).size() > 0) {
			chargeBills = importRecordMapper.findImportRecord(map);
		}
		return succ(new PageInfo<>(chargeBills));
	}

	@ApiOperation(value = "下载导入记录", notes = "下载导入记录")
	@GetMapping(value = "/download")
	public void download(@ApiParam(value = "{\"filename\" : \"市医保8月第［1］版.xls\"}") @RequestParam Map<String, Object> map,
			HttpServletResponse response, HttpServletRequest request) throws IOException {
		String filename = map.get("filename").toString();
		String path = PropertiesUtil.properties.getProperty("xlsfilePath") + filename;
		File file = new File(path);
		InputStream fis = new BufferedInputStream(new FileInputStream(path));
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		response.reset();
//		if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
//			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
//		} else {
//			filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
//			response.addHeader("Content-Disposition",
//					"attachment;filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
//		}
		response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
		// response.addHeader("Content-Disposition", "attachment;filename=" + new
		// String(filename.getBytes("utf-8")));
		response.addHeader("Content-Length", "" + file.length());
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		toClient.write(buffer);
		toClient.flush();
		toClient.close();
	}
}
