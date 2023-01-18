package cn.mykine.mall.common.dto;

import cn.mykine.mall.common.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单信息DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO extends BaseBean {

    /** 订单号 **/
    private Long orderNo;

    /** 订单金额 **/
    private BigDecimal amount;

    /** 订单状态 0-初始化 1-提交 2-完成 3-撤销 **/
    private Integer status;

    /** 用户ID **/
    private Long userId;

    /** 优惠券ID **/
    private Long couponRecordId;

    /** 订单明细列表 **/
    private List<OrderItemDTO> orderItemDTOList;

}
