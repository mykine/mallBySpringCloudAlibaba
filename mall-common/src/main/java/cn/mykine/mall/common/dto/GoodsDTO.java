package cn.mykine.mall.common.dto;


import cn.mykine.mall.common.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 商品信息DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDTO extends BaseBean {

    /** 商品ID **/
    private Long id;

    /** 商品名称 **/
    private String name;

    /** 商品描述 **/
    private String description;

    /** 商品类型 **/
    private Integer type;

    /** 商品价格 **/
    private BigDecimal price;

    /** 商品库存 **/
    private Long stock;

}
