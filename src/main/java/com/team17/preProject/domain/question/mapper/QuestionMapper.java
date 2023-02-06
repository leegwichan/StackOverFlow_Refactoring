package com.team17.preProject.domain.question.mapper;

import com.team17.preProject.domain.answer.dto.AnswerDto;
import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.member.dto.MemberDto;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.question.entity.Question;
import com.team17.preProject.domain.question.dto.QuestionDto;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    default Question questionPostDtoToQuestion(QuestionDto.Post post){
         Question question = new Question();
         question.setTitle(post.getTitle());
         question.setContent(post.getContent());

         Member memberData = new Member();
         memberData.setMemberId(post.getMemberId());
         question.setMember(memberData);

         return question;
    }
    Question questionPatchDtoToQuestion(QuestionDto.Patch patch);
    default QuestionDto.Response questionToQuestionResponseDto(Question question){

        Answer bestAnswer = question.getBestAnswer();
        return new QuestionDto.Response(
                question.getQuestionId(),
                question.getTitle(),
                question.getContent(),
                question.getView(),
                question.getVote(),
                question.getCreatedAt(),
                question.getModifiedAt(),
                memberToMemberSubResponseDto(question.getMember()),
                answersToAnswerResponseDtos(question.getAnswers()),
                question.getAnswers().size(),
                bestAnswer != null ? bestAnswer.getAnswerId() : null
        );
    };
    default List<QuestionDto.SubResponse> questionsToQuestionSubResponseDtos(List<Question> question){
        return question.stream()
                .map(question1 -> questionToQuestionSubResponseDto(question1))
                .collect(Collectors.toList());
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
                bestAnswer != null ? bestAnswer.getAnswerId() : null
        );
    }

    MemberDto.SubResponse memberToMemberSubResponseDto(Member member);
    List<AnswerDto.Response> answersToAnswerResponseDtos(List<Answer> answer);
}
