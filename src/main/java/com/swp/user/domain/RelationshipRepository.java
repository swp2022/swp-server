package com.swp.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RelationshipRepository extends JpaRepository<Relationship, Integer> {
    Optional<Relationship> findByFromUserAndToUser(User fromUser,User toUser);
}
