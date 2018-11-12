package com.yupaits.wx.service.impl;

import com.yupaits.wx.entity.MpAccount;
import com.yupaits.wx.mapper.MpAccountMapper;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

/**
 * 适用于多公众号的WxMpService
 * @author yupaits
 * @date 2018/11/12
 */
public class BaseWxServiceImpl extends WxMpServiceImpl {

    public BaseWxServiceImpl(Long id, MpAccountMapper mpAccountMapper) {
        //根据id从数据库中查出公众号信息
        MpAccount mpAccount = mpAccountMapper.selectById(id);
        //配置公众号接口所需参数
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        config.setAppId(mpAccount.getAppId());
        config.setSecret(mpAccount.getAppSecret());
        config.setToken(mpAccount.getToken());
        config.setAesKey(mpAccount.getEncodingAesKey());
        super.setWxMpConfigStorage(config);

    }
}
