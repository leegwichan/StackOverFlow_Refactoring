package com.team17.preProject.domain.question.entity;

import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.audit.Auditable;
import com.team17.preProject.domain.follow.entity.FollowQuestion;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.vote.entity.VoteQuestion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
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
    private long view = 0;

    @Column(nullable = false)
    private long vote = 0;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "question",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
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


}
