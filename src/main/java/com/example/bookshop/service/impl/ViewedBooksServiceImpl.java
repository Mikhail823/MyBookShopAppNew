package com.example.bookshop.service.impl;

import com.example.bookshop.repository.ViewedBooksRepository;
import com.example.bookshop.security.BookstoreUserDetails;
import com.example.bookshop.security.BookstoreUserRegister;
import com.example.bookshop.service.UserService;
import com.example.bookshop.service.ViewedBooksService;
import com.example.bookshop.struct.book.BookEntity;
import com.example.bookshop.struct.book.links.Book2UserTypeEntity;
import com.example.bookshop.struct.book.links.ViewedBooks;
import com.example.bookshop.struct.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ViewedBooksServiceImpl implements ViewedBooksService {
    @Autowired
    private final ViewedBooksRepository viewedBooksRepository;
    @Autowired
    private final BookstoreUserRegister registerUser;
    @Autowired
    private final UserService userService;

    @Override
    public void saveViewedBooksUser(BookEntity book, HttpServletRequest request){
        ViewedBooks viewedBooks = new ViewedBooks();
        Cookie[] cookies = request.getCookies();
        String hashUser = "";

        for (Cookie cookie : cookies){
            if (cookie.getName().equals("USER-ANONYMOUS")){
                hashUser = cookie.getValue();
            }
        }
        Object user = registerUser.getCurrentUser();

            if (registerUser.isAuthUser()) {
                if (getViewedBookUser(book, ((BookstoreUserDetails) user).getContact().getUserId()) != null) return;
                viewedBooks.setBook(book);
                viewedBooks.setUser(((BookstoreUserDetails) user).getContact().getUserId());
                viewedBooks.setType(Book2UserTypeEntity.StatusBookType.VIEWED);
                viewedBooks.setTime(LocalDateTime.now());
                viewedBooksRepository.save(viewedBooks);

            } else {
                UserEntity anonyUser = userService.findByUserFromHash(hashUser);
                if (getViewedBookUser(book, anonyUser) != null) return;
                viewedBooks.setBook(book);
                viewedBooks.setUser(anonyUser);
                viewedBooks.setType(Book2UserTypeEntity.StatusBookType.VIEWED);
                viewedBooks.setTime(LocalDateTime.now());
                viewedBooksRepository.save(viewedBooks);

        }
    }

    public ViewedBooks getViewedBookUser(BookEntity book, UserEntity user){
        return viewedBooksRepository.findFirstByBookAndUser(book, user);
    }
}
