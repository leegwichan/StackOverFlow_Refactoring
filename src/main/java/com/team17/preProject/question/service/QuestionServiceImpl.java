package com.team17.preProject.question.service;

import com.team17.preProject.answer.entity.Answer;
import com.team17.preProject.answer.service.AnswerService;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.businessLogic.ExceptionCode;
import com.team17.preProject.member.entity.Member;
import com.team17.preProject.member.repository.MemberRepository;
import com.team17.preProject.member.service.MemberService;
import com.team17.preProject.question.entity.Question;
import com.team17.preProject.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.Optional;
@Transactional
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService{

    private final QuestionRepository questionRepository;
    private final MemberService memberService;

    @Override
    public Question findQuestion(long questionId) {
        Question question = findVerifiedQuestion(questionId);
        question.setView(question.getView() +1);
        questionRepository.save(question);
        return question;
    }

    @Override
    public Page<Question> findQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size,
                Sort.by("questionId").descending()));
    }

    @Override
    public Page<Question> findQuestionsByKeyword(int page, int size, String keyword) {
        return questionRepository.findByTitleContaining(keyword,
                PageRequest.of(page, size, Sort.by("questionId").descending()));
    }

    @Override
    public Page<Question> findQuestionsByMemberID(int page, int size, long memberId) {
        Member member = memberService.findVerifiedMember(memberId);
        return questionRepository.findByMember(member,
                PageRequest.of(page, size, Sort.by("questionId").descending()));
    }

    @Override
    public Question createQuestion(Question question) {
        Member findMember = memberService.findMember(question.getMember().getMemberId());
        question.setMember(findMember);
        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Question updateQuestion) {
        Question findQuestion = findVerifiedQuestion(updateQuestion.getQuestionId());

        Optional.ofNullable(updateQuestion.getTitle())
                .ifPresent(title -> findQuestion.setTitle(title));
        Optional.ofNullable(updateQuestion.getContent())
                .ifPresent(content -> findQuestion.setContent(content));

        return questionRepository.save(findQuestion);
    }

    @Override
    public Question updateQuestionDirectly(Question updateQuestion) {
        return questionRepository.save(updateQuestion);
    }


    @Override
    public void deleteQuestion(long questionId) {
        Question findQuestion = findVerifiedQuestion(questionId);
        questionRepository.delete(findQuestion);
    }

    public Question findVerifiedQuestion(long questionId){
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question findQuestion = optionalQuestion.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        return findQuestion;
    }
}
