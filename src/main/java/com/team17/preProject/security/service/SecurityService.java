package com.team17.preProject.security.service;

import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.answer.service.AnswerService;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.businessLogic.ExceptionCode;
import com.team17.preProject.domain.question.entity.Question;
import com.team17.preProject.domain.question.service.QuestionService;
import com.team17.preProject.security.auth.PrincipalDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityService {

    private final QuestionService questionService;
    private final AnswerService answerService;

    public void checkMemberEqual(Authentication authentication, long memberId){

        checkMemberIdEqual(returnMemberId(authentication), memberId);
    }

    public void checkQuestionWriter(Authentication authentication, long questionId){

        Question findQuestion = questionService.findVerifiedQuestion(questionId);
        long writerMemberId = findQuestion.getMember().getMemberId();

        checkMemberIdEqual(returnMemberId(authentication), writerMemberId);
    }

    public void checkAnswerWriter(Authentication authentication, long answerId){
        Answer findAnswer = answerService.findVerifiedAnswer(answerId);
        long writerMemberId = findAnswer.getMember().getMemberId();

        checkMemberIdEqual(returnMemberId(authentication), writerMemberId);
    }

    public void checkQuestionWriterByAnswerId(Authentication authentication, long answerId){
        Answer findAnswer = answerService.findVerifiedAnswer(answerId);
        long questionWriterMemberId = findAnswer.getQuestion().getMember().getMemberId();

        checkMemberIdEqual(returnMemberId(authentication), questionWriterMemberId);
    }

    private long returnMemberId(Authentication authentication){
        if (authentication == null) throw new BusinessLogicException(ExceptionCode.TRY_LOGIN_AGAIN);
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        return userDetails.getMember().getMemberId();
    }

    private void checkMemberIdEqual(long loginMemberId, long requestInfoMemberId){
        if (loginMemberId != requestInfoMemberId){
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_MATCHED);
        }
    }
}
