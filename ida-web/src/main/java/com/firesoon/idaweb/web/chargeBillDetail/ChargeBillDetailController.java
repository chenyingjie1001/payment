package com.firesoon.idaweb.web.chargeBillDetail;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.firesoon.idaweb.aop.annotation.IdaPointcut;
import com.firesoon.idaweb.web.base.BaseController;
import com.firesoon.paymentmapper.chargeBillDetail.ChargeBillDetailMapper;
import com.firesoon.utils.FileUtil;
import com.firesoon.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api
@Slf4j
@RestController
//@RequestMapping(value = "/chargeBillDetail/", method = { RequestMethod.POST, RequestMethod.GET })
@RequestMapping(value = "/api/chargeBillDetail/", method = { RequestMethod.POST, RequestMethod.GET })
public class ChargeBillDetailController extends BaseController {

	@Autowired
	private ChargeBillDetailMapper chargeBillDetailMapper;

	@IdaPointcut
	@ApiOperation(value = "查扣款单明细", notes = "查扣款单明细")
	@GetMapping(value = "/query")
	public Object query(
			@ApiParam(value = "{\"month\" : \"2018-11\",\"pageNum\" : \"1\",\"pageSize\" : \"10\",\"iswrite\" : \"0\",\"isappeal\" : \"0,1\",\"itemname\" : \"医疗,运输\",\"chargeDepartments\" : \"骨科,皮肤科\"}") @RequestBody Map<String, Object> map) {
		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageSize").toString();
		String departs = (map.get("chargeDepartments") == null ? "" : map.get("chargeDepartments").toString());
		String isappeal = (map.get("isappeal") == null ? "" : map.get("isappeal").toString());
		String itemname = (map.get("itemname") == null ? "" : map.get("itemname").toString());
		List<String> departments = new ArrayList<>();
		List<String> isappeals = new ArrayList<>();
		List<String> itemnames = new ArrayList<>();
		if (departs.length() > 0) {
			departments = Arrays.asList(departs.split(","));
		}
		if (isappeal.length() > 0) {
			isappeals = Arrays.asList(isappeal.split(","));
		}
		if (itemname.length() > 0) {
			itemnames = Arrays.asList(itemname.split(","));
		}
//		String h = departments.get(0);
//		if (!"".equals(departments.get(0))) {
//			map.put("chargeDepartments", departments);
//		}
//		if (!"".equals(isappeals.get(0))) {
//			map.put("isappeal", isappeals);
//		}
//		if (!"".equals(itemnames.get(0))) {
//			map.put("itemname", itemnames);
//		}
		map.put("chargeDepartments", departments);
		map.put("isappeal", isappeals);
		map.put("itemname", itemnames);
		// List<Map<String, Object>> chargeBills = new ArrayList<>();
		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
		List<Map<String, Object>> list = chargeBillDetailMapper.findChargeBillDetail(map);

		// list.stream().forEach(m-> m.entrySet().stream().filter(et ->
		// !"APPEALIMG".equals(et.getKey())).collect(Collectors.toSet()));
		for (Map<String, Object> m : list) {
			Iterator<Entry<String, Object>> it = m.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Object> eneity = it.next();
				if ("APPEALIMG".equals(eneity.getKey())) {
					m.remove(eneity.getKey());
					break;
				}
			}
		}
		Map<String, Object> result = new HashMap<>();
		result.put("pageInfo", new PageInfo<>(list));
		result.put("excelTable", chargeBillDetailMapper.getExcelTable());
		return succ(result);
	}

	@IdaPointcut
	@ApiOperation(value = "查所有扣款科室", notes = "查所有扣款科室")
	@GetMapping(value = "/allDepartment")
	public Object allDepartment(@ApiParam(value = "{\"month\" : \"2018-11\"}") @RequestBody Map<String, Object> map) {
		List<Map<String, Object>> chargeBill = new ArrayList<>();
		if (chargeBillDetailMapper.getAllChargeDepartment(map).size() > 0) {
			chargeBill = chargeBillDetailMapper.getAllChargeDepartment(map);
		}
		return succ(chargeBill);
	}

	@ApiOperation(value = "查所有扣款核算项目", notes = "查所有扣款核算项目")
	@GetMapping(value = "/allItem")
	public Object allItem(@ApiParam(value = "{\"month\": \"2018-11\"}") @RequestBody Map<String, Object> map) {
		List<Map<String, Object>> chargeBill = new ArrayList<>();
		if (chargeBillDetailMapper.getAllChargeItem(map).size() > 0) {
			chargeBill = chargeBillDetailMapper.getAllChargeItem(map);
		}
		return succ(chargeBill);
	}

	@ApiOperation(value = "更改扣款科室", notes = "更改扣款科室")
	@GetMapping(value = "/changeDepartment")
	public Object changeDepartment(
			@ApiParam(value = "{\"id\" : \"7B8FE6184D98B098E05010AC49033255\",\"department\" : \"骨科\"}") @RequestBody Map<String, Object> map) {
		chargeBillDetailMapper.updateDepartment(map);
		return succ();
	}

	@ApiOperation(value = "查申诉信息", notes = "查申诉信息")
	@GetMapping(value = "/getAppealMessage")
	public Object getAppealMessage(@ApiParam(value = "{\"id\" : \"扣款单明细几句id\"}") @RequestBody Map<String, Object> map) {
		List<Map<String, Object>> result = chargeBillDetailMapper.getAppealMessage(map);
		result.stream().forEach(m -> m.put("APPEALIMG", StringUtil.Clob2String((Clob) m.get("APPEALIMG"))));
		return succ(result);
	}

	@IdaPointcut
	@ApiOperation(value = "申诉", notes = "申诉")
	@GetMapping(value = "/appeal")
	public Object appeal(
			@ApiParam(value = "{\"id\" : \"扣款单明细id\",\"appealtext\" : \"理由\",\"appealimg\" : \"[图片1Base64,图片2Base64]\"}") @RequestBody Map<String, Object> map) {
		map.put("appealimg", JSONUtils.toJSONString(map.get("appealimg")));
		chargeBillDetailMapper.updateWrite(map);
		return succ();
	}

	@ApiOperation(value = "获取科级月份", notes = "获取科级月份")
	@GetMapping(value = "/getSectionLevelMonth")
	public Object getSectionLevelMonth() {
		List<Map<String, Object>> result = chargeBillDetailMapper.getSectionLevelMonth();
		Map<String, Object> monthMap = new HashMap<>();
		if (result.size() == 0) {
			monthMap.put("levelmonth", new SimpleDateFormat("yyyy-MM").format(new Date()));
		} else {
			monthMap = result.get(0);
		}
		return succ(monthMap);
	}

	@IdaPointcut
	@ApiOperation(value = "科级导出", notes = "科级导出")
	@GetMapping(value = "/levelDownload")
	public void levelDownload(
			@ApiParam(value = "{\"month\" : \"2018-11\",\"iswrite\" : \"0\",\"isappeal\" : \"0\",\"itemname\" : \"医疗\",\"chargeDepartments\" : \"骨科,皮肤科\"}") @RequestParam Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String departs = (map.get("chargeDepartments") == null ? "" : map.get("chargeDepartments").toString());
		String isappeal = (map.get("isappeal") == null ? "" : map.get("isappeal").toString());
		String itemname = (map.get("itemname") == null ? "" : map.get("itemname").toString());
		List<String> departments = new ArrayList<>();
		List<String> isappeals = new ArrayList<>();
		List<String> itemnames = new ArrayList<>();
		if (departs.length() > 0) {
			departments = Arrays.asList(departs.split(","));
		}
		if (isappeal.length() > 0) {
			isappeals = Arrays.asList(isappeal.split(","));
		}
		if (itemname.length() > 0) {
			itemnames = Arrays.asList(itemname.split(","));
		}
		map.put("chargeDepartments", departments);
		map.put("isappeal", isappeals);
		map.put("itemname", itemnames);
		List<Map<String, Object>> list = chargeBillDetailMapper.findChargeBillDetail(map);
		List<Map<String, Object>> cols = chargeBillDetailMapper.getExcelTable();
		List<ExcelExportEntity> entityList = new ArrayList<>();
		for (Map<String, Object> col : cols) {
			String name = col.get("COLUMN_NAME").toString();
			String key = col.get("COLUMN_CODE").toString();
			ExcelExportEntity excelExportEntity = new ExcelExportEntity(name, key);
			String[] arg = new String[] { "未填写_0", "已填写_1" };
			String[] appealarg = new String[] { "未申诉_0", "已申诉_1", "申诉失败_-1" };
			if ("申诉状态".equals(name)) {
				excelExportEntity.setReplace(appealarg);
			} else if ("填写状态".equals(name)) {
				excelExportEntity.setReplace(arg);
			}

			entityList.add(excelExportEntity);
		}
		String codedFileName = map.get("month").toString() + "扣款单";
		FileUtil.exportExcel(new ExportParams(), entityList, list, codedFileName, response, request);
	}

	@ApiOperation(value = "查所有医院科室配置", notes = "查所有医院科室配置")
	@GetMapping(value = "/allDepartmentConfig")
	public Object allDepartmentConfig() {
		return succ(chargeBillDetailMapper.getAllDepartmentConfig());
	}

