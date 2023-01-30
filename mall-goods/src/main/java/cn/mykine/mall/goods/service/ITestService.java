package cn.mykine.mall.goods.service;


import cn.mykine.mall.common.dto.GoodsDTO;
import cn.mykine.mall.common.dto.OrderItemDTO;
import cn.mykine.mall.common.dto.PageQueryGoodsDTO;

import java.util.List;

/**
 * 测试专用Service
 */
public interface ITestService {

    /**
     * 测试布隆过滤器
     *
     * @param
     * @return
     */
    String testBloomFilter(Long id);

}
