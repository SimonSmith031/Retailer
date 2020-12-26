package com.example.retailer.domain;

import java.io.Serializable;
import java.util.Arrays;

public class BookItem implements Serializable {
    public String name;
    public String fullName;
    public Double price;
    public Integer number;
    public String description;

    // 在注入description的时候生成，因为mysql是不支持数组的（但是Postgresql支持😅）
    public String[] paragraphs;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    // 重写了Description方法
    public void setDescription(String description) {
        this.description = description;
        this.paragraphs = description.split("<br/>");
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "BookItem{" +
                "name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", price=" + price +
                ", number=" + number +
                ", description='" + description + '\'' +
                ", paragraphs=" + Arrays.toString(paragraphs) +
                '}';
    }
}
