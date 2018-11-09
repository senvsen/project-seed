package com.yupaits.commons.utils.msg.sms;

import com.yupaits.commons.utils.msg.MsgModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yupaits
 * @date 2018/11/9
 */
@Data
@Accessors(chain = true)
public class SmsMsg implements MsgModel<String>, Serializable {

    private static final long serialVersionUID = 1L;

    private String sender;

    private String receiver;

    private String content;
}
