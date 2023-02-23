package com.team17.preProject.domain.member.service;

import com.team17.preProject.domain.member.entity.Member;

public interface MemberService {
    Member findMember(long memberId);
    Member createMember(Member member);
    Member updateMember(Member member);
    void deleteMember(long memberId);
    void resetPasswordByEmail(String email);
    void checkEmailValidate(String email);
}
