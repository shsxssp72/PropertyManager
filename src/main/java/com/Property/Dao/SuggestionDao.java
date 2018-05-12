package com.Property.Dao;

import com.Property.Domain.Suggestion;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface SuggestionDao
{
	List<Suggestion> getAll();

	int giveAdvice(Suggestion suggestion);

	List<Suggestion> getAdviceHistory(String id);

	int createAdvice(Suggestion suggestion);

	/*根据参数值查询*/
	List<Suggestion> getSuggestionbyParams(Map<String, Object> params);
}
