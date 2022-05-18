package com.swp.board.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CommentCreateRequestDto {

	@Size(max = 1000, message = "내용은 1000글자를 넘을 수 없습니다")
	private String content;
}
