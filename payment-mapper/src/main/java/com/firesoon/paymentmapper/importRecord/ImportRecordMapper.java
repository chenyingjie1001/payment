package com.firesoon.paymentmapper.importRecord;

import com.firesoon.dto.user.User;
import com.firesoon.paymentmapper.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ImportRecordMapper extends BaseMapper<User> {

	List<Map<String, Object>> findImportRecord(Map<String, Object> paramMap);
	
	List<Map<String, Object>> findAllAddress(Map<String, Object> paramMap);

	void insertImportRecord(Map<String, Object> paramMap);
	
	void deleteImportRecord(Map<String, Object> paramMap);

}
