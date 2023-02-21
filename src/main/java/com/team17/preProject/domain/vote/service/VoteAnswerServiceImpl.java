package com.team17.preProject.domain.vote.service;

import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.answer.repository.AnswerRepository;
import com.team17.preProject.domain.answer.service.AnswerService;
import com.team17.preProject.domain.vote.entity.VoteAnswer;
import com.team17.preProject.domain.vote.repository.VoteAnswerRepository;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.businessLogic.ExceptionCode;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class VoteAnswerServiceImpl implements VoteAnswerService{

    private final VoteAnswerRepository repository;
    private final AnswerRepository answerRepository;
    private final MemberService memberService;
    private final AnswerService answerService;




    @Override
    public VoteAnswer voteGood(long memberId, long answerId) {
        Member member = memberService.findMember(memberId);
        Answer answer = answerService.findVerifiedAnswer(answerId);
        VoteAnswer voteAnswer = repository.findByMemberAndAnswer(member, answer);

        if (member.getMemberId() == answer.getMember().getMemberId()){
            throw new BusinessLogicException(ExceptionCode.SAME_WRITER_VOTER);
        }

        if (voteAnswer == null){
            return saveVoteAnswer(member, answer, 1);
        } else if (voteAnswer.getVote() == 1){
            throw new BusinessLogicException(ExceptionCode.ALREADY_VOTE_GOOD);
        } else if (voteAnswer.getVote() == -1){
            throw new BusinessLogicException(ExceptionCode.ALREADY_VOTE_BAD);
        }

        return null;
    }

    @Override
    public VoteAnswer voteBad(long memberId, long answerId) {
        Member member = memberService.findMember(memberId);
        Answer answer = answerService.findVerifiedAnswer(answerId);
        VoteAnswer voteAnswer = repository.findByMemberAndAnswer(member, answer);

        if (member.getMemberId() == answer.getMember().getMemberId()){
            throw new BusinessLogicException(ExceptionCode.SAME_WRITER_VOTER);
        }

        if (voteAnswer == null){
            return saveVoteAnswer(member, answer, -1);
        } else if (voteAnswer.getVote() == 1){
            throw new BusinessLogicException(ExceptionCode.ALREADY_VOTE_GOOD);
        } else if (voteAnswer.getVote() == -1){
            throw new BusinessLogicException(ExceptionCode.ALREADY_VOTE_BAD);
        }

        return null;
    }

    private VoteAnswer saveVoteAnswer(Member member, Answer answer, int vote){
        VoteAnswer voteAnswer = new VoteAnswer();
        voteAnswer.setMember(member);
        voteAnswer.setAnswer(answer);
        voteAnswer.setVote(vote);

        answer.setVote(answer.getVote() + vote);
        answerRepository.save(answer);

        return repository.save(voteAnswer);
    }
}
