package com.team17.preProject.domain.vote.mapper;

import com.team17.preProject.domain.vote.dto.VoteQuestionDto;
import com.team17.preProject.domain.vote.entity.VoteQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoteQuestionMapper {

    @Mapping(target = "memberId", expression = "java(voteQuestion.getMember().getMemberId())")
    @Mapping(target = "questionId", expression = "java(voteQuestion.getQuestion().getQuestionId())")
    VoteQuestionDto.Response voteQuestionToVoteQuestionResponseDto(VoteQuestion voteQuestion);
}
