package com.team17.preProject.domain.follow.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.team17.preProject.domain.follow.dto.FollowQuestionDto;
import com.team17.preProject.domain.follow.entity.FollowQuestion;
import com.team17.preProject.domain.question.entity.Question;
import com.team17.preProject.helper.stub.MemberStub;
import com.team17.preProject.helper.stub.QuestionStub;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class FollowQuestionMapperTest {

    private static final FollowQuestionMapper mapper = Mappers.getMapper(FollowQuestionMapper.class);
    private static final FollowQuestion MOCK_ENTITY = new FollowQuestion();
    static {
        MOCK_ENTITY.setMember(MemberStub.getEntity());
        Question mockQuestion = QuestionStub.getEntity();
        mockQuestion.setMember(MemberStub.getEntity());
        MOCK_ENTITY.setQuestion(mockQuestion);
    }

    @Test
    void followQuestionToFollowQuestionPostResponseDto() {
        FollowQuestionDto.PostResponse expected = new FollowQuestionDto.PostResponse(
                MemberStub.getSubResponse(), QuestionStub.getSubResponse());

        FollowQuestionDto.PostResponse result = mapper.followQuestionToFollowQuestionPostResponseDto(MOCK_ENTITY);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }
}
