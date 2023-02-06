package com.team17.preProject.domain.member.service;

import com.team17.preProject.domain.member.entity.Member;

public interface MemberService {
    Member findMember(long memberId);
    void resetPasswordByEmail(String email);
    Member createMember(Member member);
    Member updateMember(Member member);
    void deleteMember(long memberId);
    Member findVerifiedMember(long memberId);
    void checkEmailValidate(String email);
}
