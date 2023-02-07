package com.team17.preProject.domain.member.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.team17.preProject.domain.member.dto.MemberDto;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.member.entity.MemberRole;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class MemberMapperTest {

    private final MemberMapper memberMapper = Mappers.getMapper(MemberMapper.class);
    private static final Member MOCK_MEMBER;
    static {
        MOCK_MEMBER = new Member();
        MOCK_MEMBER.setMemberId(5L);
        MOCK_MEMBER.setEmail("email@email.com");
        MOCK_MEMBER.setDisplayName("name");
        MOCK_MEMBER.setImage("https://img");
        MOCK_MEMBER.setLocation("location");
        MOCK_MEMBER.setMemberTitle("title");
        MOCK_MEMBER.setAboutMe("aboutMe");
    }

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

    @Test
    void memberPostDtoToMemberTest() {
        MemberDto.Post dto = new MemberDto.Post("email@naver.com", "password1!", "name");
        Member expected = new Member();
        expected.setEmail("email@naver.com");
        expected.setPassword("password1!");
        expected.setDisplayName("name");

        Member member = memberMapper.memberPostDtoToMember(dto);

        assertThat(member).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void memberToMemberResponseDto() {
        MemberDto.Response expected =
                new MemberDto.Response(MOCK_MEMBER.getMemberId(), MOCK_MEMBER.getEmail(), MOCK_MEMBER.getDisplayName(),
                        MOCK_MEMBER.getImage(), MOCK_MEMBER.getLocation(),
                        MOCK_MEMBER.getMemberTitle(), MOCK_MEMBER.getAboutMe());

        MemberDto.Response result = memberMapper.memberToMemberResponseDto(MOCK_MEMBER);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void memberToMemberSubResponseDto() {
        MemberDto.SubResponse expected = new MemberDto.SubResponse(
                MOCK_MEMBER.getMemberId(), MOCK_MEMBER.getEmail(),
                MOCK_MEMBER.getDisplayName(), MOCK_MEMBER.getImage());

        MemberDto.SubResponse result = memberMapper.memberToMemberSubResponseDto(MOCK_MEMBER);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }
}
