package com.team17.preProject.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.member.repository.MemberRepository;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.helper.email.password.TemporaryPasswordSender;
import com.team17.preProject.helper.password.PasswordDto;
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
    @MockBean private TemporaryPassword temporaryPassword;
    @MockBean private TemporaryPasswordSender temporaryPasswordSender;

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
        Member findMember = MemberStub.getChangeableEntity();
        Member updateInfo = Member.builder()
                .memberId(findMember.getMemberId())
                .aboutMe("update aboutMe")
                .displayName("update display name").build();
        Member expected = Member.builder()
                .memberId(findMember.getMemberId())
                .email(findMember.getEmail())
                .image(findMember.getImage())
                .location(findMember.getLocation())
                .memberTitle(findMember.getMemberTitle())
                .displayName(updateInfo.getDisplayName())
                .aboutMe(updateInfo.getAboutMe()).build();
        given(repository.findById(updateInfo.getMemberId())).willReturn(Optional.of(findMember));
        given(repository.save(findMember)).willReturn(findMember);

        Member result = memberService.updateMember(updateInfo);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
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
        given(temporaryPassword.create()).willReturn(new PasswordDto("aaa", "bbb"));

        assertDoesNotThrow(() -> memberService.resetPasswordByEmail(stubEmail));
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
