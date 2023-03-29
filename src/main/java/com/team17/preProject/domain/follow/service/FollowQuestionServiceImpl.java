package com.team17.preProject.domain.follow.service;

import com.team17.preProject.domain.follow.entity.FollowQuestion;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.businessLogic.ExceptionCode;
import com.team17.preProject.domain.follow.repository.FollowQuestionRepository;
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

@Transactional
@Service
@RequiredArgsConstructor
public class FollowQuestionServiceImpl implements FollowQuestionService {

    private final FollowQuestionRepository repository;
    private final MemberService memberService;
    private final QuestionService questionService;

    @Override
    public Page<FollowQuestion> findFollowQuestionsByMember(int page, int size, long memberId) {
        Member findMember = memberService.findMember(memberId);
        return repository.findByMember(findMember,
                PageRequest.of(page, size, Sort.by("fqId").descending()));
    }

    @Override
    public FollowQuestion createFollowQuestion(long memberId, long questionId) {
        Member findMember = memberService.findMember(memberId);
        Question findQuestion = questionService.findQuestion(questionId);
        validateNotExistFollowQuestion(findMember, findQuestion);

        FollowQuestion followQuestion = FollowQuestion.builder()
                .member(findMember)
                .question(findQuestion).build();
        return repository.save(followQuestion);
    }

    @Override
    public void deleteFollowQuestion(long memberId, long questionId) {
        Member findMember = memberService.findMember(memberId);
        Question findQuestion = questionService.findQuestion(questionId);

        FollowQuestion followQuestion = validateExistFollowQuestion(findMember, findQuestion);
        repository.delete(followQuestion);
    }

    private void validateNotExistFollowQuestion(Member member, Question question){
        repository.findByMemberAndQuestion(member, question)
                .ifPresent(fq -> {throw new BusinessLogicException(ExceptionCode.ALREADY_FOLLOW_POST);});
    }

    private FollowQuestion validateExistFollowQuestion(Member member, Question question){
        return repository.findByMemberAndQuestion(member, question)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOLLOW_POST));
    }
}
