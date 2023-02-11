package com.team17.preProject.domain.follow.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.follow.dto.FollowAnswerDto;
import com.team17.preProject.domain.follow.entity.FollowAnswer;
import com.team17.preProject.helper.stub.AnswerStub;
import com.team17.preProject.helper.stub.MemberStub;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.util.List;

public class FollowAnswerMapperTest {

    private static final FollowAnswerMapper mapper = Mappers.getMapper(FollowAnswerMapper.class);
    private static final FollowAnswer MOCK_ENTITY = new FollowAnswer();
    static {
        Answer mockAnswer = AnswerStub.getEntity();
        mockAnswer.setMember(MemberStub.getEntity());
        MOCK_ENTITY.setAnswer(mockAnswer);
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
}
