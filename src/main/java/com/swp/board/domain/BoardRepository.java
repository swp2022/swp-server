package com.swp.board.domain;

import com.swp.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
	List<Board> findByUser(User user, Pageable pageable);
	List<Board> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
	List<Board> findAllByUserIn(List<User> userList, Pageable pageable);
}
