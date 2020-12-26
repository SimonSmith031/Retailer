package com.example.retailer.api;

import com.example.retailer.service.AccountService;
import com.example.retailer.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginAPI {
    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public String login(@RequestBody String body) {
        Map<String, String> requestBody = JacksonUtil.deserialize(body, Map.class);
        String username = requestBody.get("username");
        String password = requestBody.get("password");

        Map<String, Object> map = new HashMap<String, Object>();
        AccountService.ValidationResult result = accountService.validateAccount(username, password);
        map.put("status", result.flag ? "success" : "failure");
        if (!result.flag) {
            map.put("info", result.info);
        } else {
            // 登录成功，返回账户对应的token
            map.put("token", result.token);
        }
        String json =  JacksonUtil.serialize(map);
        return json;
    }
}
