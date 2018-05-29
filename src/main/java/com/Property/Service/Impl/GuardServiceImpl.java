package com.Property.Service.Impl;

import com.Property.Dao.CarIORecordDao;
import com.Property.Dao.DailyTaskDao;
import com.Property.Dao.StaffDao;
import com.Property.Dao.TicketDao;
import com.Property.Domain.CarIORecord;
import com.Property.Domain.DailyTask;
import com.Property.Domain.Staff;
import com.Property.Domain.Ticket;
import com.Property.Service.GuardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class GuardServiceImpl implements GuardService
{

	@Autowired
	private DailyTaskDao dailyTaskDao;
	@Autowired
	private CarIORecordDao carIORecordDao;
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private StaffDao staffDao;

	@Override
	public List<DailyTask> tbdTask(String id)
	{
		return dailyTaskDao.tbdTask(id);
	}

	@Override
	public int tbdTaskCount(String id)
	{
		return dailyTaskDao.tbdTaskCount(id);
	}

	@Override
	public int finishTask(String result,Boolean isException,String task_id)
	{
		return dailyTaskDao.finishTask(result,isException,task_id);
	}

	@Override
	public List<DailyTask> getHistoryTask(String id)
	{
		return dailyTaskDao.getHistoryTask(id);
	}

	@Override
	public List<CarIORecord> getAll()
	{
		return carIORecordDao.getAll();
	}

	@Override
	public List<CarIORecord> getExternal()
	{
		return carIORecordDao.getExternal();
	}

	@Override
	public List<Ticket> tbdTicket(String id)
	{
		return ticketDao.tbdTicket(id);
	}

	@Override
	public int finishTicket(Timestamp handle_time,String result,String ticket_id)
	{
		return ticketDao.finishTicket(handle_time,result,ticket_id);
	}

	@Override
	public int staffTicket(Ticket ticket)
	{
		return ticketDao.staffTicket(ticket);
	}

	@Override
	public List<Ticket> getHistoryCreated(String id)
	{
		return ticketDao.getHistoryCreated(id);
	}

	@Override
	public List<Ticket> getHistoryFinished(String id)
	{
		return ticketDao.getHistoryFinished(id);
	}

	@Override
	public Staff getSelfInfo(String id)
	{
		return staffDao.getSelfInfo(id);
	}
}
