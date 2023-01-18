package cn.mykine.mall.goods;

import cn.mykine.mall.common.base.BaseResponse;
import cn.mykine.mall.common.dto.GoodsDTO;
import cn.mykine.mall.common.dto.OrderItemDTO;
import cn.mykine.mall.common.dto.PageQueryGoodsDTO;
import cn.mykine.mall.goods.service.IGoodsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
public class GoodsController {

    @Resource
    private IGoodsService goodsService;

    @PostMapping("/goods")
    BaseResponse addGoods(@RequestBody GoodsDTO goodsDTO) {
        goodsService.addGoods(goodsDTO);
        return BaseResponse.success();
    }

    @GetMapping("/goods/{id}")
    BaseResponse<GoodsDTO> getGoods(@PathVariable("id") Long id) {
        GoodsDTO goods = goodsService.getGoods(id);
        return BaseResponse.success(goods);
    }

    @PostMapping("/goods/list")
    BaseResponse<List<GoodsDTO>> pageQueryGoods(@RequestBody PageQueryGoodsDTO param) {
        List<GoodsDTO> goods = goodsService.pageQueryGoods(param);
        return BaseResponse.success(goods);
    }

    @GetMapping("/goods/v2/{id}")
    BaseResponse<GoodsDTO> getGoodsNameAndPrice(@PathVariable("id") Long id) {
        GoodsDTO goods = goodsService.getGoodsNameAndPrice(id);
        return BaseResponse.success(goods);
    }

    @PutMapping("/goods/stock")
    BaseResponse checkAndDecreaseStock(@RequestBody List<OrderItemDTO> orderItemDTOList) {
        goodsService.decreaseStock(orderItemDTOList);
        return BaseResponse.success();
    }

}
