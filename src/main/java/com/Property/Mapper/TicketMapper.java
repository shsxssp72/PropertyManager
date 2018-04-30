package com.Property.Mapper;

import com.Property.Entity.Ticket;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TicketMapper
{
	List<Ticket> getAll();
}
