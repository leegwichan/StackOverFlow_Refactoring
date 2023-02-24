package com.team17.preProject.domain.question.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.member.service.MemberService;
import com.team17.preProject.domain.question.entity.Question;
import com.team17.preProject.domain.question.repository.QuestionRepository;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.helper.stub.MemberStub;
import com.team17.preProject.helper.stub.QuestionStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;

@SpringBootTest(classes = {QuestionServiceImpl.class})
public class QuestionServiceTest {

    @Autowired private QuestionService questionService;
    @MockBean private QuestionRepository repository;
    @MockBean private MemberService memberService;

    private static final Question QUESTION_STUB = QuestionStub.ENTITY;

    @Test
    void findQuestionTest() {
        Question questionStub = QuestionStub.getChangeableEntity();
        long questionId = questionStub.getQuestionId();
        long beforeView = questionStub.getView();
        given(repository.findById(questionId)).willReturn(Optional.of(questionStub));
        given(repository.save(any())).willReturn(questionStub);

        Question result = questionService.findQuestion(questionId);

        assertThat(result).usingRecursiveComparison().isEqualTo(questionStub);
        assertThat(result.getView()).isEqualTo(beforeView + 1);
    }

    @Test
    void findQuestionTest_whenQuestionNotExist() {
        long questionId = 1L;
        given(repository.findById(questionId)).willReturn(Optional.empty());

        Exception result = assertThrows(BusinessLogicException.class,
                () -> questionService.findQuestion(questionId));
        assertThat(result.getMessage()).isEqualTo("Question not found");
    }

    @Test
    void createQuestionTest() {
        Question question = QuestionStub.getChangeableEntity();
        Member memberInfo = Member.builder().memberId(1L).build();
        question.setInitialMember(memberInfo);
        Member findMember = MemberStub.ENTITY;
        given(memberService.findMember(memberInfo.getMemberId())).willReturn(findMember);
        given(repository.save(any())).willReturn(question);

        Question result = questionService.createQuestion(question);

        assertThat(result).usingRecursiveComparison().isEqualTo(question);
        assertThat(result.getMember()).isEqualTo(findMember);
    }

    @Test
    void updateQuestionTest() {
        Question found = QuestionStub.getChangeableEntity();
        Question update = Question.builder()
                .title("update title")
                .build();
        Question expected = Question.builder()
                .questionId(found.getQuestionId())
                .title(update.getTitle())
                .content(found.getContent())
                .view(found.getView())
                .vote(found.getVote())
                .member(found.getMember())
                .build();
        given(repository.findById(update.getQuestionId())).willReturn(Optional.of(found));
        given(repository.save(found)).willReturn(found);

        Question result = questionService.updateQuestion(update);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void updateQuestionTest_whenQuestionDoesNotExist() {
        Question questionStub = QUESTION_STUB;
        given(repository.findById(questionStub.getQuestionId())).willReturn(Optional.empty());

        Exception result = assertThrows(BusinessLogicException.class,
                () -> questionService.updateQuestion(questionStub));
        assertThat(result.getMessage()).isEqualTo("Question not found");
    }

    @Test
    void deleteQuestionTest() {
        Question questionStub = QUESTION_STUB;
        given(repository.findById(questionStub.getQuestionId())).willReturn(Optional.of(questionStub));

        assertDoesNotThrow(() -> questionService.deleteQuestion(questionStub.getQuestionId()));
    }

    @Test
    void deleteQuestionTest_whenQuestionNotExist() {
        given(repository.findById(any())).willReturn(Optional.empty());

        Exception result = assertThrows(BusinessLogicException.class,
                () -> questionService.deleteQuestion(1L));
        assertThat(result.getMessage()).isEqualTo("Question not found");
    }
}
