package com.swp.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swp.board.domain.Board;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponseDto {
    private Integer boardId;
    private String nickname;
    private String profileImage;
    // Study 내용 추가 예정
    private String content;
    private Integer commentCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Seoul")
    private LocalDateTime createdAt;

    public static BoardResponseDto from(Board board) {
        return BoardResponseDto.builder()
                .boardId((board.getBoardId()))
                .nickname(board.getUser().getNickname())
                .profileImage(board.getUser().getProfileImage())
                .content(board.getContent())
                .commentCount(board.getCommentList().size())
                .createdAt(board.getCreatedAt())
                .build();
    }
}
