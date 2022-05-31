package com.swp.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swp.board.domain.Board;
import com.swp.study.dto.StudyResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponseDto {
	private Integer boardId;
	private String nickname;
	private String profileImage;
	private StudyResponseDto studyResponseDto;
	private String content;
	private Integer commentCount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Seoul")
	private LocalDateTime createdAt;

	public static BoardResponseDto from(Board board) {
		return BoardResponseDto.builder()
			.boardId((board.getBoardId()))
			.nickname(board.getUser().getNickname())
			.profileImage(board.getUser().getProfileImage())
			.studyResponseDto(StudyResponseDto.from(board.getStudy()))
			.content(board.getContent())
			.commentCount(board.getCommentList().size())
			.createdAt(board.getCreatedAt())
			.build();
	}
}
