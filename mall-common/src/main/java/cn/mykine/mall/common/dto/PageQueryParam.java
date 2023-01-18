package cn.mykine.mall.common.dto;


import cn.mykine.mall.common.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageQueryParam {
    private Integer pageNo;
    private Integer pageSize;
}
