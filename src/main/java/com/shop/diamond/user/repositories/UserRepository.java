package com.shop.diamond.user.repositories;

import com.shop.diamond.user.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    @Override
    public List<User> findAll();

    public Optional<User> findFirstByEmail(String email);


}
