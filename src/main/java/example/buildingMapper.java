package example;

import com.Property.Entity.Building;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface buildingMapper
{
	@Select("select * from building")
	@Results({
			@Result(property="building_id", column="building_id", javaType=String.class),
			@Result(property="subarea_id", column="subarea_id",javaType=String.class),
			@Result(property="max_floor", column="max_floor",javaType=int.class),
			@Result(property="max_room_num", column="max_room_num",javaType=int.class),
	})
	List<Building> getAll();


}
