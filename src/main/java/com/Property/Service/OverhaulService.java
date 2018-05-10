package com.Property.Service;

import com.Property.Domain.OverhaulRecord;
import com.Property.Domain.Staff;
import com.Property.Domain.Ticket;

import java.sql.Timestamp;
import java.util.List;

public interface OverhaulService {

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

    /*完成检修任务*/
    int finishOverhaul(Timestamp overhaul_time, String result, String overhaul_id);

    /*查看待完成检修任务*/
    List<OverhaulRecord> tbdOverhaul(String id);

    /*查看检修任务历史*/
    List<OverhaulRecord> overhaulHistory(String id);
}
