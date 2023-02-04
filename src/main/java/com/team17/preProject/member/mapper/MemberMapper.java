package com.team17.preProject.member.mapper;

import com.team17.preProject.member.dto.MemberDto;
import com.team17.preProject.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

     Member memberPatchDtoToMember(MemberDto.Patch patch);
     Member memberPostDtoToMember(MemberDto.Post post);
     MemberDto.Response memberToMemberResponseDto(Member member);
     MemberDto.SubResponse memberToMemberSubResponseDto(Member member);

}
