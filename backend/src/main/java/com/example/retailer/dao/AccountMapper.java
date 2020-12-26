package com.example.retailer.dao;

import com.example.retailer.domain.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

public interface AccountMapper {

    @Select("select * from Account where id=#{id}")
    Account findAccountById(String id);

    @Insert("insert into Account values(#{id}, #{password}, #{token})")
    void InsertUser(String id, String password, String token);

    @Select("select * from Account where token=#{token}")
    Account findAccountByToken(String token);

    // 通过token查找到对应的id
    @Select("select id from Account where token=#{token}")
    String findIdByToken(String token);
}
