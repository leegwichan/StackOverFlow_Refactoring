package com.team17.preProject.vote.mapper;

import com.team17.preProject.vote.dto.VoteQuestionDto;
import com.team17.preProject.vote.entity.VoteQuestion;
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
