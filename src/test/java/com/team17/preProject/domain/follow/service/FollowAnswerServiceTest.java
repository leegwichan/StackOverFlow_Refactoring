package com.team17.preProject.domain.follow.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.team17.preProject.domain.answer.service.AnswerService;
import com.team17.preProject.domain.follow.entity.FollowAnswer;
import com.team17.preProject.domain.follow.repository.FollowAnswerRepository;
import com.team17.preProject.domain.member.service.MemberService;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.helper.stub.AnswerStub;
import com.team17.preProject.helper.stub.FollowAnswerStub;
import com.team17.preProject.helper.stub.MemberStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {FollowAnswerServiceImpl.class})
class FollowAnswerServiceTest {

    @Autowired private FollowAnswerService followAnswerService;
    @MockBean private FollowAnswerRepository repository;
    @MockBean private MemberService memberService;
    @MockBean private AnswerService answerService;

    @Test
    void findFollowAnswerTest() {
        given(memberService.findMember(anyLong())).willReturn(MemberStub.getChangeableEntity());
        List<FollowAnswer> content = List.of(FollowAnswerStub.getEntity(3L), FollowAnswerStub.getEntity(4L));
        given(repository.findByMember(any(), any())).willReturn(new PageImpl<>(content));

        Page<FollowAnswer> result = followAnswerService.findFollowAnswerByMember(1,2,3L);

        assertThat(result.getContent()).usingRecursiveComparison().isEqualTo(content);
    }

    @Test
    void createFollowAnswerTest() {
        given(memberService.findMember(anyLong())).willReturn(MemberStub.getChangeableEntity());
        given(answerService.findAnswer(anyLong())).willReturn(AnswerStub.getEntity());
        given(repository.findByMemberAndAnswer(any(), any())).willReturn(Optional.empty());
        given(repository.save(any())).willReturn(FollowAnswerStub.getEntity(3L));

        FollowAnswer result = followAnswerService.createFollowAnswer(3L, 3L);

        assertThat(result).usingRecursiveComparison().isEqualTo(FollowAnswerStub.getEntity(3L));
    }

    @Test
    void createFollowAnswerTest_whenAlreadyExist() {
        given(memberService.findMember(anyLong())).willReturn(MemberStub.getChangeableEntity());
        given(answerService.findAnswer(anyLong())).willReturn(AnswerStub.getEntity());
        given(repository.findByMemberAndAnswer(any(), any()))
                .willReturn(Optional.of(FollowAnswerStub.getEntity(3L)));

        Exception result = assertThrows(BusinessLogicException.class,
                () -> followAnswerService.createFollowAnswer(3L, 3L));

        assertThat(result.getMessage()).isEqualTo("Already follow post");
    }

    @Test
    void deleteFollowAnswerTest() {
        given(memberService.findMember(anyLong())).willReturn(MemberStub.getChangeableEntity());
        given(answerService.findAnswer(anyLong())).willReturn(AnswerStub.getEntity());
        given(repository.findByMemberAndAnswer(any(), any()))
                .willReturn(Optional.of(FollowAnswerStub.getEntity(3L)));

        assertDoesNotThrow(() -> followAnswerService.deleteFollowAnswer(3L, 3L));
    }

    @Test
    void deleteFollowAnswerTest_whenNotExist() {
        given(memberService.findMember(anyLong())).willReturn(MemberStub.getChangeableEntity());
        given(answerService.findAnswer(anyLong())).willReturn(AnswerStub.getEntity());
        given(repository.findByMemberAndAnswer(any(), any())).willReturn(Optional.empty());

        Exception result = assertThrows(BusinessLogicException.class,
                () -> followAnswerService.deleteFollowAnswer(3L, 3L));

        assertThat(result.getMessage()).isEqualTo("Not follow post");
    }
}