package com.team17.preProject.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.member.repository.MemberRepository;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.helper.email.EmailSender;
import com.team17.preProject.helper.password.TemporaryPassword;
import com.team17.preProject.helper.stub.MemberStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;

@SpringBootTest(classes = {MemberServiceImpl.class})
public class MemberServiceTest {

    @Autowired private MemberService memberService;
    @MockBean private MemberRepository repository;
    @MockBean private EmailSender emailSender;
    @MockBean private TemporaryPassword temporaryPassword;

    private static final Member MEMBER_STUB = MemberStub.ENTITY;

    @Test
    void findMemberTest() {
        Member expected = MEMBER_STUB;
        given(repository.findById(1L)).willReturn(Optional.of(expected));

        Member result = memberService.findMember(1L);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void findMemberTest_whenMemberNotFound() {
        given(repository.findById(1L)).willReturn(Optional.empty());

        Exception result = assertThrows(BusinessLogicException.class,
                () -> memberService.findMember(1L));
        assertThat(result.getMessage()).isEqualTo("Member not found");
    }

    @Test
    void createMemberTest() {
        Member expected = MEMBER_STUB;
        given(repository.findByEmail(any())).willReturn(null);
        given(repository.save(any())).willReturn(MEMBER_STUB);

        Member result = memberService.createMember(MEMBER_STUB);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void createMemberTest_whenEmailOverlapped() {
        given(repository.findByEmail(any())).willReturn(MEMBER_STUB);

        Exception result = assertThrows(BusinessLogicException.class,
                () -> memberService.createMember(MEMBER_STUB));
        assertThat(result.getMessage()).isEqualTo("Already exist email");
    }

    @Test
    void updateMemberTest() {
        // 리팩토링 후 빌더 패턴 적용 후, 작성
    }

    @Test
    void updateMemberTest_whenMemberNotFound() {
        given(repository.findById(MEMBER_STUB.getMemberId())).willReturn(Optional.empty());

        Exception result = assertThrows(BusinessLogicException.class,
                () -> memberService.updateMember(MEMBER_STUB));
        assertThat(result.getMessage()).isEqualTo("Member not found");
    }

    @Test
    void deleteMemberTest() {
        given(repository.findById(1L)).willReturn(Optional.of(MEMBER_STUB));

        assertDoesNotThrow(() -> memberService.deleteMember(1L));
    }

    @Test
    void deleteMemberTest_whenMemberNotFound() {
        long memberId = 1L;
        given(repository.findById(memberId)).willReturn(Optional.empty());

        Exception result = assertThrows(BusinessLogicException.class,
                () -> memberService.deleteMember(memberId));
        assertThat(result.getMessage()).isEqualTo("Member not found");
    }

    @Test
    void resetPasswordByEmailTest() {
        // 리팩토링 이후 추가 작성 예정
        String stubEmail = MEMBER_STUB.getEmail();

        given(repository.findByEmail(stubEmail)).willReturn(MEMBER_STUB);

        // assertDoesNotThrow(() -> memberService.resetPasswordByEmail(stubEmail));
    }

    @Test
    void resetPasswordByEmailTest_whenMemberNotFound() {
        String stubEmail = "emamil@email.com";
        given(repository.findByEmail(stubEmail)).willReturn(null);

        Exception result = assertThrows(BusinessLogicException.class,
                () -> memberService.resetPasswordByEmail(stubEmail));
        assertThat(result.getMessage()).isEqualTo("Member not found");
    }

}
