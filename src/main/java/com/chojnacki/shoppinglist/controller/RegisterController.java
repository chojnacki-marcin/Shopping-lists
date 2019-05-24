package com.chojnacki.shoppinglist.controller;

import com.chojnacki.shoppinglist.dto.UserDto;
import com.chojnacki.shoppinglist.model.Account;
import com.chojnacki.shoppinglist.repository.AccountRepository;
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

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @Autowired
    public RegisterController(PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/register")
    public String showRegistrationForm(UserDto userDto){
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid UserDto userDto, BindingResult bindingResult, HttpServletRequest request) throws ServletException {
        if(bindingResult.hasErrors() ||  accountRepository.existsAccountByEmail(userDto.getEmail())){
            return "register";
        }

        Account account = new Account();
        account.setEmail(userDto.getEmail());
        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        account.setEncryptedPassword(encryptedPassword);
        accountRepository.save(account);
        request.login(userDto.getEmail(), userDto.getPassword());
        return "redirect:/";
    }
}
