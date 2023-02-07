package com.team17.preProject.domain.vote.mapper;

import com.team17.preProject.domain.vote.dto.VoteAnswerDto;
import com.team17.preProject.domain.vote.entity.VoteAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoteAnswerMapper {

    @Mapping(target = "memberId", expression = "java(voteAnswer.getMember().getMemberId())")
    @Mapping(target = "answerId", expression = "java(voteAnswer.getAnswer().getAnswerId())")
    VoteAnswerDto.Response voteAnswerToVoteAnswerResponseDto(VoteAnswer voteAnswer);
}
