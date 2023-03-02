package com.team17.preProject.domain.answer.service;

import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.answer.repository.AnswerRepository;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.businessLogic.ExceptionCode;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.member.service.MemberService;
import com.team17.preProject.domain.question.entity.Question;
import com.team17.preProject.domain.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final MemberService memberService;

    @Override
    public List<Answer> findAnswersByQuestion(long questionId) {
        Question findQuestion = questionService.findQuestion(questionId);
        return answerRepository.findByQuestion(findQuestion);
    }

    @Override
    public Page<Answer> findAnswersByMemberId(int page, int size, long memberId) {
        Member findMember = memberService.findMember(memberId);
        return answerRepository.findByMember(findMember,
                PageRequest.of(page, size, Sort.by("answerId").descending()));
    }

    @Override
    public Answer createAnswer(Answer answer) {
        Member findMember = memberService.findMember(answer.getMember().getMemberId());
        answer.setMember(findMember);
        Question findQuestion = questionService.findQuestion(answer.getQuestion().getQuestionId());
        answer.setQuestion(findQuestion);

        if (findMember.getMemberId() == findQuestion.getMember().getMemberId()){
            throw new BusinessLogicException(ExceptionCode.SAME_QUESTION_ANSWER_MEMBER);
        }
        return answerRepository.save(answer);
    }

    @Override
    public Answer updateAnswer(Answer answer) {
        Answer findAnswer = findVerifiedAnswer(answer.getAnswerId());
        findAnswer.update(answer);
        return answerRepository.save(findAnswer);
    }

    @Override
    public void deleteAnswer(long answerId) {
        Answer findAnswer = findVerifiedAnswer(answerId);
        answerRepository.delete(findAnswer);
    }

    public Answer findVerifiedAnswer(long answerId){
        return answerRepository.findById(answerId).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
    }
}
