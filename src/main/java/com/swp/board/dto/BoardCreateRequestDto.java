package com.swp.board.dto;

import com.swp.study.domain.Study;
import com.swp.user.domain.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardCreateRequestDto {
    private Integer studyId;
    private String content;
}
