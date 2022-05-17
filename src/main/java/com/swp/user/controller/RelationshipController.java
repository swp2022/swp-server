package com.swp.user.controller;

import com.swp.auth.dto.JwtUserDetails;
import com.swp.user.domain.RelationshipService;
import com.swp.user.dto.RelationshipRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RelationshipController {

    private final RelationshipService relationshipService;

    @PostMapping(value = "/v1/relationship")
    @ResponseStatus(HttpStatus.CREATED)
    public void followUser(@RequestBody RelationshipRequestDto relationshipRequestDto){
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        relationshipService.createRelationship(relationshipRequestDto);
    }

    @DeleteMapping(value = "/v1/relationship")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unfollowUser(@RequestBody RelationshipRequestDto relationshipRequestDto){
        JwtUserDetails userDetails = (JwtUserDetails)SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        relationshipService.deleteRelationship(relationshipRequestDto);
    }
}
