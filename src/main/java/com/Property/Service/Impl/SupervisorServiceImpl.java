package com.Property.Service.Impl;

import com.Property.Dao.DailyTaskDao;
import com.Property.Dao.OverhaulRecordDao;
import com.Property.Dao.StaffDao;
import com.Property.Dao.TicketDao;
import com.Property.Domain.DailyTask;
import com.Property.Domain.OverhaulRecord;
import com.Property.Domain.Staff;
import com.Property.Domain.Ticket;
import com.Property.Service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class SupervisorServiceImpl implements SupervisorService
{

	@Autowired
	private StaffDao staffDao;
	@Autowired
	private DailyTaskDao dailyTaskDao;
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private OverhaulRecordDao overhaulRecordDao;

	@Override
	public Staff getSelfInfo(String id)
	{
		return staffDao.getSelfInfo(id);
	}

	@Override
	public List<Staff> getDeptStaff(String dept)
	{
		return staffDao.getDeptStaff(dept);
	}

	@Override
	public List<DailyTask> getHistoryTask(String id)
	{
		return dailyTaskDao.getHistoryTask(id);
	}

	@Override
	public List<DailyTask> tbdTask(String id)
	{
		return dailyTaskDao.getHistoryTask(id);
	}

	@Override
	public int changeTaskHandler(String new_handler,String task_id)
	{
		return dailyTaskDao.changeTaskHandler(new_handler,task_id);
	}

	@Override
	public List<Ticket> getHistoryFinished(String id)
	{
		return ticketDao.getHistoryFinished(id);
	}

	@Override
	public List<Ticket> tbdTicket(String id)
	{
		return ticketDao.tbdTicket(id);
	}

	@Override
	public int changeTicketHandler(String new_handler,String ticket_id)
	{
		return ticketDao.changeTicketHandler(new_handler,ticket_id);
	}

	@Override
	public List<OverhaulRecord> overhaulHistory(String id)
	{
		return overhaulRecordDao.overhaulHistory(id);
	}

	@Override
	public List<OverhaulRecord> tbdOverhaul(String id)
	{
		return overhaulRecordDao.tbdOverhaul(id);
	}

	@Override
	public int changeOverhaulHandler(String new_handler,String overhaul_id)
	{
		return overhaulRecordDao.changeOverhaulHandler(new_handler,overhaul_id);
	}
}
