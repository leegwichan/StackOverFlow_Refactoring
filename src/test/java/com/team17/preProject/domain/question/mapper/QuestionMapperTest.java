package com.team17.preProject.domain.question.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.team17.preProject.domain.answer.mapper.AnswerMapperImpl;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.member.mapper.MemberMapperImpl;
import com.team17.preProject.domain.question.dto.QuestionDto;
import com.team17.preProject.domain.question.entity.Question;
import com.team17.preProject.helper.stub.MemberStub;
import com.team17.preProject.helper.stub.QuestionStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {QuestionMapperImpl.class, AnswerMapperImpl.class, MemberMapperImpl.class})
public class QuestionMapperTest {

    @Autowired
    private QuestionMapper questionMapper;
    private static final Question MOCK_QUESTION = QuestionStub.ENTITY;


    @Test
    void questionPostDtoToQuestionTest() {
        QuestionDto.Post dto = new QuestionDto.Post("title", "content", 5L);
        Member member = Member.builder().memberId(5L).build();
        Question expected = Question.builder()
                .title("title")
                .content("content")
                .member(member)
                .build();

        Question result = questionMapper.questionPostDtoToQuestion(dto);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void questionPatchDtoToQuestionTest() {
        QuestionDto.Patch dto = new QuestionDto.Patch(5L,"title", "content");
        Question expected = Question.builder()
                .questionId(5L)
                .title("title")
                .content("content")
                .build();

        Question result = questionMapper.questionPatchDtoToQuestion(dto);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void questionToQuestionResponseDtoTest() {
        Question question = MOCK_QUESTION;
        QuestionDto.Response expected = mockResponseDto();

        QuestionDto.Response result = questionMapper.questionToQuestionResponseDto(question);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void questionToQuestionSubResponseDto() {
        Question question = MOCK_QUESTION;
        QuestionDto.SubResponse expected = mockSubResponseDto();

        QuestionDto.SubResponse result = questionMapper.questionToQuestionSubResponseDto(question);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void questionsToQuestionSubResponseDtos() {
        List<Question> questions = List.of(MOCK_QUESTION, MOCK_QUESTION, MOCK_QUESTION);
        List<QuestionDto.SubResponse> expected =
                List.of(mockSubResponseDto(), mockSubResponseDto(), mockSubResponseDto());

        List<QuestionDto.SubResponse> result = questionMapper.questionsToQuestionSubResponseDtos(questions);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    QuestionDto.Response mockResponseDto() {
        Question question = MOCK_QUESTION;
        QuestionDto.Response responseDto = new QuestionDto.Response();
        responseDto.setQuestionId(question.getQuestionId());
        responseDto.setTitle(question.getTitle());
        responseDto.setContent(question.getContent());
        responseDto.setView(question.getView());
        responseDto.setVote(question.getVote());
        responseDto.setCreatedAt(question.getCreatedAt());
        responseDto.setModifiedAt(question.getModifiedAt());
        responseDto.setAnswerCount(question.getAnswers().size());
        responseDto.setAnswers(new ArrayList<>());
        responseDto.setMember(MemberStub.SUB_RESPONSE);

        return responseDto;
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
