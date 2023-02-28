package com.team17.preProject.domain.question.service;

import com.team17.preProject.domain.question.entity.Question;
import org.springframework.data.domain.Page;

public interface QuestionService {

    Question inquireQuestion(long questionId);
    Question findQuestion(long questionId);
    Page<Question> findQuestions(int page, int size);
    Page<Question> findQuestions(int page, int size, String searchWord);
    Page<Question> findQuestions(int page, int size, long memberId);
    Question createQuestion(Question question);
    Question updateQuestion(Question question);
    void deleteQuestion(long questionId);
}
