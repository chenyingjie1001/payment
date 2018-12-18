package com.firesoon.idaweb.web.chargeBill;

import com.firesoon.idaweb.aop.annotation.IdaPointcut;
import com.firesoon.idaweb.web.base.BaseController;
import com.firesoon.paymentmapper.chargeBill.ChargeBillMapper;
import com.firesoon.paymentmapper.chargeBillDetail.ChargeBillDetailMapper;
import com.firesoon.paymentmapper.importRecord.ImportRecordMapper;
import com.firesoon.utils.PropertiesUtil;
import com.firesoon.utils.UserUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@IdaPointcut
@Api
@Slf4j
@RestController
//@RequestMapping(value = "/chargeBill/", method = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping(value = "/api/chargeBill/", method = RequestMethod.POST)
public class ChargeBillController extends BaseController {
	@Autowired
	private ChargeBillMapper chargeBillMapper;

	@Autowired
	private ChargeBillDetailMapper chargeBillDetailMapper;

	@Autowired
	private ImportRecordMapper importRecordMapper;

	@IdaPointcut
	@ApiOperation(value = "查扣款单", notes = "查扣款单")
	@GetMapping(value = "/query")
	public Object query(
			@ApiParam(value = "{\"month\" : \"2018-11\",\"pageNum\" : \"1\",\"pageSize\" : \"10\"}") @RequestBody Map<String, Object> map) {
		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageSize").toString();
		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
		List<Map<String, Object>> chargeBills = new ArrayList<>();
		if (chargeBillMapper.findChargeBill(map).size() > 0) {
			chargeBills = chargeBillMapper.findChargeBill(map);
		}
		return succ(new PageInfo<>(chargeBills));
	}

//	@ApiOperation(value = "insert", notes = "insert")
//	@GetMapping(value = "/insert")
//	public Object insert(@ApiParam(value = "{\"month\" : \"2018-11\",\"remark\" : \"备注\"}") @RequestBody Map<String, Object> map) {
//		map.forEach((k, v) -> map.put(k, map.get(k).toString()));
//		// map.put("importusername", UserUtil.getUser().getUser_name());
//		// map.put("importusername", "yj");
//		// map.put("importtime", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
//		String num = chargeBillMapper.countChargeBill(map).get(0).get("NUM").toString();
//		if (num.equals("0")) {
//			String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
//			map.put("id", uuid);
//			chargeBillMapper.insertChargeBill(map);
//		} else {
//			chargeBillMapper.updateChargeBill(map);
//		}
//		return succ();
//	}

	@ApiOperation(value = "删扣款单", notes = "删扣款单")
	@GetMapping(value = "/delete")
	public Object delete(@ApiParam(value = "{\"month\" : \"2018-11\"}") @RequestBody Map<String, Object> map) {
		List<Map<String, Object>> dataList = importRecordMapper.findAllAddress(map);
		if (dataList.size() > 0 && dataList.get(0) != null) {
			// Map<String, Object> addressMap = dataList.get(0);
			// String allAddress = addressMap.get("ADDRESSES").toString();
			// List<String> addressList = Arrays.asList(allAddress.split(","));
			String path = PropertiesUtil.properties.getProperty("xlsfilePath");
			for (Map<String, Object> data : dataList) {
				String address = data.getOrDefault("FILE_ADDRESS", "").toString();
				// for (String address : addressList) {
				File file = new File(path + address);
				if (file.exists()) {
					file.delete();
				}
			}
		}
		chargeBillMapper.deleteChargeBill(map);
		chargeBillDetailMapper.deleteChargeBillDetail(map);
		importRecordMapper.deleteImportRecord(map);
		return succ();
	}

}
