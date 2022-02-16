package com.neu.edu.pojo;

import java.time.LocalDateTime;
import com.neu.edu.pojo.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CmtCategory extends BasePojo {

    private static final long serialVersionUID = 1L;

    /**
     * 父类别id   当id=0时说明是根节点 一级类别
     */
    private Integer parentId;

    /**
     * 类别名称
     */
    private String name;

    /**
     * 所处层级，根节点为0
     */
    private int level;


    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态
     */
    private int active;

}
