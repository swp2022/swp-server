package com.swp.user.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.swp.board.domain.Board;
import com.swp.common.domain.CreatedAtEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User extends CreatedAtEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false)
	private Integer id;

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

	@OneToMany(mappedBy = "user")
	private List<Board> boardList = new ArrayList<>();

	@Builder
	public User(String email, String nickname, String profileImage, String provider, String providerId) {
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
