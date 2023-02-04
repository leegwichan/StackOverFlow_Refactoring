package com.team17.preProject.vote.service;

import com.team17.preProject.answer.service.AnswerService;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.businessLogic.ExceptionCode;
import com.team17.preProject.member.entity.Member;
import com.team17.preProject.member.service.MemberService;
import com.team17.preProject.question.entity.Question;
import com.team17.preProject.question.repository.QuestionRepository;
import com.team17.preProject.question.service.QuestionService;
import com.team17.preProject.vote.dto.VoteQuestionDto;
import com.team17.preProject.vote.entity.VoteQuestion;
import com.team17.preProject.vote.repository.VoteQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class VoteQuestionServiceImpl implements VoteQuestionService{

    private final VoteQuestionRepository repository;
    private final QuestionRepository questionRepository;
    private final MemberService memberService;
    private final QuestionService questionService;

    @Override
    public VoteQuestion voteGood(long memberId, long questionId) {
        Member member = memberService.findVerifiedMember(memberId);
        Question question = questionService.findVerifiedQuestion(questionId);
        VoteQuestion voteQuestion = repository.findByMemberAndQuestion(member,question);

        if (question.getMember().getMemberId() == member.getMemberId()){
            throw new BusinessLogicException(ExceptionCode.SAME_WRITER_VOTER);
        }

        if (voteQuestion == null){
            return saveVoteQuestion(member, question, 1);
        } else if (voteQuestion.getVote() == 1){
            throw new BusinessLogicException(ExceptionCode.ALREADY_VOTE_GOOD);
        } else if (voteQuestion.getVote() == -1){
            throw new BusinessLogicException(ExceptionCode.ALREADY_VOTE_BAD);
        }

        return null;
    }

    @Override
    public VoteQuestion voteBad(long memberId, long questionId) {
        Member member = memberService.findVerifiedMember(memberId);
        Question question = questionService.findVerifiedQuestion(questionId);
        VoteQuestion voteQuestion = repository.findByMemberAndQuestion(member,question);

        if (question.getMember().getMemberId() == member.getMemberId()){
            throw new BusinessLogicException(ExceptionCode.SAME_WRITER_VOTER);
        }

        if (voteQuestion == null){
            return saveVoteQuestion(member, question, -1);
        } else if (voteQuestion.getVote() == 1){
            throw new BusinessLogicException(ExceptionCode.ALREADY_VOTE_GOOD);
        } else if (voteQuestion.getVote() == -1){
            throw new BusinessLogicException(ExceptionCode.ALREADY_VOTE_BAD);
        }

        return null;
    }

    private VoteQuestion saveVoteQuestion(Member member, Question question, int vote){
        VoteQuestion voteQuestion = new VoteQuestion();
        voteQuestion.setMember(member);
        voteQuestion.setQuestion(question);
        voteQuestion.setVote(vote);

        question.setVote(question.getVote() + vote);
        questionRepository.save(question);

        return repository.save(voteQuestion);
    }

}
