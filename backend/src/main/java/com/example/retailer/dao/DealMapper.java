package com.example.retailer.dao;

import com.example.retailer.domain.Deal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface DealMapper {

    @Select("select * from Deal where userId=#{userId}")
    List<Deal> findDealByUserId(String userId);

    @Select("select * from Deal")
    List<Deal> findAllDeals();

    @Update("update Deal set userId=#{userId} bookName=#{bookName} where id=#{id}")
    void updateDeal(Deal deal);

    @Insert("insert into Deal values(#{id}, #{userId}, #{bookName}, #{time}, #{number})")
    void addDeal(Deal deal);

    /**
     * 通过token来查询交易记录，按照时间顺序倒序排列
     */
    @Select("select * from Deal, Account where userId=Account.id and token=#{token} order by time desc")
    List<Deal> findDealByToken(String token);
}
