package com.swp.common.domain;

import javax.persistence.*;

@Entity
@Table(name = "relationships")
public class Relationship {
    @Id
    @ManyToOne
    @JoinColumn(name ="user_id",nullable = false)
    private Long fromUserId;

    @Id
    @ManyToOne
    @JoinColumn(name ="user_id",nullable = false)
    private Long toUserId;


}
