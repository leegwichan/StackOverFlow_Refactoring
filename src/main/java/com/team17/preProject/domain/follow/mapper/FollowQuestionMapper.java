package com.team17.preProject.domain.follow.mapper;

import com.team17.preProject.domain.follow.entity.FollowQuestion;
import com.team17.preProject.domain.follow.dto.FollowQuestionDto;
import com.team17.preProject.domain.member.mapper.MemberMapper;
import com.team17.preProject.domain.question.dto.QuestionDto;
import com.team17.preProject.domain.question.mapper.QuestionMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class, MemberMapper.class})
public interface FollowQuestionMapper {

    FollowQuestionDto.PostResponse followQuestionToFollowQuestionPostResponseDto(FollowQuestion followQuestion);

    List<QuestionDto.SubResponse> followQuestionsToQuestionSubResponseDtos(List<FollowQuestion> followQuestions);

    @Mapping(target = ".", source = "followQuestion.question")
    QuestionDto.SubResponse followQuestionToQuestionSubResponseDto(FollowQuestion followQuestion);
}
