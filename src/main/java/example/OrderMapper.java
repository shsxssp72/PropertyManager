package example;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface OrderMapper
{
	@Select("select * from OrderInfo where orderID = #{ID}")
	Order getByID(@Param("ID") int ID);
}
