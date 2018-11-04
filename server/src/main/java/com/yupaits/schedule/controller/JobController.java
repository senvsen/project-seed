package com.yupaits.schedule.controller;

import com.google.common.collect.Lists;
import com.yupaits.schedule.entity.Job;
import com.yupaits.schedule.helper.ScheduleJobHelper;
import com.yupaits.schedule.service.IJobService;
import com.yupaits.schedule.vo.JobVO;
import com.yupaits.schedule.dto.JobCreate;
import com.yupaits.schedule.dto.JobUpdate;
import com.yupaits.commons.utils.ValidateUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultWrapper;
import com.yupaits.commons.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 定时任务 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-11-03
 */
@Slf4j
@Api(tags = "定时任务接口")
@RestController
@RequestMapping("/schedule/job")
public class JobController {

    private final IJobService jobService;
    private final Scheduler scheduler;

    @Autowired
    public JobController(IJobService jobService, Scheduler quartzScheduler) {
        this.jobService = jobService;
        this.scheduler = quartzScheduler;
    }

    @ApiOperation("创建定时任务")
    @PostMapping("")
    public Result addJob(@RequestBody JobCreate jobCreate) {
        if (!jobCreate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        Job job = new Job();
        BeanUtils.copyProperties(jobCreate, job);
        if (jobService.save(job)) {
            ScheduleJobHelper.createScheduleJob(scheduler, job);
            return ResultWrapper.success();
        }
        return ResultWrapper.fail(ResultCode.CREATE_FAIL);
    }

    @ApiOperation("编辑定时任务")
    @PutMapping("/{id:\\d{19}}")
    public Result updateJob(@RequestBody JobUpdate jobUpdate) {
        if (!jobUpdate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        Job job = new Job();
        BeanUtils.copyProperties(jobUpdate, job);
        if (jobService.updateById(job)) {
            ScheduleJobHelper.updateScheduleJob(scheduler, job);
            return ResultWrapper.success();
        }
        return ResultWrapper.fail(ResultCode.UPDATE_FAIL);
    }

    @ApiOperation("批量保存定时任务")
    @PutMapping("/batch-save")
    public Result batchSaveJob(@RequestBody List<JobUpdate> jobUpdateList) {
        if (!JobUpdate.isValid(jobUpdateList)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        List<Long> jobIds = Lists.newArrayList();
        List<Job> jobList = jobUpdateList.stream().map(jobUpdate -> {
            Job job = new Job();
            BeanUtils.copyProperties(jobUpdate, job);
            jobIds.add(jobUpdate.getId());
            return job;
        }).collect(Collectors.toList());
        if (jobService.saveOrUpdateBatch(jobList)) {
            jobService.listByIds(jobIds).forEach(job -> ScheduleJobHelper.updateScheduleJob(scheduler, job));
            return ResultWrapper.success();
        }
        return ResultWrapper.fail(ResultCode.SAVE_FAIL);
    }

    @ApiOperation("根据ID删除定时任务")
    @DeleteMapping("/{id:\\d{19}}")
    public Result deleteJob(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        Job job = jobService.getById(id);
        if (jobService.removeById(id)) {
            if (job != null) {
                ScheduleJobHelper.deleteJob(scheduler, job);
            }
            return ResultWrapper.success();
        }
        return ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("批量删除定时任务")
    @PutMapping("/batch-delete")
    public Result batchDeleteJob(@RequestBody List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        List<Job> jobList = (List<Job>) jobService.listByIds(ids);
        if (jobService.removeByIds(ids)) {
            jobList.forEach(job -> ScheduleJobHelper.deleteJob(scheduler, job));
            return ResultWrapper.success();
        }
        return ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("根据ID获取定时任务信息")
    @GetMapping("/{id:\\d{19}}")
    public Result getJob(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        Job job = jobService.getById(id);
        if (job == null) {
            return ResultWrapper.fail(ResultCode.DATA_NOT_FOUND);
        }
        JobVO jobVO = new JobVO();
        BeanUtils.copyProperties(job, jobVO);
        return ResultWrapper.success(jobVO);
    }

    @ApiOperation("按条件获取定时任务列表")
    @PostMapping("/list")
    public Result getJobList(@RequestBody(required = false) Map<String, Object> query) {
        QueryWrapper<Job> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                //TODO 设置查询条件
            });
        }
        List<JobVO> jobVOList = jobService.list(queryWrapper).stream().map(job -> {
            JobVO jobVO = new JobVO();
            BeanUtils.copyProperties(job, jobVO);
            return jobVO;
        }).collect(Collectors.toList());
        return ResultWrapper.success(jobVOList);
    }

    @ApiOperation("获取定时任务分页信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "当前页码", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", defaultValue = "10", dataType = "int", paramType = "query")})
    @PostMapping("/page")
    public Result getJobPage(@RequestParam(required = false, defaultValue = "1") int page,
                             @RequestParam(required = false, defaultValue = "10") int size,
                             @RequestParam(required = false) @ApiParam(value = "降序字段") List<String> descs,
                             @RequestParam(required = false) @ApiParam(value = "升序字段") List<String> ascs,
                             @RequestBody(required = false) Map<String, Object> query) {
        Page<Job> pager = new Page<>(page, size);
        if (CollectionUtils.isNotEmpty(descs)) {
            pager.setDescs(descs);
        }
        if (CollectionUtils.isNotEmpty(ascs)) {
            pager.setAscs(ascs);
        }
        QueryWrapper<Job> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                //TODO 设置查询条件
                if (StringUtils.equals(key, "id") && StringUtils.isNotBlank(String.valueOf(value))) {
                    queryWrapper.eq("id", value);
                }
                if (StringUtils.equals(key, "keyword")) {
                    queryWrapper.and(i -> i.like("class_name", value).or().like("job_name", value)
                            .or().like("job_group", value).or().like("trigger_name", value)
                            .or().like("trigger_group", value));
                }
                if (StringUtils.equals(key, "paused") && value != null) {
                    queryWrapper.eq("paused", value);
                }
            });
        }
        IPage<JobVO> jobVOPage = new Page<>();
        BeanUtils.copyProperties(jobService.page(pager, queryWrapper), jobVOPage);
        jobVOPage.setRecords(pager.getRecords().stream().map(job -> {
            JobVO jobVO = new JobVO();
            BeanUtils.copyProperties(job, jobVO);
            return jobVO;
        }).collect(Collectors.toList()));
        return ResultWrapper.success(jobVOPage);
    }

    @ApiOperation("暂停定时任务")
    @PutMapping("/{id:\\d{19}}/pause")
    public Result pauseJob(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        Job job = jobService.getById(id);
        if (job == null) {
            return ResultWrapper.fail(ResultCode.DATA_NOT_FOUND);
        }
        job.setPaused(true);
        jobService.updateById(job);
        ScheduleJobHelper.pauseJob(scheduler, job);
        return ResultWrapper.success();
    }

    @ApiOperation("恢复定时任务")
    @PutMapping("/{id:\\d{19}}/resume")
    public Result resumeJob(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        Job job = jobService.getById(id);
        if (job == null) {
            return ResultWrapper.fail(ResultCode.DATA_NOT_FOUND);
        }
        job.setPaused(false);
        jobService.updateById(job);
        ScheduleJobHelper.resumeJob(scheduler, job);
        return ResultWrapper.success();
    }

}