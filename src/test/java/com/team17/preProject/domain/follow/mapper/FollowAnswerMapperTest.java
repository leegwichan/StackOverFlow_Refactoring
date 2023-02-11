package com.team17.preProject.domain.follow.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.team17.preProject.domain.answer.dto.AnswerDto;
import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.follow.dto.FollowAnswerDto;
import com.team17.preProject.domain.follow.entity.FollowAnswer;
import com.team17.preProject.domain.question.entity.Question;
import com.team17.preProject.helper.stub.AnswerStub;
import com.team17.preProject.helper.stub.MemberStub;
import com.team17.preProject.helper.stub.QuestionStub;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.util.List;

public class FollowAnswerMapperTest {

    private static final FollowAnswerMapper mapper = Mappers.getMapper(FollowAnswerMapper.class);
    private static final FollowAnswer MOCK_ENTITY = new FollowAnswer();
    private static final Answer MOCK_ANSWER = AnswerStub.getEntity();
    private static final Question MOCK_QUESTION = QuestionStub.getEntity();
    static {
        MOCK_QUESTION.setMember(MemberStub.getEntity());
        MOCK_ANSWER.setQuestion(MOCK_QUESTION);
        MOCK_ANSWER.setMember(MemberStub.getEntity());
        MOCK_ENTITY.setAnswer(MOCK_ANSWER);
        MOCK_ENTITY.setMember(MemberStub.getEntity());
    }

    @Test
    void followAnswerToFollowAnswerPostResponseDtoTest() {
        FollowAnswerDto.PostResponse expected
                = new FollowAnswerDto.PostResponse(MemberStub.getSubResponse(), AnswerStub.getResponse());

        FollowAnswerDto.PostResponse result
                = mapper.followAnswerToFollowAnswerPostResponseDto(MOCK_ENTITY);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void followAnswersToAnswerResponseDtos() {
        List<FollowAnswer> followAnswers = List.of(MOCK_ENTITY, MOCK_ENTITY);
        List<AnswerDto.FullResponse> expected = List.of(mockFullResponseDto(), mockFullResponseDto());

        List<AnswerDto.FullResponse> result = mapper.followAnswersToAnswerResponseDtos(followAnswers);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    AnswerDto.FullResponse mockFullResponseDto() {
        return new AnswerDto.FullResponse(
                MOCK_ANSWER.getAnswerId(), MOCK_ANSWER.getContent(), MOCK_ANSWER.getVote(),
                MOCK_ANSWER.getCreatedAt(), MOCK_ANSWER.getModifiedAt(), QuestionStub.getSubResponse());
    }
}