//	@ApiOperation(value = "查所有申诉模板", notes = "查所有申诉模板")
//	@GetMapping(value = "/getAllAppealConfig")
//	public Object getAllAppealConfig(
//			@ApiParam(value = "{\"rulename\" : \"规则名称\",\"itemcode\" : \"项目编码\",\"itemname\" : \"项目名称\"}") @RequestBody Map<String, Object> map) {
//		return succ(chargeBillDetailMapper.getAllAppealConfig(map));
//	}

//	@ApiOperation(value = "downloadImg", notes = "downloadImg")
//	@RequestMapping(value = "/downloadImg")
//	public Object downloadImg(ServletRequest request, HttpServletResponse response,
//			@ApiParam(value = "{\"filename\": \"pic1.jpg,pic2.jpg\"}") @RequestBody Map<String, Object> params)
//			throws IOException {
//		String name = params.get("filename").toString();
//		String[] picNames = name.split(",");
//		for (String pic : picNames) {
//
//		}
//		return succ();
//	}

//	@ApiOperation(value = "uploadImg", notes = "uploadImg")
//	@RequestMapping(value = "/uploadImg")
//	public Object uploadImg(MultipartFile file, HttpServletRequest request) throws IOException {
//		if (file.isEmpty()) {
//			return succ("文件不能为空");
//		}
//		String filename = file.getOriginalFilename();
//		String suffix = filename.substring(filename.lastIndexOf(".") + 1);
//		if (suffix == null || !"JPG,".contains(suffix.toUpperCase() + ",")) {
//			throw new WarnException("不支持的文件类型！");
//		}
//		String filepath = PropertiesUtil.properties.getProperty("imgFilePath") + filename;
//		file.transferTo(new File(filepath));
//		Map<String, Object> result = new HashMap<>();
//		result.put("filename", filename);
//		return succ(result);
//	}

