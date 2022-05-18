package com.swp.board.controller;

import com.swp.auth.dto.JwtUserDetails;
import com.swp.board.domain.BoardService;
import com.swp.board.domain.CommentService;
import com.swp.board.dto.BoardCreateRequestDto;
import com.swp.board.dto.BoardCreateResponseDto;
import com.swp.board.dto.BoardResponseDto;
import com.swp.board.dto.BoardUpdateRequestDto;
import com.swp.board.dto.CommentCreateRequestDto;
import com.swp.board.dto.CommentResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/board")
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping(value = "/{boardId}")
    public BoardResponseDto getBoard(@PathVariable Integer boardId) {
        return boardService.getBoard(boardId);
    }

    @GetMapping(value = "/user/{userId}")
    public List<BoardResponseDto> getAllBoardListByUserId(@PathVariable Integer userId) {
        return boardService.getBoardListByUserId(userId);
    }

    @GetMapping(value = "/my")
    public List<BoardResponseDto> getMyBoardList() {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return boardService.getMyBoardList(userDetails);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardCreateResponseDto createBoard(@RequestBody BoardCreateRequestDto boardCreateRequestDto) {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return boardService.createBoard(userDetails, boardCreateRequestDto);
    }

    @PutMapping(value = "/{boardId}")
    public void updateBoard(@PathVariable Integer boardId, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        boardService.updateBoard(userDetails, boardId, boardUpdateRequestDto);
    }

    @DeleteMapping(value = "/{boardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBoard(@PathVariable Integer boardId) {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        boardService.deleteBoard(userDetails, boardId);
    }

    @GetMapping("/{boardId}/comment")
    public List<CommentResponseDto> getComments(@PathVariable Integer boardId) {
        return commentService.getComments(boardId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{boardId}/comment")
    public CommentResponseDto writeComment(@PathVariable Integer boardId,
        @RequestBody CommentCreateRequestDto requestDto) {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();
        return commentService.writeComment(userDetails, boardId, requestDto);
    }
}
