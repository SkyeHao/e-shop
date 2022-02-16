package com.neu.edu.pojo;

import java.time.LocalDateTime;
import com.neu.edu.pojo.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author hsk
 * @since 2021-07-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OmtOrder extends BasePojo {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    private String orderNo;


    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 总额
     */
    private double payment;

    /**
     * 支付方式
     */
    private Integer paymentType;

    /**
     * 订单状态
     */
    private int status;

    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    private LocalDateTime sendTime;
    private LocalDateTime closeTime;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 收货地址
     */
    private Integer shippingId;

    private Long createUserId;

    private Long updateUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String mark;

    /**
     * 是否有效
     */
    private int active;


}
