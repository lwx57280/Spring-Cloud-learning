package com.learn.userlike.solo.coderiver.task;

import com.learn.userlike.solo.coderiver.service.LikedService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 点赞的定时任务
 */
@Slf4j
public class LikeTask extends QuartzJobBean {

    @Autowired
    private LikedService likedService;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

//        log.info("LikeTask-------- {}", DateTimeUtil.format(new Date(), DateTimeUtil.TimeFormat.LONG_DATE_PATTERN_LINE.name()));
        log.info("LikeTask-------- {}", sdf.format(new Date()));
        // 将 Redis 里的点赞信息同步到数据库里
        likedService.transLikedFromRedis2DB();
        likedService.transLikedCountFromRedis2DB();
    }
}
