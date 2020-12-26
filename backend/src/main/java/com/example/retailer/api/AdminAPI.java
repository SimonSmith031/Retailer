package com.example.retailer.api;

import com.example.retailer.App;
import com.example.retailer.domain.Book;
import com.example.retailer.service.BookService;
import com.example.retailer.util.JacksonUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
/* 最开始是配置了一个配置器解决问题的，后来不知道为什么问题还是解决不了，才加上了这个注解，也不知道是哪里起到了作用 */
public class AdminAPI {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    // 更新书籍的库存和价格
    @PutMapping("/book/{token}/{bookName}/{number}/{price}")
    public String updateStockAndPirce(@PathVariable("token") String token,
                               @PathVariable("bookName") String bookName,
                               @PathVariable("number") Integer number,
                               @PathVariable("price") Double price) {
        Map<String, String> resultMap = new HashMap<>();
        // 安全检查在bookService中完成
        // 可能权限不足、或者输入数据不合法
        boolean flag = bookService.updateBookByToken(token, bookName, number, price);
        if (flag) {
            resultMap.put("status", "success");
        } else {
            resultMap.put("status", "failure");
        }
        return JacksonUtil.serialize(resultMap);
    }

    // 添加新的图书
    @PostMapping("/book")
    public String addBook(String token, String name, String fullName, String description, Integer stockNumber,
                          Double price, MultipartFile cover, MultipartFile pdf) {
        Map<String, String> resultMap = new HashMap<>();

        // 检查表单的提交参数是否合法
        if (name == null || name.trim().isEmpty() || fullName == null ||
                fullName.trim().isEmpty() || description == null
                || description.trim().isEmpty() || stockNumber == null || price == null ||
                price <= 0.0 || stockNumber <= 0.0) {
            // 添加失败，返回响应的反馈信息
            logger.info("添加失败: [name]: "+name+"\n[fullName]: "+fullName+"\n[description]: "+description+"\n");
            resultMap.put("status", "failure");
            return JacksonUtil.serialize(resultMap);
        }

        // 处理表单参数
        String bookName = name.trim();
        String bookFullName = fullName.trim();
        String bookDescription = description.trim();

        // 事务处理
        Book book = new Book(bookName,bookFullName,price,stockNumber,bookDescription);
        boolean flag = bookService.addBookByToken(token, book);

        // 检查处理结果
        if (!flag) {
            logger.info("添加书籍失败");
            resultMap.put("status", "failure");
            return JacksonUtil.serialize(resultMap);
        }

        // 处理成功后，上传资源文件
        uploadFile(cover, ResourceAPI.getBookImageFolder(name), "cover.png");
        // 目前默认书籍是pdf格式的
        uploadFile(pdf, ResourceAPI.getBookContentFolder(),  name + ".pdf");

        // 返回给前端反馈信息
        resultMap.put("status", "success");
        return JacksonUtil.serialize(resultMap);
    }

    // 上传资源文件，如果传过来的是空指针则跳过上传
    private void uploadFile(MultipartFile multipartFile, String filePath, String fileName) {
        if (multipartFile == null) {
            logger.info(fileName + "：该文件不存在，跳过上传");
            return;
        }
        // 检查文件所在的路径是否存在，不存在则创建
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdir();
        }
        // 创建资源文件
        File dest = new File(filePath + fileName);
        try {
            multipartFile.transferTo(dest);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    // 删除一本书
    @DeleteMapping("/book/{token}/{bookName}")
    public String deleteBook(@PathVariable("token") String token,
                             @PathVariable("bookName") String bookName) {
        Map<String, String> resultMap = new HashMap<>();
        boolean flag = bookService.deleteBookByToken(token, bookName);
        if (!flag) {
            resultMap.put("status", "failure");
            resultMap.put("info", "删除书籍失败，可能书籍不存在或没有权限。");
        } else {
            resultMap.put("status", "success");
            deleteBookResources(bookName);
        }
        return JacksonUtil.serialize(resultMap);
    }

    // 删除一本书相应的资源文件
    private void deleteBookResources(String bookName) {
        try {
            // 删除图片
            String filePath = ResourceAPI.getBookImageFolder(bookName);
            File imageFolder = new File(filePath);
            FileUtils.deleteDirectory(imageFolder);
            // 删除资源文件
            filePath = ResourceAPI.getBookContentFolder() + bookName + ".pdf";
            File resourceFile = new File(filePath);
            if (resourceFile.exists()) {
                resourceFile.delete();
            }
        } catch (Exception exception) {
            logger.info("[" + bookName + "] 删除部分资源文件失败，可能资源不存在");
        } finally {
            logger.info("[" + bookName + "] 删除资源的流程结束");
        }
    }
}
