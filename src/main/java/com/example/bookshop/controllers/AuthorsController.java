package com.example.bookshop.controllers;


import com.example.bookshop.dto.BooksPageDto;
import com.example.bookshop.dto.SearchWordDto;
import com.example.bookshop.repository.AuthorRepository;
import com.example.bookshop.service.AuthorService;
import com.example.bookshop.service.BookService;
import com.example.bookshop.struct.book.BookEntity;
import com.example.bookshop.struct.book.author.AuthorEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Api(description = "author data")
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthorsController {
    @Autowired
    private final AuthorService authorService;
    @Autowired
    private final AuthorRepository authorRepository;
    @Autowired
    private final BookService bookService;

    @ModelAttribute("authorsMap")
    public Map<String,List<AuthorEntity>> authorsMap(){
        return authorService.getAuthorsMap();
    }

    @GetMapping("/authors")
    public String authorsPage(){
        return "/authors/index";
    }

    @ApiOperation("method to get mapping")
    @GetMapping("/api/authors")
    @ResponseBody
    public List<AuthorEntity> authors(){
        return authorRepository.findAll();
    }

    @GetMapping("/author/{id:\\d+}")
    public String getPageAuthor(@PathVariable(value = "id") Integer id, Model model){
        Page<BookEntity> booksPage = bookService.getBooksByAuthorId(id, 0, 6);
        model.addAttribute("author", authorService.getAuthorById(id));
        model.addAttribute("authorBooks", booksPage.getContent());
        model.addAttribute("size", booksPage.getTotalElements());
        return "/authors/slug";
    }

    @GetMapping("/books/author/page/{id}")
    @ResponseBody
    public BooksPageDto getNextPageAuthorBooks(@PathVariable(value = "id") Integer id,
                                               Integer offset, Integer limit){
        return new BooksPageDto(bookService.getBooksByAuthorId(id, offset, limit).getContent());
    }

    @GetMapping("/books/authors/{id}")
    public String getAuthorPageBooks(@PathVariable(value = "id") Integer id, Model model){
        model.addAttribute("author", authorService.getAuthorById(id));
        model.addAttribute("booksAuthor", bookService.getBooksByAuthorId(id, 0, 5).getContent());
        return "/books/author";
    }


}
