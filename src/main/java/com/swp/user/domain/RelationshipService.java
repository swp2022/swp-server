package com.swp.user.domain;

import com.swp.auth.dto.JwtUserDetails;
import com.swp.user.dto.RelationshipRequestDto;
import com.swp.user.exception.RelationshipNotFoundException;
import com.swp.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RelationshipService {
    private final RelationshipRepository relationshipRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createRelationship(JwtUserDetails userDetails, RelationshipRequestDto relationshipRequestDto) {
        User fromUser = userRepository.findByProviderAndProviderId(userDetails.getProvider(), userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));
        User toUser = userRepository.findById(relationshipRequestDto.getToUserId())
                .orElseThrow(() -> new UserNotFoundException("팔로우할 유저를 찾을 수 없습니다."));
        relationshipRepository.save(Relationship.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .build());
    }

    @Transactional
    public void deleteRelationship(JwtUserDetails userDetails, RelationshipRequestDto relationshipRequestDto) {
        User fromUser = userRepository.findByProviderAndProviderId(userDetails.getProvider(), userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));
        User toUser = userRepository.findById(relationshipRequestDto.getToUserId())
                .orElseThrow(() -> new UserNotFoundException("팔로우 취소할 유저를 찾을 수 없습니다."));

        relationshipRepository.delete(
                relationshipRepository.findByFromUserAndToUser(fromUser, toUser)
                        .orElseThrow(() -> new RelationshipNotFoundException(toUser.getNickname() + "유저를 팔로우하고 있지 않습니다."))
        );
    }
}
