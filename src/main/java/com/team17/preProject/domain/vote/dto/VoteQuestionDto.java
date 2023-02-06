package com.team17.preProject.domain.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.Positive;

public class VoteQuestionDto {

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
    public static class Response{
        private long memberId;
        private long questionId;
        private int vote;
    }
}
