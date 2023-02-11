package com.team17.preProject.domain.follow.mapper;

import com.team17.preProject.domain.answer.dto.AnswerDto;
import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.follow.dto.FollowAnswerDto;
import com.team17.preProject.domain.follow.entity.FollowAnswer;
import com.team17.preProject.domain.member.dto.MemberDto;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.question.dto.QuestionDto;
import com.team17.preProject.domain.question.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")

public interface FollowAnswerMapper {
    default FollowAnswerDto.PostResponse followAnswerToFollowAnswerPostResponseDto(FollowAnswer followAnswer) {
        return new FollowAnswerDto.PostResponse(
                memberToMemberSubResponseDto(followAnswer.getMember()),
                answerToAnswerResponseDto(followAnswer.getAnswer())
        );
    }

    MemberDto.SubResponse memberToMemberSubResponseDto(Member member);
    default AnswerDto.Response answerToAnswerResponseDto(Answer answer){
        return new AnswerDto.Response(
                answer.getAnswerId(),
                answer.getContent(),
                answer.getVote(),
                answer.getCreatedAt(),
                answer.getModifiedAt(),
                memberToMemberSubResponseDto(answer.getMember())
        );
    }

    default AnswerDto.FullResponse answerToAnswerFullResponseDto(Answer answer){

        Question question = answer.getQuestion();

        Answer bestAnswer = question.getBestAnswer();
        QuestionDto.SubResponse questionDto =  new QuestionDto.SubResponse(
                question.getQuestionId(),
                question.getTitle(),
                question.getContent(),
                question.getView(),
                question.getVote(),
                question.getCreatedAt(),
                question.getModifiedAt(),
                memberToMemberSubResponseDto(question.getMember()),
                question.getAnswers().size(),
                bestAnswer != null ? bestAnswer.getAnswerId() : 0L
        );

        return new AnswerDto.FullResponse(
                answer.getAnswerId(),
                answer.getContent(),
                answer.getVote(),
                answer.getCreatedAt(),
                answer.getModifiedAt(),
                questionDto
        );
    }

    default List<AnswerDto.FullResponse> followAnswersToAnswerResponseDtos(List<FollowAnswer> followAnswers){
        return followAnswers.stream().map(
                followAnswer -> answerToAnswerFullResponseDto(followAnswer.getAnswer()))
                .collect(Collectors.toList());
    }
}
