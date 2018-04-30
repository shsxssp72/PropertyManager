package com.Property.Entity;

import java.sql.Timestamp;

public class Ticket
{
	private String ticket_id;
	private String ticket_type;
	private Timestamp ticket_time;
	private String initiator_prprt_id;
	private String initiator_staff_id;
	private String subarea_id;
	private String aprt_building;
	private int aprt_floor;
	private int aprt_room_num;
	private String description;
	private String handler_id;
	private Timestamp handle_time;
	private String ticket_result;
	private int ticket_fdbk;

	public String getTicket_id()
	{
		return ticket_id;
	}

	public void setTicket_id(String ticket_id)
	{
		this.ticket_id=ticket_id;
	}

	public String getTicket_type()
	{
		return ticket_type;
	}

	public void setTicket_type(String ticket_type)
	{
		this.ticket_type=ticket_type;
	}

	public Timestamp getTicket_time()
	{
		return ticket_time;
	}

	public void setTicket_time(Timestamp ticket_time)
	{
		this.ticket_time=ticket_time;
	}

	public String getInitiator_prprt_id()
	{
		return initiator_prprt_id;
	}

	public void setInitiator_prprt_id(String initiator_prprt_id)
	{
		this.initiator_prprt_id=initiator_prprt_id;
	}

	public String getInitiator_staff_id()
	{
		return initiator_staff_id;
	}

	public void setInitiator_staff_id(String initiator_staff_id)
	{
		this.initiator_staff_id=initiator_staff_id;
	}

	public String getSubarea_id()
	{
		return subarea_id;
	}

	public void setSubarea_id(String subarea_id)
	{
		this.subarea_id=subarea_id;
	}

	public String getAprt_building()
	{
		return aprt_building;
	}

	public void setAprt_building(String aprt_building)
	{
		this.aprt_building=aprt_building;
	}

	public int getAprt_floor()
	{
		return aprt_floor;
	}

	public void setAprt_floor(int aprt_floor)
	{
		this.aprt_floor=aprt_floor;
	}

	public int getAprt_room_num()
	{
		return aprt_room_num;
	}

	public void setAprt_room_num(int aprt_room_num)
	{
		this.aprt_room_num=aprt_room_num;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description=description;
	}

	public String getHandler_id()
	{
		return handler_id;
	}

	public void setHandler_id(String handler_id)
	{
		this.handler_id=handler_id;
	}

	public Timestamp getHandle_time()
	{
		return handle_time;
	}

	public void setHandle_time(Timestamp handle_time)
	{
		this.handle_time=handle_time;
	}

	public String getTicket_result()
	{
		return ticket_result;
	}

	public void setTicket_result(String ticket_result)
	{
		this.ticket_result=ticket_result;
	}

	public int getTicket_fdbk()
	{
		return ticket_fdbk;
	}

	public void setTicket_fdbk(int ticket_fdbk)
	{
		this.ticket_fdbk=ticket_fdbk;
	}
}

