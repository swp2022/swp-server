package com.swp.study.domain;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swp.user.domain.User;

@Repository
public interface StudyRepository extends JpaRepository<Study, Integer> {

	List<Study> findByUserAndEndAtIsNotNull(User user, Pageable pageable);
}
