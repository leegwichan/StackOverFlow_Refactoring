package com.team17.preProject.vote.controller;

import com.team17.preProject.dto.SingleResponseDto;
import com.team17.preProject.security.service.SecurityService;
import com.team17.preProject.vote.dto.VoteQuestionDto;
import com.team17.preProject.vote.entity.VoteQuestion;
import com.team17.preProject.vote.mapper.VoteQuestionMapper;
import com.team17.preProject.vote.service.VoteQuestionService;
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
@RequestMapping("/api/vote-question")
@RequiredArgsConstructor
public class VoteQuestionController {

    private final VoteQuestionService voteQuestionService;
    private final SecurityService securityService;
    private final VoteQuestionMapper mapper;

    @Secured("ROLE_USER")
    @PostMapping("/good")
    public ResponseEntity postVoteQuestionGood(@RequestBody @Valid VoteQuestionDto.Post requestBody,
                                               Authentication authentication) {
        securityService.checkMemberEqual(authentication, requestBody.getMemberId());

        VoteQuestion postVoteQuestion = voteQuestionService.voteGood(
                requestBody.getMemberId(), requestBody.getQuestionId());
        VoteQuestionDto.Response response = mapper.voteQuestionToVoteQuestionResponseDto(postVoteQuestion);

        return new ResponseEntity(
                new SingleResponseDto<>(response), HttpStatus.OK);

    }

    @Secured("ROLE_USER")
    @PostMapping("/bad")
    public ResponseEntity postVoteQuestionBad(@RequestBody @Valid VoteQuestionDto.Post requestBody,
                                              Authentication authentication){
        securityService.checkMemberEqual(authentication, requestBody.getMemberId());

        VoteQuestion postVoteQuestion = voteQuestionService.voteBad(
                requestBody.getMemberId(), requestBody.getQuestionId());
        VoteQuestionDto.Response response = mapper.voteQuestionToVoteQuestionResponseDto(postVoteQuestion);

        return new ResponseEntity(
                new SingleResponseDto<>(response), HttpStatus.OK);
    }
}
