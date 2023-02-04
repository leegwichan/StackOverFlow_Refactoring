package com.team17.preProject.follow.dto;

import com.team17.preProject.member.dto.MemberDto;
import com.team17.preProject.question.dto.QuestionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;

public class FollowQuestionDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Post{

        @Positive
        private long memberId;

        @Positive
        private long questionId;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class PostResponse {
        private MemberDto.SubResponse member;
        private QuestionDto.SubResponse question;
    }

}
