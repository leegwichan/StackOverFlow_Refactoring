package com.team17.preProject.domain.follow.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.team17.preProject.domain.follow.entity.FollowQuestion;
import com.team17.preProject.domain.follow.repository.FollowQuestionRepository;
import com.team17.preProject.domain.member.service.MemberService;
import com.team17.preProject.domain.question.service.QuestionService;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.helper.stub.FollowQuestionStub;
import com.team17.preProject.helper.stub.MemberStub;
import com.team17.preProject.helper.stub.QuestionStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {FollowQuestionServiceImpl.class})
public class FollowQuestionServiceTest {

    @Autowired private FollowQuestionService followQuestionService;
    @MockBean private FollowQuestionRepository repository;
    @MockBean private MemberService memberService;
    @MockBean private QuestionService questionService;

    @Test
    void findFollowQuestionTest() {
        given(memberService.findMember(anyLong())).willReturn(MemberStub.ENTITY);
        List<FollowQuestion> content = List.of(FollowQuestionStub.getEntity());
        given(repository.findByMember(any(), any())).willReturn(new PageImpl<>(content));

        Page<FollowQuestion> result =
                followQuestionService.findFollowQuestionsByMember(1,1,MemberStub.ENTITY.getMemberId());

        assertThat(result.getContent()).isEqualTo(content);
    }

    @Test
    void createFollowQuestionTest() {
        given(memberService.findMember(anyLong())).willReturn(MemberStub.getChangeableEntity());
        given(questionService.findQuestion(anyLong())).willReturn(QuestionStub.getChangeableEntity());
        given(repository.findByMemberAndQuestion(any(), any())).willReturn(Optional.empty());
        given(repository.save(any())).willReturn(FollowQuestionStub.getEntity(3L));

        FollowQuestion result = followQuestionService.createFollowQuestion(3L, 3L);

        assertThat(result).usingRecursiveComparison().isEqualTo(FollowQuestionStub.getEntity(3L));
    }

    @Test
    void createFollowQuestionTest_whenAlreadyExist() {
        given(memberService.findMember(anyLong())).willReturn(MemberStub.getChangeableEntity());
        given(questionService.findQuestion(anyLong())).willReturn(QuestionStub.getChangeableEntity());
        given(repository.findByMemberAndQuestion(any(), any()))
                .willReturn(Optional.of(FollowQuestionStub.getEntity(3L)));

        Exception result = assertThrows(BusinessLogicException.class,
                () -> followQuestionService.createFollowQuestion(3L, 3L));

        assertThat(result.getMessage()).isEqualTo("Already follow post");
    }
}