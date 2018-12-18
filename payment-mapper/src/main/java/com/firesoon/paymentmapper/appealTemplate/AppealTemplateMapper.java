package com.firesoon.paymentmapper.appealTemplate;

import com.firesoon.dto.user.User;
import com.firesoon.paymentmapper.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface AppealTemplateMapper extends BaseMapper<User> {

	List<Map<String, Object>> findAppealTemplate(Map<String, Object> paramMap);

	void insertAppealTemplate(Map<String, Object> paramMap);

	void deleteAppealTemplate(Map<String, Object> paramMap);
	
	void updateAppealTemplate(Map<String, Object> paramMap);
}
