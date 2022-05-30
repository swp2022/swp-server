package com.swp.user.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RelationshipRepository extends JpaRepository<Relationship, Integer> {
    Optional<Relationship> findByFromUserAndToUser(User fromUser, User toUser);
    List<Relationship> findByFromUser(User fromUser, Pageable pageable);
    List<Relationship> findByToUser(User toUser, Pageable pageable);
    boolean existsByFromUserAndToUser(User fromUser, User toUser);
}
