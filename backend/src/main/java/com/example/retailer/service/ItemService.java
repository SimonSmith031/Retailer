package com.example.retailer.service;

import com.example.retailer.dao.AccountMapper;
import com.example.retailer.dao.ItemMapper;
import com.example.retailer.domain.BookItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Transactional
public class ItemService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private AccountMapper accountMapper;

    public void deleteOne(String user, String bookName) {
        itemMapper.deleteOne(user, bookName);
    }

    public void deleteAll(String user) {
        itemMapper.deleteAll(user);
    }

    public List<BookItem> findAll(String user) {
        return itemMapper.findAll(user);
    }

    public void addOne(String user, String bookName) {
        itemMapper.addOne(user, bookName);
    }

    public void updateAll(String user, Iterable<BookItem> items) {
        logger.info("即将使用内存中的数据来更新数据库...");
        itemMapper.updateAll(user, items);
    }

    // ************************* 使用token的操作 *************************

    // 使用token查找书籍
    public List<BookItem> findAllByToken(String token) {
        return itemMapper.findAllByToken(token);
    }

    // 使用token增加购物车记录
    public void addOneByToken(String token, String bookName) {
        String id = accountMapper.findIdByToken(token);
        if (id == null) {
            logger.info("[addOneByToken] id为空，函数提前终止");
            return;
        }
        itemMapper.addOne(id, bookName);
    }

    // 更新数目，不对number合法性做检查，需要调用层检查
    public void updateNumberOfOne(String token, String bookName, Integer number) {
        // 查询id
        String id = accountMapper.findIdByToken(token);
        // 更新项目
        itemMapper.updateNumberOfOne(id, bookName, number);
    }

    // 删除所有的购物车记录
    public void deleteAllByToken(String token) {
        String id = accountMapper.findIdByToken(token);
        itemMapper.deleteAll(id);
    }

    // 删除一个购物车项目
    public void deleteOneByToken(String token, String bookName) {
        // 查询id
        String id = accountMapper.findIdByToken(token);
        // 删除项目
        itemMapper.deleteOne(id, bookName);
    }
}
