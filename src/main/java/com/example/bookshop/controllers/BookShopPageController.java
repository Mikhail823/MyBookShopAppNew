package com.example.bookshop.controllers;

import com.example.bookshop.dto.MessageFormDto;
import com.example.bookshop.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class BookShopPageController {

    private static final String REDIRECT_CONT = "redirect:/contacts";

    @Autowired
    private final MessageService messageService;

    @GetMapping("/about")
    public String handlerAbout(){
        return "about";
    }

    @GetMapping("/documents")
    public String handlerDocuments(){
        return "documents/index";
    }

    @GetMapping("/contacts")
    public String handlerContacts(){
        return "contacts";
    }

    @GetMapping("/faq")
    public String handlerFaq(){
        return "faq";
    }

    @PostMapping("/message")
    public String handlerMessage(@Validated MessageFormDto formDto){
        messageService.saveMessage(formDto);
        return REDIRECT_CONT;
    }


}
