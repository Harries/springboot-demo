package com.et.graphql.model;


import javax.persistence.*;

@Entity
@Table(name = "Author", schema = "BOOK_API_DATA")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column(name = "firstname")
    String firstName;
    @Column(name = "lastname")
    String lastName;
    @Column(name = "bookid")
    Integer bookId;

    public Author(Integer id, String firstName, String lastName, Integer bookId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bookId = bookId;
    }

    public Author() {

    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
}
