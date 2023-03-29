package com.team17.preProject.domain.follow.entity;

import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class FollowAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long faId;

    @ManyToOne
    @JoinColumn(name = "ANSWER_ID")
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @Builder
    private FollowAnswer(long faId, Answer answer, Member member) {
        this.faId = faId;
        this.answer = answer;
        this.member = member;
    }
}
