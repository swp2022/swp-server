package com.swp.user.domain;
import javax.persistence.*;

@Entity
@Table(name = "relationships")
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relationship_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="from_user_id",nullable = false)
    private User fromUser;

    @ManyToOne
    @JoinColumn(name="to_user_id",nullable = false)
    private User toUser;
}
