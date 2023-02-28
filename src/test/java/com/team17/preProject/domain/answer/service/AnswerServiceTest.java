package com.team17.preProject.domain.answer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.answer.repository.AnswerRepository;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.member.service.MemberService;
import com.team17.preProject.domain.question.entity.Question;
import com.team17.preProject.domain.question.service.QuestionService;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.helper.stub.AnswerStub;
import com.team17.preProject.helper.stub.MemberStub;
import com.team17.preProject.helper.stub.QuestionStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {AnswerServiceImpl.class})
public class AnswerServiceTest {

    private static final Answer ANSWER_STUB = AnswerStub.ENTITY;

    @Autowired private AnswerService answerService;
    @MockBean private AnswerRepository answerRepository;
    @MockBean private QuestionService questionService;
    @MockBean private MemberService memberService;

    @Test
    void createAnswerTest() {
        Answer answer = Answer.builder()
                .content("content")
                .member(Member.builder().memberId(5L).build())
                .question(Question.builder().questionId(5L).build())
                .build();
        Member member = Member.builder()
                .memberId(5L)
                .email("email@email.com")
                .displayName("nickname")
                .build();
        Question question = Question.builder()
                .member(Member.builder().memberId(4L).build())
                .title("title").content("content")
                .build();
        given(memberService.findMember(5L)).willReturn(member);
        given(questionService.findQuestion(5L)).willReturn(question);
        given(answerRepository.save(answer)).willReturn(answer);

        Answer result = answerService.createAnswer(answer);

        assertThat(result.getMember()).usingRecursiveComparison().isEqualTo(member);
        assertThat(result.getQuestion()).usingRecursiveComparison().isEqualTo(question);
    }

    @Test
    void createAnswerTest_whenQuestionerEqualsAnswerer() {
        Answer answer = Answer.builder()
                .content("content")
                .member(Member.builder().memberId(5L).build())
                .question(Question.builder().questionId(5L).build())
                .build();
        Member member = Member.builder()
                .memberId(5L)
                .email("email@email.com")
                .displayName("nickname")
                .build();
        Question question = Question.builder()
                .member(Member.builder().memberId(5L).build())
                .title("title").content("content")
                .build();
        given(memberService.findMember(5L)).willReturn(member);
        given(questionService.findQuestion(5L)).willReturn(question);
        given(answerRepository.save(answer)).willReturn(answer);

        Exception result = assertThrows(BusinessLogicException.class,
                () -> answerService.createAnswer(answer));

        assertThat(result.getMessage()).isEqualTo("자신의 질문에 답변을 달 수 없습니다.");
    }
}
