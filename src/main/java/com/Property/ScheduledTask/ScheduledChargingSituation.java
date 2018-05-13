package com.Property.ScheduledTask;


import com.Property.Dao.ChargingItemDao;
import com.Property.Dao.ChargingSituationDao;
import com.Property.Dao.ProprietorDao;
import com.Property.Domain.ChargingItem;
import com.Property.Domain.Proprietor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@RestController                                                              //每月15日上午10:15更新缴费记录
@EnableScheduling
public class ScheduledChargingSituation {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future;

    @Autowired
    private ChargingItemDao chargingItemDao;
    @Autowired
    private ProprietorDao proprietorDao;
    @Autowired
    private ChargingSituationDao chargingSituationDao;

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
    @RequestMapping("/startCharging")
    public String StartCharging(){
        /**
         * task:定时任务要执行的方法
         * trigger:定时任务执行的时间
         */
        future=threadPoolTaskScheduler.schedule(new ScheduledChargingSituation.chargingRunnable(),new CronTrigger("0 15 10 15 * ?") );

        return "startCharging";
    }

    /**
     * 停止定时任务
     * @return
     */
    @RequestMapping("/endCharging")
    public String endCharging(){
        if(future!=null){
            future.cancel(true);
        }
        System.out.println("endCharging");
        return "endCharging";
    }

    /**
     * 改变调度的时间
     * 步骤：
     * 1,先停止定时器
     * 2,在启动定时器
     */
    @RequestMapping("/changeCharging")
    public String changeCharging(){
        //停止定时器
        endCharging();
        //定义新的执行时间
        future=threadPoolTaskScheduler.schedule(new ScheduledChargingSituation.chargingRunnable(),new CronTrigger("0 15 10 15 * ?") );
        //启动定时器
        StartCharging();
        System.out.println("changeCharging");
        return "changeCharging";
    }

    /**
     * 定义定时任务执行的方法
     * @author Admin
     *
     */
    public class chargingRunnable implements Runnable{
        @Override
        public void run() {
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
}
