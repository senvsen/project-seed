package com.yupaits.schedule.service.impl;

import com.yupaits.schedule.entity.Job;
import com.yupaits.schedule.mapper.JobMapper;
import com.yupaits.schedule.service.IJobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定时任务 服务实现类
 * </p>
 *
 * @author yupaits
 * @since 2018-11-03
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements IJobService {

}
