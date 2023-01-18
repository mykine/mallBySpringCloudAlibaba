package cn.mykine.mall.common.dto;

import cn.mykine.mall.common.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 订单明细信息DTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO extends BaseBean {

    /** 订单明细ID **/
    private Long id;

    /** 订单号 **/
    private Long orderNo;

    /** 商品ID **/
    private Long goodsId;

    /** 数量 **/
    private Long number;

    /** 金额 **/
    private BigDecimal amount;

}
