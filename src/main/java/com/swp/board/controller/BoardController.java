package com.swp.board.controller;

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
public class BoardController {
    private final BoardService boardService;

    @GetMapping(value="/v1/board/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public BoardResponseDto getBoard(@PathVariable Integer boardId) {
        return boardService.getBoard(boardId);
    }

    @GetMapping(value="/v1/board/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BoardResponseDto> getAllBoardListByUserId(@PathVariable Integer userId){
        return boardService.getBoardListByUserId(userId);
    }

    @PostMapping(value="/v1/board")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBoard(@RequestBody BoardCreateRequestDto boardCreateRequestDto){
        boardService.createBoard(boardCreateRequestDto);
    }

    @PutMapping(value="/v1/board")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBoard(@RequestBody BoardUpdateRequestDto boardUpdateRequestDto){
        boardService.updateBoard(boardUpdateRequestDto);
    }

    @DeleteMapping(value="/v1/board")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBoard(@RequestBody BoardDeleteRequestDto boardDeleteRequestDto){
        boardService.deleteBoard(boardDeleteRequestDto);
    }
}
