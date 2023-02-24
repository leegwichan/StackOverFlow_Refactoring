package com.team17.preProject.domain.question.entity;

import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.audit.Auditable;
import com.team17.preProject.domain.follow.entity.FollowQuestion;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.vote.entity.VoteQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Question extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 10000)
    private String content;

    @Column(nullable = false)
    @Builder.Default
    private long view = 0;

    @Column(nullable = false)
    @Builder.Default
    private long vote = 0;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "question",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    @Builder.Default
    private List<Answer> answers = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "BEST_ANSWER_ID")
    private Answer bestAnswer;

    @OneToMany(mappedBy = "question",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<FollowQuestion> followQuestions;

    @OneToMany(mappedBy = "question",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<VoteQuestion> voteQuestions;

    public void setInitialMember(Member member) {
        this.member = Optional.of(this.member).orElse(member);
    }

    public void setBestAnswer(Answer answer) {
        this.bestAnswer = answer;
    }

    public void setVote(long vote) {
        this.vote = vote;
    }

    public void update(Question question) {
        this.title = Optional.of(question.getTitle()).orElse(this.title);
        this.content = Optional.of(question.getContent()).orElse(this.content);
    }

    public void increaseView() {
        this.view++;
    }
}
