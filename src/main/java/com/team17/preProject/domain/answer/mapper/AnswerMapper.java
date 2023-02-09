package com.team17.preProject.domain.answer.mapper;

import com.team17.preProject.domain.answer.dto.AnswerDto;
import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.member.mapper.MemberMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {MemberMapper.class})
public interface AnswerMapper {

    @Mapping(target = "question.questionId", source = "questionId")
    @Mapping(target = "member.memberId", source = "memberId")
    Answer answerPostDtoToAnswer(AnswerDto.Post post);

    Answer answerPatchDtoToAnswer(AnswerDto.Patch patch);

    AnswerDto.Response answerToAnswerResponseDto(Answer answer);
    List<AnswerDto.Response> answersToAnswerResponseDtos(List<Answer> answers);
    AnswerDto.FullResponse answerToAnswerFullResponseDto(Answer answer);
    List<AnswerDto.FullResponse> answersToAnswerFullResponseDtos(List<Answer> answers);
}
