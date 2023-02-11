package com.team17.preProject.domain.vote.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.team17.preProject.domain.vote.dto.VoteAnswerDto;
import com.team17.preProject.domain.vote.entity.VoteAnswer;
import com.team17.preProject.helper.stub.VoteStub;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class VoteAnswerMapperTest {

    private final VoteAnswerMapper mapper = Mappers.getMapper(VoteAnswerMapper.class);
    private static final VoteAnswer MOCK_ENTITY = VoteStub.VOTE_ANSWER_ENTITY;

    @Test
    void voteAnswerToVoteAnswerResponseDtoTest() {
        VoteAnswerDto.Response expected = new VoteAnswerDto.Response(
                MOCK_ENTITY.getMember().getMemberId(),
                MOCK_ENTITY.getAnswer().getAnswerId(),
                MOCK_ENTITY.getVote()
        );

        VoteAnswerDto.Response result = mapper.voteAnswerToVoteAnswerResponseDto(MOCK_ENTITY);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }
}
