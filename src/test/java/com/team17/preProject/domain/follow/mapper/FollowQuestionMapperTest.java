package com.team17.preProject.domain.follow.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.team17.preProject.domain.answer.mapper.AnswerMapperImpl;
import com.team17.preProject.domain.follow.dto.FollowQuestionDto;
import com.team17.preProject.domain.follow.entity.FollowQuestion;
import com.team17.preProject.domain.member.mapper.MemberMapperImpl;
import com.team17.preProject.domain.question.dto.QuestionDto;
import com.team17.preProject.domain.question.entity.Question;
import com.team17.preProject.domain.question.mapper.QuestionMapperImpl;
import com.team17.preProject.helper.stub.MemberStub;
import com.team17.preProject.helper.stub.QuestionStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest(classes = {MemberMapperImpl.class, QuestionMapperImpl.class,
        AnswerMapperImpl.class, FollowQuestionMapperImpl.class})
public class FollowQuestionMapperTest {

    @Autowired
    private FollowQuestionMapper mapper;

    private static final FollowQuestion MOCK_ENTITY;
    private static final Question MOCK_QUESTION = QuestionStub.getChangeableEntity();
    static {
        MOCK_ENTITY = FollowQuestion.builder()
                .member(MemberStub.getChangeableEntity())
                .question(MOCK_QUESTION)
                .build();

        MOCK_QUESTION.setMember(MemberStub.getChangeableEntity());
    }

    @Test
    void followQuestionToFollowQuestionPostResponseDtoTest() {
        FollowQuestionDto.PostResponse expected = new FollowQuestionDto.PostResponse(
                MemberStub.getSubResponse(), QuestionStub.getSubResponse());

        FollowQuestionDto.PostResponse result = mapper.followQuestionToFollowQuestionPostResponseDto(MOCK_ENTITY);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void followQuestionsToQuestionSubResponseDtoTest() {
        List<FollowQuestion> followQuestions = List.of(MOCK_ENTITY, MOCK_ENTITY);
        List<QuestionDto.SubResponse> expected = List.of(mockSubResponseDto(), mockSubResponseDto());

        List<QuestionDto.SubResponse> result = mapper.followQuestionsToQuestionSubResponseDtos(followQuestions);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    QuestionDto.SubResponse mockSubResponseDto() {
        Question question = MOCK_QUESTION;
        QuestionDto.SubResponse dto = new QuestionDto.SubResponse();
        dto.setQuestionId(question.getQuestionId());
        dto.setTitle(question.getTitle());
        dto.setContent(question.getContent());
        dto.setView(question.getView());
        dto.setVote(question.getVote());
        dto.setCreatedAt(question.getCreatedAt());
        dto.setModifiedAt(question.getModifiedAt());
        dto.setAnswerCount(question.getAnswers().size());
        dto.setMember(MemberStub.SUB_RESPONSE);

        return dto;
    }
}
