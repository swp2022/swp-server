package com.swp.board.controller;

import com.swp.board.domain.Board;
import com.swp.board.domain.BoardService;
import com.swp.board.dto.BoardDeleteRequestDto;
import com.swp.board.dto.BoardCreateRequestDto;
import com.swp.board.dto.BoardResponseDto;
import com.swp.board.dto.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        return boardService.getMyBoardList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBoard(@RequestBody BoardCreateRequestDto boardCreateRequestDto) {
        boardService.createBoard(boardCreateRequestDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBoard(@RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {
        boardService.updateBoard(boardUpdateRequestDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBoard(@RequestBody BoardDeleteRequestDto boardDeleteRequestDto) {
        boardService.deleteBoard(boardDeleteRequestDto);
    }
}
