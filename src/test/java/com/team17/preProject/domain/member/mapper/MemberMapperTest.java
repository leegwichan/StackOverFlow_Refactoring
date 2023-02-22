package com.team17.preProject.domain.member.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.team17.preProject.domain.member.dto.MemberDto;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.helper.stub.MemberStub;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class MemberMapperTest {

    private final MemberMapper memberMapper = Mappers.getMapper(MemberMapper.class);
    private static final Member MOCK_MEMBER = MemberStub.ENTITY;

    @Test
    void memberPatchDtoToMemberTest() {
        MemberDto.Patch dto = new MemberDto.Patch(1L,"nickname", "http://url", "location",
                "title", "aboutMe");
        Member expected = Member.builder()
                .memberId(1L)
                .displayName("nickname")
                .image("http://url")
                .location("location")
                .memberTitle("title")
                .aboutMe("aboutMe").build();

        Member member = memberMapper.memberPatchDtoToMember(dto);

        assertThat(member).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void memberPostDtoToMemberTest() {
        MemberDto.Post dto = new MemberDto.Post("email@naver.com", "password1!", "name");
        Member expected = Member.builder()
                .email("email@naver.com")
                .password("password1!")
                .displayName("name").build();

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
