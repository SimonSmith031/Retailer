package com.example.retailer.api;

import com.example.retailer.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

// 这里不是RestController
@Controller
@RequestMapping("/api")
public class ResourceAPI {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 获取图像存放文件夹的位置
    public static String getBookImageFolder(String bookName) {
        return App.resourcePath + "/images/books/" + bookName + "/";
    }

    public static String getBookContentFolder() {
        return App.resourcePath + "/content/";
    }

    // 返回图片资源文件
    @RequestMapping(value = "/image/{book}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> imageDownload(@PathVariable("book") String bookName) throws IOException {
        String path = getBookImageFolder(bookName) + "cover.png";
        return getResource(path);
    }

    // 获取指定资源的过程抽取
    public ResponseEntity<byte[]> getResource(String path) throws IOException {
        File file = new File(path);

        // 检查文件是否存在
        if (!file.exists()) {
            logger.info("[" + path + "] 资源不存在，返回空资源");
            return new ResponseEntity<>(new byte[0], new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

        // 传输文件
        InputStream is = new FileInputStream(file);
        byte[] body = new byte[is.available()];
        is.read(body);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=" + file.getName());
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
        logger.info("[" + path + "] 成功找到资源");
        return entity;
    }

//    @RequestMapping(value = "/resource/{book}",method = RequestMethod.GET)
    @Deprecated
    public ResponseEntity<byte[]> resourceDownload(@PathVariable("book") String bookName) throws IOException {
        String path = getBookContentFolder() + bookName + ".pdf";
        return getResource(path);
    }
}
