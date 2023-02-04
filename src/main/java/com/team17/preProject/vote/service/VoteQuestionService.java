package com.team17.preProject.vote.service;

import com.team17.preProject.vote.entity.VoteQuestion;

public interface VoteQuestionService {
    VoteQuestion voteGood(long memberId, long answerId);
    VoteQuestion voteBad(long memberId, long answerId);
}
