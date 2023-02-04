package com.team17.preProject.follow.mapper;

import com.team17.preProject.answer.entity.Answer;
import com.team17.preProject.follow.dto.FollowQuestionDto;
import com.team17.preProject.follow.entity.FollowQuestion;
import com.team17.preProject.member.dto.MemberDto;
import com.team17.preProject.member.entity.Member;
import com.team17.preProject.question.dto.QuestionDto;
import com.team17.preProject.question.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FollowQuestionMapper {
    default List<QuestionDto.SubResponse> FollowQuestionsToQuestionSubResponseDto(
            List<FollowQuestion> followQuestions){

        return followQuestions.stream().map(
                followQuestion -> questionToQuestionSubResponseDto(followQuestion.getQuestion()))
                .collect(Collectors.toList());
    }

    default FollowQuestionDto.PostResponse followQuestionToFollowQuestionPostResponseDto(FollowQuestion followQuestion){
        return new FollowQuestionDto.PostResponse(
                memberToMemberSubResponseDto(followQuestion.getMember()),
                questionToQuestionSubResponseDto(followQuestion.getQuestion())
        );
    }

    default QuestionDto.SubResponse questionToQuestionSubResponseDto(Question question){

        Answer bestAnswer = question.getBestAnswer();


        return new QuestionDto.SubResponse(
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
    }

    MemberDto.SubResponse memberToMemberSubResponseDto(Member member);
}
