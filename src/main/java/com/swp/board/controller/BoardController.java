package com.swp.board.controller;

import com.swp.auth.dto.JwtUserDetails;
import com.swp.board.domain.BoardService;
import com.swp.board.domain.CommentService;
import com.swp.board.dto.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
	public List<BoardResponseDto> getAllBoardListByUserId(@PathVariable Integer userId,
	                                                      @RequestParam(name = "size", defaultValue = "20") Integer size,
	                                                      @RequestParam(name = "page", defaultValue = "0") Integer page) {
		return boardService.getBoardListByUserId(userId, size, page);
	}

	@GetMapping(value = "/follow")
	public List<BoardResponseDto> getFollowingBoardList(@AuthenticationPrincipal JwtUserDetails userDetails,
	                                                    @RequestParam(name = "size", defaultValue = "20") Integer size,
	                                                    @RequestParam(name = "page", defaultValue = "0") Integer page) {
		return boardService.getFollowingUserBoard(userDetails, size, page);
	}

	@GetMapping(value = "/my")
	public List<BoardResponseDto> getMyBoardList(@AuthenticationPrincipal JwtUserDetails userDetails,
	                                             @RequestParam(name = "size", defaultValue = "20") Integer size,
	                                             @RequestParam(name = "page", defaultValue = "0") Integer page) {
		return boardService.getMyBoardList(userDetails, size, page);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BoardCreateResponseDto createBoard(@AuthenticationPrincipal JwtUserDetails userDetails, @RequestBody BoardCreateRequestDto boardCreateRequestDto) {
		return boardService.createBoard(userDetails, boardCreateRequestDto);
	}

	@PutMapping(value = "/{boardId}")
	public void updateBoard(@AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable Integer boardId, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {
		boardService.updateBoard(userDetails, boardId, boardUpdateRequestDto);
	}

	@DeleteMapping(value = "/{boardId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBoard(@AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable Integer boardId) {
		boardService.deleteBoard(userDetails, boardId);
	}

	@ApiOperation("댓글 가져오기")
	@GetMapping("/{boardId}/comment")
	public List<CommentResponseDto> getComments(@PathVariable Integer boardId,
	                                            @RequestParam(name = "size", defaultValue = "20") Integer size,
	                                            @RequestParam(name = "page", defaultValue = "0") Integer page) {
		return commentService.getComments(boardId, size, page);
	}

	@ApiOperation("댓글 작성")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/{boardId}/comment")
	public CommentResponseDto writeComment(@AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable Integer boardId, @Valid @RequestBody CommentCreateRequestDto requestDto) {
		return commentService.writeComment(userDetails, boardId, requestDto);
	}

	@ApiOperation(value = "댓글 삭제", notes = "댓글이 속한 boardId로 요청해야 합니다")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{boardId}/comment/{commentId}")
	public void deleteComment(@AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable Integer boardId, @PathVariable Integer commentId) {
		commentService.deleteComment(userDetails, boardId, commentId);
	}
}
