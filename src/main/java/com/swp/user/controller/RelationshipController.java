package com.swp.user.controller;

import com.swp.user.domain.RelationshipService;
import com.swp.user.dto.RelationshipRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RelationshipController {

    private final RelationshipService relationshipService;

    @PostMapping(value = "/v1/relationship")
    @ResponseStatus(HttpStatus.CREATED)
    public void followUser(@RequestBody RelationshipRequestDto relationshipRequestDto){
        relationshipService.createRelationship(relationshipRequestDto);
    }

    @DeleteMapping(value = "/v1/relationship")
    public void unfollowUser(@RequestBody RelationshipRequestDto relationshipRequestDto){
        relationshipService.deleteRelationship(relationshipRequestDto);
    }
}
