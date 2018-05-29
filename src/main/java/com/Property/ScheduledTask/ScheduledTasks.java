package com.Property.ScheduledTask;

import com.Property.Dao.*;
import com.Property.Domain.*;
import com.Property.Service.CleanService;
import com.Property.Service.GuardService;
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


@RestController                                                         //每日凌晨生成新的警卫每日任务
@EnableScheduling
public class ScheduledTasks
{

	@Autowired
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;

	private ScheduledFuture<?> future;

	@Autowired
	private SubareaDao subareaDao;
	@Autowired
	private StaffDao staffDao;
	@Autowired
	private GuardService guardService;
	@Autowired
	private CleanService cleanService;
	@Autowired
	private ChargingItemDao chargingItemDao;
	@Autowired
	private ProprietorDao proprietorDao;
	@Autowired
	private ChargingSituationDao chargingSituationDao;
	@Autowired
	private FacilitiesDao facilitiesDao;
	@Autowired
	private OverhaulService overhaulService;

	@Bean
	public ThreadPoolTaskScheduler trPoolTaskScheduler()
	{
		return new ThreadPoolTaskScheduler();
	}
	/**
	 * 1，定义一个方法实现定时任务的启动
	 * 2，定义一个方法实现用于终止定时任务
	 * 3，修改定时任务时间：changeCron
	 */
	/**
	 * 启动定时器
	 *
	 * @return
	 */
	@RequestMapping("/startGuard")
	public String StartGuard()
	{
		/**
		 * task:定时任务要执行的方法
		 * trigger:定时任务执行的时间
		 */
		future=threadPoolTaskScheduler.schedule(new ScheduledTasks.guardRunnable(),new CronTrigger("0 0 0 * * ?"));

		return "startGuard";
	}

	@RequestMapping("/startClean")
	public String StartClean()
	{
		/**
		 * task:定时任务要执行的方法
		 * trigger:定时任务执行的时间
		 */
		future=threadPoolTaskScheduler.schedule(new ScheduledTasks.cleanRunnable(),new CronTrigger("0 0 0 * * ?"));

		return "startClean";
	}

	@RequestMapping("/startCharging")
	public String StartCharging()
	{
		/**
		 * task:定时任务要执行的方法
		 * trigger:定时任务执行的时间
		 */
		future=threadPoolTaskScheduler
				.schedule(new ScheduledTasks.chargingRunnable(),new CronTrigger("0 15 10 15 * ?"));

		return "startCharging";
	}

	@RequestMapping("/startOverhaul")
	public String StartOverhaul()
	{
		/**
		 * task:定时任务要执行的方法
		 * trigger:定时任务执行的时间
		 */
		future=threadPoolTaskScheduler
				.schedule(new ScheduledTasks.overhaulRunnable(),new CronTrigger("0 15 10 15 * ?"));

		return "startOverhaul";
	}

	/**
	 * 停止定时任务
	 *
	 * @return
	 */
	@RequestMapping("/endGuard")
	public String endGuard()
	{
		if(future!=null)
		{
			future.cancel(true);
		}
		System.out.println("endGuard");
		return "endGuard";
	}

	@RequestMapping("/endClean")
	public String endClean()
	{
		if(future!=null)
		{
			future.cancel(true);
		}
		System.out.println("endClean");
		return "endClean";
	}

	@RequestMapping("/endCharging")
	public String endCharging()
	{
		if(future!=null)
		{
			future.cancel(true);
		}
		System.out.println("endCharging");
		return "endCharging";
	}

