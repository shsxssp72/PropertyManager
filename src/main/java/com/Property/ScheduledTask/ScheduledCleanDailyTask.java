package com.Property.ScheduledTask;


import com.Property.Dao.FacilitiesDao;
import com.Property.Dao.StaffDao;
import com.Property.Dao.SubareaDao;
import com.Property.Domain.Staff;
import com.Property.Domain.Subarea;
import com.Property.Service.CleanService;
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

@RestController                                                         //每日凌晨生成新的每日清洁任务
@EnableScheduling
public class ScheduledCleanDailyTask {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future;

    @Autowired
    private SubareaDao subareaDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private CleanService cleanService;

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
    @RequestMapping("/startClean")
    public String StartClean(){
        /**
         * task:定时任务要执行的方法
         * trigger:定时任务执行的时间
         */
        future=threadPoolTaskScheduler.schedule(new ScheduledCleanDailyTask.cleanRunnable(),new CronTrigger("0 0 0 * * ?") );

        return "startClean";
    }

    /**
     * 停止定时任务
     * @return
     */
    @RequestMapping("/endClean")
    public String endClean(){
        if(future!=null){
            future.cancel(true);
        }
        System.out.println("endClean");
        return "endClean";
    }

    /**
     * 改变调度的时间
     * 步骤：
     * 1,先停止定时器
     * 2,在启动定时器
     */
    @RequestMapping("/changeClean")
    public String changeClean(){
        //停止定时器
        endClean();
        //定义新的执行时间
        future=threadPoolTaskScheduler.schedule(new ScheduledCleanDailyTask.cleanRunnable(),new CronTrigger("0 0 0 * * ?") );
        //启动定时器
        StartClean();
        System.out.println("changeTask");
        return "changeTask";
    }

    /**
     * 定义定时任务执行的方法
     * @author Admin
     *
     */
    public class cleanRunnable implements Runnable{
        @Override
        public void run() {
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

    }
}
