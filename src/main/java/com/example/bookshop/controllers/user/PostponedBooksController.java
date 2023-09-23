package com.example.bookshop.controllers.user;


import com.example.bookshop.security.BookstoreUserDetails;
import com.example.bookshop.security.BookstoreUserRegister;
import com.example.bookshop.service.BookService;

import com.example.bookshop.service.components.CookieService;
import com.example.bookshop.service.UserService;
import com.example.bookshop.struct.book.BookEntity;

import com.example.bookshop.struct.book.links.Book2UserTypeEntity;
import com.example.bookshop.struct.user.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.example.bookshop.struct.book.links.Book2UserTypeEntity.StatusBookType.KEPT;

@Controller
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class PostponedBooksController {
    @Autowired
    private final BookService bookService;
    @Autowired
    private final BookstoreUserRegister userRegister;
    @Autowired
    private final CookieService cookieService;
    @Autowired
    private final UserService userServiceImp;
    private static final String REDIRECT_SLUG = "redirect:/api/books/";
    private static final String REDIRECT_POSTPONED = "redirect:/api/books/postponed";

    @ModelAttribute("postponedBooks")
    public List<BookEntity> postponedBook(){
        return new ArrayList<>();
    }

    @GetMapping("/postponed")
    public ModelAndView handlePostponedRequest(@CookieValue(name = "postponedBook", required = false)
                                                       String postponedBook, Model model, HttpServletResponse response,
                                               HttpServletRequest request){
        Object curUser = userRegister.getCurrentUser();
        if (curUser instanceof BookstoreUserDetails){
            cookieService.clearCookie(response, request);
            bookService.getPostponedBooksOfUser(model);

        } else {
            cookieService.cookiePostponedBooks(postponedBook, model);
        }
        return new ModelAndView("postponed");
    }

    @PostMapping("/changeBookStatus/postponed/{slug}")
    public ModelAndView handleChangeBookPostponed(@PathVariable(name = "slug") String slug,
                                                  @CookieValue(name = "postponedBook", required = false) String postponedBook,
                                                  @RequestBody Map<String, String> allParams,
                                                  HttpServletResponse response, Model model){
        Object curUser = userRegister.getCurrentUser();

        BookEntity book = bookService.getBookPageSlug(slug);
        int quantityPostponed = book.getNumberOfPosponed() == null ? 0 : book.getNumberOfPosponed();;
        if (curUser instanceof BookstoreUserDetails){
            UserEntity user = userServiceImp.getUserName(((BookstoreUserDetails) curUser).getName());
            bookService.saveBook2User(book, user, KEPT);
            bookService.updateCountPostponedBook(slug, quantityPostponed + 1);
        }
        else {
            cookieService.addBooksCookiePostponed(postponedBook, model, allParams, response, slug);
            bookService.updateCountPostponedBook(slug, quantityPostponed + 1);
       }
        return new ModelAndView(REDIRECT_SLUG + slug);
    }

    @PostMapping("/changeBookStatus/postponed/remove/{slug}")
    public ModelAndView handleChangeRemoveBookPostponed(@PathVariable(name = "slug") String slug,
                                                        @CookieValue(name = "postponedBook", required = false) String postponedBook,
                                                        HttpServletResponse response, HttpServletRequest request,  Model model){

        Object currUser = userRegister.getCurrentUser();
        if (currUser instanceof BookstoreUserDetails){
            BookEntity book = bookService.getBookPageSlug(slug);
            int   removeQuantityPostponed = book.getNumberOfPosponed() == null ? 0 : book.getNumberOfPosponed();
            bookService.updateCountPostponedBook(slug, removeQuantityPostponed - 1);
            bookService.removeBook2User(book, ((BookstoreUserDetails) currUser).getContact().getUserId());
        }
        else {
            cookieService.deleteBookThePostponedCookie(slug, postponedBook, response, model);
            cookieService.clearCookie(response, request);
      }
        return new ModelAndView(REDIRECT_SLUG + slug);
    }

    @PostMapping("/changeBookStatus/payAllPostponed/{books}")
    public String handlerPayAllPostponedBooks(@PathVariable("books") List<BookEntity> books,
                                             HttpServletRequest request){
        if (userRegister.isAuthUser()) {
            for (BookEntity book : books) {
                int quantityCart = book.getQuantityTheBasket() == null ? 0 : book.getQuantityTheBasket();
                bookService.saveBook2User(book,
                        ((BookstoreUserDetails) userRegister.getCurrentUser()).getContact().getUserId(),
                        Book2UserTypeEntity.StatusBookType.CART);
                bookService.updateCountCartAndCountPostponed(book.getSlug(),
                        quantityCart + 1, book.getNumberOfPosponed() - 1);
            }
        } else{

        }

        return REDIRECT_POSTPONED + "isCart=true";
    }

}
