package oauthserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AccountController {


    @GetMapping("/me")
    public Map<String, String> user(Principal principal) {
        Map<String, String> map = new HashMap<>();
        map.put("name", principal.getName());
        return map;
    }


}
