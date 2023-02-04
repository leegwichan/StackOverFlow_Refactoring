package com.team17.preProject.vote.mapper;

import com.team17.preProject.vote.dto.VoteAnswerDto;
import com.team17.preProject.vote.entity.VoteAnswer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoteAnswerMapper {
    default VoteAnswerDto.Response voteAnswerToVoteAnswerResponseDto(VoteAnswer voteAnswer){
        return new VoteAnswerDto.Response(
                voteAnswer.getMember().getMemberId(),
                voteAnswer.getAnswer().getAnswerId(),
                voteAnswer.getVote()
        );
    }
}
