package cn.mykine.mall.goods.service;


import cn.mykine.mall.common.dto.GoodsDTO;
import cn.mykine.mall.common.dto.OrderItemDTO;
import cn.mykine.mall.common.dto.PageQueryGoodsDTO;

import java.util.List;

/**
 * 商品Service
 */
public interface IGoodsService {

    /**
     * 新增商品
     *
     * @param goodsDTO
     * @return
     */
    boolean addGoods(GoodsDTO goodsDTO);

    /**
     * 获取商品
     *
     * @param id
     * @return
     */
    GoodsDTO getGoods(Long id);

    /**
     * 分页获取商品列表
     *
     * @param param
     * @return
     */
    List<GoodsDTO> pageQueryGoods(PageQueryGoodsDTO param);

    /**
     * 获取商品名称和价格
     *
     * @param id
     * @return
     */
    GoodsDTO getGoodsNameAndPrice(Long id);

    /**
     * 检查并扣减商品库存
     *
     * @param orderItemDTOList
     * @return
     */
    boolean decreaseStock(List<OrderItemDTO> orderItemDTOList);
}
