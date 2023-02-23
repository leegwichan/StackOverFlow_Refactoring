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
import java.util.Optional;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService{

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final MemberService memberService;

    @Override
    public List<Answer> findAnswersByQuestion(long questionId) {

        Question findQuestion = questionService.findVerifiedQuestion(questionId);
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
        Question findQuestion = questionService.findVerifiedQuestion(answer.getQuestion().getQuestionId());
        answer.setQuestion(findQuestion);

        if (findMember.getMemberId() == findQuestion.getMember().getMemberId()){
            throw new BusinessLogicException(ExceptionCode.SAME_QUESTION_ANSWER_MEMBER);
        }

        return answerRepository.save(answer);
    }

    @Override
    public Answer updateAnswer(Answer answer) {
        Answer findAnswer = findVerifiedAnswer(answer.getAnswerId());

        Optional.ofNullable(answer.getContent())
                .ifPresent(content -> findAnswer.setContent(content));

        return answerRepository.save(findAnswer);
    }

    @Override
    public Answer checkBestAnswer(long answerId) {
        Answer findAnswer = findVerifiedAnswer(answerId);
        Question question = findAnswer.getQuestion();
        if (question == null) throw new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND);

        checkAlreadyExistBestAnswer(findAnswer.getQuestion());
        checkVerifiedBestAnswerByQuestion(findAnswer, question.getQuestionId());

        question.setBestAnswer(findAnswer);
        questionService.updateQuestionDirectly(question);
        return findAnswer;
    }

    @Override
    public void deleteAnswer(long answerId) {
        Answer findAnswer = findVerifiedAnswer(answerId);
        answerRepository.delete(findAnswer);
    }

    public Answer findVerifiedAnswer(long answerId){
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        Answer findAnswer = optionalAnswer.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
        return findAnswer;
    }

    private void checkVerifiedBestAnswerByQuestion(Answer answer, long questionId){
        if (answer.getQuestion().getQuestionId() != questionId){
            throw new BusinessLogicException(ExceptionCode.NOT_VERIFIED_ANSWER_OF_QUESTION);
        }
    }

    private void checkAlreadyExistBestAnswer(Question question){
        if (question.getBestAnswer() != null){
            throw new BusinessLogicException(ExceptionCode.ALREADY_EXIST_BEST_ANSWER);
        }
    }
}
