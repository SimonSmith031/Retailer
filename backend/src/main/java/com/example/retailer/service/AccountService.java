package com.example.retailer.service;

import com.example.retailer.dao.AccountMapper;
import com.example.retailer.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
public class AccountService {
    private final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountMapper accountMapper;

    public static class ValidationResult {
        public boolean flag;
        public String info;
        public String token;

        public ValidationResult(boolean flag, String info) {
            this.flag = flag;
            this.info = info;
        }

        public ValidationResult(boolean flag, String inf, String token) {
            this.flag = flag;
            this.info = info;
            this.token = token;
        }
    }

    // 验证账户，不存在这个用户就创建
    public ValidationResult validateAccount(String id, String password) {
        if (id == null || password == null || id.length() == 0 || password.length() == 0) {
            logger.info("账号或者密码为空");
            return new ValidationResult(false, "账号或者密码为空，请检查输入。");
        }
        if (id.contains(" ") || id.contains("\t")) {
            logger.info("账号或者密码中包含了非法字符");
            return new ValidationResult(false, "账号或者密码中包含了非法字符，请重新输入。");
        }
        Account account = accountMapper.findAccountById(id);
        if (account == null) {
            logger.info("该用户未在数据库中找到，将创建一个新的记录");
            // 创建一个新的UUID作为token并插入
            String token;
            Account accountWithToken;
            do {
                token = UUID.randomUUID().toString();
                accountWithToken = accountMapper.findAccountByToken(token);
            } while (accountWithToken != null);
            // 把生成的不重复的token返回
            accountMapper.InsertUser(id, password, token);
            return new ValidationResult(true, "该用户未在数据库中找到，将创建一个新的记录。", token);
        }

        if (!account.password.equals(password)) {
            logger.info("用户名能够在数据库中找到，但密码是错误的");
            return new ValidationResult(false, "用户名能够在数据库中找到，但密码是错误的。");
        }

        logger.info("用户的账号和密码正确");
        return new ValidationResult(true, "用户的账号和密码正确。", account.token);
    }

    // 检查账户是否为管理员
    public boolean isAdmin(String token) {
        String id = accountMapper.findIdByToken(token);
        return "admin".equals(id);
    }
}
