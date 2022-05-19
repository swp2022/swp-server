package com.swp.board.domain;

import static java.util.stream.Collectors.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swp.auth.dto.JwtUserDetails;
import com.swp.board.dto.CommentCreateRequestDto;
import com.swp.board.dto.CommentResponseDto;
import com.swp.board.exception.BoardNotFoundException;
import com.swp.board.exception.CommentForbiddenException;
import com.swp.board.exception.CommentNotFoundException;
import com.swp.user.domain.Role;
import com.swp.user.domain.User;
import com.swp.user.domain.UserRepository;
import com.swp.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final UserRepository userRepository;
	private final BoardRepository boardRepository;
	private final CommentRepository commentRepository;

	@Transactional
	public CommentResponseDto writeComment(JwtUserDetails userDetails, Integer boardId,
		CommentCreateRequestDto requestDto) {
		User user = userRepository.findByProviderAndProviderId(userDetails.getProvider(), userDetails.getUsername())
			.orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));

		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new BoardNotFoundException("글을 찾을 수 없습니다"));

		Comment savedComment = commentRepository.save(
			Comment.builder()
				.user(user)
				.board(board)
				.content(requestDto.getContent())
				.build()
		);

		return CommentResponseDto.from(savedComment);
	}

	@Transactional
	public void deleteComment(JwtUserDetails userDetails, Integer boardId, Integer commentId) {
		User user = userRepository.findByProviderAndProviderId(userDetails.getProvider(), userDetails.getUsername())
			.orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));

		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new BoardNotFoundException("글을 찾을 수 없습니다"));

		Comment comment = commentRepository.findById(commentId)
			.filter(c -> c.getBoard().equals(board))
			.orElseThrow(CommentNotFoundException::new);

		if (comment.getUser().equals(user) || user.getRole().equals(Role.ADMIN)) {
			commentRepository.delete(comment);
		} else {
			throw new CommentForbiddenException();
		}
	}

	@Transactional
	public List<CommentResponseDto> getComments(Integer boardId) {
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new BoardNotFoundException("글을 찾을 수 없습니다"));

		return board.getCommentList()
			.stream()
			.map(CommentResponseDto::from)
			.collect(toList());
	}
}
