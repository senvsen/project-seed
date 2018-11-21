package com.yupaits.wx.controller;

import com.alibaba.fastjson.JSON;
import com.yupaits.commons.result.Result;
import com.yupaits.commons.result.ResultWrapper;
import com.yupaits.wx.helper.WxMpServiceHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 微信公众号菜单 前端控制器
 * </p>
 * @author yupaits
 * @date 2018/11/21
 */
@Api(tags = "微信公众号菜单接口")
@RestController
@RequestMapping("/wx/mp-menu")
public class MpMenuController {

    private final WxMpServiceHelper wxMpServiceHelper;

    @Autowired
    public MpMenuController(WxMpServiceHelper wxMpServiceHelper) {
        this.wxMpServiceHelper = wxMpServiceHelper;
    }

    @ApiOperation("根据账户ID获取微信公众号菜单信息")
    @GetMapping("/{id}")
    public Result getMpMenu(@PathVariable Long id) {
        try {
            WxMpMenu mpMenu = wxMpServiceHelper.wxMpMenuService(id).menuGet();
            if (mpMenu == null) {
                return ResultWrapper.fail("当前公众号无菜单信息");
            }
            return ResultWrapper.success(mpMenu);
        } catch (WxErrorException e) {
            return ResultWrapper.fail(e.getError().getErrorMsg());
        }
    }

    @ApiOperation("保存微信公众号菜单信息")
    @PostMapping("/{id}")
    public Result saveMpMenu(@PathVariable Long id, @RequestBody Map<String, Object> mpMenuJson) {
        try {
            wxMpServiceHelper.wxMpMenuService(id).menuCreate(JSON.toJSONString(mpMenuJson));
            return ResultWrapper.success();
        } catch (WxErrorException e) {
            return ResultWrapper.fail(e.getError().getErrorMsg());
        }
    }
}
