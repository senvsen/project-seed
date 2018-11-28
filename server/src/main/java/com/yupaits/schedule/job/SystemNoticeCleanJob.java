package com.yupaits.schedule.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupaits.msg.entity.SystemNotice;
import com.yupaits.msg.service.ISystemNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yupaits
 * @date 2018/11/28
 */
@Slf4j
@Component
public class SystemNoticeCleanJob implements Job {

    @Autowired
    private ISystemNoticeService systemNoticeService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<SystemNotice> noticeList = systemNoticeService.list(new QueryWrapper<SystemNotice>().lt("end_time", LocalDateTime.now()));
        if (CollectionUtils.isNotEmpty(noticeList)) {
            log.info("开始清理过期的系统通知");
            systemNoticeService.removeByIds(noticeList.stream().map(SystemNotice::getId).collect(Collectors.toList()));
            log.info("清理过期的系统通知完成, 清理数量: {}", noticeList.size());
        }
    }
}