//	@ApiOperation(value = "downloadImg", notes = "downloadImg")
//	@RequestMapping(value = "/downloadImg")
//	public void downloadImg(ServletRequest request, ServletResponse responsee, HttpServletResponse response,
//			@ApiParam(value = "{\"filename\": \"pic1.jpg,pic2.jpg\"}") @RequestBody Map<String, Object> params)
//			throws IOException {
//		String name = params.get("filename").toString();
//		String[] picNames = name.split(",");
//		for (String pic : picNames) {
//			String filepath = PropertiesUtil.properties.getProperty("imgFilePath") + pic;
//			File file = new File(filepath);
//			FileImageInputStream fs = new FileImageInputStream(file);
//			int streamLength = (int) fs.length();
//			byte[] image = new byte[streamLength];
//			fs.read(image, 0, streamLength);
//			fs.close();
//			response.setHeader("Content-Type", "application/octet-stream");
//			response.setHeader("Content-Disposition", "attachment;filename=" + pic);
//			response.getOutputStream().write(image);
//			response.getOutputStream().flush();
//			response.getOutputStream().close();
//		}
//	}

//	@ApiOperation(value = "deleteImg", notes = "deleteImg")
//	@RequestMapping(value = "/deleteImg")
//	public Object deleteImg(@ApiParam(value = "{\"filename\": \"pic.jpg\"}") @RequestBody Map<String, Object> params) {
//		String name = params.get("filename").toString();
//		String filepath = PropertiesUtil.properties.getProperty("imgFilePath") + name;
//		File file = new File(filepath);
//		if (file.exists()) {
//			file.delete();
//		}
//		return succ();
//	}
}