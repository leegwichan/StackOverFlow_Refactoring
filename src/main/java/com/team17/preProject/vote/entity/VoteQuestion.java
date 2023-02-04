package com.team17.preProject.vote.entity;

import com.team17.preProject.member.entity.Member;
import com.team17.preProject.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class VoteQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long vqId;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Column(nullable = false)
    private int vote;
}
