package com.example.retailer.api;

import com.example.retailer.service.BookService;
import com.example.retailer.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BookAPI {
    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public String getAllBooksOnSale() {
        return JacksonUtil.serialize(bookService.findAllBooks());
    }
}
