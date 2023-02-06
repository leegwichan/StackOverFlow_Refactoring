package com.team17.preProject.domain.question.controller;


import com.team17.preProject.domain.answer.dto.AnswerDto;
import com.team17.preProject.domain.member.dto.MemberDto;
import com.team17.preProject.dto.MultiResponseDto;
import com.team17.preProject.dto.SingleResponseDto;
import com.team17.preProject.domain.question.dto.QuestionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/dummy/question")
public class QuestionControllerStub {

    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@RequestParam int page,
                                      @RequestParam int size,
                                      @PathVariable("question-id") long questionId){

        return new ResponseEntity<>(
                new SingleResponseDto<>(questionDto()), HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity getQuestions(@RequestParam int page,
                                       @RequestParam int size){
        return new ResponseEntity<>(
                questionMultiResponseDto(), HttpStatus.OK
        );
    }

    private QuestionDto.Response questionDto(){

        return new QuestionDto.Response(
                1,
                "게시글 titile 입니다.",
                "게시글이 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~",
                456,
                23,
                LocalDateTime.now(),
                LocalDateTime.now(),
                memberSubDto(),
                answerMultiResponseDto().getData(),
                answerMultiResponseDto().getData().size(),
                1L
        );
    }

    private MemberDto.SubResponse memberSubDto() {
        return new MemberDto.SubResponse(
                1,
                "cnddkscndgus@naver.com",
                "귀찮Lee",
                "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAxOTEyMDJfMjM5%2FMDAxNTc1MjQ5NzIyOTMw.qW1xbu96TGcuxCSh_WH9fvqjiQd8Sy8GvtWe7s_WjTcg._QJQWDySEPuRmJC4LGXq_587vzAnmyWztycwWlAdaXwg.JPEG.ty5568yr%2FKakaoTalk_20191202_101754417.jpg&type=ff332_332"
        );
    }

    private MultiResponseDto answerMultiResponseDto() {
        AnswerDto.Response data1 = new AnswerDto.Response(
                1,
                "이것이 답변 content 입니다 1",
                10000,
                LocalDateTime.now(),
                LocalDateTime.now(),
                memberSubDto()
        );

        AnswerDto.Response data2 = new AnswerDto.Response(
                2,
                "이것이 내용 title입니다 1, 이것이 내용 title입니다 1, 이것이 내용 title입니다 1",
                100000,
                LocalDateTime.now(),
                LocalDateTime.now(),
                memberSubDto()
        );
        AnswerDto.Response data3 = new AnswerDto.Response(
                3,
                "이것이 내용 title입니다 1, 이것이 내용 title입니다 1, 이것이 내용 title입니다 1",
                1000000,
                LocalDateTime.now(),
                LocalDateTime.now(),
                memberSubDto()
        );
        AnswerDto.Response data4 = new AnswerDto.Response(
                4,
                "이것이 내용 title입니다 1, 이것이 내용 title입니다 1, 이것이 내용 title입니다 1",
                -1234,
                LocalDateTime.now(),
                LocalDateTime.now(),
                memberSubDto()
        );

        List<AnswerDto.Response> data = List.of(data1, data2, data3, data4);
        Page page = new PageImpl(data,
                PageRequest.of(0, 10, Sort.by("createdAt").descending()), 2);

        return new MultiResponseDto(data, page);
    }

    private MultiResponseDto questionMultiResponseDto() {
        QuestionDto.SubResponse data1 = new QuestionDto.SubResponse(
                1,
                "게시글 titile 입니다.",
                "게시글이 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~",
                456,
                23,
                LocalDateTime.now(),
                LocalDateTime.now(),
                memberSubDto(),
                4,
                null
        );
        QuestionDto.SubResponse data2 = new QuestionDto.SubResponse(
                2,
                "게시글 titile 입니다.",
                "게시글이 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~",
                45600,
                230,
                LocalDateTime.now(),
                LocalDateTime.now(),
                memberSubDto(),4,
                1L
        );
        QuestionDto.SubResponse data3 = new QuestionDto.SubResponse(
                3,
                "게시글 titile 입니다.",
                "게시글이 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~",
                456000,
                -45,
                LocalDateTime.now(),
                LocalDateTime.now(),
                memberSubDto(),4,
                null
        );
        QuestionDto.SubResponse data4 = new QuestionDto.SubResponse(
                4,
                "게시글 titile 입니다.",
                "게시글이 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~",
                4,
                1,
                LocalDateTime.now(),
                LocalDateTime.now(),
                memberSubDto(),4,
                5L
        );

        List<QuestionDto.SubResponse> data = List.of(data1, data2, data3, data4);
        Page page = new PageImpl(data,
                PageRequest.of(0, 10, Sort.by("createdAt").descending()), 2);

        return new MultiResponseDto(data, page);
    }
}
