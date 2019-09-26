package com.h5.entity;

import java.time.LocalDateTime;
import com.h5.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2019-09-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Score extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("userId")
    private String userId;

    @TableField("snakeId")
    private String snakeId;

    private Boolean 是否通过;

    @TableField("scoreCheckpoint")
    private Integer scoreCheckpoint;

    private String 难度;

    @TableField("scoreSC")
    private String scoreSC;

    private String createBy;

    private LocalDateTime gmtCreate;

    private String lastModifiedBy;

    private LocalDateTime gmtModified;

    private Boolean isDelete;

    private Integer version;

    private Integer sortNo;


}
