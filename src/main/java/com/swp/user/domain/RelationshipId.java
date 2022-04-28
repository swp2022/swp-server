package com.swp.user.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class RelationshipId implements Serializable {
    private Long fromUser;
    private Long toUser;

    @Override
    public boolean equals(Object obj) {
        if(obj == null || this.getClass()!=obj.getClass())
            return false;
        return (((RelationshipId) obj).fromUser .equals( this.fromUser )&& ((RelationshipId) obj).toUser.equals( this.toUser) );
    }
}
