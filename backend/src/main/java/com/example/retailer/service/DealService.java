package com.example.retailer.service;

import com.example.retailer.dao.AccountMapper;
import com.example.retailer.dao.DealMapper;
import com.example.retailer.domain.Deal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class DealService {
    private final Logger logger = LoggerFactory.getLogger(DealService.class);

    @Autowired
    private DealMapper dealMapper;

//    public boolean CreateDeal(Deal deal){
//        return false;
//    }
    public List<Deal> getDealsByToken(String token) {
        return dealMapper.findDealByToken(token);
    }
}
