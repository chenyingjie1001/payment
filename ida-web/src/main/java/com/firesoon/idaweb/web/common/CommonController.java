package com.firesoon.idaweb.web.common;

import com.firesoon.dto.base.WarnException;
import com.firesoon.idaservice.common.CommonService;
import com.firesoon.idaweb.aop.annotation.IdaPointcut;
import com.firesoon.idaweb.web.base.BaseController;
import com.firesoon.utils.PropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author create by yingjie.chen on 2018/11/22.
 * @version 2018/11/22 15:02
 */
@Api
@Slf4j
@RestController
@RequestMapping(value = "/api/common/", method = RequestMethod.POST)
public class CommonController extends BaseController {

    @Autowired
    private CommonService commonService;

    @ApiOperation(value = "upload上传通用方法", notes = "upload上传通用方法")
    @RequestMapping(value = "upload")
    public Object upload(MultipartFile file, HttpServletRequest request) throws IOException {
        if (file.isEmpty()) {
            return succ("文件不能为空");
        }
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        if (suffix == null || !"CSV,XLSX,XLS,".contains(suffix.toUpperCase() + ",")) {
            throw new RuntimeException("不支持的文件类型！");
        }
        String filepath = PropertiesUtil.properties.getProperty("xlsfilePath") + filename;
        file.transferTo(new File(filepath));
        Map<String, Object> result = new HashMap<>();
        result.put("filename", filename);
        return succ(result);
    }


    @IdaPointcut
    @ApiOperation(value = "importXls", notes = "importXls")
    @RequestMapping(value = "import")
    public Object importXls(@ApiParam(value = "{\"filename\": \"市医保8月第［1］版.xls\",\"month\": \"2018-08\", \"bak\": \"这个是备注\"}") @RequestBody Map<String, Object> params){
        Map<String, Object> data = new HashMap<>();
        data.put("total", commonService.importXls(params));
        data.put("msg", "导入成功");
        data.put("times", commonService.importTimes(params));
        return succ(data);
    }

}