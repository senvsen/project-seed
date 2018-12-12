package com.yupaits.wx.helper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.yupaits.wx.entity.MpEventHandler;
import com.yupaits.wx.mapper.MpEventHandlerMapper;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 公众号事件处理辅助类
 * @author yupaits
 * @date 2018/11/12
 */
@Slf4j
@Component
public class MpEventHandlerHelper {

    private static final String MP_EVENT_HANDLER = "mp:event:handler";
    private final RedisTemplate redisTemplate;
    private final MpEventHandlerMapper mpEventHandlerMapper;

    @Autowired
    public MpEventHandlerHelper(RedisTemplate redisTemplate, MpEventHandlerMapper mpEventHandlerMapper) {
        this.redisTemplate = redisTemplate;
        this.mpEventHandlerMapper = mpEventHandlerMapper;
    }

    @SuppressWarnings("unchecked")
    public Map<String, WxMpMessageHandler> fetchHandlerMap(Long id) {
        Map<String, WxMpMessageHandler> handlerMap = (Map<String, WxMpMessageHandler>) redisTemplate.opsForHash().get(MP_EVENT_HANDLER, id);
        if (handlerMap == null) {
            handlerMap = Maps.newHashMap();
            List<MpEventHandler> eventHandlerList = mpEventHandlerMapper.selectList(new QueryWrapper<MpEventHandler>().eq("account_id", id));
            if (CollectionUtils.isEmpty(eventHandlerList)) {
                return handlerMap;
            }
            for (MpEventHandler mpEventHandler : eventHandlerList) {
                if (!mpEventHandler.isValid()) {
                    continue;
                }
                try {
                    handlerMap.put(mpEventHandler.getEventType(), getHandler(mpEventHandler.getHandlerClass()));
                } catch (IllegalAccessException | ClassNotFoundException | InstantiationException e) {
                    log.error("加载事件处理 {} 出错", mpEventHandler, e);
                }
            }
            redisTemplate.opsForHash().put(MP_EVENT_HANDLER, id, handlerMap);
        }
        return handlerMap;
    }

    /**
     * 根据MpAccountId清除缓存
     * @param id MpAccountId
     */
    public void evictHandlerMap(Long id) {
        //noinspection unchecked
        redisTemplate.opsForHash().delete(MP_EVENT_HANDLER, id);
    }

    /**
     * 根据handler类路径反射生成实例
     * @param handlerClass handler类路径
     * @return WxMpMessageHandler实例
     */
    private WxMpMessageHandler getHandler(String handlerClass) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Class handlerClazz = Class.forName(handlerClass);
        return (WxMpMessageHandler) handlerClazz.newInstance();
    }
}
