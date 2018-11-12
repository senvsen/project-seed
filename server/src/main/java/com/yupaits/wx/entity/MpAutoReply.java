package com.yupaits.wx.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import com.yupaits.wx.dto.WxMpReplyMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;

/**
 * <p>
 * 公众号自动回复
 * </p>
 *
 * @author yupaits
 * @since 2018-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wx_mp_auto_reply")
public class MpAutoReply extends Model<MpAutoReply> {

    private static final long serialVersionUID = 1L;

    /**
     * 自动回复ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 公众号账户ID
     */
    private Long accountId;

    /**
     * 关键词
     */
    private String keywords;

    /**
     * 匹配规则
     */
    private MatchRule matchRule;

    /**
     * 回复内容
     */
    private String reply;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 更新人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;

    /**
     * 删除标记
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public List<String> getKeywords() {
        return JSON.parseArray(this.keywords, String.class);
    }

    public WxMpReplyMessage getReply() {
        return JSON.parseObject(this.reply, WxMpReplyMessage.class);
    }

    public enum MatchRule {
        /**
         * 包含所有
         */
        AND,
        /**
         * 包含任一个
         */
        OR
    }

    /**
     * 验证AutoReply是否有效
     * @param withPk 是否验证主键
     * @return 验证结果
     */
    public boolean isValid(boolean withPk) {
        boolean isValid = accountId != null && accountId.compareTo(0L) > 0 && !CollectionUtils.isEmpty(getKeywords()) && matchRule != null && getReply().isValid();
        return withPk ? id != null && id.compareTo(0L) > 0 && isValid : isValid;
    }

    /**
     * 验证AutoReply集合是否有效
     * @param autoReplyIterable AutoReply集合
     * @param withPk 是否验证主键
     * @return 验证结果
     */
    public static boolean isValid(Iterable<MpAutoReply> autoReplyIterable, boolean withPk) {
        if (IterableUtils.isEmpty(autoReplyIterable)) {
            return false;
        }
        for (MpAutoReply reply : autoReplyIterable) {
            if (!reply.isValid(withPk)) {
                return false;
            }
        }
        return true;
    }
}