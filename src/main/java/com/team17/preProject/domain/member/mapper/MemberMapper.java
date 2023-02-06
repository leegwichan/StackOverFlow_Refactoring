package com.team17.preProject.domain.member.mapper;

import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.member.dto.MemberDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

     Member memberPatchDtoToMember(MemberDto.Patch patch);
     Member memberPostDtoToMember(MemberDto.Post post);
     MemberDto.Response memberToMemberResponseDto(Member member);
     MemberDto.SubResponse memberToMemberSubResponseDto(Member member);

}
