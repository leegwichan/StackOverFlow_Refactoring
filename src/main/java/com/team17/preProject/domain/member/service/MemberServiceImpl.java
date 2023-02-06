package com.team17.preProject.domain.member.service;

import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.businessLogic.ExceptionCode;
import com.team17.preProject.helper.email.EmailSender;
import com.team17.preProject.helper.password.NewPassword;
import com.team17.preProject.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final EmailSender emailSender;

    private final MemberRepository memberRepository;
    private final NewPassword newPassword;

    @Override
    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    @Override
    public void resetPasswordByEmail(String email) {

        Member findMember = findMemberByEmailExpectExist(email);

        String encodePassword;
        try {

            String title = "TEAM17 StackOverflow 클론 비밀번호 변경되었습니다";
            String[] passwordArray = newPassword.create();
            String password = passwordArray[0];
            encodePassword = passwordArray[1];
            String content = "당신의 비밀번호는 " + password + "로 변경되었습니다. \n 페이지로 가서 비밀번호를 바꿔주세요.";

            emailSender.setEmailSenderSendOne(findMember.getEmail(), title, content);
        } catch (Exception e){
            throw new BusinessLogicException(ExceptionCode.FAIL_SEND_EMAIL);
        }

        findMember.setPassword(encodePassword);
        memberRepository.save(findMember);
    }

    @Override
    public Member createMember(Member member) {

        findMemberByEmailExpectNull(member.getEmail());
        return memberRepository.save(member);
    }

    @Override
    public Member updateMember(Member member) {
        Member findMember = findVerifiedMember(member.getMemberId());

        Optional.ofNullable(member.getImage())
                .ifPresent(image -> findMember.setImage(image));
        Optional.ofNullable(member.getDisplayName())
                .ifPresent(displayName -> findMember.setDisplayName(displayName));
        Optional.ofNullable(member.getLocation())
                .ifPresent(location -> findMember.setLocation(location));
        Optional.ofNullable(member.getMemberTitle())
                .ifPresent(title -> findMember.setMemberTitle(title));
        Optional.ofNullable(member.getAboutMe())
                .ifPresent(aboutMe -> findMember.setAboutMe(aboutMe));

        return memberRepository.save(findMember);
    }

    @Override
    public void deleteMember(long memberId) {
        Member findMember = findVerifiedMember(memberId);
        memberRepository.delete(findMember);
    }

    public Member findVerifiedMember(long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findMember;
    }

    @Override
    public void checkEmailValidate(String email) {
        findMemberByEmailExpectNull(email);
    }

    private void findMemberByEmailExpectNull(String email){

        Member findMember = memberRepository.findByEmail(email);
        if (findMember != null){
            throw new BusinessLogicException(ExceptionCode.ALREADY_EXIST_EMAIL);
        }
    }

    private Member findMemberByEmailExpectExist(String email){
        Member findMember = memberRepository.findByEmail(email);
        if (findMember == null){
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
        return findMember;
    }
}
