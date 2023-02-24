package com.team17.preProject.domain.vote.service;

import com.team17.preProject.domain.vote.entity.VoteQuestion;
import com.team17.preProject.domain.vote.repository.VoteQuestionRepository;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.businessLogic.ExceptionCode;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.member.service.MemberService;
import com.team17.preProject.domain.question.entity.Question;
import com.team17.preProject.domain.question.repository.QuestionRepository;
import com.team17.preProject.domain.question.service.QuestionService;
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
        Member member = memberService.findMember(memberId);
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
        Member member = memberService.findMember(memberId);
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

        // 추후 setter 변경 필요
        question.setVote(question.getVote() + vote);
        questionRepository.save(question);

        return repository.save(voteQuestion);
    }

}
