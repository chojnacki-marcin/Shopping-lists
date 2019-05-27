package com.chojnacki.shoppinglist.webclient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

//    private final AccountService accountService;
//
//    @Autowired
//    public RegisterController(AccountService accountService) {
//        this.accountService = accountService;
//    }
//
//    @GetMapping
//    public String showRegistrationForm(UserDto userDto){
//        return "register";
//    }
//
//    @PostMapping
//    public String processRegistration(@Valid UserDto userDto, BindingResult bindingResult, HttpServletRequest request) throws ServletException {
//        if(bindingResult.hasErrors() ||  accountService.emailExists(userDto.getEmail())){
//            return "register";
//        }
//
//        accountService.createAccount(userDto);
//        request.login(userDto.getEmail(), userDto.getPassword());
//        return "redirect:/";
//    }
}
