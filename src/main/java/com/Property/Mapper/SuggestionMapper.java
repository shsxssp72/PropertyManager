package com.Property.Mapper;

import com.Property.Entity.Suggestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SuggestionMapper
{
	List<Suggestion> getAll();
}
