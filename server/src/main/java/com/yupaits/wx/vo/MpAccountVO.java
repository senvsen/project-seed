package com.yupaits.wx.vo;

import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yupaits.commons.core.serializer.LongSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 微信公众号账户VO
 * </p>
 *
 * @author yupaits
 * @date 2018-11-12
 */
@Data
@ApiModel(value = "MpAccountVO", description = "MpAccountVO对象")
public class MpAccountVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公众号账户ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long id;

    @ApiModelProperty(value = "账户名称")
    private String accountName;

    @ApiModelProperty(value = "关联类型")
    private Integer relatedType;

    @ApiModelProperty(value = "关联ID")
    private String relatedId;

    @ApiModelProperty(value = "公众号名称")
    private String appName;

    @ApiModelProperty(value = "公众号AppId")
    private String appId;

    @ApiModelProperty(value = "公众号Secret")
    private String appSecret;

    @ApiModelProperty(value = "公众号Token")
    private String token;

    @ApiModelProperty(value = "公众号EncodingAesKey")
    private String encodingAesKey;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "创建人ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long createdBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "更新人ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long updatedBy;
}
