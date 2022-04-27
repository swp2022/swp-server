package com.swp.user.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class RelationshipId implements Serializable {
    private Long fromUser;
    private Long toUser;
}
