package com.Property.Service;

import com.Property.Domain.DailyTask;
import com.Property.Domain.OverhaulRecord;
import com.Property.Domain.Staff;
import com.Property.Domain.Ticket;
import org.springframework.stereotype.Component;

import java.util.List;

public interface SupervisorService {

    /*查看个人信息*/
    Staff getSelfInfo(String id);

    /*查看所属部门员工信息*/
    List<Staff> getDeptStaff(String dept);

    /*查看指定员工的每日任务完成历史*/
    List<DailyTask> getHistoryTask(String id);

    /*查看指定员工的待完成的每日任务*/
    List<DailyTask> tbdTask(String id);

    /*重新分配每日任务*/
    int changeTaskHandler(String new_handler, String task_id);

    /*查看指定员工的工单完成历史*/
    List<Ticket> getHistoryFinished(String id);

    /*查看指定员工的待完成工单*/
    List<Ticket> tbdTicket(String id);

    /*重新分配工单*/
    int changeTicketHandler(String new_handler, String ticket_id);

    /*查看指定维修工的检修历史*/
    List<OverhaulRecord> overhaulHistory(String id);

    /*查看指定维修工的待检修任务*/
    List<OverhaulRecord> tbdOverhaul(String id);

    /*重新分配检修任务*/
    int changeOverhaulHandler(String new_handler, String overhaul_id);
}
