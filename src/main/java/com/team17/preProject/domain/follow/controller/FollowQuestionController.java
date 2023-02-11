package com.team17.preProject.domain.follow.controller;

import com.team17.preProject.domain.follow.entity.FollowQuestion;
import com.team17.preProject.domain.follow.mapper.FollowQuestionMapper;
import com.team17.preProject.domain.follow.service.FollowQuestionService;
import com.team17.preProject.dto.MultiResponseDto;
import com.team17.preProject.dto.SingleResponseDto;
import com.team17.preProject.domain.follow.dto.FollowQuestionDto;
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
@RequestMapping("/api/follow-question")
@RequiredArgsConstructor
@Validated
public class FollowQuestionController {

    private final FollowQuestionService followQuestionService;
    private final SecurityService securityService;
    private final FollowQuestionMapper mapper;

    @GetMapping("/{member-id}")
    public ResponseEntity getFollowQuestionsByMember(@PathVariable("member-id") @Positive long memberId,
                                                   @RequestParam @Positive int page,
                                                   @RequestParam @Positive @Max(500L) int size){
        Page<FollowQuestion> followQuestions
                = followQuestionService.findFollowQuestionsByMember(page-1, size, memberId);
        List<QuestionDto.SubResponse> response
                = mapper.followQuestionsToQuestionSubResponseDto(followQuestions.getContent());

        return new ResponseEntity<>(
                new MultiResponseDto<>(response, followQuestions), HttpStatus.OK
        );
    }

    @Secured("USER_ROLE")
    @PostMapping
    public ResponseEntity postFollowQuestion(@RequestBody @Valid FollowQuestionDto.Post requestBody,
                                             Authentication authentication){
        securityService.checkMemberEqual(authentication, requestBody.getMemberId());

        FollowQuestion followQuestion
                = followQuestionService.createFollowQuestion(requestBody.getMemberId(), requestBody.getQuestionId());
        FollowQuestionDto.PostResponse response
                = mapper.followQuestionToFollowQuestionPostResponseDto(followQuestion);

        return new ResponseEntity(
                new SingleResponseDto<>(response), HttpStatus.OK
        );
    }

    @Secured("USER_ROLE")
    @DeleteMapping
    public ResponseEntity deleteFollowQuestion(@RequestParam("member-id") @Positive long memberId,
                                               @RequestParam("question-id") @Positive long questionId,
                                               Authentication authentication){
        securityService.checkMemberEqual(authentication, memberId);

        followQuestionService.deleteFollowQuestion(memberId, questionId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
