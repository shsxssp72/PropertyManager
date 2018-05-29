

/*static scheduled task*/



/*
package com.Property.ScheduledTask;

import com.Property.Dao.*;
import com.Property.Domain.*;
import com.Property.Service.CleanService;
import com.Property.Service.GuardService;
import com.Property.Service.OverhaulService;
import com.Property.Service.ProprietorService;
import com.Property.Utility.AssignAlgorithmUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScheduledService {

    @Autowired
    private SubareaDao subareaDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private FacilitiesDao facilitiesDao;
    @Autowired
    private GuardService guardService;
    @Autowired
    private CleanService cleanService;
    @Autowired
    private OverhaulService overhaulService;
    @Autowired
    private DailyTaskDao dailyTaskDao;
    @Autowired
    private ChargingItemDao chargingItemDao;
    @Autowired
    private ProprietorDao proprietorDao;
    @Autowired
    private ChargingSituationDao chargingSituationDao;
    @Autowired
    private ProprietorService proprietorService;

    @Async                                                                     //每日凌晨生成新的警卫每日任务
    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduledGuardDailyTask(){

        int task_num = 0;
        int rev_num = 0;
        List<Subarea> subareas = subareaDao.getAll();
        String[] task = new String[subareas.size()];
        for(Subarea s : subareas){
            task[task_num] = s.getSubarea_id();
            task_num++;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("position", "guard");
        List<Staff> staff = staffDao.getStaffbyParams(params);
        String[][] rev_task = new String[staff.size()][3];
        for (Staff s : staff){
            rev_task[rev_num][0] = s.getStaff_id();
            rev_task[rev_num][1] = String.valueOf(guardService.tbdTaskCount(s.getStaff_id()));
            rev_num++;
        }

        AssignAlgorithmUtil.taskAllocation(task_num, rev_num, rev_task);

        int k=0;
        for (int i =0; i<rev_num; i++){
            for (int j =0; j<Integer.parseInt(rev_task[i][2]); j++){
                //生成新的巡逻任务
                k++;
            }
        }
    }

    @Async                                                                         //每日凌晨生成新的每日清洁任务
    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduledCleanDailyTask(){

        int task_num = 0;
        int rev_num = 0;
        List<Subarea> subareas = subareaDao.getAll();
        String[] task = new String[subareas.size()];
        for(Subarea s : subareas){
            task[task_num] = s.getSubarea_id();
            task_num++;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("position", "cleaner");
        List<Staff> staff = staffDao.getStaffbyParams(params);
        String[][] rev_task = new String[staff.size()][3];
        for (Staff s : staff){
            rev_task[rev_num][0] = s.getStaff_id();
            rev_task[rev_num][1] = String.valueOf(cleanService.tbdTaskCount(s.getStaff_id()));
            rev_num++;
        }

        AssignAlgorithmUtil.taskAllocation(task_num, rev_num, rev_task);

        int k=0;
        for (int i =0; i<rev_num; i++){
            for (int j =0; j<Integer.parseInt(rev_task[i][2]); j++){
                //生成新的清洁任务
                k++;
            }
        }
    }

    @Async                                                                     //每月15日上午10:15更新检修任务
    @Scheduled(cron = "0 15 10 15 * ?")
    public void scheduledOverhaulTask(){
        int task_num = 0;
        int rev_num = 0;
        List<Facilities> facilities = facilitiesDao.getAll();
        String[] task = new String[facilities.size()];
        for(Facilities f : facilities){
            task[task_num] = f.getFclt_id();
            task_num++;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("position", "repairman");
        List<Staff> staff = staffDao.getStaffbyParams(params);
        String[][] rev_task = new String[staff.size()][3];
        for (Staff s : staff){
            rev_task[rev_num][0] = s.getStaff_id();
            rev_task[rev_num][1] = String.valueOf(overhaulService.tbdOverhaulCount(s.getStaff_id()));
            rev_num++;
        }

        AssignAlgorithmUtil.taskAllocation(task_num, rev_num, rev_task);

        int k=0;
        for (int i =0; i<rev_num; i++){
            for (int j =0; j<Integer.parseInt(rev_task[i][2]); j++){
                //生成新的检修任务
                k++;
            }
        }
    }

    @Async                                                                  //每月15日上午10:15更新缴费记录
    @Scheduled(cron = "0 15 10 15 * ?")
    public void scheduledChargingSituation(){
        List<ChargingItem> chargingItems = chargingItemDao.getAll();
        List<Proprietor> proprietors = proprietorDao.getAll();

        for (ChargingItem c : chargingItems){
            for (Proprietor p : proprietors){
                Date now = new Date();
                Date latest = chargingSituationDao.getLatestPayment(p.getPrprt_id(), c.getItem_id());
                if (latest==null){
                    //生成缴费记录
                }else{
                    long nd = 1000 * 24 * 60 * 60;
                    long diff = latest.getTime() - now.getTime();
                    long day = diff / nd;
                    if(day<=30){
                        //生成缴费记录
                    }
                }
            }
        }
    }

}
*/
