package com.team17.preProject.domain.question.mapper;

import com.team17.preProject.domain.answer.mapper.AnswerMapper;
import com.team17.preProject.domain.member.mapper.MemberMapper;
import com.team17.preProject.domain.question.entity.Question;
import com.team17.preProject.domain.question.dto.QuestionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {MemberMapper.class, AnswerMapper.class})
public interface QuestionMapper {

    @Mapping(target = "member.memberId", source = "memberId")
    Question questionPostDtoToQuestion(QuestionDto.Post post);

    Question questionPatchDtoToQuestion(QuestionDto.Patch patch);

    @Mapping(target = "answerCount", expression = "java(question.getAnswers().size())")
    @Mapping(target = "bestAnswerId",
            expression = "java(question.getBestAnswer() != null ? question.getBestAnswer().getAnswerId() : null)")
    QuestionDto.Response questionToQuestionResponseDto(Question question);

    @Mapping(target = "answerCount", expression = "java(question.getAnswers().size())")
    @Mapping(target = "bestAnswerId",
            expression = "java(question.getBestAnswer() != null ? question.getBestAnswer().getAnswerId() : null)")
    QuestionDto.SubResponse questionToQuestionSubResponseDto(Question question);
    List<QuestionDto.SubResponse> questionsToQuestionSubResponseDtos(List<Question> question);
}
