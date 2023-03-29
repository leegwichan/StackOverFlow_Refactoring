package com.team17.preProject.domain.vote.service;

import com.team17.preProject.domain.answer.entity.Answer;
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

    private static final int GOOD = 1;
    private static final int BAD = -1;

    private final VoteAnswerRepository repository;
    private final MemberService memberService;
    private final AnswerService answerService;

    @Override
    public VoteAnswer voteGood(long memberId, long answerId) {
        Member member = memberService.findMember(memberId);
        Answer answer = answerService.findAnswer(answerId);

        validateNotAnswerOwner(member, answer);
        validateNotExistVoteAnswer(member, answer);

        return save(member, answer, GOOD);
    }

    @Override
    public VoteAnswer voteBad(long memberId, long answerId) {
        Member member = memberService.findMember(memberId);
        Answer answer = answerService.findAnswer(answerId);

        validateNotAnswerOwner(member, answer);
        validateNotExistVoteAnswer(member, answer);

        return save(member, answer, BAD);
    }

    private void validateNotAnswerOwner(Member member, Answer answer) {
        if (member.getMemberId() == answer.getMember().getMemberId()){
            throw new BusinessLogicException(ExceptionCode.SAME_WRITER_VOTER);
        }
    }

    private void validateNotExistVoteAnswer(Member member, Answer answer) {
        repository.findByMemberAndAnswer(member, answer)
                .ifPresent(voteAnswer -> throwException(voteAnswer));
    }

    private void throwException(VoteAnswer voteAnswer) {
        if (voteAnswer.getVote() == GOOD) {
            throw new BusinessLogicException(ExceptionCode.ALREADY_VOTE_GOOD);
        }
        if (voteAnswer.getVote() == BAD) {
            throw new BusinessLogicException(ExceptionCode.ALREADY_VOTE_BAD);
        }
        throw new BusinessLogicException(ExceptionCode.STRANGE_DATA);
    }

    private VoteAnswer save(Member member, Answer answer, int vote){
        VoteAnswer voteAnswer = VoteAnswer.builder()
                .member(member)
                .answer(answer)
                .vote(vote).build();

        answer.addVote(voteAnswer.getVote());
        answerService.updateAnswer(answer);

        return repository.save(voteAnswer);
    }
}
