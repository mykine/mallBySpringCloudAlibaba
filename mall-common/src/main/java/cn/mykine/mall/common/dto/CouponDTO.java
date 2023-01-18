package cn.mykine.mall.common.dto;


import cn.mykine.mall.common.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 优惠券信息DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO extends BaseBean {

    /** 优惠券ID **/
    private Long id;

    /** 优惠券标题 **/
    private String title;

    /** 满减金额 **/
    private BigDecimal withAmount;

    /** 优惠金额 **/
    private BigDecimal usedAmount;

    /** 发放数量 **/
    private Long quota;

    /** 已领取数量 **/
    private Long takeCount;

    /** 已使用数量 **/
    private Long usedCount;

    /** 状态 1-生效 2-失效 **/
    private Integer status;

}
