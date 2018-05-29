package com.Property.Dao;

import com.Property.Domain.Ticket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.eclipse.jetty.util.DateCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface TicketDao
{
	List<Ticket> getAll();

	int prprtCallRepair(Ticket ticket);

	List<Ticket> getRepairHistory(String id);

	int updateRepairFB(@Param("feedback") int feedback,@Param("prprt_id") String prprt_id,
					   @Param("ticket_id") String ticket_id);

	int finishTicket(@Param("handle_time") Timestamp handle_time,@Param("result") String result,
					 @Param("ticket_id") String ticket_id);

	int staffTicket(Ticket ticket);

	List<Ticket> getHistoryCreated(String id);

	List<Ticket> getHistoryFinished(String id);

	List<Ticket> tbdTicket(String id);

	int changeTicketHandler(@Param("new_handler") String new_handler,@Param("ticket_id") String ticket_id);

	/*根据参数值查询*/
	List<Ticket> getTicketbyParams(Map<String,Object> params);

	int addTicket(Ticket ticket);

	int deleteTicket(String id);

	int updateTicket(Ticket ticket);
}
