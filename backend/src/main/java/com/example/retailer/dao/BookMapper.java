package com.example.retailer.dao;

import com.example.retailer.domain.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookMapper {
    @Select("select * from Book where name=#{name}")
    Book findBookByName(String name);

    @Select("select * from Book")
    List<Book> findAllBooks();

    // 有修改，增加对修改价格的支持
    @Update("update Book set stockNumber=#{stockNumber}, price=#{price}, fullName=#{fullName}," +
            "description=#{description} where name=#{name}")
    void updateBook(Book book);

    @Insert("insert into Book values(#{name}, #{fullName}, #{stockNumber}, #{price}, #{description})")
    void addBook(Book book);

    @Delete("delete from Book where name=#{bookName}")
    void deleteBook(String bookName);
}
