package com.yupaits.wx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultCode;
import com.yupaits.commons.result.ResultWrapper;
import com.yupaits.commons.utils.ValidateUtils;
import com.yupaits.wx.dto.MpAutoReplyCreate;
import com.yupaits.wx.dto.MpAutoReplyUpdate;
import com.yupaits.wx.entity.MpAutoReply;
import com.yupaits.wx.service.IMpAutoReplyService;
import com.yupaits.wx.vo.MpAutoReplyVO;
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
 * 公众号自动回复 前端控制器
 * </p>
 *
 * @author yupaits
 * @since 2018-11-12
 */
@Slf4j
@Api(tags = "公众号自动回复接口")
@RestController
@RequestMapping("/wx/mp-auto-reply")
public class MpAutoReplyController {

    private final IMpAutoReplyService mpAutoReplyService;

    @Autowired
    public MpAutoReplyController(IMpAutoReplyService mpAutoReplyService) {
        this.mpAutoReplyService = mpAutoReplyService;
    }

    @ApiOperation("创建公众号自动回复")
    @PostMapping("")
    public Result addMpAutoReply(@RequestBody MpAutoReplyCreate mpAutoReplyCreate) {
        if (!mpAutoReplyCreate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        MpAutoReply mpAutoReply = new MpAutoReply();
        BeanUtils.copyProperties(mpAutoReplyCreate, mpAutoReply);
        return mpAutoReplyService.save(mpAutoReply) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.CREATE_FAIL);
    }

    @ApiOperation("编辑公众号自动回复")
    @PutMapping("/{id:\\d{19}}")
    public Result updateMpAutoReply(@RequestBody MpAutoReplyUpdate mpAutoReplyUpdate) {
        if (!mpAutoReplyUpdate.isValid()) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        MpAutoReply mpAutoReply = new MpAutoReply();
        BeanUtils.copyProperties(mpAutoReplyUpdate, mpAutoReply);
        return mpAutoReplyService.updateById(mpAutoReply) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.UPDATE_FAIL);
    }

    @ApiOperation("根据ID删除公众号自动回复")
    @DeleteMapping("/{id:\\d{19}}")
    public Result deleteMpAutoReply(@PathVariable Long id) {
        if (!ValidateUtils.idValid(id)) {
            return ResultWrapper.fail(ResultCode.PARAMS_ERROR);
        }
        return mpAutoReplyService.removeById(id) ? ResultWrapper.success() : ResultWrapper.fail(ResultCode.DELETE_FAIL);
    }

    @ApiOperation("获取公众号自动回复分页信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "当前页码", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数量", defaultValue = "10", dataType = "int", paramType = "query")})
    @PostMapping("/page")
    public Result getMpAutoReplyPage(@RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "10") int size,
                              @RequestParam(required = false) @ApiParam(value = "降序字段") List<String> descs,
                              @RequestParam(required = false) @ApiParam(value = "升序字段") List<String> ascs,
                              @RequestBody(required = false) Map<String, Object> query) {
        Page<MpAutoReply> pager = new Page<>(page, size);
        if (CollectionUtils.isNotEmpty(descs)) {
            pager.setDescs(descs);
        }
        if (CollectionUtils.isNotEmpty(ascs)) {
            pager.setAscs(ascs);
        }
        QueryWrapper<MpAutoReply> queryWrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(query)) {
            query.forEach((key, value) -> {
                if (StringUtils.equals(key, "accountId")) {
                    queryWrapper.eq("account_id", value);
                }
            });
        }
        IPage<MpAutoReplyVO> mpAutoReplyVOPage = new Page<>();
        BeanUtils.copyProperties(mpAutoReplyService.page(pager, queryWrapper), mpAutoReplyVOPage);
        mpAutoReplyVOPage.setRecords(pager.getRecords().stream().map(mpAutoReply -> {
            MpAutoReplyVO mpAutoReplyVO = new MpAutoReplyVO();
            BeanUtils.copyProperties(mpAutoReply, mpAutoReplyVO);
            return mpAutoReplyVO;
        }).collect(Collectors.toList()));
        return ResultWrapper.success(mpAutoReplyVOPage);
    }

}