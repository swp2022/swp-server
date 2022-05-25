package com.swp.study.domain;

import com.swp.user.domain.User;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "studies")
public class Study {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "study_id", nullable = false)
	private Integer studyId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "start_at", nullable = false)
	@CreatedDate
	private LocalDateTime startAt;

	@Column(name = "end_at")
	private LocalDateTime endAt;

	@OneToMany(mappedBy = "study")
	private List<StudyLog> studyLogList = new ArrayList<>();
}
