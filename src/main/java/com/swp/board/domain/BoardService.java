package com.swp.board.domain;

import com.swp.auth.dto.JwtUserDetails;
import com.swp.board.dto.BoardDeleteRequestDto;
import com.swp.board.dto.BoardCreateRequestDto;
import com.swp.board.dto.BoardResponseDto;
import com.swp.board.dto.BoardUpdateRequestDto;
import com.swp.board.exception.BoardForbiddenException;
import com.swp.board.exception.BoardNotFoundException;
import com.swp.study.Exception.StudyNotFoundException;
import com.swp.study.domain.Study;
import com.swp.study.domain.StudyRepository;
import com.swp.user.domain.Role;
import com.swp.user.domain.User;
import com.swp.user.domain.UserRepository;
import com.swp.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final StudyRepository studyRepository;

    @Transactional
    public void createBoard(BoardCreateRequestDto boardCreateRequestDto){
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user=userRepository.findByProviderAndProviderId(userDetails.getProvider(), userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));

        Study study=studyRepository.findById(boardCreateRequestDto.getStudyId())
                .orElseThrow(() -> new StudyNotFoundException("공부 정보를 찾을 수 없습니다."));

        boardRepository.save(Board.builder()
                .user(user)
                .study(study)
                .content(boardCreateRequestDto.getContent())
                .build());
    }

    @Transactional
    public void updateBoard(BoardUpdateRequestDto boardUpdateRequestDto){
        Board updatedBoard=boardRepository.findById(boardUpdateRequestDto.getBoardId())
                .orElseThrow(() -> new BoardNotFoundException("글을 찾을 수 없습니다."));

        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user=userRepository.findByProviderAndProviderId(userDetails.getProvider(), userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));

        if(user.equals(updatedBoard.getUser())) {
            //글을 쓴 사람만이 글을 수정할 권한이 있다고 가정함
            updatedBoard.updateContent(boardUpdateRequestDto.getContent());
            boardRepository.save(updatedBoard);
        }
        else { throw new BoardForbiddenException("글을 수정할 권한이 없습니다."); }
    }

    @Transactional
    public void deleteBoard(BoardDeleteRequestDto boardDeleteRequestDto){
        Board board=boardRepository.findById(boardDeleteRequestDto.getBoardId())
                .orElseThrow(() -> new BoardNotFoundException("글을 찾을 수 없습니다."));

        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user=userRepository.findByProviderAndProviderId(userDetails.getProvider(), userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));

        if(user.getRole().equals(Role.ADMIN) || user.equals(board.getUser())){
            //Admin, 글을 쓴 사람만이 글을 삭제할 권한이 있다고 가정함
            boardRepository.delete(board);
        }
        else { throw new BoardForbiddenException("글을 수정할 권한이 없습니다."); }
    }

    @Transactional
    public BoardResponseDto getBoard(Integer boardId){
        Board board=boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("글을 찾을 수 없습니다."));

        return BoardResponseDto.builder()
                .nickname(board.getUser().getNickname())
                .content(board.getContent())
                .createdAt(board.getCreatedAt())
                .build();
    }

    @Transactional
    public List<BoardResponseDto> getBoardListByUserId(Integer userId){
        User user=userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));

        return boardRepository.findAllByUser(user)
                .stream()
                .map(board -> BoardResponseDto.builder()
                        .nickname(board.getUser().getNickname())
                        .content(board.getContent())
                        .createdAt(board.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public List<BoardResponseDto> getMyBoardList(){
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user=userRepository.findByProviderAndProviderId(userDetails.getProvider(), userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));
        return user.getBoardList().stream()
                .map(board -> BoardResponseDto.builder()
                        .nickname(board.getUser().getNickname())
                        .content(board.getContent())
                        .createdAt(board.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}