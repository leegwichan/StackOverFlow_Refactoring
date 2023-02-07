package com.team17.preProject.domain.answer.mapper;

import com.team17.preProject.domain.answer.dto.AnswerDto;
import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.member.dto.MemberDto;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.question.dto.QuestionDto;
import com.team17.preProject.domain.question.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
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
        return new AnswerDto.FullResponse(
                answer.getAnswerId(),
                answer.getContent(),
                answer.getVote(),
                answer.getCreatedAt(),
                answer.getModifiedAt(),
                questionToQuestionSubResponseDto(answer.getQuestion())
        );
    }

    default List<AnswerDto.FullResponse> answersToAnswerFullResponseDtos(List<Answer> answers){
        return answers.stream()
                .map(answer -> answerToAnswerFullResponseDto(answer))
                .collect(Collectors.toList());
    }
    default List<AnswerDto.Response> answersToAnswerResponseDtos(List<Answer> answers){
        return answers.stream()
                .map(answer -> answerToAnswerResponseDto(answer))
                .collect(Collectors.toList());
    }
    default Answer answerPostDtoToAnswer(AnswerDto.Post post){
        Answer answer = new Answer();
        answer.setContent(post.getContent());

        Question question = new Question();
        question.setQuestionId(post.getQuestionId());
        answer.setQuestion(question);

        Member member = new Member();
        member.setMemberId(post.getMemberId());
        answer.setMember(member);

        return answer;
    }
    Answer answerPatchDtoToAnswer(AnswerDto.Patch patch);
    MemberDto.SubResponse memberToMemberSubResponseDto(Member member);
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
                bestAnswer != null ? bestAnswer.getAnswerId() : null
        );
    }
}
