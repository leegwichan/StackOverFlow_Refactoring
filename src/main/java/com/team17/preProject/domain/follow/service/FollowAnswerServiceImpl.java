package com.team17.preProject.domain.follow.service;

import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.answer.service.AnswerService;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.businessLogic.ExceptionCode;
import com.team17.preProject.domain.follow.entity.FollowAnswer;
import com.team17.preProject.domain.follow.repository.FollowAnswerRepository;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.member.service.MemberService;
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
public class FollowAnswerServiceImpl implements FollowAnswerService {

    private final FollowAnswerRepository repository;
    private final MemberService memberService;
    private final AnswerService answerService;

    @Override
    public Page<FollowAnswer> findFollowAnswerByMember(int page, int size, long memberId) {

        Member findMember = memberService.findMember(memberId);
        return repository.findByMember(findMember,
                PageRequest.of(page, size, Sort.by("faId").descending()));
    }

    @Override
    public FollowAnswer createFollowAnswer(long memberId, long answerId) {
        Member findMember = memberService.findMember(memberId);
        Answer findAnswer = answerService.findAnswer(answerId);

        validateNotExistFollowAnswer(findMember, findAnswer);

        FollowAnswer followAnswer = new FollowAnswer();
        followAnswer.setMember(findMember);
        followAnswer.setAnswer(findAnswer);

        return repository.save(followAnswer);
    }

    @Override
    public void deleteFollowAnswer(long memberId, long answerId) {
        Member findMember = memberService.findMember(memberId);
        Answer findAnswer = answerService.findAnswer(answerId);
        FollowAnswer followAnswer = validateExistFollowAnswer(findMember, findAnswer);
        repository.delete(followAnswer);
    }

    private void validateNotExistFollowAnswer(Member member, Answer answer){
        repository.findByMemberAndAnswer(member, answer)
                .ifPresent(fa -> {throw new BusinessLogicException(ExceptionCode.ALREADY_FOLLOW_POST);});
    }

    private FollowAnswer validateExistFollowAnswer(Member member, Answer answer){
        return repository.findByMemberAndAnswer(member, answer)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOLLOW_POST));
    }
}
