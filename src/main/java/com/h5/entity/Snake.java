package com.h5.entity;

import java.time.LocalDateTime;
import com.h5.entity.BaseEntity;
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
 * @since 2019-09-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Snake extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("snakeLength")
    private Integer snakeLength;

    @TableField("snakeColor")
    private String snakeColor;

    @TableField("snakeSkin")
    private String snakeSkin;

}
