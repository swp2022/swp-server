package com.swp.board.controller;

import com.swp.auth.dto.JwtUserDetails;
import com.swp.board.domain.BoardService;
import com.swp.board.dto.BoardCreateRequestDto;
import com.swp.board.dto.BoardCreateResponseDto;
import com.swp.board.dto.BoardResponseDto;
import com.swp.board.dto.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/board")
public class BoardController {
    private final BoardService boardService;

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
}
