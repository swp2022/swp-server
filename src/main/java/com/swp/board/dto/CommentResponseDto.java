package com.swp.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swp.board.domain.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponseDto {

	private Integer commentId;
	private Integer boardId;
	private Integer userId;
	private String content;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Seoul")
	private LocalDateTime createdAt;

	public static CommentResponseDto from(Comment comment) {
		return CommentResponseDto.builder()
			.commentId(comment.getCommentId())
			.boardId(comment.getBoard().getBoardId())
			.userId(comment.getUser().getId())
			.content(comment.getContent())
			.createdAt(comment.getCreatedAt())
			.build();
	}
}