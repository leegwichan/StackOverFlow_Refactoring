package com.team17.preProject.domain.answer.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.team17.preProject.domain.answer.dto.AnswerDto;
import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.question.entity.Question;
import com.team17.preProject.helper.stub.AnswerStub;
import com.team17.preProject.helper.stub.MemberStub;
import com.team17.preProject.helper.stub.QuestionStub;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.util.List;

public class AnswerMapperTest {

    private final AnswerMapper answerMapper = Mappers.getMapper(AnswerMapper.class);
    private static final Answer MOCK_ANSWER = AnswerStub.ENTITY;

    @Test
    void answerPostDtoToAnswer() {
        AnswerDto.Post dto = new AnswerDto.Post("answer_content", 5L, 3L);
        Question question = new Question(); question.setQuestionId(5L);
        Member member = new Member(); member.setMemberId(3L);
        Answer expected = new Answer();
        expected.setContent("answer_content");
        expected.setQuestion(question);
        expected.setMember(member);

        Answer result = answerMapper.answerPostDtoToAnswer(dto);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void answerPatchDtoToAnswer() {
        AnswerDto.Patch dto = new AnswerDto.Patch("answer_content");
        Answer expected = new Answer();
        expected.setContent("answer_content");

        Answer result = answerMapper.answerPatchDtoToAnswer(dto);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void answerToAnswerResponseDtoTest() {
        AnswerDto.Response expected = mockResponseDto();

        AnswerDto.Response result = answerMapper.answerToAnswerResponseDto(MOCK_ANSWER);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void answersToAnswerResponseDtodTest() {
        List<Answer> answers = List.of(MOCK_ANSWER, MOCK_ANSWER, MOCK_ANSWER);
        List<AnswerDto.Response> expected = List.of(mockResponseDto(), mockResponseDto(), mockResponseDto());

        List<AnswerDto.Response> result = answerMapper.answersToAnswerResponseDtos(answers);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void answerToAnswerFullResponseDtoTest() {
        AnswerDto.FullResponse expected = mockFullResponseDto();

        AnswerDto.FullResponse result = answerMapper.answerToAnswerFullResponseDto(MOCK_ANSWER);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void answersToAnswerFullResponseDtosTest() {
        List<Answer> answers = List.of(MOCK_ANSWER, MOCK_ANSWER);
        List<AnswerDto.FullResponse> expected = List.of(mockFullResponseDto(), mockFullResponseDto());

        List<AnswerDto.FullResponse> result = answerMapper.answersToAnswerFullResponseDtos(answers);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    AnswerDto.Response mockResponseDto() {
        return new AnswerDto.Response(
                MOCK_ANSWER.getAnswerId(), MOCK_ANSWER.getContent(), MOCK_ANSWER.getVote(),
                MOCK_ANSWER.getCreatedAt(), MOCK_ANSWER.getModifiedAt(), MemberStub.SUB_RESPONSE);
    }

    AnswerDto.FullResponse mockFullResponseDto() {
        return new AnswerDto.FullResponse(
                MOCK_ANSWER.getAnswerId(), MOCK_ANSWER.getContent(), MOCK_ANSWER.getVote(),
                MOCK_ANSWER.getCreatedAt(), MOCK_ANSWER.getModifiedAt(), QuestionStub.SUB_RESPONSE);
    }
}
