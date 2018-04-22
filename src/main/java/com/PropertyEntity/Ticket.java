package com.PropertyEntity;

import java.sql.Timestamp;

public class Ticket
{
	private String ticket_id;
	private String ticket_type;
	private Timestamp ticket_time;
	private String initiator_id;
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
		this.ticket_id = ticket_id;
	}

	public String getTicket_type()
	{
		return ticket_type;
	}

	public void setTicket_type(String ticket_type)
	{
		this.ticket_type = ticket_type;
	}

	public Timestamp getTicket_time()
	{
		return ticket_time;
	}

	public void setTicket_time(Timestamp ticket_time)
	{
		this.ticket_time = ticket_time;
	}

	public String getInitiator_id()
	{
		return initiator_id;
	}

	public void setInitiator_id(String initiator_id)
	{
		this.initiator_id = initiator_id;
	}

	public String getHandler_id()
	{
		return handler_id;
	}

	public void setHandler_id(String handler_id)
	{
		this.handler_id = handler_id;
	}

	public Timestamp getHandle_time()
	{
		return handle_time;
	}

	public void setHandle_time(Timestamp handle_time)
	{
		this.handle_time = handle_time;
	}

	public String getTicket_result()
	{
		return ticket_result;
	}

	public void setTicket_result(String ticket_result)
	{
		this.ticket_result = ticket_result;
	}

	public int getTicket_fdbk()
	{
		return ticket_fdbk;
	}

	public void setTicket_fdbk(int ticket_fdbk)
	{
		this.ticket_fdbk = ticket_fdbk;
	}
}

