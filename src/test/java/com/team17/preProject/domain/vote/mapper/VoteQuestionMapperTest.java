package com.team17.preProject.domain.vote.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.team17.preProject.domain.vote.dto.VoteQuestionDto;
import com.team17.preProject.domain.vote.entity.VoteQuestion;
import com.team17.preProject.helper.stub.VoteStub;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class VoteQuestionMapperTest {

    private final VoteQuestionMapper mapper = Mappers.getMapper(VoteQuestionMapper.class);
    private static final VoteQuestion MOCK_ENTITY = VoteStub.VOTE_QUESTION_ENTITY;

    @Test
    void voteQuestionToVoteQuestionResponseDto() {
        VoteQuestionDto.Response expected = new VoteQuestionDto.Response(
                MOCK_ENTITY.getMember().getMemberId(),
                MOCK_ENTITY.getQuestion().getQuestionId(),
                MOCK_ENTITY.getVote()
        );

        VoteQuestionDto.Response result = mapper.voteQuestionToVoteQuestionResponseDto(MOCK_ENTITY);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }
}
