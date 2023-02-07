package com.team17.preProject.helper.stub;

import com.team17.preProject.domain.member.dto.MemberDto;
import com.team17.preProject.domain.member.entity.Member;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class MemberStub {

    private static Map<HttpMethod, Object> requestBody;
    private static MemberDto.Response singleResponseBody;
    private static Page<MemberDto.Response> multiResponseBody;

    private static MemberDto.SubResponse subResponse;

    static {
        requestBody = new HashMap<>();
        requestBody.put(HttpMethod.PATCH, new MemberDto.Patch("GillDong Hong1",
                Stub.getProfileImage(), "서울1", "백엔드 개발자입니다.1", "저는 ~~~~~~~~~~~~~~ 를 좋아합니다.1"));

        singleResponseBody = new MemberDto.Response(1,"gillDong1@gmail.com","GillDong Hong1",
                Stub.getProfileImage(), "서울1", "백엔드 개발자입니다.1", "저는 ~~~~~~~~~~~~~~ 를 좋아합니다.1");

        List<MemberDto.Response> multiResponseBodyContent = new ArrayList<>();
        multiResponseBodyContent.add( new MemberDto.Response(1,"gillDong1@gmail.com","GillDong Hong1",
                Stub.getProfileImage(), "서울1", "백엔드 개발자입니다.1", "저는 ~~~~~~~~~~~~~~ 를 좋아합니다.1"));
        multiResponseBodyContent.add( new MemberDto.Response(2,"gillDong2@gmail.com","GillDong Hong2",
                Stub.getProfileImage(), "서울2", "백엔드 개발자입니다.2", "저는 ~~~~~~~~~~~~~~ 를 좋아합니다.2"));
        multiResponseBodyContent.add( new MemberDto.Response(3,"gillDong3@gmail.com","GillDong Hong3",
                Stub.getProfileImage(), "서울3", "백엔드 개발자입니다.3", "저는 ~~~~~~~~~~~~~~ 를 좋아합니다.3"));

        multiResponseBody = new PageImpl(multiResponseBodyContent,
                PageRequest.of(0,10, Sort.by("memberId").descending()),
                multiResponseBodyContent.size());

        subResponse = new MemberDto.SubResponse(1,"gillDong1@gmail.com",
                "GillDong Hong1",Stub.getProfileImage());
    }

    public static Map<HttpMethod, Object> getRequestBody(){
        return requestBody;
    }

    public static MemberDto.Response getSingleResponseBody() {
        return singleResponseBody;
    }

    public static Page<MemberDto.Response> getMultiResponseBody() {
        return multiResponseBody;
    }

    public static MemberDto.SubResponse getSubResponse(){
        return subResponse;
    }

    public static final Member ENTITY;
    static {
        ENTITY = new Member();
        ENTITY.setMemberId(5L);
        ENTITY.setEmail("email@email.com");
        ENTITY.setDisplayName("name");
        ENTITY.setImage("https://img");
        ENTITY.setLocation("location");
        ENTITY.setMemberTitle("title");
        ENTITY.setAboutMe("aboutMe");
    }
    public static final MemberDto.SubResponse SUB_RESPONSE = new MemberDto.SubResponse(
            ENTITY.getMemberId(), ENTITY.getEmail(), ENTITY.getDisplayName(), ENTITY.getImage());
}
