package com.team17.preProject.follow.service;

import com.team17.preProject.answer.entity.Answer;
import com.team17.preProject.answer.service.AnswerService;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.businessLogic.ExceptionCode;
import com.team17.preProject.follow.entity.FollowAnswer;
import com.team17.preProject.follow.repository.FollowAnswerRepository;
import com.team17.preProject.follow.repository.FollowQuestionRepository;
import com.team17.preProject.member.entity.Member;
import com.team17.preProject.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class FollowAnswerServiceImpl implements FollowAnswerService{

    private final FollowAnswerRepository repository;
    private final MemberService memberService;
    private final AnswerService answerService;

    @Override
    public Page<FollowAnswer> findFollowAnswerByMember(int page, int size, long memberId) {

        Member findMember = memberService.findVerifiedMember(memberId);
        return repository.findByMember(findMember,
                PageRequest.of(page, size, Sort.by("faId").descending()));
    }

    @Override
    public FollowAnswer createFollowAnswer(long memberId, long answerId) {
        Member findMember = memberService.findVerifiedMember(memberId);
        Answer findAnswer = answerService.findVerifiedAnswer(answerId);

        findFollowAnswerExpectNull(findMember, findAnswer);

        FollowAnswer followAnswer = new FollowAnswer();
        followAnswer.setMember(findMember);
        followAnswer.setAnswer(findAnswer);

        return repository.save(followAnswer);
    }

    @Override
    public void deleteFollowAnswer(long memberId, long answerId) {
        Member findMember = memberService.findVerifiedMember(memberId);
        Answer findAnswer = answerService.findVerifiedAnswer(answerId);
        FollowAnswer followAnswer = findFollowExceptionExceptPresent(findMember, findAnswer);
        repository.delete(followAnswer);
    }

    private void findFollowAnswerExpectNull(Member member, Answer answer){
        Optional<FollowAnswer> optional = repository.findByMemberAndAnswer(member, answer);
        if (optional.isPresent()){
            throw new BusinessLogicException(ExceptionCode.ALREADY_FOLLOW_POST);
        };
    }

    private FollowAnswer findFollowExceptionExceptPresent(Member member, Answer answer){
        Optional<FollowAnswer> optional = repository.findByMemberAndAnswer(member, answer);
        if (optional.isEmpty()){
            throw new BusinessLogicException(ExceptionCode.NOT_FOLLOW_POST);
        }

        return optional.get();
    }
}
