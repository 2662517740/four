package com.h5.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: BaseEntity
 * @Description:
 * @Author: shengtt
 * @Date: 2019/9/23
 */
@Data
public class BaseEntity implements Serializable {
    private String id;
    private String createBy;//创建人
    private String gmtCreate;//创建时间
    private String lastModifiedBy;//更新人
    private String gmtModified;//更新时间
    @TableLogic
    private int isDelete;//是否作废1表示作废，0表示未作废
    private int sortNo;//序号
    private int version;//版本号
//    private String
// ;
}