	@RequestMapping("/endOverhaul")
	public String endOverhaul()
	{
		if(future!=null)
		{
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
	@RequestMapping("/changeGuard")
	public String changeGuard()
	{
		//停止定时器
		endGuard();
		//定义新的执行时间
		future=threadPoolTaskScheduler.schedule(new ScheduledTasks.guardRunnable(),new CronTrigger("0 0 0 * * ?"));
		//启动定时器
		StartGuard();
		System.out.println("changeGuard");
		return "changeGuard";
	}

	@RequestMapping("/changeClean")
	public String changeClean()
	{
		//停止定时器
		endClean();
		//定义新的执行时间
		future=threadPoolTaskScheduler.schedule(new ScheduledTasks.cleanRunnable(),new CronTrigger("0 0 0 * * ?"));
		//启动定时器
		StartClean();
		System.out.println("changeTask");
		return "changeTask";
	}

	@RequestMapping("/changeCharging")
	public String changeCharging()
	{
		//停止定时器
		endCharging();
		//定义新的执行时间
		future=threadPoolTaskScheduler
				.schedule(new ScheduledTasks.chargingRunnable(),new CronTrigger("0 15 10 15 * ?"));
		//启动定时器
		StartCharging();
		System.out.println("changeCharging");
		return "changeCharging";
	}

	@RequestMapping("/changeOverhaul")
	public String changeOverhaul()
	{
		//停止定时器
		endOverhaul();
		//定义新的执行时间
		future=threadPoolTaskScheduler
				.schedule(new ScheduledTasks.overhaulRunnable(),new CronTrigger("0 15 10 15 * ?"));
		//启动定时器
		StartOverhaul();
		System.out.println("changeOverhaul");
		return "changeOverhaul";
	}

	/**
	 * 定义定时任务执行的方法
	 *
	 * @author Admin
	 */
	public class guardRunnable implements Runnable
	{
		@Override
		public void run()
		{
			int task_num=0;
			int rev_num=0;
			List<Subarea> subareas=subareaDao.getAll();
			String[] task=new String[subareas.size()];
			for(Subarea s : subareas)
			{
				task[task_num]=s.getSubarea_id();
				task_num++;
			}
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("position","guard");
			List<Staff> staff=staffDao.getStaffbyParams(params);
			String[][] rev_task=new String[staff.size()][3];
			for(Staff s : staff)
			{
				rev_task[rev_num][0]=s.getStaff_id();
				rev_task[rev_num][1]=String.valueOf(guardService.tbdTaskCount(s.getStaff_id()));
				rev_num++;
			}

			AssignAlgorithmUtil.taskAllocation(task_num,rev_num,rev_task);

			int k=0;
			for(int i=0;i<rev_num;i++)
			{
				for(int j=0;j<Integer.parseInt(rev_task[i][2]);j++)
				{
					//生成新的巡逻任务
					k++;
				}
			}
		}
	}

	public class cleanRunnable implements Runnable
	{
		@Override
		public void run()
		{
			int task_num=0;
			int rev_num=0;
			List<Subarea> subareas=subareaDao.getAll();
			String[] task=new String[subareas.size()];
			for(Subarea s : subareas)
			{
				task[task_num]=s.getSubarea_id();
				task_num++;
			}
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("position","cleaner");
			List<Staff> staff=staffDao.getStaffbyParams(params);
			String[][] rev_task=new String[staff.size()][3];
			for(Staff s : staff)
			{
				rev_task[rev_num][0]=s.getStaff_id();
				rev_task[rev_num][1]=String.valueOf(cleanService.tbdTaskCount(s.getStaff_id()));
				rev_num++;
			}

			AssignAlgorithmUtil.taskAllocation(task_num,rev_num,rev_task);

			int k=0;
			for(int i=0;i<rev_num;i++)
			{
				for(int j=0;j<Integer.parseInt(rev_task[i][2]);j++)
				{
					//生成新的清洁任务
					k++;
				}
			}
		}

	}

	public class chargingRunnable implements Runnable
	{
		@Override
		public void run()
		{
			List<ChargingItem> chargingItems=chargingItemDao.getAll();
			List<Proprietor> proprietors=proprietorDao.getAll();

			for(ChargingItem c : chargingItems)
			{
				for(Proprietor p : proprietors)
				{
					Date now=new Date();
					Date latest=chargingSituationDao.getLatestPayment(p.getPrprt_id(),c.getItem_id());
					if(latest==null)
					{
						//生成缴费记录
					}
					else
					{
						long nd=1000*24*60*60;
						long diff=latest.getTime()-now.getTime();
						long day=diff/nd;
						if(day<=30)
						{
							//生成缴费记录
						}
					}
				}
			}
		}

	}

	public class overhaulRunnable implements Runnable
	{
		@Override
		public void run()
		{
			int task_num=0;
			int rev_num=0;
			List<Facilities> facilities=facilitiesDao.getAll();
			String[] task=new String[facilities.size()];
			for(Facilities f : facilities)
			{
				task[task_num]=f.getFclt_id();
				task_num++;
			}
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("position","repairman");
			List<Staff> staff=staffDao.getStaffbyParams(params);
			String[][] rev_task=new String[staff.size()][3];
			for(Staff s : staff)
			{
				rev_task[rev_num][0]=s.getStaff_id();
				rev_task[rev_num][1]=String.valueOf(overhaulService.tbdOverhaulCount(s.getStaff_id()));
				rev_num++;
			}

			AssignAlgorithmUtil.taskAllocation(task_num,rev_num,rev_task);

			int k=0;
			for(int i=0;i<rev_num;i++)
			{
				for(int j=0;j<Integer.parseInt(rev_task[i][2]);j++)
				{
					//生成新的检修任务
					k++;
				}
			}
		}
	}
}
