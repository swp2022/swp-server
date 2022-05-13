package com.swp.user.controller;

import com.swp.user.domain.RelationshipService;
import com.swp.user.dto.RelationshipRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class RelationshipController {

    private final RelationshipService relationshipService;

    @PostMapping(value = "/v1/relationship")
    public ResponseEntity<?> followUser(@RequestBody RelationshipRequestDto relationshipRequestDto){
        relationshipService.createRelationship(relationshipRequestDto);
        return new ResponseEntity<> (relationshipRequestDto,HttpStatus.OK);
    }

    @DeleteMapping(value="/v1/relationship")
    public ResponseEntity<?> unfollowUser(@RequestBody RelationshipRequestDto relationshipRequestDto){
        relationshipService.deleteRelationship(relationshipRequestDto);
        return new ResponseEntity<> (relationshipRequestDto,HttpStatus.OK);
    }
}
