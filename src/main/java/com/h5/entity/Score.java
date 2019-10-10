package com.h5.entity;

import java.time.LocalDateTime;
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

    @TableField("is_pass")
    private int is_pass ;

    @TableField("scoreCheckpoint")
    private Integer scoreCheckpoint;

    @TableField("scoreDifficulty")
    private String scoreDifficulty;

    @TableField("scoreSC")
    private String scoreSC;



}
