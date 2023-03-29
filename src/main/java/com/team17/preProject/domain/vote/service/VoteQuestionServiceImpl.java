package com.team17.preProject.domain.vote.service;

import com.team17.preProject.domain.vote.entity.VoteQuestion;
import com.team17.preProject.domain.vote.repository.VoteQuestionRepository;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.businessLogic.ExceptionCode;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.member.service.MemberService;
import com.team17.preProject.domain.question.entity.Question;
import com.team17.preProject.domain.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class VoteQuestionServiceImpl implements VoteQuestionService{

    private static final int GOOD = 1;
    private static final int BAD = -1;

    private final VoteQuestionRepository repository;
    private final MemberService memberService;
    private final QuestionService questionService;

    @Override
    public VoteQuestion voteGood(long memberId, long questionId) {
        Member member = memberService.findMember(memberId);
        Question question = questionService.findQuestion(questionId);

        validateNotQuestionOwner(member, question);
        validateNotExistVoteQuestion(member, question);

        return saveVoteQuestion(member, question, GOOD);
    }

    @Override
    public VoteQuestion voteBad(long memberId, long questionId) {
        Member member = memberService.findMember(memberId);
        Question question = questionService.findQuestion(questionId);

        validateNotQuestionOwner(member, question);
        validateNotExistVoteQuestion(member, question);

        return saveVoteQuestion(member, question, BAD);
    }

    private void validateNotQuestionOwner(Member member, Question question) {
        if (member.getMemberId() == question.getMember().getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.SAME_WRITER_VOTER);
        }
    }

    private void validateNotExistVoteQuestion(Member member, Question question) {
        repository.findByMemberAndQuestion(member, question)
                .ifPresent(voteQuestion -> throwException(voteQuestion));
    }

    private void throwException(VoteQuestion voteQuestion) {
        if (voteQuestion.getVote() == GOOD) {
            throw new BusinessLogicException(ExceptionCode.ALREADY_VOTE_GOOD);
        }
        if (voteQuestion.getVote() == BAD) {
            throw new BusinessLogicException(ExceptionCode.ALREADY_VOTE_BAD);
        }
        throw new BusinessLogicException(ExceptionCode.STRANGE_VOTE_DATA);
    }

    private VoteQuestion saveVoteQuestion(Member member, Question question, int vote){
        VoteQuestion voteQuestion = new VoteQuestion();
        voteQuestion.setMember(member);
        voteQuestion.setQuestion(question);
        voteQuestion.setVote(vote);

        // 추후 setter 변경 필요
        question.setVote(question.getVote() + vote);
        questionService.updateQuestion(question);

        return repository.save(voteQuestion);
    }

}
