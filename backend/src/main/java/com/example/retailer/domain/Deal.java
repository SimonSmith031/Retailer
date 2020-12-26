package com.example.retailer.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class Deal implements Serializable{

    public Integer id;
    public String userId;
    public String bookName;
    public Timestamp time;
    public Integer number;

    public Deal(){}

    public Deal(Integer id,String userId,String bookName, Timestamp time, Integer number){
        this.id = id;
        this.bookName = bookName;
        this.userId = userId;
        this.time = time;
        this.number = number;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public String toString() {
        return "Deal{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", time=" + time +
                ", number=" + number +
                '}';
    }
}
