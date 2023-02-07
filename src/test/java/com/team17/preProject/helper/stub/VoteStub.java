package com.team17.preProject.helper.stub;

import com.team17.preProject.domain.vote.entity.VoteAnswer;
import com.team17.preProject.domain.vote.entity.VoteQuestion;

public class VoteStub {

    public static final VoteAnswer VOTE_ANSWER_ENTITY;
    static {
        VOTE_ANSWER_ENTITY = new VoteAnswer();
        VOTE_ANSWER_ENTITY.setVaId(8L);
        VOTE_ANSWER_ENTITY.setMember(MemberStub.ENTITY);
        VOTE_ANSWER_ENTITY.setAnswer(AnswerStub.ENTITY);
        VOTE_ANSWER_ENTITY.setVote(1);
    }

    public static final VoteQuestion VOTE_QUESTION_ENTITY;
    static {
        VOTE_QUESTION_ENTITY = new VoteQuestion();
        VOTE_QUESTION_ENTITY.setVqId(10L);
        VOTE_QUESTION_ENTITY.setMember(MemberStub.ENTITY);
        VOTE_QUESTION_ENTITY.setQuestion(QuestionStub.ENTITY);
        VOTE_QUESTION_ENTITY.setVote(-1);
    }
}
