package com.yupaits.msg.service.impl;

import com.yupaits.msg.entity.Template;
import com.yupaits.msg.mapper.TemplateMapper;
import com.yupaits.msg.service.ITemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息模板 服务实现类
 * </p>
 *
 * @author yupaits
 * @since 2018-11-08
 */
@Service
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template> implements ITemplateService {

}
