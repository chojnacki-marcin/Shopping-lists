package oauthserver.service;


import oauthserver.dto.AccountDto;
import oauthserver.model.Account;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface AccountService extends UserDetailsService {

    Account createAccount(AccountDto account);

    boolean emailExists(String email);
}
