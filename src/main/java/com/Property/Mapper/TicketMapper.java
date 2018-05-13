package com.Property.Mapper;

import com.Property.Entity.Ticket;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TicketMapper
{
	List<Ticket> getAll();
}
