package com.swp.study.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.swp.common.domain.CreatedAtEntity;
import com.swp.user.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "studies")
@Getter
@NoArgsConstructor
public class Study extends CreatedAtEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "study_id", nullable = false)
	private Integer studyId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "end_at")
	private LocalDateTime endAt;

	@OneToMany(mappedBy = "study")
	private List<StudyLog> studyLogList = new ArrayList<>();

	public void finishStudy() {
		this.endAt = LocalDateTime.now();
	}

	@Builder
	public Study(User user, LocalDateTime endAt) {
		this.user = user;
		this.endAt = endAt;
	}

}
