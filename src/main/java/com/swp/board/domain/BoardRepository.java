package com.swp.board.domain;

import com.swp.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    Optional<Board> findById(Integer id);

    List<Board> findAllByUser(User user);
}
