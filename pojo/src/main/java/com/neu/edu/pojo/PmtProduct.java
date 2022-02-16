package com.neu.edu.pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.neu.edu.pojo.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PmtProduct extends BasePojo {

    private static final long serialVersionUID = 1L;

    /**
     * 分类id
     */
    private Integer categoryId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 已购买人数
     */
    private int purchased;

    /**
     * 产品主图
     */
    private String mainImage;

    /**
     * 图片地址
     */
    private String subImages;

    /**
     * 商品详情
     */
    private String detail;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 商品状态 1-在售 2-下架 3-删除
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态
     */
    private int active;

}
