package oauthserver.service;

import oauthserver.dto.AccountDto;
import oauthserver.model.Account;
import oauthserver.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class JpaAccountService implements AccountService{

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public JpaAccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
        if(account == null){
            throw new UsernameNotFoundException("User " + email + " not found.");
        }
        return account;
    }


    @Override
    public Account createAccount(AccountDto accountDto) {
        Account account = new Account();
        String encryptedPassword = passwordEncoder.encode(accountDto.getPassword());

        account.setEmail(accountDto.getEmail());
        account.setEncryptedPassword(encryptedPassword);
        accountRepository.save(account);

        return account;
    }

    @Override
    public boolean emailExists(String email) {
        return accountRepository.existsAccountByEmail(email);
    }
}
