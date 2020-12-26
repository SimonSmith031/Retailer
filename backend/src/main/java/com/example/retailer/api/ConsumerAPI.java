package com.example.retailer.api;

import com.example.retailer.domain.BookItem;
import com.example.retailer.domain.Deal;
import com.example.retailer.service.AccountService;
import com.example.retailer.service.BookService;
import com.example.retailer.service.DealService;
import com.example.retailer.service.ItemService;
import com.example.retailer.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ConsumerAPI {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemService itemService;

    @Autowired
    private DealService dealService;

    @Autowired
    private BookService bookService;

    @Autowired
    private AccountService accountService;

    // 获取所有的购物车项目
    @GetMapping("/shopcart-items/{token}")
    public String getShopcartItems(@PathVariable("token") String token) {
        List<BookItem> items = itemService.findAllByToken(token);
        String json = JacksonUtil.serialize(items);
        return json;
    }

    // 获取所有的已购物品项目
    @GetMapping("/purchased-items/{token}")
    public String getPurchasedItems(@PathVariable("token") String token) {
        List<Deal> deals = dealService.getDealsByToken(token);
        String json = JacksonUtil.serialize(deals);
        return json;
    }

    // 添加物品到购物车
    @PostMapping("/shopcart-item/{token}/{bookName}")
    public String addToShopcart(@PathVariable("token") String token,
                                @PathVariable("bookName") String bookName) {
        itemService.addOneByToken(token, bookName);
        logger.info("用户使用token添加了" + bookName + "到购物清单中");
        Map<String, String> map = new HashMap<>();
        map.put("status", "success");
        return JacksonUtil.serialize(map);
    }

    // 更新购物车列表的信息
    @PutMapping("/shopcart-item/{token}/{bookName}/{number}")
    public String updateItemInShopcart(@PathVariable("token") String token,
                                       @PathVariable("bookName") String bookName,
                                       @PathVariable("number") Integer number) {
        Map<String, String> map = new HashMap<>();
        // 对number合法性做检查
        if (number <= 0) {
            map.put("status", "failure");
            map.put("info", "指定的数量必须是正整数。");
            return JacksonUtil.serialize(map);
        }
        itemService.updateNumberOfOne(token, bookName, number);
        logger.info("[updateNumberOfOne]: " + bookName + ", [now]: " + number);

        map.put("status", "success");
        return JacksonUtil.serialize(map);
    }

    // 购买购物车的一个项目
    @PostMapping("/purchased-item/{token}/{bookName}/{number}")
    public String purchaseItem(@PathVariable("token") String token,
                               @PathVariable("bookName") String bookName,
                               @PathVariable("number") Integer number) {
        Map<String, String> map = new HashMap<>();
        // 检查number的合法性
        if (number <= 0) {
            map.put("status", "failure");
            map.put("info", "指定的数量必须是正整数。");
            return JacksonUtil.serialize(map);
        }
        // 开始购买流程
        boolean flag = bookService.buyItemByToken(bookName, token, number);
        if (flag) {
            map.put("status", "success");
            return JacksonUtil.serialize(map);
        } else {
            map.put("status", "failure");
            map.put("info", "购买过程中出现错误。");
            return JacksonUtil.serialize(map);
        }
    }

    // 购买购物车的所有项目
    @PostMapping("/purchased-items/{token}")
    public String purchaseItems(@PathVariable("token") String token, @RequestBody String body) {
        // 获取请求体中的信息
        Map<String, Object> requestMap = JacksonUtil.deserialize(body, Map.class);
        List<Map<String, Object>> registries = JacksonUtil.deserialize(
                (String) requestMap.get("items"), List.class);
        // 购买操作
        Map<String, String> map = new HashMap<>();
        try {
            bookService.buyItemsByToken(token, registries);
        } catch (RuntimeException runtimeException) {
            logger.info("购买过程中出现错误。");
            map.put("status", "failure");
            map.put("info", "购买一批物品的过程中出现了错误。");
//            throw runtimeException; // 调试用
            return JacksonUtil.serialize(map);
        }
        map.put("status", "success");
        String json = JacksonUtil.serialize(map);
        return json;
    }

    // 删除购物车的一个项目
    @DeleteMapping("/shopcart-item/{token}/{bookName}")
    public String deleteShopcartItem(@PathVariable("token") String token,
                                     @PathVariable("bookName") String bookName) {
        Map<String, String> map = new HashMap<>();
        itemService.deleteOneByToken(token, bookName);
        map.put("status", "success");
        return JacksonUtil.serialize(map);
    }

    // 删除购物车的所有项目
    @DeleteMapping("/shopcart-items/{token}")
    public String deleteShopcartItems(@PathVariable("token") String token) {
        Map<String, String> map = new HashMap<>();
        itemService.deleteAllByToken(token);
        map.put("status", "success");
        return JacksonUtil.serialize(map);
    }
}
