package com.Property.Entity;

import java.sql.Timestamp;

public class DailyTask
{
	private String task_id;
	private String task_type;
	private Timestamp task_time;
	private String task_area;
	private String task_handler;
	private String task_result;
	private boolean isException;

	public String getTask_id()
	{
		return task_id;
	}

	public void setTask_id(String task_id)
	{
		this.task_id = task_id;
	}

	public String getTask_type()
	{
		return task_type;
	}

	public void setTask_type(String task_type)
	{
		this.task_type = task_type;
	}

	public Timestamp getTask_time()
	{
		return task_time;
	}

	public void setTask_time(Timestamp task_time)
	{
		this.task_time = task_time;
	}

	public String getTask_area()
	{
		return task_area;
	}

	public void setTask_area(String task_area)
	{
		this.task_area = task_area;
	}

	public String getTask_handler()
	{
		return task_handler;
	}

	public void setTask_handler(String task_handler)
	{
		this.task_handler = task_handler;
	}

	public String getTask_result()
	{
		return task_result;
	}

	public void setTask_result(String task_result)
	{
		this.task_result = task_result;
	}

	public boolean isException()
	{
		return isException;
	}

	public void setException(boolean exception)
	{
		isException=exception;
	}
}
