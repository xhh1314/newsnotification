package cn.haiwai.newsnotification.manage.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import cn.haiwai.newsnotification.dao.ContentDao;

/**
 * 定时查询数据库，保证数据库连接不断开 实现ApplicationRunner类，在springboot项目启动完成后执行代码
 * 注意：mysql查询异常会阻塞任务的执行
 * 
 * @author Administrator
 * @date 2017年12月1日
 * @version 1.0
 */
@Component
public class TimingSelectMysql implements Runnable, ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(TimingSelectMysql.class);
	@Autowired
	private ContentDao contentDao;
	private ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
	private final long initialDelay = 1;
	private final long period = 240;

	public void startTask() {
		logger.info("初始化查询mysql任务{}", this.getClass());
		schedule.scheduleAtFixedRate(this, initialDelay, period, TimeUnit.MINUTES);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		logger.info("执行定时查询mysql！");
		contentDao.countContent();

	}


	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		startTask();
	}

}
