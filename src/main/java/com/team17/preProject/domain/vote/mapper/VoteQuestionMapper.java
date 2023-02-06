package com.team17.preProject.domain.vote.mapper;

import com.team17.preProject.domain.vote.dto.VoteQuestionDto;
import com.team17.preProject.domain.vote.entity.VoteQuestion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoteQuestionMapper {
    default VoteQuestionDto.Response voteQuestionToVoteQuestionResponseDto(VoteQuestion voteQuestion){
        return new VoteQuestionDto.Response(
                voteQuestion.getMember().getMemberId(),
                voteQuestion.getQuestion().getQuestionId(),
                voteQuestion.getVote()
        );
    }
}
