package com.yupaits.msg.dto;

import com.yupaits.commons.core.identity.RelatedId;
import com.yupaits.msg.entity.MessageUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yupaits
 * @date 2018/11/9
 */
@Data
@ApiModel(value = "MessagePayload", description = "MessagePayload对象")
public class MessagePayload implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息", required = true)
    private MessageCreate message;

    @ApiModelProperty(value = "关联键组合", required = true)
    private RelatedId<Long> relatedId;

    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return message != null && relatedId != null && message.isValid()
                && relatedId.isValid(MessageUser.class);
    }
}
