package com.team17.preProject.domain.answer.controller;

import com.team17.preProject.domain.answer.dto.AnswerDto;
import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.answer.mapper.AnswerMapper;
import com.team17.preProject.domain.answer.service.AnswerService;
import com.team17.preProject.domain.question.mapper.QuestionMapper;
import com.team17.preProject.dto.MultiResponseDto;
import com.team17.preProject.dto.SingleResponseDto;
import com.team17.preProject.domain.question.dto.QuestionDto;

import com.team17.preProject.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
@Validated
public class AnswerController {

    private final AnswerService answerService;
    private final SecurityService securityService;
    private final AnswerMapper mapper;
    private final QuestionMapper questionMapper;

    @GetMapping
    public ResponseEntity getAnswersByQuestionId(@RequestParam("question-id") @Positive long questionId){

        List<Answer> answers = answerService.findAnswersByQuestion(questionId);
        List<AnswerDto.Response> response = mapper.answersToAnswerResponseDtos(answers);

        return new ResponseEntity(
                new SingleResponseDto(response), HttpStatus.OK);
    }

    @GetMapping("/by-member")
    public ResponseEntity getAnswersByMemberId(@RequestParam("member-id") @Positive long memberId,
                                               @RequestParam @Positive int page,
                                               @RequestParam @Positive @Max(500L) int size){

        Page<Answer> answers = answerService.findAnswersByMemberId(page-1, size, memberId);
        List<AnswerDto.FullResponse> response = mapper.answersToAnswerFullResponseDtos(answers.getContent());

        return new ResponseEntity(
                new MultiResponseDto<>(response, answers), HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @PostMapping
    public ResponseEntity postAnswer(@RequestBody @Valid AnswerDto.Post requestBody,
                                     Authentication authentication){
        securityService.checkMemberEqual(authentication, requestBody.getMemberId());

        Answer answer = mapper.answerPostDtoToAnswer(requestBody);
        Answer postAnswer = answerService.createAnswer(answer);
        AnswerDto.Response response = mapper.answerToAnswerResponseDto(postAnswer);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.CREATED
        );
    }

    @Secured("ROLE_USER")
    @PatchMapping("/{answer-id}")
    public ResponseEntity patchAnswer(@PathVariable("answer-id") @Positive long answerId,
                                      @RequestBody @Valid AnswerDto.Patch requestBody,
                                      Authentication authentication){
        securityService.checkAnswerWriter(authentication, answerId);

        requestBody.setAnswerId(answerId);
        Answer answer = mapper.answerPatchDtoToAnswer(requestBody);
        Answer patchAnswer = answerService.updateAnswer(answer);
        AnswerDto.Response response = mapper.answerToAnswerResponseDto(patchAnswer);

        return new ResponseEntity(
                new SingleResponseDto<>(response), HttpStatus.OK
        );
    }

    @Secured("ROLE_USER")
    @PatchMapping("/pick/{answer-id}")
    public ResponseEntity checkBestAnswer(@PathVariable("answer-id") @Positive long answerId,
                                          Authentication authentication){
        securityService.checkQuestionWriterByAnswerId(authentication, answerId);

        Answer patchQuestion = answerService.checkBestAnswer(answerId);
        QuestionDto.SubResponse response = questionMapper.questionToQuestionSubResponseDto(patchQuestion.getQuestion());

        return new ResponseEntity(
                new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id") @Positive long answerId,
                                       Authentication authentication){
        securityService.checkAnswerWriter(authentication, answerId);

        answerService.deleteAnswer(answerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
