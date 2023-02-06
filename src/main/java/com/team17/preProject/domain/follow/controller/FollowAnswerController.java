package com.team17.preProject.domain.follow.controller;

import com.team17.preProject.domain.answer.dto.AnswerDto;
import com.team17.preProject.domain.follow.service.FollowAnswerService;
import com.team17.preProject.dto.MultiResponseDto;
import com.team17.preProject.dto.SingleResponseDto;
import com.team17.preProject.domain.follow.dto.FollowAnswerDto;
import com.team17.preProject.domain.follow.entity.FollowAnswer;
import com.team17.preProject.domain.follow.mapper.FollowAnswerMapper;
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
@RequestMapping("/api/follow-answer")
@RequiredArgsConstructor
@Validated
public class FollowAnswerController {

    private final FollowAnswerService followAnswerService;
    private final SecurityService securityService;
    private final FollowAnswerMapper mapper;

    @GetMapping("/{member-id}")
    public ResponseEntity getFollowAnswersByMember(@PathVariable("member-id") @Positive long memberId,
                                                   @RequestParam @Positive int page,
                                                   @RequestParam @Positive @Max(500L) int size){
        Page<FollowAnswer> followAnswers = followAnswerService.findFollowAnswerByMember(page-1, size, memberId);
        List<AnswerDto.FullResponse> response = mapper.followAnswersToAnswerResponseDtos(followAnswers.getContent());
        return new ResponseEntity(
                new MultiResponseDto<>(response, followAnswers), HttpStatus.OK
        );
    }

    @Secured("USER_ROLE")
    @PostMapping
    public ResponseEntity postFollowAnswer(@RequestBody @Valid FollowAnswerDto.Post requestBody,
                                           Authentication authentication){
        securityService.checkMemberEqual(authentication, requestBody.getMemberId());

        FollowAnswer postFollowAnswer
                = followAnswerService.createFollowAnswer(requestBody.getMemberId(), requestBody.getAnswerId());
        FollowAnswerDto.PostResponse response = mapper.FollowAnswerToFollowAnswerPostResponseDto(postFollowAnswer);

        return new ResponseEntity(
                new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @Secured("USER_ROLE")
    @DeleteMapping
    public ResponseEntity deleteFollowAnswer(@RequestParam("member-id") @Positive long memberId,
                                             @RequestParam("answer-id") @Positive long answerId,
                                             Authentication authentication){
        securityService.checkMemberEqual(authentication, memberId);

        followAnswerService.deleteFollowAnswer(memberId, answerId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
