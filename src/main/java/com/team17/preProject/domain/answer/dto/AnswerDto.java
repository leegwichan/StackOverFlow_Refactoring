package com.team17.preProject.domain.answer.dto;

import com.team17.preProject.domain.member.dto.MemberDto;
import com.team17.preProject.domain.question.dto.QuestionDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
public class AnswerDto {

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Response{
        private long answerId;
        private String content;
        private long vote;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private MemberDto.SubResponse member;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class FullResponse{
        private long answerId;
        private String content;
        private long vote;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private QuestionDto.SubResponse question;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Post{

        @Size(max = 10000, message = "게시물의 내용은 최대 10000자 입니다.")
        @NotBlank(message = "글의 content 는 공백이 아니어야 합니다.")
        private String content;

        @Positive
        private long questionId;

        @Positive
        private long memberId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Patch{
        @Size(max = 10000, message = "게시물의 내용은 최대 10000자 입니다.")
        @NotBlank(message = "글의 content 는 공백이 아니어야 합니다.")
        private String content;
    }
}
