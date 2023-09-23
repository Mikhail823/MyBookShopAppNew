package com.example.bookshop.struct.book.links;
import com.example.bookshop.struct.book.BookEntity;
import com.example.bookshop.struct.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "book2user")
@Setter
@Getter
public class Book2UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
    private Date time;

    @Column(name = "book_id", columnDefinition = "INT NOT NULL")
    private Integer bookId;

    @Column(name = "user_id", columnDefinition = "INT NOT NULL")
    private Integer userId;

    @Column(name = "type_id", columnDefinition = "INT NOT NULL")
    private Integer typeId;
}