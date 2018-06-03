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

	Suggestion getByID(String inputID);

	int giveAdvice(Suggestion suggestion);

	List<Suggestion> getAdviceHistory(String id);

	int createAdvice(Suggestion suggestion);

	/*根据参数值查询*/
	List<Suggestion> getSuggestionbyParams(Map<String, Object> params);

	int addSuggestion(Suggestion suggestion);

	int deleteSuggestion(String id);

	int updateSuggestion(Suggestion suggestion);

	//用于判定ID是否重复
	int getIdCount(String inputID);
}
