package com.team17.preProject.follow.dto;

import com.team17.preProject.answer.dto.AnswerDto;
import com.team17.preProject.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.Positive;

public class FollowAnswerDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Post{

        @Positive
        private long memberId;

        @Positive
        private long answerId;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class PostResponse {
        private MemberDto.SubResponse member;
        private AnswerDto.Response answer;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class GetResponse {
        private String questionTitle;
        private String answerContent;
    }
}
