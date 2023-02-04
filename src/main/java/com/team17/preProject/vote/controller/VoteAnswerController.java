package com.team17.preProject.vote.controller;

import com.team17.preProject.dto.SingleResponseDto;
import com.team17.preProject.security.service.SecurityService;
import com.team17.preProject.vote.dto.VoteAnswerDto;
import com.team17.preProject.vote.dto.VoteQuestionDto;
import com.team17.preProject.vote.entity.VoteAnswer;
import com.team17.preProject.vote.mapper.VoteAnswerMapper;
import com.team17.preProject.vote.service.VoteAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/vote-answer")
@RequiredArgsConstructor
public class VoteAnswerController {

    private final VoteAnswerService voteAnswerService;
    private final SecurityService securityService;
    private final VoteAnswerMapper mapper;

    @Secured("ROLE_USER")
    @PostMapping("/good")
    public ResponseEntity postVoteAnswerGood(@RequestBody @Valid VoteAnswerDto.Post requestBody,
                                             Authentication authentication){
        securityService.checkMemberEqual(authentication, requestBody.getMemberId());

        VoteAnswer postVoteAnswer = voteAnswerService.voteGood(
                requestBody.getMemberId(), requestBody.getAnswerId());
        VoteAnswerDto.Response response = mapper.voteAnswerToVoteAnswerResponseDto(postVoteAnswer);

        return new ResponseEntity(
                new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @PostMapping("/bad")
    public ResponseEntity postVoteAnswerGBad(@RequestBody @Valid VoteAnswerDto.Post requestBody,
                                             Authentication authentication){
        securityService.checkMemberEqual(authentication, requestBody.getMemberId());

        VoteAnswer postVoteAnswer = voteAnswerService.voteBad(
                requestBody.getMemberId(), requestBody.getAnswerId());
        VoteAnswerDto.Response response = mapper.voteAnswerToVoteAnswerResponseDto(postVoteAnswer);

        return new ResponseEntity(
                new SingleResponseDto<>(response), HttpStatus.OK);
    }
}
