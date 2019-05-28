package oauthserver.controller;


import oauthserver.dto.AccountDto;
import oauthserver.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final AccountService accountService;

    @Autowired
    public RegisterController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String showRegistrationForm(Model model){
        model.addAttribute("userDto", new AccountDto());
        return "register";
    }

    @PostMapping
    public String processRegistration(@Valid AccountDto userDto, BindingResult bindingResult, Model model) throws ServletException {
        if(bindingResult.hasErrors() ||  accountService.emailExists(userDto.getEmail())){
            model.addAttribute("userDto", userDto);
            return "register";
        }
        accountService.createAccount(userDto);
        return "redirect:/login";
    }
}
