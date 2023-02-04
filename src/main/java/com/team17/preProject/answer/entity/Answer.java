package com.team17.preProject.answer.entity;

import com.team17.preProject.audit.Auditable;
import com.team17.preProject.follow.entity.FollowAnswer;
import com.team17.preProject.member.entity.Member;
import com.team17.preProject.question.entity.Question;
import com.team17.preProject.vote.entity.VoteAnswer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Answer extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answerId;

    @Column(nullable = false, length = 10000)
    private String content;

    @Column(nullable = false)
    private long vote = 0;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "answer",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<FollowAnswer> followQuestions;

    @OneToMany(mappedBy = "answer",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private List<VoteAnswer> voteAnswers;
}
