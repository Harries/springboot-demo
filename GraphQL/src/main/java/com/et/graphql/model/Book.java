package com.et.graphql.model;

import javax.persistence.*;

@Entity
@Table(name = "Book", schema = "BOOK_API_DATA")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @Column(name = "pagecount")
    private String pageCount;

    public Book(Integer id, String name, String pageCount) {
        this.id = id;
        this.name = name;
        this.pageCount = pageCount;
    }

    public Book() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }
}