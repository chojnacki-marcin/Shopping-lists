package com.chojnacki.shoppinglist.repository;

import com.chojnacki.shoppinglist.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByEmail(String email);
    boolean existsAccountByEmail(String email);
}
