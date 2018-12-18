package com.firesoon.idaweb.web.appealTemplate;

import com.firesoon.idaweb.web.base.BaseController;
import com.firesoon.paymentmapper.appealTemplate.AppealTemplateMapper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping(value = "/api/appealTemplate/", method = { RequestMethod.GET, RequestMethod.POST })
public class AppealTemplateController extends BaseController {

	@Autowired
	private AppealTemplateMapper appealTemplateMapper;

	@ApiOperation(value = "查医院科室配置", notes = "查医院科室配置")
	@GetMapping(value = "/query")
	public Object query(
			@ApiParam(value = "{\"rulename\" : \"规则名称\",\"itemcode\" : \"项目编码\",\"itemname\" : \"项目名称\"}") @RequestBody Map<String, Object> map) {
		return succ(new PageInfo<>(appealTemplateMapper.findAppealTemplate(map)));
	}

	@ApiOperation(value = "新增医院科室配置", notes = "新增医院科室配置")
	@GetMapping(value = "/add")
	public Object add(
			@ApiParam(value = "{\"rulename\" : \"规则名称\",\"itemcode\" : \"项目编码\",\"itemname\" : \"项目名称\",\"template\" : \"模板\"}") @RequestBody Map<String, Object> map) {
		appealTemplateMapper.insertAppealTemplate(map);
		return succ();
	}

	@ApiOperation(value = "修改医院科室配置", notes = "修改医院科室配置")
	@GetMapping(value = "/update")
	public Object update(
			@ApiParam(value = "{\"id\" : \"12sed\",\\\"rulename\\\" : \\\"规则名称\\\",\\\"itemcode\\\" : \\\"项目编码\\\",\\\"itemname\\\" : \\\"项目名称\\\",\\\"template\\\" : \\\"模板\\\"}") @RequestBody Map<String, Object> map) {
		appealTemplateMapper.updateAppealTemplate(map);
		return succ();
	}

	@ApiOperation(value = "删除医院科室配置", notes = "删除医院科室配置")
	@GetMapping(value = "/delete")
	public Object delete(@ApiParam(value = "{\"id\" : \"12sed\"}") @RequestBody Map<String, Object> map) {
		appealTemplateMapper.deleteAppealTemplate(map);
		return succ();
	}

}
