package com.team17.preProject.domain.vote.entity;

import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.businessLogic.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
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

    @Builder
    public VoteAnswer(long vaId, Answer answer, Member member, int vote) {
        validateVote(vote);

        this.vaId = vaId;
        this.answer = answer;
        this.member = member;
        this.vote = vote;
    }

    private void validateVote(int vote) {
        if (vote != 1 && vote != -1) {
            throw new BusinessLogicException(ExceptionCode.VOTE_VALUE_INCORRECT);
        }
    }
}
