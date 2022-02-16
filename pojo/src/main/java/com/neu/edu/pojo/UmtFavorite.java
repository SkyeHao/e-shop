package com.neu.edu.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class UmtFavorite extends BasePojo {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 收货姓名
     */
    private Integer productId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    /**
     * 状态
     */
    private int active;


}
