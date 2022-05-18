package com.swp.board.controller;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.swp.auth.dto.JwtUserDetails;
import com.swp.board.domain.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/v1/comment")
@RestController
public class CommentController {

	private final CommentService commentService;

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{commentId}")
	public void deleteComment(@PathVariable Integer commentId) {
		JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();
		commentService.deleteComment(userDetails, commentId);
	}
}
