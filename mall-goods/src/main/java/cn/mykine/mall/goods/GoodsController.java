package cn.mykine.mall.goods;

import cn.mykine.mall.common.base.BaseResponse;
import cn.mykine.mall.common.dto.GoodsDTO;
import cn.mykine.mall.common.dto.OrderItemDTO;
import cn.mykine.mall.common.dto.PageQueryGoodsDTO;
import cn.mykine.mall.goods.service.IGoodsService;
import com.sun.org.apache.xalan.internal.extensions.ExpressionContext;
import org.apache.catalina.core.StandardContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
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

    /**
     * el表达式
     * */
    @GetMapping("/goods/elexpression/{el}")
    BaseResponse<String> getGoods(@PathVariable("el") String el) {
        //解析器
        ExpressionParser parser = new SpelExpressionParser();
        //表达式
        Expression expression = parser.parseExpression("'mall:goods:' + #el");
        //构建上下文
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("el",el);
        //在上下文中求值
        String res = expression.getValue(context).toString();
        return BaseResponse.success(res);
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
