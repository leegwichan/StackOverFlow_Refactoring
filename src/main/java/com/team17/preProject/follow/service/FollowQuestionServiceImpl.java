package com.team17.preProject.follow.service;

import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.businessLogic.ExceptionCode;
import com.team17.preProject.follow.entity.FollowQuestion;
import com.team17.preProject.follow.repository.FollowQuestionRepository;
import com.team17.preProject.member.entity.Member;
import com.team17.preProject.member.service.MemberService;
import com.team17.preProject.question.entity.Question;
import com.team17.preProject.question.service.QuestionService;
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
public class FollowQuestionServiceImpl implements FollowQuestionService{

    private final FollowQuestionRepository repository;
    private final MemberService memberService;
    private final QuestionService questionService;

    @Override
    public Page<FollowQuestion> findFollowQuestionsByMember(int page, int size, long memberId) {
        Member findMember = memberService.findVerifiedMember(memberId);
        return repository.findByMember(findMember,
                PageRequest.of(page, size, Sort.by("fqId").descending()));
    }

    @Override
    public FollowQuestion createFollowQuestion(long memberId, long questionId) {
        Member findMember = memberService.findVerifiedMember(memberId);
        Question findQuestion = questionService.findVerifiedQuestion(questionId);

        findFollowQuestionExpectNull(findMember, findQuestion);

        FollowQuestion followQuestion = new FollowQuestion();
        followQuestion.setMember(findMember);
        followQuestion.setQuestion(findQuestion);

        return repository.save(followQuestion);
    }

    @Override
    public void deleteFollowQuestion(long memberId, long questionId) {
        Member findMember = memberService.findVerifiedMember(memberId);
        Question findQuestion = questionService.findVerifiedQuestion(questionId);

        FollowQuestion followQuestion = findFollowQuestionExpectPresent(findMember, findQuestion);
        repository.delete(followQuestion);
    }

    private void findFollowQuestionExpectNull(Member member, Question question){
        Optional<FollowQuestion> optional = repository.findByMemberAndQuestion(member, question);
        if(optional.isPresent()){
            throw new BusinessLogicException(ExceptionCode.ALREADY_FOLLOW_POST);
        }
    }

    private FollowQuestion findFollowQuestionExpectPresent(Member member, Question question){
        Optional<FollowQuestion> optional = repository.findByMemberAndQuestion(member, question);
        if(optional.isEmpty()){
            throw new BusinessLogicException(ExceptionCode.NOT_FOLLOW_POST);
        }

        return optional.get();
    }
}
