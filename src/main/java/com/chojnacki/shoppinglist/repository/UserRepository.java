package com.chojnacki.shoppinglist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.chojnacki.shoppinglist.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
