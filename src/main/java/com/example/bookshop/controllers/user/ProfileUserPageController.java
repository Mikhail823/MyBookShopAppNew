package com.example.bookshop.controllers.user;

import com.example.bookshop.dto.ProfileFormDto;
import com.example.bookshop.security.BookstoreUserDetails;
import com.example.bookshop.security.BookstoreUserRegister;
import com.example.bookshop.service.BookService;
import com.example.bookshop.service.PaymentService;
import com.example.bookshop.service.UserService;
import com.example.bookshop.struct.enums.ContactType;
import com.example.bookshop.struct.payments.BalanceTransactionEntity;
import com.example.bookshop.struct.user.UserContactEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProfileUserPageController {
    private final String PROF_REDIRECT = "redirect:/profile";
    @Autowired
    private final BookstoreUserRegister userRegister;
    @Autowired
    private final UserService userService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private final BookService bookService;



    @GetMapping("/my")
    public String handleMy(@AuthenticationPrincipal BookstoreUserDetails user, Model model) {
        model.addAttribute("myBooks", bookService.getNotReadBooks(user.getContact().getUserId().getId()));
        model.addAttribute("curUsr", user.getContact().getUserId());
        return "my";
    }

    @GetMapping("/profile")
    public String handlerProfile(@AuthenticationPrincipal BookstoreUserDetails user, Model model) {
        List<BalanceTransactionEntity> listTransaction =
                paymentService.getListTransactionUser(user.getContact().getUserId());
        UserContactEntity email = userService.findContactUser(user.getContact().getUserId(), ContactType.EMAIL);
        UserContactEntity phone = userService.findContactUser(user.getContact().getUserId(), ContactType.PHONE);
        model.addAttribute("transactionList", listTransaction);
        model.addAttribute("email", email.getContact());
        model.addAttribute("phone", phone.getContact());
        model.addAttribute("curUsr", user.getContact().getUserId());
        return "profile";
    }

    @GetMapping("/archive")
    public String handlerArchive(Model model, @AuthenticationPrincipal BookstoreUserDetails user) {
        model.addAttribute("myArchiveBooks", bookService.getBooksArchiveUser(user.getContact().getUserId().getId()));
        return "myarchive";
    }

    @PostMapping("/profile/save")
    public String updateProfile(ProfileFormDto profileDto) throws JsonProcessingException {
        userService.confirmChangingUserProfile(profileDto);
        return "redirect:/profile";
    }

    @PostMapping("/payment")
    public RedirectView handlerPayment(@AuthenticationPrincipal BookstoreUserDetails user,
                                       @RequestParam(value = "sum", required = false) Double sum) throws NoSuchAlgorithmException {
        String paymentRedirect = paymentService.getPaymentUrl(user.getContact().getUserId(), sum);
        return new RedirectView(paymentRedirect);
    }

    @GetMapping("/profile/verify/{token}")
    public String handleProfileVerification(@PathVariable String token) throws JsonProcessingException {
        userService.changeUserProfile(token);
        return "redirect:/profile";
    }


}
