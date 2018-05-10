package com.Property.Service;

import com.Property.Domain.DailyTask;
import com.Property.Domain.Staff;
import com.Property.Domain.Ticket;

import java.sql.Timestamp;
import java.util.List;

public interface CleanService {

    /*查看待完成任务*/
    List<DailyTask> tbdTask(String id);

    /*完成日常任务*/
    int finishTask(String result, Boolean isException, String task_id);

    /*查看历史任务*/
    List<DailyTask> getHistoryTask(String id);

    /*查看待完成工单*/
    List<Ticket> tbdTicket(String id);

    /*完成工单*/
    int finishTicket(Timestamp handle_time, String result, String ticket_id);

    /*发起工单*/
    int staffTicket(Ticket ticket);

    /*查看发起工单历史*/
    List<Ticket> getHistoryCreated(String id);

    /*查看完成工单历史*/
    List<Ticket> getHistoryFinished(String id);

    /*查看个人信息*/
    Staff getSelfInfo(String id);
}
