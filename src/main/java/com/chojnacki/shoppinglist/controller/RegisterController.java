package com.chojnacki.shoppinglist.controller;

import com.chojnacki.shoppinglist.dto.UserDto;
import com.chojnacki.shoppinglist.repository.AccountRepository;
import com.chojnacki.shoppinglist.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegisterController {

    private final AccountService accountService;

    @Autowired
    public RegisterController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(UserDto userDto){
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid UserDto userDto, BindingResult bindingResult, HttpServletRequest request) throws ServletException {
        if(bindingResult.hasErrors() ||  accountService.emailExists(userDto.getEmail())){
            return "register";
        }

        accountService.createAccount(userDto);
        request.login(userDto.getEmail(), userDto.getPassword());
        return "redirect:/";
    }
}
