package com.team17.preProject.helper.stub;

import com.team17.preProject.domain.vote.entity.VoteAnswer;
import com.team17.preProject.domain.vote.entity.VoteQuestion;

public class VoteStub {

    public static final VoteAnswer VOTE_ANSWER_ENTITY;
    static {
        VOTE_ANSWER_ENTITY = VoteAnswer.builder()
                .vaId(8L)
                .member(MemberStub.ENTITY)
                .answer(AnswerStub.ENTITY)
                .vote(1).build();
    }

    public static final VoteQuestion VOTE_QUESTION_ENTITY;
    static {
        VOTE_QUESTION_ENTITY = VoteQuestion.builder()
                .vqId(10L)
                .member(MemberStub.ENTITY)
                .question(QuestionStub.ENTITY)
                .vote(-1)
                .build();
    }
}
