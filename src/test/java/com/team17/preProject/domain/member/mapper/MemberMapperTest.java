package com.team17.preProject.domain.member.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.team17.preProject.domain.member.dto.MemberDto;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.member.entity.MemberRole;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class MemberMapperTest {

    private final MemberMapper memberMapper = Mappers.getMapper(MemberMapper.class);

    @Test
    void memberPatchDtoToMemberTest() {
        MemberDto.Patch dto = new MemberDto.Patch("nickname", "http://url", "location",
                "title", "aboutMe");
        Member expected = new Member();
        expected.setDisplayName("nickname");
        expected.setImage("http://url");
        expected.setLocation("location");
        expected.setMemberTitle("title");
        expected.setAboutMe("aboutMe");

        Member member = memberMapper.memberPatchDtoToMember(dto);

        assertThat(member).usingRecursiveComparison().isEqualTo(expected);
    }
}
