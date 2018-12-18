package com.firesoon.paymentmapper.chargeBillDetail;

import com.firesoon.dto.user.User;
import com.firesoon.paymentmapper.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ChargeBillDetailMapper extends BaseMapper<User> {

	List<Map<String, Object>> findChargeBillDetail(Map<String, Object> map);

	void insertChargeBillDetail(Map<String, Object> map);

	void deleteChargeBillDetail(Map<String, Object> map);

	void updateDepartment(Map<String, Object> map);

	void updateWrite(Map<String, Object> map);

	List<Map<String, Object>> getAllChargeDepartment(Map<String, Object> map);

	List<Map<String, Object>> getAllChargeItem(Map<String, Object> map);

	List<Map<String, Object>> getExcelTable();

	List<Map<String, Object>> getAppealMessage(Map<String, Object> map);

	List<Map<String, Object>> getSectionLevelMonth();

	List<Map<String, Object>> getAllDepartmentConfig();

	List<Map<String, Object>> getAllAppealConfig(Map<String, Object> map);

}
