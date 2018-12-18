package com.firesoon.paymentmapper.chargeBill;

import com.firesoon.dto.user.User;
import com.firesoon.paymentmapper.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ChargeBillMapper extends BaseMapper<User> {

	List<Map<String, Object>> findChargeBill(Map<String, Object> paramMap);

	void insertChargeBill(Map<String, Object> paramMap);

	void deleteChargeBill(Map<String, Object> paramMap);
	
	List<Map<String, Object>> countChargeBill(Map<String, Object> paramMap);
	
	void updateChargeBill(Map<String, Object> paramMap);
}
