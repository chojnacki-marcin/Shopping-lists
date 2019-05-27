package com.chojnacki.oauthserver.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AccountDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 4, max=30)
    private String password;


    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
