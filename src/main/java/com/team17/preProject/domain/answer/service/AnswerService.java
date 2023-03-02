package com.team17.preProject.domain.answer.service;

import com.team17.preProject.domain.answer.entity.Answer;
import org.springframework.data.domain.Page;
import java.util.List;

public interface AnswerService {
    Answer findAnswer(long answerId);
    List<Answer> findAnswersByQuestion(long questionId);
    Page<Answer> findAnswersByMemberId(int page, int size, long memberId);
    Answer createAnswer(Answer answer);
    Answer updateAnswer(Answer answer);
    void deleteAnswer(long answerId);
}
