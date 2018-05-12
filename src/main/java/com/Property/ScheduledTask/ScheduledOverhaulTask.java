package com.Property.ScheduledTask;


import com.Property.Dao.FacilitiesDao;
import com.Property.Dao.StaffDao;
import com.Property.Domain.Facilities;
import com.Property.Domain.Staff;
import com.Property.Service.OverhaulService;
import com.Property.Utility.AssignAlgorithmUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@RestController                                                         //每月15日上午10:15更新检修任务
@EnableScheduling
public class ScheduledOverhaulTask {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future;

    @Autowired
    private StaffDao staffDao;
    @Autowired
    private FacilitiesDao facilitiesDao;
    @Autowired
    private OverhaulService overhaulService;

    @Bean
    public ThreadPoolTaskScheduler trPoolTaskScheduler(){
        return new ThreadPoolTaskScheduler();
    }
    /**
     * 1，定义一个方法实现定时任务的启动
     * 2，定义一个方法实现用于终止定时任务
     * 3，修改定时任务时间：changeCron
     */
    /**
     * 启动定时器
     * @return
     */
    @RequestMapping("/startOverhaul")
    public String StartOverhaul(){
        /**
         * task:定时任务要执行的方法
         * trigger:定时任务执行的时间
         */
        future=threadPoolTaskScheduler.schedule(new ScheduledOverhaulTask.overhaulRunnable(),new CronTrigger("0 15 10 15 * ?") );

        return "startOverhaul";
    }

    /**
     * 停止定时任务
     * @return
     */
    @RequestMapping("/endOverhaul")
    public String endOverhaul(){
        if(future!=null){
            future.cancel(true);
        }
        System.out.println("endOverhaul");
        return "endOverhaul";
    }

    /**
     * 改变调度的时间
     * 步骤：
     * 1,先停止定时器
     * 2,在启动定时器
     */
    @RequestMapping("/changeOverhaul")
    public String changeOverhaul(){
        //停止定时器
        endOverhaul();
        //定义新的执行时间
        future=threadPoolTaskScheduler.schedule(new ScheduledOverhaulTask.overhaulRunnable(),new CronTrigger("0 15 10 15 * ?") );
        //启动定时器
        StartOverhaul();
        System.out.println("changeOverhaul");
        return "changeOverhaul";
    }

    /**
     * 定义定时任务执行的方法
     * @author Admin
     *
     */
    public class overhaulRunnable implements Runnable{
        @Override
        public void run() {
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
    }
}
