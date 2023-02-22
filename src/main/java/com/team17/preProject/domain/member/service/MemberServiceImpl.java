package com.team17.preProject.domain.member.service;

import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.businessLogic.ExceptionCode;
import com.team17.preProject.helper.email.EmailSender;
import com.team17.preProject.helper.email.password.TemporaryPasswordSender;
import com.team17.preProject.helper.password.PasswordDto;
import com.team17.preProject.helper.password.TemporaryPassword;
import com.team17.preProject.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final TemporaryPassword temporaryPassword;
    private final TemporaryPasswordSender temporaryPasswordSender;

    @Override
    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    @Override
    public Member createMember(Member member) {
        findMemberByEmailExpectNull(member.getEmail());
        return memberRepository.save(member);
    }

    @Override
    public Member updateMember(Member member) {
        Member findMember = findVerifiedMember(member.getMemberId());

        findMember.updateMember(member);
        return memberRepository.save(findMember);
    }

    @Override
    public void deleteMember(long memberId) {
        Member findMember = findVerifiedMember(memberId);
        memberRepository.delete(findMember);
    }

    @Override
    public void resetPasswordByEmail(String email) {
        Member findMember = findMemberByEmailExpectExist(email);

        PasswordDto newPassword = temporaryPassword.create();
        temporaryPasswordSender.send(email, newPassword.getDecodedPassword());

        findMember.updatePassword(newPassword.getEncodedPassword());
        memberRepository.save(findMember);
    }

    @Override
    public void checkEmailValidate(String email) {
        findMemberByEmailExpectNull(email);
    }

    private Member findVerifiedMember(long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findMember;
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
