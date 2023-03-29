package com.team17.preProject.domain.follow.entity;

import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.question.entity.Question;
import lombok.AllArgsConstructor;
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
@Entity
@EntityListeners(AuditingEntityListener.class)
public class FollowQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fqId;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public FollowQuestion(long fqId, Question question, Member member) {
        this.fqId = fqId;
        this.question = question;
        this.member = member;
    }
}
