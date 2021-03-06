package com.swp.user.domain;

import com.swp.board.domain.Board;
import com.swp.common.domain.CreatedAtEntity;
import com.swp.study.domain.Study;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User extends CreatedAtEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false)
	private Integer id;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(name = "email", length = 100, nullable = false, unique = true)
	private String email;

	@Column(name = "nickname", length = 100, nullable = false, unique = true)
	private String nickname;

	@Column(name = "profile_image", nullable = true, length = 1000)
	private String profileImage;

	@Column(name = "provider")
	private String provider;

	@Column(name = "provider_id")
	private String providerId;

	@Column(name = "deleted_at", nullable = true)
	private LocalDateTime deletedAt;

	@OneToMany(mappedBy = "fromUser", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Relationship> followingList = new ArrayList<>();

	@OneToMany(mappedBy = "toUser", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Relationship> followerList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Board> boardList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Study> studyList = new ArrayList<>();

	@Builder
	public User(Role role, String email, String nickname, String profileImage, String provider, String providerId) {
		this.role = role;
		this.email = email;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.provider = provider;
		this.providerId = providerId;
	}

	public User updateProfile(String nickname, String profileImage) {
		this.nickname = nickname;
		this.profileImage = profileImage;
		return this;
	}
}
