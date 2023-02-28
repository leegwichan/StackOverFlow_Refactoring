package com.team17.preProject.domain.question.service;

import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.member.service.MemberService;
import com.team17.preProject.domain.question.entity.Question;
import com.team17.preProject.domain.question.repository.QuestionRepository;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.businessLogic.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private static final Sort DESCENDING_QUESTION_ID = Sort.by("questionId").descending();

    private final QuestionRepository questionRepository;
    private final MemberService memberService;

    @Override
    public Question inquireQuestion(long questionId) {
        Question question = findQuestion(questionId);
        question.increaseView();
        return questionRepository.save(question);
    }

    @Override
    public Question findQuestion(long questionId){
        return questionRepository.findById(questionId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }

    @Override
    public Page<Question> findQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size, DESCENDING_QUESTION_ID));
    }

    @Override
    public Page<Question> findQuestions(int page, int size, String searchWord) {
        return questionRepository.findByTitleContaining(searchWord,
                PageRequest.of(page, size, DESCENDING_QUESTION_ID));
    }

    @Override
    public Page<Question> findQuestions(int page, int size, long memberId) {
        Member member = memberService.findMember(memberId);
        return questionRepository.findByMember(member,
                PageRequest.of(page, size, DESCENDING_QUESTION_ID));
    }

    @Override
    public Question createQuestion(Question question) {
        Member findMember = memberService.findMember(question.getMember().getMemberId());
        question.setMember(findMember);
        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Question question) {
        Question findQuestion = findQuestion(question.getQuestionId());
        findQuestion.update(question);
        return questionRepository.save(findQuestion);
    }

    @Override
    public void deleteQuestion(long questionId) {
        Question findQuestion = findQuestion(questionId);
        questionRepository.delete(findQuestion);
    }
}
