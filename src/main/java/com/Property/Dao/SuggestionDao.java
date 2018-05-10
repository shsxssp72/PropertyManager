package com.Property.Dao;

import com.Property.Domain.Suggestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SuggestionDao
{
	List<Suggestion> getAll();

	int giveAdvice(Suggestion suggestion);

	List<Suggestion> getAdviceHistory(String id);

	int createAdvice(Suggestion suggestion);
}
