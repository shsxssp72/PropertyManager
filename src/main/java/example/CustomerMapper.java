package example;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface CustomerMapper
{
	@Select("select * from CustomerInfo where customerName = #{name}")
	Customer findUserByName(@Param("name") String name);
}