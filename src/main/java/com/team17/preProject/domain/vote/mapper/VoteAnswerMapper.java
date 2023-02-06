package com.team17.preProject.domain.vote.mapper;

import com.team17.preProject.domain.vote.dto.VoteAnswerDto;
import com.team17.preProject.domain.vote.entity.VoteAnswer;
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
