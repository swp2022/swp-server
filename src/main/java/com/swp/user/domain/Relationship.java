package com.swp.user.domain;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(RelationshipId.class)
@Table(name = "relationships")
public class Relationship {
    @Id
    @ManyToOne
    @JoinColumn(name="from_user_id",nullable = false)
    private User fromUser;

    @Id
    @ManyToOne
    @JoinColumn(name="to_user_id",nullable = false)
    private User toUser;
}
