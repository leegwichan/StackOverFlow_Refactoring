package com.team17.preProject.domain.answer.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.team17.preProject.domain.answer.dto.AnswerDto;
import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.member.mapper.MemberMapperImpl;
import com.team17.preProject.domain.question.entity.Question;
import com.team17.preProject.helper.stub.AnswerStub;
import com.team17.preProject.helper.stub.MemberStub;
import com.team17.preProject.helper.stub.QuestionStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest(classes = {AnswerMapperImpl.class, MemberMapperImpl.class})
public class AnswerMapperTest {

    @Autowired
    private AnswerMapper answerMapper;
    private static final Answer MOCK_ANSWER = AnswerStub.getEntity();
    static {
        MOCK_ANSWER.setQuestion(QuestionStub.getChangeableEntity());
        MOCK_ANSWER.getQuestion().setMember(MemberStub.getChangeableEntity());
        MOCK_ANSWER.setMember(MemberStub.getChangeableEntity());
    }

    @Test
    void answerPostDtoToAnswer() {
        AnswerDto.Post dto = new AnswerDto.Post("answer_content", 5L, 3L);
        Question question = Question.builder().questionId(5L).build();
        Member member = Member.builder().memberId(3L).build();
        Answer expected = Answer.builder().content("answer_content")
                        .question(question).member(member).build();

        Answer result = answerMapper.answerPostDtoToAnswer(dto);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void answerPatchDtoToAnswer() {
        AnswerDto.Patch dto = new AnswerDto.Patch(5L,"answer_content");
        Answer expected = Answer.builder().answerId(5L).content("answer_content").build();

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
    void answersToAnswerResponseDtosTest() {
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
                MOCK_ANSWER.getCreatedAt(), MOCK_ANSWER.getModifiedAt(), MemberStub.getSubResponse());
    }

    AnswerDto.FullResponse mockFullResponseDto() {
        return new AnswerDto.FullResponse(
                MOCK_ANSWER.getAnswerId(), MOCK_ANSWER.getContent(), MOCK_ANSWER.getVote(),
                MOCK_ANSWER.getCreatedAt(), MOCK_ANSWER.getModifiedAt(), QuestionStub.getSubResponse());
    }
}
