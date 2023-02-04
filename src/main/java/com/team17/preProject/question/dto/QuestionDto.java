package com.team17.preProject.question.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team17.preProject.answer.dto.AnswerDto;
import com.team17.preProject.dto.MultiResponseDto;
import com.team17.preProject.member.dto.MemberDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class QuestionDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response{
        private long questionId;
        private String title;
        private String content;
        private long view;
        private long vote;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private MemberDto.SubResponse member;
        private List<AnswerDto.Response> answers;
        private int answerCount;
        private Long bestAnswerId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SubResponse{
        private long questionId;
        private String title;
        private String content;
        private long view;
        private long vote;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private MemberDto.SubResponse member;
        private int answerCount;
        private Long bestAnswerId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Post{

        @Size(max = 225, message = "게시물의 제목은 최대 255자 입니다.")
        @NotBlank(message = "글의 title 은 공백이 아니어야 합니다.")
        private String title;

        @Size(max = 10000, message = "게시물의 내용은 최대 10000자 입니다.")
        @NotBlank(message = "글의 content 는 공백이 아니어야 합니다.")
        private String content;

        @Positive
        private long memberId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Patch{

        @Size(max = 225, message = "게시물의 제목은 최대 255자 입니다.")
        @NotBlank(message = "글의 title 은 공백이 아니어야 합니다.")
        private String title;

        @Size(max = 10000, message = "게시물의 내용은 최대 10000자 입니다.")
        @NotBlank(message = "글의 content 는 공백이 아니어야 합니다.")
        private String content;
    }
}
