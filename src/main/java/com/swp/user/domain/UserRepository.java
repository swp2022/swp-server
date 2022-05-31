package com.swp.user.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findById(Integer id);

	Optional<User> findByProviderAndProviderId(String provider, String providerId);

	Optional<User> findByEmail(String email);

	List<User> findByNicknameStartsWith(String nickname, Pageable pageable);
}
