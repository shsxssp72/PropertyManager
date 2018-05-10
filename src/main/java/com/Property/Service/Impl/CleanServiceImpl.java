package com.Property.Service.Impl;

import com.Property.Dao.DailyTaskDao;
import com.Property.Dao.StaffDao;
import com.Property.Dao.TicketDao;
import com.Property.Domain.DailyTask;
import com.Property.Domain.Staff;
import com.Property.Domain.Ticket;
import com.Property.Service.CleanService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

public class CleanServiceImpl implements CleanService {

    @Autowired
    private DailyTaskDao dailyTaskDao;
    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private StaffDao staffDao;

    @Override
    public List<DailyTask> tbdTask(String id) {
        return dailyTaskDao.tbdTask(id);
    }

    @Override
    public int finishTask(String result, Boolean isException, String task_id) {
        return dailyTaskDao.finishTask(result, isException, task_id);
    }

    @Override
    public List<DailyTask> getHistoryTask(String id) {
        return dailyTaskDao.getHistoryTask(id);
    }

    @Override
    public List<Ticket> tbdTicket(String id) {
        return ticketDao.tbdTicket(id);
    }

    @Override
    public int finishTicket(Timestamp handle_time, String result, String ticket_id) {
        return ticketDao.finishTicket(handle_time, result, ticket_id);
    }

    @Override
    public int staffTicket(Ticket ticket) {
        return ticketDao.staffTicket(ticket);
    }

    @Override
    public List<Ticket> getHistoryCreated(String id) {
        return ticketDao.getHistoryCreated(id);
    }

    @Override
    public List<Ticket> getHistoryFinished(String id) {
        return ticketDao.getHistoryFinished(id);
    }

    @Override
    public Staff getSelfInfo(String id) {
        return staffDao.getSelfInfo(id);
    }
}
