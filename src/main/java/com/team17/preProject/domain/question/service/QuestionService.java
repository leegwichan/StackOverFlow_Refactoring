package com.team17.preProject.domain.question.service;

import com.team17.preProject.domain.question.entity.Question;
import org.springframework.data.domain.Page;

public interface QuestionService {

    public Question findQuestion(long questionId);
    public Page<Question> findQuestions(int page, int size);
    public Page<Question> findQuestions(int page, int size, String keyword);
    public Page<Question> findQuestions(int page, int size, long memberId);
    public Question createQuestion(Question question);
    public Question updateQuestion(Question updateQuestion);
    public Question updateQuestionDirectly(Question updateQuestion);
    public void deleteQuestion(long questionId);
    public Question findVerifiedQuestion(long questionId);

}
