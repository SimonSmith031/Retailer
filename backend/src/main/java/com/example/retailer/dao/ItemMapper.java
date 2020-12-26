package com.example.retailer.dao;

import com.example.retailer.domain.BookItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ItemMapper {
    @Delete("delete from ShoppingList where bookName=#{bookName} and userId=#{user}")
    void deleteOne(String user, String bookName);

    @Delete("delete from ShoppingList where userId=#{user}")
    void deleteAll(String user);

    @Select("select name, fullName, price, number, description " +
            "from ShoppingList, Book " +
            "where name=bookName and userId=#{user}")
    List<BookItem> findAll(String user);

    // 添加购物清单表项：如果没有就加入，如果有就更新
    @Insert("insert into ShoppingList values(#{user}, #{bookName}, 1) " +
            "on duplicate key update number=number + 1")
    void addOne(String user, String bookName);

    // 使用配置文件的方法来提供支持
    @Update("<script> " +
            "update ShoppingList " +
            "set number = case bookName " +
            "<foreach collection='records' item='record'> " +
            "when #{record.name} then #{record.number} " +
            "</foreach> " +
            "end " +
            "where userId=#{user} " +
            "</script>")
    void updateAll(String user, @Param("records") Iterable<BookItem> items);

    // ************************** 为token操作和前后端分离重写的映射 *****************************
    // 使用token查找所有项目
    @Select("select name, fullName, price, number, description " +
            "from ShoppingList, Book, Account " +
            "where name=bookName and userId=id and token=#{token}")
    List<BookItem> findAllByToken(String token);

    // 更新一个item的数目信息
    @Update("update ShoppingList set number=#{number} where userId=#{id} and bookName=#{bookName}")
    void updateNumberOfOne(String id, String bookName, Integer number);
}
