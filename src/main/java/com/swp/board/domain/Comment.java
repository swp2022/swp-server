package com.swp.board.domain;

import com.swp.common.domain.CreatedAtEntity;
import com.swp.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "comments")
public class Comment extends CreatedAtEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id", nullable = false)
	private Integer commentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id", nullable = false)
	private Board board;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "content", length = 1000, nullable = false)
	private String content;

	@Builder
	public Comment(Board board, User user, String content) {
		this.board = board;
		this.user = user;
		this.content = content;
	}
}
