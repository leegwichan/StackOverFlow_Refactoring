package com.team17.preProject.domain.question.controller;

import com.team17.preProject.domain.question.entity.Question;
import com.team17.preProject.domain.question.mapper.QuestionMapper;
import com.team17.preProject.domain.question.service.QuestionService;
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
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import java.util.List;

@Controller
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@Validated
public class QuestionControllerImpl{

    private final QuestionService questionService;
    private final QuestionMapper mapper;
    private final SecurityService securityService;

    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@PathVariable("question-id") @Positive long questionId) {

        Question findQuestion = questionService.inquireQuestion(questionId);
        QuestionDto.Response response = mapper.questionToQuestionResponseDto(findQuestion);

        return new ResponseEntity(
                new SingleResponseDto(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getQuestions(@RequestParam @Positive int page,
                                       @RequestParam @Positive @Max(500L) int size) {

        Page<Question> findQuestions = questionService.findQuestions(page-1, size);
        List<QuestionDto.SubResponse> response = mapper.questionsToQuestionSubResponseDtos(findQuestions.getContent());
        return new ResponseEntity(
                new MultiResponseDto(response, findQuestions), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity getQuestionsByKeyword(@RequestParam String keyword,
                                               @RequestParam @Positive int page,
                                               @RequestParam @Positive @Max(500L) int size){

        Page<Question> findQuestions = questionService.findQuestions(page-1, size, keyword);
        List<QuestionDto.SubResponse> response = mapper.questionsToQuestionSubResponseDtos(findQuestions.getContent());
        return new ResponseEntity(
                new MultiResponseDto<>(response, findQuestions), HttpStatus.OK);
    }

    @GetMapping("/by-member")
    public ResponseEntity getQuestionsByMemberId(@RequestParam("member-id") @Positive long memberId,
                                                 @RequestParam @Positive int page,
                                                 @RequestParam @Positive @Max(500L) int size){

        Page<Question> findQuestion = questionService.findQuestions(page-1, size, memberId);
        List<QuestionDto.SubResponse> responses = mapper.questionsToQuestionSubResponseDtos(findQuestion.getContent());
        return new ResponseEntity(
                new MultiResponseDto<>(responses, findQuestion), HttpStatus.OK
        );
    }

    @Secured("ROLE_USER")
    @PostMapping
    public ResponseEntity postQuestion(@RequestBody @Valid QuestionDto.Post requestBody,
                                       Authentication authentication) {

        securityService.checkMemberEqual(authentication, requestBody.getMemberId());

        Question question = mapper.questionPostDtoToQuestion(requestBody);
        Question postQuestion = questionService.createQuestion(question);
        QuestionDto.SubResponse response = mapper.questionToQuestionSubResponseDto(postQuestion);

        return new ResponseEntity(
                new SingleResponseDto(response), HttpStatus.CREATED);
    }

    @Secured("ROLE_USER")
    @PatchMapping("/{question-id}")
    public ResponseEntity patchQuestion(@PathVariable("question-id") @Positive long questionId,
                                        @RequestBody @Valid QuestionDto.Patch requestBody,
                                        Authentication authentication) {
        securityService.checkQuestionWriter(authentication, questionId);
        requestBody.setQuestionId(questionId);
        Question question = mapper.questionPatchDtoToQuestion(requestBody);

        Question patchQuestion = questionService.updateQuestion(question);
        QuestionDto.SubResponse response = mapper.questionToQuestionSubResponseDto(patchQuestion);

        return new ResponseEntity<>(
                new SingleResponseDto(response), HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("{question-id}")
    public ResponseEntity deleteQuestion(@PathVariable("question-id") @Positive long questionId,
                                         Authentication authentication) {
        securityService.checkQuestionWriter(authentication, questionId);

        questionService.deleteQuestion(questionId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
