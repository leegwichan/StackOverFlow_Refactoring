package com.team17.preProject.domain.follow.mapper;

import com.team17.preProject.domain.answer.dto.AnswerDto;
import com.team17.preProject.domain.answer.mapper.AnswerMapper;
import com.team17.preProject.domain.follow.dto.FollowAnswerDto;
import com.team17.preProject.domain.follow.entity.FollowAnswer;
import com.team17.preProject.domain.member.mapper.MemberMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMappings;
import org.mapstruct.control.MappingControl;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MemberMapper.class, AnswerMapper.class})

public interface FollowAnswerMapper {

    FollowAnswerDto.PostResponse followAnswerToFollowAnswerPostResponseDto(FollowAnswer followAnswer);

    List<AnswerDto.FullResponse> followAnswersToAnswerResponseDtos(List<FollowAnswer> followAnswers);

    @Mapping(target = ".", source = "followAnswer.answer")
    AnswerDto.FullResponse followAnswerToAnswerResponseDto(FollowAnswer followAnswer);
}
