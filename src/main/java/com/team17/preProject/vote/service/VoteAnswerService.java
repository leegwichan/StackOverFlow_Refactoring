package com.team17.preProject.vote.service;

import com.team17.preProject.vote.entity.VoteAnswer;

public interface VoteAnswerService {
    VoteAnswer voteGood(long memberId, long answerId);
    VoteAnswer voteBad(long memberId, long answerId);
}
