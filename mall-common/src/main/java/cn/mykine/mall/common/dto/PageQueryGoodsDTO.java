package cn.mykine.mall.common.dto;


import cn.mykine.mall.common.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 优惠券分页查询参数
 */
public class PageQueryGoodsDTO extends PageQueryParam {

    /** 商品ID **/
    private Long goodsId;

    /** 商品名字 **/
    private String name;

}
