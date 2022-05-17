package com.swp.board.controller;

import com.swp.auth.dto.JwtUserDetails;
import com.swp.board.domain.Board;
import com.swp.board.domain.BoardService;
import com.swp.board.dto.BoardDeleteRequestDto;
import com.swp.board.dto.BoardCreateRequestDto;
import com.swp.board.dto.BoardResponseDto;
import com.swp.board.dto.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping(value = "/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public BoardResponseDto getBoard(@PathVariable Integer boardId) {
        return boardService.getBoard(boardId);
    }

    @GetMapping(value = "/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BoardResponseDto> getAllBoardListByUserId(@PathVariable Integer userId) {
        return boardService.getBoardListByUserId(userId);
    }

    @GetMapping(value = "/my")
    @ResponseStatus(HttpStatus.OK)
    public List<BoardResponseDto> getMyBoardList() {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return boardService.getMyBoardList(userDetails);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBoard(@RequestBody BoardCreateRequestDto boardCreateRequestDto) {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        boardService.createBoard(userDetails, boardCreateRequestDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBoard(@RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        boardService.updateBoard(userDetails, boardUpdateRequestDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBoard(@RequestBody BoardDeleteRequestDto boardDeleteRequestDto) {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        boardService.deleteBoard(userDetails, boardDeleteRequestDto);
    }
}
