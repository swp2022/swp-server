package com.swp.board.domain;

import com.swp.common.domain.CreatedAtEntity;
import com.swp.study.domain.Study;
import com.swp.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boards")
@Getter
@RequiredArgsConstructor
public class Board extends CreatedAtEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id", nullable = false)
	private Integer boardId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "study_id", nullable = false)
	private Study study;

	@Column(name = "content", length = 1000, nullable = true)
	private String content;

	@OneToMany(mappedBy = "board")
	private List<Comment> commentList = new ArrayList<>();

	@Builder
	public Board(User user, Study study, String content) {
		this.user = user;
		this.study = study;
		this.content = content;
	}

	public Board updateContent(String content) {
		this.content = content;
		return this;
	}
}
