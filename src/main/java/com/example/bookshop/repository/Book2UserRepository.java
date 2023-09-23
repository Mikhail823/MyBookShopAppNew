package com.example.bookshop.repository;

import com.example.bookshop.struct.book.BookEntity;
import com.example.bookshop.struct.book.links.Book2UserEntity;
import com.example.bookshop.struct.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Book2UserRepository extends JpaRepository<Book2UserEntity, Integer> {

   Book2UserEntity findBook2UserEntityByUserIdAndBookId(Integer user, Integer book);
//   @Query(value = "select b2u from book2user b2u where b2u.book_id = :book and b2u.user_id= :user", nativeQuery = true)
//   Book2UserEntity findBook2UserEntityByBookIdAndUserIddd(@Param("book") BookEntity book, @Param("user") UserEntity user);
//
//   Book2UserEntity getBook2UserEntityByBookIdAndBookId(Integer book, Integer user);

}
