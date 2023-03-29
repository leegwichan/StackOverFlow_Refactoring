package com.team17.preProject.domain.vote.entity;

import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.question.entity.Question;
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

    @Builder
    public VoteQuestion(long vqId, Question question, Member member, int vote) {
        validateVote(vote);

        this.vqId = vqId;
        this.question = question;
        this.member = member;
        this.vote = vote;
    }

    private void validateVote(int vote) {
        if (vote != 1 && vote != -1) {
            throw new BusinessLogicException(ExceptionCode.VOTE_VALUE_INCORRECT);
        }
    }
}
