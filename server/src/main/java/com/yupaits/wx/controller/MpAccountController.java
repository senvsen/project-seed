package com.yupaits.wx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultCode;
import com.yupaits.commons.result.ResultWrapper;
import com.yupaits.commons.utils.ValidateUtils;
import com.yupaits.wx.dto.MpAccountCreate;
import com.yupaits.wx.dto.MpAccountUpdate;
import com.yupaits.wx.entity.MpAccount;
import com.yupaits.wx.service.IMpAccountService;
import com.yupaits.wx.vo.MpAccountVO;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 微信公众号账户 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-11-12
 */
@Slf4j
@Api(tags = "微信公众号账户接口")
@RestController
@RequestMapping("/wx/mp-account")
public class MpAccountController {

    private final IMpAccountService mpAccountService;

    @Autowired
    public MpAccountController(IMpAccountService mpAccountService) {
        this.mpAccountService = mpAccountService;
    }

    @ApiOperation("创建微信公众号账户")
    @PostMapping("")
    public Result addMpAccount(@RequestBody MpAccountCreate mpAccountCreate) {
        if (!mpAccountCreate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        if (mpAccountService.count(new QueryWrapper<MpAccount>().eq("account_name", mpAccountCreate.getAccountName())) > 0) {
            return ResultWrapper.fail(ResultCode.DATA_CONFLICT);
        }
        MpAccount mpAccount = new MpAccount();
        BeanUtils.copyProperties(mpAccountCreate, mpAccount);
        return mpAccountService.save(mpAccount) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.CREATE_FAIL);
    }

    @ApiOperation("编辑微信公众号账户")
    @PutMapping("/{id:\\d{19}}")
    public Result updateMpAccount(@RequestBody MpAccountUpdate mpAccountUpdate) {
        if (!mpAccountUpdate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        if (mpAccountService.count(new QueryWrapper<MpAccount>().ne("id", mpAccountUpdate.getId()).eq("account_name", mpAccountUpdate.getAccountName())) > 0) {
            return ResultWrapper.fail(ResultCode.DATA_CONFLICT);
        }
        MpAccount mpAccount = new MpAccount();
        BeanUtils.copyProperties(mpAccountUpdate, mpAccount);
        return mpAccountService.updateById(mpAccount) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.UPDATE_FAIL);
    }

    @ApiOperation("批量保存微信公众号账户")
    @PutMapping("/batch-save")
    public Result batchSaveMpAccount(@RequestBody List<MpAccountUpdate> mpAccountUpdateList) {
        if (!MpAccountUpdate.isValid(mpAccountUpdateList)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        List<MpAccount> mpAccountList = mpAccountUpdateList.stream().map(mpAccountUpdate -> {
            MpAccount mpAccount = new MpAccount();
            BeanUtils.copyProperties(mpAccountUpdate, mpAccount);
            return mpAccount;
        }).collect(Collectors.toList());
        return mpAccountService.saveOrUpdateBatch(mpAccountList) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.SAVE_FAIL);
    }

    @ApiOperation("根据ID删除微信公众号账户")
    @DeleteMapping("/{id:\\d{19}}")
    public Result deleteMpAccount(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return mpAccountService.removeById(id) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("批量删除微信公众号账户")
    @PutMapping("/batch-delete")
    public Result batchDeleteMpAccount(@RequestBody List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return mpAccountService.removeByIds(ids) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("根据ID获取微信公众号账户信息")
    @GetMapping("/{id:\\d{19}}")
    public Result getMpAccount(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        MpAccount mpAccount = mpAccountService.getById(id);
        if (mpAccount == null) {
            return ResultWrapper.fail(ResultCode.DATA_NOT_FOUND);
        }
        MpAccountVO mpAccountVO = new MpAccountVO();
        BeanUtils.copyProperties(mpAccount, mpAccountVO);
        return ResultWrapper.success(mpAccountVO);
    }

    @ApiOperation("按条件获取微信公众号账户列表")
    @PostMapping("/list")
    public Result getMpAccountList(@RequestBody(required = false) Map<String, Object> query) {
        QueryWrapper<MpAccount> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                //TODO 设置查询条件
            });
        }
        List<MpAccountVO> mpAccountVOList = mpAccountService.list(queryWrapper).stream().map(mpAccount -> {
            MpAccountVO mpAccountVO = new MpAccountVO();
            BeanUtils.copyProperties(mpAccount, mpAccountVO);
            return mpAccountVO;
        }).collect(Collectors.toList());
        return ResultWrapper.success(mpAccountVOList);
    }

    @ApiOperation("获取微信公众号账户分页信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "当前页码", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", defaultValue = "10", dataType = "int", paramType = "query")})
    @PostMapping("/page")
    public Result getMpAccountPage(@RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "10") int size,
                              @RequestParam(required = false) @ApiParam(value = "降序字段") List<String> descs,
                              @RequestParam(required = false) @ApiParam(value = "升序字段") List<String> ascs,
                              @RequestBody(required = false) Map<String, Object> query) {
        Page<MpAccount> pager = new Page<>(page, size);
        if (CollectionUtils.isNotEmpty(descs)) {
            pager.setDescs(descs);
        }
        if (CollectionUtils.isNotEmpty(ascs)) {
            pager.setAscs(ascs);
        }
        QueryWrapper<MpAccount> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                if (StringUtils.equals(key, "id")) {
                    queryWrapper.eq("id", value);
                }
                if (StringUtils.equals(key, "keyword")) {
                    queryWrapper.and(i -> i.like("account_name", value).or().like("app_name", value));
                }
            });
        }
        IPage<MpAccountVO> mpAccountVOPage = new Page<>();
        BeanUtils.copyProperties(mpAccountService.page(pager, queryWrapper), mpAccountVOPage);
        mpAccountVOPage.setRecords(pager.getRecords().stream().map(mpAccount -> {
            MpAccountVO mpAccountVO = new MpAccountVO();
            BeanUtils.copyProperties(mpAccount, mpAccountVO);
            return mpAccountVO;
        }).collect(Collectors.toList()));
        return ResultWrapper.success(mpAccountVOPage);
    }

}