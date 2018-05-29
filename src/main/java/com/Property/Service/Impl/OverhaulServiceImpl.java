package com.Property.Service.Impl;

import com.Property.Dao.OverhaulRecordDao;
import com.Property.Dao.StaffDao;
import com.Property.Dao.TicketDao;
import com.Property.Domain.OverhaulRecord;
import com.Property.Domain.Staff;
import com.Property.Domain.Ticket;
import com.Property.Service.OverhaulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OverhaulServiceImpl implements OverhaulService
{

	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private StaffDao staffDao;
	@Autowired
	private OverhaulRecordDao overhaulRecordDao;

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

	@Override
	public int finishOverhaul(Timestamp overhaul_time,String result,String overhaul_id)
	{
		return overhaulRecordDao.finishOverhaul(overhaul_time,result,overhaul_id);
	}

	@Override
	public List<OverhaulRecord> tbdOverhaul(String id)
	{
		return overhaulRecordDao.tbdOverhaul(id);
	}

	@Override
	public int tbdOverhaulCount(String id)
	{
		return overhaulRecordDao.tbdOverhaulCount(id);
	}

	@Override
	public List<OverhaulRecord> overhaulHistory(String id)
	{
		return overhaulRecordDao.overhaulHistory(id);
	}

	@Override
	public List<Ticket> tbdTicket(String id)
	{
		return ticketDao.tbdTicket(id);
	}
}
