package com.swp.common.domain;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class users {
    @Id
    @Column(name = "user_id",nullable = false)
    private Long user_id;

    @Column(name="email",length=100,nullable = false,unique = true)
    private String email;

    @Column(name="nickname",length=100,nullable=false,unique = true)
    private String nickname;

    @Column(name="created_at",nullable=false)
    @CreatedDate
    private LocalDateTime created_at;

    @Column(name="deleted_at")
    private LocalDateTime deleted_at;

}
