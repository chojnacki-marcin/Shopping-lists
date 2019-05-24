package com.chojnacki.shoppinglist.service;

import com.chojnacki.shoppinglist.model.Account;
import com.chojnacki.shoppinglist.model.User;
import com.chojnacki.shoppinglist.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserService implements UserService {
    private static final String INTERNAL_PREFIX = "shopping-lists-internal";

    private final UserRepository userRepository;

    public JpaUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUserFromInternalAccount(Account account) {
        var userOptional = getUserFromInternalAccount(account);
        //create new user if the account isn't already linked with user entity
        if(userOptional.isEmpty()) {
            User user = new User();
            user.setProvider(User.AuthenticationProvider.INTERNAL);
            user.setUserIdentifier(generateInternalIdentifier(account.getId()));

            userRepository.save(user);

            return user;
        }
        return userOptional.get();

    }

    @Override
    public Optional<User> getUserFromInternalAccount(Account account) {
        String id = generateInternalIdentifier(account.getId());
        return userRepository.findById(id);
    }


    private String generateInternalIdentifier(long accountId) {
        return String.format("%s:%d", INTERNAL_PREFIX, accountId);
    }
}
