package com.swp.user.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

        Optional<User> findById(Integer id);

        Optional<User> findByProviderAndProviderId(String provider, String providerId);

        Optional<User> findByEmail(String email);

        Optional<User> findByNickname(String nickname);
}
