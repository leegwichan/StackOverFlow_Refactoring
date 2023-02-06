package com.team17.preProject.domain.vote.entity;

import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.member.entity.Member;
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
public class VoteAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long vaId;

    @ManyToOne
    @JoinColumn(name = "ANSWER_ID", nullable = false)
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Column(nullable = false)
    private int vote;


}
