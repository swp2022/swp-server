package com.swp.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardCreateRequestDto {
	private Integer studyId;
	private String content;
}
