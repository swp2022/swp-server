package com.swp.board.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CommentCreateRequestDto {

	@NotBlank(message = "내용이 비어있습니다")
	@Size(max = 1000, message = "내용은 1000글자를 넘을 수 없습니다")
	private String content;
}
