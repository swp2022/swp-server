package com.swp.user.domain;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	void findUser() {
		Optional<User> user = userRepository.findByProviderAndProviderId("44", "55");
		if (user.isPresent()) {
			System.out.println("user.get().getId() = " + user.get().getNickname());
		}
	}
}