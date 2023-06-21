package com.sda.java3.ecommerce.repositories;

import com.sda.java3.ecommerce.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
	List<User> findAll();

    Optional<User> findById(UUID id);

    User findByEmail(String email);

    User save(User user);

    void deleteById(UUID id);
}
