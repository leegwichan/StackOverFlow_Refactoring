package com.team17.preProject.answer.service;

import com.team17.preProject.answer.entity.Answer;
import com.team17.preProject.question.entity.Question;
import org.springframework.data.domain.Page;
import java.util.List;

public interface AnswerService {
    public List<Answer> findAnswersByQuestion(long questionId);
    public Page<Answer> findAnswersByMemberId(int page, int size, long memberId);
    public Answer createAnswer(Answer answer);
    public Answer updateAnswer(Answer answer);
    public Answer checkBestAnswer(long answerId);
    public void deleteAnswer(long answerId);
    Answer findVerifiedAnswer(long answerId);
}
