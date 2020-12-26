package com.example.retailer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Paths;

@SpringBootApplication
public class App {
    // 资源所在的路径
    public static String resourcePath;

    public static void main(String[] args) {
        // 检查参数
        if (args.length > 0) {
            // 需要指定的是一个绝对路径
            resourcePath = args[0];
        } else {
            // 未指定时则使用当前路径作为基本路径，然后找到同级目录下面的assets文件夹
            String cwd = Paths.get("").toAbsolutePath().toString();
            System.out.println("Working Directory: " + cwd);
            resourcePath = cwd + "/assets";
        }

        SpringApplication.run(App.class, args);
    }

}
